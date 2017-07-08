package server.model.players.skills;

import server.Config;
import server.CycleEvent;
import server.CycleEventContainer;
import server.CycleEventHandler;
import server.model.players.AchievementManager;
import server.model.players.Achievements;
import server.model.players.Client;

@SuppressWarnings("all")
public class Prayer {

	Client c;

	public int[][] bonesExp = { { 526, 50 }, { 532, 100 }, { 534, 150 },
			{ 536, 200 }, { 6729, 250 }, { 18830, 350 } };

	public Prayer(Client c) {
		this.c = c;
	}

	public void buryBone(int id, int slot) {
		if (System.currentTimeMillis() - c.buryDelay > 1500) {
			c.getItems().deleteItem(id, slot, 1);
			c.sendMessage("You bury the bones.");
			c.getPA().addSkillXP(getExp(id) * Config.PRAYER_EXPERIENCE, 5);
			c.buryDelay = System.currentTimeMillis();
			c.startAnimation(827);
		}
	}

	public void bonesOnAltar(int id) {
		if (System.currentTimeMillis() - c.buryDelay > 1000) {
			c.getItems().deleteItem(id, c.getItems().getItemSlot(id), 1);
			c.sendMessage("The gods are pleased with your offering.");
			AchievementManager.increase(c, Achievements.PRIEST);
			c.buryDelay = System.currentTimeMillis();
			c.getPA().addSkillXP(getExp(id) * 2 * Config.PRAYER_EXPERIENCE, 5);
		}
	}

	public void bonesOnHouseAltar(int id) {
		if (c.COLectt > 3) {
			c.getItems().deleteItem(id, c.getItems().getItemSlot(id), 1);
			c.sendMessage("The gods are pleased with your offering.");
			c.sendMessage("You receive more XP from you're lecterns.");
			c.getPA().addSkillXP(
					getExp(id) * 2 * Config.PRAYER_EXPERIENCEINHOUSE, 5);
		} else {
			c.sendMessage("You need to have Lecterns built to do this.");
		}
	}

	private int boneAmount;

	public void bonesOnAltar2(final int id, int amount) {
		if (amount >= 1 && c.usingAltar && c.getItems().playerHasItem(id, 1)) {
			c.getItems().deleteItem(id, c.getItems().getItemSlot(id), 1);
			c.sendMessage("The gods are pleased with your offering.");
			c.getPA().addSkillXP(getExp(id) * 2 * Config.PRAYER_EXPERIENCE, 5);
			c.startAnimation(713);
			if (c.absX == 3091 && c.absY == 3506) {
				c.getPA().stillGfx(624, 3092, 3506, 0, 0);
			} else if (c.absX == 3092 && c.absY == 3505) {
				c.getPA().stillGfx(624, 3092, 3506, 0, 0);
			} else if (c.absX == 3093 && c.absY == 3505) {
				c.getPA().stillGfx(624, 3093, 3506, 0, 0);
			} else if (c.absX == 3094 && c.absY == 3506) {
				c.getPA().stillGfx(624, 3093, 3506, 0, 0);
			}
		}
		amount--;
		boneAmount = amount;
		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer altar) {
				if (boneAmount > 0 && c.usingAltar
						&& c.getItems().playerHasItem(id, 1)) {
					boneAmount--;
					c.getItems()
							.deleteItem(id, c.getItems().getItemSlot(id), 1);
					c.sendMessage("The gods are pleased with your offering.");
					c.getPA().addSkillXP(
							getExp(id) * 2 * Config.PRAYER_EXPERIENCE, 5);
					c.startAnimation(713);
					if (c.absX == 3091 && c.absY == 3506) {
						c.getPA().stillGfx(624, 3092, 3506, 0, 0);
					} else if (c.absX == 3092 && c.absY == 3505) {
						c.getPA().stillGfx(624, 3092, 3506, 0, 0);
					} else if (c.absX == 3093 && c.absY == 3505) {
						c.getPA().stillGfx(624, 3093, 3506, 0, 0);
					} else if (c.absX == 3094 && c.absY == 3506) {
						c.getPA().stillGfx(624, 3093, 3506, 0, 0);
					}
				} else
					altar.stop();
			}

			@Override
			public void stop() {
				c.usingAltar = false;
			}
		}, 6);

	}

	public boolean isBone(int id) {
		for (int j = 0; j < bonesExp.length; j++)
			if (bonesExp[j][0] == id)
				return true;
		return false;
	}

	public int getExp(int id) {
		for (int j = 0; j < bonesExp.length; j++) {
			if (bonesExp[j][0] == id)
				return bonesExp[j][1];
		}
		return 0;
	}
}