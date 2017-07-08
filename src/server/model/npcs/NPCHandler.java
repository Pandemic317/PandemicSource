package server.model.npcs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import server.clip.region.Region;
import server.world.map.VirtualWorld;
import  server.model.minigames.Event;
import  server.model.minigames.EventContainer;
import  server.model.minigames.EventManager;
import server.model.minigames.Raid;
import server.model.minigames.TzhaarSpawn;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import server.CycleEvent;
import server.CycleEventContainer;
import server.CycleEventHandler;

import org.apache.poi.hssf.record.formula.functions.N;

import server.Config;
import server.Server;
import server.model.players.*;
import server.model.players.Content.familiar.FamiliarAttractData;
import server.task.Task;
import server.util.Misc;
@SuppressWarnings("all")
public class NPCHandler {


public int summonItemId(int itemId) {
		if(itemId == 1555) return 761;
		if(itemId == 1556) return 762;
		if(itemId == 1557) return 763;
		if(itemId == 1558) return 764;
		if(itemId == 18651) return 666;
		if(itemId == 1559) return 765;
		//if(itemId == 1560) return 6691;
		if(itemId == 1561) return 768;
		if(itemId == 1562) return 769;
		if(itemId == 1563) return 770;
		if(itemId == 1564) return 771;
		if(itemId == 1565) return 772;
		if(itemId == 1566) return 773;
		if(itemId == 7585) return 3507;
		if(itemId == 7584) return 3506;
		if(itemId == 7583) return 3505;
                if(itemId == 12007) return 3589;
if(itemId == 12470) return 6901;
if(itemId == 12472) return 6903;
if(itemId == 12474) return 6905;
if(itemId == 12476) return 6907;
if(itemId == 12513) return 6959;
if(itemId == 12515) return 6961;
if(itemId == 12517) return 6963;
if(itemId == 12519) return 6965;
if(itemId == 12521) return 6967;
if(itemId == 12523) return 6968;
if(itemId == 12482) return 6909;
		return 0;
	}
public String NexTalk = "";
	public static int maxNPCs = 19000;
	public static int zombieNPC = 3622;
	public static int maxListedNPCs = 19000;
	public static int random;
	public static int maxNPCDrops = 35000;
	public static NPC npcs[] = new NPC[maxNPCs];
	public static NPCList NpcList[] = new NPCList[maxListedNPCs];
	public NPCHandler() {
		for(int i = 0; i < maxNPCs; i++) {
			npcs[i] = null;
		}
		for(int i = 0; i < maxListedNPCs; i++) {
			NpcList[i] = null;
		}
		loadNPCList("./Data/CFG/npc.cfg");
		loadAutoSpawn("./Data/CFG/spawn-config.cfg");
	}
	
				public void NexTalk1(Client c) {
				int RandomNexTalk = Misc.random(2);
				if (RandomNexTalk == 0) {
					NexTalk = "Die now, in a prison of ice!";
				} else if (RandomNexTalk == 1) {
					NexTalk = "There is...NO ESCAPE!";
 				c.getPA().sendMp3("NoEscape");	
				} else if (RandomNexTalk == 2) {
					NexTalk = "Infuse me with the power of ice";
				c.getPA().sendMp3("Ice");	
				}
				}
			
			

			public void NexTalk2(Client c) {
				int RandomNexTalk = Misc.random(3);
				if (RandomNexTalk == 0) {
	 				NexTalk = "Fumus, don't fail me!";
                                        c.getPA().sendMp3("Fumus");	
				} else if (RandomNexTalk == 1) {
					NexTalk = "Cruor, don't fail me!";
                                        c.getPA().sendMp3("Cruor");																                        
				} else if (RandomNexTalk == 2) {
					NexTalk = "Glacies, don't fail me!";
				c.getPA().sendMp3("Glacies");
				} else if (RandomNexTalk == 3) {
					NexTalk = "Umbra, don't fail me!";
				c.getPA().sendMp3("Umbra");
				}
				}


			
			public void NexTalk3(Client c) {
				int RandomNexTalk = Misc.random(2);
				if (RandomNexTalk == 0) {
					NexTalk = "Let the virus flow through you!";
					c.getPA().sendMp3("Nex");
				} else if (RandomNexTalk == 1) {
					NexTalk = "Fill my soul with smoke!";
				//c.getPA().sendMp3("NotAddedHere");
				} else if (RandomNexTalk == 2) {
					NexTalk = "Contain this!";
				//c.getPA().sendMp3("NotAddedHere");
				}
				}

			public void NexTalk4(Client c) {
				int RandomNexTalk = Misc.random(2);
				if (RandomNexTalk == 0) {
					NexTalk = "I demand a blood sacrifice!";
				c.getPA().sendMp3("Blood");
				} else if (RandomNexTalk == 1) {
					NexTalk = "Flood my lungs with blood!";
				//c.getPA().sendMp3("NotAddedHere");
				} else if (RandomNexTalk == 2) {
					NexTalk = "AT LAST!";
				c.getPA().sendMp3("AtLast");
				}
				}

			public void NexTalk5(Client c) {
				NexTalk = "AT LAST!";
				c.getPA().sendMp3("Zaros");
				}

		public void newNPC2(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}

		if(slot == -1) return;		// no free slot found

		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}
		
		
		private void killedRaid(int i) {
			final Client c2 = (Client) PlayerHandler.players[npcs[i].spawnedBy];
			c2.RaidKills++;
			if (c2.RaidKills == c2.RaidKillsLeft) {
				c2.raidNpcID++;
				Server.getTaskScheduler().addEvent(new Task(10, false) {
					public void execute() {
						if (c2 != null) {
							Server.raid.spawnWave(c2);
							c2.sendMessage("Wave: " +c2.raidNpcID);
						}
						if(c2.RaidKills == c2.RaidKillsLeft) {
						Server.raid.endGame(c2);
						}
						this.stop();
					}
				});
			}
		}
	/***/
	/***** PEST CONTROL -Redone by Haaack3r
	*****/
	   public static NPC forType(int type) {
      NPC[] var4 = npcs;
      int var3 = npcs.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         NPC n = var4[var2];
         if(n != null && n.npcType == type) {
            return n;
         }
      }

      return null;
   }
	   private final int[][] COORDS = { { 2990, 9636 }, { 2987, 9640 },
				{ 2984, 9636 }, { 2987, 9632 }};

	   public void spawnIhlakhizan(Client c, int[][] coords) { //10156
			for (int j = 1; j < coords.length; j++)
				Server.npcHandler.spawnNpc55(c, 10148, coords[j][0], coords[j][1],
						4, 0, 4500, 7, 300, 50, true, false);
			c.getPA().movePlayer(2987, 9636, 4);
			c.sendMessage("The Ihlakhizan teleports you to the centre!");

		}
	   private final int[][] COORDS1 = { { 3167, 9762 }, { 3173, 9758 },
				{ 3167, 9755 }, { 3164, 9758 }};

	   public void spawnMiniJad(Client c, int[][] coords1) { //10156
			for (int j = 1; j < coords1.length; j++)
				Server.npcHandler.spawnNpc55(c, 4020, c.absX -1, c.absY,
						c.heightLevel, 0, 4500, 30, 300, 50, true, false);
		}
	
		public void spawnNpc55(Client c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer, boolean headIcon) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if(slot == -1) {
			//Misc.println("No Free Slot");
			return;		// no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = 1200;
		newNPC.MaxHP = 1200;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		if(headIcon) 
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if(attackPlayer) {
			newNPC.underAttack = true;
			if(c != null) {
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] != newNPC.npcType) {
					if(newNPC.npcType == 2025 || newNPC.npcType == 2026 || newNPC.npcType == 2027 || newNPC.npcType == 2028 || newNPC.npcType == 2029 || newNPC.npcType == 2030) {
						newNPC.forceChat("You dare disturb my rest!");
					}
				}
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] == newNPC.npcType) {
					newNPC.forceChat("You dare steal from us!");
				}
				
				newNPC.killerId = c.playerId;
			}
		}
		npcs[slot] = newNPC;
	}
	
		public void spawnNpc56(Client c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer, boolean headIcon) {
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				c.barbLeader = slot = i;
				break;
			}
		}
		if(slot == -1) {
			return;
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit * 10;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		if(headIcon) 
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if(attackPlayer) {
			newNPC.underAttack = true;
			if(c != null) {
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] != newNPC.npcType) {
					if(newNPC.npcType == 2025 || newNPC.npcType == 2026 || newNPC.npcType == 2027 || newNPC.npcType == 2028 || newNPC.npcType == 2029 || newNPC.npcType == 2030) {
						newNPC.forceChat("You dare disturb my rest!");
					}
				}
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] == newNPC.npcType) {
					newNPC.forceChat("You dare steal from us!");
				}
				
				newNPC.killerId = c.playerId;
			}
		}
		npcs[slot] = newNPC;
	}
	
	
	/****/
	/****** Shitty Summoning :D
	/****/
	public void Summon(Client c, int npcType, int x, int y, int heightLevel,
			int WalkingType, int HP, int maxHit, boolean attackPlayer,
			int attack, int defence) {

		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if (slot == -1) {
			// Misc.println("No Free Slot");
			return; // no free slot found
		}
			npcs[slot] = new NPC(slot, npcType);
			npcs[slot].absX = x;
			npcs[slot].absY = y;
			npcs[slot].makeX = x;
			npcs[slot].makeY = y;
			npcs[slot].heightLevel = heightLevel;
			npcs[slot].walkingType = WalkingType;
			npcs[slot].HP = HP;
			npcs[slot].MaxHP = HP;
			npcs[slot].maxHit = maxHit;
			npcs[slot].attack = attack;
			npcs[slot].defence = defence;

			npcs[slot].spawnedBy = c.getId();

			npcs[slot].followPlayer = c.getId();
			// followPlayer(npcType, c.getId());
			npcs[slot].summon = true;
			npcs[slot].npcslot = slot;
			npcs[slot].gfx100(1315);
		c.hasFollower = npcType;
		c.summon = true;
		c.summoningnpcid = slot;/*
		if (c.hasFollower == 6870) {
		c.healingEventW();
		} else {
		if (c.hasFollower == 6814 || c.hasFollower == 6823) {
		c.healingEvent();
		}
		}*/

	}


	public void appendJailKc(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			int[] Jail = {
				132
			};
			for (int j : Jail) {
				if (npcs[i].npcType == j) {
					c.monkeyk0ed++;
					c.sendMessage("You now have "+c.monkeyk0ed+" Monkey kills!");
					} else {
						c.sendMessage("Woah man slow down.. you already have 20 monkey kills..");
					break;
				}
			}
		}	
	}
	
	public void multiAttackGfx(int i, int gfx) {
		if (npcs[i].projectileId < 0)
			return;
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (c.heightLevel != npcs[i].heightLevel)
					continue;
				if (Server.playerHandler.players[j].goodDistance(c.absX, c.absY, npcs[i].absX, npcs[i].absY, 15)) {
					int nX = Server.npcHandler.npcs[i].getX() + offset(i);
					int nY = Server.npcHandler.npcs[i].getY() + offset(i);
					int pX = c.getX();
					int pY = c.getY();
					int offX = (nY - pY)* -1;
					int offY = (nX - pX)* -1;
					c.getPA().createPlayersProjectile(nX, nY, offX, offY, 50, getProjectileSpeed(i), npcs[i].projectileId, 43, 31, -c.getId() - 1, 65);					
				}
			}		
		}
	}
	
	public boolean switchesAttackers(int i) {
		switch(npcs[i].npcType) {
			case 6261:
			case 2589:
			case 2588:
			case 2587:
			case 2586:
			case 6263:
			case 6265:
			case 6223:
			case 6225:
			case 6227:
			case 6248:
			case 6250:
			case 8133:
			case 4004:
			case 40:
			case 10128:
			case 6252:
			case 8596:
			case 2892:
			case 2894:
			case 50:
			case 4000:
			case 6206:
			case 6208:
			case 6204:
			case 9947:
			case 3847:
			case 2636:
			return true;
		
		}
	
		return false;
	}
		public boolean ArmadylKC(int i) {
		switch(npcs[i].npcType) {
			case 6222:
			case 4003:
			case 6223:
			case 6225:
			case 6230:
			case 6239: // Aviansie
			case 6227:
			case 6232:
			case 6229:
			case 6233:
			case 6231:
			return true;
		
		}
	
		return false;
	}
		public boolean BandosKC(int i) {
		switch(npcs[i].npcType) {
			case 6260:
			case 4001:
			case 6261:
			case 6263:
			case 6265:
			case 6277:
			case 6269:
			case 6270:
			case 3247:
			case 6276:
			case 6272:
			case 6274:
			case 6278:
			return true;
		
		}
	
		return false;
	}
		public boolean ZammyKC(int i) {
		switch(npcs[i].npcType) {
		case 8350:
			case 6203:
			case 3065:
			case 6204:
			case 6206:
			case 6208:
			case 10039:
			case 5247:
			case 6219:
			case 1904:
			case 1977:
			case 6218:
			case 3067:
			case 6212:
			case 205:
			case 3248:
			case 6220:
			case 6221:
			case 6272:
			case 10903:
			case 1157:
			case 1154:
			case 1153:
			case 10093:
			case 3665:
			case 1622:
			case 1633:
			return true;
		
		}
	
		return false;
	}
		public boolean SaraKC(int i) {
		switch(npcs[i].npcType) {
			case 6247:
			case 4006:
			case 6248:
			case 6250:
			case 6254:
			case 6252:
			case 6257:
			case 6255:
			case 6256:
			case 6258:
			return true;
		
		}
	
		return false;
	}
		public int getNpcDeleteTime(int i) {
		switch(npcs[i].npcType) {
			case 1265:
			case 90:
			case 1648:
			case 1341:
			case 1851:
			case 1857:
			case 1854:
		        return 2; 
			case 82:
                        return 3;			
			case 103:
			
                        return 0;
                        case 117:
                        return 6; 			
			default:
			return 4;
		}
	}
	
	public void multiAttackDamage(int i) {
		int max = getMaxHit(i);
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (c.isDead || c.heightLevel != npcs[i].heightLevel)
					continue;
				if (Server.playerHandler.players[j].goodDistance(c.absX, c.absY, npcs[i].absX, npcs[i].absY, 15)) {
					if (npcs[i].attackType == 2) {
						if (!c.prayerActive[16] && !c.curseActive[7]) {
							if (Misc.random(500) + 200 > Misc.random(c.getCombat().mageDef())) {
								int dam = Misc.random(max);
								c.dealDamage(dam);
								c.handleHitMask(dam, c.playerId);							
							} else {
								c.dealDamage(0);
								c.handleHitMask(0, c.playerId);							
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0, c.playerId);
						}
					} else if (npcs[i].attackType == 1) {
						if (!c.prayerActive[17] && !c.curseActive[8]) {
							int dam = Misc.random(max);
							if (Misc.random(500) + 200 > Misc.random(c.getCombat().calculateRangeDefence())) {
								c.dealDamage(dam);
								c.handleHitMask(dam, c.playerId);							
							} else {
								c.dealDamage(0);
								c.handleHitMask(0, c.playerId);
							}
						} else {
							c.dealDamage(0);
							c.handleHitMask(0, c.playerId);							
						}
					}
					if (npcs[i].endGfx > 0) {
						c.gfx0(npcs[i].endGfx);					
					}
				}
				c.getPA().refreshSkill(3);
			}		
		}
	}
	
	public int getClosePlayer(int i) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				if (j == npcs[i].spawnedBy)
					return j;
				if (goodDistance(Server.playerHandler.players[j].absX, Server.playerHandler.players[j].absY, npcs[i].absX, npcs[i].absY, 2 + distanceRequired(i) + followDistance(i)) || isFightCaveNpc(i) || isDominionNpc(i) || isBarrowsNpc(i) || isRFDNpc(i)) {
					if ((Server.playerHandler.players[j].underAttackBy <= 0 && Server.playerHandler.players[j].underAttackBy2 <= 0) || Server.playerHandler.players[j].inMulti())
						if (Server.playerHandler.players[j].heightLevel == npcs[i].heightLevel)
							return j;
				}
			}	
		}
		return 0;
	}
	
	public int getCloseRandomPlayer(int i) {
		ArrayList<Integer> players = new ArrayList<Integer>();
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				if (goodDistance(Server.playerHandler.players[j].absX, Server.playerHandler.players[j].absY, npcs[i].absX, npcs[i].absY, 2 + distanceRequired(i) + followDistance(i)) || isFightCaveNpc(i) || isDominionNpc(i) || isBarrowsNpc(i) || isRFDNpc(i)) {
					if ((Server.playerHandler.players[j].underAttackBy <= 0 && Server.playerHandler.players[j].underAttackBy2 <= 0) || Server.playerHandler.players[j].inMulti())
						if (Server.playerHandler.players[j].heightLevel == npcs[i].heightLevel)
							players.add(j);
				}
			}	
		}
		if (players.size() > 0)
			return players.get(Misc.random(players.size() -1));
		else
			return 0;
	}
	
	public int npcSize(int i) {
		switch (npcs[i].npcType) {
		case 2883:
		case 2882:
		case 2881:
		case 4007:
		case 4008:
		case 4009:
		case 3493:
			return 3;
		case 3494:
			return 5;
		}
		return 0;
	}
	
	public boolean isAggressive(int i) {
		if (Raid.getNpcState(npcs[i].npcType) == Raid.PLAYING ) {
			return true;
		}
		switch (npcs[i].npcType) {
		case 2026:
		case 3200:
		case 10156:
		case 10148:
		case 103:
		case 1385:
		case 1053:
		case 11111:
		case 7479:
		case 518:
		case 517:
		case 8988:
		case 3069:
		case 237:
		case 5906:
		case 1958:
		case 9752:
		case 10040:
		case 10400:
		case 3067:
		case 3622:
		case 8528:
		case 3663:
		case 751:
		case 4477:
		case 2636:
			case 6260:
			case 53:
			      case 3732:
      case 3734:
      case 3736:
      case 3738:
      case 3740:
			case 742:
			case 941:
			case 4677:
			case 6261:
			case 6263:
			case 6265:
			case 6222:
			case 6223:
			case 6225:
			case 6227:
                        case 6247:
			case 6248:
			case 6250:
			case 6252:
			case 6713:
                        case 1156:
			case 795:

			case 8133:
			case 40:
			case 10128:
			case 3101:
			case 3102:
			case 5666:
			case 3103:
			
			case 2892:
			case 2894:
			case 2881:
			case 50:
			case 2882:
			case 2883:

			case 6203:
			case 6206:
			case 6208:
			case 4972:
			case 6204:
			case 9947:
			case 3847:
			return true;		
		}
		if (npcs[i].inWild() && npcs[i].MaxHP > 0 && npcs[i].npcType != 941)
			return false;
			
		if (isBarbNpc(i))
			return true;
                if (isDominionNpc(i))
                        return true;
                if (isBarrowsNpc(i))
                	return true;
		if (isFightCaveNpc(i))
			return true;
                if (isHack3rsFuckingCAT(i))
			return false;
		if (isRFDNpc(i))
			return true;
		return false;
	}
	
		public boolean isBarbNpc(int i) {
		return Server.barbDefence.killableNpcs(i);
	}
	
	public boolean isFightCaveNpc(int i) {
		switch (npcs[i].npcType) {
			case 2627:
			case 2630:
			case 2631:
			case 2741:
			case 2743: 
			case 2745:
			return true;		
		}
		return false;
	}

                     public boolean isHack3rsFuckingCAT(int i) {
		switch (npcs[i].npcType) {
			case 3505:
			case 6968:
			case 6959:
			case 6961:
			case 6963: 
			case 6965:
			case 6967:
			case 6901:
			case 6903:
			case 6905:
			case 6907:
			case 3506:
			case 766:
			case 765:
			case 764:
			case 763:
			case 762:
			case 761:
			case 768:
			case 769:
			case 770:
			case 771:
			case 772:
			case 773:
			case 3589:
			case 6909:
                        case 666:
			return true;		
		}
		return false;
	}

        public boolean isDominionNpc(int i) {
		switch (npcs[i].npcType) {
			case 677:
			case 667:
			case 934:
			case 3064:
			case 1472:
			case 2591:
			case 2598:
			case 2604:
			case 2610:
			case 2746:
			case 7770:
			case 2741:
			case 2743:
			case 4020:
			//case 2745:
			return true;
		}
		return false;
        }
        public boolean isBarrowsNpc(int i) {
		switch (npcs[i].npcType) {
        case 4022:
        case 4023:
        case 4024:
        case 4025:
        case 4026:
        case 2025:
        case 2027:
        case 2028:
        case 2029:
        case 2030:
			return true;
		}
		return false;
        }

	public boolean isRFDNpc(int i) {
		switch (npcs[i].npcType) {
			case 3493:
			case 3494:
			case 3495:
			case 3496:
			case 3491:
			return true;		
		}
		return false;
	}

	public boolean isRFDNpc2(int i) {
		switch (npcs[i].npcType) {
			case 3495:
			return true;		
		}
		return false;
	}
	
	/**
	* Summon npc, barrows, etc
	**/
	


