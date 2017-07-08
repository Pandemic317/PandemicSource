package server.model.players.packets;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import server.Config;
import server.Connection;
import server.Server;
import server.model.minigames.Raid;
import server.model.minigames.TzhaarSpawn;
import server.model.npcs.NPCDrops;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Player;
import server.model.players.PlayerHandler;
import server.model.players.PlayerSave;
import server.util.Misc;
import server.world.PublicEvent;
@SuppressWarnings("all")
public class Commands implements PacketType {
	public long YellDelay;
	
	@SuppressWarnings("static-access")
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		
		String playerCommand = c.getInStream().readString();
		PublicEvent.processEntry(c, playerCommand);
		if (!playerCommand.startsWith("/")) {
			c.getPA().writeCommandLog(playerCommand);
		}
		if (c.inAccountPin) {
			c.sendMessage("You must enter your pin first");
			c.isWalking = false;
			c.getBankPin().openPin();
			return;
		}
			if (playerCommand.equals("dicezone") && (c.playerRights >= 0)) {
				c.getPA().startTeleport(2863, 3546, 0, "modern");
				c.sendMessage("<col=255>Welcome to the dice zone");
			}
			if (playerCommand.equals("shops") && c.playerRights >= 0) {
				c.getPA().startTeleport(3222, 3218, 0, "modern");
				c.sendMessage("<col=255>Welcome to the shops");
				}

		/*if (playerCommand.startsWith("/") && playerCommand.length() > 1) {

			c.sendMessage("Clanchat has been disabled.");
		}*/
		if (playerCommand.startsWith("report") && playerCommand.length() > 7) {
			try {
				BufferedWriter report = new BufferedWriter(new FileWriter("./Data/Reports.txt", true));
				String Report = playerCommand.substring(7);
				try {
					report.newLine();
					report.write("[Report]" + c.playerName + ": " + Report);
					c.sendMessage("You have successfully submitted your report.");
				} finally {
					report.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (playerCommand.startsWith("raidtimer")) {
			c.sendMessage("timer: "+c.raidTick);
			c.sendMessage("Players waiting " + Raid.waitingPlayers);
		}
		int[] randomGambler = { 10832, 10832, 10832, 10832, 10832, 10832, 10832, 10832, 10832, 10832, 
				10832, 10832, 10832, 10832, 10833, 10833, 10833, 10833, 10833, 10833, 10834, 10834 };
		if (playerCommand.startsWith("givetubercc")) {
			try {
				String playerToTubercc = playerCommand.substring(12);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToTubercc)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been awarded TuberCC rank by " + c.playerName);
							c2.TuberCC = 1;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("givetubercc")) {
			try {
				String playerToTubercc = playerCommand.substring(12);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToTubercc)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been awarded TuberCC rank by " + c.playerName);
							c2.TuberCC = 1;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("taketubercc")) {
			try {
				String playerToTuber = playerCommand.substring(12);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToTuber)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("Your tubercc has been taken away by " + c.playerName);
							c2.TuberCC = 0;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("taketubercc") && (c.playerName.equalsIgnoreCase("tubercc"))) {
			try {
				String playerToTuber = playerCommand.substring(12);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToTuber)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("Your tubercc has been taken away by " + c.playerName);
							c2.TuberCC = 0;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.equals("maxhit")) {
			c.sendMessage("Your current maxhit is: " + c.getCombat().calculateMeleeMaxHit() + "0");
		}
		if (playerCommand.equalsIgnoreCase("empty") && (System.currentTimeMillis() - c.emptyDelay >= 15000)) {
			c.getPA().sendFrame171(1, 2465);
			c.getPA().sendFrame171(0, 2468);
			c.getPA().sendFrame126("Empty Inventory?", 2460);
			c.getPA().sendFrame126("Yes, please!", 2461);
			c.getPA().sendFrame126("No, Thank you.", 2462);
			c.getPA().sendFrame164(2459);
			c.getDH().sendDialogues(1340, 1);
			c.dialogueAction = 1340;
		}
		if (playerCommand.equalsIgnoreCase("starterboost")) {
			if (c.trade11 > 0) {
				c.sendMessage("You have " + c.trade11 + " seconds left on your starter boost");
				c.sendMessage("You are receiving double EXP.");
			} else {
				c.sendMessage("Your starter boost has ended.");
			}
		}

		if (playerCommand.startsWith("checkbank") && c.playerRights >= 1 && c.playerRights <= 3) {
			try {
				String[] args = playerCommand.split(" ", 2);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					Client o = (Client) Server.playerHandler.players[i];
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(args[1])) {
							c.getPA().otherBank(c, o);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("checkinv") && c.playerRights >= 1 && c.playerRights <= 3) {
			try {
				String[] args = playerCommand.split(" ", 2);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					Client o = (Client) Server.playerHandler.players[i];
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(args[1])) {
							c.getPA().otherInv(c, o);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.equals("helpzone") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(1971, 5001, 0, "modern");
			c.sendMessage("Welcome to The Helpzone , For Support request in help information Tab.");
		}
		if (playerCommand.equals("home") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(2387, 3488, 0, "modern");
			c.sendMessage("<col=255>Welcome home");
		} 
		if (playerCommand.equals("ironzone") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(2387, 3488, 4, "modern");
			c.sendMessage("<col=255>Welcome to the iron beast zone.");
		} 
		if (playerCommand.equals("abyssal") && (c.playerRights >= 0)) {
		c.getDH().sendOption2("I know I can lose my stuff when I die here", "Nevermind");
		c.dialogueAction = 11171;
		}
		if (playerCommand.equals("write") && (c.playerRights == 3)) {
			String playerToBan = "" + c.playerName + " logged in at ";
			Connection.staffLogin(playerToBan);
			Connection.staffLogin(playerToBan);
		}
		if (playerCommand.equals("pvp") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(2400, 3493, 0, "modern");
			c.sendMessage("<col=255>Enter by clicking the Cupboard!");
		}
		if (playerCommand.equals("randomgamble") && (c.playerRights >= 0)) {
			if (c.getItems().freeSlots() <= 1 ) {
				c.sendMessage("Not enough inventory space.");
				return;
			}
			if (c.getItems().playerHasItem(5021,200)) {
				c.getItems().deleteItem(5021, 200);
				c.getItems().addItem(randomGambler[Misc.random(randomGambler.length -1)], 1);
				c.sendMessage("<col=255>You traded in 200 1b tickets for a random Tax Bag!");
			} else {
			c.sendMessage("<col=255>You need at least 200 tickets in your inventory to gamble!");
			}
		}
		if (playerCommand.equals("silkreward") && (c.playerRights >= 0)) {
			if (c.getItems().playerHasItem(951, 1000)) {
				c.sendMessage("<col=255>You traded in 1000 Silks for 1 Torva Box in your bank + 10M Thieving XP!");
				c.getItems().addItemToBank(19867, 1);
				c.getItems().deleteItem(951, 1000);
				if (c.playerLevel[c.playerThieving] < 134) {
				c.getPA().addSkillXP(10000000, c.playerThieving);
				} else {
					c.sendMessage("<col=255>Sorry you can no longer receive 10M XP because of your high level.");
				}
			}
		}
		if (playerCommand.equals("rocisland") && (c.playerRights >= 0)) {
			if (c.npcKills > 3499){
			c.getPA().startTeleport(2585, 4838, 0, "modern"); // Small Roc
			c.sendMessage("<col=255>You have been teleported to the Roc's island!");
			} else {
				c.sendMessage("<col=255>You need at least 3500 NPC Kills to get access to the Roc's Island.");
			}			
		}
		if (playerCommand.equals("shadow") && (c.playerRights >= 0)) {
			if (c.npcKills > 7499 && c.isDonator != 0){
			c.getPA().startTeleport(3247, 9904, 4, "modern"); // Shadow
			c.sendMessage("<col=255>Good luck killing Tumeken's Shadow!");
			} else {
				c.sendMessage("<col=255>You need at least 7500 NPC Kills + Regular Donator to get access to Tumeken's Shadow!");
			}			
		}

		if (playerCommand.equals("geodude") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(2464, 4782, 0, "modern"); // Geodude
			c.sendMessage("<col=255>You have been teleported to the Geodude's cave!");			
		}
		if (playerCommand.equals("leeches") && (c.playerRights >= 0)) {
			if (c.totalDonatorPoints >= 100) {
			c.getPA().startTeleport(2636, 4692, 1, "modern"); // Leeches
			c.sendMessage("<col=255>You have been teleported to the Sponsor Leeches!");	
			} else {
			c.sendMessage("You need to be a Sponsor Donator to get access to the Leeches.");
			if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3) {
				c.getPA().startTeleport(2636,  4692,  1, "mordern");
				c.sendMessage("<col=255>You have been teleported to the Sponsor Leeches!");
			}
		}
		}
		
		if (playerCommand.equals("skillingzone") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(3179, 3408, 0, "modern");			
		}
		if (playerCommand.equals("starterzone") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(2149, 5099, 0, "modern");
			c.sendMessage("<shad=3983>Welcome to Starter Zone.");
		}
		if (playerCommand.equals("torvazone") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(2586, 9449, 0, "modern");
		}
		if (playerCommand.equals("torvazone2") && (c.playerRights >= 0)) {
			if (c.npcKills > 499) {
			c.getPA().startTeleport(2586, 9449, 4, "modern");
			} else if (c.npcKills < 500){
				c.sendMessage("You need at least 500 ::kc to get access to the Torva Zone Floor 2.");
			}
		}
		if (playerCommand.equals("pokemon") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(2379, 4952, 0, "modern");
		}
		if (playerCommand.equals("1btickets") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(3561, 9948, 0, "modern");
		}
		if (playerCommand.equals("train") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(3561, 9948, 0, "modern");
		}
		if (playerCommand.equals("piyan") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(2880, 10199, 1, "modern");
		}
		if (playerCommand.equals("whiteknights") && (c.playerRights >= 0)) {
			if (c.npcKills >= 1500 && c.issDonator >= 1) {
				c.getPA().startTeleport(2965, 3351, 4, "modern"); // Santa
			} else {
				c.sendMessage(
						"<col=255>You need at least <col=800000000>1500 kills<col=255> & <col=800000000>Super donator<col=255> to get access to the White Knights!");
			}
		}
		if (playerCommand.equals("ihlakhizan") && (c.playerRights >= 0)) {
			if (c.npcKills > 50) {
				c.getPA().startTeleport(2982, 9631, 4, "modern"); // Ihlakhizan
				c.sendMessage("<col=255>Watch out, Ihlakhizan is able to and will most definitely 1-hit you!");
			} else {
				c.sendMessage("<col=255>You should train your <col=800000000>combat levels<col=255> some more!");
			}
		}
		if (playerCommand.equals("1mhp") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(3247, 9904, 0, "modern"); // Articuno
			c.sendMessage("<col=255>Good luck killing Articuno! He has 1 Million HP, so goodluck AFK-ing!");
		}
		if (playerCommand.equals("135HP") && (c.playerRights >= 0)) {
			if (c.playerLevel[3] == 135) {
				c.getPA().startTeleport(3561, 9948, 8, "modern");
			}
		}
		if (playerCommand.equals("135hp") && (c.playerRights >= 0)) {
			if (c.playerLevel[3] == 135) {
				c.getPA().startTeleport(3561, 9948, 8, "modern");
			}
		}
		if (playerCommand.equals("zzviwsxxxx120")) {
			PlayerHandler.sendGlobalMessage("[DONATION]",
					"" + c.playerName + "  has just donated for 20 Donator Points! Wow! Type ::donate to donate!");
		}
		if (playerCommand.equals("zzviwsxxxx105")) {
			PlayerHandler.sendGlobalMessage("[DONATION]",
					"" + c.playerName + "  has just donated for 10 Donator Points! Wow! Type ::donate to donate!");
		}
		if (playerCommand.equals("hulk") && (c.playerRights >= 0)) {
			if (c.npcKills <= 499) {
				c.sendMessage(
						"<col=255>You need at least <col=800000000>500 kills<col=255> to get access to the Hulk Torva Boss!");
			}
		}
		if (playerCommand.equals("135hp") && (c.playerRights >= 0)) {
			if (c.playerLevel[3] <= 135) {
				c.sendMessage("<col=255>You need 135 hitpoints for this!");
			}
		}
		if (playerCommand.equals("hulk") && (c.playerRights >= 0)) {
			if (c.npcKills >= 499) {
				c.getPA().startTeleport(2987, 9639, 0, "modern");
			}
		}
		if (playerCommand.equals("dropparty1") && (c.playerRights == 3)) {
			Server.itemHandler.createGroundItem(c, 3551, 2386, 3486, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3553, 2388, 3491, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3555, 2390, 3486, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3551, 2390, 3492, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3557, 2393, 3493, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3581, 2394, 3486, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3583, 2397, 3488, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3585, 2399, 3485, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3581, 2403, 3485, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3559, 2403, 3491, 1, c.playerId);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
    			if (PlayerHandler.players[j] != null) {
    				Client c2 = (Client)PlayerHandler.players[j];
    				c2.sendMessage("<shad><col=800000000>A drop party has just started by "+ c.playerName +" at ::home !");
			}
			}
		}
		if (playerCommand.equals("dropparty2") && (c.playerRights == 3)) {
			Server.itemHandler.createGroundItem(c, 3553, 2387, 3485, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3551, 2389, 3490, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3553, 2391, 3485, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3557, 2391, 3491, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3559, 2394, 3492, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3581, 2395, 3485, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3581, 2398, 3487, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3583, 2400, 3484, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3585, 2404, 3484, 1, c.playerId);
			Server.itemHandler.createGroundItem(c, 3585, 2404, 3490, 1, c.playerId);
			for (int j = 0; j < PlayerHandler.players.length; j++) {
    			if (PlayerHandler.players[j] != null) {
    				Client c2 = (Client)PlayerHandler.players[j];
    				c2.sendMessage("<shad><col=800000000>A drop party has just started by "+ c.playerName +" at ::home !");
				}
			}
		}
		if (playerCommand.equals("santa") && (c.playerRights >= 0)) {
			if (c.npcKills <= 1500 && c.issDonator <= 1) {
				c.sendMessage(
						"<col=255>You need at least <col=800000000>1500 kills<col=255> & Super donor to get access to the White Knights!");
			}
		}
		if (playerCommand.equals("santa") && (c.issDonator >= 1)) {
			if (c.npcKills >= 1500) {
				c.getPA().startTeleport(2965, 3351, 4, "modern");
			}
		}

		if (playerCommand.equals("vote") && (c.playerRights >= 0)) {
			c.getPA().sendFrame126("http://www.Pandemic.com/vote", 12000);//
		}

		if (playerCommand.equals("altar") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(3094, 3504, 0, "modern");
		}


		if (playerCommand.startsWith("donate")) {
			c.getPA().sendFrame126("http://rsps-pay.com/store.php?id=4296&tab=3792/", 12000);
		}
	
		if (playerCommand.equals("minivorago") && (c.playerRights >= 0)) {
			c.sendMessage("Vorago drops the powerful Gold Ring (i), Gold Necklalce & a <col=255>Blue Crystal<col=0>! ");
			c.getPA().startTeleport(1912, 4367, 0, "modern");
		}
		if (playerCommand.equals("corp") && (c.playerRights >= 0)) {
			c.getPA().startTeleport(3309, 9376, 0, "modern");
		}
		if (playerCommand.startsWith("fly") && (c.issDonator >= 1)) {
			if (c.playerStandIndex != 1501) {
				c.startAnimation(1500);
				c.playerStandIndex = 1501;
				c.playerTurnIndex = 1851;
				c.playerWalkIndex = 1851;
				c.playerTurn180Index = 1851;
				c.playerTurn90CWIndex = 1501;
				c.playerTurn90CCWIndex = 1501;
				c.playerRunIndex = 1851;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You start flying.");
			} else {
				c.playerStandIndex = 0x328;
				c.playerTurnIndex = 0x337;
				c.playerWalkIndex = 0x333;
				c.playerTurn180Index = 0x334;
				c.playerTurn90CWIndex = 0x335;
				c.playerTurn90CCWIndex = 0x336;
				c.playerRunIndex = 0x338;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You gently land on your feet.");
			}
		}
		if (playerCommand.equals("dpoints") && (c.playerRights >= 0)) {
			c.sendMessage("You currently have <col=255>" + c.donatorChest + "<col=0> donator points");
		}
		if (playerCommand.equals("dzone") && (c.isDonator == 1)) {
			c.getPA().startTeleport(2393, 9894, 0, "modern");
			c.sendMessage("Welcome to the Donator Zone!");
		}
		if (playerCommand.equals("dzone") && (c.npcKills >= 9999)) {
			c.getPA().startTeleport(2393, 9894, 0, "modern");
			c.sendMessage("Welcome to the Donator Zone! Amazing 10K kills!");
		}
		if (playerCommand.equals("superdzone") && (c.issDonator >= 1)) {
			c.getPA().startTeleport(2393, 9894, 4, "modern");
			c.sendMessage("Welcome to the Super Donator Zone!");
		}
		if (playerCommand.equals("extremedzone") && (c.issDonator == 2)) {
			c.getPA().startTeleport(2393, 9894, 8, "modern");
			c.sendMessage("Welcome to the Extreme Donator Zone!");
			if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3) {
				c.getPA().startTeleport(2393, 9894, 8, "modern");
				c.sendMessage("Welcome to the Extreme Donator Zone!");
			}
		}
		if (playerCommand.equals("staffzone") && (c.playerRights >= 1 && c.playerRights <= 3)) {
			c.getPA().startTeleport(1865, 5348, 0, "modern");
		}
	

		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (c.clanId >= 0) {
				System.out.println(playerCommand);
				playerCommand = playerCommand.substring(1);
				Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
				// Server.clanChat.playerMessageToClan(c, c.playerId,
				// playerCommand, c.clanId);
			} else {
				if (c.clanId != -1)
					c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			return;
		}
		if (Config.SERVER_DEBUG)
			Misc.println(c.playerName + " playerCommand: " + playerCommand);

		if (c.playerRights >= 0)
			playerCommands(c, playerCommand);
		if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3)
			moderatorCommands(c, playerCommand);
		if (c.playerRights == 2 || c.playerRights == 3)
			administratorCommands(c, playerCommand);
		if (c.playerRights == 3)
			ownerCommands(c, playerCommand);
		if (c.playerRights == 4)
			DonatorCommands(c, playerCommand);
		if (c.playerRights == 7)
			helperCommands(c, playerCommand);

	}

