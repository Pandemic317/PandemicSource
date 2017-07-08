package server.model.players.Content.AuctionHouse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import server.model.items.GameItem;
import server.model.items.ItemAssistant;
import server.model.players.Client;
import server.model.players.Player;
import server.model.players.PlayerHandler;
import server.util.Misc;

public class AuctionInterface {

	private static final int MAIN_INTERFACE = 53000, SELL_INTERFACE = 53100, BUY_INTERFACE = 53200;
	private static final int ITEM_ID = 5021, MIN_BID = 10, MAX_BID = 1000000, MAX_OFFERS = 10;
	private static final String ITEM_NAME = ItemAssistant.getItemName2(ITEM_ID);
	private static final int DEFAULT_ITEM_TICKS = 3000;
	private static final String AUCTION_START = "<col=9895936>[AUCTION HOUSE] ";
	private static final String[] SELL_INFO = { "               How to sell items:",
			"1. When in the main screen or sell", "screen, click an item in your ",
			"inventory to load it onto the sell ", "screen. If you'd like to sell more ",
			"than one of the item, right click it.", "2. You can now press the \"Edit ",
			"Price\" or \"Shop Price\" button to ", "modify the price of the item.",
			"3. The min bid is " + MIN_BID + " @red@" + ITEM_NAME + "@whi@s.",
			"The max bid is " + Misc.formatMoney(MAX_BID) + " @red@" + ITEM_NAME + "@whi@s.",
			"4. Verify the price and item is", " correct, and press \"Confirm\".", "5. Wait the 1 hour for the auction",
			"to end, and your tickets will be", "deposited to your bank." };
	private static final String[] BUY_INFO = { "             How to bid on items:", "1. When on the main screen, click",
			"on an item you want to bid on.", "2. On the Buy interface (this one),",
			"Press \"Min. Price\" to set your bid", "to the min. value needed or press",
			"\"Edit Bid\" to enter a custom value.", "3. Verify the price is above the",
			"current bid, and that the item is", "correct, then press \"Confirm\".", "If someone outbids you, you will",
			"be notified @red@only@whi@ if you're online." };
	private static final int[] BUY_INTERFACE_IDS = { 53021, 53028, 53035, 53042, 53049, 53056, 53063, 53070, 53077,
			53084 };
	private static final int[] RESTRICTED_ITEMS = { 5020, 5021, 4067, 13663, 896, 995 };

	public static ArrayList<AuctionItem> auctionList = new ArrayList<AuctionItem>();
	public static ArrayList<AuctionItem> unclaimedBids = new ArrayList<AuctionItem>();
	public static ArrayList<AuctionItem> unclaimedItems = new ArrayList<AuctionItem>();
	private static ArrayList<AuctionItem> toRemove = new ArrayList<AuctionItem>();

	private Client c;
	private AuctionItem selectedItem;
	private int currentPage;
	private int openAuctionInterface;
	private int buyItemIndex;
	private int buyItemBid;
	private String searchTerm;
	private boolean myItems;

	public AuctionInterface(Client c) {
		this.c = c;
	}

	private static void writeLog(AuctionItem item) {
		try (BufferedWriter wr = new BufferedWriter(new FileWriter("./Data/AuctionHouse/AuctionLogs.txt"))) {
			wr.write(Misc.getTimestamp() + " " + item.getItemOwner() + " Sold [" + item.getAmount() + "] x ["
					+ ItemAssistant.getItemName2(item.getItemId()) + " (" + item.getItemId() + ")]" + " for "
					+ item.getCurrentBid() + " " + ItemAssistant.getItemName2(ITEM_ID) + " to ["
					+ item.getCurrentBidder() + "]");
			wr.newLine();
			wr.flush();
		} catch (IOException e) {

		}
	}

