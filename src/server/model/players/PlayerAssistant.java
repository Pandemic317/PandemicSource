package server.model.players;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import server.Config;
import server.Connection;
import server.Server;
import server.clip.region.Region;
import server.model.minigames.Event;
import server.model.minigames.EventContainer;
import server.model.minigames.EventManager;
import server.model.npcs.NPC;
import server.model.npcs.NPCHandler;
import server.model.players.Content.Gambler.GambleInterface;
import server.model.players.packets.PathFinder;
import server.model.players.skills.Woodcutting;
import server.task.Task;
import server.util.Misc;

public class PlayerAssistant {
	@SuppressWarnings("all")
	private void showItems(int items[], int itemsN[]) {
		c.getOutStream().createFrameVarSizeWord(53);
		c.getOutStream().writeWord(5382);
		c.getOutStream().writeWord(Config.BANK_SIZE);
		for (int j = 0; j < items.length; j++) {
			if (items[j] > 254) {
				c.getOutStream().writeByte(255);
				c.getOutStream().writeDWord_v2(itemsN[j]);
			} else {
				c.getOutStream().writeByte(itemsN[j]);
			}
			if (itemsN[j] < 1) {
				items[j] = 0;
			}
			if (items[j] > Config.ITEM_LIMIT | items[j] < 0) {
				items[j] = Config.ITEM_LIMIT;
			}
			c.getOutStream().writeWordBigEndianA(items[j]);
		}
		c.getOutStream().endFrameVarSizeWord();
		c.flushOutStream();
	}

	public void sendFrame34P2(int item, int slot, int frame, int amount) {
		c.outStream.createFrameVarSizeWord(34);
		c.outStream.writeWord(frame);
		c.outStream.writeByte(slot);
		c.outStream.writeWord(item + 1);
		c.outStream.writeByte(255);
		c.outStream.writeDWord(amount);
		c.outStream.endFrameVarSizeWord();
	}

	public void takeOut(int itemID, int fromSlot, int amount, boolean x) {
		for (int j = 0; j < Config.BANK_SIZE; j++) {
			if (c.bankItems[j] > 0) {
				if (c.bankItems[j] - 1 == itemID) {
					if (x) {// means their using remove x
						if (amount > c.getItems().freeSlots() & amount > c.bankItemsN[j] & c.bankItemsN[j] >= amount) {
							c.bankItemsN[j] -= c.getItems().freeSlots();
							c.getItems().addItem(itemID, c.getItems().freeSlots());
						} else {
							c.getItems().addItem(itemID, amount);
							c.bankItemsN[j] -= amount;
							if (c.bankItemsN[j] == 0)
								c.bankItems[j] = 0;
							c.itemsN[fromSlot] = 0;
						}
					} else if (amount == -1) {// their using remove all
						if (c.bankItemsN[j] > c.getItems().freeSlots()) {
							c.bankItemsN[j] -= c.getItems().freeSlots();
							c.itemsN[fromSlot] -= c.getItems().freeSlots();
							c.getItems().addItem(itemID, c.getItems().freeSlots());
						}
						c.getItems().addItem(itemID, c.bankItemsN[j]);
						c.bankItemsN[j] = 0;
						c.bankItems[j] = 0;
						c.items[fromSlot] = 0;
						c.itemsN[fromSlot] = 0;
						break;
					} else if ((c.bankItemsN[j] - amount) > 0) {
						if (amount > c.bankItemsN[j]) {
							if (!c.getItems().addItem(itemID, c.bankItemsN[j]))
								break;
							c.bankItemsN[j] -= c.bankItemsN[j];
							c.bankItems[j] = 0;
							c.itemsN[fromSlot] -= c.bankItemsN[j];
							c.items[fromSlot] = 0;
							break;
						}
						if (!c.getItems().addItem(itemID, amount))
							break;
						c.bankItemsN[j] -= amount;
						c.itemsN[fromSlot] -= amount;
						c.items[fromSlot] = 0;
						if (c.bankItemsN[j] == 0)
							c.bankItems[j] = 0;
					} else {
						if (amount > c.bankItemsN[j]) {
							if (!c.getItems().addItem(itemID, c.bankItemsN[j]))
								break;
							c.bankItems[j] = 0;
							c.bankItemsN[j] -= c.bankItemsN[j];
							c.itemsN[fromSlot] -= c.bankItemsN[j];
							c.items[fromSlot] = 0;
							break;
						}
						if (!c.getItems().addItem(itemID, amount))
							break;
						c.bankItems[j] = 0;
						c.bankItemsN[j] -= amount;
						c.items[fromSlot] = 0;
						c.itemsN[fromSlot] -= amount;
					}
				}
			}
		}
		c.getItems().resetTempItems();
		c.lastSearch = true;
		searchBank(c.searchName);
	}

	public void movePlayer1(int x, int y) {
		c.resetWalkingQueue();
		c.teleportToX = x;
		c.teleportToY = y;
		requestUpdates();
	}

	public void birdsNest() {
		Server.itemHandler.createGroundItem(c, 5070 + Misc.random(4), c.getX(), c.getY(), 1, c.getId());// Makes
																										// any
																										// item
																										// with
																										// the
																										// ID
																										// of
																										// 5070
																										// to
																										// 5074
																										// appear
																										// on
																										// the
																										// ground.
		c.sendMessage("A bird's nest falls out of the tree!");// Sends Message
																// telling you
																// that a birds
																// nest has
																// fallen out of
																// the tree.
	}

	@SuppressWarnings("unused")
	public void dragonkinFormula(int skillId) {
		int currentLevel = getLevelForXP(c.playerXP[skillId]);
		int formula = (int) Math
				.floor((Math.pow(currentLevel, 3) - 2 * Math.pow(currentLevel, 2) + 100 * currentLevel) / 20);
		if (System.currentTimeMillis() - c.lastEffigy < 5000) {
			return;
		}
		if (c.getItems().playerHasItem(18782, 1)) {
			c.getPA().addSkillXP(300000, skillId);
			c.getItems().deleteItem2(18782, 1);
			c.sendMessage("You have gained 300k experience in this skill.");
			c.getPA().removeAllWindows();
			c.lastEffigy = System.currentTimeMillis();
		} else if (c.getItems().playerHasItem(7498, 1)) {
			c.getPA().addSkillXP(15000, skillId);
			c.getItems().deleteItem2(7498, 1);
			c.sendMessage("You have gained 15k experience in this skill.");
			c.getPA().removeAllWindows();
			c.lastEffigy = System.currentTimeMillis();
		}
	}

	public void destroySearch() {
		c.lastSearch = false;
		c.isSearching = false;
		c.items = new int[500];
		c.itemsN = new int[500];
		c.searchName = "";
		c.getItems().resetTempItems();
		c.getItems().rearrangeBank();
		c.getItems().resetBank();
		c.getItems().resetKeepItems();
		sendFrame126("The Grand Bank Of Pandemic.", 5383);
	}

	public void enterDT() {
		c.getPA().movePlayer(3165, 9758, c.playerId * 4);
		c.waveId = 0;
		c.dominionToKill = -1;
		c.dominionKilled = -1;
		Server.dominionTower.spawnNextWave(c);
		//c.sendMessage("Dominion Tower Is Being Worked On. You can earn points here.");
		//c.getPA().movePlayer(2864, 9776, 0);
	}

	public void searchBank(String str) {
		sendFrame126("The Grand Bank Of Pandemic.", 5383);
		c.items = new int[500];
		c.itemsN = new int[500];
		int p = 0;
		int slot = 0;
		for (int j = 0; j < c.bankItems.length; j++) {
			if (c.bankItems[j] > 0) {
				if (c.getItems().getItemName(c.bankItems[j] - 1).toLowerCase().contains(str.toLowerCase())) {
					c.items[slot] = c.bankItems[j];
					c.itemsN[slot] = c.bankItemsN[j];
					slot++;
					p++;
				}
			}
		}
		if (p > 0) {
			sendFrame126("The Grand Bank Of Pandemic - (search: '" + str + "')", 5383);
			showItems(c.items, c.itemsN);
			c.getItems().resetTempItems();
			slot = 0;
			c.isSearching = true;
		} else {
			if (c.lastSearch & c.isSearching) {
				destroySearch();
				return;
			} else {
				sendFrame126("No results were found for '" + str + "'.", 5383);
				c.isSearching = false;
			}
		}
	}

	public void destroyInterface(int itemId) {
		itemId = c.droppedItem;
		String itemName = c.getItems().getItemName(c.droppedItem);
		String[][] info = { { "Are you sure you want to drop this item?", "14174" }, { "Yes.", "14175" },
				{ "No.", "14176" }, { "", "14177" }, { "Dropping is Disabled, you will NOT", "14182" },
				{ "get it back once you click Yes.", "14183" }, { itemName, "14184" } };
		sendFrame34(itemId, 0, 14171, 1);
		for (int i = 0; i < info.length; i++)
			sendFrame126(info[i][0], Integer.parseInt(info[i][1]));
		sendFrame164(14170);
	}

	public void destroyItem(int itemId) {
		itemId = c.droppedItem;
		String itemName = c.getItems().getItemName(itemId);
		c.getItems().deleteItem(itemId, c.getItems().getItemSlot(itemId),
				c.playerItemsN[c.getItems().getItemSlot(itemId)]);
		c.sendMessage("Your " + itemName + " vanishes as you drop it on the ground.");
		removeAllWindows();
	}

	public void sendFrame34a(int frame, int item, int slot, int amount) { // new
																			// items
																			// kept
																			// on
																			// death
		c.outStream.createFrameVarSizeWord(34);
		c.outStream.writeWord(frame);
		c.outStream.writeByte(slot);
		c.outStream.writeWord(item + 1);
		c.outStream.writeByte(255);
		c.outStream.writeDWord(amount);
		c.outStream.endFrameVarSizeWord();
	}

	@SuppressWarnings("static-access")
	public double shieldEffect(int damageType) {
		double meleeAbsorb = 1;
		double rangeAbsorb = 1;
		double magicAbsorb = 1;

		switch (c.playerEquipment[c.playerShield]) {
		case 18359:// chaotickiteshield
			rangeAbsorb = .86;
			meleeAbsorb = .93;
			c.sendMessage("Your shield absorb's the damage");
			break;
		case 18361:// eagly-eyeshield
			magicAbsorb = .86;
			rangeAbsorb = .93;
			c.sendMessage("Your shield absorb's the damage");
			break;
		case 18363:// farseerkiteshield
			meleeAbsorb = .86;
			magicAbsorb = .93;
			c.sendMessage("Your shield absorb's the damage");
			break;
		case 18747:// faithful
			meleeAbsorb = .86;
			magicAbsorb = .93;
			c.sendMessage("Your shield absorb's the damage");
			break;
		}
		switch (damageType) {
		case 0: // melee
			return meleeAbsorb;
		case 1:// range
			return rangeAbsorb;
		case 2:// mage
			return magicAbsorb;
		default:
			return 1;
		}
	}