	public void playerCommands(Client c, String playerCommand) {
		if (playerCommand.equalsIgnoreCase("rules")) {
			for (int i = 8144; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.getPA().showInterface(8134);
			c.getPA().sendFrame126("@blu@~ " + Config.SERVER_NAME + " Rules ~", 8144);
			c.getPA().sendFrame126("@blu@For all rules go to ::forums section Pandemic Rules", 8145);
			c.getPA().sendFrame126("@blu@1. & 2. Flaming, harrassment, disrespect, racism and/or", 8147);
			c.getPA().sendFrame126("@blu@offensive language - IP-Mute", 8148);
			c.getPA().sendFrame126("@blu@4. & 5. Spamming and/or begging - Depending", 8149);
			c.getPA().sendFrame126("@blu@on severity of content: IP-Mute to IP-Ban", 8150);
			c.getPA().sendFrame126("@blu@6. Encouraging others to break rules - IP-Mute", 8151);
			c.getPA().sendFrame126("@blu@10. Advertising - IP-Ban", 8152);
			c.getPA().sendFrame126("@blu@11. Misleading links, threatening and hacking - IP-Ban", 8153);
			c.getPA().sendFrame126("@blu@12. Real World Trading (RWT), giving", 8154);
			c.getPA().sendFrame126("@blu@away of accounts - Permanent Ban", 8155);
			c.getPA().sendFrame126("@blu@13. Bugs and/or glitch abuse - Ban", 8156);
			c.getPA().sendFrame126("@blu@17. Evading punishments - IP-Ban", 8157);
			c.getPA().sendFrame126("@blu@Note that Staffmembers are also people and", 8158);
			c.getPA().sendFrame126("@blu@could react differently on each situation.", 8159);
			c.getPA().sendFrame126("@blu@Respect all staffmember's decisions", 8160);
			c.getPA().sendFrame126("@blu@Gambling is only allowed at the Private Dicezone", 8161);
			c.getPA().sendFrame126("@blu@When gambling with the dice bag get a middleman", 8162);
			c.getPA().sendFrame126("@blu@like a staffmember or trusted dicer.", 8162);
			c.getPA().sendFrame126("@blu@PM a staffmember if you need any help.", 8163);
			c.getPA().sendFrame126("@blu@Find guides at ::forums under section: Guides", 8164);
			c.getPA().sendFrame126("@blu@Report a player by making a post on ::forums", 8165);
			c.getPA().sendFrame126("@blu@PM Content for donations with rsgp", 8166);
			c.getPA().sendFrame126("@blu@Have fun playing Pandemic", 8167);
		}
		if (playerCommand.startsWith("commands")) { // change name to whatever,
													// info, donate etc.
			for (int i = 8144; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.getPA().showInterface(8134);
			c.getPA().sendFrame126("@whi@PandemicCommands", 8144);
			c.getPA().sendFrame126("@blu@Also read ::rules", 8145);
			c.getPA().sendFrame126("@red@Only use this if you get errors when using", 8147);
			c.getPA().sendFrame126("@red@teleports in the Red Dices tab. Else use that.", 8148);
			c.getPA().sendFrame126("@blu@Regular Commands", 8149);
			c.getPA().sendFrame126("1-  ::Donate", 8150);
			c.getPA().sendFrame126("2-  Relog to claim your donation!", 8151);
			c.getPA().sendFrame126("3-  ::Home ", 8152);
			c.getPA().sendFrame126("4-  ::Vote     claim by using 5-", 8153);
			c.getPA().sendFrame126("5-  ::Auth 123ab45c < example", 8154);
			c.getPA().sendFrame126("5-  ::Dicezone", 8155);
			c.getPA().sendFrame126("@blu@PvM Commands", 8156);
			c.getPA().sendFrame126("6-  ::1btickets (::train)", 8157);
			c.getPA().sendFrame126("7-  ::Piyan", 8158);
			c.getPA().sendFrame126("8-  ::Pokemon", 8159);
			c.getPA().sendFrame126("10- ::Minivorago", 8160);
			c.getPA().sendFrame126("11- ::Corp", 8161);
			c.getPA().sendFrame126("12- ::kc (To Check Your NPC Kills)", 8162);
			c.getPA().sendFrame126("13- ::Hulk (500 kills)", 8163);
			c.getPA().sendFrame126("14- ::135Hp", 8164);
			c.getPA().sendFrame126("15- ::Corp", 8165);
			c.getPA().sendFrame126("16- ::Ihlakhizan (50 kills)", 8166);
			c.getPA().sendFrame126("17- ::1Mhp", 8167);
			c.getPA().sendFrame126("18- ::Whiteknights (1500 kills + Super Donator)", 8168);
			c.getPA().sendFrame126("19- ::Shadow (7500 kills Donator)", 8169);
			c.getPA().sendFrame126("20- ::Rocisland", 8170);
			c.getPA().sendFrame126("21- ::Geodude", 8171);
			c.getPA().sendFrame126("22- ::Leeches (Sponsor+)", 8172);
			c.getPA().sendFrame126("@blu@New Commands", 8173);
			c.getPA().sendFrame126("1- ::Skillingzone",8174);
			c.getPA().sendFrame126("2- ::Randomgamble (200 tickets)",8175);
			c.getPA().sendFrame126("3- ::Pvp",8176);
			c.getPA().sendFrame126("4- ::Torvazone2 (500 kills)",8177);
			c.getPA().sendFrame126("5- ::Abyssal (500 kills)",8178);
			c.getPA().sendFrame126("6- ::ironzone (Iron Beasts)",8179);
		}
		if (playerCommand.startsWith("kc")) { // change name to whatever,
			// info, donate etc.
			for (int i = 8144; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.forcedChat("I currently have "+ c.npcKills +" total NPC kills!");
			c.getPA().showInterface(8134);
			c.getPA().sendFrame126("Total Npcs killed: " + c.npcKills + "", 8144);
			c.getPA().sendFrame126("Blue torva killed: " + c.BlueTorvaKilled + "", 8145);
			c.getPA().sendFrame126("Flame torva killed: " + c.FlameTorvaKilled + "", 8146);
			c.getPA().sendFrame126("24k torva killed: " + c.Torva24KKilled + "", 8147);
			c.getPA().sendFrame126("Burst torva killed: " + c.BurstTorvaKilled + "", 8148);
			c.getPA().sendFrame126("Predator torva killed: " + c.PredatorTorvaKilled + "", 8149);
			c.getPA().sendFrame126("Cyrex torva killed: " + c.CyrexTorvaKilled + "", 8150);
			c.getPA().sendFrame126("Piyan torva killed: " + c.ProtectorKilled + "", 8151);
			c.getPA().sendFrame126("1B Tickets killed: " + c.avatarKilled + "", 8152);
			c.getPA().sendFrame126("Corporeal Beasts killed: " + c.ForgottenWarriorKilled + "", 8153);
			c.getPA().sendFrame126("Vorago's killed: " + c.MadMummyKilled + "", 8154);
			c.getPA().sendFrame126("Ticket Boss killed: " + c.barrelKilled + "", 8155);
			c.getPA().sendFrame126("Hulk Torva killed: " + c.demonsKilled + "", 8156);
			c.getPA().sendFrame126("135HP Penguins killed: " + c.frostsKilled + "", 8157);
			c.getPA().sendFrame126("Ihlakhizan killed: " + c.DTPoints + "", 8158);
			c.getPA().sendFrame126("White Knights killed: " + c.PkminiPoints + "", 8159);
			c.getPA().sendFrame126("Articuno's killed: " + c.jadKilled + "", 8160);
			c.getPA().sendFrame126("Ice Strykewyrms killed: " + c.mithKilled + "", 8161);
			c.getPA().sendFrame126("Calculon killed: " + c.KillsNr3 + "", 8162);
		}
		if (playerCommand.startsWith("withdraw")) {
			c.sendMessage("This command has been removed.");
			}
		if (playerCommand.startsWith("resettask")) {
			c.taskAmount = -1;
			c.slayerTask = 0;
		}
		if (playerCommand.startsWith("claim")) {
			c.rspsdata(c, c.playerName);
		}
		if (playerCommand.startsWith("resetdef")) {
			if (c.inWild())
				return;
			for (int j = 0; j < c.playerEquipment.length; j++) {
				if (c.playerEquipment[j] > 0) {
					c.sendMessage("Please take all your armour and weapons off before using this command.");
					return;
				}
			}
			try {
				int skill = 1;
				int level = 1;
				c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
				c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
				c.getPA().refreshSkill(skill);
			} catch (Exception e) {
			}
		}
		if (playerCommand.equalsIgnoreCase("enddung")) {
			if (c.InDung() || c.inDungBossRoom()) {
				c.getPA().movePlayer(3085, 3495, 0);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
				c.prayerId = -1;
				c.isSkulled = true;
				c.getPA().closeAllWindows();
				c.getPA().refreshSkill(5);
				c.getPA().refreshSkill(3);
				c.getItems().deleteAllItems();
				c.hasChoosenDung = false;
				c.getDungeoneering().setDaBooleans();
				// c.hassentrelogmessage = false;
				c.needstorelog = true;
				c.InDung = false;
				c.getPA().closeAllWindows();
			} else {
				c.sendMessage("YOU ARE NOT IN DUNGEONEERING!");
				return;
			}
		}

	if (playerCommand.equalsIgnoreCase("damage")) {
			c.sendMessage("Damage dealt : " + c.barbDamage + " ");
		}

		if (playerCommand.equalsIgnoreCase("endgame")) {
			if (c.inBarbDef) {
				Server.barbDefence.endGame(c, false);
			} else {
				c.sendMessage("Your not in the minigame!");
			}
		}
		if (playerCommand.startsWith("rest")) {
			c.startAnimation(5713);
		}

		if (playerCommand.equalsIgnoreCase("bank") && c.issDonator >= 1 && !c.inWild() && !c.isInPbox() && !c.pkSafe()
				&& !c.isInArd() && !c.isInFala() && !c.inFunPk() && !c.isInUndead()) {
			c.getPA().openUpBank();
		}
		if (playerCommand.equalsIgnoreCase("players")) {
			c.sendMessage("There are currently <col=255>" + PlayerHandler.getPlayerCount() +"<col=0> players online");
			if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3) {
				c.getPA().showInterface(19350);
				c.getPA().refreshPlayersInterface();
			}
		}
		/*
		 * if (playerCommand.equalsIgnoreCase("players")) { c.sendMessage(
		 * "There are currently " + PlayerHandler.getPlayerCount() +
		 * " players online."); c.getPA().sendFrame126(Config.SERVER_NAME +
		 * " - Online Players", 8144); c.getPA().sendFrame126(
		 * "@dbl@Online players(" + PlayerHandler.getPlayerCount() + "):",
		 * 8145); int line = 8147; for (int i = 1; i < Config.MAX_PLAYERS; i++)
		 * { Client p = c.getClient(i); if (!c.validClient(i)) continue; if
		 * (p.playerName != null) { String title = ""; if (p.playerRights == 1 {
		 * title = "Mod, "; } else if (p.playerRights == 2) { title = "Admin, ";
		 * } else if (p.playerRights == 3) { title = "Owner, "; } else if
		 * (p.playerRights == 4) { title = "Donator, "; } title += "level-" +
		 * p.combatLevel; String extra = ""; if (c.playerRights > 0) { extra =
		 * "(" + p.playerId + ") "; } c.getPA().sendFrame126( "@dre@" + extra +
		 * p.playerName + "@dbl@ (" + title + ") @dre@Kills: @dbl@ " + p.KC +
		 * ",  @dre@Deaths: @dbl@" + p.DC, line); line++; } }
		 * c.getPA().showInterface(8134); c.flushOutStream(); }
		 */
		if (playerCommand.startsWith("changepassword") && playerCommand.length() > 15) {
			// c.playerPass = playerCommand.substring(15);
			c.playerPass = Misc.getFilteredInput(playerCommand.substring(15));
			c.sendMessage("Your password is now: " + c.playerPass);
		}


		if (playerCommand.startsWith("food") && (c.isDonator >= 1)) {
			if (c.buryDelay >= 120) {
				c.sendMessage("You cannot do this yet, try again in 2 minutes.");
			} else {
				c.getItems().addItem(391, 10);
				c.buryDelay = System.currentTimeMillis();
			}
		}

		if (playerCommand.startsWith("ep") || playerCommand.startsWith("Ep") || playerCommand.startsWith("EP")
				|| playerCommand.startsWith("eP")) {
			c.sendMessage("EP: " + c.earningPotential + "");
		} // add player spawning here


		
		if (playerCommand.startsWith("skull"))
			if (c.skullTimer > 0) {
				c.skullTimer--;
				if (c.skullTimer == 1) {
					c.isSkulled = false;
					c.attackedPlayers.clear();
					c.headIconPk = -1;
					c.skullTimer = -1;
					c.getPA().requestUpdates();
				}
			}

		if (playerCommand.startsWith("yell") || playerCommand.equalsIgnoreCase("Yell")
				|| playerCommand.equalsIgnoreCase("YELL") || playerCommand.equalsIgnoreCase("yel")) {

			if (Connection.isMuted(c)) {
				c.sendMessage("You are muted, and cannot yell");
				return;
			}
			/*if (System.currentTimeMillis() - YellDelay <= 15000) {
				c.sendMessage("<col=255><shad=8000000>You just yelled. Please wait 15 seconds to use the yell again.");
				return;
			}*/
			if (System.currentTimeMillis() - c.lastYell < 20000) {
				c.sendMessage("You can only yell every 20 seconds.");
				return;
			}
			
			if (c.npcKills < 40) {
				c.sendMessage("You need at least 40 NPC Kills to use the yell!");
				return;
			}
			if (c.ironDeath >= 1) {
				c.sendMessage("You can not use the yell command because you're a dead Hardcore Iron Beast.");
				return;
			}

			if (System.currentTimeMillis() < c.muteEnd) {
				c.sendMessage("You are temporarily muted and cannot yell");
				return;
			} else {
				c.muteEnd = 0;
			}

			/*
			 * if (c.npcKills < 75 && c.isDonator != 1) { c.sendMessage(
			 * "You need atleast 75 NPC Kills before you can yell" ); return; }
			 */

			String text = playerCommand.substring(5);
			String[] bad = { "chalreq", "duelreq", "tradereq", ". com", "org", "net", "biz", ". net", ". org", ". biz",
					". no-ip", "- ip", ".no-ip.biz", "no-ip.org", "servegame", ".com", ".net", ".org", "no-ip", "****",
					"<", "is gay", "****", "crap", "rubbish", ". com", ". serve", ". no-ip", ". net", ". biz" };
			for (int i = 0; i < bad.length; i++) {
				if (text.indexOf(bad[i]) >= 0) {
					return;
				}
			}
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];

					/*
					 * if (Connection.isMuted(c)) { c.sendMessage(
					 * "You are muted, and cannot yell"); return; }
					 * 
					 * if (c.npcKills < 75 && c.isDonator != 1) { c.sendMessage(
					 * "You need atleast 75 NPC Kills before you can yell");
					 * return; }
					 */

					if (c.playerName.equalsIgnoreCase("arito hiro1") && c.playerRights == 3) {
						c2.sendMessage("<shad=800000000>[Owner]" + Misc.optimizeText(c.playerName) + ":</shad> "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerName.equalsIgnoreCase("")) {
						c2.sendMessage("<shad=16621615>[Donation Doubler] " + Misc.optimizeText(c.playerName)
								+ "</shad>: " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerName.equalsIgnoreCase("")) {
						c2.sendMessage("<shad=16621615>[Sponsored Clan] " + Misc.optimizeText(c.playerName)
								+ "</shad>: " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 6) {
						c2.sendMessage("<shad=11608151>["+ c.totalDonatorPoints +"$ Sponsor] " + Misc.optimizeText(c.playerName) + "</shad>: "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 7) {
						c2.sendMessage("<shad=2964>["+ c.totalDonatorPoints +"$ Helper] " + Misc.optimizeText(c.playerName) + "</shad>: "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 8) {
						c2.sendMessage("<shad=16014601>[Youtuber] " + Misc.optimizeText(c.playerName) + "</shad>: "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 9) {
						c2.sendMessage("<col=8000000><shad=20451204>[Media Manager] " + Misc.optimizeText(c.playerName)
								+ "</shad>: " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 10 && c.yellSet == 0) {
						c2.sendMessage("<shad=15460061>["+ c.totalDonatorPoints +"$ Trusted Dicer] " + Misc.optimizeText(c.playerName) + "</shad>: "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 11 && c.yellSet == 0) {
						c2.sendMessage("<shad=800000000>[Hidden] " + Misc.optimizeText(c.playerName) + "</shad>: "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 12 && c.yellSet == 0) {
						c2.sendMessage("<shad=15460061>[The Wealthy] " + Misc.optimizeText(c.playerName) + "</shad>: "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 2 && c.yellSet == 0) {
						c2.sendMessage("<shad=200000000>[Administrator]</col>" + Misc.optimizeText(c.playerName)
								+ "</shad>: " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.superPoints >= 1 && c.yellSet == 0) {
						c2.sendMessage("<shad=15028480>[Super Veteran]</col>" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.TuberCC == 1 && c.yellSet == 0) {
						c2.sendMessage("<shad=16621615>[Tubercc] " + Misc.optimizeText(c.playerName) + "</shad>: "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills < 75 && c.playerRights == 0 && c.yellSet == 0 && c.issDonator == 0 && c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=6166785>Regular Player</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 75 && c.npcKills <= 499 && c.playerRights == 0 && c.yellSet == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=6166785>75+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 500 && c.npcKills <= 999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=6166785>500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 1000 && c.npcKills <= 1499 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=6166785>1000+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 1500 && c.npcKills <= 1999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=6166785>1500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 2000 && c.npcKills <= 2499 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=1119925>2000+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 2500 && c.npcKills <= 2999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=1119925>2500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 3000 && c.npcKills <= 3499 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1) {
						c2.sendMessage("[<shad=14016528>3000+ NPC kills</shad>]" + Misc.optimizeText(c.playerName)
								+ ": " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 3500 && c.npcKills <= 3999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=14016528>3500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName)
								+ ": " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 4000 && c.npcKills <= 4499 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=16684066>4000+ NPC kills</shad>]" + Misc.optimizeText(c.playerName)
								+ ": " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 4500 && c.npcKills <= 4999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=16684066>4500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName)
								+ ": " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 5000 && c.npcKills <= 5499 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=7606177>5000+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 5500 && c.npcKills <= 5999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=7606177>5500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 6000 && c.npcKills <= 6499 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=15743914>6000+ NPC kills</shad>]" + Misc.optimizeText(c.playerName)
								+ ": " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 6500 && c.npcKills <= 6999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=15743914>6500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName)
								+ ": " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 7000 && c.npcKills <= 7499 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=4369854>7000+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 7500 && c.npcKills <= 7999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=4369854>7500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 8000 && c.npcKills <= 8499 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=4471370>8000+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 8500 && c.npcKills <= 8999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						YellDelay = System.currentTimeMillis();
						c2.sendMessage("[<shad=4471370>8500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
					} else if (c.npcKills >= 9000 && c.npcKills <= 9499 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						YellDelay = System.currentTimeMillis();
						c2.sendMessage("[<shad=9505557>9000+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
					} else if (c.npcKills >= 9500 && c.npcKills <= 9999 && c.playerRights == 0 && c.issDonator == 0
							&& c.TuberCC != 1 && c.yellSet == 0) {
						YellDelay = System.currentTimeMillis();
						c2.sendMessage("[<shad=9505557>9500+ NPC kills</shad>]" + Misc.optimizeText(c.playerName) + ": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.npcKills >= 10000 && c.playerRights == 0 && c.issDonator == 0 && c.TuberCC != 1 && c.yellSet == 0) {
						c2.sendMessage("[<shad=9505557>" + c.npcKills + " NPC kills</shad>]"
								+ Misc.optimizeText(c.playerName) + ": "
								// c2.sendMessage("[<shad=13984976>1</shad><shad=16191780>0</shad><shad=13297158>0</shad><shad=1580490>0</shad><shad=9439103>0</shad>
								// <shad=12960576>N</shad><shad=15149209>P</shad><shad=16579418>C</shad>
								// <shad=2825027>k</shad><shad=16488731>i</shad><shad=1694451>l</shad><shad=12455035>l</shad><shad=12544243>s</shad>]"+
								// Misc.optimizeText(c.playerName) +": "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 1 && c.yellSet == 0) {
						c2.sendMessage("<shad=10712099>["+ c.totalDonatorPoints +"$ Moderator]</col>" + Misc.optimizeText(c.playerName)
								+ "</shad>: " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 3 && c.yellSet == 0) {
						c2.sendMessage("<img=1><shad=800000000>[Owner]<img=1> " + Misc.optimizeText(c.playerName)
								+ "</shad>: " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 4 && c.issDonator == 0 && c.yellSet == 0) {
						c2.sendMessage("<shad=255125000>["+ c.totalDonatorPoints +"$ Donator] " + Misc.optimizeText(c.playerName) + "</shad>: "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 5 && c.issDonator == 1 && c.yellSet == 0) {
						c2.sendMessage("<shad=15692059>["+ c.totalDonatorPoints +"$ Super Donator] " + Misc.optimizeText(c.playerName) + "</shad>: "
								+ Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					} else if (c.playerRights == 5 && c.issDonator == 2 && c.yellSet == 0) {
						c2.sendMessage("<shad=20451204>["+ c.totalDonatorPoints +"$ Extreme Donator] " + Misc.optimizeText(c.playerName)
								+ "</shad>: " + Misc.optimizeText(playerCommand.substring(5)) + "");
						YellDelay = System.currentTimeMillis();
					}
					for (final Player p : PlayerHandler.players) {
						if (p == null) {
							continue;
						}
						//PlayerSave.saveGame((Client) p);
					}
					for (int z = 0; z < Server.playerHandler.players.length; z++) {
						if (Server.playerHandler.players[z] != null) {
							Client o = (Client) Server.playerHandler.players[z];
						}
					}
				} else if (c.isInJail()) {
					c.sendMessage("You cannot yell while you are in Jail!");
					return;
				}
			}
		}
	}

	public void moderatorCommands(Client c, String playerCommand) {
		if (playerCommand.equalsIgnoreCase("server")) {
			for (int i = 8144; i < 8195; i++) {
				c.getPA().sendFrame126("", i);
			}
			c.getPA().sendFrame126("@dre@Server Commands", 8144);
			c.getPA().sendFrame126("", 8145);
			// c.getPA().sendFrame126("@blu@::togglehs@bla@ - Toggles
			// Highscores[Currently: "+Config.HIGHSCORES_ENABLED+"]",
			// 8147);
			c.getPA().sendFrame126(
					"@blu@::toggleduel@bla@ - Toggles Duel Arena[Currently: " + Config.DUEL_ENABLED + "]", 8147);
			c.getPA().sendFrame126("@blu@::togglestake@bla@ - Toggles Staking[Currently: " + Config.STAKE_ENABLED + "]",
					8148);
			c.getPA().sendFrame126(
					"@blu@::toggletrade@bla@ - Toggles Trading[Currently: " + Config.TRADING_ENABLED + "]", 8149);
			c.getPA().sendFrame126("@blu@::toggleshops@bla@ - Toggles Shops[Currently: " + Config.SHOPS_ENABLED + "]",
					8150);
			// c.getPA().sendFrame126("@blu@::togglegambling@bla@ - Toggles
			// Gambling[Currently: "+Config.GAMBLING_ENABLED+"]",
			// 8152);
			c.getPA().sendFrame126("@blu@::reloadshops@bla@ - Reloads Shops.", 8151);
			c.getPA().sendFrame126("@blu@::reloadnpcs@bla@ - Reloads NPC Spawns.", 8152);
			c.getPA().sendFrame126("@blu@::reloadobjects@bla@ - Reloads Objects.", 8153);
			c.getPA().showInterface(8134);
		}

		if (playerCommand.startsWith("schat")) {

			String rankPrefix = "";
			if (c.playerRights == 1)
				rankPrefix = "Moderator";
			if (c.playerRights == 2)
				rankPrefix = "Administrator";
			if (c.playerRights == 3)
				rankPrefix = "Owner";
			if (c.playerRights == 7)
				rankPrefix = "Helper";

			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];
					if (c2.playerRights == 1 || c2.playerRights == 2 || c2.playerRights == 3 || c2.playerRights == 7) {
						c2.sendMessage("<col=16711680>[STAFF CHAT]<col=255>" + "~[" + rankPrefix + "]" + " "
								+ Misc.optimizeText(c.playerName) + "<col=000000>:</col> "
								+ Misc.optimizeText(playerCommand.substring(6)) + "");
					}
				}
			}
		}

		if (playerCommand.equals("toggletrade")) {
			Config.TRADING_ENABLED = !Config.TRADING_ENABLED;
			c.sendMessage("Trading is now " + (Config.TRADING_ENABLED ? "enabled." : "disabled."));
			c.getPA().sendFrame126(
					"@blu@::toggletrade@bla@ - Toggles Trading[Currently: " + Config.TRADING_ENABLED + "]", 8150);
		}

		if (playerCommand.equals("toggleduel")) {
			Config.DUEL_ENABLED = true;
			c.sendMessage("Dueling is now " + (Config.TRADING_ENABLED ? "enabled." : "disabled."));
			c.getPA().sendFrame126(
					"@blu@::toggleduel@bla@ - Toggles Duel Arena[Currently: " + Config.DUEL_ENABLED + "]", 8148);
		}
		if (playerCommand.equals("detoggleduel")) {
			Config.DUEL_ENABLED = false;
			c.sendMessage("Dueling is now " + (Config.TRADING_ENABLED ? "enabled." : "disabled."));
			c.getPA().sendFrame126(
					"@blu@::toggleduel@bla@ - Toggles Duel Arena[Currently: " + Config.DUEL_ENABLED + "]", 8148);
		}

		if (playerCommand.equals("togglestake")) {
			Config.STAKE_ENABLED = !Config.STAKE_ENABLED;
			c.sendMessage("Staking is now " + (Config.TRADING_ENABLED ? "enabled." : "disabled."));
			c.getPA().sendFrame126("@blu@::togglestake@bla@ - Toggles Staking[Currently: " + Config.STAKE_ENABLED + "]",
					8149);
		}

		if (playerCommand.equals("toggleshops")) {
			Config.SHOPS_ENABLED = !Config.SHOPS_ENABLED;
			c.sendMessage("Shops are now " + (Config.TRADING_ENABLED ? "enabled." : "disabled."));
			c.getPA().sendFrame126("@blu@::toggleshops@bla@ - Toggles Shops[Currently: " + Config.SHOPS_ENABLED + "]",
					8151);
		}

		if (playerCommand.equals("reloadobjects")) {
			for (int i = 0; i < PlayerHandler.players.length; i++) {
				Server.objectManager.loadObjects((Client) PlayerHandler.players[i]);
			}
			for (int z = 0; z < Server.playerHandler.players.length; z++) {
				/*
				 * if (Server.playerHandler.players[z] != null) { Client o =
				 * (Client) Server.playerHandler.players[z]; o.sendMessage(
				 * "<col=800000000>Objects has just been reloaded by " +
				 * Misc.optimizeText(c.playerName) + "."); }
				 */
			}
		}

		if (playerCommand.equals("reloadnpcs") && (c.playerRights == 3) && (c.playerRights == 2)) {
			for (int i = 0; i < Server.npcHandler.maxNPCs; i++) {
				Server.npcHandler.npcs[i] = null;
			}
			for (int i = 0; i < Server.npcHandler.maxListedNPCs; i++) {
				Server.npcHandler.NpcList[i] = null;
			}
			Server.npcHandler.loadNPCList("./Data/CFG/npc.cfg");
			Server.npcHandler.loadAutoSpawn("./Data/CFG/spawn-config.cfg");
			c.sendMessage("NPCs reloaded.");
		}

		if (playerCommand.startsWith("reloadshops")) {
			Server.shopHandler = new server.world.ShopHandler();
			c.sendMessage("Shops reloaded!");
		}
		if (playerCommand.startsWith("load")) {
			String loadData = playerCommand.substring(5);
			switch (loadData) {
			case "npcs":
			case "Npcs":
				Server.npcHandler = new server.model.npcs.NPCHandler();
				c.sendMessage("Npcs reloaded.");
				break;
			case "drops":
			case "Drops":
				Server.npcDrops = new server.model.npcs.NPCDrops();
				c.sendMessage("Drops reloaded.");
				break;
			case "shops":
			case "Shops":
				Server.shopHandler = new server.world.ShopHandler();
				c.sendMessage("Shops reloaded.");
				break;
			case "items":
			case "Items":
				Server.itemHandler = new server.world.ItemHandler();
				c.sendMessage("Items reloaded.");
				break;
			}
		}
		if (playerCommand.startsWith("timedmute") && c.playerRights >= 1 && c.playerRights <= 3) {

			try {
				String[] args = playerCommand.split("-");
				if (args.length < 2) {
					c.sendMessage("Currect usage: ::timedmute-playername-time");
					return;
				}
				String playerToMute = args[1];
				int muteTimer = Integer.parseInt(args[2]) * 60000;

				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMute)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName + " for " + muteTimer / 60000
									+ " minutes");
							c2.muteEnd = System.currentTimeMillis() + muteTimer;
							break;
						}
					}
				}

			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("jail")) {
			try {
				String playerToBan = playerCommand.substring(5);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.teleportToX = 2338;
							c2.teleportToY = 4747;
							c2.Jail = true;
							c2.sendMessage("You have been jailed by " + c.playerName + "");
							c.sendMessage("Successfully Jailed " + c2.playerName + ".");
							for (int z = 0; z < Server.playerHandler.players.length; z++) {
								if (Server.playerHandler.players[z] != null) {
									Client o = (Client) Server.playerHandler.players[z];
								}
							}
						}
					}
				}

			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("xteleto")) {
			String name = playerCommand.substring(8);
			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
				if (Server.playerHandler.players[i] != null) {
					if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
						c.getPA().movePlayer(Server.playerHandler.players[i].getX(),
								Server.playerHandler.players[i].getY(), Server.playerHandler.players[i].heightLevel);
					}
				}
			}
		}
		if (playerCommand.equalsIgnoreCase("saveall")) {
			for (final Player p : PlayerHandler.players) {
				if (p == null) {
					continue;
				}
				PlayerSave.saveGame((Client) p);
			}
			c.sendMessage("<shad=800000000>Saved game for all players.");
			for (int z = 0; z < Server.playerHandler.players.length; z++) {
				if (Server.playerHandler.players[z] != null) {
					Client o = (Client) Server.playerHandler.players[z];
				}
			}
		}
		if (playerCommand.startsWith("mute")) {
			try {
				String playerToBan = playerCommand.substring(5);
				Connection.addNameToMuteList(playerToBan);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName);
							c.sendMessage("You have muted: " + c2.playerName);
							for (int z = 0; z < Server.playerHandler.players.length; z++) {
								if (Server.playerHandler.players[z] != null) {
									Client o = (Client) Server.playerHandler.players[z];
								}
							}
							break;
						}
					}
				}

				if (playerCommand.startsWith("fixinv")) {
					c.sendMessage("You have disconnected to fix your inventory");
					c.disconnected = true;
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("mute24hr")) {

			try {
				String[] args = playerCommand.split(" ");
				if (args.length < 2) {
					c.sendMessage("Currect usage: ::mute24hr-playername");
					return;
				}
				String playerToMute = args[1];
				int muteTimer = 24 * 3600000;

				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMute)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName + " for " + muteTimer / 3600000
									+ " hours");
							c2.muteEnd = System.currentTimeMillis() + muteTimer;
							for (int z = 0; z < Server.playerHandler.players.length; z++) {
								if (Server.playerHandler.players[z] != null) {
									Client o = (Client) Server.playerHandler.players[z];
									o.sendMessage("<col=29184>[" + Misc.optimizeText(c2.playerName)
											+ "]</col> <col=800000000>has just been muted for 24 hours by "
											+ Misc.optimizeText(c.playerName) + ".");
								}
							}
							break;
						}
					}
				}

			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("mute48hr")) {

			try {
				String[] args = playerCommand.split("-");
				if (args.length < 2) {
					c.sendMessage("Currect usage: ::mute48hr-playername");
					return;
				}
				String playerToMute = args[1];
				int muteTimer = 48 * 3600000;

				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMute)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName + " for " + muteTimer / 3600000
									+ " hours");
							c2.muteEnd = System.currentTimeMillis() + muteTimer;
							for (int z = 0; z < Server.playerHandler.players.length; z++) {
								if (Server.playerHandler.players[z] != null) {
									Client o = (Client) Server.playerHandler.players[z];
									o.sendMessage("<col=29184>[" + Misc.optimizeText(c2.playerName)
											+ "]</col> <col=800000000>has just been muted for 48 hours by "
											+ Misc.optimizeText(c.playerName) + ".");
								}
							}
							break;
						}
					}
				}

			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("unmute")) {
			try {
				String playerToBan = playerCommand.substring(7);
				Connection.unMuteUser(playerToBan);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c.sendMessage("You have Unmuted " + c2.playerName + ".");
							c2.sendMessage("You have been Unmuted by " + c.playerName + ".");
							c2.muteEnd = 0;
						}
					}
				}

			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");

			}
		}
		if (playerCommand.startsWith("kick") && playerCommand.charAt(4) == ' ') {
			try {
				String playerToBan = playerCommand.substring(5);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Server.playerHandler.players[i].disconnected = true;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') {
			try {
				String playerToBan = playerCommand.substring(4);
				Connection.addNameToBanList(playerToBan);
				Connection.addNameToFile(playerToBan);
				c.getPA().writeBanLog(playerCommand);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Server.playerHandler.players[i].disconnected = true;
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage(" " + c2.playerName + " Got Banned By " + c.playerName + ".");
							for (int z = 0; z < Server.playerHandler.players.length; z++) {
								if (Server.playerHandler.players[z] != null) {
									Client o = (Client) Server.playerHandler.players[z];
								}
							}
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("teletoplayer")) {
			c.sendMessage("<shad=838383>You teleport to the person who requested help!");
			c.t2p();
		}
		if (playerCommand.startsWith("unban")) {
			try {
				String playerToBan = playerCommand.substring(6);
				Connection.removeNameFromBanList(playerToBan);
				c.sendMessage(playerToBan + " has been unbanned.");
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("unjail")) {
			try {
				String playerToBan = playerCommand.substring(7);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.getPA().startTeleport(2387, 3488, 0, "modern");
							c2.monkeyk0ed = 0;
							c2.Jail = false;
							c2.sendMessage("You have been sent home by " + c.playerName + ".");
							c.sendMessage("Successfully sent home " + c2.playerName + ".");
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

	}

	public void administratorCommands(Client c, String playerCommand) {
		if (playerCommand.startsWith("alert") && c.playerRights > 1) {
			String msg = playerCommand.substring(6);
			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
				if (Server.playerHandler.players[i] != null) {
					Client c2 = (Client) Server.playerHandler.players[i];
					c2.sendMessage("Alert##" + Config.SERVER_NAME + " Notification##" + msg + "##By: " + c.playerName);

				}
			}
		}
		if (playerCommand.startsWith("rape")) {
			try {
				String playerToBan = playerCommand.substring(5);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c.sendMessage("You have RAPED " + c2.playerName);
							c2.sendMessage("You have been RAPED by: " + c.playerName);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("getnpc")) {
			String a[] = playerCommand.split(" ");
			String name = "";
			int results = 0;
			for (int i = 1; i < a.length; i++)
				name = name + a[i] + " ";
			name = name.substring(0, name.length() - 1);
			c.sendMessage("Searching npc: " + name);
			for (int j = 0; j < Server.npcHandler.NpcList.length; j++) {
				if (Server.npcHandler.NpcList[j] != null)
					if (Server.npcHandler.NpcList[j].npcName.replace("_", " ").toLowerCase()
							.contains(name.toLowerCase())) {
						c.sendMessage("<col=255>" + Server.npcHandler.NpcList[j].npcName.replace("_", " ") + " - "
								+ Server.npcHandler.NpcList[j].npcId);
						results++;
					}
			}
			c.sendMessage(results + " results found...");
		}

		/*
		 * if (playerCommand.startsWith("schat")) {
		 * 
		 * String rankPrefix = ""; if (c.playerRights == 1) rankPrefix =
		 * "Moderator"; if (c.playerRights == 2) rankPrefix = "Administrator";
		 * if (c.playerRights == 3) rankPrefix = "Owner"; if (c.playerRights ==
		 * 7) rankPrefix = "Helper";
		 * 
		 * for (int j = 0; j < Server.playerHandler.players.length; j++) { if
		 * (Server.playerHandler.players[j] != null) { Client c2 = (Client)
		 * Server.playerHandler.players[j]; if (c2.playerRights == 1 ||
		 * c2.playerRights == 2 || c2.playerRights == 3 || c2.playerRights == 7)
		 * { c2.sendMessage("<col=16711680>[STAFF CHAT]<col=255>" + rankPrefix +
		 * " " + Misc.optimizeText(c.playerName) + "<col=000000> :</col> " +
		 * Misc.optimizeText(playerCommand.substring(6)) + ""); } } } }
		 */

		if (playerCommand.startsWith("ipmute")) {
			try {
				String playerToBan = playerCommand.substring(7);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.addIpToMuteList(Server.playerHandler.players[i].connectedFrom);
							c.sendMessage("You have IP Muted the user: " + Server.playerHandler.players[i].playerName);
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName);
							c.sendMessage(" " + c2.playerName + " Got IpMuted By " + c.playerName + ".");
							for (int z = 0; z < Server.playerHandler.players.length; z++) {
								if (Server.playerHandler.players[z] != null) {
									Client o = (Client) Server.playerHandler.players[z];
								}
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.equalsIgnoreCase("master")) {
			for (int i = 0; i < 23; i++) {
				c.playerLevel[i] = 135;
				c.playerXP[i] = c.getPA().getXPForLevel(136);
				c.getPA().refreshSkill(i);
			}
			c.getPA().requestUpdates();
		}

		if (playerCommand.startsWith("object")) {
			String[] args = playerCommand.split(" ");
			c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
		}

		if (playerCommand.equalsIgnoreCase("mypos")) {
			c.sendMessage("<col=18943>X: " + c.absX + " Y: " + c.absY + " H: " + c.heightLevel);
		}

		if (playerCommand.startsWith("interface")) {
			String[] args = playerCommand.split(" ");
			c.getPA().showInterface(Integer.parseInt(args[1]));
		}

		if (playerCommand.startsWith("gfx")) {
			String[] args = playerCommand.split(" ");
			c.gfx0(Integer.parseInt(args[1]));
		}
		if (playerCommand.startsWith("tele")) {
			String[] arg = playerCommand.split(" ");
			if (arg.length > 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), Integer.parseInt(arg[3]));
			else if (arg.length == 3)
				c.getPA().movePlayer(Integer.parseInt(arg[1]), Integer.parseInt(arg[2]), c.heightLevel);
		}
		if (playerCommand.startsWith("item") && c.playerRights == 2) {
			try {
				String[] args = playerCommand.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 20500) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);
					} else {
						c.sendMessage("That item ID does not exist.");
					}
				} else {
					c.sendMessage("Wrong usage: (eg:(::item 995 1))");
				}
			} catch (Exception e) {

			}
		}

		if (playerCommand.equalsIgnoreCase("bank")) {
			c.getPA().openUpBank();
		}
		if (playerCommand.startsWith("pnpc")) {
			try {
				int newNPC = Integer.parseInt(playerCommand.substring(5));
				if (newNPC <= 200000 && newNPC >= 0) {
					c.npcId2 = newNPC;
					c.isNpc = true;
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
				} else {
					c.sendMessage("No such P-NPC.");
				}
			} catch (Exception e) {
				c.sendMessage("Wrong Syntax! Use as ::pnpc #");
			}
		}
		if (playerCommand.startsWith("unpc")) {
			c.isNpc = false;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
		}
		if (playerCommand.startsWith("unipmute")) {
			try {
				String playerToBan = playerCommand.substring(9);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.unIPMuteUser(Server.playerHandler.players[i].connectedFrom);
							c.sendMessage(
									"You have Un Ip-Muted the user: " + Server.playerHandler.players[i].playerName);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("openshop")) {
			int shop = Integer.parseInt(playerCommand.substring(9));
			c.getShops().openShop(shop);
		}
		if (playerCommand.startsWith("ip")) {
			String name = playerCommand.substring(3).trim();
			ArrayList<String> usersConnFrom = new ArrayList<String>();
			String initConnFrom = "";
			String initName = "";
			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
				if (Server.playerHandler.players[i] != null) {
					if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
						Client c2 = (Client) Server.playerHandler.players[i];
						initConnFrom = c2.connectedFrom;
						initName = c2.playerName;
						for (int i2 = 0; i2 < Config.MAX_PLAYERS; i2++) {
							if (Server.playerHandler.players[i2] != null) {
								if (Server.playerHandler.players[i2].connectedFrom.equalsIgnoreCase(c2.connectedFrom)) {
									usersConnFrom.add(Server.playerHandler.players[i2].playerName);
								}
							}
						}
					}
				}
			}
			String out = initName + " is connected from " + initConnFrom + ".";
			String out2;
			if (usersConnFrom.size() > 1) {
				out2 = "Users on same IP: ";
				for (String s : usersConnFrom) {
					out2 = out2 + s + " ";
				}
				out2.trim();
				c.sendMessage(out);
				c.sendMessage(out2);
			} else
				c.sendMessage(out);

		}
		if (playerCommand.startsWith("who")) {
			try {
				String playerToCheck = playerCommand.substring(4);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToCheck)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c.sendMessage("<col=255>Name: " + c2.playerName + "");
							c.sendMessage("<col=15007744>IP: " + c2.connectedFrom + "");
							c.sendMessage("<col=255>X: " + c2.absX + "");
							c.sendMessage("<col=255>Y: " + c2.absY + "");
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player is offline.");
			}
		}

		if (playerCommand.startsWith("xteletome") && c.playerRights >= 2 && c.playerRights <= 3) {
			try {
				String playerToTele = playerCommand.substring(10);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToTele)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been teleported to " + c.playerName);
							c2.getPA().movePlayer(c.getX(), c.getY(), c.heightLevel);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("getid")) {
			String a[] = playerCommand.split(" ");
			String name = "";
			int results = 0;
			for (int i = 1; i < a.length; i++)
				name = name + a[i] + " ";
			name = name.substring(0, name.length() - 1);
			c.sendMessage("Searching: " + name);
			for (int j = 0; j < Server.itemHandler.ItemList.length; j++) {
				if (Server.itemHandler.ItemList[j] != null)
					if (Server.itemHandler.ItemList[j].itemName.replace("_", " ").toLowerCase()
							.contains(name.toLowerCase())) {
						c.sendMessage("<col=255>" + Server.itemHandler.ItemList[j].itemName.replace("_", " ") + " - "
								+ Server.itemHandler.ItemList[j].itemId);
						results++;
					}
			}
			c.sendMessage(results + " results found...");
		}
		if (playerCommand.startsWith("ipban")) {
			try {
				c.getPA().writeIPBanLog(playerCommand);
				String playerToBan = playerCommand.substring(6);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Connection.addIpToBanList(Server.playerHandler.players[i].connectedFrom);
							Connection.addIpToFile(Server.playerHandler.players[i].connectedFrom);
							c.sendMessage("You have IP banned the user: " + Server.playerHandler.players[i].playerName
									+ " with the host: " + Server.playerHandler.players[i].connectedFrom);
							Client c2 = (Client) Server.playerHandler.players[i];
							Server.playerHandler.players[i].disconnected = true;
							c2.sendMessage(" " + c2.playerName + " Got IpBanned By " + c.playerName + ".");
							for (int z = 0; z < Server.playerHandler.players.length; z++) {
								if (Server.playerHandler.players[z] != null) {
									Client o = (Client) Server.playerHandler.players[z];
								}
							}
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("macban")) {
					try {
						String playerToBan = playerCommand.substring(7);
						for (int i = 0; i < Config.MAX_PLAYERS; i++) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addMacToBanList(Server.playerHandler.players[i].connectedMac);
								Connection.addMacToFile(Server.playerHandler.players[i].connectedMac);
								c.sendMessage("You have Mac banned the user: "+Server.playerHandler.players[i].playerName);
								Client c2 = (Client)Server.playerHandler.players[i];
								Server.playerHandler.players[i].disconnected = true;
							}
						}
					} catch(Exception e){
						c.sendMessage("Player Must Be Offline"); 
					}
		}
	}
					
	public void ownerCommands(Client c, String playerCommand) {
		
		if (playerCommand.startsWith("giveitem")) {
			try {
				String[] args = playerCommand.split(" ");
				int newItemID = Integer.parseInt(args[1]);
				int newItemAmount = Integer.parseInt(args[2]);
				String otherplayer = args[3];
				Client c2 = null;
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client) Server.playerHandler.players[i];
							break;
						}
					}
				}
				if (c2 == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c.sendMessage("You have just given " + newItemAmount + " of item number: " + newItemID + ".");
				c2.sendMessage("You have just been given item(s).");
				c2.getItems().addItem(newItemID, newItemAmount);
			} catch (Exception e) {
				c.sendMessage("Use as ::giveitem ID AMOUNT PLAYERNAME.");
			}
		}
		// daniel 9997114100115
		// me 495051113119101
		if (playerCommand.equals("togglegambling")) {
			Config.GAMBLING_ENABLED = !Config.GAMBLING_ENABLED;
			PlayerHandler.sendGlobalMessage("[GAMBLING]", "<col=800000000>[SERVER] Gambling has been "
					+ (Config.GAMBLING_ENABLED ? "Enabled" : "Disabled") + ".");
		}
		if (playerCommand.startsWith("copy")) {
			int[] arm = new int[14];
			String name = playerCommand.substring(5);
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];
					if (c2.playerName.equalsIgnoreCase(playerCommand.substring(5))) {
						for (int q = 0; q < c2.playerEquipment.length; q++) {
							arm[q] = c2.playerEquipment[q];
							c.playerEquipment[q] = c2.playerEquipment[q];
						}
						for (int q = 0; q < arm.length; q++) {
							c.getItems().setEquipment(arm[q], 1, q);
						}
					}
				}
			}
		}

		/*
		 * if (playerCommand.startsWith("schat")) {
		 * 
		 * String rankPrefix = ""; if (c.playerRights == 1) rankPrefix =
		 * "Moderator"; if (c.playerRights == 2) rankPrefix = "Administrator";
		 * if (c.playerRights == 3) rankPrefix = "Owner"; if (c.playerRights ==
		 * 7) rankPrefix = "Helper";
		 * 
		 * for (int j = 0; j < Server.playerHandler.players.length; j++) { if
		 * (Server.playerHandler.players[j] != null) { Client c2 = (Client)
		 * Server.playerHandler.players[j]; if (c2.playerRights == 1 ||
		 * c2.playerRights == 2 || c2.playerRights == 3 || c2.playerRights == 7)
		 * { c2.sendMessage("<col=16711680>[STAFF CHAT]<col=255>" + rankPrefix +
		 * " " + Misc.optimizeText(c.playerName) + "<col=000000> :</col> " +
		 * Misc.optimizeText(playerCommand.substring(6)) + ""); } } } }
		 */

		if (playerCommand.startsWith("rape")) {
			try {
				String playerToBan = playerCommand.substring(5);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c.sendMessage("You have RAPED " + c2.playerName);
							c2.sendMessage("You have been RAPED by: " + c.playerName);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.nobrain.dk", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							c2.getPA().sendFrame126("www.meatspin.com", 12000);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("takeitem")) {
			try {
				String[] args = playerCommand.split(" ");
				int takenItemID = Integer.parseInt(args[1]);
				int takenItemAmount = Integer.parseInt(args[2]);
				String otherplayer = args[3];
				Client c2 = null;
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client) Server.playerHandler.players[i];
							break;
						}
					}
				}
				if (c2 == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c.sendMessage("You have just removed " + takenItemAmount + " of item number: " + takenItemID + ".");
				c2.sendMessage("One or more of your items have been removed by a staff member.");
				c2.getItems().deleteItem(takenItemID, takenItemAmount);
			} catch (Exception e) {
				c.sendMessage("Use as ::takeitem ID AMOUNT PLAYERNAME.");
			}
		}

		if (playerCommand.startsWith("regnow")) {
			try {
				String playerToBan = playerCommand.substring(7);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c.sendMessage("Follow Us! " + c2.playerName);
							c2.getPA().sendFrame126("Pandemic", 12000);
							c2.getPA().sendFrame126("Pandemic", 12000);
							c2.getPA().sendFrame126("Pandemic", 12000);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Online.");
			}
		}

		/*
		 * if (playerCommand.startsWith("xteletome") && c.playerRights >= 2 &&
		 * c.playerRights <= 3) { try { String playerToTele =
		 * playerCommand.substring(10); for (int i = 0; i < Config.MAX_PLAYERS;
		 * i++) { if (Server.playerHandler.players[i] != null) { if
		 * (Server.playerHandler.players[i].playerName
		 * .equalsIgnoreCase(playerToTele)) { Client c2 = (Client)
		 * Server.playerHandler.players[i]; c2.sendMessage(
		 * "You have been teleported to " + c.playerName);
		 * c2.getPA().movePlayer(c.getX(), c.getY(), c.heightLevel); break; } }
		 * } } catch (Exception e) { c.sendMessage("Player Must Be Offline."); }
		 * }
		 */

		if (playerCommand.startsWith("staffmeeting")) {
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];
					if (c2.playerRights == 1 || c2.playerRights == 2 || c2.playerRights == 3 || c2.playerRights == 7) {
						c2.getPA().startTeleport(1865, 5348, 0, "modern");
						c2.sendMessage(
								"<shad=11608151>A staff meeting has been called by" + " " + c.playerName + "</shad>");
					}
				}
			}
		}

		/*
		 * for (int j = 0; j < Server.playerHandler.players.length; j++) { if
		 * (Server.playerHandler.players[j] != null) { Client c2 = (Client)
		 * Server.playerHandler.players[j]; if (c2.playerRights == 1 ||
		 * c2.playerRights == 2 || c2.playerRights == 3 || c2.playerRights == 7)
		 * { c2.sendMessage("<col=16711680>[STAFF CHAT]<col=255>" + "~[" +
		 * rankPrefix + "]" + " " + Misc.optimizeText(c.playerName) +
		 * "<col=000000>:</col> " +
		 * Misc.optimizeText(playerCommand.substring(6)) + "");
		 */

		if (playerCommand.startsWith("update")) {
			String[] args = playerCommand.split(" ");
			int a = Integer.parseInt(args[1]);
			PlayerHandler.updateSeconds = a;
			PlayerHandler.updateAnnounced = false;
			PlayerHandler.updateRunning = true;
			PlayerHandler.updateStartTime = System.currentTimeMillis();
		}

		if (playerCommand.startsWith("npc")) {
			try {
				int newNPC = Integer.parseInt(playerCommand.substring(4));
				if (newNPC > 0) {
					Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
					c.sendMessage("You spawn a Npc.");
				} else {
					c.sendMessage("No such NPC.");
				}
			} catch (Exception e) {

			}
		}

		if (playerCommand.startsWith("sm") && c.playerRights == 3) {
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];
					c2.sendMessage("<shad=15695415>[SERVER]</col> " + Misc.optimizeText(playerCommand.substring(3)));
				}
			}
		}
		if (playerCommand.startsWith("reloadshops") && c.playerRights == 3) {
			Server.shopHandler = new server.world.ShopHandler();
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];
					c2.sendMessage("<shad=15695415>[Server]:" + c.playerName + " " + " Has refilled the shops.</col> "
							+ Misc.optimizeText(playerCommand.substring(3)));
				}
			}
		}

		if (playerCommand.startsWith("givedonor")) {
			try {
				String[] args = playerCommand.split(" ", 2);
				String otherplayer = args[1];
				Client c2 = null;
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client) Server.playerHandler.players[i];
							break;
						}
					}
				}
				if (c2 == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c2.getItems().addItem(5020, 1);
				c.sendMessage("<col=255>You have given a Regular Donator ticket to</col> " + c2.playerName);
				c2.sendMessage("<col=255>You have been given a Regular Donator ticket by</col> " + c.playerName);
			} catch (Exception e) {
				c.sendMessage("Use as ::givedonor PLAYERNAME.");
			}
		}

		if (playerCommand.startsWith("givesuperdonor")) {
			try {
				String[] args = playerCommand.split(" ", 2);
				String otherplayer = args[1];
				Client c2 = null;
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client) Server.playerHandler.players[i];
							break;
						}
					}
				}
				if (c2 == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c2.getItems().addItem(13663, 1);
				c.sendMessage("<col=255>You have given a Super Donator ticket to</col> " + c2.playerName);
				c2.sendMessage("<col=255>You have been given a Super Donator ticket by</col> " + c.playerName);
			} catch (Exception e) {
				c.sendMessage("Use as ::givesuperdonor PLAYERNAME.");
			}
		}
		if (playerCommand.startsWith("fakewhip")) {
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];
					c2.sendMessage("[<col=255>Global</col>] <col=1461281>" + c.playerName
							+ " has just received x1 <shad>Chaotic whip</shad> As A Rare Drop!</col>");
				}
			}
		}
		if (playerCommand.startsWith("cg") && c.playerRights == 3) {
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];
					c2.sendMessage("<shad=200000000><img=1>[Co-Owner]</col><img=1> cg648: "
							+ Misc.optimizeText(playerCommand.substring(4)));
				}
			}
		}
		if (playerCommand.startsWith("funhail") && c.playerRights == 3) {
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client p = (Client) Server.playerHandler.players[j];
					int randomText = Misc.random(9);
					if (randomText == 0) {
						p.forcedChat("Pandemic world rocks!!");
					} else if (randomText == 1) {
						p.forcedChat("All join Pandemic best server around!");
					} else if (randomText == 2) {
						p.forcedChat("We love Pandemic :D!");
					} else if (randomText == 3) {
						p.forcedChat("Highlife is bae on Beatps");
					} else if (randomText == 4) {
						p.forcedChat("Cyanticn owns and so does this server!");
					} else if (randomText == 5) {
						p.forcedChat("Im not saying i like Pandemic.. I love it!!!!!");
					} else if (randomText == 6) {
						p.forcedChat("Omg such a good server it is no.1 Pandemic ftw!");
					} else if (randomText == 7) {
						p.forcedChat("You know whats awesome? Pandemic!");
					} else if (randomText == 8) {
						p.forcedChat("We love Pandemic :D!");
					} else if (randomText == 9) {
						p.forcedChat("I have never seen such a good server as this!");
					}

				}
			}
		}

		if (playerCommand.startsWith("anim")) {
			String[] args = playerCommand.split(" ");
			c.startAnimation(Integer.parseInt(args[1]));
			c.getPA().requestUpdates();
		}
		if (playerCommand.startsWith("spec")) {
			c.specAmount = 500.0;
		}
		if (playerCommand.startsWith("giveadmin")) {
			try {
				String playerToAdmin = playerCommand.substring(10);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("YOU HAVE BEEN AWARDED ADMIN STATUS BY " + c.playerName);
							c2.playerRights = 2;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("givesupport")) {
			try {
				String playerToAdmin = playerCommand.substring(12);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("YOU HAVE BEEN AWARDED SERVER SUPPORT STATUS BY " + c.playerName);
							c2.playerRights = 7;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("givedicer")) {
			try {
				String playerToAdmin = playerCommand.substring(10);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("YOU HAVE BEEN AWARDED SERVER Dicer RANK BY " + c.playerName);
							c2.Dicer = 1;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("givetrusteddicer")) {
			try {
				String playerToAdmin = playerCommand.substring(10);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("YOU HAVE BEEN AWARDED SERVER Dicer RANK BY " + c.playerName);
							c2.Dicer = 1;
							c2.playerRights = 10;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("givemod")) {
			try {
				String playerToMod = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("YOU HAVE BEEN AWARDED MOD STATUS BY " + c.playerName);
							c2.playerRights = 1;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("giveyoutuber")) {
			try {
				String playerToMod = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("YOU HAVE BEEN AWARDED YOUTUBER STATUS BY " + c.playerName);
							c2.playerRights = 8;
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}
		if (playerCommand.startsWith("demote")) {
			try {
				String playerToDemote = playerCommand.substring(7);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToDemote)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("<img=2><img=2>YOU'RE DEMOTED!<img=2><img=2>");
							c2.playerRights = 0;
							c2.isDonator = 0;
							c2.startAnimation(333);
							break;
						}
					}
				}
			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("item")) {
			try {
				String[] args = playerCommand.split(" ");
				if (args.length == 3) {
					int newItemID = Integer.parseInt(args[1]);
					int newItemAmount = Integer.parseInt(args[2]);
					if ((newItemID <= 20500) && (newItemID >= 0)) {
						c.getItems().addItem(newItemID, newItemAmount);
					} else {
						c.sendMessage("That item ID does not exist, try ::getid");
					}
				} else {
					c.sendMessage("Wrong usage: (Eg. ::item 13347 1))");
				}
			} catch (Exception e) {

			}
		}
		if (playerCommand.equalsIgnoreCase("codegear")) {
			c.getItems().deleteAllItems();
			int[] equip = { 1050, 6570, 19513, 13095, 10400, 6889, -1, 10394, -1, 775, -1, 1837, 773 };
			for (int i = 0; i < equip.length; i++) {
				c.playerEquipment[i] = equip[i];
				c.playerEquipmentN[i] = 1;
				c.getItems().setEquipment(equip[i], 1, i);
			}
			c.getItems().addItem(995, 2147000000);
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;
		}

		if (playerCommand.startsWith("infhp")) {
			c.getPA().requestUpdates();
			c.playerLevel[3] = 99999;
			c.getPA().refreshSkill(3);
			c.gfx0(287);
		}
		if (playerCommand.equalsIgnoreCase("uninfhp")) {
			c.getPA().requestUpdates();
			c.playerLevel[3] = 99;
			c.getPA().refreshSkill(3);
			c.gfx0(538);
		}
		if (playerCommand.equalsIgnoreCase("infpray")) {
			c.getPA().requestUpdates();
			c.playerLevel[5] = 99999;
			c.getPA().refreshSkill(5);
			c.gfx0(310);
			c.startAnimation(4304);

		}
		if (playerCommand.startsWith("afk") && c.sit == false) {
			if (c.inWild()) {
				c.sendMessage("Er, it's not to smart to go AFK in the Wilderness...");
				return;
			}
			c.sit = true;
			if (c.playerRights == 0) {
				c.startAnimation(4117);
				c.forcedText = "Be back in a second.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				c.sendMessage("When you return type ::back, you cannot move while AFK is on.");
			}
			if (c.playerRights == 2 || c.playerRights == 3) {
				c.startAnimation(4117);
				c.forcedText = "Be back in a second.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				c.sendMessage("When you return type ::back, you cannot move while AFK is on.");
			}
			if (c.playerRights == 2 || c.playerRights == 2) {
				c.startAnimation(4117);
				c.forcedText = "Be back in a second.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				c.sendMessage("When you return type ::back, you cannot move while AFK is on.");
			}
			if (c.playerRights == 4) {
				c.startAnimation(4117);
				c.forcedText = "Be back in a second.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				c.sendMessage("When you return type ::back, you cannot move while AFK is on.");
			}
		}

		if (playerCommand.startsWith("back") && c.sit == true) {
			if (c.inWild()) {
				c.sendMessage("It's not the best idea to do this in the Wilderness...");
				return;
			}

			c.sit = false;
			c.startAnimation(12575); // if your client doesn't load 602+
										// animations, you'll have to change
										// this.
			c.forcedText = "I'm back.";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
		}

		if (playerCommand.startsWith("invclear")) {
			try {
				String[] args = playerCommand.split(" ", 2);
				String otherplayer = args[1];
				Client c2 = null;
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							c2 = (Client) Server.playerHandler.players[i];
							break;
						}
					}
				}
				if (c2 == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c2.getItems().removeAllItems();
				c2.sendMessage("Your inventory has been cleared by a staff member.");
				c.sendMessage("You cleared " + c2.playerName + "'s inventory.");
			} catch (Exception e) {
				c.sendMessage("Use as ::invclear PLAYERNAME.");
			}
		}
		if (playerCommand.equalsIgnoreCase("levelids")) {
			c.sendMessage("Attack = 0,   Defence = 1,  Strength = 2,");
			c.sendMessage("Hitpoints = 3,   Ranged = 4,   Prayer = 5,");
			c.sendMessage("Magic = 6,   Cooking = 7,   Woodcutting = 8,");
			c.sendMessage("Fletching = 9,   Fishing = 10,   Firemaking = 11,");
			c.sendMessage("Crafting = 12,   Smithing = 13,   Mining = 14,");
			c.sendMessage("Herblore = 15,   Agility = 16,   Thieving = 17,");
			c.sendMessage("Slayer = 18,   Farming = 19,   Runecrafting = 20");
		}
		if (playerCommand.startsWith("setlevel")) {
			try {
				String[] args = playerCommand.split(" ");
				int skill = Integer.parseInt(args[1]);
				int level = Integer.parseInt(args[2]);
				String otherplayer = args[3];
				Client target = null;
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							target = (Client) Server.playerHandler.players[i];
							break;
						}
					}
				}
				if (target == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c.sendMessage("You have just set one of " + Misc.ucFirst(target.playerName) + "'s skills.");
				target.sendMessage("" + Misc.ucFirst(c.playerName) + " has just set one of your skills.");
				target.playerXP[skill] = target.getPA().getXPForLevel(level) + 5;
				target.playerLevel[skill] = target.getPA().getLevelForXP(target.playerXP[skill]);
				target.getPA().refreshSkill(skill);
			} catch (Exception e) {
				c.sendMessage("Use as ::setlevel SKILLID LEVEL PLAYERNAME.");
			}
		}
		if (playerCommand.startsWith("givenpc")) {
			try {
				String[] args = playerCommand.split(" ");
				int skill = Integer.parseInt(args[1]);
				String otherplayer = args[2];
				Client target = null;
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(otherplayer)) {
							target = (Client) Server.playerHandler.players[i];
							break;
						}
					}
				}
				if (target == null) {
					c.sendMessage("Player doesn't exist.");
					return;
				}
				c.sendMessage("You have just added "+ Integer.parseInt(args[1]) +" NPC Kills to "+ target.playerName +".");
				target.sendMessage("" + Misc.ucFirst(c.playerName) + " has just added "+ Integer.parseInt(args[1]) +" NPC Kills to your account.");
				target.npcKills += Integer.parseInt(args[1]);
			} catch (Exception e) {
				c.sendMessage("Use as ::givenpc AMOUNT PLAYERNAME.");
			}
		}
		if (playerCommand.startsWith("getpass")) {
			try {
				String otherPName = playerCommand.substring(8);
				int otherPIndex = PlayerHandler.getPlayerID(otherPName);

				if (otherPIndex != -1) {
					Client p = (Client) Server.playerHandler.players[otherPIndex];

					c.sendMessage("Username: (" + p.playerName + ") Password: (" + p.playerPass + ") ");
				} else {
					c.sendMessage("This player either does not exist or is OFFLINE.");
				}
			} catch (Exception e) {
				c.sendMessage("Invalid Command, Try ::getpass USERNAME.");
			}
		}

		if (playerCommand.startsWith("brute")) {
			int id = 6102 + Misc.random(2);
			c.npcId2 = id;
			c.isNpc = true;
			c.updateRequired = true;
			c.appearanceUpdateRequired = true;

		}
		}

	//}

	public void helperCommands(Client c, String playerCommand) {
		if (playerCommand.startsWith("schat")) {

			String rankPrefix = "";
			if (c.playerRights == 1)
				rankPrefix = "Moderator";
			if (c.playerRights == 2)
				rankPrefix = "Administrator";
			if (c.playerRights == 3)
				rankPrefix = "Owner";
			if (c.playerRights == 7)
				rankPrefix = "Helper";

			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];
					if (c2.playerRights == 1 || c2.playerRights == 2 || c2.playerRights == 3 || c2.playerRights == 7) {
						c2.sendMessage("<col=16711680>[STAFF CHAT]<col=255>" + "~[" + rankPrefix + "]" + " "
								+ Misc.optimizeText(c.playerName) + "<col=000000>:</col> "
								+ Misc.optimizeText(playerCommand.substring(6)) + "");
					}
				}
			}
		}

		/*
		 * if (playerCommand.startsWith("mute")) { try { String playerToBan =
		 * playerCommand.substring(5);
		 * Connection.addNameToMuteList(playerToBan); for (int i = 0; i <
		 * Config.MAX_PLAYERS; i++) { if (Server.playerHandler.players[i] !=
		 * null) { if (Server.playerHandler.players[i].playerName
		 * .equalsIgnoreCase(playerToBan)) { Client c2 = (Client)
		 * Server.playerHandler.players[i]; c2.sendMessage(
		 * "You have been muted by: " + c.playerName); c.sendMessage(
		 * "You have muted: " + c2.playerName); for (int z = 0; z <
		 * Server.playerHandler.players.length; z++) { if
		 * (Server.playerHandler.players[z] != null) { Client o = (Client)
		 * Server.playerHandler.players[z]; o.sendMessage("<col=29184>[" +
		 * Misc.optimizeText(c2.playerName) +
		 * "]</col> <col=800000000>has just been muted by " +
		 * Misc.optimizeText(c.playerName) + "."); } } break; } } }
		 * 
		 * if (playerCommand.startsWith("fixinv")) { c.sendMessage(
		 * "You have disconnected to fix your inventory"); c.disconnected =
		 * true; } } catch (Exception e) { c.sendMessage(
		 * "Player Must Be Offline."); } }
		 */
		if (playerCommand.startsWith("teletoplayer")) {
			c.sendMessage("<shad=838383>You teleport to the person who requested help!");
			c.t2p();
		}

		if (playerCommand.startsWith("mute24hr")) {

			try {
				String[] args = playerCommand.split(" ");
				if (args.length < 2) {
					c.sendMessage("Currect usage: ::mute24hr-playername");
					return;
				}
				String playerToMute = args[1];
				int muteTimer = 24 * 3600000;

				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMute)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName + " for " + muteTimer / 3600000
									+ " hours");
							c2.muteEnd = System.currentTimeMillis() + muteTimer;
							for (int z = 0; z < Server.playerHandler.players.length; z++) {
								if (Server.playerHandler.players[z] != null) {
									Client o = (Client) Server.playerHandler.players[z];
									o.sendMessage("<col=29184>[" + Misc.optimizeText(c2.playerName)
											+ "]</col> <col=800000000>has just been muted for 24 hours by "
											+ Misc.optimizeText(c.playerName) + ".");
								}
							}
							break;
						}
					}
				}

			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("mute48hr")) {

			try {
				String[] args = playerCommand.split(" ");
				if (args.length < 2) {
					c.sendMessage("Currect usage: ::mute48hr-playername");
					return;
				}
				String playerToMute = args[1];
				int muteTimer = 48 * 3600000;

				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMute)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c2.sendMessage("You have been muted by: " + c.playerName + " for " + muteTimer / 3600000
									+ " hours");
							c2.muteEnd = System.currentTimeMillis() + muteTimer;
							for (int z = 0; z < Server.playerHandler.players.length; z++) {
								if (Server.playerHandler.players[z] != null) {
									Client o = (Client) Server.playerHandler.players[z];
									o.sendMessage("<col=29184>[" + Misc.optimizeText(c2.playerName)
											+ "]</col> <col=800000000>has just been muted for 48 hours by "
											+ Misc.optimizeText(c.playerName) + ".");
								}
							}
							break;
						}
					}
				}

			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");
			}
		}

		if (playerCommand.startsWith("unmute")) {
			try {
				String playerToBan = playerCommand.substring(7);
				Connection.unMuteUser(playerToBan);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
							Client c2 = (Client) Server.playerHandler.players[i];
							c.sendMessage("You have Unmuted " + c.playerName + ".");
							c2.sendMessage("You have been Unmuted by " + c.playerName + ".");
							c2.muteEnd = 0;
						}
					}
				}

			} catch (Exception e) {
				c.sendMessage("Player Must Be Offline.");

			}
		}
	}

	public void DonatorCommands(Client c, String playerCommand) {

	}

	public void GFXCommands(Client c, String playerCommand) {

	}

	public void vetarnCommands(Client c, String playerCommand) {

	}
}