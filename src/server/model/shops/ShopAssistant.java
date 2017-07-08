package server.model.shops;

import server.Config;
import server.CustomItem;
import server.Server;
import server.model.items.Item;
import server.model.players.Client;
@SuppressWarnings("all")
public class ShopAssistant {

	private Client c;

	public ShopAssistant(Client client) {
		this.c = client;
	}

	/**
	 * Shops
	 **/
	public static final int[] ironGambleShops = {121,122,123,124,2,72,77,17};
	public void openShop(int ShopID) {
		if (!Config.SHOPS_ENABLED) {
			c.sendMessage("Shops are currently disabled!");
			return;
		}
		if (c.ironGambler >= 1) {
				if (ShopID == 121 ||ShopID == 54 ||ShopID == 122 ||ShopID == 123 ||ShopID == 2 ||ShopID == 72 ||ShopID == 77 ||ShopID == 17) {
					c.getItems().resetItems(3823);
					resetShop(ShopID);
					c.isShopping = true;
					c.myShopId = ShopID;
					c.getPA().sendFrame248(3824, 3822);
					c.getPA().sendFrame126(Server.shopHandler.ShopName[ShopID], 3901);
					return;
				} else 
			c.sendMessage("<col=800000000>You are not allowed to use this shop as an Iron Beast.");
			return;
		}
		if (c.ironGambler == 0 && ShopID == 121) {c.sendMessage("You are not allowed to use the Iron Beast mode shop.");return;}
		if (c.ironGambler == 0 && ShopID == 122) {c.sendMessage("You are not allowed to use the Iron Beast mode shop.");return;}
		if (c.ironGambler == 0 && ShopID == 123) {c.sendMessage("You are not allowed to use the Iron Beast mode shop.");return;}
		
		c.getItems().resetItems(3823);
		resetShop(ShopID);
		c.isShopping = true;
		c.myShopId = ShopID;
		c.getPA().sendFrame248(3824, 3822);
		c.getPA().sendFrame126(Server.shopHandler.ShopName[ShopID], 3901);
	}

	public boolean shopSellsItem(int itemID) {
		for (int i = 0; i < Server.shopHandler.ShopItems.length; i++) {
			if (itemID == (Server.shopHandler.ShopItems[c.myShopId][i] - 1)) {
				return true;
			}
		}
		return false;
	}

	public void updatePlayerShop() {
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {
			if (Server.playerHandler.players[i] != null) {
				if (Server.playerHandler.players[i].isShopping == true
						&& Server.playerHandler.players[i].myShopId == c.myShopId
						&& i != c.playerId) {
					Server.playerHandler.players[i].updateShop = true;
				}
			}
		}
	}

	public void updateshop(int i) {
		resetShop(i);
	}

