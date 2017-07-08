package server.model.players.Content.Gambler;

import java.util.ArrayList;

import server.Config;
import server.CycleEvent;
import server.CycleEventContainer;
import server.CycleEventHandler;
import server.model.items.GameItem;
import server.model.players.Client;
import server.model.players.PlayerHandler;
import server.util.Misc;

public class GambleInterface {

	public GambleInterface(Client player) {
		this.player = player;
	}

	private Client player;
	private GameType playerGameType;
	private int requestId = -1;
	private int challengeId = -1;
	private int gameStatus = -1;
	private ArrayList<GameItem> offeredItems = new ArrayList<GameItem>();
	private long lastRequest;

	public boolean openOptions(Client challenged) {
		if (challenged == null || !challenged.inGambleZone() || !player.inGambleZone())
			return false;
		if (GameType.values().length < 2)
			return challengePlayer(challenged, GameType.DICE_DUEL);
		/**
		 * Here is where you would add support for other types of gambling I'd
		 * send a dialogue with a custom ID then use the clicking buttons to
		 * determine the GameType.
		 */
		return false;
	}

	public boolean challengePlayer(Client challenged, GameType type) {
		if (challenged.inAccountPin) {
			challenged.sendMessage("You must enter your pin first");
			challenged.isWalking = false;
			challenged.getBankPin().openPin();
			return true;
		}
		if (player.inAccountPin) {
			player.sendMessage("You must enter your pin first");
			player.isWalking = false;
			player.getBankPin().openPin();
			return true;
		}
		if (player.playerRights == 2) {
			player.sendMessage("Administrators are not allowed to Gamble.");
			return false;
		}
		if (player.diceBanned == 2) {
			player.sendMessage("<col=800000000><shad>You have banned yourself from gambling. You can not undo this.");
			return false;
		}
		if(player.ironGambler >= 1) {
			player.sendMessage("You can't gamble players as an Iron Gambler.");
			return false;
		}
		if (!Config.DUEL_ENABLED) {
			player.sendMessage("Dicing will be enabled during the weekend.");
			return false;
		}
		if(player.npcKills < 11) {
			player.sendMessage("You need at least 10 monster kills in order to trade! You currently have <col=800000000>"+ player.npcKills +"</col> kills.");
			return false;
		}
		if (player.playerId == challenged.playerId){
			return true;
		}
		if (challenged == null || type == null || !challenged.inGambleZone() || !player.inGambleZone())
			return false;
		if (lastRequest > System.currentTimeMillis()) {
			player.sendMessage("Please wait a few seconds before sending another request.");
			return true;
		}
		if (gambling(player) || player.playerIsBusy()) {
			player.sendMessage("Please finish what you're doing before gambling a player.");
			return true;
		}
		if (gambling(challenged) || challenged.playerIsBusy()) {
			player.sendMessage("The other player is busy. Please try again later.");
			return true;
		}
		if (challenged.getGambling().requestId == player.playerId) {
			answerRequest(challenged);
			return true;
		}
		player.getGambling().requestId = challenged.playerId;
		player.getGambling().lastRequest = System.currentTimeMillis() + 1200;// 2
																				// server
																				// ticks.
		challenged.sendMessage(Misc.optimizeText(player.playerName) + ":gamblreq:");
		player.sendMessage("Sending gamble request...");
		player.getGambling().playerGameType = type;
		return true;
	}

	public static boolean gambling(Client plr) {
		if (plr == null || !plr.inGambleZone() || plr.getGambling().gameStatus < 0 || plr.getGambling().challengeId < 1
				|| plr.getGambling().playerGameType == null)
			return false;
		return true;
	}

	public boolean answerRequest(Client challenger) {
		if (player.playerRights == 2) {
			player.sendMessage("Administrators are not allowed to Gamble.");
			return false;
		}
		if(player.ironGambler >= 1) {
			player.sendMessage("You can't gamble players as an Iron Gambler.");
			return false;
		}
		if (player.diceBanned == 2) {
			player.sendMessage("<col=800000000><shad>You have banned yourself from gambling. You can not undo this.");
			return false;
		}
		if (player == null || challenger == null || gambling(player) || gambling(challenger))
			return false;
		player.getGambling().playerGameType = challenger.getGambling().playerGameType;
		challenger.getGambling().challengeId = player.playerId;
		player.getGambling().challengeId = challenger.playerId;
		challenger.getGambling().requestId = -1;
		player.getGambling().requestId = -1;
		challenger.getGambling().gameStatus = 0;
		player.getGambling().gameStatus = 0;
		openInterface(player);
		openInterface(challenger);
		return true;
	}

