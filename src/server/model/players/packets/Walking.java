package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Content.Gambler.GambleInterface;
import server.model.players.skills.SkillHandler;
import server.model.players.skills.Woodcutting;

/**
 * Walking packet
 **/
@SuppressWarnings("all")
public class Walking implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		if (c.inAccountPin) {
			c.sendMessage("You must enter your pin first");
			c.isWalking = false;
			c.getBankPin().openPin();
			return;
		}
		if (c.playerIsWoodcutting) {
			Woodcutting.resetWoodcutting(c);
		}
		if(c.getPlayerAction().canWalk = false){
			c.isWalking = false;
			return;
			
		}
		
		if(c.GambleStatus == 1){
			return;
		}
		if (GambleInterface.gambling(c)){
			GambleInterface.closeGamble(c);
		}
		
		c.walkingToItem = false;
		c.clickNpcType = 0;
		c.clickObjectType = 0;
		if (packetType == 248 || packetType == 164) {
			c.faceUpdate(0);
			if (c.SSPLIT == false) {
				c.npcIndex = 0;
				c.playerIndex = 0;
			} else {
			}
			if (c.followId > 0 || c.followId2 > 0)
				c.getPA().resetFollow();
		}
		if (c.spinning == true) {
			c.isWalking = true;
			c.spinning = false;
		}
		c.isWalking = true;
		// c.getMining().resetMining();
		c.fishing = false;
		c.usingAltar = false;
		c.isCooking = false;
		c.playerIsFiremaking = false;
		if (!c.isBanking && !c.inTrade) {
			c.getPA().removeAllWindows();
			c.startAnimation(65535);
		}
		if (c.isSpinningDaSof == true) {
			c.sendMessage("You can't walk at the moment. Please open up SoF again, or relog.");
			return;
		}
		if (c.InDung == false && c.IsIDung == 1) {
			c.getPA().resetDung();
		}
		if (!c.InDung() && !c.inDungBossRoom()) {
			for (int i : Config.DUNG_ARM) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						if (c.getItems().playerHasItem(i, 1)) {
							Client c2 = (Client) Server.playerHandler.players[j];
							c2.sendMessage("<shad=132833>" + c.playerName + " Has Dungeoneering items! Jailed.");
							c.sendMessage("Hand The Items To a Staff Member or WHOLE ACCOUNT RESET.");
							c.teleportToX = 3102;
							c.teleportToY = 9516;
							c.Jail = true;
							return;
						}
					}
				}
			}
		}
		if (c.duelRule[1] && c.duelStatus == 5) {
			if (Server.playerHandler.players[c.duelingWith] != null) {
				if (!c.goodDistance(c.getX(), c.getY(), Server.playerHandler.players[c.duelingWith].getX(),
						Server.playerHandler.players[c.duelingWith].getY(), 1) || c.attackTimer == 0) {
					c.sendMessage("Walking has been disabled in this duel!");
				}
			}
			c.playerIndex = 0;
			return;
		}

		if (c.inTrade) {
			c.sendMessage("Please use the Decline option.");
			return;
		}

		if (c.selectStarter) {
			c.getDH().sendDialogues(9999, -1);
			c.sendMessage("You can not walk while choosing Starter..");
			return;
		}
		if (c.selectStarterr) {
			c.getDH().sendDialogues(9999, -1);
			c.sendMessage("You can not walk while choosing Starter..");
			return;
		}

		if (c.isChoosingDung) {
			c.sendMessage(".");
			c.isChoosingDung = false;
			return;
		}

		if (c.isChoosing) {
			c.sendMessage("AntiDupe: You can't walk while choosing vote reward. Close this window first");
			return;
		}

		if (c.openDuel) {
			Client o = (Client) Server.playerHandler.players[c.duelingWith];
			c.openDuel = false;
			if (o != null)
				o.openDuel = false;
			c.getPA().closeAllWindows();
			if (o != null)
				o.getPA().closeAllWindows();
			c.sendMessage("Anti-Dupe, All Windows Closed.");
			if (o != null)
				o.sendMessage("Anti-Dupe, All Windows Closed.");
		}
		if (c.stopPlayerSkill) {
			SkillHandler.resetPlayerSkillVariables(c);
			c.stopPlayerSkill = false;
		}
		if (c.inTrade) {
			c.getTradeAndDuel().declineTrade(true);
		}
		if (c.freezeTimer > 0) {
			if (Server.playerHandler.players[c.playerIndex] != null) {
				if (c.goodDistance(c.getX(), c.getY(), Server.playerHandler.players[c.playerIndex].getX(),
						Server.playerHandler.players[c.playerIndex].getY(), 1) && packetType != 98) {
					c.playerIndex = 0;
					return;
				}
			}
			if (packetType != 98) {
				c.sendMessage("A magical force stops you from moving.");
				c.playerIndex = 0;
			}
			return;
		}

		if (System.currentTimeMillis() - c.lastSpear < 4000) {
			c.sendMessage("You have been stunned.");
			c.playerIndex = 0;
			return;
		}

		if (packetType == 98) {
			c.mageAllowed = true;
			c.fishing = false;
			c.isCooking = false;
		}
		if (c.sit == true) {
			c.sendMessage("::unsit before you can start walking again!");
			return;
		}
		if (c.duelStatus >= 1 && c.duelStatus <= 4) {
			c.getTradeAndDuel().declineDuel();
			return;
		}
		if ((c.duelStatus >= 1 && c.duelStatus <= 4) || c.duelStatus == 6) {
			if (c.duelStatus == 6) {
				c.getTradeAndDuel().claimStakedItems();
			}
			return;
		}

		if (c.hasAchieved == true) {
			c.getPA().closeAllWindows();
		}

		if (c.respawnTimer > 3) {
			return;
		}
		if (System.currentTimeMillis() - c.lastEmote <= 7000) {
			return;
		}
		if (c.inTrade) {
			c.sendMessage("Please use the Decline option.");
			return;
		}
		if (c.isBanking) {
			c.getPA().closeAllWindows();
		}
		if (c.isShopping == true) {
			c.isShopping = false;
		}
		if (packetType == 248) {
			packetSize -= 14;
		}
		c.newWalkCmdSteps = (packetSize - 5) / 2;
		if (++c.newWalkCmdSteps > c.walkingQueueSize) {
			c.newWalkCmdSteps = 0;
			return;
		}

		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;

		int firstStepX = c.getInStream().readSignedWordBigEndianA() - c.getMapRegionX() * 8;
		for (int i = 1; i < c.newWalkCmdSteps; i++) {
			c.getNewWalkCmdX()[i] = c.getInStream().readSignedByte();
			c.getNewWalkCmdY()[i] = c.getInStream().readSignedByte();
		}

		int firstStepY = c.getInStream().readSignedWordBigEndian() - c.getMapRegionY() * 8;
		c.setNewWalkCmdIsRunning(c.getInStream().readSignedByteC() == 1);
		for (int i1 = 0; i1 < c.newWalkCmdSteps; i1++) {
			c.getNewWalkCmdX()[i1] += firstStepX;
			c.getNewWalkCmdY()[i1] += firstStepY;
		}
	}

}