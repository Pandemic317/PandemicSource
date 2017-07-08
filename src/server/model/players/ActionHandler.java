package server.model.players;

import server.Config;
import server.model.players.skills.Thieving;
import server.Server;
import  server.model.minigames.Event;
import  server.model.minigames.EventContainer;
import  server.model.minigames.EventManager;
import server.model.minigames.Sailbyhack3r;
import server.model.objects.Object;
import server.model.players.skills.ConstructionEvents;
import server.model.players.skills.Dungeoneering;
import server.model.players.skills.Mining;
import server.model.players.skills.Woodcutting;
import server.util.Misc;
import server.util.ScriptManager;
@SuppressWarnings("all")
public class ActionHandler {

	private Client c;
	int[] donatorRitem = { 12435, 4151, 15441, 15442, 15443, 15444, 15241, 4753, 4755, 
							4757, 4759, 14595, 14603, 14602, 14605, 11235, 989, 19337, 
							19341, 19336, 19934, 9926, 19935, 13822, 15488, 13225, 13832, 
							15502, 15503, 15504, 15492, 14484, 14490, 14492, 14494, 14479, 
							7319, 1048, 7158, 18360, 18365, 18371, 18373, 18349, 18347, 
							17361, 17355, 17349, 17351, 17259, 17257, 17255, 17253};

	public int donatorRitem() {
		return donatorRitem[(int) (Math.random() * donatorRitem.length)];
	}
	int[] randomGambler = { 10832, 10832, 10832, 10832, 10832, 10832, 10832, 10832, 10832, 10832, 
			10832, 10832, 10832, 10832, 10833, 10833, 10833, 10833, 10833, 10833, 10834, 10834 };

public int randomGambler() {
return randomGambler[(int) (Math.random() * randomGambler.length)];
}


	public ActionHandler(Client Client) {
		this.c = Client;
	}

	int[] PvpItems = { 14876, 14877, 14878, 14879, 14880, 14881, 14882, 14883, 14884, 14885, 14886, 14888, 14889, 14890, 14891, 14892 };
	int[] PvpPrices = { 10000000, 1000000, 500000, 35000, 800000,150000, 280000, 840000, 150000, 125000, 80000, 5000000, 240000, 108700, 200000, 284000 };

    public void wildyditch() {
			{
		if (c.absY <= c.objectY){
			
			c.startAnimation(6132);
			c.getPA().walkTo(0, +3);
			} else if (c.objectY < c.absY) {
			c.startAnimation(6132);
					c.getPA().walkTo(0, -3);
					} 
			}
	}

	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;

		if (Woodcutting.playerTrees(c, objectType)) {
			Woodcutting.attemptData(c, objectType, obX, obY);
			return;
		}

		if (Mining.miningRocks(c, objectType)) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		}

		switch(objectType) {
		case 2566:
			c.getPA().movePlayer(2387, 3488, 0);
			break;
		case 5167:
			if (c.donatorChest <= 9) {
				c.sendMessage("<shad>You need at least 10 donator points to get a new try. You will not lose any progress,");
				c.sendMessage("<shad>You will simply be put back into Pandemic with the same stats, items and npc kills.");
			} else {
				c.getDH().sendOption2("Yeah I wanna pay 10 donator points for new access!", "Nevermind");
				c.dialogueAction = 11174;
			}
			break;
		case 369:
			c.getDH().sendOption2("I know I can lose my stuff when I die here", "Nevermind");
			c.dialogueAction = 11169;
			break;
		case 18266:
			if (c.playerEquipment[c.playerCape] == 896 || c.playerEquipment[c.playerCape] == 19709) {
				c.sendMessage("You can not enter this minigame with an Owners or Master Cape!");
				return;
			}
			c.getPA().movePlayer(3165, 9758, c.playerId * 4);
			c.waveId = 0;
			c.dominionToKill = -1;
			c.dominionKilled = -1;
			Server.dominionTower.spawnNextWave(c);
			break;
		case 2672:
			if (obX == 3180 && obY == 3403) {
				if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("<col=255>You need at least 3 free inventory spots!");
			return;
			}
				if (c.captoSmith > 0) {
					c.sendMessage("<col=255>You have already smith this armour set. You can only smith one set.");
				return;
				}
			if (c.playerLevel[c.playerSmithing] < 100) {
				c.sendMessage("<col=255>You need at least 100 smithing to smith a Capto set!");
			return;
			}
			if (c.getItems().playerHasItem(9632, 250)) {
			c.startAnimation(898);
			c.getItems().deleteItem(9632, 250);
			c.getItems().addItem(8810, 1);
			c.getItems().addItem(8811, 1);
			c.getItems().addItem(8812, 1);
			c.captoSmith ++;
			c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 25, c.playerSmithing);
			c.sendMessage("<col=255>You successfully smith a Capto armour set!");//smithing
			} else {
			c.sendMessage("<col=255>You need at least 1000 daeyalt ores to smith a Capto set!");	
			}
			}
			if (obX == 3181 && obY == 3403) {
			if (c.getItems().freeSlots() <= 3) {
				c.sendMessage("<col=255>You need at least 3 free inventory spots!");
			return;
			}
			if (c.nationSmith > 0) {
				c.sendMessage("<col=255>You have already smith this armour set. You can only smith one set.");
			return;
			}
			if (c.playerLevel[c.playerSmithing] < 110) {
				c.sendMessage("<col=255>You need at least 110 smithing to smith a Nation set!");
			return;
			}
			if (c.getItems().playerHasItem(9632, 250)) {
			c.startAnimation(898);
			c.getItems().deleteItem(9632, 750);
			c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 25, c.playerSmithing);
			c.getItems().addItem(8813, 1);
			c.getItems().addItem(8814, 1);
			c.getItems().addItem(8815, 1);
			c.nationSmith ++;
			c.sendMessage("<col=255>You successfully smith a Nation armour set!");//smithing
			} else {
			c.sendMessage("<col=255>You need at least 1250 daeyalt ores to smith a Nation set!");	
			}
		}
			if (obX == 3182 && obY == 3403) {
				if (c.getItems().freeSlots() <= 3) {
					c.sendMessage("<col=255>You need at least 3 free inventory spots!");
				return;
				}
				if (c.vortexSmith > 0) {
					c.sendMessage("<col=255>You have already smith this armour set. You can only smith one set.");
				return;
				}
				if (c.playerLevel[c.playerSmithing] < 130) {
					c.sendMessage("<col=255>You need at least 130 smithing to smith a Vortex set!");
				return;
				}
				if (c.getItems().playerHasItem(9632, 1500)) {
				c.getItems().deleteItem(9632, 1500);
				c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 25, c.playerSmithing);
				c.startAnimation(898);
				c.getItems().addItem(8807, 1);
				c.getItems().addItem(8808, 1);
				c.getItems().addItem(8809, 1);
				c.vortexSmith ++;
				c.sendMessage("<col=255>You successfully smith a Vortex armour set!");//smithing
				} else {
				c.sendMessage("<col=255>You need at least 1500 daeyalt ores to smith a Vortex set!");	
				}	
			}
				if (obX == 3177 && obY == 3403) {
				if (c.getItems().freeSlots() <= 1) {
					c.sendMessage("<col=255>You need at least 1 free inventory spot!");
				return;
				}
				if (c.getItems().playerHasItem(9632, 3)) {
					c.startAnimation(898);
					c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 35, c.playerSmithing);
					c.getItems().addItem(5021, 3);
					c.getItems().deleteItem(9632, 3);
					c.sendMessage("<col=255>You receive 3 tickets for smithing 3 daeyalt ores.");
				} else {
					c.sendMessage("<col=255>You need at least 3 daeyalt ores to use this!");
				}
			}
			if (obX == 3179 && obY == 3403) {
				if (c.getItems().freeSlots() <= 3) {
					c.sendMessage("<col=255>You need at least 3 free inventory spots!");
				return;
				}
				if (c.dragonboneSmith > 0) {
					c.sendMessage("<col=255>You have already smith this armour set. You can only smith one set.");
				return;
				}
				if (c.playerLevel[c.playerSmithing] < 85) {
					c.sendMessage("<col=255>You need at least 85 smithing to smith a Dragonbone set!");
				return;
				}
				if (c.getItems().playerHasItem(9632, 750)) {
				c.getItems().deleteItem(9632, 750);
				c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 25, c.playerSmithing);
				c.startAnimation(898);
				c.getItems().addItem(11607, 1);
				c.getItems().addItem(11608, 1);
				c.getItems().addItem(11611, 1);
				c.dragonboneSmith ++;
				c.sendMessage("<col=255>You successfully smith a Dragonbone armour set!");//smithing
				} else {
				c.sendMessage("<col=255>You need at least 750 daeyalt ores to smith a Dragonbone set!");	
				}
			}
			if (obX == 3178 && obY == 3403) {
				if (c.getItems().freeSlots() <= 3) {
					c.sendMessage("<col=255>You need at least 3 free inventory spots!");
				return;
				}
				if (c.bluetorvaSmith > 0) {
					c.sendMessage("<col=255>You have already smith this armour set. You can only smith one set.");
				return;
				}
				if (c.playerLevel[c.playerSmithing] < 70) {
					c.sendMessage("<col=255>You need at least 70 smithing to smith a Blue Torva set!");
				return;
				}
				if (c.getItems().playerHasItem(9632, 600)) {
				c.getItems().deleteItem(9632, 600);
				c.getPA().addSkillXP(c.playerLevel[c.playerSmithing] * 25, c.playerSmithing);
				c.startAnimation(898);
				c.getItems().addItem(20252, 1);
				c.getItems().addItem(20253, 1);
				c.getItems().addItem(20251, 1);
				c.bluetorvaSmith ++;
				c.sendMessage("<col=255>You successfully smith a Blue Torva armour set!");//smithing
				} else {
				c.sendMessage("<col=255>You need at least 600 daeyalt ores to smith a Blue Torva set!");	
				}	
			}
			//c.getDH().sendDialogues(6967, 1375); 
		break;
		case 3042:
			if (obX == 3182 && obY == 3406 && c.playerRights > 0) {
				if (c.getItems().freeSlots() <= 1 ) {
					c.sendMessage("<col=255>You need at least one free inventory slot!");
					return;
				}
				if (System.currentTimeMillis() - c.lastAction < 2500) {
					return;
				}
				if (c.miningPoints == 52) {
					c.getPA().movePlayer(3180, 3409, 0);
					c.sendMessage("Woah! I just got moved a little bit. Is it because I mine too much??");
					c.miningPoints = 0;
					return;
				}
				if (c.miningPoints == 13) {
					c.getItems().addItem(15522, 1);
					c.sendMessage("You find a strange rock!");
				}
				if (c.miningPoints == 28) {
					c.getItems().addItem(15522, 1);
					c.sendMessage("You find a strange rock!");
				}
				if (c.miningPoints == 43) {
					c.getItems().addItem(15522, 1);
					c.sendMessage("You find a strange rock!");
				}
				if (c.playerEquipment[c.playerWeapon] == 13221) {
				c.getItems().addItem(9632, 2);
				c.sendMessage("<col=255>You received a double amount of ores for wielding the great Diamond Pickaxe!");
				}
				if (c.playerEquipment[c.playerWeapon] != 13221) {
				c.getItems().addItem(9632, 1);	
				}
				c.sendMessage("You manage to mine some "+ c.getItems().getItemName(9632).toLowerCase()+" from the donator rock!");
				c.getPA().addSkillXP(c.playerLevel[c.playerMining] * 25, c.playerMining);
				c.startAnimation(6758);
				c.miningPoints ++;
				c.lastAction = System.currentTimeMillis();
				
			} if (obX == 3182 && obY == 3406 && c.playerRights == 0 && c.playerEquipment[c.playerWeapon] != 13221) {
				c.sendMessage("This is a donator+ rock. Please ::donate at least 1$ to use this or wield the Diamond Pickaxe.");
				return;
			} if (c.getItems().freeSlots() <= 1 ) {
				c.sendMessage("<col=255>You need at least one free inventory slot!");
				return;
			}
			if (System.currentTimeMillis() - c.lastAction < 2500) {
				return;
			}
			if (c.miningPoints == 52) {
				c.getPA().movePlayer(3180, 3409, 0);
				c.sendMessage("Woah! I just got moved a little bit. Is it because I mine too much??");
				c.miningPoints = 0;
				return;
			}
			if (c.miningPoints == 23) {
				c.getItems().addItem(15522, 1);
				c.sendMessage("You find a strange rock!");
			}
			if (c.playerEquipment[c.playerWeapon] == 13221) {
			c.getItems().addItem(9632, 2);
			c.sendMessage("<col=255>You received a double amount of ores for wielding the great Diamond Pickaxe!");
			}
			if (c.playerEquipment[c.playerWeapon] != 13221) {
			c.getItems().addItem(9632, 1);	
			}
			c.sendMessage("You manage to mine some "+ c.getItems().getItemName(9632).toLowerCase()+".");
			c.getPA().addSkillXP(c.playerLevel[c.playerMining] * 25, c.playerMining);
			c.startAnimation(6758);
			c.miningPoints ++;
			c.lastAction = System.currentTimeMillis();
			break;
		case 4954:
		if (c.getItems().playerHasItem(5021, 2000)) {
			c.dialogueAction = 2995;
				c.getItems().deleteItem(5021, 2000);
			} else {
		c.sendMessage("<col=255>You need at least 2000 1b tickets to get access to this feature!");
			}
			break;
		case 3581:
			if (c.getItems().freeSlots() >= 1 ) {
			if (System.currentTimeMillis() - c.lastThieve < 2500)
			return;
			if (c.playerLevel[c.playerThieving] > 0) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 35, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 20) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 70, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 40) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 120, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 60) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 350, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 80) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 850, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 100) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 1050, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 150) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 1500, c.playerThieving);
			}
		c.startAnimation(881);
		c.lastThieve = System.currentTimeMillis();
		c.thiefPoints ++;
		int[] arrayOfThief ={3529, 3529, 3529, 3529, 3531, 3529, 3531, 3529, 3531, 3533};
		if (c.thiefPoints >= 50) {
			c.thiefPoints = 0;
			c.getItems().addItem(arrayOfThief[Misc.random(arrayOfThief.length -1)], 1);
			c.sendMessage("<col=255>You received an extra casket for 50 successful thieves!");
		}
		if (c.thiefPoints == 13) {
		c.sendMessage("You thieve some coins... Darned...");
		c.getItems().addItem(995, 100000000);
		c.getItems().addItem(950, 1);
		c.sendMessage("<col=255>Please use ::silkreward if you have at least 1.000 noted silks in your inventory!");
		} else if (c.thiefPoints == 27) {
		c.sendMessage("You thieve some coins... Darned...");
		c.getItems().addItem(995, 100000000);
		c.getItems().addItem(950, 1);
		c.sendMessage("<col=255>Please use ::silkreward if you have at least 1.000 noted silks in your inventory!");
		} else if (c.thiefPoints == 41) {
		c.getItems().addItem(995, 100000000);
		c.getItems().addItem(950, 1);
		c.sendMessage("<col=255>Please use ::silkreward if you have at least 1.000 noted silks in your inventory!");
		c.sendMessage("You thieve some coins... Darned...");
		} else {
		c.getItems().addItem(5021, 1);
		c.getItems().addItem(950, 1);
		c.sendMessage("You thieve some tickets...");
		}
			} else {
				c.sendMessage("You need at least 1 free inventory slot!");
			}
			break;
		case 3608:
			if (c.playerRights == 0) {
				c.sendMessage("Not available for regular players.");
			} else {
			if (c.getItems().freeSlots() >= 1 ) {
			if (System.currentTimeMillis() - c.lastThieve < 2500)
			return;
			if (c.playerLevel[c.playerThieving] > 0) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 70, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 20) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 140, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 40) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 180, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 60) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 450, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 80) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 1050, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 100) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 1500, c.playerThieving);
			} else if (c.playerLevel[c.playerThieving] < 150) {
			c.getPA().addSkillXP(c.playerLevel[c.playerThieving] * 1700, c.playerThieving);
			}
		c.startAnimation(881);
		c.lastThieve = System.currentTimeMillis();
		c.thiefPoints ++;
		int[] arrayOfThief ={3531, 3531, 3531, 3531, 3533, 3531, 3533, 3531, 3533, 3535};
		if (c.thiefPoints >= 50) {
			c.thiefPoints = 0;
			c.getItems().addItem(arrayOfThief[Misc.random(arrayOfThief.length -1)], 1);
			c.sendMessage("<col=255>You received an extra casket for 50 successful thieves!");
		}
		if (c.thiefPoints == 13) {
		c.sendMessage("<col=255>You thieve 14 tickets! Yeah!");
		c.getItems().addItem(5021, 14);
		c.getItems().addItem(950, 1);
		c.sendMessage("<col=255>Please use ::silkreward if you have at least 1.000 noted silks in your inventory!");
		} else if (c.thiefPoints == 41) {
		c.getItems().addItem(950, 1);
		c.getItems().addItem(5021, 9);
		c.sendMessage("<col=255>Please use ::silkreward if you have at least 1.000 noted silks in your inventory!");
		c.sendMessage("<col=255>You thieve 9 tickets! Yeah!");
		} else {
		c.getItems().addItem(5021, 1);
		c.getItems().addItem(950, 1);
		c.sendMessage("You thieve some tickets...");
		}
			} else {
				c.sendMessage("You need at least 1 free inventory slot!");
			}
			}
			break;
		case 2466:
			if (c.npcKills < 50) {
			c.sendMessage("<col=255>You will get access to Floor 1 at 50 kills");
			}
			if (c.npcKills > 50 && c.npcKills < 500) {
				c.getPA().startTeleport(2834, 3252, 0, "modern");
				c.sendMessage("<col=255>You have "+ c.npcKills +" NPC Kills so you teleport to Floor 1!");
				c.sendMessage("<col=255>You will get access to Floor 2 at 500 kills");
			}
			if (c.npcKills > 500 && c.npcKills < 1500) {
				c.getPA().startTeleport(2834, 3252, 4, "modern");
				c.sendMessage("<col=255>You have "+ c.npcKills +" NPC Kills so you teleport to Floor 2!");
				c.sendMessage("<col=255>You will get access to Floor 3 at 1500 kills");
			}
			if (c.npcKills > 1500 && c.npcKills < 5000) {
				c.getPA().startTeleport(2834, 3252, 8, "modern");
				c.sendMessage("<col=255>You have "+ c.npcKills +" NPC Kills so you teleport to Floor 3!");
				c.sendMessage("<col=255>You will get access to Floor 4 at 15000 kills");
			}
			if (c.npcKills > 5000 && c.npcKills < 15000) {
				c.getPA().startTeleport(2834, 3252, 12, "modern");
				c.sendMessage("<col=255>You have "+ c.npcKills +" NPC Kills so you teleport to Floor 4!");
				c.sendMessage("<col=255>You will get access to Floor 5 at 25000 kills");
			}
			if (c.npcKills > 15000 && c.npcKills < 25000) {
				c.getPA().startTeleport(2834, 3252, 16, "modern");
				c.sendMessage("<col=255>You have "+ c.npcKills +" NPC Kills so you teleport to Floor 5!");
				c.sendMessage("<col=255>You will get access to Floor 6 at 35000 kills");
			}
			if (c.npcKills > 25000) {
				c.getPA().startTeleport(2834, 3252, 20, "modern");
				c.sendMessage("<col=255>You have "+ c.npcKills +" NPC Kills so you teleport to Floor 6!");
			}
			
			
			
			/*if (c.npcKills > 50 && c.npcKills < 500 && obX == 2406 && obY == 3487) {
			c.getPA().startTeleport(3087, 3497, 0, "modern");
			c.sendMessage("You have "+ c.npcKills +" NPC Kills so you teleport to Floor 1!");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			}
			if (c.npcKills > 500 && c.npcKills < 1500 && obX == 2406 && obY == 3488) {
			c.getPA().startTeleport(3087, 3497, 0, "modern");
			c.sendMessage("You have "+ c.npcKills +" NPC Kills so you teleport to Floor 2!");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			} else {
			c.sendMessage("You need at least 500 NPC Kills for this teleport!");
			}
			if (c.npcKills > 1500 && c.npcKills < 5000 && obX == 2406 && obY == 3489) {
			c.getPA().startTeleport(3087, 3497, 0, "modern");
			c.sendMessage("You have "+ c.npcKills +" NPC Kills so you teleport to Floor 3!");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			} else {
			c.sendMessage("You need at least 1500 NPC Kills for this teleport!");
			}
			if (c.npcKills > 5000 && c.npcKills < 15000 && obX == 2406 && obY == 3490) {
			c.getPA().startTeleport(3087, 3497, 0, "modern");
			c.sendMessage("You have "+ c.npcKills +" NPC Kills so you teleport to Floor 4!");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			} else {
			c.sendMessage("You need at least 5000 NPC Kills for this teleport!");
			}
			if (c.npcKills > 15000 && c.npcKills < 35000 && obX == 2406 && obY == 3491) {
			c.getPA().startTeleport(3087, 3497, 0, "modern");
			c.sendMessage("You have "+ c.npcKills +" NPC Kills so you teleport to Floor 5!");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			} else {
			c.sendMessage("You need at least 15000 NPC Kills for this teleport!");
			}
			if (c.npcKills > 35000 && obX == 2406 && obY == 3492) {
			c.getPA().startTeleport(2834, 3252, 0, "modern");
			c.sendMessage("You have "+ c.npcKills +" NPC Kills so you teleport to Floor 6!");
			c.getPA().closeAllWindows();
			c.dialogueAction = -1;
			} else {
			c.sendMessage("You need at least 35000 NPC Kills for this teleport!");
			}
			break;*/
			break;
			
		case 3485:
			if (c.Arma == 1) {
			c.getPA().startTeleport(3179, 3408, 0, "modern"); //Change teleport location
			c.getItems().deleteItem(19042, 1);
			c.getItems().addItem(11639, 1);
			c.getItems().addItem(12240, 1);
			c.getItems().addItem(8816, 1);
			c.getItems().addItem(8817, 1);
			c.getItems().addItem(8818, 1);
			c.getItems().addItem(3529, 1);
			c.Arma = 0;
			} else {
				c.sendMessage("<col=255>You have already claimed your starter package!");
				c.getPA().startTeleport(3179, 3408, 0, "modern"); //Change teleport location
				}
			return;


