package server.model.items;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.util.Misc;
import server.model.players.EarningPotential;

@SuppressWarnings("all")
public class ItemAssistant {

	private Client c;

	public ItemAssistant(Client client) {
		this.c = client;
	}

	/**
	 * Items
	 **/

	public int getCarriedWealth() {
		int toReturn = 0;
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] > 0 && Server.itemHandler.ItemList[c.playerEquipment[i]] != null)
				toReturn += (Server.itemHandler.ItemList[c.playerEquipment[i]].ShopValue * c.playerEquipmentN[i]);
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] > 0 && Server.itemHandler.ItemList[c.playerItems[i]] != null)
				toReturn += (Server.itemHandler.ItemList[c.playerItems[i]].ShopValue * c.playerItemsN[i]);
		}
		return toReturn;

	}

	public boolean updateInventory = false;

	public void updateInventory() {
		updateInventory = false;
		resetItems(3214);
	}

	public int[][] brokenBarrows = { { 4708, 4860 }, { 4710, 4866 }, { 4712, 4872 }, { 4714, 4878 }, { 4716, 4884 },
			{ 4720, 4896 }, { 4718, 4890 }, { 4720, 4896 }, { 4722, 4902 }, { 4732, 4932 }, { 4734, 4938 },
			{ 4736, 4944 }, { 4738, 4950 }, { 4724, 4908 }, { 4726, 4914 }, { 4728, 4920 }, { 4730, 4926 },
			{ 4745, 4956 }, { 4747, 4926 }, { 4749, 4968 }, { 4751, 4794 }, { 4753, 4980 }, { 4755, 4986 },
			{ 4757, 4992 }, { 4759, 4998 } };

	public void resetItems(int WriteFrame) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(WriteFrame);
				c.getOutStream().writeWord(c.playerItems.length);
				for (int i = 0; i < c.playerItems.length; i++) {
					if (c.playerItemsN[i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(c.playerItemsN[i]);
					} else {
						c.getOutStream().writeByte(c.playerItemsN[i]);
					}
					c.getOutStream().writeWordBigEndianA(c.playerItems[i]);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
		}
	}

	public int getEquipmentNet(Client c) {
		int toReturn = 0;
		for (int i = 0; i < c.playerEquipment.length; i++) {
			toReturn += (c.getShops().getItemShopValue(c.playerEquipment[i]) * c.playerEquipmentN[i]);
		}
		return toReturn;
	}

	public void handleEffigies(int skillId) {
		int[] levelReq = { 91, 93, 95, 97 };
		if (System.currentTimeMillis() - c.lastThieve < 5000) {
			c.sendMessage("You really shouldn't be trying to do this. Please try again.");
			c.getPA().removeAllWindows();
			return;
		}
		if (c.getLastClicked() == 18778 && c.playerLevel[skillId] >= levelReq[0]) {
			if (c.getItems().playerHasItem(18778, 1)) {
				c.getItems().deleteItem(18778, 1);
				c.getItems().addItem(18779, 1);
				c.getPA().addSkillXP(15000, skillId);
				c.lastThieve = System.currentTimeMillis();
				c.startAnimation(7368);
				c.getPA().removeAllWindows();
				c.effigy = 0;
			}
		}
		if (c.getLastClicked() == 18779 && c.playerLevel[skillId] >= levelReq[1]) {
			if (c.getItems().playerHasItem(18779, 1)) {
				c.getItems().deleteItem(18779, 1);
				c.getItems().addItem(18780, 1);
				c.getPA().addSkillXP(20000, skillId);
				c.lastThieve = System.currentTimeMillis();
				c.startAnimation(7368);
				c.getPA().removeAllWindows();
				c.effigy = 0;
			}
		}
		if (c.getLastClicked() == 18780 && c.playerLevel[skillId] >= levelReq[2]) {
			if (c.getItems().playerHasItem(18780, 1)) {
				c.getItems().deleteItem(18780, 1);
				c.getItems().addItem(18781, 1);
				c.getPA().addSkillXP(25000, skillId);
				c.lastThieve = System.currentTimeMillis();
				c.startAnimation(7368);
				c.getPA().removeAllWindows();
				c.effigy = 0;
			}
		}
		if (c.getLastClicked() == 18781 && c.playerLevel[skillId] >= levelReq[3]) {
			if (c.getItems().playerHasItem(18781, 1)) {
				c.getItems().deleteItem(18781, 1);
				c.getItems().addItem(18782, 1);
				c.getPA().addSkillXP(30000, skillId);
				c.lastThieve = System.currentTimeMillis();
				c.startAnimation(7368);
				c.getPA().removeAllWindows();
				c.effigy = 0;
			}
		}
	}

	public int getInventoryNet(Client c) {
		int toReturn = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			toReturn += (c.getShops().getItemShopValue(c.playerItems[i] + 1) * c.playerItemsN[i]);
		}
		return toReturn;
	}

	/**
	 * Christmas Event
	 **/
	public boolean hasPlank() {
		return playerHasItem(8794, 1) && playerHasItem(1511, 1);
	}

	public void makePlank() {
		deleteItem(1511, 1);
		addItem(960, 1);
		c.sendMessage("You make a Plank.");
	}

	public boolean hasHandles() {
		return playerHasItem(946, 1) && playerHasItem(960, 1);
	}

	public void makeHandles() {
		deleteItem(960, 1);
		addItem(6864, 1);
		c.sendMessage("You make some Marionette Handles.");
	}

	public boolean hasBlueMari() {
		return playerHasItem(946, 1) && playerHasItem(960, 1);
	}

	public void makeBlueMari() {
		deleteItem(6864, 1);
		deleteItem(6878, 1);
		addItem(6868, 1);
		c.sendMessage("You make a Blue Marionette.");
	}

	public boolean hasRedMari() {
		return playerHasItem(946, 1) && playerHasItem(960, 1);
	}

	public void makeRedMari() {
		deleteItem(6864, 1);
		deleteItem(6874, 1);
		addItem(6870, 1);
		c.sendMessage("You make a Red Marionette.");
	}

	public boolean hasGreenMari() {
		return playerHasItem(946, 1) && playerHasItem(960, 1);
	}

	public void makeGreenMari() {
		deleteItem(6864, 1);
		deleteItem(6882, 1);
		addItem(6869, 1);
		c.sendMessage("You make a Green Marionette.");
	}

	public boolean hasBlueTorso() {
		return playerHasItem(6875, 1);
	}

	public boolean hasRedTorso() {
		return playerHasItem(6871, 1);
	}

	public boolean hasGreenTorso() {
		return playerHasItem(6879, 1);
	}

	public boolean hasBlueHead() {
		return playerHasItem(6876, 1);
	}

	public boolean hasRedHead() {
		return playerHasItem(6872, 1);
	}

	public boolean hasGreenHead() {
		return playerHasItem(6880, 1);
	}

	public boolean hasBlueArms() {
		return playerHasItem(6877, 1);
	}

	public boolean hasRedArms() {
		return playerHasItem(6873, 1);
	}

	public boolean hasGreenArms() {
		return playerHasItem(6881, 1);
	}

	/*
	 * End of Event
	 **/

	public int getBankAmount(int item) {
		int count = 0;
		for (int j = 0; j < c.bankItems.length; j++) {
			if (c.bankItems[j] == item + 1) {
				count += c.bankItemsN[j];
			}
		}
		return count;
	}

	public int getItemCount(int itemID) {
		int count = 0;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (c.playerItems[j] == itemID + 1) {
				count += c.playerItemsN[j];
			}
		}
		return count;
	}

	public void writeBonus() {
		int offset = 0;
		String send = "";
		for (int i = 0; i < c.playerBonus.length; i++) {
			if (c.playerBonus[i] >= 0) {
				send = BONUS_NAMES[i] + ": +" + c.playerBonus[i];
			} else {
				send = BONUS_NAMES[i] + ": -" + java.lang.Math.abs(c.playerBonus[i]);
			}

			if (i == 10) {
				offset = 1;
			}
			c.getPA().sendFrame126(send, (1675 + i + offset));
		}

	}

	public int getTotalCount(int itemID) {
		int count = 0;
		for (int j = 0; j < c.playerItems.length; j++) {
			if (Item.itemIsNote[itemID + 1]) {
				if (itemID + 2 == c.playerItems[j])
					count += c.playerItemsN[j];
			}
			if (!Item.itemIsNote[itemID + 1]) {
				if (itemID + 1 == c.playerItems[j]) {
					count += c.playerItemsN[j];
				}
			}
		}
		for (int j = 0; j < c.bankItems.length; j++) {
			if (c.bankItems[j] == itemID + 1) {
				count += c.bankItemsN[j];
			}
		}
		return count;
	}

	public void sendItemsKept() {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(53);
				c.getOutStream().writeWord(6963);
				c.getOutStream().writeWord(c.itemKeptId.length);
				for (int i = 0; i < c.itemKeptId.length; i++) {
					if (c.playerItemsN[i] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord_v2(1);
					} else {
						c.getOutStream().writeByte(1);
					}
					if (c.itemKeptId[i] > 0) {
						c.getOutStream().writeWordBigEndianA(c.itemKeptId[i] + 1);
					} else {
						c.getOutStream().writeWordBigEndianA(0);
					}
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
		}
	}

	/**
	 * Item kept on death
	 **/

	public void keepItem(int keepItem, boolean deleteItem) {
		int value = 0;
		int item = 0;
		int slotId = 0;
		boolean itemInInventory = false;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] - 1 > 0) {
				int inventoryItemValue = c.getShops().getItemShopValue(c.playerItems[i] - 1);
				if (inventoryItemValue > value && (!c.invSlot[i])) {
					value = inventoryItemValue;
					item = c.playerItems[i] - 1;
					slotId = i;
					itemInInventory = true;
				}
			}
		}
		for (int i1 = 0; i1 < c.playerEquipment.length; i1++) {
			if (c.playerEquipment[i1] > 0) {
				int equipmentItemValue = c.getShops().getItemShopValue(c.playerEquipment[i1]);
				if (equipmentItemValue > value && (!c.equipSlot[i1])) {
					value = equipmentItemValue;
					item = c.playerEquipment[i1];
					slotId = i1;
					itemInInventory = false;
				}
			}
		}
		if (itemInInventory) {
			c.invSlot[slotId] = true;
			if (deleteItem) {
				deleteItem(c.playerItems[slotId] - 1, getItemSlot(c.playerItems[slotId] - 1), 1);
			}
		} else {
			c.equipSlot[slotId] = true;
			if (deleteItem) {
				deleteEquipment(item, slotId);
			}
		}
		c.itemKeptId[keepItem] = item;
	}

	/**
	 * Reset items kept on death
	 **/

	public void resetKeepItems() {
		for (int i = 0; i < c.itemKeptId.length; i++) {
			c.itemKeptId[i] = -1;
		}
		for (int i1 = 0; i1 < c.invSlot.length; i1++) {
			c.invSlot[i1] = false;
		}
		for (int i2 = 0; i2 < c.equipSlot.length; i2++) {
			c.equipSlot[i2] = false;
		}
	}

	/**
	 * delete all items
	 **/

	public void deleteAllItems() {
		for (int i1 = 0; i1 < c.playerEquipment.length; i1++) {
			deleteEquipment(c.playerEquipment[i1], i1);
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			deleteItem(c.playerItems[i] - 1, getItemSlot(c.playerItems[i] - 1), c.playerItemsN[i]);
		}
	}

	/**
	 * Drop all items for your killer
	 **/

	public void dropAllItems() {
		
		Client o = (Client) Server.playerHandler.players[c.killerId];
		if (c.playerRights == 3/* || c.isInArd() || c.isInFala() */) {
			return;
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			if (o != null) {
				if (tradeable(c.playerItems[i] - 1)) {
					Server.itemHandler.createGroundItem(o, c.playerItems[i] - 1, c.getX(), c.getY(), c.playerItemsN[i],
							c.killerId);
				} else {
					if (specialCase(c.playerItems[i] - 1))
						Server.itemHandler.createGroundItem(o, 995, c.getX(), c.getY(),
								getUntradePrice(c.playerItems[i] - 1), c.killerId);
					Server.itemHandler.createGroundItem(c, c.playerItems[i] - 1, c.getX(), c.getY(), c.playerItemsN[i],
							c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.playerItems[i] - 1, c.getX(), c.getY(), c.playerItemsN[i],
						c.playerId);
			}
		}
		for (int e = 0; e < c.playerEquipment.length; e++) {
			if (o != null) {
				if (tradeable(c.playerEquipment[e])) {
					Server.itemHandler.createGroundItem(o, c.playerEquipment[e], c.getX(), c.getY(),
							c.playerEquipmentN[e], c.killerId);
				} else {
					if (specialCase(c.playerEquipment[e]))
						Server.itemHandler.createGroundItem(o, 995, c.getX(), c.getY(),
								getUntradePrice(c.playerEquipment[e]), c.killerId);
					Server.itemHandler.createGroundItem(c, c.playerEquipment[e], c.getX(), c.getY(),
							c.playerEquipmentN[e], c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.playerEquipment[e], c.getX(), c.getY(), c.playerEquipmentN[e],
						c.playerId);
			}
		}
		if (o != null) {
			Server.itemHandler.createGroundItem(o, 526, c.getX(), c.getY(), 1, c.killerId);
		}
	}

	public void dropAllItemsPVP() {
		Client o = (Client) Server.playerHandler.players[c.killerId];
		EarningPotential.giveBonusDrops(o, c);
	}

	public int getUntradePrice(int item) {
		switch (item) {
		case 2518:
		case 2524:
		case 2526:
			return 100000;
		case 2520:
		case 2522:
			return 150000;
		}
		return 0;
	}

	public int customItem(int item) {
		switch (item) {
		case 14921:
		case 4762:
		case 4764:
			case 14445:
			case 11307:
			case 11317:

			case 11318:
			case 11319:
			case 11312:
			return 200;

			case 11615:
			case 11616:
			case 11614:
			case 10708:
			case 19266:
			case 19263:
			case 10705:
			case 19203:
			case 19200:
			case 15038:
			case 15037:
			case 19943:
			case 19112:
			case 19113:
			case 19114:
			case 19036:
			case 19037:
			case 19038:
			case 19041:
			case 19040:
			case 20155:
			case 19827:
			case 17865:
			case 4767:
			case 3807:
			case 20102:
			case 20106:
			case 20114:
			case 20100:
			case 20104:
			case 20108:
			case 20112:
			case 20103:
			case 20107:
			case 20111:
			case 20115:
			case 20101:
			case 20105:
			case 20109:
			case 20113:
			case 4763:
			case 11629:
			case 20141:
			case 11349:
			case 4064:
			case 11653:
			case 11654:
			case 11651:
			case 4633:
			case 11638:
			case 11147:
			case 20082:
			case 20083:
			case 20084:
			case 19960:
			case 979:
			case 4630:
			case 4631:
			case 11596:
			case 4647:
			case 4648:
			case  11149:
			case 11148:
			case 19959:
			case 20085:
			case 20081:
			case 20080:
			case 20087:
			case 4649:
			case 20086:
			case 20089:
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
			 

			case 19952:
			 

			case 19931:
			 

			case 19961:
			 

			case 19953:
			return 25;

			case 18382:
			 

			case 62771:
			 

			case 18384:
			 

			case 4638:
			return 70;

			case 19951:
			 

			case 3273:
			 

			case 3275:
			 

			case 3277:
			 

			case 3279:
			 

			case 3281:
			 

			case 3283:
			 

			//case 3285:
			// 

			//case 3287:
			// 

			//case 3289:
			// 

			case 3291:
			return 500;

			case 3293:
			 

			case 3295:
			 

			case 3297:
			 

			case 3299:
			 

			case 3305:
			 

			case 3307:
			 

			case 3309:
			 

			case 3311:
			 

			case 3313:
			 

			case 3315:
			 

			case 3317:
			 

			case 3319:
			 

			case 3321:
			 

			case 3323:
			 

			case 13495:
			return 25;

			case 13492:
			return 25;

			case 13493:
			return 25;

			case 4639:
			 

			case 4641:
			return 40;

			case 3809:
			return 25;

			case 18743:
			 

			case 4804:
			return 25;

			case 4805:
			return 25;

			case 4806:
			return 25;

			case 13221:
			 

			case 13225:
			 

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
			 

			case 13998:
			 

			case 13999:
			 

			case 14000:
			 

			case 14001:
			 

			case 14002:
			 

			case 14003:
			 

			case 14004:
			 

			case 14005:
			 

			case 14006:
			 

			case 14007:
			return 500;

			case 14008:
			 

			case 14009:
			 

			case 14010:
			 

			case 14011:
			 

			case 9899:
			return 300;

			case 14390:
			 

			case 11662:
			return 200;

			case 11660:
			return 200;

			case 20250:
			return 200;

			case 20251:
			 

			case 20252:
			 

			case 20253:
			 

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
			 

			case 12275:
			 

			case 12276:
			 

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
			 

			case 4651:
			 

			case 3811:
			 

			case 3813:
			 

			case 4652:
			 

			case 19936:
			 

			case 11626:
			 

			case 11593:
			 

			case 11592:
			 

			case 11591:
			 

			case 11590:
			 

			case 11627:
			 

			case 9898:
			 

			case 11142:
			return 15;

			case 18362:
			 

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
			 

			case 11146:
			return 15;

			case 19933:
			 

			case 9374:
			 

			case 9897:
			 

			case 19930:
			return 200;

			case 9895:
			 

			case 18785:
			 

			case 18784:
			 

			case 12310:
			 

			case 3890:
			 

			case 3821:
			return 200;

			case 19558:
			 

			case 9396:
			 

			case 15017:
			 

			case 5023:
			 

			case 962:
			 

			case 4083:
			 

			case 19913:
			 

			case 13362:
			 

			case 20254:
			 

			case 19929:
			 

			case 938:
			 

			case 13197:
			 

			case 4642:
			 

			case 918:
			 

			case 4646:
			 

			case 19958:
			 

			case 19916:
			 

			case 19957:
			return 200;

			case 19956:
			 

			case 13840:
			return 300;

			case 18388:
			 

			case 4058:
			 

			case 9992:
			 

			case 4643:
			 

			case 4644:
			 

			case 19753:
			 

			case 968:
			 

			case 930:
			 

			case 919:
			 

			case 19915:
			 

			case 13360:
			 

			case 11724:
			return 200;

			case 11726:
			return 200;

			case 933:
			 

			case 934:
			 

			case 4056:
			 

			case 1000:
			 

			case 11728:
			return 20;

			case 9719:
			 

			case 3823:
			 

			case 13358:
			 

			case 13355:
			 

			case 13354:
			 

			case 13352:
			 

			case 13350:
			 

			case 13347:
			return 500;

			case 4063:
			 

			case 17837:
			 

			case 17881:
			 

			case 13348:
			 

			case 13346:
			 

			case 13344:
			 

			case 13342:
			 

			case 13340:
			 

			case 13370:
			 

			case 7783:
			 

			case 11631:
			return 1000;

			case 3886:
			 

			case 3825:
			 

			case 3884:
			 

			case 11607:
			 

			case 11608:
			 

			case 11609:
			 

			case 11610:
			 

			case 11611:
			 

			case 11612:
			 

			case 11634:
			 

			case 4060:
			 

			case 4061:
			 

			case 13356:
			 

			case 5200:
			 

			case 5201:
			 

			case 5211:
			 

			case 5212:
			 

			case 5203:
			return 500;

			case 16481:
			 

			case 898:
			 

			case 6499:
			 

			case 900:
			 

			case 6196:
			return 200;

			case 6189:
			 

			case 6190:
			 

			case 6191:
			 

			case 6192:
			 

			case 6193:
			 

			case 5204:
			 

			case 5205:
			 

			case 5206:
			 

			case 5222:
			 

			case 5213:
			 

			case 5214:
			 

			case 5215:
			 

			case 5216:
			 

			case 5217:
			 

			case 5218:
			 

			case 12241:
			 

			case 12242:
			 

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
			 

			case 5186:
			 

			case 5187:
			 

			case 5188:
			 

			case 5189:
			 

			case 5190:
			 

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
			 

			case 12234:
			 

			case 12235:
			 

			case 12236:
			 

			case 12237:
			 

			case 12238:
			 

			case 12225:
			 

			case 12227:
			 

			case 12228:

			case 12229:

			case 12230:

			case 12231:

			case 12232:
		}
		return 0;
	}

	public boolean specialCase(int itemId) {
		switch (itemId) {
		case 2518:
		case 2520:
		case 2522:
		case 2524:
		case 2526:
			return true;
		}
		return false;
	}

	public void handleSpecialPickup(int itemId) {
		// c.sendMessage("My " + getItemName(itemId) + " has been recovered. I
		// should talk to the void knights to get it back.");
		// c.getItems().addToVoidList(itemId);
	}

	public void addToVoidList(int itemId) {
		switch (itemId) {
		case 2518:
			c.voidStatus[0]++;
			break;
		case 2520:
			c.voidStatus[1]++;
			break;
		case 2522:
			c.voidStatus[2]++;
			break;
		case 2524:
			c.voidStatus[3]++;
			break;
		case 2526:
			c.voidStatus[4]++;
			break;
		}
	}

	public boolean tradeable(int itemId) {
		for (int j = 0; j < Config.ITEM_TRADEABLE.length; j++) {
			if (itemId == Config.ITEM_TRADEABLE[j])
				return false;
		}
		return true;
	}

	/**
	 * Adds an item to the bank without checking if the player has it in there
	 * inventory
	 * 
	 * @param itemId
	 *            the id of the item were banking
	 * @param amount
	 *            amount of items to bank
	 */
	public void addItemToBank(int itemId, int amount) {
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] <= 0 || c.bankItems[i] == itemId + 1 && c.bankItemsN[i] + amount < Integer.MAX_VALUE) {
				c.bankItems[i] = itemId + 1;
				c.bankItemsN[i] += amount;
				resetBank();
				return;
			}
		}
	}

	/**
	 * Add Item
	 **/
	public boolean addItem(int item, int amount) {
		synchronized (c) {
			if (amount < 1) {
				amount = 1;
			}
			if (item <= 0) {
				return false;
			}
			if ((((freeSlots() >= 1) || playerHasItem(item, 1)) && Item.itemStackable[item])
					|| ((freeSlots() > 0) && !Item.itemStackable[item])) {
				for (int i = 0; i < c.playerItems.length; i++) {
					if ((c.playerItems[i] == (item + 1)) && Item.itemStackable[item] && (c.playerItems[i] > 0)) {
						c.playerItems[i] = (item + 1);
						if (((c.playerItemsN[i] + amount) < Config.MAXITEM_AMOUNT)
								&& ((c.playerItemsN[i] + amount) > -1)) {
							c.playerItemsN[i] += amount;
						} else {
							c.playerItemsN[i] = Config.MAXITEM_AMOUNT;
						}
						/*
						 * if(c.getOutStream() != null && c != null ) {
						 * c.getOutStream().createFrameVarSizeWord(34);
						 * c.getOutStream().writeWord(3214);
						 * c.getOutStream().writeByte(i);
						 * c.getOutStream().writeWord(c.playerItems[i]); if
						 * (c.playerItemsN[i] > 254) {
						 * c.getOutStream().writeByte(255);
						 * c.getOutStream().writeDWord(c.playerItemsN[i]); }
						 * else { c.getOutStream().writeByte(c.playerItemsN[i]);
						 * } c.getOutStream().endFrameVarSizeWord();
						 * c.flushOutStream(); }
						 */
						updateInventory = true;
						updateInventory();
						i = 30;
						return true;
					}
				}
				for (int i = 0; i < c.playerItems.length; i++) {
					if (c.playerItems[i] <= 0) {
						c.playerItems[i] = item + 1;
						if ((amount < Config.MAXITEM_AMOUNT) && (amount > -1)) {
							c.playerItemsN[i] = 1;
							if (amount > 1) {
								c.getItems().addItem(item, amount - 1);
								return true;
							}
						} else {
							c.playerItemsN[i] = Config.MAXITEM_AMOUNT;
						}
						/*
						 * if(c.getOutStream() != null && c != null ) {
						 * c.getOutStream().createFrameVarSizeWord(34);
						 * c.getOutStream().writeWord(3214);
						 * c.getOutStream().writeByte(i);
						 * c.getOutStream().writeWord(c.playerItems[i]); if
						 * (c.playerItemsN[i] > 254) {
						 * c.getOutStream().writeByte(255);
						 * c.getOutStream().writeDWord(c.playerItemsN[i]); }
						 * else { c.getOutStream().writeByte(c.playerItemsN[i]);
						 * } c.getOutStream().endFrameVarSizeWord();
						 * c.flushOutStream(); }
						 */
						// resetItems(3214);
						updateInventory = true;
						updateInventory();
						i = 30;
						return true;
					}
				}
				return false;
			} else {
				updateInventory = true;
				updateInventory();
				c.sendMessage("Not enough space in your inventory.");
				return false;
			}
		}
	}

	public String itemType(int item) {
		if (Item.playerCape(item)) {
			return "cape";
		}
		if (Item.playerBoots(item)) {
			return "boots";
		}
		if (Item.playerGloves(item)) {
			return "gloves";
		}
		if (Item.playerShield(item)) {
			return "shield";
		}
		if (Item.playerAmulet(item)) {
			return "amulet";
		}
		if (Item.playerArrows(item)) {
			return "arrows";
		}
		if (Item.playerRings(item)) {
			return "ring";
		}
		if (Item.playerHats(item)) {
			return "hat";
		}
		if (Item.playerLegs(item)) {
			return "legs";
		}
		if (Item.playerBody(item)) {
			return "body";
		}
		if (item == 19474) {
			return "legs";
		}
		if (item == 11626) {
			return "cape";
		}
		if (item == 12259) {
			return "legs";
		}
		if (item == 12268) {
			return "legs";
		}
		if (item == 12262) {
			return "legs";
		}
		if (item == 12269) {
			return "hat";
		}
		if (item == 12265) {
			return "legs";
		}
		if (item == 12270) {
			return "body";
		}
		if (item == 12272) {
			return "hat";
		}
		if (item == 12277) {
			return "legs";
		}
		if (item == 12279) {
			return "body";
		}
		if (item == 12278) {
			return "hat";
		}
		if (item == 12280) {
			return "legs";
		}
		if (item == 4762) {
			return "body";
		}
		if (item == 4763) {
			return "legs";
		}
		if (item == 12282) {
			return "body";
		}
		if (item == 12281) {
			return "hat";
		}
		if (item == 12283) {
			return "legs";
		}
		if (item == 12285) {
			return "body";
		}
		if (item == 12284) {
			return "hat";
		}
		if (item == 12286) {
			return "legs";
		}
		if (item == 12288) {
			return "body";
		}
		if (item == 12287) {
			return "hat";
		}
		if (item == 12289) {
			return "legs";
		}
		if (item == 12291) {
			return "body";
		}
		if (item == 12290) {
			return "hat";
		}
		if (item == 12271) {
			return "legs";
		}
		if (item == 12273) {
			return "body";
		}
		if (item == 12275) {
			return "hat";
		}
		if (item == 12274) {
			return "legs";
		}
		if (item == 12276) {
			return "body";
		}
		if (item == 12256) {
			return "legs";
		}
		if (item == 12257) {
			return "hat";
		}
		if (item == 12258) {
			return "body";
		}
		if (item == 12260) {
			return "hat";
		}
		if (item == 12263) {
			return "hat";
		}
		if (item == 12266) {
			return "hat";
		}
		if (item == 18377) {
			return "cape";
		}
		if (item == 11627) {
			return "cape";
		}
		if (item == 1) {
			return "cape";
		}
		if (item == 11590) {
			return "cape";
		}
		if (item == 12323) {
			return "legs";
		}
		if (item == 12325) {
			return "hat";
		}
		if (item == 12327) {
			return "body";
		}
		if (item == 17865) {
			return "cape";
		}
		if (item == 19918) {
			return "cape";
		}
		if (item == 3811) {
			return "cape";
		}
		if (item == 3813) {
			return "cape";
		}
		if (item == 19960) {
			return "cape";
		}
		if (item == 13412) {
			return "body";
		}
		if (item == 12267) {
			return "body";
		}
		if (item == 12264) {
			return "body";
		}
		if (item == 12261) {
			return "body";
		}

		if (item == 4630) {
			return "cape";
		}
		if (item == 15445) {
			return "shield";
		}
		if (item == 15445) {
			return "shield";
		}
		if (item == 19986) {
			return "shield";
		}
		if (item == 15447) {
			return "shield";
		}
		if (item == 15448) {
			return "shield";
		}
		if (item == 15449) {
			return "shield";
		}
		if (item == 4649) {
			return "hat";
		}
		if (item == 4631) {
			return "hat";
		}
		if (item == 4650) {
			return "cape";
		}
		if (item == 4651) {
			return "cape";
		}
		if (item == 4652) {
			return "cape";
		}

		if (item == 11591) {
			return "cape";
		}
		if (item == 14005) {
			return "legs";
		}
		if (item == 11592) {
			return "cape";
		}
		if (item == 11593) {
			return "cape";
		}
		if (item == 19957) {
			return "boots";
		}
		if (item == 19929) {
			return "hat";
		}
		if (item == 1052) {
			return "cape";
		}
		if (item == 939) {
			return "cape";
		}
		if (item == 9374) {
			return "cape";
		}
		if (item == 19936) {
			return "cape";
		}
		if (item == 19712) {
			return "shield";
		}
		if (item == 16711) {
			return "hat";
		}
		if (item == 9396) {
			return "hat";
		}
		if (item == 20096) {
			return "hat";
		}
		if (item == 20094) {
			return "hat";
		}
		if (item == 18510) {
			return "hat";
		}

		// afros

		if (item == 14743) {
			return "hat";
		}
		if (item == 14745) {
			return "hat";
		}
		if (item == 14747) {
			return "hat";
		}
		if (item == 14748) {
			return "hat";
		}
		if (item == 14751) {
			return "hat";
		}
		if (item == 14753) {
			return "hat";
		}
		if (item == 14755) {
			return "hat";
		}
		if (item == 14757) {
			return "hat";
		}
		if (item == 14759) {
			return "hat";
		}
		if (item == 14761) {
			return "hat";
		}
		if (item == 14763) {
			return "hat";
		}
		if (item == 14765) {
			return "hat";
		}
		if (item == 14767) {
			return "hat";
		}
		if (item == 14769) {
			return "hat";
		}
		if (item == 14771) {
			return "hat";
		}
		if (item == 14773) {
			return "hat";
		}
		if (item == 14775) {
			return "hat";
		}
		if (item == 14777) {
			return "hat";
		}
		if (item == 14779) {
			return "hat";
		}
		if (item == 14781) {
			return "hat";
		}
		if (item == 14783) {
			return "hat";
		}
		if (item == 14785) {
			return "hat";
		}
		if (item == 14787) {
			return "hat";
		}
		if (item == 14789) {
			return "hat";
		}

		// afroend

		if (item == 19951) {
			return "shield";
		}
		if (item == 19952) {
			return "shield";
		}
		if (item == 19953) {
			return "shield";
		}
		if (item == 19954) {
			return "cape";
		}
		if (item == 19955) {
			return "cape";
		}

		if (item == 20095) {
			return "hat";
		}
		if (item == 20097) {
			return "hat";
		}
		if (item == 20091) {
			return "hat";
		}
		if (item == 20092) {
			return "hat";
		}
		if (item == 20093) {
			return "hat";
		}
		if (item == 13824) {
			return "hat";
		}
		if (item == 19747) {
			return "hat";
		}
		if (item == 16733) {
			return "body";
		}
		if (item == 17259) {
			return "body";
		}
		if (item == 19958) {
			return "boots";
		}
		if (item == 14791) {
			return "hat";
		}

		if (item == 20090) {
			return "hat";
		}
		if (item == 20087) {
			return "hat";
		}
		if (item == 20088) {
			return "hat";
		}
		if (item == 16689) {
			return "legs";
		}
		if (item == 11118) {
			return "ring";
		}
		if (item == 15117) {
			return "cape";
		}
		if (item == 9419) {
			return "Arrows";
		}
		if (item == 15120) {
			return "cape";
		}
		if (item == 15121) {
			return "cape";
		}
		if (item == 15118) {
			return "hat";
		}
		if (item == 15119) {
			return "hat";
		}
		if (item == 4764) {
			return "hat";
		}
		if (item == 15124) {
			return "hat";
		}

		if (item == 11119) {
			return "ring";
		}
		if (item == 11120) {
			return "ring";
		}
		if (item == 11121) {
			return "ring";
		}
		if (item == 11122) {
			return "ring";
		}
		if (item == 11123) {
			return "ring";
		}
		if (item == 11124) {
			return "ring";
		}
		if (item == 11125) {
			return "ring";
		}
		if (item == 11126) {
			return "ring";
		}
		if (item == 11127) {
			return "ring";
		}
		if (item == 16667) {
			return "legs";
		}
		if (item == 17361) {
			return "shield";
		}
		if (item == 16359) {
			return "boots";
		}
		if (item == 4765) {
			return "gloves";
		}
		if (item == 16293) {
			return "gloves";
		}
		if (item == 20116) {
			return "body";
		}
		return "weapon";
	}

	/**
	 * Bonuses
	 **/

	public final String[] BONUS_NAMES = { "Stab", "Slash", "Crush", "Magic", "Range", "Stab", "Slash", "Crush", "Magic",
			"Range", "Strength", "Prayer" };

	public void resetBonus() {
		for (int i = 0; i < c.playerBonus.length; i++) {
			c.playerBonus[i] = 0;
		}
	}

	public void getBonus() {
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] > -1) {
				for (int j = 0; j < Config.ITEM_LIMIT; j++) {
					if (Server.itemHandler.ItemList[j] != null) {
						if (Server.itemHandler.ItemList[j].itemId == c.playerEquipment[i]) {
							for (int k = 0; k < c.playerBonus.length; k++) {
								c.playerBonus[k] += Server.itemHandler.ItemList[j].Bonuses[k];
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Wear Item
	 **/

	public void sendWeapon(int Weapon, String WeaponName) {
		String WeaponName2 = WeaponName.replaceAll("Bronze", "");
		WeaponName2 = WeaponName2.replaceAll("Iron", "");
		WeaponName2 = WeaponName2.replaceAll("Steel", "");
		WeaponName2 = WeaponName2.replaceAll("Black", "");
		WeaponName2 = WeaponName2.replaceAll("Mithril", "");
		WeaponName2 = WeaponName2.replaceAll("Adamant", "");
		WeaponName2 = WeaponName2.replaceAll("Rune", "");
		WeaponName2 = WeaponName2.replaceAll("Granite", "");
		WeaponName2 = WeaponName2.replaceAll("Dragon", "");
		WeaponName2 = WeaponName2.replaceAll("Drag", "");
		WeaponName2 = WeaponName2.replaceAll("Crystal", "");
		WeaponName2 = WeaponName2.trim();
		if (WeaponName.equals("Unarmed")) {
			c.setSidebarInterface(0, 5855); // punch, kick, block
			c.getPA().sendFrame126(WeaponName, 5857);
		} else if (WeaponName.endsWith("whip")) {
			c.setSidebarInterface(0, 12290); // flick, lash, deflect
			c.getPA().sendFrame246(12291, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 12293);
		} else if (WeaponName2.toLowerCase().contains("maul") || WeaponName.endsWith("warhammer")) {
			c.setSidebarInterface(0, 425); // war hamer equip.
			c.getPA().sendFrame246(426, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 428);
		} else if (WeaponName.endsWith("bow") || WeaponName.endsWith("10") || c.playerEquipment[c.playerWeapon] == 13879
				|| c.playerEquipment[c.playerWeapon] == 11639 || c.playerEquipment[c.playerWeapon] == 15241
				|| c.playerEquipment[c.playerWeapon] == 18357 || c.playerEquipment[c.playerWeapon] == 13880
				|| c.playerEquipment[c.playerWeapon] == 13881 || c.playerEquipment[c.playerWeapon] == 13882
				|| c.playerEquipment[c.playerWeapon] == 13883 || WeaponName.endsWith("full")
				|| WeaponName.startsWith("seercull")) {
			c.setSidebarInterface(0, 1764); // accurate, rapid, longrange
			c.getPA().sendFrame246(1765, 200, Weapon);
			// c.getPA().sendFrame126(WeaponName, 1767);
		} else if (WeaponName2.startsWith("dagger") || c.playerEquipment[c.playerWeapon] == 13905
				|| c.playerEquipment[c.playerWeapon] == 13899 || WeaponName2.contains("Staff of light")
				|| c.playerEquipment[c.playerWeapon] == 18349 || c.playerEquipment[c.playerWeapon] == 10887
				|| WeaponName2.contains("sword")) {
			c.setSidebarInterface(0, 2276); // stab, lunge, slash, block
			c.getPA().sendFrame246(2277, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 2279);
		} else if (WeaponName.startsWith("Staff") || WeaponName.endsWith("staff") || WeaponName.endsWith("wand")) {
			c.setSidebarInterface(0, 328); // spike, impale, smash, block
			c.getPA().sendFrame246(329, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 331);
		} else if (WeaponName2.startsWith("dart") || WeaponName2.startsWith("knife")
				|| WeaponName2.startsWith("javelin") || WeaponName.equalsIgnoreCase("toktz-xil-ul")) {
			c.setSidebarInterface(0, 4446); // accurate, rapid, longrange
			c.getPA().sendFrame246(4447, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 4449);
		} else if (WeaponName2.startsWith("pickaxe")) {
			c.setSidebarInterface(0, 5570); // spike, impale, smash, block
			c.getPA().sendFrame246(5571, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 5573);
		} else if (WeaponName2.startsWith("axe") || WeaponName2.startsWith("battleaxe")) {
			c.setSidebarInterface(0, 1698); // chop, hack, smash, block
			c.getPA().sendFrame246(1699, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 1701);
		} else if (WeaponName2.startsWith("halberd")) {
			c.setSidebarInterface(0, 8460); // jab, swipe, fend
			c.getPA().sendFrame246(8461, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 8463);
		} else if (c.playerEquipment[c.playerWeapon] == 14484) {
			c.setSidebarInterface(0, 7762); // claws case 20120:
			c.getPA().sendFrame246(7763, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 7765);
		} else if (c.playerEquipment[c.playerWeapon] == 11651 || c.playerEquipment[c.playerWeapon] == 11652 || 
				c.playerEquipment[c.playerWeapon] == 11653 || c.playerEquipment[c.playerWeapon] == 11654) {
			c.setSidebarInterface(0, 7762); // claws case 20120:
			c.getPA().sendFrame246(7763, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 7765);
		} else if (c.playerEquipment[c.playerWeapon] == 17861) {
			c.setSidebarInterface(0, 7762); // Greatness
			c.getPA().sendFrame246(7763, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 7765);
		} else if (c.playerEquipment[c.playerWeapon] == 9899 || c.playerEquipment[c.playerWeapon] == 9900) {
			c.setSidebarInterface(0, 7762); // Beastly Sword
			c.getPA().sendFrame246(7763, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 7765);
		} else if (c.playerEquipment[c.playerWeapon] == 17416) {
			c.setSidebarInterface(0, 7762); // Upgraded Drygore rapier
			c.getPA().sendFrame246(7763, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 7765);
		} else if (c.playerEquipment[c.playerWeapon] == 13444) {
			c.setSidebarInterface(0, 12290); // flick, lash, deflect abyssal tentacle
			c.getPA().sendFrame246(12291, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 12293);
		} else if (c.playerEquipment[c.playerWeapon] == 20120) {
			c.setSidebarInterface(0, 7762); // claws case 20120:
			c.getPA().sendFrame246(7763, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 7765);
		} else if (c.playerEquipment[c.playerWeapon] == 13347) {
			c.setSidebarInterface(0, 7762); // claws
			c.getPA().sendFrame246(7763, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 7765);
		} else if (c.playerEquipment[c.playerWeapon] == 4063) {
			c.setSidebarInterface(0, 7762); // claws
			c.getPA().sendFrame246(7763, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 7765);
		} else if (c.playerEquipment[c.playerWeapon] == 938) {
			c.setSidebarInterface(0, 7762); // claws
			c.getPA().sendFrame246(7763, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 7765);
		} else if (WeaponName2.startsWith("scythe")) {
			c.setSidebarInterface(0, 8460); // jab, swipe, fend
			c.getPA().sendFrame246(8461, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 8463);
		} else if (WeaponName2.startsWith("spear")) {
			c.setSidebarInterface(0, 4679); // lunge, swipe, pound, block
			c.getPA().sendFrame246(4680, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 4682);
		} else if (WeaponName2.toLowerCase().contains("mace") || c.playerEquipment[c.playerWeapon] == 13902) {
			c.setSidebarInterface(0, 3796);
			c.getPA().sendFrame246(3797, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 3799);
		} else if (c.playerEquipment[c.playerWeapon] == 4153) {
			c.setSidebarInterface(0, 425); // war hamer equip.
			c.getPA().sendFrame246(426, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 428);
		} else if (c.playerEquipment[c.playerWeapon] == 18351) {
			c.setSidebarInterface(0, 2423); // war hamer equip.
			c.getPA().sendFrame246(426, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 428);
		} else {
			c.setSidebarInterface(0, 2423); // chop, slash, lunge, block
			c.getPA().sendFrame246(2424, 200, Weapon);
			c.getPA().sendFrame126(WeaponName, 2426);
		}

	}

	/**
	 * Weapon Requirements
	 **/

	public void getRequirements(String itemName, int itemId) {
		c.attackLevelReq = c.defenceLevelReq = c.strengthLevelReq = c.rangeLevelReq = c.slayerLevelReq = c.dungLevelReq = c.magicLevelReq = c.Donatorreq = 0;
		if (itemName.contains("mystic") || itemName.contains("nchanted")) {
			if (itemName.contains("staff of light")) {
				c.magicLevelReq = 75;
				c.attackLevelReq = 75;
			}
			if (itemName.contains("staff")) {
				c.magicLevelReq = 20;
				c.attackLevelReq = 40;
			} else {
				c.magicLevelReq = 20;
				c.defenceLevelReq = 20;
			}
		}

		if (itemName.contains("infinity")) {
			c.magicLevelReq = 50;
			c.defenceLevelReq = 25;
		}
		if (itemName.contains("splitbark")) {
			c.magicLevelReq = 40;
			c.defenceLevelReq = 40;
		}
		if (itemName.contains("rune c'bow")) {
			c.rangeLevelReq = 61;
		}
		if (itemName.contains("black d'hide")) {
			c.rangeLevelReq = 70;
		}
		if (itemName.contains("slayer helmet")) {
			c.defenceLevelReq = 35;
		}
		if (itemName.contains("falador sh")) {
			c.defenceLevelReq = 30;
		}
		if (itemName.contains("gilded")) {
			c.defenceLevelReq = 40;
		}
		if (itemName.contains("third-age")) {
			c.defenceLevelReq = 40;
		}
		if (itemName.contains("tzhaar-ket-om")) {
			c.strengthLevelReq = 60;
		}
		if (itemName.contains("red d'hide")) {
			c.rangeLevelReq = 60;
		}
		if (itemName.contains("blue d'hide")) {
			c.rangeLevelReq = 50;
		}
		if (itemName.contains("green d'hide")) {
			c.rangeLevelReq = 40;
		}
		if (itemName.contains("snakeskin")) {
			c.rangeLevelReq = 40;
			c.defenceLevelReq = 30;
		}
		if (itemName.contains("initiate")) {
			c.defenceLevelReq = 20;
		}
		if (itemName.contains("bronze")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("iron")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 1;
			}
			return;
		}
		if (itemName.contains("steel")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 5;
			}
			return;
		}
		if (itemName.contains("mithril")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 20;
			}
			return;
		}
		if (itemName.contains("adamant")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe")) {
				c.attackLevelReq = c.defenceLevelReq = 30;
			}
			return;
		}
		if (itemName.contains("rune")) {
			if (!itemName.contains("knife") && !itemName.contains("dart") && !itemName.contains("javelin")
					&& !itemName.contains("thrownaxe") && !itemName.contains("'bow")) {
				c.attackLevelReq = c.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("granite shield")) {
			if (!itemName.contains("maul")) {
				c.defenceLevelReq = 50;
			}
			return;
		}
		if (itemName.contains("granite maul")) {
			if (!itemName.contains("shield")) {
				c.attackLevelReq = 50;
			}
			return;
		}
		if (itemName.contains("warrior")) {
			if (!itemName.contains("ring")) {
				c.defenceLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("dragonfire")) {

			c.defenceLevelReq = 75;

		}
		if (itemName.contains("flameburst")) {

			c.defenceLevelReq = 65;

		}
		if (itemName.contains("enchanted")) {

			c.defenceLevelReq = 40;

		}
		if (itemName.contains("primal kite")) {

			c.defenceLevelReq = 99;

		}
		if (itemName.contains("primal plate")) {

			c.defenceLevelReq = 99;

		}
		if (itemName.contains("d'hide body")) {
			if (!itemName.contains("chaps")) {
				c.defenceLevelReq = c.rangeLevelReq = 40;
			}
			return;
		}
		if (itemName.contains("celestial")) {

			c.defenceLevelReq = 90;
			c.magicLevelReq = 90;

		}
		if (itemName.contains("Sagittarian")) {

			c.defenceLevelReq = 90;
			c.rangeLevelReq = 90;

		}
		if (itemName.contains("dragon dagger")) {

			c.attackLevelReq = 60;
		}
		if (itemName.contains("drag dagger")) {

			c.attackLevelReq = 60;
		}
		if (itemName.contains("ancient")) {

			c.attackLevelReq = 50;
		}
		if (itemName.contains("hardleather")) {

			c.defenceLevelReq = 10;
		}
		if (itemName.contains("studded")) {

			c.defenceLevelReq = 20;
		}
		if (itemName.contains("party")) {

			// c.Donatorreq = 1;

		}
		if (itemName.contains("h'ween")) {

			// c.Donatorreq = 1;

		}
		if (itemName.contains("santa")) {

			// c.Donatorreq = 1;

		}
		if (itemName.contains("bandos")) {
			if (!itemName.contains("godsword")) {
				c.strengthLevelReq = c.defenceLevelReq = 65;
				// c.Donatorreq = 1;
				return;
			}
		}
		if (itemName.contains("dragon")) {
			if (!itemName.contains("nti-") && !itemName.contains("fire")) {
				c.attackLevelReq = c.defenceLevelReq = 60;
				return;
			}
		}
		if (itemName.contains("crystal")) {
			if (itemName.contains("shield")) {
				c.defenceLevelReq = 70;
			}/* else {
				c.rangeLevelReq = 70;
			}*/
			//return;
		}
		if (itemName.contains("ahrim")) {
			if (itemName.contains("staff")) {
				c.magicLevelReq = 70;
				c.attackLevelReq = 70;
			} else {
				c.magicLevelReq = 70;
				c.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("karil")) {
			if (itemName.contains("crossbow")) {
				c.rangeLevelReq = 70;
			} else {
				c.rangeLevelReq = 70;
				c.defenceLevelReq = 70;
			}
		}
		if (itemName.contains("armadyl")) {
			if (itemName.contains("godsword")) {
				c.attackLevelReq = 75;
				c.Donatorreq = 1;
			} else {
				c.rangeLevelReq = c.defenceLevelReq = 65;
			}
		}
		if (itemName.contains("saradomin")) {
			if (itemName.contains("sword")) {
				c.attackLevelReq = 70;
			}
			if (itemName.contains("crozier")) {
				c.attackLevelReq = 1;
				if (itemName.contains("robe")) {
					c.attackLevelReq = 1;

				} else {
					c.defenceLevelReq = 40;

				}
			}
		}
		if (itemName.contains("godsword")) {
			c.attackLevelReq = 75;
		}
		if (itemName.contains("3rd age") && !itemName.contains("amulet")) {
			c.defenceLevelReq = 60;
		}
		if (itemName.contains("verac") || itemName.contains("guthan") || itemName.contains("dharok")
				|| itemName.contains("torag")) {

			if (itemName.contains("hammers")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else if (itemName.contains("axe")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else if (itemName.contains("warspear")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else if (itemName.contains("flail")) {
				c.attackLevelReq = 70;
				c.strengthLevelReq = 70;
			} else {
				c.defenceLevelReq = 70;
			}
		}

		switch (itemId) {
		case 10887:
			c.strengthLevelReq = 1;
			return;
		}
	}

	/**
	 * two handed weapon check
	 **/
	public boolean is2handed(String itemName, int itemId) {
		if (itemName.contains("ahrim") || itemName.contains("karil") || itemName.contains("verac")
				|| itemName.contains("guthan") || itemName.contains("dharok") || itemName.contains("torag")) {
			return true;
		}
		if (itemName.contains("longbow") || itemName.contains("shortbow") || itemName.contains("ark bow")) {
			return true;
		}
		if (itemName.contains("crystal") || itemName.contains("maul") || itemName.contains("cannon")) {
			return true;
		}
		if (itemName.contains("godsword") || itemName.contains("claw")  || itemName.contains("ice fury") || itemName.contains("aradomin sword")
				|| itemName.contains("2h") || itemName.contains("spear")) {
			return true;
		}
		if (itemName.contains("zaryte")) {
			return true;
		}
		switch (itemId) {
		case 6724: // seercull
		case 11730:
		case 4153:
		case 6528:
		case 10887:
		case 14484:
		case 20120:
		case 13347:
		case 12926:
		case 4063:
		case 938:
		case 9899:
		case 9900:
		case 17861:
		case 17876:
		case 17878:
		case 17877:
		case 14007:
		case 13453:
			return true;
		}
		return false;
	}

	/**
	 * Weapons special bar, adds the spec bars to weapons that require them and
	 * removes the spec bars from weapons which don't require them
	 **/

	public void addSpecialBar(int weapon) {
		switch (weapon) {

		case 11142:
		case 11143:
		case 11144:
		case 19931:
		case 20135:
		case 20154:
		case 20130:
		case 11145:
		case 11146:
		case 11147:
		case 20250:
		case 20159:
		case 3287:
		case 12230:
		case 12238:
		case 15441: // whip
		case 15442: // whip
		case 15443: // whip
		case 15444: // whip
			c.getPA().sendFrame171(0, 12323);
			specialAmount(weapon, c.specAmount, 12335);
			break;

		case 19780:// Korasi's Sword
		case 9899:
		case 9900:
		case 17861:// Greatness
		case 11629:// 1000truths
		case 19784:
		case 13444:
			c.getPA().sendFrame171(0, 7574);
			specialAmount(weapon, c.specAmount, 7586);
			break;

		case 15241:
		case 11639:
		case 859: // magic bows
		case 861:
		case 11235:
		case 15501:
		case 15701: // dark bow
		case 15702: // dark bow
		case 15703: // dark bow
		case 15704: // dark bow
		case 13879:
		case 13883:
			c.getPA().sendFrame171(0, 7549);
			specialAmount(weapon, c.specAmount, 7561);
			break;

		case 4587:// dscimmy
			c.getPA().sendFrame171(0, 7599);
			specialAmount(weapon, c.specAmount, 7611);
			break;

		case 3204: // d hally
			c.getPA().sendFrame171(0, 8493);
			specialAmount(weapon, c.specAmount, 8505);
			break;

		case 1377: // d battleaxe
			c.getPA().sendFrame171(0, 7499);
			specialAmount(weapon, c.specAmount, 7511);
			break;

		case 4153: // gmaul
			c.getPA().sendFrame171(0, 7474);
			specialAmount(weapon, c.specAmount, 7486);
			break;

		case 1249: // dspear
			c.getPA().sendFrame171(0, 7674);
			specialAmount(weapon, c.specAmount, 7686);
			break;

		case 13347:
		case 4063:
		case 11651:
		case 11652:
		case 11653:
		case 11654:
		case 14484: // dragon claws
		case 20120:
		case 938:
		case 7783:
			c.getPA().sendFrame171(0, 7800);
			specialAmount(weapon, c.specAmount, 7812);
			break;
		case 13902: // Statius War
			c.getPA().sendFrame171(0, 7474);
			specialAmount(weapon, c.specAmount, 7486);
			break;
		case 13904: // Statius War (deg)
			c.getPA().sendFrame171(0, 7474);
			specialAmount(weapon, c.specAmount, 7486);
			break;

		case 15486: // SOL
		case 1215:// dragon dagger
		case 1231:
		case 13899:
		case 13901:
		case 10887:
		case 5680:
		case 13905:
		case 13907:
		case 5698:
		case 1305: // dragon long
		case 11694:
		case 11698:
		case 11700:
		case 11730:
		case 11696:
			c.getPA().sendFrame171(0, 7574);
			specialAmount(weapon, c.specAmount, 7586);
			break;

		case 1434: // dragon mace
			c.getPA().sendFrame171(0, 7624);
			specialAmount(weapon, c.specAmount, 7636);
			break;

		default:
			c.getPA().sendFrame171(1, 7624); // mace interface
			c.getPA().sendFrame171(1, 7474); // hammer, gmaul
			c.getPA().sendFrame171(1, 7499); // axe
			c.getPA().sendFrame171(1, 7549); // bow interface
			c.getPA().sendFrame171(1, 7574); // sword interface
			c.getPA().sendFrame171(1, 7599); // scimmy sword interface, for most
												// swords
			c.getPA().sendFrame171(1, 8493);
			c.getPA().sendFrame171(1, 12323); // whip interface
			break;
		}
	}

	/**
	 * Specials bar filling amount
	 **/

	public void specialAmount(int weapon, double specAmount, int barId) {
		c.specBarId = barId;
		c.getPA().sendFrame70(specAmount >= 10 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 9 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 8 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 7 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 6 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 5 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 4 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 3 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 2 ? 500 : 0, 0, (--barId));
		c.getPA().sendFrame70(specAmount >= 1 ? 500 : 0, 0, (--barId));
		updateSpecialBar();
		sendWeapon(weapon, getItemName(weapon));
	}

	/**
	 * Special attack text and what to highlight or blackout
	 **/

	public void updateSpecialBar() {
		if (c.usingSpecial && c.playerEquipment[c.playerWeapon] != 15486) {
			c.getPA().sendFrame126("@yel@ Special Attack (" + (int) c.specAmount * 10 + "%)", c.specBarId);
		} else {
			c.getPA().sendFrame126("@bla@ Special Attack (" + (int) c.specAmount * 10 + "%)", c.specBarId);
		}
	}

	/**
	 * Wear Item
	 **/

	public boolean wearItem(int wearID, int slot) {
		if ((wearID == 7449) && (c.playerRights != 3)) {
			c.sendMessage("This is a Owner only item.");
			return false;
		}
		if ((wearID == 773) && (c.totalDonatorPoints < 337)) {
			c.sendMessage("You need at least 337$ to wear the Perfect Ring.");
			return false;
		}
		if ((wearID == 895 || wearID == 9893) && (c.playerRights == 1 || c.playerRights == 4)) {
			c.sendMessage("This is a Administrators only item.");
			return false;
		}
		if ((wearID == 896 && c.totalDonatorPoints < 337)) {
			c.sendMessage("You need at least 337$ to wear the owner cape.");
			return false;
		}
		if ((wearID == 896 && c.isInBarrows())) {
			c.sendMessage("You can not wield this inside the Barrows Minigame.");
			return false;
		}
		if ((wearID == 894) && (c.playerRights == 2 || c.playerRights == 4)) {
			c.sendMessage("This is a Moderators only item.");
			return false;
		}
		if ((wearID == 15262)) {
			c.sendMessage("This feature is currently disabled.");
			return false;
		}

		synchronized (c) {
			int targetSlot = 0;
			if (wearID > 15085 && wearID < 15102) {
				/*
				 * if (c.clanId >= 0){ //c.useDice(wearID, true); }else{
				 * c.sendMessage(
				 * "You must be in a clan chat channel to do that."); } return
				 * false;
				 */
				if (System.currentTimeMillis() - c.diceDelay >= 1200) {
					if (c.Dicer != 1) {
						c.sendMessage("You need to be a Dicer in order to use the Dice Bag!");
						return false;
					}
					if (!c.inAclan) {
						c.sendMessage("You need to be in a clan to do this.");
					} else {
						c.startAnimation(11900);
						c.gfx0(2075);
						Server.clanChat.messageToClan("<shad=15695415>" + c.playerName + " just rolled <shad=16112652>"
								+ Misc.random(100) + "<shad=15695415> on the percentile dice.", c.clanId);
						c.diceDelay = System.currentTimeMillis();
						// c.sendMessage("Use ::dice");
					}
				}
				return false;
			}

			if (targetSlot == 3) {
				c.usingSpecial = false;
				addSpecialBar(wearID);
			}
			boolean canWearItem = true;

			if (c.playerItems[slot] == (wearID + 1)) {
				getRequirements(getItemName(wearID).toLowerCase(), wearID);
				targetSlot = Item.targetSlots[wearID];

				if (itemType(wearID).equalsIgnoreCase("cape")) {
					targetSlot = 1;
				} else if (itemType(wearID).equalsIgnoreCase("hat")) {
					targetSlot = 0;
				} else if (itemType(wearID).equalsIgnoreCase("afro")) {
					targetSlot = 0;
				} else if (itemType(wearID).equalsIgnoreCase("amulet")) {
					targetSlot = 2;
				} else if (itemType(wearID).equalsIgnoreCase("arrows")) {
					targetSlot = 13;
				} else if (itemType(wearID).equalsIgnoreCase("body")) {
					targetSlot = 4;
				} else if (itemType(wearID).equalsIgnoreCase("shield")) {
					targetSlot = 5;
				} else if (itemType(wearID).equalsIgnoreCase("legs")) {
					targetSlot = 7;
				} else if (itemType(wearID).equalsIgnoreCase("gloves")) {
					targetSlot = 9;
				} else if (itemType(wearID).equalsIgnoreCase("boots")) {
					targetSlot = 10;
				} else if (itemType(wearID).equalsIgnoreCase("ring")) {
					targetSlot = 12;
				} else {
					targetSlot = 3;
				}
				if (wearID == 15005) {
					targetSlot = 7;
				}
				if (wearID == 19829) {
					targetSlot = 0;
				}
				if (wearID == 12317) {
					targetSlot = 7;
				}
				if (wearID == 12319) {
					targetSlot = 0;
				}
				if (wearID == 12321) {
					targetSlot = 4;
				}
				if (wearID == 12301) {
					targetSlot = 7;
				}
				if (wearID == 12303) {
					targetSlot = 0;
				}
				if (wearID == 12305) {
					targetSlot = 4;
				}
				if (wearID == 14553) {
					targetSlot = 0;
				}
				if (wearID == 14551) {
					targetSlot = 4;
				}
				if (wearID == 14552) {
					targetSlot = 7;
				}
				if (wearID == 19828) {
					targetSlot = 1;
				}
				if (wearID == 14554) {
					targetSlot = 10;
				}
				if (wearID == 14555) {
					targetSlot = 9;
				}
				if (wearID == 17854) {
					targetSlot = 5;
				}
				if (wearID == 17584) {
					targetSlot = 5;
				}

				if (wearID == 3287) {
					targetSlot = 9;
				}
				if (wearID == 3286) {
					targetSlot = 10;
				}
				if (wearID == 3289) {
					targetSlot = 7;
				}
				if (wearID == 3288) {
					targetSlot = 0;
				}
				if (wearID == 3285) {
					targetSlot = 4;
				}
				if (wearID == 12227) {
					targetSlot = 9;
				}
				if (wearID == 12225) {
					targetSlot = 10;
				}
				if (wearID == 14007) {
					targetSlot = 3;
				}
				if (wearID == 12232) {
					targetSlot = 4;
				}
				if (wearID == 12228) {
					targetSlot = 0;
				}
				if (wearID == 12229) {
					targetSlot = 7;
				}
				if (wearID == 12234) {
					targetSlot = 10;
				}
				if (wearID == 12235) {
					targetSlot = 9;
				}
				if (wearID == 12236) {
					targetSlot = 0;
				}
				if (wearID == 12233) {
					targetSlot = 4;
				}
				if (wearID == 12237) {
					targetSlot = 7;
				}
				if (wearID == 12239) {
					targetSlot = 1;
				}
				if (wearID == 7918) {
					targetSlot = 1;
				}
				if (wearID == 12231) {
					targetSlot = 1;
				}
				if (wearID == 3291) {
					targetSlot = 1;
				}
				if (wearID == 894 || wearID == 895) {
					targetSlot = 1;
				}
				if (wearID == 20165) {
					targetSlot = 5;
				}
				if (wearID == 18379) {
					targetSlot = 1;
				}
				if (wearID == 18376) {
					targetSlot = 0;
				}
				if (wearID == 18375) {
					targetSlot = 7;
				}

				if (wearID == 20158) {
					targetSlot = 1;
				}
				if (wearID == 20252) {
					targetSlot = 0;
				}
				if (wearID == 20253) {
					targetSlot = 4;
				}
				if (wearID == 20251) {
					targetSlot = 7;
				}
				if (wearID == 20161) {
					targetSlot = 0;
				}
				if (wearID == 20157) {
					targetSlot = 4;
				}
				if (wearID == 20210) {
					targetSlot = 7;
				}
				if (wearID == 920) {
					targetSlot = 4;
				}
				if (wearID == 921) {
					targetSlot = 7;
				}
				if (wearID == 922) {
					targetSlot = 0;
				}
				if (wearID == 19111) {
					targetSlot = 1;
				}
				if (wearID == 19142) {
					targetSlot = 1;
				}
				if (wearID == 13836) {
					targetSlot = 10;
				}
				if (wearID == 13832) {
					targetSlot = 10;
				}
				if (wearID == 13834) {
					targetSlot = 10;
				}
				if (wearID == 13997) {
					targetSlot = 4;
				}
				if (wearID == 13998) {
					targetSlot = 7;
				}
				if (wearID == 13999) {
					targetSlot = 0;
				}
				if (wearID == 13830) {
					targetSlot = 0;
				}
				if (wearID == 13830) {
					targetSlot = 0;
				}
				if (wearID == 13826) {
					targetSlot = 0;
				}
				if (wearID == 13822) {
					targetSlot = 3;
				}
				if (wearID == 13484) {
					targetSlot = 3;
				}
				if (wearID == 19140) {
					targetSlot = 1;
				}
				if (wearID == 13495) {
					targetSlot = 5;
				}
				if (wearID == 13493) {
					targetSlot = 5;
				}
				if (wearID == 13492) {
					targetSlot = 5;
				}
				if (wearID == 11613) {
					targetSlot = 5;
				}
				if (wearID == 19138) {
					targetSlot = 1;
				}
				if (wearID == 17935) {
					targetSlot = 1;
				}
				if (wearID == 17937) {
					targetSlot = 1;
				}
				if (wearID == 896) {
					targetSlot = 1;
				}
				if (wearID == 19709) {
					targetSlot = 1;
				}
				if (wearID == 17939) {
					targetSlot = 1;
				}
				if (wearID == 17941) {
					targetSlot = 1;
				}
				if (wearID == 17943) {
					targetSlot = 1;
				}
				if (wearID == 17945) {
					targetSlot = 1;
				}
				if (wearID == 17947) {
					targetSlot = 1;
				}
				if (wearID == 17949) {
					targetSlot = 1;
				}
				if (wearID == 17951) {
					targetSlot = 1;
				}
				if (wearID == 19139) {
					targetSlot = 0;
				}
				if (wearID == 19141) {
					targetSlot = 0;
				}
				if (wearID == 19954) {
					targetSlot = 0;
				}
				if (wearID == 19956) {
					targetSlot = 0;
				}
				if (wearID == 19958) {
					targetSlot = 10;
				}
				if (wearID == 13225) {
					targetSlot = 0;
				}
				if (wearID == 14001) {
					targetSlot = 4;
				}

				if (c.duelRule[11] && targetSlot == 0) {
					c.sendMessage("Wearing hats has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[12] && targetSlot == 1) {
					c.sendMessage("Wearing capes has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[13] && targetSlot == 2) {
					c.sendMessage("Wearing amulets has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[14] && targetSlot == 3) {
					c.sendMessage("Wielding weapons has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[15] && targetSlot == 4) {
					c.sendMessage("Wearing bodies has been disabled in this duel!");
					return false;
				}
				if ((c.duelRule[16] && targetSlot == 5)
						|| (c.duelRule[16] && is2handed(getItemName(wearID).toLowerCase(), wearID))) {
					c.sendMessage("Wearing shield has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[17] && targetSlot == 7) {
					c.sendMessage("Wearing legs has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[18] && targetSlot == 9) {
					c.sendMessage("Wearing gloves has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[19] && targetSlot == 10) {
					c.sendMessage("Wearing boots has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[20] && targetSlot == 12) {
					c.sendMessage("Wearing rings has been disabled in this duel!");
					return false;
				}
				if (c.duelRule[21] && targetSlot == 13) {
					c.sendMessage("Wearing arrows has been disabled in this duel!");
					return false;
				}

				if (Config.itemRequirements) {
					if (targetSlot == 10 || targetSlot == 7 || targetSlot == 5 || targetSlot == 4 || targetSlot == 0
							|| targetSlot == 9 || targetSlot == 10) {
						if (c.defenceLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[1]) < c.defenceLevelReq) {
								c.sendMessage(
										"You need a defence level of " + c.defenceLevelReq + " to wear this item.");
								canWearItem = false;
							}
						}
						if (c.rangeLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[4]) < c.rangeLevelReq) {
								c.sendMessage("You need a range level of " + c.rangeLevelReq + " to wear this item.");
								canWearItem = false;
							}
						}
						if (c.dungLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[24]) < c.dungLevelReq) {
								c.sendMessage(
										"You need a Dungeoneering level of " + c.dungLevelReq + " to wear this item.");
								canWearItem = false;
							}
						}
						if (c.slayerLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[18]) < c.slayerLevelReq) {
								c.sendMessage("You need a Slayer level of " + c.slayerLevelReq + " to wear this item.");
								canWearItem = false;
							}
						}
						if (c.magicLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[6]) < c.magicLevelReq) {
								c.sendMessage("You need a magic level of " + c.magicLevelReq + " to wear this item.");
								canWearItem = false;
							}
						}
					}
					if (targetSlot == 3) {
						if (c.attackLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[0]) < c.attackLevelReq) {
								c.sendMessage(
										"You need an attack level of " + c.attackLevelReq + " to wield this weapon.");
								canWearItem = false;
							}
						}
						if (c.dungLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[24]) < c.dungLevelReq) {
								c.sendMessage(
										"You need a Dungeoneering level of " + c.dungLevelReq + " to wear this item.");
								canWearItem = false;
							}
						}
						if (c.slayerLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[18]) < c.slayerLevelReq) {
								c.sendMessage("You need a Slayer level of " + c.slayerLevelReq + " to wear this item.");
								canWearItem = false;
							}
						}
						if (c.rangeLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[4]) < c.rangeLevelReq) {
								c.sendMessage(
										"You need a range level of " + c.rangeLevelReq + " to wield this weapon.");
								canWearItem = false;
							}
						}
						if (c.magicLevelReq > 0) {
							if (c.getPA().getLevelForXP(c.playerXP[6]) < c.magicLevelReq) {
								c.sendMessage(
										"You need a magic level of " + c.magicLevelReq + " to wield this weapon.");
								canWearItem = false;
							}
						}
					}
				}

				if (!canWearItem) {
					return false;
				}

				int wearAmount = c.playerItemsN[slot];
				if (wearAmount < 1) {
					return false;
				}

				if (targetSlot == c.playerWeapon) {
					c.autocasting = false;
					c.autocastId = 0;
					c.getPA().sendFrame36(108, 0);
				}

				if (slot >= 0 && wearID >= 0) {
					int toEquip = c.playerItems[slot];
					int toEquipN = c.playerItemsN[slot];
					int toRemove = c.playerEquipment[targetSlot];
					int toRemoveN = c.playerEquipmentN[targetSlot];
					if (toEquip == toRemove + 1 && Item.itemStackable[toRemove]) {
						deleteItem(toRemove, getItemSlot(toRemove), toEquipN);
						c.playerEquipmentN[targetSlot] += toEquipN;
					} else if (toRemove != -1 && Item.itemStackable[toRemove] && c.getItems().playerHasItem(toRemove)) {
						c.playerItems[slot] = 0;
						c.playerItemsN[slot] = 0;
						c.playerItems[c.getItems().getItemSlot(toRemove)] = toRemove + 1;
						c.playerItemsN[c.getItems().getItemSlot(toRemove)] += toRemoveN;
						c.playerEquipment[targetSlot] = toEquip - 1;
						c.playerEquipmentN[targetSlot] = toEquipN;
					} else if (targetSlot != 5 && targetSlot != 3) {
						c.playerItems[slot] = toRemove + 1;
						c.playerItemsN[slot] = toRemoveN;
						c.playerEquipment[targetSlot] = toEquip - 1;
						c.playerEquipmentN[targetSlot] = toEquipN;
					} else if (targetSlot == 5) {
						boolean wearing2h = is2handed(getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase(),
								c.playerEquipment[c.playerWeapon]);
						boolean wearingShield = c.playerEquipment[c.playerShield] > 0;
						if (wearing2h) {
							toRemove = c.playerEquipment[c.playerWeapon];
							toRemoveN = c.playerEquipmentN[c.playerWeapon];
							c.playerEquipment[c.playerWeapon] = -1;
							c.playerEquipmentN[c.playerWeapon] = 0;
							updateSlot(c.playerWeapon);
						}
						c.playerItems[slot] = toRemove + 1;
						c.playerItemsN[slot] = toRemoveN;
						c.playerEquipment[targetSlot] = toEquip - 1;
						c.playerEquipmentN[targetSlot] = toEquipN;
					} else if (targetSlot == 3) {
						boolean is2h = is2handed(getItemName(wearID).toLowerCase(), wearID);
						boolean wearingShield = c.playerEquipment[c.playerShield] > 0;
						boolean wearingWeapon = c.playerEquipment[c.playerWeapon] > 0;
						if (is2h) {
							if (wearingShield && wearingWeapon) {
								if (freeSlots() > 0) {
									c.playerItems[slot] = toRemove + 1;
									c.playerItemsN[slot] = toRemoveN;
									c.playerEquipment[targetSlot] = toEquip - 1;
									c.playerEquipmentN[targetSlot] = toEquipN;
									removeItem(c.playerEquipment[c.playerShield], c.playerShield);
								} else {
									c.sendMessage("You do not have enough inventory space to do this.");
									return false;
								}
							} else if (wearingShield && !wearingWeapon) {
								c.playerItems[slot] = c.playerEquipment[c.playerShield] + 1;
								c.playerItemsN[slot] = c.playerEquipmentN[c.playerShield];
								c.playerEquipment[targetSlot] = toEquip - 1;
								c.playerEquipmentN[targetSlot] = toEquipN;
								c.playerEquipment[c.playerShield] = -1;
								c.playerEquipmentN[c.playerShield] = 0;
								updateSlot(c.playerShield);
							} else {
								c.playerItems[slot] = toRemove + 1;
								c.playerItemsN[slot] = toRemoveN;
								c.playerEquipment[targetSlot] = toEquip - 1;
								c.playerEquipmentN[targetSlot] = toEquipN;
							}
						} else {
							c.playerItems[slot] = toRemove + 1;
							c.playerItemsN[slot] = toRemoveN;
							c.playerEquipment[targetSlot] = toEquip - 1;
							c.playerEquipmentN[targetSlot] = toEquipN;
						}
					}
					//// updateInventory = true;
					resetItems(3214);
				}

				if (targetSlot == 3) {
					c.usingSpecial = false;
					addSpecialBar(wearID);
				}
				if (c.getOutStream() != null && c != null) {
					c.getOutStream().createFrameVarSizeWord(34);
					c.getOutStream().writeWord(1688);
					c.getOutStream().writeByte(targetSlot);
					c.getOutStream().writeWord(wearID + 1);

					if (c.playerEquipmentN[targetSlot] > 254) {
						c.getOutStream().writeByte(255);
						c.getOutStream().writeDWord(c.playerEquipmentN[targetSlot]);
					} else {
						c.getOutStream().writeByte(c.playerEquipmentN[targetSlot]);
					}

					c.getOutStream().endFrameVarSizeWord();
					c.flushOutStream();
				}
				sendWeapon(c.playerEquipment[c.playerWeapon], getItemName(c.playerEquipment[c.playerWeapon]));
				resetBonus();
				getBonus();
				writeBonus();
				c.getCombat()
						.getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.getPA().requestUpdates();
				return true;
			} else {
				return false;
			}
		}
	}

	public void wearItem(int wearID, int wearAmount, int targetSlot) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(targetSlot);
				c.getOutStream().writeWord(wearID + 1);

				if (wearAmount > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(wearAmount);
				} else {
					c.getOutStream().writeByte(wearAmount);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
				c.playerEquipment[targetSlot] = wearID;
				c.playerEquipmentN[targetSlot] = wearAmount;
				c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon],
						c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
				c.getItems().resetBonus();
				c.getItems().getBonus();
				c.getItems().writeBonus();
				c.getCombat()
						.getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
				c.updateRequired = true;
				c.setAppearanceUpdateRequired(true);
			}
		}
	}

	public void updateSlot(int slot) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(slot);
				c.getOutStream().writeWord(c.playerEquipment[slot] + 1);
				if (c.playerEquipmentN[slot] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(c.playerEquipmentN[slot]);
				} else {
					c.getOutStream().writeByte(c.playerEquipmentN[slot]);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
			}
		}

	}

	public boolean playerHasEquipped(int itemID) {
		itemID++;
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] == itemID) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove Item
	 **/
	public void removeItem(int wearID, int slot) {
		synchronized (c) {
			if (c.getOutStream() != null && c != null) {
				if (c.playerEquipment[slot] > -1) {
					if (addItem(c.playerEquipment[slot], c.playerEquipmentN[slot])) {
						c.playerEquipment[slot] = -1;
						c.playerEquipmentN[slot] = 0;
						sendWeapon(c.playerEquipment[c.playerWeapon], getItemName(c.playerEquipment[c.playerWeapon]));
						resetBonus();
						getBonus();
						writeBonus();
						c.getCombat().getPlayerAnimIndex(
								c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
						c.getOutStream().createFrame(34);
						c.getOutStream().writeWord(6);
						c.getOutStream().writeWord(1688);
						c.getOutStream().writeByte(slot);
						c.getOutStream().writeWord(0);
						c.getOutStream().writeByte(0);
						c.flushOutStream();
						c.updateRequired = true;
						c.setAppearanceUpdateRequired(true);
					}
				}
			}
		}
	}

	/**
	 * BANK
	 */

	public void rearrangeBank() {
		int totalItems = 0;
		int highestSlot = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] != 0) {
				totalItems++;
				if (highestSlot <= i) {
					highestSlot = i;
				}
			}
		}

		for (int i = 0; i <= highestSlot; i++) {
			if (c.bankItems[i] == 0) {
				boolean stop = false;

				for (int k = i; k <= highestSlot; k++) {
					if (c.bankItems[k] != 0 && !stop) {
						int spots = k - i;
						for (int j = k; j <= highestSlot; j++) {
							c.bankItems[j - spots] = c.bankItems[j];
							c.bankItemsN[j - spots] = c.bankItemsN[j];
							stop = true;
							c.bankItems[j] = 0;
							c.bankItemsN[j] = 0;
						}
					}
				}
			}
		}

		int totalItemsAfter = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] != 0) {
				totalItemsAfter++;
			}
		}

		if (totalItems != totalItemsAfter)
			c.disconnected = true;
	}

	public void itemOnInterface(int id, int amount) {
		synchronized (c) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(2274);
			c.getOutStream().writeWord(1);
			if (amount > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord_v2(amount);
			} else {
				c.getOutStream().writeByte(amount);
			}
			c.getOutStream().writeWordBigEndianA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void resetBank() {
		synchronized (c) {
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(5382); // bank
			c.getOutStream().writeWord(Config.BANK_SIZE);
			for (int i = 0; i < Config.BANK_SIZE; i++) {
				if (c.bankItemsN[i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(c.bankItemsN[i]);
				} else {
					c.getOutStream().writeByte(c.bankItemsN[i]);
				}
				if (c.bankItemsN[i] < 1) {
					c.bankItems[i] = 0;
				}
				if (c.bankItems[i] > Config.ITEM_LIMIT || c.bankItems[i] < 0) {
					c.bankItems[i] = Config.ITEM_LIMIT;
				}
				c.getOutStream().writeWordBigEndianA(c.bankItems[i]);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public void resetTempItems() {
		synchronized (c) {
			int itemCount = 0;
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] > -1) {
					itemCount = i;
				}
			}
			c.getOutStream().createFrameVarSizeWord(53);
			c.getOutStream().writeWord(5064);
			c.getOutStream().writeWord(itemCount + 1);
			for (int i = 0; i < itemCount + 1; i++) {
				if (c.playerItemsN[i] > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord_v2(c.playerItemsN[i]);
				} else {
					c.getOutStream().writeByte(c.playerItemsN[i]);
				}
				if (c.playerItems[i] > Config.ITEM_LIMIT || c.playerItems[i] < 0) {
					c.playerItems[i] = Config.ITEM_LIMIT;
				}
				c.getOutStream().writeWordBigEndianA(c.playerItems[i]);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}

	public boolean bankItem(int itemID, int fromSlot, int amount) {
		// if(c.storing && playerHasItem(itemID, amount, fromSlot) &&
		// c.occupied[1] == false)
		// {
		// c.getPA().Frame34(7423, itemID, 1, 1);
		// deleteItem(itemID, 1);
		// c.occupied[1] = true;
		// c.storeditems[1] = itemID;
		// c.amount[1] = amount;

		// resetTempItems();
		// resetBank();
		// return false;
		// }

		if (c.storing && playerHasItem(itemID, amount, fromSlot)) {

			c.firstslot();

			if (c.occupied[c.summoningslot] == false && c.maxstore > c.summoningslot && c.maxstore != c.totalstored) {

				if (Item.itemStackable[itemID]) {
					c.sendMessage("You can't store Stackable items.");
					return false;
				}
				// c.getPA().Frame34(7423, itemID, c.summoningslot, 1);
				c.getPA().Frame34(2702, itemID, c.summoningslot, 1);
				deleteItem(itemID, 1);
				c.occupied[c.summoningslot] = true;
				c.storeditems[c.summoningslot] = itemID;
				c.amount[c.summoningslot] = amount;
				c.summoningslot += 1;
				c.totalstored += 1;

				resetTempItems();
				resetBank();
			}

			if (c.totalstored == c.maxstore) {
				c.sendMessage("Your npc can only store " + c.maxstore + " items");
			}

			return false;

		}

		c.getPA().sendFrame126(bankSlotsUsed() + " / " + Config.BANK_SIZE, 23003);
		if (c.inTrade) {
			c.sendMessage("You can't store items while trading!");
			return false;
		}
		/*
		 * if(c.tradeStatus == 1 && !c.inTrade) {
		 * c.getTradeAndDuel().declineTrade(); c.sendMessage(
		 * "AntiDupe: You tried to bank an item while dueling, duel closed.");
		 * return false; }
		 */
		if (c.inTrade) {
			c.getTradeAndDuel().declineDuel();
			c.sendMessage("AntiDupe: You tried to bank an item while dueling, trade closed.");
			return false;
		}
		if (c.isShopping) {
			c.sendMessage("You cannot bank items while shopping! - Walk then try.");
			return false;
		} /*
			 * if (c.lastAttackedBy != null) { c.sendMessage(
			 * "AntiDupe: Cannot bank item, errorcode: You can't drop items in combat."
			 * ); return false; }
			 */
		if (c.playerItemsN[fromSlot] <= 0) {
			return false;
		}
		if (!Item.itemIsNote[c.playerItems[fromSlot] - 1]) {
			if (c.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[c.playerItems[fromSlot] - 1] || c.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Config.BANK_SIZE; i++) {
					if (c.bankItems[i] == c.playerItems[fromSlot]) {
						if (c.playerItemsN[fromSlot] < amount)
							amount = c.playerItemsN[fromSlot];
						alreadyInBank = true;
						toBankSlot = i;
						i = Config.BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Config.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Config.BANK_SIZE + 1;
						}
					}
					c.bankItems[toBankSlot] = c.playerItems[fromSlot];
					if (c.playerItemsN[fromSlot] < amount) {
						amount = c.playerItemsN[fromSlot];
					}
					if ((c.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT
							&& (c.bankItemsN[toBankSlot] + amount) > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						c.sendMessage("Bank full!");
						return false;
					}
					deleteItem((c.playerItems[fromSlot] - 1), fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					if ((c.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT
							&& (c.bankItemsN[toBankSlot] + amount) > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						c.sendMessage("Bank full!");
						return false;
					}
					deleteItem((c.playerItems[fromSlot] - 1), fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = c.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Config.BANK_SIZE; i++) {
					if (c.bankItems[i] == c.playerItems[fromSlot]) {
						alreadyInBank = true;
						toBankSlot = i;
						i = Config.BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Config.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Config.BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if ((c.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItems[toBankSlot] = c.playerItems[firstPossibleSlot];
							c.bankItemsN[toBankSlot] += 1;
							deleteItem((c.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if ((c.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItemsN[toBankSlot] += 1;
							deleteItem((c.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.sendMessage("Bank full!");
					return false;
				}
			}
		} else if (Item.itemIsNote[c.playerItems[fromSlot] - 1] && !Item.itemIsNote[c.playerItems[fromSlot] - 2]) {
			if (c.playerItems[fromSlot] <= 0) {
				return false;
			}
			if (Item.itemStackable[c.playerItems[fromSlot] - 1] || c.playerItemsN[fromSlot] > 1) {
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Config.BANK_SIZE; i++) {
					if (c.bankItems[i] == (c.playerItems[fromSlot] - 1)) {
						if (c.playerItemsN[fromSlot] < amount)
							amount = c.playerItemsN[fromSlot];
						alreadyInBank = true;
						toBankSlot = i;
						i = Config.BANK_SIZE + 1;
					}
				}

				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Config.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Config.BANK_SIZE + 1;
						}
					}
					c.bankItems[toBankSlot] = (c.playerItems[fromSlot] - 1);
					if (c.playerItemsN[fromSlot] < amount) {
						amount = c.playerItemsN[fromSlot];
					}
					if ((c.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT
							&& (c.bankItemsN[toBankSlot] + amount) > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					deleteItem((c.playerItems[fromSlot] - 1), fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					if ((c.bankItemsN[toBankSlot] + amount) <= Config.MAXITEM_AMOUNT
							&& (c.bankItemsN[toBankSlot] + amount) > -1) {
						c.bankItemsN[toBankSlot] += amount;
					} else {
						return false;
					}
					deleteItem((c.playerItems[fromSlot] - 1), fromSlot, amount);
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.sendMessage("Bank full!");
					return false;
				}
			} else {
				itemID = c.playerItems[fromSlot];
				int toBankSlot = 0;
				boolean alreadyInBank = false;
				for (int i = 0; i < Config.BANK_SIZE; i++) {
					if (c.bankItems[i] == (c.playerItems[fromSlot] - 1)) {
						alreadyInBank = true;
						toBankSlot = i;
						i = Config.BANK_SIZE + 1;
					}
				}
				if (!alreadyInBank && freeBankSlots() > 0) {
					for (int i = 0; i < Config.BANK_SIZE; i++) {
						if (c.bankItems[i] <= 0) {
							toBankSlot = i;
							i = Config.BANK_SIZE + 1;
						}
					}
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if ((c.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItems[toBankSlot] = (c.playerItems[firstPossibleSlot] - 1);
							c.bankItemsN[toBankSlot] += 1;
							deleteItem((c.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else if (alreadyInBank) {
					int firstPossibleSlot = 0;
					boolean itemExists = false;
					while (amount > 0) {
						itemExists = false;
						for (int i = firstPossibleSlot; i < c.playerItems.length; i++) {
							if ((c.playerItems[i]) == itemID) {
								firstPossibleSlot = i;
								itemExists = true;
								i = 30;
							}
						}
						if (itemExists) {
							c.bankItemsN[toBankSlot] += 1;
							deleteItem((c.playerItems[firstPossibleSlot] - 1), firstPossibleSlot, 1);
							amount--;
						} else {
							amount = 0;
						}
					}
					resetTempItems();
					resetBank();
					return true;
				} else {
					c.sendMessage("Bank full!");
					return false;
				}
			}
		} else {
			c.sendMessage("Item not supported " + (c.playerItems[fromSlot] - 1));
			return false;
		}
	}

	public int bankSlotsUsed() {
		int freeS = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] > 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public int freeBankSlots() {
		int freeS = 0;
		for (int i = 0; i < Config.BANK_SIZE; i++) {
			if (c.bankItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public void fromBank(int itemID, int fromSlot, int amount) {
		if (!c.isBanking) {
			c.getPA().closeAllWindows();
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (Server.playerHandler.players[j] != null) {
					Client c2 = (Client) Server.playerHandler.players[j];
					// c2.sendMessage("<shad=15695415>[Abuse]:
					// "+Misc.optimizeText(c.playerName)+" Tried to Bank Hack -
					// Stopped.");
				}
			}
			return;
		}
		if (amount > 0) {
			if (c.bankItems[fromSlot] > 0) {
				if (!c.takeAsNote) {
					if (Item.itemStackable[c.bankItems[fromSlot] - 1]) {
						if (c.bankItemsN[fromSlot] > amount) {
							if (addItem((c.bankItems[fromSlot] - 1), amount)) {
								c.bankItemsN[fromSlot] -= amount;
								resetBank();
								c.getItems().resetItems(5064);
							}
						} else {
							if (addItem((c.bankItems[fromSlot] - 1), c.bankItemsN[fromSlot])) {
								c.bankItems[fromSlot] = 0;
								c.bankItemsN[fromSlot] = 0;
								resetBank();
								c.getItems().resetItems(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (c.bankItemsN[fromSlot] > 0) {
								if (addItem((c.bankItems[fromSlot] - 1), 1)) {
									c.bankItemsN[fromSlot] += -1;
									amount--;
								} else {
									amount = 0;
								}
							} else {
								amount = 0;
							}
						}
						resetBank();
						c.getItems().resetItems(5064);
					}
				} else if (c.takeAsNote && Item.itemIsNote[c.bankItems[fromSlot]]) {
					if (c.bankItemsN[fromSlot] > amount) {
						if (addItem(c.bankItems[fromSlot], amount)) {
							c.bankItemsN[fromSlot] -= amount;
							resetBank();
							c.getItems().resetItems(5064);
						}
					} else {
						if (addItem(c.bankItems[fromSlot], c.bankItemsN[fromSlot])) {
							c.bankItems[fromSlot] = 0;
							c.bankItemsN[fromSlot] = 0;
							resetBank();
							c.getItems().resetItems(5064);
						}
					}
				} else {
					c.sendMessage("This item can't be withdrawn as a note.");
					if (Item.itemStackable[c.bankItems[fromSlot] - 1]) {
						if (c.bankItemsN[fromSlot] > amount) {
							if (addItem((c.bankItems[fromSlot] - 1), amount)) {
								c.bankItemsN[fromSlot] -= amount;
								resetBank();
								c.getItems().resetItems(5064);
							}
						} else {
							if (addItem((c.bankItems[fromSlot] - 1), c.bankItemsN[fromSlot])) {
								c.bankItems[fromSlot] = 0;
								c.bankItemsN[fromSlot] = 0;
								resetBank();
								c.getItems().resetItems(5064);
							}
						}
					} else {
						while (amount > 0) {
							if (c.bankItemsN[fromSlot] > 0) {
								if (addItem((c.bankItems[fromSlot] - 1), 1)) {
									c.bankItemsN[fromSlot] += -1;
									amount--;
								} else {
									amount = 0;
								}
							} else {
								amount = 0;
							}
						}
						resetBank();
						c.getItems().resetItems(5064);
					}
				}
			}
		}
	}

	public int itemAmount(int itemID) {
		int tempAmount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID) {
				tempAmount += c.playerItemsN[i];
			}
		}
		return tempAmount;
	}

	public boolean isStackable(int itemID) {
		return Item.itemStackable[itemID];
	}

	/**
	 * Update Equip tab
	 **/

	public void setEquipment(int wearID, int amount, int targetSlot) {
		synchronized (c) {
			c.getOutStream().createFrameVarSizeWord(34);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(targetSlot);
			c.getOutStream().writeWord(wearID + 1);
			if (amount > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord(amount);
			} else {
				c.getOutStream().writeByte(amount);
			}
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
			c.playerEquipment[targetSlot] = wearID;
			c.playerEquipmentN[targetSlot] = amount;
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}

	/**
	 * Move Items
	 **/

	public void moveItems(int from, int to, int moveWindow) {
		if(c.inTrade) {
			c.getTradeAndDuel().declineTrade();
			return;
		}
		if(c.tradeStatus == 1) {
			c.getTradeAndDuel().declineTrade();
			return;
		}
		if(c.duelStatus == 1) {
			c.getTradeAndDuel().declineDuel();
			return;
		}
		if (moveWindow == 3724) {
			int tempI;
			int tempN;
			tempI = c.playerItems[from];
			tempN = c.playerItemsN[from];

			c.playerItems[from] = c.playerItems[to];
			c.playerItemsN[from] = c.playerItemsN[to];
			c.playerItems[to] = tempI;
			c.playerItemsN[to] = tempN;
		}

		if (moveWindow == 34453 && from >= 0 && to >= 0 && from < Config.BANK_SIZE && to < Config.BANK_SIZE
				&& to < Config.BANK_SIZE) {
			int tempI;
			int tempN;
			tempI = c.bankItems[from];
			tempN = c.bankItemsN[from];

			c.bankItems[from] = c.bankItems[to];
			c.bankItemsN[from] = c.bankItemsN[to];
			c.bankItems[to] = tempI;
			c.bankItemsN[to] = tempN;
		}

		if (moveWindow == 34453) {
			resetBank();
		}
		if (moveWindow == 18579) {
			int tempI;
			int tempN;
			tempI = c.playerItems[from];
			tempN = c.playerItemsN[from];

			c.playerItems[from] = c.playerItems[to];
			c.playerItemsN[from] = c.playerItemsN[to];
			c.playerItems[to] = tempI;
			c.playerItemsN[to] = tempN;
			//// updateInventory = true;
			resetItems(3214);
		}
		resetTempItems();
		if (moveWindow == 3724) {
			//// updateInventory = true;
			resetItems(3214);
		}

	}

	/**
	 * delete Item
	 **/

	public void deleteEquipment(int i, int j) {
		synchronized (c) {
			if (Server.playerHandler.players[c.playerId] == null) {
				return;
			}
			if (i < 0) {
				return;
			}

			c.playerEquipment[j] = -1;
			c.playerEquipmentN[j] = c.playerEquipmentN[j] - 1;
			c.getOutStream().createFrame(34);
			c.getOutStream().writeWord(6);
			c.getOutStream().writeWord(1688);
			c.getOutStream().writeByte(j);
			c.getOutStream().writeWord(0);
			c.getOutStream().writeByte(0);
			getBonus();
			if (j == c.playerWeapon) {
				sendWeapon(-1, "Unarmed");
			}
			resetBonus();
			getBonus();
			writeBonus();
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}

	public void deleteItem(int id, int amount) {
		deleteItem(id, getItemSlot(id), amount);
	}

	public void deleteItem(int id, int slot, int amount) {
		if (id <= 0 || slot < 0) {
			return;
		}
		if (c.playerItems[slot] == (id + 1)) {
			if (c.playerItemsN[slot] > amount) {
				c.playerItemsN[slot] -= amount;
			} else {
				c.playerItemsN[slot] = 0;
				c.playerItems[slot] = 0;
			}
			resetItems(3214);
		}
	}

	public void deleteItem2(int id, int amount) {
		int am = amount;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (am == 0) {
				break;
			}
			if (c.playerItems[i] == (id + 1)) {
				if (c.playerItemsN[i] > amount) {
					c.playerItemsN[i] -= amount;
					break;
				} else {
					c.playerItems[i] = 0;
					c.playerItemsN[i] = 0;
					am--;
				}
			}
		}
		resetItems(3214);
	}

	/**
	 * Delete Arrows
	 **/
	public void deleteArrow() {
		synchronized (c) {
			if (c.playerEquipment[c.playerCape] == 10499 && Misc.random(5) != 1
					&& c.playerEquipment[c.playerArrows] != 4740)
				return;
			if (c.playerEquipmentN[c.playerArrows] == 1) {
				c.getItems().deleteEquipment(c.playerEquipment[c.playerArrows], c.playerArrows);
			}
			if (c.playerEquipmentN[c.playerArrows] != 0) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(c.playerArrows);
				c.getOutStream().writeWord(c.playerEquipment[c.playerArrows] + 1);
				if (c.playerEquipmentN[c.playerArrows] - 1 > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(c.playerEquipmentN[c.playerArrows] - 1);
				} else {
					c.getOutStream().writeByte(c.playerEquipmentN[c.playerArrows] - 1);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
				c.playerEquipmentN[c.playerArrows] -= 1;
			}
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}

	public void deleteEquipment() {
		synchronized (c) {
			if (c.playerEquipmentN[c.playerWeapon] == 1) {
				c.getItems().deleteEquipment(c.playerEquipment[c.playerWeapon], c.playerWeapon);
			}
			if (c.playerEquipmentN[c.playerWeapon] != 0) {
				c.getOutStream().createFrameVarSizeWord(34);
				c.getOutStream().writeWord(1688);
				c.getOutStream().writeByte(c.playerWeapon);
				c.getOutStream().writeWord(c.playerEquipment[c.playerWeapon] + 1);
				if (c.playerEquipmentN[c.playerWeapon] - 1 > 254) {
					c.getOutStream().writeByte(255);
					c.getOutStream().writeDWord(c.playerEquipmentN[c.playerWeapon] - 1);
				} else {
					c.getOutStream().writeByte(c.playerEquipmentN[c.playerWeapon] - 1);
				}
				c.getOutStream().endFrameVarSizeWord();
				c.flushOutStream();
				c.playerEquipmentN[c.playerWeapon] -= 1;
			}
			c.updateRequired = true;
			c.setAppearanceUpdateRequired(true);
		}
	}

	/**
	 * Dropping Arrows
	 **/

	public void dropArrowNpc() {
		if (c.playerEquipment[c.playerCape] == 10499)
			return;
		if (c.playerEquipment[c.playerArrows] == 15243)
			return;
		/*
		 * if (c.playerEquipment[c.playerWeapon] == 13883) // MORRIGAN'S AXE
		 * return; if (c.playerEquipment[c.playerWeapon] == 13879) // MORRIGAN'S
		 * JAVELIN return;
		 */
		int enemyX = Server.npcHandler.npcs[c.oldNpcIndex].getX();
		int enemyY = Server.npcHandler.npcs[c.oldNpcIndex].getY();
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX, enemyY, 1, c.getId());
			} else if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX, enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX, enemyY);
				Server.itemHandler.removeGroundItem(c, c.rangeItemUsed, enemyX, enemyY, false);
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX, enemyY, amount + 1, c.getId());
			}
		}
	}

	public void dropArrowPlayer() {
		int enemyX = Server.playerHandler.players[c.oldPlayerIndex].getX();
		int enemyY = Server.playerHandler.players[c.oldPlayerIndex].getY();
		if (c.playerEquipment[c.playerCape] == 10499) // AVA'S ACCUMAlator
			return;
		/*
		 * if (c.playerEquipment[c.playerWeapon] == 13883) // MORRIGAN'S AXE
		 * return; if (c.playerEquipment[c.playerWeapon] == 13879) // MORRIGAN'S
		 * JAVELIN return;
		 */
		if (c.playerEquipment[c.playerArrows] == 15243) // HAND CANNON SHOTS
			return;
		if (Misc.random(10) >= 4) {
			if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX, enemyY) == 0) {
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX, enemyY, 1, c.getId());
			} else if (Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX, enemyY) != 0) {
				int amount = Server.itemHandler.itemAmount(c.playerName, c.rangeItemUsed, enemyX, enemyY);
				Server.itemHandler.removeGroundItem(c, c.rangeItemUsed, enemyX, enemyY, false);
				Server.itemHandler.createGroundItem(c, c.rangeItemUsed, enemyX, enemyY, amount + 1, c.getId());
			}
		}
	}

	public void removeAllItems() {
		for (int i = 0; i < c.playerItems.length; i++) {
			c.playerItems[i] = 0;
		}
		for (int i = 0; i < c.playerItemsN.length; i++) {
			c.playerItemsN[i] = 0;
		}
		resetItems(3214);
	}

	public int freeSlots() {
		int freeS = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] <= 0) {
				freeS++;
			}
		}
		return freeS;
	}

	public int findItem(int id, int[] items, int[] amounts) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if (((items[i] - 1) == id) && (amounts[i] > 0)) {
				return i;
			}
		}
		return -1;
	}

	public String getItemName(int ItemID) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					return Server.itemHandler.ItemList[i].itemName;
				}
			}
		}
		return "Unarmed";
	}

	public static String getItemName2(int ItemID) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					return Misc.formatPlayerName(Server.itemHandler.ItemList[i].itemName);
				}
			}
		}
		return "Unarmed";
	}

	public int getItemId(String itemName) {
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemName.equalsIgnoreCase(itemName)) {
					return Server.itemHandler.ItemList[i].itemId;
				}
			}
		}
		return -1;
	}

	public int getItemSlot(int ItemID) {
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == ItemID) {
				return i;
			}
		}
		return -1;
	}

	public int getItemAmount(int ItemID) {
		int itemCount = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if ((c.playerItems[i] - 1) == ItemID) {
				itemCount += c.playerItemsN[i];
			}
		}
		return itemCount;
	}

	public boolean playerHasItem(int itemID, int amt, int slot) {
		itemID++;
		int found = 0;
		if (c.playerItems[slot] == (itemID)) {
			for (int i = 0; i < c.playerItems.length; i++) {
				if (c.playerItems[i] == itemID) {
					if (c.playerItemsN[i] >= amt) {
						return true;
					} else {
						found++;
					}
				}
			}
			if (found >= amt) {
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean playerHasItem(int itemID) {
		itemID++;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID)
				return true;
		}
		return false;
	}

	public boolean playerHasItem(int itemID, int amt) {
		itemID++;
		int found = 0;
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] == itemID) {
				if (c.playerItemsN[i] >= amt) {
					return true;
				} else {
					found++;
				}
			}
		}
		if (found >= amt) {
			return true;
		}
		return false;
	}

	public int getUnnotedItem(int ItemID) {
		int NewID = ItemID - 1;
		String NotedName = "";
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					NotedName = Server.itemHandler.ItemList[i].itemName;
				}
			}
		}
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemName == NotedName) {
					if (Server.itemHandler.ItemList[i].itemDescription
							.startsWith("Swap this note at any bank for a") == false) {
						NewID = Server.itemHandler.ItemList[i].itemId;
						break;
					}
				}
			}
		}
		return NewID;
	}

	/**
	 * Drop Item
	 **/

	public void createGroundItem(int itemID, int itemX, int itemY, int itemAmount) {
		synchronized (c) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((itemY - 8 * c.mapRegionY));
			c.getOutStream().writeByteC((itemX - 8 * c.mapRegionX));
			c.getOutStream().createFrame(44);
			c.getOutStream().writeWordBigEndianA(itemID);
			c.getOutStream().writeWord(itemAmount);
			c.getOutStream().writeByte(0);
			c.flushOutStream();
		}
	}

	/**
	 * Pickup Item
	 **/

	public void removeGroundItem(int itemID, int itemX, int itemY, int Amount) {
		synchronized (c) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((itemY - 8 * c.mapRegionY));
			c.getOutStream().writeByteC((itemX - 8 * c.mapRegionX));
			c.getOutStream().createFrame(156);
			c.getOutStream().writeByteS(0);
			c.getOutStream().writeWord(itemID);
			c.flushOutStream();
		}
	}

	public boolean ownsCape() {
		if (c.getItems().playerHasItem(2412, 1) || c.getItems().playerHasItem(2413, 1)
				|| c.getItems().playerHasItem(2414, 1))
			return true;
		for (int j = 0; j < Config.BANK_SIZE; j++) {
			if (c.bankItems[j] == 2412 || c.bankItems[j] == 2413 || c.bankItems[j] == 2414)
				return true;
		}
		if (c.playerEquipment[c.playerCape] == 2413 || c.playerEquipment[c.playerCape] == 2414
				|| c.playerEquipment[c.playerCape] == 2415)
			return true;
		return false;
	}

	public boolean hasAllShards() {
		return playerHasItem(11712, 1) && playerHasItem(11712, 1) && playerHasItem(11714, 1);
	}

	public void makeBlade() {
		deleteItem(11710, 1);
		deleteItem(11712, 1);
		deleteItem(11714, 1);
		addItem(11690, 1);
		c.sendMessage("You combine the shards to make a blade.");
	}

	public void makeGodsword(int i) {
		int godsword = i - 8;
		if (playerHasItem(11690) && playerHasItem(i)) {
			deleteItem(11690, 1);
			deleteItem(i, 1);
			addItem(i - 8, 1);
			c.sendMessage("You combine the hilt and the blade to make a godsword.");
		}
	}

	public boolean isHilt(int i) {
		return i >= 11702 && i <= 11708 && i % 2 == 0;
	}

}