	public void openInterface(Client plr) {
		if (plr == null || plr.getGambling().playerGameType == null || plr.getGambling().challengeId < 0
				|| PlayerHandler.players[plr.getGambling().challengeId] == null)
			return;
		Client o = (Client) PlayerHandler.players[plr.getGambling().challengeId];
		if (!gambling(plr) || o == null)
			return;
		String otherName = Misc.formatPlayerName(PlayerHandler.players[plr.getGambling().challengeId].playerName);
		GameType type = plr.getGambling().playerGameType;
		for (int i = 0; i < 10; i++) {
			plr.getPA().sendFrame126(
					(type.getRules() == null || i > (type.getRules().length - 1)) ? " " : type.getRules()[i],
					52514 + i);
		}
		//o.GambleStatus = 1;
		//plr.GambleStatus = 1;
		plr.turnPlayerTo(o.absX, o.absY);
		o.turnPlayerTo(plr.absX, plr.absY);
		plr.getPA().sendFrame126(otherName, 52508);
		plr.getPA().sendFrame126(otherName + "'s bet:", 52510);
		plr.getPA().sendFrame126(type.getGameName(), 52512);
		refreshItems(plr);
		plr.getPA().interfaceWithInventory(52500, 52600);
		plr.getItems().resetItems(52601);
	}
	
	
	
	public void acceptGamble() {
		if (!gambling(player))
			return;
		Client o = (Client) PlayerHandler.players[player.getGambling().challengeId];
		if (o == null || !gambling(o))
			return;
		if (o.getGambling().gameStatus > 1 || gameStatus > 1)
			return;
		boolean start = false;
		if (o.getGambling().gameStatus == 1) {
			o.getGambling().gameStatus = 2;
			gameStatus = 2;
			start = true;
			//o.GambleStatus = 1;
			//player.GambleStatus = 1;
		} else {
			gameStatus = 1;
		}
		refreshStatus(player);
		if (start)
			gamble();
	}

