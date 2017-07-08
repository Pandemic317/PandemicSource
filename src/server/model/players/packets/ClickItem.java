package server.model.players.packets;

import server.util.Misc;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.Player;
import server.model.players.PlayerAssistant;
import server.model.players.PlayerHandler;
import server.Server;
import server.Config;
import server.model.players.Content.MysteryBox;
import server.model.minigames.*;


/**
 * Clicking an item, bury bone, eat food etc
 **/
@SuppressWarnings("all")
public class ClickItem implements PacketType {
                      
public static int flower[] = {2980,2981,2982,2983,2984,2985,2986,2987};
            public int randomflower() {
                return flower[(int)(Math.random()*flower.length)];
            }
	public static int flowerX = 0;
	public static int flowerY = 0;
	public static int flowerTime = -1;
	public static int flowers = 0;
	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		c.setLastClicked(itemId);
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
			
		}

		if(itemId == 15098) {
c.sendMessage("<col=255>This feature has been disabled! Please use the clan-roll.");
/*		if(c.inWild()) {
		        c.sendMessage("Er, it's not to smart to do this in the Wilderness.");
			return;
                }
				if (System.currentTimeMillis() - c.diceDelay >= 1200) {
                c.forcedText = "["+ c.playerName +"] Just Rolled "+ Misc.random(100) +" On The Dice!";
                c.forcedChatUpdateRequired = true;
            	c.updateRequired = true;
				c.diceDelay = System.currentTimeMillis();
}*/
}
                                           if (itemId == 5021){
		c.getItems().addItem(995, 1000000000);
			c.getItems().deleteItem(5021,1);
			c.sendMessage("You Redeem Your Ticket!");
        }
        if (itemId == 5021 && c.getItems().playerHasItem(995,2147483647)) {
        	c.sendMessage("You already have a full cash stack!");
        }
        if (itemId == 6605) {
        	if (c.npcKills > 99) {
            	c.getItems().deleteItem(itemId, 1);
        	c.getItems().addItem(6607, 1);
        	c.sendMessage("Congratulations! You have just upgraded your GLOCK!");
        }
        	if (c.npcKills < 100) {
        	c.sendMessage("You need at least 100 NPC Kills to upgrade your GLOCK!");
        }
        }
        if (itemId == 6607) {
        	if (c.npcKills > 499) {
            	c.getItems().deleteItem(itemId, 1);
        	c.getItems().addItem(6609, 1);
        	c.sendMessage("Congratulations! You have just upgraded your GLOCK!");
        } if (c.npcKills < 500){
        	c.sendMessage("You need at least 500 NPC Kills to upgrade your GLOCK!");
        }
        }
        if (itemId == 6609) {
        	if (c.npcKills > 999) {
            	c.getItems().deleteItem(itemId, 1);
        	c.getItems().addItem(6611, 1);
        	c.sendMessage("Congratulations! You have just upgraded your GLOCK!");
        }  if (c.npcKills < 1000){
        	c.sendMessage("You need at least 1000 NPC Kills to upgrade your GLOCK!");
        }
        }
        if (itemId == 6611) {
        	if (c.npcKills > 2499) {
            	c.getItems().deleteItem(itemId, 1);
        	c.getItems().addItem(6613, 1);
        	c.sendMessage("Congratulations! You have just upgraded your GLOCK!");
        }  if (c.npcKills < 2500){
        	c.sendMessage("You need at least 2500 NPC Kills to upgrade your GLOCK!");
        }
        }
        if (itemId == 621) {
        	if (c.Dicer >= 1) {
        		c.sendMessage("You have already claimed the Dice Rank");
        		return;
        	}
        	c.Dicer = 1;
        	c.getItems().deleteItem(621, 1);
        	for (int j = 0; j < PlayerHandler.players.length; j++) {
        		if (PlayerHandler.players[j] != null) {
            			Client c2 = (Client)PlayerHandler.players[j];
            		c2.sendMessage("<col=800000000>The Dice rank has just been claimed by "+ c.playerName +"!</col>");
        		}
        		}
        }
        if (itemId == 4067 && c.getItems().playerHasItem(995,2147483647)) {
        	c.sendMessage("You already have a full cash stack!");
        }
        if (itemId == 3527){
        	c.getItems().deleteItem(3527, 1);
        	c.getItems().addItem(989, 1);
        }
                                           if (itemId == 4067){
		c.getItems().addItem(995, 2000000000);
			c.getItems().deleteItem(4067,1);
			c.sendMessage("You Redeem Your Ticket!");
		}
        /*                                   if (itemId == 4810){
        c.getItems().deleteItem(4810,1);
		c.getItems().addItem(299, 100);
			c.sendMessage("You Redeem 100 Mithril Seeds!");
		}*/
                                           if (itemId == 13451){
                  if (c.getItems().playerHasItem(6644)) {
        c.getItems().deleteItem(13451,1);
		c.getItems().addItem(13453, 1);
		c.startAnimation(3157);
		c.gfx100(559);
			c.sendMessage("You charge your Ice Fury!");
		} else 
			c.sendMessage("You need a blue crystal to complete the ritual.");
                                           }

                                           if (itemId == 13453){
        c.getItems().deleteItem(13453,1);
		c.getItems().addItem(13451, 1);
        c.getPA().teleTabTeleport(3052, 9577, 0, "teleTab");
			c.sendMessage("You teleport and your Ice Fury loses it's charge.");
		}

                                           if (itemId == 896 || itemId == 773){
                	  c.getPA().movePlayer(2772, 9340, 0);
			c.sendMessage("<col=800000000>You teleport to the Owner Cape zone!");
		}
                                           
                                          if (itemId == 5022){
			c.sendMessage("You Redeem Your Ticket!");
		c.getItems().addItem(995, 100000000);
			c.getItems().deleteItem(5022,1);
		}
                                          if (itemId == 18626){
		                c.getPA().teleTabTeleport(2916, 3628 ,20, "teleTab");
				c.sendMessage("Drops DeathTouch Darts, Ice Katana + Ornamental armoury!");
				c.gfx0(2280);
		}

		if(itemId == 15262) {
                   c.getItems().deleteItem(15262, 1);
                   c.getItems().addItem(18016, 15000);
                }   // SUMMON SHARDS
		if (itemId == 15084){
			c.getDH().sendDialogues(106, 4289);
		}
                                           if (itemId == 6722){
			c.sendMessage("You Redeem Your Spin Token For one spin!");
                                                                c.spinsLe += 1;
		}

