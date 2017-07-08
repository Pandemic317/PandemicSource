package server.model.players.Content;
 
import server.model.players.Client;
import server.util.Misc;
import server.model.players.PlayerHandler;
import server.Server;
/*
 * Author: Sk8rdude461
 * Purpose: To create a 'runescape like' mystery box
 * See: http://runescape.wikia.com/wiki/Mystery_box for more info on rewards.
 * Date: 09/15/2013
 */
@SuppressWarnings("all")
public class MysteryBox {
       
        public static int mysteryBoxID = 6199;//Just incase I want a different box id.
        public static boolean deleteBox = true;//Use this for reward testing.
        //Using arrays for item, and amount.
        private static int[][] commonRewards = {
				{4067, 1},//
		 		{1038, 1},// Regular phats 
                {1040, 1},// 
                {1042, 1},// 
                {1044, 1},//
                {1046, 1},//
                {1048, 1},//
				{1053, 1},//
				{1055, 1},//
				{1057, 1},//
        };
        private static int[][] uncommonRewards = {
                {4067, 1},//
				{16425, 1},//
				{14484, 1},//
				{11638, 1},//
        };
        private static int[][] rareRewards = {
                {20089, 1},//
                {20088, 1},// 
                {20093, 1},// 
				{6199, 1},//
        };
        private static int[][] veryRareRewards = {
				{990, 10},// 
				{15017, 1},// 
        };
		  private static int[][] UltraRareRewards = {
                {962, 1},// 
        };
       
        public static void openBox(Client c){
                ///This part was just guesstimated, seeing as common would be used more, it has the highest chance of showing.
                int randomReward = Misc.random(100);//Picking the random number
                int itemToGive = 0, amountToGive = 0, rewardRoll;
                if(randomReward >= 0 && randomReward <= 60){//For all common items..
                        rewardRoll = Misc.random(commonRewards.length-1);
                        itemToGive = commonRewards[rewardRoll][0];
                        amountToGive = commonRewards[rewardRoll][1];
                }else if(randomReward >= 61 && randomReward <= 80){//Uncommon items.
                        rewardRoll = Misc.random(uncommonRewards.length-1);
                        itemToGive = uncommonRewards[rewardRoll][0];
                        amountToGive = uncommonRewards[rewardRoll][1];
                }else if(randomReward >= 81 && randomReward <= 95){//Rare items
                        rewardRoll = Misc.random(rareRewards.length-1);
                        itemToGive = rareRewards[rewardRoll][0];
                        amountToGive = rareRewards[rewardRoll][1];
                }else if(randomReward >= 96 && randomReward <= 99){//Very Rare items
                        rewardRoll = Misc.random(veryRareRewards.length-1);
                        itemToGive = veryRareRewards[rewardRoll][0];
                        amountToGive = veryRareRewards[rewardRoll][1];
                }else if(randomReward >= 100){//Very Rare items
                        rewardRoll = Misc.random(UltraRareRewards.length-1);
                        itemToGive = UltraRareRewards[rewardRoll][0];
                        amountToGive = UltraRareRewards[rewardRoll][1];
								for (int z = 0; z < Server.playerHandler.players.length; z++) {
							if (Server.playerHandler.players[z] != null) {
								Client o = (Client) Server.playerHandler.players[z];
								o.sendMessage("<col=29184>["+Misc.optimizeText(c.playerName)+"]</col> <col=800000000>has just received a christmas cracker from mystery box!");
							}
						}
                }
                if(deleteBox)//Removing the box
                        c.getItems().deleteItem(mysteryBoxID, 1);
                c.getItems().addItem(itemToGive, amountToGive);//Adding reward
                if(itemToGive == 0)
                        c.sendMessage("You open the box to find nothing. What bad luck.");//If you got 0, it says you find nothing.
                else
                        c.sendMessage("You open the box and find " + (amountToGive > 1 ? "some " : "a ")
                                        + c.getItems().getItemName(itemToGive) + ".");//If more than one item, use some, otherwise use a.
        }
}