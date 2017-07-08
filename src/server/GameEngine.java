package server;

import server.model.players.Client;
import server.model.players.Player;
import server.model.players.PlayerHandler;
import server.model.players.PlayerSave;
import server.model.players.Content.AuctionHouse.AuctionInterface;
import server.world.PublicEvent;

public class GameEngine extends Server {
	public GameEngine() {

	}

	public void run() {
		try {
			if (sleepTime > 0)
				Thread.sleep(sleepTime);
			PublicEvent.process();
			engineTimer.reset();
			itemHandler.process();
			playerHandler.process();
			AuctionInterface.updateItems();
			CycleEventHandler.getSingleton().process();
			npcHandler.process();
			shopHandler.process();
			objectManager.process();
			Server.pestControl.gameProcess();
			cycleTime = engineTimer.elapsed();
			if (cycleTime < 575)
				sleepTime = cycleRate - cycleTime;
			else
				sleepTime = 0;
			totalCycleTime += cycleTime;
			cycles++;
			debug();
			if (Config.SERVER_DEBUG)
				secundes++;
			if (secundes == 120) {
				minutes++;
				secundes = 0;
			}
			if (minutes == 60) {
				hours++;
				minutes = 0;
			}
			if (hours == 24) {
				days++;
				hours = 0;
			}
			if (hours == 2 && minutes == 0 && secundes == 20) {
				PlayerHandler.updateSeconds = 60;
				PlayerHandler.updateAnnounced = false;
				PlayerHandler.updateRunning = true;
				PlayerHandler.updateStartTime = System.currentTimeMillis();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			for (Player p : PlayerHandler.players) {
				if (p == null)
					continue;
				PlayerSave.saveGame((Client) p);
				System.out.println("[Saved game]" + p.playerName + ".");
			}
		}
	}

}
