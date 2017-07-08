package server.model.players;

import java.util.HashMap;

/**
 * @author Sanity
 */
@SuppressWarnings("all")
public class Food {


  private Client c;

  public Food (Client c) {
    this.c = c;  
  }
  public static enum FoodToEat {    
    
  /*	HEIM_CRAB(18159,12,"Heim crab"),
  	ROCKTAIL(15272,24,"Rocktail"),
    MANTA(391,22,"Manta Ray"),
    SHARK(385,20,"Shark"),
    WEBSNIPPER(18169,5,"Web Snipper"),
    LOBSTER(379,12,"Lobster"),
    TROUT(333,7,"Trout"),
    SALMON(329,9,"Salmon"),
    SWORDFISH(373,14,"Swordfish"),
    TUNA(361,10,"Tuna"),
    MONKFISH(7946,16,"Monkfish"),
    SEA_TURTLE(397,21,"Sea Turtle"),
    CAKE(1891,4,"Cake"),
    BASS(365,13,"Bass"),
    COD(339,7,"Cod"),
    BANDAGES(4049,3,"Bandages"),
    POTATO(1942,1,"Potato"),
    BAKED_POTATO(6701,4,"Baked Potato"),
    POTATO_WITH_CHEESE(6705,16,"Potato with Cheese"),
    EGG_POTATO(7056,16,"Egg Potato"),
    CHILLI_POTATO(7054,14,"Chilli Potato"),
    MUSHROOM_POTATO(7058,20,"Mushroom Potato"),
    TUNA_POTATO(7060,22,"Tuna Potato"),
    SHRIMPS(315,3,"Shrimps"),
    HERRING(347,5,"Herring"),
    SARDINE(325,4,"Sardine"),
    CHOCOLATE_CAKE(1897,5,"Chocolate Cake"),
    ANCHOVIES(319,1,"Anchovies"),
    PLAIN_PIZZA(2289,7,"Plain Pizza"),
    MEAT_PIZZA(2293,8,"Meat Pizza"),
    ANCHOVY_PIZZA(2297,9,"Anchovy Pizza"),
    PINEAPPLE_PIZZA(2301,11,"Pineapple Pizza"),
    BREAD(2309,5,"Bread"),
    APPLE_PIE(2323,7,"Apple Pie"),
    REDBERRY_PIE(2325,5,"Redberry Pie"),
    MEAT_PIE(2327,6,"Meat Pie"),
    PIKE(351,8,"Pike"),
    POTATO_WITH_BUTTER(6703,14,"Potato with Butter"),
    BANANA(1963,2,"Banana"),
    PEACH(6883,8,"Peach"),
    ORANGE(2108,2,"Orange"),
    PINEAPPLE_RINGS(2118,2,"Pineapple Rings"),
    PINEAPPLE_CHUNKS(2116,2,"Pineapple Chunks"),
    TROLL_POTION(3265,1,"Troll Potion"),
    BIG_SWORDFISH(7992,19,"Big Swordfish"),
    BIG_SHARK(7994,25,"Big Shark"),
    WHALE(3301,27,"Whale"),
    STINGRAY(3303,30,"Stingray");*/
	ROCKTAIL(15055,0,"Rocktail"),
		MANTA(391,22,"Manta Ray"),
		SHARK(385,20,"Shark"),
		LOBSTER(379,12,"Lobster"),
		TROUT(333,7,"Trout"),
		SALMON(329,9,"Salmon"),
		SWORDFISH(373,14,"Swordfish"),
		TUNA(361,10,"Tuna"),
		MONKFISH(7946,16,"Monkfish"),
		SEA_TURTLE(397,21,"Sea Turtle"),
		CAKE(1891,4,"Cake"),
		BANDAGES(4049,3,"Bandages"),
		COOKED_KARAMBWAN(3144, 18, "Cooked karambwan"),
		BASS(365,13,"Bass"),
		COD(339,7,"Cod"),
		POTATO(1942,1,"Potato"),
		BAKED_POTATO(6701,4,"Baked Potato"),
		POTATO_WITH_CHEESE(6705,16,"Potato with Cheese"),
		EGG_POTATO(7056,16,"Egg Potato"),
		CHILLI_POTATO(7054,14,"Chilli Potato"),
		MUSHROOM_POTATO(7058,20,"Mushroom Potato"),
		TUNA_POTATO(7060,22,"Tuna Potato"),
		SHRIMPS(315,3,"Shrimps"),
		HERRING(347,5,"Herring"),
		SARDINE(325,4,"Sardine"),
		CHOCOLATE_CAKE(1897,5,"Chocolate Cake"),
		ANCHOVIES(319,1,"Anchovies"),
		PLAIN_PIZZA(2289,7,"Plain Pizza"),
		MEAT_PIZZA(2293,8,"Meat Pizza"),
		ANCHOVY_PIZZA(2297,9,"Anchovy Pizza"),
		PINEAPPLE_PIZZA(2301,11,"Pineapple Pizza"),
		BREAD(2309,5,"Bread"),
		APPLE_PIE(2323,7,"Apple Pie"),
		REDBERRY_PIE(2325,5,"Redberry Pie"),
		MEAT_PIE(2327,6,"Meat Pie"),
		PIKE(351,8,"Pike"),
		POTATO_WITH_BUTTER(6703,14,"Potato with Butter"),
		BANANA(1963,2,"Banana"),
		PEACH(6883,8,"Peach"),
		ORANGE(2108,2,"Orange"),
		PINEAPPLE_RINGS(2118,2,"Pineapple Rings"),
		PINEAPPLE_CHUNKS(2116,2,"Pineapple Chunks"),
		PURPLE_SWEETS(10476,25,"Purple Sweet"),
		WEB_SNIPPER(18169,15, "Web snipper"),
		BOULDABASS(18171,17, "Bouldabass"),
		BLUE_CRAB(18175,22, "Blue crab"),
		CAVE_MORAY(18177,25, "Cave moray"),
		DUSK_EEL(18163,7, "Dusk eel"),
		GIANT_FLATFISH(18165,10, "Giant flatfish"),
		SHORT_FINNED_EEL(18167,12, "Short-finned eel"),
		RED_EYE(18161,5, "Red-eye"),
		HEIM_CRAB(18159,2, "Heim crab"),
		SALVE_EEL(18173,20, "Salve eel"),
		Easter_Egg(1961, 12, "Easter Egg"),
		Pumpkin(1959, 14, "Pumpkin"), 
		Half_Jug_of_Wine(1989, 7, "Half Full Wine Jug"),
		Wine(1993, 11, "Wine"),
		BEER(1917, 1, "Beer"), 
		GREENMANS_ALE(1909, 1,"Greenman's Ale"), 
		CABBAGE(1965, 1, "Cabbage"), 
        HALF_CHOCOLATE_CAKE(1899, 5,"2/3 Chocolate Cake"),
        CHOCOLATE_SLICE(1901, 5,"Chocolate Slice"), 
        HALF_PLAIN_PIZZA(2291, 7,"1/2 Plain pizza"), 
        HALF_MEAT_PIZZA(2295, 8, "1/2 Meat Pizza"), 
        HALF_ANCHOVY_PIZZA(2299, 9,"1/2 Anchovy Pizza"), 
        HALF_PINEAPPLE_PIZZA(2303, 11,"1/2 Pineapple Pizza"), 
        HALF_APPLE_PIE(2335, 7, "Half Apple Pie"), 
        HALF_REDBERRY_PIE(2333, 5,"Half Redberry Pie"), 
        Ugthanki_kebab(1883, 2, "Ugthanki kebab"), 
        HALF_MEAT_PIE(2331, 6, "Half Meat Pie"), 
        SUMMER_PIE(7218, 11, "Summer Pie"), 
        HALF_SUMMER_PIE(7220, 11,"Half Summer Pie"), 
        EASTER_EGG(7928, 1, "Easter Egg"), 
        EASTER_EGG2(7929, 1, "Easter Egg"),
        EASTER_EGG3(7930, 1, "Easter Egg"), 
        EASTER_EGG4(7931, 1, "Easter Egg"), 
        EASTER_EGG5(7932, 1, "Easter Egg"), 
        EASTER_EGG6(7933, 1, "Easter Egg"),
    	CHICKEN(2140, 3, "Cooked Chicken"),
		MACKERAL(355, 6, "Mackeral"),
		BISCUITS(19467,20,"Cookies");



