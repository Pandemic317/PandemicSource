package server.model.players;

import server.Config;
import server.Connection;
import server.Server;

public class Tutorial {

	private Client c;
	public Tutorial(Client client){
		this.c = client;
	}




	public int tutorialStage = -1;
	public void handleTutorial(){
		if(c.getTutorial().tutorialStage > 0){
			c.sendMessage("<col=800000>You did not finish the tutorial please speak to me to finish it off.</col>");
		}
		switch (tutorialStage){
		case -1:
			//Tutorial Finished Moved Player To Home.
			c.getPA().movePlayer(2382, 3488, 0);
			c.sendMessage("<col=80000> Teleports are located in the Red Dice icon tab</col>! ");
			c.getPlayerAction().canWalk(true);
			c.getDH().sendNpcChat4(" I guess that is all!","Thanks for taking a moment to take my tutorial, ","Click the starting well right here to begin your adventure!","", 1375, "Gamble Advisor");
			c.nextChat = 0;
			starterPackage();
			c.saveCharacter = true;
			break;
		case 0:
			//Entering the Tutorial stage
			tutorialStage = 1;
			c.getPA().moveOnLogout(3094, 3109, 0);
			c.getDH().sendNpcChat3(" Talk to us for some useful tips" , " We're standing right beside you.","" , 1375, "Gamble Advisor " );

			break;
		case 1:
			//Accept tutorial or leave
			c.getDH().sendDialogues(1375, 1375);
			break;

		case 2:
			c.getDH().sendDialogues(1376, 1375);
			break;

		default:
			c.getDH().sendDialogues(1375+(tutorialStage-1), 1375);
			break;
		}
	}

	public void showAround(int stage) {
		c.getPlayerAction().canWalk(false);
		switch (stage) {
		case 3://home
			c.getPA().startTeleport(Config.START_LOCATION_X, Config.START_LOCATION_Y, 0, "ForceTutorial");

			break;	
		case 4://alters
			c.getPA().startTeleport(2391, 3484, 0, "ForceTutorial");
			break;
		case 5://banks
			c.getPA().startTeleport(2396, 3488, 0, "ForceTutorial");
			break;
		case 6://home finish
			c.getPA().startTeleport(2382, 3488, 0, "ForceTutorial");
			break;
		default:
			break;
		}

	}	

	public void teleportToStart() {
		c.getPA().moveOnLogout(3093, 3106, 0);
	}

