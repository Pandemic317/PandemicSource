package server.model.players;

import server.Server;
import server.model.players.Client;

/**
 * 
 * @author 2012 : 18/08/2011
 * 
 */

public class AchievementExtra {
	
	public static void addExtra(Client c, int achievement) {
		switch (achievement) {

		case Achievements.MUNCHER:
			c.getPA().addSkillXP(10000, 3);
			addItems(c, new int[][] { {386, 50}, {392, 20}, {380, 250}});
			c.sendMessage("You're awarded 50 sharks, 20 manta ray and 250 lobsters!");
			c.sendMessage("And also 10,000 hitpoints experience!");
		break;

		case Achievements.POKEMASTER:
			addItems(c, new int[][] {{4273, 1}});
			c.sendMessage("You're awarded an extra key!");
			c.sendMessage("Double Click The Chest!!!");
		break;	
		

		case Achievements.NOTAFRAID:
			addItems(c, new int[][] { {989, 1}});
			c.sendMessage("You're awarded A Crystal Key!");
			c.sendMessage("You have unlocked the ability to equip the 'Not Afraid' title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;	

		case Achievements.BANKING:
			c.customPoints += 2;
			c.pcPoints += 2;
			c.dungPoints += 2;
			c.sendMessage("You're awarded With: 2 Custom Points, 2 Pandemic Points &");
			c.sendMessage("2 Dungeoneering Points.");
		break;

		case Achievements.CATCHEMALL:
			c.getPA().movePlayer(3017, 5199, 0);
			c.customPoints += 10;
			c.sendMessage("You've been granted access to round 3 & granted the points you missed.");
			c.sendMessage("You Received 10 Custom Points.");
		break;	

		case Achievements.TREASURE:
			addItems(c, new int[][] { {989, 1}});
			c.sendMessage("You Received a Free Crystal Key.");
		break;	

		case Achievements.MADTREASURE:
			addItems(c, new int[][] { {989, 1}, {995, 250000000} });
			c.sendMessage("You Received a Free Crystal Key & 250m");
		break;	

		case Achievements.DEDICATION:
			addItems(c, new int[][] {{995, 200000000}});
			c.sendMessage("You Received a Free 200m.");
		break;

		case Achievements.VETERAN:
			addItems(c, new int[][] {{995, 1000000000}, {19933, 1}});
			c.sendMessage("You Received Veteran Status, A Veteran Cape & One Bil.");
		break;

		case Achievements.PRAYER:
			addItems(c, new int[][] {{537, 10}});
			c.sendMessage("You Received Ten Dragon Bones.");
		break;

		case Achievements.PRAYERMASTER:
			addItems(c, new int[][] {{537, 30}});
			c.sendMessage("You Received Thirty Dragon Bones.");
		break;

		case Achievements.CURSED:
			addItems(c, new int[][] {{2435, 5}});
			c.sendMessage("You Received 5 Prayer Potion's & The Ability To Equip The 'Cursed' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.PURITY:
			addItems(c, new int[][] {{2435, 5}});
			c.sendMessage("You Received 5 Prayer Potion's & The Ability To Equip The 'The Original' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.MODERN:
			addItems(c, new int[][] {{1381, 1}});
			c.sendMessage("You Received A Staff Of Air & The Ability To Equip The 'Modern' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.ANCIENT:
			addItems(c, new int[][] {{4675, 1}});
			c.sendMessage("You Received An Ancient Staff & The Ability To Equip The 'Ancient' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.LUNAR:
			c.sendMessage("You Received The Ability To Equip The 'The Wise' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.PRIEST:
			addItems(c, new int[][] {{537, 60}});
			c.sendMessage("You Received Sixty Dragon Bones & The Ability To Equip The 'Priest' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");			
		break;

		case Achievements.HELPME:
			addItems(c, new int[][] {{537, 1}});			
		break;		

		case Achievements.GUITAR:
			addItems(c, new int[][] {{14921, 1}});
			c.sendMessage("You're awarded a guitar!");
			c.sendMessage("You Received The Ability To Equip The 'Guitarist' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");			
		break;	

		case Achievements.DTAB:
			addItems(c, new int[][] {{990, 5}});		
		break;

		case Achievements.TELEHOME:
			addItems(c, new int[][] {{995, 100000}});
			c.sendMessage("You Received 10m.");
		break;

		case Achievements.YAKS:
			c.customPoints += 20;
			c.sendMessage("You Received Twenty Custom Points & The Ability To Equip The 'Yak Hunter' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.CRABS:
			c.customPoints += 10;
			c.sendMessage("You Received 10 Custom Points & The Ability To Equip The 'Rockcrab Hunter' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.VESB:
			c.customPoints += 30;
			c.sendMessage("You Received 30 Custom Points & The Ability To Equip The 'Vesbeast Slayer' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.GNOME:
			c.Wheel += 1000;
			c.getPA().addSkillXP(50000, c.playerAgility);
			c.sendMessage("You Received 1000 Agility Points, 50k Xp & The Ability To Equip The 'Patience' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.SUMM:
			c.getPA().addSkillXP(5000, 21);
			c.sendMessage("You Received 5000 Summoning XP & The Ability To Equip The 'Summoner' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.CUS:
			c.sendMessage("You Received The Ability To Equip The 'Custom' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.VENTURE:
			c.sendMessage("You Received The Ability To Equip The 'Adventurer' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.TDS:
			addItems(c, new int[][] {{14484, 1}});
			c.sendMessage("You Received A Pair Of Dragon Claws & The Ability To Equip The 'Tormentor' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.PKMN:
			addItems(c, new int[][] {{990, 5}});
			c.sendMessage("You Received Five Crystal Keys & The Ability To Equip The 'Pokemon Trainer' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.PKMK:
			addItems(c, new int[][] {{990, 2}});
			c.customPoints += 20;
			c.sendMessage("You Received 2 Crystal Keys, 20 Custom Pts & The Ability To Equip The 'Pokemon Killer' Title.");
			c.sendMessage("Visit The Achievement Titles NPC To Do So.");
		break;

		case Achievements.FISHIES:
			addItems(c, new int[][] {{990, 1}});
			c.sendMessage("You Received 1 Crystal Keys");
		break;

		case Achievements.GOB:
			addItems(c, new int[][] {{990, 2}});
			c.spinsLe += 2;
			c.sendMessage("You Received 2 Crystal Keys.");
		break;
		
		
		default: 
			c.sendMessage("Unfortunately there isn't a reward for this achievement."); 
		break;
		}
	}
	
	
	
	private static void addItems(Client c, int[][] items) {
		int itemAmount = items.length;
		for (int i = 0; i < itemAmount; i++) {
			if (c.getItems().freeSlots() < itemAmount) {
				Server.itemHandler.createGroundItem(c, items[i][0], c.absX,
						c.absY, items[i][1], c.playerId);
			} else {
				c.getItems().addItem(items[i][0], items[i][1]);
			}
		}
	}
}