	public static void updateItems() {
		boolean updateUnclaimed = false;
		for (AuctionItem item : auctionList) {
			if (item != null) {
				item.setTicksRemaining(item.getTicksRemaining() - 1);
			}
			if (item != null && item.getTicksRemaining() < 1) {
				Client owner = Client.getClient(item.getItemOwner());
				Client bidder = Client.getClient(item.getCurrentBidder());
				writeLog(item);
				toRemove.add(item);
				if (bidder == null && item.getCurrentBidder() == null) {
					item.setCurrentBidder(item.getItemOwner());
					if (owner == null) {
						unclaimedItems.add(item);
						updateUnclaimed = true;
					} else {
						owner.getItems().addItemToBank(item.getItemId(), item.getAmount());
						owner.sendMessage(AUCTION_START + "Your item did not sell. It's been sent to your bank.");
					}
					continue;
				}
				if (bidder == null && !item.getCurrentBidder().equalsIgnoreCase(item.getItemOwner())) {
					unclaimedItems.add(item);
					updateUnclaimed = true;
				}
				if (bidder != null && !item.getCurrentBidder().equalsIgnoreCase(item.getItemOwner())) {
					bidder.getItems().addItemToBank(item.getItemId(), item.getAmount());
					bidder.sendMessage(AUCTION_START + "You won the bid for " + item.getAmount() + "x "
							+ ItemAssistant.getItemName2(item.getItemId()) + ".");
					PlayerHandler.sendGlobalMessage("[AUCTION]", "" + bidder.playerName
							+ "   has just won " + item.getAmount() + "x "
							+ ItemAssistant.getItemName2(item.getItemId()) + " at auction for " + item.getCurrentBid() + "B!");
				}
				if (owner == null && !item.getCurrentBidder().equalsIgnoreCase(item.getItemOwner())) {
					unclaimedBids.add(item);
					updateUnclaimed = true;
				}
				if (owner != null && !item.getCurrentBidder().equalsIgnoreCase(item.getItemOwner())) {
					owner.getItems().addItemToBank(ITEM_ID, item.getCurrentBid());
					owner.sendMessage(AUCTION_START + "You sold your " + ItemAssistant.getItemName2(item.getItemId())
							+ " for " + item.getCurrentBid() + " " + ItemAssistant.getItemName2(ITEM_ID) + ".");
				}
			}
		}
		for (AuctionItem i : toRemove) {
			removeItem(i);
		}
		toRemove.clear();
		updateInterfaces();
		if (updateUnclaimed)
			saveUnclaimed();
	}

