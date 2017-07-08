package server;

import server.model.players.Content.AuctionHouse.AuctionInterface;

public class ShutdownHook extends Thread {

	public void run() {
		System.out.println("Running shutdown hook...");
		if (AuctionInterface.save() && AuctionInterface.saveUnclaimed())
			System.out.println("Saved auction house data.");
	}

}
