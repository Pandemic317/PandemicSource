package server.model.players.Content.familiar;

public enum FamiliarAttractData {
//follower npc id, item id
TICKET_BIRD(1475, 5021),
CASKET_BANKER(2574, 3529);

	private FamiliarAttractData(int npcId, int itemId ) {
		this.npcId = npcId;
		this.itemId = itemId;
	}

	
	public final int npcId;
	public final int itemId;


	public static FamiliarAttractData forId(int itemId) {
		for (FamiliarAttractData familiar : FamiliarAttractData.values()) {
			if (familiar.getItemId() == itemId) {
				return familiar;
			}
		}
		return null;
	}

	public static FamiliarAttractData forNPCId(int npcId) {
		for (FamiliarAttractData familiar : FamiliarAttractData.values()) {
			if (familiar.npcId == npcId)
				return familiar;
		}
		return null;
	}
	
	public int getNpcId() {
		return npcId;
	}

	public int getItemId() {
		return itemId;
	}
}