	private void gamble() {
		if (player == null || playerGameType == null || !gambling(player) || gameStatus < 1)
			return;
		Client o = (Client) PlayerHandler.players[player.getGambling().challengeId];
		if (o == null || o.getGambling().playerGameType == null || !gambling(o) || o.getGambling().gameStatus < 1)
			return;
		player.getPA().removeAllWindows();
		o.getPA().removeAllWindows();
		player.GambleStatus = 1;
		o.GambleStatus = 1;
		CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
			int stage = 0;

			@Override
			public void execute(CycleEventContainer container) {
				switch (playerGameType) {
				case DICE_DUEL:
					switch (stage) {
					case 0:
						player.forcedChat("3");
						o.forcedChat("3");
						break;
					case 1:
						player.forcedChat("2");
						o.forcedChat("2");
						break;
					case 2:
						player.forcedChat("1");
						o.forcedChat("1");
						break;
					case 4:// Rolling
						player.startAnimation(11900);
						o.startAnimation(11900);
						player.sendMessage("You roll the dice...");
						o.sendMessage("You roll the dice...");
						if (player.absX < o.absX) {
							player.getPA().stillGfx(2096, player.absX, player.absY, player.heightLevel, 0);
						} else if (player.absX > o.absX) {
							player.getPA().stillGfx(2088, player.absX, player.absY, player.heightLevel, 0);
						} else if (player.absY < o.absY) {
							player.getPA().stillGfx(2072, player.absX, player.absY, player.heightLevel, 0);
						} else if (player.absY > o.absY) {
							player.getPA().stillGfx(2080, player.absX, player.absY, player.heightLevel, 0);
						} else {
							player.getPA().stillGfx(2080, player.absX, player.absY, player.heightLevel, 0);
						}
						break;
					case 6:// Announcing dice rolled.
						int playerRoll = Misc.random(100);
						int chalRoll = Misc.random(100);
						playerRoll = playerRoll == 0 ? 1 : playerRoll;
						chalRoll = chalRoll == 0 ? 1 : chalRoll;
						player.sendMessage("You rolled <col=255>" + playerRoll + "</col> on the 6 sided dice.");
						o.sendMessage("You rolled <col=255>" + chalRoll + "</col> on the 6 sided dice.");
						String wintype = "";
						if (chalRoll > playerRoll) {
							o.forcedChat("I WON! I rolled "+ playerRoll +"!");
							player.agilitypoints += 1;
							player.sendMessage(Misc.formatPlayerName(o.playerName)
									+ " won the dice duel with a <col=255>" + chalRoll + "</col>.");
							o.sendMessage("You won the dice duel against " + Misc.formatPlayerName(player.playerName)
									+ " who rolled a <col=255>" + playerRoll + "</col>.");
							wintype = "challenger";
						} else if (playerRoll > chalRoll) {
							player.barbPoints += 1;
							player.forcedChat("I WON! I rolled "+ playerRoll +"!");
							player.sendMessage("You won the dice duel against " + Misc.formatPlayerName(o.playerName)
									+ " who rolled a <col=255>" + chalRoll + "</col>.");
							o.sendMessage(Misc.formatPlayerName(player.playerName)
									+ " won the dice duel with a <col=255>" + playerRoll + "</col>.");
							wintype = "player";
						} else {
							player.sendMessage("Both you and " + Misc.formatPlayerName(o.playerName)
									+ " tied by rolling a <col=255>" + chalRoll + "</col>.");
							o.sendMessage("Both you and " + Misc.formatPlayerName(player.playerName)
									+ " tied by rolling a <col=255>" + playerRoll + "</col>.");
							wintype = "tie";
						}
						
						o.GambleStatus = 0;
						rewardUsers(player, o, wintype);
						container.stop();
						break;
					}
					break;
				}
				
				stage++;
			}

			@Override
			public void stop() {
				resetGamble(player);
				resetGamble(o);
			}
		}, 2);
	}

	private void rewardUsers(Client player, Client challenger, String winType) {
		if (!gambling(player) || !gambling(challenger))
			return;
		winType = winType.toLowerCase().trim();
		switch (winType) {
		case "player":
			for (GameItem i : challenger.getGambling().offeredItems) {
				player.getGambling().offeredItems.add(i);
			}
			challenger.getGambling().offeredItems.clear();
			break;
		case "challenger":
			for (GameItem i : player.getGambling().offeredItems) {
				challenger.getGambling().offeredItems.add(i);
			}
			player.getGambling().offeredItems.clear();
			break;
		}
		boolean added = true;
		if (player.getGambling().offeredItems.size() > 0) {
			for (GameItem i : player.getGambling().offeredItems) {
				if (!player.getItems().addItem(i.id, i.amount)) {
					player.getItems().addItemToBank(i.id, i.amount);
					added = false;
				}
			}
			if (!added) {
				player.sendMessage("<col=7602176>Not all of your winnings could fit in your inventory.");
				player.sendMessage("<col=7602176>Some items have been sent to your bank.");
			}
		}
		if (challenger == null)
			return;
		added = true;
		if (challenger.getGambling().offeredItems.size() > 0) {
			for (GameItem i : challenger.getGambling().offeredItems) {
				if (!challenger.getItems().addItem(i.id, i.amount)) {
					challenger.getItems().addItemToBank(i.id, i.amount);
					added = false;
				}
			}
			if (!added) {
				challenger.sendMessage("<col=7602176>Not all of your winnings could fit in your inventory.");
				challenger.sendMessage("<col=7602176>Some items have been sent to your bank.");
			}
		}
	}

	private static void refreshStatus(Client plr) {
		if (plr == null || !gambling(plr))
			return;
		Client o = (Client) PlayerHandler.players[plr.getGambling().challengeId];
		plr.getPA().sendFrame126(plr.getGambling().getStatus(plr.getGambling().gameStatus, o.getGambling().gameStatus),
				52524);
		o.getPA().sendFrame126(plr.getGambling().getStatus(o.getGambling().gameStatus, plr.getGambling().gameStatus),
				52524);
		//o.GambleStatus = 0;
		//plr.GambleStatus = 0;
	}

	private void refreshItems(Client plr) {
		if (plr == null || plr.getGambling().playerGameType == null || plr.getGambling().challengeId < 1
				|| PlayerHandler.players[plr.getGambling().challengeId] == null)
			return;
		if (!gambling(plr))
			return;
		ArrayList<GameItem> otherItems = ((Client) PlayerHandler.players[plr.getGambling().challengeId])
				.getGambling().offeredItems;
		for (int i = 0; i < 20; i++) {
			int id = plr.getGambling().offeredItems == null || plr.getGambling().offeredItems.size() < 1
					|| i >= plr.getGambling().offeredItems.size() || plr.getGambling().offeredItems.get(i) == null ? -1
							: plr.getGambling().offeredItems.get(i).id;
			int idT = otherItems == null || i >= otherItems.size() || otherItems.get(i) == null ? -1
					: otherItems.get(i).id;
			plr.getPA().displayItemOnInterface(52525, id, i, id > 0 ? plr.getGambling().offeredItems.get(i).amount : 0);
			plr.getPA().displayItemOnInterface(52550, idT, i, idT > 0 ? otherItems.get(i).amount : 0);
		}
		plr.getItems().resetItems(52601);
		refreshStatus(plr);
	}

	public void offerItem(GameItem item) {
		for (int i : Config.ITEM_TRADEABLE) {
			if(i == item.id) {
				player.sendMessage("You can not gamble this item.");
				return;
			}		
		}
		if (item.id == 896 || item.id == 12477 || item.id == 12109 || item.id == 9900) {
			player.sendMessage("You can not gamble this item.");
		return;
		}
		if (player == null || player.getGambling().playerGameType == null || player.getGambling().challengeId < 0
				|| PlayerHandler.players[player.getGambling().challengeId] == null)
			return;
		if (!gambling(player))
			return;
		if (!player.getItems().playerHasItem(item.id))
			return;
		if (player.getItems().getItemCount(item.id) < item.amount)
			item.amount = player.getItems().getItemCount(item.id);
		player.getItems().deleteItem2(item.id, item.amount);
		GameItem duplicate = getItem(player, item);
		Client other = (Client) PlayerHandler.players[player.getGambling().challengeId];
		if (duplicate != null) {
			duplicate.amount = item.amount + duplicate.amount;
		} else {
			player.getGambling().offeredItems.add(item);
		}
		if (other.getGambling().gameStatus > 0)
			other.getGambling().gameStatus = 0;
		if (gameStatus > 0)
			gameStatus = 0;
		refreshItems(player);
		refreshItems(other);
	}

	public void removeItem(GameItem item) {
		if (player == null || player.getGambling().playerGameType == null || player.getGambling().challengeId < 0
				|| PlayerHandler.players[player.getGambling().challengeId] == null)
			return;
		
		if (!gambling(player))
			return;
		Client other = (Client) PlayerHandler.players[player.getGambling().challengeId];
		GameItem duplicate = getItem(player, item);
		if(other.GambleStatus > 0){
			System.out.println("[ "+ player.playerName + " ] - Has just tried to cheat on the gamble system. " );
			return;
		}
		if(player.GambleStatus > 0){
			System.out.println("[ "+ other.playerName + " ] - Has just tried to cheat on the gamble system. " );
			return;
		}
		if (duplicate == null)
			return;
		if (duplicate.amount < item.amount)
			item.amount = duplicate.amount;
		player.getItems().addItem(item.id, item.amount);
		if (duplicate.amount - item.amount <= 0) {
			player.getGambling().offeredItems.remove(duplicate);
		} else {
			duplicate.amount -= item.amount;
		}
		if (other.getGambling().gameStatus > 0)
			other.getGambling().gameStatus = 0;
		if (gameStatus > 0)
			gameStatus = 0;
		refreshItems(player);
		refreshItems(other);
	}

	private GameItem getItem(Client plr, GameItem item) {
		for (GameItem i : plr.getGambling().offeredItems) {
			if (i.id == item.id)
				return i;
		}
		return null;
	}

	private String getStatus(int playerStatus, int otherStatus) {
		if (playerStatus > 0 && otherStatus > 0)
			return "Game starting...";
		else if (playerStatus > 0 && otherStatus == 0)
			return "Waiting for other player";
		else if (playerStatus == 0 && otherStatus > 0)
			return "Other player has accepted.";
		else
			return "Waiting for both players";
	}

	public static void closeGamble(Client plr) {
		if (plr == null || !gambling(plr))
			return;
		if (plr.getGambling().offeredItems.size() > 0) {
			for (GameItem i : plr.getGambling().offeredItems)
				plr.getItems().addItem(i.id, i.amount);
		}
		Client other = ((Client) PlayerHandler.players[plr.getGambling().challengeId]);
		if (other != null) {
			for (GameItem i : other.getGambling().offeredItems)
				other.getItems().addItem(i.id, i.amount);
			other.getGambling().challengeId = -1;
			resetGamble(other);
			closeGamble(other);
			refreshStatus(other);	
			other.sendMessage("The other player declined the gamble request.");
		}
		resetGamble(plr);
		refreshStatus(plr);	
		closeGamble(plr);
		closeGamble(other);
		refreshStatus(other);	
	}

	public static void resetGamble(Client plr) {
		plr.getGambling().playerGameType = null;
		plr.getGambling().challengeId = -1;
		plr.getGambling().gameStatus = -1;
		plr.getGambling().offeredItems = new ArrayList<GameItem>();
		plr.getGambling().lastRequest = System.currentTimeMillis() - 1201;
		plr.getPA().removeAllWindows();
		refreshStatus(plr);
		closeGamble(plr);
		
	}

	public int getChallengerId() {
		return challengeId;
	}
}
