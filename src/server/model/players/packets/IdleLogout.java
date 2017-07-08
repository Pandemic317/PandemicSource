package server.model.players.packets;


import server.model.players.Client;
import server.util.Misc;
import server.model.players.PacketType;

@SuppressWarnings("all")
public class IdleLogout implements PacketType {

	int[] emotes = {2756, 2761, 2763, 2764};
	
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		//if (c.membership) {
			if (c.underAttackBy > 0 || c.underAttackBy2 > 0)
            return;
			c.gfx100(277);
			c.GiantMoleKilled += 1;
			if (c.GiantMoleKilled == 3) 
			c.logout();
			c.GiantMoleKilled = 0;
			return;
			
		//}
	}
}