package server.model.players;

/**
 * 
 * @author Jordon Barber
 * Displays the amount of NPC's the user has killed.
 *
 */

public class NPCStats {
	
	public static NPCStats instance = new NPCStats();
	
	/**
	 * 
	 * Holds the NPC id's
	 * 
	 */
	
	 public static final int DAGGBOSS1 = 2881;
	 public static final int DAGGBOSS2 = 2882;
	 public static final int DAGGBOSS3 = 2883;
	 public static final int KBD = 50;
	 public static final int TORMDEMON = 8349;
	 public static final int NEX = 13447;
	 public static final int NOMAD = 8528;
	 public static final int JAD = 2745;
	 public static final int BARRELCHEST = 5666;
	 public static final int AVATAR = 8596;
	 public static final int FROSTDRAG = 10770;
	 public static final int BANDOSBOSS = 6260;
	 public static final int ARMABOSS = 6222;
	 public static final int ZAMMYBOSS = 6203;
	 public static final int CHAOSELE = 3200;
	 public static final int GLACOR = 14301;
	 public static final int CORPORAL = 8133;
	 public static final int MITHDRAG = 5363;
	 
	 public static final int CyrexTorva = 507;
	 public static final int BurstTorva = 508;
	 public static final int Torva24K = 503;
	 public static final int BlueTorva = 502;
	 public static final int FlameTorva = 504;
	 public static final int PredatorTorva = 509;
	 public static final int MadMummy = 4477;
	 public static final int ForgottenWarrior = 10530;
	 public static final int KalQueen = 1158;
	 public static final int GiantMole = 3340;
	 public static final int Wrecker = 41;
	 public static final int Protector = 2586;
	 public static final int VesBeast = 40;

	
	/**
	 * 
	 * Displays the interface
	 * 
	 */
	
	public void execute(Client c) {

		c.getPA().sendFrame126("@red@NPC Stats", 8144);
		c.getPA().sendFrame126(
				"Total NPCs Killed - @dre@"+c.totalKilled, 8145);
		c.getPA().sendFrame126(
				"Total Bosses Killed - @dre@"+c.bossesKilled, 8147);
		c.getPA().sendFrame126(
				"Total Slayer Tasks Completed - @dre@"+c.tasksCompleted, 8148);
		c.getPA().sendFrame126(
				"", 8149);
			c.getPA().sendFrame126(
				"Dagganoth Kings Killed - @dre@"+c.daggsKilled, 8150);
			c.getPA().sendFrame126(
					"Tormented Demons Killed - @dre@"+c.demonsKilled, 8151);
			c.getPA().sendFrame126(
					"King Black Dragons Killed - @dre@"+c.kbdKilled, 8152);
			c.getPA().sendFrame126(
					"Nexs Killed - @dre@"+c.nexKilled, 8153);
			c.getPA().sendFrame126(
					"Nomads Killed - @dre@"+c.nomadKilled, 8154);
			c.getPA().sendFrame126(
					"Corporal Beasts Killed - @dre@"+c.corpKilled, 8155);
			c.getPA().sendFrame126(
					"Chaos Elemental Killed - @dre@"+c.chaosKilled, 8156);
			c.getPA().sendFrame126(
					"Barrelchest Killed - @dre@"+c.barrelKilled, 8157);
			c.getPA().sendFrame126(
					"Avatar of Destruction Killed - @dre@"+c.avatarKilled, 8158);
			c.getPA().sendFrame126(
					"Glacors Killed - @dre@"+c.glacorKilled, 8159);
			c.getPA().sendFrame126(
					"Frost Dragons Killed - @dre@"+c.frostsKilled, 8160);
			c.getPA().sendFrame126(
					"Godwar Bosses Killed - @dre@"+c.godwarKilled, 8161);
			c.getPA().sendFrame126(
					"Jads Killed - @dre@"+c.jadKilled, 8162);
			c.getPA().sendFrame126(
					"Mithril Dragons Killed - @dre@"+c.mithKilled, 8163);
			
			

		for (int i = 8164; i < 8175; i++) {
			c.getPA().sendFrame126("", i);
		}
	//	c.getActionSender().sendInterface(8134);
	c.getPA().showInterface(8134);
	//c.showInterface(12140);
	}
}