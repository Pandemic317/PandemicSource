package server.model.players.Content.AuctionHouse;

public class AuctionItem {

	private int itemId;
	private int amount;
	private int ticksRemaining;
	private String itemOwner;
	private String currentBidder;
	private int currentBid;

	public AuctionItem() {
		this.setItemId(1);
		this.setAmount(1);
		this.setTicksRemaining(600);
		this.setItemOwner(null);
		this.setCurrentBidder(null);
		this.setCurrentBid(1);
	}

	public AuctionItem(int itemId, int amount, int ticksRemaining, String itemOwner) {
		this.setItemId(itemId);
		this.setAmount(amount);
		this.setTicksRemaining(ticksRemaining);
		this.setItemOwner(itemOwner);
		this.setCurrentBidder(null);
		this.setCurrentBid(1);
	}

	public AuctionItem(int itemId, int amount, int ticksRemaining, String itemOwner, String currentBidder,
			int currentBid) {
		this.setItemId(itemId);
		this.setAmount(amount);
		this.setTicksRemaining(ticksRemaining);
		this.setItemOwner(itemOwner);
		this.setCurrentBidder(currentBidder);
		this.setCurrentBid(currentBid);
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTicksRemaining() {
		return ticksRemaining;
	}

	public void setTicksRemaining(int ticksRemaining) {
		this.ticksRemaining = ticksRemaining;
	}

	public String getItemOwner() {
		return itemOwner;
	}

	public void setItemOwner(String itemOwner) {
		this.itemOwner = itemOwner;
	}

	public String getCurrentBidder() {
		return currentBidder;
	}

	public void setCurrentBidder(String currentBidder) {
		this.currentBidder = currentBidder;
	}

	public int getCurrentBid() {
		return currentBid;
	}

	public void setCurrentBid(int currentBid) {
		this.currentBid = currentBid;
	}
}
