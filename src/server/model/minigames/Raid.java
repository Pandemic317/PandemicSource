package server.model.minigames;

import java.util.HashMap;
import java.util.Map;

import server.CycleEvent;
import server.CycleEventContainer;
import server.CycleEventHandler;
import server.Server;
import server.model.players.Client;
import server.model.players.PlayerHandler;
import server.task.Task;

public class Raid {
	
	/**
	 * The list of players playing custom Raid.
	 */
	private static Map<Client, String> raidPlayers = new HashMap<Client, String>();
	
	/**
	 * The list of NPC's playing custom Raid.
	 */
	private static Map<Integer, String> raidNpcs = new HashMap<Integer, String>();
	
	/**
	 * @return HashMap Value
	 */
	public static String getState(Client player) {
		return raidPlayers.get(player);
	}
	
	/**
	 * @return HashMap Value
	 */
	public static String getNpcState(int npcId) {
		return raidNpcs.get(npcId);
	}

	public static final String PLAYING = "PLAYING";
	public static final String WAITING = "WAITING";
	
	/**
	 * @RAID MINIGAME
	 * Spawns ordered by waves
	 */
	private int[][] SPAWNS = {
			{167}, // wave 1
			{167, 46}, // wave 2
			{167, 46, 167}, // wave 3
			{167, 167, 46, 167, 268}, // wave 4
			{167, 167, 46, 46, 167, 268, 268, 98}}; // wave 5
	
	private int[] NPCS = {167, 46, 167, 268, 98};
	
	/**
	 * @RAID MINIGAME
	 * Spawn coordinates
	 */
	private int[][] COORDS = {
				{2645, 9896},
				{2650, 9897},
				{2649, 9900},
				{2649, 9904},
				{2646, 9905},
				{2644, 9902},
				{2644, 9898},
				{2641, 9896},
				{2639, 9900},
				{2639, 9904},
				{2637, 9899}};
	
	public static int waitingPlayers = 0;

	/**
	 * Add player to the raid's waiting room.
	 * @RAID MINIGAME
	 */
	
	public static void addWaitingPlayer(Client c) {
		c.getPA().movePlayer(2904, 3569, 1);
		c.sendMessage("Welcome to custom raid's waiting room.");
		raidPlayers.put(c, WAITING);
		waitingPlayers++;
	}
	
	
	/**
	 * Starts the raid minigame for the waiting players
	 * Sets the state to @PLAYING.
	 * @RAID MINIGAME
	 */
	
	public static void startGame(Client c) {
		String state = getState(c);
		if(state.equals(WAITING)) {
			raidPlayers.put(c, PLAYING);
			c.getPA().movePlayer(2637, 9911, 0);
			c.sendMessage("Get Ready! Custom raid waves are about to start...");
			c.raidTick = 0;
			waitingPlayers = 0;
			c.playingRaid = true;
		}
		Server.getTaskScheduler().addEvent(new Task(11, false) {
			public void execute() {
				Server.raid.spawnWave((Client) Server.playerHandler.players[c.playerId]);
				this.stop();
			}
		});
	}
	
	
	/**
	 * End's raid minigame.
	 * Resets everything and increase's poins
	 * @RAID MINIGAME
	 */
	
	public void endGame(Client c) {
		c.raidNpcID = 0;
		c.RaidKillsLeft = -1;
		c.RaidKills = -1;
		c.getPA().movePlayer(2904, 3569, 1);
		c.sendMessage("Congratulations you have finished the custom raid minigame!");
		if (raidPlayers.equals(PLAYING)) {
			raidPlayers.put(c, WAITING);
			waitingPlayers++;
		}
		c.playingRaid = false;
		c.RaidPoints++;
		raidPlayers.clear();
	}
	
	public void spawnWave(Client c) {
		if (c != null) {
			if (c.raidNpcID >= SPAWNS.length) {
				c.raidNpcID = 0;
				return;
			}
			if (c.raidNpcID < 0) {
				return;
			}
			int npcAmount = SPAWNS[c.raidNpcID].length;
			for (int j = 0; j < npcAmount; j++) {
				int npc = SPAWNS[c.raidNpcID][j];
				int X = COORDS[j][0];
				int Y = COORDS[j][1];
				int H = c.heightLevel;
				int hp = getHp(npc);
				int max = getMax(npc);
				int atk = getAtk(npc);
				int def = getDef(npc);
				raidNpcs.put(npc, PLAYING);
				Server.npcHandler.spawnNpc(c, npc, X, Y, H, 0, hp, max, atk,
						def, true, false);
			}
			c.RaidKillsLeft = npcAmount;
			c.RaidKills = 0;
		}
	}

	public int getHp(int npc) {
		switch (npc) {
		//kaals je verschillend wil moet je case npc id: return hitpoints;n gwn allemaal zelfde HP, rond de 1
		}
		return 45000;
	}
	

	public int getMax(int npc) {
		switch (npc) {
		case 46:
			return 10;
		case 167:
			return 15;
		case 268:
			return 13;
		case 98:
			return 7;
		}
		return 5;
	}

	public int getAtk(int npc) {
		switch (npc) {
		case 46:
			return 90;
		case 167:
			return 95;
		case 268:
			return 83;
		case 98:
			return 75;
		}
		return 100;
	}

	public int getDef(int npc) {
		switch (npc) {
		case 46:
			return 110;
		case 167:
			return 95;
		case 268:
			return 43;
		case 98:
			return 7;
		}
		return 100;
	}

}