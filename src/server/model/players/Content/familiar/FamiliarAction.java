package server.model.players.Content.familiar;

import server.Server;
import server.model.npcs.NPC;
import server.model.npcs.NPCHandler;
import server.model.players.Client;
import server.model.players.Player;

/**
 * Summoning class 
 * @author Ophion @RUNESERVER
 *
 */

public class FamiliarAction {

	/**
	 * The player who owns the familiar
	 */
	private Player player;

	/**
	 * The current familiar the player has put down
	 */
	private Familiar familiar;
	

	/**
	 * @param player The player who is using familiar
	 */
	public FamiliarAction(Player player) {
		this.player = player;
	}
	
	/**
	 * Spawns a Familiar
	 * @param familiar The familiar you want to be spawned
	 */
	private Client c;
	public void SpawnPet(Client c, int npcType, int x, int y, int heightLevel,
			int WalkingType, int HP, int maxHit, boolean attackPlayer,
			int attack, int defence) {
		
		c.sendMessage("Your familliar starts to follow you around...");

		int slot = -1;
		for (int i = 1; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			return;
		}
			NPCHandler.npcs[slot] = new NPC(slot, npcType);
			NPCHandler.npcs[slot].absX = x;
			NPCHandler.npcs[slot].absY = y;
			NPCHandler.npcs[slot].makeX = x;
			NPCHandler.npcs[slot].makeY = y;
			NPCHandler.npcs[slot].heightLevel = heightLevel;
			NPCHandler.npcs[slot].walkingType = WalkingType;
			NPCHandler.npcs[slot].HP = HP;
			NPCHandler.npcs[slot].MaxHP = HP;
			NPCHandler.npcs[slot].maxHit = maxHit;
			NPCHandler.npcs[slot].attack = attack;
			NPCHandler.npcs[slot].defence = defence;
			NPCHandler.npcs[slot].spawnedBy = c.getId();
			NPCHandler.npcs[slot].followPlayer = c.getId();
			NPCHandler.npcs[slot].summon = true;
			NPCHandler.npcs[slot].npcslot = slot;
		c.hasFollower = npcType;
		c.summon = true;
		c.summoningnpcid = slot;

	}
	

	/**
	 * Resets the familiar
	 */
	public void reset() {
		
		/** Sends message to player */
		c.sendMessage("Your familiar runs away!");
		
		/** Sets the familiar to null */
		this.familiar = null;
		
	}
	
	/**
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @return cat
	 */
	public Familiar getFamiliar() {
		return familiar;
	}
	
	public Familiar setFamiliar(Familiar familiar) {
		return this.familiar = familiar;
	}
}