//		switch(objectType) {
//		case 6436:
//			GhostBuster.SpawnNpcs(c);
//			break;
//			}


//		switch(objectType) {
//		case 1755:
//		if (c.getItems().playerHasItem(17466, 125)) {
//			GhostBoss.SpawnNpcs(c);
//			} else {
//			c.sendMessage("Collect 125 Spiritbloom and you will be able to face the mighty Ghast!");
//			}
//			break;
//			}


			case 6461:
				c.getPA().movePlayer(2787, 3802, 0);
			c.sendMessage("You have entered the Skeletal Wyverns!");
			break;
			case 6462:
				c.getPA().movePlayer(2787, 3802, 0);
			c.sendMessage("You have entered the Skeletal Wyverns!");
			break;
			case 24452:
				c.getAuctions().openAuction();
				break;

		case 13671:
			if (c.playerRights != 3) {
				c.sendMessage("You don't have the rights to sit here.");
			return;
			} else {
				if (obX == 1864 && obY == 5352 || obX == 1864 && obY == 5342) {
				c.turnPlayerTo(1863 + 1, 5350);
				c.getPA().movePlayer(obX, obY, 0);
				c.startAnimation(4117);
				} else if (obX == 1863 && obY == 5352) {
				c.turnPlayerTo(1863 + 1, 5343);
				c.getPA().movePlayer(obX, obY, 0);
				c.startAnimation(4117);
				} else if (obX == 1863 && obY == 5342) {
				c.turnPlayerTo(1863, 5343);
				c.getPA().movePlayer(obX, obY, 0);
				c.startAnimation(4117);
				}
			}
			break;
		case 13660:
			c.getPA().showInterface(36500);
		break;
		case 12089:
			c.getPA().showInterface(36500);
		break;
