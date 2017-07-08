package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;

public class AuctionSearch implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		long item = c.getInStream().readQWord();
		String itemName = Misc.longToString(item);
		c.getAuctions().searchForItem(itemName);
	}

}