	public void resetShop(int ShopID) {
		synchronized (c) {
			int TotalItems = 0;
			for (int i = 0; i < Server.shopHandler.MaxShopItems; i++) {
				// if (Server.shopHandler.ShopItems[ShopID][i] > 0) {
				// TotalItems++;
				if (Server.shopHandler.ShopItems[ShopID][i] > 0
						&& Server.shopHandler.ShopItemsN[ShopID][i] > 0
						|| i <= Server.shopHandler.ShopItemsStandard[ShopID]) { // Server.shopHandler.ShopItemsStandard
					TotalItems++;
				}
			}
			if (TotalItems > Server.shopHandler.MaxShopItems) {
				TotalItems = Server.shopHandler.MaxShopItems;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
			int TotalCount = 0;
			for (int i = 0; i < Server.shopHandler.ShopItems.length; i++) {
				if (Server.shopHandler.ShopItems[ShopID][i] > 0
						|| i <= Server.shopHandler.ShopItemsStandard[ShopID]) {
					if (Server.shopHandler.ShopItemsN[ShopID][i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(
								Server.shopHandler.ShopItemsN[ShopID][i]);
					} else {
						c.getOutStream().writeByte(
								Server.shopHandler.ShopItemsN[ShopID][i]);
					}
					if (Server.shopHandler.ShopItems[ShopID][i] > Config.ITEM_LIMIT
							|| Server.shopHandler.ShopItems[ShopID][i] < 0) {
						Server.shopHandler.ShopItems[ShopID][i] = Config.ITEM_LIMIT;
					}
					c.getOutStream().writeWordBigEndianA(
							Server.shopHandler.ShopItems[ShopID][i]);
					TotalCount++;
				}
				if (TotalCount > TotalItems) {
					break;
				}
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public double getItemShopValue(int ItemID, int Type, int fromSlot) {
		if (c.myShopId == 7390) {
			return c.myShopClient.playerShopP[fromSlot];
		}
		double ShopValue = 1;
		double Overstock = 0;
		double TotPrice = 0;
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					ShopValue = Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}

		TotPrice = ShopValue;

		if (Server.shopHandler.ShopBModifier[c.myShopId] == 1) {
			TotPrice *= 1;
			TotPrice *= 1;
			if (Type == 1) {
				TotPrice *= 1;
			}
		} else if (Type == 1) {
			TotPrice *= 1;
		}
		return TotPrice;
	}

	public void openPlayerShop(Client o) {
		if (o == null || o.properLogout)
			return;
		c.getItems().resetItems(3823);
		resetShop(o);
		c.myShopClient = o;
		c.myShopId = 7390;
		c.isShopping = true;
		c.getPA().sendFrame248(3824, 3822);
		c.getPA().sendFrame126(o.playerName + "'s Shop!", 3901);
	}

	public int[] fixArray(int[] array) {
		int arrayPos = 0;
		int[] newArray = new int[array.length];
		for (int x = 0; x < array.length; x++) {
			if (array[x] != 0) {
				newArray[arrayPos] = array[x];
				arrayPos++;
			}
		}
		return newArray;
	}

	public void fixShop(Client o) {
		o.playerShop = fixArray(o.playerShop);
		o.playerShopN = fixArray(o.playerShopN);
		o.playerShopP = fixArray(o.playerShopP);
	}

	public void resetShop(Client o) {
		synchronized (c) {
			fixShop(o);
			for (int x = 0; x < 10; x++) {
				if (o.playerShopN[x] <= 0) {
					o.playerShop[x] = 0;
				}
			}
			int TotalItems = 0;
			for (int i = 0; i < 10; i++) {
				if (o.playerShop[i] > 0) {
					TotalItems++;
				}
			}
			if (TotalItems > 10) {
				TotalItems = 10;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
			int TotalCount = 0;
			for (int i = 0; i < o.playerShop.length; i++) {
				if (o.playerShop[i] > 0) {
					if (o.playerShopN[i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(o.playerShopN[i]);
					} else {
						c.getOutStream().writeByte(o.playerShopN[i]);
					}
					c.getOutStream().writeWordBigEndianA((o.playerShop[i] + 1));
					TotalCount++;
				}
				if (TotalCount > TotalItems) {
					break;
				}
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public int getItemShopValue(int itemId) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == itemId) {
					return (int) Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}
		return 0;
	}

	/**
	 * buy item from shop (Shop Price)
	 **/

	public void buyFromShopPrice(int removeId, int removeSlot) {
		int ShopValue = (int) Math.floor(getItemShopValue(removeId, 0,
				removeSlot));
		ShopValue *= 1;
		String ShopAdd = "";
		if (c.myShopId == 7390 && c.myShopClient != null
				&& !c.myShopClient.playerName.equals(c.playerName)) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs "
					+ c.myShopClient.playerShopP[removeSlot] + " coins.");
			return;
		} else if (c.myShopId == 7390 && c.myShopClient != null
				&& c.myShopClient.playerName.equals(c.playerName)) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + c.playerShopP[removeSlot]
					+ " coins.");
			return;
		}
		if (c.myShopId == 54) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Vote Points");
			return;
		}
		if (c.myShopId == 20) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Pandemic Points!");
			return;
		}
		if (c.myShopId == 18) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Pandemic Points!");
			return;
		}
		if (c.myShopId == 84) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Dungeoneering Tokens.");
			return;
		}
		if (c.myShopId == 97) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Custom Points.");
			return;
		}
		if (c.myShopId == 85) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " PK Minigame Points.");
			return;
		}
		if (c.myShopId == 29) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " 2b Tickets.");
			return;
		}
			if (c.myShopId == 120) {
				c.sendMessage(c.getItems().getItemName(removeId)
						+ ": currently costs " + getSpecialItemValue(removeId)
						+ " 1b Tokens.");
			return;
		}
		if (c.myShopId == 96) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " MageBank Points.");
			return;
		}
		if (c.myShopId == 72) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Donator Points.");
			return;
		}
		if (c.myShopId == 122) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Donator Points.");
			return;
		}
		if (c.myShopId == 116) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " NPC Kills.");
			return;
		}
		if (c.myShopId == 17) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Skill Points.");
			return;
		}
		if (c.myShopId == 119) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Mummy Points.");
			return;
		}
		if (c.myShopId == 77) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs <col=800000000>" + getSpecialItemValue(removeId)
					+ "</col> Barrows Minigame Points.");
			return;
		}
		if (c.myShopId == 95) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Achievement Points.");
			return;
		}
		if (c.myShopId == 16) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Agility Points.");
			return;
		}
		/*
		 * if (c.myShopId == 117) {
		 * c.sendMessage(c.getItems().getItemName(removeId)+": currently costs "
		 * + get1BTicketShopValue(removeId) + " 1 Bill Tickets."); return; }
		 */
		if (c.myShopId == 117) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSuperSpecialItemSellValue(removeId)
					+ " 1B tickets " + ShopAdd);
			return;
		}
		if (c.myShopId == 121) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getIronManValue(removeId)
					+ " 1B tickets " + ShopAdd);
			return;
		}
		if (c.myShopId == 118) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getTicketSell(removeId)
					+ " 1 Bill Tickets.");
			return;
		}
		if (c.myShopId == 43) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getSpecialItemValue(removeId)
					+ " Dominion Tower Points.");
			return;
		}
		if (c.myShopId == 4 || c.myShopId == 5) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getPvpValue(removeId)
					+ " PvP Points.");
			return;
		}
		if (c.myShopId == 100) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getBarb(removeId)
					+ " Raid Points.");
			return;
		}
		if (c.myShopId == 123) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": currently costs " + getBarb(removeId)
					+ " Raid Points.");
			return;
		}

		if (c.myShopId == 15) {
			c.sendMessage("This item current costs "
					+ c.getItems().getUntradePrice(removeId) + " coins.");
			return;
		}
		if (ShopValue >= 1000 && ShopValue < 1000000) {
			ShopAdd = " (" + (ShopValue / 1000) + "K)";
		} else if (ShopValue >= 1000000) {
			ShopAdd = " (" + (ShopValue / 1000000) + " million)";
		}
		c.sendMessage(c.getItems().getItemName(removeId) + ": currently costs "
				+ ShopValue + " coins" + ShopAdd);
		}

	public int getTicketSell(int id) {
		switch (id) {
		case 14921:
			return 50;
		case 15098:
			return 4500;
		case 4762:
		case 4764:
			return 50;
			case 14445:
			return 100;

			case 11307:
			return 200;

			case 11317:
			return 200;

			case 11318:
			return 200;

			case 11319:
			return 200;

			case 11312:
			return 200;

			case 11615:
			return 150;

			case 11616:
			return 150;

			case 11614:
			return 150;

			case 10708:
			return 300;

			case 19266:
			return 300;

			case 19263:
			return 300;

			case 10705:
			return 200;

			case 19203:
			return 200;

			case 19200:
			return 200;

			case 15038:
			return 200;

			case 15037:
			return 200;

			case 19943:
			return 25;

			case 19112:
			return 25;

			case 19113:
			return 25;

			case 19114:
			return 25;

			case 19036:
			return 25;

			case 19037:
			return 25;

			case 19038:
			return 25;

			case 19041:
			return 25;

			case 19040:
			return 25;

			case 20155:
			return 25;

			case 19827:
			return 200;

			case 17865:
			return 25;

			case 4767:
			return 25;

			case 3807:
			return 900;

			case 20102:
			return 10;

			case 20106:
			return 15;

			case 20114:
			return 25;

			case 20100:
			return 15;

			case 20104:
			return 20;

			case 20108:
			return 25;

			case 20112:
			return 30;

			case 20103:
			return 15;

			case 20107:
			return 20;

			case 20111:
			return 25;

			case 20115:
			return 30;

			case 20101:
			return 15;

			case 20105:
			return 20;

			case 20109:
			return 25;

			case 20113:
			return 30;

			case 4763:
			return 50;

			case 11629:
			return 100;

			case 20141:
			return 100;

			case 11349:
			return 800;

			case 4064:
			return 1000;

			case 11653:
			return 150;

			case 11654:
			return 150;

			case 11651:
			return 150;

			case 4633:
			return 25;

			case 11638:
			return 15;

			case 11147:
			return 15;

			case 20082:
			return 1000;

			case 20083:
			return 1200;

			case 20084:
			return 1500;

			case 19960:
			return 25;

			case 979:
			return 25;

			case 4630:
			return 100;

			case 4631:
			return 100;

			case 11596:
			return 1000;

			case 4647:
			return 1000;

			case 4648:
			return 1000;

			case  11149:
			return 700;

			case 11148:
			return 15;

			case 19959:
			return 25;

			case 20085:
			return 1500;

			case 20081:
			return 1000;

			case 20080:
			return 2000;

			case 20087:
			return 2000;

			case 4649:
			return 5000;

			case 20086:
			return 1000;

			case 20089:
			return 1000;

			case 20088:
			return 1000;

			case 20091:
			return 1500;

			case 20090:
			return 1000;

			case 20092:
			return 1000;

			case 20093:
			return 1000;

			case 20095:
			return 1000;

			case 20094:
			return 1000;

			case 20097:
			return 1000;

			case 4632:
			return 25;

			case 4634:
			return 30;

			case 13198:
			return 25;

			case 4635:
			return 25;

			case 4636:
			return 25;

			case 4637:
			return 25;

			case 20096:
			return 1000;

			case 19952:
			return 100;

			case 19931:
			return 100;

			case 19961:
			return 100;

			case 19953:
			return 25;

			case 18382:
			return 100;

			case 62771:
			return 100;

			case 18384:
			return 50;

			case 4638:
			return 70;

			case 19951:
			return 100;

			case 3273:
			return 50;

			case 3275:
			return 50;

			case 3277:
			return 50;

			case 3279:
			return 50;

			case 3281:
			return 50;

			case 3283:
			return 50;

			//case 3285:
			//return 50;

			//case 3287:
			//return 50;

			//case 3289:
			//return 50;

			case 3291:
			return 500;

			case 3293:
			return 50;

			case 3295:
			return 50;

			case 3297:
			return 50;

			case 3299:
			return 50;

			case 3305:
			return 50;

			case 3307:
			return 50;

			case 3309:
			return 50;

			case 3311:
			return 50;

			case 3313:
			return 50;

			case 3315:
			return 50;

			case 3317:
			return 50;

			case 3319:
			return 50;

			case 3321:
			return 50;

			case 3323:
			return 50;

			case 13495:
			return 25;

			case 13492:
			return 25;

			case 13493:
			return 25;

			case 4639:
			return 50;

			case 4641:
			return 40;

			case 3809:
			return 25;

			case 18743:
			return 250;

			case 4804:
			return 25;

			case 4805:
			return 25;

			case 4806:
			return 25;

			case 13221:
			return 250;

			case 13225:
			return 250;

			case 19477:
			return 25;

			case 4640:
			return 25;

			case 19476:
			return 25;

			case 13824:
			return 25;

			case 13834:
			return 25;

			case 13836:
			return 25;

			case 13826:
			return 25;

			case 13828:
			return 25;

			case 13830:
			return 25;

			case 13997:
			return 50;

			case 13998:
			return 50;

			case 13999:
			return 50;

			case 14000:
			return 50;

			case 14001:
			return 50;

			case 14002:
			return 50;

			case 14003:
			return 50;

			case 14004:
			return 50;

			case 14005:
			return 50;

			case 14006:
			return 50;

			case 14007:
			return 500;

			case 14008:
			return 50;

			case 14009:
			return 50;

			case 14010:
			return 50;

			case 14011:
			return 50;

			case 9899:
			return 300;

			case 14390:
			return 50;

			case 11662:
			return 200;

			case 11660:
			return 200;

			case 20250:
			return 200;

			case 20251:
			return 100;

			case 20252:
			return 100;

			case 20253:
			return 100;

			case 20159:
			return 300;

			case 20157:
			return 300;

			case 20210:
			return 300;

			case 20161:
			return 300;

			case 11661:
			return 200;

			case 12256:
			return 500;

			case 12257:
			return 500;

			case 12258:
			return 500;

			case 12268:
			return 500;

			case 12269:
			return 500;

			case 12270:
			return 500;

			case 12259:
			return 500;

			case 12260:
			return 500;

			case 12261:
			return 500;

			case 12262:
			return 500;

			case 12263:
			return 500;

			case 12264:
			return 500;

			case 12271:
			return 500;

			case 12272:
			return 500;

			case 12273:
			return 500;

			case 12274:
			return 2000;

			case 12275:
			return 2000;

			case 12276:
			return 2000;

			case 12265:
			return 800;

			case 12266:
			return 800;

			case 12267:
			return 800;

			case 12277:
			return 800;

			case 12278:
			return 800;

			case 12279:
			return 800;

			case 12280:
			return 500;

			case 12281:
			return 500;

			case 12282:
			return 500;

			case 12283:
			return 500;

			case 12284:
			return 500;

			case 12285:
			return 500;

			case 12289:
			return 500;

			case 12290:
			return 500;

			case 12291:
			return 500;

			case 12292:
			return 500;

			case 939:
			return 50;

			case 4651:
			return 50;

			case 3811:
			return 50;

			case 3813:
			return 50;

			case 4652:
			return 50;

			case 19936:
			return 50;

			case 11626:
			return 50;

			case 11593:
			return 50;

			case 11592:
			return 50;

			case 11591:
			return 50;

			case 11590:
			return 50;

			case 11627:
			return 50;

			case 9898:
			return 50;

			case 11142:
			return 15;

			case 18362:
			return 350;

			case 13196:
			return 200;

			case 19939:
			return 4000;

			case 11143:
			return 15;

			case 11144:
			return 15;

			case 11145:
			return 15;

			case 20098:
			return 15;

			case 20132:
			return 15;

			case 4765:
			return 100;

			case 11146:
			return 15;

			case 19933:
			return 50;

			case 9374:
			return 50;

			case 9897:
			return 50;

			case 19930:
			return 200;

			case 9895:
			return 50;

			case 18785:
			return 2000;

			case 18784:
			return 2000;

			case 12310:
			return 2000;

			case 3890:
			return 250;

			case 3821:
			return 200;

			case 19558:
			return 50;

			case 9396:
			return 1000;

			case 15017:
			return 250;

			case 5023:
			return 100;

			case 962:
			return 2000;

			case 4083:
			return 3000;

			case 19913:
			return 100;

			case 13362:
			return 100;

			case 20254:
			return 100;

			case 19929:
			return 350;

			case 938:
			return 50;

			case 13197:
			return 50;

			case 4642:
			return 50;

			case 918:
			return 50;

			case 4646:
			return 50;

			case 19958:
			return 100;

			case 19916:
			return 100;

			case 19957:
			return 200;

			case 19956:
			return 2000;

			case 13840:
			return 300;

			case 18388:
			return 50;

			case 4058:
			return 50;

			case 9992:
			return 50;

			case 4643:
			return 50;

			case 4644:
			return 50;

			case 19753:
			return 50;

			case 968:
			return 50;

			case 930:
			return 50;

			case 919:
			return 50;

			case 19915:
			return 100;

			case 13360:
			return 100;

			case 11724:
			return 200;

			case 11726:
			return 200;

			case 933:
			return 50;

			case 934:
			return 50;

			case 4056:
			return 50;

			case 1000:
			return 50;

			case 11728:
			return 20;

			case 9719:
			return 100;

			case 3823:
			return 100;

			case 13358:
			return 100;

			case 13355:
			return 100;

			case 13354:
			return 100;

			case 13352:
			return 100;

			case 13350:
			return 100;

			case 13347:
			return 500;

			case 4063:
			return 100;

			case 17837:
			return 2000;

			case 17881:
			return 50;

			case 13348:
			return 100;

			case 13346:
			return 100;

			case 13344:
			return 50;

			case 13342:
			return 50;

			case 13340:
			return 50;

			case 13370:
			return 50;

			case 7783:
			return 50;

			case 11631:
			return 1000;

			case 3886:
			return 100;

			case 3825:
			return 100;

			case 3884:
			return 100;

			case 11607:
			return 100;

			case 11608:
			return 100;

			case 11609:
			return 100;

			case 11610:
			return 100;

			case 11611:
			return 100;

			case 11612:
			return 100;

			case 11634:
			return 50;

			case 4060:
			return 50;

			case 4061:
			return 50;

			case 13356:
			return 50;

			case 5200:
			return 50;

			case 5201:
			return 50;

			case 5211:
			return 50;

			case 5212:
			return 50;

			case 5203:
			return 500;

			case 16481:
			return 50;

			case 898:
			return 50;

			case 6499:
			return 50;

			case 900:
			return 50;

			case 6196:
			return 200;

			case 6189:
			return 50;

			case 6190:
			return 50;

			case 6191:
			return 50;

			case 6192:
			return 50;

			case 6193:
			return 50;

			case 5204:
			return 50;

			case 5205:
			return 50;

			case 5206:
			return 50;

			case 5222:
			return 50;

			case 5213:
			return 50;

			case 5214:
			return 50;

			case 5215:
			return 50;

			case 5216:
			return 50;

			case 5217:
			return 50;

			case 5218:
			return 50;

			case 12241:
			return 50;

			case 12242:
			return 50;

			case 12243:
			return 500;

			case 12244:
			return 500;

			case 12245:
			return 500;

			case 12246:
			return 500;

			case 12247:
			return 500;

			case 12248:
			return 500;

			case 12249:
			return 500;

			case 12250:
			return 500;

			case 12251:
			return 500;

			case 5185:
			return 100;

			case 5186:
			return 100;

			case 5187:
			return 100;

			case 5188:
			return 100;

			case 5189:
			return 100;

			case 5190:
			return 100;

			case 3285:
			return 500;

			case 3286:
			return 500;

			case 3287:
			return 500;

			case 3288:
			return 500;

			case 3289:
			return 500;

			case 3290:
			return 500;

			case 12233:
			return 250;

			case 12234:
			return 250;

			case 12235:
			return 250;

			case 12236:
			return 250;

			case 12237:
			return 250;

			case 12238:
			return 350;

			case 12225:
			return 350;

			case 12227:
			return 350;

			case 12228:
			return 350;

			case 12229:
			return 350;

			case 12230:
			return 350;

			case 12231:
			return 350;

			case 12232:
			return 350;
		
		}
		return 0;
	}
	public int getBarb(int id) {
		switch (id) {
		case 11964:
		return 400000;
		case 3581:
			return 3500;
		case 3583:
			return 7500;
		case 3585:
			return 100000;
		case 3587:
			return 125000;
		case 3589:
			return 450000;
		}
		return 99999;
	}
	public int getPvpValue(int id) {
		switch (id) {
		case 16955:
		case 16909:
		case 17143:
		case 16425:
		case 16403:
			return 25;
		case 16711:
		case 16689:
		case 17259:
		case 17361:
			return 35;
		} 
		return 0;
	}
	public int getSpecialItemValue(int id) {
		switch (id) {

		case 4059:
			return 1250;
		case 12111:
		case 12113:
		case 12115:
		case 12117:
		case 12119:
			return 10;
		case 15332:
			return 2;
		case 4212:
			return 65;
		case 13221:
			return 150;
		case 13867:
			return 80;
		case 10563:
			return 45;
		case 896:
			return 600;
		case 621:
			return 10;
		case 11964:
			return 40;
		case 2572:
			return 15;
		case 19986:
			return 40;
		case 15017:
			return 5;
		case 15503:
		case 15505:
		case 15507:
			return 10;
		case 4634:
			return 750;
		case 4635:
			return 750;
		case 4636:
			return 750;
		case 4637:
			return 750;
		case 3807:
			return 50;
		case 4056:
			return 200;
		case 4062:
			return 200;
		case 4063:
			return 650;
		case 4630:
			return 150;
		case 4632:
			return 100;
		case 4631:
			return 150;
		case 4640:
			return 300;
		case 4641:
			return 300;
		case 4642:
			return 600;
		case 4644:
			return 250;
		case 4648:
			return 200;
		case 4649:
			return 50;
		case 19939:
			return 15;
		case 18357:
			return 11;
		case 8810:
		case 8811:
		case 8812:
			return 4;
		case 8813:
		case 8814:
		case 8815:
			return 6;
		case 8807:
		case 8808:
		case 8809:
			return 11;
		case 1436:
			return 10;
		case 7936:
			return 30;

		case 3529:
			return 1;
		case 3531:
			return 2;
		case 3533:
			return 3;
		case 3535:
			return 5;
		case 3537:
			return 8;
		case 3539:
			return 10;
		case 3541:
			return 12;
		case 3543:
			return 15;
		case 3545:
			return 17;
		case 3547:
			return 20;
		case 806:
			return 7;
		case 12311:
			return 85;
		case 12309:
			return 95;
		case 15078:
			return 35;
		case 19138:
			return 15;
		case 3565:
			return 6;
		case 17882:
			return 15;
		case 17875:
			return 35;
		case 13451:
			return 65;
		case 12315:
			return 50;
		case 5020:
			return 1;



		case 15445:
			return 50;
		case 15446:
			return 100;
		case 15447:
			return 200;
		case 15448:
			return 500;
		case 15449:
			return 1500;
		case 4643:
			return 200;
		case 18384:
			return 200;

		case 15455:
			return 50;
		case 15456:
			return 100;
		case 15457:
			return 200;
		case 15458:
			return 500;
		case 15459:
			return 1500;
		case 13663:
			return 25;
		case 14700:
			return 45;
		case 13659:
			return 20;
		case 18626:
			return 12;
		case 744:
			return 8;
		case 4650:
			return 200;
		case 4651:
			return 200;
		case 4652:
			return 200;
		case 13196:
			return 700;
		case 13197:
			return 500;
		case 13198:
			return 150;
		case 20079:
			return 750;

		case 2412:
			return 500;
		case 2413:
			return 500;
		case 2414:
			return 500;
		case 6889:
			return 1000;

		case 17845:
		case 17847:
		case 17849:
		case 17851:
		case 17855:
		case 17857:
		case 18380:
			return 5;

		case 17881:
			return 500;
		case 17863:
			return 1000;
		case 18785:
			return 6;
		case 17859:
			return 35;
		case 17873:
			return 5000;
		case 18382:
			return 10000;

		case 14003:
		case 14004:
		case 14005:
			return 10000;
		case 14008:
		case 14009:
			return 7500;
		case 19477:
			return 20000;
		case 20155:
			return 35000;
		case 9396:
			return 45;
		case 18743:
			return 10000;
		case 9374:
			return 7000;
		case 19936:
			return 15000;
		case 19957:
			return 7000;
		case 19753:
			return 10000;
		case 918:
			return 10000;
		case 930:
			return 10000;
		case 7783:
			return 25000;
		case 13347:
			return 50000;
		case 11631:
			return 10420;
		case 11607:
			return 25000;
		case 11608:
			return 25000;
		case 11611:
			return 25000;
		case 11610:
			return 10000;
		case 20098:
		case 20099:
			return 10000;
		case 19961:
			return 4500;
		case 1767:
			return 7500;
		case 16667:
		case 16689:
			return 5000;
		case 19951:
		case 19952:
		case 19953:
			return 4500;
		case 19954:
			return 2000;
		case 11349:
			return 5000;
		case 16733:
			return 3000;
		case 16909:
			return 4000;
		case 16359:
		case 16293:
			return 2500;
		case 16955:
		case 16425:
		case 16403:
		case 15773:
		case 17039:
			return 4000;
			// Dominion minigame shop prices
		case 18349:
			return 2000;
		case 19478:
			return 4500;
		case 18353:
			return 2000;
		case 18351:
			return 2000;
		case 18355:
			return 2000;
		case 18359:
			return 2000;
		case 18365:
			return 1000;
			// Vote Point Shop
		case 13362:
			return 10;
		case 13358:
			return 10;
		case 18830:
		case 4151:
		case 2934:
		case 2914:
		case 2904:
		case 634:
		case 626:
		case 13666:
		case 1837:
			return 10;
		case 13360:
			return 10;
		case 14001:
		case 4067:
		case 6082:
		case 15486:
			return 2;
		case 4566:
		case 11634:
		case 20085:
			return 5;
		case 14002:
			return 2;
		case 10550: // range hat
			return 65;
		case 10548: // fight hat
			return 65;
		case 10551: // torso
			return 10;
		case 11730: // sara sword
			return 75;
		case 1897: // chocolate cake
			return 75;
		case 13344: // ancient blah blah helm
			return 135;
		case 13342:
			return 200;
		case 13340:
			return 150;
		case 10552: // torso
			return 75;

		case 19920:
		case 19921:
		case 19919:
		case 3819:
			return 18000;

		case 19916:
		case 19918:
		case 19914:
		case 19915:
		case 19913:
			return 23000;

		case 18747:
			return 35;
		case 4057:
			return 8000;
		case 4064:
			return 5000;
		case 4065:
			return 5000;
		case 4066:
			return 5000;
		case 4639:
			return 10000;
		case 4647:
			return 7500;
		case 4061:
			return 8000;

			// END OF ASSAULT

			// case 1767:
			// case 1765:
			// case 1771:
			// case 5559:
			// return 30;
		case 6916:
		case 6918:
		case 6920:
		case 6922:
		case 6924:
			return 70;
		case 19790:
			return 140;
		case 11663:
		case 11664:
		case 11665:
		case 8842:
			return 40;
		case 20083:
			return 14;
		case 17020:
			return 70;
		case 17004:
		case 17003:
		case 17005:
		case 17002:
			return 50;
		case 15308:
		case 15312:
		case 15316:
		case 15320:
		case 15324:
			return 4;
		case 11696:
		case 11698:
		case 11700:
			return 600;
		case 11694:
			return 800;
		case 10499:
			return 30;
		case 13887:
		case 13893:
			return 580;
		case 18017:
		case 18018:
		case 18019:
		case 18020:
			return 3;
		case 14508:
			return 250;
		case 8845:
			return 10;
		case 8846:
			return 10;
		case 8847:
			return 15;
		case 8848:
			return 20;
		case 8849:
		case 8850:
			return 25;
		case 6570:
			return 5;
		case 10887:
			return 250;
		case 11851:
		case 12210:
		case 12213:
		case 12216:
		case 12219:
		case 12222:
			return 100;
		case 11724:
			return 1200;
		case 11728:
			return 250;
		case 11720:
			return 1000;
		case 11722:
			return 900;
		case 11726:
			return 1100;
		case 15126:
			return 3;
		case 13263:
			return 200;
		case 18335:
			return 2500;
		case 19780:
			return 1000;
		case 17841:
			return 20;
		case 17843:
			return 20;
		case 9898:
			return 25;
		case 6585:
			return 15;
		case 16713:
			return 400;

		case 15018:
			return 850;
		case 13870:
		case 13873:
			return 300;
		case 13876:
			return 250;
		case 15055:
			return 5;
		case 15031:
			return 600;
		case 15007:
		case 15006:
			return 700;
		case 17259:
			return 6000;
		case 17361:
			return 4000;
		case 16711:
			return 5000;
		case 11821:
			return 1000;
		case 11822:
			return 1000;
		case 11820:
			return 800;
		case 15022:
		case 15026:
			return 750;
		case 15021:
		case 15023:
			return 850;
		case 13350:
			return 2200;
		case 15220:
			return 150;
		case 13351:
			return 2200;
		case 15025:
			return 230;
		case 15024:
			return 170;
		case 15043:
		case 15042:
			return 790;
		case 15041:
		case 15040:
			return 650;
		case 15051:
			return 500;
		case 15050:
			return 700;
		case 19785:
			return 160;
		case 19786:
			return 140;
		case 13858:
		case 13861:
			return 300;
		case 1555:
		case 7584:
		case 1556:
		case 1557:
		case 1558:
			return 150;
		case 13890:
		case 13884:
			return 400;
		case 13899:
			return 15;
		case 13896:
			return 350;
		case 13902:
			return 15;
		case 15509:
		case 15511:
			return 35;
		case 15073:
			return 55;
		case 15074:
			return 55;
		case 17025:
			return 35;
		case 17018:
			return 90;
		case 17019:
			return 80;
		case 19784:
			return 1000;
		case 15054:
			return 200;
		case 17271:
			return 150;
		case 14936:
			return 300;
		case 14938:
			return 250;
		case 20088:
			return 21;
		case 20089:
			return 26;
		case 15090:
		case 15091:
		case 15092:
		case 15093:
		case 15094:
		case 15095:
		case 15096:
		case 15097:
		case 15098:
			return 350;
		case 15085:
			return 1300;
		case 15081:
			return 1150;
		case 15080:
			return 1150;
		case 15083:
			return 600;
		case 13352:
		case 13353:
		case 13354:
			return 700;
			// START OF AGILITY STORE
		case 10400: // ELEGANT TOP
		case 10432: // green
		case 10428:
		case 10404:
			return 300;
		case 10402: // ELEGANT SKIRT
		case 10430: // blue skirt
		case 10434:
		case 10406:
			return 250;
		case 10766: // BLACK BOATER
		case 10764: // blue boater
		case 10758: // redboater
		case 10762: // green boater
			return 220;
		case 13661: // adze
			return 600; // adze
		case 16222:
			return 1100;
		case 14497:
		case 14501:
			return 150;
		case 14499:
			return 60;
		case 15060:
		case 15062:
			return 150;
		case 15061:
			return 70;
		case 15020:
			return 700;
		case 2577:
		case 2581:
			return 40;
		case 6914:
		case 6890:
			return 45;

		case 552:
			return 100;
		case 9006:
			return 100;
		case 11789:
			return 100;
		case 1419:
			return 100;
		case 18346:
			return 100;

		case 5216:
		case 5215:
		case 5214:
		case 5213:
		case 5218:
		case 5217:
			return 500;
		case 5203:
			return 450;
		case 5201:
			return 400;
		case 5211:
		case 5212:
			return 900;

		case 11137:
			return 21;
		case 19750:
			return 5000;

		}
		return 9999999;
	}

	public int get1BTicketShopValue(int id) {
		switch (id) {
		case 6857:
		case 6861:
		case 6859:
		case 19311:
			return 500;
		case 10340:
		case 10338:
		case 10350:
		case 10352:
			return 5000;
		case 10348:
		case 10346:
			return 10000;
		}
		return 0;
	}

	public int get2BTicketShopValue(int id) {
		switch (id) {
		case 6857:
		case 6861:
		case 6859:
		case 19311:
			return 250;
		case 10340:
		case 10338:
		case 10350:
		case 10352:
			return 2500;
		case 10348:
		case 10346:
			return 5000;
		}
		return 0;
	}
	public int getIronManValue(int id) {
	switch (id) {
	case 4810:
		return 1000;
	case 3581:
		return 150;
	case 3583:
		return 850;
	case 3585:
		return 1800;
	case 3587:
		return 2950;
	case 3589:
		return 12500;
	case 11964:
		return 35000;
	case 10563:
		return 75000;
	case 14162:
		return 1;
	case 19867:
		return 35000;
	case 19865:
		return 125000;
	case 19709:
		return 2250000;
		}
		return 99999999;
	}
	public int getSuperSpecialItemSellValue(int id) {
	switch (id) {
	case 4810:
		return 1000;
	case 19867:
		return 1000;
	case 19865:
		return 15000;
	case 19709:
		return 2000000;
	case 12293:
		return 1000;
	case 12295:
		return 10000;
	case 12297:
		return 100000;
	case 12299:
		return 1000000;
	case 3551:
		return 150;
	case 3553:
		return 220;
	case 3555:
		return 350;
	case 3557:
		return 450;
	case 3559:
		return 750;
	case 3561:
		return 1250;
	case 3563:
		return 2000;
	case 892:
		return 1;
	case 18830:
		return 2;
	case 2435:
		return 1;
	case 14162:
		return 15;
	case 11660:
	case 11661:
	case 11662:
		return 2300;
	case 12257:
	case 12258:
	case 12256:
		return 5200;
	case 12260:
	case 12261:
	case 12259:
		return 5400;
	case 12263:
	case 12264:
	case 12262:
		return 5700;
	case 12269:
	case 12270:
	case 12268:
		return 5900;
	case 12272:
	case 12273:
	case 12271:
		return 6200;
	case 12281:
	case 12282:
	case 12280:
		return 6500;
	case 12284:
	case 12285:
	case 12283:
		return 6900;
	case 12290:
	case 12291:
	case 12289:
		return 7500;
	case 12266:
	case 12267:
	case 12265:
		return 8900;
	}
	return 0;
	}
	public int getSpecialItemSellValue(int id) {
		switch (id) {

		case 19939:
			return 15;
		case 17875:
			return 35;
		case 13451:
			return 60;
		case 11931:
			return 100;
		case 18785:
			return 6;
		case 6605:
			return 5;
		case 17854:
			return 300;
		case 18362:
			return 150;
		case 8810:
		case 8811:
		case 8812:
			return 4;
		case 8813:
		case 8814:
		case 8815:
			return 6;
		case 8807:
		case 8808:
		case 8809:
			return 11;

		case 934:
			return 10;
		case 20161:
		case 20157:
		case 20210:
		case 20158:
			return 75;
		case 20159:
			return 50;
		case 12225:
		case 12227:
		case 12228:
		case 12229:
		case 12231:
		case 12232:
		case 12230:
			return 150;
		case 20252:
		case 20253:
		case 20251:
			return 20;

		case 13879:
			return 2;
		case 20250:
			return 30;
		//New shop by Haden
		case 11660:
		return 50;
		case 968:
		return 5;
		case 11661:
		return 50;
		case 11662:
		return 50;
		case 19958:
		return 150;
		case 10705:
		return 300;
		case 15037:
		return 150;
		case 15038:
		return 150;
		case 19200:
		return 300;
		case 19203:
		return 300;
		case 19931:
		return 200;
		case 10707:
		return 300;
		case 19242:
		return 300;
		case 19245:
		return 300;
		case 6196:
		return 35;
		case 13738:
		case 13740:
		case 13742:
		case 13744:
		return 3;
		case 15002:
		return 75;
		case 15004:
		return 75;
		case 15005:
		return 75;
		case 15001:
		return 75;
		case 9899:
		return 250;
		case 19087:
		return 175;
		case 19088:
		return 175;
		case 19089:
		return 175;
		case 19090:
		return 175;
		case 19930:
		return 25;
		case 8804:
		case 8805:
		case 8806:
		return 5;
		//end of shop by Haden
		case 3288:
		case 3285:
		case 3289:
		case 3291:
		case 3286:
		case 3287:
			return 200;
		case 3290:
			return 250;
		case 12233:
		case 12234:
		case 12235:
		case 12236:
		case 12237:
		case 12239:
			return 125;
		case 12238:
			return 75;
		case 4741:
		case 4742:
		case 4629:
		case 4626:
		case 4744:
			return 250;
		case 4743:
			return 300;
		case 11607:
		case 11608:
		case 11609:
		case 11610:
		case 11611:
		case 11612:
		case 19953:
			return 10;
		case 20155:
			return 5;

		}
		return 0;
	}

	/**
	 * Sell item to shop (Shop Price)
	 **/
	public void sellToShopPrice(int removeId, int removeSlot) {
		if (c.playerRights == 2 && Config.ADMIN_CAN_SELL_ITEMS == false) {
			c.sendMessage("You can't sell "
					+ c.getItems().getItemName(removeId).toLowerCase() + ".");
			return;
		}
		for (int i : Config.ITEM_SELLABLE) {

			if (c.myShopId == 7390) {
				if (c.playerRights == 2 && Config.ADMIN_CAN_SELL_ITEMS == false
						&& !c.playerName.equalsIgnoreCase("Tommy17890")) {
					c.sendMessage("You can't sell items.");
					return;
				}
				c.sendMessage("Choose your price by pushing sell on the item.");
				return;
			}

			if (i == removeId) {
				c.sendMessage("You can't sell "
						+ c.getItems().getItemName(removeId).toLowerCase()
						+ ".");
				return;
			}

		}
		boolean IsIn = false;
		if (Server.shopHandler.ShopSModifier[c.myShopId] > 1) {
			for (int j = 0; j <= Server.shopHandler.ShopItemsStandard[c.myShopId]; j++) {
				if (removeId == (Server.shopHandler.ShopItems[c.myShopId][j] - 1)) {
					IsIn = true;
					break;
				}
			}
		} else {
			IsIn = true;
		}
		if (c.myShopId == 117) {
			IsIn = true;
		}
		if (c.myShopId == 121) {
			IsIn = true;
		}
		if(c.myShopId == 118) {// # shop id
			if (getTicketSell(removeId) == 0) {
				c.sendMessage("You can not sell this item to the Ticket Merchant.");
			} else if (getTicketSell(removeId) >= 0) {
	        c.sendMessage(c.getItems().getItemName(removeId)+": currently costs "+getTicketSell(removeId)+" 1b Tickets");//### token name
	        return;
		}
		if (IsIn == false) {
			c.sendMessage("You can't sell "
					+ c.getItems().getItemName(removeId).toLowerCase()
					+ " to this store.");
		} else if (c.myShopId == 117) {
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": shop will buy for "
					+ getSuperSpecialItemSellValue(removeId) + " 1B Tickets");
		} else {
			int ShopValue = (int) Math.floor(getItemShopValue(removeId, 1,
					removeSlot));
			String ShopAdd = "";
			if (ShopValue >= 1000 && ShopValue < 1000000) {
				ShopAdd = " (" + (ShopValue / 1000) + "K)";
			} else if (ShopValue >= 1000000) {
				ShopAdd = " (" + (ShopValue / 1000000) + " million)";
			}
			c.sendMessage(c.getItems().getItemName(removeId)
					+ ": shop will buy for " + ShopValue + " coins" + ShopAdd);
		}
	}
	}

	public boolean sellItem(int itemID, int fromSlot, int amount) {
		
		if (this.getItemShopValue(itemID) == 0
				&& this.getSpecialItemSellValue(itemID) == 0 && this.getTicketSell(itemID) == 0) {
			c.sendMessage("Sorry this item is worth nothing, you cannot sell it.");
			return false;
		}
		if (this.getTicketSell(itemID) == -1) {
			c.sendMessage("Sorry this item is worth nothing in this store.");
			return false;
		}
			if (System.currentTimeMillis() - c.lastButton > 15000) {
				c.lastButton = System.currentTimeMillis();

			} else {
				c.lastButton = System.currentTimeMillis();
		}
		if (!Config.SHOPS_ENABLED) {
			c.sendMessage("Shops are currently disabled!");
			return false;
		}
		/*
		 * if (c.myShopId == 117) {
		 * c.sendMessage("You can't sell "+c.getItems().
		 * getItemName(itemID).toLowerCase()+" to this store."); return false; }
		 */
		if (c.myShopId == 117 && getSuperSpecialItemSellValue(itemID) > 0) {
			if (System.currentTimeMillis() - c.buryDelay > 1500) {

			if (c.getItems().freeSlots() > 0) {
				c.sendMessage("You can't sell items here!");
				if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
					Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
				}
			} else {
				c.sendMessage("You can't sell items here!");
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
			} else if (c.myShopId == 117 && System.currentTimeMillis() - c.buryDelay < 1500) {
			c.sendMessage("<col=800000000>You can't sell items this fast!");
			}
			}
		/*if (c.myShopId == 118) {
			c.sendMessage("You can't sell "
					+ c.getItems().getItemName(itemID).toLowerCase()
					+ " to this store.");
			return false;
		}*/
		if (c.inTrade) {
			c.sendMessage("You cant sell items to the shop while your in trade!");
			return false;
		}
		if (c.playerRights == 2 && Config.ADMIN_CAN_SELL_ITEMS == false
				&& !c.playerName.equalsIgnoreCase("Tommy17890")) {
			c.sendMessage("You can't sell "
					+ c.getItems().getItemName(itemID).toLowerCase() + ".");
			return false;
		}
		if(c.myShopId == 118) {//# shop id
			if (getTicketSell(itemID) == 0) { 
				c.sendMessage("Sorry, you can not sell this item to the shop for 1B tickets!");
			} else if (System.currentTimeMillis() - buyDelay < 1500) {
				return false;
			} else   if (c.getItems().freeSlots() > 0) {
                c.getItems().deleteItem(itemID, fromSlot, 1);
				buyDelay = System.currentTimeMillis();
                    c.getItems().addItem(5021, getTicketSell(itemID));// 6529 token ID you want to be added upon selling.
                
                        addShopItem(itemID, 1);
                            Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] += 1;
                                Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
                                  //  Server.shopHandler.ShopItemsRestock[c.myShopId][fromSlot] = System.currentTimeMillis();
                    if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
                        Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
                    }
                } else {
                    c.sendMessage("@red@You don't have enough space in your inventory.");    
                
			}
                            c.getItems().resetItems(3823);
    resetShop(c.myShopId);
    updatePlayerShop();
    return true;
            }
		if (c.myShopId == 7390) {
			for (int i : Config.ITEM_TRADEABLE) {
				if (i == itemID) {
					c.sendMessage("You can't sell this item.");
					return false;
				}
			}
			if (c.playerName.equals(c.myShopClient.playerName)) {
				c.sellingId = itemID;
				c.sellingN = amount;
				c.sellingS = fromSlot;
				c.xInterfaceId = 7390;
				c.outStream.createFrame(27);
			} else {
				c.sendMessage("You can only sell items on your own store.");
			}
			return true;
		}
		if (c.myShopId == 14)
			return false;
		for (int i : Config.ITEM_SELLABLE) {
			if (i == itemID) {
				c.sendMessage("You can't sell "
						+ c.getItems().getItemName(itemID).toLowerCase() + ".");
				return false;
			}
		}

		if (amount > 0 && itemID == (c.playerItems[fromSlot] - 1)) {
			if (Server.shopHandler.ShopSModifier[c.myShopId] > 1) {
				boolean IsIn = false;
				for (int i = 0; i <= Server.shopHandler.ShopItemsStandard[c.myShopId]; i++) {
					if (itemID == (Server.shopHandler.ShopItems[c.myShopId][i] - 1)) {
						IsIn = true;
						break;
					}
				}
				if (IsIn == false) {
					c.sendMessage("You can't sell "
							+ c.getItems().getItemName(itemID).toLowerCase()
							+ " to this store.");
					return false;
				}
			}

			if (amount > c.playerItemsN[fromSlot]
					&& (Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == true || Item.itemStackable[(c.playerItems[fromSlot] - 1)] == true)) {
				amount = c.playerItemsN[fromSlot];
			} else if (amount > c.getItems().getItemAmount(itemID)
					&& Item.itemIsNote[(c.playerItems[fromSlot] - 1)] == false
					&& Item.itemStackable[(c.playerItems[fromSlot] - 1)] == false) {
				amount = c.getItems().getItemAmount(itemID);
			}
			// double ShopValue;
			// double TotPrice;
			int TotPrice2 = 0;
			// int Overstock;
			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 1,
						fromSlot));
				if (c.getItems().freeSlots() > 0
						|| c.getItems().playerHasItem(995)) {
					if (Item.itemIsNote[itemID] == false) {
						c.getItems().deleteItem(itemID,
								c.getItems().getItemSlot(itemID), 1);
					} else {
						c.getItems().deleteItem(itemID, fromSlot, 1);
					}
					c.getItems().addItem(995, TotPrice2);
					addShopItem(itemID, 1);
				} else {
					c.sendMessage("You don't have enough space in your inventory.");
					break;
				}
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return true;
	}

	public boolean addShopItem(int itemID, int amount) {
		boolean Added = false;
		if (amount <= 0) {
			return false;
		}
		if (Item.itemIsNote[itemID] == true) {
			itemID = c.getItems().getUnnotedItem(itemID);
		}
		for (int i = 0; i < Server.shopHandler.ShopItems.length; i++) {
			if ((Server.shopHandler.ShopItems[c.myShopId][i] - 1) == itemID) {
				Server.shopHandler.ShopItemsN[c.myShopId][i] += amount;
				Added = true;
			}
		}
		if (Added == false) {
			for (int i = 0; i < Server.shopHandler.ShopItems.length; i++) {
				if (Server.shopHandler.ShopItems[c.myShopId][i] == 0) {
					Server.shopHandler.ShopItems[c.myShopId][i] = (itemID + 1);
					Server.shopHandler.ShopItemsN[c.myShopId][i] = amount;
					Server.shopHandler.ShopItemsDelay[c.myShopId][i] = 0;
					break;
				}
			}
		}
		return true;
	}

	public long buyDelay;

	public boolean buyItem(int itemID, int fromSlot, int amount) {
		if (System.currentTimeMillis() - buyDelay < 1500) {
			return false;
		}
		if (c.myShopId == 7390 && c.myShopClient != null
				&& !c.myShopClient.properLogout
				&& !c.playerName.equals(c.myShopClient.playerName)) {
			if (c.playerRights == 2) {
				c.sendMessage("Sorry but buying as admin is disabled :/..");
				return false;
			}
			if (!Config.SHOPS_ENABLED) {
				c.sendMessage("Shops are currently disabled!");
				return false;
			}
			int bought = 0;
			int price = c.myShopClient.playerShopP[fromSlot];
			if (amount > c.myShopClient.playerShopN[fromSlot])
				amount = c.myShopClient.playerShopN[fromSlot];
			for (int x = 0; x < amount; x++) {
				if (c.getItems().playerHasItem(995,
						c.myShopClient.playerShopP[fromSlot])
						&& c.getItems().freeSlots() > 0) {
					c.getItems().deleteItem2(995,
							c.myShopClient.playerShopP[fromSlot]);
					c.getItems()
							.addItem(c.myShopClient.playerShop[fromSlot], 1);
					c.myShopClient.playerShopN[fromSlot]--;
					c.myShopClient.playerCollect += c.myShopClient.playerShopP[fromSlot];
					if (c.myShopClient.playerShopN[fromSlot] == 0) {
						c.myShopClient.playerShop[fromSlot] = 0;
						c.myShopClient.playerShopP[fromSlot] = 0;
					}
					bought++;
				} else {
					c.sendMessage("Not enought space or money.");
					break;
				}
			}
			if (c.myShopId == 117) {
				c.sendMessage("You can only sell items to this store!");
				return false;
			}
			if (bought > 0) {
				if (c.playerRights == 2) {
					c.sendMessage("Sorry but buying as admin is disabled :/..");
					return false;
				}
				resetShop(c.myShopClient);
				c.getItems().resetItems(3823);
				;
				c.sendMessage("You just bought " + bought + " "
						+ c.getItems().getItemName(itemID) + " for "
						+ (bought * price));
				c.myShopClient.sendMessage(c.playerName + " has bought "
						+ bought + " " + c.getItems().getItemName(itemID)
						+ " from you!");
				c.myShopClient.sendMessage("You now have "
						+ c.myShopClient.playerCollect
						+ " coins to collect (::collect)");
			}
			return false;
		} else if (c.myShopId == 7390 && c.myShopClient != null
				&& !c.myShopClient.properLogout
				&& c.playerName.equals(c.myShopClient.playerName)) {
			if (c.playerRights == 2) {
				c.sendMessage("Sorry but buying as admin is disabled :/..");
				return false;
			}
			if (amount > c.myShopClient.playerShopN[fromSlot])
				amount = c.myShopClient.playerShopN[fromSlot];
			for (int x = 0; x < amount; x++) {
				if (c.getItems().freeSlots() > 0) {
					c.getItems()
							.addItem(c.myShopClient.playerShop[fromSlot], 1);
					c.myShopClient.playerShopN[fromSlot]--;
					if (c.myShopClient.playerShopN[fromSlot] == 0) {
						c.myShopClient.playerShop[fromSlot] = 0;
						c.myShopClient.playerShopP[fromSlot] = 0;
						fixShop(c);
					}
				} else {
					c.sendMessage("Not enought space.");
					break;
				}
			}
			resetShop(c.myShopClient);
			c.getItems().resetItems(3823);
			return false;
		} else if (c.myShopId == 7390) {
			return false;
		}
		if (Server.shopHandler.ShopItems[c.myShopId][fromSlot] - 1 != itemID
				&& c.myShopId != 14 && c.myShopId != 1 && c.myShopId != 7390) {
			c.sendMessage("Stop trying to cheat.");
			return false;
		}

		if (c.myShopId == 14) {
			skillBuy(itemID);
			return false;

		} else if (c.myShopId == 15) {
			buyVoid(itemID);
			return false;

		} else if (c.myShopId == 1) {
			buyVoid(itemID);
			return false;
		}
		if (itemID != itemID) {
			c.sendMessage("Don't dupe or you will be IP Banned");
			return false;
		}

		if (!shopSellsItem(itemID))
			return false;

		if (amount > 0) {
			if (amount > Server.shopHandler.ShopItemsN[c.myShopId][fromSlot]) {
				amount = Server.shopHandler.ShopItemsN[c.myShopId][fromSlot];
			}
			// double ShopValue;
			// double TotPrice;
			int TotPrice2 = 0;
			// int Overstock;
			int Slot = 0;
			int Slot1 = 0;// 2b Tickets
			int Slot2 = 0;// Pking Points
			int Slot3 = 0;// Donator Gold
			int Slot4 = 0;// 1B Whore
			int Slot5 = 0;// 2B Whore

			if (c.myShopId == 18) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 117) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 121) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 118) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 72) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 122) {
				handleOtherShop(itemID);
				return false;
			}

			if (c.myShopId == 116) {
				handleOtherShop(itemID);
				return false;
			}

			if (c.myShopId == 77) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 120) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 95) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 43) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 4 || c.myShopId == 5) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 16) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 20) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 100) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 123) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 17) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 119) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 54) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 84) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 97) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 85) {
				handleOtherShop(itemID);
				return false;
			}
			if (c.myShopId == 96) {
				handleOtherShop(itemID);
				return false;
			}

			for (int i = amount; i > 0; i--) {
				TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0,
						fromSlot));
				Slot = c.getItems().getItemSlot(995);
				Slot1 = c.getItems().getItemSlot(4067);
				Slot3 = c.getItems().getItemSlot(5555);
				Slot4 = c.getItems().getItemSlot(5021);
				Slot5 = c.getItems().getItemSlot(4067);
				if (Slot == -1 && c.myShopId != 11 && c.myShopId != 29
						&& c.myShopId != 30 && c.myShopId != 31
						&& c.myShopId != 95 && c.myShopId != 72
						&& c.myShopId != 77 && c.myShopId != 84
						&& c.myShopId != 100 && c.myShopId != 117
						&& c.myShopId != 118) {
					c.sendMessage("You don't have enough coins.");
					break;
				}
				if ((Slot4 == -1) && (c.myShopId == 117)) {
					c.sendMessage("You don't have enough 1 Bill Tickets.");
					break;
				}
				if ((Slot4 == -1) && (c.myShopId == 121)) {
					c.sendMessage("You don't have enough 1 Bill Tickets.");
					break;
				}
				if ((Slot5 == -1) && (c.myShopId == 118)) {
					c.sendMessage("You don't have enough 1 Bill Tickets.");
					break;
				}
				if (Slot1 == -1 && c.myShopId == 29 || c.myShopId == 30) {
					c.sendMessage("You don't have enough 1b Tickets.");
					break;
				}
				if (Slot3 == -1 && c.myShopId == 353) {
					c.sendMessage("You don't have enough donator gold.");
					break;
				}

				if (TotPrice2 <= 1) {
					TotPrice2 = (int) Math.floor(getItemShopValue(itemID, 0,
							fromSlot));
					TotPrice2 *= 1.66;
				} else if (c.myShopId == 54) {
					if (c.votingPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.votingPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough points!");
						break;
					}

				}
				if (c.myShopId == 29 || c.myShopId == 30) {
					if (c.playerItemsN[Slot1] >= TotPrice2) {
						if (Item.itemStackable[itemID]
								&& c.getItems().playerHasItem(995,
										TotPrice2 * amount)) {
							c.getItems().deleteItem(995,
									c.getItems().getItemSlot(995),
									TotPrice2 * amount);
							c.getItems().addItem(itemID, amount);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= amount;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							c.getItems().resetItems(3823);
							resetShop(c.myShopId);
							updatePlayerShop();
							return false;
						}
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.getItems().deleteItem(4067,
									c.getItems().getItemSlot(4067), TotPrice2);
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough 2b Tickets.");
						break;
					}
				} else if (c.myShopId == 29) {
					if (c.playerItemsN[Slot1] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.Wheel -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Agility Points.");
						break;
					}
				} else if (c.myShopId == 16) {
					if (c.Wheel >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.Wheel -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Agility Points.");
						break;
					}
				} else if (c.myShopId == 100) {
					if (c.barbPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.barbPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Points.");
						break;
					}} else if (c.myShopId == 123) {
						if (c.barbPoints >= TotPrice2) {
							if (c.getItems().freeSlots() > 0) {
								buyDelay = System.currentTimeMillis();
								c.barbPoints -= TotPrice2;
								c.getItems().addItem(itemID, 1);
								Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
								Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
								if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
									Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
								}
							} else {
								c.sendMessage("You don't have enough space in your inventory.");
								break;
							}
						} else {
							c.sendMessage("You don't have enough Points.");
							break;
						}
				} else if (c.myShopId == 117) {
					if (c.playerItemsN[Slot4] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.getItems().deleteItem(5021,
									c.getItems().getItemSlot(5021), TotPrice2);
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough 1 Bill Tickets.");
							break;
						}
					}
				} else if (c.myShopId == 121) {
					if (c.playerItemsN[Slot4] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.getItems().deleteItem(5021,
									c.getItems().getItemSlot(5021), TotPrice2);
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough 1 Bill Tickets.");
							break;
						}
					}
				} else if (c.myShopId == 118) {
					/*if (c.playerItemsN[Slot5] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.getItems().deleteItem(5021,
									c.getItems().getItemSlot(5021), TotPrice2);
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough 1 Bill Tickets.");
						break;
					}*/
					c.sendMessage("You can not buy items from this store!");
				} else if (c.myShopId == 84) {
					if (c.dungPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.dungPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Dungeoneering Tokens.");
						break;
					}
				} else if (c.myShopId == 84) {
					if (c.customPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.customPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Custom Points.");
						break;
					}
				} else if (c.myShopId == 72) {
					if (c.donatorChest >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.donatorChest -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough NPC Kills.");
						break;
					}
				} else if (c.myShopId == 122) {
					if (c.donatorChest >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.donatorChest -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough NPC Kills.");
						break;
					}
				} else if (c.myShopId == 116) {
					if (c.npcKills >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.npcKills -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Donator Points.");
						break;
					}
				} else if (c.myShopId == 17) {
					if (c.Skillpoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.Skillpoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Skill Points.");
						break;
					}
				} else if (c.myShopId == 119) {
					if (c.superPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.superPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Mummy Points.");
						break;
					}
				} else if (c.myShopId == 77) {
					if (c.barrowPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.barrowPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You do not have enough <col=800000000>Barrows Minigame Points</col>.");
						break;
					}
				} else if (c.myShopId == 95) {
					if (c.achievementPoints >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.achievementPoints -= TotPrice2;
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough Achievement Points.");
						break;
					}
				} else if (c.myShopId == 11) {
					if (c.playerItemsN[Slot3] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.getItems().deleteItem(5555,
									c.getItems().getItemSlot(5555), TotPrice2);
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough donator gold.");
						break;
					}
				} else if (c.myShopId != 11 && c.myShopId != 29
						|| c.myShopId != 30 || c.myShopId != 31
						|| c.myShopId != 84 || c.myShopId != 95
						|| c.myShopId != 96 || c.myShopId != 85
						|| c.myShopId != 97 || c.myShopId != 77
						|| c.myShopId != 72 || c.myShopId != 16) {
					if (c.playerItemsN[Slot] >= TotPrice2) {
						if (c.getItems().freeSlots() > 0) {
							buyDelay = System.currentTimeMillis();
							c.getItems().deleteItem(995,
									c.getItems().getItemSlot(995), TotPrice2);
							c.getItems().addItem(itemID, 1);
							Server.shopHandler.ShopItemsN[c.myShopId][fromSlot] -= 1;
							Server.shopHandler.ShopItemsDelay[c.myShopId][fromSlot] = 0;
							if ((fromSlot + 1) > Server.shopHandler.ShopItemsStandard[c.myShopId]) {
								Server.shopHandler.ShopItems[c.myShopId][fromSlot] = 0;
							}
						} else {
							c.sendMessage("You don't have enough space in your inventory.");
							break;
						}
					} else {
						c.sendMessage("You don't have enough coins.");
						break;
					}
				}
			}
			c.getItems().resetItems(3823);
			resetShop(c.myShopId);
			updatePlayerShop();
			return true;
		}
		return false;
	}

	public void handleOtherShop(int itemID) {
		if (c.myShopId == 20) {
			if (c.pcPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.pcPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Pandemic Points to buy this item.");
			}
		} else if (c.myShopId == 18) {
			if (c.pcPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.pcPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Pandemic Points to buy this item.");
			}
		} else if (c.myShopId == 54) {
			if (c.votingPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.votingPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Vote Points to buy this item.");
			}
		} else if (c.myShopId == 84) {
			if (c.dungPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.dungPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Dungeoneering Tokens to buy this item.");
			}
		/*} else if (c.myShopId == 118) {
			if (c.getItems().playerHasItem(5021, get2BTicketShopValue(itemID))) {
				if (c.getItems().freeSlots() > 0) {
					c.getItems().deleteItem(5021, get2BTicketShopValue(itemID));
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough 1 Bill Tickets to buy this item.");
			}*/
			} else if (c.myShopId == 120) {
				if (c.getItems().playerHasItem(5021, get1BTicketShopValue(itemID))) {
					if (c.getItems().freeSlots() > 0) {
						c.getItems().deleteItem(5021, get1BTicketShopValue(itemID));
						c.getItems().addItem(itemID, 1);
						c.getItems().resetItems(3823);
					}
				} else {
					c.sendMessage("You do not have enough 1 Bill Tickets to buy this item.");
			}
			} else if (c.myShopId == 117) {
				if (c.getItems().playerHasItem(5021, getSuperSpecialItemSellValue(itemID))) {
					if (c.getItems().freeSlots() > 0) {
						c.getItems().deleteItem(5021, getSuperSpecialItemSellValue(itemID));
						c.getItems().addItem(itemID, 1);
						c.getItems().resetItems(3823);
					} else {
						c.sendMessage("You do not have enough 2b tickets.");
					}
				}
				} else if (c.myShopId == 121) {
					if (c.getItems().playerHasItem(5021, getIronManValue(itemID))) {
						if (c.getItems().freeSlots() > 0) {
							c.getItems().deleteItem(5021, getIronManValue(itemID));
							c.getItems().addItem(itemID, 1);
							c.getItems().resetItems(3823);
						} else {
							c.sendMessage("You do not have enough 1b tickets.");
						}
					}
		} else if (c.myShopId == 85) {
			if (c.PkminiPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.PkminiPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough PK Minigame Points to buy this item.");
			}
		} else if (c.myShopId == 97) {
			if (c.customPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.customPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Custom Points to buy this item.");
			}
		}else if (c.myShopId == 96) {
			if (c.mbPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.mbPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough MageBank Points to buy this item.");
			}
		} else if (c.myShopId == 95) {
			if (c.achievementPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.achievementPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Achievement Points to buy this item.");
			}
		} else if (c.myShopId == 72) {
			if (c.donatorChest >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.donatorChest -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Donator Points to buy this item.");
			}
		} else if (c.myShopId == 122) {
			if (c.donatorChest >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.donatorChest -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Donator Points to buy this item.");
			}
		} else if (c.myShopId == 116) {
			if (c.npcKills >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.npcKills -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough NPC Kills to buy this item.");
			}
		} else if (c.myShopId == 17) {
			if (c.Skillpoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.Skillpoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Skill Points to buy this item.");
			}
	} else if (c.myShopId == 119) {
			if (c.superPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.superPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Mummy Points to buy this item.");
			}
		} else if (c.myShopId == 77) {
			if (c.barrowPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.barrowPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough <col=800000000>Barrows Minigame Points</col> to buy this item.");
			}
		} else if (c.myShopId == 43) {
			if (c.DTPoints >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.DTPoints -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Dominion Tower Points to buy this item.");
			}
		} else if (c.myShopId == 4 || c.myShopId == 5) {
			if (c.PkminiPoints >= getPvpValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.PkminiPoints -= getPvpValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough PvP Points to buy this item.");
			}
		} else if (c.myShopId == 4) {
			if (c.pkPoints >= getPvpValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.pkPoints -= getPvpValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough PvP Points to buy this item.");
			}
		} else if (c.myShopId == 5) {
			if (c.pkPoints >= getPvpValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.pkPoints -= getPvpValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough PvP Points to buy this item.");
			}
		} else if (c.myShopId == 16) {
			if (c.Wheel >= getSpecialItemValue(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.Wheel -= getSpecialItemValue(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Agility Points to buy this item.");
			}

		} else if (c.myShopId == 100) {
			if (c.barbPoints >= getBarb(itemID)) {
				if (c.getItems().freeSlots() > 0) {
					c.barbPoints -= getBarb(itemID);
					c.getItems().addItem(itemID, 1);
					c.getItems().resetItems(3823);
				}
			} else {
				c.sendMessage("You do not have enough Barbarian Assault points to buy this item.");
			}

		}
	else if (c.myShopId == 123) {
	if (c.barbPoints >= getBarb(itemID)) {
		if (c.getItems().freeSlots() > 0) {
			c.barbPoints -= getBarb(itemID);
			c.getItems().addItem(itemID, 1);
			c.getItems().resetItems(3823);
		}
	} else {
		c.sendMessage("You do not have enough Barbarian Assault points to buy this item.");
	}

}
}

	public void openSkillCape() {
		int capes = get99Count();
		if (capes > 1)
			capes = 1;
		else
			capes = 0;
		c.myShopId = 15;
		setupSkillCapes(capes, get99Count());
	}

	public int[] skillCapes = { 9747, 9753, 9750, 9768, 9756, 9759, 9762, 9801,
			9807, 9783, 9798, 9804, 9780, 9795, 9792, 9774, 9771, 9777, 9786,
			9810, 9765, 9948, 12169 };

	public int get99Count() {
		int count = 0;
		for (int j = 0; j < skillCapes.length; j++) {
			if (c.getLevelForXP(c.playerXP[j]) >= 99) {
				count++;
			}
		}
		return count;
	}

	public void setupSkillCapes(int capes, int capes2) {
		synchronized (c) {
			c.getItems().resetItems(3823);
			c.isShopping = true;
			c.myShopId = 14;
			c.getPA().sendFrame248(3824, 3822);
			c.getPA().sendFrame126("Skillcape Shop", 3901);

			int TotalItems = 0;
			TotalItems = capes2;
			if (TotalItems > Server.shopHandler.MaxShopItems) {
				TotalItems = Server.shopHandler.MaxShopItems;
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(3900);
			c.getOutStream().writeWord(TotalItems);
			int TotalCount = 0;
			int off = (capes > 1 ? 2 : 0);
			for (int i = 0; i < skillCapes.length; i++) {
				if (c.getLevelForXP(c.playerXP[i]) < 99)
					continue;
				c.getOutStream().writeByte(1);
				c.getOutStream().writeWordBigEndianA(skillCapes[i] + off); // why
																			// the
																			// +2?
																			// uhh
																			// this
																			// is
																			// just
																			// from
																			// the
																			// original
																			// nrpk?
																			// i
																			// think
				TotalCount++;
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void skillBuy(int item) {
		int nn = get99Count();
		if (nn > 1)
			nn = 1;
		else
			nn = 0;
		for (int j = 0; j < skillCapes.length; j++) {
			if (skillCapes[j] == item || skillCapes[j] + 1 == item) {
				if (c.getItems().freeSlots() > 1) {
					if (c.getItems().playerHasItem(995, 99000)) {
						if (c.getLevelForXP(c.playerXP[j]) >= 99) {
							c.getItems().deleteItem(995,
									c.getItems().getItemSlot(995), 99000);
							c.getItems().addItem(skillCapes[j] + nn, 1);
							c.getItems().addItem(skillCapes[j] + 2, 1);
						} else {
							c.sendMessage("You must have 99 in the skill of the cape you're trying to buy.");
						}
					} else {
						c.sendMessage("You need 99k to buy this item.");
					}
				} else {
					c.sendMessage("You must have at least 1 inventory spaces to buy this item.");
				}
			}
			/*
			 * if (skillCapes[j][1 + nn] == item) { if (c.getItems().freeSlots()
			 * >= 1) { if (c.getItems().playerHasItem(995,99000)) { if
			 * (c.getLevelForXP(c.playerXP[j]) >= 99) {
			 * c.getItems().deleteItem(995, c.getItems().getItemSlot(995),
			 * 99000); c.getItems().addItem(skillCapes[j] + nn,1);
			 * c.getItems().addItem(skillCapes[j] + 2,1); } else {
			 * c.sendMessage(
			 * "You must have 99 in the skill of the cape you're trying to buy."
			 * ); } } else { c.sendMessage("You need 99k to buy this item."); }
			 * } else { c.sendMessage(
			 * "You must have at least 1 inventory spaces to buy this item."); }
			 * break; }
			 */
		}
		c.getItems().resetItems(3823);
	}

	public void openVoid() {
		/*
		 * synchronized(c) { c.getItems().resetItems(3823); c.isShopping = true;
		 * c.myShopId = 15; c.getPA().sendFrame248(3824, 3822);
		 * c.getPA().sendFrame126("Void Recovery", 3901);
		 * 
		 * int TotalItems = 5; c.getOutStream().createFrameVarSizeWord(53);
		 * c.getOutStream().writeWord(3900);
		 * c.getOutStream().writeWord(TotalItems); for (int i = 0; i <
		 * c.voidStatus.length; i++) {
		 * c.getOutStream().writeByte(c.voidStatus[i]);
		 * c.getOutStream().writeWordBigEndianA(2519 + i * 2); }
		 * c.getOutStream().endFrameVarSizeWord(); c.flushOutStream(); }
		 */
	}

	public void buyVoid(int item) {
		/*
		 * if (item > 2527 || item < 2518) return; //c.sendMessage("" + item);
		 * if (c.voidStatus[(item-2518)/2] > 0) { if (c.getItems().freeSlots()
		 * >= 1) { if
		 * (c.getItems().playerHasItem(995,c.getItems().getUntradePrice(item)))
		 * { c.voidStatus[(item-2518)/2]--;
		 * c.getItems().deleteItem(995,c.getItems().getItemSlot(995),
		 * c.getItems().getUntradePrice(item)); c.getItems().addItem(item,1);
		 * openVoid(); } else { c.sendMessage("This item costs " +
		 * c.getItems().getUntradePrice(item) + " coins to rebuy."); } } else {
		 * c.sendMessage("I should have a free inventory space."); } } else {
		 * c.sendMessage
		 * ("I don't need to recover this item from the void knights."); }
		 */
	}

}