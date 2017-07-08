package server.model.players.packets;

import server.Config;
import server.Connection;
import server.Server;
import server.model.items.GameItem;
import server.model.minigames.GnomeGlider;
import server.model.minigames.Raid;
import server.model.minigames.TzhaarSpawn;
import server.model.players.AchievementManager;
import server.model.players.Achievements;
import server.model.players.Client;
import server.model.players.Cons;
import server.model.players.Hack3rPouch;
import server.model.players.PacketType;
import server.model.players.PartyRoom;
import server.model.players.PlayerHandler;
import server.model.players.Content.Prestige;
import server.model.players.Content.RequestHelp;
import server.model.players.Content.Gambler.GambleInterface;
import server.model.players.skills.ConstructionEvents;
import server.model.players.skills.Cooking;
import server.util.Misc;

/**
 * Clicking most buttons
 **/
public class ClickingButtons implements PacketType {

	private long lastDelay = 0;
	public static boolean sentBankMes = false;

	@SuppressWarnings({ "static-access", "null", "unused" })
	@Override
	public void processPacket(final Client c, int packetType, int packetSize) {
		int actionButtonId = Misc.hexToInt(c.getInStream().buffer, 0, packetSize);
		Hack3rPouch.makePouch(c, packetType, packetSize);
		Cons.HandleConstructionInterface(c, packetType, packetSize);

		// int actionButtonId = c.getInStream().readShort();
		GnomeGlider.flightButtons(c, actionButtonId);
		if (c.isDead)
			return;
		if (c.playerRights == 3)
			Misc.println(c.playerName + " - actionbutton: " + actionButtonId);
		for (int i = 0; i < c.qCAB.length; i++) {
			if (actionButtonId == c.qCAB[i][0]) {
				for (int j = 0; j < c.qCS.length; j++) {
					if (j == i) {
						c.forcedText = c.qC + "My " + c.qCS[j] + " Level is "
								+ c.getLevelForXP(c.playerXP[c.qCAB[i][1]]) + ".";
						c.forcedChatUpdateRequired = true;
						c.updateRequired = true;
					}
				}
			}
		}
		int[] spellIds = { 4128, 4130, 4132, 4134, 4136, 4139, 4142, 4145, 4148, 4151, 4153, 4157, 4159, 4161, 4164,
				4165, 4129, 4133, 4137, 6006, 6007, 6026, 6036, 6046, 6056, 4147, 6003, 47005, 4166, 4167, 4168, 48157,
				50193, 50187, 50101, 50061, 50163, 50211, 50119, 50081, 50151, 50199, 50111, 50071, 50175, 50223, 50129,
				50091 };
		for (int i = 0; i < spellIds.length; i++) {
			if (actionButtonId == spellIds[i]) {
				if (c.autocasting) {
					c.autocasting = false;
					c.getPA().resetAutocast();
				} else {
					c.autocasting = true;
					c.autocastId = i;
				}
			}

		}
		if (GambleInterface.gambling(c) && actionButtonId != 205025 && actionButtonId == 205026) {
			c.sendMessage(" " + actionButtonId);
			return;
		}
		switch (actionButtonId) {
		case 207015:// Auction house open sell
			c.getAuctions().openAuctionSell();
			break;
		case 207099:// Auction house close search
			c.getAuctions().closeSearch();
			break;

		case 207214:// Auction house buy back to main
		case 207114:// Auction house sell back to main
			c.getAuctions().openAuction();
			break;
		case 207215:// Auction house change bid
			c.xInterfaceId = 53200;
			c.getOutStream().createFrame(27);
			break;
		case 207115:// Auction house change price
			c.xInterfaceId = 53100;
			c.getOutStream().createFrame(27);
			break;
		case 207116:// Auction house sell default bid.
			c.getAuctions().setMinBid();
			break;
		case 207216:// Auction house buy min bid.
			c.getAuctions().setMinBid();
			break;
		case 207117:// Auction house confirm sell
			c.getAuctions().sellItem();
			break;
		case 207217:// Auction house confirm buy
			c.getAuctions().buyItem();
			break;
		case 207019:// Auction house open history
			c.getAuctions().openMyItems();
			break;
		case 207020:// Auction house page back
			c.getAuctions().changePage(-1);
			break;
		case 207023:// Auction house page forward
			c.getAuctions().changePage(1);
			break;
		case 205025:// Accept button - real.
			if (GambleInterface.gambling(c))
				c.getGambling().acceptGamble();
			break;
		case 205026:// Accept button - fake.
			if (GambleInterface.gambling(c))
				c.sendMessage("This is "
						+ Misc.formatPlayerName(PlayerHandler.players[c.getGambling().getChallengerId()].playerName)
						+ "'s button.");
			break;
		// ppanel shit
		case 55096:// This is the button id
			c.getPA().removeAllWindows();// Choosing No will remove all the
											// windows
			c.droppedItem = -1;
			break;
		case 71034:// This is the button id
			c.getPA().removeAllWindows();
			break;

		case 55095:// This is the button id
			c.getPA().destroyItem(c.droppedItem);// Choosing Yes will delete the
													// item and make it
													// dissapear
			c.droppedItem = -1;
			break;
		case 140162:
		case 63204: // Close Button
			c.getPA().closeAllWindows();
			break;
		case 75053:
			c.getPA().refreshPlayersInterface();
			break;
		case 91184:
			//AchievementManager.increase(c, Achievements.SUMM);
			break;

		case 161004: // zzzzz
			c.getPA().sendFrame126("www.Pandemic.com", 12000);
			break;
		case 161017: // zzzzz
			c.forcedChat("I currently have " + c.barbPoints + " wins and " + c.agilitypoints + " losses");
			break;
		case 153128:
			c.getPA().startTeleport(2387, 3488, 0, "modern"); // shops
			break;
		case 153129:
			c.getPA().startTeleport(3561, 9948, 0, "modern"); // shops
			break;
		case 153130:
			c.getPA().startTeleport(3561, 9948, 4, "modern"); // shops
			break;
		case 153131:
			c.getPA().startTeleport(2917, 3628, 0, "modern"); // shops
			break;
		case 153132:
			if (c.Dicer == 0) {
				c.sendMessage("You need Dice Rights in order to purchase this.");
				return;
			}
			if (c.getItems().playerHasItem(5021, 5000)) {
				c.getItems().addItem(15098, 1);
				c.getItems().deleteItem(5021, 5000);
				c.sendMessage("<col=255>Remember, the dice bag is untradeable!");
			} else if (c.getItems().playerHasItem(4067, 2500)) {
				c.getItems().addItem(15098, 1);
				c.getItems().deleteItem(4067, 2500);
				c.sendMessage("<col=255>Remember, the dice bag is untradeable!");
			} else {
				c.sendMessage("<col=255>You need <col=800000000>5000 1b tickets<col=255> or <col=800000000>2500 2b tickets<col=255> to claim your dicebag and start hosting!");
			}
			break;
		case 153133:
			if (c.isDonator >= 1) {
				c.getPA().startTeleport(2393, 9894, 0, "modern"); // dzone
				c.sendMessage("Welcome to the Donator Zone.");
			} else {
				c.sendMessage("<col=255>You need to be a <col=800000000>Donator<col=255> to get access to the Donator Zone!");
			}
			break;
		case 153134:
			if (c.issDonator >= 1) {
				c.getPA().startTeleport(2393, 9894, 4, "modern"); // super dzone
				c.sendMessage("Welcome to the Super Donator Zone.");
			} else {
				c.sendMessage("<col=255>You need to be a <col=800000000>Super Donator<col=255> to get access to the Super Donator Zone!");
			}
			break;
		case 153135:
			c.getPA().startTeleport(3309, 9376, 0, "modern"); // corp
			break;
		case 153136:
			c.getPA().startTeleport(1912, 4367, 0, "modern"); // Vorago
			c.sendMessage("Vorago drops the powerful Gold Ring (i), Gold Necklalce & a <col=255>Blue Crystal<col=0>! ");
			break;
		case 153137:
			c.getPA().startTeleport(2586, 9449, 0, "modern"); // torvazone
			break;
		case 153138:
			c.getPA().startTeleport(2379, 4952, 0, "modern"); // torvazone
			break;
		case 153139:
			if (c.playerLevel[3] == 135) {
				c.getPA().startTeleport(3561, 9948, 8, "modern"); // shops 135HP
			} else {
				c.sendMessage("<col=255>You need <col=800000000>135 hitpoints<col=255> to get access to the 135 zone!");
			}
			break;
		case 153140:
			if (c.npcKills > 499) {
				c.getPA().startTeleport(2985, 9640, 0, "modern"); // Hulk
			} else {
				c.sendMessage(
						"<col=255>You need at least <col=800000000>500 kills<col=255> to get access to the Hulk Torva Boss!");
			}
			break;
		case 153141:
			c.getPA().startTeleport(2880, 10199, 1, "modern"); // Piyan
			break;
		case 153142:
			if (c.npcKills > 1499 && c.issDonator >= 1) {
				c.getPA().startTeleport(2965, 3351, 4, "modern"); // Santa
			} else {
				c.sendMessage(
						"<col=255>You need at least <col=800000000>1500 kills<col=255> & <col=800000000>Super donator<col=255> to get access to the White Knights!");
			}
			break;
		case 153143:
			if (c.npcKills > 49) {
				c.getPA().startTeleport(2982, 9631, 4, "modern"); // Ihlakhizan
				c.sendMessage("<col=255>Watch out, Ihlakhizan is able to and will most definitely 1-hit you!");
			} else {
				c.sendMessage("<col=255>You should train your <col=800000000>combat levels<col=255> some more!");
			}
			break;
		case 153144:
				c.getPA().startTeleport(3247, 9904, 0, "modern"); // Articuno
				c.sendMessage("<col=255>Good luck killing Articuno! He has 1 Million HP, so goodluck AFK-ing!");
			break;
		case 153145:
			if (c.npcKills > 7499 && c.isDonator != 0){
			c.getPA().startTeleport(3247, 9904, 4, "modern"); // Shadow
			c.sendMessage("<col=255>Good luck killing Tumeken's Shadow!");
			} else {
				c.sendMessage("<col=255>You need at least 7500 NPC Kills + Regular Donator to get access to Tumeken's Shadow!");
			}
		break;
		case 153146:
			if (c.npcKills > 3499){
			c.getPA().startTeleport(2585, 4838, 0, "modern"); // Small Roc
			c.sendMessage("<col=255>You have been teleported to the Roc's island!");
			} else {
				c.sendMessage("<col=255>You need at least 3500 NPC Kills to get access to Roc's Island.");
			}
		break;
		case 153147:
			c.getPA().startTeleport(2464, 4782, 0, "modern"); // Geodude
			c.sendMessage("<col=255>You have been teleported to the Geodude's cave!");
		break;
		case 153148:
			if (c.totalDonatorPoints >= 100) {
			c.getPA().startTeleport(2636, 4692, 1, "modern"); // Leeches
			c.sendMessage("<col=255>You have been teleported to the Sponsor Leeches!");
			} else {
				c.sendMessage("You need to be a Sponsor Donator to get access to the Leeches.");
			}
		break;
		case 153149:
			c.getPA().startTeleport(3179, 3408, 0, "modern"); // Skilling Zone
		break;
		case 153150:
		if (c.npcKills > 499) {
		c.getPA().startTeleport(2586, 9449, 4, "modern"); // Torva Zone 2
		} else if (c.npcKills < 500){
			c.sendMessage("You need at least 500 ::kc to get access to the Torva Zone Floor 2.");
		}
		break;
		case 153151:
				c.getDH().sendOption2("I know I can lose my stuff when I die here", "Nevermind"); //Abyssal Demons
				c.dialogueAction = 11171;
		break;
		case 153152:
			if (c.ironGambler >= 1) {
			c.getPA().startTeleport(2387, 3488, 4, "modern"); // shops
			} else 
				c.sendMessage("This zone is exclusively for Iron Beast or Hardcore Iron Beast.");
			break;
		case 161005:
			c.getPA().sendFrame126("www.Pandemic.com", 12000);
			break;
		case 161006:
			c.getPA().sendFrame126("http://Pandemic.com/vote", 12000);
			c.sendMessage("Type ::auth (code) after voting.");
			break;
		case 161007:
			c.getPA().sendFrame126("http://Pandemic.com/donate", 12000);
			c.sendMessage("Relog after donating to claim your reward.");
			break;
		case 161022:
			c.getPA().sendFrame126("www.Pandemic.com", 12000);
			break;
		case 161021:
			RequestHelp.callForHelp(c);
			//AchievementManager.increase(c, Achievements.HELPME);
			break;
		case 161020:
			c.sendMessage("There are currently<col=255> " + PlayerHandler.getPlayerCount() +" <col=0>players online");
			if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3) {
				c.getPA().showInterface(19350);
				c.getPA().refreshPlayersInterface();
			}
			break;

		case 160242:
			c.setSidebarInterface(2, 41250);
			c.getPA().sendFrame126("@red@Owner: @cya@Cyantic @cya@Musicc", 41215);
			break;
		case 160245:
			c.setSidebarInterface(2, 41300);
			c.getPA().sendFrame126("@red@Owner: @cya@Cyantic @cya@Musicc", 41215);
			c.getPA().sendFrame126("@or1@1B Tickets killed: " + c.glacorKilled + "", 41227);
			c.getPA().sendFrame126("@or1@2B Tickets killed: " + c.avatarKilled + "", 41228);
			c.getPA().sendFrame126("@or1@Ticket Bosses killed: " + c.barrelKilled + "", 41229);
			c.getPA().sendFrame126("@or1@Corporeal Beast killed: " + c.ForgottenWarriorKilled + "", 41230);
			c.getPA().sendFrame126("@or1@Vorago killed: " + c.MadMummyKilled + "", 41231);
			c.getPA().sendFrame126("@or1@Loyalty Points: " + c.LoyaltyPoints + "", 41232);
			c.getPA().sendFrame126("@or1@Dice duel ratio: @gre@W: " + c.barbPoints + " @red@L: " + c.agilitypoints + "",
					41233);
			break;
		case 160248:
			c.setSidebarInterface(2, 41350);
			c.getPA().sendFrame126("@red@Owner: @cya@Cyantic @cya@Musicc", 41215);
			c.getPA().sendFrame126("@or1@Playername: " + c.playerName + "", 41227);
			c.getPA().sendFrame126("@or1@Donator points: " + c.donatorChest + "", 41228);
			c.getPA().sendFrame126("@or1@Loyalty points: " + c.LoyaltyPoints + "", 41229);
			break;
		/*case 160248:
			AchievementManager.writeInterfaces(c);
			break;*/
		case 160251:
			c.setSidebarInterface(2, 41400);
			c.getPA().sendFrame126("@red@Owner: @cya@Cyantic @cya@Musicc", 41215);
			c.getPA().sendFrame126("@or1@Online Players", 41236);
			c.getPA().sendFrame126("@or1@Call for help", 41237);
			c.getPA().sendFrame126("", 41239);
			break;
		// achievementshop

		case 171227:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 1500) {
				c.achievementPoints -= 1500;
				c.getItems().addItem(11144, 1);
			} else {
				c.sendMessage("You need atleast 1500 Achievement Points to use this feature.");
			}
			break;
		case 171229:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 500) {
				c.achievementPoints -= 500;
				c.getItems().addItem(11142, 1);
			} else {
				c.sendMessage("You need atleast 500 Achievement Points to use this feature.");
			}
			break;
		case 171231:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 500) {
				c.achievementPoints -= 500;
				c.getItems().addItem(11147, 1);
			} else {
				c.sendMessage("You need atleast 500 Achievement Points to use this feature.");
			}
			break;
		case 171233:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 500) {
				c.achievementPoints -= 500;
				c.getItems().addItem(11145, 1);
			} else {
				c.sendMessage("You need atleast 500 Achievement Points to use this feature.");
			}
			break;
		case 171235:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 500) {
				c.achievementPoints -= 500;
				c.getItems().addItem(11146, 1);
			} else {
				c.sendMessage("You need atleast 500 Achievement Points to use this feature.");
			}
			break;
		case 171237:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 500) {
				c.achievementPoints -= 500;
				c.getItems().addItem(11143, 1);
			} else {
				c.sendMessage("You need atleast 500 Achievement Points to use this feature.");
			}
			break;
		case 171239:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 1000) {
				c.achievementPoints -= 1000;
				c.getItems().addItem(20091, 1);
			} else {
				c.sendMessage("You need atleast 1000 Achievement Points to use this feature.");
			}
			break;
		case 171241:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 1000) {
				c.achievementPoints -= 1000;
				c.getItems().addItem(20095, 1);
			} else {
				c.sendMessage("You need atleast 1000 Achievement Points to use this feature.");
			}
			break;
		case 171243:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 1000) {
				c.achievementPoints -= 1000;
				c.getItems().addItem(20094, 1);
			} else {
				c.sendMessage("You need atleast 1000 Achievement Points to use this feature.");
			}
			break;
		case 171245:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 1000) {
				c.achievementPoints -= 1000;
				c.getItems().addItem(20092, 1);
			} else {
				c.sendMessage("You need atleast 1000 Achievement Points to use this feature.");
			}
			break;
		case 171247:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 1000) {
				c.achievementPoints -= 1000;
				c.getItems().addItem(20096, 1);
			} else {
				c.sendMessage("You need atleast 1000 Achievement Points to use this feature.");
			}
			break;
		case 171249:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 1000) {
				c.achievementPoints -= 1000;
				c.getItems().addItem(20081, 1);
			} else {
				c.sendMessage("You need atleast 1000 Achievement Points to use this feature.");
			}
			break;
		case 171251:
			c.sendMessage("Santa hats are currently disabled.");
			break;
		case 171253:
			c.sendMessage("Santa hats are currently disabled.");
			break;
		case 171255:
			c.sendMessage("Santa hats are currently disabled.");
			break;
		case 172001:
			c.sendMessage("Santa hats are currently disabled.");
			break;
		case 172003:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 1000) {
				c.achievementPoints -= 1000;
				c.getItems().addItem(20080, 1);
			} else {
				c.sendMessage("You need atleast 1000 Achievement Points to use this feature.");
			}
			break;
		case 172005:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.achievementPoints >= 1000) {
				c.achievementPoints -= 1000;
				c.getItems().addItem(20082, 1);
			} else {
				c.sendMessage("You need atleast 1000 Achievement Points to use this feature.");
			}
			break;
		// new teleporting menus
		case 176149: // Barrows
			c.getPA().startTeleport(3565, 3313, 0, "modern");
			break;
		case 176164: // Nomad&Angry goblin
			c.getPA().startTeleport(3077, 3504, 0, "modern");
			break;
		case 178037: // FUNPK
			c.getPA().startTeleport(2605, 3153, 0, "modern"); // original
			break;
		case 178040: // FUNPK
			//AchievementManager.increase(c, Achievements.VENTURE);
			c.getPA().startTeleport(3035, 3699, 0, "modern"); // original
			break;
		case 176152: // Pest Control
			// c.getPA().startTeleport(2658, 2660, 0, "modern");
			c.sendMessage("PC Is Currently Broken, Will Fix It Asap.");
			c.sendMessage("-Flamehax.");
			break;
		case 176155: // Tzhaar
			c.getPA().startTeleport(2445, 5177, 0, "modern");
			break;
		case 142151:
			if (System.currentTimeMillis() - lastDelay >= 2500) {
				lastDelay = System.currentTimeMillis();
				c.PokeMini = 0;
				c.getPA().closeAllWindows();
				//AchievementManager.increase(c, Achievements.CATCHEMALL);
				c.getPA().startTeleport(2938, 3282, 0, "modern");
				c.PokeMini += 1;
			}
			break;
		case 176158: // Warriors Guild
			c.getPA().startTeleport(2854, 3543, 0, "modern");
			break;
		case 176161: // Assault
			c.getPA().startTeleport(3186, 9758, 0, "modern");

			break;

		// NewInterfaceForTitles
		case 222169:
			if (c.achievement[2] >= 1) {
				c.playerTitle = 41;
				c.sendMessage("You Apply The Title 'Not Afraid' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Not Afraid' To Equip this title");
			}
			break;
		case 222171:
			if (c.achievement[8] >= 100) {
				c.playerTitle = 42;
				c.sendMessage("You Apply The Title 'Veteran' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Veteran' To Equip this title");
			}
			break;
		case 222174:
			if (c.achievement[11] >= 1) {
				c.playerTitle = 43;
				c.sendMessage("You Apply The Title 'Cursed' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Cursed' To Equip this title");
			}
			break;
		case 222176:
			if (c.achievement[12] >= 1) {
				c.playerTitle = 44;
				c.sendMessage("You Apply The Title 'The Original' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'The Original' To Equip this title");
			}
			break;
		case 222178:
			if (c.achievement[13] >= 1) {
				c.playerTitle = 45;
				c.sendMessage("You Apply The Title 'Modern' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Modern' To Equip this title");
			}
			break;
		case 222180:
			if (c.achievement[14] >= 1) {
				c.playerTitle = 46;
				c.sendMessage("You Apply The Title 'Ancient' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Ancient' To Equip this title");
			}
			break;
		case 222182:
			if (c.achievement[15] >= 1) {
				c.playerTitle = 47;
				c.sendMessage("You Apply The Title 'The Wise' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'The Wise' To Equip this title");
			}
			break;
		case 222184:
			if (c.achievement[16] >= 1) {
				c.playerTitle = 48;
				c.sendMessage("You Apply The Title 'Priest' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Priest' To Equip this title");
			}
			break;
		case 222186:
			c.getPA().showInterface(57500);
			break;
		case 224157:
			if (c.achievement[18] >= 1) {
				c.playerTitle = 49;
				c.sendMessage("You Apply The Title 'Guitarist' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Guitarist' To Equip this title");
			}
			break;
		case 224159:
			if (c.achievement[23] >= 100) {
				c.playerTitle = 50;
				c.sendMessage("You Apply The Title 'Yak Hunter' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Yak Hunter' To Equip this title");
			}
			break;
		case 224162:
			if (c.achievement[24] >= 200) {
				c.playerTitle = 51;
				c.sendMessage("You Apply The Title 'Rockcrab Hunter' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Rockcrab Hunter' To Equip this title");
			}
			break;
		case 224164:
			if (c.achievement[25] >= 30) {
				c.playerTitle = 52;
				c.sendMessage("You Apply The Title 'Vesbeast Slayer' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Vesbeast Slayer' To Equip this title");
			}
			break;
		case 224166:
			if (c.achievement[26] >= 50) {
				c.playerTitle = 53;
				c.sendMessage("You Apply The Title 'Patience' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Patience To Equip this title");
			}
			break;
		case 224168:
			if (c.achievement[27] >= 1) {
				c.playerTitle = 54;
				c.sendMessage("You Apply The Title 'Summoner' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Summoner' To Equip this title");
			}
			break;
		case 224170:
			if (c.achievement[28] >= 1) {
				c.playerTitle = 55;
				c.sendMessage("You Apply The Title 'Custom' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Custom' To Equip this title");
			}
			break;
		case 224172:
			if (c.achievement[30] >= 1000) {
				c.playerTitle = 56;
				c.sendMessage("You Apply The Title 'PIIIKAAA' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'PIIIKAAA' To Equip this title");
			}
			break;
		case 224174:
			c.getPA().showInterface(58000);
			break;
		case 226145:
			if (c.achievement[32] >= 50) {
				c.playerTitle = 58;
				c.sendMessage("You Apply The Title 'Tormentor' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Tormentor' To Equip this title");
			}
			break;
		case 226147:
			if (c.achievement[33] >= 15) {
				c.playerTitle = 59;
				c.sendMessage("You Apply The Title 'Pokemon Trainer' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Pokemon Trainer' To Equip this title");
			}
			break;
		case 226150:
			if (c.achievement[34] >= 200) {
				c.playerTitle = 60;
				c.sendMessage("You Apply The Title 'Pokemon Killer' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Pokemon Killer' To Equip this title");
			}
			break;

		case 226152:
			if (c.achievement[31] >= 1) {
				c.playerTitle = 57;
				c.sendMessage("You Apply The Title 'Adventurer' ");
			} else {
				c.sendMessage("You Must Complete The Achievement 'Adventurer' To Equip this title");
			}
			break;
		case 226154:
			if (c.prestigeLevel == 1) {
				c.playerTitle = 61;
				c.sendMessage("You Apply The Title 'Prestige 1' ");
			} else if (c.prestigeLevel == 2) {
				c.playerTitle = 62;
				c.sendMessage("You Apply The Title 'Prestige 2' ");
			} else if (c.prestigeLevel == 3) {
				c.playerTitle = 63;
				c.sendMessage("You Apply The Title 'Prestige 3' ");
			} else if (c.prestigeLevel == 4) {
				c.playerTitle = 64;
				c.sendMessage("You Apply The Title 'Prestige 4' ");
			} else if (c.prestigeLevel == 5) {
				c.playerTitle = 65;
				c.sendMessage("You Apply The Title 'Prestige 5' ");
			} else if (c.prestigeLevel == 6) {
				c.playerTitle = 66;
				c.sendMessage("You Apply The Title 'Prestige 6' ");
			} else if (c.prestigeLevel == 7) {
				c.playerTitle = 67;
				c.sendMessage("You Apply The Title 'Prestige 7' ");
			} else if (c.prestigeLevel == 8) {
				c.playerTitle = 68;
				c.sendMessage("You Apply The Title 'Prestige 8' ");
			} else if (c.prestigeLevel == 9) {
				c.playerTitle = 69;
				c.sendMessage("You Apply The Title 'Prestige 9' ");
			} else if (c.prestigeLevel == 10) {
				c.playerTitle = 70;
				c.sendMessage("You Apply The Title 'Prestige 10' ");
			} else {
				c.sendMessage("You Must Prestige atleast once to get a title.");
			}

			break;
		case 226156:
		case 226158:
		case 226160:
			c.sendMessage("Coming Soon");
			break;
		case 226162:
			c.getPA().showInterface(57000);
			break;

		// AchievementManager.increase(c, Achievements.VENTURE);
		// AchievementManager.increase(c, Achievements.NOTAFRAID); venbeast
		// endthat
		// BOSS TELEPORTS
		case 164023:
			c.getPA().startTeleport(2271, 4681, 0, "modern"); // KBD
			break;
		case 176167:
		case 178055:
		case 177211:
			c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151
					: c.playerMagicBook == 1 ? 12855 : c.playerMagicBook == 2 ? 29999 : 12855);
			c.dialogueAction = -1;
			break;
		case 164029:
			c.getPA().startTeleport(3234, 9368, 0, "modern"); // torm demons
			break;
		case 164025:
			c.getPA().startTeleport(3293, 9374, 0, "modern"); // corp beast
			c.sendMessage("Warning: You may need a team.");
			break;
		case 164039:
			// AchievementManager.increase(c, Achievements.VENTURE);
			c.getPA().startTeleport(3035, 3699, 0, "modern"); // no npc in wild
		case 178043:
			c.getPA().startTeleport(2993, 3599, 0, "modern"); // green dragons
			break;

		// case 164021: //nex
		// c.getPA().showInterface(14040);
		// godwars
		// c.sendMessage("If there is no floor/maps are gone RESTART CLIENT!");
		// c.getPA().spellTeleport(2882, 5310, 2);
		// c.getPA().spellTeleport(2909, 3613, 0);
		// //c.getPA().sendMp3("Music");
		// c.getDH().sendDialogues(5555, 1);
		// c.sendMessage("Nex currently disabled / will be back soon!");
		// break;

		case 164041:
			c.getPA().showInterface(45000);
			// next interface 45000
			break;

		case 164027:
			c.getPA().startTeleport(3467, 9495, 0, "modern"); // K QUEEN
			break;
		case 175209:
			//AchievementManager.increase(c, Achievements.NOTAFRAID);
			c.getPA().startTeleport(3037, 5234, 0, "modern"); // ventige corp
																// beast
			break;
		case 175203:
			c.getPA().startTeleport(3055, 5185, 0, "modern"); // ventige wrecker
			break;

		case 175205:
			c.sendMessage("Coming soon!");
			break;
		case 175207:
			c.sendMessage("Coming soon!");
			break;
		case 175211:
			c.sendMessage("Coming soon!");
			break;

		case 164019:
			c.getPA().startTeleport(2881, 5310, 2, "modern"); // godwars
			break;
		case 164033:
			c.getPA().startTeleport(2368, 4956, 0, "modern"); // barrelchest
			break;
		case 164037:
			c.getPA().startTeleport(1761, 5196, 0, "modern"); // giant mole
			break;
		case 164035:
			c.getPA().startTeleport(2517, 3044, 0, "modern"); // forgotten
																// warrior
																// (primal)
			break;
		case 164031:
			c.getPA().startTeleport(2962, 9631, 0, "modern"); // barebones/mummy
			break;
		case 164021:
			c.getDH().sendDialogues(5555, 1);
			break;
		// case 164019:
		// c.getPA().startTeleport(3037, 5234, 0, "modern");// vesbeast green
		// c.sendMessage("VesBeast is extremely dangerous.");
		// AchievementManager.increase(c, Achievements.NOTAFRAID);
		// break;
		// END OF BOSS TELE
		// Player Handling Panel, points, login interface etc
		case 73188:// PLAY
			c.getPA().closeAllWindows();
			c.sendMessage("Welcome to " + Config.SERVER_NAME + "! If you need help contact a staff member!");
			break;
		case 71032:// DONATE
			c.getPA().sendFrame126("www.Pandemic.com/donate", 12000);
			c.sendMessage("Thank you for clicking, this keeps the server alive!");
			break;
		case 71029:// VOTE
			c.getPA().sendFrame126("www.Pandemic.com/vote", 12000);
			c.sendMessage("Thanks for Voting, this will promote our server!");
			break;

		// DungeoneeringButtons

		case 154078:
			c.getPA().spellTeleport(2986, 9631, 0);
			c.sendMessage("You have selected Cave One! - THIS ISNT A SAFE AREA.");
			break;
		case 154080:
			if (c.playerLevel[24] >= 50) {
				c.getPA().spellTeleport(2716, 9515, 0);
				c.sendMessage("You have selected Cave Two! - THIS ISNT A SAFE AREA.");
			} else if (c.playerLevel[24] < 50) {
				c.sendMessage("You need atleast 50 Dungeoneering to enter this cave");
				return;
			}
			break;
		case 154082:
			if (c.playerLevel[24] >= 75) {
				c.getPA().spellTeleport(2488, 3045, 0);
				c.sendMessage("You have selected Cave Three! - THIS ISNT A SAFE AREA.");
			} else if (c.playerLevel[24] < 75) {
				c.sendMessage("You need atleast 75 Dungeoneering to enter this cave");
				return;
			}
			break;
		case 154084:
			if (c.playerLevel[24] >= 99) {
				c.getPA().spellTeleport(3416, 3115, 0);
				c.sendMessage("You have selected the boss cave! - THIS ISNT A SAFE AREA.");
			} else if (c.playerLevel[24] < 99) {
				c.sendMessage("You need atleast 99 Dungeoneering to enter this cave");
				return;
			}
			break;

		// commands
		case 65073:
			c.getPA().showInterface(36000);
			break;
		// end
		// moreskillsinterfacebuttons
		case 126248:
			c.getPA().showInterface(27000);
			break;
		case 126249:
			c.getPA().spellTeleport(3448, 3517, 0);
			c.sendMessage("You have teleported to the Summoning shops!");
			break;
		case 126250:
			c.getPA().spellTeleport(2529, 3305, 0);
			break;
		case 126251:
			c.getPA().spellTeleport(2710, 3462, 0);
			break;
		case 126252:
			c.getPA().spellTeleport(3233, 2910, 0);
			break;
		case 126253:
			c.getPA().closeAllWindows();
			break;
		// endofinterface
		case 71026:// WEBSITE
			c.getPA().sendFrame126("www.Pandemic.com", 12000);
			break;
		case 65064:
			c.getPA().showInterface(17330);
			break;
		case 64052:
			c.getShops().openShop(18);
			break;
		case 65067:
			if (c.inWild() || c.pkSafe()) {
				c.sendMessage("You must be outside wilderness to use this!");
				return;
			} else {
				c.getPA().showInterface(16430);
			}
			break;
		case 65070:
			if (c.inWild() || c.pkSafe()) {
				c.sendMessage("You must be outside wilderness to use this!");
				return;
			} else {
				c.getPA().showInterface(12000);
			}
			break;
		case 63106:
			if (c.xpLock == false) {
				c.xpLock = true;
				c.sendMessage("Your XP is now LOCKED!");
			} else {
				c.xpLock = false;
				c.sendMessage("Your XP is now UNLOCKED!");
			}
			break;

		case 63109:
			c.getPA().showInterface(22595);
			c.sendMessage("Change your password here, Make sure its not easy to guess");
			c.sendMessage("We would like for you to use this on a weekly basis. Thank you.");
			break;

		// customsshops2
		case 167251:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 750) {
				c.customPoints -= 750;
				c.getItems().addItem(19929, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 750 Custom Points to use this feature.");
			}
			break;
		case 167253:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 1000) {
				c.customPoints -= 1000;
				c.getItems().addItem(19956, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 1000 Custom Points to use this feature.");
			}
			break;
		case 167255:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 500) {
				c.customPoints -= 500;
				c.getItems().addItem(9897, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 500 Custom Points to use this feature.");
			}
			break;
		case 168001:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 250) {
				c.customPoints -= 250;
				c.getItems().addItem(9895, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 250 Custom Points to use this feature.");
			}
			break;
		case 168003:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 10000) {
				c.customPoints -= 10000;
				c.getItems().addItem(938, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 10,000 Custom Points to use this feature.");
			}
			break;
		case 168005:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 8000) {
				c.customPoints -= 8000;
				c.getItems().addItem(938, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 8,000 Custom Points to use this feature.");
			}
			break;
		case 168007:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 15000) {
				c.customPoints -= 15000;
				c.getItems().addItem(968, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 15,000 Custom Points to use this feature.");
			}
			break;
		case 168009:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 8000) {
				c.customPoints -= 8000;
				c.getItems().addItem(930, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 8,000 Custom Points to use this feature.");
			}
			break;
		case 168011:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 12000) {
				c.customPoints -= 12000;
				c.getItems().addItem(934, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 12,000 Custom Points to use this feature.");
			}
			break;
		case 168013:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 10000) {
				c.customPoints -= 10000;
				c.getItems().addItem(1000, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 10,000 Custom Points to use this feature.");
			}
			break;
		case 168015:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 12000) {
				c.customPoints -= 12000;
				c.getItems().addItem(933, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 12,000 Custom Points to use this feature.");
			}
			break;
		case 168017:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 20000) {
				c.customPoints -= 20000;
				c.getItems().addItem(7783, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 20,000 Custom Points to use this feature.");
			}
			break;
		case 168019:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 30000) {
				c.customPoints -= 30000;
				c.getItems().addItem(13347, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 30,000 Custom Points to use this feature.");
			}
			break;
		case 168021:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 10420) {
				c.customPoints -= 10420;
				c.getItems().addItem(11631, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 10,420 Custom Points to use this feature.");
			}
			break;
		case 168023:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 6000) {
				c.customPoints -= 6000;
				c.getItems().addItem(11610, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 6,000 Custom Points to use this feature.");
			}
			break;
		case 168025:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 20000) {
				c.customPoints -= 20000;
				c.getItems().addItem(11607, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 20,000 Custom Points to use this feature.");
			}
			break;
		case 168027:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 20000) {
				c.customPoints -= 20000;
				c.getItems().addItem(11608, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 20,000 Custom Points to use this feature.");
			}
			break;
		case 168029:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 20000) {
				c.customPoints -= 20000;
				c.getItems().addItem(11611, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 20,000 Custom Points to use this feature.");
			}
			break;
		// customshop
		case 21134:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 15000) {
				c.customPoints -= 15000;
				c.getItems().addItem(9396, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 15,000 Custom Points to use this feature.");
			}
			break;
		case 21130:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 5000) {
				c.customPoints -= 5000;
				c.getItems().addItem(18743, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 5,000 Custom Points to use this feature.");
			}
			break;
		case 21132:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 1000) {
				c.customPoints -= 1000;
				c.getItems().addItem(13824, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 1,000 Custom Points to use this feature.");
			}
			break;
		case 21136:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 5000) {
				c.customPoints -= 5000;
				c.getItems().addItem(9374, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 5,000 Custom Points to use this feature.");
			}
			break;
		case 21138:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 10000) {
				c.customPoints -= 10000;
				c.getItems().addItem(19930, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 10,000 Custom Points to use this feature.");
			}
			break;
		case 21140:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 7000) {
				c.customPoints -= 7000;
				c.getItems().addItem(19958, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 7,000 Custom Points to use this feature.");
			}
			break;
		case 21142:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 10000) {
				c.customPoints -= 10000;
				c.getItems().addItem(19936, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 10,000 Custom Points to use this feature.");
			}
			break;
		case 21144:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 5000) {
				c.customPoints -= 5000;
				c.getItems().addItem(19957, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 5,000 Custom Points to use this feature.");
			}
			break;
		case 21146:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 7000) {
				c.customPoints -= 7000;
				c.getItems().addItem(19753, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 7,000 Custom Points to use this feature.");
			}
			break;
		case 21148:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 12000) {
				c.customPoints -= 12000;
				c.getItems().addItem(9899, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 12,000 Custom Points to use this feature.");
			}
			break;
		case 21150:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 15000) {
				c.customPoints -= 15000;
				c.getItems().addItem(11660, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 15,000 Custom Points to use this feature.");
			}
			break;
		case 21152:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 15000) {
				c.customPoints -= 15000;
				c.getItems().addItem(11662, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 15,000 Custom Points to use this feature.");
			}
			break;
		case 21154:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 15000) {
				c.customPoints -= 15000;
				c.getItems().addItem(11661, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 15,000 Custom Points to use this feature.");
			}
			break;
		case 21156:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 8000) {
				c.customPoints -= 8000;
				c.getItems().addItem(939, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 8,000 Custom Points to use this feature.");
			}
			break;
		case 21158:
			if (c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need atleast 1 free slot to use this feature.");
			} else if (c.customPoints >= 10000) {
				c.customPoints -= 10000;
				c.getItems().addItem(14390, 1);
				//AchievementManager.increase(c, Achievements.CUS);
			} else {
				c.sendMessage("You need atleast 10,000 Custom Points to use this feature.");
			}
			break;

		// end

		// Purchase Skillcapes
		case 115198:// Summoning
			if (c.playerLevel[22] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(12169, 1);
				c.getItems().addItem(12170, 1);
				c.getItems().addItem(12171, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115201:// construction
			if (c.playerLevel[23] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9789, 1);
				c.getItems().addItem(9790, 1);
				c.getItems().addItem(9791, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115126:// fishing
			if (c.playerLevel[10] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9798, 1);
				c.getItems().addItem(9799, 1);
				c.getItems().addItem(9800, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115129:// fletching
			if (c.playerLevel[9] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9783, 1);
				c.getItems().addItem(9784, 1);
				c.getItems().addItem(9785, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115132:// firemaking
			if (c.playerLevel[11] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9804, 1);
				c.getItems().addItem(9805, 1);
				c.getItems().addItem(9806, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115135:// herblore
			if (c.playerLevel[15] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9774, 1);
				c.getItems().addItem(9775, 1);
				c.getItems().addItem(9776, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115141:// Hunter
			if (c.playerLevel[21] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9948, 1);
				c.getItems().addItem(9949, 1);
				c.getItems().addItem(9950, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115138:// hitpoints
			if (c.playerLevel[3] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9768, 1);
				c.getItems().addItem(9769, 1);
				c.getItems().addItem(9770, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115144:// Magic
			if (c.playerLevel[6] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9762, 1);
				c.getItems().addItem(9763, 1);
				c.getItems().addItem(9764, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115147:// Mining
			if (c.playerLevel[14] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9792, 1);
				c.getItems().addItem(9793, 1);
				c.getItems().addItem(9794, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115150:// Prayer
			if (c.playerLevel[5] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9759, 1);
				c.getItems().addItem(9760, 1);
				c.getItems().addItem(9761, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115153:// Quest
			c.sendMessage("Sorry, This cape is currently unavailable.");
			break;
		case 115156:// Range
			if (c.playerLevel[4] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9756, 1);
				c.getItems().addItem(9757, 1);
				c.getItems().addItem(9758, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115159:// Runecrafting
			if (c.playerLevel[20] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9765, 1);
				c.getItems().addItem(9766, 1);
				c.getItems().addItem(9767, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115162:// Slayer
			if (c.playerLevel[18] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9786, 1);
				c.getItems().addItem(9787, 1);
				c.getItems().addItem(9788, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115165:// Smithing
			if (c.playerLevel[13] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9795, 1);
				c.getItems().addItem(9796, 1);
				c.getItems().addItem(9797, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115168:// Strength
			if (c.playerLevel[2] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9750, 1);
				c.getItems().addItem(9751, 1);
				c.getItems().addItem(9752, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115171:// Theiving
			if (c.playerLevel[17] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9777, 1);
				c.getItems().addItem(9778, 1);
				c.getItems().addItem(9779, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115174:// Wcing
			if (c.playerLevel[8] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9807, 1);
				c.getItems().addItem(9808, 1);
				c.getItems().addItem(9809, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115177:// Agility
			if (c.playerLevel[16] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9771, 1);
				c.getItems().addItem(9772, 1);
				c.getItems().addItem(9773, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115180:// Attack
			if (c.playerLevel[0] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9747, 1);
				c.getItems().addItem(9748, 1);
				c.getItems().addItem(9749, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115183:// dung
			if (c.playerLevel[24] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape's!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(18508, 1);
				c.getItems().addItem(18509, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115186:// Cooking
			if (c.playerLevel[7] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9801, 1);
				c.getItems().addItem(9802, 1);
				c.getItems().addItem(9803, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115189:// crafting
			if (c.playerLevel[12] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9780, 1);
				c.getItems().addItem(9781, 1);
				c.getItems().addItem(9782, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115192:// Defence
			if (c.playerLevel[1] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9753, 1);
				c.getItems().addItem(9754, 1);
				c.getItems().addItem(9755, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		case 115195:// Farming
			if (c.playerLevel[19] < 99) {
				c.sendMessage("You Need 99 in this skill for the cape!");
			} else if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("You need atleast 3 free slot's to use this feature.");
			} else if (c.getItems().playerHasItem(995, 99000)) {
				c.getItems().deleteItem2(995, 99000);
				c.getItems().addItem(9810, 1);
				c.getItems().addItem(9811, 1);
				c.getItems().addItem(9812, 1);
			} else {
				c.sendMessage("You need atleast 99,000 coins to use this feature.");
			}
			break;
		// Runecrafting Start
		case 105122: // Air
			c.getPA().movePlayer(2846, 4834, 0);
			c.getPA().closeAllWindows();
			break;
		case 105123: // Mind
			c.getPA().movePlayer(2786, 4838, 0);
			c.getPA().closeAllWindows();
			break;
		case 105124: // Water
			c.getPA().movePlayer(2713, 4836, 0);
			c.getPA().closeAllWindows();
			break;
		case 105125: // Earth
			c.getPA().movePlayer(2660, 4839, 0);
			c.getPA().closeAllWindows();
			break;
		case 105126: // Fire
			c.getPA().movePlayer(2584, 4836, 0);
			c.getPA().closeAllWindows();
			break;
		case 105127: // Cosmic
			c.getPA().movePlayer(2162, 4833, 0);
			c.getPA().closeAllWindows();
			break;
		case 105128: // Chaos
			c.getPA().movePlayer(2268, 4842, 0);
			c.getPA().closeAllWindows();
			break;
		case 105129: // Astral
			c.getPA().movePlayer(2160, 3862, 0);
			c.getPA().closeAllWindows();
			break;
		case 105130: // Nature
			c.getPA().movePlayer(2397, 4841, 0);
			c.getPA().closeAllWindows();
			break;
		case 105131: // Law
			c.getPA().movePlayer(2464, 4834, 0);
			c.getPA().closeAllWindows();
			break;
		case 105132: // Death
			c.getPA().movePlayer(2205, 4834, 0);
			c.getPA().closeAllWindows();
			break;
		case 105133: // Blood
			c.getPA().movePlayer(3498, 3571, 0);
			c.getPA().closeAllWindows();
			break;
		case 105134: // Close Button
			c.getPA().closeAllWindows();
			break;
		// Runecrafting End
		// Monster Teleports Start
		case 132211:
			c.getDH().sendOption2("Yak's (Neitznot)", "Rock Crabs");
			c.dialogueAction = 222;
			// c.getPA().spellTeleport(2676, 3715, 0);
			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151); // modern
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855); // ancient
			} else {
				c.setSidebarInterface(6, 16640);
			}
			break;
		case 132214:
			c.getPA().spellTeleport(2884, 9798, 0);

			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151); // modern
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855); // ancient
			} else {
				c.setSidebarInterface(6, 16640);
			}
			break;
		case 132217:
			c.getPA().spellTeleport(3428, 3537, 0);

			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151); // modern
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855); // ancient
			} else {
				c.setSidebarInterface(6, 16640);
			}
			break;
		case 132220:
			c.getPA().spellTeleport(2710, 9466, 0);
			c.sendMessage("You have teleported to Brimhaven Dungeon, be sure to bring an antifire-shield!");

			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151); // modern
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855); // ancient
			} else {
				c.setSidebarInterface(6, 16640); // Lunar
			}
			break;

		case 132227:
			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151); // modern
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855); // ancient
			} else {
				c.setSidebarInterface(6, 16640); // Lunar
			}
			break;

		case 132230:
			c.setSidebarInterface(6, 39000);
			break;

		case 152099:
			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151); // modern
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855); // ancient
			} else {
				c.setSidebarInterface(6, 16640); // Lunar
			}
			break;
		case 152102:
			c.setSidebarInterface(6, 34000);
			break;

		case 152091:
			c.getPA().spellTeleport(2706, 9450, 0);// Hillys

			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151); // modern
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855); // ancient
			} else {
				c.setSidebarInterface(6, 16640);
			}
			break;

		case 152094:
			if ((c.playerLevel[22] < 90) && (c.playerLevel[16] < 90)) {
				c.sendMessage("You need 90 Agility And 90 Hunter to enter the Strykworm's Cave");
			} else {
				if ((c.playerLevel[22] > 89) && (c.playerLevel[16] < 90)) {
					c.sendMessage("You need 90 Hunter And Agility to enter the Strykworm's Cave");
				} else {
					if ((c.playerLevel[22] < 90) && (c.playerLevel[16] > 89)) {
						c.sendMessage("You need 90 Hunter And Agility to enter the Strykworm's Cave");
					} else {
						if ((c.playerLevel[22] > 89) && (c.playerLevel[16] > 89)) {
							c.getPA().startTeleport(2515, 4632, 0, "modern");
							c.sendMessage("A sense of nervousness fills your body..");
							c.sendMessage("you find yourself in a mystery cave!");
						}
					}
				}
			}
			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151); // modern
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855); // ancient
			} else {
				c.setSidebarInterface(6, 16640);
			}
			break;
		// Monster Teleports end
		// New loyalty Shop
		case 39234:
			c.sendMessage("Currently Unavailable, Sorry.");
			break;
		case 39232:
			if (c.LoyaltyPoints >= 2000) {
				c.getItems().addItem(13493, 1);
				c.sendMessage("You Purchase the Aura! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 2000;
			} else {
				c.sendMessage("You Need 2000 Loyalty Points for this (Play some more)");
			}
			break;
		case 39230:
			if (c.LoyaltyPoints >= 3000) {
				c.getItems().addItem(13495, 1);
				c.sendMessage("You Purchase the Aura! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 3000;
			} else {
				c.sendMessage("You Need 3000 Loyalty Points for this (Play some more)");
			}
			break;
		case 39228:
			if (c.LoyaltyPoints >= 3000) {
				c.getItems().addItem(13492, 1);
				c.sendMessage("You Purchase the Aura! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 3000;
			} else {
				c.sendMessage("You Need 3000 Loyalty Points for this (Play some more)");
			}
			break;
		case 43210:
			if (c.LoyaltyPoints >= 7000) {
				c.getItems().addItem(13221, 1);
				c.getItems().addItem(13225, 1);
				c.getItems().addItem(14445, 1);
				c.sendMessage("You Purchase the Minecraft Armour! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 7000;
			} else {
				c.sendMessage("You Need 7000 Loyalty Points for this (Play some more)");
			}
			break;
		case 43206:
			if (c.LoyaltyPoints >= 3000) {
				c.getItems().addItem(1050, 1);
				c.sendMessage("You Purchase the Santa Hat Set! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 3000;
			} else {
				c.sendMessage("You Need 3000 Loyalty Points for this (Play some more)");
			}
			break;
		case 63090:
			if (c.LoyaltyPoints >= 1500) {
				c.getItems().addItem(12474, 1);
				c.sendMessage("You Purchase the Dragon! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 1500;
			} else {
				c.sendMessage("You Need 1500 Loyalty Points for this (Play some more)");
			}
			break;
		case 63086:
			if (c.LoyaltyPoints >= 1500) {
				c.getItems().addItem(12472, 1);
				c.sendMessage("You Purchase the Dragon! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 1500;
			} else {
				c.sendMessage("You Need 1500 Loyalty Points for this (Play some more)");
			}
			break;
		case 63088:
			if (c.LoyaltyPoints >= 1500) {
				c.getItems().addItem(12476, 1);
				c.sendMessage("You Purchase the Dragon! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 1500;
			} else {
				c.sendMessage("You Need 1500 Loyalty Points for this (Play some more)");
			}
			break;
		case 63084:
			if (c.LoyaltyPoints >= 1500) {
				c.getItems().addItem(12470, 1);
				c.sendMessage("You Purchase the Dragon! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 1500;
			} else {
				c.sendMessage("You Need 1500 Loyalty Points for this (Play some more)");
			}
			break;
		case 43202:
			c.getPA().showInterface(12000);
			break;
		case 59106:
			c.getPA().showInterface(12000);
			break;
		case 63082:
			c.getPA().showInterface(12000);
			break;
		case 39226:
			c.getPA().showInterface(12000);
			break;
		case 47186:
			c.sendMessage("Currently Disabled.");
			//c.getPA().showInterface(11000);
			break;
		case 47184:
			//c.getPA().showInterface(15000);
			c.sendMessage("Currently Disabled.");
			break;
		case 47182:
			// c.getPA().showInterface(16000);
			c.sendMessage("Currently Disabled.");
			break;
		case 47180:
			c.sendMessage("Currently Disabled.");
			//c.getPA().showInterface(10000);
			break;

		case 187130:
			AchievementManager.writeInterface(c);
			break;
		case 153099:
			c.setSidebarInterface(1, 48000);
			break;
		case 39295:
			c.getPA().startTeleport(2387, 3488, 0, "modern"); // shops
			break;
		case 39296:
			c.getPA().startTeleport(3561, 9948, 0, "modern"); // shops
			break;
		case 39297:
			c.getPA().startTeleport(3561, 9948, 4, "modern"); // shops
			break;
		case 39298:
			c.getPA().startTeleport(2917, 3628, 0, "modern"); // shops
		case 39299:
			if (c.getItems().playerHasItem(5021, 5000)) {
				c.getItems().addItem(15098, 1);
				c.getItems().deleteItem(5021, 5000);
			} else if (c.getItems().playerHasItem(4067, 2500)) {
				c.getItems().addItem(15098, 1);
				c.getItems().deleteItem(4067, 2500);
			}
			break;
		case 187132:
			c.getPA().startTeleport(3222, 3218, 0, "modern"); // shops
			c.sendMessage("You teleport to the Shops.");
			break;
		case 187217:
			if (c.diceBanned == 2) {
				c.sendMessage("Your account has been restricted from this zone.");
				return;
			}
			if (c.npcKills >= 100) {
			c.getPA().startTeleport(2863, 3546, 0, "modern"); // dicingzone
			c.sendMessage("<col=800000000><shad>DICE BAG DICE DUEL SCAMS WITHOUT A HELPER MIDDLEMAN WILL NOT GET REFUNDED.");
			} else {
				c.sendMessage("You need at least 100 NPC Kills to start gambling! Build up some bank first!");
			}
			break;
		case 72038:
			c.sendMessage("Ape Toll is not accessable.");
			break;

		// Staff Tab
		case 88056:
			RequestHelp.setInterface(c);
			//AchievementManager.increase(c, Achievements.HELPME);
			break;
		case 109114:
			//AchievementManager.increase(c, Achievements.HELPME);
			RequestHelp.callForHelp(c);
			break;
		// StafftabEND
		// loyalty Title on PlayerTab START
		case 113239:
			c.playerTitle = 0;
			c.sendMessage("You Have Reset your playertitle!");
			break;
		case 43204:
			if (c.LoyaltyPoints >= 20000) {
				c.getItems().addItem(19933, 1);
				c.sendMessage("You Purchase the Veteran Cape! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 20000;
			} else {
				c.sendMessage("You Need 20,000 Loyalty Points for this (Play some more)");
			}
			break;
		case 43208:
			if (c.LoyaltyPoints >= 3000) {
				c.getItems().addItem(1055, 1);
				c.sendMessage("You Purchase the H'ween! You now have " + c.LoyaltyPoints + " Points");
				c.LoyaltyPoints -= 3000;
			} else {
				c.sendMessage("You Need 3000 Loyalty Points for this (Play some more)");
			}
			break;
		case 59108:
			if (c.LoyaltyPoints >= 75) {
				c.playerTitle = 2;
				c.LoyaltyPoints -= 75;
				c.sendMessage("You Apply Sir, You now have " + c.LoyaltyPoints + "");
			} else {
				c.sendMessage("You Need 75 Loyalty Points for this (Play some more)");
			}
			break;
		case 59111:
			if (c.LoyaltyPoints >= 150) {
				c.playerTitle = 1;
				c.LoyaltyPoints -= 150;
				c.sendMessage("You Apply Lord, You now have " + c.LoyaltyPoints + "");
			} else {
				c.sendMessage("You Need 150 Loyalty Points for this (Play some more)");
			}
			break;
		case 59112:
			if (c.LoyaltyPoints >= 150) {
				c.playerTitle = 17;
				c.LoyaltyPoints -= 150;
				c.sendMessage("You Apply Lady, You now have " + c.LoyaltyPoints + "");
			} else {
				c.sendMessage("You Need 150 Loyalty Points for this (Play some more)");
			}
			break;
		case 59114:
			if (c.LoyaltyPoints >= 300) {
				c.playerTitle = 6;
				c.LoyaltyPoints -= 300;
				c.sendMessage("You Apply King, You now have " + c.LoyaltyPoints + "");
			} else {
				c.sendMessage("You Need 300 Loyalty Points for this (Play some more)");
			}
			break;
		case 59116:
			if (c.LoyaltyPoints >= 300) {
				c.playerTitle = 24;
				c.LoyaltyPoints -= 300;
				c.sendMessage("You Apply Queen, You now have " + c.LoyaltyPoints + "");
			} else {
				c.sendMessage("You Need 300 Loyalty Points for this (Play some more)");
			}
			break;
		case 59118:
			if (c.LoyaltyPoints >= 500) {
				c.playerTitle = 31;
				c.LoyaltyPoints -= 500;
				c.sendMessage("You Apply Master, You now have " + c.LoyaltyPoints + "");
			} else {
				c.sendMessage("You Need 500 Loyalty Points for this (Play some more)");
			}
			break;
		case 59120:
			if (c.LoyaltyPoints >= 1000) {
				c.playerTitle = 35;
				c.LoyaltyPoints -= 1000;
				c.sendMessage("You Apply Royal, You now have " + c.LoyaltyPoints + "");
			} else {
				c.sendMessage("You Need 1000 Loyalty Points for this (Play some more)");
			}
			break;
		case 59122:
			if (c.LoyaltyPoints >= 2000) {
				c.playerTitle = 34;
				c.LoyaltyPoints -= 2000;
				c.sendMessage("You Apply 1337, You now have " + c.LoyaltyPoints + "");
			} else {
				c.sendMessage("You Need 2000 Loyalty Points for this (Play some more)");
			}
			break;
		// PLAYERTABTITLES-END
		// Lodestone Teles
		case 102108:
			c.getPA().startTeleport(3213, 3424, 0, "modern");
			c.sendMessage("You Teleport to Varrock");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
			
		case 102111:
			c.getPA().startTeleport(3087, 3497, 0, "modern");
			c.sendMessage("You Teleport to EdgeVille");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102093:
			c.getPA().startTeleport(3222, 3218, 0, "modern");
			c.sendMessage("You Teleport to Lumbridge");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102132:
			c.getPA().startTeleport(2725, 3485, 0, "modern");
			c.sendMessage("You Teleport to Seers Village");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102126:
			c.getPA().startTeleport(2808, 3433, 0, "modern");
			c.sendMessage("You Teleport to Catherby");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102114:
			c.getPA().startTeleport(2964, 3380, 0, "modern");
			c.sendMessage("You Teleport to Falador");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102120:
			c.getPA().startTeleport(3079, 3350, 0, "modern");
			c.sendMessage("You Teleport to Draynor Village");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102096:
			c.getPA().startTeleport(2899, 3547, 0, "modern");
			c.sendMessage("You Teleport to Burthorpe");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102123:
			c.getPA().startTeleport(2662, 3305, 0, "modern");
			c.sendMessage("You Teleport to Ardounge");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102129:
			c.getPA().startTeleport(2606, 3093, 0, "modern");
			c.sendMessage("You Teleport to Yannile");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102099:
			c.getPA().startTeleport(3176, 2987, 0, "modern");
			c.sendMessage("You Teleport to Bandit Camp");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102117:
			c.getPA().startTeleport(3026, 3207, 0, "modern");
			c.sendMessage("You Teleport to Port Sarim");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102105:
			c.getPA().startTeleport(3300, 3209, 0, "modern");
			c.sendMessage("You Teleport to Al Kharid");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102090:
			c.getPA().startTeleport(2111, 3915, 0, "modern");
			c.sendMessage("You Teleport to Lunar Isle");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			break;
		case 102102:
			c.sendMessage("Taverly Maps Broke Atm Being Fixed.");
			c.sendMessage("Please Chose another area!");
			break;
		// End of Lodestone Teles
		// Aura
		case 113217:
			if (c.getItems().freeSlots() < 1) {
				c.sendMessage("You need atleast 1 free inventory space.");
				return;
			}
			c.getItems().addItem(c.AuraEquiped, 1);
			c.AuraEquiped = -1;
			c.getPA().sendFrame34(c.AuraEquiped, 0, 10794, 1);
			break;
		// AURA END
		// XP lamp
		case 136191:
			c.antiqueSelect = 0;
			c.sendMessage("<col=225>You select Attack");
			break;
		case 136212:
			c.antiqueSelect = 2;
			c.sendMessage("<col=225>You select Strength");
			break;
		case 136215:
			c.antiqueSelect = 4;
			c.sendMessage("<col=225>You select Ranged");
			break;
		case 136194:
			c.antiqueSelect = 6;
			c.sendMessage("<col=225>You select Magic");
			break;
		case 136233:
			c.antiqueSelect = 1;
			c.sendMessage("<col=225>You select Defence");
			break;
		case 136254:
			c.antiqueSelect = 3;
			c.sendMessage("<col=225>You select Hitpoints");
			break;
		case 136236:
			c.antiqueSelect = 5;
			c.sendMessage("<col=225>You select Prayer");
			break;
		case 137001:
			c.antiqueSelect = 24;
			c.sendMessage("<col=225>You select Dungeoneering");
			break;
		case 136203:
			c.antiqueSelect = 16;
			c.sendMessage("<col=225>You select Agility");
			break;
		case 136224:
			c.antiqueSelect = 15;
			c.sendMessage("<col=225>You select Herblore");
			break;
		case 136209:
			c.antiqueSelect = 17;
			c.sendMessage("<col=225>You select Thieving");
			break;
		case 136242:
			c.antiqueSelect = 12;
			c.sendMessage("<col=225>You select Crafting");
			break;
		case 137007:
			c.antiqueSelect = 20;
			c.sendMessage("<col=225>You select Runecrafting");
			break;
		case 136227:
			c.antiqueSelect = 18;
			c.sendMessage("<col=225>You select Slayer");
			break;
		case 136245:
			c.antiqueSelect = 19;
			c.sendMessage("<col=225>You select Farming");
			break;
		case 136197:
			c.antiqueSelect = 14;
			c.sendMessage("<col=225>You select Mining");
			break;
		case 136218:
			c.antiqueSelect = 13;
			c.sendMessage("<col=225>You select Smithing");
			break;
		case 136239:
			c.antiqueSelect = 10;
			c.sendMessage("<col=225>You select Fishing");
			break;
		case 137004:
			c.antiqueSelect = 7;
			c.sendMessage("<col=225>You select Cooking");
			break;
		case 136221:
			c.antiqueSelect = 11;
			c.sendMessage("<col=225>You select Firemaking");
			break;
		case 136230:
			c.antiqueSelect = 23;
			c.sendMessage("<col=225>You select Construction");
			break;
		case 136251:
			c.antiqueSelect = 21;
			c.sendMessage("<col=225>You select Summoning");
			break;
		case 136248:
			c.antiqueSelect = 22;
			c.sendMessage("<col=225>You select hunter");
			break;
		case 136200:
			c.antiqueSelect = 8;
			c.sendMessage("<col=225>You select Woodcutting");
			break;
		case 136206:
			c.antiqueSelect = 9;
			c.sendMessage("<col=225>You select Fletching");
			break;
		case 137013:
			if (c.lampfix == 5) {
				c.getPA().addSkillXP(750000, c.antiqueSelect);
				c.getItems().deleteItem(2528, 1);
				c.sendMessage("<col=225>750k Xp added to skill chosen! Lamp Disapeared");
				c.getPA().closeAllWindows();
				c.lampfix = 0;
			} else if (c.lampfix == 10) {
				// c.getPA().addSkillXP(14000000,c.antiqueSelect);
				c.playerLevel[c.antiqueSelect] = 99;
				c.playerXP[c.antiqueSelect] = c.getPA().getXPForLevel(100);
				c.getPA().refreshSkill(c.antiqueSelect);
				c.getItems().deleteItem(11137, 1);
				c.sendMessage("<col=225>14M Xp added to skill chosen! Lamp Disapeared");
				c.getPA().closeAllWindows();
				c.lampfix = 0;
			} else if (c.lampfix == 15) {
				c.playerLevel[c.antiqueSelect] = 135;
				c.playerXP[c.antiqueSelect] = c.getPA().getXPForLevel(136);
				c.getPA().refreshSkill(c.antiqueSelect);
				// c.getPA().addSkillXP(508438379,c.antiqueSelect);
				c.getItems().deleteItem(19750, 1);
				c.sendMessage("<col=225>500M Xp added to skill chosen! Lamp Disapeared");
				c.getPA().closeAllWindows();
				c.lampfix = 0;
			} else {
				// c.sendMessage("<col=225>SORRY YOU CANT MULTICLICK OR CHEAT");
				c.getPA().closeAllWindows();
			}
			break;
		// LAMPEND
		case 192012:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.constructionID = 1088;
				c.getDH().sendDialogues(28168, 1);
			}
			break;
		case 192013:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.constructionID = 594;
				c.getDH().sendDialogues(28169, 1);
			}
			break;
		case 82012:// search button.
			c.isSearching = true;
			c.isSearching2 = !c.isSearching2;
			if (!c.isSearching2) {
				if (c.getInStream() != null & c != null) {
					c.getOutStream().createFrame(187);
					c.flushOutStream();
				}
				c.isSearching = true;
				c.isSearching2 = false;
			} else {
				c.isSearching = false;
				c.isSearching2 = true;
			}
			break;
		case 53152:
			Cooking.getAmount(c, 1);
			break;
		case 53151:
			Cooking.getAmount(c, 5);
			break;
		case 53150:
			Cooking.getAmount(c, 10);
			break;
		case 53149:
			Cooking.getAmount(c, 28);
			break;
		case 108005:
			if (c.inTrade) {
				c.sendMessage("Bustin Your Balls..");
				return;
			}
			c.getPA().showInterface(19148);
			break;
		case 8198:
			PartyRoom.accept(c);
			break;
		case 66122:
			switch (c.hasFollower) {
			case 6806: // thorny snail
			case 6807:
			case 6994: // spirit kalphite
			case 6995:
			case 6867: // bull ant
			case 6868:
			case 6794: // spirit terrorbird
			case 6795:
			case 6815: // war tortoise
			case 6816:
			case 6873: // pack yak
			case 3594: // yak
			case 3590: // war tortoise
			case 3596: // terrorbird
			case 6874:
				for (int i = 0; i < c.maxstore; i++) {
					if (c.storeditems[i] > 0) {
						c.getPA().removeBoBItems(i, 1);
						c.startAnimation(827);
						c.stopMovement();
					}
				}
				break;
			}
			break;

		case 39014:
			c.sendMessage("You're familiar will automaticly aid you in multi.");
			break;
		case 39013:
			c.sendMessage("No need to do this!");
			break;
		case 39011:
			c.getDH().Kill();
			break;
		case 39010:
			c.sendMessage("Familiar called.");
			break;
		/*
		 * case 39012: //c.getPA().closeAllWindows();
		 * //c.getPA().showInterface(19148); switch(c.hasFollower) { case 6806:
		 * //thorny snail case 6807: case 6994: //spirit kalphite case 6995:
		 * case 6867: //bull ant case 6868: case 6794: //spirit terrorbird case
		 * 6795: case 6815: //war tortoise case 6816: case 6873: //pack yak case
		 * 3594: // yak case 3590: // war tortoise case 3596: // terrorbird case
		 * 6874: for (int i = 0; i < c.maxstore; i++) { if (c.storeditems[i] >
		 * 0) { c.getPA().BoBToBank(i, 1); //c.startAnimation(827);
		 * c.stopMovement(); } } break; } break;
		 */
		case 58074:
			c.getBankPin().closeBankPin();
			break;

		case 58073:
			// c.getBankPin().resetBankPin();
			c.getPA().sendFrame126("www.Pandemic.com/forum", -1);
			c.sendMessage("Opened forums, Request A New Pin on Forums!");
			break;

		case 113236:
			c.getPA().sendFrame126("www.Pandemic.com", -1);
			c.sendMessage("<col=225>Opening the Main Page Don't forget to Register on Forums");
			break;

		case 58025:
		case 58026:
		case 58027:
		case 58028:
		case 58029:
		case 58030:
		case 58031:
		case 58032:
		case 58033:
		case 58034:
			c.getBankPin().bankPinEnter(actionButtonId);
			break;
		case 113238:
			if (c.xpLock == false) {
				c.xpLock = true;
				c.usedxplock = true;
				c.sendMessage("<col=255>Your XP is now LOCKED!</col>");
			} else {
				c.xpLock = false;
				c.sendMessage("<col=255>Your XP is now UNLOCKED!</col>");
			}
			break;
		// end of quests n dung tab + LOCKXP
		// quickpray
		/*
		 * case 67079: c.getQC().clickConfirm(); c.sendMessage(
		 * "Confirmed QuickPrayers/QuickCurses! Activate them by pushing the orb!"
		 * ); break;
		 * 
		 * case 67050: case 67051: case 67052: case 67053: case 67054: case
		 * 67055: case 67056: case 67057: case 67058: case 67059: case 67060:
		 * case 67061: case 67062: case 67063: case 67064: case 67065: case
		 * 67066: case 67067: case 67068: case 67069: case 67070: case 67071:
		 * case 67072: case 67073: case 67074: case 67075: c.sendMessage(
		 * "Prayer selected."); if (c.altarPrayed == 0)
		 * c.getQP().clickPray(actionButtonId); else
		 * c.getQC().clickCurse(actionButtonId); break;
		 * 
		 * case 70080: c.sendMessage("Prayers turned to: ON");
		 * c.getQC().turnOnQuicks(); break;
		 * 
		 * case 70081: c.getQC().turnOffQuicks(); c.sendMessage(
		 * "Prayers turned to: OFF"); break;
		 * 
		 * case 70082: c.getQC().selectQuickInterface();
		 * c.getPA().sendFrame106(5); break;
		 */
		// votecash interface
		case 74111:
			if (c.vote == 1 && c.getItems().freeSlots() >= 1) {
				c.getItems().addItem(995, 5000000);
				c.startAnimation(3543);
				c.sendMessage("Your receive 5m cash! Thank you for voting!");
				c.getPA().closeAllWindows();
				c.vote = 0;
				c.isChoosing = false;
			} else if (c.vote == 1 && c.getItems().freeSlots() <= 1) {
				Server.itemHandler.createGroundItem(c, 995, c.getX(), c.getY(), 5000000, c.getId());
				c.startAnimation(3543);
				c.sendMessage("Your inventory is full so the item appears on the ground.");
				c.sendMessage("Your receive 5m cash! Thank you for voting!");
				c.getPA().closeAllWindows();
				c.vote = 0;
				c.isChoosing = false;
			}
			break;
		case 74108:
			if (c.vote == 1 && c.getItems().freeSlots() >= 1) {
				c.getItems().addItem(c.randomreward2(), 1);
				c.startAnimation(3543);
				c.sendMessage("Your receive a misc reward! Thank you for voting!");
				c.getPA().closeAllWindows();
				c.vote = 0;
				c.isChoosing = false;
			} else if (c.vote == 1 && c.getItems().freeSlots() <= 1) {
				// Server.itemHandler.createGroundItem(c, c.miscpackage1(),
				// c.getX(), c.getY(), 1, c.getId());
				Server.itemHandler.createGroundItem(c, c.randomreward2(), c.getX(), c.getY(), 1, c.getId());
				c.startAnimation(3543);
				c.sendMessage("Your inventory is full so the item appears on the ground.");
				c.sendMessage("Your receive a misc reward! Thank you for voting!");
				c.getPA().closeAllWindows();
				c.vote = 0;
				c.isChoosing = false;
			}
			break;
		// end of votecash
		/*
		 * case 86000:
		 * 
		 * break;
		 */
		case 20174:
			c.getDH().sendDialogues(119, 8275);
			// crafting + fletching interface:
			// case 89223: //Deposit Inventory old client
			/*
			 * case 66117: switch(c.hasFollower) { case 6870: //wolpertinger
			 * if(c.getItems().playerHasItem(12437, 1)) {
			 * c.getItems().deleteItem(12437, 1); c.gfx0(1311);
			 * if(c.playerLevel[6] > c.getLevelForXP(c.playerXP[6]))
			 * c.playerLevel[6] = c.getLevelForXP(c.playerXP[6]); else
			 * c.playerLevel[6] += (c.getLevelForXP(c.playerXP[6]) * .1);
			 * c.getPA().refreshSkill(6); c.sendMessage(
			 * "Your Magic bonus has increased!"); } else c.sendMessage(
			 * "You don't have a scroll for that NPC!"); break;
			 */
		case 150:
			if (c.autoRet == 0)
				c.autoRet = 1;
			else
				c.autoRet = 0;
			break;
		case 70146:
			if (c.playerLevel[24] > 98) {
				c.getItems().addItem(18509, 1);
			} else {
				c.sendMessage("You must be 99 Dungeoneering to Recieve This.");
			}
			break;

		/*
		 * case 66122: switch(c.npcType) { case 6807: case 6874: case 6868: case
		 * 6795: case 6816: case 6873:
		 * 
		 * c.sendMessage("You are now storing items inside your npc");
		 * c.Summoning().store(); } break;
		 */
		case 66127:
			c.sendMessage("Not available at the moment.");
			break;
		case 21010:
			c.takeAsNote = true;
			break;
		case 21011:
			c.takeAsNote = false;
			break;
		case 85248:
			c.takeAsNote = !c.takeAsNote;
			break;
		case 68244:
			c.getPA().startTeleport(2676, 3711, 0, "modern");
			break;
		case 54221:
			c.getPA().startTeleport(2897, 3618, 12, "modern");
			c.sendMessage("Welcome to The God Bandos's chamber");
			break;

		case 54231:
			// c.getPA().startTeleport(2897, 3618, 4, "modern");
			c.getPA().startTeleport(2897, 3618, 8, "modern");
			c.sendMessage("Welcome to The God Saradomin's chamber");
			break;

		case 54228:
			c.getPA().startTeleport(2897, 3618, 4, "modern");
			// c.getPA().startTeleport(2897, 3618, 8, "modern");
			c.sendMessage("Welcome to The God Armadyl's chamber");
			break;
		case 68247:
			c.getPA().startTeleport(2884, 9798, 0, "modern");
			break;
		case 68250:
			c.getPA().startTeleport(3428, 3537, 0, "modern");
			break;
		case 68253:
			c.getPA().startTeleport(2710, 9466, 0, "modern");
			break;
		case 69000:
			c.getPA().startTeleport(2905, 9730, 0, "modern");
			break;
		case 69003:
			c.getPA().startTeleport(2908, 9694, 0, "modern");
			break;
		/*
		 * case 69006: if((c.playerLevel[21] < 90) && (c.playerLevel[16] < 90))
		 * { c.sendMessage(
		 * "You need 90 Agility And 90 Hunter to enter the Strykworm's Cave"); }
		 * else { if((c.playerLevel[21] > 89) && (c.playerLevel[16] < 90)) {
		 * c.sendMessage("You need 90 Agility to enter the Strykworm's Cave"); }
		 * else { if((c.playerLevel[21] < 90) && (c.playerLevel[16] > 89)) {
		 * c.sendMessage("You need 90 Hunter to enter the Strykworm's Cave"); }
		 * else { if((c.playerLevel[21] > 89) && (c.playerLevel[16] >89)) {
		 * c.getPA().startTeleport(2515, 4632, 0, "modern"); c.sendMessage(
		 * "A sense of nervousness fills your body.."); c.sendMessage(
		 * "you find yourself in a mystery cave!"); } } } }
		 */

		case 69006:
			if ((c.playerLevel[21] < 90) && (c.playerLevel[16] < 90)) {
				c.sendMessage("You need 90 Agility And 90 Hunter to enter the Strykworm's Cave");
			} else {
				if ((c.playerLevel[21] > 89) && (c.playerLevel[16] < 90)) {
					c.sendMessage("You need 90 Agility to enter the Strykworm's Cave");
				} else {
					if ((c.playerLevel[21] < 90) && (c.playerLevel[16] > 89)) {
						c.sendMessage("You need 90 Hunter to enter the Strykworm's Cave");
					} else {
						if ((c.playerLevel[21] > 89) && (c.playerLevel[16] > 89)) {
							c.getPA().startTeleport(2515, 4632, 0, "modern");
							c.sendMessage("A sense of nervousness fills your body..");
							c.sendMessage("you find yourself in a mystery cave!");
						}
					}
				}
			}

			break;

		/*
		 * case 114112://melee set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 10) { c.sendMessage(
		 * "You need atleast 10 free slot's to use this feature." ); } else if
		 * (c.getItems().playerHasItem(995, 350000)) {
		 * c.getItems().deleteItem2(995, 350000); c.getItems().addItem(10828,
		 * 1); c.getItems().addItem(1127, 1); c.getItems().addItem(1079, 1);
		 * c.getItems().addItem(3842, 1); c.getItems().addItem(4587, 1);
		 * c.getItems().addItem(1231, 1); c.getItems().addItem(1725, 1);
		 * c.getItems().addItem(3105, 1); c.getItems().addItem(2550, 1); } else
		 * { c.sendMessage("You need atleast 350,000 coins to use this feature."
		 * ); } break; case 46230: c.getItems().addItem(10828, 1);
		 * c.getItems().addItem(10551, 1); c.getItems().addItem(4087, 1);
		 * c.getItems().addItem(11732, 1); c.getItems().addItem(13006, 1);
		 * c.getItems().addItem(1725, 1); c.getItems().addItem(6737, 1);
		 * c.getItems().addItem(8850, 1); c.getItems().addItem(4151, 1);
		 * c.getItems().addItem(995, 50000000); c.getPA().showInterface(3559);
		 * c.getPA().addSkillXP((15000000), 0); c.getPA().addSkillXP((15000000),
		 * 1); c.getPA().addSkillXP((15000000), 2);
		 * c.getPA().addSkillXP((15000000), 3); c.getPA().addSkillXP((15000000),
		 * 4); c.getPA().addSkillXP((15000000), 5);
		 * c.getPA().addSkillXP((15000000), 6); c.playerXP[3] =
		 * c.getPA().getXPForLevel(99)+5; c.playerLevel[3] =
		 * c.getPA().getLevelForXP(c.playerXP[3]); c.getPA().refreshSkill(3);
		 * c.puremaster = 1; break; case 46234: c.getItems().addItem(10941, 1);
		 * c.getItems().addItem(10939, 1); c.getItems().addItem(10940, 1);
		 * c.getItems().addItem(10933, 1); c.getItems().addItem(18508, 1);
		 * c.getItems().addItem(2462, 1); c.getItems().addItem(995, 50000000);
		 * c.getPA().showInterface(3559); break; case 46227:
		 * c.getItems().addItem(12222, 1); c.getItems().addItem(6107, 1);
		 * c.getItems().addItem(2497, 1); c.getItems().addItem(3105, 1);
		 * c.getItems().addItem(12988, 1); c.getItems().addItem(10498, 1);
		 * c.getItems().addItem(1725, 1); c.getItems().addItem(861, 1);
		 * c.getItems().addItem(4151, 1); c.getItems().addItem(892, 1000);
		 * c.getItems().addItem(995, 50000000); c.getPA().showInterface(3559);
		 * c.getPA().addSkillXP((15000000), 0); c.getPA().addSkillXP((15000000),
		 * 2); c.getPA().addSkillXP((15000000), 3);
		 * c.getPA().addSkillXP((15000000), 4); c.getPA().addSkillXP((15000000),
		 * 6); c.playerXP[3] = c.getPA().getXPForLevel(99)+5; c.playerLevel[3] =
		 * c.getPA().getLevelForXP(c.playerXP[3]); c.getPA().refreshSkill(3);
		 * c.puremaster = 1; break;
		 * 
		 * case 114113://mage set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 7) { c.sendMessage(
		 * "You need atleast 7 free slot's to use this feature."); } else if
		 * (c.getItems().playerHasItem(995, 300000)) {
		 * c.getItems().deleteItem2(995, 300000); c.getItems().addItem(4091, 1);
		 * c.getItems().addItem(4093, 1); c.getItems().addItem(3755, 1);
		 * c.getItems().addItem(2550, 1); c.getItems().addItem(1704, 1);
		 * c.getItems().addItem(3842, 1); c.getItems().addItem(4675, 1); } else
		 * { c.sendMessage("You need atleast 300,000 coins to use this feature."
		 * ); } break;
		 * 
		 * case 114114://range set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 13) { c.sendMessage(
		 * "You need atleast 13 free slot's to use this feature." ); } else if
		 * (c.getItems().playerHasItem(995, 450000)) {
		 * c.getItems().deleteItem2(995, 450000); c.getItems().addItem(3749, 1);
		 * c.getItems().addItem(1704, 1); c.getItems().addItem(2503, 1);
		 * c.getItems().addItem(2497, 1); c.getItems().addItem(2491, 1);
		 * c.getItems().addItem(6328, 1); c.getItems().addItem(2550, 1);
		 * c.getItems().addItem(9185, 1); c.getItems().addItem(9243, 100);
		 * c.getItems().addItem(10499, 1); c.getItems().addItem(861, 1);
		 * c.getItems().addItem(892, 100); } else { c.sendMessage(
		 * "You need atleast 450,000 coins to use this feature."); } break;
		 * 
		 * case 114115://hybrid set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 14) { c.sendMessage(
		 * "You need atleast 14 free slot's to use this feature." ); } else if
		 * (c.getItems().playerHasItem(995, 450000)) {
		 * c.getItems().deleteItem2(995, 450000); c.getItems().addItem(555,
		 * 300); c.getItems().addItem(560, 200); c.getItems().addItem(565, 100);
		 * c.getItems().addItem(4675, 1); c.getItems().addItem(2497, 1);
		 * c.getItems().addItem(2415, 1); c.getItems().addItem(10828, 1);
		 * c.getItems().addItem(3841, 1); c.getItems().addItem(2503, 1);
		 * c.getItems().addItem(7460, 1); c.getItems().addItem(1704, 1);
		 * c.getItems().addItem(2550, 1); c.getItems().addItem(4091, 1);
		 * c.getItems().addItem(4093, 1); c.getItems().addItem(3105, 1); } else
		 * { c.sendMessage("You need atleast 450,000 coins to use this feature."
		 * ); } break;
		 * 
		 * case 114118://runes set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 10) { c.sendMessage(
		 * "You need atleast 10 free slot's to use this feature." ); } else if
		 * (c.getItems().playerHasItem(995, 300000)) {
		 * c.getItems().deleteItem2(995, 300000);
		 * c.getItems().addItem(560,1000); c.getItems().addItem(555,1000);
		 * c.getItems().addItem(565,1000); c.getItems().addItem(9075,1000);
		 * c.getItems().addItem(557,1000); c.getItems().addItem(556,1000);
		 * c.getItems().addItem(554,1000); c.getItems().addItem(562,1000);
		 * c.getItems().addItem(561,1000); c.getItems().addItem(563,1000); }
		 * else { c.sendMessage(
		 * "You need atleast 300,000 coins to use this feature."); } break;
		 * 
		 * case 114119://barrage set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 3) { c.sendMessage(
		 * "You need atleast 3 free slot's to use this feature."); } else if
		 * (c.getItems().playerHasItem(995, 2000000)) {
		 * c.getItems().deleteItem2(995, 2000000);
		 * c.getItems().addItem(555,6000); c.getItems().addItem(560,4000);
		 * c.getItems().addItem(565,2000); } else { c.sendMessage(
		 * "You need atleast 2,000,000 coins to use this feature." ); } break;
		 * 
		 * case 114120://veng set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 3) { c.sendMessage(
		 * "You need atleast 3 free slot's to use this feature."); } else if
		 * (c.getItems().playerHasItem(995, 100000)) {
		 * c.getItems().deleteItem2(995, 100000);
		 * c.getItems().addItem(557,1000); c.getItems().addItem(560,200);
		 * c.getItems().addItem(9075,400); } else { c.sendMessage(
		 * "You need atleast 100,000 coins to use this feature."); } break;
		 * 
		 * case 114123://shark set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 1) { c.sendMessage(
		 * "You need atleast 1 free slot's to use this feature."); } else if
		 * (c.getItems().playerHasItem(995, 100000)) {
		 * c.getItems().deleteItem2(995, 100000);
		 * c.getItems().addItem(385,1000); } else { c.sendMessage(
		 * "You need atleast 100,000 coins to use this feature."); } break;
		 * 
		 * case 114124://tuna pot set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 1) { c.sendMessage(
		 * "You need atleast 1 free slot's to use this feature."); } else if
		 * (c.getItems().playerHasItem(995, 150000)) {
		 * c.getItems().deleteItem2(995, 150000);
		 * c.getItems().addItem(7060,1000); } else { c.sendMessage(
		 * "You need atleast 150,000 coins to use this feature."); } break;
		 * 
		 * case 114125://super set if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 1) { c.sendMessage(
		 * "You need atleast 1 free slot's to use this feature."); } else if
		 * (c.getItems().playerHasItem(995, 80000)) {
		 * c.getItems().deleteItem2(995, 80000); c.getItems().addItem(146,100);
		 * c.getItems().addItem(158,100); c.getItems().addItem(164,100); } else
		 * { c.sendMessage("You need atleast 80,000 coins to use this feature."
		 * ); } break;
		 * 
		 * case 114126://super restores biatch if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 1) { c.sendMessage(
		 * "You need atleast 1 free slot's to use this feature."); } else if
		 * (c.getItems().playerHasItem(995, 30000)) {
		 * c.getItems().deleteItem2(995, 30000); c.getItems().addItem(3025,100);
		 * } else { c.sendMessage(
		 * "You need atleast 30,000 coins to use this feature."); } break;
		 * 
		 * case 114127://mage pots if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 1) { c.sendMessage(
		 * "You need atleast 1 free slot's to use this feature."); } else if
		 * (c.getItems().playerHasItem(995, 30000)) {
		 * c.getItems().deleteItem2(995, 30000); c.getItems().addItem(3041,100);
		 * } else { c.sendMessage(
		 * "You need atleast 30,000 coins to use this feature."); } break;
		 * 
		 * case 114128://range pots if (c.inWild() && c.isBanking) {
		 * c.sendMessage("You cannot do this right now"); } else
		 * if(c.getItems().freeSlots() <= 1) { c.sendMessage(
		 * "You need atleast 1 free slot's to use this feature."); } else if
		 * (c.getItems().playerHasItem(995, 36000)) {
		 * c.getItems().deleteItem2(995, 36000); c.getItems().addItem(2445,100);
		 * } else { c.sendMessage(
		 * "You need atleast 36,000 coins to use this feature."); } break;
		 */

		case 17111:// stop viewing viewing orb
			c.setSidebarInterface(10, 2449);
			c.viewingOrb = false;
			c.teleportToX = 2399;
			c.teleportToY = 5171;
			c.appearanceUpdateRequired = true;
			c.updateRequired = true;
			break;

		case 59139:// viewing orb southwest
			c.viewingOrb = true;
			c.teleportToX = 2388;
			c.teleportToY = 5138;
			c.appearanceUpdateRequired = true;
			c.updateRequired = true;
			break;

		case 59138:// viewing orb southeast
			c.viewingOrb = true;
			c.teleportToX = 2411;
			c.teleportToY = 5137;
			c.appearanceUpdateRequired = true;
			c.updateRequired = true;
			break;

		case 59137:// viewing orb northeast
			c.viewingOrb = true;
			c.teleportToX = 2409;
			c.teleportToY = 5158;
			c.appearanceUpdateRequired = true;
			c.updateRequired = true;
			break;

		case 59136:// viewing orb northwest
			c.viewingOrb = true;
			c.teleportToX = 2384;
			c.teleportToY = 5157;
			c.appearanceUpdateRequired = true;
			c.updateRequired = true;
			break;

		case 59135:// viewing orb middle
			c.viewingOrb = true;
			c.teleportToX = 2398;
			c.teleportToY = 5150;
			c.appearanceUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 107229:
			if (c.isDonator == 1 && c.slayerTask <= 0) {
				c.getDH().sendDialogues(11, 8275);
				c.sendMessage("Your magical donator rank makes you able to contact Duradel!");
			} else {
				c.sendMessage("You alredy have a task!");
			}
			break;

		case 108003:
			if (c.isDonator == 1 || c.isDonator >= 1 || c.playerRights == 3) {
				//AchievementManager.increase(c, Achievements.DTAB);
				c.getPA().showInterface(4200);
			} else if (c.isInUndead()){
			c.sendMessage("You can not access your bank at this place!");	
			}
			else {c.sendMessage("You must be an donator to view the donator interface!");
			}
			break;

		case 16113:
			if (c.isDonator == 0 || c.inWild()) {
				c.sendMessage("You must be outside wilderness and be a donator to use this!");
				return;
			}
			if (c.isDonator == 1 && !c.inWild()) {
				if (c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
					c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
					c.getPA().getLevelForXP(c.playerXP[5]);
					c.sendMessage("You recharge your prayer points.");
					c.getPA().refreshSkill(5);
				} else {
					c.sendMessage("You already have full prayer points.");
				}
			}
			break;

		case 16119:
			if (c.isDonator == 0 || c.inWild()) {
				c.sendMessage("You must be outside wilderness and be a donator to use this!");
				return;
			}
			if (c.isDonator == 1 && !c.inWild()) {
				if (System.currentTimeMillis() - c.buryDelay > 25000) {
					c.getItems().addItem(391, 10);
					c.buryDelay = System.currentTimeMillis();
				}
			}
			break;

		case 82020:
			for (int i = 0; i < c.playerItems.length; i++) {
				c.getItems().bankItem(c.playerItems[i], i, c.playerItemsN[i]);
			}
			break;

		case 16115:
			if (c.isDonator == 1) {
				c.getPA().spellTeleport(2393, 9894, 0);
				c.sendMessage(
						"<img=0>You teleported to donator Island a place to chill/relax, theres also alot of benefits.<img=0>");
			} else {
				c.sendMessage("You must be an donator to teleport to the donator Island!");
				return;
			}
			break;
		/*
		 * case 108006: if(!c.isSkulled) { c.getItems().resetKeepItems();
		 * c.getItems().keepItem(0, false); c.getItems().keepItem(1, false);
		 * c.getItems().keepItem(2, false); c.getItems().keepItem(3, false);
		 * c.sendMessage(
		 * "You can keep three items and a fourth if you use the protect item prayer."
		 * ); } else { c.getItems().resetKeepItems(); c.getItems().keepItem(0,
		 * false); c.sendMessage(
		 * "You are skulled and will only keep one item if you use the protect item prayer."
		 * ); } c.getItems().sendItemsKept(); c.getPA().showInterface(6960);
		 * c.getItems().resetKeepItems(); break;
		 */
		case 108006: // items kept on death?
			if (c.inTrade) {
				return;
			}
			c.StartBestItemScan();
			c.EquipStatus = 0;
			for (int k = 0; k < 4; k++)
				c.getPA().sendFrame34a(10494, -1, k, 1);
			for (int k = 0; k < 39; k++)
				c.getPA().sendFrame34a(10600, -1, k, 1);
			if (c.WillKeepItem1 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem1, 0, c.WillKeepAmt1);
			if (c.WillKeepItem2 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem2, 1, c.WillKeepAmt2);
			if (c.WillKeepItem3 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem3, 2, c.WillKeepAmt3);
			if (c.WillKeepItem4 > 0)
				c.getPA().sendFrame34a(10494, c.WillKeepItem4, 3, 1);
			for (int ITEM = 0; ITEM < 28; ITEM++) {
				if (c.playerItems[ITEM] - 1 > 0
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& !(c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus, c.playerItemsN[ITEM]);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem1 && ITEM == c.WillKeepItem1Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem2 && ITEM == c.WillKeepItem2Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt2) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem3 && ITEM == c.WillKeepItem3Slot)
						&& c.playerItemsN[ITEM] > c.WillKeepAmt3) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus,
							c.playerItemsN[ITEM] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerItems[ITEM] - 1 > 0
						&& (c.playerItems[ITEM] - 1 == c.WillKeepItem4 && ITEM == c.WillKeepItem4Slot)
						&& c.playerItemsN[ITEM] > 1) {
					c.getPA().sendFrame34a(10600, c.playerItems[ITEM] - 1, c.EquipStatus, c.playerItemsN[ITEM] - 1);
					c.EquipStatus += 1;
				}
			}
			for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
				if (c.playerEquipment[EQUIP] > 0
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& !(c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus, c.playerEquipmentN[EQUIP]);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem1 && EQUIP + 28 == c.WillKeepItem1Slot)
						&& c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - c.WillKeepAmt1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt1);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem2 && EQUIP + 28 == c.WillKeepItem2Slot)
						&& c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - c.WillKeepAmt2 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt2);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem3 && EQUIP + 28 == c.WillKeepItem3Slot)
						&& c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - c.WillKeepAmt3 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus,
							c.playerEquipmentN[EQUIP] - c.WillKeepAmt3);
					c.EquipStatus += 1;
				} else if (c.playerEquipment[EQUIP] > 0
						&& (c.playerEquipment[EQUIP] == c.WillKeepItem4 && EQUIP + 28 == c.WillKeepItem4Slot)
						&& c.playerEquipmentN[EQUIP] > 1 && c.playerEquipmentN[EQUIP] - 1 > 0) {
					c.getPA().sendFrame34a(10600, c.playerEquipment[EQUIP], c.EquipStatus,
							c.playerEquipmentN[EQUIP] - 1);
					c.EquipStatus += 1;
				}
			}

			c.ResetKeepItems();
			c.getPA().showInterface(17100);

			break;
		case 16109:
			if (c.isDonator == 0 || c.inWild()) {
				c.sendMessage("You must be outside wilderness and be a donator to use this!");
				return;
			}
			if (c.playerMagicBook == 0 && c.isDonator == 1 && !c.inWild()) {
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.setSidebarInterface(0, 328);
				c.sendMessage("An ancient wisdomin fills your mind.");
				c.getPA().resetAutocast();
				return;
			}
			if (c.playerMagicBook == 1 && c.isDonator == 1 && !c.inWild()) {
				c.playerMagicBook = 2;
				c.setSidebarInterface(0, 328);
				c.setSidebarInterface(6, 29999);
				c.sendMessage("Your mind becomes stirred with thoughs of dreams.");
				c.getPA().resetAutocast();
				return;
			}
			if (c.playerMagicBook == 2 && c.isDonator == 1 && !c.inWild()) {
				c.setSidebarInterface(6, 1151); // modern
				c.playerMagicBook = 0;
				c.setSidebarInterface(0, 328);
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
				return;
			}
			break;
		case 16111:
			if (c.isDonator == 0 || c.inWild()) {
				c.sendMessage("You must be outside wilderness and be a donator to use this!");
				return;
			}
			if (c.isDonator == 1 && !c.inWild()) {
				if (System.currentTimeMillis() - c.buryDelay > 86400000) {
					c.getItems().addItem(19955, 1);
					c.buryDelay = System.currentTimeMillis();
				} else {
					c.sendMessage("You can only do this once per 24 Hours.");
				}
			}
			break;

		case 94142:
			if (c.hasFollower > 0) {
				c.firstslot();
				for (int i = 0; i < 29; i += 1) {
					Server.itemHandler.createGroundItem(c, c.storeditems[i],
							Server.npcHandler.npcs[c.summoningnpcid].absX,
							Server.npcHandler.npcs[c.summoningnpcid].absY, 1, c.playerId);
					c.storeditems[i] = -1;
					c.occupied[i] = false;
				}
				c.totalstored = 0;
				c.summoningnpcid = 0;
				c.summoningslot = 0;
				c.sendMessage("Your BoB items have drop on the floor");
			} else {
				c.sendMessage("You do not have a familiar currently spawned");
			}
			break;
		// 1st tele option

		case 9190:
			if (c.teleAction == 43) {
				c.getPA().startTeleport(3222, 3218, 0, "normal");
			}
			if (c.dialogueAction == 106) {
				if (c.getItems().playerHasItem(c.diceID, 1)) {
					c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);
					c.getItems().addItem(15086, 1);
					c.sendMessage("You get a six-sided die out of the dice bag.");
				}
				if (c.dialogueAction == 99911) { // tutorial
					c.getDH().sendDialogues(3848, -1);
				}
			} else if (c.dialogueAction == 50) {
				c.trade11 = 1400;
				c.trade11();
				/*
				 * if (Server.singleStarter.contains(c.connectedFrom)) {
				 * c.sendMessage(
				 * "You have already received a starter recently. You may receive another later."
				 * ); c.selectStarter = false; c.getPA().closeAllWindows();
				 * return; }
				 */
				c.getItems().addItem(1704, 1);
				c.getItems().addItem(995, 5000000);
				// Server.singleStarter.add(c.connectedFrom);
				c.getPA().closeAllWindows();
				c.getItems().addItem(6950, 1);
				c.getItems().addItem(6107, 1);
				c.getItems().addItem(6108, 1);
				c.getItems().addItem(3106, 10);
				c.getItems().addItem(4676, 10);
				c.getItems().addItem(2413, 1);
				c.getItems().addItem(11119, 10);
				c.getItems().addItem(3842, 1);
				c.getItems().addItem(2941, 10);
				c.getItems().addItem(10499, 1);
				c.getItems().addItem(5699, 10);
				c.getItems().addItem(4154, 10);
				c.getItems().addItem(555, 1000);
				c.getItems().addItem(560, 1000);
				c.getItems().addItem(565, 1000);
				c.getItems().addItem(9341, 100);
				c.getItems().addItem(386, 1500);
				c.getItems().addItem(9186, 10);
				c.getItems().addItem(2498, 10);
				c.getItems().addItem(146, 100);
				c.getItems().addItem(158, 100);
				c.getItems().addItem(2435, 100);
				c.getItems().addItem(170, 100);
				c.getItems().addItem(3041, 100);
				c.getPA().addSkillXP(3358594, 4);
				c.getPA().addSkillXP(3358594, 6);
				c.getPA().addSkillXP(3358594, 2);
				c.getPA().addSkillXP(3358594, 3);
				c.getPA().addSkillXP(106333, 0);
				c.playerLevel[3] = 85;
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(0);
				c.getPA().refreshSkill(6);
				c.getPA().refreshSkill(4);
				c.getPA().refreshSkill(2);
				c.sendMessage(
						"<shad=15369497>To get RFD gloves, do the Recipe for Disaster minigame south of edgeville general store!!<shad=15369497>");
				c.sendMessage("This server is one of the best.. Real summoning.. Ect.. Enjoy it!");
				c.dialogueAction = -1;
				c.getPA().requestUpdates();
				c.selectStarter = false;
				c.selectStarterr = false;
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
			} else if (c.teleAction == 6) {
				// kalphite queen REDONE TO MOLE
				c.getPA().spellTeleport(1761, 5196, 0);
				c.sendMessage("The giant mole, once ate a child, Be carefull!");
			} else if (c.dialogueAction == 107) {
				if (c.getItems().playerHasItem(c.diceID, 1)) {
					c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);
					c.getItems().addItem(15092, 1);
					c.sendMessage("You get a ten-sided die out of the dice bag.");
				}
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 1) {
				// rock crabs
				c.getPA().spellTeleport(2676, 3715, 0);
			} else if (c.teleAction == 2) {
				// barrows
				c.getPA().spellTeleport(3565, 3314, 0);
			} else if (c.teleAction == 199) {
				// Assault
				c.getPA().spellTeleport(3186, 9758, 0);
				c.sendMessage("Enter the champion Stone to begin!");
				c.sendMessage("If the minigame bugs up type ::endgame, you will receive your points.");
				// c.sendMessage("DISABLED ATM DUE TO BUGS, FIXING THIS
				// WEEKEND");
			} else if (c.teleAction == 3) {
				c.sendMessage("GodWars is under construction atm please wait untill its fixed");
			} else if (c.teleAction == 4) {
				// varrock wildy
				c.getPA().spellTeleport(2539, 4716, 0);
			} else if (c.teleAction == 5) {
				c.getDH().sendOption2("Crafting", "Hunter");
				// c.getPA().spellTeleport(3298,3287,0);
				// c.sendMessage("You can mine almost everything here..
				// Enjoy.");
			} else if (c.teleAction == 20) {
				// lum
				c.getPA().spellTeleport(3222, 3218, 0);// 3222 3218
			} else if (c.teleAction == 8) {
				c.getPA().spellTeleport(2960, 9477, 0);// sea troll queen
			} if (c.teleAction == 6969) {
				if (c.getItems().freeSlots() <= 3) {
					c.sendMessage("<col=255>You need at least 3 free inventory spots!");
				return;
				}
				if (c.playerLevel[c.playerSmithing] <= 30) {
					c.sendMessage("<col=255>You need at least 30 smithing to craft a Capto set!");
				return;
				}
				if (c.getItems().playerHasItem(9632, 250)) {
				c.startAnimation(898);
				c.getItems().deleteItem(9632, 250);
				c.getItems().addItem(8810, 1);
				c.getItems().addItem(8811, 1);
				c.getItems().addItem(8812, 1);
				c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 25, c.playerSmithing);
				c.sendMessage("<col=255>You successfully smith a Capto armour set!");//smithing
				} else {
				c.sendMessage("<col=255>You need at least 250 daeyalt ores + 30 smithing to craft a Capto set!");	
				}
			}

			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2845, 4832, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2786, 4839, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2398, 4841, 0);
				c.dialogueAction = -1;
			} else if (c.teleAction == 21) {
				c.getPA().spellTeleport(2480, 3437, 0);// 2480 3437
				c.sendMessage("To start running again relog!");
				c.dialogueAction = -1;
			}
			if (c.dialogueAction == 15000) {
				if (System.currentTimeMillis() - c.logoutDelay < 10000) {
					c.sendMessage("You cannot Transform while in combat!");
				} else
					c.isNpc = false;
				c.playerStandIndex = 0x328;
				c.playerTurnIndex = 0x337;
				c.playerWalkIndex = 0x333;
				c.playerTurn180Index = 0x334;
				c.playerTurn90CWIndex = 0x335;
				c.playerTurn90CCWIndex = 0x336;
				c.playerRunIndex = 0x338;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You transformed back to regular.");
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 15001) {
				if (System.currentTimeMillis() - c.logoutDelay < 10000) {
					c.sendMessage("You cannot Transform while in combat!");
				} else
					// if (c.playerStandIndex != 171) {
					c.npcId2 = 8541;
				c.isNpc = true;
				c.playerStandIndex = 171;
				c.playerTurnIndex = 168;
				c.playerWalkIndex = 168;
				c.playerTurn180Index = 168;
				c.playerTurn90CWIndex = 171;
				c.playerTurn90CCWIndex = 171;
				c.playerRunIndex = 168;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You transformed into an Ice Imp.");
				c.getPA().closeAllWindows();
				// }
			}
			break;
		// mining - 3046,9779,0
		// smithing - 3079,9502,0

		/*
		 * case 66122: // HANDLES THE STORE BUTTON IN SUMMON INTERFACE TAB
		 * if(c.hasFollower == 3596 || c.hasFollower == 6816 || c.hasFollower ==
		 * 6874 || c.hasFollower == 6795 || c.hasFollower == 6806 ||
		 * c.hasFollower == 6815 || c.hasFollower == 6867 || c.hasFollower ==
		 * 9469 || c.hasFollower == 6807 || c.hasFollower == 3594 ||
		 * c.hasFollower == 6868 || c.hasFollower == 3590 || c.hasFollower ==
		 * 6873) { c.sendMessage("You are now storing items inside your npc");
		 * c.Summoning().store(); } else { c.sendMessage(
		 * "You don't have an summoning NPC that can hold items." ); } break;
		 */

		case 66126:
			if (c.hasFollower > 0) {
				// c.sumTimer = 3;
				c.callFamilliar = true;
				c.sendMessage("Familliar called");

			}

			if (c.hasFollower <= 0) {
				c.sendMessage("You don't have a follower");
			}
			break;

		/*
		 * case 66126: //Summoning Special Moves if (c.summonSpec < 1){ if
		 * (c.hasFollower == 7344 && c.getItems().playerHasItem(12825, 1)) {
		 * final int damage = Misc.random(20) + 6; if(c.npcIndex > 0) {
		 * Server.npcHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
		 * Server.npcHandler.npcs[c.npcIndex].updateRequired = true;
		 * Server.npcHandler.npcs[c.npcIndex].hitDiff2 = damage;
		 * Server.npcHandler.npcs[c.npcIndex].HP -= damage; c.sendMessage(
		 * "Your Steel Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12825, 1); c.startAnimation(1914); } else
		 * if(c.oldPlayerIndex > 0 || c.playerIndex > 0) {
		 * Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
		 * Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
		 * Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 =
		 * true; Server.playerHandler.players[c.playerIndex].updateRequired =
		 * true; //o.sendMessage(
		 * "Your opponent's steal titan causes you damage."); c.sendMessage(
		 * "Your Steel Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12825, 1); c.startAnimation(1914); } } else
		 * if (c.hasFollower == 7340 && c.getItems().playerHasItem(12833, 1)) {
		 * // 12833 is scroll final int damage = Misc.random(18) + 5;
		 * if(c.npcIndex > 0) {
		 * Server.npcHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
		 * Server.npcHandler.npcs[c.npcIndex].updateRequired = true;
		 * Server.npcHandler.npcs[c.npcIndex].hitDiff2 = damage;
		 * Server.npcHandler.npcs[c.npcIndex].HP -= damage; c.sendMessage(
		 * "Your Geyser Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12833, 1); c.startAnimation(1914); } else
		 * if(c.oldPlayerIndex > 0 || c.playerIndex > 0) {
		 * Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
		 * Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
		 * Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 =
		 * true; Server.playerHandler.players[c.playerIndex].updateRequired =
		 * true; //o.sendMessage(
		 * "Your opponent's steal titan causes you damage."); c.sendMessage(
		 * "Your Geyser Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12833, 1); c.startAnimation(1914); } } else
		 * if (c.hasFollower == 7356 && c.getItems().playerHasItem(12824, 1)) {
		 * // is scroll 12824 final int damage = Misc.random(17) + 4;
		 * if(c.npcIndex > 0) {
		 * Server.npcHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
		 * Server.npcHandler.npcs[c.npcIndex].updateRequired = true;
		 * Server.npcHandler.npcs[c.npcIndex].hitDiff2 = damage;
		 * Server.npcHandler.npcs[c.npcIndex].HP -= damage; c.sendMessage(
		 * "Your Fire Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12824, 1); c.startAnimation(1914); } else
		 * if(c.oldPlayerIndex > 0 || c.playerIndex > 0) {
		 * Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
		 * Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
		 * Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 =
		 * true; Server.playerHandler.players[c.playerIndex].updateRequired =
		 * true; //o.sendMessage(
		 * "Your opponent's steal titan causes you damage."); c.sendMessage(
		 * "Your Fire Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12824, 1); c.startAnimation(1914); } } else
		 * if (c.hasFollower == 7350 && c.getItems().playerHasItem(12827, 1)) {
		 * // 12827 final int damage = Misc.random(16) + 3; if(c.npcIndex > 0) {
		 * Server.npcHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
		 * Server.npcHandler.npcs[c.npcIndex].updateRequired = true;
		 * Server.npcHandler.npcs[c.npcIndex].hitDiff2 = damage;
		 * Server.npcHandler.npcs[c.npcIndex].HP -= damage; c.sendMessage(
		 * "Your Abyssal Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12827, 1); c.startAnimation(1914); } else
		 * if(c.oldPlayerIndex > 0 || c.playerIndex > 0) {
		 * Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
		 * Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
		 * Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 =
		 * true; Server.playerHandler.players[c.playerIndex].updateRequired =
		 * true; //o.sendMessage(
		 * "Your opponent's steal titan causes you damage."); c.sendMessage(
		 * "Your Abyssal Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12827, 1); c.startAnimation(1914); } } else
		 * if (c.hasFollower == 7358 && c.getItems().playerHasItem(12824, 1)) {
		 * final int damage = Misc.random(15) + 2; if(c.npcIndex > 0) {
		 * Server.npcHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
		 * Server.npcHandler.npcs[c.npcIndex].updateRequired = true;
		 * Server.npcHandler.npcs[c.npcIndex].hitDiff2 = damage;
		 * Server.npcHandler.npcs[c.npcIndex].HP -= damage; c.sendMessage(
		 * "Your Moss Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12824, 1); c.startAnimation(1914); } else
		 * if(c.oldPlayerIndex > 0 || c.playerIndex > 0) {
		 * Server.playerHandler.players[c.playerIndex].playerLevel[3] -= damage;
		 * Server.playerHandler.players[c.playerIndex].hitDiff2 = damage;
		 * Server.playerHandler.players[c.playerIndex].hitUpdateRequired2 =
		 * true; Server.playerHandler.players[c.playerIndex].updateRequired =
		 * true; //o.sendMessage(
		 * "Your opponent's steal titan causes you damage."); c.sendMessage(
		 * "Your Moss Titan Damages your Opponent.");
		 * c.getItems().deleteItem(12824, 1); c.startAnimation(1914); } } else
		 * if (c.hasFollower == 6874 && c.getItems().playerHasItem(12435, 1)) {
		 * // 12435 c.getItems().addItem(15272, 4); c.sendMessage(
		 * "Your Pak Yack's Special Supplys you with 4 rocktails!" );
		 * c.sendMessage("You can receive food again in 4 minutes.");
		 * c.getItems().deleteItem(12435, 1); } else if (c.hasFollower == 6823
		 * && c.getItems().playerHasItem(12434, 1)) { // 12435 c.playerLevel[3]
		 * += 13; if(c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
		 * c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]); c.sendMessage(
		 * "Your Unicorn's Special Heals you for 13 HP!"); c.sendMessage(
		 * "You can heal yourself again in 4 minutes.");
		 * c.getItems().deleteItem(12434, 1); c.getPA().refreshSkill(3); } else
		 * if (c.hasFollower == 6814 && c.getItems().playerHasItem(12438, 1)) {
		 * // 12435 c.playerLevel[3] += 8; if(c.playerLevel[3] >
		 * c.getLevelForXP(c.playerXP[3])) c.playerLevel[3] =
		 * c.getLevelForXP(c.playerXP[3]); c.sendMessage(
		 * "Your Bunyip's Special Heals you for 8 HP!"); c.sendMessage(
		 * "You can heal yourself again in 4 minutes.");
		 * c.getItems().deleteItem(12438, 1); c.getPA().refreshSkill(3); } else
		 * if (c.hasFollower == 6870 && c.getItems().playerHasItem(12437, 1)) {
		 * // 12435 c.playerLevel[3] += 15; c.playerLevel[6] += 6;
		 * c.sendMessage("Your Wolpertinger's Special Heals you for 15 HP!");
		 * c.sendMessage (
		 * "Your Wolpertinger's Increases and Restores your Magic skill!");
		 * if(c.playerLevel[6] > c.getLevelForXP(c.playerXP[6]))
		 * c.playerLevel[6] = c.getLevelForXP(c.playerXP[6])+6;
		 * if(c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
		 * c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
		 * c.getPA().refreshSkill(3); c.getPA().refreshSkill(6);
		 * c.getItems().deleteItem(12437, 1); } else { c.sendMessage(
		 * "You have no familiar or you have no scrolls!"); } c.summonSpec =
		 * 240; } else { c.sendMessage(
		 * "You must wait at least 4 Minutes before using this again." ); }
		 * break;
		 */

		case 154:
			if (System.currentTimeMillis() - c.logoutDelay < 8000) {
				c.sendMessage("You cannot do skillcape emotes in combat!");
				return;
			}
			if (System.currentTimeMillis() - c.lastEmote >= 7000) {
				if (c.getPA().wearingCape(c.playerEquipment[c.playerCape])) {
					c.stopMovement();
					c.gfx0(c.getPA().skillcapeGfx(c.playerEquipment[c.playerCape]));
					c.startAnimation(c.getPA().skillcapeEmote(c.playerEquipment[c.playerCape]));
				} else if (c.playerEquipment[c.playerCape] == 19710) {
					c.getPA().dungemote(c);
				} else if (c.playerEquipment[c.playerCape] == 6570) {
					c.stopMovement();
					c.gfx0(1173);
					c.gfx0(1390);
					c.startAnimation(4939);
					c.forcedText = "" + Config.SERVER_NAME + " is FTW!";
					c.forcedChatUpdateRequired = true;
					c.updateRequired = true;
				} else if (c.playerEquipment[c.playerCape] == 19140) {
					c.getPA().compemote(c);
				} else if (c.playerEquipment[c.playerCape] == 19138) {
					c.getPA().compemote(c);
				} else if (c.playerEquipment[c.playerCape] == 18509) {
					c.getPA().dungemote2(c);
				} else if (c.playerEquipment[c.playerCape] == 18508) {
					c.getPA().dungemote(c);
				} else if (c.playerEquipment[c.playerCape] == 18415) {
					c.stopMovement();
					c.gfx0(1390);
					c.gfx0(828);
					c.startAnimation(4981);
				} else if (c.playerEquipment[c.playerCape] == 18403) {
					c.stopMovement();
					c.gfx0(1390);
					c.gfx0(823);
					c.startAnimation(4959);
				} else if (c.playerEquipment[c.playerCape] == 18413) {
					c.stopMovement();
					c.gfx0(1390);
					c.gfx0(832);
					c.startAnimation(4973);
				} else if (c.playerEquipment[c.playerCape] == 18411) {
					c.stopMovement();
					c.gfx0(1390);
					c.gfx0(829);
					c.startAnimation(4979);
				} else if (c.playerEquipment[c.playerCape] == 18405) {
					c.stopMovement();
					c.gfx0(1390);
					c.gfx0(824);
					c.startAnimation(4961);
				} else if (c.playerEquipment[c.playerCape] == 18407) {
					c.stopMovement();
					c.gfx0(1390);
					c.gfx0(833);
					c.startAnimation(4971);
				} else if (c.playerEquipment[c.playerCape] == 18409) {
					c.stopMovement();
					c.gfx0(1390);
					c.gfx0(813);
					c.startAnimation(4939);
				} else if (c.playerEquipment[c.playerCape] == 18417) {
					c.stopMovement();
					c.gfx0(1390);
					c.gfx0(1515);
					c.startAnimation(8525);
				} else {
					c.sendMessage("You must be wearing a Skillcape to do this emote.");
				}
				c.lastEmote = System.currentTimeMillis();
			}
			break;

		// 2nd tele option
		case 9191:
			if (c.dialogueAction == 35) {
				c.getItems().handleEffigies(12);
			} else if (c.dialogueAction == 36) {
				c.getItems().handleEffigies(20);
			} else if (c.dialogueAction == 37) {
				c.getItems().handleEffigies(7);
			} else if (c.dialogueAction == 38) {
				c.getItems().handleEffigies(19);
			} else if (c.dialogueAction == 39) {
				c.getItems().handleEffigies(9);
			} else if (c.dialogueAction == 40) {
				c.getItems().handleEffigies(15);
			} else if (c.dialogueAction == 41) {
				c.getItems().handleEffigies(13);
			}
			if (c.teleAction == 199) {
				c.getPA().spellTeleport(2724, 3500, 0);
				c.sendMessage("To start, enter the portal!");
				c.sendMessage("Everytime you kill a boss in the minigame, a pair of gloves will be unlocked.");
				c.sendMessage("To buy gloves click the chest.");
			}
			if (c.dialogueAction == 106) {
				if (c.getItems().playerHasItem(c.diceID, 1)) {
					c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);
					c.getItems().addItem(15088, 1);
					c.sendMessage("You get two six-sided dice out of the dice bag.");
				}
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 50) {
				c.trade11 = 1400;
				c.trade11();
				/*
				 * if (Server.singleStarter.contains(c.connectedFrom)) {
				 * c.sendMessage(
				 * "You have already received a starter recently. You may receive another later."
				 * ); c.selectStarter = false; c.getPA().closeAllWindows();
				 * return; }
				 */
				c.getItems().addItem(1704, 1);
				c.getItems().addItem(995, 5000000);
				// Server.singleStarter.add(c.connectedFrom);
				c.getPA().closeAllWindows();
				// c.getItems().addItem(6950, 1);
				c.getItems().addItem(4588, 10);
				c.getItems().addItem(1080, 10);
				c.getItems().addItem(1128, 10);
				c.getItems().addItem(3106, 10);
				c.getItems().addItem(4676, 10);
				c.getItems().addItem(2413, 1);
				c.getItems().addItem(11119, 10);
				c.getItems().addItem(3842, 1);
				c.getItems().addItem(3752, 10);
				c.getItems().addItem(10499, 1);
				c.getItems().addItem(5699, 10);
				c.getItems().addItem(4154, 10);
				c.getItems().addItem(555, 1000);
				c.getItems().addItem(560, 1000);
				c.getItems().addItem(565, 1000);
				c.getItems().addItem(4094, 10);
				c.getItems().addItem(4092, 10);
				c.getItems().addItem(386, 1500);
				c.getItems().addItem(2498, 10);
				c.getItems().addItem(146, 100);
				c.getItems().addItem(158, 100);
				c.getItems().addItem(2435, 100);
				c.getItems().addItem(170, 100);
				c.getItems().addItem(3041, 100);
				c.getPA().addSkillXP(3358594, 4);
				c.getPA().addSkillXP(3358594, 6);
				c.getPA().addSkillXP(127660, 5);
				c.getPA().addSkillXP(277742, 0);
				c.getPA().addSkillXP(3358594, 2);
				c.getPA().addSkillXP(3358594, 3);
				c.getPA().addSkillXP(40224, 1);
				c.playerLevel[3] = 85;
				c.playerLevel[5] = 52;
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(0);
				c.getPA().refreshSkill(6);
				c.getPA().refreshSkill(4);
				c.getPA().refreshSkill(2);
				c.getPA().refreshSkill(1);
				c.getPA().refreshSkill(5);
				c.sendMessage(
						"<shad=15369497>To get RFD gloves, do the Recipe for Disaster minigame south of edgeville general store!!<shad=15369497>");
				c.sendMessage("This server is one of the best.. Real summoning.. Ect.. Enjoy it!");
				c.dialogueAction = -1;
				c.getPA().requestUpdates();
				// c.getDH().sendDialogues(22, -1);
				c.selectStarter = false;
				c.selectStarterr = false;
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
			} else if (c.dialogueAction == 107) {
				if (c.getItems().playerHasItem(c.diceID, 1)) {
					c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);
					c.getItems().addItem(15094, 1);
					c.sendMessage("You get a twelve-sided die out of the dice bag.");
				}
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 1) {
				// tav dungeon
				c.getPA().spellTeleport(2884, 9798, 0);
			} else if (c.teleAction == 2) {
				// pest control
				c.getPA().spellTeleport(2662, 2650, 0);
			} else if (c.teleAction == 3) {
				// kbd
				c.getPA().spellTeleport(3007, 3849, 0);
			} else if (c.teleAction == 6) {
				// Giant Mole REDONE TO FORGOTTEN WARRIOR
				c.getPA().spellTeleport(2517, 3044, 0);
				// }
			} else if (c.teleAction == 4) {
				// graveyard
				c.getPA().spellTeleport(3243, 3517, 0);
			} else if (c.teleAction == 5) {
				// c.getPA().spellTeleport(3300, 3302,0);
				c.getDH().sendOption2("Mining", "Smithing");
				c.teleAction = 999;

			} else if (c.teleAction == 8) {
				c.getPA().spellTeleport(2984, 9630, 0);
				c.sendMessage("Beware: Recommended team of 5 Players or More");

			} else if (c.teleAction == 20) {
				c.getPA().spellTeleport(2387, 3488, 0);// 3210 3424
			} if (c.teleAction == 6969) {
				if (c.getItems().freeSlots() <= 3) {
					c.sendMessage("<col=255>You need at least 3 free inventory spots!");
				return;
				}
				if (c.playerLevel[c.playerSmithing] <= 75) {
					c.sendMessage("<col=255>You need at least 75 smithing to craft a Nation set!");
				return;
				}
				if (c.getItems().playerHasItem(9632, 250)) {
				c.startAnimation(898);
				c.getItems().deleteItem(9632, 750);
				c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 25, c.playerSmithing);
				c.getItems().addItem(8813, 1);
				c.getItems().addItem(8814, 1);
				c.getItems().addItem(8815, 1);
				c.sendMessage("<col=255>You successfully smith a Nation armour set!");//smithing
				} else {
				c.sendMessage("<col=255>You need at least 750 daeyalt ores to craft a Nation set!");	
				}
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2796, 4818, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2527, 4833, 0);
				c.dialogueAction = -1;
			}
			if (c.teleAction == 21) { // hunter
				c.getPA().spellTeleport(3492, 3488, 0);
				c.sendMessage("<shad=6081134>For information on how to train RC talk to Aubury.");
				c.dialogueAction = -1;
			}
			if (c.dialogueAction == 15000) {
				if (System.currentTimeMillis() - c.logoutDelay < 10000) {
					c.sendMessage("You cannot Transform while in combat!");
				} else if (c.playerStandIndex != 1310) {
					c.npcId2 = 1265;
					c.isNpc = true;
					c.playerStandIndex = 1310;
					c.playerTurnIndex = 1311;
					c.playerWalkIndex = 1311;
					c.playerTurn180Index = 1311;
					c.playerTurn90CWIndex = 1310;
					c.playerTurn90CCWIndex = 1310;
					c.playerRunIndex = 1311;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.sendMessage("You transformed into a Rock Crab.");
				}
			}
			if (c.dialogueAction == 15001) {
				if (System.currentTimeMillis() - c.logoutDelay < 10000) {
					c.sendMessage("You cannot Transform while in combat!");
				} else
					// if (c.playerStandIndex != 171) {
					c.npcId2 = 8534;
				c.isNpc = true;
				c.playerStandIndex = 171;
				c.playerTurnIndex = 168;
				c.playerWalkIndex = 168;
				c.playerTurn180Index = 168;
				c.playerTurn90CWIndex = 171;
				c.playerTurn90CCWIndex = 171;
				c.playerRunIndex = 168;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.sendMessage("You transformed into Royal Imp.");
				c.getPA().closeAllWindows();
				// }
			}
			break;
		// 3rd tele option

		case 9192:
			if (c.teleAction == 199) { // Nomad and angry goblin
				c.sendMessage("To fight Nomad, talk to him");
				c.sendMessage("To fight Angry Goblin talk to Ticket Goblin");
				c.getPA().spellTeleport(3078, 3505, 0);
			}
			if (c.teleAction == 21) { // Summoning
				c.getPA().spellTeleport(3450, 3515, 0);
				c.dialogueAction = -1;
			}
			if (c.dialogueAction == 106) {
				if (c.getItems().playerHasItem(c.diceID, 1)) {
					c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);
					c.getItems().addItem(15100, 1);
					c.sendMessage("You get a four-sided die out of the dice bag.");
				}
				c.getPA().closeAllWindows();

			} else if (c.dialogueAction == 50) {
				c.trade11 = 1400;
				c.trade11();
				/*
				 * if (Server.singleStarter.contains(c.connectedFrom)) {
				 * c.sendMessage(
				 * "You have already received a starter recently. You may receive another later."
				 * ); c.selectStarter = false; c.getPA().closeAllWindows();
				 * return; }
				 */
				c.getItems().addItem(1704, 1);
				c.getItems().addItem(995, 5000000);
				// Server.singleStarter.add(c.connectedFrom);
				c.getPA().closeAllWindows();
				// c.getItems().addItem(6950, 1);
				c.getItems().addItem(6329, 10);
				c.getItems().addItem(2504, 10);
				c.getItems().addItem(892, 10000);
				c.getItems().addItem(9186, 10);
				c.getItems().addItem(862, 10);
				c.getItems().addItem(9244, 300);
				c.getItems().addItem(2492, 10);
				c.getItems().addItem(3750, 10);
				c.getItems().addItem(10499, 1);
				c.getItems().addItem(9075, 1000);
				c.getItems().addItem(557, 1000);
				c.getItems().addItem(560, 1000);
				c.getItems().addItem(386, 1500);
				c.getItems().addItem(2498, 10);
				c.getItems().addItem(2435, 100);
				c.getItems().addItem(170, 100);
				c.getItems().addItem(3041, 100);
				c.getPA().addSkillXP(3368594, 4);
				c.getPA().addSkillXP(3368594, 6);
				c.getPA().addSkillXP(3368594, 3);
				c.getPA().addSkillXP(3368594, 1);
				c.getPA().addSkillXP(127660, 5);
				c.playerLevel[3] = 85;
				c.playerLevel[5] = 52;
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(5);
				c.getPA().refreshSkill(4);
				c.getPA().refreshSkill(6);
				c.getPA().refreshSkill(1);
				c.sendMessage(
						"<shad=15369497>To get RFD gloves, do the Recipe for Disaster minigame south of edgeville general store!!<shad=15369497>");
				c.sendMessage("This server is one of the best.. Real summoning.. Ect.. Enjoy it!");
				c.dialogueAction = -1;
				// c.getDH().sendDialogues(22, -1);
				c.getPA().requestUpdates();
				c.selectStarter = false;
				c.selectStarterr = false;
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
				// }

			} else if (c.dialogueAction == 107) {
				if (c.getItems().playerHasItem(c.diceID, 1)) {
					c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);
					c.getItems().addItem(15096, 1);
					c.sendMessage("You get a twenty-sided die out of the dice bag.");
				}
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 1) {
				// slayer tower
				c.getPA().spellTeleport(3428, 3537, 0);
			} else if (c.teleAction == 2) {
				// tzhaar
				c.getPA().spellTeleport(2438, 5168, 0);
				c.sendMessage("To fight Jad, enter the cave.");
			} else if (c.teleAction == 3) {
				// dag kings
				c.getPA().spellTeleport(1910, 4367, 0);
				c.sendMessage("Climb down the ladder to get into the lair.");
			} else if (c.teleAction == 6) {
				// MAD MUMMY
				c.getPA().spellTeleport(2962, 9631, 0);
				c.sendMessage("He mages & melees!");
			} else if (c.teleAction == 4) {
				// Lava Crossing
				// c.getPA().spellTeleport(3367, 3935, 0);
				c.sendMessage("Disabled atm due to people missclicking & going 50 wild..");
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2597, 3408, 0);

			} else if (c.teleAction == 20) {
				c.getPA().spellTeleport(2757, 3477, 0);
			}

			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2713, 4836, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 11) {
				c.getPA().spellTeleport(2162, 4833, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 12) {
				c.getPA().spellTeleport(2207, 4836, 0);
				c.dialogueAction = -1;
			}
			if (c.teleAction == 8) { // Used to be the latest boss teleport
										// option, now its gwd, was goblin/nomad
				c.sendMessage("If there is no floor/maps are gone RESTART CLIENT!");
				c.getPA().spellTeleport(2882, 5310, 2);
				/*
				 * c.sendMessage("To fight Nomad, talk to him"); c.sendMessage(
				 * "To fight Angry Goblin talk to Ticket Goblin");
				 * c.getPA().spellTeleport(3078, 3505, 0);
				 */
			}
			if (c.teleAction == 6969) {
				if (c.getItems().freeSlots() <= 3) {
					c.sendMessage("<col=255>You need at least 3 free inventory spots!");
				return;
				}
				if (c.playerLevel[c.playerSmithing] <= 130) {
					c.sendMessage("<col=255>You need at least 130 smithing to craft a Vortex set!");
				return;
				}
				if (c.getItems().playerHasItem(9632, 1500)) {
				c.getItems().deleteItem(9632, 1500);
				c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 25, c.playerSmithing);
				c.startAnimation(898);
				c.getItems().addItem(8807, 1);
				c.getItems().addItem(8808, 1);
				c.getItems().addItem(8809, 1);
				c.sendMessage("<col=255>You successfully smith a Vortex armour set!");//smithing
				} else {
				c.sendMessage("<col=255>You need at least 1500 daeyalt ores to craft a Vortex set!");	
				}	
			}
			if (c.dialogueAction == 15000) {
				if (System.currentTimeMillis() - c.logoutDelay < 10000) {
					c.sendMessage("You cannot Transform while in combat!");
				} else if (c.playerStandIndex != 5386) {
					c.npcId2 = 951;
					c.isNpc = true;
					c.playerStandIndex = 5386;
					c.playerTurnIndex = 5385;
					c.playerWalkIndex = 5385;
					c.playerTurn180Index = 5385;
					c.playerTurn90CWIndex = 5386;
					c.playerTurn90CCWIndex = 5386;
					c.playerRunIndex = 5385;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.sendMessage("You transformed into a Chicken.");
					c.getPA().closeAllWindows();
				}
			}
			break;

		case 9193:
			if (c.teleAction == 199) { // Castlewars tele
				c.getPA().spellTeleport(2443, 3090, 0);
				c.sendMessage("Sorry, This minigame is currently unavailable.");
			}

			if (c.dialogueAction == 35) {
				c.getItems().handleEffigies(16);
			} else if (c.dialogueAction == 36) {
				c.getItems().handleEffigies(17);
			} else if (c.dialogueAction == 37) {
				c.getItems().handleEffigies(11);
			} else if (c.dialogueAction == 38) {
				c.getItems().handleEffigies(10);
			} else if (c.dialogueAction == 39) {
				c.getItems().handleEffigies(8);
			} else if (c.dialogueAction == 40) {
				c.getItems().handleEffigies(5);
			} else if (c.dialogueAction == 41) {
				c.getItems().handleEffigies(14);
			}

			if (c.teleAction == 21) { // Dung
				c.getPA().spellTeleport(2533, 3304, 0);
				c.dialogueAction = -1;
				c.sendMessage("You Teleport to the dung area");
			}

			if (c.dialogueAction == 106) {
				if (c.getItems().playerHasItem(c.diceID, 1)) {
					c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);
					c.getItems().addItem(15090, 1);
					c.sendMessage("You get an eight-sided die out of the dice bag.");
				}
				c.getPA().closeAllWindows();

			} else if (c.dialogueAction == 50) {
				c.trade11 = 1400;
				c.trade11();
				/*
				 * if (Server.singleStarter.contains(c.connectedFrom)) {
				 * c.sendMessage(
				 * "You have already received a starter recently. You may receive another later."
				 * ); c.selectStarter = false; c.getPA().closeAllWindows();
				 * return; }
				 */
				c.getItems().addItem(1704, 1);
				c.getItems().addItem(995, 5000000);
				// Server.singleStarter.add(c.connectedFrom);
				c.getPA().closeAllWindows();
				// c.getItems().addItem(6950, 1);
				c.getItems().addItem(4587, 1);
				c.getItems().addItem(1079, 1);
				c.getItems().addItem(1127, 1);
				c.getItems().addItem(1540, 1);
				c.getItems().addItem(10828, 1);
				c.getItems().addItem(11732, 1);
				c.getItems().addItem(6568, 1);
				c.getItems().addItem(2497, 1);
				c.getItems().addItem(2503, 1);
				c.getItems().addItem(11118, 1);
				c.getItems().addItem(10499, 1);
				c.getItems().addItem(2570, 1);
				c.getItems().addItem(5698, 1);
				c.getItems().addItem(386, 1500);
				c.getItems().addItem(892, 1000);
				c.getItems().addItem(9185, 1);
				c.getItems().addItem(861, 1);
				c.getItems().addItem(9244, 300);
				c.getPA().addSkillXP(2000000, 0);
				c.getPA().addSkillXP(2000000, 1);
				c.getPA().addSkillXP(2000000, 2);
				c.getPA().addSkillXP(2000000, 3);
				c.getPA().addSkillXP(2000000, 4);
				c.getPA().addSkillXP(750627, 5);
				c.getPA().addSkillXP(2000000, 6);
				c.playerLevel[3] = 80;
				c.playerLevel[5] = 70;
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(5);
				c.sendMessage(
						"<shad=15369497>To get RFD gloves, do the Recipe for Disaster minigame south of edgeville general store!!<shad=15369497>");
				c.sendMessage("This server is one of the best.. Real summoning.. Ect.. Enjoy it!");
				c.dialogueAction = -1;
				c.getPA().requestUpdates();
				// c.getDH().sendDialogues(22, -1);
				c.selectStarter = false;
				c.selectStarterr = false;
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
			} else if (c.dialogueAction == 107) {
				if (c.getItems().playerHasItem(c.diceID, 1)) {
					c.getItems().deleteItem(c.diceID, c.getItems().getItemSlot(c.diceID), 1);
					c.getItems().addItem(15098, 1);
					c.sendMessage("You get the percentile dice out of the dice bag.");
				}
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 1) {
				// brimhaven dungeon
				// c.getPA().spellTeleport(2710, 9466, 0);
				// c.sendMessage("You teleported to brimhaven dungeon, be sure
				// to bring antifire-shield.");
				c.sendMessage("Brimhaven is disabled at the moment.");
			} else if (c.teleAction == 2) {
				// duel arena
				// c.getPA().spellTeleport(3358, 3269, 0);
				c.sendMessage("Duel Arena is disabled at the moment.");
			} else if (c.teleAction == 6) {
				// Barrelschest NOT ANYMORE!
				// c.getPA().spellTeleport(2367, 4956, 0);
				c.sendMessage("Please Suggest new ideas on forum for bosses!");
			} else if (c.teleAction == 3) {
				// chaos elemental
				c.getPA().spellTeleport(2717, 9805, 0);
				// c.getPA().spellTeleport(2611,3396,0);
			} else if (c.teleAction == 4) {
				// Fala
				c.getPA().spellTeleport(3211, 3422, 0);

			} else if (c.teleAction == 5) {
				c.getDH().sendOption2("WoodCutting", "FireMaking");
				c.teleAction = 22;
				// c.getPA().spellTeleport(2710,3462,0);
				// c.sendMessage("Good luck WCing!");
			}
			if (c.dialogueAction == 10) {
				c.getPA().spellTeleport(2660, 4839, 0);
				c.dialogueAction = -1;
			} else if (c.teleAction == 20) {
				c.getPA().spellTeleport(2964, 3378, 0);
			}
			if (c.teleAction == 8) {
				c.getPA().startTeleport(2465, 4770, 0, "ancient");
				c.sendMessage("Beware of the Snakes!.");
			}
			if (c.teleAction == 6969) { //smithing
				if (c.getItems().freeSlots() <= 1) {
					c.sendMessage("<col=255>You need at least 1 free inventory spot!");
				return;
				}
				if (c.getItems().playerHasItem(9632, 20)) {
					c.startAnimation(898);
					c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 55, c.playerSmithing);
					c.getItems().addItem(5021, 3);
					c.getItems().deleteItem(9632, 3);
					c.sendMessage("<col=255>You receive 3 tickets for smithing 3 daeyalt ores.");
				} else {
					c.sendMessage("<col=255>You need at least 3 noted daeyalt ores to train smithing!");
				}
			}
			if (c.dialogueAction == 15000) { // Castlewars tele
				if (System.currentTimeMillis() - c.logoutDelay < 10000) {
					c.sendMessage("You cannot Transform while in combat!");
				} else if (c.playerStandIndex != 11046) {
					c.npcId2 = 8540;
					c.isNpc = true;
					c.playerStandIndex = 11046;
					c.playerTurnIndex = 11047;
					c.playerWalkIndex = 11047;
					c.playerTurn180Index = 11047;
					c.playerTurn90CWIndex = 11046;
					c.playerTurn90CCWIndex = 11046;
					c.playerRunIndex = 11047;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.sendMessage("You transformed into Santa!");
					c.getPA().closeAllWindows();
				}
			}
			break;

		case 9194:
			if (c.teleAction == 199) { // Dominion tele
				c.getPA().spellTeleport(3168, 9747, 0);
				c.sendMessage("To start, Talk to the Ghost captain!");
				c.sendMessage("Welcome to the dominion Tower Minigame");
			}
			if (c.dialogueAction == 15000) { // Dominion tele
				c.getDH().sendDialogues(15001, 1);
			}
			if (c.teleAction == 21) {
				c.getDH().sendOption2("Fletching", "Thieving");
			}
			if (c.dialogueAction == 107) {
				c.getDH().sendDialogues(106, 4289);
				return;
			}
			if (c.dialogueAction == 106) {
				c.getDH().sendDialogues(107, 4289);
				return;
			}
			if (c.teleAction == 1) {
				// island
				c.getPA().spellTeleport(3117, 9847, 0);

			} else if (c.dialogueAction == 50) {
				c.trade11 = 1400;
				c.trade11();
				c.getItems().addItem(1704, 1);
				c.getItems().addItem(995, 5000000);
				// Server.singleStarter.add(c.connectedFrom);
				c.getPA().closeAllWindows();
				c.getItems().addItem(995, 5000000);
				c.getItems().addItem(590, 1);
				c.getItems().addItem(6739, 1);
				c.getItems().addItem(1704, 1);
				c.getItems().addItem(554, 200);
				c.getItems().addItem(555, 200);
				c.getItems().addItem(556, 200);
				c.getItems().addItem(558, 600);
				c.getItems().addItem(1381, 1);
				c.getItems().addItem(1323, 1);
				c.getItems().addItem(1067, 1);
				c.getItems().addItem(1115, 1);
				c.getItems().addItem(1153, 1);
				c.getItems().addItem(3105, 1);
				c.getItems().addItem(841, 1);
				c.getItems().addItem(882, 500);
				c.getItems().addItem(380, 100);
				c.getItems().addItem(11118, 1);
				c.getItems().addItem(379, 5);
				Connection.addIpToStarterList1(Server.playerHandler.players[c.playerId].connectedFrom);
				Connection.addIpToStarter1(Server.playerHandler.players[c.playerId].connectedFrom);
				c.sendMessage("You have recieved 1 out of 2 starter packages on this IP address.");
				c.dialogueAction = -1;
				c.getPA().requestUpdates();
				// c.getDH().sendDialogues(22, -1);
				c.selectStarter = false;
				c.selectStarterr = false;
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;

			} else if (c.teleAction == 2) {
				// last minigame spot
				c.getPA().spellTeleport(2865, 3546, 0);
				// c.getPA().closeAllWindows();
			} else if (c.teleAction == 3) {
				c.getPA().spellTeleport(3302, 9372, 0);
				c.sendMessage("Enter the gate to fight the mighty Corporeal Beast!");
				c.sendMessage("Note: Magic protect, Ruby bolts (e) and Diamond bolts (e) are recommended!");
			} else if (c.teleAction == 6) {
				c.sendMessage("Please Suggest new ideas on forum for bosses!");
				// c.getPA().spellTeleport(2882, 5310, 2);
				// c.getPA().spellTeleport(3079,3504,0);
				// c.sendMessage("To fight Angry goblin talk to Ticket Goblin,
				// otherwise talk to Nomad.");
				c.getPA().closeAllWindows();
			} else if (c.teleAction == 4) {
				c.getPA().spellTeleport(2980, 3617, 0);
			} else if (c.teleAction == 5) {
				c.getPA().spellTeleport(2812, 3463, 0);
			}
			if (c.dialogueAction == 10 || c.dialogueAction == 11) {
				c.dialogueId++;
				c.getDH().sendDialogues(c.dialogueId, 0);
			} else if (c.dialogueAction == 12) {
				c.dialogueId = 17;
				c.getDH().sendDialogues(c.dialogueId, 0);

			} else if (c.teleAction == 20) {
				c.getPA().spellTeleport(3493, 3484, 0);

			} else if (c.teleAction == 8) {
				c.getPA().startTeleport(2916, 3628, 12, "ancient");
				c.sendMessage("The Brutal Avatar of Destruction, Good Luck!");
				c.sendMessage("He has 2x hp bars, more then 2 ppl recommended!");
			}
			break;

		case 71074:
			/*
			 * if (c.clanId >= 0 &&
			 * Server.clanChat.clans[c.clanId].owner.equalsIgnoreCase
			 * (c.playerName)) { if (c.CSLS == 0) {
			 * if(System.currentTimeMillis() - c.lastEmote >= 1500) {
			 * Server.clanChat.clans[c.clanId].CS = 1;
			 * Server.clanChat.sendLootShareMessage(c.clanId,
			 * "LootShare has been toggled to " +
			 * (!Server.clanChat.clans[c.clanId].lootshare ? "ON" : "OFF") +
			 * " by the clan leader.");
			 * Server.clanChat.clans[c.clanId].lootshare =
			 * !Server.clanChat.clans[c.clanId].lootshare; c.CSLS = 1;
			 * Server.clanChat.updateClanChat(c.clanId); c.lastEmote =
			 * System.currentTimeMillis(); return; } } if (c.CSLS == 1) {
			 * if(System.currentTimeMillis() - c.lastEmote >= 1500) { c.CSLS =
			 * 2; Server.clanChat.clans[c.clanId].CS = 2;
			 * Server.clanChat.updateClanChat(c.clanId);
			 * Server.clanChat.sendLootShareMessage(c.clanId,
			 * "LootShare has been toggled to " +
			 * (!Server.clanChat.clans[c.clanId].lootshare ? "ON" : "OFF") +
			 * " by the clan leader.");
			 * Server.clanChat.clans[c.clanId].lootshare =
			 * !Server.clanChat.clans[c.clanId].lootshare; c.lastEmote =
			 * System.currentTimeMillis(); return;
			 * 
			 * } } if (c.CSLS == 2) { if(System.currentTimeMillis() -
			 * c.lastEmote >= 1500) { if(Server.clanChat.clans[c.clanId].playerz
			 * == 1) { c.sendMessage(
			 * "There must be atleast 2 members in the clan chat to toggle Coinshare ON."
			 * ); c.CSLS = 0; Server.clanChat.clans[c.clanId].CS = 0;
			 * Server.clanChat.updateClanChat(c.clanId); c.lastEmote =
			 * System.currentTimeMillis(); return; } c.CSLS = 3;
			 * Server.clanChat.clans[c.clanId].CS = 3;
			 * Server.clanChat.updateClanChat(c.clanId);
			 * Server.clanChat.sendCoinShareMessage(c.clanId,
			 * "CoinShare has been toggled to " +
			 * (!Server.clanChat.clans[c.clanId].coinshare ? "ON" : "OFF") +
			 * " by the clan leader.");
			 * Server.clanChat.clans[c.clanId].coinshare =
			 * !Server.clanChat.clans[c.clanId].coinshare; return;
			 * 
			 * } } if (c.CSLS == 3) { if(System.currentTimeMillis() -
			 * c.lastEmote >= 1500) { c.CSLS = 0;
			 * Server.clanChat.clans[c.clanId].CS = 0;
			 * Server.clanChat.updateClanChat(c.clanId);
			 * Server.clanChat.sendCoinShareMessage(c.clanId,
			 * "CoinShare has been toggled to " +
			 * (!Server.clanChat.clans[c.clanId].coinshare ? "ON" : "OFF") +
			 * " by the clan leader.");
			 * Server.clanChat.clans[c.clanId].coinshare =
			 * !Server.clanChat.clans[c.clanId].coinshare; c.lastEmote =
			 * System.currentTimeMillis(); return; } } } else { c.sendMessage(
			 * "Only the owner of the clan has the power to do that." ); }
			 */
			c.sendMessage("Lootshare is currently disabled, will be fixed soon.");
			break;
		case 34185:
		case 34184:
		case 34183:
		case 34182:
		case 34189:
		case 34188:
		case 34187:
		case 34186:
		case 34193:
		case 34192:
		case 34191:
		case 34190:
			if (c.craftingLeather)
				c.getCrafting().handleCraftingClick(actionButtonId);
			if (c.getFletching().fletching)
				c.getFletching().handleFletchingClick(actionButtonId);
			break;

		case 15147:
			if (c.smeltInterface) {
				c.smeltType = 2349;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 15146:
			if (c.smeltInterface) {
				c.smeltType = 2349;
				c.smeltAmount = 5;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 10247:
			if (c.smeltInterface) {
				c.smeltType = 2349;
				c.smeltAmount = 10;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 9110:
			if (c.smeltInterface) {
				c.smeltType = 2349;
				c.smeltAmount = 28;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 15151:
			if (c.smeltInterface) {
				c.smeltType = 2351;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 15150:
			if (c.smeltInterface) {
				c.smeltType = 2351;
				c.smeltAmount = 5;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 15149:
			if (c.smeltInterface) {
				c.smeltType = 2351;
				c.smeltAmount = 10;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 15148:
			if (c.smeltInterface) {
				c.smeltType = 2351;
				c.smeltAmount = 28;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 15159:
			if (c.smeltInterface) {
				c.smeltType = 2353;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 15158:
			if (c.smeltInterface) {
				c.smeltType = 2353;
				c.smeltAmount = 5;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 15157:
			if (c.smeltInterface) {
				c.smeltType = 2353;
				c.smeltAmount = 10;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 15156:
			if (c.smeltInterface) {
				c.smeltType = 2353;
				c.smeltAmount = 28;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 29017:
			if (c.smeltInterface) {
				c.smeltType = 2359;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 29016:
			if (c.smeltInterface) {
				c.smeltType = 2359;
				c.smeltAmount = 5;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 24253:
			if (c.smeltInterface) {
				c.smeltType = 2359;
				c.smeltAmount = 10;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 16062:
			if (c.smeltInterface) {
				c.smeltType = 2359;
				c.smeltAmount = 28;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 29022:
			if (c.smeltInterface) {
				c.smeltType = 2361;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 29020:
			if (c.smeltInterface) {
				c.smeltType = 2361;
				c.smeltAmount = 5;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 29019:
			if (c.smeltInterface) {
				c.smeltType = 2361;
				c.smeltAmount = 10;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 29018:
			if (c.smeltInterface) {
				c.smeltType = 2361;
				c.smeltAmount = 28;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 29026:
			if (c.smeltInterface) {
				c.smeltType = 2363;
				c.smeltAmount = 1;
				c.getSmithing().startSmelting(c.smeltType);
			}

			break;
		case 29025:// smelt 5
			if (c.smeltInterface) {
				c.smeltType = 2363;
				c.smeltAmount = 5;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;
		case 29024:// smelt 10
			if (c.smeltInterface) {
				c.smeltType = 2363;
				c.smeltAmount = 10;
				c.getSmithing().startSmelting(c.smeltType);
			}
			break;

		case 59004:
			c.getPA().removeAllWindows();
			break;
		case 16117:
			if (c.isDonator == 0 || c.inWild() || c.pkSafe()) {
				c.sendMessage("You must be outside wilderness and be a donator to use this!");
				return;
			}
			if (c.isDonator == 1 && !c.inWild() && !c.pkSafe() && !c.isInUndead()) {
				c.getPA().openUpBank();
				return;
			}
			break;
		case 70212:
			if (c.clanId > -1)
				Server.clanChat.leaveClan(c.playerId, c.clanId);
			else
				c.sendMessage("You are not in a clan.");
			break;
		case 62137:
			if (c.clanId >= 0) {
				c.sendMessage("You are already in a clan.");
				break;
			}
			if (c.getOutStream() != null) {
				c.getOutStream().createFrame(187);
				c.flushOutStream();
			}
			break;
		case 9178:
			if (c.dialogueAction == 9995) {
				c.getPA().enterDT();
			}
			if (c.dialogueAction == 90) {
				sentBankMes = false;
				if ((c.playerEquipment[c.playerHat] == -1) && (c.playerEquipment[c.playerRing] == -1)
						&& (c.playerEquipment[c.playerCape] == -1) && (c.playerEquipment[c.playerHands] == -1)
						&& (c.playerEquipment[c.playerArrows] == -1) && (c.playerEquipment[c.playerAmulet] == -1)
						&& (c.playerEquipment[c.playerChest] == -1) && (c.playerEquipment[c.playerShield] == -1)
						&& (c.playerEquipment[c.playerLegs] == -1) && (c.playerEquipment[c.playerHands] == -1)
						&& (c.playerEquipment[c.playerFeet] == -1) && (c.playerEquipment[c.playerWeapon] == -1)) {
					if ((c.getItems().freeSlots() == 28)) {
						// c.sendMessage("Sorry, but the Dungeoneering skill is
						// currently under developement.");
						c.getDungeoneering().startfloor1(c);
					} else {
						if (!sentBankMes == true) {
							c.sendMessage("<shad=15695415>Bank all your items first!");
							c.getPA().closeAllWindows();
							sentBankMes = true;
						}
					}
				} else {
					c.sendMessage(
							"<shad=15695415>Please Un-Equip all your worn items before teleporting to Dungeoneering.");
					c.getPA().removeAllWindows();
					return;
				}
			}

			int npcType = 6138;
			if (c.teleAction == 43) {
				c.getPA().startTeleport(3085, 3491, 0, "normal");
			}
			if (c.dialogueAction == 55) {
				ConstructionEvents.conChair2(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 59) {
				ConstructionEvents.conBook3(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 60) {
				ConstructionEvents.conAle2(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 56) {
				ConstructionEvents.conFern2(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 58) {
				ConstructionEvents.conTree2(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 42) {

				if (c.inWild())
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.getPA().closeAllWindows();
						c.getDH().sendDialogues(420, npcType);
						return;
					}
				}
				try {
					int skilld = 1;
					int leveld = 1;
					c.playerXP[skilld] = c.getPA().getXPForLevel(leveld) + 5;
					c.playerLevel[skilld] = c.getPA().getLevelForXP(c.playerXP[skilld]);
					c.getPA().refreshSkill(skilld);
					// c.getPA().closeAllWindows();
					c.getDH().sendDialogues(230, npcType);
				} catch (Exception e) {
				}
			}
			if (c.usingGlory)
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0, "modern");
			c.gdegradeNow = true;
			c.getPA().gloryDegrade();
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(3428, 3538, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(3565, 3314, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 4, "modern");
			}
			if (c.dialogueAction == 100) {
				c.getDH().sendDialogues(25, 946);
			}

			break;
		

		case 9179:
			if (c.dialogueAction == 90) {
				c.getPA().closeAllWindows();
				if (c.playerLevel[24] >= 49) {
					c.getPA().closeAllWindows();
					c.getPA().movePlayer(3467, 9495, 0);
					c.sendMessage("<shad=16112652>Kill NPC's here for fast Dung XP/Tokens!");
					// c.sendMessage("<shad=16112652>You are taken to a
					// underground cave!");
					return;
				} else {
					c.sendMessage("You need atleast a level of 49 Dungeoneering to enter this cave!");
					c.getPA().closeAllWindows();
					return;
				}
			}
			npcType = 6138;
			if (c.teleAction == 43) {
				c.getPA().startTeleport(2387, 3488, 0, "normal");
			}
			if (c.dialogueAction == 9995) {
				c.getShops().openShop(43);// ID of Dominion Tower Shop
				c.sendMessage("You have " + c.DTPoints + " Dominion Tower Points.");
			}
			if (c.dialogueAction == 55) {
				ConstructionEvents.conChair3(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 60) {
				ConstructionEvents.conAle3(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 56) {
				ConstructionEvents.conFern3(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 59) {
				ConstructionEvents.conBook4(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 58) {
				ConstructionEvents.conTree3(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 42) { // prayer
				if (c.inWild())
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.getPA().closeAllWindows();
						c.getDH().sendDialogues(420, npcType);
						return;
					}
				}
				try {
					int skillp = 5;
					int levelp = 1;
					c.playerXP[skillp] = c.getPA().getXPForLevel(levelp) + 5;
					c.playerLevel[skillp] = c.getPA().getLevelForXP(c.playerXP[skillp]);
					c.getPA().refreshSkill(skillp);
					// c.getPA().closeAllWindows();
					c.getDH().sendDialogues(260, npcType);
				} catch (Exception e) {
				}
			}
			if (c.usingGlory)
				c.getPA().startTeleport(Config.AL_KHARID_X, Config.AL_KHARID_Y, 0, "modern");
			c.gdegradeNow = true;
			c.getPA().gloryDegrade();
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2884, 3395, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3243, 3513, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2444, 5170, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 12, "modern");
			}
			if (c.dialogueAction == 101) {
				c.getDH().sendDialogues(21, 946);
			}
			if (c.dialogueAction == 100) {
				c.getGamble().gambleBlackJack(c);
			}
			break;

		case 9180:
			if (c.dialogueAction == 90) {
				c.getPA().removeAllWindows();
				c.sendMessage("<shad=6081134>Your current Dungeoneering level: "
						+ c.getPA().getLevelForXP(c.playerXP[24]) + "");
				c.sendMessage("<shad=6081134>Total amount of tokens: " + c.dungPoints + "");
			}
			if (c.dialogueAction == 9995) {
				c.sendMessage("Yes, if you should die you will keep all your items.");
				c.getPA().removeAllWindows();
			}
			if (c.teleAction == 43) {
				c.getPA().startTeleport(2060, 3146, 0, "normal");
			}
			if (c.dialogueAction == 55) {
				ConstructionEvents.conChair4(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 60) {
				ConstructionEvents.conAle4(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 59) {
				ConstructionEvents.conBook(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 56) {
				ConstructionEvents.conFern4(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 58) {
				ConstructionEvents.conTree4(c);
				c.getPA().removeAllWindows();
			}
			npcType = 6138;
			if (c.dialogueAction == 42) { // attack
				if (c.inWild())
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.getPA().closeAllWindows();
						c.getDH().sendDialogues(420, npcType);
						return;
					}
				}
				try {
					int skill = 0;
					int levela = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(levela) + 5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
					// c.getPA().closeAllWindows();
					c.getDH().sendDialogues(240, npcType);
				} catch (Exception e) {
				}
			}
			if (c.usingGlory)
				c.getPA().startTeleport(Config.DRAYNOR_X, Config.DRAYNOR_Y, 0, "modern");
			c.gdegradeNow = true;
			c.getPA().gloryDegrade();
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2471, 10137, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(3363, 3676, 0, "modern");
			if (c.dialogueAction == 4)
				c.getPA().startTeleport(2659, 2676, 0, "modern");
			if (c.dialogueAction == 20) {
				c.getPA().startTeleport(2897, 3618, 8, "modern");
			}
			if (c.dialogueAction == 101) {
				c.getDH().sendDialogues(23, 946);
			}
			if (c.dialogueAction == 100) {
				if (!c.getItems().playerHasItem(995, 1000000)) {
					c.sendMessage("You need at least 1M coins to play this game!");
					c.getPA().removeAllWindows();
					break;
				}
				c.getGamble().playGame(c);
			}
			break;

		case 9181:
			if (c.dialogueAction == 90) {
				c.sendMessage("<shad=6081134>You currently have " + c.dungPoints + " Dungeoneering Tokens.");
				c.getShops().openShop(85);
				return;
			}
			if (c.teleAction == 43) {
				c.getPA().startTeleport(2757, 3477, 0, "normal");
			}
			if (c.dialogueAction == 55) {
				ConstructionEvents.conChair(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 60) {
				ConstructionEvents.conAle(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 59) {
				ConstructionEvents.conBook2(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 56) {
				ConstructionEvents.conFern(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 58) {
				ConstructionEvents.conTree(c);
				c.getPA().removeAllWindows();
			}
			if (c.dialogueAction == 58) {
				ConstructionEvents.conTree(c);
				c.getPA().removeAllWindows();
			}
			npcType = 6138;
			if (c.dialogueAction == 42) { // allstats
				if (c.inWild())
					return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.getPA().closeAllWindows();
						c.getDH().sendDialogues(420, npcType);
						return;
					}
				}
				try {
					int skill1 = 0;
					int level = 1;
					c.playerXP[skill1] = c.getPA().getXPForLevel(level) + 5;
					c.playerLevel[skill1] = c.getPA().getLevelForXP(c.playerXP[skill1]);
					c.getPA().refreshSkill(skill1);
					int skill2 = 1;
					// int level = 1;
					c.playerXP[skill2] = c.getPA().getXPForLevel(level) + 5;
					c.playerLevel[skill2] = c.getPA().getLevelForXP(c.playerXP[skill2]);
					c.getPA().refreshSkill(skill2);
					int skill3 = 2;
					// int level = 1;
					c.playerXP[skill3] = c.getPA().getXPForLevel(level) + 5;
					c.playerLevel[skill3] = c.getPA().getLevelForXP(c.playerXP[skill3]);
					c.getPA().refreshSkill(skill3);
					int skill4 = 3;
					level = 10;
					c.playerXP[skill4] = c.getPA().getXPForLevel(level) + 5;
					c.playerLevel[skill4] = c.getPA().getLevelForXP(c.playerXP[skill4]);
					c.getPA().refreshSkill(skill4);
					int skill5 = 4;
					level = 1;
					c.playerXP[skill5] = c.getPA().getXPForLevel(level) + 5;
					c.playerLevel[skill5] = c.getPA().getLevelForXP(c.playerXP[skill5]);
					c.getPA().refreshSkill(skill5);
					int skill6 = 5;
					// int level = 1;
					c.playerXP[skill6] = c.getPA().getXPForLevel(level) + 5;
					c.playerLevel[skill6] = c.getPA().getLevelForXP(c.playerXP[skill6]);
					c.getPA().refreshSkill(skill6);
					int skill7 = 6;
					// int level = 1;
					c.playerXP[skill7] = c.getPA().getXPForLevel(level) + 5;
					c.playerLevel[skill7] = c.getPA().getLevelForXP(c.playerXP[skill7]);
					c.getPA().refreshSkill(skill7);
					// c.getPA().closeAllWindows();
					c.getDH().sendDialogues(250, npcType);
				} catch (Exception e) {
				}
			}
			if (c.usingGlory)
				c.getPA().startTeleport(Config.MAGEBANK_X, Config.MAGEBANK_Y, 0, "modern");
			c.gdegradeNow = true;
			c.getPA().gloryDegrade();
			if (c.dialogueAction == 2)
				c.getPA().startTeleport(2669, 3714, 0, "modern");
			if (c.dialogueAction == 3)
				c.getPA().startTeleport(2540, 4716, 0, "modern");
			if (c.dialogueAction == 4) {
				// c.getPA().startTeleport(3358, 3269, 0, "modern");
				c.sendMessage("Dueling is at your own risk. Refunds will not be given for items lost due to glitches.");
				c.sendMessage("If you get a black screen or freeze just type ::train.");
			}
			if (c.dialogueAction == 20) {
				// c.getPA().startTeleport(3366, 3266, 0, "modern");
				// c.killCount = 0;
				c.sendMessage("This will be added shortly");
			} else if (c.dialogueAction == 10 || c.dialogueAction == 101) {
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
			} else {
				c.getPA().removeAllWindows();
			}
			c.dialogueAction = 0;
			break;

		case 1093:
		case 1094:
		case 1097:
		case 15486:
			if (c.autocastId > 0) {
				c.getPA().resetAutocast();
			} else {
				if (c.playerMagicBook == 1) {
					if (c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15486
							 || c.playerEquipment[c.playerWeapon] == 13867
								|| c.playerEquipment[c.playerWeapon] == 18355)
						c.setSidebarInterface(0, 1689);
					else
						c.sendMessage("You can't autocast ancients without an ancient, chaotic staff or a SOL.");
				} else if (c.playerMagicBook == 0) {
					if (c.playerEquipment[c.playerWeapon] == 4170 || c.playerEquipment[c.playerWeapon] == 15486
							|| c.playerEquipment[c.playerWeapon] == 15040) {
						c.setSidebarInterface(0, 12050);
					} else {
						c.setSidebarInterface(0, 1829);
					}
				}

			}
			break;
		case 115121:
			c.getPA().closeAllWindows();
			break;
//FIRST OF 2 OPTIONS
		case 9157:
			if (c.dialogueAction == 9183) {
				Raid.addWaitingPlayer(c);
			}
			if (c.teleAction == 60) {
				c.getPA().spellTeleport(2036, 4524, 0);
				c.sendMessage("Welcome to the beautiful Asgard!");
			}
			if (Prestige.prestigeChat == 1) {
				Prestige.prestigeChat = -1;
				Prestige.openPrestigeShop(c);
				return;
			}

			if (c.dialogueAction == 1001) {
				c.getPA().spellTeleport(2598, 4771, 0);
				c.startAnimation(863);
				c.sendMessage("You wave goodbye as Sigli teleports you away.");
			}
			if (c.dialogueAction == 10010) {
				if (c.getItems().playerHasItem(12238, 1) && c.getItems().playerHasItem(20250, 1)
						&& c.getItems().playerHasItem(20159, 1) && c.getItems().playerHasItem(19931, 1)
						&& c.getItems().playerHasItem(3290, 1) && c.getItems().playerHasItem(11144, 1)
						&& c.getItems().playerHasItem(12230, 1)) {
					c.getItems().deleteItem(3290, 1);
					c.getItems().deleteItem(19931, 1);
					c.getItems().deleteItem(20159, 1);
					c.getItems().deleteItem(20250, 1);
					c.getItems().deleteItem(12238, 1);
					c.getItems().deleteItem(11144, 1);
					c.getItems().deleteItem(12230, 1);
					c.getItems().addItem(19939, 1);
					//AchievementManager.increase(c, Achievements.CWhip);
					c.sendMessage("You receive a Chaotic Whip.");
				} else {
					c.sendMessage("You don't have the required items.");
				}
				if (c.dialogueAction == 1003) {
					c.getPA().showInterface(5500);
				}
			}

			if (c.dialogueAction == 10018) {
				if (c.superPoints == 0 && c.customPoints >= 100000 && c.pcPoints >= 500000) {
					c.superPoints += 1;
					c.customPoints -= 100000;
					c.pcPoints -= 500000;
					c.logout();
				} else {
					c.sendMessage("You don't have the required Points.");
				}
			}
			if (c.dialogueAction == 1375) {
				c.getTutorial().tutorialStage++;
				c.getTutorial().handleTutorial();
				c.dialogueAction = 0;
				return;
			}
			if (c.dialogueAction == 10012) {
				if (c.getItems().playerHasItem(4067, 35)) {
					c.getItems().deleteItem(4067, 35);
					c.getItems().addItem(18747, 1);
					c.sendMessage("You Buy a Faithful Shield for 70b.");
				} else {
					c.sendMessage("You don't have the required items.");
				}
			}

			if (c.dialogueAction == 10011) {
				if (c.getItems().playerHasItem(4067, 10) && c.getItems().playerHasItem(11694, 1)
						&& c.getItems().playerHasItem(11696, 1) && c.getItems().playerHasItem(11698, 1)
						&& c.getItems().playerHasItem(11700, 1) && c.getItems().playerHasItem(11653, 1)
						&& c.getItems().playerHasItem(11654, 1)) {
					c.getItems().deleteItem(4067, 10);
					c.getItems().deleteItem(11694, 1);
					c.getItems().deleteItem(11696, 1);
					c.getItems().deleteItem(11698, 1);
					c.getItems().deleteItem(11700, 1);
					c.getItems().deleteItem(11653, 1);
					c.getItems().deleteItem(11654, 1);
					c.getItems().addItem(13840, 1);
					c.sendMessage("You receive a Frost Dragon GS.");
				} else {
					c.sendMessage("You don't have the required items.");
				}
			}
			
			if (c.dialogueAction == 25002) {
				if (c.getItems().freeSlots() == 28){
				c.getItems().removeAllItems();
				c.getItems().wearItem(1215, 1);
				c.getPA().movePlayer(3191, 3471, 0);
				c.sendMessage("<col=800000000>If you were wearing items, they have been dropped in front of the portal!");
				c.sendMessage("<col=800000000>If you were wearing items, they have been dropped in front of the portal!");
				c.sendMessage("<col=800000000>If you were wearing items, they have been dropped in front of the portal!");
			} else {
				c.sendMessage("<col=800000000>You need to bank all your items before you can enter!");
			}
			}
			
			if (c.dialogueAction == 10014) {
				if (c.getItems().playerHasItem(15445, 1) && (c.pummelerPoints >= 9)) {
					c.getItems().deleteItem(15445, 1);
					c.getItems().addItem(15446, 1);
					c.pummelerPoints -= 9;
					c.sendMessage("You Buy an Attacker Level 2.");
				} else {
					c.sendMessage("You don't have enough Pummeler Points or an Attacker Level 1.");
				}
			}
			if (c.dialogueAction == 10015) {
				if (c.getItems().playerHasItem(15446, 1) && (c.pummelerPoints >= 15)) {
					c.getItems().deleteItem(15446, 1);
					c.getItems().addItem(15447, 1);
					c.pummelerPoints -= 15;
					c.sendMessage("You Buy an Attacker Level 3.");
				} else {
					c.sendMessage("You don't have enough Pummeler Points or an Attacker Level 2.");
				}
			}
			if (c.dialogueAction == 10016) {
				if (c.getItems().playerHasItem(15447, 1) && (c.pummelerPoints >= 25)) {
					c.getItems().deleteItem(15447, 1);
					c.getItems().addItem(15448, 1);
					c.pummelerPoints -= 25;
					c.sendMessage("You Buy an Attacker Level 4.");
				} else {
					c.sendMessage("You don't have enough Pummeler Points or an Attacker Level 3.");
				}
			}
			if (c.dialogueAction == 10017) {
				if (c.getItems().playerHasItem(15448, 1) && (c.pummelerPoints >= 50)) {
					c.getItems().deleteItem(15448, 1);
					c.getItems().addItem(15449, 1);
					c.pummelerPoints -= 50;
					c.sendMessage("You Buy an Attacker Level 5.");
				} else {
					c.sendMessage("You don't have enough Pummeler Points or an Attacker Level 4.");
				}
			}
			if (c.dialogueAction == 1340) {
				if (System.currentTimeMillis() - c.emptyDelay >= 15000)
					;
				c.emptyDelay = System.currentTimeMillis();
				c.sendMessage("You've emptied your inventory, you must wait another 15 seconds to empty again.");
				c.getPA().removeAllItems();
			}
			if (c.dialogueAction == 10013) {
				if (c.pummelerPoints >= 1) {
					c.pummelerPoints -= 1;
					c.getItems().addItem(15445, 1);
					c.sendMessage("You Buy an Attacker Level 1.");
				} else {
					c.sendMessage("You don't have any Pummeler Points.");
				}
			}
			if (c.dialogueAction == 1327) {
				c.getItems().deleteAllItems();
				c.getPA().closeAllWindows();
				c.getDungeoneering().redostartfloor1(c);
			}
			if (c.dialogueAction == 4445) {
				if (c.getItems().playerHasItem(995, 250000)) {
					c.getPA().closeAllWindows();
					c.getItems().deleteItem(995, 250000);
					c.getItems().addItem(15121, 1);
					c.sendMessage("You've purchased a Milestone Cape!");
				} else {
					c.sendMessage("You need atleast 250K to buy the Milestone Cape!");
					c.getPA().closeAllWindows();
				}
			}
			if (c.dialogueAction == 1000) {
				c.getItems().addItem(c.floweritem, 1);
				c.getPA().objectToRemove(ClickItem.flowerX, ClickItem.flowerY);
				ClickItem.flowerX = 0;
				ClickItem.flowerY = 0;
				ClickItem.flowers = 0;
				c.getPA().closeAllWindows();
			}
			if (c.dialogueAction == 333) {
				c.getPA().spellTeleport(3374, 9811, 0);
				c.getPA().closeAllWindows();
				// c.sendMessage("Do not relog!");
			}
			if (c.dialogueAction == 222) {
				c.getPA().spellTeleport(2326, 3802, 0);
				// c.sendMessage("Do not relog!");
			}
			if (c.dialogueAction == 1000) {
				c.getPA().spellTeleport(2095, 3162, 0);
			}
			if (c.dialogueAction == 111) {
				c.getPA().enterCaves();
				c.sendMessage("Do not relog!");
				c.getPA().closeAllWindows();
				return;
			}
			if (c.dialogueAction == 11169) {
				int randomNumber = Misc.random(39);
				if (randomNumber == 39) {
		        	for (int j = 0; j < PlayerHandler.players.length; j++) {
		        		if (PlayerHandler.players[j] != null) {
		            			Client c2 = (Client)PlayerHandler.players[j];
		            		c2.sendMessage("<col=800000000><shad>[PVP]</shad></col> The Player <col=800000000>"+ c.playerName +"</col> has just entered <col=800000000>NARNIA</col>! Wow!");
		    				c.getPA().movePlayer(2621, 3266, 4);
		        		}
		        		}
		        	return;
				}
				c.sendMessage("<col=255><shad>Goodluck and do not forget, you will lose items here.");
				c.sendMessage("<col=255><shad>You can not use custom items here either.");
	        	for (int j = 0; j < PlayerHandler.players.length; j++) {
	        		if (PlayerHandler.players[j] != null) {
	            			Client c2 = (Client)PlayerHandler.players[j];
	            		c2.sendMessage("<col=800000000><shad>[PVP]</shad></col> The Player<col=800000000> "+ c.playerName +"</col> has just entered<col=800000000> PvP</col>!");
	            		c.getPA().movePlayer(3240, 3606, 0);
	        		}
	        		}
			}
				if (c.dialogueAction == 11170) {
					if (c.inWild()) {
						c.sendMessage("Please do not use this while inside the Wilderness.");
						c.getPA().closeAllWindows();
						return;
					}
					c.getPA().startTeleport(3190, 3958, 0, "modern");
					c.sendMessage("<col=800000000><shad>You see the pages shatter while your Abyssal Book slowly vanishes..");
					c.sendMessage("Exit by clicking one of the chests.");
					c.getItems().deleteItem(5520, 1);
		            c.Arma = 0;
					c.lastThieve = System.currentTimeMillis();
				return;
			}
				if (c.dialogueAction == 11171) {
					c.getPA().startTeleport(3110, 3592, 0, "modern");
					c.sendMessage("<col=800000000><shad>You teleport to the Abyssal Demons");
			}
				if (c.dialogueAction == 11172) {
					c.diceBanned = 1;
					c.lastAction = System.currentTimeMillis();
					c.sendMessage("<col=800000000><shad>Please come back in 15 minutes if you're serious about this.");
					c.sendMessage("<col=800000000><shad>This will restrict your account from ever gambling again.");
			}
				if (c.dialogueAction == 11173) {
					c.diceBanned = 2;
					c.sendMessage("<col=800000000><shad>Your account has now been restricted from gambling.");
			}
				if (c.dialogueAction == 11174) {
					c.ironDeath = 0;
					c.donatorChest -= 10;
					c.Jail = false;
					c.getPA().startTeleport(2387, 3488, 0, "modern");
					c.sendMessage("<col=800000000><shad>You paid 10 donator points for a new fresh start as Hardcore Iron Man!");
			}
			if (c.dialogueAction == 112) {
				c.getPA().enterNewCaves();
				// c.sendMessage("Disabled atm");
				c.getPA().closeAllWindows();
				return;
			}
			if (c.dialogueAction == 77) {
				if (c.hasHouse > 0) {
					c.sendMessage("You already have a house! Use a tablet to teleport to it!");
					return;
				}
				if (c.getItems().playerHasItem(995, 25000000)) {
					c.getDH().sendDialogues(29170, 4547);
					c.getItems().deleteItem(995, 25000000);
					c.vlsLeft2 = 10;
					c.SaveGame();
					break;
				} else {
					if (!c.getItems().playerHasItem(995, 25000000)) {
						c.sendMessage("You don't have any money!");
						c.getPA().closeAllWindows();
					}
				}
			}

			if (c.teleAction == 966) {
				c.getPA().spellTeleport(2928, 5203, 0);
				c.sendMessage("You must RE-LOG upon Exiting Nex.");
			}
			if (c.teleAction == 21) { // FLETCHING
				c.getPA().spellTeleport(2709, 3463, 0);
			}
			if (c.teleAction == 999) {
				c.getPA().spellTeleport(3300, 3302, 0);
				c.sendMessage("Have fun Mining!");
			}
			if (c.teleAction == 5) {
				c.getPA().spellTeleport(2741, 3444, 0);
				c.sendMessage("Flax Picking & Flax Stringing.");
				c.sendMessage("You can also craft amulets, buy the uncut's from the Crafter.");
			}
			if (c.teleAction == 22) { // wcing woodcutting
				c.getPA().spellTeleport(2710, 3462, 0);
			}
			if (c.dialogueAction == 510) {
				c.getDH().sendDialogues(1055, 278);
				return;
			}
			if (c.dialogueAction == 180) {
				c.stopMovement();
				c.gfx0(1173);
				c.gfx0(1390);
				c.teleportToX = 2976;
				c.teleportToY = 3239;
				c.sendMessage("<shad=9440238>Welcome to the driving Zone!");
				c.sendMessage("<shad=9440238>To leave, Simply Type ::leave");
				c.sendMessage("<shad=9440238>Have Fun.");
				c.npcId2 = 2221;
				c.isNpc = true;
				c.updateRequired = true;
				c.appearanceUpdateRequired = true;
				c.getPA().removeAllWindows();
				c.dialogueAction = -1;
				return;
			}
			if (c.dialogueAction == 120) {
				if (Config.ALLOWPINS) {
					if (!c.hasBankPin) {
						c.getBankPin().openPin();
					} else {
						c.sendMessage("You already have a bank pin!");
					}
				} else {
					c.sendMessage("You may not set a bank pin now!");
				}
				// }
				break;
			} else if (c.dialogueAction == 1) {
				int r = 4;
				// int r = Misc.random(3);
				switch (r) {
				case 0:
					c.getPA().movePlayer(3534, 9677, 0);
					break;

				case 1:
					c.getPA().movePlayer(3534, 9712, 0);
					break;

				case 2:
					c.getPA().movePlayer(3568, 9712, 0);
					break;

				case 3:
					c.getPA().movePlayer(3568, 9677, 0);
					break;
				case 4:
					c.getPA().movePlayer(3551, 9694, 0);
					break;
				}
			} else if (c.dialogueAction == 2) {
				c.getPA().movePlayer(2507, 4717, 0);
			} else if (c.dialogueAction == 5) {
				c.getSlayer().giveTask();
			} else if (c.dialogueAction == 6) {
				c.getSlayer().giveTask2();
			} else if (c.dialogueAction == 211) {
				c.sendMessage("Dialogue Closed");
			} else if (c.dialogueAction == 7) {
				c.getPA().startTeleport(3088, 3933, 0, "modern");
				c.sendMessage("NOTE: You are now in the wilderness...");
			} else if (c.dialogueAction == 50) {
				c.sendMessage("This is ");
			} else if (c.dialogueAction == 34) {
				c.sendMessage("Coming soon ");
			} else if (c.dialogueAction == 9999) {
				if (c.getItems().playerHasItem(5021, 250)) {
							c.getItems().deleteItem(5021, 250);
							c.getPA().startTeleport(2476, 4368, 0, "modern");//Mini-bosses
							c.sendMessage("<col=255>You have paid 250B and have been granted access to the mini-bosses!");
		} else {
			c.sendMessage("<col=255>You need 250B for this feature!");
		}
		} else if (c.dialogueAction == 8888) {
			c.getPA().startTeleport(2439, 4386, 0, "modern");
		} else if (c.dialogueAction == 9996) {
			if (c.ironGambler >= 1) {
				c.sendMessage("Sorry this feature is not available for Iron Beast modes.");
				return;
			}
				if (c.getItems().playerHasItem(5021, 400000)) {
					c.playerTitle = 60;
					c.sendMessage("<col=255>You purchased the Wealthy title. Relog to see the rank!");
					c.getItems().deleteItem(5021, 400000);
					c.getPA().closeAllWindows();
				}
		} else if (c.dialogueAction == 9997) {
			c.playerTitle = 59;
			c.sendMessage("<col=800000000>You chose for the Iron Baest mode. Goodluck!");
			c.getTutorial().handleTutorial();
		} else if (c.dialogueAction == 9998) {
			c.playerTitle = 58;
			c.sendMessage("<col=800000000>You chose for the Iron Beast mode. Goodluck!");
			c.getTutorial().handleTutorial();
		} else if (c.dialogueAction == 2995) {
			if (c.getItems().playerHasItem(5021, 500)) {
				c.getItems().deleteItem(5021, 500);
				TzhaarSpawn.SpawnNpcs(c);
			}
			} else if (c.dialogueAction == 8) {
				c.getPA().resetBarrows();
				c.sendMessage("Your barrows have been reset.");
			} else if (c.dialogueAction == 200) {
				c.getPA().enterElvarg();
				c.sendMessage("The dragon is soon home, pot up!!");
				// c.getPA().movePlayer(2855, 9637, c.playerId * 4);
			} else if (c.dialogueAction == 555) {
				c.getDH().sendDialogues(556, 553);
				c.sendMessage("You've now started Rune Mysteries Quest.");
			} else if (c.dialogueAction == 259) {
				c.getPA().removeAllWindows();
			} else if (c.nextChat == 999) {
				c.teleAction = 0;
				if (c.hasFollower > 0 && c.totalstored > -1) {
					for (int i = 0; i < 29; i++) {
						Server.itemHandler.createGroundItem(c, c.storeditems[i], c.absX, c.absY, 1, c.playerId);
						c.storeditems[i] = -1;
						c.occupied[i] = false;
						c.yak = false;
						c.totalstored = 0;
						c.summoningnpcid = 0;
						c.summoningslot = 0;
						c.yak = false;
					}
					c.sendMessage("Your BoB items have drop on the floor");
				} else {
					c.sendMessage("You do not have a npc currently spawned");
				}

			} else if (c.dialogueAction == 27) {
				c.getPA().movePlayer(3086, 3493, 0);
				c.monkeyk0ed = 0;
				c.Jail = false;
				c.forcedText = "I swear to god that i will never break the rules anymore!";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			}
			c.dialogueAction = 0;
			c.getPA().removeAllWindows();
			break;

		case 9158:
			if (c.dialogueAction == 1375) {//Leaving
				
				c.getTutorial().tutorialStage = -1;
				c.getTutorial().handleTutorial();
			} 
			if (c.teleAction == 60) {
				c.getPA().removeAllWindows();
				c.dialogueAction = -1;
			}

			if (c.dialogueAction == 222) {
				c.getPA().spellTeleport(2676, 3715, 0);
			}

			if (c.dialogueAction == 10235) {
				c.getPA().spellTeleport(3103, 3164, 3);
			}

			if (Prestige.prestigeChat == 1) {
				Prestige.prestigeChat = -1;
				Prestige.initiatePrestige(c);
				return;
			}
			if (c.dialogueAction == 111) {
				// c.getPA().enterNewCaves();
				c.getDH().sendDialogues(2222, 111);
				return;
			}
			if (c.dialogueAction == 11169) {
				c.getPA().closeAllWindows();
			}
			if (c.teleAction == 999) {
				c.getPA().spellTeleport(2857, 3431, 0);
			}
			if (c.teleAction == 21) { // Thieving
				c.getPA().spellTeleport(2662, 3308, 0);
				c.sendMessage("Pickpocket the NPC's untill you can train on stalls.");
			}
			if (c.dialogueAction == 1340) {
				if (System.currentTimeMillis() - c.emptyDelay >= 15000)
					;
				c.sendMessage("You decided not to empty your inventory!");
			}
			if (c.teleAction == 5) {
				c.getPA().spellTeleport(2604, 4772, 0);
				c.sendMessage("<shad=6081134>Sell the impling Jar's to the general shop for money");
				c.sendMessage("<shad=6081134>Buy a Butterfly Net from Tamayu if you dont have one");
			}
			if (c.teleAction == 22) { // Firemaking Fm tele
				// c.getPA().spellTeleport(2904,3463,0);
				c.sendMessage("You can firemake all over " + Config.SERVER_NAME + "!");
			}
			if (c.dialogueAction == 510) {
				c.getDH().sendDialogues(1061, 278);
				return;
			}
			if (c.dialogueAction == 50) {
				c.getPA().startTeleport(2559, 3089, 0, "modern");
				c.sendMessage("This is PVP!");
			} else if (c.dialogueAction == 1000) {
				c.getPA().closeAllWindows();
				ClickItem.flowerTime = 20;
			} else if (c.dialogueAction == 875) {
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 259) { // Drop item warning
				c.getPA().destroyItem(c.droppedItem);
				c.droppedItem = -1;
			} else if (c.dialogueAction == 186) {// Lottery
				Server.lottery.enterLottery(c);
				c.getPA().removeAllWindows();
			} else if (c.dialogueAction == 257) { // Dung leave warning
				c.getPA().resetDung();
				c.getPA().closeAllWindows();
			} else if (c.dialogueAction == 8591) { // NOMAD DIALOGUE
				if (c.Nomad == true) {
					c.sendMessage("You have already finished this minigame!");
					return;
				}
				c.getPA().enterNomad();
				c.sendMessage("<col=255>If you do not have 2 free slots you will not get anything!</col>");
				c.sendMessage("<col=255>NOMAD WILL SPAWN WITHIN 10 SECONDS, Pot up and activate prayers</col>");
				c.sendMessage("<col=255>IF YOU DIE YOU WILL LOOSE YOUR ITEMS!</col>");
				c.sendMessage("<col=255>To escape climb the ladder! Remember - Nomad has alot of hp</col>");
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 3664) { // ANGRY GOBLIN
				if (c.Goblin == true) {
					c.sendMessage("You have already finished this minigame!");
					return;
				}
				c.getPA().enterGoblin();
				c.sendMessage("<col=255>If you do not have 2 free slots you will not get anything!</col>");
				c.sendMessage("<col=255>The Goblin WILL SPAWN WITHIN 10 SECONDS, Pot up and activate prayers</col>");
				c.sendMessage("<col=255>If you die you will loose items!</col>");
				c.sendMessage("<col=255>Drink from couldrons to escape!</col>");
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 51) {
				c.getPA().startTeleport(3243, 3790, 0, "modern");
			} else if (c.dialogueAction == 51) {
				c.getPA().startTeleport(3243, 3790, 0, "modern");

			} else if (c.dialogueAction == 13) {
				c.getPA().spellTeleport(3202, 3859, 0);
				c.dialogueAction = -1;
			} else if (c.dialogueAction == 34) {
				c.getPA().removeAllWindows();
				c.dialogueAction = -1;
			}
			if (c.dialogueAction == 180) {
				c.getPA().removeAllWindows();
				c.dialogueAction = -1;
			}

			if (c.dialogueAction == 8) {
				c.getPA().fixAllBarrows();
			} else {
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
			}
			break;

		case 9159:
			if (c.dialogueAction == 51) {
				c.getPA().startTeleport(3351, 3659, 0, "modern");
			}
			break;
		case 107243:
			c.setSidebarInterface(4, 1644);
			break;

		case 107215:
			c.setSidebarInterface(11, 904);
			break;

		/** Specials **/
		case 29188:
			c.specBarId = 7636; // the special attack text - sendframe126(S P E
								// C I A L A T T A C K, c.specBarId);
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29163:
			c.specBarId = 7611;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 33033:
			c.specBarId = 8505;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29038:
			if (c.playerEquipment[c.playerWeapon] == 13902) {
				c.specBarId = 7486;
				c.usingSpecial = !c.usingSpecial;
				c.getItems().updateSpecialBar();
			} else {
				c.specBarId = 7486;
				/*
				 * if (c.specAmount >= 5) { c.attackTimer = 0;
				 * c.getCombat().attackPlayer(c.playerIndex); c.usingSpecial =
				 * true; c.specAmount -= 5; }
				 */
				// c.getCombat().handleGmaulPlayer();
				c.getItems().updateSpecialBar();
			}
			break;

		case 29063:
			if (c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
				c.gfx0(246);
				c.forcedChat("Raarrrrrgggggghhhhhhh!");
				c.startAnimation(1056);
				c.playerLevel[2] = c.getLevelForXP(c.playerXP[2]) + (c.getLevelForXP(c.playerXP[2]) * 15 / 100);
				c.getPA().refreshSkill(2);
				c.getItems().updateSpecialBar();
			} else {
				c.sendMessage("You don't have the required special energy to use this attack.");
			}
			break;

		case 48023:
			c.specBarId = 12335;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 30108:
			c.specBarId = 7812;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29138:
			if (c.playerEquipment[c.playerWeapon] == 15486) {
				if (c.getCombat().checkSpecAmount(c.playerEquipment[c.playerWeapon])) {
					c.gfx0(1958);
					c.SolProtect = 120;
					c.startAnimation(10518);
					c.getItems().updateSpecialBar();
					c.usingSpecial = !c.usingSpecial;
					c.sendMessage("All damage will be split into half for 1 minute.");
					c.forcedChat("I am Protected By the Light!");
					c.getPA().sendFrame126("@bla@S P E C I A L  A T T A C K", 7562);
				} else {
					c.sendMessage("You don't have the required special energy to use this attack.");
				}
			}
			c.specBarId = 7586;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29113:
			c.specBarId = 7561;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		case 29238:
			c.specBarId = 7686;
			c.usingSpecial = !c.usingSpecial;
			c.getItems().updateSpecialBar();
			break;

		/** Dueling **/
		case 26065: // no forfeit
			c.sendMessage("Forfeiting IN DUELS IS DISABLED DUE TO BUGS!!");
			c.sendMessage("Forfeiting IN DUELS IS DISABLED DUE TO BUGS!!");
			c.sendMessage("Forfeiting IN DUELS IS DISABLED DUE TO BUGS!!");
			c.sendMessage("Forfeiting IN DUELS IS DISABLED DUE TO BUGS!!");
			return;

		case 26040:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(0);
			break;

		case 26066: // no movement
		case 26048:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(1);
			break;

		case 26069: // no range
		case 26042:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(2);
			break;

		case 26070: // no melee
		case 26043:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(3);
			break;

		case 26071: // no mage
		case 26041:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(4);
			break;

		case 26072: // no drinks
		case 26045:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(5);
			break;

		case 26073: // no food
		case 26046:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(6);
			break;

		case 26074: // no prayer
		case 26047:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(7);
			c.getCombat().resetPrayers();
			c.getCurse().resetCurse();
			break;

		case 26076: // obsticals
		case 26075:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(8);
			break;

		case 2158: // fun weapons
			c.sendMessage("FUN WEAPONS IN DUELS ARE DISABLED DUE TO BUGS!!");
			c.sendMessage("FUN WEAPONS IN DUELS ARE DISABLED DUE TO BUGS!!");
			c.sendMessage("FUN WEAPONS IN DUELS ARE DISABLED DUE TO BUGS!!");
			c.sendMessage("FUN WEAPONS IN DUELS ARE DISABLED DUE TO BUGS!!");
			return;

		case 2157:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(9);
			break;

		case 30136: // sp attack
		case 30137:
			c.duelSlot = -1;
			c.getTradeAndDuel().selectRule(10);
			break;

		case 53245: // no helm
			c.duelSlot = 0;
			c.getTradeAndDuel().selectRule(11);
			break;

		case 53246: // no cape
			c.duelSlot = 1;
			c.getTradeAndDuel().selectRule(12);
			break;

		case 53247: // no ammy
			c.duelSlot = 2;
			c.getTradeAndDuel().selectRule(13);
			break;

		case 53249: // no weapon.
			c.duelSlot = 3;
			c.getTradeAndDuel().selectRule(14);
			break;

		case 53250: // no body
			c.duelSlot = 4;
			c.getTradeAndDuel().selectRule(15);
			break;

		case 53251: // no shield
			c.duelSlot = 5;
			c.getTradeAndDuel().selectRule(16);
			break;

		case 53252: // no legs
			c.duelSlot = 7;
			c.getTradeAndDuel().selectRule(17);
			break;

		case 53255: // no gloves
			c.duelSlot = 9;
			c.getTradeAndDuel().selectRule(18);
			break;

		case 53254: // no boots
			c.duelSlot = 10;
			c.getTradeAndDuel().selectRule(19);
			break;

		case 53253: // no rings
			c.duelSlot = 12;
			c.getTradeAndDuel().selectRule(20);
			break;

		case 53248: // no arrows
			c.duelSlot = 13;
			c.getTradeAndDuel().selectRule(21);
			break;

		case 26018:
		if (c.duelStatus == 5) {
			//c.sendMessage("This glitch has been fixed by Ardi, sorry sir.");
			return;
		}
		if(c.inDuelArena()) {
			Client o = (Client) Server.playerHandler.players[c.duelingWith];
			if(o == null) {
				c.getTradeAndDuel().declineDuel();
				o.getTradeAndDuel().declineDuel();
				return;
			}
				
			
			if(c.duelRule[2] && c.duelRule[3] && c.duelRule[4]) {
				c.sendMessage("You won't be able to attack the player with the rules you have set.");
				break;
			}
			c.duelStatus = 2;
			if(c.duelStatus == 2) {
				c.getPA().sendFrame126("Waiting for other player...", 6684);
				o.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			if(o.duelStatus == 2) {
				o.getPA().sendFrame126("Waiting for other player...", 6684);
				c.getPA().sendFrame126("Other player has accepted.", 6684);
			}
			
			if(c.duelStatus == 2 && o.duelStatus == 2) {
				c.canOffer = false;
				o.canOffer = false;
				c.duelStatus = 3;
				o.duelStatus = 3;
				c.getTradeAndDuel().confirmDuel();
				o.getTradeAndDuel().confirmDuel();
			}
		} else {
				Client o = (Client) Server.playerHandler.players[c.duelingWith];
				c.getTradeAndDuel().declineDuel();
				o.getTradeAndDuel().declineDuel();
				c.sendMessage("You can't stake out of Duel Arena.");
		}
		break;

		case 25120:
		if (c.duelStatus == 5) {
			//c.sendMessage("This glitch has been fixed by Ardi, sorry sir.");
			return;
		}
		if(c.inDuelArena()) {	
			if(c.duelStatus == 5) {
				break;
			}
			Client o1 = (Client) Server.playerHandler.players[c.duelingWith];
			if(o1 == null) {
				c.getTradeAndDuel().declineDuel();
				return;
			}

			c.duelStatus = 4;
			if(o1.duelStatus == 4 && c.duelStatus == 4) {				
				c.getTradeAndDuel().startDuel();
				o1.getTradeAndDuel().startDuel();
				o1.duelCount = 4;
				c.duelCount = 4;
				c.duelDelay = System.currentTimeMillis();
				o1.duelDelay = System.currentTimeMillis();
			} else {
				c.getPA().sendFrame126("Waiting for other player...", 6571);
				o1.getPA().sendFrame126("Other player has accepted", 6571);
			}
		} else {
				Client o1 = (Client) Server.playerHandler.players[c.duelingWith];
				c.getTradeAndDuel().declineDuel();
				o1.getTradeAndDuel().declineDuel();
				c.sendMessage("You can't stake out of Duel Arena.");
		}
		break;

		case 4169: // god spell charge
			c.usingMagic = true;
			if (!c.getCombat().checkMagicReqs(48)) {
				break;
			}

			if (System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE) {
				c.sendMessage("You still feel the charge in your body!");
				break;
			}
			c.godSpellDelay = System.currentTimeMillis();
			c.sendMessage("You feel charged with a magical power!");
			c.gfx100(c.MAGIC_SPELLS[48][3]);
			c.startAnimation(c.MAGIC_SPELLS[48][2]);
			c.usingMagic = false;
			break;

		case 28164: // item kept on death
			break;

		/*
		 * case 152: if (c.isRunning = true) { c.isRunning = false; }
		 * c.sendMessage("You're alredy walking.."); return; case 153:
		 * c.isRunning = c.isRunning; c.isRunning = true; break;
		 */
		case 152:
			c.isRunning = !c.isRunning;
			break;
		case 153:
			c.isRunning = !c.isRunning;
			break;

		case 9154:
			c.logout();
			break;

		case 82016:
			c.takeAsNote = !c.takeAsNote;
			break;

		// NEWSKILLTELESINTERFACE

		case 12132:
			c.getPA().spellTeleport(2741, 3443, 0);
			c.sendMessage("You Teleport To Camelot Flax Field.");
			break;

		case 12133:
			c.getPA().spellTeleport(3300, 3307, 0);
			c.sendMessage("You Teleport To The AL-Kharid Mining Pit.");
			break;

		case 12134:
			c.getPA().spellTeleport(2597, 3408, 0);
			c.sendMessage("You Teleport To The Fishing Guild.");
			break;

		case 12135:
			c.getPA().spellTeleport(2710, 3462, 0);
			c.sendMessage("You Teleport To Seers Village.");
			break;

		case 12136:
			c.getPA().spellTeleport(2812, 3463, 0);
			c.sendMessage("You Teleport To Catherby.");
			break;

		case 12137:
			c.getPA().spellTeleport(2479, 3434, 0);
			c.sendMessage("You Teleport To Tree Gnome Village.");
			break;

		// MORESKILLS
		case 12142:
			c.getPA().showInterface(27000);
			c.sendMessage("Select an altar.");
			break;

		case 12143:
			c.getPA().spellTeleport(3450, 3515, 0);
			c.sendMessage("You Teleport To The Swamp.");
			break;

		case 12144:
			c.getPA().spellTeleport(2533, 3304, 0);
			c.sendMessage("You Teleport To Dungeoneering.");
			break;

		case 12145:
			c.getPA().spellTeleport(2710, 3462, 0);
			c.sendMessage("You Teleport To Seers Village.");
			break;

		case 12146:
			c.getPA().spellTeleport(2662, 3308, 0);
			c.sendMessage("You Teleport To Ardougne, Pickpocket until you are high enough to thieve from stalls.");
			c.sendMessage("Alternative stalls can be found at home.");
			break;

		case 12147:
			c.getPA().closeAllWindows();
			break;

		// END

		// home teleports
		case 4171:
		case 50056:
		case 117048:
			c.getPA().startTeleport(2387, 3488, 0, "modern");
			break;

		case 4143:
		case 50245:
		case 117123:
			// c.getDH().sendOption5("Barb Assault", "Recipe for Disaster",
			// "Nomad & Angry Goblin", "Castle Wars", "Dominion Tower");
			// c.teleAction = 199;
			c.getPA().startTeleport(3222, 3218, 0, "modern");
			break;

		case 50253:
		case 117131:
		case 4146:
			c.getPA().startTeleport(2964, 3378, 0, "modern");
			break;

		case 51005:
		case 117154:
		case 4150:
			c.getPA().startTeleport(2757, 3477, 0, "modern");
			break;

		case 50235:
		case 4140:
		case 117112:
			c.getPA().startTeleport(3210, 3424, 0, "modern"); //Varrock Tele.
			break;

		case 51013:
		case 6004:
		case 117162:
			c.getPA().startTeleport(2662, 3305, 0, "modern");
			break;

		case 117186:
			c.getDH().sendOption5("Sea Troll Queen", "Lakhrahnaz", "Nomad", "Giant sea Snake", "Avatar of Destruction");
			c.teleAction = 8;
			break;
			
		case 6969: //mining
			c.getDH().sendOption4("Capto -250 ores-(Tier 1)", "Nation -750 ores-(Tier 2)", "Vortex -1500 ores-(Tier 3)", "Train -3 ores-(get XP");
			c.teleAction = 6969;
			break;

		case 51023:
		case 6005:
			c.getDH().sendOption5("Lumbridge", "Varrock", "Camelot", "Falador", "Canafis");
			c.teleAction = 20;
			break;

		case 51031:
		case 29031:
			c.sendMessage("Trollheim is not accessable.");
			break;

		case 9125: // Accurate
		case 6221: // range accurate
		case 22230: // kick (unarmed)
		case 48010: // flick (whip)
		case 21200: // spike (pickaxe)
		case 1080: // bash (staff)
		case 6168: // chop (axe)
		case 6236: // accurate (long bow)
		case 17102: // accurate (darts)
		case 8234: // stab (dagger)

		case 30088: // claws
		case 1177: // hammer
			c.fightMode = 0;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9126: // Defensive
		case 48008: // deflect (whip)
		case 22228: // punch (unarmed)
		case 21201: // block (pickaxe)
		case 1078: // focus - block (staff)
		case 6169: // block (axe)
		case 33019: // fend (hally)
		case 18078: // block (spear)
		case 8235: // block (dagger)
		case 1175: // accurate (darts)
		case 30089: // stab (dagger)
			c.fightMode = 1;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9127: // Controlled
		case 48009: // lash (whip)
		case 33018: // jab (hally)
		case 6234: // longrange (long bow)
		case 6219: // longrange
		case 18077: // lunge (spear)
		case 18080: // swipe (spear)
		case 18079: // pound (spear)
		case 17100: // longrange (darts)
			c.fightMode = 3;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		case 9128: // Aggressive
		case 6220: // range rapid
		case 22229: // block (unarmed)
		case 21203: // impale (pickaxe)
		case 21202: // smash (pickaxe)
		case 1079: // pound (staff)
		case 6171: // hack (axe)
		case 6170: // smash (axe)
		case 33020: // swipe (hally)
		case 6235: // rapid (long bow)
		case 17101: // repid (darts)
		case 8237: // lunge (dagger)
		case 30091: // claws
		case 1176: // stat hammer
		case 8236: // slash (dagger)

		case 30090: // claws
			c.fightMode = 2;
			if (c.autocasting)
				c.getPA().resetAutocast();
			break;

		/** Prayers **/
		/*
		 * case 87231: // thick skin if(c.trade11 > 1) { for(int p = 0; p <
		 * c.PRAYER.length; p++) { // reset prayer glows c.prayerActive[p] =
		 * false; c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0); } c.sendMessage(
		 * "You must wait 15 minutes before using this!"); c.trade11(); return;
		 * } c.getCurse().activateCurse(0); break; case 87233: // burst of str
		 * c.getCurse().activateCurse(1); break; case 87235: // charity of
		 * thought c.getCurse().activateCurse(2); break; case 87237: // range
		 * c.getCurse().activateCurse(3); break; case 87239: // mage
		 * c.getCurse().activateCurse(4); break; case 87241: // berserker
		 * if(c.altarPrayed == 0) { return; } c.getCurse().activateCurse(5);
		 * break; case 87243: // super human c.getCurse().activateCurse(6);
		 * break; case 87245: // improved reflexes
		 * c.getCurse().activateCurse(7); break; case 87247: //hawk eye
		 * c.getCurse().activateCurse(8); break; case 87249:
		 * c.getCurse().activateCurse(9); break; case 87251: // protect Item
		 * c.getCurse().activateCurse(10); break; case 87253: // 26 range
		 * c.getCurse().activateCurse(11); break; case 87255: // 27 mage
		 * c.getCurse().activateCurse(12); break; case 88001: // steel skin
		 * c.getCurse().activateCurse(13); break; case 88003: // ultimate str
		 * c.getCurse().activateCurse(14); break; case 88005: // incredible
		 * reflex c.getCurse().activateCurse(15); break; case 88007: // protect
		 * from magic c.getCurse().activateCurse(16); break; case 88009: //
		 * protect from range c.getCurse().activateCurse(17); break; case 88011:
		 * // protect from melee c.getCurse().activateCurse(18); break; case
		 * 88013: // 44 range c.getCurse().activateCurse(19); break; /**End of
		 * curse prayers**
		 * 
		 * 
		 * *Prayers** case 97168: // thick skin c.getCombat().activatePrayer(0);
		 * break; case 97170: // burst of str c.getCombat().activatePrayer(1);
		 * break; case 97172: // charity of thought
		 * c.getCombat().activatePrayer(2); break; case 97174: // range
		 * c.getCombat().activatePrayer(3); break; case 97176: // mage
		 * c.getCombat().activatePrayer(4); break; case 97178: // rockskin
		 * c.getCombat().activatePrayer(5); break; case 97180: // super human
		 * c.getCombat().activatePrayer(6); break; case 97182: // improved
		 * reflexes c.getCombat().activatePrayer(7); break; case 97184: //hawk
		 * eye c.getCombat().activatePrayer(8); break; case 97186:
		 * c.getCombat().activatePrayer(9); break; case 97188: // protect Item
		 * /*if(c.trade11 > 1) { for(int p = 0; p < c.PRAYER.length; p++) { //
		 * reset prayer glows c.prayerActive[p] = false;
		 * c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0); } c.sendMessage(
		 * "You must wait 15 minutes before using this!"); return; }*
		 * c.getCombat().activatePrayer(10); break; case 97190: // 26 range
		 * c.getCombat().activatePrayer(11); break; case 97192: // 27 mage
		 * c.getCombat().activatePrayer(12); break; case 97194: // steel skin
		 * c.getCombat().activatePrayer(13); break; case 97196: // ultimate str
		 * c.getCombat().activatePrayer(14); break; case 97198: // incredible
		 * reflex c.getCombat().activatePrayer(15); break; case 97200: //
		 * protect from magic c.getCombat().activatePrayer(16); break; case
		 * 97202: // protect from range c.getCombat().activatePrayer(17); break;
		 * case 97204: // protect from melee c.getCombat().activatePrayer(18);
		 * break; case 97206: // 44 range c.getCombat().activatePrayer(19);
		 * break; case 97208: // 45 mystic c.getCombat().activatePrayer(20);
		 * break; case 97210: // retrui c.getCombat().activatePrayer(21); break;
		 * case 97212: // redem c.getCombat().activatePrayer(22); break; case
		 * 97214: // smite c.getCombat().activatePrayer(23); break; case 97216:
		 * // chiv c.getCombat().activatePrayer(24); break; case 97218: // piety
		 * c.getCombat().activatePrayer(25); break;
		 */

		/** CURSE Prayers **/
		case 87231: // protect Item
			/*
			 * if(c.trade11 > 1) { for(int p = 0; p < c.PRAYER.length; p++) { //
			 * reset prayer glows c.prayerActive[p] = false;
			 * c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0); } c.sendMessage(
			 * "You must wait 15 minutes before using this!"); return; }
			 */
			c.getCurse().activateCurse(0);
			break;
		case 87233: // sap warrior
			c.getCurse().activateCurse(1);

			break;
		case 87235: // sap ranger
			c.getCurse().activateCurse(2);

			break;
		case 87237: // sap mage
			c.getCurse().activateCurse(3);
			break;
		case 87239: // sap spirit
			c.getCurse().activateCurse(4);

			break;
		case 87241: // berserker
			c.startAnimation(12589);
			c.gfx0(2266);
			c.getCurse().activateCurse(5);
			break;
		case 87243: // deflect summoning
			c.getCurse().activateCurse(6);
			break;
		case 87245: // deflect mage
			c.getCurse().activateCurse(7);
			break;
		case 87247: // deflect range
			c.getCurse().activateCurse(8);
			break;
		case 87249: // deflect meele
			c.getCurse().activateCurse(9);
			break;
		case 87251: // Leech attack
			c.getCurse().activateCurse(10);

			break;
		case 87253: // leech range
			c.getCurse().activateCurse(11);

			break;
		case 87255: // leech mage
			c.getCurse().activateCurse(12);
			break;
		case 88001: // leech def
			c.getCurse().activateCurse(13);

			break;
		case 88003: // leech str
			c.getCurse().activateCurse(14);

			break;
		case 88005: // leech run
			c.getCurse().activateCurse(15);
			break;
		case 88007: // leech spec
			c.getCurse().activateCurse(16);
			break;
		case 88009: // Wrath
			c.getCurse().activateCurse(17);
			break;
		case 88011: // SS
			c.getCurse().activateCurse(18);
			break;
		case 88013: // turmoil
			c.getCurse().activateCurse(19);
			break;
		/** End of curse prayers **/

		/** Prayers **/
		case 21233: // thick skin
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(0);
			break;
		case 21234: // burst of str
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(1);
			break;
		case 21235: // charity of thought
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(2);
			break;
		case 70080: // range
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(3);
			break;
		case 70082: // mage
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(4);
			break;
		case 21236: // rockskin
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(5);
			break;
		case 21237: // super human
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(6);
			break;
		case 21238: // improved reflexes
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(7);
			break;
		case 21239: // hawk eye
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(8);
			break;
		case 21240:
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(9);
			break;
		case 21241: // protect Item
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(10);
			break;
		case 70084: // 26 range
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(11);
			break;
		case 70086: // 27 mage
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(12);
			break;
		case 21242: // steel skin
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(13);
			break;
		case 21243: // ultimate str
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(14);
			break;
		case 21244: // incredible reflex
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(15);
			break;
		case 21245: // protect from magic
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(16);
			break;
		case 21246: // protect from range
			if (c.altarPrayed == 1) {
				return;
			}
			c.getCombat().activatePrayer(17);
			break;
		case 21247:// case 97204: // protect from melee
			c.getCombat().activatePrayer(18);
			break;
		case 70088:// case 97206: // 44 range
			c.getCombat().activatePrayer(19);
			break;
		case 70090:
			// case 97208: // 45 mystic
			c.getCombat().activatePrayer(20);
			break;
		// case 97210: // retrui
		case 2171:
			c.getCombat().activatePrayer(21);
			break;
		// case 97212: // redem
		case 2172:
			c.getCombat().activatePrayer(22);
			break;
		// case 97214: // smite
		case 2173:
			c.getCombat().activatePrayer(23);
			break;
		// case 97216: // chiv
		case 70092:
			c.getCombat().activatePrayer(24);
			break;
		// case 97218: // piety
		case 70094:
			c.getCombat().activatePrayer(25);
			break;

		case 13092:
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			Client ot = (Client) Server.playerHandler.players[c.tradeWith];
			if (ot == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}
			c.getPA().sendFrame126("Waiting for other player...", 3431);
			ot.getPA().sendFrame126("Other player has accepted", 3431);
			c.goodTrade = true;
			ot.goodTrade = true;

			for (GameItem item : c.getTradeAndDuel().offeredItems) {
				if (item.id > 0) {
					if (ot.getItems().freeSlots() < c.getTradeAndDuel().offeredItems.size()) {
						c.sendMessage(ot.playerName + " only has " + ot.getItems().freeSlots()
								+ " free slots, please remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots()) + " items.");
						ot.sendMessage(c.playerName + " has to remove "
								+ (c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots())
								+ " items or you could offer them "
								+ (c.getTradeAndDuel().offeredItems.size() - ot.getItems().freeSlots()) + " items.");
						c.goodTrade = false;
						ot.goodTrade = false;
						c.getPA().sendFrame126("Not enough inventory space...", 3431);
						ot.getPA().sendFrame126("Not enough inventory space...", 3431);
						break;
					} else {
						c.getPA().sendFrame126("Waiting for other player...", 3431);
						ot.getPA().sendFrame126("Other player has accepted", 3431);
						c.goodTrade = true;
						ot.goodTrade = true;
					}
				}
			}
			if (c.inTrade && !c.tradeConfirmed && ot.goodTrade && c.goodTrade) {
				c.tradeConfirmed = true;
				if (ot.tradeConfirmed) {
					c.getTradeAndDuel().confirmScreen();
					ot.getTradeAndDuel().confirmScreen();
					break;
				}

			}

			break;
		case 13218:
			if (System.currentTimeMillis() - c.lastButton < 400) {

				c.lastButton = System.currentTimeMillis();

				break;

			} else {

				c.lastButton = System.currentTimeMillis();

			}
			c.tradeAccepted = true;
			Client ot1 = (Client) Server.playerHandler.players[c.tradeWith];
			if (ot1.tradeWith != c.playerId) {
				return;
			}
			if (ot1 == null) {
				c.getTradeAndDuel().declineTrade();
				c.sendMessage("Trade declined as the other player has disconnected.");
				break;
			}

			if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed && !c.tradeConfirmed2) {
				c.tradeConfirmed2 = true;
				if (ot1.tradeConfirmed2) {
					c.acceptedTrade = true;
					ot1.acceptedTrade = true;
					c.getTradeAndDuel().giveItems();
					ot1.getTradeAndDuel().giveItems();
					
					c.getTradeAndDuel().resetTrade();
					ot1.getTradeAndDuel().resetTrade();
					
					c.sendMessage("Trade accepted.");
					c.SaveGame();
					ot1.SaveGame();
					ot1.sendMessage("Trade accepted.");
					break;
				}
				ot1.getPA().sendFrame126("Other player has accepted.", 3535);
				c.getPA().sendFrame126("Waiting for other player...", 3535);
			}

			break;
		/*
		 * case 13218: if (System.currentTimeMillis() - c.lastButton < 400) {
		 * 
		 * c.lastButton = System.currentTimeMillis();
		 * 
		 * break;
		 * 
		 * } else {
		 * 
		 * c.lastButton = System.currentTimeMillis();
		 * 
		 * } c.tradeAccepted = true; Client ot1 = (Client)
		 * Server.playerHandler.players[c.tradeWith];
		 * if(TradeAndDuel.twoTraders(c, ot1)) { if (ot1 == null) {
		 * c.getTradeAndDuel().declineTrade(); c.sendMessage(
		 * "Trade declined as the other player has disconnected." ); break; }
		 * 
		 * if (c.inTrade && c.tradeConfirmed && ot1.tradeConfirmed &&
		 * !c.tradeConfirmed2) { c.tradeConfirmed2 = true;
		 * if(ot1.tradeConfirmed2) { c.acceptedTrade = true; ot1.acceptedTrade =
		 * true; c.getTradeAndDuel().giveItems();
		 * ot1.getTradeAndDuel().giveItems(); c.sendMessage("Trade accepted.");
		 * c.SaveGame(); ot1.SaveGame(); ot1.sendMessage("Trade accepted.");
		 * break; } ot1.getPA().sendFrame126("Other player has accepted.",
		 * 3535); c.getPA().sendFrame126("Waiting for other player...", 3535); }
		 * } else { //Trading.declineTrade(c, true);
		 * c.getTradeAndDuel().declineTrade(); c.sendMessage(
		 * "You can't trade two people at once!"); ot1.sendMessage(
		 * "You can't trade two people at once!"); }
		 * 
		 * break;
		 */
		/* Rules Interface Buttons */
		case 125011: // Click agree
			if (!c.ruleAgreeButton) {
				c.ruleAgreeButton = true;
				c.getPA().sendFrame36(701, 1);
			} else {
				c.ruleAgreeButton = false;
				c.getPA().sendFrame36(701, 0);
			}
			break;
		case 67100:// Accept
			c.getPA().showInterface(3559);
			c.newPlayer = false;
			c.sendMessage("You need to click on you agree before you can continue on.");
			break;
		case 67103:// Decline
			c.sendMessage("You have chosen to decline, Client will be disconnected from the server.");
			break;
		/* End Rules Interface Buttons */
		/* Player Options */
		case 74176:
			if (!c.mouseButton) {
				c.mouseButton = true;
				c.getPA().sendFrame36(500, 1);
				c.getPA().sendFrame36(170, 1);
			} else if (c.mouseButton) {
				c.mouseButton = false;
				c.getPA().sendFrame36(500, 0);
				c.getPA().sendFrame36(170, 0);
			}
			break;
		case 74184:
			if (!c.splitChat) {
				c.splitChat = true;
				c.getPA().sendFrame36(502, 1);
				c.getPA().sendFrame36(287, 1);
			} else {
				c.splitChat = false;
				c.getPA().sendFrame36(502, 0);
				c.getPA().sendFrame36(287, 0);
			}
			break;
		case 100231:
			if (!c.chatEffects) {
				c.chatEffects = true;
				c.getPA().sendFrame36(501, 1);
				c.getPA().sendFrame36(171, 0);
			} else {
				c.chatEffects = false;
				c.getPA().sendFrame36(501, 0);
				c.getPA().sendFrame36(171, 1);
			}
			break;
		case 100237:
			//AchievementManager.increase(c, Achievements.HELPME);
			if (!c.acceptAid) {
				c.acceptAid = true;
				c.getPA().sendFrame36(503, 1);
				c.getPA().sendFrame36(427, 1);
			} else {
				c.acceptAid = false;
				c.getPA().sendFrame36(503, 0);
				c.getPA().sendFrame36(427, 0);
			}
			break;
		case 74201:// brightness1
			c.getPA().sendFrame36(505, 1);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 1);
			break;
		case 74203:// brightness2
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 1);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 2);
			break;

		case 74204:// brightness3
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 1);
			c.getPA().sendFrame36(508, 0);
			c.getPA().sendFrame36(166, 3);
			break;

		case 74205:// brightness4
			c.getPA().sendFrame36(505, 0);
			c.getPA().sendFrame36(506, 0);
			c.getPA().sendFrame36(507, 0);
			c.getPA().sendFrame36(508, 1);
			c.getPA().sendFrame36(166, 4);
			break;
		case 74206:// area1
			c.getPA().sendFrame36(509, 1);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74207:// area2
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 1);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74208:// area3
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 1);
			c.getPA().sendFrame36(512, 0);
			break;
		case 74209:// area4
			c.getPA().sendFrame36(509, 0);
			c.getPA().sendFrame36(510, 0);
			c.getPA().sendFrame36(511, 0);
			c.getPA().sendFrame36(512, 1);
			break;
		case 168:
			c.startAnimation(855);
			break;
		case 169:
			c.startAnimation(856);
			break;
		case 162:
			c.startAnimation(857);
			break;
		case 164:
			c.startAnimation(858);
			break;
		case 165:
			c.startAnimation(859);
			break;
		case 161:
			c.startAnimation(860);
			break;
		case 170:
			c.startAnimation(861);
			break;
		case 171:
			c.startAnimation(862);
			break;
		case 163:
			c.startAnimation(863);
			break;
		case 167:
			c.startAnimation(864);
			break;
		case 172:
			c.startAnimation(865);
			break;
		case 166:
			c.startAnimation(866);
			break;
		case 52050:
			c.startAnimation(2105);
			break;
		case 52051:
			c.startAnimation(2106);
			break;
		case 52052:
			c.startAnimation(2107);
			break;
		case 52053:
			c.startAnimation(2108);
			break;
		case 52054:
			c.startAnimation(2109);
			break;
		case 52055:
			c.startAnimation(2110);
			break;
		case 52056:
			c.startAnimation(2111);
			break;
		case 52057:
			c.startAnimation(2112);
			break;
		case 52058:
			c.startAnimation(2113);
			break;
		case 43092:
			c.startAnimation(0x558);
			c.stopMovement();
			c.gfx0(574);
			break;
		case 2155:
			c.startAnimation(0x46B);
			break;
		case 25103:
			c.startAnimation(0x46A);
			break;
		case 25106:
			c.startAnimation(0x469);
			break;
		case 2154:
			c.startAnimation(0x468);
			break;
		case 52071:
			c.startAnimation(0x84F);
			break;
		case 52072:
			c.startAnimation(0x850);
			break;
		case 59062:
			c.startAnimation(4280);
			break;
		case 72032:
			c.startAnimation(3544);
			break;
		case 72033:
			c.startAnimation(4278);
			break;
		case 72254:
			c.startAnimation(4275);
			break;
		case 72255:
			c.startAnimation(6111);
			c.stopMovement();
			break;
		case 88058:
			c.startAnimation(7531);
			c.stopMovement();
			break;
		case 88062:
			c.startAnimation(10530);
			c.stopMovement();
			c.gfx0(1864);
			break;
		case 88063:
			c.startAnimation(11044);
			c.stopMovement();
			c.gfx0(1973);
			break;
		case 88060:
			c.startAnimation(8770);
			c.stopMovement();
			c.gfx0(1553);
			break;
		case 88061:
			c.startAnimation(9990);
			c.stopMovement();
			c.gfx0(1734);
			break;
		case 73004:
			c.startAnimation(7272);
			c.stopMovement();
			c.gfx0(1244);
			break;
		case 88059:
			if (System.currentTimeMillis() - c.logoutDelay < 8000) {
				c.sendMessage("You cannot do this emote in combat!");
				return;
			}
			c.startAnimation(2414);
			//AchievementManager.increase(c, Achievements.GUITAR);
			c.stopMovement();
			c.gfx0(1537);
			break;
		case 88064: // turkey
			c.startAnimation(10994);
			break;
		case 73003:
			c.startAnimation(2836);
			c.stopMovement();
			break;
		case 73000:
			if (System.currentTimeMillis() - c.logoutDelay < 8000) {
				c.sendMessage("You cannot do skillcape emotes in combat!");
				return;
			}
			c.startAnimation(3543);
			c.stopMovement();
			break;
		case 73001:
			c.startAnimation(3544);
			c.stopMovement();
			break;
		case 88066:
			c.startAnimation(12658);
			c.gfx0(780);
			c.stopMovement();
			break;
		case 88065:
			c.startAnimation(11542);
			c.gfx0(2037);
			c.stopMovement();
			break;
		/* END OF EMOTES */
		case 28166:

			break;
		case 118098:
			c.getPA().castVeng();
			break;

		case 27209:
			c.forcedText = "[QC] My Slayer level is  " + c.getPA().getLevelForXP(c.playerXP[18]) + ".";
			c.sendMessage("I must slay another " + c.taskAmount + " " + Server.npcHandler.getNpcListName(c.slayerTask));
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;

		case 27211:
			c.forcedText = "[QC] My Hunter level is  " + c.getPA().getLevelForXP(c.playerXP[21]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27190:
			c.forcedText = "[QC] My Attack level is  " + c.getPA().getLevelForXP(c.playerXP[0]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27193:
			c.forcedText = "[QC] My Strength level is  " + c.getPA().getLevelForXP(c.playerXP[2]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27196:
			c.forcedText = "[QC] My Defence level is  " + c.getPA().getLevelForXP(c.playerXP[1]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27191:
			c.forcedText = "[QC] My Hitpoints level is  " + c.getPA().getLevelForXP(c.playerXP[3]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27199:
			c.forcedText = "[QC] My Range level is  " + c.getPA().getLevelForXP(c.playerXP[4]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27202:
			c.forcedText = "[QC] My Prayer level is  " + c.getPA().getLevelForXP(c.playerXP[5]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27205:
			c.forcedText = "[QC] My Magic level is  " + c.getPA().getLevelForXP(c.playerXP[6]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27201:
			c.forcedText = "[QC] My Cooking level is  " + c.getPA().getLevelForXP(c.playerXP[7]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27207:
			c.forcedText = "[QC] My Woodcutting level is  " + c.getPA().getLevelForXP(c.playerXP[8]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27206:
			c.forcedText = "[QC] My Fletching level is  " + c.getPA().getLevelForXP(c.playerXP[9]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27198:
			c.forcedText = "[QC] My Fishing level is  " + c.getPA().getLevelForXP(c.playerXP[10]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27204:
			c.forcedText = "[QC] My Firemaking level is  " + c.getPA().getLevelForXP(c.playerXP[11]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27203:
			c.forcedText = "[QC] My Crafting level is  " + c.getPA().getLevelForXP(c.playerXP[12]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27195:
			c.forcedText = "[QC] My Smithing level is  " + c.getPA().getLevelForXP(c.playerXP[13]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27192:
			c.forcedText = "[QC] My Mining level is  " + c.getPA().getLevelForXP(c.playerXP[14]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27197:
			c.forcedText = "[QC] My Herblore level is  " + c.getPA().getLevelForXP(c.playerXP[15]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27194:
			c.forcedText = "[QC] My Agility level is  " + c.getPA().getLevelForXP(c.playerXP[16]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27200:
			c.forcedText = "[QC] My Thieving level is  " + c.getPA().getLevelForXP(c.playerXP[17]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27210:
			c.forcedText = "[QC] My Farming level is  " + c.getPA().getLevelForXP(c.playerXP[19]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 27208:
			c.forcedText = "[QC] My Runecrafting level is  " + c.getPA().getLevelForXP(c.playerXP[20]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 74240:
			c.forcedText = "[Quick Chat] My Summoning level is  " + c.getPA().getLevelForXP(c.playerXP[21]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 74239:
			c.forcedText = "[Quick Chat] My Hunter level is  " + c.getPA().getLevelForXP(c.playerXP[22]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 74238:
			c.forcedText = "[Quick Chat] My Construction level is  " + c.getPA().getLevelForXP(c.playerXP[23]) + ".";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			break;
		case 74241:
			if (c.playerXP[24] >= 0 && c.playerXP[24] <= 14391160) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is  " + c.getPA().getLevelForXP(c.playerXP[24])
						+ ".";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 14391161 && c.playerXP[24] <= 15889108) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 100.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 15889109 && c.playerXP[24] <= 17542976) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 101.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 17542977 && c.playerXP[24] <= 19368991) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 102.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 19368992 && c.playerXP[24] <= 21385072) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 103.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 21385073 && c.playerXP[24] <= 23611005) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 104.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 23611006 && c.playerXP[24] <= 26068631) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 105.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 26068632 && c.playerXP[24] <= 28782068) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 106.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 28782069 && c.playerXP[24] <= 31777942) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 107.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 31777943 && c.playerXP[24] <= 35085653) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 108.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 35085654 && c.playerXP[24] <= 38737660) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 109.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 38737661 && c.playerXP[24] <= 42769799) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 110.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 42769800 && c.playerXP[24] <= 47221639) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 111.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 47221640 && c.playerXP[24] <= 52136868) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 112.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 52136869 && c.playerXP[24] <= 57563717) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 113.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 57563718 && c.playerXP[24] <= 63555442) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 114.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 63555443 && c.playerXP[24] <= 70170839) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 115.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 70170840 && c.playerXP[24] <= 77474827) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 116.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 77474828 && c.playerXP[24] <= 85539081) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 117.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 85539082 && c.playerXP[24] <= 94442735) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 118.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 94442736 && c.playerXP[24] <= 104273166) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 119.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			} else if (c.playerXP[24] >= 104273167 && c.playerXP[24] <= 200000000) {
				c.forcedText = "[Quick Chat] My Dungeoneering level is 120.";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
			}
		case 77036:
			if (c.hasFollower > 0 && c.totalstored > -1) {
				for (int i = 0; i < 29; i += 1) {
					Server.itemHandler.createGroundItem(c, c.storeditems[i], c.absX, c.absY, 1, c.playerId);
					c.firstslot();
					c.storeditems[i] = -1;
					c.occupied[i] = false;
					c.yak = false;
					c.totalstored = 0;
					c.summoningnpcid = 0;
					c.summoningslot = 0;
					c.yak = false;
					c.sendMessage("Your BoB items have drop on the floor");
				}
			} else {
				c.sendMessage("You do not have a npc currently spawned");
			}

		case 66156:
			if (!c.InDung) {
				c.getPA().closeAllWindows();
				c.sendMessage("Please re-enter Dungeoneering.");
				return;
			}
			if (c.hasChoosenDung == true) {
				return;
			}
			if (c.playerLevel[6] <= 9) {
				c.sendMessage("You must be 10+ Magic To Choose Magic Class");
				return;
			}
			c.getItems().addItem(19893, 1);
			c.getItems().addItem(19892, 1);
			c.getItems().addItem(15786, 1);
			c.getItems().addItem(15797, 1);
			c.getItems().addItem(15837, 1);
			c.getItems().addItem(15892, 1);
			c.getItems().addItem(16185, 1);
			c.getItems().addItem(16153, 1);
			c.getItems().addItem(391, 3);
			c.getItems().addItem(554, 10000);
			c.getItems().addItem(555, 10000);
			c.getItems().addItem(556, 10000);
			c.getItems().addItem(557, 10000);
			c.getItems().addItem(558, 10000);
			c.getItems().addItem(559, 10000);
			c.getItems().addItem(560, 10000);
			c.needstorelog = true;
			c.getItems().addItem(561, 10000);
			c.getItems().addItem(562, 10000);
			c.getItems().addItem(563, 10000);
			c.getItems().addItem(565, 10000);
			c.getItems().addItem(564, 10000);
			c.getItems().addItem(566, 10000);
			c.getItems().addItem(2434, 1);
			c.getItems().addItem(3040, 1);
			c.playerMagicBook = 1;
			c.setSidebarInterface(6, 12855);
			c.getPA().closeAllWindows();
			c.isSkulled = true;
			c.gwdelay = 1000;
			c.sendMessage("<shad=6081134>[Dungeoneering] <shad=15695415> Magic Equipment chosen.");
			c.isChoosingDung = false;
			c.hasChoosenDung = true;
			break;
		case 66157:
			if (!c.InDung) {
				c.getPA().closeAllWindows();
				c.sendMessage("Please re-enter Dungeoneering.");
				return;
			}
			if (c.hasChoosenDung == true) {
				return;
			}
			c.getItems().addItem(15808, 1);
			c.getItems().addItem(15914, 1);
			c.getItems().addItem(15925, 1);
			c.getItems().addItem(15936, 1);
			c.getItems().addItem(16013, 1);
			c.getItems().addItem(16035, 1);
			c.getItems().addItem(16127, 1);
			c.getItems().addItem(16262, 1);
			c.getItems().addItem(19893, 1);
			c.getItems().addItem(19892, 1);
			c.getItems().addItem(391, 3);
			c.getItems().addItem(2434, 1);
			c.getItems().addItem(2440, 1);
			c.getItems().addItem(2442, 1);
			c.getItems().addItem(2436, 1);
			c.getItems().addItem(2503, 1);
			c.getItems().addItem(2497, 1);
			c.getItems().addItem(4587, 1);
			c.getPA().closeAllWindows();
			c.needstorelog = true;
			c.isSkulled = true;
			c.gwdelay = 1000;
			c.sendMessage("<shad=6081134>[Dungeoneering] <shad=15695415> Melee Equipment chosen.");
			c.isChoosingDung = false;
			c.hasChoosenDung = true;
			// }
			// }
			break;
		case 16106:
			c.getPA().closeAllWindows();
			break;
		case 66158:
			if (!c.InDung) {
				c.getPA().closeAllWindows();
				c.sendMessage("Please re-enter Dungeoneering.");
				return;
			}
			if (c.hasChoosenDung == true) {
				return;
			}
			if (c.playerLevel[4] <= 9) {
				c.sendMessage("You must be 10+ Ranged To Choose Ranged Class");
				return;
			}
			c.getItems().addItem(16002, 1);
			c.getItems().addItem(16046, 1);
			c.needstorelog = true;
			c.getItems().addItem(16057, 1);
			c.getItems().addItem(16068, 1);
			c.getItems().addItem(16105, 1);
			c.getItems().addItem(19893, 1);
			c.getItems().addItem(19892, 1);
			c.getItems().addItem(861, 1);
			c.getItems().addItem(892, 10000);
			c.getItems().addItem(397, 3);
			c.getItems().addItem(3024, 1);
			c.getItems().addItem(2444, 1);
			c.getItems().addItem(2503, 1);
			c.getItems().addItem(2497, 1);

			c.isSkulled = true;
			c.gwdelay = 1000;
			c.getPA().closeAllWindows();
			c.sendMessage("<shad=6081134>[Dungeoneering] <shad=15695415> Ranged Equipment chosen.");
			c.isChoosingDung = false;
			c.hasChoosenDung = true;
			break;
		// Dungeoneering finish
		// case 164025:
		// c.getPA().showInterface(14040);
		// godwars
		// c.sendMessage("If there is no floor/maps are gone RESTART CLIENT!");
		// c.getPA().spellTeleport(2882, 5310, 2);
		// c.getPA().spellTeleport(2909, 3613, 0);
		// //c.getPA().sendMp3("Music");
		// c.getDH().sendDialogues(5555, 1);
		// c.sendMessage("Nex currently disabled / will be back soon!");
		// break;
		case 177206:
			c.getPA().spellTeleport(3007, 3849, 0);
			break;
		case 177209:
			c.getPA().spellTeleport(1910, 4367, 0);
			break;
		case 177212:
			c.getPA().spellTeleport(2717, 9805, 0);
			break;
		case 177221:
			c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151
					: c.playerMagicBook == 1 ? 12855 : c.playerMagicBook == 2 ? 29999 : 12855);
			break;
		case 176177:
			c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151
					: c.playerMagicBook == 1 ? 12855 : c.playerMagicBook == 2 ? 29999 : 12855);
			break;
		case 178065:
			c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151
					: c.playerMagicBook == 1 ? 12855 : c.playerMagicBook == 2 ? 29999 : 12855);
			break;
		case 178034:
			c.getPA().spellTeleport(2539, 4716, 0);
			break;
		case 178050:
			c.getPA().spellTeleport(3243, 3517, 0);
			break;
		case 178053:
			c.getPA().spellTeleport(3158, 3667, 0);
			break;
		case 178056:
			c.getPA().spellTeleport(3088, 3523, 0);
			break;
		case 178059:
			c.getPA().spellTeleport(3344, 3667, 0);
			break;
		case 176162:
			c.getPA().spellTeleport(3565, 3314, 0);
			break;
		case 176168:
			c.getPA().spellTeleport(2438, 5172, 0);
			break;
		case 176146:
			c.getPA().startTeleport(3312, 3235, 0, "modern");
			//AchievementManager.increase(c, Achievements.ARENA);
			break;
		case 176165:
			c.getPA().spellTeleport(2662, 2650, 0);
			break;
		case 176171:
			c.getPA().spellTeleport(2865, 3546, 0);
			// c.sendMessage("Warriors Guild Will be released in V4.");
			break;
		case 176246:
			c.getPA().spellTeleport(2676, 3715, 0);
			break;

		case 10252:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(0);
			}
			break; // Attack

		case 10253:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(2);
			}
			break; // Strength
		case 10254:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(4);
			}
			break; // Range

		case 10255:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(6);
			}

			break; // Magic

		case 11000:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(1);
			}

			break; // Defence

		case 11006:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(12);
			}

			break; // Crafting

		case 11001:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(3);
			}

			break; // Constitution

		case 11002:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(5);
			}

			break; // Prayer

		case 11003:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(16);
			}

			break; // Agility

		case 11004:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(15);
			}

			break; // Herblore
		case 11005:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(17);
			}

			break; // Thieving
		case 11010:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(10);
			}

			break; // Fishing
		case 11007:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(20);
			}

			break;
		case 47002:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(18);
			}

			break;
		case 54090:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(19);
			}

			break;
		case 11008:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(14);
			}

			break;
		case 11009:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(13);
			}

			break;
		case 11011:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(7);
			}

			break;
		case 11012:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(11);
			}

			break;
		case 11013:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(8);
				break;
			}
		case 11014:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().dragonkinFormula(9);
			}
			break;

		case 11015:
			if (System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getPA().removeAllWindows();
			}
			break;

		case 177006:
			c.getPA().spellTeleport(2884, 9798, 0);
			break;
		case 177009:
			c.getPA().spellTeleport(2710, 9466, 0);
			break;
		case 177012:
			c.getPA().spellTeleport(3428, 3527, 0);
			break;
		case 177015:
			c.getPA().spellTeleport(3117, 9847, 0);
			break;
		case 177021:
			c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151
					: c.playerMagicBook == 1 ? 12855 : c.playerMagicBook == 2 ? 29999 : 12855);
			break;
		case 177215:
			c.getPA().spellTeleport(3303, 9375, 0);
			break;

		case 69009:
			if (c.playerMagicBook == 0) {
				c.setSidebarInterface(6, 1151); // modern
			} else if (c.playerMagicBook == 1) {
				c.setSidebarInterface(6, 12855); // ancient
			} else {
				c.setSidebarInterface(6, 29999);
			}
			break;

		case 24017:
			c.getPA().resetAutocast();
			// c.sendFrame246(329, 200, c.playerEquipment[c.playerWeapon]);
			c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon],
					c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
			// c.setSidebarInterface(0, 328);
			// c.setSidebarInterface(6, c.playerMagicBook == 0 ? 1151 :
			// c.playerMagicBook == 1 ? 12855 : 1151);
			break;
		}
		if (c.isAutoButton(actionButtonId))
			c.assignAutocast(actionButtonId);
	}

}