	@SuppressWarnings("static-access")
	private void starterPackage() {
		if (!Connection.hasRecieved1stStarter(Server.playerHandler.players[c.playerId].connectedFrom)) {
			c.trade11 = 200;
			c.Arma = 1;
			if (c.ironGambler == 1) {
			PlayerHandler.sendGlobalMessage("[IRON Heaven]",
					" " + c.playerName + "  has just joined Pandemic as an <shad>Iron Beast</shad>!");
			}
			if (c.ironGambler == 2) {
			PlayerHandler.sendGlobalMessage("[HC IRON Heaven]",
					" " + c.playerName + "  has just joined Pandemic as a <shad>Hardcore IronBeast</shad>!");
			}
			if (c.ironGambler == 0) {
			PlayerHandler.sendGlobalMessage("[Pandemic]",
					" " + c.playerName + "  has just joined Pandemic as a <shad>Regular Player</shad>!");
			}
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
			if (c.ironGambler >= 1) {
			c.getItems().addItem(3025, 1500); // super restore
			c.getItems().addItem(1067, 1); // green dhide vambs
			c.getItems().addItem(1115, 1); // iron platebody
			c.getItems().addItem(1153, 1); // iron fullhelm
			c.getItems().addItem(6605, 1); // GLOCK
			c.getItems().addItem(386, 1000); // shark
			c.getItems().addItem(5021, 50); //1b tickets
			} else {
			c.getItems().addItem(3025, 1500); // super restore
			c.getItems().addItem(8816, 1); // starter full helm
			c.getItems().addItem(8817, 1); // starter plate legs
			c.getItems().addItem(8818, 1); // starter platebody
			c.getItems().addItem(3025, 1500); // super restore
			c.getItems().addItem(9900, 1); // starter beastly sword
			c.getItems().addItem(17873, 1); // Pink Dildo
			c.getItems().addItem(12240, 1); // drygore rapier
			c.getItems().addItem(386, 1000); // shark
			c.getItems().addItem(5021, 50); //1b tickets
			}
			c.getPA().startTeleport(2387, 3488, 0, "modern"); //Change teleport location
			Connection.addIpToStarterList1(Server.playerHandler.players[c.playerId].connectedFrom);
			Connection.addIpToStarter1(Server.playerHandler.players[c.playerId].connectedFrom);
			// c.sendMessage("You have recieved 1 out of 2 starter packages on
			// this IP address.");
			// c2.sendMessage("<shad=29184>[Pandemic] " +
			// Misc.optimizeText(name) + " has just logged in for the first
			// time.");
		} else if (Connection.hasRecieved1stStarter(Server.playerHandler.players[c.playerId].connectedFrom)
				&& !Connection.hasRecieved2ndStarter(Server.playerHandler.players[c.playerId].connectedFrom)) {
			c.trade11 = 200;
			c.Arma = 1;
			if (c.ironGambler == 1) {
			PlayerHandler.sendGlobalMessage("[IRON BEAST]",
					" " + c.playerName + "  <col=800000000>has just joined Pandemic as an Iron Man!");
			}
			if (c.ironGambler == 2) {
			PlayerHandler.sendGlobalMessage("[HC IRON BEAST]",
					" <shad>" + c.playerName + "  <col=800000000>has just joined Pandemic as an Hardcore Iron Man!");
			}
			if (c.ironGambler == 0) {
			PlayerHandler.sendGlobalMessage("[Pandemic]",
					" " + c.playerName + "  has just joined Pandemic, welcome!");
			}
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;
			if (c.ironGambler >= 1) {
			c.getItems().addItem(3025, 1500); // super restore
			c.getItems().addItem(1067, 1);
			c.getItems().addItem(1115, 1);
			c.getItems().addItem(1153, 1);
			c.getItems().addItem(6605, 1);
			c.getItems().addItem(386, 1000); // shark
			c.getItems().addItem(5021, 50); //1b tickets
			} else {
			c.getItems().addItem(8816, 1);
			c.getItems().addItem(8817, 1);
			c.getItems().addItem(8818, 1);
			c.getItems().addItem(3551, 1);
			c.getItems().addItem(9900, 1);
			c.getItems().addItem(17873, 1); // Pink Dildo
			c.getItems().addItem(12240, 1);
			c.getItems().addItem(386, 1000); // shark
			c.getItems().addItem(5021, 50); //1b tickets
			}
			c.getPA().startTeleport(2387, 3488, 0, "modern"); //Change teleport location
			// c.sendMessage("You have recieved 2 out of 2 starter packages on
			// this IP address.");
			// c2.sendMessage("<shad=29184>[Pandemic] " +
			// Misc.optimizeText(name) + " has just logged in for the first
			// time.");
			Connection.addIpToStarterList2(Server.playerHandler.players[c.playerId].connectedFrom);
			Connection.addIpToStarter2(Server.playerHandler.players[c.playerId].connectedFrom);
		} else if (Connection.hasRecieved1stStarter(Server.playerHandler.players[c.playerId].connectedFrom)
				&& Connection.hasRecieved2ndStarter(Server.playerHandler.players[c.playerId].connectedFrom)) {
			c.sendMessage("You have already recieved 2 starters!");
			if (c.ironGambler >= 1) {
			c.getItems().addItem(3025, 1500); // super restore
			c.getItems().addItem(1067, 1);
			c.getItems().addItem(1115, 1);
			c.getItems().addItem(1153, 1);
			c.getItems().addItem(6605, 1);
			c.getItems().addItem(386, 1000); // shark
			c.getItems().addItem(5021, 50); //1b tickets
			} else {
			c.getItems().addItem(8816, 1);
			c.getItems().addItem(8817, 1);
			c.getItems().addItem(8818, 1);
			c.getItems().addItem(9900, 1);
			c.getItems().addItem(17873, 1); // Pink Dildo
			c.getItems().addItem(3025, 1500); // super restore
			c.getItems().addItem(386, 1000); // shark
			}
			// c2.sendMessage("<shad=29184>[Pandemic] " +
			// Misc.optimizeText(name) + " has just logged in for the first
			// time.");
		}

		c.getItems().updateInventory();
	}
}