public static void destroyDungNpcs(Client c) {
c.juststarted = true;
		for (NPC n : NPCHandler.npcs) {
			if (n != null)
				if (n.spawnedBy == c.playerId)
					n.isDead = true;
		}
	}
	
		public static void spawnDungNpcs(Client c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer, boolean headIcon) {
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
			break;
			}
		}
		if(slot == -1) {
			return;
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		c.juststarted = false;
		if(headIcon) 
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if(attackPlayer) {
			newNPC.underAttack = true;
		}
		npcs[slot] = newNPC;
	}

	public void spawnNpc(Client c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer, boolean headIcon) {
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if(slot == -1) {
			return;
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		if(headIcon) 
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if(attackPlayer) {
			newNPC.underAttack = true;
			if(c != null) {
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] != newNPC.npcType) {
					if(newNPC.npcType == 2025 || newNPC.npcType == 2026 || newNPC.npcType == 2027 || newNPC.npcType == 2028 || newNPC.npcType == 2029 || newNPC.npcType == 2030) {
						newNPC.forceChat("You dare disturb my rest!");
					}
				}
				if (newNPC.npcType >= 4278 && newNPC.npcType <= 4284) {
					newNPC.forceAnim(4410);
					c.getPA().walkTo(0, 3);
					newNPC.forceChat("I'M ALIVE!");
				}
				if(server.model.minigames.Barrows.COFFIN_AND_BROTHERS[c.randomCoffin][1] == newNPC.npcType) {
					newNPC.forceChat("You dare steal from us!");
				}
				
				newNPC.killerId = c.playerId;
			}
		}
		npcs[slot] = newNPC;
	}
	
                      public void spawnNpc3(Client c, int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean attackPlayer, boolean headIcon, boolean summonFollow) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if(slot == -1) {
			//Misc.println("No Free Slot");
			return;		// no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.spawnedBy = c.getId();
		newNPC.underAttack = true;
		newNPC.facePlayer(c.playerId);
		if(headIcon) 
			c.getPA().drawHeadicon(1, slot, 0, 0);
		if (summonFollow) {
			newNPC.summoner = true;
			newNPC.summonedBy = c.playerId;
			c.summonId = npcType;
			c.hasNpc = true;
		}
		if(attackPlayer) {
			newNPC.underAttack = true;
			if(c != null) {			
				newNPC.killerId = c.playerId;
			}
		}
		npcs[slot] = newNPC;
	}

	public void spawnNpc2(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence) {
		// first, search for a free slot
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if(slot == -1) {
			//Misc.println("No Free Slot");
			return;		// no free slot found
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}
	
		public static void spawnNewNPC(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence, boolean needSpawn) {
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
			break;
			}
		}
		if(slot == -1) {
			return;
		}
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		newNPC.NeedsRespawn = needSpawn;
		npcs[slot] = newNPC;
		if (newNPC.npcType == 1160) {
			newNPC.startAnim(1181);//spawn anim
		}
	}
	
	
	
	/**
	* Emotes
	**/
	public static int getAttackEmote(int i) {
		switch(Server.npcHandler.npcs[i].npcType) {
		
		case 97:
		case 98:
		case 96:
		case 95:
		case 94:
		return 64;
		case 1172:
		case 1173:
		return 1199;
		case 2591:
			return 9294;
		case 2598:
			return 9281;
		case 2604:
			return 9281;
		case 2741:
			return 9250;
		case 2743:
			return 9267;
		case 2746:
			return 9255;
		
		
		case 4972:
		case 4971:
			if (npcs[i].attackType == 2)
			return 5024;	//ANIMATION
			else if (npcs[i].attackType == 1)
			return 5024;	//ANIMATION
			else if (npcs[i].attackType == 0)
			return 5031;	//ANIMATION
		case 1813:
		case 1814:
		return 382;
		case 3070:
		case 3069:
			return 1589;
		case 1812:
			if (npcs[i].attackType == 2)
			return 811;	//ANIMATION
			else if (npcs[i].attackType == 1)
			return 811;	//ANIMATION
			else if (npcs[i].attackType == 0)
			return 1818;	//ANIMATION
			
		case 8988:
			if (npcs[i].attackType == 2)
			return 12517;	//ANIMATION
			else if (npcs[i].attackType == 1)
			return 12517;	//ANIMATION
			else if (npcs[i].attackType == 0)
			return 12515;	//ANIMATION
		case 7480:
		case 7479:
			if (npcs[i].attackType == 2)
			return 11628;	//ANIMATION
			else if (npcs[i].attackType == 1)
			return 11628;	//ANIMATION
			else if (npcs[i].attackType == 0)
			return 11628;	//ANIMATION
			else if (npcs[i].attackType == 10)
			return 11628;	//ANIMATION

case 502:
case 503:
case 504:
case 507:
case 508:
case 509:
case 291:
case 11115:
case 11118:
	return 1658;
case 2587:
	return 3678;

		case 9752:	//THE NPC ID
			if (npcs[i].attackType == 2)
			return 13425;	//ANIMATION
			else if (npcs[i].attackType == 1)
			return 13430;	//ANIMATION
			else if (npcs[i].attackType == 0)
			return 13430;	//ANIMATION

		case 1958:
			return 5549;

		case 10156:	//THE NPC ID
		case 10148:
		case 10149:
			return 13019;	//ANIMATION
		
		case 8995: //Vorago
		case 8996: //vorago
		case 8997:
		case 8998:
		case 8999:
			if (npcs[i].attackType == 2)
			return 12204;	//ANIMATION
			else if (npcs[i].attackType == 1)
			return 12204;	//ANIMATION
			else if (npcs[i].attackType == 0)
			return 12196;	//ANIMATION

		case 9780: //vorago
			if (npcs[i].attackType == 2)
			return 13703;	//ANIMATION
			else if (npcs[i].attackType == 1)
			return 13703;	//ANIMATION
			else if (npcs[i].attackType == 0)
			return 13703;	//ANIMATION

		case 517:
		case 518:
			if (npcs[i].attackType == 2)
			return 2661;	//ANIMATION
			else if (npcs[i].attackType == 1)
			return 2661;	//ANIMATION
			else if (npcs[i].attackType == 0)
			return 2661;	//ANIMATION

		case 5906:
		return 6310;
		case 10040:
		return 10816;
		case 2779:
		return 426;
		case 3067:
		return 7041; // 2h emote
		case 5529:
		return 5782;
					case 3622://Zombies!!
			return 5578;
      case 3732:
      case 3734:
      case 3736:
      case 3738:
      case 3740:
         return 3901;
case 751: // the spirit
return 6728;
		case 5213: case 5214: case 5215: case 5216: case 5217: case 5218: case 5219: // Penance fighter
				return 5097;
			
	case 5229: case 5230: case 5231: case 5232: case 5233: case 5234: case 5235: case 5236: case 5237: // Penance ranger
				return 5396;
				//end of barb assault
		
	case 2589:
	case 2588:
		return 1979;
		case 8528:
		return 12696;
		case 6260:
		case 4001:
		return 7060;
		case 2586:
		case 511:
		case 512:
		case 513:
		case 514:
		case 515:
		case 516:
		case 11114:
		case 11113:
		case 11119:
			return 2661;
		case 560:
			return 2661;
		case 8597:
return 11202;// avatar of desc
					case 10530: // forgotten warrior
					return 2661;
		case 5666: // barrelschest
			if (npcs[i].attackType == 0)
				return 5894;
			else
				return 5895;
			case 5248:
return 6354+ Misc.random(1);
			case 2892:
			case 2894:
			return 2868;
			
			case 4477:
//startAnimation(5556);
			//	c.gfx0(369);
				return 5556;
			case 9463:
			return 12791;
			
			case 9467:
			return 12791;
			
			case 9465:
			return 12791;
case 40:
			case 10128:
			case 8133: // Corporeal beast
			case 4004:
			if (npcs[i].attackType == 2)
			return 10058;
			else if (npcs[i].attackType == 1)
			return 10058;
			else if (npcs[i].attackType == 0)
			return 10057;
			case 3101: // Melee
			return 10058;
			case 3102: // Range
			case 3103: // Mage
			return 10057;
			case 4278:
			case 4279:
			case 4280:
			case 4281:
			case 4282:
			case 4284:
			return 451;
			case 93:
			return 5499;
			case 4283:
			return 7048;
//dung npcs
case 4382: //ankou
return 422;
case 5247: // PENANCE QUEEN
return 5411;
//GODWARS
			case 3247: // Hobgoblin
			return 6184;

			case 6270: // Cyclops
			case 6269: // Ice cyclops
			case 4291: // Cyclops
			case 4292: // Ice cyclops
			return 4652;

			case 6219: // Spiritual Warrior
			case 6255: // Spiritual Warrior
			return 451;

			case 6229: // Spirtual Warrior arma
					return 6954;

			case 6218: // Gorak
					return 4300;

			case 6212: // Werewolf
					return 6536;

			case 6220: // Spirtual Ranger
			case 6256: // Spirtual Ranger
					return 426;

			case 6257: // Spirtual Mage
			case 6221: // Spirtual Mage
					return 811;

			case 6276: // Spirtual Ranger
			case 6278: // Spirtual Mage
			case 6272: // Ork
			case 6274: // Ork
			case 6277: // Spirtual Warrior bandos
					return 4320;

			case 6230: // Spirtual Ranger
			case 6233: // Aviansie
			case 6239: // Aviansie
			case 6232: // Aviansie
			case 41:
					return 6953;

			case 6254: // Saradomin Priest
					return 440;
			case 6258: // Saradomin Knight
					return 7048;
			case 6231: // Spirtual Mage
					return 6952;

			case 3248: // HellHound
					return 6579;
//ENDS

			case 6204:
			return 64;
			case 6208:
			return 6947;
			case 6206:
			return 6945;
			case 3494:
			return 1750;
			case 3491:
			return 1979;
			case 3496:
				if (npcs[i].attackType == 0)
					return 3508;
				else
					return 3507;	
case 6203:
				if (npcs[i].attackType == 0)
					return 6945;
				else
					return 6947; // end
			case 2627:
			return 2621;
			case 2630:
			return 2625;
			case 2631:
			return 2633;
			case 2607:
			return 2611;
			case 3493:
				if (npcs[i].attackType == 0)
					return 3501;
				else
					return 3502; // end
			case 50:
			case 4000:
			case 53:
			case 54:
			case 742:
			case 55:
				case 941:
				case 4677:
			case 1590:
			case 1591:
			case 1592:
				if (npcs[i].attackType == 0)
					return 80;
				else
					return 81;
			//bandos gwd
			case 6261:
			case 6263:
			case 6265:
			return 6154;
			//end of gwd
			//arma gwd
			
			case 6222:
			case 4003:
					return 6976;
			case 6225:
			return 6953;
			case 6223:
			return 6952;
			case 6227:
			return 6954;
			//end of arma gwd
			//sara gwd
			case 6247:
			case 4006:
			return 6964;
			case 6248:
			return 6376;
			case 6250:
			return 7018;
			case 6252:
			return 7009;
			//end of sara gwd
			case 13: //wizards
			return 711;
			case 103:
			return 5540;
			case 655:
			case 104:
			return 5535;
			
			case 1624:
			return 1557;
			
			case 1648:
			return 1590;
			
			case 2783: //dark beast
			return 2731;
			
			case 1615: //abby demon
			return 1537;
			
			case 1613: //nech
			return 1528;
			
			case 1610: case 1611: //garg
			return 1519;
			
			case 1616: //basilisk
			return 1546;
			
			case 90: //skele
			return 260;
			
			case 124: //earth warrior
			return 390;
			
			case 803: //monk
			return 422;
			
			case 52: //baby drag
			return 25;			

			case 58: //Shadow Spider
            case 59: //Giant Spider
            case 60: //Giant Spider
            case 61: //Spider
            case 62: //Jungle Spider
            case 63: //Deadly Red Spider
            case 64: //Ice Spider
            case 134:
			return 5327;	
			
			case 105: //Bear
            case 106:  //Bear
			return 4925;
			
			case 412:
			case 78:
			return 4915;
			
			case 2033: //rat
			return 4933;	
			
			case 2031: // bloodworm
			return 2070;
			
			case 101: // goblin
			case 3663:
			return 6188;	
			
			case 81: // cow
			return 5849;
			
			case 21: // hero
			return 451;	
			

			
			case 9: // guard
			case 32: // guard
			case 20: // paladin
			return 451;	
			
			case 1338: // dagannoth
			case 1340:
			case 1342:
			return 1341;
		
			case 19: // white knight
			return 406;
			
			case 110:
			case 111: // ice giant
			case 112:
			case 117:
			return 4672;
			
			case 2452:
			return 1312;
			
			case 2889:
			return 2859;
			
			case 118:
			case 119:
			return 99;
			
			case 82://Lesser Demon
            case 83://Greater Demon
            case 84://Black Demon
            case 1472://jungle demon
			case 10039:
			return 64;
			
			case 1267:
			case 1265:
			return 1312;
			
			case 125: // ice warrior
			case 178:
			return 451;
			
            case 1154: //Kalphite Soldier
			return 6225;
            case 1156: //Kalphite worker
					case 1160:// kalphite queen flying one
					return 6236;
            case 1157: //Kalphite guardian
			return 6226;
			case 1153: //Kalphite Worker
			case 1158: // kalphite queen no fly
			//case 1160:// kalphite queen flying one
			return 6223;
			
			case 123:
			case 122:
			return 164;
			
			case 2028: // karil
			return 2075;
					
			case 4022:
			case 2025: // ahrim
			return 729;
			
			case 2026: // dharok
			return 2067;
			
			case 2027: // guthan
			case 4023:
			return 2080;
			
			case 2029: // torag
			case 4025:
			return 0x814;
			
			case 2030: // verac
			case 4026:
			return 2062;
			
			case 2881: //supreme
			case 4007:
			case 4008:
			case 4009:
			return 2855;
			
			case 2882: //prime
			return 2854;
			
			case 2883: //rex
			return 2851;
			
			case 3200:
			return 3146;



case 7334:
return 8172;
case 7336:
return 7871;
case 5228:
return 5228;

case 7340:
return 7879;

case 7342:
return 7879;

case 7344:
return 8190;

case 7346:
return 8048;

case 7348:
return 5989;


case 7350:
return 7693;


case 6795:
return 1010;



case 10775:
return 13151;

case 2037:
				return 5485;
				
case 6797:
return 8104;
	
case 6799:
return 8069;

case 6801:
return 7853;

case 6803:
return 8159;

case 6805:
return 7786;

case 6807:
return 8148;

case 6810:
return 7970;

case 6812:
return 7935;

case 6814:
return 7741;

case 6816:
return 8288;

case 6819:
return 7667;

case 6821:
return 7680;


case 6823:
return 6376;


case 6826:
return 5387;


case 6828:
return 8208;


case 6830:
return 8292;
case 6832:
return 7795;
case 6834:
return 8248;
case 6836:
return 8275;
case 6838:
return 6254;
case 6856:
return 4921;
case 6858:
return 5327;

case 6860:
case 6862:
case 6864:
return 7656;

case 6868:
return 7896;

case 6870:
return 8303;


case 6872:
return 7769;

case 6874:
return 5782;

case 6890:
return 7260;

case 7330:
return 8223;

case 7332:
return 8032;

case 7338:
return 5228;

case 7352:
return 8234;

case 7354:
return 7755;

case 7355:
return 7834;

case 7358:
return 7844;

case 7359:
return 8183;

case 7362:
return 8257;

case 7364:
case 7366:
return 5228;

case 7368:
case 7369:

return 8130;

case 7371:
return 8093;

case 7374: 
return 7994;

case 7376:
return 7946;

			case 8349:
			if (npcs[i].attackType == 2)
			return 10917;
			else if (npcs[i].attackType == 1)
			return 10919;
			else if (npcs[i].attackType == 0)
			return 10922;
			
						case 8350:
			if (npcs[i].attackType == 2)
			return 10917;
			else if (npcs[i].attackType == 1)
			return 10919;
			else if (npcs[i].attackType == 0)
			return 10922;
			
			case 2745:
			case 4020:
			if (npcs[i].attackType == 2)
			return 9300;
			else if (npcs[i].attackType == 1)
			return 9276;
			else if (npcs[i].attackType == 0)
			return 9277;
			
			case 2636://
				if (npcs[i].attackType == 5) //Thelife
					return 6948;
				else if (npcs[i].attackType == 4)
					return 6326;
				else if (npcs[i].attackType == 3)
					return 6354;
				else if (npcs[i].attackType == 2)
					return 6355;
				else if (npcs[i].attackType == 1)
					return 6326;
				else if (npcs[i].attackType == 0)
					return 6326;
			
			
			default:
			return 0x326;		
		}
	}	

	
	public int getDeadEmote(int i) {
		switch(npcs[i].npcType) {	
		case 10040:
		return 10815;
		case 2264:
		return 3310;
		case 1172:
		case 1173:
		return 1201;
		case 4971:
			return 5033;
		case 4972:
			return 5037;
		case 5906:
		return 6315;
		case 3070:
		case 3069:
		return 1591;
		case 9752:
		return 13420;
		case 8995:
		case 8997:
		case 8998:
		case 8999:
		case 8996:
			return 12206;
		case 8988:
		return 12518;
		case 10156:
		case 10149:
		case 10148:
			return 13017;
							case 3622://Zombies!!
			return 5575;
				case 5529:
		return 5784;
		      case 3732:
      case 3734:
      case 3736:
      case 3738:
      case 3740:
         return 3903;
		 
		case 5229: case 5230: case 5231: case 5232: case 5233: case 5234: case 5235: case 5236: case 5237: // Penance ranger
				return 5397;
				
			
case 5213: case 5214: case 5215: case 5216: case 5217: case 5218: case 5219: // Penance fighter
				
				return 5098;
		
		
		//end of barbarien assault
		
		
		
		
		
		
case 2636:			
return 6951;
				case 10530:
		return 0; // forgotten warrior
		case 8596:
		case 8597:
	return 11199;
	case 3068:
	return 2987;
		case 6142:
		case 6143:
		case 6144:
		case 6145:
		return 6;
		// START OF Dungeoneering
		case 1622:
		return 1597;
		case 1631:
		case 1632:
		return 1568;
		case 1260:
		return 1563;
		case 4382: // ankou
		return 836;
		case 5247: // penance queen
		return 5412;
		//END OF DUNGEONEERING
			case 8528:
			return 12694;
			case 10775:
			return 13153;
			case 9463:
			return 12793;
	case 5248:
			return 6951;
			case 9467:
			return 12793;
			case 9465:
			return 12793;
			case 8349:
			case 8350:
			return 10924;
			case 111: // ice giant
			case 112:
			case 117:
			return 4668;
			case 93:
			return 5503;
		case 5666:
			return 5898;
			case 110:
			return 4673;
			case 8133: // Corporeal beast
			case 4004:
			case 40:
			case 10128:

			case 3101: // Melee
			case 3102: // Range
			case 3103: // Mage
			return 10059;
//GODWARS

			case 3247: // Hobgoblin
			return 6182;

			case 6270: // Cyclops
			case 6269: // Ice cyclops
			case 4291: // Cyclops
			case 4292: // Ice cyclops
			return 4653;

			case 6218: // Gorak
					return 4302;

			case 6212: // Werewolf
					return 6537;

			case 6276: // Spirtual Ranger
			case 6278: // Spirtual Mage
			case 6272: // Ork
			case 6274: // Ork
			case 6277: // Spirtual Warrior bandos
					return 4321;

			case 6230: // Spirtual Ranger
			case 6233: // Aviansie
			case 6239: // Aviansie
			case 6232: // Aviansie
			case 6231: // Spirtual Mage
			case 6229: // Spirtual Warrior arma
case 41:
			case 6223: // Aviansie
			case 6225: // Spirtual Mage
			case 6227: // Spirtual Warrior arma
					return 6956;

			case 3248: // HellHound
					return 6576;

//ENDS
			//sara gwd
			case 6247:
			case 4006:
			return 6965;
			case 1265:
			return 1314;
			case 103:
			return 5542;
			case 655:
			case 104:
			return 5534;
			case 6248:
			return 6377;
			case 6250:
			return 7016;
			case 3491:
			return 3357;
			case 6222:
			case 4003:
			return 6975;
case 6203:
case 6204:
case 6206:
case 6208:
return 6946;
			case 3493:
			return 3503;
			case 2565:
			return 7011;
			case 3494:
			return 1752;
			case 3496:
			return 3509;
			case 6252:
			return 7011;
			//bandos gwd
			case 6261:
			case 6263:
			case 6265:
			return 6156;
			case 6260:
			case 4001:
			return 7062;
			case 2550:
			return 7062;
			case 2892:
			case 2894:
			return 2865;
			case 1612: //banshee
			return 1524;
			case 2559:
			case 2560:
			case 2561:
			return 6956;
			case 2607:
			return 2607;
			case 2627:
			return 2620;
			case 2630:
			return 2627;
			case 2631:
			return 2630;
			case 2738:
			return 2627;
			case 2741:
			return 2638;
			case 2743:
			return 2646;
			case 2745:
			case 4020:
			return 9279;
			
			case 3777:
			case 3778:
			case 3779:
			case 3780:
			return -1;
			
			case 3200:
			return 3147;
			
			case 2035: //spider
			return 5329;
			
			case 2033: //rat
			return 4935;
			
			case 2031: // bloodvel
			return 2073;
			
			case 101: //goblin	
			case 3663:
			return 6190;
			
			case 81: // cow
			return 5851;
			
			case 1338: // dagannoth
			case 1340:
			case 1342:
			return 1342;
			
			case 2881:
			case 4007:
			case 4008:
			case 4009:
			case 2882:
			case 2883:
			return 2856;
			
			case 125: // ice warrior
			return 843;
			
			case 751://Zombies!!
			return 6727;
			
			case 1626:
            case 1627:
            case 1628:
            case 1629:
				case 1630:
            return 1597;
			
			case 1616: //basilisk
            return 1548;
			
			case 1653: //hand
            return 1590;
			
			case 82://demons
			case 83:
			case 84:
			case 10039:
			return 67;
			
			case 1605://abby spec
			return 1508;
			
			case 51://baby drags
			case 52:
			case 1589:
			case 3376:
			return 28;
			
			case 1610:
			case 1611:
			return 1518;
			
			case 1618:
			case 1619:
			return 1553;
			
			case 1620: case 1621:
			return 1563;
			
			case 2783:
			return 2733;
			
			case 1615:
			return 1538;
			
			case 1624:
			return 1558;
			
			case 1613:
			return 1530;
			
			case 1633: case 1634: case 1635: case 1636:
			return 1580;
			
			case 1648: case 1649: case 1650: case 1651: case 1652: case 1654: case 1655: case 1656: case 1657:
			return 1590;
			
			case 100: case 102:
			return 6182;
			
			case 105:
			case 106:
			return 4929;
			
			case 412:
			case 78:
			return 4917;
			
			case 122:
			case 123:
			return 167;
			
			case 58: case 59: case 60: case 61: case 62: case 63: case 64: case 134:
			return 5329;
			
			case 1153: case 1154: case 1156: case 1157:
			return 6230;
			
			
			case 118: case 119:
			return 102;
			
			
			case 50://drags
			case 4000:
			case 53:
			case 742:
			case 54:
			case 55:
				case 941:
				case 4677:
			case 1590:
			case 1591:
			case 1592:
			return 92;
			
			
			default:
			return 2304;
		}
	}
	public boolean AttackNPC(int NPCID) {
		if (Server.npcHandler.npcs[npcs[NPCID].attacknpc] != null) {
			int EnemyX = Server.npcHandler.npcs[npcs[NPCID].attacknpc].absX;
			int EnemyY = Server.npcHandler.npcs[npcs[NPCID].attacknpc].absY;
			int EnemyHP = Server.npcHandler.npcs[npcs[NPCID].attacknpc].HP;
			int hitDiff = 0;

			hitDiff = Misc.random(npcs[NPCID].maxHit);
			if (goodDistance(EnemyX, EnemyY, npcs[NPCID].absX,npcs[NPCID].absY, 1) == true) {
				if (Server.npcHandler.npcs[npcs[NPCID].attacknpc].isDead == true) {
					//ResetAttackNPC(NPCID);
					// npcs[NPCID].textUpdate = "Oh yeah I win!";
					// npcs[NPCID].textUpdateRequired = true;
					npcs[NPCID].animNumber = 2103;
					npcs[NPCID].animUpdateRequired = true;
					npcs[NPCID].updateRequired = true;
				} else {
					if ((EnemyHP - hitDiff) < 0) {
						hitDiff = EnemyHP;
					}
					if (npcs[NPCID].npcType == 9)
						npcs[NPCID].animNumber = 386;
					if (npcs[NPCID].npcType == 3200)
						npcs[NPCID].animNumber = 0x326; // drags: chaos ele
					// emote ( YESSS )
					if ((npcs[NPCID].npcType == 1605)
							|| (npcs[NPCID].npcType == 1472)) {
						npcs[NPCID].animNumber = 386; // drags: abberant
						// spector death ( YAY )
					}
					
					npcs[NPCID].animUpdateRequired = true;
					npcs[NPCID].updateRequired = true;
					Server.npcHandler.npcs[npcs[NPCID].attacknpc].hitDiff = hitDiff;
					Server.npcHandler.npcs[npcs[NPCID].attacknpc].attacknpc = NPCID;
					Server.npcHandler.npcs[npcs[NPCID].attacknpc].updateRequired = true;
					Server.npcHandler.npcs[npcs[NPCID].attacknpc].hitUpdateRequired = true;
					npcs[NPCID].actionTimer = 7;
					return true;
				}
			}
		}
		return false;
	}

		public void bosses() {
	for (int i = 0; i < maxNPCs; i++) {
	if (npcs[i] == null) continue;	
	}       
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] != null) {
				if (npcs[i].npcType == 3067 || npcs[i].npcType == 2779 || npcs[i].npcType == 2780) {
				int r = Misc.random(30);
					if (r == 5) {
						if (npcs[i].HP < 250) {
							npcs[i].HP += 35;
							npcs[i].animNumber = 829;
							npcs[i].animUpdateRequired = true;
							npcs[i].updateRequired = true;
								if (Misc.random(10) == 1) {
									npcs[i].forceChat("Rofl someone's gone mad, you think ur gonna win?");
							} else {
									npcs[i].forceChat("Stupid noob, im a legend, run away like a chicken!");		
							}
						}
					}
				}
			}			
		}
	}
	
