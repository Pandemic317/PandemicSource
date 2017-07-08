package server.model.players.packets;

import server.CycleEvent;
import server.CycleEventContainer;
import server.CycleEventHandler;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Player;
import server.model.players.PlayerHandler;

public class GambleResponse implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int clientId = c.getInStream().readSignedWordBigEndian();
		if (clientId < 0)
			return;
		Player o = PlayerHandler.players[clientId];
		if (o == null)
			return;
		if (!c.inGambleZone() || !o.inGambleZone())
			return;
		if (c.goodDistance(o.getX(), o.getY(), c.getX(), o.getY(), 1)
				&& o.goodDistance(c.getX(), c.getY(), o.getX(), o.getY(), 1)) {
			if (packetType == 142)
				c.getGambling().answerRequest((Client) o);
			else if (packetType == 143)
				c.getGambling().openOptions((Client) o);
			return;
		}
		CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {

			@Override
			public void execute(CycleEventContainer container) {
				if (c.goodDistance(o.getX(), o.getY(), c.getX(), o.getY(), 1)
						&& o.goodDistance(c.getX(), c.getY(), o.getX(), o.getY(), 1)) {
					if (packetType == 142)
						c.getGambling().answerRequest((Client) o);
					else if (packetType == 143)
						c.getGambling().openOptions((Client) o);
					container.stop();
				}
			}

			@Override
			public void stop() {
				// TODO Auto-generated method stub

			}
		}, 1);
	}

}
