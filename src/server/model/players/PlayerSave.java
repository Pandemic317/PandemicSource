package server.model.players;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import server.Server;
import server.util.Misc;
@SuppressWarnings("all")
public class PlayerSave
{



	/**
	 *Loading
	 **/
	public static int loadGame(Client p, String playerName, String playerPass) {
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		boolean File1 = false;

		try {
			characterfile = new BufferedReader(new FileReader("./data/characters/"+playerName+".txt"));
			File1 = true;
		} catch(FileNotFoundException fileex1) {
		}

		if (File1) {
			//new File ("./data/characters/"+playerName+".txt");
		} else {
			//Misc.println(playerName+": character file not found.");
			p.newPlayer = false;
			return 0;
		}
		try {
			line = characterfile.readLine();
		} catch(IOException ioexception) {
			Misc.println(playerName+": error loading file.");
			return 3;
		}
		while(EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t");


				switch (ReadMode) {
				case 1:
					if (token.equals("character-password")) {
						if (playerPass.equalsIgnoreCase(token2) || Misc.basicEncrypt(playerPass).equals(token2) || Misc.md5Hash(playerPass).equals(token2)) {
							playerPass = Misc.basicEncrypt(playerPass);
						} else {
							return 3;
						}
					}
					break;
				case 2:
					if (token.equals("character-height")) {
						p.heightLevel = Integer.parseInt(token2);
					} else if (token.equals("character-posx")) {
						p.teleportToX = (Integer.parseInt(token2) <= 0 ? 3210 : Integer.parseInt(token2));
					} else if (token.equals("character-posy")) {
						p.teleportToY = (Integer.parseInt(token2) <= 0 ? 3424 : Integer.parseInt(token2));
					} else if (token.equals("character-rights")) {
						p.playerRights = Integer.parseInt(token2);
					} else if(token.equals("connected-from")) {
						p.lastConnectedFrom.add(token2);
					} else if(token.equals("Yell-Tag")) {
						p.yellTag = token2;
						//		} else if(token.equals("Yell-ban")) {
						//		p.yellBanReason = token2;
						//		} else if(token.equals("Can-Yell")) {
						//		p.canYell =  Boolean.parseBoolean(token2);
					} else if(token.equals("Custom-Yell")) {
						p.customYell =  Boolean.parseBoolean(token2);
					} else if (token.equals("summonTime")) {
						p.summonTime = Integer.parseInt(token2);
						/*} else if (token.equals("bhenter")) {
						p.enterBHTime = Integer.parseInt(token2);
					} else if (token.equals("bhleave")) {
						p.enterBHTime = Integer.parseInt(token2);
					} else if (token.equals("bhpickup")) {
						p.enterBHTime = Integer.parseInt(token2);
					} else if (token.equals("bhcash")) {
						p.enterBHTime = Integer.parseInt(token2);
					} else if (token.equals("bhcrater")) {
						p.enterBHTime = Integer.parseInt(token2);
					} else if (token.equals("bhrogue")) {
						p.rogueKill = Integer.parseInt(token2);
					} else if (token.equals("bhtarget")) {
						p.targetKill = Integer.parseInt(token2);*/

					} else if (token.equals("character-title")) {
						p.playerTitle = Integer.parseInt(token2);
					} else if (token.equals("effigy")) {
						p.effigy = Integer.parseInt(token2);
					} else if (token.equals("loyalty-Points")) {
						p.loyaltypoints = Integer.parseInt(token2);
					} else if (token.equals("Skillpoints")) {
						p.Skillpoints = Integer.parseInt(token2);
					} else if (token.equals("dungPoints")) {
						p.dungPoints = Integer.parseInt(token2);
					} else if (token.equals("Wheel")) {
						p.Wheel = Integer.parseInt(token2);
					} else if (token.equals("spinsLe")) {
						p.spinsLe = Integer.parseInt(token2);
					} else if (token.equals("TuberCC")) {
						p.TuberCC = Integer.parseInt(token2);
					} else if (token.equals("present")) {
						p.present = Integer.parseInt(token2);
					} else if (token.equals("PresentsDone")) {
						p.PresentsDone = Integer.parseInt(token2);
					} else if (token.equals("DTPoints")) {
						p.DTPoints = Integer.parseInt(token2);
					} else if (token.equals("AuraEquiped")) {
						p.AuraEquiped = Integer.parseInt(token2);
					} else if (token.equals("prestigeLevel")) {
						p.prestigeLevel = Integer.parseInt(token2);
					} else if (token.equals("votingPoints")) {
						p.votingPoints = Integer.parseInt(token2);
					} else if (token.equals("agilityPoints")) {
						p.agilitypoints = Integer.parseInt(token2);
					} else if (token.equals("skull-timer")) {
						p.skullTimer = Integer.parseInt(token2);
					} else if (line.startsWith("ppsLeft")) {
						p.ppsLeft = Integer.parseInt(token2);
					} else if (token.equals("MoneyOrb")) {
						p.MoneyCash = Integer.parseInt(token2);
					} else if (token.equals("LoyaltyPoints")) {
						p.LoyaltyPoints = Integer.parseInt(token2);			
					} else if (token.equals("LoyaltyScore")) {
						p.LoyaltyScore = Integer.parseInt(token2);	
					} else if (token.equals("summonId")) {
						p.summonId = Integer.parseInt(token2);
					} else if (token.equals("has-npc")) {
						p.hasNpc = Boolean.parseBoolean(token2);
					} else if(token.equals("tempid")) {
						p.tempid = Integer.parseInt(token2);
					} else if(token.equals("tempamt")) {
						p.tempamt = Integer.parseInt(token2);
					}  else if(token.equals("tempprice")) {
						p.tempprice = Integer.parseInt(token2);
					} else if (token.equals("EP")) {
						p.earningPotential = Integer.parseInt(token2);
					} else if (token.equals("bankPin1")) {
						p.bankPin1 = Integer.parseInt(token2);
					} else if (token.equals("bankPin2")) {
						p.bankPin2 = Integer.parseInt(token2);
					} else if (token.equals("bankPin3")) {
						p.bankPin3 = Integer.parseInt(token2);
					} else if (token.equals("bankPin4")) {
						p.bankPin4 = Integer.parseInt(token2);
					} else if (token.equals("hasBankPin")) {
						p.hasBankPin = Boolean.parseBoolean(token2);
					} else if (token.equals("VL")) {
						p.vlsLeft2 = Integer.parseInt(token2);
					} else if (token.equals("AGP")) {
						p.spoints = Integer.parseInt(token2);
					} else if (token.equals("kingQuest")) {
						p.kingQuest = Integer.parseInt(token2);
						/*	} else if (token.equals("runeQuest")) {
						p.rMQ = Integer.parseInt(token2);
					} else if (token.equals("herbQuest")) {
						p.bMQ = Integer.parseInt(token2);*/
					} else if (token.equals("character-longsword")) {
						p.vlsLeft = Integer.parseInt(token2);
					} else if (token.equals("character-warhammer")) {
						p.statLeft = Integer.parseInt(token2);
					} else if (token.equals("character-spear")) {
						p.vSpearLeft = Integer.parseInt(token2);
					} else if (token.equals("character-chainbody")) {
						p.vTopLeft = Integer.parseInt(token2);
					} else if (token.equals("shopcollect")) {
						p.playerCollect = Integer.parseInt(token2);
					} else if (token.equals("santaPrize")) {
						p.santaPrize = Integer.parseInt(token2);	
					} else if (token.equals("bossesKilled")) {
						p.bossesKilled = Integer.parseInt(token2);	
					} else if (token.equals("tasksCompleted")) {
						p.tasksCompleted = Integer.parseInt(token2);	
					} else if (token.equals("daggsKilled")) {
						p.daggsKilled = Integer.parseInt(token2);	
					} else if (token.equals("demonsKilled")) {
						p.demonsKilled = Integer.parseInt(token2);	
					} else if (token.equals("nexKilled")) {
						p.nexKilled = Integer.parseInt(token2);	
					} else if (token.equals("nomadKilled")) {
						p.nomadKilled = Integer.parseInt(token2);	
					} else if (token.equals("corpKilled")) {
						p.corpKilled = Integer.parseInt(token2);	
					} else if (token.equals("chaosKilled")) {
						p.chaosKilled = Integer.parseInt(token2);	
					} else if (token.equals("YellTime")) {
						p.YellTime = Integer.parseInt(token2);	
					} else if (token.equals("barrelKilled")) {//
						p.barrelKilled = Integer.parseInt(token2);
					} else if (token.equals("avatarKilled")) {
						p.avatarKilled = Integer.parseInt(token2);
					} else if (token.equals("glacorKilled")) {
						p.glacorKilled = Integer.parseInt(token2);
					} else if (token.equals("HulkKills")) {
						p.HulkKills = Integer.parseInt(token2);
					} else if (token.equals("frostsKilled")) {
						p.frostsKilled = Integer.parseInt(token2);
					} else if (token.equals("RaidPoints")) {
						p.RaidPoints = Integer.parseInt(token2);
					} else if (token.equals("thiefPoints")) {
						p.thiefPoints = Integer.parseInt(token2);
					} else if (token.equals("smallRocKilled")) {
						p.smallRocKilled = Integer.parseInt(token2);
					} else if (token.equals("ironGambler")) {
						p.ironGambler = Integer.parseInt(token2);
					} else if (token.equals("ironDeath")) {
						p.ironDeath = Integer.parseInt(token2);
					} else if (token.equals("KillsNr1")) {
						p.KillsNr1 = Integer.parseInt(token2);
					} else if (token.equals("KillsNr2")) {
						p.KillsNr2 = Integer.parseInt(token2);
					} else if (token.equals("KillsNr3")) {
						p.KillsNr3 = Integer.parseInt(token2);
					} else if (token.equals("KillsNr4")) {
						p.KillsNr4 = Integer.parseInt(token2);
					} else if (token.equals("vortexSmith")) {
						p.vortexSmith = Integer.parseInt(token2);
					} else if (token.equals("nationSmith")) {
						p.nationSmith = Integer.parseInt(token2);
					} else if (token.equals("captoSmith")) {
						p.captoSmith = Integer.parseInt(token2);
					} else if (token.equals("dragonboneSmith")) {
						p.dragonboneSmith = Integer.parseInt(token2);
					} else if (token.equals("bluetorvaSmith")) {
						p.bluetorvaSmith = Integer.parseInt(token2);
					} else if (token.equals("miningPoints")) {
						p.miningPoints = Integer.parseInt(token2);
					} else if (token.equals("barrowPoints")) {
						p.barrowPoints = Integer.parseInt(token2);
					} else if (token.equals("randomEventPoints")) {
						p.randomEventPoints = Integer.parseInt(token2);
					} else if (token.equals("godwarKilled")) {
						p.godwarKilled = Integer.parseInt(token2);
					} else if (token.equals("jadKilled")) {
						p.jadKilled = Integer.parseInt(token2);
					} else if (token.equals("jadPetKilled")) {
						p.jadPetKilled = Integer.parseInt(token2);
					} else if (token.equals("jadMiniKilled")) {
						p.jadMiniKilled = Integer.parseInt(token2);
					} else if (token.equals("mithKilled")) {
						p.mithKilled = Integer.parseInt(token2);
					} else if (token.equals("VesBeastKilled")) {
						p.VesBeastKilled = Integer.parseInt(token2);
					} else if (token.equals("ProtectorKilled")) {
						p.ProtectorKilled = Integer.parseInt(token2);
					} else if (token.equals("WreckerKilled")) {
						p.WreckerKilled = Integer.parseInt(token2);
					} else if (token.equals("GiantMoleKilled")) {
						p.GiantMoleKilled = Integer.parseInt(token2);
					} else if (token.equals("KalQueenKilled")) {
						p.KalQueenKilled = Integer.parseInt(token2);
					} else if (token.equals("ForgottenWarriorKilled")) {
						p.ForgottenWarriorKilled = Integer.parseInt(token2);
					} else if (token.equals("MadMummyKilled")) {
						p.MadMummyKilled = Integer.parseInt(token2);
					} else if (token.equals("PredatorTorvaKilled")) {
						p.PredatorTorvaKilled = Integer.parseInt(token2); 
					} else if (token.equals("FlameTorvaKilled")) {
						p.FlameTorvaKilled = Integer.parseInt(token2);
					} else if (token.equals("BlueTorvaKilled")) {
						p.BlueTorvaKilled = Integer.parseInt(token2);
					} else if (token.equals("Torva24KKilled")) {
						p.Torva24KKilled = Integer.parseInt(token2);
					} else if (token.equals("BurstTorvaKilled")) {
						p.BurstTorvaKilled = Integer.parseInt(token2);
					} else if (token.equals("CyrexTorvaKilled")) {
						p.CyrexTorvaKilled = Integer.parseInt(token2);
						//}
					} else if (token.equals("character-chainskirt")) {
						p.vLegsLeft = Integer.parseInt(token2);
					} else if (token.equals("character-full helm")) {
						p.sHelmLeft = Integer.parseInt(token2);
					} else if (token.equals("character-platebody")) {
						p.sTopLeft = Integer.parseInt(token2);
					} else if (token.equals("character-platelegs")) {
						p.sLegsLeft = Integer.parseInt(token2);
					} else if (token.equals("character-hood")) {
						p.zHoodLeft = Integer.parseInt(token2);
					} else if (token.equals("character-staff")) {
						p.zStaffLeft = Integer.parseInt(token2);
					} else if (token.equals("character-robe top")) {
						p.zTopLeft = Integer.parseInt(token2);
					} else if (token.equals("character-robe bottom")) {
						p.zBottomLeft = Integer.parseInt(token2);
					} else if (token.equals("character-leather body")) {
						p.mBodyLeft = Integer.parseInt(token2);
					} else if (token.equals("character-chaps")) {
						p.mChapsLeft = Integer.parseInt(token2);
					} else if (token.equals("magic-book")) {
						p.playerMagicBook = Integer.parseInt(token2);
					} else if (token.equals("xpLock")) {
						p.xpLock = Boolean.parseBoolean(token2);
					} else if (token.equals("yelltag")) {
						p.playeryelltag = token2;
					} else if (token.equals("yellcolor")) {
						p.playeryellcolor = token2;
					} else if (token.equals("yellset")) {
						p.yellSet = Integer.parseInt(token2);
					} else if (token.equals("Jailed")) {
						p.Jail = Boolean.parseBoolean(token2);
					} else if (token.equals("hasChoosenDung")) {
						p.hasChoosenDung = Boolean.parseBoolean(token2);
					} else if (token.equals("Agrith")) {
						p.Agrith = Boolean.parseBoolean(token2);
					} else if (token.equals("degrade")) {
						p.degradeTime = Integer.parseInt(token2); 
					} else if (token.equals("Flambeed")) {
						p.Flambeed = Boolean.parseBoolean(token2);
					} else if (token.equals("Karamel")) {
						p.Karamel = Boolean.parseBoolean(token2);
					} else if (token.equals("Dessourt")) {
						p.Dessourt = Boolean.parseBoolean(token2);
					} else if (token.equals("culin")) {
						p.Culin = Boolean.parseBoolean(token2);	
					} else if (token.equals("Nomad")) {
						p.Nomad = Boolean.parseBoolean(token2);	
					} else if (token.equals("Goblin")) {
						p.Goblin = Boolean.parseBoolean(token2);
					} else if (token.equals("sir")) {
						p.sir = Boolean.parseBoolean(token2);						
					} else if (token.equals("vote")) {
						p.vote = Integer.parseInt(token2);						
					} else if (token.equals("PkminiPoints")) {
						p.PkminiPoints = Integer.parseInt(token2);						
					} else if (token.equals("brother-info")) {
						p.barrowsNpcs[Integer.parseInt(token3[0])][1] = Integer.parseInt(token3[1]);
					} else if (token.equals("special-amount")) {
						p.specAmount = Double.parseDouble(token2);	
					} else if (token.equals("selected-coffin")) {
						p.randomCoffin = Integer.parseInt(token2);	
					} else if (token.equals("barrows-killcount")) {
						p.pkPoints = Integer.parseInt(token2);
					} else if (token.equals("tutorial-stage")) {
						p.getTutorial().tutorialStage = Integer.parseInt(token2);
					} else if (token.equals("teleblock-length")) {
						p.teleBlockDelay = System.currentTimeMillis();
						p.teleBlockLength = Integer.parseInt(token2);							
					} else if (token.equals("pc-points")) {
						p.pcPoints = Integer.parseInt(token2);	
					} else if (token.equals("gwdelay")) {
						p.gwdelay = Integer.parseInt(token2);
					} else if (token.equals("Dicer")) {
						p.Dicer = Integer.parseInt(token2);
						/*			} else if (token.equals("summonSpec")) {
						p.summonSpec = Integer.parseInt(token2);*/
					} else if (token.equals("dungRest")) {
						p.dungRest = Integer.parseInt(token2);
					} else if (token.equals("Altar")) {
						p.altarPrayed = Integer.parseInt(token2);
					} else if (token.equals("Killed")) {
						p.altarPrayed = Integer.parseInt(token2);
					} else if (token.equals("Death")) {
						p.altarPrayed = Integer.parseInt(token2);
					} else if (token.equals("Arma-KC")) {
						p.Arma = Integer.parseInt(token2);	
					} else if (token.equals("Band-KC")) {
						p.Band = Integer.parseInt(token2);	
					} else if (token.equals("Zammy-KC")) {
						p.Zammy = Integer.parseInt(token2);	
					} else if (token.equals("Sara-KC")) {
						p.Sara = Integer.parseInt(token2);	
					} else if (token.equals("customPoints")) {
						p.customPoints = Integer.parseInt(token2);
					} else if (token.equals("npc-kills")) {
						p.npcKills = Integer.parseInt(token2);
					} else if (token.equals("lizard-spawn")) {
						p.lizardSpawn = Integer.parseInt(token2);
					} else if (token.equals("superPoints")) {
						p.superPoints = Integer.parseInt(token2);
					} else if (token.equals("pummelerPoints")) {
						p.pummelerPoints = Integer.parseInt(token2);
					} else if (token.equals("mbPoints")) {
						p.mbPoints = Integer.parseInt(token2);
					} else if (token.equals("isDonator")) {
						p.isDonator = Integer.parseInt(token2);
					} else if (token.equals("issDonator")) {
						p.issDonator = Integer.parseInt(token2);
					} else if (token.equals("donatorChest")) {
						p.donatorChest = Integer.parseInt(token2);
					} else if (token.equals("totalDonatorPoints")) {
						p.totalDonatorPoints = Integer.parseInt(token2);
					} else if (token.equals("usedxplock")) {
						p.usedxplock = Boolean.parseBoolean(token2);						
					} else if (token.equals("slayerTask")) {
						p.slayerTask = Integer.parseInt(token2);					
					} else if (token.equals("taskAmount")) {
						p.taskAmount = Integer.parseInt(token2);					
					} else if (token.equals("magePoints")) {
						p.magePoints = Integer.parseInt(token2);
						/*		} else if (line.startsWith("KC")) {
						p.KC = Integer.parseInt(token2);
					} else if (line.startsWith("DC")) {
						p.DC = Integer.parseInt(token2);*/
					} else if (line.startsWith("totalstored")) {
						p.totalstored = Integer.parseInt(token2);
					} else if (token.equals("autoRet")) {
						p.autoRet = Integer.parseInt(token2);
					} else if(token.equals("mute-end")) {
						p.muteEnd = Long.parseLong(token2);
					} else if(token.equals("staffLogin")) {
						p.staffLogin = Long.parseLong(token2);
					} else if (token.equals("achieved")) {
						for (int j = 0; j < token3.length; j++) {
							p.achieved[j] = Boolean.parseBoolean(token3[j]);						
						}
					} else if (token.equals("achievement")) {
						for (int j = 0; j < token3.length; j++) {
							p.achievement[j] = Integer.parseInt(token3[j]);						
						}
					}  else if (token.equals("points")) {
						p.achievementPoints = Integer.parseInt(token2);

					} else if (token.equals("barbPoints")) {
						p.barbPoints = Integer.parseInt(token2);
						/*} else if (token.equals("grimPrize")) {
						p.grimPrize = Integer.parseInt(token2);	*/
					} else if (token.equals("trade11")) {
						p.trade11 = Integer.parseInt(token2);
					} else if (token.equals("SpeDelay")) {
						p.SpecialDelay = Integer.parseInt(token2);
					} else if (token.equals("barrowskillcount")) {
						p.barrowsKillCount = Integer.parseInt(token2);
						/*	} else if (token.equals("flagged")) {
						p.accountFlagged = Boolean.parseBoolean(token2);
					} else if (token.equals("Rules")) {
						p.readRules = Boolean.parseBoolean(token2);*/
					} else if (token.equals("isShopping")) {
						p.isShopping = Boolean.parseBoolean(token2);
					} else if (token.equals("spoints")) {
						p.spoints = Integer.parseInt(token2);
					} else if (token.equals("wave")) {
						p.waveId = Integer.parseInt(token2);
					} else if (token.equals("dfs-charges")) {
						p.dfsCount = Integer.parseInt(token2);
					} else if (token.equals("hasFollower")) {
						p.hasFollower = Integer.parseInt(token2);

					} else if (token.equals("summoningnpcid")) {
						p.summoningnpcid = Integer.parseInt(token2);

					} else if (token.equals("void")) {
						for (int j = 0; j < token3.length; j++) {
							p.voidStatus[j] = Integer.parseInt(token3[j]);						
						}
					} else if (token.equals("fightMode")) {
						p.fightMode = Integer.parseInt(token2);
					} else if (token.equals("COaltar")) {
						p.COaltar = Integer.parseInt(token2);
					} else if (token.equals("IsIDung")) {
						p.IsIDung = Integer.parseInt(token2);	
					} else if (token.equals("DUfood1")) {
						p.DUfood1 = Integer.parseInt(token2);
					} else if (token.equals("DUfood2")) {
						p.DUfood2 = Integer.parseInt(token2);
					} else if (token.equals("DUfood3")) {
						p.DUfood3 = Integer.parseInt(token2);
					} else if (token.equals("DUfood4")) {
						p.DUfood4 = Integer.parseInt(token2);
					} else if (token.equals("OWdungn")) {
						p.dungn = Integer.parseInt(token2);
					} else if (token.equals("COchest")) {
						p.COchest = Integer.parseInt(token2);
					} else if (token.equals("CObeddy")) {
						p.CObeddy = Integer.parseInt(token2);
					} else if (token.equals("COtreee")) {
						p.COtreee = Integer.parseInt(token2);
					} else if (token.equals("COLectt")) {
						p.COLectt = Integer.parseInt(token2);
					} else if (token.equals("COtelee")) {
						p.COtelee = Integer.parseInt(token2);
					} else if (token.equals("COcryst")) {
						p.COcryst = Integer.parseInt(token2);
						/*		} else if (token.equals("Black-marks")) {
						p.BlackMarks = Integer.parseInt(token2); */
					}
					break;
				case 3:
					if (token.equals("character-equip")) {
						p.playerEquipment[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerEquipmentN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 4:
					if (token.equals("character-look")) {
						p.playerAppearance[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
					} 
					break;
				case 5:
					if (token.equals("character-skill")) {
						p.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 6:
					if (token.equals("character-item")) {
						p.playerItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 7:
					if (token.equals("character-bank")) {
						p.bankItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.bankItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
					}
					break;
				case 8:
					if (token.equals("character-friend")) {
						p.friends[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					} 
					break;
				case 9:
					/* if (token.equals("character-ignore")) {
						ignores[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
					} */
					break;
				case 20:
					if (token.equals("stored")) {
						p.storeditems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
					}
					break;

				case 21:
					if (token.equals("occupy")) {
						p.occupied[Integer.parseInt(token3[0])] = Boolean.parseBoolean(token3[1]);
					} 
					break;
				case 10:
					if (token.equals("character-shop")) {
						p.playerShop[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
						p.playerShopP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
						p.playerShopN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[3]);
					} 
					break;
				}
			} else {
				if (line.equals("[ACCOUNT]")) {		ReadMode = 1;
				} else if (line.equals("[CHARACTER]")) {	ReadMode = 2;
				} else if (line.equals("[EQUIPMENT]")) {	ReadMode = 3;
				} else if (line.equals("[LOOK]")) {		ReadMode = 4;
				} else if (line.equals("[SKILLS]")) {		ReadMode = 5;
				} else if (line.equals("[ITEMS]")) {		ReadMode = 6;
				} else if (line.equals("[BANK]")) {		ReadMode = 7;
				} else if (line.equals("[FRIENDS]")) {		ReadMode = 8;
				} else if (line.equals("[IGNORES]")) {		ReadMode = 9;
				} else if (line.equals("[STORED]")) {		ReadMode = 20;
				} else if (line.equals("[OCCUPY]")) {		ReadMode = 21;
				} else if (line.equals("[SHOP]")) {		ReadMode = 10;
				} else if (line.equals("[EOF]")) {		try { characterfile.close(); } catch(IOException ioexception) { } return 1;
				}
			}
			try {
				line = characterfile.readLine();
			} catch(IOException ioexception1) { EndOfFile = true; }
		}
		try { characterfile.close(); } catch(IOException ioexception) { }
		return 13;
	}




	/**
	 *Saving
	 **/
	public static boolean saveGame(Client p) {
		if(!p.saveFile || p.newPlayer || !p.saveCharacter) {
			//System.out.println("first");
			return false;
		}
		if(p.playerName == null || Server.playerHandler.players[p.playerId] == null) {
			//System.out.println("second");
			return false;
		}
		p.playerName = p.playerName2;
		int tbTime = (int)(p.teleBlockDelay - System.currentTimeMillis() + p.teleBlockLength);
		if(tbTime > 300000 || tbTime < 0){
			tbTime = 0;
		}

		BufferedWriter characterfile = null;
		try {
			characterfile = new BufferedWriter(new FileWriter("./Data/characters/"+p.playerName+".txt"));

			/*ACCOUNT*/
			characterfile.write("[ACCOUNT]", 0, 9);
			characterfile.newLine();
			characterfile.write("character-username = ", 0, 21);
			characterfile.write(p.playerName, 0, p.playerName.length());
			characterfile.newLine();
			characterfile.write("character-password = ", 0, 21);			
			String passToWrite = Misc.md5Hash(p.playerPass);
			characterfile.write(passToWrite, 0, passToWrite.length());
			characterfile.newLine();
			characterfile.newLine();
			/*	characterfile.write("KC = ", 0, 4);
			characterfile.write(Integer.toString(p.KC), 0, Integer.toString(p.KC).length());
			characterfile.newLine();
			characterfile.write("DC = ", 0, 4);
			characterfile.write(Integer.toString(p.DC), 0, Integer.toString(p.DC).length());
			characterfile.newLine();*/

			/*CHARACTER*/
			characterfile.write("[CHARACTER]", 0, 11);
			characterfile.newLine();
			characterfile.write("character-height = ", 0, 19);
			characterfile.write(Integer.toString(p.heightLevel), 0, Integer.toString(p.heightLevel).length());
			characterfile.newLine();

			characterfile.write("character-posx = ", 0, 17);
			characterfile.write(Integer.toString(p.absX), 0, Integer.toString(p.absX).length());
			characterfile.newLine();
			characterfile.write("character-posy = ", 0, 17);
			characterfile.write(Integer.toString(p.absY), 0, Integer.toString(p.absY).length());
			characterfile.newLine();
			characterfile.write("character-rights = ", 0, 19);
			characterfile.write(Integer.toString(p.playerRights), 0, Integer.toString(p.playerRights).length());
			characterfile.newLine();
			characterfile.write("character-title = ", 0, 18);
			characterfile.write(Integer.toString(p.playerTitle), 0, Integer.toString(p.playerTitle).length());
			characterfile.newLine();
			for (int i = 0; i < p.lastConnectedFrom.size(); i++) {
				characterfile.write("connected-from = ", 0, 17);
				characterfile.write(p.lastConnectedFrom.get(i), 0, p.lastConnectedFrom.get(i).length());
				characterfile.newLine();
			}
			characterfile.newLine();
			characterfile.write("[POINTS]", 0, 8);
			characterfile.newLine();
			characterfile.write("prestigeLevel = ", 0, 16);
			characterfile.write(Integer.toString(p.prestigeLevel), 0, Integer
					.toString(p.prestigeLevel).length());
			characterfile.newLine();
			characterfile.write("loyaltypoints = ", 0, 16);
			characterfile.write(Integer.toString(p.loyaltypoints), 0, Integer.toString(p.loyaltypoints).length());
			characterfile.newLine();
			characterfile.write("pkName = ", 0, 9);
			characterfile.write(p.pkName, 0, p.pkName.length());
			characterfile.newLine();
			characterfile.write("Skillpoints = ", 0, 14);
			characterfile.write(Integer.toString(p.Skillpoints), 0, Integer.toString(p.Skillpoints).length());
			characterfile.newLine();
			characterfile.write("dungPoints = ", 0, 13);
			characterfile.write(Integer.toString(p.dungPoints), 0, Integer.toString(p.dungPoints).length());
			characterfile.newLine();
			characterfile.write("agilitypoints = ", 0, 16);
			characterfile.write(Integer.toString(p.agilitypoints), 0, Integer.toString(p.agilitypoints).length());
			characterfile.newLine();
			characterfile.write("DTPoints = ", 0, 11);
			characterfile.write(Integer.toString(p.DTPoints), 0, Integer.toString(p.DTPoints).length());
			characterfile.newLine();
			characterfile.write("dungPoints = ", 0, 13);
			characterfile.write(Integer.toString(p.dungPoints), 0, Integer.toString(p.dungPoints).length());
			characterfile.newLine();
			characterfile.write("votingPoints = ", 0, 15);            
			characterfile.write(Integer.toString(p.votingPoints), 0, Integer.toString(p.votingPoints).length());
			characterfile.newLine();
			characterfile.write("LoyaltyPoints = ", 0, 16);
			characterfile.write(Integer.toString(p.LoyaltyPoints), 0, Integer.toString(p.LoyaltyPoints).length());
			characterfile.newLine();
			characterfile.write("tutorial-stage = ", 0, 17);
			characterfile.write(Integer.toString(p.getTutorial().tutorialStage), 0, Integer.toString(p.getTutorial().tutorialStage).length());
			characterfile.newLine();
			characterfile.write("pc-points = ", 0, 12);
			characterfile.write(Integer.toString(p.pcPoints), 0, Integer.toString(p.pcPoints).length());
			characterfile.newLine();
			characterfile.write("barbPoints = ", 0, 13);
			characterfile.write(Integer.toString(p.barbPoints), 0, Integer.toString(p.barbPoints).length());
			characterfile.newLine();
			characterfile.write("pummelerPoints = ", 0, 17);
			characterfile.write(Integer.toString(p.pummelerPoints), 0, Integer.toString(p.pummelerPoints).length());
			characterfile.newLine();
			characterfile.write("customPoints = ", 0, 15);
			characterfile.write(Integer.toString(p.customPoints), 0, Integer.toString(p.customPoints).length());
			characterfile.newLine();
			characterfile.write("npc-kills = ", 0, 12);
			characterfile.write(Integer.toString(p.npcKills), 0, Integer.toString(p.npcKills).length());
			characterfile.newLine();
			characterfile.write("lizardSpawn = ", 0, 14);
			characterfile.write(Integer.toString(p.lizardSpawn), 0, Integer.toString(p.lizardSpawn).length());
			characterfile.newLine();
			characterfile.write("PkminiPoints = ", 0, 15);
			characterfile.write(Integer.toString(p.PkminiPoints), 0, Integer.toString(p.PkminiPoints).length());
			characterfile.newLine();
			characterfile.write("superPoints = ", 0, 14);
			characterfile.write(Integer.toString(p.superPoints), 0, Integer.toString(p.superPoints).length());
			characterfile.newLine();
			characterfile.write("mbPoints = ", 0, 11);
			characterfile.write(Integer.toString(p.mbPoints), 0, Integer.toString(p.mbPoints).length());
			characterfile.newLine();
			characterfile.write("magePoints = ", 0, 13);
			characterfile.write(Integer.toString(p.magePoints), 0, Integer.toString(p.magePoints).length());
			characterfile.newLine();
			characterfile.write("Killed = ", 0, 9);
			characterfile.write(Integer.toString(p.Killed), 0, Integer.toString(p.Killed).length());
			characterfile.newLine();
			characterfile.write("Death = ", 0, 8);
			characterfile.write(Integer.toString(p.Death), 0, Integer.toString(p.Death).length());
			characterfile.newLine();
			characterfile.write("Dicer = ", 0, 8);
			characterfile.write(Integer.toString(p.Dicer), 0, Integer.toString(p.Dicer).length());
			characterfile.newLine();
			characterfile.newLine();
			characterfile.write("[DONATOR]", 0, 9);
			characterfile.newLine();			
			characterfile.write("isDonator = ", 0, 12);
			characterfile.write(Integer.toString(p.isDonator), 0, Integer.toString(p.isDonator).length());
			characterfile.newLine();
			characterfile.write("issDonator = ", 0, 13);
			characterfile.write(Integer.toString(p.issDonator), 0, Integer.toString(p.issDonator).length());
			characterfile.newLine();
			characterfile.write("donatorChest = ", 0, 15);
			characterfile.write(Integer.toString(p.donatorChest), 0, Integer.toString(p.donatorChest).length());
			characterfile.newLine();
			characterfile.write("totalDonatorPoints = ", 0, 21);
			characterfile.write(Integer.toString(p.totalDonatorPoints), 0, Integer.toString(p.totalDonatorPoints).length());
			characterfile.newLine();
			characterfile.newLine();
			characterfile.write("[Monsters]", 0, 10);
			characterfile.newLine();			
			characterfile.write("tasksCompleted = ", 0, 17);
			characterfile.write(Integer.toString(p.tasksCompleted), 0, Integer.toString(p.tasksCompleted).length());
			characterfile.newLine();
			characterfile.write("daggsKilled = ", 0, 14);
			characterfile.write(Integer.toString(p.daggsKilled), 0, Integer.toString(p.daggsKilled).length());
			characterfile.newLine();
			characterfile.write("demonsKilled = ", 0, 15);
			characterfile.write(Integer.toString(p.demonsKilled), 0, Integer.toString(p.demonsKilled).length());
			characterfile.newLine();
			characterfile.write("nexKilled = ", 0, 12);
			characterfile.write(Integer.toString(p.nexKilled), 0, Integer.toString(p.nexKilled).length());
			characterfile.newLine();
			characterfile.write("nomadKilled = ", 0, 14);
			characterfile.write(Integer.toString(p.nomadKilled), 0, Integer.toString(p.nomadKilled).length());
			characterfile.newLine();
			characterfile.write("corpKilled = ", 0, 13);
			characterfile.write(Integer.toString(p.corpKilled), 0, Integer.toString(p.corpKilled).length());
			characterfile.newLine();
			characterfile.write("chaosKilled = ", 0, 14);
			characterfile.write(Integer.toString(p.chaosKilled), 0, Integer.toString(p.chaosKilled).length());
			characterfile.newLine();
			/*characterfile.write("YellTime = ", 0, 14);
			characterfile.write(Integer.toString(p.YellTime), 0, Integer.toString(p.YellTime).length());
			characterfile.newLine();*/
			characterfile.write("barrelKilled = ", 0, 15);
			characterfile.write(Integer.toString(p.barrelKilled), 0, Integer.toString(p.barrelKilled).length());
			characterfile.newLine();
			characterfile.write("avatarKilled = ", 0, 15);
			characterfile.write(Integer.toString(p.avatarKilled), 0, Integer.toString(p.avatarKilled).length());
			characterfile.newLine();
			characterfile.write("glacorKilled = ", 0, 15);
			characterfile.write(Integer.toString(p.glacorKilled), 0, Integer.toString(p.glacorKilled).length());
			characterfile.newLine();
			characterfile.write("HulkKills = ", 0, 12);
			characterfile.write(Integer.toString(p.HulkKills), 0, Integer.toString(p.HulkKills).length());
			characterfile.newLine();
			characterfile.write("frostsKilled = ", 0, 15);
			characterfile.write(Integer.toString(p.frostsKilled), 0, Integer.toString(p.frostsKilled).length());
			characterfile.newLine();
			characterfile.write("raidPoints = ", 0, 13);
			characterfile.write(Integer.toString(p.raidPoints), 0, Integer.toString(p.raidPoints).length());
			characterfile.newLine();
			characterfile.write("thiefPoints = ", 0, 14);
			characterfile.write(Integer.toString(p.thiefPoints), 0, Integer.toString(p.thiefPoints).length());
			characterfile.newLine();
			characterfile.write("KillsNr1 = ", 0, 11);
			characterfile.write(Integer.toString(p.KillsNr1), 0, Integer.toString(p.KillsNr1).length());
			characterfile.newLine();
			characterfile.write("KillsNr2 = ", 0, 11);
			characterfile.write(Integer.toString(p.KillsNr2), 0, Integer.toString(p.KillsNr2).length());
			characterfile.newLine();
			characterfile.write("KillsNr3 = ", 0, 11);
			characterfile.write(Integer.toString(p.KillsNr3), 0, Integer.toString(p.KillsNr3).length());
			characterfile.newLine();
			characterfile.write("KillsNr4 = ", 0, 11);
			characterfile.write(Integer.toString(p.KillsNr4), 0, Integer.toString(p.KillsNr4).length());
			characterfile.newLine();
			characterfile.write("vortexSmith = ", 0, 14);
			characterfile.write(Integer.toString(p.vortexSmith), 0, Integer.toString(p.vortexSmith).length());
			characterfile.newLine();
			characterfile.write("nationSmith = ", 0, 14);
			characterfile.write(Integer.toString(p.nationSmith), 0, Integer.toString(p.nationSmith).length());
			characterfile.newLine();
			characterfile.write("captoSmith = ", 0, 13);
			characterfile.write(Integer.toString(p.captoSmith), 0, Integer.toString(p.captoSmith).length());
			characterfile.newLine();
			characterfile.write("dragonboneSmith = ", 0, 18);
			characterfile.write(Integer.toString(p.dragonboneSmith), 0, Integer.toString(p.dragonboneSmith).length());
			characterfile.newLine();
			characterfile.write("bluetorvaSmith = ", 0, 17);
			characterfile.write(Integer.toString(p.bluetorvaSmith), 0, Integer.toString(p.bluetorvaSmith).length());
			characterfile.newLine();
			characterfile.write("miningPoints = ", 0, 15);
			characterfile.write(Integer.toString(p.miningPoints), 0, Integer.toString(p.miningPoints).length());
			characterfile.newLine();
			characterfile.write("randomEventPoints = ", 0, 20);
			characterfile.write(Integer.toString(p.KillsNr4), 0, Integer.toString(p.KillsNr4).length());
			characterfile.newLine();
			characterfile.write("barrowPoints = ", 0, 15);
			characterfile.write(Integer.toString(p.barrowPoints), 0, Integer.toString(p.barrowPoints).length());
			characterfile.newLine();
			characterfile.write("smallRocKilled = ", 0, 17);
			characterfile.write(Integer.toString(p.smallRocKilled), 0, Integer.toString(p.smallRocKilled).length());
			characterfile.newLine();
			characterfile.write("ironGambler = ", 0, 14);
			characterfile.write(Integer.toString(p.ironGambler), 0, Integer.toString(p.ironGambler).length());
			characterfile.newLine();
			characterfile.write("ironDeath = ", 0, 12);
			characterfile.write(Integer.toString(p.ironDeath), 0, Integer.toString(p.ironDeath).length());
			characterfile.newLine();
			characterfile.write("godwarKilled = ", 0, 15);
			characterfile.write(Integer.toString(p.godwarKilled), 0, Integer.toString(p.godwarKilled).length());
			characterfile.newLine();
			characterfile.write("jadKilled = ", 0, 12);
			characterfile.write(Integer.toString(p.jadKilled), 0, Integer.toString(p.jadKilled).length());
			characterfile.newLine();
			characterfile.write("jadPetKilled = ", 0, 15);
			characterfile.write(Integer.toString(p.jadPetKilled), 0, Integer.toString(p.jadPetKilled).length());
			characterfile.newLine();
			characterfile.write("jadMiniKilled = ", 0, 16);
			characterfile.write(Integer.toString(p.jadMiniKilled), 0, Integer.toString(p.jadMiniKilled).length());
			characterfile.newLine();
			characterfile.write("mithKilled = ", 0, 13);
			characterfile.write(Integer.toString(p.mithKilled), 0, Integer.toString(p.mithKilled).length());
			characterfile.newLine();
			characterfile.write("Yell-Tag = ", 0, 11);
			characterfile.write(p.yellTag, 0, p.yellTag.length());
			characterfile.newLine();
			/*	characterfile.write("Yell-ban = ", 0, 11);//Custom-Yell
			characterfile.write(p.yellBanReason, 0, p.yellBanReason.length());//canYell
			characterfile.newLine();
			characterfile.write("can-Yell = ", 0, 11);
			characterfile.write(Boolean.toString(p.canYell), 0, Boolean.toString(p.canYell).length());
			characterfile.newLine();*/
			characterfile.write("Custom-Yell = ", 0, 14);
			characterfile.write(Boolean.toString(p.customYell), 0, Boolean.toString(p.customYell).length());
			characterfile.newLine();

			characterfile.write("VesBeastKilled = ", 0, 17);
			characterfile.write(Integer.toString(p.VesBeastKilled), 0, Integer.toString(p.VesBeastKilled).length());
			characterfile.newLine();
			characterfile.write("ProtectorKilled = ", 0, 18);
			characterfile.write(Integer.toString(p.ProtectorKilled), 0, Integer.toString(p.ProtectorKilled).length());
			characterfile.newLine();
			characterfile.write("WreckerKilled = ", 0, 16);
			characterfile.write(Integer.toString(p.WreckerKilled), 0, Integer.toString(p.WreckerKilled).length());
			characterfile.newLine();
			characterfile.write("GiantMoleKilled = ", 0, 18);
			characterfile.write(Integer.toString(p.GiantMoleKilled), 0, Integer.toString(p.GiantMoleKilled).length());
			characterfile.newLine();
			characterfile.write("KalQueenKilled = ", 0, 17);
			characterfile.write(Integer.toString(p.KalQueenKilled), 0, Integer.toString(p.KalQueenKilled).length());
			characterfile.newLine();
			characterfile.write("ForgottenWarriorKilled = ", 0, 25);
			characterfile.write(Integer.toString(p.ForgottenWarriorKilled), 0, Integer.toString(p.ForgottenWarriorKilled).length());
			characterfile.newLine();
			characterfile.write("MadMummyKilled = ", 0, 17);
			characterfile.write(Integer.toString(p.MadMummyKilled), 0, Integer.toString(p.MadMummyKilled).length());
			characterfile.newLine();
			characterfile.write("BurstTorvaKilled = ", 0, 19);
			characterfile.write(Integer.toString(p.BurstTorvaKilled), 0, Integer.toString(p.BurstTorvaKilled).length());
			characterfile.newLine();
			characterfile.write("PredatorTorvaKilled = ", 0, 22);
			characterfile.write(Integer.toString(p.PredatorTorvaKilled), 0, Integer.toString(p.PredatorTorvaKilled).length());
			characterfile.newLine();
			characterfile.write("FlameTorvaKilled = ", 0, 19);
			characterfile.write(Integer.toString(p.FlameTorvaKilled), 0, Integer.toString(p.FlameTorvaKilled).length());
			characterfile.newLine();
			characterfile.write("BlueTorvaKilled = ", 0, 18);
			characterfile.write(Integer.toString(p.BlueTorvaKilled), 0, Integer.toString(p.BlueTorvaKilled).length());
			characterfile.newLine();
			characterfile.write("Torva24KKilled = ", 0, 17);
			characterfile.write(Integer.toString(p.Torva24KKilled), 0, Integer.toString(p.Torva24KKilled).length());
			characterfile.newLine();
			characterfile.write("CyrexTorvaKilled = ", 0, 19);
			characterfile.write(Integer.toString(p.CyrexTorvaKilled), 0, Integer.toString(p.CyrexTorvaKilled).length());
			characterfile.newLine();
			characterfile.newLine();
			characterfile.newLine();
			characterfile.write("[DUNGEONEERING]", 0, 15);
			characterfile.newLine();			
			characterfile.write("COaltar = ", 0, 10);
			characterfile.write(Integer.toString(p.COaltar), 0, Integer.toString(p.COaltar).length());
			characterfile.newLine();	
			characterfile.write("IsIDung = ", 0, 10);
			characterfile.write(Integer.toString(p.IsIDung), 0, Integer.toString(p.IsIDung).length());
			characterfile.newLine();
			characterfile.write("DUfood1 = ", 0, 10);
			characterfile.write(Integer.toString(p.DUfood1), 0, Integer.toString(p.DUfood1).length());
			characterfile.newLine();
			characterfile.write("DUfood2 = ", 0, 10);
			characterfile.write(Integer.toString(p.DUfood2), 0, Integer.toString(p.DUfood2).length());
			characterfile.newLine();
			characterfile.write("DUfood3 = ", 0, 10);
			characterfile.write(Integer.toString(p.DUfood3), 0, Integer.toString(p.DUfood3).length());
			characterfile.newLine();
			characterfile.write("DUfood4 = ", 0, 10);
			characterfile.write(Integer.toString(p.DUfood4), 0, Integer.toString(p.DUfood4).length());
			characterfile.newLine();
			characterfile.write("OWdungn = ", 0, 10);
			characterfile.write(Integer.toString(p.dungn), 0, Integer.toString(p.dungn).length());
			characterfile.newLine();
			characterfile.write("COchest = ", 0, 10);
			characterfile.write(Integer.toString(p.COchest), 0, Integer.toString(p.COchest).length());
			characterfile.newLine();
			characterfile.write("COtreee = ", 0, 10);
			characterfile.write(Integer.toString(p.COtreee), 0, Integer.toString(p.COtreee).length());
			characterfile.newLine();
			characterfile.write("COLectt = ", 0, 10);
			characterfile.write(Integer.toString(p.COLectt), 0, Integer.toString(p.COLectt).length());
			characterfile.newLine();
			characterfile.write("COtelee = ", 0, 10);
			characterfile.write(Integer.toString(p.COtelee), 0, Integer.toString(p.COtelee).length());
			characterfile.newLine();
			characterfile.write("COcryst = ", 0, 10);
			characterfile.write(Integer.toString(p.COcryst), 0, Integer.toString(p.COcryst).length());
			characterfile.newLine();
			characterfile.write("CObeddy = ", 0, 10);
			characterfile.write(Integer.toString(p.CObeddy), 0, Integer.toString(p.CObeddy).length());
			characterfile.newLine();
			characterfile.newLine();
			characterfile.write("summonTime = ", 0, 13);
			characterfile.write(Integer.toString(p.summonTime), 0, Integer.toString(p.summonTime).length());
			characterfile.newLine();
			characterfile.write("hasHousee = ", 0, 12);
			characterfile.write(Boolean.toString(p.hasHousee), 0, Boolean.toString(p.hasHousee).length());
			characterfile.newLine();
			characterfile.write("effigy = ", 0, 9);
			characterfile.write(Integer.toString(p.effigy), 0, Integer.toString(p.effigy).length());
			characterfile.newLine();
			characterfile.write("shopcollect = ", 0, 14);
			characterfile.write(Integer.toString(p.playerCollect), 0, Integer.toString(p.playerCollect).length());
			characterfile.newLine();
			characterfile.write("santaPrize = ", 0, 13);
			characterfile.write(Integer.toString(p.santaPrize), 0, Integer.toString(p.santaPrize).length());
			characterfile.newLine();

			characterfile.write("ppsLeft = ", 0, 10);
			characterfile.write(Integer.toString(p.ppsLeft), 0, Integer.toString(p.ppsLeft).length());
			characterfile.newLine();
			characterfile.write("Wheel = ", 0, 8);
			characterfile.write(Integer.toString(p.Wheel), 0, Integer.toString(p.Wheel).length());
			characterfile.newLine();	
			characterfile.write("spinsLe = ", 0, 10);
			characterfile.write(Integer.toString(p.spinsLe), 0, Integer.toString(p.spinsLe).length());
			characterfile.newLine();
			characterfile.write("mute-end = ", 0, 11);
			characterfile.write(Long.toString(p.muteEnd), 0, Long.toString(p.muteEnd).length());
			characterfile.newLine();
			characterfile.write("staffLogin = ", 0, 13);
			characterfile.write(Long.toString(p.staffLogin), 0, Long.toString(p.staffLogin).length());
			characterfile.newLine();
			characterfile.write("present = ", 0, 10);
			characterfile.write(Integer.toString(p.present), 0, Integer.toString(p.present).length());
			characterfile.newLine();
			characterfile.write("PresentsLe = ", 0, 13);
			characterfile.write(Integer.toString(p.PresentsDone), 0, Integer.toString(p.PresentsDone).length());
			characterfile.newLine();
			characterfile.write("Aura = ", 0, 7);
			characterfile.write(Integer.toString(p.AuraEquiped), 0, Integer.toString(p.AuraEquiped).length());
			characterfile.newLine();
			characterfile.write("crystal-bow-shots = ", 0, 20);
			characterfile.write(Integer.toString(p.crystalBowArrowCount), 0, Integer.toString(p.crystalBowArrowCount).length());
			characterfile.newLine();
			characterfile.write("VLS-hits = ", 0, 11);
			characterfile.write(Integer.toString(p.degradeTime), 0, Integer.toString(p.degradeTime).length());
			characterfile.newLine(); 
			characterfile.write("skull-timer = ", 0, 14);
			characterfile.write(Integer.toString(p.skullTimer), 0, Integer.toString(p.skullTimer).length());
			characterfile.newLine();
			characterfile.write("MoneyOrb = ", 0, 11);
			characterfile.write(Integer.toString(p.MoneyCash), 0, Integer.toString(p.MoneyCash).length());
			characterfile.newLine();	
			characterfile.write("LoyaltyScore = ", 0, 15);
			characterfile.write(Integer.toString(p.LoyaltyScore), 0, Integer.toString(p.LoyaltyScore).length());
			characterfile.newLine();	
			characterfile.write("has-npc = ", 0, 10);
			characterfile.write(Boolean.toString(p.hasNpc), 0, Boolean.toString(p.hasNpc).length());
			characterfile.newLine();
			characterfile.write("summonId = ", 0, 11);
			characterfile.write(Integer.toString(p.summonId), 0, Integer.toString(p.summonId).length());
			characterfile.newLine();
			characterfile.write("tempid = ", 0, 9);
			characterfile.write(Integer.toString(p.tempid), 0, Integer.toString(p.tempid).length());
			characterfile.newLine();
			characterfile.write("tempamt = ", 0, 10);
			characterfile.write(Integer.toString(p.tempamt), 0, Integer.toString(p.tempamt).length());
			characterfile.newLine();
			characterfile.write("tempprice = ", 0, 12);
			characterfile.write(Integer.toString(p.tempprice), 0, Integer.toString(p.tempprice).length());
			characterfile.newLine();
			characterfile.write("EP = ", 0, 5);
			characterfile.write(Integer.toString(p.earningPotential), 0, Integer.toString(p.earningPotential).length());
			characterfile.newLine();
			characterfile.write("VL = ", 0, 5);
			characterfile.write(Integer.toString(p.vlsLeft2), 0, Integer.toString(p.vlsLeft2).length());
			characterfile.newLine();	
			characterfile.write("AP = ", 0, 5);
			characterfile.write(Integer.toString(p.spoints), 0, Integer.toString(p.spoints).length());
			characterfile.newLine();
			characterfile.write("character-longsword = ", 0, 22);
			characterfile.write(Integer.toString(p.vlsLeft), 0, Integer.toString(p.vlsLeft).length());
			characterfile.newLine();
			characterfile.write("character-warhammer = ", 0, 22);
			characterfile.write(Integer.toString(p.statLeft), 0, Integer.toString(p.statLeft).length());
			characterfile.newLine();
			characterfile.write("character-spear = ", 0, 18);
			characterfile.write(Integer.toString(p.vSpearLeft), 0, Integer.toString(p.vSpearLeft).length());
			characterfile.newLine();
			characterfile.write("character-chainbody = ", 0, 22);
			characterfile.write(Integer.toString(p.vTopLeft), 0, Integer.toString(p.vTopLeft).length());
			characterfile.newLine();
			characterfile.write("character-chainskirt = ", 0, 23);
			characterfile.write(Integer.toString(p.vLegsLeft), 0, Integer.toString(p.vLegsLeft).length());
			characterfile.newLine();
			characterfile.write("character-full helm = ", 0, 22);
			characterfile.write(Integer.toString(p.sHelmLeft), 0, Integer.toString(p.sHelmLeft).length());
			characterfile.newLine();
			characterfile.write("character-platebody = ", 0, 22);
			characterfile.write(Integer.toString(p.sTopLeft), 0, Integer.toString(p.sTopLeft).length());
			characterfile.newLine();
			characterfile.write("character-platelegs = ", 0, 22);
			characterfile.write(Integer.toString(p.sLegsLeft), 0, Integer.toString(p.sLegsLeft).length());
			characterfile.newLine();
			characterfile.write("character-hood = ", 0, 17);
			characterfile.write(Integer.toString(p.zHoodLeft), 0, Integer.toString(p.zHoodLeft).length());
			characterfile.newLine();
			characterfile.write("character-staff = ", 0, 18);
			characterfile.write(Integer.toString(p.zStaffLeft), 0, Integer.toString(p.zStaffLeft).length());
			characterfile.newLine();
			characterfile.write("character-robe top = ", 0, 21);
			characterfile.write(Integer.toString(p.zTopLeft), 0, Integer.toString(p.zTopLeft).length());
			characterfile.newLine();
			characterfile.write("character-robe bottom = ", 0, 24);
			characterfile.write(Integer.toString(p.zBottomLeft), 0, Integer.toString(p.zBottomLeft).length());
			characterfile.newLine();
			characterfile.write("character-leather body = ", 0, 25);
			characterfile.write(Integer.toString(p.mBodyLeft), 0, Integer.toString(p.mBodyLeft).length());
			characterfile.newLine();
			characterfile.write("character-chaps = ", 0, 18);
			characterfile.write(Integer.toString(p.mChapsLeft), 0, Integer.toString(p.mChapsLeft).length());
			characterfile.newLine();
			characterfile.write("magic-book = ", 0, 13);
			characterfile.write(Integer.toString(p.playerMagicBook), 0, Integer.toString(p.playerMagicBook).length());
			characterfile.newLine();
			for (int b = 0; b < p.barrowsNpcs.length; b++) {
				characterfile.write("brother-info = ", 0, 15);
				characterfile.write(Integer.toString(b), 0, Integer.toString(b).length());
				characterfile.write("	", 0, 1);
				characterfile.write(p.barrowsNpcs[b][1] <= 1 ? Integer.toString(0) : Integer.toString(p.barrowsNpcs[b][1]), 0, Integer.toString(p.barrowsNpcs[b][1]).length());
				characterfile.newLine();
			}	
			characterfile.write("special-amount = ", 0, 17);
			characterfile.write(Double.toString(p.specAmount), 0, Double.toString(p.specAmount).length());
			characterfile.newLine();
			characterfile.write("selected-coffin = ", 0, 18);
			characterfile.write(Integer.toString(p.randomCoffin), 0, Integer.toString(p.randomCoffin).length());
			characterfile.newLine();
			characterfile.write("barrows-killcount = ", 0, 20);
			characterfile.write(Integer.toString(p.barrowsKillCount), 0, Integer.toString(p.barrowsKillCount).length());
			characterfile.newLine();
			characterfile.write("teleblock-length = ", 0, 19);
			characterfile.write(Integer.toString(tbTime), 0, Integer.toString(tbTime).length());
			characterfile.newLine();
			characterfile.write("gwdelay = ", 0, 10);
			characterfile.write(Integer.toString(p.gwdelay), 0, Integer.toString(p.gwdelay).length());
			characterfile.newLine();
			characterfile.write("dungRest = ", 0, 10);
			characterfile.write(Integer.toString(p.dungRest), 0, Integer.toString(p.dungRest).length());
			characterfile.newLine();
			characterfile.write("TuberCC = ", 0, 10);
			characterfile.write(Integer.toString(p.TuberCC), 0, Integer.toString(p.TuberCC).length());
			characterfile.newLine();
			characterfile.write("Altar = ", 0, 8);
			characterfile.write(Integer.toString(p.altarPrayed), 0, Integer.toString(p.altarPrayed).length());
			characterfile.newLine();
			characterfile.write("Arma-KC = ", 0, 10);
			characterfile.write(Integer.toString(p.Arma), 0, Integer.toString(p.Arma).length());
			characterfile.newLine();
			characterfile.write("Band-KC = ", 0, 10);
			characterfile.write(Integer.toString(p.Band), 0, Integer.toString(p.Band).length());
			characterfile.newLine();
			characterfile.write("Zammy-KC = ", 0, 11);
			characterfile.write(Integer.toString(p.Zammy), 0, Integer.toString(p.Zammy).length());
			characterfile.newLine();
			characterfile.write("Sara-KC = ", 0, 10);
			characterfile.write(Integer.toString(p.Sara), 0, Integer.toString(p.Sara).length());
			characterfile.newLine();
			characterfile.write("usedxplock = ", 0, 13);
			characterfile.write(Boolean.toString(p.usedxplock), 0, Boolean.toString(p.usedxplock).length());
			characterfile.newLine();
			characterfile.write("yelltag = ", 0, 10);
			characterfile.write(p.playeryelltag, 0, p.playeryelltag.length());
			characterfile.newLine();
			characterfile.write("yellcolor = ", 0, 12);
			characterfile.write(p.playeryellcolor, 0, p.playeryellcolor.length());
			characterfile.newLine();
			characterfile.write("yellset = ", 0, 10);
			characterfile.write(Integer.toString(p.yellSet), 0, Integer.toString(p.yellSet).length());
			characterfile.newLine();
			characterfile.write("slayerTask = ", 0, 13);
			characterfile.write(Integer.toString(p.slayerTask), 0, Integer.toString(p.slayerTask).length());
			characterfile.newLine(); 
			characterfile.write("xpLock = ", 0, 9);
			characterfile.write(Boolean.toString(p.xpLock), 0, Boolean.toString(p.xpLock).length());
			characterfile.newLine();
			characterfile.write("Agrith = ", 0, 9);
			characterfile.write(Boolean.toString(p.Agrith), 0, Boolean.toString(p.Agrith).length());
			characterfile.newLine();
			characterfile.write("Flambeed = ", 0, 11);
			characterfile.write(Boolean.toString(p.Flambeed), 0, Boolean.toString(p.Flambeed).length());
			characterfile.newLine();
			characterfile.write("Karamel = ", 0, 10);
			characterfile.write(Boolean.toString(p.Karamel), 0, Boolean.toString(p.Karamel).length());
			characterfile.newLine();
			characterfile.write("Dessourt = ", 0, 11);
			characterfile.write(Boolean.toString(p.Dessourt), 0, Boolean.toString(p.Dessourt).length());
			characterfile.newLine();
			characterfile.write("culin = ", 0, 8);
			characterfile.write(Boolean.toString(p.Culin), 0, Boolean.toString(p.Culin).length());
			characterfile.newLine();
			characterfile.write("Nomad = ", 0, 8);
			characterfile.write(Boolean.toString(p.Nomad), 0, Boolean.toString(p.Nomad).length());
			characterfile.newLine();
			characterfile.write("Goblin = ", 0, 8);
			characterfile.write(Boolean.toString(p.Goblin), 0, Boolean.toString(p.Goblin).length());
			characterfile.newLine();
			characterfile.write("sir = ", 0, 6);
			characterfile.write(Boolean.toString(p.sir), 0, Boolean.toString(p.sir).length());
			characterfile.newLine();
			characterfile.write("vote = ", 0, 7);
			characterfile.write(Integer.toString(p.vote), 0, Integer.toString(p.vote).length());
			characterfile.newLine();
			characterfile.write("hasBankPin = ", 0, 13);
			characterfile.write(Boolean.toString(p.hasBankPin), 0, Boolean.toString(p.hasBankPin).length());
			characterfile.newLine();
			characterfile.write("bankPin1 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin1), 0, Integer.toString(p.bankPin1).length());
			characterfile.newLine();
			characterfile.write("bankPin2 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin2), 0, Integer.toString(p.bankPin2).length());
			characterfile.newLine();
			characterfile.write("bankPin3 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin3), 0, Integer.toString(p.bankPin3).length());
			characterfile.newLine();
			characterfile.write("bankPin4 = ", 0, 11);
			characterfile.write(Integer.toString(p.bankPin4), 0, Integer.toString(p.bankPin4).length());
			characterfile.newLine();
			characterfile.write("taskAmount = ", 0, 13);
			characterfile.write(Integer.toString(p.taskAmount), 0, Integer.toString(p.taskAmount).length());
			characterfile.newLine();
			/*	characterfile.write("KC = ", 0, 4);
			characterfile.write(Integer.toString(p.KC), 0, Integer.toString(p.KC).length());
			characterfile.newLine();
			characterfile.write("DC = ", 0, 4);
			characterfile.write(Integer.toString(p.DC), 0, Integer.toString(p.DC).length());
			characterfile.newLine();*/
			characterfile.write("kingQuest = ", 0, 12);
			characterfile.write(Integer.toString(p.kingQuest), 0, Integer.toString(p.kingQuest).length());
			characterfile.newLine();
			/*	characterfile.write("runeQuest = ", 0, 12);
			characterfile.write(Integer.toString(p.rMQ), 0, Integer.toString(p.rMQ).length());
			characterfile.newLine();
			characterfile.write("herbQuest = ", 0, 12);
			characterfile.write(Integer.toString(p.bMQ), 0, Integer.toString(p.bMQ).length());
			characterfile.newLine();*/
			characterfile.write("totalstored = ", 0, 14);
			characterfile.write(Integer.toString(p.totalstored), 0, Integer.toString(p.totalstored).length());
			characterfile.newLine();
			characterfile.write("autoRet = ", 0, 10);
			characterfile.write(Integer.toString(p.autoRet), 0, Integer.toString(p.autoRet).length());
			characterfile.newLine();
			characterfile.write("trade11 = ", 0, 10);
			characterfile.write(Integer.toString(p.trade11), 0, Integer.toString(p.trade11).length());
			characterfile.newLine();
			characterfile.write("SpeDelay = ", 0, 11);
			characterfile.write(Long.toString(p.SpecialDelay), 0, Long.toString(p.SpecialDelay).length());
			characterfile.newLine();
			characterfile.write("barrowskillcount = ", 0, 19);
			characterfile.write(Integer.toString(p.barrowsKillCount), 0, Integer.toString(p.barrowsKillCount).length());
			characterfile.newLine();
			/*	characterfile.write("flagged = ", 0, 10);
			characterfile.write(Boolean.toString(p.accountFlagged), 0, Boolean.toString(p.accountFlagged).length());
			characterfile.newLine();
			characterfile.write("Rules = ", 0, 8);
			characterfile.write(Boolean.toString(p.readRules), 0, Boolean.toString(p.readRules).length());
			characterfile.newLine();*/
			characterfile.write("shopping = ", 0, 11);
			characterfile.write(Boolean.toString(p.isShopping), 0, Boolean.toString(p.isShopping).length());
			characterfile.newLine();
			characterfile.write("Jailed = ", 0, 9);
			characterfile.write(Boolean.toString(p.Jail), 0, Boolean.toString(p.Jail).length());
			characterfile.newLine();
			characterfile.write("hasChoosenDung = ", 0, 17);
			characterfile.write(Boolean.toString(p.hasChoosenDung), 0, Boolean.toString(p.hasChoosenDung).length());
			characterfile.newLine();
			characterfile.write("wave = ", 0, 7);
			characterfile.write(Integer.toString(p.waveId), 0, Integer.toString(p.waveId).length());
			characterfile.newLine();
			characterfile.write("dfs-charges = ", 0, 14);
			characterfile.write(Integer.toString(p.dfsCount), 0, Integer.toString(p.dfsCount).length());
			characterfile.newLine();
			characterfile.write("hasFollower = ", 0, 14);
			characterfile.write(Integer.toString(p.hasFollower), 0, Integer.toString(p.hasFollower).length());
			characterfile.newLine();
			characterfile.write("summoningnpcid = ", 0, 17);
			characterfile.write(Integer.toString(p.summoningnpcid), 0, Integer.toString(p.summoningnpcid).length());
			characterfile.newLine();
			characterfile.write("fightMode = ", 0, 12);
			characterfile.write(Integer.toString(p.fightMode), 0, Integer.toString(p.fightMode).length());
			characterfile.newLine();

			/*EQUIPMENT*/
			characterfile.write("[ACHIEVEMENT]", 0, 13);
			characterfile.newLine();
			characterfile.write("achieved = ", 0, 11);
			String toWrite1 = "";
			for(int i1 = 0; i1 < p.achieved.length; i1++) {
				toWrite1 += p.achieved[i1] +"\t";
			}
			characterfile.write(toWrite1);
			characterfile.newLine();
			characterfile.write("achievement = ", 0, 14);
			String toWrite2 = "";
			for(int i1 = 0; i1 < p.achievement.length; i1++) {
				toWrite2 += p.achievement[i1] +"\t";
			}
			characterfile.write(toWrite2);
			characterfile.newLine();
			characterfile.write("points = ", 0, 9);
			characterfile.write(Integer.toString(p.achievementPoints), 0, Integer.toString(p.achievementPoints).length());
			characterfile.newLine();
			characterfile.write("[EQUIPMENT]", 0, 11);
			characterfile.newLine();
			for (int i = 0; i < p.playerEquipment.length; i++) {
				characterfile.write("character-equip = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipment[i]), 0, Integer.toString(p.playerEquipment[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerEquipmentN[i]), 0, Integer.toString(p.playerEquipmentN[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.newLine();
				characterfile.newLine();

			}
			characterfile.newLine();

			/*LOOK*/
			characterfile.write("[LOOK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.playerAppearance.length; i++) {
				characterfile.write("character-look = ", 0, 17);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerAppearance[i]), 0, Integer.toString(p.playerAppearance[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/*SKILLS*/
			characterfile.write("[SKILLS]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < p.playerLevel.length; i++) {
				characterfile.write("character-skill = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerLevel[i]), 0, Integer.toString(p.playerLevel[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.playerXP[i]), 0, Integer.toString(p.playerXP[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/*ITEMS*/
			characterfile.write("[ITEMS]", 0, 7);
			characterfile.newLine();
			for (int i = 0; i < p.playerItems.length; i++) {
				if (p.playerItems[i] > 0) {
					characterfile.write("character-item = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItems[i]), 0, Integer.toString(p.playerItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerItemsN[i]), 0, Integer.toString(p.playerItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/*BANK*/
			characterfile.write("[BANK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.bankItems.length; i++) {
				if (p.bankItems[i] > 0) {
					characterfile.write("character-bank = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItems[i]), 0, Integer.toString(p.bankItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.bankItemsN[i]), 0, Integer.toString(p.bankItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/*FRIENDS*/
			characterfile.write("[FRIENDS]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < p.friends.length; i++) {
				if (p.friends[i] > 0) {
					characterfile.write("character-friend = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write("" + p.friends[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/*Storeditems*/
			characterfile.write("[STORED]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < p.storeditems.length; i++) {
				characterfile.write("stored = ", 0, 9);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(p.storeditems[i]), 0, Integer.toString(p.storeditems[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/*Storeditems*/
			characterfile.write("[OCCUPY]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < p.occupied.length; i++) {
				characterfile.write("occupy = ", 0, 9);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Boolean.toString(p.occupied[i]), 0, Boolean.toString(p.occupied[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();


			/*SHOP*/
			characterfile.write("[SHOP]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < p.playerShop.length; i++) {
				if (p.playerShop[i] > 0) {
					characterfile.write("character-shop = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerShop[i]), 0, Integer.toString(p.playerShop[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerShopP[i]), 0, Integer.toString(p.playerShopP[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(p.playerShopN[i]), 0, Integer.toString(p.playerShopN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();



			/*IGNORES*/
			/*characterfile.write("[IGNORES]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < ignores.length; i++) {
				if (ignores[i] > 0) {
					characterfile.write("character-ignore = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Long.toString(ignores[i]), 0, Long.toString(ignores[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();*/
			/*EOF*/
			characterfile.write("[EOF]", 0, 5);
			characterfile.newLine();
			characterfile.newLine();
			characterfile.close();
		} catch(IOException ioexception) {
			Misc.println(p.playerName+": error writing file.");
			return false;
		}
		return true;
	}	


}