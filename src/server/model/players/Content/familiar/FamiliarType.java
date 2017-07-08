package server.model.players.Content.familiar;


public enum FamiliarType {
/**
 * Bij enums, alleen de laatste element, plaats je een ; teken.	
 * 
 */
KITTEN(1555, 761),
TICKET_BIRD(10563, 1475),
BANKER_PET(11964, 2574),
KBD_PET(10531, 4010),
GRAARDOR_PET(10532, 4011),
CHAOS_ELEMENTAL_PET(10533, 4012),
KREE_ARRA_PET(10534, 4013),
CORP_PET(10535, 4014),
KRIL_TRUTSAROTH_PET(10536, 4015), //bugged!
ZILYANA_PET(10537, 4016),
IHLAKHIZY(12109, 10155),
MINI_JAD(12477, 4021),
DAGGANOTH_SUPREME(11965, 4017),
AHRIM_PET(12111, 4027),
GUTHAN_PET(12113, 4028),
KARIL_PET(12115, 4029),
TORAG_PET(12117, 4030),
VERAC_PET(12119, 4031);

	private FamiliarType(int itemId, int npcId) {
		this.itemId = itemId;
		this.npcId = npcId;
	}

	public final int itemId;
	public final int npcId;


	public static FamiliarType forId(int itemId) {
		for (FamiliarType familiar : FamiliarType.values()) {
			if (familiar.getItemId() == itemId) {
				return familiar;
			}
		}
		return null;
	}

	public static FamiliarType forNPCId(int npcId) {
		for (FamiliarType familiar : FamiliarType.values()) {
			if (familiar.npcId == npcId)
				return familiar;
		}
		return null;
	}

	public int getItemId() {
		return itemId;
	}
}
