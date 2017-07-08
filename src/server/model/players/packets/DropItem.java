package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.minigames.*;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Content.familiar.FamiliarType;

/**
 * Drop Item
 **/
@SuppressWarnings("all")
public class DropItem implements PacketType {



public void dat(Client c) {
int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
int slot = c.getInStream().readUnsignedWordA();
c.alchDelay = System.currentTimeMillis();
 c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
 c.SaveGame();
	}

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();
		  if(!c.getItems().playerHasItem(itemId)) {
            return;
        }
			FamiliarType familiar = FamiliarType.forId(itemId);
			
			if (familiar != null) {
				if (c.hasFollower > 0) {
					c.sendMessage("You already have a familiar following you.");
					return;
				}
				c.getFamiliar().SpawnPet(c, familiar.npcId, c.absX, c.absY-1, c.heightLevel, 0, 100, 5, false, 59, 50);
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				return;
			}
		  if (c.inAccountPin) {
				c.sendMessage("You must enter your pin first");
				c.isWalking = false;
				c.getBankPin().openPin();
				return;
			}
		if(c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You cannot drop items in the trade screen.");
			return;
		}
						if(c.InDung()) {
			c.sendMessage("You can't drop inside the dungeoneering floors!");
			return;
		}
				if(c.tradeStatus == 1) {
			c.getTradeAndDuel().declineTrade();
			c.sendMessage("AntiDupe: You tried to drop an item, duel closed.");
                             		return;
                        	}
		if(c.duelStatus == 1) {
		Client o = (Client) Server.playerHandler.players[c.duelingWith];
			c.getTradeAndDuel().declineDuel();
			o.getTradeAndDuel().declineDuel();
			o.sendMessage("Duel closed - other player dropped an item.");
			c.sendMessage("AntiDupe: You tried to drop an item, duel closed.");
			return;
		}
				if (c.isShopping) {
			c.sendMessage("You cannot drop items while shopping! - Walk then try.");
			return;
		}
				if(c.InDung == false && c.IsIDung == 1) {
		c.getPA().resetDung();
		}
						if (c.underAttackBy > 0) {
						c.sendMessage("AntiDupe: Cannot drop item, errorcode: You can't drop items in combat.");
						return;
				}
		if(c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
if(!c.getItems().playerHasItem(itemId,1,slot)) {
			c.sendMessage("ANTI DUPE: STOPPED!");
			return;
			}
		}
                if(c.playerRights == 2) {
                    c.getItems().deleteItem(itemId, slot, 1);
					c.sendMessage("You cannot drop items.");
                    return;
                }
				if(c.playerRights == 5) {
                    c.getItems().deleteItem(itemId, slot, 1);
					c.sendMessage("The item dissapears as you drop it");
                    return;
                }
		
		c.getPA().closeAllWindows();
		
		boolean droppable = true;
                for (int i : Config.CAT_ITEMS) {
				if (i == itemId) {
				if(c.hasNpc == true) {
                                        c.sendMessage("Sorry, You Already Have A Npc Playing Dismiss to use.");
					droppable = false;
					break;
				}
			}
		}
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
							c.sendMessage("<shad=15695415>[server]You can't drop a unDroppable item!");
				droppable = false;
				break;
			}
		}
		for (int i : Config.ITEM_SELLABLE) {
			if (i == itemId) {
				droppable = false;
				c.sendMessage("<shad=15695415>[server]You can't drop a unsellable item!");
				break;
			}
		}
		
				for (int i : Config.ITEM_SELLABLE) {
			if (i == itemId) {
				droppable = false;
				c.sendMessage("<shad=15695415>Failed to drop, errorcode: Untradeable item!");
				break;
			}
		}
if(c.inRFD()){
	c.sendMessage("<shad=15695415>You may not drop in the RFD Minigame.");
	return;	
}
		if(c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
			if(droppable) {
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 10000) {
						c.sendMessage("You may not drop items worth more than 10,000gp while in combat.");
						return;
					}
				}

				//Server.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
				//c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				if (c.playerRights == 3) {
					Server.itemHandler.createGroundItem(c, itemId, c.getX(), c.getY(), c.playerItemsN[slot], c.getId());
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				} else if (c.playerRights != 3) {
                                c.droppedItem = itemId;
                                c.getPA().destroyInterface(itemId);
			}
			} else {
				c.sendMessage("This item cannot be dropped.");
			}
		}
			if(c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
if(!c.getItems().playerHasItem(itemId,1,slot)) {
			c.sendMessage("Stop cheating!");
			return;
			}
		}
	}
}