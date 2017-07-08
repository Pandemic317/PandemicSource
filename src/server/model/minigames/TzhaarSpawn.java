package server.model.minigames;

import server.Server;
import server.model.players.Client;
import server.model.players.PlayerHandler;

/**
 * @author No1killme Xd
 */

public class TzhaarSpawn {
	
	public static int TzhaarCount = 0;
	public static int MoneyId = 5021;// the 1 billion ticket id
	public static int MoneyToEnter = 300;// the amount of 1 billion ticket to enter and to delete it
	public static int TzhaarId = 69;//The npc that spawns id

	public static int[][] npcCoords = {//these are the npc coords
			{3202, 3428},
			{3204, 3430},
			{3206, 3427},
			{3208, 3424},
			{3212, 3425},
			{3210, 3428},
			{3212, 3433},
			{3208, 3435},
			{3205, 3439},
			{3216, 3435},
			{3216, 3432},
			{3219, 3431},
			{3222, 3434},
			{3225, 3429},
			{3221, 3430},
			{3222, 3426},
			{3219, 3422},
			{3217, 3425},
			{3213, 3422},
			{3211, 3426},
			{3211, 3419}//15
			};
	
	public static void SpawnNpcs(Client c) {
		//if (MoneyId < 300) {
		//	c.sendMessage("<col=255>You need at least 500 1b tickets to get access to this feature!");
		//	return;
		//}
		//if (c.getItems().playerHasItem(5021, 500)) {
			c.npcId2 = 1665;
			for (int j = 0; j < PlayerHandler.players.length; j++) {
    			if (PlayerHandler.players[j] != null) {
    				Client c2 = (Client)PlayerHandler.players[j];
    				c2.sendMessage("<shad=255125000><col=800000>The Lizard Men arrived at Varrock! Kill them for great loot!</col>");
			}
			}
			TzhaarCount = 15;
			for (int i = 0; i < npcCoords.length; i++) {
				Server.npcHandler.spawnNpc2(TzhaarId, npcCoords[i][0], npcCoords[i][1], 0, 1, 2900, 120, 9, 9);
			}
		}
	}
	
//}