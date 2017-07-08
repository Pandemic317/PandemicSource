package server.model.minigames;

import server.Server;
import server.model.players.Client;
import server.model.players.PlayerHandler;

/**
 * @author Sanity
 */
@SuppressWarnings("all")
public class PestControl {
	
	public PestControl() {
		
	}
	
	public final int GAME_TIMER = 70; //5 minutes
	public final int WAIT_TIMER = 7;
	
	public int gameTimer = -1;
	public int waitTimer = 15;
	public int properTimer = 0;
	
	   private String secondsToMinutesAndSeconds(int seconds) {
      int minutes = seconds / 60;
      String time = minutes + " min " + (seconds - minutes * 60) + " seconds";
      return time;
   }

   private String secondsToMinutes(int seconds) {
      int minutes = seconds / 60;
      String time = minutes + " min";
      return time;
   }
   
	public void gameProcess() {
		setInterface();
		Server.pestControl.updateBoat();
		if (properTimer > 0) {
			properTimer--;
			return;
		} else {
			properTimer = 4;
		}
		if (waitTimer > 0)
			waitTimer--;
		else if (waitTimer == 0)
			startGame();
		if (gameTimer > 0) {
			gameTimer--;
			if (allPortalsDead()) {
				endGame(true);
			}
		} else if (gameTimer == 0)
			endGame(false);
	}
	
	   public void updateBoat() {
      for(int j = 0; j < PlayerHandler.players.length; ++j) {
         if(PlayerHandler.players[j] != null) {
            Client c = (Client)PlayerHandler.players[j];
            if(c.inPcBoat()) {
               c.getPA().sendFrame126("Next Departure: " + this.secondsToMinutesAndSeconds(this.waitTimer), 21120);
			//   c.sendMessage("Next Departure: " + this.secondsToMinutesAndSeconds(this.waitTimer));
               c.getPA().sendFrame126("Players Ready: " + this.playersInBoat(), 21121);
               c.getPA().sendFrame126("(Need 3 to 25 players)", 21122);
               c.getPA().sendFrame126("Pest Points: " + c.pcPoints, 21123);
               c.getPA().walkableInterface(21119);
            }
         }
      }

   }
	