public void attackNPC(int c, int i) {
		if(npcs[i] != null) {
			if (npcs[i].isDead)
				return;
			if (!npcs[i].inMulti() && npcs[i].underAttackBy > 0 && npcs[i].underAttackBy != npcs[c].npcId) {
				npcs[i].killerId = 0;
				return;
			}
			if (!npcs[i].inMulti() && (npcs[c].underAttackBy > 0 || (npcs[c].underAttackBy2 > 0 && npcs[c].underAttackBy2 != i))) {
				npcs[i].killerId = 0;
				return;
			}
			if (npcs[i].heightLevel != npcs[c].heightLevel) {
				npcs[i].killerId = 0;
				return;
			}
follownpc(i, c);
			npcs[i].facePlayer(npcs[c].npcId);
npcs[i].facenpc(npcs[c].npcId);
			boolean special = false;//specialCase(c,i);
			if(goodDistance(npcs[i].getX(), npcs[i].getY(), npcs[c].getX(), npcs[c].getY(), distanceRequired(i)) || special) {
				if(npcs[c].actionTimer <= 0) {
					npcs[i].facePlayer(npcs[c].npcId);
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					npcs[i].attackType = 0;

					if (special)
						loadSpell2(i);
					else

						loadSpell(i);




					npcs[c].underAttackBy2 = i;
					npcs[c].actionTimer = 7;
npcs[i].actionTimer = 5;
int damg = Misc.random(npcs[i].maxHit);
npcs[c].handleHitMask(damg, c);
npcs[c].HP -= damg;
npcs[c].hitUpdateRequired2 = true;
npcs[c].hitUpdateRequired = true;

					npcs[i].oldIndexNPC = npcs[c].npcId;
					startAnimation(getAttackEmote(i), i);
					//c.getPA().removeAllWindows();
				} 
			} 
		}
	}
	public void attacknpc(int i) {
		if(npcs[i] != null) {
			if (npcs[i].isDead)
				return;
			if (!npcs[i].inMulti() && npcs[i].underAttackBy > 0) {
				npcs[i].killerId = 0;
				return;
			}


			

			boolean special = false;//specialCase(c,i);
			if(goodDistance(npcs[i].getX(), npcs[i].getY(), Server.npcHandler.npcs[npcs[i].attacknpc].getX(), Server.npcHandler.npcs[npcs[i].attacknpc].getY(), 1) || special) {
			if(npcs[i].actionTimer <= 0 && npcs[i].isDead == false && Server.npcHandler.npcs[npcs[i].attacknpc].isDead == false)
{

					npcs[i].facePlayer(Server.npcHandler.npcs[npcs[i].attacknpc].npcId);
Server.npcHandler.npcs[npcs[i].attacknpc].facePlayer(npcs[i].npcId);
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					npcs[i].attackType = 0;
					if (special)
						loadSpell2(i);
					else
						loadSpell(i);
					if (npcs[i].attackType == 3)
						npcs[i].hitDelayTimer += 2;
					if (multiAttacks(i)) {
						multiAttackGfx(i, npcs[i].projectileId);
						startAnimation(getAttackEmote(i), i);
						npcs[i].oldIndex = Server.npcHandler.npcs[npcs[i].attacknpc].npcId;
						return;
					}
					Server.npcHandler.npcs[npcs[i].attacknpc].handleHitMask(Misc.random(npcs[i].maxHit), npcs[i].attacknpc);
if(Server.npcHandler.npcs[npcs[i].attacknpc].actionTimer <= 0 && npcs[i].isDead == false && Server.npcHandler.npcs[npcs[i].attacknpc].isDead == false)
{
Server.npcHandler.npcs[npcs[i].attacknpc].actionTimer = 7;
Server.npcHandler.npcs[npcs[i].attacknpc].handleHitMask(Misc.random(Server.npcHandler.npcs[npcs[i].attacknpc].maxHit), npcs[i].attacknpc);
				startAnimation(getAttackEmote(npcs[i].attacknpc), npcs[i].attacknpc);
}
					npcs[i].actionTimer = 7;
					npcs[i].npcIndex = Server.npcHandler.npcs[npcs[i].attacknpc].npcId;
					//npcs[i].facenpc(Server.npcHandler.npcs[npcs[i].attacknpc].npcId);
					//Server.npcHandler.npcs[npcs[i].attacknpc].facenpc(npcs[i].npcId);
					startAnimation(getAttackEmote(i), i);
Server.npcHandler.npcs[npcs[i].attacknpc].attacknpc = i;
	
					
				} 
			}			
		}
	}
	/**
	* Attack delays
	**/
	public int getNpcDelay(int i) {
		switch(npcs[i].npcType) {
			case 2025:
			case 4022:
			case 4024:
			case 2028:
			return 7;
			case 2030:
				return 5;
			case 9752:
			return 6;
			case 10040:
			return 3;
						case 1158:
			case 1160:
				return 6;
			case 2586:
			case 2587:
			case 2588:
			case 2589:
				return 9;
			case 1219:
				return 4;
			case 8133: // Corporeal beast
			case 4004:
			case 40:
			case 10128:
			case 3101: // Melee
			case 3102: // Range
			case 3103: // Mage
			return 7;
			case 9947:
			case 3847:
			return 6;
			case 8349: case 8350: case 8351:
			if (npcs[i].attackType == 2)
			return 4;
			else if (npcs[i].attackType == 1)
			return 6;
			else if (npcs[i].attackType == 0)
			return 7;
			case 3495:
			case 4020:
			return 3;
			case 2745:
			return 8;
			case 50:
			case 4000:
			case 53:
			case 54:
			case 55:
			case 941:
			case 4677:
			case 1590:
			case 1591:
			case 1592:
			return 8;

			case 6222:
			case 4003:
			case 6223:
			case 6206:
			case 6208:
			case 6204:
			case 6225:
			case 6227:
			case 6260:
			case 4001:
			return 6;
			//saradomin gw boss
			case 6247:
			case 4006:
			return 3;
			
			case 2636:
				if (npcs[i].attackType == 5) 
					return 12;
				else if (npcs[i].attackType == 4)
					return 7;
				else if (npcs[i].attackType == 3)
					return 3;
				else if (npcs[i].attackType == 2)
					return 7;
				else if (npcs[i].attackType == 1)
					return 7;
				else if (npcs[i].attackType == 0)
					return 7;
			
			default:
			return 5;
		}
	}
	
	/**
	* Hit delays
	**/
	public int getHitDelay(int i) {
		switch(npcs[i].npcType) {
			case 2881:
			case 4007:
			case 4008:
			case 4009:
			case 2882:
			case 3200:
			case 2892:
			case 2894:
			case 6208:
			case 6206:
			case 6203:
			case 1812:
			return 3;
			case 4972:
				return 3;
			case 4971:
				return 2;
			case 11119:
			return 1;
						case 1158:
			case 1160:
				if (npcs[i].attackType == 1 || npcs[i].attackType == 2)
					return 3;
				else
					return 2;
			case 2743:
			case 9752:
				if (npcs[i].attackType == 1 || npcs[i].attackType == 0)
					return 5;
				else
					return 4;
			case 2631:
			case 6222:
			case 4003:
			case 6223:
			case 6225:

			case 6239: // Aviansie
			case 6230:
			case 6233:
			case 6232:
			case 6276:
			case 6257:
			case 6221:
case 41:
			return 3;
			
			case 2745:
			case 4020:
			if (npcs[i].attackType == 1 || npcs[i].attackType == 2) {
			return 5;
			} else {
			return 2;
			} 			
			case 2025:
			case 4022:
			return 4;
			case 2028:
			case 4024:
			return 3;
			case 3495:
			return 2;
			
			case 2636:
				if (npcs[i].attackType == 5) {//2=2,1=4,0=4,4=4,5=6,3=2
					return 6;
					} else 	if (npcs[i].attackType == 4) {
						return 4;
					} else 	if (npcs[i].attackType == 3) {
						return 2;
					} else 	if (npcs[i].attackType == 2) {
						return 2;
					} else 	if (npcs[i].attackType == 1) {
						return 4;
					} else 	if (npcs[i].attackType == 0) {
						return 4;
					}

			default:
			return 2;
		}
	}
	
	/**
	* Npc respawn time
	**/
	public int getRespawnTime(int i) {
		switch(npcs[i].npcType) {


			case 40:
			case 10128:
			case 41:
			case 2636:
			case 6261:
			case 6263:
			case 6265:
			case 8349: 
			case 8350: 
			case 8351:
			case 1158:
			case 6203:
			case 6204:
			case 6206:
			case 2881:
			case 4007:
			case 4008:
			case 4009:
			case 2882:
			case 2883:
			case 6222:
			case 4003:
			case 6223:
			case 6225:
			case 6227:
			case 6247:
			case 4006:
			case 6248:
			case 6250:
			case 6260:
			case 4001:
			case 6208:
			case 2586:
			case 2587:
			case 2588:
			case 2589:
			case 8133:
			case 4004:
			case 3101:
			case 3102:
			case 3103:
			case 97:
			case 98:
			case 3247:
			case 6270:
			case 6219:
			case 6255:
			case 6229:
			case 6277:
			case 6233:
			case 6232:
			case 6218:
			case 6269:
			case 3248:
			case 6212:
			case 6220:
			case 6276:
			case 6256:
			case 6239:
			case 6230:
			case 6221:
			case 6231:
			case 6257:
			case 6278:
			case 6272:
			case 6274:
			case 6254:
			case 6258:
			case 50:
			case 4000:
			case 53:
			case 54:
			case 55:
			case 941:
			case 4677:
			case 1590:
			case 1591:
			case 4291:
			case 4292:
			case 1592:
	        case 6142: 
	        case 6143: 
	        case 6144: 
	        case 6145: 
			case 9947:
			case 3847:
			return 12;
			case 7479:
			return 301;
			case 3069:
			return 12;
			case 1816:
			case 10148:
			case 4020:
			case 69:
			case 1814:
			return -1;//spawn timer so it won't respawn
			case 10156://Ihlakhizan
			return 200;
			case 10149:
			return 100;
			
			default:
			return 15;

		}
	}


	
	
	
	public void newNPC(int npcType, int x, int y, int heightLevel, int WalkingType, int HP, int maxHit, int attack, int defence) {
		int slot = -1;
		for (int i = 1; i < maxNPCs; i++) {
			if (npcs[i] == null) {
				slot = i;
				break;
			}
		}
		if(slot == -1)
			return;
		NPC newNPC = new NPC(slot, npcType);
		newNPC.absX = x;
		newNPC.absY = y;
		newNPC.makeX = x;
		newNPC.makeY = y;
		newNPC.heightLevel = heightLevel;
		newNPC.walkingType = WalkingType;
		newNPC.HP = HP;
		newNPC.MaxHP = HP;
		newNPC.maxHit = maxHit;
		newNPC.attack = attack;
		newNPC.defence = defence;
		npcs[slot] = newNPC;
	}

	public void newNPCList(int npcType, String npcName, int combat, int HP) {
		int slot = -1;
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] == null) {
				slot = i;
				break;
			}
		}
		if(slot == -1)
			return;
		NPCList newNPCList = new NPCList(npcType);
		newNPCList.npcName = npcName;
		newNPCList.npcCombat = combat;
		newNPCList.npcHealth = HP;
		NpcList[slot] = newNPCList;
	}

	public void process() {
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] == null) continue;
			npcs[i].clearUpdateFlags();
			
		}
		for (int i = 0; i < maxNPCs; i++) {
			if (npcs[i] != null) {
                                          Client slaveOwner = (Client) Server.playerHandler.players[npcs[i].summonedBy]; 
				
				if (slaveOwner == null && npcs[i].summoner) {
					npcs[i].absX = 0;
					npcs[i].absY = 0;
				}
				if (slaveOwner != null && slaveOwner.hasNpc && !slaveOwner.goodDistance(npcs[i].getX(), npcs[i].getY(), slaveOwner.absX, slaveOwner.absY, 15) && npcs[i].summoner) {
					npcs[i].absX = slaveOwner.absX;
					npcs[i].absY = slaveOwner.absY - 1;
				}





if(npcs[i].summon == true) {
							Client c = (Client)Server.playerHandler.players[npcs[i].spawnedBy];	
						

if(c != null && c.npcIndex > 0) {

follownpc(i, c.npcIndex);
}
if(c != null && c.playerIndex < 1 && npcs[i].summon == true) {
if(!npcs[i].underAttack) {
if(!Server.playerHandler.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), Server.playerHandler.players[npcs[i].spawnedBy].getX(), Server.playerHandler.players[npcs[i].spawnedBy].getY(), 2) && c.npcIndex < 1)
followPlayer(i, c.playerId);
}
} else {
if(c != null && npcs[i].summon == true) {
if(!Server.playerHandler.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), Server.playerHandler.players[npcs[i].spawnedBy].getX(), Server.playerHandler.players[npcs[i].spawnedBy].getY(), 5) && c.playerIndex < 1 || c.npcIndex < 1)
{
followPlayer(i, c.playerId);
}
}
	
}			


				if(c != null && c.hasFollower > 0 && !Server.playerHandler.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), Server.playerHandler.players[npcs[i].spawnedBy].getX(), Server.playerHandler.players[npcs[i].spawnedBy].getY(), 10) && npcs[i].summon == true && !npcs[i].isDead)
				{

npcs[i].isDead = true;
npcs[i].applyDead = true;
c.Summoning.SummonNewNPC(c.hasFollower);
npcs[i].gfx0(1315);
npcs[i].underAttackBy2 = -1;
npcs[i].updateRequired = true;
npcs[i].dirUpdateRequired = true;
npcs[i].getNextWalkingDirection();
				}


if(c != null && c.hasFollower < 0 || c == null)
{
npcs[i].isDead = true;
npcs[i].applyDead = true;
npcs[i].summon = false;
npcs[i].underAttackBy2 = -1;
}


