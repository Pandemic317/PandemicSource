package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
@SuppressWarnings("all")

/**
 * Magic on items
 **/
public class MagicOnItems implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int slot = c.getInStream().readSignedWord();
		int itemId = c.getInStream().readSignedWordA();
		int junk = c.getInStream().readSignedWord();
		int spellId = c.getInStream().readSignedWordA();
		
		c.usingMagic = true;
		if(c.playerItems[slot]-1 != itemId) { // cheat client
			return;
		}
		if(!c.getItems().playerHasItem(itemId, 1, slot))
            return;
		c.getPA().magicOnItems(slot, itemId, spellId);
		c.usingMagic = false;

	}

}