	public static boolean saveUnclaimed() {
		Gson g = new Gson();
		try (BufferedWriter w = new BufferedWriter(new FileWriter("./Data/AuctionHouse/UnclaimedBids.json"));
				BufferedWriter wr = new BufferedWriter(new FileWriter("./Data/AuctionHouse/UnclaimedItems.json"))) {
			w.write(g.toJson(unclaimedBids));
			w.flush();
			wr.write(g.toJson(unclaimedItems));
			wr.flush();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean loadUnclaimed() {
		Gson g = new Gson();
		try (BufferedReader r = new BufferedReader(new FileReader("./Data/AuctionHouse/UnclaimedBids.json"));
				BufferedReader rw = new BufferedReader(new FileReader("./Data/AuctionHouse/UnclaimedItems.json"))) {
			unclaimedBids = g.fromJson(r, new TypeToken<ArrayList<AuctionItem>>() {
			}.getType());
			unclaimedItems = g.fromJson(rw, new TypeToken<ArrayList<AuctionItem>>() {
			}.getType());
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public void checkUnclaimed() {
		if (c == null)
			return;
		int unclaimedBid = 0;
		int unclaimedItem = 0;
		try {
			if (unclaimedBids != null && !unclaimedBids.isEmpty()) {
				for (AuctionItem i : unclaimedBids) {
					if (i != null && i.getItemOwner().equalsIgnoreCase(c.playerName)) {
						toRemove.add(i);
						c.getItems().addItemToBank(ITEM_ID, i.getAmount());
						unclaimedBid++;
					}
				}
			}
			if (toRemove != null && !toRemove.isEmpty()) {
				for (AuctionItem i : toRemove)
					unclaimedBids.remove(i);
				toRemove.clear();
			}
			if (unclaimedItems != null && !unclaimedItems.isEmpty()) {
				for (AuctionItem i : unclaimedItems) {
					if (i != null && i.getCurrentBidder().equalsIgnoreCase(c.playerName)) {
						toRemove.add(i);
						c.getItems().addItemToBank(i.getItemId(), i.getAmount());
						unclaimedItem++;
					}
				}
			}
			if (toRemove != null && !toRemove.isEmpty()) {
				for (AuctionItem i : toRemove)
					unclaimedItems.remove(i);
				toRemove.clear();
			}

		} catch (NullPointerException er) {
			System.out.println("[AuctionHouse]" + er.getMessage());
			unclaimedBid = 0;
			unclaimedItem = 0;
		}
		if (unclaimedBid > 0) {
			c.sendMessage(AUCTION_START + "You had " + unclaimedBid + " unclaimed bids. Your "
					+ ItemAssistant.getItemName2(ITEM_ID) + "'s have been sent banked.");
		}
		if (unclaimedItem > 0) {
			c.sendMessage(AUCTION_START + "You had " + unclaimedItem
					+ " unclaimed items. Your auction winnings have been sent banked.");
		}
		if (unclaimedBid > 0 || unclaimedItem > 0)
			saveUnclaimed();
	}

	private static void updateInterfaces() {
		for (Player p : PlayerHandler.players) {
			Client cl = (Client) p;
			if (cl != null && cl.getAuctions().openInterface()) {
				switch (cl.getAuctions().openAuctionInterface) {
				case MAIN_INTERFACE:
					cl.getAuctions().refreshMainInterface();
					break;
				case BUY_INTERFACE:
					cl.getAuctions().refreshBuyInterface();
					break;
				}
			}
		}
	}

	public static boolean load() {
		Gson g = new Gson();
		try (BufferedReader r = new BufferedReader(new FileReader("./Data/AuctionData.json"))) {
			ArrayList<AuctionItem> gsonList = g.fromJson(r, new TypeToken<ArrayList<AuctionItem>>() {
			}.getType());
			if (gsonList != null) {
				auctionList = gsonList;
			}
			return loadUnclaimed();
		} catch (IOException e) {
		}
		return false;
	}

	public static boolean save() {
		Gson g = new Gson();
		try (BufferedWriter w = new BufferedWriter(new FileWriter("./Data/AuctionData.json"))) {
			w.write(g.toJson(auctionList));
			w.flush();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void addItem(AuctionItem i) {
		if (i == null)
			return;
		auctionList.add(i);
		save();
	}

	public static void removeItem(AuctionItem i) {
		if (i == null)
			return;
		auctionList.remove(i);
		save();
	}

	public void openAuction() {
		if (c == null || c.playerIsBusy())
			return;
		myItems = false;
		currentPage = 0;
		openAuctionInterface = MAIN_INTERFACE;
		refreshMainInterface();
		c.getPA().interfaceWithInventory(MAIN_INTERFACE, 53150);
	}

	public void openAuctionSell() {
		if (c == null || c.playerIsBusy())
			return;
		openAuctionInterface = SELL_INTERFACE;
		for (int i = 0; i < 19; i++) {
			c.getPA().sendFrame126(i >= SELL_INFO.length ? "" : SELL_INFO[i], 53115 + i);
		}
		refreshSellInterface();
		c.getPA().interfaceWithInventory(SELL_INTERFACE, 53150);
		c.getItems().resetItems(53151);
	}

	public void auctionItem(GameItem item) {
		if (!openInterface() || item == null)
			return;
		if (openAuctionInterface == BUY_INTERFACE) {
			c.sendMessage("Please finish what you're doing first.");
			return;
		}
		if (badItem(item.id)) {
			c.sendMessage(AUCTION_START + "We're sorry. This item cannot be sold in the Auction House.");
			return;
		}
		selectedItem = new AuctionItem(item.id, item.amount, DEFAULT_ITEM_TICKS, c.playerName, null, MIN_BID);
		if (openAuctionInterface == MAIN_INTERFACE)
			openAuctionSell();
		else
			refreshSellInterface();
	}

	public void refreshSellInterface() {
		if (selectedItem == null || !c.getItems().playerHasItem(selectedItem.getItemId(), selectedItem.getAmount())) {
			c.getPA().displayItemOnInterface(53110, -1, 0, 0);
			c.getPA().sendFrame126("", 53113);
			c.getPA().sendFrame126("0 " + ITEM_NAME, 53114);
		} else {
			c.getPA().displayItemOnInterface(53110, selectedItem.getItemId(), 0, selectedItem.getAmount());
			c.getPA().sendFrame126(c.getItems().getItemName(selectedItem.getItemId()), 53113);
			c.getPA().sendFrame126(selectedItem.getCurrentBid() + " " + ITEM_NAME, 53114);
		}
	}

	public void refreshMainInterface() {
		if (c == null || c.playerIsBusy() || !openInterface() || openAuctionInterface != MAIN_INTERFACE)
			return;
		ArrayList<AuctionItem> list = searchTerm != null ? filteredList(searchTerm) : auctionList;
		for (int i = 0; i < 10; i++) {
			AuctionItem it = (list.size() == 0 || (currentPage == 0 ? i : i + (currentPage * 10)) >= list.size()
					|| list.isEmpty()) ? null : list.get((currentPage == 0 ? i : i + (currentPage * 10)));
			if (it != null && (myItems != it.getItemOwner().equalsIgnoreCase(c.playerName)))
				it = null;
			if (it == null) {
				c.getPA().sendHideInterfaceLayer(53019 + (i * 7), true);
				c.getPA().displayItemOnInterface(53021 + (i * 7), 0, 0, 0);
				c.getPA().sendFrame126("0 Minutes", 53024 + (i * 7));
				c.getPA().sendFrame126("0 " + ItemAssistant.getItemName2(ITEM_ID), 53024 + (i * 7));
			} else {
				c.getPA().displayItemOnInterface(53021 + (i * 7), it.getItemId(), 0, it.getAmount());
				c.getPA().sendHideInterfaceLayer(53019 + (i * 7), false);
				c.getPA().sendFrame126(getTime(it.getTicksRemaining()), 53024 + (i * 7));
				c.getPA().sendFrame126(Integer.toString(it.getCurrentBid()) + " " + ItemAssistant.getItemName2(ITEM_ID),
						53025 + (i * 7));
			}
		}
		c.getPA().sendHideInterfaceLayer(53090, (searchTerm != null || myItems) ? false : true);
		c.getPA().sendFrame126(Integer.toString(currentPage), 53018);
		c.getItems().resetItems(53151);

	}

	public void refreshBuyInterface() {
		if (buyItemIndex < 0 || !openInterface() || openAuctionInterface != BUY_INTERFACE)
			return;
		AuctionItem item = buyItemIndex >= (searchTerm != null ? filteredList(searchTerm) : auctionList).size() ? null
				: (searchTerm != null ? filteredList(searchTerm) : auctionList).get(buyItemIndex);
		if (item == null) {
			c.sendMessage("This item is no longer in the Auction House.");
			openAuction();
			return;
		}
		c.getPA().sendFrame126(item.getCurrentBid() + " " + ItemAssistant.getItemName2(ITEM_ID), 53214);
		c.getPA().sendFrame126(getTime(item.getTicksRemaining()), 53241);
		c.getPA().sendFrame126(buyItemBid + " " + ItemAssistant.getItemName2(ITEM_ID), 53216);
	}

	public void setSellPrice(int price) {
		if (selectedItem == null || !c.getItems().playerHasItem(selectedItem.getItemId(), selectedItem.getAmount())
				|| !openInterface() || openAuctionInterface != SELL_INTERFACE)
			return;
		if (price < MIN_BID)
			price = MIN_BID;
		if (price > MAX_BID)
			price = MAX_BID;
		selectedItem.setCurrentBid(price);
		refreshSellInterface();
	}

	public void setDefaultPrice() {
		setSellPrice(MIN_BID);
	}

	public void removeSellItem(int item) {
		if (selectedItem == null || !c.getItems().playerHasItem(selectedItem.getItemId(), selectedItem.getAmount())
				|| !openInterface() || openAuctionInterface != SELL_INTERFACE)
			return;
		selectedItem = null;
		refreshSellInterface();
	}

	public void searchForItem(String item) {
		if (!openInterface() || openAuctionInterface != MAIN_INTERFACE || item == null)
			return;
		searchTerm = item;
		currentPage = 0;
		c.sendMessage(AUCTION_START + "Searching auction for: " + item);
		refreshMainInterface();
	}

	private ArrayList<AuctionItem> filteredList(String itemName) {
		if (itemName == null)
			return null;
		itemName = itemName.replaceAll("_", " ").toLowerCase().trim();
		ArrayList<AuctionItem> list = new ArrayList<AuctionItem>();
		for (AuctionItem i : auctionList) {
			try {
				String foundItem = ItemAssistant.getItemName2(i.getItemId());
				foundItem = foundItem.toLowerCase().trim();
				if (i != null
						&& (foundItem.equalsIgnoreCase(itemName) || foundItem.contains(itemName)
								|| foundItem.startsWith(itemName) || foundItem.endsWith(itemName))
						&& (myItems = i.getItemOwner().equalsIgnoreCase(c.playerName))) {
					list.add(i);
				}
			} catch (Exception e) {

			}
		}
		return list;
	}

	public void closeSearch() {
		searchTerm = null;
		if (myItems)
			myItems = false;
		openAuction();
	}

	public boolean openInterface() {
		return openAuctionInterface > 0;
	}

	public void close() {
		c.getPA().closeAllWindows();
		openAuctionInterface = 0;
		selectedItem = null;
		currentPage = 0;
		myItems = false;
		searchTerm = null;
	}

	private static String getTime(int ticks) {
		int time = ticks * 600;
		int seconds = (time / 1000);
		if (seconds > 60)
			return (seconds / 60) + " Minutes";
		else
			return seconds + " Seconds";
	}

	public void sellItem() {
		if (selectedItem == null || !c.getItems().playerHasItem(selectedItem.getItemId(), selectedItem.getAmount())
				|| !openInterface() || openAuctionInterface != SELL_INTERFACE)
			return;
		if (maxSellOffers()) {
			c.sendMessage("You already have " + MAX_OFFERS + " items for auction. Please wait for some to finish.");
			return;
		}
		c.getItems().deleteItem2(selectedItem.getItemId(), selectedItem.getAmount());
		addItem(selectedItem);
		openAuction();
		c.sendMessage(AUCTION_START + "Your " + selectedItem.getAmount() + "x "
				+ ItemAssistant.getItemName2(selectedItem.getItemId()) + " have been added to the auction house.");
		selectedItem = null;
	}

	private boolean maxBuyOffers() {
		int count = 0;
		for (AuctionItem i : auctionList) {
			if (i.getCurrentBidder() != null && i.getCurrentBidder().equalsIgnoreCase(c.playerName))
				count++;
		}
		return count >= MAX_OFFERS;
	}

	private boolean maxSellOffers() {
		int count = 0;
		for (AuctionItem i : auctionList) {
			if (i.getItemOwner() != null && i.getItemOwner().equalsIgnoreCase(c.playerName))
				count++;
		}
		return count >= MAX_OFFERS;
	}

	private boolean badItem(int item) {
		return Arrays.stream(RESTRICTED_ITEMS).anyMatch(i -> i == item);
	}

	public void changePage(int pageChange) {
		if (!openInterface() || openAuctionInterface != MAIN_INTERFACE || pageChange == 0)
			return;
		ArrayList<AuctionItem> list = searchTerm != null ? filteredList(searchTerm) : auctionList;
		if ((pageChange > 0 && ((currentPage * 10) + 10) > list.size()) || (pageChange < 0 && currentPage == 0)) {
			c.sendMessage("There are no pages left.");
			return;
		}
		currentPage += pageChange;
		refreshMainInterface();
	}

	public boolean selectBuyItem(int interfaceId, int slotId, int itemId) {
		if (c == null || c.playerIsBusy() || !openInterface() || openAuctionInterface != MAIN_INTERFACE)
			return false;

		int ind = -1;
		for (int i = 0; i < BUY_INTERFACE_IDS.length; i++) {
			if (interfaceId == BUY_INTERFACE_IDS[i]) {
				ind = i;
				break;
			}
		}
		if (ind == -1)
			return false;
		if (maxBuyOffers()) {
			c.sendMessage(AUCTION_START + "You already have the maximum amount of bids.");
			return true;
		}
		ind = (currentPage * 10) + ind;
		AuctionItem item = ind >= (searchTerm != null ? filteredList(searchTerm) : auctionList).size() ? null
				: (searchTerm != null ? filteredList(searchTerm) : auctionList).get(ind);
		if (item == null) {
			c.sendMessage(AUCTION_START + "This item is no longer in the Auction House.");
			return true;
		}
		if (myItems || c.playerName.equalsIgnoreCase(item.getItemOwner())) {
			c.sendMessage(AUCTION_START + "You cannot bid on your own items.");
			return true;
		}
		if (item.getCurrentBidder() != null && item.getCurrentBidder().equalsIgnoreCase(c.playerName)) {
			c.sendMessage(AUCTION_START + "You already have the top bid for this item.");
			return true;
		}
		openAuctionInterface = BUY_INTERFACE;
		c.getPA().showInterface(BUY_INTERFACE);
		buyItemIndex = ind;
		for (int i = 0; i < 19; i++) {
			c.getPA().sendFrame126(i >= BUY_INFO.length ? "" : BUY_INFO[i], 53217 + i);
		}
		c.getPA().sendFrame126(ItemAssistant.getItemName2(item.getItemId()), 53213);
		c.getPA().displayItemOnInterface(53210, item.getItemId(), 0, item.getAmount());
		refreshBuyInterface();
		return true;
	}

	public void setMinBid() {
		if (buyItemIndex < 0 || !openInterface() || openAuctionInterface != BUY_INTERFACE)
			return;
		AuctionItem item = buyItemIndex >= (searchTerm != null ? filteredList(searchTerm) : auctionList).size() ? null
				: (searchTerm != null ? filteredList(searchTerm) : auctionList).get(buyItemIndex);
		if (item == null) {
			c.sendMessage(AUCTION_START + "This item is no longer in the Auction House.");
			return;
		}
		if (myItems || c.playerName.equalsIgnoreCase(item.getItemOwner())) {
			c.sendMessage(AUCTION_START + "You cannot bid on your own items.");
			return;
		}
		buyItemBid = (item.getCurrentBidder() == null ? item.getCurrentBid() : (item.getCurrentBid() + 1));
		refreshBuyInterface();
		c.sendMessage(
				AUCTION_START + " You set your bid to " + buyItemBid + " " + ItemAssistant.getItemName2(ITEM_ID) + ".");
	}

	public void setBid(int newBid) {
		if (buyItemIndex < 0 || !openInterface() || openAuctionInterface != BUY_INTERFACE || newBid < 1)
			return;
		AuctionItem item = buyItemIndex >= (searchTerm != null ? filteredList(searchTerm) : auctionList).size() ? null
				: (searchTerm != null ? filteredList(searchTerm) : auctionList).get(buyItemIndex);
		if (item == null) {
			c.sendMessage("This item is no longer in the Auction House.");
			return;
		}
		if (myItems || c.playerName.equalsIgnoreCase(item.getItemOwner())) {
			c.sendMessage("You cannot bid on your own items.");
			return;
		}
		buyItemBid = newBid;
		refreshBuyInterface();
		c.sendMessage(
				AUCTION_START + " You set your bid to " + buyItemBid + " " + ItemAssistant.getItemName2(ITEM_ID) + ".");
		if (buyItemBid < (item.getCurrentBidder() == null ? item.getCurrentBid() : (item.getCurrentBid() + 1)))
			c.sendMessage(AUCTION_START + "Your bid of " + buyItemBid + " " + ItemAssistant.getItemName2(ITEM_ID)
					+ " is less than the minimal bid.");
	}

	public void buyItem() {
		if (buyItemIndex < 0 || !openInterface() || openAuctionInterface != BUY_INTERFACE)
			return;
		AuctionItem item = buyItemIndex >= (searchTerm != null ? filteredList(searchTerm) : auctionList).size() ? null
				: (searchTerm != null ? filteredList(searchTerm) : auctionList).get(buyItemIndex);
		if (item == null) {
			c.sendMessage(AUCTION_START + "This item is no longer in the Auction House.");
			return;
		}
		if (myItems || c.playerName.equalsIgnoreCase(item.getItemOwner())) {
			c.sendMessage(AUCTION_START + "You cannot bid on your own items.");
			return;
		}
		if (buyItemBid < (item.getCurrentBidder() == null ? item.getCurrentBid() : (item.getCurrentBid() + 1))) {
			c.sendMessage(AUCTION_START + "You must bid at least "
					+ (item.getCurrentBidder() == null ? item.getCurrentBid() : (item.getCurrentBid() + 1)) + " "
					+ ItemAssistant.getItemName2(ITEM_ID) + " for this item.");
			return;
		}
		if (item.getCurrentBidder() != null && item.getCurrentBidder().equalsIgnoreCase(c.playerName)) {
			c.sendMessage(AUCTION_START + "You already have the top bid for this item.");
			return;
		}
		if (!c.getItems().playerHasItem(ITEM_ID, buyItemBid)) {
			c.sendMessage(AUCTION_START + "You do not have " + buyItemBid + " " + ItemAssistant.getItemName2(ITEM_ID)
					+ "(s) to bid.");
			return;
		}
		Client old = Client.getClient(item.getCurrentBidder());
		if (old != null) {
			old.sendMessage(AUCTION_START + "You have been out-bid for " + item.getAmount() + "x "
					+ ItemAssistant.getItemName2(item.getItemId()) + ", your tickets went to your bank.");
			old.getItems().addItemToBank(ITEM_ID, item.getCurrentBid());
		} else {
			unclaimedBids.add(new AuctionItem(ITEM_ID, item.getCurrentBid(), 0, item.getCurrentBidder(),
					item.getCurrentBidder(), 0));
		}
		c.getItems().deleteItem2(ITEM_ID, buyItemBid);
		item.setCurrentBid(buyItemBid);
		item.setCurrentBidder(c.playerName);
		c.sendMessage(AUCTION_START + " You set your bid to " + buyItemBid + " " + ItemAssistant.getItemName2(ITEM_ID)
				+ " for " + item.getAmount() + "x " + ItemAssistant.getItemName2(item.getItemId()) + ".");
		buyItemBid = 0;
		c.getAuctions().openAuction();
	}

	public void openMyItems() {
		if (c == null || c.playerIsBusy() || !openInterface() || openAuctionInterface != MAIN_INTERFACE)
			return;
		myItems = !myItems;
		refreshMainInterface();
	}
}