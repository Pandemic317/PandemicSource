package server.model.players;

import server.CycleEvent;
import server.CycleEventContainer;
import server.CycleEventHandler;
import server.model.players.Client;
import server.model.players.PlayerSave;

public class AchievementManager {

public static final int MAX_ACHIEVEMENTS = 100;

private static final int[] REQUIRED_AMOUNT = { 100, 1, 1, 1, 10, 1, 500, 10, 100, 1, 50, 1, 1, 1, 1, 1, 300, 1, 1, 1, 1, 100, 100, 200, 30, 50, 1, 1, 10, 1000, 1, 50, 15, 200, 150, 500, 1, 250, 1, 1, 100, 90, 80, 70, 60, 50, 1}; //40

private static final int[] ACHIEVEMENT_POINTS = { 10, 25, 5, 5, 10, 20, 100, 50, 50, 5, 50, 5, 5, 5, 5, 5, 30, 5, 15, 65, 10, 30, 30, 70, 200, 30, 25, 10, 10, 150, 50, 30, 70, 70, 70, 70, 70, 65, 20, 20, 50, 60, 70, 80, 90, 100, 200}; //40

private static final String[] ACHIEVEMENT_NAME = { "Hungry?", "PokeMaster", "Not Afraid", "Banking", "Catch Em All", "Treasure", "Mad Treasure", "Dedication", "Veteran", "Prayer", "PrayerMaster", "Cursed", "Purity", "Modern", "Ancient", "Lunar", "Priest", "Help Me", "Guitar", "Donor Tab", "Home Tele", "Tele Home", "Yak Hunter", "Rockcrab Hunter", "Vesbeast Slayer", "Patience", "Summon Me", "Splashing Out", "Pika", "Pika Pika", "Venturing Out", "Tormentor", "Pokemon Trainer", "Pokemon Killer", "Woodcutter", "Fire Starter", "Nex's Boss", "Fishy Feelin'", "Kill Wrecker", "Ah, That Arena", "Blue torva", "Flame torva", "24K Torva", "Burst Torva", "Predator Torva", "Cyrex Torva", "Chaotic whip"}; //40

private static final String[] ACHIEVEMENT = { "Welcome To The Teleport Tab!", "Home", "1b Tickets (Floor 1)", "1b Tickets (Floor 2)", "Ticket Boss", "Claim Dicebag (5000b)", "Donator Zone (Regular+)", "Super Donator Zone (Super+)", "Corporeal Beast", "Mini Vorago", "Torvazone", "Pokémons", "135 (135 HP level 135)", "Hulk Torva Boss (500 kills)", "Piyan", "WhiteKnights (Super+1500 kills)", "Ihlakhizan", "1 Million HP Boss", "Shadow (Donor + 7500 kills)", "Roc Island (3500 kills)", "Geodude's cave", "Leeches (Sponsor+)", "Skilling Zone", "Torva Zone 2 (500 kills)", "Abyssal Demons (Wildy)", "Iron Zone (Iron Gambler)"};//40
/*private static final String[] ACHIEVEMENTS = { "Home Only"};//40*/
			
	public static void increase(final Client c, int achievement) {
		c.achievement[achievement]++;
		
		if (c.achievement[achievement] == REQUIRED_AMOUNT[achievement]) {
			c.achieved[achievement] = true;
			c.sendMessage("<col=176>Congratulations! You've completed the achievement <col=129>" + ACHIEVEMENT_NAME[achievement] + "!");
			c.achievementPoints += ACHIEVEMENT_POINTS[achievement];
			c.sendMessage("<col=176>You recieve " + ACHIEVEMENT_POINTS[achievement]	+ " points! <col=146>You now have the total of " + c.achievementPoints + " achievement points.");
			c.getPA().sendFrame126("You completed the achievement\\n" + ACHIEVEMENT_NAME[achievement] + "!", 25136);
			c.hasAchieved = true;
			//c.getPA().showInterface(25133);
			
			AchievementExtra.addExtra(c, achievement);
			PlayerSave.saveGame(c);
			CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
				@Override
				public void execute(CycleEventContainer container) {
					c.hasAchieved = false;
					container.stop();
				}
				@Override
				public void stop() {

				}
			}, 5);
		}
	}

	/*public static void writeInterfaces(Client c) {
		for (int i = 0; i < ACHIEVEMENTS.length; i++) {
			c.getPA().sendFrame126("" + (c.achieved[i] ? "@gre@" : "@gre@") + "" + ACHIEVEMENTS[i] + "", 39295 + i);
		}
		c.setSidebarInterface(2, 41350);
	}*/
	public static void writeInterface(Client c) {
		for (int i = 0; i < ACHIEVEMENT.length; i++) {
			c.getPA().sendFrame126("" + (c.achieved[i] ? "@gre@" : "@gre@") + "" + ACHIEVEMENT[i] + "", 39295 + i);
		}
		c.setSidebarInterface(1, 39265);
	}
}