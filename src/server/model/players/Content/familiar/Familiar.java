package server.model.players.Content.familiar;

import server.model.npcs.NPC;

public class Familiar {
	
	/**
	 * The pet as a mob
	 */
	private NPC npc;
	
	/**
	 * The type the pet is
	 */
	private FamiliarType type;
	
	/**
	 * The item of pet
	 */
	private int item;
	
	/**
	 * @param npc The pet npc
	 * @param type The pet type
	 * @param item The pet as an item
	 */
	public Familiar(NPC npc, int item) {
		this.npc = npc;
		this.item = item;
	}
	
	/**
	 * @return npc
	 */
	public NPC getNpc() {
		return npc;
	}
	
	/**
	 * @return type
	 */
	public FamiliarType getType() {
		return type;
	}
	
	/**
	 * @param item
	 */
	public int getItem() {
		return item;
	}

}