	public void startGame() {
		if (playersInBoat() > 2) {
			gameTimer = GAME_TIMER;
			waitTimer = -1;
			//spawn npcs
			spawnNpcs();	
			//move players into game
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					if (Server.playerHandler.players[j].inPcBoat()) {
						movePlayer(j);
					}			
				}		
			}
		} else {
			waitTimer = WAIT_TIMER;
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					if (Server.playerHandler.players[j].inPcBoat()) {
						Client c = (Client)Server.playerHandler.players[j];
						c.sendMessage("There need to be at least 3 players to start a game of pest control.");
					}			
				}		
			}
		}
	}

	public void setInterface() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				/*if (Server.playerHandler.players[j].inPcBoat()) {
					Client c = (Client) Server.playerHandler.players[j];
					c.getPA().sendFrame126("Next Departure: "+waitTimer+"", 21006);
					c.getPA().sendFrame126("Players Ready: "+playersInBoat()+"", 21007);
					c.getPA().sendFrame126("(Need 3 to 25 players)", 21008);
					c.getPA().sendFrame126("ConcepticX Points: "+c.pcPoints+"", 21009);
				}*/
 				if (Server.playerHandler.players[j].inPcGame()) {
					Client c = (Client) Server.playerHandler.players[j];
					for (j = 0; j < Server.npcHandler.npcs.length; j++) {
						if (Server.npcHandler.npcs[j] != null) {
							if (Server.npcHandler.npcs[j].npcType == 6142)
								c.getPA().sendFrame126("" + Server.npcHandler.npcs[j].HP + "", 21111);
							if (Server.npcHandler.npcs[j].npcType == 6143)
								c.getPA().sendFrame126("" + Server.npcHandler.npcs[j].HP + "", 21112);
							if (Server.npcHandler.npcs[j].npcType == 6144)
								c.getPA().sendFrame126("" + Server.npcHandler.npcs[j].HP + "", 21113);
							if (Server.npcHandler.npcs[j].npcType == 6145)
								c.getPA().sendFrame126("" + Server.npcHandler.npcs[j].HP + "", 21114);
						}
					}
					c.getPA().sendFrame126("0", 21115);
						if (c.pcDamage < 10) {
c.getPA().sendFrame126("@red@"+c.pcDamage+".", 21116);
	} else {
c.getPA().sendFrame126("@gre@"+c.pcDamage+".", 21116);
}
					c.getPA().sendFrame126("Time remaining: "+gameTimer+"", 21117);
				}
			}
		}
	}
	
	public int playersInBoat() {
		int count = 0;
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				if (Server.playerHandler.players[j].inPcBoat()) {
						count++;
				}
			}
		}
		return count;
	}
	
	public void endGame(boolean won) {
		gameTimer = -1;
		waitTimer = WAIT_TIMER;
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				if (Server.playerHandler.players[j].inPcGame()) {
					Client c = (Client)Server.playerHandler.players[j];
					c.getPA().movePlayer(2657, 2639, 0);
					if (won && c.pcDamage > 10) {
						c.sendMessage("<shad=6081134>You have won the Minigame and won 4 Points!");
						c.pcPoints += 4;
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
						c.playerLevel[5] = c.getLevelForXP(c.playerXP[5]);
						c.specAmount = 10;
						c.getItems().addItem(995, c.combatLevel * 50);
						c.getPA().refreshSkill(3);
						c.getPA().refreshSkill(5);
					} else if (won) {
						c.sendMessage("The void knights notice your lack of zeal.");
					} else {
						c.sendMessage("You failed to kill all the portals in 5 minutes and have not been awarded any points.");
					}
					c.pcDamage = 0;
					c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
					c.getCombat().resetPrayers();
				}			
			}		
		}

		for (int j = 0; j < Server.npcHandler.npcs.length; j++) {
			if (Server.npcHandler.npcs[j] != null) {
				if (Server.npcHandler.npcs[j].npcType >= 6142 && Server.npcHandler.npcs[j].npcType <= 6145)
					Server.npcHandler.npcs[j] = null;
			}			
		}
	}
	
	public boolean allPortalsDead() {
		int count = 0;
		for (int j = 0; j < Server.npcHandler.npcs.length; j++) {
			if (Server.npcHandler.npcs[j] != null) {
				if (Server.npcHandler.npcs[j].npcType >= 6142 && Server.npcHandler.npcs[j].npcType <= 6145)
					if (Server.npcHandler.npcs[j].needRespawn)
						count++;		
			}			
		}
		return count >= 4;	
	}
	
	public void movePlayer(int index) {
		Client c = (Client)Server.playerHandler.players[index];
		if (c.combatLevel < 40) {
			c.sendMessage("You must be at least combat level 40 to enter this boat.");
			return;
		}
		c.getPA().movePlayer(2658,2611,0);
	}
	
   public static void spawnNpcs() { //SHIFTERS ,VOID KNIGHT, AND PORTALS.
    Server.npcHandler.spawnNpc2(3782, 2656, 2592, 0, 0, 200, 0, 0, 100);
    Server.npcHandler.spawnNpc2(6142, 2628, 2591, 0, 0, 200, 0, 0, 100);
	Server.npcHandler.spawnNpc2(6143, 2680, 2588, 0, 0, 200, 0, 0, 100);
    Server.npcHandler.spawnNpc2(6144, 2669, 2570, 0, 0, 200, 0, 0, 100);
    Server.npcHandler.spawnNpc2(6145, 2645, 2569, 0, 0, 200, 0, 0, 100);
	Server.npcHandler.spawnNpc2(3732, 2657, 2579, 0, 1, 400, 10, 200, 100);
	Server.npcHandler.spawnNpc2(3734, 2667, 2575, 0, 1, 400, 10, 100, 100);
	Server.npcHandler.spawnNpc2(3736, 2679, 2591, 0, 1, 400, 10, 200, 100);
	Server.npcHandler.spawnNpc2(3738, 2631, 2594, 0, 1, 400, 10, 200, 100);
	Server.npcHandler.spawnNpc2(3738, 2632, 2591, 0, 1, 400, 10, 200, 100);
	Server.npcHandler.spawnNpc2(3734, 2657, 2606, 0, 1, 400, 10, 200, 100);
	Server.npcHandler.spawnNpc2(3734, 2659, 2606, 0, 1, 200, 10, 200, 100);
   }
}