if (itemId == 18778){
c.getDH().sendDialogues(33, 0);
}
		if (itemId == 18779){
c.getDH().sendDialogues(33, 0);
		}
		if (itemId >= 5070 && itemId <= 5074){
			c.getItems().deleteItem(itemId, c.getItems().getItemSlot(itemId), 1);
			c.getItems().addItem(5075, 1);
			c.getItems().addItem(c.getPA().randomBirdNests(), 10);
		}
		if (itemId == 14162) {
			c.getItems().deleteItem(itemId, c.getItems().getItemSlot(itemId), 1);
			c.getItems().addItem(386, 10);
		}
 		if (itemId == 13663 && c.playerRights == 0){
			c.isDonator = 1;
			c.issDonator = 1;
			c.playerRights = 5;
			c.getItems().deleteItem(13663,1);
                	c.sendMessage("You claim your Super donator rank!");
            }
 		if (itemId == 13663 && c.playerRights == 4){
			c.isDonator = 1;
			c.issDonator = 1;
			c.playerRights = 5;
			c.getItems().deleteItem(13663,1);
                	c.sendMessage("You claim your Super donator rank!");
            }
 		if (itemId == 14700 && c.playerRights == 0){
			c.isDonator = 1;
			c.issDonator = 2;
			c.playerRights = 5;
			c.getItems().deleteItem(14700,1);
                	c.sendMessage("You claim your Extreme donator rank!");
            }
 		if (itemId == 14700 && c.playerRights == 4){
			c.isDonator = 1;
			c.issDonator = 2;
			c.playerRights = 5;
			c.getItems().deleteItem(14700,1);
                	c.sendMessage("You claim your Extreme donator rank!");
            }
 		if (itemId == 14700 && c.playerRights == 4 && c.issDonator == 1){
			c.isDonator = 1;
			c.issDonator = 2;
			c.playerRights = 5;
			c.getItems().deleteItem(14700,1);
                	c.sendMessage("You claim your Extreme donator rank!");
	    }
 		if (itemId == 13663 && c.playerRights <= 3 && c.playerRights >= 5){
                	c.sendMessage("You cannot claim this ticket");
            }
 		if (itemId == 5520){
 			if (System.currentTimeMillis() - c.lastThieve < 5500) {
 				return;
 			}
 			if (c.Arma < 2) {
 			c.Arma = 2;
 			c.gfx0(343);
 			c.startAnimation(722);
 			c.sendMessage("<col=800000000><shad>You enchant yourself with the use your Abyssal Book.");
 			c.forcedChat("Ikles Verutio Abyss Unde aro");
 			c.lastThieve = System.currentTimeMillis();
 			return;
 			} 
 			if (c.Arma >= 2){
 				c.getDH().sendOption2("I know I can lose my stuff when I die here", "Nevermind");
 				c.dialogueAction = 11170;
 				return;
            }
 		}
 		if (itemId == 5020){
 			c.getItems().deleteItem(5020, 1);
			c.donatorChest ++;
            c.sendMessage("<col=800000000>You claimed 1 donator point!");
            }
		if (itemId == 19865) {
			if (System.currentTimeMillis() - c.buryDelay > 500) {
			if (c.getItems().freeSlots() >= 1 ) {
			c.getItems().deleteItem(itemId, 1);
			int[] arrayOfC4 ={12265,12266,12267,12274,12275,12276,12277,12278,12279,12280,12281,12282,
					12265,12266,12267,12274,12275,12276,12277,12278,12279,12280,12281,12282,
					12265,12266,12267,12274,12275,12276,12277,12278,12279,12280,12281,12282,
					12265,12266,12267,12274,12275,12276,12277,12278,12279,12280,12281,12282,
					12265,12266,12267,12274,12275,12276,12277,12278,12279,12280,12281,12282,
					12286,12287,12288,1071,1159,1121};
			c.getItems().addItem(arrayOfC4[Misc.random(arrayOfC4.length -1)], 1);
			c.sendMessage("<col=255>You opened your Super Torva Box!");
			c.buryDelay = System.currentTimeMillis();
					} else {
						c.sendMessage("You need at least 1 free slot in your inventory!");
					}
		}
		}
		if (itemId == 19867) {
			if (System.currentTimeMillis() - c.buryDelay > 500) {
			if (c.getItems().freeSlots() >= 1 ) {
			c.getItems().deleteItem(itemId, 1);
			int[] arrayOfC4 ={11660,12257,12260,12263,12269,12272,12281,12284,12290,12266,11661,12258,12261,12264,12270,12273,12282,12285,
					12291,12267,11662,12256,12259,12262,12268,12271,12280,12283,12289,12265};
			c.getItems().addItem(arrayOfC4[Misc.random(arrayOfC4.length -1)], 1);
			c.sendMessage("<col=255>You opened your Torva Box!");
			c.buryDelay = System.currentTimeMillis();
					} else {
						c.sendMessage("You need at least 1 free slot in your inventory!");
					}
		}
		}
		
		if (itemId == 10025) {
			if (System.currentTimeMillis() - c.buryDelay > 500) {
			if (c.getItems().freeSlots() >= 1 ) {
			c.getItems().deleteItem(itemId, 1);
			int[] arrayOfC4 ={};
			c.getItems().addItem(arrayOfC4[Misc.random(arrayOfC4.length -1)], 1);
			c.sendMessage("<col=255>You opened your box!");
			c.buryDelay = System.currentTimeMillis();
					} else {
						c.sendMessage("You need at least 1 free slot in your inventory!");
					}
		}
		}
            
		if (itemId == 18780){
			c.getDH().sendDialogues(33, 0);
	}
		if (itemId == 15522){
			c.getPA().addSkillXP(25000, c.playerMining);
			c.getItems().deleteItem(15522, 1);
			c.sendMessage("You receive 25.000 mining xp from the Strange Rock.");
	}
		if (itemId == 18781){
					c.getDH().sendDialogues(33, 0);
			}
			
		if (itemId == 18782){
			c.getPA().showInterface(2808);
			c.sendMessage("Once you click the skill desired your experience will be given. Choose carefully.");
		}

		if (itemId == 3549) {
			if (System.currentTimeMillis() - c.buryDelay > 500) {
c.getItems().deleteItem(3549, 1);
c.getItems().addItemToBank(1038, 1);
c.getItems().addItemToBank(1040, 1);
c.getItems().addItemToBank(1042, 1);
c.getItems().addItemToBank(1044, 1);
c.getItems().addItemToBank(1046, 1);
c.getItems().addItemToBank(1048, 1);
c.sendMessage("<col=255>The partyhat set has been placed in your bank!");
c.buryDelay = System.currentTimeMillis();
		}
		}
		if ((itemId == 3551) || (itemId == 3529)) {
			if (System.currentTimeMillis() - c.buryDelay > 500) {
			if (c.getItems().freeSlots() >= 1 ) {
			c.getItems().deleteItem(itemId, 1);//casket I
			int[] arrayOfC1 ={14921,11615,11616,11614,19943,19112,19113,19114,19036,19037,19038,19041,19040,11728,11147,11148,11142,11143,11144,11145,
							  11147,20098,4632,3809,20102,20103,20101,13495,13492,13493,4804,4805,4806,13826,13828,13830,9895,934,5201,20106,20107,20105,
							  11638,17881,13356,13344,13342,13340,13370,20110,20100,20111,20109,919,7783,5205,5206,938,13197,4642,918,4646,16481,12241,
							  12242,20114,20104,20115,20113,13198,3273,3275,3277,3279,3281,3283,3285,3287,3289,3291,3293,3295,3297,3299,3305,3307,3309,
							  3311,3313,3315,3317,3319,3321,3323,19478,19477,4640,13832,13834,13836,19558,5211,898,6499,6196,20114,20100,20108,13824,930,
							  20155,17865,4767,20112,4645,4633,19960,19918,979,19959,4635,4636,18384,19476,14390,939,4651,3813,19936,11593,11592,11591,
							  11590,11627,9898,19933,9374,4643,4644,19753,968,11634,4060,4061,5212};
			c.getItems().addItem(arrayOfC1[Misc.random(arrayOfC1.length -1)], 1);
			c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
			c.buryDelay = System.currentTimeMillis();
					} else {
						c.sendMessage("You need at least 1 free slot in your inventory!");
					}
		}
		}
			if ((itemId == 3553) || (itemId == 3531)) {
				if (System.currentTimeMillis() - c.buryDelay > 500) {
				if (c.getItems().freeSlots() >= 1 ) {
				c.getItems().deleteItem(itemId, 1); //casket II
				int[] arrayOfC2 ={4806,13826,13828,13830,9895,934,5201,20106,20107,20105, 11638,17881,13356,13344,13342,13340,13370,
								  20110,20100,20111,20109,919,7783,5205,5206,938,13197,4642,918,4646,16481,12241,14445,12242,20114,20104,20115,
								  20113,13198,3273,3275,3277,3279,19918,979,19959,4635,4636,18384,19476,14390,939,4651,3813,19936,
								  11593,11592,11591,11590,11627,9898,19933,9374,4643,4644,19753,968,11634,4060,4061,5212,4637,19953,5213,
								  5214,5215,5216,5217,5218,4634,13997,13998,13999,14000,14001,14002,14003,14005,14004,14006,14007,14008,14009,14010,
								  14011,17837,5204,4641,18388,4058,11630,4638,5200,17867,9897,19951,4639,11626,20254,933,4056,1000,4063,14445};
				c.getItems().addItem(arrayOfC2[Misc.random(arrayOfC2.length -1)], 1);
				c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
				c.buryDelay = System.currentTimeMillis();
						} else {
							c.sendMessage("You need at least 1 free slot in your inventory!");
						}
			}
			}
				if ((itemId == 3555) || (itemId == 3533)) {
					if (System.currentTimeMillis() - c.buryDelay > 500) {
					if (c.getItems().freeSlots() >= 1 ) {
					c.getItems().deleteItem(itemId, 1); //casket III
					int[] arrayOfC3 ={3813,19936,19961,19111,19958,6196,6189,6190,6191,6192,6193,5185,5186,5187,5188,5189,5190,4652,19952,
									  11593,11592,11591,11590,11627,9898,19933,9374,4643,4644,19753,968,11634,4060,4061,5212,4637,19953,5213,
									  5214,5215,5216,5217,5218,4634,13997,13998,13999,14000,14001,14002,14003,14005,14004,14006,14007,14008,14009,14010,
									  14011,17837,5204,4641,18388,4058,11630,4638,5200,17867,9897,19951,4639,11626,20254,933,4056,1000,4063,
									  15017,13362,19915,13360,19914,13358,13355,13354,13352,13350,861,13348,13346,19916,12240,4765,17848};
					c.getItems().addItem(arrayOfC3[Misc.random(arrayOfC3.length -1)], 1);
					c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
					c.buryDelay = System.currentTimeMillis();
							} else {
								c.sendMessage("You need at least 1 free slot in your inventory!");
							}
				}
				}
					if ((itemId == 3557) || (itemId == 3535)) {
						if (System.currentTimeMillis() - c.buryDelay > 500) {
						if (c.getItems().freeSlots() >= 1 ) {
						c.getItems().deleteItem(itemId, 1); //casket IV
						int[] arrayOfC4 ={17837,5204,4641,18388,4058,11630,4638,5200,17867,9897,19951,4639,11626,20254,933,4056,1000,4063,
										  15017,13362,19915,13360,19914,13358,13355,13354,13352,13350,861,13348,13346,19916,12240,4765,11607,11608,
										  11609,11610,11611,11612,19957,20141,11629,11653,11654,11651,4630,4631,18382,4764,19930,3821};
						c.getItems().addItem(arrayOfC4[Misc.random(arrayOfC4.length -1)], 1);
						c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
						c.buryDelay = System.currentTimeMillis();
								} else {
									c.sendMessage("You need at least 1 free slot in your inventory!");
								}
					}
					}
						if ((itemId == 3559) || (itemId == 3537)) {
							if (System.currentTimeMillis() - c.buryDelay > 500) {
							if (c.getItems().freeSlots() >= 1 ) {
							c.getItems().deleteItem(itemId, 1); //casket V
							int[] arrayOfC5 ={13346,19916,12240,4765,11607,11608,
											  11609,11610,11611,11612,19957,20141,11629,11653,11654,11651,4630,4631,18382,4764,19930,3821,20250,11724,11726,
											  13840,11662,11660,11661,4762,4763,13225,9899,13196,3890,5203,11307,11317,11318,11319,11312};
							c.getItems().addItem(arrayOfC5[Misc.random(arrayOfC5.length -1)], 1);
							c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
							c.buryDelay = System.currentTimeMillis();
									} else {
										c.sendMessage("You need at least 1 free slot in your inventory!");
									}
						}
						}
							if ((itemId == 3561) || (itemId == 3539)) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //casket VI
								int[] arrayOfC6 ={9899,13196,3890,5203,17871,20158,20157,20210,20161,
												  12233,12234,12235,12236,12237,12239,12238,20159,18362,12249,12250,12251,12225,12227,12228,12229,12231,12232,
												  19929,11631,3285,3286,3287,3288,3289,3291,10708,19266,19263,10705,19203,19200,15038,15037,12259,12260,
												  12261,12262,12263,12264,12268,12269,12270};
								c.getItems().addItem(arrayOfC6[Misc.random(arrayOfC6.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if ((itemId == 3563) || (itemId == 3541)) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //casket VII
								int[] arrayOfC7 ={20159,18362,12249,12250,12251,12225,12227,12228,12229,12231,12232,
												  19929,11631,3285,3286,3287,3288,3289,3291,19921,19919,19920,12248,12247,12246,3819,3290,20094,12245,
												  12244,12243,18743,20096,6929,17854,8807,8808,8809,12271,12272,12273,12280,12281,12282,12283,12284,
												  12285,12289,12290,12291};
								c.getItems().addItem(arrayOfC7[Misc.random(arrayOfC7.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 3543) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //casket VIII
								int[] arrayOfC8 ={3288,3289,3291,19921,19919,19920,12248,12247,12246,3819,3290,20094,12245,
												  12244,12243,18743,20096,6929,6927,14007,6931,20093,11149,6930,20090,9396,20088,
												  3892,3882,3888,17879,20089,20081,11596,20097,20092,17861,4629,4741,4742,4743,
												  4744,4626,19107,4746,11349,20091,4648,20082,3807,4066,4065,4057,4064,20083,
												  18785,18784,17877,17880};
								c.getItems().addItem(arrayOfC8[Misc.random(arrayOfC8.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 3545) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //casket IX
								int[] arrayOfCC3 ={20083,20084,20085,20086,962,20080,11650,20087,19956,17859,5232};
								c.getItems().addItem(arrayOfCC3[Misc.random(arrayOfCC3.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 3547) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //casket X
								int[] arrayOfCC2 ={20083,20084,20085,20086,962,20080,11650,20087,19956,17859,5232,
										17882,13138};
								c.getItems().addItem(arrayOfCC2[Misc.random(arrayOfCC2.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Casket, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 3565) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //Scary Chest
								int[] arrayOfCC1 ={12277,12278,12279,12292};
								c.getItems().addItem(arrayOfCC1[Misc.random(arrayOfCC1.length -1)], 1);
								c.sendMessage("<col=255>You have opened your SCARY Casket, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 3581) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //Weapon Casket I
								int[] arrayOfCC1 ={11143,11144,11145,11146,11142,11147,11148,17867,11638,19478,18388,4058,968,934,17861,5206,12241,12242,12240,
													918,4646,11630,4645,4633,16481,17848,14445,5211,5212,898,900,19476,20254,5204,5205,5222,5213,5214,
													5215,5216,5217,5218};
								c.getItems().addItem(arrayOfCC1[Misc.random(arrayOfCC1.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Weapon Casket I, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 3583) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //Weapon Casket II
								int[] arrayOfCC1 ={17861,12240,4056,1000,17867,11638,19478,18388,13196,7783,12238,20250,20159,11653,3892,6605,9899,13840,17837,17838,17840,17842};
								c.getItems().addItem(arrayOfCC1[Misc.random(arrayOfCC1.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Weapon Casket II, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 3585) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //Weapon Casket III
								int[] arrayOfCC1 ={4743,19931,13196,3290,12238,20159,18785,14007,3892,6605,9899,13840,17837,17838};
								c.getItems().addItem(arrayOfCC1[Misc.random(arrayOfCC1.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Weapon Casket III, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 3587) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //Weapon Casket IV
								int[] arrayOfCC1 ={4743,19939,12230,19931,18785,18784,14007,10900,17877};
								c.getItems().addItem(arrayOfCC1[Misc.random(arrayOfCC1.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Weapon Casket IV, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 3589) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //Weapon Casket V
								int[] arrayOfCC1 ={4743,19939,17882,18784,18785,10900,17880,11650,17859,};
								c.getItems().addItem(arrayOfCC1[Misc.random(arrayOfCC1.length -1)], 1);
								c.sendMessage("<col=255>You have opened your Weapon Casket V, hoping for some good loot!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							
							if (itemId == 12293) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //20.000b
								c.getItems().addItem(5021, 1000);
								c.sendMessage("<col=255>You tear the ticket and collect 1.000 1b tickets!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 12295) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //20.000b
								c.getItems().addItem(5021, 10000);
								c.sendMessage("<col=255>You tear the ticket and collect 10.000 1b tickets!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 12297) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //20.000b
								c.getItems().addItem(5021, 100000);
								c.sendMessage("<col=255>You tear the ticket and collect 100.000 1b tickets!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
							if (itemId == 12299) {
								if (System.currentTimeMillis() - c.buryDelay > 500) {
								if (c.getItems().freeSlots() >= 1 ) {
								c.getItems().deleteItem(itemId, 1); //20.000b
								c.getItems().addItem(5021, 1000000);
								c.sendMessage("<col=255>You tear the ticket and collect 1000000 1b tickets!");
								c.buryDelay = System.currentTimeMillis();
										} else {
											c.sendMessage("You need at least 1 free slot in your inventory!");
										}
							}
							}
		
			
		if (itemId == 7498){
			c.getPA().showInterface(2808);
			c.sendMessage("Once you click the skill desired your experience will be given. Choose carefully.");
		}

		if (itemId == 2714) { // Easy Clue Scroll Casket
			c.getItems().deleteItem(itemId, 1);
			TreasureTrails.addClueReward(c, 0);
		}
		if (itemId == 2802) { // Medium Clue Scroll Casket
			c.getItems().deleteItem(itemId, 1);
			TreasureTrails.addClueReward(c, 1);
		}
		if (itemId == 2775) { // Hard Clue Scroll Casket
			c.getItems().deleteItem(itemId, 1);
			TreasureTrails.addClueReward(c, 2);
		}
		if(itemId == 2713) {
			c.getPA().showInterface(17537);
		}
		if(itemId == 2712) {
			c.getPA().showInterface(9043);
		}
		if(itemId == 2711) {
			c.getPA().showInterface(7271);
		}
		if(itemId == 2710) {
			c.getPA().showInterface(7045);
		}
		if(itemId == 2709) {
		c.getPA().showInterface(9275);
		}
		if(itemId == 2708) {
		c.getPA().showInterface(7113);
		}
		if(itemId == 2707) {
		c.getPA().showInterface(17634);
		}
		if(itemId == 2706) {
		c.getItems().deleteItem(2706, c.getItems().getItemSlot(2706), 1);
				c.getItems().addItem(2802,1);
				c.sendMessage("You recieve a MEDIUM Casket!");
		}
		if(itemId == 2705) {
		c.getPA().showInterface(4305);
		}
		if(itemId == 2704) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("In a liar of a Boss lies", 6971);
			c.getPA().sendFrame126("the next clue scroll!", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2703) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("I seek another cluse just", 6971);
			c.getPA().sendFrame126("west of the fountain, at the origin!", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2702) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("We are here lying to protect", 6971);
			c.getPA().sendFrame126("the castle that we truely love!", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2701) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("This has to be bob's favorite", 6971);
			c.getPA().sendFrame126("training spot in-game.", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2700) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("We all love water, especially", 6971);
			c.getPA().sendFrame126("from big, clean, fountains!", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2699) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("I love to eat cake, maybe", 6971);
			c.getPA().sendFrame126("you want to seal some?", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2698) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("We stall seek history within", 6971);
			c.getPA().sendFrame126("the ancient museum.", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2697) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("We pay to Pk, especially", 6971);
			c.getPA().sendFrame126("within a city named Falador.", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2696) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("Rats! Rats! Rats!", 6971);
			c.getPA().sendFrame126("The sewers are full of them!", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2695) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("These fish must be hot!", 6971);
			c.getPA().sendFrame126("We shall call this, Lava Fishing", 6972);
			c.getPA().showInterface(6965);
		}
		//if(itemId == 2694) {
		//c.sendMessage("My loved one..Once murdered in front of my eyes..I couldn't save her..");
		//}
		if(itemId == 2693) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("This village contains torches,", 6971);
			c.getPA().sendFrame126("rocks, and some kind of stronghold.", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2692) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("We shall thieve Master Farmers!", 6971);
			c.getPA().sendFrame126("I Pandemic where I can find them...", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2691) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("arggggghhh mate,", 6971);
			c.getPA().sendFrame126("Would you like some beer?", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2690) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("Cabbage!", 6971);
			c.getPA().sendFrame126("Lots, and lots of Cabbage!", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2689) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("Ew, a scorpian.", 6971);
			c.getPA().sendFrame126("Why are these mines so messed up!", 6972);
			c.getPA().showInterface(6965);
		}
		if(itemId == 2688) {
		for(int i = 6968; i < 6976; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("I seek many, many, banana trees.", 6971);
			c.getPA().sendFrame126("Do you know where it is?", 6972);
			c.getPA().showInterface(6965);
		}
				/**Hard Clue Scroll**/
		if(c.safeAreas(2969, 3411, 2974, 3415) & (c.getItems().playerHasItem(2713,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2713, c.getItems().getItemSlot(2713), 1);
				c.getItems().addItem(2712,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(2613, 3075, 2619, 3080) & (c.getItems().playerHasItem(2712,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2712, c.getItems().getItemSlot(2712), 1);
				c.getItems().addItem(2711,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3030, 3394, 3049, 3401) & (c.getItems().playerHasItem(2711,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2711, c.getItems().getItemSlot(2711), 1);
				c.getItems().addItem(2775,1);
				c.sendMessage("You recieve a HARD Casket!");
			} else if(c.safeAreas(3285, 3371, 3291, 3375) & (c.getItems().playerHasItem(2710,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2710, c.getItems().getItemSlot(2710), 1);
				c.getItems().addItem(2709,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3106, 3148, 3113, 3154) & (c.getItems().playerHasItem(2709,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2709, c.getItems().getItemSlot(2709), 1);
				c.getItems().addItem(2708,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3092, 3213, 3104, 3225) & (c.getItems().playerHasItem(2708,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2708, c.getItems().getItemSlot(2708), 1);
				c.getItems().addItem(2775,1);
				c.sendMessage("You recieve a HARD Casket!");
			} else if(c.safeAreas(2719, 3336, 2725, 3339) & (c.getItems().playerHasItem(2707,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2707, c.getItems().getItemSlot(2707), 1);
				c.getItems().addItem(2706,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3301, 3684, 3313, 3698) & (c.getItems().playerHasItem(2706,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2706, c.getItems().getItemSlot(2706), 1);
				c.getItems().addItem(2705,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(2903, 3287, 2909, 3300) & (c.getItems().playerHasItem(2705,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2705, c.getItems().getItemSlot(2705), 1);
				c.getItems().addItem(2775,1);
				c.sendMessage("You recieve a HARD Casket!");
			/**Easy Clue Scrolls**/
			} else if(c.safeAreas(2259, 4680, 2287, 4711) & (c.getItems().playerHasItem(2704,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2704, c.getItems().getItemSlot(2704), 1);
				c.getItems().addItem(2703,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3217, 3207, 3225, 3213) & (c.getItems().playerHasItem(2703,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2703, c.getItems().getItemSlot(2703), 1);
				c.getItems().addItem(2702,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(2962, 3331, 2987, 3351) & (c.getItems().playerHasItem(2702,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2702, c.getItems().getItemSlot(2702), 1);
				c.getItems().addItem(2714,1);
				c.sendMessage("You recieve a EASY Casket!");
			} else if(c.safeAreas(3253, 3256, 3265, 3296) & (c.getItems().playerHasItem(2701,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2701, c.getItems().getItemSlot(2701), 1);
				c.getItems().addItem(2700,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3208, 3421, 3220, 3435) & (c.getItems().playerHasItem(2700,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2700, c.getItems().getItemSlot(2700), 1);
				c.getItems().addItem(2699,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3084, 3486, 3086, 3488) & (c.getItems().playerHasItem(2699,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2699, c.getItems().getItemSlot(2699), 1);
				c.getItems().addItem(2698,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3253, 3445, 3261, 3453) & (c.getItems().playerHasItem(2698,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2698, c.getItems().getItemSlot(2698), 1);
				c.getItems().addItem(2714,1);
				c.sendMessage("You recieve a EASY Casket!");
			/**Medium Clue Scrolls**/
			} else if(c.safeAreas(2953, 3365, 2977, 3392) & (c.getItems().playerHasItem(2697,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2697, c.getItems().getItemSlot(2697), 1);
				c.getItems().addItem(2696,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3228, 9860, 3259, 9873) & (c.getItems().playerHasItem(2696,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2696, c.getItems().getItemSlot(2696), 1);
				c.getItems().addItem(2695,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(2875, 9763, 2904, 9776) & (c.getItems().playerHasItem(2695,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2695, c.getItems().getItemSlot(2695), 1);
				c.getItems().addItem(2802,1);
				c.sendMessage("You recieve a MEDIUM Casket!");
			} else if(c.safeAreas(3074, 3407, 3085, 3436) & (c.getItems().playerHasItem(2693,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2693, c.getItems().getItemSlot(2693), 1);
				c.getItems().addItem(2692,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3074, 3245, 3085, 3255) & (c.getItems().playerHasItem(2692,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2692, c.getItems().getItemSlot(2692), 1);
				c.getItems().addItem(2691,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3044, 3255, 3055, 3259) & (c.getItems().playerHasItem(2691,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2691, c.getItems().getItemSlot(2691), 1);
				c.getItems().addItem(2802,1);
				c.sendMessage("You recieve a MEDIUM Casket!");
			} else if(c.safeAreas(3041, 3284, 3067, 3298) & (c.getItems().playerHasItem(2690,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2690, c.getItems().getItemSlot(2690), 1);
				c.getItems().addItem(2689,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(3032, 9756, 3056, 9804) & (c.getItems().playerHasItem(2689,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2689, c.getItems().getItemSlot(2689), 1);
				c.getItems().addItem(2688,1);
				c.sendMessage("You recieve another scroll.");
			} else if(c.safeAreas(2906, 3155, 2926, 3175) & (c.getItems().playerHasItem(2688,1))) {
				c.getPA().removeAllWindows();
				c.getItems().deleteItem(2688, c.getItems().getItemSlot(2688), 1);
				c.getItems().addItem(2802,1);
				c.sendMessage("You recieve a MEDIUM Casket!");
			}


		if(itemId == 11949) {
		c.makesnow();
		c.stopMovement();
                c.sendMessage("<shad=838383>You Have "+c.present+"/15 Presents!");
                c.sendMessage("<shad=838383>There is a Ghost at every lodestone Area Hidden in houses!");
		}

                if(itemId == 13495) {
                if(c.AuraEquiped > 1){
                c.getItems().addItem(c.AuraEquiped, 1);
                }
                c.AuraEquiped = 13495;
                c.getPA().sendFrame34(c.AuraEquiped, 0, 10794, 1);
                c.AuraEffect = 1;
                c.getItems().deleteItem(13495,c.getItems().getItemSlot(13495),1);
                }
         
				
		if(itemId == 6199) { //mbox
c.getItems().deleteItem(6199, 1);
int[] arrayOfItems ={6199,6199,6199,6199,6199,6199,6199,1050,1038,1040,1042,1044,1046,1048,1050,1038,1040,1042,
		     6199,6199,6199,6199,1044,1046,1048,1050,1038,1040,1042,1044,1046,1048,6199,6199,6199,6199,
		     6199,6199,6199,1050,1038,1040,1042,1044,1046,1048,1050,1038,1040,1042,
		     6199,6199,6199,6199,1044,1046,1048,1050,1038,1040,1042,1044,1046,1048,
		     6199,6199,6199,6199,6199,6199,6199,1050,1038,1040,1042,1044,1046,1048,1050,1038,1040,1042,
		     6199,6199,6199,6199,1044,1046,1048,1050,1038,1040,1042,1044,1046,1048,6199,6199,6199,6199,
		     6199,6199,6199,1050,1038,1040,1042,1044,1046,1048,1050,1038,1040,1042,
		     6199,6199,6199,6199,1044,1046,1048,1050,1038,1040,1042,1044,1046,1048,
		     6199,6199,6199,6199,6199,6199,6199,1050,1038,1040,1042,1044,1046,1048,1050,1038,1040,1042,
		     6199,6199,6199,6199,1044,1046,1048,1050,1038,1040,1042,1044,1046,1048,6199,6199,6199,6199,
		     6199,6199,6199,1050,1038,1040,1042,1044,1046,1048,1050,1038,1040,1042,
		     6199,6199,6199,6199,1044,1046,1048,1050,1038,1040,1042,1044,1046,1048,13362,13358,13360,
		     5200,19618,6929,6927,4647,4632,806,
                     14000,14001,14002,1044,1046,1048,1050,1038,1040,1042,1044,1046,1048,1050,6930,6931};
c.getItems().addItem(arrayOfItems[Misc.random(arrayOfItems.length)], 1);
		}
		if (itemId == 10832) {
			c.getItems().deleteItem(10832, 1);
			c.getItems().addItem(5021, 100);
			}
		if (itemId == 10833) {
			c.getItems().deleteItem(10833, 1);
			c.getItems().addItem(5021, 250);
			}
		if (itemId == 10834) {
			c.getItems().deleteItem(10834, 1);
			c.getItems().addItem(5021, 800);
			}
	if (itemId == 9721) {
c.getPA().showInterface(6965);
c.getPA().sendFrame126("@red@~ Overload Instruction Manual ~",6968);
c.getPA().sendFrame126("",6969);
c.getPA().sendFrame126("@gre@Step 1: @cya@Get a Herblore level of 99.",6970);
c.getPA().sendFrame126("@gre@Step 2: @cya@Have all Extreme Potions ",6971);
c.getPA().sendFrame126("@cya@In your inventory, along with a",6972);
c.getPA().sendFrame126("@gre@Step 3: @cya@Cleaned torstol ,use the",6973);
c.getPA().sendFrame126("@cya@ Torstol on an extreme potion.",6974);
c.getPA().sendFrame126("@gre@Step 4: @cya@You now have an overload!",6975);
c.getPA().sendFrame126("",6976);
c.getPA().sendFrame126("",6977);
c.getPA().sendFrame126("",6978);
c.getPA().sendFrame126("",6979);
c.getPA().sendFrame126("",6980);
}

if(itemId == 299) {
flowers = randomflower();
flowerX += c.absX;
flowerY += c.absY;
c.getPA().object(flowers, c.absX, c.absY, 0, 10);
c.sendMessage("You plant the seed...");
c.getItems().deleteItem(299, 1);
c.getPA().walkTo(-1,0); 
c.getDH().sendDialogues(9999, -1);   
}

if (itemId == 1856) {
c.getPA().showInterface(6965);
c.getPA().sendFrame126("@red@~ Duel Arena - Known Bugs ~",6968);
c.getPA().sendFrame126("",6969);
c.getPA().sendFrame126("@gre@1.@cya@Dueling is at your own risk.",6970);
c.getPA().sendFrame126("@cya@If you loose items to players/bugs",6971);
c.getPA().sendFrame126("@cya@ YOU WILL NOT GET YOUR ITEMS BACK.",6972);
c.getPA().sendFrame126("@gre@2.@cya@FORFEITING DOES NOT WORK!!",6973);
c.getPA().sendFrame126("@gre@3.@cya@FUN WEAPONS ARE DISABLED",6974);
c.getPA().sendFrame126("@gre@4.@cya@ROCKTAILS COUNTS AS FOOD! BE CAREFUL",6975);
c.getPA().sendFrame126("",6976);
c.getPA().sendFrame126("",6977);
c.getPA().sendFrame126("",6978);
c.getPA().sendFrame126("",6979);
c.getPA().sendFrame126("",6980);
}

if (itemId == 9719) {
c.getPA().showInterface(6965);
c.getPA().sendFrame126("@red@~ Extremes Instruction Manual ~",6968);
c.getPA().sendFrame126("",6969);
c.getPA().sendFrame126("@gre@Step 1: @cya@Get a Herblore level of 80.",6970);
c.getPA().sendFrame126("@gre@Step 2: @cya@Have a super potion (3) and ",6971);
c.getPA().sendFrame126("@cya@ the required cleaned herb.",6972);
c.getPA().sendFrame126("@gre@Step 3: @cya@Use the herb on the super potion",6973);
c.getPA().sendFrame126("@cya@ You will now get a extreme potion (3)",6974);
c.getPA().sendFrame126("@gre@Step 4: @cya@Use RS Wiki for herbs req's",6975);
c.getPA().sendFrame126("",6976);
c.getPA().sendFrame126("",6977);
c.getPA().sendFrame126("",6978);
c.getPA().sendFrame126("",6979);
c.getPA().sendFrame126("",6980);
}

				if(itemId == 8013) {
						if (c.inWarriorG()) {
		return;
		}
				                if(c.inPits) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
                if(c.getPA().inPitsWait()) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
        if(c.Jail == true){
                c.sendMessage("You can't teleport out of Jail!");
        } 
	if(c.inJail() && c.Jail == true) {
                c.sendMessage("You can't teleport out oaf Jail!");
 	} 
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(c.InDung) {
			c.sendMessage("You can't teleport from here moron");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(c.inNomad()) {
			c.sendMessage("You can't teleport during Nomad Minigame");
			return;
		}
		if(c.inGoblin()) {
			c.sendMessage("You can't teleport during Goblin Minigame");
			return;
		}
		                   c.getPA().teleTabTeleport(2059, 3143, 4, "house");
						   c.sendMessage("You teleport into you're custom place..");
                   c.getItems().deleteItem(8013,c.getItems().getItemSlot(8013),1);
                }

				if(itemId == 15707) {
                  c.getPA().startTeleport(2533, 3304, 0, "dungtele");
				   c.sendMessage("Your Ring of Kinship takes you to Dungeoneering.");
				    //c.sendMessage("Dungeoneering is currently Disabled! We're adding new dung atm.");
                }   
								if(itemId == 18821) {
                   c.getPA().startTeleport(2533, 3304, 0, "dungtele");
				   c.sendMessage("Your Ring of Kinship takes you to Dungeoneering.");
				   // c.sendMessage("Dungeoneering is currently Disabled! We're adding new dung atm.");
                } 
								if(itemId == 18817) {
                  c.getPA().startTeleport(2533, 3304, 0, "dungtele");
				   //c.sendMessage("Your Ring of Kinship takes you to Dungeoneering.");
				   // c.sendMessage("Dungeoneering is currently Disabled! We're adding new dung atm.");
                }  
								if(itemId == 18823) {
                    c.getPA().startTeleport(2533, 3304, 0, "dungtele");
				   //c.sendMessage("Your Ring of Kinship takes you to Dungeoneering.");
				    //c.sendMessage("Dungeoneering is currently Disabled! We're adding new dung atm.");
                }  
				
				if(itemId == 8007) {
				                if(c.inPits) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
                if(c.getPA().inPitsWait()) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
        if(c.Jail == true){
                c.sendMessage("You can't teleport out of Jail!");
        } 
	if(c.inJail() && c.Jail == true) {
                c.sendMessage("You can't teleport out oaf Jail!");
 	} 
		if(c.InDung) {
			c.sendMessage("You can't teleport from here moron");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(c.inNomad()) {
			c.sendMessage("You can't teleport during Nomad Minigame");
			return;
		}
		if (c.inWarriorG()) {
		return;
		}
		if(c.inGoblin()) {
			c.sendMessage("You can't teleport during Goblin Minigame");
			return;
		}
		                c.getPA().teleTabTeleport(3216,3424,0, "teleTab");
						c.getItems().deleteItem(8007,c.getItems().getItemSlot(8007),1);
                } 
				if(itemId == 8008) {
						if (c.inWarriorG()) {
		return;
		}
				                if(c.inPits) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
                if(c.getPA().inPitsWait()) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
        if(c.Jail == true){
                c.sendMessage("You can't teleport out of Jail!");
        } 
	if(c.inJail() && c.Jail == true) {
                c.sendMessage("You can't teleport out oaf Jail!");
 	} 
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(c.InDung) {
			c.sendMessage("You can't teleport from here moron");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(c.inNomad()) {
			c.sendMessage("You can't teleport during Nomad Minigame");
			return;
		}
		if(c.inGoblin()) {
			c.sendMessage("You can't teleport during Goblin Minigame");
			return;
		}
		                   c.getPA().teleTabTeleport(3221, 3217, 0, "teleTab");
                   c.getItems().deleteItem(8008,c.getItems().getItemSlot(8008),1);
                }
              if(itemId == 8009) {
			  		if (c.inWarriorG()) {
		return;
		}
			                  if(c.inPits) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
                if(c.getPA().inPitsWait()) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
        if(c.Jail == true){
                c.sendMessage("You can't teleport out of Jail!");
        } 
	if(c.inJail() && c.Jail == true) {
                c.sendMessage("You can't teleport out oaf Jail!");
 	} 
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(c.InDung) {
			c.sendMessage("You can't teleport from here moron");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(c.inNomad()) {
			c.sendMessage("You can't teleport during Nomad Minigame");
			return;
		}
		if(c.inGoblin()) {
			c.sendMessage("You can't teleport during Goblin Minigame");
			return;
		}
			                     c.getPA().teleTabTeleport(2964, 3380, 0, "teleTab");
                   c.getItems().deleteItem(8009,c.getItems().getItemSlot(8009),1);
                }
            if(itemId == 8010) {
					if (c.inWarriorG()) {
		return;
		}
			                if(c.inPits) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
                if(c.getPA().inPitsWait()) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
        if(c.Jail == true){
                c.sendMessage("You can't teleport out of Jail!");
        } 
	if(c.inJail() && c.Jail == true) {
                c.sendMessage("You can't teleport out oaf Jail!");
 	} 
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(c.InDung) {
			c.sendMessage("You can't teleport from here moron");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(c.inNomad()) {
			c.sendMessage("You can't teleport during Nomad Minigame");
			return;
		}
		if(c.inGoblin()) {
			c.sendMessage("You can't teleport during Goblin Minigame");
			return;
		}
			c.getPA().teleTabTeleport(2756, 3479, 0, "teleTab");
                  c.getItems().deleteItem(8010,c.getItems().getItemSlot(8010),1);
                }
          if(itemId == 8011) {
		  		if (c.inWarriorG()) {
		return;
		}
		                  if(c.inPits) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
                if(c.getPA().inPitsWait()) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
        if(c.Jail == true){
                c.sendMessage("You can't teleport out of Jail!");
        } 
	if(c.inJail() && c.Jail == true) {
                c.sendMessage("You can't teleport out oaf Jail!");
 	} 
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(c.InDung) {
			c.sendMessage("You can't teleport from here moron");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(c.inNomad()) {
			c.sendMessage("You can't teleport during Nomad Minigame");
			return;
		}
		if(c.inGoblin()) {
			c.sendMessage("You can't teleport during Goblin Minigame");
			return;
		}
		  c.getPA().teleTabTeleport(2661, 3306, 0, "teleTab");
		  c.foodDelay = System.currentTimeMillis();
                   c.getItems().deleteItem(8011,c.getItems().getItemSlot(8011),1);
				}
          if(itemId == 8012) {
		                  if(c.inPits) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
                if(c.getPA().inPitsWait()) {
                        c.sendMessage("You can't teleport during Fight Pits.");
                        return;
                }
        if(c.Jail == true){
                c.sendMessage("You can't teleport out of Jail!");
        } 
	if(c.inJail() && c.Jail == true) {
                c.sendMessage("You can't teleport out oaf Jail!");
 	} 
		if(c.duelStatus == 5) {
			c.sendMessage("You can't teleport during a duel!");
			return;
		}
		if(c.InDung) {
			c.sendMessage("You can't teleport from here moron");
			return;
		}
		if(c.inWild() && c.wildLevel > Config.NO_TELEPORT_WILD_LEVEL) {
			c.sendMessage("You can't teleport above level "+Config.NO_TELEPORT_WILD_LEVEL+" in the wilderness.");
			return;
		}
		if(System.currentTimeMillis() - c.teleBlockDelay < c.teleBlockLength) {
			c.sendMessage("You are teleblocked and can't teleport.");
			return;
		}
		if(c.inNomad()) {
			c.sendMessage("You can't teleport during Nomad Minigame");
			return;
		}
		if(c.inGoblin()) {
			c.sendMessage("You can't teleport during Goblin Minigame");
			return;
		}
		          c.getPA().teleTabTeleport(2549, 3113, 0, "teleTab");
                  c.getItems().deleteItem(8012,c.getItems().getItemSlot(8012),1);
                }
		if(itemId == 4447) {	
			c.getPA().addSkillXP(3000, 24);
			c.sendMessage("You rub the lamp and feel yourself further in the arts of dungeoneering.");
			c.getItems().deleteItem(4447, 1);	
		}
		if(itemId == 15262) {
			c.getItems().addItem(18016, 10000);
			c.getItems().deleteItem(15262, 1);
		}
		if(itemId == 8002) {
			if (c.playerLevel[15] >= 100) { 
				if (System.currentTimeMillis() - c.buryDelay > 3000) {
						c.getItems().deleteItem(8002, 1);
						c.getItems().addItem(8003, 1);
						c.startAnimation(885);
						c.sendMessage("You Clean the Grimy Lavender.");
						c.getPA().addSkillXP(50000, c.playerHerblore);
						c.buryDelay = System.currentTimeMillis();
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 100 Herblore to do this.");
				return;
			}
		}
		if(itemId == 8005) {
			if (c.playerLevel[15] >= 110) { 
				if (System.currentTimeMillis() - c.buryDelay > 3000) {
						c.getItems().deleteItem(8005, 1);
						c.getItems().addItem(8006, 1);
						c.startAnimation(885);
						c.sendMessage("You Clean the Grimy Rose.");
						c.getPA().addSkillXP(80000, c.playerHerblore);
						c.buryDelay = System.currentTimeMillis();
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 110 Herblore to do this.");
				return;
			}
		}
		if(itemId == 18751) {
			if (c.playerLevel[15] >= 115) { 
				if (System.currentTimeMillis() - c.buryDelay > 3000) {
						c.getItems().deleteItem(18751, 1);
						c.getItems().addItem(18750, 1);
						c.startAnimation(885);
						c.sendMessage("You Clean the Grimy Orchid.");
						c.getPA().addSkillXP(120000, c.playerHerblore);
						c.buryDelay = System.currentTimeMillis();
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 115 Herblore to do this.");
				return;
			}
		}
		if(itemId == 19940) {
			if (c.playerLevel[15] >= 125) { 
				if (System.currentTimeMillis() - c.buryDelay > 3000) {
						c.getItems().deleteItem(19940, 1);
						c.getItems().addItem(19941, 1);
						c.startAnimation(885);
						c.sendMessage("You Clean the Grimy Tulip.");
						c.getPA().addSkillXP(150000, c.playerHerblore);
						c.buryDelay = System.currentTimeMillis();
				} else {
					return;
				}
			} else {
				c.sendMessage("You need 125 Herblore to do this.");
				return;
			}
		}

		
			
		if(itemId == 19775) {
			c.playerLevel[0] = 135;
			c.playerLevel[2] = 135;
			c.playerLevel[3] = 135;
			c.playerLevel[4] = 135;
			c.playerLevel[6] = 135;
			c.playerXP[0] = c.getPA().getXPForLevel(135);
			c.playerXP[2] = c.getPA().getXPForLevel(135);
			c.playerXP[3] = c.getPA().getXPForLevel(135);
			c.playerXP[4] = c.getPA().getXPForLevel(135);
			c.playerXP[6] = c.getPA().getXPForLevel(135);
			c.getPA().refreshSkill(0);
			c.getPA().refreshSkill(2);
			c.getPA().refreshSkill(3);
			c.getPA().refreshSkill(4);
			c.getPA().refreshSkill(6);
			c.getItems().deleteItem(19775, 1);
			c.sendMessage("You use the MASTER combat Ring");
			}
			
		if (itemId == 15272) {
		if (System.currentTimeMillis() - c.foodDelay >= 1500 && c.playerLevel[3] > 0) {
			c.getCombat().resetPlayerAttack();
			c.attackTimer += 2;
			c.startAnimation(829);
			c.getItems().deleteItem(15272, 1);
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += 23;
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3] + 10);
			}
			c.foodDelay = System.currentTimeMillis();
			c.getPA().refreshSkill(3);
			c.sendMessage("You eat the Rocktail.");
		}
 		//c.playerLevel[3] += 10;
		if (c.playerLevel[3] > (c.getLevelForXP(c.playerXP[3])*1.11 + 1)) {
			c.playerLevel[3] = (int)(c.getLevelForXP(c.playerXP[3])*1.11);
		}
		c.getPA().refreshSkill(3);
			return;
		}

		if (itemId == 3265) {
		if (System.currentTimeMillis() - c.foodDelay >= 1500 && c.playerLevel[3] > 0) {
			c.getCombat().resetPlayerAttack();
			c.attackTimer += 2;
			c.startAnimation(829);
			c.getItems().deleteItem(3265, 1);
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += 1;
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3] + 10);
			}
			c.foodDelay = System.currentTimeMillis();
			c.getPA().refreshSkill(3);
			c.sendMessage("TROLOLOLOLOLOLL.");
			c.sendMessage("TROLOLOLOLOLOLL.");
			c.sendMessage("TROLOLOLOLOLOLL.");
			c.sendMessage("TROLOLOLOLOLOLL.");
			c.sendMessage("TROLOLOLOLOLOLL.");
			c.sendMessage("TROLOLOLOLOLOLL.");
			c.sendMessage("TROLOLOLOLOLOLL.");
			c.sendMessage("TROLOLOLOLOLOLL.");
			c.sendMessage("TROLOLOLOLOLOLL.");
		}
 		//c.playerLevel[3] += 10;
		if (c.playerLevel[3] > (c.getLevelForXP(c.playerXP[3])*1.11 + 1)) {
			c.playerLevel[3] = (int)(c.getLevelForXP(c.playerXP[3])*1.11);
		}
		c.getPA().refreshSkill(3);
			return;
		}

if (itemId == 7992) {
		if (System.currentTimeMillis() - c.foodDelay >= 1500 && c.playerLevel[3] > 0) {
			c.getCombat().resetPlayerAttack();
			c.attackTimer += 2;
			c.startAnimation(829);
			c.getItems().deleteItem(7992, 1);
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += 19;
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3] + 10);
			}
			c.foodDelay = System.currentTimeMillis();
			c.getPA().refreshSkill(3);
			c.sendMessage("You eat the Big Swordfish.");
		}
 		//c.playerLevel[3] += 10;
		if (c.playerLevel[3] > (c.getLevelForXP(c.playerXP[3])*1.11 + 1)) {
			c.playerLevel[3] = (int)(c.getLevelForXP(c.playerXP[3])*1.11);
		}
		c.getPA().refreshSkill(3);
			return;
		}
		if (itemId == 7994) {
		if (System.currentTimeMillis() - c.foodDelay >= 1500 && c.playerLevel[3] > 0) {
			c.getCombat().resetPlayerAttack();
			c.attackTimer += 2;
			c.startAnimation(829);
			c.getItems().deleteItem(7994, 1);
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += 25;
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3] + 10);
			}
			c.foodDelay = System.currentTimeMillis();
			c.getPA().refreshSkill(3);
			c.sendMessage("You eat the Big Shark.");
		}
 		//c.playerLevel[3] += 10;
		if (c.playerLevel[3] > (c.getLevelForXP(c.playerXP[3])*1.11 + 1)) {
			c.playerLevel[3] = (int)(c.getLevelForXP(c.playerXP[3])*1.11);
		}
		c.getPA().refreshSkill(3);
			return;
		}
		if (itemId == 3301) {
		if (System.currentTimeMillis() - c.foodDelay >= 1500 && c.playerLevel[3] > 0) {
			c.getCombat().resetPlayerAttack();
			c.attackTimer += 2;
			c.startAnimation(829);
			c.getItems().deleteItem(3301, 1);
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += 27;
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3] + 10);
			}
			c.foodDelay = System.currentTimeMillis();
			c.getPA().refreshSkill(3);
			c.sendMessage("You eat the Whale.");
		}
 		//c.playerLevel[3] += 10;
		if (c.playerLevel[3] > (c.getLevelForXP(c.playerXP[3])*1.11 + 1)) {
			c.playerLevel[3] = (int)(c.getLevelForXP(c.playerXP[3])*1.11);
		}
		c.getPA().refreshSkill(3);
			return;
		}
		if (itemId == 3303) {
		if (System.currentTimeMillis() - c.foodDelay >= 1500 && c.playerLevel[3] > 0) {
			c.getCombat().resetPlayerAttack();
			c.attackTimer += 2;
			c.startAnimation(829);
			c.getItems().deleteItem(3303, 1);
			if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
				c.playerLevel[3] += 30;
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3] + 10);
			}
			c.foodDelay = System.currentTimeMillis();
			c.getPA().refreshSkill(3);
			c.sendMessage("You eat the Stingray.");
		}
 		//c.playerLevel[3] += 10;
		if (c.playerLevel[3] > (c.getLevelForXP(c.playerXP[3])*1.11 + 1)) {
			c.playerLevel[3] = (int)(c.getLevelForXP(c.playerXP[3])*1.11);
		}
		c.getPA().refreshSkill(3);
			return;
		}



		if (itemId == 2528) {
                                           c.lampfix = 5;
		c.sendMessage("You rub the 750K xp Lamp.");
                c.getPA().showInterface(35000);
		}
		if (itemId == 11137) {
                                           c.lampfix = 10;
		c.sendMessage("You rub the xp Lamp.");
                c.getPA().showInterface(35000);
		}
		if (itemId == 19750) {
                                           c.lampfix = 15;
		c.sendMessage("You rub the xp Lamp.");
                c.getPA().showInterface(35000);
		}
		if (itemId == 11850) {
		c.getItems().deleteItem(11850,1);
		c.getItems().addItem(4724,1);
		c.getItems().addItem(4726,1);
		c.getItems().addItem(4728,1);
		c.getItems().addItem(4730,1);
		}
		if (itemId == 11852) {
		c.getItems().deleteItem(11852,1);
		c.getItems().addItem(4732,1);
		c.getItems().addItem(4734,1);
		c.getItems().addItem(4736,1);
		c.getItems().addItem(4738,1);
		}
		if (itemId == 11854) {
		c.getItems().deleteItem(11854,1);
		c.getItems().addItem(4745,1);
		c.getItems().addItem(4747,1);
		c.getItems().addItem(4749,1);
		c.getItems().addItem(4751,1);
		}
		if (itemId == 11856) {
		c.getItems().deleteItem(11856,1);
		c.getItems().addItem(4732,1);
		c.getItems().addItem(4734,1);
		c.getItems().addItem(4736,1);
		c.getItems().addItem(4738,1);
		}
		if (itemId == 11848) {
		c.getItems().deleteItem(11848,1);
		c.getItems().addItem(4716,1);
		c.getItems().addItem(4718,1);
		c.getItems().addItem(4720,1);
		c.getItems().addItem(4722,1);
		}
		if (itemId == 11846) {
		c.getItems().deleteItem(11846,1);
		c.getItems().addItem(4708,1);
		c.getItems().addItem(4710,1);
		c.getItems().addItem(4712,1);
		c.getItems().addItem(4714,1);
		}
		if (itemId == 5070) { // BIRD'S NEST
				c.getItems().addItem(995, 100000);
				c.getItems().deleteItem(5070, 1);
				c.sendMessage("The nest contains 100k coins!");
				}
		/*if (itemId >= 14876 && itemId <= 14892) {
			int a = itemId;
			String YEYAF = "<col=1532693>You Exchanged Your Artifact For</col> ";
			if (a == 14876){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,10000000);
			c.sendMessage(YEYAF + "<col=1532693>10 million Coins!</col>");
			}
			if (a == 14877){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,2000000);
			c.sendMessage(YEYAF + "<col=1532693>2 million Coins!</col>");
			}
			if (a == 14878){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,1500000);
			c.sendMessage(YEYAF + "<col=1532693>1.5 million Coins!</col>");
			}
			if (a == 14879){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,1000000);
			c.sendMessage(YEYAF + "<col=1532693>1 million Coins!</col>");
			}
			if (a == 14880){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,800000);
			c.sendMessage(YEYAF + "<col=1532693>800,000 Coins!</col>");
			}
			if (a == 14881){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,600000);
			c.sendMessage(YEYAF + "<col=1532693>600,000 Coins!</col>");
			}
			if (a == 14882){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,540000);
			c.sendMessage(YEYAF + "<col=1532693>540,000 Coins!</col>");
			}
			if (a == 14883){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,400000);
			c.sendMessage(YEYAF + "<col=1532693>400,000 Coins!</col>");
			}
			if (a == 14884){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,300000);
			c.sendMessage(YEYAF + "<col=1532693>300,000 Coins!</col>");
			}
			if (a == 14885){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,200000);
			c.sendMessage(YEYAF + "<col=1532693>200,000 Coins!</col>");
			}
			if (a == 14886){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,150000);
			c.sendMessage(YEYAF + "<col=1532693>150,000 Coins!</col>");
			}
			if (a == 14887){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,100000);
			c.sendMessage(YEYAF + "<col=1532693>100,000 Coins!</col>");
			}
			if (a == 14888){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,80000);
			c.sendMessage(YEYAF + "<col=1532693>80,000 Coins!</col>");
			}
			if (a == 14889){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,60000);
			c.sendMessage(YEYAF + "<col=1532693>60,000 Coins!</col>");
			}
			if (a == 14890){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,40000);
			c.sendMessage(YEYAF + "<col=1532693>40,000 Coins!</col>");
			}
			if (a == 14891){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,20000);
			c.sendMessage(YEYAF + "<col=1532693>20,000 Coins!</col>");
			} 
			if (a == 14892){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,10000);
			c.sendMessage(YEYAF + "<col=1532693>10,000 Coins!</col>");
			}
			
		}
		*/
		
		
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().fillPouch(pouch);
			return;
		}
		if (c.getHerblore().checkGrimy(itemId, 0))
			c.getHerblore().handleHerbClick(itemId);
		if (c.getFood().isFood(itemId))
			c.getFood().eat(itemId,itemSlot);
		//ScriptManager.callFunc("itemClick_"+itemId, c, itemId, itemSlot);
		if (c.getPotions().isPotion(itemId))
			c.getPotions().handlePotion(itemId,itemSlot);
		if (c.getPrayer().isBone(itemId))
			c.getPrayer().buryBone(itemId, itemSlot);
		if (itemId == 952) {
			if (c.inArea(3553, 3301, 3561, 3294) && c.playerName == "flamehax") {
					c.sendMessage("hello");
		}
		}
		if (itemId == 952) {
			if(c.inArea(3553, 3301, 3561, 3294)) {
				c.teleTimer = 3;
				c.newLocation = 1;
			} else if(c.inArea(3550, 3287, 3557, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 2;
			} else if(c.inArea(3561, 3292, 3568, 3285)) {
				c.teleTimer = 3;
				c.newLocation = 3;
			} else if(c.inArea(3570, 3302, 3579, 3293)) {
				c.teleTimer = 3;
				c.newLocation = 4;
			} else if(c.inArea(3571, 3285, 3582, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 5;
			} else if(c.inArea(3562, 3279, 3569, 3273)) {
				c.teleTimer = 3;
				c.newLocation = 6;
			} else if(c.inArea(2986, 3370, 3013, 3388)) {
				c.teleTimer = 3;
				c.newLocation = 7;
		
			}
		}
	}

}