    private int id; private int heal; private String name;

    private FoodToEat(int id, int heal, String name) {
      this.id = id;
      this.heal = heal;
      this.name = name;    
    }

    public int getId() {
      return id;
    }

    public int getHeal() {
      return heal;
    }

    public String getName() {
      return name;
    }
    public static HashMap <Integer,FoodToEat> food = new HashMap<Integer,FoodToEat>();

    public static FoodToEat forId(int id) {
      return food.get(id);
    }

    static {
    for (FoodToEat f : FoodToEat.values())
      food.put(f.getId(), f);
    }
  }
  public int gwdarmourStat(double amount) {
		return (int)(c.getLevelForXP(c.playerXP[3]) * amount);
	}
  public boolean hasFullPrimal() {
    return c.playerEquipment[c.playerHat] == 13362 && c.playerEquipment[c.playerChest] == 13360 && c.playerEquipment[c.playerLegs] == 13361;
  }

  public boolean torva() {
    return c.playerEquipment[c.playerHat] == 13362 && c.playerEquipment[c.playerChest] == 13360 && c.playerEquipment[c.playerLegs] == 13361;
  }
  public boolean dbone() {
    return c.playerEquipment[c.playerHat] == 11607 && c.playerEquipment[c.playerChest] == 11608 && c.playerEquipment[c.playerLegs] == 11611;
  }

    	public void eat(int id, int slot) {
		if (c.duelRule[6]) {
			c.sendMessage("You may not eat in this duel.");
			return;
		}
		if (System.currentTimeMillis() - c.foodDelay >= 1500 && c.playerLevel[3] > 0) {
			c.getCombat().resetPlayerAttack();
			c.attackTimer += 2;
			c.startAnimation(829);
			//AchievementManager.increase(c, Achievements.MUNCHER);
			c.getItems().deleteItem(id,slot,1);
			FoodToEat f = FoodToEat.food.get(id);
			if (c.playerLevel[3] < gwdarmourStat(1.41) && c.torva()) {
				c.playerLevel[3] += f.getHeal();
				if (c.playerLevel[3] > gwdarmourStat(1.41))
					c.playerLevel[3] = gwdarmourStat(1.41);
			}
			else if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3]) && !c.torva()) {
				c.playerLevel[3] += f.getHeal();
				if(id != 15272) {
				if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
//this makes sure normal food doesn't overload
			} else {
// this says if their eating rocktails and their hp level is more then their player xp + 10, then make it playerxp + 10. If it isnt then it will overload anyway.
			if ((c.playerLevel[3] > (c.getLevelForXP(c.playerXP[3])))) {
					c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]) + 10;
			}
			}
			}
			c.foodDelay = System.currentTimeMillis();
			c.getPA().refreshSkill(3);
			c.sendMessage("You eat the " + f.getName() + ".");
		}			
	}


  public boolean isFood(int id) {
    return FoodToEat.food.containsKey(id);
  }  


}