	public void frame174(int i1, int i2, int i3) // another thing, tested
													// doesn't logout, looks
													// like something to do with
													// music
	{
		c.outStream.createFrame(174);
		c.outStream.writeWord(i1);
		c.outStream.writeByte(i2);
		c.outStream.writeWord(i3);
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public void frame74(int songID) {

		c.outStream.createFrame(74);
		c.outStream.writeWordBigEndian(songID);
	}

	public int randomMusic() {
		return randomMusics[(int) (Math.random() * randomMusics.length)];
	}

	int[] randomMusics = { 109, 8, 144, 5, 2, 6, 3, 18, 20, 106, 82, 80, 76 };

	public void removeBoBItems(int slot, int amount) {
		if (c.getItems().freeSlots() > 0) {
			c.getItems().addItem(c.storeditems[slot], amount);
			c.occupied[slot] = false;
			c.storeditems[slot] = 0;
			c.getItems().resetTempItems();
			c.getItems().resetBank();
			c.totalstored -= slot;
		} else {
			c.getItems().resetTempItems();
			c.getItems().resetBank();
			c.sendMessage("Not enough space in your inventory.");
			return;
		}
	}

	public void BoBToBank(int slot, int amount) {
		for (int i = 0; i <= 28; i++) {
			c.getItems().bankItem(c.storeditems[slot], i, amount);
			c.occupied[slot] = false;
			c.storeditems[slot] = 0;
			c.getItems().resetTempItems();
			c.getItems().resetBank();
			c.totalstored -= slot;
		}
	}

	public void sendMp3(String mp3) {
		// synchronized(c) {
		// sendFrame126(":mp3:"+mp3+"", 24000);
		// }
	}

	public void sendshh() {
		// synchronized(c) {
		// sendFrame126("silence", 24000);
		// }
	}

	public void interfaceWithInventory(int interfaceId, int inventoryId) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(248);
			c.getOutStream().writeWordA(interfaceId);
			c.getOutStream().writeWord(inventoryId);
			c.flushOutStream();
		}
	}

	public void teleTabTeleport(int x, int y, int height, String teleportType) {
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.startAnimation(4069);
			c.getCombat().resetPlayerAttack();
			c.usingAltar = false;
			c.stopMovement();
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if (teleportType.equalsIgnoreCase("teleTab")) {
				c.foodDelay = System.currentTimeMillis();
				c.startAnimation(4731);
				c.gfx0(678);
				c.foodDelay = System.currentTimeMillis();
				c.teleTimer = 8;
				c.teleEndAnimation = 1;
			}
			if (teleportType.equalsIgnoreCase("House")) {
				c.foodDelay = System.currentTimeMillis();
				c.startAnimation(4731);
				c.gfx0(678);
				c.handleAllEvents();
				c.foodDelay = System.currentTimeMillis();
				c.teleTimer = 8;
				c.teleEndAnimation = 1;
			}
		}
	}

	public String getTotalAmount(Client c, int j) {
		if (j >= 10000 && j < 10000000) {
			return j / 1000 + "K";
		} else if (j >= 10000000 && j <= 2147483647) {
			return j / 1000000 + "M";
		} else {
			return "" + j + " gp";
		}
	}

	private Client c;

	public PlayerAssistant(Client Client) {
		this.c = Client;
	}
	
	public void moveOnLogout(int x, int y, int h){
		c.resetWalkingQueue();
		c.teleportToX = x;
		c.teleportToY = y;
		c.heightLevel = h;
		c.currentX = c.teleportToX - 8*c.mapRegionX;
		c.currentY = c.teleportToY - 8*c.mapRegionY;
		c.absX = c.teleportToX;
		c.absY = c.teleportToY;
		requestUpdates();
	}

	public void dungemote(final Client c) {
		Server.getTaskScheduler().addEvent(new Task(1, false) {
			int dungtime = 16;

			public void execute() {
				if (dungtime == 16) {
					c.gfx0(2442);
					c.startAnimation(13190);
				}
				if (dungtime == 15) {
					c.npcId2 = 11228;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13192);
				}
				if (dungtime == 10) {
					c.npcId2 = 11227;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13193);
				}
				if (dungtime == 6) {
					c.gfx0(2442);
				}
				if (dungtime == 5) {
					c.npcId2 = 11229;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13194);
				}
				if (dungtime == 0) {
					c.isNpc = false;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				}
				if (c == null || dungtime <= 0) {
					this.stop();
					return;
				}
				if (dungtime >= 0) {
					dungtime--;
				}
			}
		});
	}

	public void dungemote2(final Client c) {
		Server.getTaskScheduler().addEvent(new Task(1, false) {
			int dungtime = 16;

			public void execute() {
				if (dungtime == 16) {
					c.gfx0(2442);
					c.startAnimation(13190);
				}
				if (dungtime == 15) {
					c.npcId2 = 11228;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13192);
				}
				if (dungtime == 10) {
					c.npcId2 = 11227;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13193);
				}
				if (dungtime == 6) {
					c.gfx0(2442);
				}
				if (dungtime == 5) {
					c.npcId2 = 11229;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13194);
				}
				if (dungtime == 0) {
					c.isNpc = false;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				}
				if (c == null || dungtime <= 0) {
					this.stop();
					return;
				}
				if (dungtime >= 0) {
					dungtime--;
				}
			}
		});
	}

	public void compemote(final Client c) {
		Server.getTaskScheduler().addEvent(new Task(1, false) {
			int comptime = 28;

			public void execute() {
				if (comptime == 28) {
					c.startAnimation(13190);
				}
				if (comptime == 27) {
					c.npcId2 = 8596;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(11197);
					c.playerStandIndex = 11195;
				}
				if (comptime == 23) {
					c.npcId2 = 8597;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(11202);
					c.playerStandIndex = 11200;
				}
				if (comptime == 20) {
					c.npcId2 = 8591;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.playerStandIndex = 9724;
				}
				if (comptime == 17) {
					c.npcId2 = 8281;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13192);
					c.startAnimation(10680);
					c.startAnimation(10681);
					c.playerStandIndex = 10665;
				}

				if (comptime == 13) {
					c.npcId2 = 10224;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13157);
					c.playerStandIndex = 13156;
				}
				if (comptime == 11) {
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13152);
					c.gfx100(2465);
					c.playerStandIndex = 13156;
				}
				if (comptime == 7) {
					c.npcId2 = 10770;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13156);
					c.playerStandIndex = 13156;
				}
				if (comptime == 0) {
					c.isNpc = false;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.playerStandIndex = 0x328;
					c.startAnimation(12567);
				}
				if (c == null || comptime <= 0) {
					this.stop();
					return;
				}
				if (comptime >= 0) {
					comptime--;
				}
			}
		});
	}

	public void writePMLog(String data) {
		checkDateAndTime();
		String filePath = "./Data/Logs/PMLogs/" + c.playerName + ".txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " + "["
					+ c.connectedFrom + "]: " + "" + data + " ");
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}

	public void writeChatLog(String data) {
		checkDateAndTime();
		String filePath = "./Data/Logs/ChatLogs/" + c.playerName + ".txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " + "["
					+ c.connectedFrom + "]: " + "" + data + " ");
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}

	public void writeCommandLog(String command) {
		checkDateAndTime();
		String filePath = "./Data/Commands2.txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " + "["
					+ c.playerName + "]: " + "[" + c.connectedFrom + "] " + "::" + command);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}

	public void writeBanLog(String command) {
		checkDateAndTime();
		String filePath = "./Data/Logs/banlog.txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " + "["
					+ c.playerName + "]: " + "[" + c.connectedFrom + "] " + "Banned" + command);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}

	public void writeIPBanLog(String command) {
		checkDateAndTime();
		String filePath = "./Data/Logs/ipbanlog.txt";
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(filePath, true));
			bw.write("[" + c.date + "]" + "-" + "[" + c.currentTime + " " + checkTimeOfDay() + "]: " + "["
					+ c.playerName + "]: " + "[" + c.connectedFrom + "] " + "IP Banned" + command);
			bw.newLine();
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
			}
		}
	}

	public void playerWalk(int x, int y) { // clipped npcs
		PathFinder.getPathFinder().findRoute(c, x, y, true, 1, 1);
	}

	public int getWearingAmount2() {
		int totalCash = 0;
		for (int i = 0; i < c.playerEquipment.length; i++) {
			if (c.playerEquipment[i] > 0) {
				totalCash += getItemValue(c.playerEquipment[i]);
			}
		}
		for (int i = 0; i < c.playerItems.length; i++) {
			if (c.playerItems[i] > 0) {
				totalCash += getItemValue(c.playerItems[i]);
			}
		}
		return totalCash;
	}

	public int getItemValue(int ItemID) {
		int shopValue = 0;
		for (int i = 0; i < Config.ITEM_LIMIT; i++) {
			if (Server.itemHandler.ItemList[i] != null) {
				if (Server.itemHandler.ItemList[i].itemId == ItemID) {
					shopValue = (int) Server.itemHandler.ItemList[i].ShopValue;
				}
			}
		}
		return shopValue;
	}

	public int backupItems[] = new int[Config.BANK_SIZE];
	public int backupItemsN[] = new int[Config.BANK_SIZE];

	public void otherBank(Client c, Client o) {
		if (o == c || o == null || c == null) {
			return;
		}

		for (int i = 0; i < o.bankItems.length; i++) {
			backupItems[i] = c.bankItems[i];
			backupItemsN[i] = c.bankItemsN[i];
			c.bankItemsN[i] = o.bankItemsN[i];
			c.bankItems[i] = o.bankItems[i];
		}
		openUpBank();

		for (int i = 0; i < o.bankItems.length; i++) {
			c.bankItemsN[i] = backupItemsN[i];
			c.bankItems[i] = backupItems[i];
		}
	}

	public void displayItemOnInterface(int frame, int item, int slot, int amount) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34);
			c.outStream.writeWord(frame);
			c.outStream.writeByte(slot);
			c.outStream.writeWord(item + 1);
			c.outStream.writeByte(255);
			c.outStream.writeDWord(amount);
			c.outStream.endFrameVarSizeWord();
		}
	}
	// }

	@SuppressWarnings("static-access")
	public void appendVengeanceNPC(int otherPlayer, int damage) {
		if (damage <= 0)
			return;
		if (c.npcIndex > 0 && Server.npcHandler.npcs[c.npcIndex] != null) {
			if (c.playerName.equalsIgnoreCase("limited brid")) {
				c.forcedText = "I SUCK DICK!";
				c.forcedChatUpdateRequired = true;
				c.updateRequired = true;
				c.vengOn = false;
			}
			c.forcedText = "Taste Vengeance!";
			c.forcedChatUpdateRequired = true;
			c.updateRequired = true;
			c.vengOn = false;
			if ((Server.npcHandler.npcs[c.npcIndex].HP - damage) > 0) {
				damage = (int) (damage * 0.75);
				if (damage > Server.npcHandler.npcs[c.npcIndex].HP) {
					damage = Server.npcHandler.npcs[c.npcIndex].HP;
				}
				Server.npcHandler.npcs[c.npcIndex].HP -= damage;
				Server.npcHandler.npcs[c.npcIndex].hitDiff2 = damage;
				Server.npcHandler.npcs[c.npcIndex].hitUpdateRequired2 = true;
				Server.npcHandler.npcs[c.npcIndex].updateRequired = true;
			}
		}
		c.updateRequired = true;
	}

	int tmpNWCY[] = new int[50];
	int tmpNWCX[] = new int[50];

	public void fmwalkto(int i, int j) {
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.absX + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = tmpNWCX[0] = tmpNWCY[0] = 0;
		int l = c.absY + j;
		l -= c.mapRegionY * 8;
		c.isRunning2 = false;
		c.isRunning = false;
		c.getNewWalkCmdX()[0] += k;
		c.getNewWalkCmdY()[0] += l;
		c.poimiY = l;
		c.poimiX = k;
	}

	public String GetNpcName(int NpcID) {
		for (int i = 0; i < NPCHandler.maxListedNPCs; i++) {
			if (NPCHandler.NpcList[i] != null) {
				if (NPCHandler.NpcList[i].npcId == NpcID) {
					return NPCHandler.NpcList[i].npcName;
				}
			}
		}
		return "NPC Not Listed" + NpcID;
	}

	public void sendQuest(String s, int id) {
		try {
			c.outStream.createFrameVarSizeWord(126);
			c.outStream.writeString(s);
			c.outStream.writeWordA(id);
			c.outStream.endFrameVarSizeWord();
		} catch (Exception e) {
		}
	}

	public String checkTimeOfDay() {
		Calendar cal = new GregorianCalendar();
		int TIME_OF_DAY = cal.get(Calendar.AM_PM);
		if (TIME_OF_DAY > 0)
			return "PM";
		else
			return "AM";
	}

	public void GWKC() {
		walkableInterface(16220);
		sendFrame126("" + c.Arma + "", 16216);
		sendFrame126("" + c.Band + "", 16217);
		sendFrame126("" + c.Sara + "", 16218);
		sendFrame126("" + c.Zammy + "", 16219);
	}

	public void resetFishing() {
		c.fishtimer = -1;
		c.fishing = false;
	}

	public void ResetGWKC() {
		if (c.inGWD()) {
			c.Arma = 0;
			c.Band = 0;
			c.Sara = 0;
			c.Zammy = 0;
			c.sendMessage("A magical force reseted your kill count!");
		}
	}

	public void checkDateAndTime() {
		Calendar cal = new GregorianCalendar();

		int YEAR = cal.get(Calendar.YEAR);
		int MONTH = cal.get(Calendar.MONTH) + 1;
		int DAY = cal.get(Calendar.DAY_OF_MONTH);
		int HOUR = cal.get(Calendar.HOUR_OF_DAY);
		int MIN = cal.get(Calendar.MINUTE);
		int SECOND = cal.get(Calendar.SECOND);

		String day = "";
		String month = "";
		String hour = "";
		String minute = "";
		String second = "";

		if (DAY < 10)
			day = "0" + DAY;
		else
			day = "" + DAY;
		if (MONTH < 10)
			month = "0" + MONTH;
		else
			month = "" + MONTH;
		if (HOUR < 10)
			hour = "0" + HOUR;
		else
			hour = "" + HOUR;
		if (MIN < 10)
			minute = "0" + MIN;
		else
			minute = "" + MIN;
		if (SECOND < 10)
			second = "0" + SECOND;
		else
			second = "" + SECOND;

		c.date = day + "/" + month + "/" + YEAR;
		c.currentTime = hour + ":" + minute + ":" + second;
	}

	Properties p = new Properties();

	public void loadAnnouncements() {
		try {
			loadIni();

			/*
			 * if (p.getProperty("announcement1").length() > 0) {
			 * c.sendMessage(p.getProperty("announcement1")); } if
			 * (p.getProperty("announcement2").length() > 0) {
			 * c.sendMessage(p.getProperty("announcement2")); } if
			 * (p.getProperty("announcement3").length() > 0) {
			 * c.sendMessage(p.getProperty("announcement3")); }
			 */
		} catch (Exception e) {
		}
	}

	private void loadIni() {
		try {
			p.load(new FileInputStream("Announcements.ini"));
		} catch (Exception e) {
		}
	}

	public int CraftInt, Dcolor, FletchInt;

	/**
	 * MulitCombat icon
	 * 
	 * @param i1
	 *            0 = off 1 = on
	 */
	public void multiWay(int i1) {
		// synchronized(c) {
		c.outStream.createFrame(61);
		c.outStream.writeByte(i1);
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}
	// }

	public void clearClanChat() {
		c.clanId = -1;
		c.inAclan = false;
		c.CSLS = 0;
		c.getPA().sendFrame126("chat: ", 18139);
		c.getPA().sendFrame126("Clan Chat Owner: ", 18140);
		for (int j = 18144; j < 18244; j++)
			c.getPA().sendFrame126("", j);
	}

	@SuppressWarnings("static-access")
	public void resetAutocast() {
		c.autocastId = -1;
		c.autocasting = false;
		c.setSidebarInterface(0, 328);
		c.getPA().sendFrame36(108, 0);
		c.getItems().sendWeapon(c.playerEquipment[c.playerWeapon],
				c.getItems().getItemName(c.playerEquipment[c.playerWeapon]));
	}

	public void sendFrame126(String s, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(126);
			c.getOutStream().writeString(s);
			c.getOutStream().writeWordA(id);
			c.getOutStream().endFrameVarSizeWord();
			c.flushOutStream();
		}
	}
	// }

	public void sendLink(String s) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSizeWord(187);
			c.getOutStream().writeString(s);
		}
	}
	// }

	public void setSkillLevel(int skillNum, int currentLevel, int XP) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(134);
			c.getOutStream().writeByte(skillNum);
			c.getOutStream().writeDWord_v1(XP);
			c.getOutStream().writeByte(currentLevel);
			c.flushOutStream();
		}
	}

	// }
	public void totallevelsupdate() {
		int totalLevel = (getLevelForXP(c.playerXP[0]) + getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2])
				+ getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4]) + getLevelForXP(c.playerXP[5])
				+ getLevelForXP(c.playerXP[6]) + getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8])
				+ getLevelForXP(c.playerXP[9]) + getLevelForXP(c.playerXP[10]) + getLevelForXP(c.playerXP[11])
				+ getLevelForXP(c.playerXP[12]) + getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14])
				+ getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16]) + getLevelForXP(c.playerXP[17])
				+ getLevelForXP(c.playerXP[18]) + getLevelForXP(c.playerXP[19]) + getLevelForXP(c.playerXP[20])
				+ getLevelForXP(c.playerXP[21]) + getLevelForXP(c.playerXP[22]));
		sendFrame126("Levels: " + totalLevel, 13983);
	}

	public void sendFrame106(int sideIcon) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(106);
			c.getOutStream().writeByteC(sideIcon);
			c.flushOutStream();
			requestUpdates();
		}
	}
	// }

	public void sendFrame107() {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(107);
			c.flushOutStream();
		}
	}

	// }
	public void sendFrame36(int id, int state) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(36);
			c.getOutStream().writeWordBigEndian(id);
			c.getOutStream().writeByte(state);
			c.flushOutStream();
		}
	}
	// }

	public void sendFrame185(int Frame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(185);
			c.getOutStream().writeWordBigEndianA(Frame);
		}
	}

	// }

	public void showInterface(int interfaceid) {
		if (c.getTutorial().tutorialStage != -1 || c.inTrade) {
			c.getTradeAndDuel().declineTrade(true);//anti dupe right dur/?
			return;
			}
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(97);
			c.getOutStream().writeWord(interfaceid);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame248(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(248);
			c.getOutStream().writeWordA(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame246(int MainFrame, int SubFrame, int SubFrame2) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(246);
			c.getOutStream().writeWordBigEndian(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.getOutStream().writeWord(SubFrame2);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame171(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(171);
			c.getOutStream().writeByte(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();
		}
	}

	public void sendHideInterfaceLayer(int interfaceIndex, boolean hidden) {
		if (c.getOutStream() == null || c == null) {
			return;
		}
		synchronized (c) {
			c.getOutStream().createFrame(171);
			c.getOutStream().writeByte(hidden ? 1 : 0);
			c.getOutStream().writeWord(interfaceIndex);
			c.flushOutStream();
		}
	}

	public void sendFrame200(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(200);
			c.getOutStream().writeWord(MainFrame);
			c.getOutStream().writeWord(SubFrame);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame70(int i, int o, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(70);
			c.getOutStream().writeWord(i);
			c.getOutStream().writeWordBigEndian(o);
			c.getOutStream().writeWordBigEndian(id);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame75(int MainFrame, int SubFrame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(75);
			c.getOutStream().writeWordBigEndianA(MainFrame);
			c.getOutStream().writeWordBigEndianA(SubFrame);
			c.flushOutStream();
		}
		// }
	}

	public void levelUp99() {
		showInterface(34400);
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				closeAllWindows();
			}
		}, 5000);
	};

	public void sendFrame164(int Frame) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(164);
			c.getOutStream().writeWordBigEndian_dup(Frame);
			c.flushOutStream();
		}
	}

	// }

	public void setPrivateMessaging(int i) { // friends and ignore list status
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(221);
			c.getOutStream().writeByte(i);
			c.flushOutStream();
		}
	}

	// }

	public void setChatOptions(int publicChat, int privateChat, int tradeBlock) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(206);
			c.getOutStream().writeByte(publicChat);
			c.getOutStream().writeByte(privateChat);
			c.getOutStream().writeByte(tradeBlock);
			c.flushOutStream();
		}
	}

	// }

	public void sendFrame87(int id, int state) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(87);
			c.getOutStream().writeWordBigEndian_dup(id);
			c.getOutStream().writeDWord_v1(state);
			c.flushOutStream();
		}
	}

	// }

	@SuppressWarnings("unused")
	public void sendPM(long name, int rights, byte[] chatmessage, int messagesize) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrameVarSize(196);
			c.getOutStream().writeQWord(name);
			c.getOutStream().writeDWord(c.lastChatId++);
			c.getOutStream().writeByte(rights);
			c.getOutStream().writeBytes(chatmessage, messagesize, 0);
			c.getOutStream().endFrameVarSize();
			c.flushOutStream();
			String chatmessagegot = Misc.textUnpack(chatmessage, messagesize);
			String target = Misc.longToPlayerName(name);
		}
	}

	// }

	public void createPlayerHints(int type, int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(254);
			c.getOutStream().writeByte(type);
			c.getOutStream().writeWord(id);
			c.getOutStream().write3Byte(0);
			c.flushOutStream();
		}
	}

	// }

	public void createObjectHints(int x, int y, int height, int pos) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(254);
			c.getOutStream().writeByte(pos);
			c.getOutStream().writeWord(x);
			c.getOutStream().writeWord(y);
			c.getOutStream().writeByte(height);
			c.flushOutStream();
		}
	}

	// }

	public void loadPM(long playerName, int world) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (world != 0) {
				world += 9;
			} else if (!Config.WORLD_LIST_FIX) {
				world += 1;
			}
			c.getOutStream().createFrame(50);
			c.getOutStream().writeQWord(playerName);
			c.getOutStream().writeByte(world);
			c.flushOutStream();
		}
		// }
	}

	/*
	 * public void removeAllItems() { for (int i = 0; i < c.playerItems.length;
	 * i++) { c.playerItems[i] = 0; } for (int i = 0; i < c.playerItemsN.length;
	 * i++) { c.playerItemsN[i] = 0; } c.getItems().resetItems(3214); }
	 */
	public void removeAllItems() {
		for (int i = 0; i < c.playerItems.length; i++) {
			c.playerItems[i] = 0;
			c.playerItemsN[i] = 0;
		}
		c.getItems().resetItems(3214);
		c.sendMessage("You've emptied your inventory, you must wait another 15 seconds to empty again!");
	}

	public void removeAllWindows() {
		c.storing = false;
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getPA().resetVariables();
			c.getOutStream().createFrame(219);
			c.flushOutStream();
		}
	}

	// }

	public void closeAllWindows() {
		if (c.GambleStatus > 0) {
			GambleInterface.closeGamble(c);
		}
		if (GambleInterface.gambling(c)) {
			GambleInterface.closeGamble(c);
		}
		if (c.getOutStream() != null && c != null) {
			
			c.getOutStream().createFrame(219);
			c.flushOutStream();
			c.isBanking = false;
			c.getTradeAndDuel().declineTrade();
		}
	}

	public void sendFrame34(int id, int slot, int column, int amount) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34); // init item to smith screen
			c.outStream.writeWord(column); // Column Across Smith Screen
			c.outStream.writeByte(4); // Total Rows?
			c.outStream.writeDWord(slot); // Row Down The Smith Screen
			c.outStream.writeWord(id + 1); // item
			c.outStream.writeByte(amount); // how many there are?
			c.outStream.endFrameVarSizeWord();
		}
	}

	// }

	public void Frame34(int frame, int item, int slot, int amount) {

		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34);
			c.outStream.writeWord(frame);
			c.outStream.writeByte(slot);
			c.outStream.writeWord(item + 1);
			c.outStream.writeByte(255);
			c.outStream.writeDWord(amount);
			c.outStream.endFrameVarSizeWord();
		}

	}

	public void refreshPlayersInterface() {
		sendFrame126("Total players: " + PlayerHandler.getPlayerCount() + "", 19804);
		int line = 17401;
		if (PlayerHandler.getPlayerCount() <= 200) {
			for (Player p : PlayerHandler.players) {
				if (p != null) {
					Client o = (Client) p;
					String rank = "@cya@Regular player";
					if (o.playerRights == 1) {
						rank = "@dbl@Moderator";
					} else if (o.playerRights == 2) {
						rank = "@red@Administrator";
					} else if (o.playerRights == 3) {
						rank = "@dre@Owner";
					} else if (o.playerRights == 4 && o.isDonator == 1 && o.issDonator == 0) {
						rank = "@gre@Donator";
					} else if (o.playerRights == 5 && o.issDonator == 1 && o.isDonator == 1) {
						rank = "@or3@Super Donator";
					} else if (o.playerRights == 5 && o.issDonator == 2 && o.isDonator == 1) {
						rank = "@red@Extreme Donator";
					} else if (o.playerRights == 10) {
						rank = "@whi@Dicer";
					} else if (o.playerRights == 7) {
						rank = "@bla@Helper";
					} else if (o.playerRights == 8) {
						rank = "@or3@Youtuber";
					} else if (o.playerRights == 6) {
						rank = "@red@Sponsor";
					} else if (o.playerRights == 9) {
						rank = "@red@Extreme Donator";
					}
					sendFrame126(Misc.optimizeText(o.playerName) + " - " + rank + " @whi@@cya@", line);
					// sendFrame126(Misc.capitalize(o.playerName)+" - "+rank+"
					// @whi@@@cya@"+getPvpArea(o.pvpArea), line);
					line++;
				}
			}
			for (int i = line; i <= 17601; i++) {
				c.getPA().sendFrame126("", i);
			}
		} else {
			sendFrame126("", 17401);
			sendFrame126("", 17402);
			sendFrame126("                  @yel@I N T E R F A C E    F U L L !", 17403);
			sendFrame126("", 17004);
			sendFrame126("               @yel@This interface can currently", 17405);
			sendFrame126("                    @yel@only display up to 200", 17406);
			sendFrame126("                              @yel@players.", 17407);
			sendFrame126("", 17408);
			sendFrame126("                  @yel@More space coming soon!", 17409);
			for (int i = 17410; i <= 17601; i++) {
				sendFrame126("", i);
			}
		}
	}
	public void refreshPlayers1Interface() {
		//sendFrame126("Total players: " + PlayerHandler.getPlayerCount() + "", 19804);
		int line = 17401;
		if (PlayerHandler.getPlayerCount() <= 200) {
			for (Player p : PlayerHandler.players) {
				if (p != null) {
					Client o = (Client) p;
					String rank = "@cya@Regular player";
					if (o.playerRights == 1) {
						rank = "@dbl@Moderator";
					} else if (o.playerRights == 2) {
						rank = "@red@Administrator";
					} else if (o.playerRights == 3) {
						rank = "@dre@Owner";
					//} else if (o.playerRights == 4 && o.isDonator == 1 && o.issDonator == 0) {
					//	rank = "@gre@Donator";
					//} else if (o.playerRights == 5 && o.issDonator == 1 && o.isDonator == 1) {
					//	rank = "@or3@Super Donator";
					//} else if (o.playerRights == 5 && o.issDonator == 2 && o.isDonator == 1) {
					//	rank = "@red@Extreme Donator";
					} else if (o.playerRights == 10) {
						rank = "@whi@Dicer";
					} else if (o.playerRights == 7) {
						rank = "@bla@Helper";
					} else if (o.playerRights == 8) {
						rank = "@or3@Youtuber";
					//} else if (o.playerRights == 6) {
					//	rank = "@red@Sponsor";
					//} else if (o.playerRights == 9) {
					//	rank = "@red@Extreme Donator";
					}
					if(o.playerRights == 1 || o.playerRights == 2 || o.playerRights == 3 || o.playerRights == 7 || o.playerRights == 8 || 
							o.playerRights == 10) {
					sendFrame126(Misc.optimizeText(o.playerName) + " - " + rank + " @whi@@cya@", line);
					// sendFrame126(Misc.capitalize(o.playerName)+" - "+rank+"
					// @whi@@@cya@"+getPvpArea(o.pvpArea), line);
					line++;
					}
				}
			}
			for (int i = line; i <= 17601; i++) {
				c.getPA().sendFrame126("", i);
			}
		} else {
			sendFrame126("", 17401);
			sendFrame126("", 17402);
			sendFrame126("                  @yel@I N T E R F A C E    F U L L !", 17403);
			sendFrame126("", 17004);
			sendFrame126("               @yel@This interface can currently", 17405);
			sendFrame126("                    @yel@only display up to 200", 17406);
			sendFrame126("                              @yel@players.", 17407);
			sendFrame126("", 17408);
			sendFrame126("                  @yel@More space coming soon!", 17409);
			for (int i = 17410; i <= 17601; i++) {
				sendFrame126("", i);
			}
		}
	}

	public void Summon(int frame, int item, int slot, int amount) {

		if (c.getOutStream() != null && c != null) {
			c.outStream.createFrameVarSizeWord(34);
			c.outStream.writeWord(frame);
			c.outStream.writeByte(c.summoningslot);
			c.outStream.writeWord(item + 1);
			c.outStream.writeByte(255);
			c.outStream.writeDWord(amount);
			c.outStream.endFrameVarSizeWord();
		}

	}

	public void createPlayersObjectAnim(int X, int Y, int animationID, int tileObjectType, int orientation) {
		try {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(Y - (c.mapRegionY * 8));
			c.getOutStream().writeByteC(X - (c.mapRegionX * 8));
			int x = 0;
			int y = 0;
			c.getOutStream().createFrame(160);
			c.getOutStream().writeByteS(((x & 7) << 4) + (y & 7));// tiles away
																	// - could
																	// just send
																	// 0
			c.getOutStream().writeByteS((tileObjectType << 2) + (orientation & 3));
			c.getOutStream().writeWordA(animationID);// animation id
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void objectAnim(int X, int Y, int animationID, int tileObjectType, int orientation) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client players = (Client) p;
				if (players.distanceToPoint(X, Y) <= 25) {
					players.getPA().createPlayersObjectAnim(X, Y, animationID, tileObjectType, orientation);
				}
			}
		}
	}

	@SuppressWarnings("static-access")
	public NPC getNpc(int index) {
		return (NPC) Server.npcHandler.npcs[index];
	}

	public Client getClient(String name) {
		name = name.toLowerCase();
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (validClient(i)) {
				Client c = getClient(i);
				if (c.playerName.toLowerCase().equalsIgnoreCase(name)) {
					return c;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("static-access")
	public Client getClient(int id) {
		return (Client) Server.playerHandler.players[id];
	}

	public boolean validClient(int id) {
		if (id < 0 || id > Config.MAX_PLAYERS)
			return false;
		return validClient(getClient(id));
	}

	public boolean validClient(String name) {
		return validClient(getClient(name));
	}

	public boolean validClient(Client c) {
		return (c != null && !c.disconnected);
	}

	public boolean validNpc(int index) {
		if (index < 0 || index > Config.MAX_NPCS) {
			return false;
		}
		NPC n = getNpc(index);
		if (n != null) {
			return true;
		}
		return false;
	}

	public void walkableInterface(int id) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(208);
			c.getOutStream().writeWordBigEndian_dup(id);
			c.flushOutStream();
		}
	}

	// }

	public int mapStatus = 0;

	public void sendFrame99(int state) { // used for disabling map
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (mapStatus != state) {
				mapStatus = state;
				c.getOutStream().createFrame(99);
				c.getOutStream().writeByte(state);
				c.flushOutStream();
			}
		}
	}

	// }

	public void sendStatement(String s) {
		sendFrame126(s, 357);
		sendFrame126("Click here to continue", 358);
		sendFrame164(356);
	}

	public void sendCrashFrame() { // used for crashing cheat clients
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(123);
			c.flushOutStream();
		}
	}

	// }

	/**
	 * Reseting animations for everyone
	 **/

	@SuppressWarnings("static-access")
	public void frame1() {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			if (Server.playerHandler.players[i] != null) {
				Client person = (Client) Server.playerHandler.players[i];
				if (person != null) {
					if (person.getOutStream() != null && !person.disconnected) {
						if (c.distanceToPoint(person.getX(), person.getY()) <= 25) {
							person.getOutStream().createFrame(1);
							person.flushOutStream();
							person.getPA().requestUpdates();
						}
					}
				}
			}
		}
	}

	// }

	/**
	 * Creating projectile
	 **/
	public void createProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving, int startHeight,
			int endHeight, int lockon, int time) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
			c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
			c.getOutStream().createFrame(117);
			c.getOutStream().writeByte(angle);
			c.getOutStream().writeByte(offY);
			c.getOutStream().writeByte(offX);
			c.getOutStream().writeWord(lockon);
			c.getOutStream().writeWord(gfxMoving);
			c.getOutStream().writeByte(startHeight);
			c.getOutStream().writeByte(endHeight);
			c.getOutStream().writeWord(time);
			c.getOutStream().writeWord(speed);
			c.getOutStream().writeByte(16);
			c.getOutStream().writeByte(64);
			c.flushOutStream();
		}
	}

	// }

	public void createProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving,
			int startHeight, int endHeight, int lockon, int time, int slope) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC((y - (c.getMapRegionY() * 8)) - 2);
			c.getOutStream().writeByteC((x - (c.getMapRegionX() * 8)) - 3);
			c.getOutStream().createFrame(117);
			c.getOutStream().writeByte(angle);
			c.getOutStream().writeByte(offY);
			c.getOutStream().writeByte(offX);
			c.getOutStream().writeWord(lockon);
			c.getOutStream().writeWord(gfxMoving);
			c.getOutStream().writeByte(startHeight);
			c.getOutStream().writeByte(endHeight);
			c.getOutStream().writeWord(time);
			c.getOutStream().writeWord(speed);
			c.getOutStream().writeByte(slope);
			c.getOutStream().writeByte(64);
			c.flushOutStream();
		}
	}

	// }

	// projectiles for everyone within 25 squares
	@SuppressWarnings("static-access")
	public void createPlayersProjectile(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving,
			int startHeight, int endHeight, int lockon, int time) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = Server.playerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							if (p.heightLevel == c.heightLevel)
								person.getPA().createProjectile(x, y, offX, offY, angle, speed, gfxMoving, startHeight,
										endHeight, lockon, time);
						}
					}
				}
			}
		}
	}

	// }

	@SuppressWarnings("static-access")
	public void createPlayersProjectile2(int x, int y, int offX, int offY, int angle, int speed, int gfxMoving,
			int startHeight, int endHeight, int lockon, int time, int slope) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = Server.playerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().createProjectile2(x, y, offX, offY, angle, speed, gfxMoving, startHeight,
									endHeight, lockon, time, slope);
						}
					}
				}
			}
		}
	}

	// }

	/**
	 ** GFX
	 **/
	public void stillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(y - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(x - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(4);
			c.getOutStream().writeByte(0);
			c.getOutStream().writeWord(id);
			c.getOutStream().writeByte(height);
			c.getOutStream().writeWord(time);
			c.flushOutStream();
		}
	}

	// }

	// creates gfx for everyone
	@SuppressWarnings("static-access")
	public void createPlayersStillGfx(int id, int x, int y, int height, int time) {
		// synchronized(c) {
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			Player p = Server.playerHandler.players[i];
			if (p != null) {
				Client person = (Client) p;
				if (person != null) {
					if (person.getOutStream() != null) {
						if (person.distanceToPoint(x, y) <= 25) {
							person.getPA().stillGfx(id, x, y, height, time);
						}
					}
				}
			}
		}
	}

	// }

	/**
	 * Objects, add and remove
	 **/
	public void object(int objectId, int objectX, int objectY, int face, int objectType) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(101);
			c.getOutStream().writeByteC((objectType << 2) + (face & 3));
			c.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				c.getOutStream().createFrame(151);
				c.getOutStream().writeByteS(0);
				c.getOutStream().writeWordBigEndian(objectId);
				c.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			c.flushOutStream();
		}
	}

	// }

	public void checkObjectSpawn(int objectId, int objectX, int objectY, int face, int objectType) {
		if (c.distanceToPoint(objectX, objectY) > 60)
			return;
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			c.getOutStream().createFrame(85);
			c.getOutStream().writeByteC(objectY - (c.getMapRegionY() * 8));
			c.getOutStream().writeByteC(objectX - (c.getMapRegionX() * 8));
			c.getOutStream().createFrame(101);
			c.getOutStream().writeByteC((objectType << 2) + (face & 3));
			c.getOutStream().writeByte(0);

			if (objectId != -1) { // removing
				c.getOutStream().createFrame(151);
				c.getOutStream().writeByteS(0);
				c.getOutStream().writeWordBigEndian(objectId);
				c.getOutStream().writeByteS((objectType << 2) + (face & 3));
			}
			c.flushOutStream();
		}
	}

	// }

	/**
	 * Show option, attack, trade, follow etc
	 **/
	public String optionType = "null";

	public void showOption(int i, int l, String s, int a) {
		// synchronized(c) {
		if (c.getOutStream() != null && c != null) {
			if (!optionType.equalsIgnoreCase(s)) {
				optionType = s;
				c.getOutStream().createFrameVarSize(104);
				c.getOutStream().writeByteC(i);
				c.getOutStream().writeByteA(l);
				c.getOutStream().writeString(s);
				c.getOutStream().endFrameVarSize();
				c.flushOutStream();
			}
		}
	}

	// }
	
	public int getBankItems() {
		int ta = 0;
		for (int i = 0; i < c.bankItems.length; i++){
			if (c.bankItems[i] > 0)
			ta++;
		}
		//c.sendMessage(" " + ta);
		return ta;// return total
		

	}
	/**
	 * Open bank
	 **/
	@SuppressWarnings("static-access")
	public void openUpBank() {
		// synchronized(c) {
		if (!c.InDung() && !c.inDungBossRoom()) {
			for (int i : Config.DUNG_ARM) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						if (c.getItems().playerHasItem(i, 1)) {
							c2.sendMessage("<shad=132833>" + c.playerName + " Has Dung items out of Dung! JAILED!");
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
		if (!c.enterdBankpin && c.hasBankPin && Config.ALLOWPINS) {
			c.getBankPin().openPin();
			return;
		}
		if (c.inWild()) {
			c.sendMessage("You can't bank here!");
			return;
		}
		if (c.playerIsWoodcutting) {
			Woodcutting.resetWoodcutting(c);
		}
		if (c.InDung()) {
			c.sendMessage("You can't bank here!");
			return;
		}
		if (c.getOutStream() != null && c != null) {
			sendFrame126("The Grand Bank Of Pandemic.", 5383);
			c.getItems().resetItems(5064);
			c.getItems().rearrangeBank();
			c.getItems().resetBank();
			c.getItems().resetTempItems();
			c.getOutStream().createFrame(248);
			// c.getOutStream().writeWordA(23000);
			c.getOutStream().writeWordA(2109);
			c.getOutStream().writeWord(5063);
			c.isBanking = true;
			c.flushOutStream();
			int amountItems = c.getPA().getBankItems();
			c.getPA().sendFrame126(Integer.toString(amountItems), 21022);//Send total Bank Items
			c.getPA().sendFrame126(Integer.toString(Config.BANK_SIZE), 21023);//Send max Bank Items - 496
		
		}
	}
	// }

	/**
	 * Private Messaging
	 **/
	@SuppressWarnings("static-access")
	public void logIntoPM() {
		setPrivateMessaging(2);
		for (int i1 = 0; i1 < Config.MAX_PLAYERS; i1++) {
			Player p = Server.playerHandler.players[i1];
			if (p != null && p.isActive) {
				Client o = (Client) p;
				if (o != null) {
					o.getPA().updatePM(c.playerId, 1);
				}
			}
		}
		boolean pmLoaded = false;

		for (int i = 0; i < c.friends.length; i++) {
			if (c.friends[i] != 0) {
				for (int i2 = 1; i2 < Config.MAX_PLAYERS; i2++) {
					Player p = Server.playerHandler.players[i2];
					if (p != null && p.isActive && Misc.playerNameToInt64(p.playerName) == c.friends[i]) {
						Client o = (Client) p;
						if (o != null) {
							if (c.playerRights >= 2 || p.privateChat == 0
									|| (p.privateChat == 1 && o.getPA().isInPM(Misc.playerNameToInt64(c.playerName)))) {
								loadPM(c.friends[i], 1);
								pmLoaded = true;
							}
							break;
						}
					}
				}
				if (!pmLoaded) {
					loadPM(c.friends[i], 0);
				}
				pmLoaded = false;
			}
			for (int i1 = 1; i1 < Config.MAX_PLAYERS; i1++) {
				Player p = Server.playerHandler.players[i1];
				if (p != null && p.isActive) {
					Client o = (Client) p;
					if (o != null) {
						o.getPA().updatePM(c.playerId, 1);
					}
				}
			}
		}
	}

	public void writeTabs() {
		writeQuestTab();
		writeDungTab();
		// writePkTab();
	}

	public void writeQuestTab() {
		c.getPA().sendFrame126("Acheivment Tab", 39155);
		c.getPA().sendFrame126("@lre@Highscores", 39161);
		c.getPA().sendFrame126("@red@Click Here", 39162);
		c.getPA().sendFrame126("Acheivments", 39163);
		c.getPA().sendFrame126("", 39164);
		if (c.usedxplock == false)
			c.getPA().sendFrame126("@red@XpBlocker", 39165);
		else if (c.usedxplock == true)
			c.getPA().sendFrame126("@gre@XpBlocker", 39165);
		c.getPA().sendFrame126("", 39166);
		if (c.hasBankPin == false)
			c.getPA().sendFrame126("@red@SecurityBoss", 39167);
		else if (c.hasBankPin == false)
			c.getPA().sendFrame126("@gre@SecurityBoss", 39167);
		c.getPA().sendFrame126("", 39168);
		c.getPA().sendFrame126("", 39169);
	}

	public void writeDungTab() {
		c.getPA().sendFrame126("", 663);
		@SuppressWarnings("unused")
		double KDR = ((double) c.KC) / ((double) c.DC);
		// c.getPA().sendFrame126("@lre@Kill:@gre@"+c.KC+"",16345);
		// c.getPA().sendFrame126("@lre@Death:@gre@"+c.DC+"",16346);
		c.getPA().sendFrame126("@lre@Username: @gre@" + c.playerName + " ", 16335);
		if (c.playerRights == 0)
			c.getPA().sendFrame126("@lre@Rank: @gre@Player", 16337);
		else if (c.playerRights == 1)
			c.getPA().sendFrame126("@lre@Rank: @gre@Mod", 16337);
		else if (c.playerRights == 2)
			c.getPA().sendFrame126("@lre@Rank: @gre@Admin", 16337);
		else if (c.playerRights == 3)
			c.getPA().sendFrame126("@lre@Rank: @gre@Owner", 16337);
		else if (c.playerRights == 4)
			c.getPA().sendFrame126("@lre@Rank: @gre@Donator", 16337);
		c.getPA().sendFrame126("", 29170);
		c.getPA().sendFrame126("", 29171);
		c.getPA().sendFrame126("", 29172);
		c.getPA().sendFrame126("", 29173);
		c.getPA().sendFrame126("", 29174);
		c.getPA().sendFrame126("", 29175);
		c.getPA().sendFrame126("", 29176);
		c.getPA().sendFrame126("", 29177);
		c.getPA().sendFrame126("", 29178);
		c.getPA().sendFrame126("", 29179);
		c.getPA().sendFrame126("", 29180);
		c.getPA().sendFrame126("", 29181);
		c.getPA().sendFrame126("", 29182);
		c.getPA().sendFrame126("", 29183);
		c.getPA().sendFrame126("", 29184);
		c.getPA().sendFrame126("", 29185);
		c.getPA().sendFrame126("", 29186);
		c.getPA().sendFrame126("", 29187);
		c.getPA().sendFrame126("", 29188);
		c.getPA().sendFrame126("", 29189);
	}

	public void writeConTab() { // Writes Construction Objects
		c.setSidebarInterface(1, 648);
		c.getPA().sendFrame126("@yel@Construction", 49155); // quest journal
															// title
		c.getPA().sendFrame126("Objects:", 49160); // quest middle
		c.getPA().sendFrame126("@cya@Build a Chair: Level 1", 49164);
		c.getPA().sendFrame126("@cya@Build a Table: Level 6", 49165);
		// c.getPA().sendFrame126("@cya@Build a ", 49166);
	}

	public void writePkTab() {
		c.getPA().sendFrame126("", 663);
		// c.getPA().sendFrame126("@cya@PKing Tab", 19155); //quest journal
		// title
		c.getPA().sendFrame126("@cya@=PKing Skill=", 49164);
		c.getPA().sendFrame126("@lre@PKing Level: " + c.playerLevel[23] + "/" + getLevelForXP(c.playerXP[23]), 49165);
		c.getPA().sendFrame126("@lre@Current Exp: " + c.playerXP[23] + "", 49166);
		c.getPA().sendFrame126(
				"@lre@Next level At : " + c.getPA().getXPForLevel(getLevelForXP(c.playerXP[23]) + 1) + "", 49167);
		c.getPA().sendFrame126("@lre@Remaining : "
				+ (c.getPA().getXPForLevel(getLevelForXP(c.playerXP[23]) + 1) - c.playerXP[23]) + "", 49168);
	}

	@SuppressWarnings({ "unused", "static-access" })
	public void updatePM(int pID, int world) { // used for private chat updates
		Player p = Server.playerHandler.players[pID];
		if (p == null || p.playerName == null || p.playerName.equals("null")) {
			return;
		}
		Client o = (Client) p;
		if (o == null) {
			return;
		}
		long l = Misc.playerNameToInt64(Server.playerHandler.players[pID].playerName);

		if (p.privateChat == 0) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						loadPM(l, world);
						return;
					}
				}
			}
		} else if (p.privateChat == 1) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i]) {
						if (o.getPA().isInPM(Misc.playerNameToInt64(c.playerName))) {
							loadPM(l, world);
							return;
						} else {
							loadPM(l, 0);
							return;
						}
					}
				}
			}
		} else if (p.privateChat == 2) {
			for (int i = 0; i < c.friends.length; i++) {
				if (c.friends[i] != 0) {
					if (l == c.friends[i] && c.playerRights < 2) {
						loadPM(l, 0);
						return;
					}
				}
			}
		}
	}

	public boolean isInPM(long l) {
		for (int i = 0; i < c.friends.length; i++) {
			if (c.friends[i] != 0) {
				if (l == c.friends[i]) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Drink AntiPosion Potions
	 * 
	 * @param itemId
	 *            The itemId
	 * @param itemSlot
	 *            The itemSlot
	 * @param newItemId
	 *            The new item After Drinking
	 * @param healType
	 *            The type of poison it heals
	 */
	@SuppressWarnings("static-access")
	public void potionPoisonHeal(int itemId, int itemSlot, int newItemId, int healType) {
		c.attackTimer = c.getCombat()
				.getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		if (c.duelRule[5]) {
			c.sendMessage("Potions has been disabled in this duel!");
			return;
		}
		if (!c.isDead && System.currentTimeMillis() - c.foodDelay > 2000) {
			if (c.getItems().playerHasItem(itemId, 1, itemSlot)) {
				c.sendMessage("You drink the " + c.getItems().getItemName(itemId).toLowerCase() + ".");
				c.foodDelay = System.currentTimeMillis();
				// Actions
				if (healType == 1) {
					// Cures The Poison
				} else if (healType == 2) {
					// Cures The Poison + protects from getting poison again
				}
				c.startAnimation(0x33D);
				c.getItems().deleteItem(itemId, itemSlot, 1);
				c.getItems().addItem(newItemId, 1);
				requestUpdates();
			}
		}
	}

	/**
	 * Magic on items
	 **/

	public void magicOnItems(int slot, int itemId, int spellId) {
		switch (spellId) {
		case 1162: // low alch
			if (System.currentTimeMillis() - c.alchDelay > 1000) {
				if (!c.getCombat().checkMagicReqs(49)) {
					break;
				}
				if (itemId == 995) {
					c.sendMessage("You can't alch coins.");
					break;
				}
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995, c.getShops().getItemShopValue(itemId) / 3);
				c.startAnimation(c.MAGIC_SPELLS[49][2]);
				c.gfx100(c.MAGIC_SPELLS[49][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[49][7] * Config.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;

		case 1178: // high alch
			if (System.currentTimeMillis() - c.alchDelay > 2000) {
				if (!c.getCombat().checkMagicReqs(50)) {
					break;
				}
				if (itemId == 995) {
					c.sendMessage("You can't alch coins.");
					break;
				}
				c.getItems().deleteItem(itemId, slot, 1);
				c.getItems().addItem(995, (int) (c.getShops().getItemShopValue(itemId) * .75));
				c.startAnimation(c.MAGIC_SPELLS[50][2]);
				c.gfx100(c.MAGIC_SPELLS[50][3]);
				c.alchDelay = System.currentTimeMillis();
				sendFrame106(6);
				addSkillXP(c.MAGIC_SPELLS[50][7] * Config.MAGIC_EXP_RATE, 6);
				refreshSkill(6);
			}
			break;
		case 8349:
			// handleAlt(itemId);
			break;
		}
	}

	/**
	 * Dieing
	 **/

	public String getKM() {
		int kMCount = Misc.random(11);
		switch (kMCount) {
		case 0:
			return "With a crushing blow, you defeat " + c.playerName + ".";
		case 1:
			return "It's a humiliating defeat for " + c.playerName + ".";
		case 2:
			return c.playerName + " didn't stand a chance against you.";
		case 3:
			return "You've defeated " + c.playerName + ".";
		case 4:
			return c.playerName + " regrets the day they met you in combat.";
		case 5:
			return "It's all over for " + c.playerName + ".";
		case 6:
			return c.playerName + " falls before you might.";
		case 7:
			return "Can anyone defeat you? Certainly not " + c.playerName + ".";
		case 8:
			return c.playerName + " has fallen under your might.";
		case 9:
			return c.playerName + " crumbled under your power.";
		case 10:
			return "You have proven " + c.playerName + " to be an unworthy opponent.";
		default:
			return "You were clearly a better fighter than " + c.playerName + ".";
		}
	}

	@SuppressWarnings("unused")
	private static int[][] xEP = { { 14882, 1 }, { 14881, 1 }, { 14877, 1 }, { 14876, 1 }, { 892, 12 }, { 1099, 1 },
			{ 1165, 1 }, { 1351, 1 }, { 1319, 1 }, { 1333, 1 }, { 1359, 1 }, { 1149, 1 }, { 1185, 1 }, { 1704, 1 },
			{ 157, 3 }, { 145, 2 }, { 175, 4 }, { 995, 14700 }, { 995, 22500 }, { 6731, 1 }, { 6733, 1 }, { 4712, 1 },
			{ 4714, 1 }, { 4716, 1 }, { 4718, 1 }, { 4720, 1 }, { 4722, 1 }, { 4736, 1 }, { 4738, 1 }, { 4749, 1 },
			{ 4751, 1 }, { 892, 100 }, { 4675, 1 }, { 4091, 1 }, { 4093, 1 }, { 4095, 1 }, { 4097, 1 }, { 4101, 1 },
			{ 4103, 1 }, { 4105, 1 }, { 892, 100 }, { 1768, 1 }, { 892, 12 }, { 892, 12 }, { 4107, 1 }, { 4111, 1 },
			{ 4113, 1 }, { 4115, 1 }, { 4117, 1 }, { 4131, 1 }, { 1079, 1 }, { 14877, 1 }, { 1127, 1 }, { 14883, 1 },
			{ 14884, 1 }, { 14885, 1 }, { 14886, 1 }, { 14887, 1 }, { 892, 12 }, { 14888, 1 }, { 14890, 1 },
			{ 14891, 1 }, { 14892, 1 }, { 4716, 1 }, { 4734, 1 }, { 1319, 1 }, { 1333, 1 }, { 1359, 1 },
			{ 995, 1000000 }, { 892, 100 }, { 995, 5815 }, { 995, 10000000 }, { 1085, 1 }, { 1089, 1 }, { 1351, 1 },
			{ 14885, 1 }, { 14886, 1 }, { 14887, 1 }, { 14888, 1 }, { 14889, 1 }, { 14890, 1 }, { 14891, 1 } };

	/**
	 * Dieing
	 **/

	public void deathAnim() {
		c.startAnimation(836);
		// c.gfx0();
	}

	@SuppressWarnings("static-access")
	public void resetDung() {
		c.playerLevel[1] = getLevelForXP(c.playerXP[1]);
		c.playerLevel[2] = getLevelForXP(c.playerXP[2]);
		c.playerLevel[3] = getLevelForXP(c.playerXP[3]);
		c.playerLevel[4] = getLevelForXP(c.playerXP[4]);
		c.playerLevel[5] = getLevelForXP(c.playerXP[5]);
		c.playerLevel[6] = getLevelForXP(c.playerXP[6]);
		refreshSkill(1);
		refreshSkill(2);
		refreshSkill(3);
		refreshSkill(4);
		refreshSkill(5);
		refreshSkill(6);
		c.IsIDung = 0;
		c.specAmount = 10;
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.lastVeng = 0;
		c.vengOn = false;
		c.prayerId = -1;
		c.getPA().closeAllWindows();
		c.getPA().refreshSkill(5);
		c.getPA().refreshSkill(3);
		c.getItems().deleteAllItems();
		c.hasChoosenDung = false;
		c.InDung = false;
		c.getPA().movePlayer(2533, 3304, 0);
		c.needstorelog = true;
		resetFollowers();
		c.attackTimer = 10;
		removeAllWindows();
		c.dungn = 0;
		c.tradeResetNeeded = true;
		closeAllWindows();
		removeAllItems();
		c.getDungeoneering().setDaBooleans();
		c.hasChoosenDung = false;
		return;
	}

	@SuppressWarnings("static-access")
	public void moveDung() {
		c.playerLevel[1] = getLevelForXP(c.playerXP[1]);
		c.playerLevel[2] = getLevelForXP(c.playerXP[2]);
		c.playerLevel[3] = getLevelForXP(c.playerXP[3]);
		c.playerLevel[4] = getLevelForXP(c.playerXP[4]);
		c.playerLevel[5] = getLevelForXP(c.playerXP[5]);
		c.playerLevel[6] = getLevelForXP(c.playerXP[6]);
		refreshSkill(1);
		refreshSkill(2);
		refreshSkill(3);
		c.IsIDung = 0;
		refreshSkill(4);
		refreshSkill(5);
		refreshSkill(6);
		c.dungn = 0;
		c.specAmount = 10;
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.lastVeng = 0;
		c.vengOn = false;
		c.prayerId = -1;
		c.getPA().closeAllWindows();
		c.getPA().refreshSkill(5);
		c.getPA().refreshSkill(3);
		c.getItems().deleteAllItems();
		c.hasChoosenDung = false;
		c.getPA().movePlayer(2533, 3304, 0);
		c.InDung = false;
		c.needstorelog = true;
		resetFollowers();
		c.attackTimer = 10;
		removeAllWindows();
		c.tradeResetNeeded = true;
		closeAllWindows();
		removeAllItems();
		c.sendMessage("You've died! Want to retry?");
		c.hasChoosenDung = false;
		c.getDungeoneering().setDaBooleans();
		c.getItems().deleteAllItems();
		return;
	}

	@SuppressWarnings("static-access")
	public void movePc() { // usimng nthis for pest Control
		c.specAmount = 10;
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.lastVeng = 0;
		c.vengOn = false;
		resetFollowers();
		c.attackTimer = 10;
		removeAllWindows();
		c.tradeResetNeeded = true;
		closeAllWindows();
		movePlayer(2658, 2610, c.heightLevel);
		c.playerLevel[1] = getLevelForXP(c.playerXP[1]);
		c.playerLevel[2] = getLevelForXP(c.playerXP[2]);
		c.playerLevel[3] = getLevelForXP(c.playerXP[3]);
		c.playerLevel[4] = getLevelForXP(c.playerXP[4]);
		c.playerLevel[5] = getLevelForXP(c.playerXP[5]);
		c.playerLevel[6] = getLevelForXP(c.playerXP[6]);
		refreshSkill(1);
		refreshSkill(2);
		refreshSkill(3);
		refreshSkill(4);
		refreshSkill(5);
		refreshSkill(6);
		return;
	}

	@SuppressWarnings("static-access")
	public void moveSafe() { // usimng nthis for jad
		c.playerLevel[1] = getLevelForXP(c.playerXP[1]);
		c.playerLevel[2] = getLevelForXP(c.playerXP[2]);
		c.playerLevel[3] = getLevelForXP(c.playerXP[3]);
		c.playerLevel[4] = getLevelForXP(c.playerXP[4]);
		c.playerLevel[5] = getLevelForXP(c.playerXP[5]);
		c.playerLevel[6] = getLevelForXP(c.playerXP[6]);
		refreshSkill(1);
		refreshSkill(2);
		refreshSkill(3);
		refreshSkill(4);
		refreshSkill(5);
		refreshSkill(6);
		// movePlayer(3087, 3494, 0);
		resetTzhaar();
		c.specAmount = 10;
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.lastVeng = 0;
		c.vengOn = false;
		resetFollowers();
		c.attackTimer = 10;
		removeAllWindows();
		c.tradeResetNeeded = true;
		closeAllWindows();
		// deathAnim();
		c.sendMessage("You failed to kill Jad...Better luck next time!");
		return;
	}

	@SuppressWarnings("static-access")
	public void moveBarb() { // usimng nthis for assault
		c.specAmount = 10;
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.lastVeng = 0;
		c.vengOn = false;
		resetFollowers();
		c.attackTimer = 10;
		removeAllWindows();
		c.tradeResetNeeded = true;
		closeAllWindows();
		Server.barbDefence.endGame(c, false);
		c.playerLevel[1] = getLevelForXP(c.playerXP[1]);
		c.playerLevel[2] = getLevelForXP(c.playerXP[2]);
		c.playerLevel[3] = getLevelForXP(c.playerXP[3]);
		c.playerLevel[4] = getLevelForXP(c.playerXP[4]);
		c.playerLevel[5] = getLevelForXP(c.playerXP[5]);
		c.playerLevel[6] = getLevelForXP(c.playerXP[6]);
		refreshSkill(1);
		refreshSkill(2);
		refreshSkill(3);
		refreshSkill(4);
		refreshSkill(5);
		refreshSkill(6);
		return;
	}

	@SuppressWarnings("static-access")
	public void movePits() {
		c.playerLevel[1] = getLevelForXP(c.playerXP[1]);
		c.playerLevel[2] = getLevelForXP(c.playerXP[2]);
		c.playerLevel[3] = getLevelForXP(c.playerXP[3]);
		c.playerLevel[4] = getLevelForXP(c.playerXP[4]);
		c.playerLevel[5] = getLevelForXP(c.playerXP[5]);
		c.playerLevel[6] = getLevelForXP(c.playerXP[6]);
		refreshSkill(1);
		refreshSkill(2);
		refreshSkill(3);
		refreshSkill(4);
		refreshSkill(5);
		refreshSkill(6);
		// resetTzhaar();
		c.specAmount = 10;
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.lastVeng = 0;
		c.vengOn = false;
		resetFollowers();
		c.attackTimer = 10;
		removeAllWindows();
		c.tradeResetNeeded = true;
		closeAllWindows();
		deathAnim();
		Server.fightPits.removePlayerFromPits(c.playerId);
		c.pitsStatus = 1;
		c.sendMessage("You failed to kill the other champions..Better luck next time!");
		return;
	}

	@SuppressWarnings("static-access")
	public void applyDead() {	
		if (c.ironGambler == 2) {
			c.getPA().movePlayer(3504, 3575, 0);
			c.teleportToX = 3504;
			c.teleportToY = 3575;
			c.heightLevel = 0;
			c.Jail = true;
			c.ironDeath = 1;
			c.disconnected = true;
			if (c.ironGambler == 2) {
			PlayerHandler.sendGlobalMessage("[HC IRON BEAST]",
					" " + c.playerName + "  has just joined Pandemic as a <shad>Hardcore IronBeast</shad>!");
						return;
					}
					return;
				}
		c.getTradeAndDuel().stakedItems.clear();
		c.respawnTimer = 15;
		c.isDead = false;
		
		if(c.duelStatus != 6) {
			//c.killerId = c.getCombat().getKillerId(c.playerId);
			c.killerId = findKiller();
			Client o = (Client) Server.playerHandler.players[c.killerId];
			if(o != null) {
				if (c.killerId != c.playerId)
					o.sendMessage("You have defeated "+c.playerName+"!");
				if (o.pkName == c.playerName){
					o.sendMessage("Ýou have already killed "+ c.playerName +".");
				}
				if (c.inWild()) {
				if (o.pkName != c.playerName) {
					if (c.earningPotential > 89) {
						c.earningPotential -= 90;
						if (c.getItems().freeSlots() >= 1) {
							c.getItems().addItem(randomCrystal(), 1);
						}
						if (c.getItems().freeSlots() == 0){
							Server.itemHandler.createGroundItem(c, randomCrystal(),
									c.absX,c.absY, c.heightLevel, c.playerId);
							c.getItems().addItemToBank(randomCrystal(), 1);
							c.sendMessage("You dropped your item for not having enough inventory space.");
						}
						c.sendMessage("Your EP decreases by 90 but you received a random item for killing "+ c.playerName +".");
						c.PkminiPoints ++;
						c.pkPoints ++;
					}
					o.sendMessage("Increase your Earning Potential (EP) up to at least 90 to receive PvP rewards.");
					o.pkName = c.playerName;
				}
				}
				c.playerKilled = c.playerId;
				if(o.duelStatus == 5) {
					o.duelStatus++;
				}
			}
		}
		c.faceUpdate(0);
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer b) {
		c.npcIndex = 0;
		c.playerIndex = 0;
				b.stop();
				}
				}, 2500);
		c.stopMovement();
		if(c.duelStatus <= 4) {
			c.getTradeAndDuel().stakedItems.clear();
			c.sendMessage("Oh dear you are dead!");
		} else if(c.duelStatus != 6) {
		Client o = (Client) Server.playerHandler.players[c.killerId];
			c.getTradeAndDuel().stakedItems.clear();
			c.sendMessage("You have lost the duel!");
			PlayerSave.saveGame(o);
			PlayerSave.saveGame(c);
		}
		PlayerSave.saveGame(c);
		resetDamageDone();
		c.specAmount = 10;
		c.getItems().addSpecialBar(c.playerEquipment[c.playerWeapon]);
		c.lastVeng = 0;
		c.vengOn = false;
		resetFollowers();
		c.attackTimer = 10;
		c.tradeResetNeeded = true;
	}

	@SuppressWarnings("static-access")
	public void dropitems() {
		if (c.hasFollower > 0) {
			if (c.totalstored > 0) {
				c.firstslot();
				for (int i = 0; i < 29; i += 1) {
					Server.itemHandler.createGroundItem(c, c.storeditems[i],
							Server.npcHandler.npcs[c.summoningnpcid].absX,
							Server.npcHandler.npcs[c.summoningnpcid].absY, 1, c.playerId);
					c.storeditems[i] = -1;
					c.occupied[i] = false;
				}
			}
			c.totalstored = 0;
			c.summoningnpcid = 0;
			c.summoningslot = 0;
			c.storing = false;
			c.sendMessage("Your BoB items have drop on the floor");
		}
	}

	/**
	 * Location change for digging, levers etc
	 **/

	public void resetDamageDone() {
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] != null) {
				PlayerHandler.players[i].damageTaken[c.playerId] = 0;
			}
		}
	}

	/*
	 * Vengeance
	 */
	public void castVeng() {

		if (c.playerLevel[6] < 94) {
			c.sendMessage("You need a magic level of 94 to cast this spell.");
			return;
		}
		if (c.playerLevel[1] < 40) {
			c.sendMessage("You need a defence level of 40 to cast this spell.");
			return;
		}
		if (!c.getItems().playerHasItem(9075, 4) || !c.getItems().playerHasItem(557, 10)
				|| !c.getItems().playerHasItem(560, 2)) {
			c.sendMessage("You don't have the required runes to cast this spell.");
			return;
		}
		if (System.currentTimeMillis() - c.lastCast < 30000) {
			c.sendMessage("You can only cast vengeance every 30 seconds.");
			return;
		}
		if (c.vengOn) {
			c.sendMessage("You already have vengeance casted.");
			return;
		}
		c.startAnimation(4410);
		c.gfx100(726);// Just use c.gfx100
		c.getItems().deleteItem2(9075, 4);
		c.getItems().deleteItem2(557, 10);// For these you need to change to
											// deleteItem(item, itemslot,
											// amount);.
		c.getItems().deleteItem2(560, 2);
		addSkillXP(2000, 6);
		c.stopMovement();
		refreshSkill(6);
		c.vengOn = true;
		c.lastCast = System.currentTimeMillis();
	}

	public void vengOther() {
		if (c.playerIndex > 0) {
			@SuppressWarnings("static-access")
			Player q = Server.playerHandler.players[c.playerIndex];
			final int oX = q.getX();
			final int oY = q.getY();
			if (c.playerLevel[6] < 93) {
				c.sendMessage("You need a magic level of 93 to cast this spell.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			if (!q.acceptAid) {
				c.sendMessage("This player has their accept Aid off, therefore you cannot veng them!");
				return;
			}

			if (c.playerLevel[1] < 40) {
				c.sendMessage("You need a defence level of 40 to cast this spell.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			if (!c.getItems().playerHasItem(9075, 3) || !c.getItems().playerHasItem(557, 10)
					|| !c.getItems().playerHasItem(560, 2)) {
				c.sendMessage("You don't have the required runes to cast this spell.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			if (System.currentTimeMillis() - c.lastCast < 30000) {
				c.sendMessage("You can only cast vengeance every 30 seconds.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			if (q.vengOn) {
				c.sendMessage("That player already have vengeance casted.");
				c.getCombat().resetPlayerAttack();
				c.stopMovement();
				c.turnPlayerTo(oX, oY);
				return;
			}
			c.startAnimation(4411);
			q.gfx100(725);// Just use c.gfx100
			c.getItems().deleteItem2(9075, 3);
			c.getItems().deleteItem2(557, 10);// For these you need to change to
												// deleteItem(item, itemslot,
												// amount);.
			c.getItems().deleteItem2(560, 2);
			q.vengOn = true;
			addSkillXP(2000, 6);
			c.turnPlayerTo(oX, oY);
			refreshSkill(6);
			c.getCombat().resetPlayerAttack();
			c.stopMovement();
			c.lastCast = System.currentTimeMillis();
		}
	}

	public boolean wearingCape(int cape) {
		int capes[] = { 9747, 9748, 9750, 9751, 9753, 9754, 9756, 9757, 9759, 9760, 9762, 9763, 9765, 9766, 9768, 9769,
				9771, 9772, 9774, 9775, 9777, 9778, 9780, 9781, 9783, 9784, 9786, 9787, 9789, 9790, 9792, 9793, 9795,
				9796, 9798, 9799, 9801, 9802, 9804, 9805, 9807, 9808, 9810, 9811, 9813, 9948, 9949, 12170 };
		for (int i = 0; i < capes.length; i++) {
			if (capes[i] == cape) {
				return true;
			}
		}
		return false;
	}

	public void vengMe() {
		if (System.currentTimeMillis() - c.lastVeng > 30000) {
			if (c.getItems().playerHasItem(557, 10) && c.getItems().playerHasItem(9075, 4)
					&& c.getItems().playerHasItem(560, 2)) {
				c.vengOn = true;
				c.lastVeng = System.currentTimeMillis();
				c.startAnimation(4410);
				c.gfx100(726);
				c.getItems().deleteItem(557, c.getItems().getItemSlot(557), 10);
				c.getItems().deleteItem(560, c.getItems().getItemSlot(560), 2);
				c.getItems().deleteItem(9075, c.getItems().getItemSlot(9075), 4);
			} else {
				c.sendMessage("You do not have the required runes to cast this spell. (9075 for astrals)");
			}
		} else {
			c.sendMessage("You must wait 30 seconds before casting this again.");
		}
	}

	public void ditchJump(final Client c, final int x, final int y) {
		c.getPA().walkTo(x, y);
		c.isRunning2 = false;
		c.playerWalkIndex = 6132;
		c.getPA().requestUpdates();
	}

	@SuppressWarnings("static-access")
	public void resetDitchJump(final Client c) {
		c.isRunning2 = true;
		c.getPA().sendFrame36(173, 1);
		c.getCombat().getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		c.getPA().requestUpdates();
	}

	public int skillcapeGfx(int cape) {
		int capeGfx[][] = { { 9747, 823 }, { 9748, 823 }, { 9750, 828 }, { 9751, 828 }, { 9753, 824 }, { 9754, 824 },
				{ 9756, 832 }, { 9757, 832 }, { 9759, 829 }, { 9760, 829 }, { 9762, 813 }, { 9763, 813 }, { 9765, 817 },
				{ 9766, 817 }, { 9768, 833 }, { 9769, 833 }, { 9771, 830 }, { 9772, 830 }, { 9774, 835 }, { 9775, 835 },
				{ 9777, 826 }, { 9778, 826 }, { 9780, 818 }, { 9781, 818 }, { 9783, 812 }, { 9784, 812 }, { 9786, 827 },
				{ 9787, 827 }, { 9789, 820 }, { 9790, 820 }, { 9792, 814 }, { 9793, 814 }, { 9795, 815 }, { 9796, 815 },
				{ 9798, 819 }, { 9799, 819 }, { 9801, 821 }, { 9802, 821 }, { 9804, 831 }, { 9805, 831 }, { 9807, 822 },
				{ 9808, 822 }, { 9810, 825 }, { 9811, 825 }, { 9948, 907 }, { 9949, 907 }, { 9813, 816 },
				{ 12170, 1515 } };
		for (int i = 0; i < capeGfx.length; i++) {
			if (capeGfx[i][0] == cape) {
				return capeGfx[i][1];
			}
		}
		return -1;
	}

	public int skillcapeEmote(int cape) {
		int capeEmote[][] = { { 9747, 4959 }, { 9748, 4959 }, { 9750, 4981 }, { 9751, 4981 }, { 9753, 4961 },
				{ 9754, 4961 }, { 9756, 4973 }, { 9757, 4973 }, { 9759, 4979 }, { 9760, 4979 }, { 9762, 4939 },
				{ 9763, 4939 }, { 9765, 4947 }, { 9766, 4947 }, { 9768, 4971 }, { 9769, 4971 }, { 9771, 4977 },
				{ 9772, 4977 }, { 9774, 4969 }, { 9775, 4969 }, { 9777, 4965 }, { 9778, 4965 }, { 9780, 4949 },
				{ 9781, 4949 }, { 9783, 4937 }, { 9784, 4937 }, { 9786, 4967 }, { 9787, 4967 }, { 9789, 4953 },
				{ 9790, 4953 }, { 9792, 4941 }, { 9793, 4941 }, { 9795, 4943 }, { 9796, 4943 }, { 9798, 4951 },
				{ 9799, 4951 }, { 9801, 4955 }, { 9802, 4955 }, { 9804, 4975 }, { 9805, 4975 }, { 9807, 4957 },
				{ 9808, 4957 }, { 9810, 4963 }, { 9811, 4963 }, { 9948, 5158 }, { 9949, 5158 }, { 9813, 4945 },
				{ 12170, 8525 } };
		for (int i = 0; i < capeEmote.length; i++) {
			if (capeEmote[i][0] == cape) {
				return capeEmote[i][1];
			}
		}
		return -1;
	}

	public void resetTb() {
		c.teleBlockLength = 0;
		c.teleBlockDelay = 0;
	}

	public void resetbarrows() {
		c.playerLevel[3] = c.playerLevel[3];
		c.waveId = -1;
		c.dominionToKill = -1;
		c.dominionKilled = -1;
		c.getPA().movePlayer(2387, 3488, 0);
	}
	public void resetDominion() {
		c.playerLevel[3] = c.playerLevel[3];
		c.waveId = -1;
		c.dominionToKill = -1;
		c.dominionKilled = -1;
		c.getPA().movePlayer(2387, 3488, 0);
	}

	@SuppressWarnings("static-access")
	public void resetFollowers() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				if (Server.playerHandler.players[j].followId == c.playerId) {
					Client c = (Client) Server.playerHandler.players[j];
					c.getPA().resetFollow();
				}
			}
		}
	}

	@SuppressWarnings("static-access")
	public void giveLife() {
		c.isDead = false;
		c.faceUpdate(-1);
		c.freezeTimer = 0;

		if (c.playerRights == 3) {
			for (int i = 0; i < 20; i++) {
				c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
				c.getPA().refreshSkill(i);
			}
			c.getCombat().resetPrayers();

			c.teleportToX = 2387;
			c.teleportToY = 3488;
			PlayerSave.saveGame(c);
			c.getPA().resetTzhaar();
			requestUpdates();
			return;

		}/*
		if (c.playerRights >= 3) {
			for (int i = 0; i < 20; i++) {
				c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
				c.getPA().refreshSkill(i);
			}
			c.getCombat().resetPrayers();

			c.teleportToX = 2387;
			c.teleportToY = 3488;
			PlayerSave.saveGame(c);
			requestUpdates();
			return;

		}*/
		if (c.inWild()) {
			c.getItems().dropAllItems(); // drop all items
			c.getItems().deleteAllItems(); // delete all items
		}
		if (c.InDung()) {
			c.getPA().moveDung();
		}
		if (c.duelStatus <= 4 && !c.getPA().inPitsWait()) { // if we are not in
															// a duel we must be
															// in wildy so
															// remove items
			if (!c.inPits && !c.inFightCaves() && !c.inDT() && !c.inPcGame() && !c.inFunPk()) {
				c.getItems().resetKeepItems();
				if ((c.playerRights == 2 && Config.ADMIN_DROP_ITEMS)) {
					if (!c.isSkulled && !c.isInArd()) { // what items to keep
						c.getItems().keepItem(0, true);
						c.getItems().keepItem(1, true);
						c.getItems().keepItem(2, true);
					}
					if (c.prayerActive[10] || c.curseActive[0] && System.currentTimeMillis() - c.lastProtItem > 700) {
						c.getItems().keepItem(3, true);
					}
					if (c.trade11 < 1 && !c.isInFala() && !c.isInArd() && c.inWild()) {
						c.getItems().dropAllItems(); // drop all items
						c.getItems().deleteAllItems(); // delete all items
					}
					if (!c.isSkulled && !c.isInArd()) { // add the kept items
														// once we finish
														// deleting and dropping
														// them
						for (int i1 = 0; i1 < 3; i1++) {
							if (c.itemKeptId[i1] > 0) {
								c.getItems().addItem(c.itemKeptId[i1], 1);
							}
						}
					}
					if (c.prayerActive[10] || c.isInArd()) { // if we have
																// protect items
						if (c.itemKeptId[3] > 0) {
							c.getItems().addItem(c.itemKeptId[3], 1);
						}
					}
				}
				c.getItems().resetKeepItems();
			} else if (c.inBarbDef) {
				Server.barbDefence.endGame(c, false);
			} else if (c.inPits) {
				Server.fightPits.removePlayerFromPits(c.playerId);
				c.pitsStatus = 1;
				c.duelStatus = 0;
			}
		}
		c.getCombat().resetPrayers();
		for (int i = 0; i < 25; i++) {
			c.playerLevel[i] = getLevelForXP(c.playerXP[i]);
			c.getPA().refreshSkill(i);
		}
		if (c.playerEquipment[c.playerRing] == 2570) {
			if (c.playerLevel[3] > 0 && c.playerLevel[3] <= c.getLevelForXP(c.playerXP[3]) / 10
					&& c.underAttackBy > 0) {
				int wildlvl = (((c.absY - 3520) / 8) + 1);
				if (wildlvl < 20) {
					c.getItems().deleteEquipment(2570, c.playerRing);
					c.getPA().startTeleport(2831, 2973, 0, "modern");
				}
			}
		}
		if (c.pitsStatus == 1) {
			movePlayer(2399, 5173, 0);
			c.pitsStatus = 0;
		} else if (c.duelStatus <= 4) { // if we are not in a duel repawn to

			// wildy
			movePlayer(Config.RESPAWN_X, Config.RESPAWN_Y, 0);
			c.isSkulled = false;
			c.skullTimer = 0;
			c.attackedPlayers.clear();

		} else if (c.inFightCaves()) {
			c.getPA().resetTzhaar();
		} else if (c.inDT()) {
			c.getPA().resetDominion();

		} else { // we are in a duel, respawn outside of arena
			Client o = (Client) Server.playerHandler.players[c.duelingWith];
			if (o != null) {
				o.getPA().createPlayerHints(10, -1);
				if (o.duelStatus == 6) {
					o.getTradeAndDuel().duelVictory();
					o.getPA().writeTabs();
				}
			}
			/*
			 * movePlayer( Config.DUELING_RESPAWN_X +
			 * (Misc.random(Config.RANDOM_DUELING_RESPAWN)),
			 * Config.DUELING_RESPAWN_Y OLD ONE +
			 * (Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
			 */
			c.getPA().movePlayer(Config.DUELING_RESPAWN_X + (Misc.random(Config.RANDOM_DUELING_RESPAWN)),
					Config.DUELING_RESPAWN_Y + (Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
			o.getPA().movePlayer(Config.DUELING_RESPAWN_X + (Misc.random(Config.RANDOM_DUELING_RESPAWN)),
					Config.DUELING_RESPAWN_Y + (Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
			if (c.duelStatus != 6) { // if we have won but have died, don't
										// reset the duel status.
				c.getTradeAndDuel().resetDuel();
			}
		}
		// PlayerSaving.getSingleton().requestSave(c.playerId);
		PlayerSave.saveGame(c);
		c.getCombat().resetPlayerAttack();
		resetAnimation();
		c.startAnimation(-1);
		frame1();
		resetTb();
		c.isSkulled = false;
		c.attackedPlayers.clear();
		c.headIconPk = -1;
		c.skullTimer = -1;
		c.damageTaken = new int[Config.MAX_PLAYERS];
		c.getPA().requestUpdates();
		removeAllWindows();
		c.tradeResetNeeded = true;

	}

	/**
	 * Location change for digging, levers etc
	 **/

	public void changeLocation() {
		switch (c.newLocation) {
		case 1:
			sendFrame99(2);
			movePlayer(3578, 9706, -1);
			break;
		case 2:
			sendFrame99(2);
			movePlayer(3568, 9683, -1);
			break;
		case 3:
			sendFrame99(2);
			movePlayer(3557, 9703, -1);
			break;
		case 4:
			sendFrame99(2);
			movePlayer(3556, 9718, -1);
			break;
		case 5:
			sendFrame99(2);
			movePlayer(3534, 9704, -1);
			break;
		case 6:
			sendFrame99(2);
			movePlayer(3546, 9684, -1);
			break;
		case 7:
			sendFrame99(2);
			movePlayer(3546, 9684, -1);
			break;
		}
		c.newLocation = 0;
	}

	/**
	 * Teleporting
	 **/
	public void spellTeleport(int x, int y, int height) {
		if (c.selectStarter == true) {
			c.sendMessage(" You can't teleport while choosing starter!");
			return;
		}
		if (c.selectStarterr == true) {
			c.sendMessage(" You can't teleport while choosing starter!");
			return;
		}
		c.getPA().startTeleport(x, y, height, c.playerMagicBook == 1 ? "ancient" : "modern");
		c.getTradeAndDuel().declineTrade();
	}

	public void startMovement(int x, int y, int height) {
		c.getPA().resetFishing();
		// c.getMining().resetMining();
		c.isCooking = false;
		c.usingAltar = false;
		c.playerIsFiremaking = false;
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if (c.InDung()) {
			c.sendMessage("You cannot teleport out of Dungeoneering! To exit, use the ladders!");
			return;
		}
		if (c.inRFD()) {
			c.sendMessage("You can't teleport out of this minigame!");
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You can't teleport out of prison idiot!");
			return;
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You can't teleport out of prison idiot!");
			return;
		}
		if (c.inWarriorG() && c.heightLevel == 2) {
			c.sendMessage("You can't teleport out of Warrior Guild!");
			return;
		}
		if (c.inGWD()) {
			ResetGWKC();
		}
		if (c.inFightCaves()) {
			c.sendMessage("You can't teleport out of this minigame!");
			return;
		}
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level " + Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			EarningPotential.checkTeleport(c);
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;

		}

	}

	public void startTeleport(int x, int y, int height, String teleportType) {
		if (c.inAccountPin) {
			c.sendMessage("You must enter your pin first");
			c.isWalking = false;
			c.getBankPin().openPin();
			return;
		}
		if (c.inTrade) {
			c.sendMessage("Please finish the trade first.");
			return;
		}
		if (!teleportType.equals("ForceTutorial") && c.getTutorial().tutorialStage != -1) {
			c.sendMessage("You cannot teleport while being in the tutorial stage.");
			return;
		} else if (teleportType.equals("ForceTutorial"))
			teleportType = "modern";
		if(c.inPits || c.viewingOrb || inPitsWait()) {
			c.sendMessage("You can't teleport in here!");
			return;
		}
		
		if (c.selectStarter == true) {
			c.sendMessage(" You can't teleport while choosing starter!");
			return;
		}
		if (c.GambleStatus > 0) {
			c.sendMessage("You can't teleport while in a gamble!");
		return;
		}
		if (c.inBarbDef == true) {
			c.sendMessage("Teleporting will make you loose points! Type ::endgame instead!");
			return;
		}
		if (c.selectStarterr == true) {
			c.sendMessage(" You can't teleport while choosing starter!");
			return;
		}
		c.getPA().resetFishing();
		// c.getMining().resetMining();
		c.isCooking = false;
		c.usingAltar = false;
		c.playerIsFiremaking = false;
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL && !c.inFunPk()) {
			c.sendMessage("You can't teleport above level " + Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		/*
		 * if(c.inNomad()) { c.sendMessage(
		 * "You can't teleport during Nomad Minigame"); return; }
		 * if(c.inGoblin()) { c.sendMessage(
		 * "You can't teleport during Goblin Minigame"); return; }
		 */
		if (c.InDung()) {
			c.sendMessage("You cannot teleport out of Dungeoneering! To exit, use the ladders!");
			return;
		}
		if (c.inPits || c.viewingOrb || inPitsWait()) {
			c.sendMessage("You can't teleport in here!");
			return;
		}
		if (c.inGWD()) {
			ResetGWKC();
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You can't teleport out of prison fucking fool!");
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You can't teleport out of prison fucking goon!");
			return;
		}
		if (c.inWarriorG() && c.heightLevel == 2) {
			c.sendMessage("You can't teleport out of Warrior Guild!");
			return;
		}
		if (c.inRFD()) {
			c.sendMessage("You can't teleport out of this minigame!");
			return;
		}
		if (c.inFightCaves()) {
			c.sendMessage("You can't teleport out of this minigame!");
			return;
		}
		if (c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level " + Config.NO_TELEPORT_WILD_LEVEL + " in the wilderness.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if((!c.isDead && c.teleTimer == 0 && c.respawnTimer == -6) || c.getTutorial().tutorialStage != -1) {
			if (c.playerIndex > 0 || c.npcIndex > 0)
				c.getCombat().resetPlayerAttack();
			c.stopMovement();
			EarningPotential.checkTeleport(c);
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			if (teleportType.equalsIgnoreCase("modern")) {
				c.getTradeAndDuel().declineTrade();
				c.startAnimation(1110);
				c.teleTimer = 9;
				//c.gfx0(453);
				//c.gfx0(1576);
				//c.teleEndAnimation = 8941;
			}

			if (teleportType.equalsIgnoreCase("dungtele")) {
				c.getTradeAndDuel().declineTrade();
				c.startAnimation(13652);
				c.teleTimer = 16;
				c.gfx0(2602);
				c.teleEndAnimation = 13654;
			}

			if (teleportType.equalsIgnoreCase("ancient")) {
				c.getTradeAndDuel().declineTrade();
				c.startAnimation(9599);
				c.teleGfx = 0;
				c.teleTimer = 11;
				c.teleEndAnimation = 8941;
				c.gfx0(1681);
			}

		}
	}

	public void startTeleport2(int x, int y, int height) {
		if (c.inTrade) {
			c.sendMessage("Please finish the trade first.");
			return;
		}
		if (c.selectStarter == true) {
			c.sendMessage(" You can't teleport while choosing starter!");
			return;
		}
		if (c.inBarbDef == true) {
			c.sendMessage("Teleporting will make you loose points! Type ::endgame instead!");
			return;
		}
		if (c.selectStarterr == true) {
			c.sendMessage(" You can't teleport while choosing starter!");
			return;
		}
		c.getPA().resetFishing();
		// c.getMining().resetMining();
		c.isCooking = false;
		c.usingAltar = false;
		c.playerIsFiremaking = false;
		if (c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		/*
		 * if(c.inNomad()) { c.sendMessage(
		 * "You can't teleport during Nomad Minigame"); return; }
		 * if(c.inGoblin()) { c.sendMessage(
		 * "You can't teleport during Goblin Minigame"); return; }
		 */
		if (c.Jail == true) {
			c.sendMessage("You can't teleport out of prison.");
			return;
		}
		if (c.InDung()) {
			c.sendMessage("You cannot teleport out of Dungeoneering! To exit, use the ladders!");
			return;
		}
		if (c.inGWD()) {
			ResetGWKC();
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You can't teleport out of prison.");
			return;
		}
		if (System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if (!c.isDead && c.teleTimer == 0) {
			c.getTradeAndDuel().declineTrade();
			c.stopMovement();
			removeAllWindows();
			c.teleX = x;
			c.teleY = y;
			c.npcIndex = 0;
			c.playerIndex = 0;
			c.faceUpdate(0);
			c.teleHeight = height;
			c.startAnimation(714);
			c.teleTimer = 11;
			c.teleGfx = 308;
			c.teleEndAnimation = 715;

		}
	}

	public void processTeleport() {
		if (c.inTrade) {
			c.sendMessage("Please finish the trade first.");
			return;
		}
		if (c.selectStarter == true) {
			c.sendMessage(" You can't teleport while choosing starter!");
			return;
		}
		if (c.inBarbDef == true) {
			c.sendMessage("Teleporting will make you loose points! Type ::endgame instead!");
			return;
		}
		if (c.selectStarterr == true) {
			c.sendMessage(" You can't teleport while choosing starter!");
			return;
		}
		c.teleportToX = c.teleX;
		c.teleportToY = c.teleY;
		c.heightLevel = c.teleHeight;
		if (c.teleEndAnimation > 0) {
			c.startAnimation(c.teleEndAnimation);
		}
	}

	public void movePlayer(int x, int y, int h) {
		c.getPA().resetFishing();
		// c.getMining().resetMining();
		c.isCooking = false;
		c.usingAltar = false;
		c.playerIsFiremaking = false;
		c.resetWalkingQueue();
		c.teleportToX = x;
		c.teleportToY = y;
		c.heightLevel = h;
		requestUpdates();
	}

	/**
	 * Following
	 **/

	@SuppressWarnings({ "unused", "static-access" })
	public void Player() {
		if (Server.playerHandler.players[c.followId] == null || Server.playerHandler.players[c.followId].isDead) {
			c.getPA().resetFollow();
			return;
		}
		if (c.freezeTimer > 0) {
			return;
		}
		int otherX = Server.playerHandler.players[c.followId].getX();
		int otherY = Server.playerHandler.players[c.followId].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 6);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean sameSpot = (c.absX == otherX && c.absY == otherY);
		if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId = 0;
			c.getPA().resetFollow();
			return;
		}
		c.faceUpdate(c.followId + 32768);
		if ((c.usingBow || c.mageFollow || c.autocastId > 0 && (c.npcIndex > 0 || c.playerIndex > 0)) && bowDistance
				&& !sameSpot) {
			c.stopMovement();
			return;
		}
		if (c.usingRangeWeapon && rangeWeaponDistance && !sameSpot && (c.npcIndex > 0 || c.playerIndex > 0)) {
			c.stopMovement();
			return;
		}
		if (c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1) && !sameSpot) {
			return;
		}
		c.outStream.createFrame(174);
		boolean followPlayer = c.followId > 0;
		if (c.freezeTimer <= 0)
			if (followPlayer)
				c.outStream.writeWord(c.followId);
			else
				c.outStream.writeWord(c.followId2);
		else
			c.outStream.writeWord(0);

		if (followPlayer)
			c.outStream.writeByte(1);
		else
			c.outStream.writeByte(0);
		if (c.usingBow && c.playerIndex > 0)
			c.followDistance = 5;
		else if (c.usingRangeWeapon && c.playerIndex > 0)
			c.followDistance = 3;
		else if (c.spellId > 0 && c.playerIndex > 0)
			c.followDistance = 5;
		else
			c.followDistance = 1;
		c.outStream.writeWord(c.followDistance);
	}

	public void followPlayer() {
		if (PlayerHandler.players[c.followId] == null || PlayerHandler.players[c.followId].isDead) {
			resetFollow();
			return;
		}
		if (c.freezeTimer > 0) {
			return;
		}
		if (c.inWG()) {
			c.followId = 0;
			return;
		}
		if (c.inDuelArena()) {
			c.followId = 0;
			return;
		}
		if (inPitsWait()) {
			c.followId = 0;
		}
		if (c.InDung()) {
			c.sendMessage("you cannot follow in Dungoneering!");
			c.followId = 0;
			return;
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You cannot follow in jail!");
			c.followId = 0;
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You cannot follow in jail!");
			c.followId = 0;
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;

		int otherX = PlayerHandler.players[c.followId].getX();
		int otherY = PlayerHandler.players[c.followId].getY();

		boolean sameSpot = (c.absX == otherX && c.absY == otherY);

		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);

		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 6);
		boolean mageDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 7);

		boolean castingMagic = (c.usingMagic || c.spellId > 0) && mageDistance;
		boolean playerRanging = (c.usingRangeWeapon) && rangeWeaponDistance;
		boolean playerBowOrCross = (c.usingBow) && bowDistance;

		if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId = 0;
			resetFollow();
			return;
		}
		c.faceUpdate(c.followId + 32768);
		if (!sameSpot) {
			if (c.playerIndex > 0 && !c.usingSpecial && c.inWild()) {
				if (c.usingSpecial && (playerRanging || playerBowOrCross)) {
					c.stopMovement();
					return;
				}
				if (castingMagic || playerRanging || playerBowOrCross) {
					c.stopMovement();
					return;
				}
				if (c.getCombat().usingHally() && hallyDistance) {
					c.stopMovement();
					return;
				}
			}
		}
		if (otherX == c.absX && otherY == c.absY) {
			int r = Misc.random(3);
			switch (r) {
			case 0:
				walkTo(0, -1);
				break;
			case 1:
				walkTo(0, 1);
				break;
			case 2:
				walkTo(1, 0);
				break;
			case 3:
				walkTo(-1, 0);
				break;
			}
		} else if (c.isRunning2 && Region.canAttack(c, (Client) PlayerHandler.players[c.followId])) {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			}
		} else {
			if (otherY > c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY - 1);
			} else if (otherY < c.getY() && otherX == c.getX()) {
				playerWalk(otherX, otherY + 1);
			} else if (otherX > c.getX() && otherY == c.getY()) {
				playerWalk(otherX - 1, otherY);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				playerWalk(otherX + 1, otherY);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				playerWalk(otherX + 1, otherY + 1);
			} else if (otherX > c.getX() && otherY > c.getY()) {
				playerWalk(otherX - 1, otherY - 1);
			} else if (otherX < c.getX() && otherY > c.getY()) {
				playerWalk(otherX + 1, otherY - 1);
			} else if (otherX > c.getX() && otherY < c.getY()) {
				playerWalk(otherX - 1, otherY + 1);
			}
		}
		c.faceUpdate(c.followId + 32768);
	}

	/*
	 * public void followPlayer() { if(Server.playerHandler.players[c.followId]
	 * == null || Server.playerHandler.players[c.followId].isDead) { c.followId
	 * = 0; return; } if(c.freezeTimer > 0) { return; } if(c.inWG()) {
	 * c.followId = 0; return; } if(c.inDuelArena()) { c.followId = 0; return; }
	 * if(inPitsWait()) { c.followId = 0; } if(c.InDung()) { c.sendMessage(
	 * "you cannot follow in Dungoneering!"); c.followId = 0; return; }
	 * if(c.inJail() && c.Jail == true) { c.sendMessage(
	 * "You cannot follow in jail!"); c.followId = 0; return; } if(c.Jail ==
	 * true) { c.sendMessage("You cannot follow in jail!"); c.followId = 0;
	 * return; } if (c.isDead || c.playerLevel[3] <= 0) return;
	 * 
	 * int otherX = Server.playerHandler.players[c.followId].getX(); int otherY
	 * = Server.playerHandler.players[c.followId].getY(); boolean withinDistance
	 * = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2); boolean
	 * goodDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
	 * boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(),
	 * c.getY(), 2); boolean bowDistance = c.goodDistance(otherX, otherY,
	 * c.getX(), c.getY(), 8); boolean rangeWeaponDistance =
	 * c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4); boolean sameSpot =
	 * c.absX == otherX && c.absY == otherY; if(!c.goodDistance(otherX, otherY,
	 * c.getX(), c.getY(), 25)) { c.followId = 0; return; }
	 * if(c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) { if (otherX !=
	 * c.getX() && otherY != c.getY()) { stopDiagonal(otherX, otherY); return; }
	 * }
	 * 
	 * if((c.usingBow || c.mageFollow || (c.playerIndex > 0 && c.autocastId >
	 * 0)) && bowDistance && !sameSpot) { return; }
	 * 
	 * if(c.getCombat().usingHally() && hallyDistance && !sameSpot) { return; }
	 * 
	 * if(c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) { return; }
	 * 
	 * c.faceUpdate(c.followId+32768);
	 * 
	 * if (otherY == c.getY() && otherX == c.getX()) { switch(Misc.random(3)) {
	 * case 0: walkTo(0, getMove(c.getX(), otherX - 1)); break;
	 * 
	 * case 1: walkTo(0, getMove(c.getX(), otherX + 1)); break;
	 * 
	 * case 2: walkTo(0, getMove(c.getY(), otherY + 1)); break;
	 * 
	 * case 3: walkTo(0, getMove(c.getY(), otherY - 1)); break; } }
	 * if(c.isRunning2 && !withinDistance) { if(otherY > c.getY() && otherX ==
	 * c.getX()) { walkTo(0, getMove(c.getY(), otherY - 1) + getMove(c.getY(),
	 * otherY - 1)); } else if(otherY < c.getY() && otherX == c.getX()) {
	 * walkTo(0, getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
	 * } else if(otherX > c.getX() && otherY == c.getY()) {
	 * walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), 0);
	 * } else if(otherX < c.getX() && otherY == c.getY()) {
	 * walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), 0);
	 * } else if(otherX < c.getX() && otherY < c.getY()) {
	 * walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1),
	 * getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1)); } else
	 * if(otherX > c.getX() && otherY > c.getY()) { walkTo(getMove(c.getX(),
	 * otherX - 1) + getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY -
	 * 1) + getMove(c.getY(), otherY - 1)); } else if(otherX < c.getX() &&
	 * otherY > c.getY()) { walkTo(getMove(c.getX(), otherX + 1) +
	 * getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1) +
	 * getMove(c.getY(), otherY - 1)); } else if(otherX > c.getX() && otherY <
	 * c.getY()) { walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(),
	 * otherX + 1), getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY -
	 * 1)); } } else { if(otherY > c.getY() && otherX == c.getX()) { walkTo(0,
	 * getMove(c.getY(), otherY - 1)); } else if(otherY < c.getY() && otherX ==
	 * c.getX()) { walkTo(0, getMove(c.getY(), otherY + 1)); } else if(otherX >
	 * c.getX() && otherY == c.getY()) { walkTo(getMove(c.getX(), otherX - 1),
	 * 0); } else if(otherX < c.getX() && otherY == c.getY()) {
	 * walkTo(getMove(c.getX(), otherX + 1), 0); } else if(otherX < c.getX() &&
	 * otherY < c.getY()) { walkTo(getMove(c.getX(), otherX + 1),
	 * getMove(c.getY(), otherY + 1)); } else if(otherX > c.getX() && otherY >
	 * c.getY()) { walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(),
	 * otherY - 1)); } else if(otherX < c.getX() && otherY > c.getY()) {
	 * walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1)); }
	 * else if(otherX > c.getX() && otherY < c.getY()) {
	 * walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY + 1)); } }
	 * }
	 */

	@SuppressWarnings({ "static-access", "unused" })
	public void followNpc() {
		if (Server.npcHandler.npcs[c.followId2] == null || Server.npcHandler.npcs[c.followId2].isDead) {
			c.followId2 = 0;
			return;
		}
		if (c.freezeTimer > 0) {
			return;
		}
		if (c.inWG()) {
			c.followId = 0;
			return;
		}
		if (c.inDuelArena()) {
			c.followId = 0;
			return;
		}
		if (c.inJail() && c.Jail == true) {
			c.sendMessage("You cannot follow in jail!");
			c.followId = 0;
			return;
		}
		if (c.Jail == true) {
			c.sendMessage("You cannot follow in jail!");
			c.followId = 0;
			return;
		}
		if (c.isDead || c.playerLevel[3] <= 0)
			return;

		int otherX = Server.npcHandler.npcs[c.followId2].getX();
		int otherY = Server.npcHandler.npcs[c.followId2].getY();
		boolean withinDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean goodDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1);
		boolean hallyDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 2);
		boolean bowDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 8);
		boolean rangeWeaponDistance = c.goodDistance(otherX, otherY, c.getX(), c.getY(), 4);
		boolean sameSpot = c.absX == otherX && c.absY == otherY;
		if (!c.goodDistance(otherX, otherY, c.getX(), c.getY(), 25)) {
			c.followId2 = 0;
			return;
		}
		if (c.goodDistance(otherX, otherY, c.getX(), c.getY(), 1)) {
			if (otherX != c.getX() && otherY != c.getY()) {
				stopDiagonal(otherX, otherY);
				return;
			}
		}

		if ((c.usingBow || c.mageFollow || (c.npcIndex > 0 && c.autocastId > 0)) && bowDistance && !sameSpot) {
			return;
		}

		if (c.getCombat().usingHally() && hallyDistance && !sameSpot) {
			return;
		}

		if (c.usingRangeWeapon && rangeWeaponDistance && !sameSpot) {
			return;
		}

		c.faceUpdate(c.followId2);
		if (otherX == c.absX && otherY == c.absY) {
			int r = Misc.random(3);
			switch (r) {
			case 0:
				walkTo(0, -1);
				break;
			case 1:
				walkTo(0, 1);
				break;
			case 2:
				walkTo(1, 0);
				break;
			case 3:
				walkTo(-1, 0);
				break;
			}
		} else if (c.isRunning2 && !withinDistance) {
			if (otherY > c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if (otherY < c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1), 0);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1), 0);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY + 1) + getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1) + getMove(c.getX(), otherX - 1),
						getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if (otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			} else if (otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1) + getMove(c.getX(), otherX + 1),
						getMove(c.getY(), otherY - 1) + getMove(c.getY(), otherY - 1));
			}
		} else {
			if (otherY > c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY - 1));
			} else if (otherY < c.getY() && otherX == c.getX()) {
				walkTo(0, getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), 0);
			} else if (otherX < c.getX() && otherY == c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), 0);
			} else if (otherX < c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY + 1));
			} else if (otherX > c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY - 1));
			} else if (otherX < c.getX() && otherY > c.getY()) {
				walkTo(getMove(c.getX(), otherX + 1), getMove(c.getY(), otherY - 1));
			} else if (otherX > c.getX() && otherY < c.getY()) {
				walkTo(getMove(c.getX(), otherX - 1), getMove(c.getY(), otherY + 1));
			}
		}
		c.faceUpdate(c.followId2);
	}

	public int getRunningMove(int i, int j) {
		if (j - i > 2)
			return 2;
		else if (j - i < -2)
			return -2;
		else
			return j - i;
	}

	public void resetFollow() {
		c.followId = 0;
		c.followId2 = 0;
		c.mageFollow = false;
		c.outStream.createFrame(174);
		c.outStream.writeWord(0);
		c.outStream.writeByte(0);
		c.outStream.writeWord(1);
	}

	public void walkTo(int i, int j) {
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void walkTo2(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public void stopDiagonal(int otherX, int otherY) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 1;
		int xMove = otherX - c.getX();
		int yMove = 0;
		if (xMove == 0)
			yMove = otherY - c.getY();
		/*
		 * if (!clipHor) { yMove = 0; } else if (!clipVer) { xMove = 0; }
		 */

		int k = c.getX() + xMove;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + yMove;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}

	}

	public void walkToCheck(int i, int j) {
		if (c.freezeDelay > 0)
			return;
		c.newWalkCmdSteps = 0;
		if (++c.newWalkCmdSteps > 50)
			c.newWalkCmdSteps = 0;
		int k = c.getX() + i;
		k -= c.mapRegionX * 8;
		c.getNewWalkCmdX()[0] = c.getNewWalkCmdY()[0] = 0;
		int l = c.getY() + j;
		l -= c.mapRegionY * 8;

		for (int n = 0; n < c.newWalkCmdSteps; n++) {
			c.getNewWalkCmdX()[n] += k;
			c.getNewWalkCmdY()[n] += l;
		}
	}

	public int getMove(int place1, int place2) {
		if (System.currentTimeMillis() - c.lastSpear < 4000)
			return 0;
		if ((place1 - place2) == 0) {
			return 0;
		} else if ((place1 - place2) < 0) {
			return 1;
		} else if ((place1 - place2) > 0) {
			return -1;
		}
		return 0;
	}

	@SuppressWarnings("static-access")
	public boolean fullVeracs() {
		return c.playerEquipment[c.playerHat] == 4753 && c.playerEquipment[c.playerChest] == 4757
				&& c.playerEquipment[c.playerLegs] == 4759 && c.playerEquipment[c.playerWeapon] == 4755;
	}

	@SuppressWarnings("static-access")
	public boolean fullGuthans() {
		return c.playerEquipment[c.playerHat] == 4724 && c.playerEquipment[c.playerChest] == 4728
				&& c.playerEquipment[c.playerLegs] == 4730 && c.playerEquipment[c.playerWeapon] == 4726;
	}

	/**
	 * reseting animation
	 **/
	@SuppressWarnings("static-access")
	public void resetAnimation() {
		c.getCombat().getPlayerAnimIndex(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		c.startAnimation(c.playerStandIndex);
		requestUpdates();
	}

	public void requestUpdates() {
		c.updateRequired = true;
		c.setAppearanceUpdateRequired(true);
	}

	public static int Wheel[] = { 995, 192, 19, 91, 139, 11, 1929, 101, 29, 10, 1, 291, 10, 172, 199, 103, 105, 183,
			481, 741, 83, 183, 1040, 123 };

	public int Wheel() {
		return Wheel[(int) (Math.random() * Wheel.length)];
	}

	public static int Dungeoneering[] = { 11694, 14484, 11696 };// addmoreitems
																// to this

	public int Dungeoneering() {
		return Dungeoneering[(int) (Math.random() * Dungeoneering.length)];
	}

	@SuppressWarnings("static-access")
	public void levelUp(int skill) {
		int totalLevel = (getLevelForXP(c.playerXP[0]) + getLevelForXP(c.playerXP[1]) + getLevelForXP(c.playerXP[2])
				+ getLevelForXP(c.playerXP[3]) + getLevelForXP(c.playerXP[4]) + getLevelForXP(c.playerXP[5])
				+ getLevelForXP(c.playerXP[6]) + getLevelForXP(c.playerXP[7]) + getLevelForXP(c.playerXP[8])
				+ getLevelForXP(c.playerXP[9]) + getLevelForXP(c.playerXP[10]) + getLevelForXP(c.playerXP[11])
				+ getLevelForXP(c.playerXP[12]) + getLevelForXP(c.playerXP[13]) + getLevelForXP(c.playerXP[14])
				+ getLevelForXP(c.playerXP[15]) + getLevelForXP(c.playerXP[16]) + getLevelForXP(c.playerXP[17])
				+ getLevelForXP(c.playerXP[18]) + getLevelForXP(c.playerXP[19]) + getLevelForXP(c.playerXP[20])
				+ getLevelForXP(c.playerXP[21]) + getLevelForXP(c.playerXP[22]) + getLevelForXP(c.playerXP[23])
				+ getLevelForXP(c.playerXP[24]));
		sendFrame126("Levels: " + totalLevel, 13983);
		switch (skill) {
		case 0:
			c.sendMessage("Congratulations, you just advanced an attack level.");
			c.sendMessage("Your attack level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			sendFrame126("Combat Level: " + c.getCombatLevel() + "", 3983);
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItemToBank(9747, 1);
				c.getItems().addItemToBank(9748, 1);
				c.getItems().addItemToBank(9749, 1);
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Attack!");
					}
				}
			}
			break;

		case 1:
			c.sendMessage("Congratulations, you just advanced a defence level.");
			c.sendMessage("Your defence level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			sendFrame126("Combat Level: " + c.getCombatLevel() + "", 3983);
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItemToBank(9753, 1);
				c.getItems().addItemToBank(9754, 1);
				c.getItems().addItemToBank(9755, 1);
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Defence!");

					}
				}
			}
			break;

		case 2:
			c.sendMessage("Congratulations, you just advanced a strength level.");
			c.sendMessage("Your strength level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			sendFrame126("Combat Level: " + c.getCombatLevel() + "", 3983);
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItemToBank(9750, 1);
				c.getItems().addItemToBank(9751, 1);
				c.getItems().addItemToBank(9752, 1);
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Strength!");

					}
				}
			}
			break;

		case 3:
			c.sendMessage("Congratulations, you just advanced a hitpoints level.");
			c.sendMessage("Your hitpoints level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			sendFrame126("Combat Level: " + c.getCombatLevel() + "", 3983);
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItemToBank(9768, 1);
				c.getItems().addItemToBank(9769, 1);
				c.getItems().addItemToBank(9770, 1);
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Hitpoints!");
					}
				}
			}
			break;

		case 4:
			c.sendMessage("Congratulations, you just advanced a ranging level.");
			c.sendMessage("Your ranged level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			sendFrame126("Combat Level: " + c.getCombatLevel() + "", 3983);
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItemToBank(9756, 1);
				c.getItems().addItemToBank(9757, 1);
				c.getItems().addItemToBank(9758, 1);
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Ranged!");

					}
				}
			}
			break;

		case 5:
			c.sendMessage("Congratulations, you just advanced a prayer level.");
			c.sendMessage("Your prayer level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			sendFrame126("Combat Level: " + c.getCombatLevel() + "", 3983);
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItemToBank(9759, 1);
				c.getItems().addItemToBank(9760, 1);
				c.getItems().addItemToBank(9761, 1);

				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Prayer!");

					}
				}
			}
			break;

		case 6:
			c.sendMessage("Congratulations, you just advanced a magic level.");
			c.sendMessage("Your magic level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			sendFrame126("Combat Level: " + c.getCombatLevel() + "", 3983);
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItemToBank(9762, 1);
				c.getItems().addItemToBank(9763, 1);
				c.getItems().addItemToBank(9764, 1);

				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Magic!");

					}
				}
			}
			break;

		case 7:
			c.sendMessage("Congratulations, you just advanced a cooking level.");
			c.sendMessage("Your cooking level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9801, 1);
				c.getItems().addItemToBank(9802, 1);
				c.getItems().addItemToBank(9803, 1);
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Cooking!");
					}
				}
			}
			break;

		case 8:
			c.sendMessage("Congratulations, you just advanced a woodcutting level.");
			c.sendMessage("Your woodcutting level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9807, 1);
				c.getItems().addItemToBank(9808, 1);
				c.getItems().addItemToBank(9809, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>] <shad=800000000>" + c.playerName + " "
								+ "just advanced to 135 Woodcutting!");
					}
				}
			}
			break;

		case 9:
			c.sendMessage("Congratulations, you just advanced a fletching level.");
			c.sendMessage("Your fletching level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9783, 1);
				c.getItems().addItemToBank(9784, 1);
				c.getItems().addItemToBank(9785, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>] <shad=800000000>" + c.playerName + " "
								+ "just advanced to 135 Fletching!");
					}
				}
			}
			break;

		case 10:
			c.sendMessage("Congratulations, you just advanced a fishing level.");
			c.sendMessage("Your fishing level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9798, 1);
				c.getItems().addItemToBank(9799, 1);
				c.getItems().addItemToBank(9800, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Fishing!");
					}
				}
			}
			break;

		case 11:
			c.sendMessage("Congratulations, you just advanced a fire making level.");
			c.sendMessage("Your firemaking level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9804, 1);
				c.getItems().addItemToBank(9805, 1);
				c.getItems().addItemToBank(9806, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>] <shad=800000000>" + c.playerName + " "
								+ "just advanced to 135 Fire Making!");
					}
				}
			}
			break;

		case 12:
			c.sendMessage("Congratulations, you just advanced a crafting level.");
			c.sendMessage("Your crafting level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9780, 1);
				c.getItems().addItemToBank(9781, 1);
				c.getItems().addItemToBank(9782, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Crafting!");
					}
				}
			}
			break;

		case 13:
			c.sendMessage("Congratulations, you just advanced a smithing level.");
			c.sendMessage("Your smithing level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9795, 1);
				c.getItems().addItemToBank(9796, 1);
				c.getItems().addItemToBank(9797, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>] <shad=800000000>" + c.playerName + " "
								+ "just advanced to 135 Smithing!");
					}
				}
			}
			break;

		case 14:
			c.sendMessage("Congratulations, you just advanced a mining level.");
			c.sendMessage("Your mining level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9792, 1);
				c.getItems().addItemToBank(9793, 1);
				c.getItems().addItemToBank(9794, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Mining!");
					}
				}
			}
			break;

		case 15:
			c.sendMessage("Congratulations, you just advanced a herblore level.");
			c.sendMessage("Your herblore level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9774, 1);
				c.getItems().addItemToBank(9775, 1);
				c.getItems().addItemToBank(9776, 1);

				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Herblore!");
					}
				}
			}
			break;

		case 16:
			c.sendMessage("Congratulations, you just advanced an agility level.");
			c.sendMessage("Your agility level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9771, 1);
				c.getItems().addItemToBank(9772, 1);
				c.getItems().addItemToBank(9773, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>] <shad=800000000>" + c.playerName + " "
								+ "just advanced to 135 Agility!");
					}
				}
			}
			break;

		case 17:
			c.sendMessage("Congratulations, you just advanced a thieving level.");
			c.sendMessage("Your theiving level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9777, 1);
				c.getItems().addItemToBank(9778, 1);
				c.getItems().addItemToBank(9779, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>] <shad=800000000>" + c.playerName + " "
								+ "just advanced to 135 Thieving!");
					}
				}
			}
			break;

		case 18:

			c.sendMessage("Congratulations, you just advanced a slayer level.");
			c.sendMessage("Your slayer level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9786, 1);
				c.getItems().addItemToBank(9787, 1);
				c.getItems().addItemToBank(9788, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>] <shad=800000000>" + c.playerName + " "
								+ "just advanced to 135 Slayer!");
					}
				}
			}
			break;
		case 19:
			c.sendMessage("Congratulations, you just advanced a farming level.");
			c.sendMessage("Your farming level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9810, 1);
				c.getItems().addItemToBank(9811, 1);
				c.getItems().addItemToBank(9812, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Farming!");
					}
				}
			}
			break;

		case 20:

			c.sendMessage("Congratulations, you just advanced a runecrafting level.");
			c.sendMessage("Your runecrafting level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(13342, 1);
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9765, 1);
				c.getItems().addItemToBank(9766, 1);
				c.getItems().addItemToBank(9767, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded A Mystery Box and 5 Skill points!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Runecrafting!");
					}
				}
			}
			break;

		case 21:
			c.sendMessage("Congratulations, you just advanced a summoning level.");
			c.sendMessage("Your summoning level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(12169, 1);
				c.getItems().addItemToBank(12170, 1);
				c.getItems().addItemToBank(12171, 1);
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				c.sendMessage("You've Been Awarded with 3 squeal spins!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Summoning!");

					}
				}
			}
			break;

		case 22:
			c.sendMessage("Congratulations, you just advanced a Hunter level.");
			c.sendMessage("Your Hunter level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9948, 1);
				c.getItems().addItemToBank(9949, 1);
				c.getItems().addItemToBank(9950, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Hunter!");
					}
				}
			}
			break;

		case 23:
			c.sendMessage("Congratulations, you just advanced a Construction level.");
			c.sendMessage("Your Construction level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			c.sendMessage("You've Been Awarded 1 Skill point for advancing a level!");
			c.Skillpoints++;
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(9789, 1);
				c.getItems().addItemToBank(9790, 1);
				c.getItems().addItemToBank(9791, 1);
				c.Skillpoints += 5;
				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Construction!");
					}
				}
			}
			break;

		case 24:
			c.sendMessage("Congratulations, you just advanced a Dungeoneering level.");
			c.sendMessage("Your Dungeoneering level is now " + getLevelForXP(c.playerXP[skill]) + ".");
			if (getLevelForXP(c.playerXP[skill]) == 99) {
				c.getItems().addItem(995, 10000000);
				c.getItems().addItemToBank(18508, 1);
				c.getItems().addItemToBank(18509, 1);

				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 99 Dungeoneering!");
					}
				}
			}
			if (getLevelForXP(c.playerXP[skill]) == 135) {
				c.getItems().addItem(19710, 1);
				c.getItems().addItemToBank(19710, 1);

				c.sendMessage("Your Skillcape Has Been Added to Your Bank!");
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client) Server.playerHandler.players[j];
						c2.sendMessage("[<col=37772>SERVER</col>]<shad=800000000> " + c.playerName + " "
								+ "just advanced to 135 Dungeoneering!");
					}
				}
			}
			break;

		}

	}

	@SuppressWarnings("unused")
	public void refreshSkill(int i) {
		int attackLeft = (getXPForLevel(getLevelForXP(c.playerXP[0]) + 1) - c.playerXP[0]);
		int defenceLeft = (getXPForLevel(getLevelForXP(c.playerXP[1]) + 1) - c.playerXP[1]);
		int strengthLeft = (getXPForLevel(getLevelForXP(c.playerXP[2]) + 1) - c.playerXP[2]);
		int hitpointsLeft = (getXPForLevel(getLevelForXP(c.playerXP[3]) + 1) - c.playerXP[3]);
		int rangeLeft = (getXPForLevel(getLevelForXP(c.playerXP[4]) + 1) - c.playerXP[4]);
		int prayerLeft = (getXPForLevel(getLevelForXP(c.playerXP[5]) + 1) - c.playerXP[5]);
		int mageLeft = (getXPForLevel(getLevelForXP(c.playerXP[6]) + 1) - c.playerXP[6]);
		int cookingLeft = (getXPForLevel(getLevelForXP(c.playerXP[7]) + 1) - c.playerXP[7]);
		int woodcuttingLeft = (getXPForLevel(getLevelForXP(c.playerXP[8]) + 1) - c.playerXP[8]);
		int fletchingLeft = (getXPForLevel(getLevelForXP(c.playerXP[9]) + 1) - c.playerXP[9]);
		int fishingLeft = (getXPForLevel(getLevelForXP(c.playerXP[10]) + 1) - c.playerXP[10]);
		int firemakingLeft = (getXPForLevel(getLevelForXP(c.playerXP[11]) + 1) - c.playerXP[11]);
		int craftingLeft = (getXPForLevel(getLevelForXP(c.playerXP[12]) + 1) - c.playerXP[12]);
		int smithingLeft = (getXPForLevel(getLevelForXP(c.playerXP[13]) + 1) - c.playerXP[13]);
		int miningLeft = (getXPForLevel(getLevelForXP(c.playerXP[14]) + 1) - c.playerXP[14]);
		int herbloreLeft = (getXPForLevel(getLevelForXP(c.playerXP[15]) + 1) - c.playerXP[15]);
		int agilityLeft = (getXPForLevel(getLevelForXP(c.playerXP[16]) + 1) - c.playerXP[16]);
		int thievingLeft = (getXPForLevel(getLevelForXP(c.playerXP[17]) + 1) - c.playerXP[17]);
		int slayerLeft = (getXPForLevel(getLevelForXP(c.playerXP[18]) + 1) - c.playerXP[18]);
		int farmingLeft = (getXPForLevel(getLevelForXP(c.playerXP[19]) + 1) - c.playerXP[19]);
		int runecraftLeft = (getXPForLevel(getLevelForXP(c.playerXP[20]) + 1) - c.playerXP[20]);
		int summoningLeft = (getXPForLevel(getLevelForXP(c.playerXP[21]) + 1) - c.playerXP[21]);
		int hunterLeft = (getXPForLevel(getLevelForXP(c.playerXP[22]) + 1) - c.playerXP[22]);
		int constructionLeft = (getXPForLevel(getLevelForXP(c.playerXP[23]) + 1) - c.playerXP[23]);
		int dungeoneeringLeft = (getXPForLevel(getLevelForXP(c.playerXP[24]) + 1) - c.playerXP[24]);
		switch (i) {
		case 0:// atack
			sendFrame126("" + c.playerLevel[0] + "", 4004);
			sendFrame126("" + getLevelForXP(c.playerXP[0]) + "", 4005);
			sendFrame126("" + c.playerXP[0] + "", 4044);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[0]) + 1) + "", 4045);
			sendFrame126("" + attackLeft + "", 18792);
			sendFrame126(c.playerLevel[0] + "/" + getLevelForXP(c.playerXP[0]), 18790);
			break;

		case 1:// defence
			sendFrame126("" + c.playerLevel[1] + "", 4008);
			sendFrame126("" + getLevelForXP(c.playerXP[1]) + "", 4009);
			sendFrame126("" + c.playerXP[1] + "", 4056);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[1]) + 1) + "", 4057);
			sendFrame126("" + defenceLeft + "", 18817);
			sendFrame126(c.playerLevel[1] + "/" + getLevelForXP(c.playerXP[1]), 18815);
			break;

		case 2:// strength
			sendFrame126("" + c.playerLevel[2] + "", 4006);
			sendFrame126("" + getLevelForXP(c.playerXP[2]) + "", 4007);
			sendFrame126("" + c.playerXP[2] + "", 4050);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[2]) + 1) + "", 4051);
			sendFrame126("" + strengthLeft + "", 18798);
			sendFrame126(c.playerLevel[2] + "/" + getLevelForXP(c.playerXP[2]), 18796);
			break;

		case 3:// hitpoints
			sendFrame126("" + c.playerLevel[3] + "", 4016);
			sendFrame126("" + getLevelForXP(c.playerXP[3]) + "", 4017);
			sendFrame126("" + c.playerXP[3] + "", 18853);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[3]) + 1) + "", 18854);
			sendFrame126("" + hitpointsLeft + "", 18859);
			sendFrame126(c.playerLevel[3] + "/" + getLevelForXP(c.playerXP[3]), 18857);
			break;

		case 4:// range
			sendFrame126("" + c.playerLevel[4] + "", 4010);
			sendFrame126("" + getLevelForXP(c.playerXP[4]) + "", 4011);
			sendFrame126("" + c.playerXP[4] + "", 4062);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[4]) + 1) + "", 4063);
			sendFrame126("" + rangeLeft + "", 18822);
			sendFrame126(c.playerLevel[4] + "/" + getLevelForXP(c.playerXP[4]), 18820);
			break;

		case 5:// prayer
			sendFrame126("" + c.playerLevel[5] + "", 4012);
			sendFrame126("" + getLevelForXP(c.playerXP[5]) + "", 4013);
			sendFrame126("" + c.playerXP[5] + "", 4068);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[5]) + 1) + "", 4069);
			sendFrame126("" + c.playerLevel[5] + "/" + getLevelForXP(c.playerXP[5]) + "", 687);// Prayer
																								// frame
			sendFrame126("" + prayerLeft + "", 18827);
			sendFrame126(c.playerLevel[5] + "/" + getLevelForXP(c.playerXP[5]), 18825);
			break;

		case 6:// magic
			sendFrame126("" + c.playerLevel[6] + "", 4014);
			sendFrame126("" + getLevelForXP(c.playerXP[6]) + "", 4015);
			sendFrame126("" + c.playerXP[6] + "", 18832);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[6]) + 1) + "", 18833);
			sendFrame126("" + mageLeft + "", 18838);
			sendFrame126(c.playerLevel[6] + "/" + getLevelForXP(c.playerXP[6]), 18836);
			break;

		case 7:// cooking
			sendFrame126("" + c.playerLevel[7] + "", 4034);
			sendFrame126("" + getLevelForXP(c.playerXP[7]) + "", 4035);
			sendFrame126("" + c.playerXP[7] + "", 19042);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[7]) + 1) + "", 19043);
			sendFrame126("" + cookingLeft + "", 19048);
			sendFrame126(c.playerLevel[7] + "/" + getLevelForXP(c.playerXP[7]), 19046);
			break;

		case 8:// woodcutting
			sendFrame126("" + c.playerLevel[8] + "", 4038);
			sendFrame126("" + getLevelForXP(c.playerXP[8]) + "", 4039);
			sendFrame126("" + c.playerXP[8] + "", 19084);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[8]) + 1) + "", 19085);
			sendFrame126("" + woodcuttingLeft + "", 19090);
			sendFrame126(c.playerLevel[8] + "/" + getLevelForXP(c.playerXP[8]), 19088);
			break;

		case 9:// fletching
			sendFrame126("" + c.playerLevel[9] + "", 4026);
			sendFrame126("" + getLevelForXP(c.playerXP[9]) + "", 4027);
			sendFrame126("" + c.playerXP[9] + "", 18958);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[9]) + 1) + "", 18959);
			sendFrame126("" + fletchingLeft + "", 18964);
			sendFrame126(c.playerLevel[9] + "/" + getLevelForXP(c.playerXP[9]), 18962);
			break;

		case 10:// fishing
			sendFrame126("" + c.playerLevel[10] + "", 4032);
			sendFrame126("" + getLevelForXP(c.playerXP[10]) + "", 4033);
			sendFrame126("" + c.playerXP[10] + "", 19021);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[10]) + 1) + "", 19022);
			sendFrame126("" + fletchingLeft + "", 19027);
			sendFrame126(c.playerLevel[10] + "/" + getLevelForXP(c.playerXP[10]), 19025);
			break;

		case 11:// firemaking
			sendFrame126("" + c.playerLevel[11] + "", 4036);
			sendFrame126("" + getLevelForXP(c.playerXP[11]) + "", 4037);
			sendFrame126("" + c.playerXP[11] + "", 19063);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[11]) + 1) + "", 19064);
			sendFrame126("" + firemakingLeft + "", 19069);
			sendFrame126(c.playerLevel[11] + "/" + getLevelForXP(c.playerXP[11]), 19067);
			break;

		case 12:// crafting
			sendFrame126("" + c.playerLevel[12] + "", 4024);
			sendFrame126("" + getLevelForXP(c.playerXP[12]) + "", 4025);
			sendFrame126("" + c.playerXP[12] + "", 18937);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[12]) + 1) + "", 18938);
			sendFrame126("" + craftingLeft + "", 18943);
			sendFrame126(c.playerLevel[12] + "/" + getLevelForXP(c.playerXP[12]), 18941);
			break;

		case 13:// smithing
			sendFrame126("" + c.playerLevel[13] + "", 4030);
			sendFrame126("" + getLevelForXP(c.playerXP[13]) + "", 4031);
			sendFrame126("" + c.playerXP[13] + "", 19422);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[13]) + 1) + "", 19423);
			sendFrame126("" + smithingLeft + "", 19428);
			sendFrame126(c.playerLevel[13] + "/" + getLevelForXP(c.playerXP[13]), 19426);
			break;

		case 14:// mining
			sendFrame126("" + c.playerLevel[14] + "", 4028);
			sendFrame126("" + getLevelForXP(c.playerXP[14]) + "", 4029);
			sendFrame126("" + c.playerXP[14] + "", 18979);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[14]) + 1) + "", 18980);
			sendFrame126("" + miningLeft + "", 18985);
			sendFrame126(c.playerLevel[14] + "/" + getLevelForXP(c.playerXP[14]), 18983);
			break;

		case 15:// herblore
			sendFrame126("" + c.playerLevel[15] + "", 4020);
			sendFrame126("" + getLevelForXP(c.playerXP[15]) + "", 4021);
			sendFrame126("" + c.playerXP[15] + "", 18895);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[15]) + 1) + "", 18896);
			sendFrame126("" + herbloreLeft + "", 18901);
			sendFrame126(c.playerLevel[15] + "/" + getLevelForXP(c.playerXP[15]), 18899);
			break;

		case 16:// agility
			sendFrame126("" + c.playerLevel[16] + "", 4018);
			sendFrame126("" + getLevelForXP(c.playerXP[16]) + "", 4019);
			sendFrame126("" + c.playerXP[16] + "", 18874);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[16]) + 1) + "", 18875);
			sendFrame126("" + agilityLeft + "", 18880);
			sendFrame126(c.playerLevel[16] + "/" + getLevelForXP(c.playerXP[16]), 18878);
			break;

		case 17:// thieving
			sendFrame126("" + c.playerLevel[17] + "", 4022);
			sendFrame126("" + getLevelForXP(c.playerXP[17]) + "", 4023);
			sendFrame126("" + c.playerXP[17] + "", 18916);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[17]) + 1) + "", 18917);
			sendFrame126("" + thievingLeft + "", 18922);
			sendFrame126(c.playerLevel[17] + "/" + getLevelForXP(c.playerXP[17]), 18920);
			break;

		case 18:// slayer
			sendFrame126("" + c.playerLevel[18] + "", 18809);
			sendFrame126("" + getLevelForXP(c.playerXP[18]) + "", 18810);
			sendFrame126("" + c.playerXP[18] + "", 19126);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[18]) + 1) + "", 19127);
			sendFrame126("" + slayerLeft + "", 19132);
			sendFrame126(c.playerLevel[18] + "/" + getLevelForXP(c.playerXP[18]), 19130);
			break;

		case 19:// farming
			sendFrame126("" + c.playerLevel[19] + "", 18811);
			sendFrame126("" + getLevelForXP(c.playerXP[19]) + "", 18812);
			sendFrame126("" + c.playerXP[19] + "", 19275);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[19]) + 1) + "", 19276);
			sendFrame126("" + farmingLeft + "", 19281);
			sendFrame126(c.playerLevel[19] + "/" + getLevelForXP(c.playerXP[19]), 19279);
			break;

		case 20:// runecraft
			sendFrame126("" + c.playerLevel[20] + "", 18807);
			sendFrame126("" + getLevelForXP(c.playerXP[20]) + "", 18808);
			sendFrame126("" + c.playerXP[20] + "", 19105);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[20]) + 1) + "", 19106);
			sendFrame126("" + runecraftLeft + "", 19111);
			sendFrame126(c.playerLevel[20] + "/" + getLevelForXP(c.playerXP[20]), 19109);
			break;

		case 21:// summoning
			sendFrame126("" + c.playerLevel[21] + "", 19178);
			sendFrame126("" + getLevelForXP(c.playerXP[21]) + "", 19179);
			sendFrame126("" + c.playerXP[21] + "", 19232);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[21]) + 1) + "", 19233);
			sendFrame126("" + constructionLeft + "", 19238);
			sendFrame126(c.playerLevel[21] + "/" + getLevelForXP(c.playerXP[21]), 19236);
			break;

		case 22:// hunting
			sendFrame126("" + c.playerLevel[22] + "", 19176);
			sendFrame126("" + getLevelForXP(c.playerXP[22]) + "", 19177);
			sendFrame126("" + c.playerXP[22] + "", 19211);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[22]) + 1) + "", 19212);
			sendFrame126("" + hunterLeft + "", 19217);
			sendFrame126(c.playerLevel[22] + "/" + getLevelForXP(c.playerXP[22]), 19215);
			break;

		case 23:// construction
			sendFrame126("" + c.playerLevel[23] + "", 19174);
			sendFrame126("" + getLevelForXP(c.playerXP[23]) + "", 19175);
			sendFrame126("" + c.playerXP[23] + "", 19190);
			sendFrame126("" + getXPForLevel(getLevelForXP(c.playerXP[23]) + 1) + "", 19191);
			sendFrame126("" + summoningLeft + "", 19196);
			sendFrame126(c.playerLevel[23] + "/" + getLevelForXP(c.playerXP[23]), 19194);
			break;

		case 24:
			if (c.playerXP[24] >= 0 && c.playerXP[24] <= 14391160) {
				sendFrame126((new StringBuilder()).append("").append(c.playerLevel[24]).append("").toString(), 19180);
				sendFrame126(
						(new StringBuilder()).append("").append(getLevelForXP(c.playerXP[24])).append("").toString(),
						19254);
				sendFrame126("" + c.playerXP[24] + "", 19253);
				sendFrame126("" + dungeoneeringLeft + "", 19259);
				sendFrame126("120", 19181);
				sendFrame126(c.playerLevel[24] + "/" + getLevelForXP(c.playerXP[24]), 19257);
			} else if (c.playerXP[24] >= 14391161 && c.playerXP[24] <= 15889108) {
				sendFrame126("100", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 15889109 && c.playerXP[24] <= 17542976) {
				sendFrame126("101", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 17542977 && c.playerXP[24] <= 19368991) {
				sendFrame126("102", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 19368992 && c.playerXP[24] <= 21385072) {
				sendFrame126("103", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 21385073 && c.playerXP[24] <= 23611005) {
				sendFrame126("104", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 23611006 && c.playerXP[24] <= 26068631) {
				sendFrame126("105", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 26068632 && c.playerXP[24] <= 28782068) {
				sendFrame126("106", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 28782069 && c.playerXP[24] <= 31777942) {
				sendFrame126("107", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 31777943 && c.playerXP[24] <= 35085653) {
				sendFrame126("108", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 35085654 && c.playerXP[24] <= 38737660) {
				sendFrame126("109", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 38737661 && c.playerXP[24] <= 42769799) {
				sendFrame126("110", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 42769800 && c.playerXP[24] <= 47221639) {
				sendFrame126("111", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 47221640 && c.playerXP[24] <= 52136868) {
				sendFrame126("112", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 52136869 && c.playerXP[24] <= 57563717) {
				sendFrame126("113", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 57563718 && c.playerXP[24] <= 63555442) {
				sendFrame126("114", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 63555443 && c.playerXP[24] <= 70170839) {
				sendFrame126("115", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 70170840 && c.playerXP[24] <= 77474827) {
				sendFrame126("116", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 77474828 && c.playerXP[24] <= 85539081) {
				sendFrame126("117", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 85539082 && c.playerXP[24] <= 94442735) {
				sendFrame126("118", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 94442736 && c.playerXP[24] <= 104273166) {
				sendFrame126("119", 19180);
				sendFrame126("120", 19181);
			} else if (c.playerXP[24] >= 104273167 && c.playerXP[24] <= 200000000) {
				sendFrame126("120", 19180);
				sendFrame126("120", 19181);
			}
		}
	}

	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
			if (lvl >= level)
				return output;
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		// if (exp > 13034430)
		// return 135;
		for (int lvl = 1; lvl <= lvl; lvl++) {
			points += Math.floor((double) lvl + 300.0 * Math.pow(2.0, (double) lvl / 7.0));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 135;
	}

	@SuppressWarnings("static-access")
	public boolean addSkillXP(int amount, int skill) {
		if (c.xpLock == true || c.Jail == true) {
			c.sendMessage("No XP added - reason: XP LOCK!");
			return false;
		}
		if (amount + c.playerXP[skill] < 0 || c.playerXP[skill] > 460504778) {
			return false;
		}
		if (c.playerEquipment[c.playerRing] == 15017) // your ring ID here
		{
			amount *= Config.SERVER_EXP_BONUS * 2;
		} else {
			amount *= Config.SERVER_EXP_BONUS;
		}
		int oldLevel = getLevelForXP(c.playerXP[skill]);
		c.playerXP[skill] += amount;
		if (oldLevel < getLevelForXP(c.playerXP[skill])) {
			if (c.playerLevel[skill] < c.getLevelForXP(c.playerXP[skill]) && skill != 3 && skill != 5)
				c.playerLevel[skill] = c.getLevelForXP(c.playerXP[skill]);
			levelUp(skill);
			c.gfx100(199);
			requestUpdates();
		}
		setSkillLevel(skill, c.playerLevel[skill], c.playerXP[skill]);
		refreshSkill(skill);
		return true;
	}

	public void resetBarrows() {
		c.barrowsNpcs[0][1] = 0;
		c.barrowsNpcs[1][1] = 0;
		c.barrowsNpcs[2][1] = 0;
		c.barrowsNpcs[3][1] = 0;
		c.barrowsNpcs[4][1] = 0;
		c.barrowsNpcs[5][1] = 0;
		c.barrowsKillCount = 0;
		c.randomCoffin = Misc.random(3) + 1;
	}

	public static int birdNestRewards[] = { 1636, 5297, 995, 5298, 5299, 5296, 5295, 1644, 1642, 995, 7498 };
	public static int Barrows[] = { 4708, 4710, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734,
			4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759 };
	public static int Crystal[] = {3529,3531,3533,3535,3529,3531,3533,3535,3529,3531,3533,3535,3529,3531,3533,3535,
			3529,3531,3533,3535,3529,3531,3533,3535,3529,3531,3533,3535,3529,3531,3533,3535,3529,3531,3533,3535,
			3529,3531,3533,3535,3529,3531,3533,3535,3537,3539,3537,3539,3537,3539,3537,3539,3537,3539,3537,3539,3541,3543
			,3541,3543,3541,3543,3545,3547,1038,1040,1042,1044,1046};
	/*public static int Crystal[] = { 4708, 4710, 17848, 4712, 4714, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732,
			4734, 4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759, 13884, 13890, 13896, 13887, 13893, 13899,
			13905, 15441, 15442, 15443, 15444, 11596, 19036, 19037, 19038, 19040, 19041, 2572, 6137, 6139, 6141, 6147,
			6153, 1419, 4585, 11732, 1305, 6773, 6131, 6133, 6135, 6149, 6570, 11724, 11726, 11728, 15220, 13858, 13861,
			13740, 13738, 13742, 11349, 8842, 11664, 11663, 11665, 19785, 19790, 11720, 11722 };*/
	public static int Mini[] = { 19924, 7449, 18724, 18746, 18744, 13642, 1053, 1055, 1057, 989, 19337, 19341, 19336,
			19338, 19339, 19340, 19342, 19343, 19345, 19344, 13822, 15488, 13225, 13832, 1113, 1127, 1147, 1163, 1185,
			8650, 8652, 8654, 8656, 8658, 8660, 8662, 8664, 8666, 8668, 8670, 8672, 8674, 8676, 8678, 8680, 4037, 4039,
			10400, 10402, 10404, 10406, 10408, 10410, 10412, 10414, 10416, 10418, 10420, 10422, 10424, 10426, 10428,
			10430, 10432, 10434, 10436, 10438, 2651, 2978, 2979, 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2986, 2987,
			2988, 2989, 2990, 2991, 2992, 2993, 2994, 2995, 6182, 4151, 10069, 10074, 10171, 4708, 4710, 4712, 4714,
			4753, 4755, 4757, 4759, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4716, 4718,
			4720, 4722, 14490, 14492, 14494, 14497, 14499, 14571, 14572, 14573, 14577, 14578, 14579, 14600, 14601,
			14602, 14603, 14604, 14605, 15443, 14679, 14743, 14745, 14747, 14749, 14751, 14753, 14755, 14757, 14759,
			14761, 14763, 14767, 14769, 14771, 14773, 14775, 14777, 14783, 14785, 14787, 14789, 14791, 7320, 7321, 7322,
			7323, 7324, 7325, 7326, 7327, 7328, 7329, 7330, 7331, 7332, 7333, 7334, 7335, 7336, 7337, 7338, 7339, 7340,
			7341, 7342, 7343, 7344, 7345, 7346, 7347, 7348, 7349, 7350, 7351, 7352, 7353, 7354, 7355, 7356, 7357, 7358,
			7359, 7360, 7361, 7362, 7363, 7364, 7365, 7366, 7367, 7368, 7369, 7371, 7372, 7373, 7374, 7375, 7376, 7377,
			7378, 7379, 7380, 7381, 7382, 7383, 7384, 7385, 7386, 7387, 7388, 7389, 7390, 7391, 7392, 7393, 7394, 7395,
			7396, 7397, 7398, 7399, 7400, 7401, 13362, 13360, 13358, 13355, 13354, 13352, 13350, 13348, 13346, 990,
			1033, 1035, 1037, 1053, 1057, 1059, 1060, 1061, 1062 };
	public static int Runes[] = { 4740, 558, 560, 565 };
	public static int Pots[] = {};
	public static int Prez[] = { 962, 2572, 9470, 9472, 13101, 18667, 10398, 15509, 1837, 3486, 3481, 3483, 3485, 3488,
			10840, 19323 };
	public static int Scav[] = { 2665, 2661, 2663, 2667, 2669, 2671, 2673, 2675, 2619, 2615, 2617, 2621, 2657, 2653,
			2655, 2659, 2627, 2623, 2625, 2629, 19748, 9895, 9897, 2651, 859, 1373, 1303, 11726, 11274, 989, 11335,
			4151, 14484, 15486, 7534, 11651, 11652, 11653, 11654, 11720, 11722, 1037, 19336, 19337, 19338, 19339, 19340,
			8842, 8839, 8840, 8841, 18778, 10446, 10448, 10450, 13362, 13358, 13360, 13355, 13354, 13352, 13350, 13348,
			13346, 11368, 9005, 19960, 11644, 1366, 7806, 7807, 7808, 7809, 10462, 10466, 10460, 10468, 10458, 10464,
			732, 6585, 20089, 20097, 13458, 13459, 6575, 2572 };
	public static int arti[] = { 14876, 14877, 14878, 14879, 14880, 14881, 14882, 14883, 14884, 14885, 14886, 14887,
			14888, 14889, 14890, 14891, 14892 };

	public int randomBarrows() {
		return Barrows[(int) (Math.random() * Barrows.length)];
	}

	public int randomCrystal() {
		return Crystal[(int) (Math.random() * Crystal.length)];
	}

	public int randomScav() {
		return Scav[(int) (Math.random() * Scav.length)];
	}

	public int randomPrez() {
		return Prez[(int) (Math.random() * Prez.length)];
	}

	public int randomBirdNests() {
		return birdNestRewards[(int) (Math.random() * birdNestRewards.length)];
	}

	public int randomMini() {
		return Crystal[(int) (Math.random() * Crystal.length)];
	}

	public int randomRunes() {
		return Runes[(int) (Math.random() * Runes.length)];
	}

	public int randomPots() {
		return Pots[(int) (Math.random() * Pots.length)];
	}

	/**
	 * Show an arrow icon on the selected player.
	 * 
	 * @Param i - Either 0 or 1; 1 is arrow, 0 is none.
	 * @Param j - The player/Npc that the arrow will be displayed above.
	 * @Param k - Keep this set as 0
	 * @Param l - Keep this set as 0
	 */
	public void drawHeadicon(int i, int j, int k, int l) {
		// synchronized(c) {
		c.outStream.createFrame(254);
		c.outStream.writeByte(i);

		if (i == 1 || i == 10) {
			c.outStream.writeWord(j);
			c.outStream.writeWord(k);
			c.outStream.writeByte(l);
		} else {
			c.outStream.writeWord(k);
			c.outStream.writeWord(l);
			c.outStream.writeByte(j);
		}
	}

	// }

	@SuppressWarnings("static-access")
	public int getNpcId(int id) {
		for (int i = 0; i < NPCHandler.maxNPCs; i++) {
			if (NPCHandler.npcs[i] != null) {
				if (Server.npcHandler.npcs[i].npcId == id) {
					return i;
				}
			}
		}
		return -1;
	}

	public void removeObject(int x, int y) {
		object(-1, x, x, 10, 10);
	}

	public void objectToRemove(int X, int Y) {
		object(-1, X, Y, 10, 10);
	}

	private void objectToRemove2(int X, int Y) {
		object(-1, X, Y, -1, 0);
	}

	public void removeObjects() {
		objectToRemove2(2638, 4688);
		objectToRemove(2638, 4688);
		objectToRemove(2844, 3440);
		objectToRemove(2846, 3437);
		objectToRemove(2840, 3439);
		objectToRemove(2841, 3443);
		objectToRemove2(2635, 4693);
		objectToRemove2(2634, 4693);
		objectToRemove2(2794, 9327);
		objectToRemove(3206, 3263);
		objectToRemove(3193, 3274);
		objectToRemove(3193, 3273);
	}

	public void handleGlory(int gloryId) {
		c.getDH().sendOption4("Edgeville", "Al Kharid", "Draynor Village", "Mage Bank");
		c.usingGlory = true;
	}

	@SuppressWarnings("static-access")
	public void gloryDegrade() {
		if (c.gdegradeNow == true) {
			if (c.gloryValue == 3) {
				if (c.getItems().playerHasItem(1712, 1)) {
					c.getItems().addItem(1710, 1);
					c.getItems().deleteItem(1712, 1);
					c.gloryValue = 4;
					c.gdegradeNow = false;
				}
			} else {
				if (c.playerEquipment[c.playerAmulet] == 1712) {
					c.playerEquipment[c.playerAmulet] = 1710;
					c.gloryValue = 4;
					c.gdegradeNow = false;
				}
			}
			if (c.gloryValue == 2) {
				if (c.getItems().playerHasItem(1710, 1)) {
					c.getItems().addItem(1708, 1);
					c.getItems().deleteItem(1710, 1);
					c.gloryValue = 4;
					c.gdegradeNow = false;
				}
			} else {
				if (c.playerEquipment[c.playerAmulet] == 1710) {
					c.playerEquipment[c.playerAmulet] = 1708;
					c.gloryValue = 4;
					c.gdegradeNow = false;
				}
			}
			if (c.gloryValue == 1) {
				if (c.getItems().playerHasItem(1708, 1)) {
					c.getItems().addItem(1706, 1);
					c.getItems().deleteItem(1708, 1);
					c.gloryValue = 4;
					c.gdegradeNow = false;
				}
			} else {
				if (c.playerEquipment[c.playerAmulet] == 1708) {
					c.playerEquipment[c.playerAmulet] = 1706;
					c.gloryValue = 4;
					c.gdegradeNow = false;
				}
			}
			if (c.gloryValue == 0) {
				if (c.getItems().playerHasItem(1706, 1)) {
					c.getItems().addItem(1704, 1);
					c.getItems().deleteItem(1706, 1);
					c.sendMessage("Your amulet is out of charges! Recharge location not added yet.");
					c.gdegradeNow = false;
				}
			} else {
				if (c.playerEquipment[c.playerAmulet] == 1706) {
					c.playerEquipment[c.playerAmulet] = 1704;
					c.gloryValue = 4;
					c.gdegradeNow = false;
				}
			}
		}
	}

	public void resetVariables() {
		c.getCrafting().resetCrafting();
		c.usingGlory = false;
		c.smeltInterface = false;
		c.smeltType = 0;
		c.smeltAmount = 0;
		c.woodcut[0] = c.woodcut[1] = c.woodcut[2] = 0;
	}

	public boolean inPitsWait() {
		return c.getX() <= 2404 && c.getX() >= 2394 && c.getY() <= 5175 && c.getY() >= 5169;
	}

	public void castleWarsObjects() {
		object(-1, 2373, 3119, -3, 10);
		object(-1, 2372, 3119, -3, 10);
	}

	/*
	 * public void removeFromCW() { if (c.castleWarsTeam == 1) { if (c.inCwWait)
	 * { Server.castleWars.saradominWait.remove(Server.castleWars.saradominWait.
	 * indexOf(c.playerId)); } else {
	 * Server.castleWars.saradomin.remove(Server.castleWars.saradomin.indexOf(c.
	 * playerId)); } } else if (c.castleWarsTeam == 2) { if (c.inCwWait) {
	 * Server.castleWars.zamorakWait.remove(Server.castleWars.zamorakWait.
	 * indexOf(c.playerId)); } else {
	 * Server.castleWars.zamorak.remove(Server.castleWars.zamorak.indexOf(c.
	 * playerId)); } } }
	 */

	@SuppressWarnings("static-access")
	public int antiFire() {
		int toReturn = 0;
		if (c.antiFirePot)
			toReturn++;
		if (c.playerEquipment[c.playerShield] == 1540 || c.prayerActive[12]
				|| c.playerEquipment[c.playerShield] == 11284 || c.playerEquipment[c.playerShield] == 18747
				|| c.playerEquipment[c.playerShield] == 11283)
			toReturn++;
		c.getCombat().addCharge(c);
		return toReturn;
	}

	public int backupInvItems[] = new int[28];
	public int backupInvItemsN[] = new int[28];

	public void otherInv(Client c, Client o) {
		if (o == c || o == null || c == null)
			return;
		int[] backupItems = c.playerItems;
		int[] backupItemsN = c.playerItemsN;
		c.playerItems = o.playerItems;
		c.playerItemsN = o.playerItemsN;

		c.getItems().resetItems(3214);

		c.playerItems = backupItems;
		c.playerItemsN = backupItemsN;
	}

	public boolean checkForFlags() {
		int[][] itemsToCheck = { { 995, 100000000 }, { 35, 5 }, { 667, 5 }, { 2402, 5 }, { 746, 5 }, { 4151, 150 },
				{ 565, 100000 }, { 560, 100000 }, { 555, 300000 }, { 11235, 10 } };
		for (int j = 0; j < itemsToCheck.length; j++) {
			if (itemsToCheck[j][1] < c.getItems().getTotalCount(itemsToCheck[j][0]))
				return true;
		}
		return false;
	}

	public void addStarter() {
		
		c.getTutorial().tutorialStage = 0;
		c.getTutorial().handleTutorial();
	}

	public void useOperate(int itemId) {
		switch (itemId) {
		case 1712:
		case 1710:
		case 1708:
		case 1706:
			handleGlory(itemId);
			break;
		case 11283:
		case 11284:
			c.sendMessage("Your shield has " + c.dfsCount + " charges");
			if (c.playerIndex > 0) {
				c.getCombat().handleDfs();
			} else if (c.npcIndex > 0) {
				c.getCombat().handleDfsNPC();
			}
			break;
		case 15078:
			c.getDH().sendDialogues(15000, 1);
			break;
		}
	}

	public void getSpeared(int otherX, int otherY) {
		int x = c.absX - otherX;
		int y = c.absY - otherY;
		if (x > 0)
			x = 1;
		else if (x < 0)
			x = -1;
		if (y > 0)
			y = 1;
		else if (y < 0)
			y = -1;
		moveCheck(x, y);
		c.lastSpear = System.currentTimeMillis();
	}

	public void moveCheck(int xMove, int yMove) {
		movePlayer(c.absX + xMove, c.absY + yMove, c.heightLevel);
	}

	public int findKiller() {
		int killer = c.playerId;
		int damage = 0;
		for (int j = 0; j < Config.MAX_PLAYERS; j++) {
			if (PlayerHandler.players[j] == null)
				continue;
			if (j == c.playerId)
				continue;
			if (c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX, PlayerHandler.players[j].absY, 40)
					|| c.goodDistance(c.absX, c.absY + 9400, PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY, 40)
					|| c.goodDistance(c.absX, c.absY, PlayerHandler.players[j].absX,
							PlayerHandler.players[j].absY + 9400, 40))
				if (c.damageTaken[j] > damage) {
					damage = c.damageTaken[j];
					killer = j;
				}
		}
		return killer;
	}

	public void resetTzhaar() {
		// c.jadCave = false;
		c.waveId = -1;
		c.tzhaarToKill = 1;
		c.tzhaarKilled = -1;
		c.getPA().movePlayer(2438, 5168, 0);
	}

	public void resetRFD() {
		c.waveId = -1;
		c.RFDToKill = -1;
		c.RFDKilled = -1;
		c.getPA().movePlayer(2607, 3097, 0);
	}

	public void enterRFD() {
		if (c.Culin == true) {
			c.sendMessage("You have already finished this minigame!");
			return;
		}
		if (c.Agrith == true && c.Flambeed == false) {
			c.waveId = 1;
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			Server.rfd.spawnNextWave(c);
			return;
		}
		if (c.Flambeed == true && c.Karamel == false) {
			c.waveId = 2;
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			Server.rfd.spawnNextWave(c);
			return;
		}
		if (c.Karamel == true && c.Dessourt == false) {
			c.waveId = 3;
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			Server.rfd.spawnNextWave(c);
			return;
		}
		if (c.Dessourt == true && c.Culin == false) {
			c.waveId = 4;
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			Server.rfd.spawnNextWave(c);
			return;
		}
		if (c.Agrith == false) {
			c.getPA().movePlayer(1899, 5363, c.playerId * 4 + 2);
			c.waveId = 0;
			c.RFDToKill = -1;
			c.RFDKilled = -1;
			Server.rfd.spawnNextWave(c);
		}
	}

	public void enterCaves() {
		// c.getPA().movePlayer(2413,5117, c.playerId * 4);
		c.getPA().movePlayer(2408, 5087, c.playerId * 4);
		// c.jadEvent();
		c.sendMessage("He will spawn in few seconds, pot up / pray!");
		c.sendMessage("To exit, run north and enter the cave!");
		c.waveId = 0;
		c.tzhaarToKill = -1;
		c.tzhaarKilled = -1;
		// Server.fightCaves.spawnNextWave(c);
		c.jadSpawn();
	}

	public void enterNewCaves() {
		if (!c.getItems().playerHasItem(6570, 1)) {
			c.sendMessage("You don't have a FireCape in you're inventory to sacrifice!");
			c.getPA().closeAllWindows();
			return;
		} else {
			// c.getPA().movePlayer(2413,5117, c.playerId * 4);
			c.getPA().movePlayer(2408, 5087, c.playerId * 4);
			// c.jadEvent();
			c.sendMessage("Waves will spawn in few seconds, pot up / pray!");
			c.sendMessage("To exit, run north and enter the cave!");
			c.sendMessage("IF YOU RELOG YOU WILL HAVE TO RE-DO EVERY WAVE!");
			c.getItems().deleteItem(6570, 1);
			c.waveId = 0;
			c.sendMessage("NOTE: You CAN loose you're items sometimes!");
			// Server.fightCaves.spawnNextWave(c);
			c.HardWaveSpawn();
		}

	}

	public void handleFightCavesWinNewOne() {
		c.getItems().addItem(19111, 1);
		c.sendMessage("Congratulations! You've completed all waves! Enjoy the new cape!");
		c.getPA().resetTzhaar();
	}

	public void enterNomad() {
		if (c.Nomad == true) {
			c.sendMessage("You have already finished this minigame!");
			return;
		}
		c.getPA().movePlayer(2502, 3012, c.playerId * 4);
		c.waveId = 0;
		c.nomadSpawn();
	}

	public void enterElvarg() {
		if (c.kingQuest == 8) {
			c.sendMessage("You have already finished this Quest");
			return;
		}
		c.getPA().movePlayer(2855, 9637, c.playerId * 4);
		c.waveId = 0;
		c.ElvargSpawn();
	}

	public void enterGoblin() {
		if (c.Goblin == true) {
			c.sendMessage("You have already finished this minigame!");
			return;
		}
		c.getPA().movePlayer(2541, 3034, c.playerId * 4);
		c.waveId = 0;
		c.GoblinSpawn();
	}

	public void appendPoison(int damage) {
		if (System.currentTimeMillis() - c.lastPoisonSip > c.poisonImmune) {
			c.sendMessage("You have been poisoned.");
			c.poisonDamage = damage;
		}
	}

	public boolean checkForPlayer(int x, int y) {
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				if (p.getX() == x && p.getY() == y)
					return true;
			}
		}
		return false;
	}

	public void checkPouch(int i) {
		if (i < 0)
			return;
		c.sendMessage("This pouch has " + c.pouches[i] + " rune ess in it.");
	}

	public void fillPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > c.getItems().getItemAmount(1436)) {
			toAdd = c.getItems().getItemAmount(1436);
		}
		if (toAdd > c.POUCH_SIZE[i] - c.pouches[i])
			toAdd = c.POUCH_SIZE[i] - c.pouches[i];
		if (toAdd > 0) {
			c.getItems().deleteItem(1436, toAdd);
			c.pouches[i] += toAdd;
		}
	}

	public void emptyPouch(int i) {
		if (i < 0)
			return;
		int toAdd = c.pouches[i];
		if (toAdd > c.getItems().freeSlots()) {
			toAdd = c.getItems().freeSlots();
		}
		if (toAdd > 0) {
			c.getItems().addItem(1436, toAdd);
			c.pouches[i] -= toAdd;
		}
	}

	public void fixAllBarrows() {
		int totalCost = 0;
		int cashAmount = c.getItems().getItemAmount(995);
		for (int j = 0; j < c.playerItems.length; j++) {
			boolean breakOut = false;
			for (int i = 0; i < c.getItems().brokenBarrows.length; i++) {
				if (c.playerItems[j] - 1 == c.getItems().brokenBarrows[i][1]) {
					if (totalCost + 80000 > cashAmount) {
						breakOut = true;
						c.sendMessage("You have run out of money.");
						break;
					} else {
						totalCost += 80000;
					}
					c.playerItems[j] = c.getItems().brokenBarrows[i][0] + 1;
				}
			}
			if (breakOut)
				break;
		}
		if (totalCost > 0)
			c.getItems().deleteItem(995, c.getItems().getItemSlot(995), totalCost);
	}

	public void handleLoginText() {
		c.getPA().sendFrame126("Monster Teleport", 13037);
		c.getPA().sendFrame126("Minigame Teleport", 13047);
		c.getPA().sendFrame126("Boss Teleport", 13055);
		c.getPA().sendFrame126("Pking Teleport", 13063);
		c.getPA().sendFrame126("Skill Teleport", 13071);
		c.getPA().sendFrame126("Monster Teleport", 1300);
		c.getPA().sendFrame126("Minigame Teleport", 1325);
		c.getPA().sendFrame126("Boss Teleport", 1350);
		c.getPA().sendFrame126("Pking Teleport", 1382);
		c.getPA().sendFrame126("Skill Teleport", 1415);
		c.getPA().sendFrame126("City Teleport", 1454);
		c.getPA().sendFrame126("More Skilling", 7457);
		c.getPA().sendFrame126("Miasmic Barrage", 13097);
		c.getPA().sendFrame126("More Skilling", 13089);
		c.getPA().sendFrame126("City Teleport", 13081);

	}

	public void handleWeaponStyle() {
		if (c.fightMode == 0) {
			c.getPA().sendFrame36(43, c.fightMode);
		} else if (c.fightMode == 1) {
			c.getPA().sendFrame36(43, 3);
		} else if (c.fightMode == 2) {
			c.getPA().sendFrame36(43, 1);
		} else if (c.fightMode == 3) {
			c.getPA().sendFrame36(43, 2);
		}
	}

}