case 13670:
			if (c.playerRights == 4 || c.playerRights == 0) {
				c.sendMessage("You don't have the rights to sit here.");
			return;
			} else {
				if (obX == 1865 && obY == 5345) {
				c.turnPlayerTo(1862 + 1, 5345);
				c.getPA().movePlayer(obX, obY, 0);
				c.startAnimation(4116);
				} else if (obX == 1865 && obY == 5347) {
				c.turnPlayerTo(1862 + 1, 5347);
				c.getPA().movePlayer(obX, obY, 0);
				c.startAnimation(4116);
				} else if (obX == 1865 && obY == 5349) {
				c.turnPlayerTo(1862 + 1, 5349);
				c.getPA().movePlayer(obX, obY, 0);
				c.startAnimation(4116);
				} else if (obX == 1862 && obY == 5347) {
				c.turnPlayerTo(1865 + 1, 5347);
				c.getPA().movePlayer(obX, obY, 0);
				c.startAnimation(4116);
				} else if (obX == 1862 && obY == 5345) {
				c.turnPlayerTo(1865 + 1, 5345);
				c.getPA().movePlayer(obX, obY, 0);
				c.startAnimation(4116);
				} else if (obX == 1862 && obY == 5349) {
				c.turnPlayerTo(1865 + 1, 5349);
				c.getPA().movePlayer(obX, obY, 0);
				c.startAnimation(4116);
				}
			}
			break;
		case 6282:
			c.getDH().sendDialogues(25000, 1);
		break;

		case 2543:
			c.getPA().showInterface(39500);
		break;
		case 7219:
			if (c.absX == 2957 && c.absY == 5068 && (c.getItems().playerHasItem(7299, 1))) {
				c.getPA().movePlayer(2957, 5072, 1);
				c.getItems().deleteItem(7299, 1);
			} else if (c.absX == 2955 && c.absY == 5094 && (c.getItems().playerHasItem(7302, 1))) {
				c.getPA().movePlayer(2955, 5098, 1);
				c.getItems().deleteItem(7302, 1);
			} else if (c.absX == 3047 && c.absY == 5037) {
				c.getPA().movePlayer(3043, 5037, 1);
			}
		break;

		case 7239:
			if (c.getItems().playerHasItem(7297, 1)) {
				c.getItems().deleteItem(7297, 1);
			c.getPA().movePlayer(2958, 5035, 1);
			} else {
				c.sendMessage("You do not have the key.");
			}
		break;
		case 7255:
			if (c.getItems().playerHasItem(2834, 1)) {
				c.getItems().deleteItem(2834, 1);
				c.getPA().movePlayer(2976, 5107, 1);
			} else {
				c.sendMessage("You do not have the key.");
			}
		break;
		case 823:
			if (c.playerLevel[16] >= 100) { 
				if (System.currentTimeMillis() - c.buryDelay > 3000) {
						c.startAnimation(422);
						c.sendMessage("You Whack the Dummy.");
						c.getPA().addSkillXP(50000, c.playerAgility);
						c.buryDelay = System.currentTimeMillis();
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 100 Agility to do this.");
				return;
			}
		break;



		case 12355:
			c.getPA().movePlayer(3191, 3471, 0);
		break;

		case 7316:
			c.getPA().movePlayer(3213, 3435, 0);
		break;



		case 822:
			if (c.playerLevel[16] >= 110) { 
				if (System.currentTimeMillis() - c.buryDelay > 3000) {
						c.startAnimation(1331);
						c.sendMessage("You Lift the Cannonballs.");
						c.getPA().addSkillXP(80000, c.playerAgility);
						c.buryDelay = System.currentTimeMillis();
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 110 Agility to do this.");
				return;
			}
		break;
		case 2641:
			if (c.playerLevel[16] >= 115) { 
				if (System.currentTimeMillis() - c.buryDelay > 3000) {
						c.startAnimation(3063);
						c.sendMessage("You Climb the Ladder.");
						c.getPA().addSkillXP(120000, c.playerAgility);
						c.buryDelay = System.currentTimeMillis();
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 115 Agility to do this.");
				return;
			}
		break;
		case 3563:
			if (c.playerLevel[16] >= 125) { 
				if (System.currentTimeMillis() - c.buryDelay > 3000) {
						c.startAnimation(744);
						c.sendMessage("You use the Monkey Bars.");
						c.getPA().addSkillXP(150000, c.playerAgility);
						c.buryDelay = System.currentTimeMillis();
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 125 Agility to do this.");
				return;
			}
		break;


				case 99:
			if (c.getItems().playerHasItem(2836, 1)) {
				c.getItems().deleteItem(2836, 1);
				c.getPA().movePlayer(3012, 5103, 1);
			} else {
				c.sendMessage("You do not have the key.");
			}
		break;
		case 102:
			if (c.getItems().playerHasItem(2838, 1)) {
				c.getItems().deleteItem(2838, 1);
				c.getPA().movePlayer(3025, 5102, 1);
			} else {
				c.sendMessage("You do not have the key.");
			}
		break;
		case 92:
			if (c.getItems().playerHasItem(5010, 1)) {
				c.getItems().deleteItem(5010, 1);
				c.getPA().movePlayer(3063, 5059, 1);
			} else {
				c.sendMessage("You do not have the key.");
			}
		break;
		case 1532:
			if (c.getItems().playerHasItem(9722, 1)) {
				c.getItems().deleteItem(9722, 1);
				c.getPA().movePlayer(3054, 5027, 1);
			} else {
				c.sendMessage("You do not have the key.");
			}
		break;

		case 3:
			if (c.getItems().playerHasItem(2840, 1)) {
				c.getItems().deleteItem(2840, 1);
				c.getPA().movePlayer(3044, 5105, 1);
			} else {
				c.sendMessage("You do not have the key.");
			}
		break;
		case 4:
			if (c.getItems().playerHasItem(2411, 1)) {
				c.getItems().deleteItem(2411, 1);
				c.getPA().movePlayer(3062, 5075, 1);
			} else {
				c.sendMessage("You do not have the key.");
			}
		break;

		case 93:
			if (c.getItems().playerHasItem(2832, 1)) {
				c.getItems().deleteItem(2832, 1);
				c.getPA().movePlayer(2965, 5105, 1);
			} else {
				c.sendMessage("You do not have the key.");
			}
		break;
		
		case 2423:
			if (c.getItems().playerHasItem(995, 250000000)) {
				c.getItems().deleteItem(995, 250000000);
				c.sendMessage("Pummeler Is EXTREMELY Difficult and requires a TEAM of atleast 4.");
				c.getPA().movePlayer(2718, 9612, 0);
			} else {
				c.sendMessage("You need a 250m gold to enter.");
			}
		break;
		case 4412:
			c.getDH().sendDialogues(256, 1);
		break;

		case 313:
			c.getItems().addItem(1947, 1);
		break;
					case 12731://Bank stuff at ::dicezone
			for(int i = 0; i < c.playerItems.length; i++) {
				c.getItems().bankItem(c.playerItems[i]-1, c.getItems().getItemSlot(c.playerItems[i]-1), c.playerItemsN[i]);
                                c.sendMessage("You have banked all your inventory items.");
			}
			break;

					case 4407:
					if (c.npcKills > 2500 && c.crystalBowArrowCount == 0) {
					c.getPA().startTeleport(2949, 2979, 4, "modern");
					c.crystalBowArrowCount += 1;
					} else if (c.npcKills > 2500 && c.crystalBowArrowCount == 2) {
					c.getPA().startTeleport(2949, 2979, 8, "modern");
					c.crystalBowArrowCount += 1;
					} else if (c.npcKills > 2500 && c.crystalBowArrowCount == 3) {
					c.getPA().startTeleport(2949, 2979, 12, "modern");
					c.crystalBowArrowCount += 1;
					} else if (c.npcKills > 2500 && c.crystalBowArrowCount == 4) {
					c.getPA().startTeleport(2949, 2979, 16, "modern");
					c.crystalBowArrowCount += 1;
					} else if (c.npcKills > 2500 && c.crystalBowArrowCount == 5) {
					c.getPA().startTeleport(2949, 2979, 20, "modern");
					c.crystalBowArrowCount += 1;
					} else if (c.npcKills > 2500 && c.crystalBowArrowCount == 6) {
					c.getPA().startTeleport(2949, 2979, 24, "modern");
					c.crystalBowArrowCount = 1;
					} else if (c.npcKills < 5000) {
					c.sendMessage("You need at least 2.5k kills before you can enter the mummy!");
					}
					break;

					case 16089:
					if (c.npcKills > 2500) {
					c.getPA().movePlayer(2953, 2979, 0);
					} else {
					c.sendMessage("You need at least 2.5k kills before you can enter the mummy!");
					}
					break;

		case 2503:
				c.getPA().movePlayer(2404, 4458, 1);
		break;
		case 2504:
				c.getPA().movePlayer(2404, 4458, 1);
		break;
		case 2505:
				c.getPA().movePlayer(2404, 4458, 1);
		break;
		case 2506:
				c.getPA().movePlayer(2404, 4458, 1);
		break;
		case 2507:
				c.getPA().movePlayer(2404, 4458, 1);
		break;
		case 2720:
				c.getPA().movePlayer(2368, 4956, 4);
				c.sendMessage("You have teleported to the charms monsters!");
		break;
		
			

		case 1733:
			if(c.playerRights == 0) {
				c.sendMessage("You must be a staff member to use these stairs.");
			}else if(c.playerRights == 1){
				c.getPA().movePlayer(1865, 5348, 0);
			}else if(c.playerRights == 2){
				c.getPA().movePlayer(1865, 5348, 0);
			}else if(c.playerRights == 3){
				c.getPA().movePlayer(1865, 5348, 0);
			}else if(c.playerRights >= 4){
				c.sendMessage("You must be a staff member to use these stairs.");
			}
		break;	

		case 6836:
			c.sendMessage("no...");
		break;
//100+mining ///this is client sided Kk hold up
		case 2110:
			if (c.playerLevel[14] >= 100) { 
				if (System.currentTimeMillis() - c.buryDelay > 7500) {
					if (c.playerEquipment[c.playerWeapon] == 1275){
						c.startAnimation(6752);
						c.sendMessage("You mine some Blurite ore.");
						c.getItems().addItem(668, 1);
						c.getPA().addSkillXP(50000, c.playerMining);
						c.buryDelay = System.currentTimeMillis();
					} else {
						c.sendMessage("You must have a rune pick axe equipped");
						return;
					}
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 100 mining to mine this rock.");
				return;
			}
		break;
		case 2111:
			if (c.playerLevel[14] >= 125) { 
				if (System.currentTimeMillis() - c.buryDelay > 7500) {
					if (c.playerEquipment[c.playerWeapon] == 1275){
						c.startAnimation(6752);
						c.sendMessage("<col=255>You mine some Dragonbone ore.");
						c.sendMessage("<col=255>Use ::legs, ::body or ::helm to exchange 450 noted ores into that particular piece!");
						c.getItems().addItem(17644, 1);
						c.getPA().addSkillXP(150000, c.playerMining);
						c.buryDelay = System.currentTimeMillis();
					} else {
						c.sendMessage("You must have a rune pick axe equipped");
						return;
					}
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 125 mining to mine this rock.");
				return;
			}
		break;
		case 2109:
			if (c.playerLevel[14] >= 110) { 
				if (System.currentTimeMillis() - c.buryDelay > 7500) {
					if (c.playerEquipment[c.playerWeapon] == 1275){
						c.startAnimation(6752);
						c.sendMessage("You mine some Clay.");
						c.getItems().addItem(434, 1);
						c.getPA().addSkillXP(80000, c.playerMining);
						c.buryDelay = System.currentTimeMillis();
					} else {
						c.sendMessage("You must have a rune pick axe equipped");
						return;
					}
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 110 mining to mine this rock.");
				return;
			}
		break;
		case 2106:
			if (c.playerLevel[14] >= 115) { 
				if (System.currentTimeMillis() - c.buryDelay > 7500) {
					if (c.playerEquipment[c.playerWeapon] == 1275){
						c.startAnimation(6752);
						c.sendMessage("You mine some Blue Dragon ore.");
						c.getItems().addItem(9632, 1);
						c.getPA().addSkillXP(120000, c.playerMining);
						c.buryDelay = System.currentTimeMillis();
					} else {
						c.sendMessage("You must have a rune pick axe equipped");
						return;
					}
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 115 mining to mine this rock.");
				return;
			}
		break;
		case 6555:
        case 6553:
        case 6501:
        	Dungeoneering.handleObject(c, objectType, obX, obY);
        break;

		case 21505:
			if (c.absX == 2328 && c.absY == 3805) {
				c.getPA().movePlayer(2329, 3804, 0);
			}
			if (c.absX == 2328 && c.absY == 3804) {
				c.getPA().movePlayer(2329, 3804, 0);
			} else if (c.absX == 2329 && c.absY == 3804) {
				c.getPA().movePlayer(2328, 3804, 0);
			}
		break;
		
		case 11356:
			c.getPA().movePlayer(3052, 9577, 0);
		break;
		
		case 21507:
			if (c.absX == 2328 && c.absY == 3805) {
				c.getPA().movePlayer(2329, 3805, 0);
			} else if (c.absX == 2329 && c.absY == 3805) {
				c.getPA().movePlayer(2328, 3805, 0);
			}
		break;

		case 21600:
			if (c.absX == 2326 && c.absY == 3802) {
				c.getPA().movePlayer(2326, 3801, 0);
			} else if (c.absX == 2326 && c.absY == 3801) {
				c.getPA().movePlayer(2326, 3802, 0);
			}
		break;

		//cons

		case 11209:
			if (c.playerLevel[23] < 92) {
				c.sendMessage("You need level 92 construction to build that!");
				return;
			}

			if(!c.getItems().playerHasItem(2347, 1)) {
				c.sendMessage("You need a hammer to build this!");
				return;
			}

			if (c.getItems().playerHasItem(1539, 80) && c.getItems().playerHasItem(8007, 100) && c.getItems().playerHasItem(8009, 100) && c.getItems().playerHasItem(8010, 100) && System.currentTimeMillis() - c.buryDelay > 1500) {
					c.startAnimation(898);
					c.buryDelay = System.currentTimeMillis();
					Cons.BuildHurryDurryCrystalCuzGabbeIsBAAAWS(c);
			} else {
				c.sendMessage("You don't have the required materials.");
				c.sendMessage("You need 80 Steel Nails, 1 hammer, 100 Varrock/Falador/Camelot teleport tabs.");
			}
		break;

		

		case 11212:
			if (c.playerLevel[23] < 87) {
				c.sendMessage("You need level 87 construction to build that!");
				return;
			}
			if(!c.getItems().playerHasItem(2347, 1)) {
				c.sendMessage("You need a hammer to build this!");
				return;
			}

			if (c.getItems().playerHasItem(1539, 300) && c.getItems().playerHasItem(561, 100) && c.getItems().playerHasItem(556, 100) && c.getItems().playerHasItem(554, 100) && c.getItems().playerHasItem(555, 100) && c.getItems().playerHasItem(557, 100) && System.currentTimeMillis() - c.buryDelay > 1500) {
				c.startAnimation(898);
				c.buryDelay = System.currentTimeMillis();
				Cons.BuildTeleportFuckerrDerpHurrDurr(c);
			} else {
				c.sendMessage("You don't have the required materials.");
				c.sendMessage("You need 300 Steel Nails, 1 hammer, 100 Air, Nature, Water, Fire and Earth runes.");
			}
		break;

		case 11220:
			if (c.playerLevel[23] < 67) {
				c.sendMessage("You need level 67 construction to build that!");
				return;
			}

			if(!c.getItems().playerHasItem(2347, 1)) {
				c.sendMessage("You need a hammer to build this!");
				return;
			}
			if (c.getItems().playerHasItem(1539, 54) && c.getItems().playerHasItem(561, 27) && c.getItems().playerHasItem(2351, 4) && System.currentTimeMillis() - c.buryDelay > 1500) {
				c.startAnimation(898);
				c.buryDelay = System.currentTimeMillis();
				Cons.BuildLecterFuckersss(c);
			} else {
				c.sendMessage("You don't have the required materials.");
				c.sendMessage("You need 54 Steel Nails, 27 Nature runes, 4 Iron Bars, and one hammer!");
			}
		break;

		case 11216:
			if(!c.getItems().playerHasItem(2347, 1)) {
				c.sendMessage("You need a hammer to do that.");
				return;
			}
			if(c.playerLevel[23] < 40) {
				c.sendMessage("You need a level 40 Construction to do that.");
				return;
			}
			if (c.getItems().playerHasItem(1539, 20) && c.getItems().playerHasItem(8778, 3) && System.currentTimeMillis() - c.buryDelay > 1500) {
				Cons.BuildBed(c);
				c.startAnimation(898);
				c.buryDelay = System.currentTimeMillis();
			} else {
				c.sendMessage("You don't have the required materials.");
				c.sendMessage("You need 20 Steel Nails, And 3 Oak Planks.");
			}
		break;

		case 11217:
			if(c.playerLevel[23] < 4) {
				c.sendMessage("You need a level of 5 Construction to do that.");
				return;
			}
			if (c.getItems().playerHasItem(1511, 3) && c.getItems().playerHasItem(5343, 1) && System.currentTimeMillis() - c.buryDelay > 1500) {
				Cons.BuildTree(c);
				c.startAnimation(2291);
				c.buryDelay = System.currentTimeMillis();
			} else {
				c.sendMessage("You don't have the required materials.");
				c.sendMessage("You need 3 Normal Logs and a Seed Dibber to do that!");
			}
		break;

		case 11219:
			if (c.playerLevel[23] < 95) {
				c.sendMessage("You need level 95 construction to build that!");
				return;
			}
			if(!c.getItems().playerHasItem(2347, 1)) {
				c.sendMessage("You need a hammer to build this!");
				return;
			}
			if (c.getItems().playerHasItem(1539, 40) && c.getItems().playerHasItem(8782, 1) && c.getItems().playerHasItem(8782, 1) && c.getItems().playerHasItem(2351, 1) && System.currentTimeMillis() - c.buryDelay > 1500) {
				Cons.BuildChest(c);
				c.startAnimation(898);
				c.buryDelay = System.currentTimeMillis();
			} else {
				c.sendMessage("You don't have the required materials.");
				c.sendMessage("You need 40 Steel nails, 1 iron bar and 2 Mahogany Planks!");
			}
		break;

		case 11215:
			if (c.playerLevel[23] < 63) {
				c.sendMessage("You need level 64 construction to build that!");
				return;
			}
			if(!c.getItems().playerHasItem(2347, 1)) {
				c.sendMessage("You need a hammer to build this!");
				return;
			}
			if (c.getItems().playerHasItem(1539, 15) && c.getItems().playerHasItem(8782, 2) && c.getItems().playerHasItem(2351, 1) && System.currentTimeMillis() - c.buryDelay > 1500) {
				c.startAnimation(898);
				Cons.BuildAltar(c);
				c.buryDelay = System.currentTimeMillis();
			} else {
				c.sendMessage("You don't have the required materials.");
				c.sendMessage("You need 15 Steel Nails, 2 Mahogany Planks and 1 Iron Bar!");
			}
		break;
		
		case 11214:
			c.getPA().movePlayer(obX, obY, c.heightLevel);
			c.getPA().showInterface(31250);
			c.sendMessage("Choose an item to build...");
		break;
		
		case 13641:
			c.getPA().showInterface(36500);
		break;

		case 13640:
			c.getPA().showInterface(36500);
		break;
		
		case 13405:
			if (c.objectX == 2060){
				c.getPA().startTeleport2(3195, 3429, 0);
				c.sendMessage("You teleported back to home area.");
			} else {
				c.getPA().startTeleport2(2060, 3261, 0);
			}
		break;

        case 15478:
			c.getPA().showInterface(31330);			
		break;

		case 3994:
			ConstructionEvents.MakeIronNails(c);
		break;
					
		case 2644:
			c.sendMessage("You spin a Bowstring.");
			c.spinFlax();
		break;

		case 6551:
			c.getPA().startTeleport(2893, 4811, 0, "modern");
		break;

		case 2492:
			c.getPA().movePlayer(3300, 3305, 0);
		break;

		case 2646: // flax picking
			if(System.currentTimeMillis() - c.buryDelay > 1500) {	
				c.getItems().addItem(1779,1);
				c.startAnimation(827);
				c.sendMessage("You pick some flax.");
				c.buryDelay = System.currentTimeMillis();
			}	
		break;
		
		case 2417:
			c.inPartyRoom = true;
	     	PartyRoom.open(c);
		break;

        case 2412:
			Sailbyhack3r.startTravel(c, 1);
		break;
		
		case 2414:
			Sailbyhack3r.startTravel(c, 2);
		break;
		
		case 2083:
			Sailbyhack3r.startTravel(c, 5);
		break;
		
		case 2081:
			Sailbyhack3r.startTravel(c, 6);
		break;
		
		case 14304:
			Sailbyhack3r.startTravel(c, 14);
		break;
		
		case 14306:
			Sailbyhack3r.startTravel(c, 15);
		break;

        case 23271:
            c.getPA().showInterface(35000);
	    break;

		case 2416:
			if(!c.getItems().playerHasItem(995, 500000)){
				c.sendMessage("You do not have 500K Coins!");
			} else {
				c.getItems().deleteItem2(995, 500000);
				c.inPartyRoom = true;
				PartyRoom.startTimer(c);
				PartyRoom.dropAll();
			}
		break;
		
		case 23272:
			if (c.absY > 3520) {
			    c.getPA().ditchJump(c, 0, - 3);
			}
			if (c.absY <= 3518) {
			    c.getPA().ditchJump(c, 0, 5);
			}
			if (c.absY == 3520) {
			    c.getPA().ditchJump(c, 0, 3);
			}
				EventManager.getSingleton().addEvent(new Event() {
				    public void execute(EventContainer e) {
				        c.getPA().resetDitchJump(c);
				        e.stop();
				    }
		    	public void stop() {}}, 4);
		break;

		case 10556:
			if(c.needstorelog == true) {
				c.sendMessage("Anti Bug System: Please relog before starting a new wave.");
			return;
			}
				Server.barbDefence.enter(c);
				c.sendMessage("Every once in a while you'll receive messages of you're score.");
		break;

		case 2152: //Blue Obelisk
		case 28722: //Orange Obelisk
			c.getPA().closeAllWindows();
			c.getPA().showInterface(23471);
			c.sendMessage("WARNING: This will create ALL pouches available in you're inventory.");
		break;

		case 26384:
			if(!c.hasHammer() && c.absX != 2850) {
				c.sendMessage("You need to have a hammer to enter here!");
				return;
			}
			if(c.absY == 5333 && c.absX == 2851 && c.heightLevel == 2 && c.hasHammer()) {
				c.getPA().movePlayer(2850, 5333, 2);
				c.sendMessage("Your hammer breaks when you bang on the gigantic door!");
				c.sendMessage("Welcome to the bandos room!");
				c.getItems().deleteItem(2347, 1);
			} else if (c.absY == 5333 && c.absX == 2850 && c.heightLevel == 2) {
				c.getPA().movePlayer(2851, 5333, 2);
			}
		break;

		case 26303:
			c.getPA().movePlayer(2872, 5279, 2);
			c.sendMessage("You have entered Armadyl.");
		break;
					
		case 28121:
			c.sendMessage("Not added yet.");
			//BountyHunter.enterCave(c, 2); 
		break;

		case 28120:
			//BountyHunter.enterCave(c, 1); 
			c.sendMessage("Not added yet.");
		break;

		case 28119:
			//BountyHunter.enterCave(c, 0); 
			c.sendMessage("Not added yet.");
		break;

		case 28122:
		//	BountyHunter.leaveCave(c); 
		c.sendMessage("Not added yet.");
		break;

		case 1738:
			c.getPA().movePlayer(2840, 3539, 2);
			//c.sendMessage("Push The NPC To Go Up There!");
		break;

		case 15638:
			c.getPA().movePlayer(2840, 3539, 0);
			//c.sendMessage("Push The NPC To Go Down There!");
		break;

		case 15641:
		case 15644:	
			if (c.absX == 2854 && c.absY == 3546 && c.heightLevel == 0) {
				c.getPA().movePlayer(2854, 3545, 0);
				return;		
			} else if (c.absX == 2855 && c.absY == 3546 && c.heightLevel == 0) {
				c.getPA().movePlayer(2855, 3545, 0);
			} else if (c.absX == 2854 && c.absY == 3545 && c.heightLevel == 0) {
				c.getPA().movePlayer(2854, 3546, 0);		
			} else if (c.absX == 2855 && c.absY == 3545 && c.heightLevel == 0) {
				c.getPA().movePlayer(2855, 3546, 0);
			} else if (c.absX == 2846 && c.absY == 3541 && c.heightLevel == 2) {
				if (!c.getItems().playerHasItem(8851, 100)) {
					c.sendMessage("You need 100 warriors guild tokens to enter here.");
					return;
				}
					c.sendMessage("If it continues to remove tokens when your not in this room, relog!");
					c.getPA().movePlayer(2847, 3541, 2);
					c.inWG = true;
					c.getItems().deleteItem(8851,
					c.getItems().getItemSlot(8851), 10);
					c.WGEvent();	
			} else if (c.absX == 2846 && c.absY == 3540 && c.heightLevel == 2) {
				if (!c.getItems().playerHasItem(8851, 100)) {
					c.sendMessage("You need 100 warriors guild tokens to enter here.");
					return;
				}
					c.sendMessage("If it continues to remove tokens when your not in this room, relog!");
					c.getPA().movePlayer(2847, 3540, 2);
					c.inWG = true;
					c.getItems().deleteItem(8851,
					c.getItems().getItemSlot(8851), 10);
					c.WGEvent();
			} else if (c.absX == 2847 && c.absY == 3541 && c.heightLevel == 2) {
					if (!c.getItems().playerHasItem(8851, 100)) {
						c.sendMessage("You need 100 warriors guild tokens to enter here.");
						return;
					}
						c.sendMessage("If it continues to remove tokens when your not in this room, relog!");
						c.getPA().movePlayer(2846, 3541, 2);
						c.inWG = false;
						c.getItems().deleteItem(8851,
						c.getItems().getItemSlot(8851), 10);
						c.WGEvent();	

			} else if (c.absX == 2847 && c.absY == 3540 && c.heightLevel == 2) {
					c.getPA().movePlayer(2846, 3540, 2);
					c.inWG = false;
			} else if (c.absX == 2847 && c.absY == 3541 && c.heightLevel == 2) {
					c.getPA().movePlayer(2846, 3540, 2);
					c.inWG = false;
			}
		break;
		
		case 1530:
			if (c.absX == 2611 && c.absY == 3398) {
				c.getPA().movePlayer(2611, 3399, 0);
			} else if (c.absX == 2611 && c.absY == 3399) {
				c.getPA().movePlayer(2611, 3398, 0);
			} else if (c.absX == 3108 && c.absY == 9570) {
				c.getPA().movePlayer(3107, 9570, 0);
				c.sendMessage("Search the room for an ancient talisman and bring it to aubury!");
			} else if (c.absX == 3107 && c.absY == 9570) {
	        	c.getPA().movePlayer(3108, 9570, 0);
			}
		break;

		case 4413:
			c.getPA().spellTeleport(3195, 3429, 0);
		break;

		case 2144:
		case 2143:
			if (c.absX == 2887 && c.absY == 9831) { // enter gates
				c.getPA().movePlayer(2889, 9831, 0);
				c.sendMessage("Great you're in the correct place! Now use the meats on the cauldron..");
			} else if (c.absX == 2888 && c.absY == 9831) { // enter gates
				c.getPA().movePlayer(2889, 9831, 0);
				c.sendMessage("Great you're in the correct place! Now use the meats on the cauldron..");    
			} else if (c.absX == 2889 && c.absY == 9831) { // exit
				c.getPA().movePlayer(2888, 9831, 0);   		 
			} else if (c.absX == 2889 && c.absY == 9830) { // exit
				c.getPA().movePlayer(2888, 9831, 0);   
			}	
		break;

		case 27668:
            if (c.absX == 3231 && c.absY == 5091 || c.absX == 3231 && c.absY == 5090 || c.absX == 3232 && c.absY == 5090) {
				c.getPA().movePlayer(3366, 3268, 0);
            }
        break;

        case 27669:
            c.getPA().movePlayer(3232, 5090, 0);
        break;

        case 3561://balance ledge
            if (c.absX == 2803 && c.absY == 9546) {
                c.obsticle(2592, 1, -7, 0, 3650, 1500, "You passed the balancing ledge succesfully.");
            } else if (c.absX == 2770 && c.absY == 9546) {
                c.obsticle(2592, 1, -7, 0, 3650, 1500, "You passed the balancing ledge succesfully.");
            } else if (c.absX == 2770 && c.absY == 9590) {
                c.obsticle(2592, 1, -7, 0, 3650, 1500, "You passed the log balance succesfully.");
            }
        break; 

        case 3559://balance ledge      
            if (c.absX == 2796 && c.absY == 9546) {
            	c.obsticle(2592, 1, 7, 0, 3650, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2763 && c.absY == 9546) {
            	c.obsticle(2592, 1, 7, 0, 3650, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2763 && c.absY == 9590) {
            	c.obsticle(2592, 1, 7, 0, 3650, 1500, "You passed the balancing ledge succesfully.");
            }
        break;

        case 3553:
        case 3557:
            if (c.absX == 2794 && c.absY == 9588) {
                c.obsticle(762, 1, 0, -7, 3330, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2794 && c.absY == 9581) {
                c.obsticle(762, 1, 0, 7, 3330, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2770 && c.absY == 9579) {
                c.obsticle(762, 1, -7, 0, 3330, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2763 && c.absY == 9579) {
                c.obsticle(762, 1, 7, 0, 3330, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2805 && c.absY == 9555) {
                c.obsticle(762, 1, 0, -7, 3330, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2805 && c.absY == 9548) {
                c.obsticle(762, 1, 0, -7, 3330, 1500, "You passed the log balance succesfully.");
            }
        break;

        case 3578:
            if (c.absX == 2805 && c.absY == 9577) {
				c.obsticle(741, 1, 0, -7, 3330, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2805 && c.absY == 9570) {
				c.obsticle(741, 1, 0, 7, 3330, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2761 && c.absY == 9548) {
				c.obsticle(741, 1, 0, 7, 3330, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2761 && c.absY == 9555) {
            	c.obsticle(741, 1, 0, -7, 3330, 1500, "You passed the log balance succesfully.");
        	} else if (c.absX == 2785 && c.absY == 9568) {
				c.obsticle(741, 1, 7, 0, 3330, 1500, "You passed the log balance succesfully.");
            } else if (c.absX == 2792 && c.absY == 9568) {
				c.obsticle(741, 1, -7, 0, 3330, 1500, "You passed the log balance succesfully.");
            }
        break; 

        case 3572:
        case 3570:
        case 3571:
            if (c.absX == 2803 && c.absY == 9589 || c.absX == 2803 && c.absY == 9590 || c.absX == 2803 && c.absY == 9591) {
                c.obsticle(762, 1, -7, 0, 3330, 500, "You passed the obsticle succesfully.");
                c.getPA().addSkillXP(1500, c.playerAgility);
                c.gnomeObsticle = 1;
            } else if (c.absX == 2796 && c.absY == 9591 || c.absX == 2796 && c.absY == 9590 || c.absX == 2796 && c.absY == 9589) {
                c.obsticle(762, 1, 7, 0, 3330, 500, "You passed the obsticle succesfully.");
                c.getPA().addSkillXP(1500, c.playerAgility);
            } else if (c.absX == 2763 && c.absY == 9558 || c.absX == 2763 && c.absY == 9557 || c.absX == 2763 && c.absY == 9556) {
                c.obsticle(762, 1, 7, 0, 3330, 500, "You passed the obsticle succesfully.");
                c.getPA().addSkillXP(1500, c.playerAgility);
            } else if (c.absX == 2796 && c.absY == 9591 || c.absX == 2796 && c.absY == 9590 || c.absX == 2796 && c.absY == 9589) {
                c.obsticle(762, 1, 0, 7, 3330, 500, "You passed the obsticle succesfully.");
                c.getPA().addSkillXP(1500, c.playerAgility);
            } else if (c.absX == 2770 && c.absY == 9558 || c.absX == 2770 && c.absY == 9557 || c.absX == 2770 && c.absY == 9556) {
                c.obsticle(762, 1, -7, 0, 3330, 500, "You passed the obsticle succesfully.");
                c.getPA().addSkillXP(1500, c.playerAgility);
            }
        break;

        case 3583:
            if(c.absX == 2792 && c.absY == 9592) {
                c.startAnimation(1117);
                c.obsticle(1117, 1, -7, 0, 3300, 1500, "You climbed past the hand holds succesfully.");
            } else if (c.absX == 2785 && c.absY == 9592) {
                c.startAnimation(1117);
                c.obsticle(1117, 1, 7, 0, 3600, 1500, "You climbed past the hand holds succesfully.");
            } else if (c.absX == 2792 && c.absY == 9544) {
                c.obsticle(1117, 1, -7, 0, 3600, 1500, "You climbed past the hand holds succesfully.");
            } else if (c.absX == 2785 && c.absY == 9544) {
    	        c.obsticle(1117, 1, 7, 0, 3600, 1500, "You climbed past the hand holds succesfully.");
            } else if (c.absX == 2759 && c.absY == 9566) {
                c.obsticle(1117, 1, 0, -7, 3600, 1500, "You climbed past the hand holds succesfully.");
            } else if (c.absX == 2759 && c.absY == 9559) {
                c.obsticle(1117, 1, 0, 7, 3600, 1500, "You climbed past the hand holds succesfully.");
            }
        break;

        case 3564://monkeybars
            if (c.absX == 2772 && c.absY == 9578) {
                c.obsticle(744, 1, 0, -8, 4600, 1500, "You swing across the monkey bars succesfully.");
            } else if (c.absX == 2772 && c.absY == 9569) {
                c.obsticle(744, 1, 0, 8, 4600, 1500, "You swing across the monkey bars succesfully.");
            } else if (c.absX == 2782 && c.absY == 9546) {
                c.obsticle(744, 1, -8, 0, 4600, 1500, "You swing across the monkey bars succesfully.");
            } else if (c.absX == 2773 && c.absY == 9546) {
                c.obsticle(744, 1, 8, 0, 4600, 1500, "You swing across the monkey bars succesfully.");
            } else if (c.absX == 2794 && c.absY == 9567) {
                c.obsticle(744, 1, 0, -8, 4600, 1500, "You swing across the monkey bars succesfully.");
            } else if (c.absX == 2794 && c.absY == 9558) {
                c.obsticle(744, 1, 0, 8, 4600, 1500, "You swing across the monkey bars succesfully.");
            }
        break; 

        case 3551://rope
            if (c.absX == 2783 && c.absY == 9588) {
                c.obsticle(762, 1, 0, -7, 3330, 1500, "You passed the balancing rope succesfully.");
            } else if (c.absX == 2794 && c.absY == 9548) {
                c.obsticle(762, 1, 0, 7, 3330, 1500, "You passed the balancing rope succesfully.");
            } else if (c.absX == 2794 && c.absY == 9555) {
                c.obsticle(762, 1, 0, -7, 3330, 1500, "You passed the balancing rope succesfully.");
            } else if (c.absX == 2772 && c.absY == 9566) {
                c.obsticle(762, 1, 0, -7, 3330, 1500, "You passed the balancing rope succesfully.");
            } else if (c.absX == 2772 && c.absY == 9559) {
                c.obsticle(762, 1, 0, 7, 3330, 1500, "You passed the balancing rope succesfully.");
            } else if (c.absX == 2783 && c.absY == 9581) {
                c.obsticle(762, 1, 0, 7, 3330, 1500, "You passed the balancing rope succesfully.");
            }
        break;
		
		case 2294: // Barbarian Log Balance
			if (c.absX == 2551 && c.absY == 3546) {
			if (c.getAgil().isBalance = true);
				c.sendMessage("You are alredy crossing it!");
				return;
			} else {
				c.startAnimation(762);
				c.getAgil().isBalance = true;
				c.obsticle(762, 40, -10, 0, 5550, 2000, "You passed obsticle succesfully.");
			}
		break;

        case 2284: // Barbarian Obstacle net
            c.agilityDelay(828, 2537, 3546, 1, 40, 2500, "You climbed the nets succesfully.");
        break;

        case 2302: // Barbarian Balancing ledge
            if (c.absX == 2536 && c.absY == 3547) {
                c.obsticle(756, 40, -4, 0, 2500, 3500, "You passed the balancing ledge succesfully.");
            } else {
        		return;
        	}
        break;
        
        case 3205: // Ladder
            c.agilityDelay(827, 2532, 3546, 0, 40, 15, "You climb down.");
        break;
        
        case 1948: // Crumbling Walls
            if (c.absX > 2534 || c.absX < 2542 && c.absY == 3553) {
            	c.obsticle(839, 40, 2, 0, 1200, 800, "You passed the obsticle succesfully.");
        	} else {
        		return;
        	}
        break;
		
		case 3379:
			if(c.playerLevel[24] >= 49) {
				c.getPA().movePlayer(3467, 9495, 0);
				c.sendMessage("<shad=16112652>Kill NPC's here for fast Dung XP/Tokens!");
				c.sendMessage("<shad=16112652>You are taken to a underground cave!");
			} else {
				c.sendMessage("You need atleast a level of 49 Dungeoneering to enter this cave!");
			}
		break;	

		case 1765:
			c.getPA().movePlayer(2271, 4680, 0);
		break;
		
		case 9391:
        /*    c.setSidebarInterface(10, 3209);
            c.outStream.createFrame(106); // Writes the frame 106 out.
            c.outStream.writeByteC(10); // Tells client to switch to the magic interface*/
        break;

		case 26288:
		case 26287:
		case 26286:
		case 26289:
			if(c.gwdelay > 1) {
				c.sendMessage("You can only do this once every 5 minutes!");
				return;
			}
			if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]); c.getPA().getLevelForXP(c.playerXP[5]);
				c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]); c.getPA().getLevelForXP(c.playerXP[3]);
				c.sendMessage("You recharge your prayer points & HP..");
				c.getPA().refreshSkill(5);
				c.getPA().refreshSkill(3);
				c.gwdelay = 600;
			} else {
				c.sendMessage("You already have full prayer points.");
			}
		break;
			
		case 2295:
			c.getAgil().AgilityLog(c, "Log", 1, 0, -7, 2474, 3436, 60);
			c.ag1 = 1;
		break;

		case 2285:
			c.startAnimation(828);
			c.getAgil().AgilityNet(c, "Net", 1, 2471, 3426, 1, 2471, 3424, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2472, 3426, 1, 2472, 3424, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2473, 3426, 1, 2473, 3424, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2474, 3426, 1, 2474, 3424, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2475, 3426, 1, 2475, 3424, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2476, 3426, 1, 2476, 3424, 828, 60);
			c.ag2 = 1;
		break;

		case 2313:
			c.startAnimation(828);
			c.getAgil().AgilityBranch(c, "Branch", 1, 2473, 3420, 2, 2473, 3423, 828, 60);
			c.getAgil().AgilityBranch(c, "Branch", 1, 2473, 3420, 2, 2474, 3422, 828, 60);
			c.getAgil().AgilityBranch(c, "Branch", 1, 2473, 3420, 2, 2472, 3422, 828, 60);
			c.ag3 = 1;
		break;

		case 2312:
			c.getAgil().AgilityLog(c, "Log", 1, +6, 0, 2477, 3420, 60);
			c.ag4 = 1;
		break;

		case 2314:
			c.startAnimation(828);
			c.sendMessage("You slipped and fell.");
			c.getAgil().AgilityBranch(c, "Branch", 1, 2486, 3420, 0, 2485, 3419, 828, 60);
			c.getAgil().AgilityBranch(c, "Branch", 1, 2486, 3420, 0, 2485, 3420, 828, 60);
			c.getAgil().AgilityBranch(c, "Branch", 1, 2486, 3420, 0, 2486, 3420, 828, 60);
			c.ag5 = 1;
		break;

		case 2286:
			c.startAnimation(828);
			c.getAgil().AgilityNet(c, "Net", 1, 2483, 3425, 0, 2483, 3427, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2484, 3425, 0, 2484, 3427, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2485, 3425, 0, 2485, 3427, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2486, 3425, 0, 2486, 3427, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2487, 3425, 0, 2487, 3427, 828, 60);
			c.getAgil().AgilityNet(c, "Net", 1, 2488, 3425, 0, 2488, 3427, 828, 60);
			c.ag6 = 1;
			c.getAgil().bonus = true;
		break;

		case 154:
			c.fmwalkto(0, 1);
			c.startAnimation(749);
			c.getAgil().AgilityPipe(c, "Pipe", 1, 2484, 3430, 0, +7, 2996, 10, 60);
		break;

		case 4058:
			c.fmwalkto(0, 1);
			c.startAnimation(749);
			c.getAgil().AgilityPipe(c, "Pipe", 1, 2487, 3430, 0, +7, 2996, 10, 60);
		break;
		
		case 82016:
			if(c.takeAsNote = false) {
				c.takeAsNote = true;
			} else if(c.takeAsNote = true) {
				c.takeAsNote = false;
			}
		break;
		
		case 8972:
			if((c.playerLevel[21] < 90) && (c.playerLevel[16] < 90)) {
				c.sendMessage("You need 90 Agility And 90 Hunter to enter the Strykworm's Cave");
			} else if((c.playerLevel[21] > 89) && (c.playerLevel[16] < 90)) {
				c.sendMessage("You need 90 Hunter And Agility to enter the Strykworm's Cave");
			} else if((c.playerLevel[21] < 90) && (c.playerLevel[16] > 89)) {
				c.sendMessage("You need 90 Hunter And Agility to enter the Strykworm's Cave");
			} else if((c.playerLevel[21] > 89) && (c.playerLevel[16] >89)) {
				c.getPA().movePlayer(2515, 4632, 0);
				c.sendMessage("A sense of nervousness fills your body..");
				c.sendMessage("you find yourself in a mystery cave!");
			}
		break;

			case 3333:
			case 3334:
				c.getPA().movePlayer(2145, 4648, 1);
			break;
		
		case 2273:
			c.getPA().movePlayer(0, 0, 0);
			c.sendMessage("Welcome to Woodcutting/Firemaking.");
		break;
		
		case 7315: 
			c.getPA().movePlayer(3488, 9493, 4);
			c.sendMessage("<shad=2134>Welcome To RuneCrafting.</shad>");
		break;
		
		case 2471:
			c.getPA().movePlayer(2795, 4722, 0);
			//c.sendMessage("Welcome to PkBox!");
		break;

		case 4151:
			c.getPA().movePlayer(3089, 3489, 0);
			c.sendMessage("You return home unharmed.");
		break;
		
		case 8987:
			c.getPA().movePlayer(3195, 3429, 0);
		break;
	

		case 6455:
			c.getPA().movePlayer(2837, 3803, 1);
		break;
		
		case 6456:
			c.getPA().movePlayer(2837, 3806, 0);
		break;
		
		case 2469:
			c.getPA().movePlayer(1762, 5180, 0);
		break;
		
		
		case 13623:
			c.getPA().movePlayer(2837, 3806, 0);
			c.sendMessage("Multi Zone Is Working!");
		break;

		case 411:
			if(c.altarPrayed == 0) {
				c.altarPrayed = 1;
				c.setSidebarInterface(5, 22500);
				c.startAnimation(645);
				c.sendMessage("You sense a surge of power flow through your body!");
				AchievementManager.increase(c, Achievements.CURSED);
				c.getCombat().resetPrayers();
			} else {
				c.altarPrayed = 0;
				c.setSidebarInterface(5, 5608);
				c.startAnimation(645);
				c.sendMessage("You sense a surge of purity flow through your body!");
				AchievementManager.increase(c, Achievements.PURITY);
 				c.getCurse().resetCurse();
			}
		break;

		case 13619:
			c.getPA().movePlayer(2717, 9801, 4);
			c.sendMessage("You Teleported to Donor Only TD's!");
			c.sendMessage("Only Donators Can Enter. Enjoy the peace.");
		break;

		case 6452:
			if (c.absX == 3304 && c.absY == 9376) {
				c.getPA().movePlayer(3305, 9376, 4);
				c.sendMessage("Note: It has 3 waves on it's hp bar!");
			} else {
				c.autoRet = 0;
				c.getCombat().resetPlayerAttack();
		        c.getPA().movePlayer(3304, 9376, 0);
			}
		break;

		case 6451:
			if (c.absX == 3304 && c.absY == 9375) {
				c.getPA().movePlayer(3305, 9375, 0);
				c.sendMessage("Prepare for the strongest monster in the game!");
				c.sendMessage("Note: It has 3 waves on it's hp bar!");
			} else {
				c.autoRet = 0;
				c.getCombat().resetPlayerAttack();
		        c.getPA().movePlayer(3304, 9375, 0);
			}
		break;

		case 13625:
			c.getPA().movePlayer(2975, 9515, 1);
			c.sendMessage("You teleported to Barrelchest Non-donators");
			c.sendMessage("The Donators portal to barrelchest is 3 barrelchest bosses spawns!");
		break;

		case 13617:
			c.getPA().movePlayer(2975, 9515, 5);
			c.sendMessage("You teleported to Barrelchest Donators");
			c.sendMessage("You will only see Donators here.");
		break;

		case 13620:
			c.getPA().movePlayer(2721, 9450, 4);
			c.sendMessage("You teleported to steel/iron donator only NPC's!");
			c.sendMessage("You'll only be able to see Donators here, this makes it alot easier to train.");
		break;

		case 13615:
		c.getPA().movePlayer(3115, 9838, 4);
		c.sendMessage("You teleported to Hill Giants donator only NPC's!");
		c.sendMessage("You'll only be able to see Donators here, this makes it alot easier to train.");
		break;

		case 2882:
		case 2883:
			if (c.objectX == 3268) {
				if (c.absX < c.objectX) {
					c.getPA().walkTo(1,0);
				} else {
					c.getPA().walkTo(-1,0);
				}
			}
		break;

		case 272:
			c.getPA().movePlayer(c.absX, c.absY, 1);
		break;
		
		case 273:
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;

		case 60:
		        c.getPA().movePlayer(3195, 3429, 0);
		break;

		case 26428:
		    if (c.Zammy < 15 && c.absX == 2925 && c.absY == 5332) {
				c.sendMessage("You need atleast 15 Nex KC to enter here!");
				return;
		    }	
		    if(c.absX == 2925 && c.absY == 5332) {
				c.getPA().movePlayer(2925, 5331, 6);
		        c.Zammy -= 15;
		        c.sendMessage("A magical force reseted your Nex kill count!");
		    }
		    if(c.absX == 2925 && c.absY == 5331) {
		        c.getPA().movePlayer(2925, 5332, 2);
				c.autoRet = 0;
				c.getCombat().resetPlayerAttack();
		    }
		break;

		case 26425:
		    if (c.Band < 15 && c.absX == 2863 && c.absY == 5354) {
		    	c.sendMessage("You need atleast 15 Bandos KC to enter here!");
		    	return;
		    }	
		    if(c.absX == 2863 && c.absY == 5354) {
	     		c.getPA().movePlayer(2864, 5354, 6);
		        c.Band -= 15;
		        c.sendMessage("A magical force reseted your Bandos kill count!");
		    }
		    if(c.absX == 2864 && c.absY == 5354) {
	     	    c.getPA().movePlayer(2863, 5354, 2);
				c.autoRet = 0;
				c.getCombat().resetPlayerAttack();
		    }
		break;

		case 26426:
		    if (c.Arma < 15 && c.absX == 2839 && c.absY == 5295) {
		       c.sendMessage("You need atleast 15 Armadyl KC to enter here!");
		       return;
		    }		       
		    if(c.absX == 2839 && c.absY == 5295) {
		        c.getPA().movePlayer(2839, 5296, 6);
		        c.Arma -= 15;
		    	c.sendMessage("A magical force reseted your Armadyl kill count!");
		    }
		    if(c.absX == 2839 && c.absY == 5296) {
		        c.getPA().movePlayer(2839, 5295, 2);
				c.autoRet = 0;
				c.getCombat().resetPlayerAttack();
		    }
		break;

		case 26427:
		    if (c.Sara < 15 && c.absX == 2908 && c.absY == 5265) {
		    	c.sendMessage("You need atleast 15 Saradomin KC to enter here!");
		        return;
		    }	
		    if(c.absX == 2908 && c.absY == 5265) {
		        c.Sara -= 15;
		        c.sendMessage("A magical force reseted your Saradomin kill count!");
		        c.getPA().movePlayer(2907, 5265, 4);
		    }
		       if(c.absX == 2907 && c.absY == 5265) {
		        c.getPA().movePlayer(2908, 5265, 0);
				c.autoRet = 0;
				c.getCombat().resetPlayerAttack();
		    }
		break;

		case 2403:
			if (c.Culin == true) {
				c.getShops().openShop(65);
				return;
			}
			if (c.Agrith == true && c.Flambeed == false) {
				c.getShops().openShop(61);
				return;
			} 
			if(c.Flambeed == true && c.Karamel == false) {
				c.getShops().openShop(62);
				return;
			} 
			if(c.Karamel == true && c.Dessourt == false) {
				c.getShops().openShop(63);
				return;
			} 
			if(c.Dessourt == true && c.Culin == false) {
				c.getShops().openShop(64);
				return;
			} 
			if (c.Agrith == false) {
				c.getShops().openShop(60);
			}
		break;

		case 245:
			c.getPA().movePlayer(c.absX, c.absY + 2, 2);
		break;

		case 26293:
			c.getPA().startTeleport(3087, 3492, 0, "modern");
		break;

		case 246:
			c.getPA().movePlayer(c.absX, c.absY - 2, 1);
		break;

		case 1766:
			c.getPA().movePlayer(3016, 3849, 0);
		break;

		case 1295:
			c.getPA().movePlayer(3087, 3493, 0);
		break;

		case 2024:
		case 11:
			c.getPA().movePlayer(3087, 3493, 0);
		break;

		case 410:
			if (c.playerMagicBook == 0) {
				if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15486 || c.playerEquipment[c.playerWeapon] == 15040) {
					c.setSidebarInterface(0, 328);
				}
					c.playerMagicBook = 2;
					c.setSidebarInterface(6, 29999);
					c.sendMessage("You Convert To The Lunar Spellbook.");
					AchievementManager.increase(c, Achievements.LUNAR);
					c.getPA().resetAutocast();
			} else {
				if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15486 || c.playerEquipment[c.playerWeapon] == 15040) {
					c.setSidebarInterface(0, 328);
				}
					c.setSidebarInterface(6, 1151); //modern
					c.playerMagicBook = 0;
					c.sendMessage("You Convert Back To The Modern Spellbook.");
					AchievementManager.increase(c, Achievements.MODERN);
					c.autocastId = -1;
					c.getPA().resetAutocast();
			}
		break;

		case 6552:
			if (c.playerMagicBook == 0) {
				if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15486 || c.playerEquipment[c.playerWeapon] == 15040) {
					c.setSidebarInterface(0, 328);
				}
					c.playerMagicBook = 1;
					c.setSidebarInterface(6, 12855);
					c.sendMessage("You Convert To The Ancient Spellbook.");
					AchievementManager.increase(c, Achievements.ANCIENT);
					c.getPA().resetAutocast();
			} else {
				if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15486 || c.playerEquipment[c.playerWeapon] == 15040) {
					c.setSidebarInterface(0, 328);
				}
					c.setSidebarInterface(6, 1151); //modern
					c.playerMagicBook = 0;
					c.sendMessage("You Convert Back To The Modern Spellbook.");
					AchievementManager.increase(c, Achievements.MODERN);
					c.autocastId = -1;
					c.getPA().resetAutocast();
			}	
		break;

		case 1816:
			c.getPA().startTeleport2(2271, 4680, 0);			
		break;

		case 1817:
			c.getPA().startTeleport(3195, 3429, 0, "modern");
		break;

		case 12467:
		case 12468:
			c.getPA().startTeleport(2391, 9906, 0, "modern");
		break;

		case 1814:
			c.getPA().startTeleport(3153, 3923, 0, "modern");
		break;
		
		case 9356:
		c.getDH().sendOption2("Normal Fightcaves (Firecape)", "TookHar-Kal Caves(New Cape!)");
		c.dialogueAction = 111;
		break;

		case 12356:
			if (c.Culin == true) {
				c.sendMessage("You have already finished this minigame!");
				return;
			}
			if (c.getY() < 3600) {
				c.getPA().enterRFD();
				c.sendMessage("Note: this is not a Safe Minigame, you'll lose your items on death!");
				for(int p = 0; p < c.PRAYER.length; p++) { // reset prayer glows
					c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
					c.getPA().refreshSkill(5);
					c.prayerActive[p] = false;
					c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0);	
				}
			} else {
				c.getPA().resetRFD();
			}
		break;

		case 1734:
			c.getPA().movePlayer(c.absX, c.absY - 6396, 0);
		break;
		
		case 9357:
			c.getPA().resetTzhaar();
		break;
		
		case 8959:
			if (c.getX() == 2490 && (c.getY() == 10146 || c.getY() == 10148)) {
				if (c.getPA().checkForPlayer(2490, c.getY() == 10146 ? 10148 : 10146)) {
					new Object(6951, c.objectX, c.objectY, c.heightLevel, 1, 10, 8959, 15);	
				}			
			}
		break;
		
		case 2213:
		case 14367:
		case 11758:
		case 3193:
		case 11402:
		case 27663:
		case 26972:
		case 16700:
		case 4483:
		case 12120:
		case 12121:
		PlayerSave.saveGame(c);
			c.getPA().openUpBank();
			AchievementManager.increase(c, Achievements.BANKING);
		break;
		
		case 2996:
			if (c.getItems().playerHasItem(989,1) && c.getItems().freeSlots() >= 1 && System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getItems().deleteItem(989, 1);
				c.getItems().addItem(c.getPA().randomCrystal(), 1);
				AchievementManager.increase(c, Achievements.TREASURE);
				AchievementManager.increase(c, Achievements.MADTREASURE);
				c.getDH().sendDialogues(38, 945);
				c.buryDelay = System.currentTimeMillis();
			} else {
				return; 
			}
		break;
		case 6910:
			if (c.getItems().playerHasItem(14515,1)) {
				c.getItems().deleteItem(14515, 1);
				c.getItems().addItem(c.getPA().randomScav(), 1);
				c.getItems().addItem(c.getPA().randomScav(), 1);
				c.getItems().addItem(c.getPA().randomScav(), 1);
				c.getPA().movePlayer(3195, 3429, 0);
			} else {
				c.sendMessage("You don't have the key.");
				return; 
			}
		break;
		case 7222:
			if (c.playerRights == 3) {
				c.getPA().movePlayer(3012, 5046, 1);
			} else {
				c.sendMessage("Wrong Door..");
				return; 
			}
		break;

		case 12112:
				c.getPA().movePlayer(2403, 4457, 1);
		break;

		case 12768:
			if (c.getItems().playerHasItem(4273,1) && c.getItems().freeSlots() >= 2) {
				c.getItems().deleteItem(4273, 1);
				c.getItems().addItem(c.getPA().randomMini(), 1);
				c.getItems().addItem(c.getPA().randomMini(), 1);
				c.getDH().sendDialogues(38, 945);
				c.getPA().movePlayer(3195, 3429, 0);
			} else {
				c.sendMessage("You need the pokemon key to open this chest. Make sure you have 2 free slots.");
			}
		break;

		
			
		case 7318:
			if (!c.antispampush == true) {
				c.getPA().movePlayer(3012, 5235, c.playerId * 4);
				c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.getItems().addItem(391, 1);
				c.getItems().addItem(143, 1);
				c.getPA().refreshSkill(3);
				c.getPA().refreshSkill(5);
				c.InDung = true;
				c.isSkulled = true;
				c.dungPoints += 50;
				c.DUfood4 += 5;
				c.getDungeoneering().spawnFinalWave1(c);
				c.sendMessage("<shad=6081134>[Dungeoneering] <shad=15695415>50 Dungeoneering Tokens added!");
				c.sendMessage("<shad=6081134>[Dungeoneering] <shad=15695415>If you manage to kill the bosses, you will receive 75 more!");
				c.sendMessage("<shad=6081134>[Dungeoneering] <shad=15695415>If you relog, you will have to re-fight the bosses!");
				c.antispampush = true;
				c.juststarted = false;
			}
		break;
		
		case 10177:
			c.getPA().movePlayer(1890, 4407, 0);
		break;

		case 10230:
			c.getPA().movePlayer(2900, 4449, 0);
		break;

		case 10229:
			c.getPA().movePlayer(1912, 4367, 0);
		break;

		case 2623:
			if (c.absX >= c.objectX) {
				c.getPA().walkTo(-1,0);
			} else {
				c.getPA().walkTo(1,0);
			}
		break;

		case 451:
		case 450:
			c.sendMessage("There's no ore left on the rock.");
		break;

		case 2491:
			Mining.mineEss(c, objectType);
		break;
		
		case 14315:
			c.getPA().movePlayer(2661,2639,0);
		break;

		case 14314:
			c.getPA().movePlayer(2657,2639,0);
		break;
		
		case 1596:
		case 1597:
		if (c.getY() >= c.objectY) {
			c.getPA().walkTo(0,-1);
		} else {
			c.getPA().walkTo(0,1);
		}
		break;
		
		case 14235:
		case 14233:
			if (c.objectX == 2670)
				if (c.absX <= 2670)
					c.absX = 2671;
			else
					c.absX = 2670;
			if (c.objectX == 2643)
				if (c.absX >= 2643)
					c.absX = 2642;
			else
					c.absX = 2643;
			if (c.absX <= 2585)
				c.absY += 1;
			else c.absY -= 1;
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;

		case 14829: 
		case 14830:
		case 14827: 
		case 14828: 
		case 14826: 
		case 14831:
			Server.objectManager.startObelisk(objectType);
		break;		
		
		case 9369:
			if (c.IsDueling) {
				c.sendMessage("Error! Please relog before entering here!");
				return;
			}
			if (c.absX == 2399 && c.absY == 5177) {
				c.getPA().walkTo(0, -2);
				c.duelStatus = 0;
				c.inPits2 = true;
			} else {
				c.getPA().walkTo(0, 2);
				c.duelStatus = 0;
				c.inPits2 = true;
			}
		break;
		
		case 9368:
			if (c.getY() < 5169) {
				Server.fightPits.removePlayerFromPits(c.playerId);
				c.getPA().movePlayer(2399, 5169, 0);
			}	
		break;

    	case 4467:
			c.getPA().movePlayer(c.absX == 2834 ? 2385 : 2384, 3134, 0);
		break;
				
		case 4427:
			c.getPA().movePlayer(2373, c.absY == 3120 ? 3119 : 3120, 0);
		break;
				
		case 4428:
			c.getPA().movePlayer(2372, c.absY == 3120 ? 3119 : 3120, 0);
		break;
				
		case 4465:
			c.getPA().movePlayer( c.absX == 2414 ? 2415 : 2414, 3073, 0);
		break;
				
		case 4424:
			c.getPA().movePlayer(2427, c.absY == 3087 ? 3088 : 3087, 0);
		break;
				
		case 4423:
			c.getPA().movePlayer(2427, c.absY == 3087 ? 3088 : 3087, 0);
		break;

		/*case 10284:
			if(c.barrowsKillCount < 5) {
				c.sendMessage("You haven't killed all the brothers.");
			}
			if(c.barrowsKillCount == 5 && c.barrowsNpcs[c.randomCoffin][1] == 1) {
				c.sendMessage("I have already summoned this npc.");
			}
			if(c.barrowsNpcs[c.randomCoffin][1] == 0 && c.barrowsKillCount >= 5) {
				Server.npcHandler.spawnNpc(c, c.barrowsNpcs[c.randomCoffin][0], 3551, 9694-1, 0, 0, 120, 30, 200, 200, true, true);
				c.barrowsNpcs[c.randomCoffin][1] = 1;
			}
			if((c.barrowsKillCount > 5 || c.barrowsNpcs[c.randomCoffin][1] == 2) && c.getItems().freeSlots() >= 2) {
				c.getPA().resetBarrows();
				c.getItems().addItem(c.getPA().randomRunes(), Misc.random(150) + 100);
				if (Misc.random(2) == 1)
					c.getItems().addItem(c.getPA().randomBarrows(), 1);
				c.getPA().startTeleport(3564, 3288, 0, "modern");
			} else if(c.barrowsKillCount > 5 && c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need at least 2 inventory slot opened.");
			}
		break;*/

		//doors
		case 6749:
			if(obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
		break;

		case 6730:
			if(obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
		break;

		case 6727:
			if(obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
		break;

		case 6746:
			if(obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
		break;

		case 6748:
			if(obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
		break;

		case 6729:
			if(obX == 3545 && obY == 9677){
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
		break;

		case 6726:
			if(obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
		break;

		case 6745:
			if(obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
		break;

		case 6743:
			if(obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
		break;

		case 6724:
			if(obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
		break; 

		case 6707: // verac
			c.getPA().movePlayer(3556, 3298, 0);
		break;
			
		case 6823:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY()-1, -1, 0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;

		case 6706: // torag 
			c.getPA().movePlayer(3553, 3283, 0);
		break;
			
		case 6772:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2029, c.getX()+1, c.getY(), -1, 0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;
			
			
		case 6705: // karil stairs
			c.getPA().movePlayer(3565, 3276, 0);
		break;

		case 6822:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY()-1, -1, 0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;
			
		case 6704: // guthan stairs
			c.getPA().movePlayer(3578, 3284, 0);
		break;

		case 6773:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY()-1, -1, 0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;

		case 6703: // dharok stairs
			c.getPA().movePlayer(3574, 3298, 0);
		break;

		case 6771:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[4][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2026, c.getX(), c.getY()-1, -1, 0, 120, 45, 250, 250, true, true);
				c.barrowsNpcs[4][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;
			
		case 6702: // ahrim stairs
			c.getPA().movePlayer(3565, 3290, 0);
		break;

		case 6821:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2025, c.getX(), c.getY()-1, -1, 0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
		break;
			
		case 8143:
			if (c.farm[0] > 0 && c.farm[1] > 0) {
				c.getFarming().pickHerb();
			}
		break;

		case 81:
		if (c.getItems().playerHasItem(11041, 1)) {
			c.getItems().deleteItem(11041, 1);
			c.getPA().movePlayer(3003, 5003, 1);
		} else {
			c.sendMessage("You do not have the key.");
		}
		break;

		case 7273:
		if (c.getItems().playerHasItem(18688, 1)) {
			c.getPA().movePlayer(2150, 5096, 0);
			c.getItems().deleteItem(18688, 1);
			c.sendMessage("Boss Coming Up, Be Prepared.");
		} else {
			c.sendMessage("You do not have the key.");
		}
		break;

		case 82:
		if (c.getItems().playerHasItem(11042, 1)) {
			c.getItems().deleteItem(11042, 1);
			c.getPA().movePlayer(2972, 5004, 1);
		} else {
			c.sendMessage("You do not have the key.");
		}
		break;
		case 1535:
		if (c.getItems().playerHasItem(1544, 1)) {
			c.getItems().deleteItem(1544, 1);
			c.getPA().movePlayer(3039, 5044, 1);
		} else {
			c.sendMessage("You do not have the key.");
		}
		break;
		case 1804:
		if (c.getItems().playerHasItem(14522, 1)) {
			c.getItems().deleteItem(14522, 1);
			c.getPA().movePlayer(3038, 5066, 1);
		} else {
			c.sendMessage("You do not have the key.");
		}
		break;
		case 7234:
		if (c.getItems().playerHasItem(18647, 1)) {
			c.getItems().deleteItem(18647, 1);
			c.getPA().movePlayer(3023, 5082, 1);
		} else {
			c.sendMessage("You do not have the key.");
		}
		break;
		case 2529:
		if (c.getItems().playerHasItem(6084, 1)) {
			c.getItems().deleteItem(6084, 1);
			c.getPA().movePlayer(3005, 5088, 1);
		} else {
			c.sendMessage("You do not have the key.");
		}
		break;
	
			// DOORS
		case 1516:
		case 1519:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(0,-1);
				else
					c.getPA().walkTo(0,1);
				break;
			}
		case 1534:
			if ((c.objectY == 5006) && (c.getItems().playerHasItem(11043, 1))) {
				c.getItems().deleteItem(11043, 1);
				c.getPA().movePlayer(2963, 5007, 1);
			} else if ((c.objectY == 5001) && (c.getItems().playerHasItem(14521, 1))) {
				c.getItems().deleteItem(14521, 1);
				c.getPA().movePlayer(3023, 5001, 1);
			} else {
				Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);	
			}
		break;
		case 1533:
		if ((c.objectY == 5028) && (c.getItems().playerHasItem(1548, 1))) {
				c.getItems().deleteItem(1548, 1);
				c.getPA().movePlayer(3054, 5030, 1);
			} else {
				Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);	
			}
		break;
		case 1531:
		if ((c.objectY == 5001) && (c.getItems().playerHasItem(11039, 1))) {
				c.getItems().deleteItem(11039, 1);
				c.getPA().movePlayer(3045, 5000, 1);
			} else {
				Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);	
			}
		break;
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:

		case 3197:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);	
			break;

		
		case 9319:
			if (c.heightLevel == 0)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			else if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 2);
		break;
		
		case 9320:
			if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 0);
			else if (c.heightLevel == 2)
				c.getPA().movePlayer(c.absX, c.absY, 1);
		break;
		
		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 0);
			}
		break;
		
		case 4493:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;
		
		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397)
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);				
		break;
		case 3203: //dueling forfeit
			if (c.duelCount > 0) {
				c.sendMessage("You may not forfeit yet.");
				break;
			}
			Client o = (Client) Server.playerHandler.players[c.duelingWith];				
			if(o == null) {
				c.getTradeAndDuel().resetDuel();
				c.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
				break;
			}
			if(c.duelRule[0]) {
				c.sendMessage("Forfeiting the duel has been disabled!");
				break;
			}
			if(o != null) {
				o.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
				c.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
				o.duelStatus = 6;
				o.getTradeAndDuel().duelVictory();
				c.getTradeAndDuel().resetDuel();
				c.getTradeAndDuel().resetDuelItems();
				o.sendMessage("The other player has forfeited the duel!");
				c.sendMessage("You forfeit the duel!");
				break;
			}
			
			break;
			
		case 4008:
			if(c.specAmount < 10.0){
            if (c.specRestore > 0) {
				c.specRestore = 180;
                c.specAmount = 10.0;
				c.startAnimation(645);
                c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
                c.sendMessage("Your special attack has been restored.");
				c.sendMessage("You can only do this every 3 minutes.");
            } else {
				c.sendMessage("You must wait at least 3 minutes!");
			}
			} else {
				c.sendMessage("You already have full special attack!");
			}
			break;

			case 409:
			case 13191:
			if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]); c.getPA().getLevelForXP(c.playerXP[5]);
				c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]); c.getPA().getLevelForXP(c.playerXP[3]);
				c.sendMessage("You recharge your prayer points & HP...");
				AchievementManager.increase(c, Achievements.PRAYER);
				AchievementManager.increase(c, Achievements.PRAYERMASTER);
				c.getPA().refreshSkill(5);
				c.getPA().refreshSkill(3);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
                        
			break;
		case 2875:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Guthix blesses you with a cape.");
				c.getItems().addItem(2413, 1);
			}
		break;
		case 2874:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Zamorak blesses you with a cape.");
				c.getItems().addItem(2414, 1);
			}
		break;
		case 2879:
			c.getPA().movePlayer(2538, 4716, 0);
		break;
		case 2878:
			c.getPA().movePlayer(2509, 4689, 0);
		break;
		case 5960:
			c.getPA().startTeleport2(3090, 3956, 0);
		break;
		
		case 1815:
			c.getPA().startTeleport2(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
		break;
		
		case 9706:
			c.getPA().startTeleport2(3105, 3951, 0);
		break;
		case 9707:
			c.getPA().startTeleport2(3105, 3956, 0);
		break;
		
		case 5959:
			c.getPA().startTeleport2(2539, 4712, 0);
		break;
		
		case 2558:
			c.sendMessage("This door is locked.");	
		break;
		
		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
			}
		break;
				case 104:
			if (c.isDonator == 1 && c.donatorChest == 0) {
					c.sendMessage("You need donator chest points.");	

			} else if (c.isDonator == 1 && c.donatorChest >= 10) {
					c.donatorChest -= 10;
					c.getItems().addItem(donatorRitem(),Misc.random(1));
					c.getItems().addItem(995,Misc.random(10000000));	
					c.sendMessage("You use 10 Donator Points, you now have "+c.donatorChest+"");			
					
			} else {
				c.sendMessage("This is a donator only chest.");
			}
		break;
		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
		break;
		case 10529:
		case 10527:
			if (c.absY <= c.objectY)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;
		case 3044:
			c.getSmithing().sendSmelting();
		break;
		case 733:
			c.startAnimation(451);
			/*if (Misc.random(1) == 1) {
				c.getPA().removeObject(c.objectX, c.objectY);
				c.sendMessage("You slash the web.");
			} else {
				c.sendMessage("You fail to slash the webs.");
			}*/
			if (c.objectX == 3158 && c.objectY == 3951) {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 1, 10, 733, 50);
			} else {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 0, 10, 733, 50);
			}
		break;
		
		default:
			ScriptManager.callFunc("objectClick1_"+objectType, c, objectType, obX, obY);
			break;

		}
	}
	
	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		//c.sendMessage("Object type: " + objectType);
		switch(objectType) {
				case 3994:
		ConstructionEvents.MakeIronNails(c);
		break;
							case 2644: // flax spinnner!!
			c.sendMessage("Hold on..Starting event & checking requirments..");
			c.spinFlax();
			break;
		case 2646: // flax picking
			if(System.currentTimeMillis() - c.buryDelay > 1500) {	
			c.getItems().addItem(1779,1);
				c.startAnimation(827);
				c.sendMessage("You pick some flax.");
				c.buryDelay = System.currentTimeMillis();
			}	
			break;
		case 3485:
			if (c.Arma == 1) {
			c.getPA().startTeleport(3561, 9948, 4, "modern"); //Change teleport location
			c.getItems().deleteItem(19042, 1);
			c.sendMessage("<col=255>Type <col=800000000>::startervid<col=255> to check our starter video out!");
			c.getItems().addItem(11639, 1);
			c.getItems().addItem(8816, 1);
			c.getItems().addItem(8817, 1);
			c.getItems().addItem(8818, 1);
			c.Arma = 0;
			} else {
			c.sendMessage("You have already claimed your starter package!");
			}
			return;
		/*case 2152: // SUMMON OBELISK
		case 28722:
c.sendMessage("You can not infuse scrolls at the moment.");
break;*/
case 2152: // SUMMON OBELISK blue one
case 28722: // orange one
c.getPA().closeAllWindows();
c.getPA().showInterface(23471);
c.sendMessage("WARNING: This will create ALL pouches available in you're inventory.");
break;
			case 11666:
			case 3044:
				c.getSmithing().sendSmelting();
			break;
			case 26288:
			case 26287:
			case 26286:
			case 26289:
			c.autoRet = 0;
			c.getCombat().resetPlayerAttack();
			c.getPA().movePlayer(2882, 5310, 2);
			c.sendMessage("You teleported out of the god's chamber.");
			break;
			case 2213:
			case 14367:
			case 11758:
                                                                case 16700:
				c.getPA().openUpBank();
			break;
								case 26303:
			//if (c.hasGrapple()) {
				c.getPA().movePlayer(2872, 5279, 2);
				c.sendMessage("You have entered Armadyl.");
	//			c.getItems().deleteEquipment(9419, c.playerArrows);
				
			//	} else {
			//	c.sendMessage("You need a grapple equipped to swing there");
		//}
		break;

			case 4874:
				c.getThieving().stealFromStall(1625, 10, 1); // uncut opal 
			break;
			case 4875:
				c.getThieving().stealFromStall(371, 30, 45); // raw swordie fish
			break;
			case 4876:
				c.getThieving().stealFromStall(1700, 60, 60); // diamond amulet
			break;
			case 4877:
				c.getThieving().stealFromStall(2529, 100, 80); // orb of light
			break;
			case 4878:
				c.getThieving().stealFromStall(1613, 170, 95); // red topaz
			break;


			case 6163:
				c.getThieving().stealFromStall(2503, 120, 80);
			break;
			case 6165:
				c.getThieving().stealFromStall(4089, 170, 90);
			break;
			case 6166:
				c.getThieving().stealFromStall(2509, 200, 99);
			break;




	
			case 2558:
				if (System.currentTimeMillis() - c.lastLockPick < 3000 || c.freezeTimer > 0)
					break;
				if (c.getItems().playerHasItem(1523,1)) {
						c.lastLockPick = System.currentTimeMillis();
						if (Misc.random(10) <= 3){
							c.sendMessage("You fail to pick the lock.");
							break;
						}
					if (c.objectX == 3044 && c.objectY == 3956) {
						if (c.absX == 3045) {
							c.getPA().walkTo2(-1,0);
						} else if (c.absX == 3044) {
							c.getPA().walkTo2(1,0);
						}
					
					} else if (c.objectX == 3038 && c.objectY == 3956) {
						if (c.absX == 3037) {
							c.getPA().walkTo2(1,0);
						} else if (c.absX == 3038) {
							c.getPA().walkTo2(-1,0);
						}				
					} else if (c.objectX == 3041 && c.objectY == 3959) {
						if (c.absY == 3960) {
							c.getPA().walkTo2(0,-1);
						} else if (c.absY == 3959) {
							c.getPA().walkTo2(0,1);
						}					
					}
				} else {
					c.sendMessage("I need a lockpick to pick this lock.");
				}
				case 2090:
			case 2091:
			case 11938:
				Mining.prospectRock(c, "copper ore");
				break;
			case 3042:
				Mining.prospectRock(c, "daeyalt ore");
				break;
			case 2094:
			case 2095:
			case 3043:
				Mining.prospectRock(c, "tin ore");
				break;
			case 2110:
				Mining.prospectRock(c, "blurite ore");
				break;
			case 2092:
			case 2093:
				Mining.prospectRock(c, "iron ore");
				break;
			case 2100:
			case 2101:
				Mining.prospectRock(c, "silver ore");
				break;
			case 2098:
			case 2099:
				Mining.prospectRock(c, "gold ore");
				break;
			case 2096:
			case 2097:
			case 11930:
				Mining.prospectRock(c, "coal");
				break;
			case 2102:
			case 2103:
			case 11942:
				Mining.prospectRock(c, "mithril ore");
				break;
			case 2104:
			case 2105:
			case 11939:
				Mining.prospectRock(c, "adamantite ore");
				break;
			case 2106:
			case 2107:
			case 14859:
				Mining.prospectRock(c, "runite ore");
				break;
			case 450:
			case 451:
				Mining.prospectNothing(c);
			break;
		default:
			ScriptManager.callFunc("objectClick2_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	
	
	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		switch(objectType) {
		default:
			ScriptManager.callFunc("objectClick3_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	
	public void firstClickNpc(int npcType) {
	c.fishitem = -1;
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if (c.fishitem != -1) {
                    if (!c.getItems().playerHasItem(c.fishitem)) {
                        c.sendMessage("You need a " + c.getItems().getItemName(c.fishitem) + " to fish for " + c.getItems().getItemName(c.fishies));
                        c.fishing = false;
                        return;
                    }
                    if (c.getItems().freeSlots() == 0) {
                        c.sendMessage("Your inventory is full.");
                        c.fishing = false;
                        return;
                    }
                    if (c.playerFishing < c.fishreqt) {
                        c.sendMessage("You need a fishing level of " + c.fishreqt + " to fish here.");
                        c.fishing = false;
                        return;
                    }
                    c.fishtimer = c.getFishing().fishtime(c.fishies, c.fishreqt);
                }
		
		switch(npcType) {
			case 1337:
			c.getShops().openShop(118);
		break;
			case 4445:
				int lastActionTime = (int) ((System.currentTimeMillis() - c.lastAction) / 1000);
				int waitingTime = 900 - lastActionTime;
				if (lastActionTime <= 900) {
					c.sendMessage("Please come back in "+ waitingTime +" seconds.");
				return;
				}
				if (c.diceBanned == 2) {
					c.sendMessage("You have already banned yourself from gambling.");
				}
				if (c.diceBanned == 0) {
					c.getDH().sendOption2("I am going to think about it..", "Nevermind");
					c.dialogueAction = 11172;
				}
				if (c.diceBanned == 1) {
					c.getDH().sendOption2("I Do Not Want To Dice No More", "I Do Want To Dice!");
					c.dialogueAction = 11173;
				c.lastAction = System.currentTimeMillis();
				}
				break;
		
			case 2998:
				int lastTime = (int) ((System.currentTimeMillis() - c.lastThieve) / 1000);
				if (System.currentTimeMillis() - c.lastThieve < 100000 && c.isDonator == 0) {
					c.sendMessage("The <col=800000000>Gambler</col> is recharging: <col=800000000>"+ lastTime +"</col>/<col=800000000> 100</col>.");
					return;
				} else if (System.currentTimeMillis() - c.lastThieve < 50000 && c.isDonator > 0) {
					c.sendMessage("The <col=800000000>Gambler</col> is recharging: <col=800000000>"+ lastTime +"</col>/<col=800000000> 50</col>. Less time for being a <col=800000000>donator</col>.");
					return;
				} else {
				c.sendMessage("<col=800000000>Please use any item desired to dice 55x2 with me!");
				}
				break;
				
				
			case 1423:
				if (c.playerEquipment[c.playerCape] == 896 || c.playerEquipment[c.playerCape] == 19709) {
					c.sendMessage("You can not enter this minigame with an Owners or Master Cape!");
					return;
				}
				c.getPA().movePlayer(3552, 9694, c.playerId * 4);
				c.waveId = 0;
				c.BarrowsToKill = -1;
				c.BarrowsKilled = -1;
				Server.BarrowsTower.spawnNextWave(c);
				break;
			case 1846:
				c.getDH().sendDialogues(9969, 1846);
				break;
			case 1818:
				c.getPA().showInterface(6965);
		c.getPA().sendFrame126("@red@~ Latest Client Update Checker ~",6968);
		c.getPA().sendFrame126("",6969);
		c.getPA().sendFrame126("@gre@Hey! Does your client Frame say:",6970);
		c.getPA().sendFrame126("@bla@      'GEN-13' ? ",6971);
		c.getPA().sendFrame126("@gre@If it does, you're on the correct client!",6972);
		c.getPA().sendFrame126("@gre@If it doesn't, do not worry! Simply type", 6973);
		c.getPA().sendFrame126("@gre@the command @bla@::download@gre@! Thanks for",6974);
		c.getPA().sendFrame126("@gre@checking this out! Have fun playing!",6975);
		c.getPA().sendFrame126("",6976);
		c.getPA().sendFrame126("",6977);
		c.getPA().sendFrame126("",6978);
		c.getPA().sendFrame126("",6979);
		c.getPA().sendFrame126("",6980);
		break;
			case 1375:
				c.getTutorial().handleTutorial();
				c.sendMessage("<col=800000000>You chose for the Regular mode. Goodluck!");
				break;
			case 1376:
				c.playerTitle = 59;
				c.sendMessage("<col=800000000>You chose for the Iron Gambler mode. Goodluck!");
				c.getTutorial().handleTutorial();
				c.ironGambler = 1;
				break;
			case 1377:
				c.playerTitle = 58;
				c.sendMessage("<col=800000000>You chose for the Hardcore Iron Gambler mode. Goodluck!");
				c.getTutorial().handleTutorial();
				c.ironGambler = 2;
				break;
			case 5287:
				if (c.getItems().playerHasItem(15500)) {
				c.getItems().deleteItem(15500, 1);
				c.getItems().addItem(19867, 1);
				c.donatorChest ++;
				c.totalDonatorPoints ++;
				c.sendMessage("You traded in your Ice Strykewyrm Statue for one Torva Box and 1 donator point!");
				} else { 
					c.sendMessage("<col=255>You need at least one Ice Strykewyrm Statue in order to collect your reward!");
				}
			break;
			case 168:
				if (c.getItems().playerHasItem(19042)) {
				c.getPA().startTeleport(3561, 9948, 4, "modern"); //Change teleport location
				c.getItems().deleteItem(19042, 1);
				c.sendMessage("<col=255>Type <col=800000000>::startervid<col=255> to check our starter video out!");
				c.getItems().addItem(11639, 1);
				c.getItems().addItem(8816, 1);
				c.getItems().addItem(8817, 1);
				c.getItems().addItem(8818, 1);
				}
				return;
			case 4456:
				c.getDH().sendDialogues(9969, 4456);
			break;
			case 4457:
				c.getDH().sendDialogues(8888, 4457);
				break;
			case 250:
			c.getShops().openShop(117);
		break;
			case 1378:
			c.getShops().openShop(121);
		break;
			case 1665:
					c.getDH().sendDialogues(2994, 1665);
					break;
			case 1164: //loyalty store
				if (c.LoyaltyPoints >= 199 && (c.getItems().freeSlots() >= 1 )) {
					c.LoyaltyPoints -= 200;
					c.getItems().addItem(3537, 1);
				} else if (c.getItems().freeSlots() == 0 ) {
				c.sendMessage("<col=255>You need at least 1 free inventory slot to collect your Casket V!");
				}else {
					c.sendMessage("<col=255>You need at least 200 Loyalty Points to collect your Casket V!");
					c.sendMessage("<col=255>You currently have "+ c.LoyaltyPoints +" loyalty points.");
				}
				break;
		                case 1704:
                c.getDH().sendDialogues(9995, npcType);
                break;
                
		                case 1701:
		                    c.getDH().sendDialogues(9996, npcType);
		                    break;
			case 659:
				if (c.ironGambler > 0) {
					c.sendMessage("<col=800000000>You currently have <col=255>"+c.donatorChest+"<col=800000000> donator points. Type <col=255>::donate<col=800000000> to add some more!");
					c.getShops().openShop(122);
				}
				if (c.ironGambler == 0) {
			    c.getShops().openShop(72);
			c.sendMessage("<col=800000000>You currently have <col=255>"+c.donatorChest+"<col=800000000> donator points. Type <col=255>::donate<col=800000000> to add some more!");
				}break;
			case 2221:
			c.sendMessage("You stole one of Haxmans lamborghinis! I would suggest not telling him.");
			c.npcId2 = 2221;
			c.isNpc = true;
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
				c.getPA().startTeleport2(2387, 3488, 0);
			break;

				case 501:
		c.getDH().sendDialogues(99911, npcType);
		break;
			case 5910:
			if (c.getItems().playerHasItem(17644, 28)){
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().addItem(17645, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
				c.getItems().deleteItem(17644, 28);
			} else {
			c.sendMessage("<col=255>You need 28 Dragonbone Ores to talk to me! Mine them at the purple rocks!");
			}
			break;

			case 5911:
			if (c.getItems().playerHasItem(12240, 1)){
			if (c.getItems().playerHasItem(5021, 250)){
				c.startAnimation(4939);
				c.sendMessage("<col=255>You exchanged your regular drygore rapier + 250b for an upgraded drygore rapier!");
				c.getItems().deleteItem(12240, 1);
				c.getItems().deleteItem(5021, 250);
				c.getItems().addItem(17416, 1);
				} else {
				c.sendMessage("<col=255>You need a regular drygore rapier and 250x 1B tickets for this feature!");
				}
			}
			break;


			case 670:
			    c.getShops().openShop(119);
			    c.sendMessage("You currently have "+c.PresentsDone+" Mummy points!");
			break;


				 case 597:
                c.getPA().showInterface(43000);
                break;
				case 3919:
			c.getDH().sendDialogues(129, 3919);
			break;
                case 954:
                c.getDH().sendDialogues(179, npcType);
                break;
				case 1281:
				c.getDH().sendDialogues(1002, npcType);
				break;
				case 1699:
				c.getDH().sendDialogues(1006, npcType);
				break;
				case 746:
				c.getDH().sendDialogues(1007, npcType);
				break;
				case 817:
				c.getDH().sendDialogues(1009, npcType);
				break;
				case 823:
				c.getDH().sendDialogues(1011, npcType);
				break;
				case 616:
				c.getDH().sendDialogues(1013, npcType);
				break;
				case 618:
				c.getDH().sendDialogues(1015, npcType);
				break;
				case 619:
				c.getDH().sendDialogues(1017, npcType);
				break;
				case 620:
				c.getDH().sendDialogues(1019, npcType);
				break;
				case 621:
				c.getDH().sendDialogues(1021, npcType);
				break;
				case 625:
				c.getDH().sendDialogues(1023, npcType);
				break;

				case 1395:
				c.getPA().startTeleport2(2710, 3462, 0);
				break;
				case 162:
				c.getPA().startTeleport2(2479, 3434, 0);
				break;
				case 805:
				c.getPA().startTeleport2(2741, 3443, 0);
				break;

				case 927:
					if (c.playerLevel[10] >= 100) { 
						if (System.currentTimeMillis() - c.buryDelay > 7500) {
							if (c.getItems().playerHasItem(311, 1)){
								c.startAnimation(618);
								c.sendMessage("You Catch a Big Swordfish.");
								c.getItems().addItem(7991, 1);
								c.getPA().addSkillXP(50000, c.playerFishing);
								c.buryDelay = System.currentTimeMillis();
							} else {
								c.sendMessage("You must have a Harpoon.");
								return;
							}
						} else {
							return;
						}
					} else {
						c.sendMessage("You need 100 Fishing to fish here.");
						return;
					}
				break;
				case 1238:
					if (c.playerLevel[10] >= 110) { 
						if (System.currentTimeMillis() - c.buryDelay > 7500) {
							if (c.getItems().playerHasItem(311, 1)){
								c.startAnimation(618);
								c.sendMessage("You Catch a Big Shark.");
								c.getItems().addItem(7993, 1);
								c.getPA().addSkillXP(80000, c.playerFishing);
								c.buryDelay = System.currentTimeMillis();
							} else {
								c.sendMessage("You must have a Harpoon.");
								return;
							}
						} else {
							return;
						}
					} else {
						c.sendMessage("You need 110 Fishing to fish here.");
						return;
					}
				break;
				case 1236:
					if (c.playerLevel[10] >= 115) { 
						if (System.currentTimeMillis() - c.buryDelay > 7500) {
							if (c.getItems().playerHasItem(311, 1)){
								c.startAnimation(618);
								c.sendMessage("You Catch a Whale!");
								c.getItems().addItem(4176, 1);
								c.getPA().addSkillXP(120000, c.playerFishing);
								c.buryDelay = System.currentTimeMillis();
							} else {
								c.sendMessage("You must have a Harpoon.");
								return;
							}
						} else {
							return;
						}
					} else {
						c.sendMessage("You need 115 Fishing to fish here.");
						return;
					}
				break;
case 1237:
					if (c.playerLevel[10] >= 125) { 
						if (System.currentTimeMillis() - c.buryDelay > 7500) {
							if (c.getItems().playerHasItem(311, 1)){
								c.startAnimation(618);
								c.sendMessage("You Catch a Sting Ray.");
								c.getItems().addItem(4177, 1);
								c.getPA().addSkillXP(150000, c.playerFishing);
								c.buryDelay = System.currentTimeMillis();
							} else {
								c.sendMessage("You must have a Harpoon.");
								return;
							}
						} else {
							return;
						}
					} else {
						c.sendMessage("You need 125 Fishing to fish here.");
						return;
					}
				break;
				case 600:
				if (c.playerRights == 1 || c.playerRights == 3) {
				c.getShops().openShop(101);
				} else {
				c.sendMessage("This is moderators only shop!");
				}
				break;
				case 3000:
				c.getShops().openShop(17);
				c.sendMessage("You currently have "+c.Skillpoints+" Skill Points.");
				break;
				case 421:
				c.getShops().openShop(115);
				break;
				case 248:
				c.getShops().openShop(116);
				break;
				case 601:
				if (c.playerRights == 2 || c.playerRights == 3) {
				c.getShops().openShop(102);
				} else {
					c.sendMessage("This is Administrators only shop!");
				}
				break;
				case 602:
				if (c.playerRights == 3) {
				c.getShops().openShop(103);
				} else {
					c.sendMessage("This is Owners only shop!");
				}
				break;
				case 811:
				c.getShops().openShop(54);
				c.sendMessage("You currently have <col=255>"+ c.votingPoints +" </col>vote points!");
				break;
				case 814:
				c.getShops().openShop(91);
				break;
				case 2353:
				c.getShops().openShop(87);
				break;
				case 2352:
				c.getShops().openShop(85);
				break;
				case 819:
				c.getShops().openShop(95);
				break;

                case 71:
                c.getShops().openShop(52);
                break;
					case 7121:
			if(c.playerLevel[1] >= 99 && c.playerLevel[2] >= 99 && c.playerLevel[3] >= 99 && c.playerLevel[4] >= 99 && c.playerLevel[5] >= 99 && c.playerLevel[6] >= 99 && c.playerLevel[7] >= 99 && c.playerLevel[8] >= 99 && c.playerLevel[9] >= 99 && c.playerLevel[10] >= 99 && c.playerLevel[11] >= 99 && c.playerLevel[12] >= 99 && c.playerLevel[13] >= 99 && c.playerLevel[14] >= 99 && c.playerLevel[15] >= 99 && c.playerLevel[16] >= 99 && c.playerLevel[17] >= 99 && c.playerLevel[18] >= 99 && c.playerLevel[19] >= 99 && c.playerLevel[20] >= 99 && c.playerLevel[21] >= 99 && c.playerLevel[22] >= 99 && c.playerLevel[24] >= 99 && c.playerLevel[23] >= 99) {
				//c.getShops().openShop(77);
				c.getDH().sendDialogues(4444, 1);
				//c.sendMessage("You open the store LikeABaws..");
			} else {
				c.sendMessage("You need to be 99 in all stats to talk to the General.");
			}
			break;
				case 223:
		if(c.playerLevel[3] > 30 && c.playerLevel[5] > 50) {
		c.getDH().sendDialogues(3333, 1);
		} else {
		if(c.playerLevel[3] < 30 && c.playerLevel[5] < 50) {
		c.getDH().sendDialogues(3332, 1);
		}
		}
		break;
                case 8206:
		if (c.playerRights < 1) {
		c.getDH().sendDialogues(185, 0);
		} else {
		c.sendMessage("Your rank is too high to participate in the lottery!");
		}
		break;
                case 532:
		c.getShops().openShop(54);
		break;

		case 2617:
		c.getShops().openShop(57);
		break;
		case 694:
		c.getShops().openShop(118);
		if (c.ironGambler >= 1)
		c.sendMessage("<col=255>You can only sell items in this shop!");
		break;
		case 5622:
		c.getShops().openShop(41);
		break;
		case 1552:
		c.getShops().openShop(91);
		break;
		
					case 3236:
				c.getShops().openShop(98);
				c.sendMessage("If the shop runs out of Nails, You will have to smith them you'reself!");
			break;
		case 4247:
									if (c.vlsLeft2 > 1 && c.playerLevel[23] < 98) {
		c.getDH().sendDialogues(29170, npcType);
		}
							if (c.vlsLeft2 < 1 && c.playerLevel[23] < 98) {
		c.getDH().sendDialogues(29164, npcType);
		}
		break;
		case 794:
		c.getDH().sendDialogues(2196, npcType);
		break;
						case 219:
c.getDH().sendDialogues(1196, npcType);
		break;
				case 3920:
c.getDH().sendDialogues(1185, npcType);
		break;
		case 4295:
				c.getDH().sendDialogues(1175, npcType);
		break;
                case 251:
                c.getDH().sendDialogues(4433, 1);
                break;
                case 932:
                c.getDH().sendDialogues(4439, 1);
                break;
		case 1167:
		c.getDH().sendDialogues(1155, npcType);
		break;
		case 553:
							c.getPA().showInterface(6965);
					c.getPA().sendFrame126("@red@~ RC Instruction Manual ~",6968);
					c.getPA().sendFrame126("",6969);
					c.getPA().sendFrame126("@gre@To Train RC, get a talisman from",6970);
					c.getPA().sendFrame126("@gre@Aubury's shop, then you need to ",6971);
					c.getPA().sendFrame126("@gre@Push Locate on the talisman.",6972);
					c.getPA().sendFrame126("@gre@To be teleported to the ruins,",6973);
					c.getPA().sendFrame126("@gre@Use your talisman on the Alters.",6974);
					c.getPA().sendFrame126("@gre@And you will be teleported there!",6975);
					c.getPA().sendFrame126("",6976);
					c.getPA().sendFrame126("",6977);
					c.getPA().sendFrame126("",6978);
					c.getPA().sendFrame126("",6979);
					c.getPA().sendFrame126("",6980);
					break;
		case 1928: //Bandit
				c.getDH().sendDialogues(4448, npcType);
		case 7143: //firemaker
				c.getDH().sendDialogues(1137, npcType);
		break;
		case 4906: // woodcutter
		c.getDH().sendDialogues(1112, npcType);
		break;
				case 569: // crafter
		c.getDH().sendDialogues(1111, npcType);
		break;
		case 5559: // osman
			if (c.ironGambler > 0) {
		c.getShops().openShop(123);
		c.sendMessage("You currently have "+c.barbPoints+" Raid Points.");
		}
			if (c.ironGambler == 0) {
		c.getShops().openShop(100);
		c.sendMessage("You currently have "+c.barbPoints+" Raid Points.");
		}
		break;
				case 9716: // barb assault
			c.getDH().sendDialogues(1054, npcType);
		break;
		case 6163:
		c.getPA().movePlayer(2872, 5269, 2);
		break;
                                          /** Summoning Pickup **/
				case 3506:
				case 3507:
				case 761:
				case 760:
				case 762:
				case 763:
				case 764:
				case 765:
				case 766:
				case 767:
				case 768:
				case 769:
				case 770:
				case 771:
				case 772:
				case 773:
				case 3505:
                                case 6903:
                                case 666:
				c.getSummon().pickUpClean(c, c.summonId);
				c.hasNpc = false;
				c.summonId = 0;
				break;
		case 6165:
		c.getPA().movePlayer(2872, 5279, 2);
		break;
				case 33:
		c.getDH().sendDialogues(119, 1);
		c.sendMessage("To delete your PIN type ::delete ");
		break;
       
		case 1703: // Sailor Ghost
			if(c.present1 = false) {
                        c.present += 1;
                        c.present1 = true;
			c.sendMessage("<shad=838383>Here, Have my present! Make sure this gets to Santa!");
                        return;
                        }
                        if(c.present1 = true && c.present < 15) {
			c.sendMessage("<shad=838383>You Already Have My present, Ask the other Ghosts!");
			return;
			}
                        break;
					case 9713:
					/*if (c.hasFollower != -1) {
		c.sendMessage("Please dismiss your Familiar first!");
		return;
		}*/
		
		/*      c.hasFollower = -1;
                       for (int j = 0; j < c.playerEquipment.length; j++) {
                                        if (c.playerEquipment[j] > 0) {
	    					c.sendMessage("Please Un-Equip all your worn Inventory before teleporting to Dungeoneering.");
						return;
}
}
			c.sendMessage("You Teleport to the Dungeoneering Lobby.");
			c.sendMessage("Items in your inventory have been banked.");
			c.sendMessage("You prayer points have been completely Drained.");
			for(int i = 0; i < c.playerItems.length; i++) {
				c.getItems().bankItem(c.playerItems[i], i,c.playerItemsN[i]);
				c.getPA().movePlayer(1871, 4620, 0);
				c.InDung = true;
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
				c.prayerId = -1;
				c.isSkulled = true;
				c.getPA().closeAllWindows();
				c.getPA().refreshSkill(5);
			}*/
			c.getDH().sendDialogues(400, 1);
			break;
					 case 549:
        /*	if(c.kingQuest == 5) {
        		c.getDH().sendDialogues(68, npcType);
        		return;
			} else if(c.kingQuest == 0) {
c.getShops().openShop(4);
c.sendMessage("To be able to wear Rune Platebody's, finnish Dragon Slayer! Talk to Kazgar");
        		return;
        	} else if(c.kingQuest == 6) {
        		c.sendMessage("Go talk to sedridor again");
        		return;
        	} else if(c.kingQuest == 8) {*/
			c.getShops().openShop(4);
			c.sendMessage("You currently have <col=255>" + c.PkminiPoints + "</col> PvP Points");
        //	}
        	break;
     /*   case 300:
        	if(c.kingQuest <= 0) {
        		c.sendMessage("You must talk to Razgar to talk to him");
        		return;
        	} else if(c.kingQuest == 1) {
        		c.sendMessage("Go talk to Gypsy Aris in varrock first");
        		return;
        	} else if(c.kingQuest == 2) {
        		c.getDH().sendDialogues(61, npcType);
        		return;
        	} else if(c.kingQuest == 3 && c.getItems().playerHasItem(536, 2)) {
        		c.getDH().sendDialogues(65, npcType);
        		c.getItems().deleteItem2(536, 2);
        	} else if(c.kingQuest == 3) {
        		c.sendMessage("You have dont have 2 Dragon bones!");
        		return;
        	} else if(c.kingQuest == 4) {
        		c.sendMessage("You already gave him the required items");
        		return;
        	} else if(c.kingQuest == 5) {
        		c.sendMessage("Go talk to horvik in edgeville, behind the bank!");
        		return;
        	} else if(c.kingQuest == 7) {
			if(c.getItems().freeSlots() < 2) {
			c.sendMessage("You need atleast 2 free inventory slots to receive reward");
			return;
			}
        		c.getDH().sendDialogues(75, npcType);
        		return;
        	} else if(c.kingQuest == 8) {
        		c.sendMessage("<shad=15695415>You already completed the quest");
				c.sendMessage("<shad=15695415>You can now buy Rune PlateBodys & More from Horvik!");
        	} else if(c.kingQuest == 6) {
        		c.getDH().sendDialogues(77, npcType);
        		return;
        	}
        	break;
        case 882:
        	if(c.kingQuest == 1) {
        		c.getDH().sendDialogues(55, npcType);
        		return;
        	} else if(c.kingQuest == 2) {
        		c.sendMessage("Go talk to the wizard in lumbridge's graveyard");
        		return;
        	} else if(c.kingQuest <= 0) {
        		c.sendMessage("You must talk to Razgar to talk to him, Razgar is in edge bank");
        		return;
        	} else if(c.kingQuest == 8) {
        		c.sendMessage("<shad=15695415>You already completed the quest");
        	}
        	break;
        case 2086:
        	if(c.kingQuest <= 0) {
        		c.getDH().sendDialogues(50, npcType);
        	return;
        	} else if(c.kingQuest == 1) {
        		c.sendMessage("Go talk to Gypsy Aris in varrock first");
        		return;
        	} else if(c.kingQuest == 8) {
        		c.sendMessage("<shad=15695415>You already completed the quest");
        	}
        	break;*/
			
		case 6390:
			if(c.grimPrize == 0) {
			c.getDH().sendDialogues(90, npcType);
			} else if (c.grimPrize == 2) {
			c.sendMessage("You've alredy completed this Minigame..");
			} else if (c.grimPrize == 1) {
			if(c.getItems().freeSlots() < 7) {
			c.sendMessage("You need atleast 7 free inventory slots to receive reward");
			return;
			}
			if(c.getItems().playerHasItem(317, 1) && c.getItems().playerHasItem(11256, 1) && c.getItems().playerHasItem(1513, 1) && c.getItems().playerHasItem(1727, 1) && c.getItems().playerHasItem(451, 3)) {
			c.getDH().sendDialogues(104, npcType);
			}
			c.sendMessage("You don't have the items.. You need 1 Raw Shrimp, 1 Dragon Impling Jar");
			c.sendMessage("1 Magic log, 1 Amulet of magic and 3 Runite Ores.");
			}
			break;
			
			case 3299:
						c.getDH().sendDialogues(1115, npcType);
						break;
		case 2244:
		c.getDH().sendDialogues(3848, 2244);
		break;
			case 8591: 
c.getDH().sendDialogues(8591, npcType); 
break;

case 5879: 
c.getDH().sendDialogues(3663, npcType); 
break;
// START OF RUNE MYSTERIES QUEST 
/*			case 553:  // AUBURY 
			if(c.rMQ == 0) {
c.getDH().sendDialogues(553, npcType); 
return;
} else if(c.rMQ == 1) {
c.sendMessage("Go talk to Distentor first.. He's in Wizards tower south of Draynor");
c.sendMessage("You can teleport to Draynor by using a Amulet Of Glory");
return;
} else if(c.rMQ == 4) {
c.sendMessage("Go talk to Distentor!");
return;
} else if(c.rMQ == 6) {
c.getDH().sendDialogues(577, npcType); 
return;
} else if(c.rMQ == 8) {
c.sendMessage("You've alredy finnished this quest!");
return;
} else if(c.rMQ == 7) {
if(c.getItems().freeSlots() < 3) {
c.sendMessage("You need atleast 3 free inventory slots to receive reward");
return;
}
c.getDH().sendDialogues(580, npcType); 
} else if (c.getItems().playerHasItem(681, 1) && (c.rMQ == 3)) {
c.getDH().sendDialogues(565, npcType); 
return;
} else if (!c.getItems().playerHasItem(681, 1) && (c.rMQ == 3)) {
c.getDH().sendDialogues(564, npcType); 
return;
}
break; 
case 462:
if(c.rMQ == 1) {
c.getDH().sendDialogues(558, npcType); 
return;
} else if(c.rMQ == 2) {
c.sendMessage("Right click on me and choose teleport to go to the basement.");
return;
} else if(c.rMQ == 6) {
c.sendMessage("Go talk to Aubury!!");
return;
} else if(c.rMQ == 8) {
c.sendMessage("You've alredy finnished this quest!");
return;
} else if(c.rMQ == 4) {
c.getDH().sendDialogues(570, npcType); 
return;
} else if (c.getItems().playerHasItem(554, 10) && c.getItems().playerHasItem(556, 10) && c.getItems().playerHasItem(3040, 1) && (c.rMQ == 5)) {
c.getDH().sendDialogues(575, npcType); 
return;
} else if(c.rMQ == 5) {
c.sendMessage("Bring the stuff i wanted then talk to me..");
return;
}
break;*/
case 1283: 
c.getDH().sendDialogues(1283, npcType); 
break;
			                        case 209:
		//	if(c.bMQ >= 3) {						
			c.getDH().sendDialogues(209, npcType);
	/*		} else {
			c.sendMessage("You need to complete the Herblore quest - talk to Kaqemeex!");
			}*/
			break;
			case 875:
				if(c.absX > 2816 && c.absX < 2880 && c.absY > 2944 && c.absY < 3007) {
			c.getShops().openPlayerShop(c);
			} else {
			c.sendMessage("You can only view your shops in home.");
			}
			break;
			case 2127:
			if(c.playerCollect > 0){
			c.sendMessage("You succesfully collected "+c.playerCollect+" coins.");
			c.getItems().addItem(995, c.playerCollect);
			c.playerCollect = 0;
			}else{
			c.sendMessage("You dont have anything to collect");
			}
			break;
		
			case 706:
				c.getDH().sendDialogues(9, npcType);
			break;
			case 7601:
				c.getDH().sendDialogues(70, 4289);
			break;
			case 6794:
			case 6873:
			c.getDH().sendDialogues(75, 4289);
			break;
case 946: 
c.getDH().sendDialogues(20, npcType); 
break;
case 607: 
c.sendMessage("You have: "+c.Wheel+" Agility Points. Get more by completing the course");
c.getShops().openShop(16); 
break;
			case 1113:
				c.getPA().movePlayer(3448, 3517, 0);
				c.sendMessage("You have teleported to the Summoning shops!");
			break;
case 6970:
c.getShops().openShop(12); 
break;
case 6971: 
c.getShops().openShop(13); 
break;
						case 455:
					c.getDH().sendDialogues(1130, npcType);
			break;
                case 316:
                    c.fishing = true;
					c.fishXP = 1000;
                    c.fishies = 317;
                    c.fishreqt = 0;
                    c.fishitem = 303;
                    c.fishemote = 621;
                    c.fishies2 = 0;
                    c.fishreq2 = 0;
                break;
                
                case 334:
                    c.fishing = true;
					c.fishXP = 1000;
                    c.fishies = 317;
                    c.fishreqt = 0;
                    c.fishitem = 303;
                    c.fishemote = 621;
                    c.fishies2 = 0;
                    c.fishreq2 = 0;
                break;
                case 324://cage-harpoon spot choice cage
                    c.fishing = true;
                    c.fishXP = 7000;
                    c.fishies = 377;
                    c.fishreqt = 40;
                    c.fishitem = 301;
                    c.fishemote = 619;
                    c.fishies2 = 389;
                    c.fishreq2 = 81;
					break;
		case 325:
		c.fishing = true;
                    c.fishXP = 20000;
                    c.fishies = 15272;
                    c.fishreqt = 99;
                    c.fishitem = 301;
                    c.fishemote = 619;
                    c.fishies2 = 15272;
                    c.fishreq2 = 99;
                break;
		case 8647:
		c.fishing = true;
                    c.fishXP = 10;
                    c.fishies = 17797;
                    c.fishreqt = 80;
                    c.fishitem = 301;
                    c.fishemote = 619;
                    c.fishies2 = 17797;
                    c.fishreq2 = 80;
                break;
		case 320:
		c.fishing = true;
                    c.fishXP = 20000;
                    c.fishies = 15270;
                    c.fishreqt = 99;
                    c.fishitem = 301;
                    c.fishemote = 619;
                    c.fishies2 = 15272;
                    c.fishreq2 = 95;
                break;
		case 326:
                    c.fishing = true;
                    c.fishXP = 2000;
                    c.fishies = 341;
                    c.fishreqt = 23;
                    c.fishitem = 303;
                    c.fishemote = 621;
                    c.fishies2 = 363;
                    c.fishreq2 = 46;
                break;
		case 313:
                    c.fishing = true;
					c.fishXP = 2000;
                    c.fishies = 341;
                    c.fishreqt = 23;
                    c.fishitem = 303;
                    c.fishemote = 621;
                    c.fishies2 = 363;
                    c.fishreq2 = 46;
                break;
		case 3100: 
		c.getPA().movePlayer(2717, 9801, 0);
		c.sendMessage("Goodluck killing the creatures from hell!");
		break;
			case 4289:
			c.kamfreenaDone = true;
			c.getDH().sendDialogues(47, 4289);
				break;
			case 1061:
			c.inCyclops = true;
			c.getWarriorsGuild().handleKamfreena(c, true);
			break;
			case 1062:
			c.kamfreenaDone = false;
			c.inCyclops = false;
			c.getWarriorsGuild().handleKamfreena(c, true);
			break;
			case 2258:
				c.getPA().showInterface(27000);
			break;
			case 2261:
				c.getPA().walkableInterface(-1);
				c.getPA().movePlayer(2885, 5330, 2);
			break;

			case 2259:
			if (c.absX == 2885 && c.absY == 5345 && c.heightLevel == 2) {
				c.getPA().movePlayer(2885, 5332, 2);
				} else {
				c.getPA().movePlayer(2885, 5345, 2);
				c.getPA().walkableInterface(12418);
				c.sendMessage("You have entered Nex's Room, To leave talk to me on the other side.");
		}
		break;
	
		
			case 398:
				c.getPA().movePlayer(2918, 5273, 0);
				c.sendMessage("You have entered Saradomin, To leave talk to me on the other side.");
			break;
			case 399:
				c.getPA().movePlayer(2911, 5299, 2);
			break;
			case 1064:
				c.getPA().movePlayer(2852, 5333, 2);
			break;

			case 1063:
				c.getPA().movePlayer(2849, 5333, 2);
				c.sendMessage("You have entered Bandos, To leave talk to me on the other side.");
			break;

			case 70:
				c.getPA().movePlayer(2872, 5269, 2);
				c.sendMessage("You have entered Armadyl, To leave click the Pillar.");
				c.sendMessage("Note: Ruby bolts (e) and Diamond bolts (e) are recommended!");
			break;
			case 8275:
			case 9085:
				if (c.slayerTask <= 0) {
					c.getDH().sendDialogues(11,npcType);
				} else {
					c.getDH().sendDialogues(13,npcType);
				}
			break;
			case 500:
			if (c.monkeyk0ed >= 20) {
					c.getDH().sendDialogues(30,npcType);
				} else {
					c.getDH().sendDialogues(32,npcType);
				}			
			break;
			case 793:
				c.getShops().openShop(83);
				//c.getPA().showInterface(18070);
//c.getPA().sendFrame126(""+c.dungPoints+"", 18071);
				//c.sendMessage("You currently have <col=255>" + c.dungPoints + "</col> Dungeoneering Tokens.");
			break;
			case 9711:
				c.getPA().showInterface(39500);
			break;
                        case 540:
			c.getDH().sendDialogues(540, npcType);
			break;
			
                        case 545:
			c.getDH().sendDialogues(545, npcType);
			break;
			case 211: 
c.getDH().sendDialogues(211, npcType); 
break;
                        case 692:
			    c.getShops().openShop(90);
			break;
			case 286:
			    c.getShops().openShop(92);
			break;
			case 9400:
			    c.getShops().openShop(93);
			break;
			case 919:
                        if(c.isDonator == 1) {
			    c.getShops().openShop(10);
                            c.sendMessage("<col=255>You Opened the donator shop!");
                        } else {
                            c.sendMessage("<col=255>You Need to Be Donator to open this shop!");
                        }
			break;
                        case 2284:
                        c.getPA().showInterface(12000);
                        c.sendMessage("<col=255>Welcome to the loyalty Shop!");
                        c.sendMessage("<col=255>You are awarded 100 loyalty points a hour!");
                        break;
		/*	case 1008:
			    c.getShops().openShop(82);
				c.getItems().deleteItem(12093, 1);
				c.getItems().deleteItem(12087, 1);
				c.getItems().deleteItem(12031, 1);
				c.getItems().deleteItem(12007, 1);
			break;
			case 9716:
			    c.getShops().openShop(81);
				c.getItems().deleteItem(12093, 1);
				c.getItems().deleteItem(12087, 1);
				c.getItems().deleteItem(12031, 1);
				c.getItems().deleteItem(12007, 1);
			break;
			case 9715:
			    c.getShops().openShop(80);
				c.getItems().deleteItem(12093, 1);
				c.getItems().deleteItem(12087, 1);
				c.getItems().deleteItem(12031, 1);
				c.getItems().deleteItem(12007, 1);
		    break;
			case 1202:
				c.getShops().openShop(79);
				c.getItems().deleteItem(12093, 1);
				c.getItems().deleteItem(12087, 1);
				c.getItems().deleteItem(12031, 1);
				c.getItems().deleteItem(12007, 1);
			break;
			case 560:
				c.getShops().openShop(78);
				c.getItems().deleteItem(12093, 1);
				c.getItems().deleteItem(12087, 1);
				c.getItems().deleteItem(12031, 1);
				c.getItems().deleteItem(12007, 1);
			break;*/
			case 3381:
				c.getShops().openShop(76);
			break;
			case 2830:
				c.getShops().openShop(73);
			break;
			case 5030:
				c.getShops().openShop(74);
			break;
			case 1294:
				c.getShops().openShop(72);
			break;
			case 5839:
				c.getShops().openShop(75);
			break;

			case 1778:
				c.getShops().openShop(71);
			break;
			case 1779:
				c.getShops().openShop(67);
			break;
			case 554:
				c.getShops().openShop(68);
			break;
			case 520:
				c.getShops().openShop(69);
			break;
			case 542:
			if(c.isDonator == 1) { 
			    c.getShops().openShop(9);
                        } else {
                            c.sendMessage("<col=255>You Need to Be Donator to open this shop!");
                        }
	                break;
			case 4290:
				c.getShops().openShop(66);
			break;
			
			case 461:
				c.getShops().openShop(2);
			break;
			
			case 683:
				c.getShops().openShop(3);
				break;
			case 1917:
				c.getShops().openShop(120);
			break;
			
			
			//hunter
			
			//implin's
			case 6055:
			c.CatchimpNpc("Baby Impling", 10010, 6055, 11238, 1500, 1, c.playerId);
			break;
			case 6056:
			c.CatchimpNpc("Young Impling", 10010, 6056, 11240, 3500, 17, c.playerId);
			break;
			case 6057:
			c.CatchimpNpc("Gourmet Impling", 10010, 6057, 11242, 4000, 20, c.playerId);
			break;
			case 6058:
			c.CatchimpNpc("Earth Impling", 10010, 6058, 11244, 5000, 34, c.playerId);
			break;
			case 6059:
			c.CatchimpNpc("Essence impling", 10010, 6059, 11246, 6000, 40, c.playerId);
			break;
			case 6060:
			c.CatchimpNpc("Electic impling", 10010, 6060, 11248, 8000, 50, c.playerId);
			break;
			case 6061:
			c.CatchimpNpc("Nature impling", 10010, 6061, 11250, 10000, 58, c.playerId);
			break;
			case 6062:
			c.CatchimpNpc("Magpie impling", 10010, 6062, 11252, 12500, 65, c.playerId);
			break;
			case 6063:
			c.CatchimpNpc("Ninja impling", 10010, 6063, 11254, 14000, 74, c.playerId);
			break;
			case 6064:
			c.CatchimpNpc("Dragon Impling", 10010, 6064, 11256, 25000, 90, c.playerId);
			break;
			
			//end of implin's!
			
			//butterfly's
							case 5082:
				c.CatchHunterNpc("Black Warlock", 10010, 5082, 10014, 18000, 85, c.playerId);
				break;
				case 5083:
				c.CatchHunterNpc("Snowy Knight", 10010, 5083, 10016, 15000, 75, c.playerId);
				break;
				case 5084:
				c.CatchHunterNpc("Sapphire Glacialis", 10010, 5084, 10018, 7500, 45, c.playerId);
				break;
				case 5085:
				c.CatchHunterNpc("Ruby Harvest", 10010, 5085, 10020, 5000, 30, c.playerId);
				break;
			//end of butterfly's	
				
		// end of hunter :)
			case 2538:
				c.getShops().openShop(6);
			break;
			
			case 519:
				c.getShops().openShop(8);
			break;
			case 1282:
				c.getShops().openShop(7);
			break;
			case 1152:
				c.getDH().sendDialogues(16,npcType);
			break;

			case 992:
				c.getDH().sendDialogues(70,npcType);
			break;
			case 494:
			case 496:
				//c.getPA().openUpBank();
				c.getDH().sendDialogues(397,npcType);
			break;
			case 2566:
				c.getPA().showInterface(29560);
                                c.sendMessage("<col=255>Welcome to the new Skillcape Shop!, 99k Each!");
			break;
case 3789: 
c.getDH().sendDialogues(3789, npcType); 
break;	
			case 3788:
				c.getShops().openVoid();
			break;
			case 905:
				c.getDH().sendDialogues(5, npcType);
			break;
			case 460:
				c.getDH().sendDialogues(3, npcType);
			break;
			case 6138:
                        if(c.isDonator == 1) { 
			    c.getDH().sendDialogues(200, npcType);
                        } else {
                            c.sendMessage("<col=255>For the Ability to reset your stat's you need donor!");
                        }
			break;
			case 521:
			case 522:
			case 523:
				c.getShops().openShop(1);
			break;
			case 599:
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
			break;
			case 904:
				c.sendMessage("You have " + c.mbPoints + " points.");
			break;
		default:
			ScriptManager.callFunc("npcClick1_"+npcType, c, npcType);
			if(c.playerRights == 3) 
				Misc.println("First Click Npc : "+npcType);
			break;

					}
	}

public void store(int i, int npcType)
{


switch(npcType) {
case 6807:
if(Server.npcHandler.npcs[i].npcId == c.summoningnpcid) {
c.sendMessage("You are now storing items inside your npc");
	c.Summoning().store();
}
break;
}
}

	public void secondClickNpc(int npcType) {
	c.fishitem = -1;
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if (c.fishitem != -1) {
                    if (!c.getItems().playerHasItem(c.fishitem)) {
                        c.sendMessage("You need a " + c.getItems().getItemName(c.fishitem) + " to fish for " + c.getItems().getItemName(c.fishies));
                        c.fishing = false;
                        return;
                    }
                    if (c.getItems().freeSlots() == 0) {
                       c. sendMessage("Your inventory is full.");
                        c.fishing = false;
                        return;
                    }
                    if (c.playerFishing < c.fishreqt) {
                        c.sendMessage("You need a fishing level of " + c.fishreqt + " to fish here.");
                        c.fishing = false;
                        return;
                    }
                    c.fishtimer = c.getFishing().fishtime(c.fishies, c.fishreqt);
                }
		switch(npcType) {
		case 4247: //using vlsLeft for house LOL
		if(c.vlsLeft2 > 0) {
		c.getPA().movePlayer(3352, 3346, 0);
		c.sendMessage("Enter the portal!");
		} else {
		if(c.vlsLeft2 < 0) {
		c.sendMessage("You don't have a house! Talk to the Agent!");
		}
		}
		break;
		case 2574:
			if (c.hasFollower == 2574){
				c.getPA().openUpBank();
			} else {
				c.sendMessage("You do not have a banker pet following you!");
			}
			break;
		case 4027:
			if (c.hasFollower == 4027){
				c.getPA().getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
				c.sendMessage("Your prayer has been restored.");
			}
			break;
		case 4028:
			if (c.hasFollower == 4028){
				c.getPA().getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
				c.sendMessage("Your prayer has been restored.");
			}
			break;
		case 4029:
			if (c.hasFollower == 4029){
				c.getPA().getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
				c.sendMessage("Your prayer has been restored.");
			}
			break;
		case 4030:
			if (c.hasFollower == 4030){
				c.getPA().getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
				c.sendMessage("Your prayer has been restored.");
			}
			break;
		case 4031:
			if (c.hasFollower == 4031){
				c.getPA().getLevelForXP(c.playerXP[5]);
				c.getPA().refreshSkill(5);
				c.sendMessage("Your prayer has been restored.");
			}
			break;
		case 1423:
			c.getShops().openShop(77);
			c.sendMessage("You Currently Have <col=800000000>"+ c.barrowPoints +"</col> Barrow Minigame Points.");
			break;
		case 1699:
		c.getPA().showInterface(57000);
		break;
			case 2221:
			c.sendMessage("You stole one of Haxmans lamborghinis! I would suggest not telling him.");
			c.npcId2 = 2221;
			c.isNpc = true;
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
				c.getPA().startTeleport2(2387, 3488, 0);
			break;
		
				case 2234:
				c.getPA().startTeleport2(2819, 3462, 0);
				break;
		case 9085:
				if (c.slayerTask <= 0) {
					c.getDH().sendDialogues(11,npcType);
				} else {
					c.getDH().sendDialogues(13,npcType);
				}
			break;
		case 7557:
		c.getDH().sendDialogues(1000, npcType); 
		break;
                case 251:
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] <= c.getPA().getXPForLevel(10)) {
c.sendMessage("Sorry But You Need Level 10 in ALL Levels To view the Lowest Shop!");
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(10) && c.playerXP[i] <= c.getPA().getXPForLevel(20)) {
c.getShops().openShop(31); //edit the shops hck3r 
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(20) && c.playerXP[i] <= c.getPA().getXPForLevel(30)) {
c.getShops().openShop(15); 
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(30) && c.playerXP[i] <= c.getPA().getXPForLevel(40)) {
c.getShops().openShop(14);
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(40) && c.playerXP[i] <= c.getPA().getXPForLevel(50)) {
c.getShops().openShop(32);
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(50) && c.playerXP[i] <= c.getPA().getXPForLevel(60)) {
c.getShops().openShop(34);
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(60) && c.playerXP[i] <= c.getPA().getXPForLevel(70)) {
c.getShops().openShop(36);
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(70) && c.playerXP[i] <= c.getPA().getXPForLevel(80)) {
c.getShops().openShop(37);
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(80) && c.playerXP[i] <= c.getPA().getXPForLevel(90)) {
c.getShops().openShop(20);
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(90) && c.playerXP[i] <= c.getPA().getXPForLevel(99)) {
c.getShops().openShop(39);
return;
}
}
for (int i = 0; i < 24; i++) {
if(c.playerXP[i] >= c.getPA().getXPForLevel(99)){
c.getShops().openShop(38);
}
}
break;
		case 794:
		c.getShops().openShop(50);
		break;
								case 219:
c.getShops().openShop(48);
		break;
		case 3920:
				c.getShops().openShop(47);
				c.sendMessage("For better logs to fletch, you will have to cut them yourself.");
		break;
				case 4295:
			c.getShops().openShop(1);
		break;
		case 1167:
		c.getShops().openShop(44);
		break;
		case 596:
                c.getShops().openShop(97);
                AchievementManager.increase(c, Achievements.CUS);
                break;
				
		case 597:
                c.getPA().showInterface(43000);
                break;
		case 7143:
		c.getShops().openShop(25);
		c.sendMessage("To get better logs you will have to cut them yourself.");
		break;
								case 455: // herblore
				// if(c.bMQ >= 3) {
				c.getShops().openShop(76);
				c.sendMessage("<shad=15369497>You need to get the herbs yourself - via farming/players.");
/*				} else if(c.bMQ == 0) {
c.getDH().sendDialogues(2000, npcType); 
} else if (c.getItems().playerHasItem(2136, 1) && c.getItems().playerHasItem(2134, 1) && c.getItems().playerHasItem(2138, 1) && (c.bMQ == 1)) {
c.getDH().sendDialogues(2012, npcType); 
return;
} else if(c.bMQ == 1) {
c.sendMessage("Bring me the following items before talking to me:");
c.sendMessage("Raw bear meat, from Varrock's woods, north east of Varrock Square..");
c.sendMessage("Raw Chicken meat, from Camelot");
c.sendMessage("And finaly Raw Rat Meat, from Varrock multi level 1 Wilderness!");
return;
} else if (c.getItems().playerHasItem(523, 1) && c.getItems().playerHasItem(524, 1) && c.getItems().playerHasItem(525, 1) && (c.bMQ == 2)) {
c.getDH().sendDialogues(2017, npcType); 
return;
} else if(c.bMQ == 2) {
c.sendMessage("Bring me the following items before talking to me:");
c.sendMessage("Enchanted Raw Rat Meat, Enchanted Raw Bear Meat, and Enchanted Chicken meat.");
return;
				}*/
			break;
				case 3299:
						c.getShops().openShop(26);
						break;
				case 4906: // woodcutter
		c.getShops().openShop(73);
		break;
				case 569: // crafter
				if(c.playerLevel[12] > 19) {
		c.getShops().openShop(71);
		} else {
		c.sendMessage("You need atleast a level of 20 to make amulets.");
		c.sendMessage("Train on flax till level 20!");
		}
		break;
					case 9713:
			//    c.getShops().openShop(85);
			//	c.sendMessage("You currently have " + c.dungPoints + " Dungeoneering Tokens.");
			if(c.playerLevel[24] > 119) {
			c.getShops().openShop(67);
			c.sendMessage("Wow! Your 120 Dungeoneering so you may now access the best-looking cape!");
			}
			if(c.playerLevel[24] > 98) {
			c.getShops().openShop(59);
			c.sendMessage("To acces the better-looking cape you need to be 120 Dungeoneering.");
			} else {
			c.sendMessage("You need atleast level 99 Dungeoneering to view that shop!");
			}
			break;
								case 9711:
			    c.getShops().openShop(85);
				c.sendMessage("You currently have " + c.dungPoints + " Dungeoneering Tokens.");
			break;
				case 549:
			c.getShops().openShop(5);
			c.sendMessage("You currently have <col=255>" + c.PkminiPoints + "</col> PvP Points");
				break;
	/*	case 462: // RUNE MYSTERIES
if(c.rMQ == 2) {
c.getPA().movePlayer(3105, 9576, 0); 
return;
} else if(c.rMQ == 1) {
c.sendMessage("Talk to me first!!");
return;
} else if(c.rMQ == 6) {
c.sendMessage("Go talk to aubury idiot");
return;
} else if (c.getItems().playerHasItem(1436, 10) && (c.rMQ == 5)) {
c.getPA().movePlayer(2842, 4832, 0);
c.sendMessage("Return to Distentor when you've got the 10 Air Runes!");
return;
} else if(c.rMQ == 5) {
c.sendMessage("Bring the stuff i wanted then talk to me..");
return;
}
break;*/
					case 526:
				c.getShops().openShop(14);
			break;
			case 1282:
				c.getShops().openShop(7);
			break;
			case 553:
		//	if(c.rMQ >= 8) { // RUNE MYSTERIES DONE?
			c.getShops().openShop(19);
		//	} else {
		//	c.sendMessage("You need to finnish Rune mysteries before training RuneCrafting!");
		//	} 
			break;
			case 333:
                    c.fishing = true;
                    c.fishXP = 6000;
					c.sendMessage(""+Config.SERVER_NAME+": You receive 6K bonus XP for this catch.");
                    c.fishies = 359;
                    c.fishreqt = 35;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 371;
                    c.fishreq2 = 50;
					break;
                case 312:
					 c.fishing = true;
                    c.fishXP = 6000;
                    c.fishies = 359;
                    c.fishreqt = 35;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 371;
                    c.fishreq2 = 50;
					break;
                case 324:
                    c.fishing = true;
                    c.fishXP = 6000;
					c.sendMessage(""+Config.SERVER_NAME+": You receive 6K bonus XP for this catch.");
                    c.fishies = 359;
                    c.fishreqt = 35;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 371;
                    c.fishreq2 = 50;
		break;
                case 334:
                    c.fishing = true;
                    c.fishXP = 6000;
					c.sendMessage(""+Config.SERVER_NAME+": You receive 6K bonus XP for this catch.");
                    c.fishies = 359;
                    c.fishreqt = 35;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 371;
                    c.fishreq2 = 50;
		break;
                case 316:
                    c.fishing = true;
                    c.fishXP = 1500;
					c.fishies = 327;
					c.sendMessage(""+Config.SERVER_NAME+": You receive 1.5K bonus XP for this catch.");
					c.fishreqt = 5;
                    c.fishitem = 307;
                    c.fishemote = 622;
                    c.fishies2 = 345;
                    c.fishreq2 = 10;
					break;
                case 326:
                    c.fishing = true;
                    c.fishXP = 530;
					c.sendMessage(""+Config.SERVER_NAME+": You receive 2.5K bonus XP for this catch.");
					c.fishies = 327;
					c.fishreqt = 5;
                    c.fishitem = 307;
                    c.fishemote = 622;
                    c.fishies2 = 345;
                    c.fishreq2 = 10;
					break;
               case 331:
                    c.fishing = true;
                    c.fishXP = 2500;
					c.sendMessage(""+Config.SERVER_NAME+": You receive 2.5K bonus XP for this catch.");
                    c.fishies = 349;
                    c.fishreqt = 25;
                    c.fishitem = 307;
                    c.fishemote = 622;
                    c.fishies2 = 0;
                    c.fishreq2 = 0;			
					
									


                case 313:
                    c.fishing = true;
                    c.fishXP = 15000;
					c.sendMessage(""+Config.SERVER_NAME+": You receive 15K Fishing XP!");
                    c.fishies = 383;
                    c.fishreqt = 79;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 0;
                    c.fishreq2 = 0;
                break;
			case 3788:
				c.getShops().openVoid();
			break;
			case 494:
				c.getPA().openUpBank();
			break;
						case 27663:
				c.getPA().openUpBank();
			break;
			case 904:
				c.getShops().openShop(96);
			break;
			case 521:
			case 522:
			case 523:
				c.getShops().openShop(1);
			break;
			case 541:
				c.getShops().openShop(5);
			break;
			
			case 461:
				c.getShops().openShop(2);
			break;
			
			case 683:
				c.getShops().openShop(3);
			break;
		case 8591:
		c.sendMessage("Kill Me And You Will Receive Two Nice Capes");
		break;
			
									case 540:
				c.getShops().openShop(27);
								c.sendMessage("To find out what scroll does what/or what scroll for what npc u need");
			c.sendMessage("Type ::scrolls");
			break;
						case 545:
				c.getShops().openShop(28);
				c.sendMessage("To find out what scroll does what/or what scroll for what npc u need");
			c.sendMessage("Type ::titans");
			break;

			case 209:						
			c.getShops().openShop(74);
			break;
			
			case 659:
				if (c.ironGambler > 0) {
					c.getShops().openShop(122);
					c.sendMessage("<col=800000000>You currently have <col=255>"+c.donatorChest+"<col=800000000> donator points. Type <col=255>::donate<col=800000000> to add some more!");
				}
				if (c.ironGambler == 0) {
			    c.getShops().openShop(72);
			c.sendMessage("<col=800000000>You currently have <col=255>"+c.donatorChest+"<col=800000000> donator points. Type <col=255>::donate<col=800000000> to add some more!");
				}
			break;

				
		    case 170:
			    c.getPA().showInterface(802);
			break;

			case 2538:
				c.getShops().openShop(6);
			break;
			
			case 519:
				c.getShops().openShop(8);
			break;
			case 3789:
				c.getShops().openShop(18);
				c.sendMessage("You have " + c.pcPoints + " "+Config.SERVER_NAME+" Points! Gain these from Slayer/Pc/Duel arena!");
			break;
			case 1:
			case 9:
			case 18:
			case 20:
			case 26:
			case 21:
				c.getThieving().stealFromNPC(npcType);
			break;
			default:
				ScriptManager.callFunc("npcClick2_"+npcType, c, npcType);
				if(c.playerRights == 3) 
					Misc.println("Second Click Npc : "+npcType);
				break;
			
		}
	}
	
	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch(npcType) {
		case 7557:
		c.getPA().startTeleport2(2095, 3162, 0);
		break;
		case 9085:
				if((c.playerLevel[24] < 75) && (c.playerLevel[18] < 92)) {
		c.sendMessage("You need 75 Dungeoneering and 92 Slayer to face frost dragons!");
		} else {
		if((c.playerLevel[24] > 75) && (c.playerLevel[18] < 92)) {
		c.sendMessage("You need 75 Dungeoneering and 92 Slayer to face frost dragons!");
		} else {
		if((c.playerLevel[24] < 74) && (c.playerLevel[18] > 91)) {
		c.sendMessage("You need 75 Dungeoneering and 92 Slayer to face frost dragons!");
		} else {
		if((c.playerLevel[24] > 74) && (c.playerLevel[18] >91)) {
		c.getPA().movePlayer(3052, 9577, 0);
		c.sendMessage("A nervous chill runs down your spine..");
		c.sendMessage("you find yourself in the Frost Dragons Lair!");
		}
		}
		}
		}
		break;
										case 794:
				if(c.playerLevel[7] == 99) {
		c.getShops().openShop(51);
		c.sendMessage("Welcome, master Cook "+c.playerName+"!");
		} else {
		c.sendMessage("You're not level 99 in Cooking yet!");
		}
		break;
			case 2221:
			c.sendMessage("You stole one of King's lamborghinis! I would suggest not telling him.");
			c.npcId2 = 2221;
			c.isNpc = true;
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
				c.getPA().startTeleport2(2387, 3488, 0);
			break;
								case 219:
				if(c.playerLevel[10] == 99) {
		c.getShops().openShop(49);
		c.sendMessage("Welcome, master Fisher "+c.playerName+"!");
		} else {
		c.sendMessage("You're not level 99 in Fishing yet!");
		}
		break;
		case 1699:
				AchievementManager.writeInterface(c);
				break;
						case 3920:
				if(c.playerLevel[9] == 99) {
		c.getShops().openShop(70);
		c.sendMessage("Welcome, master Fletcher "+c.playerName+"!");
		} else {
		c.sendMessage("You're not level 99 in Fletching yet!");
		}
		break;
							case 4295:
				if(c.playerLevel[17] == 99) {
		c.getShops().openShop(35);
		c.sendMessage("Welcome, master Thief "+c.playerName+"!");
		} else {
		c.sendMessage("You're not level 99 in Thieving yet!");
		}
		break;
					case 553:
				if(c.playerLevel[20] == 99) {
		c.getShops().openShop(30);
		c.sendMessage("Welcome, master RuneCrafter "+c.playerName+"!");
		} else {
		c.sendMessage("You're not level 99 in Runecrafting!");
		}
		break;
		case 596:
                c.getShops().openShop(97);
                break;
		case 597:
		c.getPA().showInterface(43000);
		break;
		case 1167: // hunter
		if(c.playerLevel[21] == 99) {
		c.getShops().openShop(45);
		c.sendMessage("Welcome, master Hunter "+c.playerName+"!");
		} else {
		c.sendMessage("You're not level 99 in Hunter.");
		}
		break;
										case 7143:
				if(c.playerLevel[11] == 99) {
		c.getShops().openShop(29);
		c.sendMessage("Welcome, master FireMaker "+c.playerName+"!");
		} else {
		c.sendMessage("You're not level 99 in FireMaking!");
		}
		break;
		
								case 455:
				if(c.playerLevel[15] == 99) {
		c.getShops().openShop(24);
		c.sendMessage("Welcome, master Potion Maker "+c.playerName+"!");
		} else {
		c.sendMessage("You're not level 99 in Herblore yet!");
		}
		break;
						case 569:
				if(c.playerLevel[12] == 99) {
		c.getShops().openShop(22);
		c.sendMessage("Welcome, master Crafter "+c.playerName+"!");
		} else {
		c.sendMessage("You're not level 99 in Crafting yet!");
		}
		break;
				case 3299:
		if(c.playerLevel[19] == 99) {
		c.getShops().openShop(23);
		c.sendMessage("Welcome, master Farmer "+c.playerName+"!");
		} else {
		c.sendMessage("Achieve 99 Farming first!");
		}
		break;
		case 4906: // woodcutter
		if(c.playerLevel[8] == 99) {
		c.getShops().openShop(75);
		c.sendMessage("Welcome, master WoodCutter "+c.playerName+"!");
		} else {
		c.sendMessage("Achieve 99 WoodCutting first! Go Chop' Chop'!");
		}
		break;
                case 4247: // Construction
		if(c.playerLevel[23] == 99) {
		c.getShops().openShop(21);
		c.sendMessage("Welcome, master Constructer "+c.playerName+"!");
		} else {
		c.sendMessage("Achieve 99 Construction first! Go Build Shit!");
		}
		break;
		
				

	
		}
	}
	

}