if(c != null && npcs[i].actionTimer < 1 && npcs[i].summon == true)
{
if(c.playerIndex > 0)
{
	Client o = (Client)Server.playerHandler.players[c.playerIndex];
if(o != null) {
if(npcs[i].IsAttackingPerson = true && o.inMulti())
{
followPlayer(i, o.playerId);
attackPlayer(o, i);
npcs[i].index = o.playerId;
npcs[i].actionTimer = 7;
}
}
}
}
		                }

                                if (npcs[i].npcType == 692) { 
                                        if (Misc.random2(2) <= 3) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Get ya Fresh 'ome grown Weed 'ere, 1 Dollar a Gram!");
                                        }
                                } 
                                
                                if (npcs[i].npcType == 1818) { 
                                    if (Misc.random2(2) <= 3) {
                                            npcs[i].updateRequired = true;
                                            npcs[i].forceChat("Talk To Me!");
                                    }
                            } 
                                
                       			  if (npcs[i].npcType == 2086) { 
                                        if (Misc.random(100) < 3) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Oh my..The dragon..It's back! We're all doomed!");
                                        }
                                }   					  

							   if (npcs[i].npcType == 7833) { 
                                        if (Misc.random2(2) <= 3) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Portal to funpk!");
                                        }
                                } 
							   
							   if (npcs[i].npcType == 1665) { 
                                   if (Misc.random2(2) <= 3) {
                                           npcs[i].updateRequired = true;
                                           npcs[i].forceChat("Release the PUMPKINS!");
                                   }
                           } 
								if (npcs[i].npcType == 596){
		npcs[i].animNumber = 866;
		npcs[i].animUpdateRequired = true;
		npcs[i].updateRequired = true;
}

							   if (npcs[i].npcType == 932) { 
                                        if (Misc.random2(2) <= 3) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Welcome!, Cross Gangplank! Next Ship to pest control!");
                                        }
                                } 
                                                                                                                                                        if (npcs[i].npcType == 1930) { 
                                        if (Misc.random2(2) <= 3) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Kill These Guys, they are angering me!");
                                        }
                                } 

							   if (npcs[i].npcType == 751) { 
							   Client c = (Client)Server.playerHandler.players[npcs[i].spawnedBy];	
                                        if (Misc.random2(2) > 500) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("Get HIM!!!!");
												
                                        } else { 
										 if (Misc.random2(2) > 450) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("WHAT ARE YOU ALL DOING? KILL HIM");
											} else {	
										if (Misc.random2(2) > 390) {
                                                npcs[i].updateRequired = true;
                                                npcs[i].forceChat("YOU SHALL SCREAM IN PAIN " + c.playerName.toUpperCase() + "!");
                                } 
								}
								}
} 								

				if (npcs[i].actionTimer > 0) {
					npcs[i].actionTimer--;
                                }
				if (npcs[i].freezeTimer > 0) {
					npcs[i].freezeTimer--;
				}
				if (npcs[i].hitDelayTimer > 0) {
					npcs[i].hitDelayTimer--;
				}
				
				if (npcs[i].hitDelayTimer == 1) {
					npcs[i].hitDelayTimer = 0;
					applyDamage(i);
				}
				
				if(npcs[i].attackTimer > 0) {
					npcs[i].attackTimer--;
				}
			
				if(npcs[i].spawnedBy > 0) { // delete summons npc this could be it..
					if(Server.playerHandler.players[npcs[i].spawnedBy] == null
					|| Server.playerHandler.players[npcs[i].spawnedBy].heightLevel != npcs[i].heightLevel	
					|| Server.playerHandler.players[npcs[i].spawnedBy].respawnTimer > 0 
					|| !Server.playerHandler.players[npcs[i].spawnedBy].goodDistance(npcs[i].getX(), npcs[i].getY(), Server.playerHandler.players[npcs[i].spawnedBy].getX(), Server.playerHandler.players[npcs[i].spawnedBy].getY(), 10)) {
							
						if(Server.playerHandler.players[npcs[i].spawnedBy] != null) {
							for(int o = 0; o < Server.playerHandler.players[npcs[i].spawnedBy].barrowsNpcs.length; o++){
								if (npcs[i].npcType != 3622) { 
									if(npcs[i].npcType == Server.playerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][0]) {
										if (Server.playerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] == 1)
											Server.playerHandler.players[npcs[i].spawnedBy].barrowsNpcs[o][1] = 0;
									}//cmpile 
								}
							}
						}
						Client c = (Client)Server.playerHandler.players[npcs[i].spawnedBy];	
						if (!npcs[i].InDung() && !npcs[i].inDungBossRoom()) { 
							npcs[i] = null;
							//loadAutoSpawn("./Data/CFG/spawn-config.cfg");
						}
					}
				}
				if (npcs[i] == null) continue;
				
				/**
				* Attacking player
				**/
				if (isAggressive(i) && !npcs[i].underAttack && !npcs[i].isDead && !switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				} else if (isAggressive(i) && !npcs[i].underAttack && !npcs[i].isDead && switchesAttackers(i)) {
					npcs[i].killerId = getCloseRandomPlayer(i);
				}
				
				if (System.currentTimeMillis() - npcs[i].lastDamageTaken > 5000)
					npcs[i].underAttackBy = 0;
				
				if((npcs[i].killerId > 0 || npcs[i].underAttack) && !npcs[i].walkingHome && retaliates(npcs[i].npcType)) {
					if(!npcs[i].isDead) {
						int p = npcs[i].killerId;
						if(Server.playerHandler.players[p] != null) {
							Client c = (Client) Server.playerHandler.players[p];					
							followPlayer(i, c.playerId);
							if (npcs[i] == null) continue;
							if(npcs[i].attackTimer == 0) {
								if(c != null) {
									attackPlayer(c, i);
								} else {
									npcs[i].killerId = 0;
									npcs[i].underAttack = false;
									npcs[i].facePlayer(0);
								}
							}
						} else {
							npcs[i].killerId = 0;
							npcs[i].underAttack = false;
							npcs[i].facePlayer(0);
						}
					}
				}
				/**
				* Random walking and walking home
				**/
				if (npcs[i] == null) continue;
				if((!npcs[i].underAttack || npcs[i].walkingHome) && npcs[i].randomWalk && !npcs[i].isDead) {
					npcs[i].facePlayer(0);
					npcs[i].killerId = 0;	
					if(npcs[i].spawnedBy == 0) {
						if((npcs[i].absX > npcs[i].makeX + Config.NPC_RANDOM_WALK_DISTANCE) || (npcs[i].absX < npcs[i].makeX - Config.NPC_RANDOM_WALK_DISTANCE) || (npcs[i].absY > npcs[i].makeY + Config.NPC_RANDOM_WALK_DISTANCE) || (npcs[i].absY < npcs[i].makeY - Config.NPC_RANDOM_WALK_DISTANCE)) {
							npcs[i].walkingHome = true;
						}
					}

					if (npcs[i].walkingHome && npcs[i].absX == npcs[i].makeX && npcs[i].absY == npcs[i].makeY) {
						npcs[i].walkingHome = false;
					} else if(npcs[i].walkingHome) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[i].makeX);
			      		npcs[i].moveY = GetMove(npcs[i].absY, npcs[i].makeY);
						npcs[i].getNextNPCMovement(i);
                                                                                                                                handleClipping(i);
						npcs[i].updateRequired = true;
					}
                                        if (npcs[i].walkingType >= 0) {
						switch(npcs[i].walkingType) {

						case 5:
							npcs[i].turnNpc(npcs[i].absX-1, npcs[i].absY);
							break;

						case 4:
							npcs[i].turnNpc(npcs[i].absX+1, npcs[i].absY);
							break;

						case 3:
							npcs[i].turnNpc(npcs[i].absX, npcs[i].absY-1);
							break;
						case 2:
							npcs[i].turnNpc(npcs[i].absX, npcs[i].absY+1);
							break;

						default:
							if (npcs[i].walkingType >= 0) {
								npcs[i].turnNpc(npcs[i].absX, npcs[i].absY); //makes it when npcs move they dont turn back to one direction
							}
							break;
						}
					}
					if(npcs[i].walkingType == 1) {
						if(Misc.random(3)== 1 && !npcs[i].walkingHome) {
							int MoveX = 0;
							int MoveY = 0;			
							int Rnd = Misc.random(9);
							if (Rnd == 1) {
								MoveX = 1;
								MoveY = 1;
							} else if (Rnd == 2) {
								MoveX = -1;
							} else if (Rnd == 3) {
								MoveY = -1;
							} else if (Rnd == 4) {
								MoveX = 1;
							} else if (Rnd == 5) {
								MoveY = 1;
							} else if (Rnd == 6) {
								MoveX = -1;
								MoveY = -1;
							} else if (Rnd == 7) {
								MoveX = -1;
								MoveY = 1;
							} else if (Rnd == 8) {
								MoveX = 1;
								MoveY = -1;
							}
										
							if (MoveX == 1) {
								if (npcs[i].absX + MoveX < npcs[i].makeX + 1) {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = -1;
								}
							}
							
							if (MoveX == -1) {
								if (npcs[i].absX - MoveX > npcs[i].makeX - 1)  {
									npcs[i].moveX = MoveX;
								} else {
									npcs[i].moveX = -1;
								}
							}
							
							if(MoveY == 1) {
								if(npcs[i].absY + MoveY < npcs[i].makeY + 1) {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = -1;
								}
							}
							
							if(MoveY == -1) {
								if(npcs[i].absY - MoveY > npcs[i].makeY - 1)  {
									npcs[i].moveY = MoveY;
								} else {
									npcs[i].moveY = -1;
								}
							}
								

							int x = (npcs[i].absX + npcs[i].moveX);
							int y = (npcs[i].absY + npcs[i].moveY);
							if (VirtualWorld.I(npcs[i].heightLevel, npcs[i].absX, npcs[i].absY, x, y, 0)) {
								handleClipping(i);
								npcs[i].getNextNPCMovement(i);
                                                                                                                                                     }
							else
							{
								npcs[i].moveX = -1;
								npcs[i].moveY = -1;
							} 
							npcs[i].updateRequired = true;
						}
					}
				}
			if (npcs[i].npcType == 659){
				npcs[i].animNumber = 4939;
				npcs[i].animUpdateRequired = true;
				npcs[i].updateRequired = true;
			}
				
				if (npcs[i].isDead == true) {
					if (npcs[i].actionTimer == 0 && npcs[i].applyDead == false
							&& npcs[i].needRespawn == false) {
						npcs[i].updateRequired = true;
						npcs[i].facePlayer(0);
						npcs[i].killedBy = getNpcKillerId(i);
						npcs[i].animNumber = getDeadEmote(i); // dead emote
						npcs[i].animUpdateRequired = true;
						npcs[i].freezeTimer = 0;
						npcs[i].applyDead = true;
						killedBarrow(i);
						if (isFightCaveNpc(i))
							killedTzhaar(i);
                                                if (isDominionNpc(i))
                                                        killedDominion(i);
                                                if (isBarrowsNpc(i))
                                                    killedBarrows(i);
                                                if (Raid.getNpcState(npcs[i].npcType) == Raid.PLAYING ) {
                                                	killedRaid(i);
                                                  }
                                                
						//if (isRFDNpc(i))
							//killedRFD(i);
                                                                                                                                if (isBarbNpc(i))
							killedBarb(i);
						if (npcs[i].summon == true)
							npcs[i].summon = false;
						npcs[i].actionTimer = 4; // delete time
						resetPlayersInCombat(i);
					} else if (npcs[i].actionTimer == 0
							&& npcs[i].applyDead == true
							&& npcs[i].needRespawn == false) {
						npcs[i].needRespawn = true;
						npcs[i].actionTimer = getRespawnTime(i); // respawn time
						if (!npcs[i].inBarbDef() && !npcs[i].InDung()) {
						dropItems(i); // npc drops items!
						}
						appendSlayerExperience(i);
						//appendKillCount(i);
						npcs[i].absX = npcs[i].makeX;
						npcs[i].absY = npcs[i].makeY;				
						npcs[i].HP = npcs[i].MaxHP;
						npcs[i].animNumber = 0x328;
						npcs[i].updateRequired = true;
						npcs[i].animUpdateRequired = true;
						if (npcs[i].npcType >= 2440 && npcs[i].npcType <= 2446) {
							Server.objectManager.removeObject(npcs[i].absX, npcs[i].absY);
						}
						//if (npcs[i].npcType == 2745) {
						//	handleJadDeath(i);
						//}
                        if (npcs[i].npcType == 2745) {
                        handleDominionDeath(i);
                                                }
                        if (npcs[i].npcType == 2030) {
                        handleBarrowsDeath(i);
                                                }
						if (npcs[i].npcType == 8528) {
							handleNomadDeath(i);
						}					
						if (npcs[i].npcType == 6260) {
							handleBandosDeath(i);
						}					
						if (npcs[i].npcType == 4001) {
							handleBandosDeath(i);
						}
						if (npcs[i].npcType == 50) {
							handleKbdDeath(i);
						}
						if (npcs[i].npcType == 3200) {
							handleChaosEleDeath(i);
						}
						if (npcs[i].npcType == 4002) {
							handleChaosEleDeath(i);
						}
						if (npcs[i].npcType == 8133) {
						handleCorpDeath(i);
						}
						if (npcs[i].npcType == 4004) {
						handleCorpDeath(i);
						}
						if (npcs[i].npcType == 3663 ) {
							handleGoblinDeath(i);
						}
						if (npcs[i].npcType == 3491) {
							handleRFDDeath(i);
						}
					} else if (npcs[i].actionTimer == 0 && npcs[i].needRespawn == true && npcs[i].npcType != 6142 && npcs[i].npcType != 6143 && npcs[i].npcType != 6144 && npcs[i].npcType != 6145) {					
						if(npcs[i].spawnedBy > 0) {
							npcs[i] = null;
						} else {
							int old1 = npcs[i].npcType;
							int old2 = npcs[i].makeX;
							int old3 = npcs[i].makeY;
							int old4 = npcs[i].heightLevel;
							int old5 = npcs[i].walkingType;
							int old6 = npcs[i].MaxHP;
							int old7 = npcs[i].maxHit;
							int old8 = npcs[i].attack;
							int old9 = npcs[i].defence;
	if(!npcs[i].InDung()) { // makes sure npcs in dung dont respawn
							npcs[i] = null;
							newNPC(old1, old2, old3, old4, old5, old6, old7,
									old8, old9);
						
						}
						}
						
					}
				}
			}
		}
	}
       
	public boolean getsPulled(int i) {
		switch (npcs[i].npcType) {
			case 6260:
				if (npcs[i].firstAttacker > 0)
					return false;
			break;
		}
		return true;
	}
	   
	public boolean multiAttacks(int i) {
		switch (npcs[i].npcType) {
			case 6222://bandos?
			case 11119:
			return true;
			case 10156://Ihlakhizan
			case 10148:
				if (npcs[i].attackType == 1)
					return true;

			case 40:

			case 10128:
			case 8996:
			case 7480:
			case 7479:	
			case 8133: // Corporeal beast
			if (npcs[i].attackType == 2)
				return true;
			
			case 6247://saradomin?
			if (npcs[i].attackType == 2)
				return true;
				
			case 6260://armadyl?
			if (npcs[i].attackType == 1)
				return true;

			
			default:
			return false;
		}
	
	
	}
	
	/**
	* Npc killer id?
	**/
	
	public int getNpcKillerId(int npcId) {
		int oldDamage = 0;
		int count = 0;
		int killerId = 0;
		for (int p = 1; p < Config.MAX_PLAYERS; p++)  {	
			if (Server.playerHandler.players[p] != null) {
				if(Server.playerHandler.players[p].lastNpcAttacked == npcId) {
					if(Server.playerHandler.players[p].totalDamageDealt > oldDamage) {
						oldDamage = Server.playerHandler.players[p].totalDamageDealt;
						killerId = p;
					}
					Server.playerHandler.players[p].totalDamageDealt = 0;
				}	
			}
		}				
		return killerId;
	}
		
	/**
	 * 
	 */
	private void killedBarrow(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			for(int o = 0; o < c.barrowsNpcs.length; o++){
				if(npcs[i].npcType == c.barrowsNpcs[o][0]) {
					c.barrowsNpcs[o][1] = 2; // 2 for dead
					c.barrowsKillCount++;	

				}
			}
		}
	}

	
	
	private void killedTzhaar(int i) {
		final Client c2 = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
		c2.tzhaarKilled++;
		//System.out.println("To kill: " + c2.tzhaarToKill + " killed: " + c2.tzhaarKilled);
		if (c2.tzhaarKilled == c2.tzhaarToKill) {
			//c2.sendMessage("STARTING EVENT");
			c2.waveId++;
			Server.getTaskScheduler().addEvent(new Task(10, false) {
				public void execute() {
					if (c2 != null) {
						Server.fightCaves.spawnNextWave(c2);
					}	
					this.stop();
				}
			});
			
		}
	}

	
	
	
        private void killedDominion(int i) {
        final Client c2 = (Client) Server.playerHandler.players[npcs[i].spawnedBy];
        c2.dominionKilled++;
        if (c2.dominionKilled == c2.dominionToKill) {
            c2.waveId++;
            EventManager.getSingleton().addEvent(new Event() {
                public void execute(EventContainer c) {
                    if (c2 != null) {
                        Server.dominionTower.spawnNextWave(c2);
                    }
                    c.stop();
                }
            }, 7500);
 
        }
    }
        private void killedBarrows(int i) {
        final Client c2 = (Client) Server.playerHandler.players[npcs[i].spawnedBy];
        c2.BarrowsKilled++;
        if (c2.BarrowsKilled == c2.BarrowsToKill) {
            c2.waveId++;
            EventManager.getSingleton().addEvent(new Event() {
                public void execute(EventContainer c) {
                    if (c2 != null) {
                        Server.BarrowsTower.spawnNextWave(c2);
                    }
                    c.stop();
                }
            }, 7500);
 
        }
    }



	
        private void killedBarb(int i) {
		final Client c2 = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
		c2.barbsKilled++;
		if (c2.barbsKilled == c2.barbsToKill) {
			c2.barbWave++;
			Server.getTaskScheduler().addEvent(new Task(4, false) {
				public void execute() {
					if (c2 != null) {
						Server.barbDefence.spawnWave(c2);
					}	
					this.stop();
				}
			});
		}
	}
	
	public void handleJadDeath(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
		c.getItems().addItem(6570,1);
		c.sendMessage("Congratulations on completing the fight caves minigame!");
		c.getPA().resetTzhaar();
		c.waveId = 300;
	}
    public void handleBarrowsDeath(int i) {
	Client c = (Client) Server.playerHandler.players[npcs[i].spawnedBy];
	
	if (c.barrowPoints == 10 || c.barrowPoints == 20 || c.barrowPoints == 30 || c.barrowPoints == 40 || c.barrowPoints == 50) {
	c.sendMessage("<col=255>You received 3 Super Torva Box in your bank for "+ c.barrowPoints +" consecutive wins!");
	c.getItems().addItemToBank(19865, 4);
	}
	c.barrowPoints ++;
	c.sendMessage("<col=800000000>You Currently Have "+ c.barrowPoints +" Barrows Minigame Points!");
	
	c.getPA().resetbarrows();
	c.waveId = 300;
    }
        public void handleDominionDeath(int i) {
		Client c = (Client) Server.playerHandler.players[npcs[i].spawnedBy];
		//c.DT();
		if (c.jadPetKilled == 15) {
			c.sendMessage("<col=255>You have already received the TokTz-Jad pet! You can no longer get this.");
			c.sendMessage("<col=255>You received a Super Torva Box in your bank for 15 wins!");
			c.getItems().addItemToBank(19865, 1);
			c.jadPetKilled ++;
			c.sendMessage("<col=800000000>You currently have "+ c.jadPetKilled +" Tzhaar Minigame wins!");
			return;
		}
		if (c.jadPetKilled >= 1) {
			c.sendMessage("<col=255>You have already received the TokTz-Jad pet! You can no longer get this.");
			c.sendMessage("<col=255>You received a Torva Box in your bank instead! At 15 wins you will receive an amazing reward.");
			c.getItems().addItemToBank(19867, 1);
			c.jadPetKilled ++;
			c.sendMessage("<col=800000000>You currently have "+ c.jadPetKilled +" Tzhaar Minigame wins!");
			return;
		}
		if (c.getItems().freeSlots() < 1 ) {
			c.sendMessage("<col=255>You have completed the Tzhaar challenge! Congratulations on the new pet!");
			c.sendMessage("<col=255>Not enough inventory space. The pet has been added to your bank.");
	        c.getItems().addItemToBank(12477, 1);
		}
		if (c.getItems().freeSlots() >= 1 ) {
			c.sendMessage("<col=255>Congratulations on completing the Tzhaar challenge! Congratulations on the new pet!");
			c.sendMessage("<col=255>The pet has been added to your inventory.");
	        c.getItems().addItem(12477, 1);
		}
		c.getPA().resetDominion();
		c.waveId = 300;
		c.jadPetKilled ++;
        }
	public void handleCorpDeath(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
			if(c.inFightCaves()) {
			c.getPA().handleFightCavesWinNewOne();
		}
		}
			public void handleKbdDeath(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
			if(c.inFightCaves()) {
			c.getPA().movePlayer(2401, 5086, c.playerId * 4);
			c.sendMessage("You're teleported back into the middle..");
		Server.hardCaves.spawnNextWave((Client)Server.playerHandler.players[c.playerId]);
		} else {
		if(!c.inFightCaves()) {
		return;
		}
		}
		}
		public void handleChaosEleDeath(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
			if(c.inFightCaves()) {
			c.getPA().movePlayer(2401, 5086, c.playerId * 4);
			c.sendMessage("You're teleported back into the middle..");
		Server.hardCaves.spawnNextWave((Client)Server.playerHandler.players[c.playerId]);
		} else {
		if(c.InDung()) {
		return;
		} else {
		if(!c.InDung() && !c.inFightCaves()) {
		return;
		}
		}
		}
		}
	public void handleBandosDeath(int i) {
Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
			if(c.inFightCaves()) {
			c.getPA().movePlayer(2401, 5086, c.playerId * 4);
			c.sendMessage("You're teleported back into the middle..");
		Server.hardCaves.spawnNextWave((Client)Server.playerHandler.players[c.playerId]);
		} else {
		if(!c.inFightCaves()) {
		return;
		}
		}
		}
		public void handleNomadDeath(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
		if(c.inFightCaves()) {
		c.getPA().movePlayer(2401, 5086, c.playerId * 4);
		c.sendMessage("You're teleported back into the middle..");
		Server.hardCaves.spawnNextWave((Client)Server.playerHandler.players[c.playerId]);
		} else {
		c.getPA().movePlayer(3211, 3422, 0);
		c.getItems().addItem(15432,1);
		c.getItems().addItem(15435,1);
		c.sendMessage("Congratulations on completing the Nomad minigame!");
		c.sendMessage("Enjoy your new sexy capes!");
	}
	}
	
			public void handleGoblinDeath(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
			if(c.inFightCaves()) {
				c.getPA().movePlayer(2401, 5086, c.playerId * 4);
				c.sendMessage("You're teleported back into the middle..");
		Server.hardCaves.spawnNextWave((Client)Server.playerHandler.players[c.playerId]);
		} else {
		c.getPA().movePlayer(3211, 3422, 0);
		c.getItems().addItem(15241,1);
		c.getItems().addItem(13263,1);
		c.sendMessage("Congratulations on completing the Mad Goblin minigame!");
		c.sendMessage("Enjoy your new items! Make sure to train firemaking!");
	}
	}

	public void handleRFDDeath(int i) {
		Client c = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
		c.sendMessage("Congratulations you have completed the RFD minigame!");
		c.sendMessage("Why not check out the chest for new sexy gloves?");
		c.getPA().resetRFD();
		c.waveId = 300;
	}
	
	
	/**
	* Dropping Items!
	**/
	 public boolean rareDrops(int i) {
	Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
       int rarity = 0;
       int ringOfWealth = 0;
       int npcDropRate = ((Integer)NPCDrops.dropRarity.get(Integer.valueOf(npcs[i].npcType))).intValue();
       if(c.isDonator == 1 && c.issDonator == 0) {
        ringOfWealth += 10;
	   }
      else if (c.issDonator == 1 && c.isDonator == 1) {
		   ringOfWealth += 20;
	   }
	         else if (c.issDonator == 2 && c.isDonator == 1) {
		   ringOfWealth += 25;
	   }
	 if (c.npcKills >= 2000 && c.npcKills <= 3999) {
		   ringOfWealth += 2;
	   } else if (c.npcKills >= 4000 && c.npcKills <= 5999) {
		   ringOfWealth += 4;
	   } else if (c.npcKills >= 6000 && c.npcKills <= 7999) {
		   ringOfWealth += 6;
	   } else if (c.npcKills >= 8000 && c.npcKills <= 9999) {
		   ringOfWealth += 8;
	   } else if (c.npcKills >= 10000) {
		   ringOfWealth += 10;
	   }
	   if(c.playerEquipment[c.playerRing] == 2572) {
		   ringOfWealth += 2;
                  }
	   if(c.playerEquipment[c.playerRing] == 13659) {
		   ringOfWealth += 5;
                  }
if (c.isDonator == 1 || c.npcKills >= 2000 || c.playerEquipment[c.playerRing] == 2572) {
	   c.sendMessage("Your drop rate has been increased by <col=16723968>"
					+ ringOfWealth + "%");
}
	   
	   
      rarity = Misc.random(npcDropRate-ringOfWealth);
       
      return rarity == 0;
   }
   int[] rareDrops = {968,4937,2572,718,15098,19058,19059,19060,19061,19062,19063,19107,3559,3561,12293,12295,12297,12299,
		   			  20251,20250,20253,20252,20158,11660,11662,11661,12233,12234,12235,12236,12237,12238,12239,5023,12225,12227,12228,
		   			  12229,12230,12231,12232,3288,3285,3289,3290,3291,3286,3287,989,990,12283,12284,12285,12274,12275,12276,12280,12281,12282,
		   			  12286,12287,12288,10900,1077,1125,1165,15500,1071,1121,1159,3539,3541,3543,3545,3547,3561,3563,3581,3583,3585,3587,3589,
		   			  10531,10532,10533,10534,10535,10536,10537,11964,11965,19865,13444};
	
	public boolean announceItem(int id) {
	
		for (int i = 0; i < rareDrops.length; i++) {
			if (id == rareDrops[i]) {
				return true;
			}
		}
		return false;
	}
	
	
	public void dropItems(int i) {
		int npc = 0;
		//if (!npcs[i].inBarbDef() || !npcs[i].InDung()) {
		//long start = System.currentTimeMillis();
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			c.npcKills += 1;
			c.lizardSpawn += 1;
			if (c.lizardSpawn >= 750) {
				c.lizardSpawn = 0;
				TzhaarSpawn.SpawnNpcs(c);
			}
			if (c.lizardSpawn == 400) {
				int raffleRolled = Misc.random(5);
				Config.SERVER_RAFFLE = raffleRolled;
				for (int j = 0; j < PlayerHandler.players.length; j++) {
	    			if (PlayerHandler.players[j] != null) {
	    				Client c2 = (Client)PlayerHandler.players[j];
	    				c2.sendMessage("<shad><col=800000>The server raffle has ended at "+ raffleRolled +". Claim your prize at home!</col>");
				}
				}
			}
			
			if (npcs[i].npcType == 912 || npcs[i].npcType == 913 || npcs[i].npcType == 914)
				c.magePoints += 1;
			if (npcs[i].npcType == 11119) {
				TzhaarSpawn.TzhaarCount--;
				NPCHandler.npcs[i].HP = 0;
       				NPCHandler.npcs[i].updateRequired = true;
        			NPCHandler.npcs[i].isDead = true;
			}
			if (NPCDrops.constantDrops.get(npcs[i].npcType) != null) {
				for (int item : NPCDrops.constantDrops.get(npcs[i].npcType)) {
					Server.itemHandler.createGroundItem(c, item, npcs[i].absX, npcs[i].absY, 1, c.playerId);
					//if (c.clanId >= 0)
						//Server.clanChat.handleLootShare(c, item, 1);
				}
			}

			//}
			//Client c = (Client)Server.playerHandler.players[npcs[i].spawnedBy];
						if(npcs[i].npcType > 0) {
int random2 = Misc.random(8);
				if(random2 == 4) {
					Server.itemHandler.createGroundItem(c, 12158, npcs[i].absX, npcs[i].absY, 1, c.playerId);
				}
	if(random2 == 3) {
					Server.itemHandler.createGroundItem(c, 12159, npcs[i].absX, npcs[i].absY, 1, c.playerId);
				}
	if(random2 == 2) {
					Server.itemHandler.createGroundItem(c, 12160, npcs[i].absX, npcs[i].absY, 1, c.playerId);
				}

	if(random2 == 1) {
					Server.itemHandler.createGroundItem(c, 12163, npcs[i].absX, npcs[i].absY, 1, c.playerId);
				}
				}
			if(npcs[i].npcType == 4291 || npcs[i].npcType == 4292 && c.inCyclops) {
				int random2 = Misc.random(25);
				if(random2 == 1) {
					Server.itemHandler.createGroundItem(c, c.getWarriorsGuild().getCyclopsDrop(c), npcs[i].absX, npcs[i].absY, 1, c.playerId);
				}
			}
if (npcs[i].npcType == 4278) {
c.getItems().addItem(8851, 5);
}
//dragon slayer
if(npcs[i].npcType == 4677) {
c.sendMessage("<shad=15695415>You killed the dragon, now go talk to sedridor, at lumbridge's graveyard");
c.kingQuest = 7;
c.getPA().startTeleport(3211, 3422, 0, "ancient");
					}
					if (npcs[i].npcType == NPCStats.GLACOR || npcs[i].npcType == NPCStats.JAD || npcs[i].npcType == NPCStats.TORMDEMON || npcs[i].npcType == NPCStats.NOMAD || npcs[i].npcType == NPCStats.CORPORAL
					 || npcs[i].npcType == NPCStats.KBD || npcs[i].npcType == NPCStats.NEX || npcs[i].npcType == NPCStats.BARRELCHEST || npcs[i].npcType == NPCStats.AVATAR || npcs[i].npcType == NPCStats.CHAOSELE
					 || npcs[i].npcType == NPCStats.DAGGBOSS1 || npcs[i].npcType == NPCStats.DAGGBOSS2 || npcs[i].npcType == NPCStats.DAGGBOSS3 || npcs[i].npcType == NPCStats.FROSTDRAG)
				{
					c.bossesKilled++;
				}
				if (npcs[i].npcType == NPCStats.MITHDRAG)
				{
					c.mithKilled++;
				}
				if (npcs[i].npcType == NPCStats.GLACOR)
				{
					c.glacorKilled++;
				}
				if (npcs[i].npcType == NPCStats.TORMDEMON)
				{
					c.demonsKilled++;
				}
				if (npcs[i].npcType == NPCStats.NOMAD)
				{
					c.nomadKilled++;
				}
				if (npcs[i].npcType == NPCStats.KBD)
				{
					c.kbdKilled++;
				}
				if (npcs[i].npcType == NPCStats.NEX)
				{
					c.nexKilled++;
				}
				if (npcs[i].npcType == NPCStats.BARRELCHEST)
				{
					c.barrelKilled++;
				}
				if (npcs[i].npcType == NPCStats.AVATAR)
				{
					c.avatarKilled++;
				}
				if (npcs[i].npcType == NPCStats.FROSTDRAG)
				{
					c.frostsKilled++;
				}
				if (npcs[i].npcType == NPCStats.CHAOSELE)
				{
					c.chaosKilled++;
				}
				if (npcs[i].npcType == NPCStats.DAGGBOSS1 || npcs[i].npcType == NPCStats.DAGGBOSS2 || npcs[i].npcType == NPCStats.DAGGBOSS3)
				{
					c.daggsKilled++;
				}
				if (npcs[i].npcType == NPCStats.ARMABOSS || npcs[i].npcType == NPCStats.ZAMMYBOSS || npcs[i].npcType == NPCStats.BANDOSBOSS)
				{
					c.godwarKilled++;
				}
				
				if (npcs[i].npcType == NPCStats.CyrexTorva)
				{
					c.CyrexTorvaKilled++;
					//AchievementManager.increase(c, Achievements.CyrexTorva);
				}
				if (npcs[i].npcType == NPCStats.BurstTorva)
				{
					c.BurstTorvaKilled++;
					//AchievementManager.increase(c, Achievements.BurstTorva);
				}
				if (npcs[i].npcType == NPCStats.Torva24K)
				{
					c.Torva24KKilled++;
					//AchievementManager.increase(c, Achievements.KTorva);
				}
				
				if (npcs[i].npcType == NPCStats.BlueTorva)
				{
					c.BlueTorvaKilled++;
					//AchievementManager.increase(c, Achievements.BlueTorva);
				}
				if (npcs[i].npcType == NPCStats.FlameTorva)
				{
					c.FlameTorvaKilled++;
					//AchievementManager.increase(c, Achievements.FlameTorva);
				}
				if (npcs[i].npcType == NPCStats.PredatorTorva)
				{
					c.PredatorTorvaKilled++;
					//AchievementManager.increase(c, Achievements.PredatorTorva);
				}
				if (npcs[i].npcType == NPCStats.MadMummy)
				{
					c.MadMummyKilled++;
				}
				if (npcs[i].npcType == NPCStats.ForgottenWarrior)
				{
					c.ForgottenWarriorKilled++;
				}
				if (npcs[i].npcType == NPCStats.KalQueen)
				{
					c.KalQueenKilled++;
				}
				if (npcs[i].npcType == NPCStats.GiantMole)
				{
					c.GiantMoleKilled++;
				}
				if (npcs[i].npcType == NPCStats.Wrecker)
				{
					c.WreckerKilled++;
				}
				if (npcs[i].npcType == NPCStats.Protector)
				{
					c.ProtectorKilled++;
				}
				if (npcs[i].npcType == NPCStats.VesBeast)
				{
					c.VesBeastKilled++;
				}
			if (npcs[i].npcType == NPCStats.CORPORAL)
			{
				c.corpKilled++;
			}

	if ((npcs[i].npcType == 4278 || npcs[i].npcType == 4279 || npcs[i].npcType == 4280 || npcs[i].npcType == 4281 || npcs[i].npcType == 4282 || npcs[i].npcType == 4283 || npcs[i].npcType == 4284)) {
c.spawned = false;
}
					//end
					
			/**
**** Start of CustomPoints
**/		
if (npcs[i].npcType == 84) {
	c.DTPoints += 3;
	c.sendMessage("You receive 3 Dominion Tower Points.");
}
if (npcs[i].npcType == 268) {
	c.customPoints += 8;
	c.sendMessage("You receive 8 Custom Points.");
}

if (npcs[i].npcType == 912) {
	c.mbPoints += 5;
	c.sendMessage("You receive 5 MageBank Points.");
}		
if (npcs[i].npcType == 913) {
	c.mbPoints += 5;
	c.sendMessage("You receive 5 MageBank Points.");
}		
if (npcs[i].npcType == 914) {
	c.mbPoints += 5;
	c.sendMessage("You receive 5 MageBank Points.");
}		
		
if (npcs[i].npcType == 6216) {
	c.customPoints += 30;
	c.pcPoints += 45;
	c.sendMessage("You receive 30 Custom & 45 Pandemic Points for killing the Pyrefiend.");
	c.sendMessage("You now have "+c.customPoints+" custom points and  "+c.pcPoints+" Pandemic points");
}		

if (npcs[i].npcType == 98) {
if(c.trade11 <= 0) {
	c.customPoints += 15;
	c.pcPoints += 25;
	c.sendMessage("You receive 15 Custom & 25 Pandemic Points for killing the Spongebob.");
	c.sendMessage("You now have "+c.customPoints+" custom points and  "+c.pcPoints+" Pandemic points");
	} else {
	c.customPoints += 30;
	c.pcPoints += 45;
	c.sendMessage("You receive 30 Custom & 45 Pandemic Points for killing the Spongebob.");
	c.sendMessage("You now have "+c.customPoints+" custom points and  "+c.pcPoints+" Pandemic points");
}
}
if (npcs[i].npcType == 168) {
if(c.trade11 <= 0) {
	c.customPoints += 15;
	c.pcPoints += 25;
	c.sendMessage("You receive 15 Custom & 25 Pandemic Points for killing the Spongebob.");
	c.sendMessage("You now have "+c.customPoints+" custom points and  "+c.pcPoints+" Pandemic points");
	} else {
	c.customPoints += 30;
	c.pcPoints += 45;
	c.sendMessage("You receive 30 Custom & 45 Pandemic Points for killing the Spongebob.");
	c.sendMessage("You now have "+c.customPoints+" custom points and  "+c.pcPoints+" Pandemic points");
}
}

if (npcs[i].npcType == 97) {
	if(c.trade11 <= 0) {
	c.customPoints += 10;
	c.pcPoints += 15;
	c.sendMessage("You receive 10 Custom & 15 Pandemic Points for killing the Pikachu.");
	c.sendMessage("You now have "+c.customPoints+" custom points and  "+c.pcPoints+" Pandemic points");
	//AchievementManager.increase(c, Achievements.PIKA);
	//AchievementManager.increase(c, Achievements.PEKA);
	} else {
	c.customPoints += 20;
	c.pcPoints += 25;
	c.sendMessage("You receive 20 Custom & 25 Pandemic Points for killing the Spongebob.");
	c.sendMessage("You now have "+c.customPoints+" custom points and  "+c.pcPoints+" Pandemic points");
	//AchievementManager.increase(c, Achievements.PIKA);
	//AchievementManager.increase(c, Achievements.PEKA);
	}
}
if (npcs[i].npcType == 2636) {
	//AchievementManager.increase(c, Achievements.NOMAD);
}
if (npcs[i].npcType == 4442) {
	//AchievementManager.increase(c, Achievements.PIKA);
	//AchievementManager.increase(c, Achievements.PEKA);
	c.customPoints += 5;
	c.pcPoints += 5;
	c.sendMessage("You receive 5 Custom & Pandemic Points.");
	}
if (npcs[i].npcType == 167) {
	//AchievementManager.increase(c, Achievements.PIKA);
	//AchievementManager.increase(c, Achievements.PEKA);
	c.customPoints += 5;
	c.pcPoints += 5;
	c.sendMessage("You receive 5 Custom & Pandemic Points.");
	}
if (npcs[i].npcType == 11120) {
	c.customPoints += 5;
	c.pcPoints += 5;
	c.sendMessage("You receive 25 Custom & Pandemic Points.");
	}
if (npcs[i].npcType == 511) {
	c.getPA().startTeleport(2796, 9332, 4, "modern");
	c.sendMessage("You have been teleported to the next boss!");
}
if (npcs[i].npcType == 512) {
	c.getPA().startTeleport(2796, 9332, 8, "modern");
	c.sendMessage("You have been teleported to the next boss!");
}
if (npcs[i].npcType == 513) {
	c.getPA().startTeleport(2796, 9332, 12, "modern");
	c.sendMessage("You have been teleported to the next boss!");
}
if (npcs[i].npcType == 514) {
	c.getPA().startTeleport(2796, 9332, 16, "modern");
	c.sendMessage("You have been teleported to the <col=255>Final Boss!");
}
if (npcs[i].npcType == 1388 && (c.customPoints == 3  || c.customPoints == 16  || c.customPoints == 50  || c.customPoints == 126)) {
	c.getItems().addItem(5021, 25);
	c.sendMessage("You received 25 extra tickets in your inventory.");
	c.customPoints += 1;
}
if (npcs[i].npcType == 1387 && (c.customPoints < 20)) {
	c.getItems().addItem(5021, 15);
	c.sendMessage("You received 15 extra tickets in your inventory for killing the Ticket!.");
	c.customPoints += 1;
}
if (npcs[i].npcType == 1386 && c.customPoints == 3  || c.customPoints == 26  || c.customPoints == 50  || c.customPoints == 76) {
	int[][] arrayOfTickets = {{4067, 100}, {4067, 50}, {5021, 70}, {4067, 65}};
int r = Misc.random(arrayOfTickets.length);
c.getItems().addItem(arrayOfTickets[r][0], arrayOfTickets[r][1]);
c.sendMessage("You received extra tickets in your inventory.");
}

if (npcs[i].npcType == 1386 && c.customPoints == 76) {
	c.customPoints = 0;
}
if (npcs[i].npcType == 1387 && c.customPoints == 126) {
	c.customPoints = 0;
}
if (npcs[i].npcType == 1388 && c.customPoints == 126) {
	c.customPoints = 0;
}
if (npcs[i].npcType == 1386) {
c.barrelKilled += 1; //Ticket Boss
}
if (npcs[i].npcType == 1387) {
c.avatarKilled += 1; //2B Ticket
}
if (npcs[i].npcType == 1388) {
	if (c.avatarKilled < 5) {
	c.getItems().addItem(5021, 50);
	c.sendMessage("<col=255>You received an extra 50 tickets because you're a new player!");
	}
c.avatarKilled += 1; //1B Ticket
}
if (npcs[i].npcType == 8133) {
	c.corpKilled += 1;//corp
}
if (npcs[i].npcType == 8995) {
	c.MadMummyKilled += 1;//vorago
}
if (npcs[i].npcType == 8996) {
	c.MadMummyKilled += 1;
}
if (npcs[i].npcType == 8997) {
	c.MadMummyKilled += 1;
}
if (npcs[i].npcType == 8998) {
	c.MadMummyKilled += 1;
}
if (npcs[i].npcType == 8999) {
	c.MadMummyKilled += 1;
}
if (npcs[i].npcType == 131) {
	c.frostsKilled += 1;
}
if (npcs[i].npcType == 91) {
	c.jadKilled += 1;
}
if (npcs[i].npcType == 10156 || npcs[i].npcType == 10156) {
	c.DTPoints += 1;
}
if (npcs[i].npcType == 1172 || npcs[i].npcType == 1173) {
	c.getPA().movePlayer(2387, 3488, 0);
	c.getItems().addItemToBank(4151, 1);
}
if (npcs[i].npcType == 1815) {
	c.ProtectorKilled += 1;
}
if (npcs[i].npcType == 9463) {//ice strykewyrm
	c.mithKilled += 1;
}
if (npcs[i].npcType == 93 && npcs[i].npcType == 94 && npcs[i].npcType == 95 && npcs[i].npcType == 96) {//pokemon
	c.ProtectorKilled += 1;
}
if (npcs[i].npcType == 1817) {
	c.KillsNr3 += 1;//Calculon
}
if (npcs[i].npcType == 1812) {
	c.demonsKilled += 1;//Hulk
}
if (npcs[i].npcType == 4971 && c.smallRocKilled < 30) {//SmallRoc
	c.smallRocKilled += 1;
	c.sendMessage("<col=255>You killed a small Roc, you now have "+ c.smallRocKilled +" / 30 Kills.");
	} else if (npcs[i].npcType == 4971 && c.smallRocKilled > 29){
		c.sendMessage("<col=255>You have been teleported to the Giant Roc's island. Good luck! He drains prayer.");
		c.getPA().startTeleport(2585, 4838, 4, "modern"); // Small Roc
	}
if (npcs[i].npcType == 4972) {
	if (c.KillsNr2 < 11) {
		c.KillsNr2 += 1;
		c.sendMessage("<col=255>You now have "+ c.KillsNr2 +" / 10 Giant Roc kills. Every 10 kills you receive an extra reward!");
	}
	if (c.KillsNr2 == 10 && c.KillsNr2 == 20 && c.KillsNr2 == 30 && c.KillsNr2 == 40 && c.KillsNr2 == 50 && c.KillsNr2 == 60
			 && c.KillsNr2 == 70 && c.KillsNr2 == 80 && c.KillsNr2 == 90 && c.KillsNr2 == 100 && c.KillsNr2 == 110)  {
		c.getItems().addItemToBank(19867, 3);
		c.sendMessage("<col=255>You received 3 extra Torva Boxes in your bank!");
	}
	if (c.getItems().freeSlots() >= 1 ) {
		int[] arrayOfC4 ={12265, 12266, 12267, 19867, 6199, 3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,
				3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,
				3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,};
		c.getItems().addItem(arrayOfC4[Misc.random(arrayOfC4.length -1)], 1);
		c.sendMessage("<col=255>Well done killing the Giant Roc, your reward has been added to your inventory.");
		c.getPA().movePlayer(2387, 3488, 0);
		c.smallRocKilled = 0;
				} else if (c.getItems().freeSlots() <= 1 ){
					int[] arrayOfC4 ={12265, 12266, 12267, 19867, 6199, 3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,
							3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,
							3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,3529, 3531, 3533, 3535, 3529, 3531, 3533, 3535,};
					c.getItems().addItemToBank(arrayOfC4[Misc.random(arrayOfC4.length -1)], 1);
					c.sendMessage("<col=255>You did not have enough inventory space. Your reward is added to your bank.");
				}
}
if (npcs[i].npcType == 2741) {
	spawnMiniJad(c, COORDS1); 
}
if (npcs[i].npcType == 10156) {
	spawnIhlakhizan(c, COORDS); 
}
if (npcs[i].npcType == 1615 && c.playerEquipment[c.playerCape] == 896) {
c.getPA().movePlayer(2772, 9340, 0);
c.sendMessage("<col=800000000>You teleport to the Owner Cape zone. You can not use your Cape here.");
}

//dung
if (npcs[i].npcType == 10530) {
c.getPA().addSkillXP(25000, 24);
c.dungPoints += 2;
c.sendMessage("You Gained 25,000 Dungeoneering Experience & Some Dung Points!");
}
if (npcs[i].npcType == 10903) {
c.getPA().addSkillXP(75000, 24);
c.dungPoints += 6;
c.sendMessage("You Gained 75,000 Dungeoneering Experience & Some Dung Points!");
}
if (npcs[i].npcType == 8596) {
c.getPA().addSkillXP(100000, 24);
c.dungPoints += 15;
c.sendMessage("You Gained 100,000 Dungeoneering Experience & Some Dung Points!");
}

//pokemon


if (npcs[i].npcType == 5529) {
//AchievementManager.increase(c, Achievements.YAKS);
}
if (npcs[i].npcType == 1265) {
//AchievementManager.increase(c, Achievements.CRABS);
}
if (npcs[i].npcType == 40) {
//AchievementManager.increase(c, Achievements.VESB);
}

if (npcs[i].npcType == 100 && c.PokeMini == 7) {
c.customPoints += 10;
c.sendMessage("You receive 10 custom points for completing the mini-game");
c.getPA().movePlayer(3547, 3565, 0);
c.PokeMini = 0;
c.getItems().addItem(4273, 1);
//AchievementManager.increase(c, Achievements.POKEMASTER);
//AchievementManager.increase(c, Achievements.PKMN);
//AchievementManager.increase(c, Achievements.PKMK);

}
if (npcs[i].npcType == 2457) {
c.customPoints += 1000;
c.pcPoints += 150;
c.getItems().addItemToBank(6199, 3);
c.sendMessage("<col=255>You receive 1k Custom & 150 Pandemic points for completing the scav mini-game!");
c.sendMessage("<col=255>3 Mystery boxes have been added to your bank for completing the scav mini-game!");
c.getPA().movePlayer(3547, 3565, 0);
c.getItems().addItem(4273, 1);
}

if (npcs[i].npcType == 10128) {
c.customPoints += 50;
c.pcPoints += 100;
c.pummelerPoints += 1;
c.sendMessage("You receive 1 Pummeler Point.");
c.sendMessage("You receive 50 custom points & 100 Pandemic Points for Slaying the Pummeler");
}

//end

if (npcs[i].npcType == 596){
		npcs[i].animNumber = 866;
		npcs[i].animUpdateRequired = true;
		npcs[i].updateRequired = true;
}
					
/**
**** 
**/
		
					
/**
**** Start of Dungeoneering KillDung!
**/
if (npcs[i].npcType == 8597) {
c.getPA().addSkillXP(30000, 24);
c.dungPoints += 16;
c.sendMessage("You've gained 30k Dungeoneering XP & 16 Tokens for killing the huge monster!");
}
if (npcs[i].npcType == 3068) {
c.getPA().addSkillXP(22000, 24);
c.dungPoints += 14;
c.sendMessage("You've gained 22k Dungeoneering XP & 14 Tokens for killing the huge monster!");
}
if (npcs[i].npcType == 2780) { //3475 9492
c.getPA().addSkillXP(19000, 24);
c.dungPoints += 13;
c.sendMessage("You've gained 19K Dungeoneering XP & 13 Tokens for killing the mage!");
}
if (npcs[i].npcType == 111) { //3475 9492
c.getPA().addSkillXP(11000, 24);
c.dungPoints += 7;
c.sendMessage("You've gained 11K Dungeoneering XP & 7 Tokens for killing the Ice Giant!");
}
if (npcs[i].npcType == 1904) { //3475 9492
c.sendMessage("You gain another kill..You've now killed "+c.Zammy+".");
}
if (npcs[i].npcType == 1157) { //3475 9492
c.sendMessage("You gain another kill..You've now killed "+c.Zammy+".");
}
if (npcs[i].npcType == 1154) { //3475 9492
c.sendMessage("You gain another kill..You've now killed "+c.Zammy+".");
}
if (npcs[i].npcType == 1633) { //3475 9492
c.customPoints += 20;
c.pcPoints += 30;
c.sendMessage("You Receive 20 Custom Points & 30 Pandemic Points.");
}
/**
****END of Dungeoneering KillDung!
**/
if (npcs[i].npcType == 132) {
appendJailKc(i);
}
if (BandosKC(i)) {
c.Band += 1;
//c.getPA().sendFrame126(""+c.Bandos+"", 16217);
}
if (SaraKC(i)) {
c.Sara += 1;
//c.getPA().sendFrame126(""+c.Sara+"", 16218);
}
if (ZammyKC(i)) {
  c.Zammy += 1;
//c.getPA().sendFrame126(""+c.Zammy+"", 16219);
}
if (npcs[i].npcType == 3493) {
c.Agrith = true;
}
if (npcs[i].npcType == 3494) {
c.Flambeed = true;
}
if (npcs[i].npcType == 3495) {
c.Karamel = true;
}
if (npcs[i].npcType == 3496) {
c.Dessourt = true;
}
if (npcs[i].npcType == 3491) {
c.Culin = true;
}
if (npcs[i].npcType == 8528) {
c.Nomad = true;
}
if (npcs[i].npcType == 3663) {
c.Goblin = true;
}
if (npcs[i].npcType == 4279) {
c.getItems().addItem(8851, 10);
c.getItems().addItem(1153, 1);
c.getItems().addItem(1115, 1);
c.getItems().addItem(1067, 1);
}
if (npcs[i].npcType == 4280) {
c.getItems().addItem(8851, 15);
c.getItems().addItem(1119, 1);
c.getItems().addItem(1069, 1);
c.getItems().addItem(1157, 1);
}
if (npcs[i].npcType == 4281) {
c.getItems().addItem(8851, 20);
c.getItems().addItem(1125, 1);
c.getItems().addItem(1077, 1);
c.getItems().addItem(1165, 1);
}
if (npcs[i].npcType == 4282) {
c.getItems().addItem(8851, 25);
c.getItems().addItem(1121, 1);
c.getItems().addItem(1071, 1);
c.getItems().addItem(1159, 1);

}
if (npcs[i].npcType == 4283) {
c.getItems().addItem(8851, 30);
c.getItems().addItem(1123, 1);
c.getItems().addItem(1161, 1);
c.getItems().addItem(1073, 1);
}
if (npcs[i].npcType == 4284) {
c.getItems().addItem(8851, 40);
c.getItems().addItem(1127, 1);
c.getItems().addItem(1079, 1);
c.getItems().addItem(1163, 1);
}

if (npcs[i].npcType == 2636) {
    int k1 = 0;
	k1 = Misc.random(30);//Nex armor= 13362 13358 13360 13355 13354 13352 13348 13346
if (k1 == 0) {
Server.itemHandler.createGroundItem(c, 1514, npcs[i].absX, npcs[i].absY, 200+Misc.random(175), c.playerId);
Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 1) {
	Server.itemHandler.createGroundItem(c, 9245, npcs[i].absX, npcs[i].absY, 200+Misc.random(175), c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 2) {
	Server.itemHandler.createGroundItem(c, 830, npcs[i].absX, npcs[i].absY, 50, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 3) {
	Server.itemHandler.createGroundItem(c, 13362, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 4) {
	Server.itemHandler.createGroundItem(c, 13360, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 5) {
	Server.itemHandler.createGroundItem(c, 13358, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 6) {
	Server.itemHandler.createGroundItem(c, 13355, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 7) {
	Server.itemHandler.createGroundItem(c, 13354, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 8) {
	Server.itemHandler.createGroundItem(c, 13352, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 9) {
	Server.itemHandler.createGroundItem(c, 13348, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 10) {
	Server.itemHandler.createGroundItem(c, 13346, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 11) {
	Server.itemHandler.createGroundItem(c, 6686, npcs[i].absX, npcs[i].absY, 4, c.playerId);
	Server.itemHandler.createGroundItem(c, 3025, npcs[i].absX, npcs[i].absY, 4, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 12) {
	Server.itemHandler.createGroundItem(c, 1289, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 13) {
	Server.itemHandler.createGroundItem(c, 1319, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 14) {
	Server.itemHandler.createGroundItem(c, 1373, npcs[i].absX, npcs[i].absY, 1, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 15) {
	Server.itemHandler.createGroundItem(c, 212, npcs[i].absX, npcs[i].absY, 75, c.playerId);
	Server.itemHandler.createGroundItem(c, 218, npcs[i].absX, npcs[i].absY, 75, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 16) {
	Server.itemHandler.createGroundItem(c, 5304, npcs[i].absX, npcs[i].absY, 12, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 17) {
	Server.itemHandler.createGroundItem(c, 5316, npcs[i].absX, npcs[i].absY, 5, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 18) {
	Server.itemHandler.createGroundItem(c, 220, npcs[i].absX, npcs[i].absY, 40, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 19) {
	Server.itemHandler.createGroundItem(c, 995, npcs[i].absX, npcs[i].absY, 19940+Misc.random(60), c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 20) {
	Server.itemHandler.createGroundItem(c, 7937, npcs[i].absX, npcs[i].absY, 4000, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 21) {
	Server.itemHandler.createGroundItem(c, 454, npcs[i].absX, npcs[i].absY, 2400, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 22) {
	Server.itemHandler.createGroundItem(c, 452, npcs[i].absX, npcs[i].absY, 80, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 23) {
	Server.itemHandler.createGroundItem(c, 1754, npcs[i].absX, npcs[i].absY, 400, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 24) {
	Server.itemHandler.createGroundItem(c, 565, npcs[i].absX, npcs[i].absY, 5000, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 25) {
	Server.itemHandler.createGroundItem(c, 560, npcs[i].absX, npcs[i].absY, 5000, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else if (k1 == 23) {
	Server.itemHandler.createGroundItem(c, 384, npcs[i].absX, npcs[i].absY, 500, c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
} else {
	Server.itemHandler.createGroundItem(c, 995, npcs[i].absX, npcs[i].absY, Misc.random(250000), c.playerId);
	Server.itemHandler.createGroundItem(c, 532, npcs[i].absX, npcs[i].absY, 1, c.playerId);
}
}

		/*	if (NPCDrops.dropRarity.get(npcs[i].npcType) != null) {
				if (rareDrops(i)) {
try {
					int random = Misc.random(NPCDrops.rareDrops.get(npcs[i].npcType).length-1);
					if (c.CSLS == 3) {
					if (c.clanId >= 0)
					Server.clanChat.handleCoinShare(c, NPCDrops.rareDrops.get(npcs[i].npcType)[random][0], NPCDrops.rareDrops.get(npcs[i].npcType)[random][1]);
					return;
					}
					if (c.CSLS == 1) {
					if (c.clanId >= 0)
					Server.clanChat.handleLootShare(c, NPCDrops.rareDrops.get(npcs[i].npcType)[random][0], NPCDrops.rareDrops.get(npcs[i].npcType)[random][1]);
					Server.itemHandler.createGroundItem(c, NPCDrops.rareDrops.get(npcs[i].npcType)[random][0], npcs[i].absX, npcs[i].absY, NPCDrops.rareDrops.get(npcs[i].npcType)[random][1], c.playerId);
					return;
					}
					Server.itemHandler.createGroundItem(c, NPCDrops.rareDrops.get(npcs[i].npcType)[random][0], npcs[i].absX, npcs[i].absY, NPCDrops.rareDrops.get(npcs[i].npcType)[random][1], c.playerId);
		} catch(Exception ex) {
		ex.printStackTrace();
		}
				} else {
					int random = Misc.random(NPCDrops.normalDrops.get(npcs[i].npcType).length-1);
						
					Server.itemHandler.createGroundItem(c, NPCDrops.normalDrops.get(npcs[i].npcType)[random][0], npcs[i].absX, npcs[i].absY, NPCDrops.normalDrops.get(npcs[i].npcType)[random][1], c.playerId);
					//Server.clanChat.handleLootShare(c, NPCDrops.normalDrops.get(npcs[i].npcType)[random][0], NPCDrops.normalDrops.get(npcs[i].npcType)[random][1]);
				}
			}	
			
		}

		//System.out.println("Took: " + (System.currentTimeMillis() - start));
	}*/
	
	if (NPCDrops.dropRarity.get(npcs[i].npcType) != null) {
				if (rareDrops(i)) {
					if (c.playerEquipment[c.playerCape] == 896 || c.playerEquipment[c.playerRing] == 773) {
					c.sendMessage("<col=800000000><shad>You just received a rare drop!");
				}
					int random = Misc.random(NPCDrops.rareDrops.get(npcs[i].npcType).length-1);
					Server.itemHandler.createGroundItem(c, NPCDrops.rareDrops.get(npcs[i].npcType)[random][0], npcs[i].absX, npcs[i].absY, NPCDrops.rareDrops.get(npcs[i].npcType)[random][1], c.playerId);
					if (announceItem(NPCDrops.rareDrops.get(npcs[i].npcType)[random][0])) {
						for (int z = 0; z < Server.playerHandler.players.length; z++) {
							if (Server.playerHandler.players[z] != null && c.playerEquipment[c.playerCape] != 896) {
								if (c.playerEquipment[c.playerRing] == 773 || c.playerEquipment[c.playerCape] == 896) {
									return;
								}
								Client o = (Client) Server.playerHandler.players[z];
								o.sendMessage("<col=616582400>" + Misc.formatPlayerName(c.playerName) + " received a drop: <shad>"+c.getItems().getItemName(NPCDrops.rareDrops.get(npcs[i].npcType)[random][0])+".");
							}
						}
					}
					if (c.clanId >= 0) {
					}
						//Server.clanChat.handleLootShare(c, NPCDrops.rareDrops.get(npcs[i].npcType)[random][0], NPCDrops.rareDrops.get(npcs[i].npcType)[random][1]);
				} else {
					int random = Misc.random(NPCDrops.normalDrops.get(npcs[i].npcType).length-1);
					FamiliarAttractData familiar = FamiliarAttractData.forNPCId(c.hasFollower);
					FamiliarAttractData items = FamiliarAttractData.forId(NPCDrops.normalDrops.get(npcs[i].npcType)[random][0]);
					
					if (items != null && familiar != null && c.getItems().freeSlots() > 0) {
						c.getItems().addItem(NPCDrops.normalDrops.get(npcs[i].npcType)[random][0], NPCDrops.normalDrops.get(npcs[i].npcType)[random][1]);
					} else {
					Server.itemHandler.createGroundItem(c, NPCDrops.normalDrops.get(npcs[i].npcType)[random][0], npcs[i].absX, npcs[i].absY, NPCDrops.normalDrops.get(npcs[i].npcType)[random][1], c.playerId);
					}
					if (c.clanId >= 0) {
					//	Server.clanChat.handleLootShare(c, NPCDrops.normalDrops.get(npcs[i].npcType)[random][0], NPCDrops.normalDrops.get(npcs[i].npcType)[random][1]);
				}}
			}	
			
		}
		//System.out.println("Took: " + (System.currentTimeMillis() - start));
	}
	
	
	
	//id of bones dropped by npcs
	public int boneDrop(int type) {
		switch (type) {
			case 1://normal bones
			
			case 9:
			case 100:
			case 12:
			case 17:
			case 803:
			case 18:
			case 81:
			case 101:
			case 41:
			case 19:
			case 90:
			case 75:
			case 86:
			case 78:
			case 912:
			case 913:
			case 914:
			case 1648:
			case 1643:
			case 1618:
			case 1624:
			case 181:
			case 119:
			case 49:
			case 26:
			case 1341:


			case 3247:
			case 6233:
			case 6232:
			case 3248:
			case 6212:
			case 6254:
			case 6258:
			return 526;
			case 117:
			case 6270: // Cyclops
			case 6269: // Ice cyclops
			case 4291: // Cyclops
			case 4292: // Ice cyclops
			case 5529:
			return 532;//big bones
			case 50://drags
			case 4000:
			case 53:
			case 54:
			case 55:
			case 941:
			case 1590:
			case 4677:
			case 1591:
			case 1592:
			case 6218:
			case 6272:
			case 6274:
			return 536;
			case 84:
			case 1615:
			case 1613:
			case 82:
			case 3200:
			case 6208:
			case 6206:
			case 6204:
			case 6203:
			return 592;
			case 2881:
			case 4007:
			case 4008:
			case 4009:
			case 2882:
			case 2883:
			return 6729;
			default:
			return -1;
		}	
	}

	public int getStackedDropAmount(int itemId, int npcId) {
		switch (itemId) {
			case 995:
				switch (npcId) {
					case 1:
					return 50+ Misc.random(50);
					case 9:
					return 133 + Misc.random(100);
					case 1624:
					return 1000 + Misc.random(300);
					case 1618:
					return 1000 + Misc.random(300);
					case 1643:
					return 1000 + Misc.random(300);
					case 1610:
					return 1000 + Misc.random(1000);
					case 1613:
					return 1500 + Misc.random(1250);
					case 1615:
					return 3000;
					case 18:
					return 500;
					case 101:
					return 60;
					case 913:
					case 912:
					case 914:
					return 750 + Misc.random(500);
					case 1612:
					return 250 + Misc.random(500);
					case 1648:
					return 250 + Misc.random(250);
					case 90:
					return 200;
					case 82:
					return 1000 + Misc.random(455);
					case 52:
					return 400 + Misc.random(200);
					case 49:
					return 1500 + Misc.random(2000);
					case 1341:
					return 1500 + Misc.random(500);
					case 26:
					return 500 + Misc.random(100);
					case 20:
					return 750 + Misc.random(100);
					case 21: 
					return 890 + Misc.random(125);
					case 117:
					return 500 + Misc.random(250);
					case 2607:
					return 500 + Misc.random(350);
				}			
			break;
			case 11212:
			return 10 + Misc.random(4);
			case 565:
			case 561:
			return 10;
			case 560:
			case 563:
			case 562:
			return 15;
			case 555:
			case 554:
			case 556:
			case 557:
			return 20;
			case 892:
			return 40;
			case 886:
			return 100;
			case 6522:
			return 6 + Misc.random(5);
			
		}
	
		return 1;
	}
	
	/**
	* Slayer Experience
	**/	

	public void appendSlayerExperience(int i) {
		int npc = 0;
		Client c = (Client)Server.playerHandler.players[npcs[i].killedBy];
		if(c != null) {
			if (c.slayerTask == npcs[i].npcType ){
				c.taskAmount--;
				c.getPA().addSkillXP(npcs[i].MaxHP * Config.SLAYER_EXPERIENCE, 18);
				if (c.taskAmount <= 0 ) {
				if (npcs[i].npcType == 1645 || npcs[i].npcType == 1591 || npcs[i].npcType == 1618 || npcs[i].npcType == 1643 || npcs[i].npcType == 941 || npcs[i].npcType == 119 || npcs[i].npcType == 82 || npcs[i].npcType == 52 || npcs[i].npcType == 1612 || npcs[i].npcType == 117 || npcs[i].npcType == 1265 || npcs[i].npcType == 112 || npcs[i].npcType == 125) {
					c.getPA().addSkillXP((npcs[i].MaxHP * 10) * Config.SLAYER_EXPERIENCE, 18);
					c.pcPoints += 10;
					c.slayerTask = -1;
					c.sendMessage("You completed your MEDIUM slayer task. Please see a slayer master to get a new one.");
					c.sendMessage("You receive 10 Pandemic Points.");
				}
				if (npcs[i].npcType == 1624 || npcs[i].npcType == 1610 || npcs[i].npcType == 1592 || npcs[i].npcType == 1613 || npcs[i].npcType == 1615 || npcs[i].npcType == 55 || npcs[i].npcType == 84 || npcs[i].npcType == 49 || npcs[i].npcType == 1618 || npcs[i].npcType == 941 || npcs[i].npcType == 82 || npcs[i].npcType == 2783 || npcs[i].npcType == 1341) {
					c.getPA().addSkillXP((npcs[i].MaxHP * 12) * Config.SLAYER_EXPERIENCE, 18);
					c.pcPoints += 15;
					c.slayerTask = -1;
					c.sendMessage("You completed your HARD slayer task. Please see a slayer master to get a new one.");
					c.sendMessage("You receive 15 Pandemic Points.");
				}
				if (npcs[i].npcType == 1648 || npcs[i].npcType == 117 || npcs[i].npcType == 1265 || npcs[i].npcType == 90 || npcs[i].npcType == 103 || npcs[i].npcType == 78 || npcs[i].npcType == 119 || npcs[i].npcType == 18 || npcs[i].npcType == 101 || npcs[i].npcType == 1265 || npcs[i].npcType == 181) {
					c.getPA().addSkillXP((npcs[i].MaxHP * 8) * Config.SLAYER_EXPERIENCE, 18);
					c.pcPoints += 5;
					c.slayerTask = -1;
					c.sendMessage("You completed your EASY slayer task. Please see a slayer master to get a new one.");
					c.sendMessage("You receive 5 Pandemic Points");
				}
				if (npcs[i].npcType == 3847 || npcs[i].npcType == 8596 || npcs[i].npcType == 3943 || npcs[i].npcType == 8528 || npcs[i].npcType == 53 || npcs[i].npcType == 9947 || npcs[i].npcType == 10775 || npcs[i].npcType == 9467 || npcs[i].npcType == 9465 || npcs[i].npcType == 6260 || npcs[i].npcType == 2881 || npcs[i].npcType == 2882 || npcs[i].npcType == 2883 || npcs[i].npcType == 8133 || npcs[i].npcType == 8349 || npcs[i].npcType == 40 || npcs[i].npcType == 9463) {
					c.getPA().addSkillXP((npcs[i].MaxHP * 13) * Config.SLAYER_EXPERIENCE, 18);
					c.pcPoints += 25;
					c.slayerTask = -1;
					c.sendMessage("You completed your ELITE slayer task. Please see a slayer master to get a new one.");
					c.sendMessage("You receive 25 Pandemic Points.");
				}

			}
		}
	}
}
		
	

	
	/**
	 *	Resets players in combat
	 */
	
	public void resetPlayersInCombat(int i) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null)
				if (Server.playerHandler.players[j].underAttackBy2 == i)
					Server.playerHandler.players[j].underAttackBy2 = 0;
		}
	}
	
	
	/**
	* Npc Follow Player
	**/
	public int GetMove(int Place1,int Place2) { 
		if ((Place1 - Place2) == 0) {
            return 0;
		} else if ((Place1 - Place2) < 0) {
			return 1;
		} else if ((Place1 - Place2) > 0) {
			return -1;
		}
        	return 0;
   	 }
	
	public boolean followPlayer(int i) {
		switch (npcs[i].npcType) {
			case 2892:
			case 2586:
			case 2587:
			case 2588:
			case 2589:
			case 2894:
			return false;
		}
		return true;
	}
	
	public void followPlayer(int i, int playerId) {
		if (Server.playerHandler.players[playerId] == null) {
			return;
		}
		if (Server.playerHandler.players[playerId].respawnTimer > 0) {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
			return;
		}

		if (!followPlayer(i)) {
			npcs[i].facePlayer(playerId);
			return;
		}
		
				if (npcs[i].npcType == 2025) {
			return;
		}

		int playerX = Server.playerHandler.players[playerId].absX;
		int playerY = Server.playerHandler.players[playerId].absY;
		npcs[i].randomWalk = false;
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), playerX, playerY,
				distanceRequired(i)))
			return;
		if ((npcs[i].spawnedBy > 0)
				|| ((npcs[i].absX < npcs[i].makeX + Config.NPC_FOLLOW_DISTANCE)
						&& (npcs[i].absX > npcs[i].makeX
								- Config.NPC_FOLLOW_DISTANCE)
						&& (npcs[i].absY < npcs[i].makeY
								+ Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absY > npcs[i].makeY
						- Config.NPC_FOLLOW_DISTANCE))) {
			if (npcs[i].heightLevel == Server.playerHandler.players[playerId].heightLevel) {
				if (Server.playerHandler.players[playerId] != null
						&& npcs[i] != null) {
					if (playerY < npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerY > npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX < npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX > npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if (playerX == npcs[i].absX
							|| playerY == npcs[i].absY) {
						int o = Misc.random(3);
						switch (o) {
						case 0:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY + 1);
							break;

						case 1:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY - 1);
							break;

						case 2:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX + 1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;

						case 3:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX - 1);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;
						}
					}
					int x = (npcs[i].absX + npcs[i].moveX);
					int y = (npcs[i].absY + npcs[i].moveY);
					npcs[i].facePlayer(playerId);
					handleClipping(i);
                                                                                                           npcs[i].getNextNPCMovement(i);
					npcs[i].facePlayer(playerId);
					npcs[i].updateRequired = true;
				}
			}
		} else {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true;
			npcs[i].underAttack = false;
		}
	}

	
	/*public void followPlayer(int i, int playerId) {    OLD FOLLOWPLAYER NO CLIPPED NPCS
		if (Server.playerHandler.players[playerId] == null) {
			return;
		}
		if (Server.playerHandler.players[playerId].respawnTimer > 0) {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true; 
	      	npcs[i].underAttack = false;	
			return;
		}
		
		if (!followPlayer(i)) {
			npcs[i].facePlayer(playerId);
			return;
		}
		
if(!goodDistance(npcs[i].getX(), npcs[i].getY(), Server.playerHandler.players[playerId].getX(), Server.playerHandler.players[playerId].getY(), 1) && npcs[i].npcType == 10127 && npcs[i].attackType == 0) {
npcs[i].attackType = 2;
return;
}
		
		int playerX = Server.playerHandler.players[playerId].absX;
		int playerY = Server.playerHandler.players[playerId].absY;
		npcs[i].randomWalk = false;
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), playerX, playerY, distanceRequired(i)))
			return;

		

		if((npcs[i].spawnedBy > 0) || ((npcs[i].absX < npcs[i].makeX + Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absX > npcs[i].makeX - Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absY < npcs[i].makeY + Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absY > npcs[i].makeY - Config.NPC_FOLLOW_DISTANCE))) {
			if(npcs[i].heightLevel == Server.playerHandler.players[playerId].heightLevel) {
				if(Server.playerHandler.players[playerId] != null && npcs[i] != null) {
					if(playerY < npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if(playerY > npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if(playerX < npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if(playerX > npcs[i].absX)  {
						npcs[i].moveX = GetMove(npcs[i].absX, playerX);
						npcs[i].moveY = GetMove(npcs[i].absY, playerY);
					} else if(playerX == npcs[i].absX || playerY == npcs[i].absY) {
						int o = Misc.random(3);
						switch(o) {
							case 0:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY+2);
							break;
							
							case 1:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY-2);
							break;
							
							case 2:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX+2);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;
							
							case 3:
							npcs[i].moveX = GetMove(npcs[i].absX, playerX-2);
							npcs[i].moveY = GetMove(npcs[i].absY, playerY);
							break;
						}	
					}
					int x = (npcs[i].absX + npcs[i].moveX);
					int y = (npcs[i].absY + npcs[i].moveY);
					npcs[i].facePlayer(playerId);
					handleClipping(i);
                                                                                                           npcs[i].getNextNPCMovement(i);
					npcs[i].facePlayer(playerId);
			      	npcs[i].updateRequired = true;
				}	
			}
		} else {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true; 
		   	npcs[i].underAttack = false;	
		}
	}*/
	
public void follownpc(int i, int playerId) {
		if (npcs[playerId] == null) {
			return;
		}

		
		if (!followPlayer(i)) {
			npcs[i].facePlayer(playerId);
			return;
		}

if(!goodDistance(npcs[i].getX(), npcs[i].getY(), npcs[playerId].getX(), npcs[playerId].getY(), 1) && npcs[i].npcType == 10127 && npcs[i].attackType == 0) {
npcs[i].attackType = 2;
return;
}
		

		npcs[i].randomWalk = false;

		if (goodDistance(npcs[i].getX(), npcs[i].getY(), npcs[playerId].absX, npcs[playerId].absY, distanceRequired(i)))
			return;

		

		if((npcs[i].spawnedBy > 0) || ((npcs[i].absX < npcs[i].makeX + Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absX > npcs[i].makeX - Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absY < npcs[i].makeY + Config.NPC_FOLLOW_DISTANCE) && (npcs[i].absY > npcs[i].makeY - Config.NPC_FOLLOW_DISTANCE))) {
			if(npcs[i].heightLevel == npcs[playerId].heightLevel) {
				if(npcs[playerId] != null && npcs[i] != null) {
					if(npcs[playerId].absY < npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
					} else if(npcs[playerId].absY > npcs[i].absY) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
					} else if(npcs[playerId].absX < npcs[i].absX) {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
					} else if(npcs[playerId].absX > npcs[i].absX)  {
						npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
						npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
					} else if(npcs[playerId].absX == npcs[i].absX || npcs[playerId].absY == npcs[i].absY) {
						int o = Misc.random(3);
						switch(o) {
							case 0:
							npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
							npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY+1);
							break;
							
							case 1:
							npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX);
							npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY-1);
							break;
							
							case 2:
							npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX+1);
							npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
							break;
							
							case 3:
							npcs[i].moveX = GetMove(npcs[i].absX, npcs[playerId].absX-1);
							npcs[i].moveY = GetMove(npcs[i].absY, npcs[playerId].absY);
							break;
						}	
					}
					int x = (npcs[i].absX + npcs[i].moveX);
					int y = (npcs[i].absY + npcs[i].moveY);
					npcs[i].facePlayer(playerId);
					handleClipping(i);
                                                                                                           npcs[i].getNextNPCMovement(i);
					npcs[i].facePlayer(playerId);
			      	npcs[i].updateRequired = true;
				}	
			}
		} else {
			npcs[i].facePlayer(0);
			npcs[i].randomWalk = true; 
		   	npcs[i].underAttack = false;	
		}
	}
	
	public void handleClipping(int i) {
		NPC npc = npcs[i];
			if(npc.moveX == 1 && npc.moveY == 1) {
					if((Region.getClipping(npc.absX + 1, npc.absY + 1, npc.heightLevel) & 0x12801e0) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
								npc.moveY = 1;
							else 
								npc.moveX = 1; 				
							}
					} else if(npc.moveX == -1 && npc.moveY == -1) {
						if((Region.getClipping(npc.absX - 1, npc.absY - 1, npc.heightLevel) & 0x128010e) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
								npc.moveY = -1;
							else
								npc.moveX = -1; 		
					}
					} else if(npc.moveX == 1 && npc.moveY == -1) {
							if((Region.getClipping(npc.absX + 1, npc.absY - 1, npc.heightLevel) & 0x1280183) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) == 0)
								npc.moveY = -1;
							else
								npc.moveX = 1; 
							}
					} else if(npc.moveX == -1 && npc.moveY == 1) {
						if((Region.getClipping(npc.absX - 1, npc.absY + 1, npc.heightLevel) & 0x128013) != 0)  {
							npc.moveX = 0; npc.moveY = 0;
							if((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) == 0)
								npc.moveY = 1;
							else
								npc.moveX = -1; 
										}
					} //Checking Diagonal movement. 
					
			if (npc.moveY == -1 ) {
				if ((Region.getClipping(npc.absX, npc.absY - 1, npc.heightLevel) & 0x1280102) != 0)
                    npc.moveY = 0;
			} else if( npc.moveY == 1) {
				if((Region.getClipping(npc.absX, npc.absY + 1, npc.heightLevel) & 0x1280120) != 0)
					npc.moveY = 0;
			} //Checking Y movement.
			if(npc.moveX == 1) {
				if((Region.getClipping(npc.absX + 1, npc.absY, npc.heightLevel) & 0x1280180) != 0) 
					npc.moveX = 0;
				} else if(npc.moveX == -1) {
				 if((Region.getClipping(npc.absX - 1, npc.absY, npc.heightLevel) & 0x1280108) != 0)
					npc.moveX = 0;
			} //Checking X movement.
	}

	private Client v;
	public NPCHandler(Client Client) {
		this.v = Client;
	}
	
	/**
	* load spell
	**/
	public void loadSpell2(int i) {
		npcs[i].attackType = 3;
		int random = Misc.random(3);
		if (random == 0) {
			npcs[i].projectileId = 393; //red
			npcs[i].endGfx = 430;
		} else if (random == 1) {
			npcs[i].projectileId = 394; //green
			npcs[i].endGfx = 429;
		} else if (random == 2) {
			npcs[i].projectileId = 395; //white
			npcs[i].endGfx = 431;
		} else if (random == 3) {
			npcs[i].projectileId = 396; //blue
			npcs[i].endGfx = 428;
		}
	}
	
	public void loadSpell(int i) {
		Client c = (Client) Server.playerHandler.players[npcs[i].killerId];

		switch(npcs[i].npcType) {
		
		case 2586:
		    int C = 0;
			C = Misc.random(1);
		    if (C == 0) {
			npcs[i].projectileId = 393; //red
			npcs[i].attackType = 3;
		    } else if (C == 1) {
			npcs[i].projectileId = 394; //green
			npcs[i].attackType = 2;
			}
		break;
		case 291:
  int C5 = 0;
			C5 = Misc.random(1);
		    if (C5 == 0) {
			npcs[i].projectileId = 393; //red
			npcs[i].attackType = 3;
		    } else if (C5 == 1) {
			npcs[i].projectileId = 394; //green
			npcs[i].attackType = 2;
			}
break;
		case 2587:
			int C2 = 0;
			C2 = Misc.random(1); 
		    if (C2 == 0) {
			npcs[i].projectileId = 393; //red
			npcs[i].attackType = 3;
		    } else if (C2 == 1) {
			npcs[i].projectileId = 394; //green
			npcs[i].attackType = 2;
			}
		break;
		case 2588:
			int C3 = 0;
			C3 = Misc.random(1);
		    if (C3 == 0) {
			npcs[i].projectileId = 393; //red
			npcs[i].attackType = 3;
		    } else if (C3 == 1) {
			npcs[i].projectileId = 394; //green
			npcs[i].attackType = 2;
			}
		break;
		
		case 2589:
			int C4 = 0;
			C4 = Misc.random(1);
		    if (C4 == 0) {
			npcs[i].projectileId = 393; //red
			npcs[i].attackType = 3;
		    } else if (C4 == 1) {
			npcs[i].projectileId = 394; //green
			npcs[i].attackType = 2;
			}
		break;
						case 2636:
				    int nexRandom = 0;
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
					nexRandom = 1;
				    } else {
					nexRandom = Misc.random(5);
                                    }
				    if (nexRandom == 0) {//Fumus/Cruor/Glacies/Umbra don't fail me
						npcs[i].attackType = 5;
						npcs[i].endGfx = -1;
						npcs[i].projectileId = -1;//
						c.gfx0(1968);
				       		 	NexTalk2(c);
			    	        		npcs[i].forceChat(""+NexTalk+"");
				    } else if (nexRandom == 1) {//Ice barrage from nex here
				        npcs[i].projectileId = -1; //mage
				        npcs[i].endGfx = -1;
				        npcs[i].attackType = 4;
				        c.freezeTimer = 15;
						c.gfx0(369);
	                				NexTalk1(c);
	              		 			npcs[i].forceChat(""+NexTalk+"");
	              		 			c.sendMessage("You have been Frozen!");
			            } else if (nexRandom == 2) {
							npcs[i].attackType = 3;
							npcs[i].endGfx = -1;
							npcs[i].projectileId = -1;
							//No sounds or text here - Regular attack
			            } else if (nexRandom == 3) {
							npcs[i].attackType = 2;
							npcs[i].gfx100(1978);
							npcs[i].projectileId = -1;
							c.gfx0(1982);
			            			c.getPA().sendMp3("Zaros");
									NexTalk5(c);
			    	        		npcs[i].forceChat(""+NexTalk+"");
			    	    } else if (nexRandom == 4) {
			    			npcs[i].attackType = 1;
			    			npcs[i].endGfx = -1;
			    			npcs[i].projectileId = 1846;
			    			c.gfx0(1847);
			    	        		NexTalk3(c);
			    	        		npcs[i].forceChat(""+NexTalk+"");
			    	    } else if (nexRandom == 5) {
			    			npcs[i].attackType = 0;
			    			npcs[i].gfx100(1942);
			    			npcs[i].projectileId = -1;
			    			c.gfx0(1932);	
			    		    		NexTalk4(c);
			    	     		   	npcs[i].forceChat(""+NexTalk+"");
			            }
			break;
				case 5666: // barrelschest
			random = Misc.random(1);
			if (random == 0) {
				npcs[i].attackType = 1;
			} else {
				npcs[i].attackType = 2;

			}
			break;
			case 2892:
			    npcs[i].projectileId = 94;
			    npcs[i].attackType = 2;
			    npcs[i].endGfx = 95;
			break;
		
case 98:
				        npcs[i].attackType = 0; // range
				        npcs[i].endGfx = 281;
				        npcs[i].projectileId = 473;
					npcs[i].requestAnimation(10816, 0);
					break;
					
		case 96:			
		npcs[i].projectileId = 140; //mage
						npcs[i].requestAnimation(10819, 0);
				        npcs[i].endGfx = 141;
				        npcs[i].attackType = 2;
						break;	

case 94:
	npcs[i].projectileId = 285; //mage
						npcs[i].requestAnimation(10819, 0);
				        npcs[i].endGfx = 286;
				        npcs[i].attackType = 2;
						break;	
case 99:
npcs[i].projectileId = 500; //mage
						npcs[i].requestAnimation(10819, 0);
				        npcs[i].endGfx = 501;
				        npcs[i].attackType = 2;
						break;	

case 93:
	npcs[i].projectileId = 290; //mage
						npcs[i].requestAnimation(10819, 0);
				        npcs[i].endGfx = 291;
				        npcs[i].attackType = 2;
						break;						
					
		case 97:
		 npcs[i].projectileId = 280; //mage
						npcs[i].requestAnimation(10819, 0);
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
						break;
		case 100:
		 npcs[i].projectileId = 280; //mage
						npcs[i].requestAnimation(10819, 0);
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
						break;
		
			//case 9947:
			case 10400:
					int k4 = 0;
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
				        k4 = Misc.random(2);
				    }else{ 
					k4 = Misc.random(1);
                                    }
				    if (k4 == 0) {					
				        npcs[i].projectileId = 280; //mage
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
					c.freezeTimer = 3;
					c.gfx0(369);
					c.sendMessage("You have been Frozen!");
			            } else if (k4 == 1) {
				        npcs[i].attackType = 1; // range
				        npcs[i].endGfx = 281;
				        npcs[i].projectileId = 473;
			            } else if (k4 == 2) {
				        npcs[i].attackType = 0; // melee
				        npcs[i].projectileId = -1;
					npcs[i].forceChat("Feel my power!");
					c.sendMessage("You suddenly feel alot weaker!");
			            }
			break;
						case 10040:
				    int k1 = 0;
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
				        k1 = Misc.random(2);
				    }else{ 
					k1 = Misc.random(1);
                                    }
				    if (k1 == 0) {					
				        npcs[i].projectileId = 280; //mage
						npcs[i].requestAnimation(10819, 0);
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
					npcs[i].forceChat("Take that!");
					c.freezeTimer = 10;
					c.gfx0(369);
					c.sendMessage("You have been Frozen!");
					npcs[i].requestAnimation(10817, 0);
			            } else if (k1 == 1) {
				        npcs[i].attackType = 0; // range
				        npcs[i].endGfx = 281;
				        npcs[i].projectileId = 473;
					npcs[i].forceChat("How dare you!");
					npcs[i].requestAnimation(10816, 0);
			            } else if (k1 == 2) {
				        npcs[i].attackType = 0; // melee
				        npcs[i].projectileId = -1;
					//npcs[i].forceChat("You will all perish by my WRATH!");
					//c.sendMessage("You have been Infected with Deadly Poisen");
			            }
			break;
			
			case 4477:
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
				        k1 = Misc.random(2);
				    }else{ 
					k1 = Misc.random(1);
                                    }
				    if (k1 == 0) {					
				        npcs[i].projectileId = 280; //mage
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
					npcs[i].forceChat("Muahahahah!");
					c.freezeTimer = 15;
					c.gfx0(369);
					c.sendMessage("You have been Frozen!");
			            } else if (k1 == 1) {
				        npcs[i].attackType = 0; // range
				        npcs[i].endGfx = 281;
				        npcs[i].projectileId = 473;
					npcs[i].forceChat("Leave my dungeon!");
			            } else if (k1 == 2) {
				        npcs[i].attackType = 0; // melee
				        npcs[i].projectileId = -1;
					npcs[i].forceChat("You will all perish by my WRATH!");
					//c.sendMessage("You have been Infected with Deadly Poisen");
			            }
			break;
			
									case 9947:
				    int o1 = 0;
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
				        o1 = Misc.random(2);
				    }else{ 
					k1 = Misc.random(1);
                                    }
				    if (o1 == 0) {	
				if(npcs[i].setIcon > -1) {
					c.CIcon = npcs[i].setIcon;
					npcs[i].setIcon = -1; 
					
				}					
				        npcs[i].projectileId = 280; //mage
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
					//npcs[i].forceChat("Muahahahah!");
					c.freezeTimer = 15;
					c.gfx0(369);

								c.CIcon = 2;
				c.getPA().refreshSkill(3);
				c.updateRequired = true;
				//c.CIcon = npcs[i].setIcon;
			            }
			break;
			
			case 1158:
			    for (int j = 0; j < Server.playerHandler.players.length; j++) {
			        if (Server.playerHandler.players[j] != null) {
				    int kq1 = 0;
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
				        kq1 = Misc.random(2);
				    }else{ 
					kq1 = Misc.random(1);
                                    }
				    if (kq1 == 0) {					
				        npcs[i].projectileId = 280; //mage
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
			            } else if (kq1 == 1) {
				        npcs[i].attackType = 1; // range
				        npcs[i].endGfx = 281;
				        npcs[i].projectileId = 473;
			            /*} else if (kq1 == 2) {
				        npcs[i].attackType = 0; // melee
				        npcs[i].projectileId = -1;*/
			            }
				}
			    }
			break;
			
						case 1160:
			    for (int j = 0; j < Server.playerHandler.players.length; j++) {
			        if (Server.playerHandler.players[j] != null) {
				    int kq1 = 0;
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
				        kq1 = Misc.random(2);
				    }else{ 
					kq1 = Misc.random(1);
                                    }
				    if (kq1 == 0) {					
				        npcs[i].projectileId = 280; //mage
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
			            } else if (kq1 == 1) {
				        npcs[i].attackType = 1; // range
				        npcs[i].endGfx = 281;
				        npcs[i].projectileId = 473;
			         }
				}
			    }
			break;
                        
			case 10039:
					int k13 = 0;
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
				        k4 = Misc.random(2);
				    }else{ 
					k4 = Misc.random(1);
                                    }
				    if (k4 == 0) {					
				        npcs[i].projectileId = 280; //mage
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
					npcs[i].forceChat("BE FROZEN BY MY POWER!");
					c.freezeTimer = 15;
					c.gfx0(369);
					c.sendMessage("You have been Frozen!");
			            } else if (k4 == 1) {
				        npcs[i].attackType = 1; // range
				        npcs[i].endGfx = 281;
				        npcs[i].projectileId = 473;
					npcs[i].forceChat("AAAAAAAAAAAAAAAARHHHH?!");
			            } else if (k4 == 2) {
				        npcs[i].attackType = 0; // melee
				        npcs[i].projectileId = -1;
					npcs[i].forceChat("LEAVE MY FLAMING PRISON!!");
					c.sendMessage("You suddenly feel alot weaker!");
			            }
			break;
						case 3663:
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
				        k4 = Misc.random(2);
				    }else{ 
					k4 = Misc.random(1);
                                    }
				    if (k4 == 0) {					
				        npcs[i].projectileId = 280; //mage
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
					npcs[i].forceChat("BE FROZEN BY MY POWER NOOB!");
					c.freezeTimer = 10;
					c.gfx0(369);
					c.sendMessage("You have been Frozen!");
			            } else if (k4 == 1) {
				        npcs[i].attackType = 1; // range
				        npcs[i].endGfx = 281;
				        npcs[i].projectileId = 473;
					npcs[i].forceChat("You made me mad! Now be my bitch!");
			            } else if (k4 == 2) {
				        npcs[i].attackType = 0; // melee
				        npcs[i].projectileId = -1;
					npcs[i].forceChat("I'ma beat the shit outta you nubby!");
					c.sendMessage("You suddenly feel alot weaker!");
			            }
			break;
			case 3847:
			random = Misc.random(2);
			        if (random == 0 || random == 1){
					npcs[i].attackType = 0;
				}else {
			        	c.gfx0(369);
			        	npcs[i].forceChat("Be Frozen by the Tides!");
					c.freezeTimer = 15;
					npcs[i].attackType = 2;
                               }
			break;

			case 8528:
			random = Misc.random(1);
			        if (random == 1){
					npcs[i].attackType = 0;
				}else {
			        	npcs[i].forceChat("You will never defeat me!");
					npcs[i].attackType = 1;
                               }
			break;
			case 2030:
				random = Misc.random(1);
		        if (random == 1){
			        	npcs[i].forceChat("You may have defeated my brothers, but not me!");
		        } else {
		        	npcs[i].forceChat("Now feel the wrath of all brothers connected together!");
					if (npcs[i].HP > 45000 && npcs[i].HP < 100000) {
						npcs[i].HP += 2500;
			        	c.sendMessage("<col=800000000>The Barrows Brother completely drains your prayer to regain health!");
						c.playerLevel[5] = -1;
						c.getPA().refreshSkill(5);
						}
						if (npcs[i].HP > 10000 && npcs[i].HP < 45000) {
						npcs[i].HP += 7500;
			        	c.sendMessage("<col=800000000>The Barrows Brother completely drains your prayer to regain health!");
						c.playerLevel[5] = -1;
						c.getPA().refreshSkill(5);
						}
						if (npcs[i].HP > 1500 && npcs[i].HP < 10000) {
						npcs[i].HP += 10000;
			        	c.sendMessage("<col=800000000>The Barrows Brother completely drains your prayer to regain health!");
						c.playerLevel[5] = -1;
						c.getPA().refreshSkill(5);
						}
		        }
			break;
			
			case 6692:
			random = Misc.random(1);
			        if (random == 1){
					npcs[i].attackType = 0;
				}else {
					npcs[i].attackType = 1;
                               }
			break;
                        
                        case 3943:
			random = Misc.random(1);
			        if (random == 1){
					npcs[i].attackType = 0;
				}else {
			        	npcs[i].forceChat("Sssss Leave my realm now!");
					npcs[i].attackType = 1;
                               }
			break;
			
                        case 6713:
			random = Misc.random(2);
			        if (random == 0 || random == 1){
					npcs[i].attackType = 0;
				}else {
			        	c.gfx0(369);
					c.freezeTimer = 20;
                                        c.sendMessage("You have been Frozen!");
					npcs[i].attackType = 2;
                               }
			break;

			case 10775:
			    int r5 = 0;
			    if (goodDistance(npcs[i].absX, npcs[i].absY, Server.playerHandler.players[npcs[i].killerId].absX, Server.playerHandler.players[npcs[i].killerId].absY, 2)){
				r5 = Misc.random(5);
			    }else{
				r5 = Misc.random(3);
                            }
			    if (r5 == 0) {
				npcs[i].projectileId = 393; //red
				npcs[i].attackType = 3;
			    } else if (r5 == 1) {
				npcs[i].projectileId = 394; //green
				npcs[i].attackType = 2;
			    } else if (r5 == 2) {
				npcs[i].projectileId = 395; //white
				npcs[i].attackType = 2;
				if(c.freezeTimer <= 0) {
					c.freezeTimer = 19;
					c.sendMessage("You have been Frozen!");
				}
			    } else if (r5 == 3) {
				npcs[i].projectileId = 396; //blue
				npcs[i].attackType = 2;
			    } else if (r5 == 4) {
				npcs[i].projectileId = -1; //melee
				npcs[i].attackType = 0;	
			    } else if (r5 == 5) {
				npcs[i].projectileId = -1; //melee
				npcs[i].attackType = 0;	
			    }	
			break;
                        case 6998:
			    int r4 = 0;
			    if (goodDistance(npcs[i].absX, npcs[i].absY, Server.playerHandler.players[npcs[i].killerId].absX, Server.playerHandler.players[npcs[i].killerId].absY, 2)){
				r4 = Misc.random(5);
			    }else{
				r4 = Misc.random(3);
                            }
			    if (r4 == 0) {
				npcs[i].projectileId = 393; //red
				npcs[i].attackType = 3;
			    } else if (r4 == 1) {
				npcs[i].projectileId = 394; //green
				npcs[i].attackType = 2;
				
			    } else if (r4 == 2) {
				npcs[i].projectileId = 395; //white
				npcs[i].attackType = 2;
				if(c.freezeTimer <= 0) {
					c.freezeTimer = 19;
					c.sendMessage("You have been Frozen!");
				}
			    } else if (r4 == 3) {
				npcs[i].projectileId = 396; //blue
				npcs[i].attackType = 2;
			    } else if (r4 == 4) {
				npcs[i].projectileId = -1; //melee
				npcs[i].attackType = 0;	
			    } else if (r4 == 5) {
				npcs[i].projectileId = -1; //melee
				npcs[i].attackType = 0;	
			    }	
			break;
			case 8596:
			    for (int j = 0; j < Server.playerHandler.players.length; j++) {
			        if (Server.playerHandler.players[j] != null) {
				    int kq1 = 0;
				    if (goodDistance(npcs[i].absX, npcs[i].absY, c.absX, c.absY, 2)){
				        kq1 = Misc.random(2);
				    }else{ 
					kq1 = Misc.random(1);
                                    }
				    if (kq1 == 0) {					
				        npcs[i].projectileId = 280; //mage
				        npcs[i].endGfx = 281;
				        npcs[i].attackType = 2;
					npcs[i].forceChat("Aheheh, Why Do you Attempt?");
			            } else if (kq1 == 1) {
				        npcs[i].attackType = 1; // range
				        npcs[i].endGfx = 281;
				        npcs[i].projectileId = 473;
					npcs[i].forceChat("I will crush you puny Bugs!");
			            } else if (kq1 == 2) {
					npcs[i].forceChat("Feel my POWER!");
				        npcs[i].attackType = 0; // melee
				        npcs[i].projectileId = -1;
			            }
				}
			    }
			break;
			

			case 2894:
			npcs[i].projectileId = 298;
			npcs[i].attackType = 2;
			
			break;
			case 9752:
			random = Misc.random(2);
			if (random == 0 || random == 1) {
				npcs[i].attackType = 0;
				npcs[i].endGfx = 1211;	//endGfx
			} else {
				npcs[i].attackType = 2;
				npcs[i].endGfx = 1625;	//endGfx
			}
			break;
			case 1219:
				if (npcs[i].HP > 1500 && npcs[i].HP < 50000) {
					npcs[i].HP += 250;
					npcs[i].endGfx = 855;	//endGfx
					}
				break;
			case 10156:
			case 10149:
			random = Misc.random(2);
			if (random == 0 || random == 1) {
				npcs[i].attackType = 0;
				npcs[i].endGfx = 1625;	//endGfx
				if (npcs[i].HP > 45000 && npcs[i].HP < 75000) {
				npcs[i].HP += 5000;
				}
				if (npcs[i].HP > 10000 && npcs[i].HP < 45000) {
				npcs[i].HP += 10000;
				}
				if (npcs[i].HP > 1500 && npcs[i].HP < 10000) {
				npcs[i].HP += 15000;
				}
			} else {
				npcs[i].attackType = 4;
				npcs[i].projectileId = -1; //mage
		        npcs[i].attackType = 4;
		        c.freezeTimer = 15;
				c.gfx0(369);
          		npcs[i].forceChat("You will feel my icy wrath!");
          		c.sendMessage("You have been Frozen!");
				npcs[i].endGfx = 1625;	//endGfx
				if (npcs[i].HP > 45000 && npcs[i].HP < 75000) {
				npcs[i].HP += 5000;
				}
				if (npcs[i].HP > 10000 && npcs[i].HP < 45000) {
				npcs[i].HP += 10000;
				}
				if (npcs[i].HP > 1500 && npcs[i].HP < 10000) {
				npcs[i].HP += 15000;
				}
			}
			break;
			case 8996: //voragoz
			case 8995: //voragoz
			case 8997:
			case 8998:
			case 8999:
			random = Misc.random(2);
			if (random == 0 || random == 1) {
				npcs[i].attackType = 0;
			npcs[i].projectileId = 2580;	//Projectile
			} else {
				npcs[i].attackType = 2;
				npcs[i].endGfx = 2599;	//endGfx
			}
			break;
			case 1812:
				random = Misc.random(2);
				if (random == 2 || random == 1) {
					npcs[i].attackType = 0;
				npcs[i].projectileId = 362;	//Projectile
				} else {
					npcs[i].attackType = 0;
					npcs[i].projectileId = 386;	//Projectile
					npcs[i].endGfx = 383;	//endGfx
				}
				break;
			case 517:
			case 518:
			random = Misc.random(2);
			if (random == 0 || random == 1) {
				npcs[i].attackType = 0;
			npcs[i].projectileId = 2592;	//Projectile
			} else {
				npcs[i].attackType = 2;
			npcs[i].projectileId = 2592;	//Projectile
			}
			break;
			case 8988:
			random = Misc.random(2);
			if (random == 0 || random == 1) {
				npcs[i].attackType = 0;
					npcs[i].endGfx = 1194;
			} else {
				npcs[i].attackType = 2;
					npcs[i].endGfx = 1196;
			}
			break;
			case 4972:
			//npcs[i].endGfx = 2039;
			c.gfx0(1197);
			if (random == 0 || random == 1) {
				npcs[i].attackType = 0;
					npcs[i].endGfx = 1207;
			} else {
				if (c.playerLevel[5] > 0) {
					c.playerLevel[5] -= 7;
					c.sendMessage("<col=255>The Giant Roc has drained your prayer points!");
					c.getPA().refreshSkill(5);
			}
			}
			break;
			case 7480:
			case 7479:
			random = Misc.random(2);
			if (random == 0 || random == 1 || random == 2) {
				npcs[i].attackType = 0;
					npcs[i].endGfx = 1207;
			} else {
				npcs[i].attackType = 10;
					npcs[i].endGfx = 1207;
			}
			break;

			case 11111:
			npcs[i].endGfx = 1977;			
			break;
			case 11120:
			if (random == 0) {
			npcs[i].projectileId = 2594;	//Projectile
			} else {
			npcs[i].projectileId = 2594;	//Projectile
			}			
			break;
			


                        case 6203:
				random = Misc.random(2);
				if (random == 0 || random == 1) {
					npcs[i].attackType = 0;
					npcs[i].projectileId = -1;
				} else {
					npcs[i].attackType = 2;
					npcs[i].projectileId = 1211;
				}
			break;
			case 6206:				
					npcs[i].attackType = 1;
					npcs[i].projectileId = 1209;	
			break;
			case 6208:				
					npcs[i].attackType = 2;
					npcs[i].projectileId = 1213;	
			break;
			case 6256:				
					npcs[i].attackType = 1;
					npcs[i].projectileId = 16;	
			break;
			case 6220:				
					npcs[i].attackType = 1;
					npcs[i].projectileId = 17;	
			break;
			
			
			case 53:
			case 54:
			case 55:
			case 941:
			case 4677:
			case 1590:
			case 1591:
			case 1592:
			int r6 = 0;	
			
			
			r6 = Misc.random(2);
			if (r6 == 0) {
				npcs[i].projectileId = 393; //fire breath this time.
				npcs[i].attackType = 3;
				npcs[i].setIcon = 2;
				
			} else if (r6 == 1) {
				npcs[i].projectileId = -1; //melee
				npcs[i].attackType = 0;
				npcs[i].setIcon = 0;
				
			} else if (r6 == 2) {
				npcs[i].projectileId = -1; //melee
				npcs[i].attackType = 0;	
				npcs[i].setIcon = 0;
			}	
				
			break;
			//arma npcs
			case 6227://kilisa
				npcs[i].attackType = 0;
			break;
			case 6225://geerin
			case 6233:
			case 6230:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1190;
			break;
			case 6239:
			case 41:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1191;
			break;
			case 6232:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1191;
			break;
			case 6276:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1195;
			break;
			case 6223://skree
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1199;
			break;
			case 6257://saradomin strike
				npcs[i].attackType = 2;
					npcs[i].endGfx = 76;
			break;
			case 6221://zamorak strike
				npcs[i].attackType = 2;
			npcs[i].endGfx = 78;
			break;
			case 6231://arma
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1199;
			break;
			case 6222://kree
			case 4003:
				random = Misc.random(1);
				npcs[i].attackType = 1 + random;
				if (npcs[i].attackType == 1) {
					npcs[i].projectileId = 1197;				
				} else {
					npcs[i].attackType = 2;
					npcs[i].projectileId = 1198;
				}	
			break;
			//sara npcs
			case 6247: //sara
			case 4006:
				random = Misc.random(1);
				if (random == 0) {
					npcs[i].attackType = 2;
					npcs[i].endGfx = 1224;
					npcs[i].projectileId = -1;
				} else if (random == 1)
					npcs[i].attackType = 0;
			break;
			case 6248: //star
				npcs[i].attackType = 0;
			break;
			case 6250: //growler
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1203;
			break;
			case 6252: //bree
				npcs[i].attackType = 1;
				npcs[i].projectileId = 9;
			break;
			//bandos npcs
			case 6260://bandos
				random = Misc.random(2);
				if (random == 0 || random == 1) {
					npcs[i].attackType = 0;
				} else {
					npcs[i].attackType = 1;
					//npcs[i].projectileId = 1200;
				}
			break;
			case 9463:
				random = Misc.random(2);
				if (random == 0 || random == 1)
					npcs[i].attackType = 0;
				else {
				c.freezeTimer = 20;
				npcs[i].attackType = 2;
				c.sendMessage("The Strykewyrm Used His Ice Bite And Froze You!");
				}
				break;
			case 9467:
				random = Misc.random(2);
				if (random == 0 || random == 1)
					npcs[i].attackType = 0;
				else {
					npcs[i].attackType = 2;
					c.sendMessage("The strykeworm uses his special bite, it hurts!");
				}
			case 9465:
				random = Misc.random(2);
				if (random == 0 || random == 1)
					npcs[i].attackType = 0;
				else {
				c.playerLevel[5] -= (c.playerLevel[5] * .22);
				npcs[i].attackType = 2;
				c.sendMessage("The Strykewyrm Drained Your Prayer Points!");
				c.getPA().refreshSkill(5);
				}
				break;
							case 10530:
				random = Misc.random(2);
				if (random == 0 || random == 1)
					npcs[i].attackType = 0;
				else {
				c.playerLevel[5] -= (c.playerLevel[5] * .22);
				npcs[i].attackType = 2;
				c.sendMessage("The Warrior Drained Your Prayer Points!");
				c.getPA().refreshSkill(5);
				}
				break;							
			case 795:
				random = Misc.random(2);
				if (random == 0 || random == 1)
					npcs[i].attackType = 0;
				else {
			c.gfx0(369);
			npcs[i].forceChat("Muhahaha");
					c.freezeTimer = 15;
					npcs[i].attackType = 2;
				}
			break;
			case 3495:
				random = Misc.random(2);
				if (random == 0 || random == 1) 
					npcs[i].attackType = 0;
				 else {
			c.gfx0(369);
			npcs[i].forceChat("Semolina-Go!");
					c.freezeTimer = 10;
					npcs[i].attackType = 2;
				}
			break;
			case 3493:
				random = Misc.random(2);
				if (random == 0 || random == 1) {
					npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
			} else {
				npcs[i].gfx100(129);
				npcs[i].projectileId = 130;
				npcs[i].endGfx = 131;
					npcs[i].attackType = 2;
				}
			break;
			case 3496:
				random = Misc.random(2);
				if (random == 0 || random == 1) {
					npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
			} else {
			npcs[i].forceChat("Hssssssssssss");
				npcs[i].gfx100(550);
				npcs[i].projectileId = 551;
				npcs[i].endGfx = 552;
					npcs[i].attackType = 2;
				}
			break;
			case 3491:
				npcs[i].projectileId = 106;
					npcs[i].attackType = 2;
			break;
			case 6261://strongstack
				npcs[i].attackType = 0;
			break;
			case 6263://steelwill
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1203;
			break;
			case 6265://grimspike
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1206;
			break;
			case 2025:
			case 4022:
			npcs[i].attackType = 2;
			int r = Misc.random(3);
			if(r == 0) {
				npcs[i].gfx100(158);
				npcs[i].projectileId = 159;
				npcs[i].endGfx = 160;
			}
			if(r == 1) {
				npcs[i].gfx100(161);
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 163;
			}
			if(r == 2) {
				npcs[i].gfx100(164);
				npcs[i].projectileId = 165;
				npcs[i].endGfx = 166;
			}
			if(r == 3) {
				npcs[i].gfx100(155);
				npcs[i].projectileId = 156;
			}
			break;
			case 2881://supreme
			case 4007:
			case 4008:
			case 4009:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 298;
			break;
			
			case 2882://prime
				npcs[i].attackType = 2;
				npcs[i].projectileId = 162;
				npcs[i].endGfx = 477;
			break;
			
			case 2028:
			case 4024:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 27;
			break;
			
			case 3200:
			int r2 = Misc.random(1);
			if (r2 == 0) {
				npcs[i].attackType = 1;
				npcs[i].gfx100(550);
				npcs[i].projectileId = 551;
				npcs[i].endGfx = 552;
			} else {
				npcs[i].attackType = 2;
				npcs[i].gfx100(553);
				npcs[i].projectileId = 554;
				npcs[i].endGfx = 555;
			}
			break;
			case 2745:
				int r3 = 0;
				if (goodDistance(npcs[i].absX, npcs[i].absY, Server.playerHandler.players[npcs[i].spawnedBy].absX, Server.playerHandler.players[npcs[i].spawnedBy].absY, 1))
					r3 = Misc.random(2);
				else
					r3 = Misc.random(1);
				if (r3 == 0) {
					npcs[i].attackType = 2;
					if (c.jadMiniKilled <= 0) {
					spawnMiniJad(c, COORDS1); 
					npcs[i].forceChat("Kill him, my minions!");
					c.sendMessage("<col=255>The Mini TokTz-Jad Minions Spawn Right Besides You!");
					c.jadMiniKilled = 3;
					}
					if (c.jadMiniKilled > 0) {
						c.jadMiniKilled -= 1;
					}
					npcs[i].endGfx = 157;
					npcs[i].projectileId = 1627;
				} else if (r3 == 1) {
					c.playerLevel[5] -= (c.playerLevel[5] * .50);
					c.sendMessage("The mighty TzTok-Jad drains half your prayer!");
					c.getPA().refreshSkill(5);
					npcs[i].attackType = 1;
					npcs[i].endGfx = 451;
					npcs[i].gfx100(1625);
					npcs[i].projectileId = -1;
				} else if (r3 == 2) {
					npcs[i].attackType = 0;
					npcs[i].projectileId = -1;
				}			
				break;
			case 4020:
			int rq3 = 0;
			if (goodDistance(npcs[i].absX, npcs[i].absY, Server.playerHandler.players[npcs[i].spawnedBy].absX, Server.playerHandler.players[npcs[i].spawnedBy].absY, 1))
				rq3 = Misc.random(2);
			else
				rq3 = Misc.random(1);
			if (rq3 == 0) {
				npcs[i].attackType = 2;
			} else if (rq3 == 1) {
				c.playerLevel[5] -= (c.playerLevel[5] * .10);
				c.sendMessage("The Mini TzTok-Jad drains 10% of your prayer!");
				c.getPA().refreshSkill(5);
				npcs[i].attackType = 1;
				npcs[i].gfx100(1625);
				npcs[i].projectileId = -1;
			} else if (rq3 == 2) {
				npcs[i].attackType = 0;
				npcs[i].projectileId = -1;
			}			
			break;
			case 10128:
			case 40:
			case 8133:
			case 4004:
			if (goodDistance(npcs[i].absX, npcs[i].absY, Server.playerHandler.players[npcs[i].killerId].absX, Server.playerHandler.players[npcs[i].killerId].absY, 3))
				r3 = Misc.random(2);
			else
				r3 = Misc.random(1);
			if (r3 == 0) {
				npcs[i].attackType = 2;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = 1828;
			} else if (r3 == 1) {
				npcs[i].attackType = 1;
				npcs[i].endGfx = -1;
				npcs[i].projectileId = 1839;
			} else if (r3 == 2) {
				npcs[i].attackType = 0;
				npcs[i].gfx100(1834);
				npcs[i].projectileId = -1;
			}			
			break;
			case 3102:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1839;
			break;
			case 3103:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 1828;
			break;
			case 8349: case 8350: case 8351:
			if (goodDistance(npcs[i].absX, npcs[i].absY, Server.playerHandler.players[npcs[i].killerId].absX, Server.playerHandler.players[npcs[i].killerId].absY, 2))
				r3 = Misc.random(2);
			else
				r3 = Misc.random(1);
			if (r3 == 0) {
				npcs[i].attackType = 2;
				npcs[i].gfx100(1885);
				npcs[i].projectileId = 1884;
			} else if (r3 == 1) {
				npcs[i].attackType = 1;
				npcs[i].projectileId = 1889;
			} else if (r3 == 2) {
				npcs[i].attackType = 0;
				npcs[i].gfx100(1886);
				npcs[i].projectileId = -1;
			}			
			break;
			case 2743:
				npcs[i].attackType = 2;
				npcs[i].projectileId = 445;
				npcs[i].endGfx = 446;
			break;
			
			case 2631:
				npcs[i].attackType = 1;
				npcs[i].projectileId = 443;
			break;
		}
	}
		
	/**
	* Distanced required to attack -> uhm.. dont think so its npc sizelol
	**/	
	public int distanceRequired(int i) {
		switch(npcs[i].npcType) {
			case 2025:
			case 4022:
			case 2028:
			case 4024:
			return 6;
			case 53:
			case 742: // maybe this?
			case 54:
			case 55:
			case 941:
			case 1590:
			case 4677:
			case 1591:
			case 1592:
			return 2;
			case 1158:
			case 1160:
				return 8;
			case 2586:
			case 2587:
			case 2588:
			case 2589:
				return 20;
			case 6247:
			case 4006:
			return 2;
			case 2881://dag kings
			case 4007:
			case 4008:
			case 4009:
			case 2882:
			case 3200://chaos ele
			case 2743:
			case 2631:
			case 2745:
			case 50:
			case 4000:
			return 8;
			case 6220:
			case 6276:
			case 6256:
			case 6230:
			case 6239:
case 41:			// Aviansie
			case 6221:
			case 6231:
			case 6257:
			case 6278:
			case 8133:
			case 4004:
			case 40:
			case 10128:
			case 6233:
			case 6232:
			return 5;
			case 3102:
			case 3103:
			return 2;
			case 8349: case 8350: case 8351:
			return 1;
			case 2883://rex
			case 4291: // Cyclops
			case 4292: // Ice cyclops
			return 2;
			case 6263:
			case 6265:
			case 6206:
			case 6208:
			case 6222:
			case 4003:
			case 6223:
			case 6225:
			case 6250:
			case 6252:
			return 15;
			//things around dags
			case 2892:
			case 2894:
			return 10;
			
			case 2636:
				if(npcs[i].attackType == 3) {
					return 3;
				} else {
					return 4;
				}
			default:
			return 1;
		}
	}

	
	
	public int followDistance(int i) {
		switch (npcs[i].npcType) {
			case 6260:
			case 6261:
			case 6247:
			case 4006:
			case 6248:
			case 6223:
			case 6225:
			case 6227:
			case 6203:
			case 6204:
			case 6206:
			case 6208:
			case 6250:
			case 6252:
			case 6263:
			case 40:
			case 10128:
			case 8133:
			case 4004:
			case 6265:
			return 15;
			case 3247:
			case 6270:
			case 6219:
			case 6255:
			case 6229:
			case 6277:
			case 6233:
			case 6232:
			case 6218:
			case 6269:
			case 3248:
			case 6212:
			case 6220:
			case 6276:
			case 6256:
			case 6230:
			case 6239:
			case 41:
			case 6221:
			case 6231:
			case 6257:
			case 6278:
			case 6272:
			case 6274:
			case 6254:
			case 4291: // Cyclops
			case 4292: // Ice cyclops
			case 6258:
			case 8349: case 8350: case 8351:
			return 7;
			case 50:
			case 4000:
			return 18;
			case 2883:
			return 4;
			case 2881:
			case 4007:
			case 4008:
			case 4009:
			case 2882:
			return 1;
		
		}
		return 0;
		
	
	}
	
	public int getProjectileSpeed(int i) {
		switch(npcs[i].npcType) {
			case 2881:
			case 4007:
			case 4008:
			case 4009:
			case 2882:
			case 3200:
			return 85;
			
			case 2745:
			return 115;
			case 1158:
			case 1160:
			return 90;
			
			case 50:
			case 4000:
			case 53:
			case 742:
			case 54:
			case 55:
			case 941:
			case 4677:
			case 1590:
			case 1591:
			case 1592:
			return 85;
			
			case 2025:
			case 4022:
			return 85;

			case 3493:
			return 85;
			
			case 2028:
			case 4020:
			return 80;
			
			default:
			return 85;
		}
	}
	
	/**
	*NPC Attacking Player
	**/
	
	public void attackPlayer(Client c, int i) {
		if(npcs[i] != null) {
			if (npcs[i].isDead)
				return;
			if (!npcs[i].inMulti() && npcs[i].underAttackBy > 0 && npcs[i].underAttackBy != c.playerId) {
				npcs[i].killerId = 0;
				return;
			}
			/*if (npcs[i].npcType != 2025 && npcs[i].npcType != 6260 && npcs[i].npcType != 6247 && npcs[i].npcType != 2745 && npcs[i].npcType != NOMAD AND ANGRY GOBLIN CBA TO DO THIS ANYMORE && !Region.canAttack(npcs[i], c)) {
				return;
			}*/

			if (!npcs[i].inMulti() && (c.underAttackBy > 0 || (c.underAttackBy2 > 0 && c.underAttackBy2 != i))) {
				npcs[i].killerId = 0;
				return;
			}
			if (npcs[i].heightLevel != c.heightLevel) {
				npcs[i].killerId = 0;
				return;
			}
		if (!goodDistance(npcs[i].getX(), npcs[i].getY(), c.getY(), c.getX(), 1) && npcs[i].npcType == 8133 && npcs[i].attackType == 0)
{
npcs[i].attackType = 1+Misc.random(1);
			return;
}
		if (!goodDistance(npcs[i].getX(), npcs[i].getY(), c.getY(), c.getX(), 1) && npcs[i].npcType == 4004 && npcs[i].attackType == 0)
{
npcs[i].attackType = 1+Misc.random(1);
			return;
}



		if (!goodDistance(npcs[i].getX(), npcs[i].getY(), c.getY(), c.getX(), 1) && npcs[i].npcType == 10127 && npcs[i].attackType == 0)
{
npcs[i].attackType = 2;
			return;
}


		if (!goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), 1) && npcs[i].npcType == 8349 && npcs[i].attackType == 0)
{
npcs[i].attackType = 1+Misc.random(1);
			return;
} 
			npcs[i].facePlayer(c.playerId);
			boolean special = false;//specialCase(c,i);
			if(goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), distanceRequired(i)) || special) {
				if(c.respawnTimer <= 0) {
					npcs[i].facePlayer(c.playerId);
					npcs[i].attackTimer = getNpcDelay(i);
					npcs[i].hitDelayTimer = getHitDelay(i);
					npcs[i].attackType = 0;
					if (special)
						loadSpell2(i);
				else
						loadSpell(i);
					if (npcs[i].attackType == 3)
						npcs[i].hitDelayTimer += 2;
					if (multiAttacks(i)) {
						multiAttackGfx(i, npcs[i].projectileId);
						startAnimation(getAttackEmote(i), i);
						npcs[i].oldIndex = c.playerId;
						return;
					}
					if(npcs[i].projectileId > 0) {
						int nX = Server.npcHandler.npcs[i].getX() + offset(i);
						int nY = Server.npcHandler.npcs[i].getY() + offset(i);
						int pX = c.getX();
						int pY = c.getY();
						int offX = (nY - pY)* -1;
						int offY = (nX - pX)* -1;
						c.getPA().createPlayersProjectile(nX, nY, offX, offY, 50, getProjectileSpeed(i), npcs[i].projectileId, 43, 31, -c.getId() - 1, 65);
					}
					c.underAttackBy2 = i;
					c.singleCombatDelay2 = System.currentTimeMillis();
					npcs[i].oldIndex = c.playerId;
					startAnimation(getAttackEmote(i), i);
					c.getPA().removeAllWindows();
				} 
			}			
		}
	}
	
	public int offset(int i) {
		switch (npcs[i].npcType) {
			case 2881:
			case 4007:
			case 4008:
			case 4009:
			case 2882:
			return 1;
			case 2745:
			case 8349: case 8350: case 8351:
			case 2743:
			case 8133:
			case 4004:
			case 40:
			case 10128:
			case 50:
			case 4000:
			return 1;	
case 1158:
			case 1160:
				return 2;			
		}
		return 0;
	}
	
	public boolean specialCase(Client c, int i) { //responsible for npcs that much 
		if (goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), 8) && !goodDistance(npcs[i].getX(), npcs[i].getY(), c.getX(), c.getY(), distanceRequired(i)))
			return true;
		return false;
	}
	
	public boolean retaliates(int npcType) {
		return npcType < 6142 || npcType > 6145 && !(npcType >= 2440 && npcType <= 2446);
	}
	
	public void applyDamage(int i) {
		if(npcs[i] != null) {
			if(Server.playerHandler.players[npcs[i].oldIndex] == null) {
				return;
			}
			if (npcs[i].isDead)
				return;
			Client c = (Client) Server.playerHandler.players[npcs[i].oldIndex];
			if (multiAttacks(i)) {
				multiAttackDamage(i);
				return;
			}
			if (c.playerIndex <= 0 && c.npcIndex <= 0)
				if (c.autoRet == 1)
					c.npcIndex = i;
			if(c.attackTimer <= 3 || c.attackTimer == 0 && c.npcIndex == 0 && c.oldNpcIndex == 0) {
				c.startAnimation(c.getCombat().getBlockEmote());
			}
			if(c.respawnTimer <= 0) {	
				int damage = 0;
				if(npcs[i].attackType == 0) {
					damage = Misc.random(npcs[i].maxHit);
					if (10 + Misc.random(c.getCombat().calculateMeleeDefence()) > Misc.random(Server.npcHandler.npcs[i].attack)) {
						damage = 0;
					}				
					if(c.prayerActive[18] || c.curseActive[9]) { // protect from melee
					if (npcs[i].npcType == 291) 
						damage = (damage / 2);
					else
						damage = (int)damage / 10;
					}	
					if(c.SolProtect >= 1) { // protect from melee
						damage = (int)damage / 2;
					}			
					if (c.playerEquipment[c.playerShield] == 13740) {
						damage = (int)damage * 70 / 100;
					}
					if (c.playerEquipment[c.playerShield] == 13742) {
					if(Misc.random(4) == 3) {
					damage = (int)damage * 65 / 100;
					}
					}	
					if (c.playerLevel[3] - damage < 0) { 
						damage = c.playerLevel[3];
					}
				}
				
				if(npcs[i].attackType == 1) { // range
					damage = Misc.random(npcs[i].maxHit);
					if (10 + Misc.random(c.getCombat().calculateRangeDefence()) > Misc.random(Server.npcHandler.npcs[i].attack)) {
						damage = 0;
					}					
					if(c.prayerActive[17] || c.curseActive[8]) { // protect from range
						damage = (int)damage / 10;
					}		
					if(c.SolProtect >= 1) { // protect from melee
						damage = (int)damage / 2;
					}	
					if (c.playerEquipment[c.playerShield] == 13740) {
						damage = (int)damage * 70 / 100;
					}
					if (c.playerEquipment[c.playerShield] == 13742) {
					if(Misc.random(4) == 3) {
					damage = (int)damage * 65 / 100;
					}
					}
					if (c.playerLevel[3] - damage < 0) { 
						damage = c.playerLevel[3];
					}
				}
				
				if(npcs[i].attackType == 2) { // magic
					damage = Misc.random(npcs[i].maxHit);
					boolean magicFailed = false;
					if (10 + Misc.random(c.getCombat().mageDef()) > Misc.random(Server.npcHandler.npcs[i].attack)) {
						damage = 0;
						magicFailed = true;
					}				
					if(c.prayerActive[16] || c.curseActive[7]) { // protect from magic
						if (npcs[i].npcType != 2745) {
     damage = (damage / 2);
} else {
     damage = 0;
}
						magicFailed = true;
					}		
					if(c.SolProtect >= 1) { // protect from melee
						damage = (int)damage / 2;
					}		
					if (c.playerEquipment[c.playerShield] == 13740) {
						damage = (int)damage * 70 / 100;
					}
					if (c.playerEquipment[c.playerShield] == 13742) {
					if(Misc.random(4) == 3) {
					damage = (int)damage * 65 / 100;
					}
					}
					if (c.playerLevel[3] - damage < 0) { 
						damage = c.playerLevel[3];
					}
					if (damage == 0 && !isRFDNpc2(i)) { 
						c.gfx100(85);
					}
					if(npcs[i].endGfx > 0 && (!magicFailed || isFightCaveNpc(i))) {
					c.gfx100(npcs[i].endGfx);
					} else {
						//c.gfx100(85);
					}
				}
				
if (npcs[i].attackType == 3) { //fire breath 
					int anti = c.getPA().antiFire();
					if (anti == 0) {
						damage = Misc.random(30) + 10;
			Server.npcHandler.npcs[i].updateRequired = true;	
			c.doubleHit = false;
			c.getPA().refreshSkill(3);
			if(npcs[i].npcType == 2636 || npcs[i].npcType == 2586 || npcs[i].npcType == 2587 || npcs[i].npcType == 2588 || npcs[i].npcType == 2589) {	
			} else {
			c.sendMessage("You are badly burnt by the dragon fire!");
			}
						c.getCombat().addCharge(c);
				} else if (anti == 1)					
					damage = Misc.random(12);
					
					else if (anti == 2)
						damage = Misc.random(6);
					
					if (c.playerLevel[3] - damage < 0)
						damage = c.playerLevel[3];
					//c.gfx100(npcs[i].endGfx);
					
				}
				handleSpecialEffects(c, i, damage);
				if (c.vengOn && damage > 0) {
					c.getCombat().appendVengeanceNPC(i, damage);
				}
				c.logoutDelay = System.currentTimeMillis(); // logout delay
				
				if(npcs[i].setIcon > -1) {
					c.CIcon = npcs[i].setIcon;
					npcs[i].setIcon = -1; 
					
				}
				//c.setHitDiff(damage);
				c.handleHitMask(damage, c.playerId);
				c.playerLevel[3] -= damage;
				c.getPA().refreshSkill(3);
				c.updateRequired = true;
				//c.CIcon = npcs[i].setIcon;
				//c.setHitUpdateRequired(true);	
				

			}
		}
	}
	
	public void handleSpecialEffects(Client c, int i, int damage) {
		if (npcs[i].npcType == 2892 || npcs[i].npcType == 2894) {
			if (damage > 0) {
				if (c != null) {
					if (c.playerLevel[5] > 0) {
						c.playerLevel[5]--;
						c.getPA().refreshSkill(5);
	
					}
				}			
			}	
		}
	
	}
		
		

	public void startAnimation(int animId, int i) {
		npcs[i].animNumber = animId;
		npcs[i].animUpdateRequired = true;
		npcs[i].updateRequired = true;
	}
	
	public boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		return ((objectX-playerX <= distance && objectX-playerX >= -distance) && (objectY-playerY <= distance && objectY-playerY >= -distance));
	}
	
      
	public int getMaxHit(int i) {
		switch (npcs[i].npcType) {
			case 6222:
			case 4003:
				if (npcs[i].attackType == 2)
					return 28;
				else
					return 68;
			case 5247: // penancae queen
					return 30;
			case 3663: // Angry fucking gobl
					return 55;
			case 40:		
			case 10128:
			case 8133:
			case 4004:
				if (npcs[i].attackType == 0)
					return 48;
				else
				if (npcs[i].attackType == 1)
					return 48;
				else
				if (npcs[i].attackType == 2)
					return 60;
			case 6203:
				if (npcs[i].attackType == 0)
					return 40;
				else
					return 35;
			case 8349: case 8350: case 8351:
				if (npcs[i].attackType == 0)
					return 20;
				else
					return 27;
			case 6247:
			case 4006:
				return 31;
			case 6260:
			case 4001:
				return 36;
		}
		return 1;
	}
	
	
	public boolean loadAutoSpawn(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./"+FileName));
		} catch(FileNotFoundException fileex) {
			Misc.println(FileName+": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(FileName+": error loading file.");
			return false;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("spawn")) {
					newNPC(Integer.parseInt(token3[0]), Integer.parseInt(token3[1]), Integer.parseInt(token3[2]), Integer.parseInt(token3[3]), Integer.parseInt(token3[4]), getNpcListHP(Integer.parseInt(token3[0])), Integer.parseInt(token3[5]), Integer.parseInt(token3[6]), Integer.parseInt(token3[7]));
				
				}
			} else {
				if (line.equals("[ENDOFSPAWNLIST]")) {
					try { characterfile.close(); } catch(IOException ioexception) { }
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return false;
	}

	public int getNpcListHP(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcHealth;
				}
			}
		}
		return 0;
	}
	
	public String getNpcListName(int npcId) {
		for (int i = 0; i < maxListedNPCs; i++) {
			if (NpcList[i] != null) {
				if (NpcList[i].npcId == npcId) {
					return NpcList[i].npcName;
				}
			}
		}
		return "nothing";
	}

	public boolean loadNPCList(String FileName) {
		String line = "";
		String token = "";
		String token2 = "";
		String token2_2 = "";
		String[] token3 = new String[10];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		try {
			characterfile = new BufferedReader(new FileReader("./"+FileName));
		} catch(FileNotFoundException fileex) {
			Misc.println(FileName+": file not found.");
			return false;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(FileName+": error loading file.");
			return false;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("npc")) {
					newNPCList(Integer.parseInt(token3[0]), token3[1], Integer.parseInt(token3[2]), Integer.parseInt(token3[3]));
				}
			} else {
				if (line.equals("[ENDOFNPCLIST]")) {
					try { characterfile.close(); } catch(IOException ioexception) { }
					return true;
				}
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return false;
	}
	

}
