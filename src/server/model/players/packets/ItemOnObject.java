package server.model.players.packets;

import server.model.items.UseItem;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.skills.*;
import server.model.minigames.*;

/**
 * @author Jeff :D
 */
@SuppressWarnings("all")
public class ItemOnObject implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		/*
		 * a = ?
		 * b = ?
		 */
		
		int a = c.getInStream().readUnsignedWord();
		int objectId = c.getInStream().readSignedWordBigEndian();
		int objectY = c.getInStream().readSignedWordBigEndianA();
		int b = c.getInStream().readUnsignedWord();
		int objectX = c.getInStream().readSignedWordBigEndianA();
		int itemId = c.getInStream().readUnsignedWord();
		UseItem.ItemonObject(c, objectId, objectX, objectY, itemId);
		c.turnPlayerTo(objectX, objectY);
		c.cookingCoords[0] = objectX;
		c.cookingCoords[1] = objectY;
		  if(!c.getItems().playerHasItem(itemId, 1)) {
            return;
        }
						if (itemId == 2134 && objectId == 2142) { // HERBLORE QUEST
				c.startAnimation(1670, 0);
				c.sendMessage("You dip the meat... It turns all blue?");
				c.getItems().deleteItem(2134, 1);
				c.getItems().addItem(523, 1);
			}
			
				if (itemId == 2136 && objectId == 2142) { // HERBLORE QUEST
				c.startAnimation(1670, 0);
				c.sendMessage("You dip the meat... It turns all blue?");
				c.getItems().deleteItem(2136, 1);
				c.getItems().addItem(524, 1);
			}
			
				if (itemId == 2138 && objectId == 2142) { // HERBLORE QUEST
				c.startAnimation(1670, 0);
				c.sendMessage("You dip the meat... It turns all blue?");
				c.getItems().deleteItem(2138, 1);
				c.getItems().addItem(525, 1);
			}
			
		if (itemId == 1438 && objectId == 2452) { // Air Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2841, 4829, 0);
			}

			if (itemId == 229 && objectId == 43) { 
				c.startAnimation(827, 0);
				c.sendMessage("You Fill Your Vial Full Of Spirit Water");
                                                                                     c.getItems().deleteItem(229, 1);
			                     c.getItems().addItem(17413, 1);
			} 
  
                                                                if (itemId == 4151 && objectId == 43) { // vial of water
				c.sendMessage("Use A Vial Of Water From This Fountain On This Whip");
			} 
   
                                                                if (itemId == 1440 && objectId == 2455) { // Earth Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2655, 4830, 0);
			} 

			if (itemId == 7991 && objectId == 2728) {
				if (c.playerLevel[7] >= 100) { 
					c.startAnimation(883);
					c.sendMessage("You Cook The Big Swordfish");
					c.getItems().deleteItem(7991, 1);
					c.getItems().addItem(7992, 1);
					c.getPA().addSkillXP(50000, c.playerCooking);
				} else {
					c.sendMessage("You need 100 cooking to do this.");
					return;
				}
			} 
			if (itemId == 7993 && objectId == 2728) {
				if (c.playerLevel[7] >= 110) { 
					c.startAnimation(883);
					c.sendMessage("You Cook The Big Shark");
					c.getItems().deleteItem(7993, 1);
					c.getItems().addItem(7994, 1);
					c.getPA().addSkillXP(80000, c.playerCooking);
				} else {
					c.sendMessage("You need 110 cooking to do this.");
					return;
				}
			}
			if (itemId == 4176 && objectId == 2728) {
				if (c.playerLevel[7] >= 115) { 
					c.startAnimation(883);
					c.sendMessage("You Cook The Whale");
					c.getItems().deleteItem(4176, 1);
					c.getItems().addItem(3301, 1);
					c.getPA().addSkillXP(120000, c.playerCooking);
				} else {
					c.sendMessage("You need 115 Cooking to do this.");
					return;
				}
			}
			if (itemId == 4177 && objectId == 2728) {
				if (c.playerLevel[7] >= 125) { 
					c.startAnimation(883);
					c.sendMessage("You Cook The Stingray");
					c.getItems().deleteItem(4177, 1);
					c.getItems().addItem(3303, 1);
					c.getPA().addSkillXP(150000, c.playerCooking);
				} else {
					c.sendMessage("You need 125 Cooking to do this.");
					return;
				}
			}
			if (itemId == 668 && objectId == 3044) {
				if (c.playerLevel[13] >= 100) { 
					c.startAnimation(899);
					c.sendMessage("You Make a Blurite Bar.");
					c.getItems().deleteItem(668, 1);
					c.getItems().addItem(9467, 1);
					c.getPA().addSkillXP(50000, c.playerSmithing);
				} else {
					c.sendMessage("You need 100 Smithing to do this.");
					return;
				}
			}
			if (itemId == 434 && objectId == 3044) {
				if (c.playerLevel[13] >= 110) { 
					c.startAnimation(899);
					c.sendMessage("You Make Softclay.");
					c.getItems().deleteItem(434, 1);
					c.getItems().addItem(1761, 1);
					c.getPA().addSkillXP(80000, c.playerSmithing);
				} else {
					c.sendMessage("You need 110 Smithing to do this.");
					return;
				}
			}
			if (itemId == 9632 && objectId == 3044) {
				if (c.playerLevel[13] >= 115) { 
					c.startAnimation(899);
					c.sendMessage("You Make a Blue Dragon Bar.");
					c.getItems().deleteItem(9632, 1);
					c.getItems().addItem(7996, 1);
					c.getPA().addSkillXP(120000, c.playerSmithing);
				} else {
					c.sendMessage("You need 115 Smithing to do this.");
					return;
				}
			}
			if (itemId == 17644 && objectId == 3044) {
				if (c.playerLevel[13] >= 125) { 
					c.startAnimation(899);
					c.sendMessage("You Make a Dragonbone Bar.");
					c.getItems().deleteItem(17644, 1);
					c.getItems().addItem(7997, 1);
					c.getPA().addSkillXP(150000, c.playerSmithing);
				} else {
					c.sendMessage("You need 125 Smithing to do this.");
					return;
				}
			}
			if (itemId == 8004 && objectId == 8151) {
				if (c.playerLevel[19] >= 100) { 
					c.startAnimation(899);
					c.sendMessage("You Receive a Grimy Lavender.");
					c.getItems().deleteItem(8004, 1);
					c.getItems().addItem(8002, 1);
					c.getPA().addSkillXP(50000, c.playerFarming);
				} else {
					c.sendMessage("You need 100 Farming to do this.");
					return;
				}
			}
			if (itemId == 736 && objectId == 8151) {
				if (c.playerLevel[19] >= 110) { 
					c.startAnimation(899);
					c.sendMessage("You Receive a Grimy Rose.");
					c.getItems().deleteItem(736, 1);
					c.getItems().addItem(8005, 1);
					c.getPA().addSkillXP(80000, c.playerFarming);
				} else {
					c.sendMessage("You need 110 Farming to do this.");
					return;
				}
			}
			if (itemId == 18752 && objectId == 8151) {
				if (c.playerLevel[19] >= 115) { 
					c.startAnimation(899);
					c.sendMessage("You Receive a Grimy Orchid.");
					c.getItems().deleteItem(18752, 1);
					c.getItems().addItem(18750, 1);
					c.getPA().addSkillXP(120000, c.playerFarming);
				} else {
					c.sendMessage("You need 115 Farming to do this.");
					return;
				}
			}
			if (itemId == 18753 && objectId == 8151) {
				if (c.playerLevel[19] >= 125) { 
					c.startAnimation(899);
					c.sendMessage("You Receive a Grimy Tulip.");
					c.getItems().deleteItem(18753, 1);
					c.getItems().addItem(19940, 1);
					c.getPA().addSkillXP(150000, c.playerFarming);
				} else {
					c.sendMessage("You need 125 Farming to do this.");
					return;
				}
			}


			if (itemId == 1442 && objectId == 3312) { // Fire Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2574, 4848, 0);
			} 
			if (itemId == 1444 && objectId == 3184) { // Water Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2727, 4833, 0);
			}
			if (itemId == 1446 && objectId == 3052) { // Body Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2522, 4825, 0);
			}
			if (itemId == 1448 && objectId == 2981) { // Mind Alter
				c.startAnimation(1670, 0);
				c.sendMessage("A mysterious force grabs hold of you.");
				c.getPA().movePlayer(2792, 4827, 0);
		}
		switch (objectId) {

		case 12269:
		case 2732:
		case 114:
		case 9374:
		case 2728:
		case 25465:
		case 11404:
		case 11405:
		case 11406:
			Cooking.cookThisFood(c, itemId, objectId); 
			break;
			
		}
	}

}
