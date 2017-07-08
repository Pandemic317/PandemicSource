package server.world;

import java.util.ArrayList;

import server.model.objects.Object;
import server.util.Misc;
import server.model.players.Client;
import server.Server;

/**
 * @author Sanity
 */
@SuppressWarnings("all")
public class ObjectManager {

	public ArrayList<Object> objects = new ArrayList<Object>();
	private ArrayList<Object> toRemove = new ArrayList<Object>();
	public void process() {
		for (Object o : objects) {
			if (o.tick > 0)
				o.tick--;
			else {
				updateObject(o);
				toRemove.add(o);
			}		
		}
		for (Object o : toRemove) {
			if (isObelisk(o.newId)) {
				int index = getObeliskIndex(o.newId);
				if (activated[index]) {
					activated[index] = false;
					teleportObelisk(index);
				}
			}
			objects.remove(o);	
		}
		toRemove.clear();
	}
	/*public void removeObject(int x, int y) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().object(-1, x, y, 0, 10);			
			}	
		}	
	}*/
	 public void removeObject(int x, int y) {
        for (int j = 0; j < Server.playerHandler.players.length; j++) {
            if (Server.playerHandler.players[j] != null) {
                Client c = (Client)Server.playerHandler.players[j];
                c.getPA().object(-1, x, y, 0, 10);            
                c.getPA().object(158, 3097, 3493, 0, 10);
            }    
        }    
    }
	
	public void updateObject(Object o) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				c.getPA().object(o.newId, o.objectX, o.objectY, o.face, o.type);			
			}	
		}	
	}
	
	public void placeObject(Object o) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				if (c.distanceToPoint(o.objectX, o.objectY) <= 60)
					c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
			}	
		}
	}
	
	public Object getObject(int x, int y, int height) {
		for (Object o : objects) {
			if (o.objectX == x && o.objectY == y && o.height == height)
				return o;
		}	
		return null;
	}

	public void Deletewalls(Client c) {
    c.getPA().checkObjectSpawn(-1, 2752, 3512, -1, 0);
	c.getPA().checkObjectSpawn(-1, 2751, 3513, -1, 0);
	c.getPA().checkObjectSpawn(-1, 2752, 3513, -1, 0);
	c.getPA().checkObjectSpawn(-1, 2750, 3513, -1, 0); //2758, 3503
	
	c.getPA().checkObjectSpawn(-1, 2758, 3503, -1, 0); //2758, 3503
	
	c.getPA().checkObjectSpawn(-1, 2750, 3504, 0, 0); // new home
	c.getPA().checkObjectSpawn(-1, 2750, 3495, 0, 0); // new home
	c.getPA().checkObjectSpawn(-1, 2757, 3503, 0, 0); // new home
	c.getPA().checkObjectSpawn(-1, 2766, 3504, 0, 0); // new home
	c.getPA().checkObjectSpawn(-1, 2766, 3495, 0, 0); // new home
}
	public void loadObjects(Client c) {
		if (c == null)
			return;
		for (Object o : objects) {
			if (loadForPlayer(o,c))
				c.getPA().object(o.objectId, o.objectX, o.objectY, o.face, o.type);
		}
		loadCustomSpawns(c);
		Deletewalls(c);
		if (c.distanceToPoint(2813, 3463) <= 60) {
			//c.getFarming().updateHerbPatch();
		}
	}
	
	private int[][] customObjects = {{}};
	public void loadCustomSpawns(Client c) { 
		c.getPA().checkObjectSpawn(4954, 2399, 4440, 0, 10);//tzhaarspawn object to enter
		/***Project-Nightmare***\
		\***OBJECTMAN***/
		c.getPA().checkObjectSpawn(-1, 2754, 3509, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 3427, 2908, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 3427, 2909, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 2590, 9449, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 3427, 2910, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 3423, 2908, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 3423, 2909, 0, 10); // new hom 3425, 2915
				c.getPA().checkObjectSpawn(-1, 3423, 2910, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 3424, 2907, 0, 10); // new hom
				
				c.getPA().checkObjectSpawn(-1, 3426, 2930, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 3424, 2925, 0, 10); // new hom
				
				c.getPA().checkObjectSpawn(-1, 3413, 2908, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 3416, 2908, 0, 10); // new hom
				
				c.getPA().checkObjectSpawn(-1, 3425, 2915, 0, 10); // new hom 3425, 2915
				
				c.getPA().checkObjectSpawn(-1, 3420, 2940, 0, 10); // new hom
				c.getPA().checkObjectSpawn(-1, 3551, 2940, 0, 10); // new hom
				

				c.getPA().checkObjectSpawn(-1, 3551, 9695, 0, 0); // Barrows Chest
	
	
	//	for (int j = 3506; j < 3515; j++) {
				//c.getPA().checkObjectSpawn(-1, 2754, j, 0, 22); // new hom
				//}
				c.getPA().checkObjectSpawn(-1, 2754, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2754, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2754, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2754, 3509, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2754, 3510, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2754, 3511, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2754, 3512, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2754, 3513, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2754, 3514, 0, 22); // new hom
				
				//for (int j = 3506; j < 3515; j++) {
				//c.getPA().checkObjectSpawn(-1, 2755, j, 0, 22); // new hom
				//}
				c.getPA().checkObjectSpawn(-1, 2755, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2755, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2755, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2755, 3509, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2755, 3510, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2755, 3511, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2755, 3512, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2755, 3513, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2755, 3514, 0, 22); // new hom
				//for (int j = 3506; j < 3515; j++) {
			//	c.getPA().checkObjectSpawn(-1, 2756, j, 0, 22); // new hom
				//}
				c.getPA().checkObjectSpawn(-1, 2756, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2756, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2756, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2756, 3509, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2756, 3510, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2756, 3511, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2756, 3512, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2756, 3513, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2756, 3514, 0, 22); // new hom
			//	for (int j = 3506; j < 3515; j++) {
				//c.getPA().checkObjectSpawn(-1, 2757, j, 0, 22); // new hom
				//}
				c.getPA().checkObjectSpawn(-1, 2757, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2757, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2757, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2757, 3509, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2757, 3510, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2757, 3511, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2757, 3512, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2757, 3513, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2757, 3514, 0, 22); // new hom
			//	for (int j = 3506; j < 3515; j++) {
			//	c.getPA().checkObjectSpawn(-1, 2758, j, 0, 22); // new hom
			//	}
				c.getPA().checkObjectSpawn(-1, 2758, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2758, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2758, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2758, 3509, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2758, 3510, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2758, 3511, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2758, 3512, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2758, 3513, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2758, 3514, 0, 22); // new hom
				c.getPA().checkObjectSpawn(2274, 1892, 4824, 0, 10);// Skilling portal
				c.getPA().checkObjectSpawn(2273, 1891, 4824, 0, 10);// Skilling portal
				c.getPA().checkObjectSpawn(7315, 3182, 3414, 0, 10);// Skilling portal

				c.getPA().checkObjectSpawn(3042, 3182, 3406, 0, 10);// Daeyalt Rock
				c.getPA().checkObjectSpawn(3042, 3182, 3405, 0, 10);// Daeyalt Rock

				c.getPA().checkObjectSpawn(2672, 3177, 3403, 3, 10);// Daeyalt Anvil
				c.getPA().checkObjectSpawn(2672, 3178, 3403, 3, 10);// Daeyalt Anvil
				c.getPA().checkObjectSpawn(2672, 3179, 3403, 3, 10);// Daeyalt Anvil
				c.getPA().checkObjectSpawn(2672, 3180, 3403, 3, 10);// Daeyalt Anvil
				c.getPA().checkObjectSpawn(2672, 3181, 3403, 3, 10);// Daeyalt Anvil
				c.getPA().checkObjectSpawn(2672, 3182, 3403, 3, 10);// Daeyalt Anvil
				
				c.getPA().checkObjectSpawn(3581, 3176, 3409, 3, 10);//Thieving Ticket Dispenser
				c.getPA().checkObjectSpawn(3608, 3176, 3410, 3, 10);//Donator Thieving Ticket Dispenser
				
				c.getPA().checkObjectSpawn(7315, 1889, 4824, 0, 10);// Skilling portal
				c.getPA().checkObjectSpawn(2478, 3485, 9492, 4, 10);// Air Altar
				c.getPA().checkObjectSpawn(2482, 3479, 9502, 4, 10);// Fire Altar
				c.getPA().checkObjectSpawn(2479, 3482, 9495, 4, 10);// Mind Altar
				c.getPA().checkObjectSpawn(2480, 3480, 9498, 4, 10);// Water Altar
				c.getPA().checkObjectSpawn(2487, 3488, 9510, 4, 10);// Chaos Altar
				c.getPA().checkObjectSpawn(2484, 3483, 9507, 4, 10);// Cosmic Altar
				c.getPA().checkObjectSpawn(2486, 3493, 9504, 4, 10);// Nature Altar
				c.getPA().checkObjectSpawn(2488, 3497, 9500, 4, 10);// Death Altar
				c.getPA().checkObjectSpawn(2480, 3491, 9495, 4, 10);// Blood Altar
				

				/*c.getPA().checkObjectSpawn(1, 2829, 3277, 4, 10);// Blood Altar
				c.getPA().checkObjectSpawn(1, 2829, 3278, 4, 10);// Blood Altar
				c.getPA().checkObjectSpawn(1, 2829, 3279, 4, 10);// Blood Altar
				c.getPA().checkObjectSpawn(1, 2829, 3280, 4, 10);// Blood Altar
				c.getPA().checkObjectSpawn(1, 2829, 3281, 4, 10);// Blood Altar
				c.getPA().checkObjectSpawn(1, 2829, 3282, 4, 10);// Blood Altar
				c.getPA().checkObjectSpawn(1, 2829, 3283, 4, 10);// Blood Altar
				c.getPA().checkObjectSpawn(1, 2829, 3284, 4, 10);// Blood Altar*/
				c.getPA().checkObjectSpawn(1, 2823, 3272, 4, 10);// Blood Altar
				c.getPA().checkObjectSpawn(1, 2822, 3272, 4, 10);// Blood Altar
				

				c.getPA().checkObjectSpawn(2466, 2406, 3488, 0, 10);// Skilling portal
				c.getPA().checkObjectSpawn(2466, 2406, 3489, 0, 10);// Skilling portal
				
				
				//for (int j = 3506; j < 3515; j++) {
				//c.getPA().checkObjectSpawn(-1, 2759, j, 0, 22); // new hom
				//}
				c.getPA().checkObjectSpawn(-1, 2759, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2759, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2759, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2759, 3509, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2759, 3510, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2759, 3511, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2759, 3512, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2759, 3513, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2759, 3514, 0, 22); // new hom
				//for (int j = 3506; j < 3515; j++) {
				//c.getPA().checkObjectSpawn(-1, 2760, j, 0, 22); // new hom
				//}
				c.getPA().checkObjectSpawn(-1, 2760, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2760, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2760, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2760, 3509, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2760, 3510, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2760, 3511, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2760, 3512, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2760, 3513, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2760, 3514, 0, 22); // new hom
				//for (int j = 3506; j < 3515; j++) {
			//	c.getPA().checkObjectSpawn(-1, 2761, j, 0, 22); // new hom
				//}
				c.getPA().checkObjectSpawn(-1, 2761, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2761, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2761, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2761, 3509, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2761, 3510, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2761, 3511, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2761, 3512, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2761, 3513, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2761, 3514, 0, 22); // new hom
				//for (int j = 3506; j < 3515; j++) {
			//	c.getPA().checkObjectSpawn(-1, 2762, j, 0, 22); // new hom
			//	}
				c.getPA().checkObjectSpawn(-1, 2762, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2762, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2762, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2762, 3509, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2762, 3510, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2762, 3511, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2762, 3512, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2762, 3513, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2762, 3514, 0, 22); // new hom
			//	for (int j = 3505; j < 3510; j++) {
				//c.getPA().checkObjectSpawn(-1, 2750, j, 0, 22); // new hom
			//	}
					c.getPA().checkObjectSpawn(-1, 2750, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2750, 3505, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2750, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2750, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2750, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2750, 3509, 0, 22); // new hom
			//	for (int j = 3505; j < 3510; j++) {
			//	c.getPA().checkObjectSpawn(-1, 2751, j, 0, 22); // new hom
				//}
				c.getPA().checkObjectSpawn(-1, 2751, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2751, 3505, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2751, 3506, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2751, 3507, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2751, 3508, 0, 22); // new hom
				c.getPA().checkObjectSpawn(-1, 2751, 3509, 0, 22); // new hom
				
				
				c.getPA().checkObjectSpawn(-1, 3421, 2928, 0, 10); // new hom
				c.getPA().checkObjectSpawn(2996, 3421, 2928, 0, 10); //Skills-Oblisk-Summoning
                                c.getPA().checkObjectSpawn(11210, 2404, 3488, 0, 10); //Skills-Oblisk-Summoning
                                c.getPA().checkObjectSpawn(11210, 2398, 3488, 0, 10); //Skills-Oblisk-Summoning
                                c.getPA().checkObjectSpawn(11210, 2394, 3488, 0, 10); //Skills-Oblisk-Summoning
                                c.getPA().checkObjectSpawn(11210, 2390, 3488, 0, 10); //Skills-Oblisk-Summoning
	//new home
c.getPA().checkObjectSpawn(35469, 2757, 3509, 0, 10);// right
//c.getPA().checkObjectSpawn(13671, 1864, 5352, 0, 10);// right
//c.getPA().checkObjectSpawn(13671, 1864, 5352, 0, 10);// right
//c.getPA().checkObjectSpawn(13671, 1864, 5352, 0, 10);// right

//Newhome objects Pandemic!
	c.getPA().checkObjectSpawn(7315, 2041, 4538, 0, 10);//funpk teleporter
	c.getPA().checkObjectSpawn(411, 2038, 4518, 0, 10); // Curse Prayers
	c.getPA().checkObjectSpawn(409, 2036, 4518, 0, 10); // Normal praying altar
	c.getPA().checkObjectSpawn(410, 2034, 4518, 0, 10); //lunar altar
	c.getPA().checkObjectSpawn(6552, 2031, 4518, 0, 10); //ancient altar
//End Newhome Pandemic

//undead crates
	c.getPA().checkObjectSpawn(1, 2988, 9644, 0, 10); // Normal praying altar
	c.getPA().checkObjectSpawn(1, 2989, 9644, 0, 10); //lunar altar
//end of crates
c.getPA().checkObjectSpawn(-1, 2757, 3498, 0, 0);
c.getPA().checkObjectSpawn(35469, 2756, 3497, 0, 10);// right

c.getPA().checkObjectSpawn(2471, 2589, 9449, 0, 10);// right2471
c.getPA().checkObjectSpawn(13671, 1864, 5352, 0, 10);// right2471
c.getPA().checkObjectSpawn(13671, 1863, 5352, 0, 10);// right
c.getPA().checkObjectSpawn(13671, 1864, 5342, 2, 10);// left
c.getPA().checkObjectSpawn(13671, 1863, 5342, 2, 10);// left


c.getPA().checkObjectSpawn(13670, 1865, 5345, 1, 10);// middle down
c.getPA().checkObjectSpawn(13670, 1865, 5347, 1, 10);// middle down
c.getPA().checkObjectSpawn(13670, 1865, 5349, 1, 10);// middle down

c.getPA().checkObjectSpawn(13670, 1862, 5349, 3, 10);// middle up
c.getPA().checkObjectSpawn(13670, 1862, 5347, 3, 10);// middle up
c.getPA().checkObjectSpawn(13670, 1862, 5345, 3, 10);// middle up




	c.getPA().checkObjectSpawn(2728, 2045, 4636, 0, 10);//Range-Skills-Cooking
	c.getPA().checkObjectSpawn(13405, 2044, 4632, 3, 10);//Cons-Portal-Skills-Cooking
	c.getPA().checkObjectSpawn(2094, 2042, 4639, 1, 10);//Tin-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2094, 2041, 4639, 1, 10);//Tin-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2090, 2040, 4639, 1, 10);//Cop-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2090, 2039, 4639, 1, 10);//Cop-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2092, 2038, 4639, 1, 10);//Iron-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2092, 2037, 4639, 1, 10);//Iron-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2096, 2036, 4639, 1, 10);//Coal-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2096, 2035, 4639, 1, 10);//Coal-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2098, 2034, 4642, 1, 10);//Gold-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2098, 2034, 4643, 1, 10);//Gold-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2100, 2034, 4640, 1, 10);//Silver-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2100, 2034, 4641, 1, 10);//Silver-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2102, 2034, 4644, 1, 10);//Mith-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2102, 2034, 4645, 1, 10);//Mith-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2104, 2036, 4646, 1, 10);//Addy-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2104, 2037, 4646, 1, 10);//Addy-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(14859, 2039, 4646, 1, 10);//Rune-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(14859, 2040, 4646, 1, 10);//Rune-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(3044, 2040, 4650, 1, 10);//Furnace-Skills-Smithing
	c.getPA().checkObjectSpawn(2783, 2038, 4650, 1, 10);//Anvil-Skills-Smithing
	c.getPA().checkObjectSpawn(2783, 2038, 4648, 1, 10);//Anvil-Skills-Smithing
	c.getPA().checkObjectSpawn(4874, 2039, 4629, 0, 10);//thiev stall
	c.getPA().checkObjectSpawn(4875, 2039, 4630, 0, 10);//thiev stall
	c.getPA().checkObjectSpawn(4876, 2039, 4631, 0, 10);//thiev stall
	c.getPA().checkObjectSpawn(4877, 2039, 4632, 0, 10);//thiev stall
	c.getPA().checkObjectSpawn(4878, 2039, 4633, 0, 10);//thiev stall
	
	c.getPA().checkObjectSpawn(2213, 2737, 3468, 0, 10);//DiceZone bank
	c.getPA().checkObjectSpawn(2213, 2738, 3468, 0, 10);//DiceZone bank
	
	c.getPA().checkObjectSpawn(2213, 3219, 3219, 1, 10);//Shops bank
	c.getPA().checkObjectSpawn(2213, 3219, 3218, 1, 10);//Shops bank
	

	c.getPA().checkObjectSpawn(2213, 2376, 4950, 2, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2377, 4950, 2, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2378, 4950, 2, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2379, 4950, 2, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2380, 4950, 2, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2381, 4950, 2, 10);//Privatezone bank
	
	
	c.getPA().checkObjectSpawn(2213, 2873, 3555, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3554, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3553, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3552, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3551, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3550, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3549, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3548, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3547, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3546, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3545, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3544, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3543, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3542, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3541, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3540, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3539, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3538, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2873, 3537, 1, 10);//Privatezone bank
	
	
	//Gnomeball Home
	
	c.getPA().checkObjectSpawn(2213, 2396, 3492, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2396, 3491, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2396, 3490, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2396, 3486, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2396, 3485, 1, 10);//Privatezone bank
	c.getPA().checkObjectSpawn(2213, 2396, 3484, 1, 10);//Privatezone bank

	c.getPA().checkObjectSpawn(27254, 2389, 3494, 0, 10);//Dark Portal
	
	c.getPA().checkObjectSpawn(18266, 2401, 3481, 2, 10);//Tunnel Entrance
	
	c.getPA().checkObjectSpawn(3485, 2381, 3488, 3, 10);//Wishing Well
	c.getPA().checkObjectSpawn(3485, 2381, 3489, 3, 10);//Wishing Well

	c.getPA().checkObjectSpawn(409, 2393, 3483, 0, 10);//altar
	c.getPA().checkObjectSpawn(411, 2391, 3483, 0, 10);//altar
	c.getPA().checkObjectSpawn(6552, 2388, 3483, 0, 10);//altar

	//historics home
        c.getPA().checkObjectSpawn(26972, 2038, 4535, 0, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2037, 4535, 0, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2036, 4535, 0, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2035, 4535, 0, 10);//Bank booth
        c.getPA().checkObjectSpawn(2996, 2033, 4535, 1, 10);//crystal chest.

        //ckey chest customs
        c.getPA().checkObjectSpawn(2996, 3053, 4983, 2, 10);//crystal chest.

	//Home Stool fix
        c.getPA().checkObjectSpawn(1101, 2044, 4527, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2044, 4528, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2044, 4529, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2044, 4530, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2044, 4531, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2029, 4527, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2029, 4528, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2029, 4529, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2029, 4530, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2029, 4531, 2, 10);//fix

        c.getPA().checkObjectSpawn(1101, 2039, 4517, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2040, 4517, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2041, 4517, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2042, 4517, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2043, 4517, 2, 10);//fix

        c.getPA().checkObjectSpawn(1101, 2043, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2042, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2041, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2040, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2039, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2038, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2037, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2036, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2035, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2034, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2033, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2032, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2031, 4541, 2, 10);//fix
        c.getPA().checkObjectSpawn(1101, 2030, 4541, 2, 10);//fix
	//End of Home Stool fix


	c.getPA().checkObjectSpawn(2110, 2445, 4953, 1, 10);//Blu-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2109, 2444, 4953, 1, 10);//Clay-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2106, 2443, 4953, 1, 10);//Clay-Ore-Skills-Mining
	c.getPA().checkObjectSpawn(2111, 2442, 4953, 1, 10);//Purp-Ore-Skills-Mining

	c.getPA().checkObjectSpawn(1292, 2719, 3465, 1, 10);//99plus-Skills-Woodcutting
	c.getPA().checkObjectSpawn(1311, 2720, 3457, 1, 10);//99plus-Skills-Woodcutting
	c.getPA().checkObjectSpawn(2023, 2720, 3462, 1, 10);//99plus-Skills-Woodcutting
	c.getPA().checkObjectSpawn(2073, 2722, 3459, 1, 10);//99plus-Skills-Woodcutting

	c.getPA().checkObjectSpawn(823, 2480, 3432, 1, 10);//99plus-Skills-Agility
	c.getPA().checkObjectSpawn(822, 2480, 3429, 1, 10);//99plus-Skills-Agility
	c.getPA().checkObjectSpawn(2641, 2480, 3426, 1, 10);//99plus-Skills-Agility
	c.getPA().checkObjectSpawn(3563, 2480, 3424, 1, 10);//99plus-Skills-Agility




	c.getPA().checkObjectSpawn(2213, 2038, 4644, 0, 10);//Skills-Bank
	c.getPA().checkObjectSpawn(2213, 2038, 4642, 0, 10);//Skills-Bank
	c.getPA().checkObjectSpawn(2213, 2039, 4643, 3, 10);//Skills-Bank
	c.getPA().checkObjectSpawn(2213, 2037, 4643, 3, 10);//Skills-Bank

	c.getPA().checkObjectSpawn(2213, 2440, 4954, 3, 10);//AdvSkills-Bank

	c.getPA().checkObjectSpawn(2213, 2045, 4634, 3, 10);//Skills-Bank
	c.getPA().checkObjectSpawn(2213, 2737, 3442, 3, 10);//Skills-Bank
	c.getPA().checkObjectSpawn(2213, 2737, 3443, 3, 10);//Skills-Bank

	c.getPA().checkObjectSpawn(2152, 2035, 4649, 0, 10); //Skills-Oblisk-Summoning

	c.getPA().checkObjectSpawn(2213, 3215, 3438, 0, 10); //Skills-Oblisk-Summoning
	c.getPA().checkObjectSpawn(2213, 3214, 3438, 0, 10); //Skills-Oblisk-Summoning
	c.getPA().checkObjectSpawn(2213, 3213, 3438, 0, 10); //Skills-Oblisk-Summoning
	c.getPA().checkObjectSpawn(2213, 3212, 3438, 0, 10); //Skills-Oblisk-Summoning
	c.getPA().checkObjectSpawn(2213, 3211, 3438, 0, 10); //Skills-Oblisk-Summoning
	c.getPA().checkObjectSpawn(2213, 3210, 3438, 0, 10); //Skills-Oblisk-Summoning
	
	
	
	
	c.getPA().checkObjectSpawn(13660, 3426, 2916, 0, 10); //Skills-Oblisk-Summoning
	c.getPA().checkObjectSpawn(6282, 2610, 3149, 1, 10); //Skills-Oblisk-Summoning

	c.getPA().checkObjectSpawn(-1, 2439, 4956, 0, 10); //AdvSkillsZone
	c.getPA().checkObjectSpawn(-1, 2035, 4640, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2043, 4630, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2043, 4629, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2041, 4630, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2044, 4630, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2040, 4630, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2040, 4630, 0, 11); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2041, 4629, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2040, 4629, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2040, 4634, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2035, 4641, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2037, 4640, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2037, 4641, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2037, 4651, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2036, 4651, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2039, 4640, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2039, 4641, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2041, 4640, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2041, 4641, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2041, 4649, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2041, 4650, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2040, 4651, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2040, 4647, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2038, 4649, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2038, 4651, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2035, 4648, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2035, 4650, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2035, 4651, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2036, 4644, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 3190, 3468, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3190, 3472, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3188, 3469, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3188, 3472, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3189, 3460, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3192, 3462, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3212, 3470, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3213, 3470, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3212, 3473, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3206, 3472, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3204, 3478, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3204, 3485, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3203, 3494, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3214, 3486, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3217, 3488, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3217, 3492, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3215, 3477, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3219, 3472, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3223, 3479, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3202, 3497, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3209, 3490, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3223, 3491, 0, 0); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3209, 3493, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3212, 3482, 0, 10); //SkillsZone
c.getPA().checkObjectSpawn(-1, 3229, 3474, 0, 10);
c.getPA().checkObjectSpawn(-1, 3229, 3471, 0, 10);
c.getPA().checkObjectSpawn(-1, 3230, 3471, 0, 10);
c.getPA().checkObjectSpawn(-1, 3226, 3471, 0, 10);
c.getPA().checkObjectSpawn(-1, 3227, 3471, 0, 10);
c.getPA().checkObjectSpawn(-1, 3232, 3471, 0, 10);
c.getPA().checkObjectSpawn(-1, 3233, 3471, 0, 10);
c.getPA().checkObjectSpawn(-1, 3229, 3482, 0, 10);

c.getPA().checkObjectSpawn(7316, 3192, 3468, 0, 10);
c.getPA().checkObjectSpawn(3476, 3188, 3474, 0, 10);
c.getPA().checkObjectSpawn(3476, 3188, 3467, 0, 10);
c.getPA().checkObjectSpawn(6461, 3194, 3469, 0, 10);
c.getPA().checkObjectSpawn(6462, 3194, 3471, 0, 10);
c.getPA().checkObjectSpawn(410, 3190, 3468, 0, 10);
c.getPA().checkObjectSpawn(6552, 3190, 3473, 0, 10);
c.getPA().checkObjectSpawn(1341, 3234, 3464, 0, 10);

c.getPA().checkObjectSpawn(1341, 3234, 3464, 0, 10);
c.getPA().checkObjectSpawn(1341, 3225, 3493, 0, 10);
c.getPA().checkObjectSpawn(1341, 3224, 3465, 0, 10);
c.getPA().checkObjectSpawn(1341, 3224, 3466, 0, 10);
c.getPA().checkObjectSpawn(1341, 3213, 3458, 0, 10);
c.getPA().checkObjectSpawn(1341, 3212, 3458, 0, 10);


c.getPA().checkObjectSpawn(1341, 3234, 3465, 0, 10);
c.getPA().checkObjectSpawn(1341, 3234, 3466, 0, 10);
c.getPA().checkObjectSpawn(1341, 3234, 3467, 0, 10);
c.getPA().checkObjectSpawn(1341, 3213, 3439, 0, 10);
c.getPA().checkObjectSpawn(1341, 3212, 3439, 0, 10);
c.getPA().checkObjectSpawn(12355, 3222, 3472, 0, 10);

	c.getPA().checkObjectSpawn(-1, 2044, 4650, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2036, 4643, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2039, 4644, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2039, 4645, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2037, 4645, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2040, 4644, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2036, 4638, 0, 10); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2042, 4644, 0, 0); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2042, 4633, 0, 0); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2041, 4646, 0, 0); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2038, 4646, 0, 0); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2035, 4646, 0, 0); //SkillsZone
	c.getPA().checkObjectSpawn(-1, 2037, 4633, 0, 0); //SkillsZone

	c.getPA().checkObjectSpawn(-1, 3216, 3429, 0, 10); //Home
	c.getPA().checkObjectSpawn(-1, 3210, 3429, 0, 10); //Home

	c.getPA().checkObjectSpawn(26972, 3053, 4985, 1, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(26972, 3053, 4984, 1, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(1530, 3045, 5001, 1, 9);//Bank booth NewMinigameScavengerDoor
	c.getPA().checkObjectSpawn(1531, 3045, 5001, 1, 9);//Bank booth NewMinigameScavengerDoor
	c.getPA().checkObjectSpawn(81, 3004, 5003, 3, 9);//Bank booth NewMinigameScavengerDoor
	c.getPA().checkObjectSpawn(82, 2973, 5004, 3, 9);//Bank booth NewMinigameScavengerDoor
	c.getPA().checkObjectSpawn(1534, 2963, 5006, 0, 9);//Bank booth NewMinigameScavengerDoor
	c.getPA().checkObjectSpawn(1240, 2964, 5109, 0, 9);//Bank booth NewMinigameScavengerDoor
	c.getPA().checkObjectSpawn(93, 2964, 5105, 3, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(99, 3011, 5103, 3, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(102, 3024, 5102, 3, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(3, 3043, 5105, 0, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(4, 3061, 5076, 0, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(92, 3063, 5061, 0, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(1532, 3060, 5028, 0, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(1533, 3054, 5028, 0, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(1535, 3039, 5042, 0, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(1534, 3024, 5001, 0, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(1804, 3038, 5065, 0, 9);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(2529, 3006, 5088, 0, 9);//Bank booth NewMinigameScavenger



	c.getPA().checkObjectSpawn(26972, 2509, 4711, 2, 10);//Bank booth Cosmetics
	c.getPA().checkObjectSpawn(26972, 2508, 4711, 2, 10);//Bank booth Cosmetics
	c.getPA().checkObjectSpawn(26972, 2507, 4711, 2, 10);//Bank booth Cosmetics
	c.getPA().checkObjectSpawn(26972, 2506, 4711, 2, 10);//Bank booth Cosmetics
	c.getPA().checkObjectSpawn(26972, 2505, 4711, 2, 10);//Bank booth Cosmetics
	c.getPA().checkObjectSpawn(26972, 2504, 4711, 2, 10);//Bank booth Cosmetics
	c.getPA().checkObjectSpawn(26972, 2503, 4711, 2, 10);//Bank booth Cosmetics
	c.getPA().checkObjectSpawn(26972, 2502, 4711, 2, 10);//Bank booth Cosmetics

	c.getPA().checkObjectSpawn(7273, 2991, 5089, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(26972, 3054, 4983, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(26972, 3055, 4983, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(26972, 3056, 4983, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(26972, 3030, 5031, 3, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(26972, 3030, 5032, 3, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(26972, 3030, 5033, 3, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(26972, 3030, 5034, 3, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 3023, 5046, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 2993, 4997, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 2976, 5009, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 2977, 5009, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 2959, 5003, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 2959, 5003, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 2965, 5019, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 2963, 5104, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 2963, 5109, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 3023, 5108, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 3023, 5110, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 3019, 5099, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 3021, 5099, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 3057, 5090, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 3061, 5028, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 3048, 5019, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 3045, 5024, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(11190, 2959, 5002, 2, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3056, 4991, 2, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3054, 5029, 2, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3024, 4997, 2, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3048, 5037, 2, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3024, 5001, 2, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3038, 5069, 0, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3038, 5071, 0, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3038, 5074, 0, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3037, 5079, 0, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 3030, 5079, 0, 0);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(-1, 2387, 4721, 0, 10);//Bank booth NewMinigameScavenger
	c.getPA().checkObjectSpawn(26972, 3057, 4983, 2, 10);//Bank booth NewMinigameScavenger
	
	c.getPA().checkObjectSpawn(-1, 3312, 3235, 0, 0);//DuelArenaGate
	c.getPA().checkObjectSpawn(-1, 3312, 3234, 0, 0);//DuelArenaGate
	c.getPA().checkObjectSpawn(-1, 3183, 3434, 0, 0);//VarrockBankStairsDoor
	
	c.getPA().checkObjectSpawn(-1, 2421, 4677, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2425, 4675, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2406, 4683, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2411, 4685, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2411, 4686, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2410, 4686, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2412, 4686, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2405, 4676, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2406, 4676, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2407, 4676, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2406, 4677, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 3345, 2874, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2387, 3488, 0, 10);//Remove-Column
	

	c.getPA().checkObjectSpawn(-1, 2792, 9334, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2793, 9334, 0, 10);//Remove-Column
	
	c.getPA().checkObjectSpawn(-1, 2402, 3481, 0, 10);//Remove-Column

	c.getPA().checkObjectSpawn(-1, 2584, 4837, 0, 10);//Remove-Column

	c.getPA().checkObjectSpawn(-1, 2581, 4839, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2586, 4834, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2589, 4839, 0, 10);//Remove-Column

	c.getPA().checkObjectSpawn(-1, 2390, 3495, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2391, 3495, 0, 10);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2404, 3488, 0, 10);//Remove-Column
	
	c.getPA().checkObjectSpawn(-1, 2421, 4677, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2425, 4675, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2406, 4683, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2411, 4685, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2411, 4686, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2410, 4686, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2412, 4686, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2405, 4676, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2406, 4676, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2407, 4676, 0, 2);//Remove-Column
	c.getPA().checkObjectSpawn(-1, 2406, 4677, 0, 2);//Remove-Column
	
	c.getPA().checkObjectSpawn(2644, 2742, 3443, 0, 10);
	c.getPA().checkObjectSpawn(2472, 2135, 4913, 0, 10);
	//EndOfCustomsCave
//startnewdungbill
	c.getPA().checkObjectSpawn(-1, 2527, 3306, 0, 10);//RemoveDungeoneeringNew
	c.getPA().checkObjectSpawn(-1, 2535, 3306, 0, 10);//RemoveDungeoneeringNew
	c.getPA().checkObjectSpawn(-1, 2527, 3304, 0, 10);//RemoveDungeoneeringNew
	c.getPA().checkObjectSpawn(-1, 2535, 3304, 0, 10);//RemoveDungeoneeringNew
	c.getPA().checkObjectSpawn(26972, 2527, 3305, 3, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2527, 3306, 3, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2527, 3304, 3, 10);//Bank booth

	c.getPA().checkObjectSpawn(26972, 2527, 3304, 0, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2527, 3304, 0, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2527, 3304, 0, 10);//Bank booth

	c.getPA().checkObjectSpawn(26972, 2722, 9666, 0, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2721, 9666, 0, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2720, 9666, 0, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2719, 9666, 0, 10);//Bank booth

	
	c.getPA().checkObjectSpawn(26972, 2531, 3306, 3, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2531, 3305, 3, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2531, 3304, 3, 10);//Bank booth
	
	c.getPA().checkObjectSpawn(26972, 2530, 3303, 2, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2528, 3303, 2, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2528, 3307, 2, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2529, 3307, 2, 10);//Bank booth
	c.getPA().checkObjectSpawn(26972, 2530, 3307, 2, 10);//Bank booth
//dunend         
          c.getPA().checkObjectSpawn(11190, 2936, 3281, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2936, 3282, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2936, 3283, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2936, 3284, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2698, 9836, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2698, 9837, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2698, 9838, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2698, 9839, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2698, 9840, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2698, 9841, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2698, 9842, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2707, 9840, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2707, 9841, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2707, 9842, 0, 10);
          c.getPA().checkObjectSpawn(11190, 2707, 9843, 0, 10);
         
          /*StaffZone*/
 
 
         // c.getPA().checkObjectSpawn(-1, 1863, 5343, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1863, 5344, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1863, 5345, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1863, 5346, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1863, 5347, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1863, 5348, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1863, 5349, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1863, 5350, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1863, 5351, 0, 10);
 
        //  c.getPA().checkObjectSpawn(-1, 1864, 5343, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1864, 5344, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1864, 5345, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1864, 5346, 0, 10);
         // c.getPA().checkObjectSpawn(-1, 1864, 5347, 0, 10);
        //  c.getPA().checkObjectSpawn(-1, 1864, 5348, 0, 10);
        //  c.getPA().checkObjectSpawn(-1, 1864, 5349, 0, 10);
        //  c.getPA().checkObjectSpawn(-1, 1864, 5350, 0, 10);
        //  c.getPA().checkObjectSpawn(-1, 1864, 5351, 0, 10);
          c.getPA().checkObjectSpawn(-1, 1862, 5350, 0, 10);
		  
		  
		  c.getPA().checkObjectSpawn(-1, 2577, 9446, 0, 10); // torvazone
		  
		  
		   c.getPA().checkObjectSpawn(-1, 2578, 9436, 0, 10); // torvazone
		   c.getPA().checkObjectSpawn(-1, 2578, 9435, 0, 10); // torvazone
		   c.getPA().checkObjectSpawn(-1, 2577, 9435, 0, 10); // torvazone
		   c.getPA().checkObjectSpawn(-1, 2576, 9433, 0, 10); // torvazone
		   c.getPA().checkObjectSpawn(-1, 2579, 9434, 0, 10); // torvazone
		   c.getPA().checkObjectSpawn(-1, 2577, 9432, 0, 10); // torvazone
		   c.getPA().checkObjectSpawn(-1, 2577, 9433, 0, 10); // torvazone
		   c.getPA().checkObjectSpawn(-1, 2580, 9463, 0, 10); // torvazone
		   c.getPA().checkObjectSpawn(-1, 2035, 4527, 0, 10); // mzone
		   c.getPA().checkObjectSpawn(-1, 2035, 4529, 0, 10); // mzone
		   c.getPA().checkObjectSpawn(-1, 2037, 4527, 0, 10); // mzone
			  c.getPA().checkObjectSpawn(-1, 2037, 4529, 0, 10); // mzone
			  c.getPA().checkObjectSpawn(-1, 2756, 3508, 0, 10); // new home
			c.getPA().checkObjectSpawn(-1, 2759, 3513, 0, 10); // new home
			 c.getPA().checkObjectSpawn(-1, 2757, 3513, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2761, 3511, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2761, 3509, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2759, 3507, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2757, 3507, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2755, 3509, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2755, 3511, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2752, 3517, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2756, 3517, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2760, 3517, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2761, 3517, 0, 10); // new home
			  
			//  c.getPA().checkObjectSpawn(-1, 2762, 3517, 0, 10); // new home
			  
			  
			  c.getPA().checkObjectSpawn(-1, 2764, 3517, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2765, 3516, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2766, 3515, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2767, 3514, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2767, 3512, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2767, 3511, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2767, 3509, 0, 10); // new home
			  c.getPA().checkObjectSpawn(-1, 2766, 3509, 0, 10); // new home
			    c.getPA().checkObjectSpawn(-1, 2765, 3509, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2765, 3510, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2767, 3508, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2767, 3506, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2767, 3505, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2750, 3510, 0, 10); // new home
				c.getPA().checkObjectSpawn(411, 3205, 3435, 0, 10); // new home

				c.getPA().checkObjectSpawn(26972, 2752, 3517, 0, 10); // new hom
				c.getPA().checkObjectSpawn(26972, 2753, 3517, 0, 10); // new hom
				c.getPA().checkObjectSpawn(26972, 2755, 3517, 0, 10); // new hom
				c.getPA().checkObjectSpawn(26972, 2756, 3517, 0, 10); // new hom
				c.getPA().checkObjectSpawn(26972, 2757, 3517, 0, 10); // new hom
				c.getPA().checkObjectSpawn(26972, 2759, 3517, 0, 10); // new hom
				c.getPA().checkObjectSpawn(26972, 2760, 3517, 0, 10); // new hom
				c.getPA().checkObjectSpawn(26972, 2761, 3517, 0, 10); // new hom
				//c.getPA().checkObjectSpawn(26972, 2762, 3517, 0, 10); // new hom
				c.getPA().checkObjectSpawn(26972, 2763, 3517, 0, 10); // new hom
				c.getPA().checkObjectSpawn(26972, 2764, 3517, 0, 10); // new hom
				
				c.getPA().checkObjectSpawn(-1, 2753, 3500, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2753, 3499, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2753, 3498, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2763, 3500, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2762, 3499, 0, 10); // new home
				c.getPA().checkObjectSpawn(-1, 2763, 3498, 0, 10); // new home
				
				
	
			
				
	  c.getPA().checkObjectSpawn(11190, 2403, 4675, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4676, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4677, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4678, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4679, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4680, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4681, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4682, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4683, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4684, 0, 10);
	  c.getPA().checkObjectSpawn(11190, 2403, 4685, 0, 10);
	  c.getPA().checkObjectSpawn(12768, 3547, 3563, 0, 10);
	  
	  c.getPA().checkObjectSpawn(-1, 3546, 3563, 0, 10);
	  c.getPA().checkObjectSpawn(-1, 3549, 3563, 0, 10);
	  c.getPA().checkObjectSpawn(-1, 3606, 3564, 0, 10);
	  
	  /* c.getPA().checkObjectSpawn(-1, 2753, 3500, 0, 10);
	    c.getPA().checkObjectSpawn(-1, 2753, 3499, 0, 10);
		 c.getPA().checkObjectSpawn(-1, 2753, 3498, 0, 10);
		  c.getPA().checkObjectSpawn(-1, 2763, 3500, 0, 10);
		   c.getPA().checkObjectSpawn(-1, 2763, 3499, 0, 10);
		    c.getPA().checkObjectSpawn(-1, 2763, 3498, 0, 10);*/
	
	
	
        c.getPA().checkObjectSpawn(-1, 2964, 3206, 0, 2);//Door
        c.getPA().checkObjectSpawn(-1, 2956, 3212, 0, 10);//Center well
        c.getPA().checkObjectSpawn(-1, 2967, 3205, 0, 10);//Colodron
        c.getPA().checkObjectSpawn(-1, 2968, 3207, 0, 10);//Chair
        c.getPA().checkObjectSpawn(-1, 2967, 3207, 0, 10);//Table
        c.getPA().checkObjectSpawn(-1, 2967, 3203, 0, 10);//Fireplace
        c.getPA().checkObjectSpawn(-1, 2970, 3206, 0, 10);//rocky chair
        c.getPA().checkObjectSpawn(-1, 2970, 3205, 0, 10);//Broom
        c.getPA().checkObjectSpawn(-1, 2970, 3204, 0, 10);//Table
        c.getPA().checkObjectSpawn(-1, 2969, 3203, 0, 10);//Bed
        c.getPA().checkObjectSpawn(-1, 2965, 3204, 0, 10);//Table
        c.getPA().checkObjectSpawn(-1, 2965, 3203, 0, 10);//wardrobe1
        c.getPA().checkObjectSpawn(-1, 2970, 3208, 0, 10);//Wardrobe2
        c.getPA().checkObjectSpawn(-1, 2965, 3208, 0, 10);//Wardrobe3
        c.getPA().checkObjectSpawn(-1, 2967, 3208, 0, 10);//Shelf1
        c.getPA().checkObjectSpawn(-1, 2968, 3208, 0, 10);//Shelf2
        c.getPA().checkObjectSpawn(-1, 2969, 3208, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3219, 3436, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3220, 3436, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3219, 3437, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3220, 3437, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3180, 3437, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3180, 3438, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3180, 3442, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3180, 3443, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3180, 3433, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3180, 3444, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3180, 3447, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3181, 3447, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3182, 3447, 0, 10);//Shelf3
        c.getPA().checkObjectSpawn(-1, 3183, 3447, 0, 10);//Shelf3

        c.getPA().checkObjectSpawn(26972, 2969, 3208, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2966, 3208, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(-1, 2965, 3215, 0, 10);//Stairs
        c.getPA().checkObjectSpawn(-1, 2611, 3096, 0, 10);//Stairs
        c.getPA().checkObjectSpawn(-1, 2612, 3096, 0, 10);//Stairs
        c.getPA().checkObjectSpawn(-1, 2612, 3097, 0, 10);//Stairs
        c.getPA().checkObjectSpawn(-1, 2611, 3088, 0, 10);//Stairs
        c.getPA().checkObjectSpawn(-1, 2612, 3097, 0, 10);//Stairs
        //Brand new Shopping center
        c.getPA().checkObjectSpawn(-1, 2948, 3203, 0, 10);//table
        c.getPA().checkObjectSpawn(-1, 2947, 3204, 0, 10);//table2
        c.getPA().checkObjectSpawn(-1, 2947, 3207, 0, 10);//small table
        c.getPA().checkObjectSpawn(-1, 2951, 3205, 0, 10);//Clothes
        c.getPA().checkObjectSpawn(-1, 2951, 3204, 0, 10);//Clothes
        c.getPA().checkObjectSpawn(-1, 2951, 3203, 0, 10);//Clothes
        c.getPA().checkObjectSpawn(-1, 2951, 3202, 0, 10);//Clothes
        c.getPA().checkObjectSpawn(-1, 2946, 3207, 0, 10);//crate
        c.getPA().checkObjectSpawn(-1, 2947, 3208, 0, 10);//crate
        c.getPA().checkObjectSpawn(-1, 2946, 3208, 0, 10);//crate
        c.getPA().checkObjectSpawn(-1, 2946, 3202, 0, 10);//crate
        c.getPA().checkObjectSpawn(-1, 2962, 3207, 0, 10);//Tree
        c.getPA().checkObjectSpawn(-1, 2958, 3207, 0, 10);//Bench
        c.getPA().checkObjectSpawn(-1, 2952, 3211, 0, 10);//Bench
        c.getPA().checkObjectSpawn(-1, 2946, 3202, 0, 10);//crate
		
		c.getPA().checkObjectSpawn(-1, 2754, 3504, 0, 10);//crate
		c.getPA().checkObjectSpawn(-1, 2761, 3504, 0, 10);//crate
		//Bills Home Zone
		c.getPA().checkObjectSpawn(-1, 2613, 3081, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2614, 3084, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2616, 3081, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2616, 3080, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2614, 3078, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2613, 3077, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2613, 3078, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2610, 3080, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2611, 3080, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2610, 3081, 0, 10);//removeshoproom
		c.getPA().checkObjectSpawn(-1, 2609, 3096, 0, 10);//removebankroom
		c.getPA().checkObjectSpawn(-1, 2609, 3089, 0, 10);//removebankroom
		c.getPA().checkObjectSpawn(-1, 2611, 3095, 0, 10);//removebankroom
		c.getPA().checkObjectSpawn(-1, 2602, 3092, 0, 10);//removebankroom
		c.getPA().checkObjectSpawn(-1, 2601, 3094, 0, 10);//removebankroom
		c.getPA().checkObjectSpawn(-1, 2603, 3082, 0, 22);//removethievdoor
		c.getPA().checkObjectSpawn(-1, 2603, 3082, 0, 9);//removethievdoor
		c.getPA().checkObjectSpawn(-1, 2603, 3082, 0, 10);//removethievdoor
		c.getPA().checkObjectSpawn(-1, 2605, 3081, 0, 10);//removethievdoor
		c.getPA().checkObjectSpawn(-1, 2606, 3081, 0, 10);//removethievdoor
		c.getPA().checkObjectSpawn(-1, 2607, 3081, 0, 10);//removethievdoor
		c.getPA().checkObjectSpawn(-1, 2607, 3077, 0, 10);//removethievdoor
        c.getPA().checkObjectSpawn(13405, 2597, 3087, 3, 10);//Portal
        c.getPA().checkObjectSpawn(13405, 2060, 3262, 0, 10);//Portal2
        c.getPA().checkObjectSpawn(26972, 2059, 3262, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2062, 3262, 2, 10);//Bank booth
		c.getPA().checkObjectSpawn(-1, 2951, 3222, 0, 10);//old Portal
		c.getPA().checkObjectSpawn(-1, 2966, 3212, 0, 10);//table
        c.getPA().checkObjectSpawn(-1, 2969, 3216, 0, 2);//Door
        c.getPA().checkObjectSpawn(-1, 2967, 3216, 0, 2);//Wall
        c.getPA().checkObjectSpawn(-1, 2967, 3215, 0, 2);//Wall2
        c.getPA().checkObjectSpawn(-1, 2968, 3216, 0, 2);//Wall
        c.getPA().checkObjectSpawn(-1, 2968, 3215, 0, 2);//Wall2
		//smithing
		c.getPA().checkObjectSpawn(3044, 3304, 3292, 3, 10);
        c.getPA().checkObjectSpawn(2783, 3301, 3292, 0, 10);
        c.getPA().checkObjectSpawn(2783, 3301, 3296, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2966, 3213, 0, 10);//chair
		c.getPA().checkObjectSpawn(-1, 2967, 3213, 0, 10);//chair
		c.getPA().checkObjectSpawn(-1, 2966, 3211, 0, 10);//chair
		c.getPA().checkObjectSpawn(-1, 2967, 3211, 0, 10);//chair
		c.getPA().checkObjectSpawn(-1, 2970, 3212, 0, 10);//piano
		c.getPA().checkObjectSpawn(-1, 2970, 3214, 0, 10);//table
        c.getPA().checkObjectSpawn(-1, 2964, 3209, 0, 10);//chest
		c.getPA().checkObjectSpawn(-1, 2963, 3209, 0, 10);//hat
		c.getPA().checkObjectSpawn(-1, 2963, 3215, 0, 10);//plant
		c.getPA().checkObjectSpawn(-1, 2966, 3209, 0, 10);//table
		c.getPA().checkObjectSpawn(-1, 2966, 3209, 0, 10);//sink
		c.getPA().checkObjectSpawn(-1, 2970, 3209, 0, 10);//Range
		c.getPA().checkObjectSpawn(-1, 2970, 3216, 0, 10);//plant
		c.getPA().checkObjectSpawn(-1, 2969, 3212, 0, 10);//Piano stool
		c.getPA().checkObjectSpawn(-1, 2963, 3216, 0, 10);//Corner table
		c.getPA().checkObjectSpawn(-1, 2968, 3208, 0, 10);//shelf1
		c.getPA().checkObjectSpawn(-1, 2969, 3208, 0, 10);//shelf2
        //booths
        c.getPA().checkObjectSpawn(26972, 2970, 3215, 1, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2970, 3214, 1, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2970, 3213, 1, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2970, 3211, 1, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2970, 3210, 1, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2969, 3209, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2968, 3209, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2967, 3209, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2966, 3209, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2963, 3215, 1, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2965, 3209, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2964, 3209, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2963, 3211, 1, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2963, 3210, 1, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2970, 3212, 1, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2964, 3216, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2965, 3216, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2966, 3216, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2967, 3216, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2420, 4689, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 2421, 4689, 2, 10);//Bank booth
		
		 c.getPA().checkObjectSpawn(26972, 3430, 2930, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 3429, 2930, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 3428, 2930, 2, 10);//Bank booth
		 c.getPA().checkObjectSpawn(26972, 3427, 2930, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 3426, 2930, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(26972, 3425, 2930, 2, 10);//Bank booth
		  c.getPA().checkObjectSpawn(26972, 3424, 2930, 2, 10);//Bank booth
		    c.getPA().checkObjectSpawn(26972, 3423, 2930, 2, 10);//Bank booth
			  c.getPA().checkObjectSpawn(26972, 3422, 2930, 2, 10);//Bank booth
			    c.getPA().checkObjectSpawn(26972, 3421, 2930, 2, 10);//Bank booth

        c.getPA().checkObjectSpawn(2720, 9666, 4689, 2, 10);//Bank booth
        c.getPA().checkObjectSpawn(2719, 9666, 4689, 2, 10);//Bank booth

        c.getPA().checkObjectSpawn(1341, 2758, 3492, 0, 10);
        c.getPA().checkObjectSpawn(1341, 2757, 3492, 0, 10);

	
        //corners
        c.getPA().checkObjectSpawn(26969, 2970, 3216, 0, 10);//Bank Deposit box
        c.getPA().checkObjectSpawn(26969, 2970, 3209, 0, 10);//Bank Deposit box
        c.getPA().checkObjectSpawn(26969, 2963, 3209, 0, 10);//Bank Deposit box
        c.getPA().checkObjectSpawn(26969, 2963, 3212, 1, 10);//Bank Deposit box 
        c.getPA().checkObjectSpawn(26969, 2968, 3216, 0, 10);//Bank Deposit box 
        c.getPA().checkObjectSpawn(26969, 2963, 3214, 1, 10);//Bank Deposit box 
        c.getPA().checkObjectSpawn(26969, 2963, 3216, 1, 10);//Bank Deposit box      
        c.getPA().checkObjectSpawn(369, 2400, 3494, 1, 10);//PK/Narnia Cupboard
        c.getPA().checkObjectSpawn(-1, 2402, 3495, 1, 10);//Remove flag   
        c.getPA().checkObjectSpawn(-1, 2401, 3495, 1, 10);//Remove flag   
        //END bank
        //home removes + lunar isle
        c.getPA().checkObjectSpawn(-1, 2104, 3911, 1, 10);
        c.getPA().checkObjectSpawn(-1, 3088, 3509, 0, 10);
        c.getPA().checkObjectSpawn(-1, 3092, 3488, 0, 10);
        c.getPA().object(6951, 2583, 9896, 2, 10);
		c.getPA().object(6951, 2578, 9899, 2, 10);
		c.getPA().object(6951, 2572, 9895, 2, 10);
		c.getPA().object(6951, 2577, 9894, 2, 10);
		c.getPA().object(6951, 2580, 9894, 2, 10);
		c.getPA().object(6951, 2577, 9893, 2, 10);
		c.getPA().object(6951, 2578, 9893, 2, 10);
		c.getPA().object(6951, 2579, 9893, 2, 10);
		c.getPA().object(6951, 2580, 9893, 2, 10);
		c.getPA().object(6951, 2585, 9895, 2, 10);
		c.getPA().checkObjectSpawn(27254, 3214, 3212, 2, 10); //score
		c.getPA().checkObjectSpawn(16152, 2861, 3546, 1, 10); //block
		//dungeoneering
		c.getPA().checkObjectSpawn(4412, 3251, 9335, 1, 10);
		c.getPA().checkObjectSpawn(4412, 3233, 9334, 1, 10);
		c.getPA().checkObjectSpawn(4412, 3220, 9328, 1, 10);
		c.getPA().checkObjectSpawn(1, 3212, 9334, 1, 10);
		c.getPA().checkObjectSpawn(7318, 3212, 9333, 1, 10);
		c.getPA().checkObjectSpawn(1, 3207, 9329, 1, 10);
		c.getPA().checkObjectSpawn(1, 3207, 9328, 1, 10);
		c.getPA().checkObjectSpawn(1, 3230, 9332, 1, 10);
		c.getPA().checkObjectSpawn(1, 3229, 9332, 1, 10);
		c.getPA().checkObjectSpawn(1, 3223, 9334, 1, 10);
		c.getPA().checkObjectSpawn(1, 3223, 9333, 1, 10);
		c.getPA().checkObjectSpawn(6553, 3218, 9328, 2, 10);
		c.getPA().checkObjectSpawn(6553, 3218, 9327, 2, 10);
		c.getPA().checkObjectSpawn(-1, 3261, 9329, -1, 10);
		c.getPA().checkObjectSpawn(-1, 3233, 9322, -1, 10);
		c.getPA().checkObjectSpawn(409, 3238, 9315, 3, 10); // Normal praying altar
		c.getPA().checkObjectSpawn(410, 3229, 9315, 0, 10); //lunar altar
		c.getPA().checkObjectSpawn(-1, 3232, 9314, 2, 10); // Removing that lion thing in lobby
		c.getPA().checkObjectSpawn(-1, 3233, 9314, 2, 10); // Removing that chair thing in lobby
		c.getPA().checkObjectSpawn(-1, 3234, 9314, 2, 10); // Removing that lion thing in lobby
		c.getPA().checkObjectSpawn(6553, 3261, 9330, 1, 10);
		c.getPA().checkObjectSpawn(6553, 3260, 9330, 1, 10);
		c.getPA().checkObjectSpawn(6553, 3215, 9311, 2, 10);
		c.getPA().checkObjectSpawn(6553, 3215, 9310, 2, 10);
		c.getPA().checkObjectSpawn(6553, 3245, 9333, 2, 10);
		c.getPA().checkObjectSpawn(6553, 3245, 9334, 2, 10);
		c.getPA().checkObjectSpawn(411, 3230, 9320, 2, 10);
		c.getPA().checkObjectSpawn(4641, 3239, 9293, 2, 10);
		c.getPA().checkObjectSpawn(4641, 3239, 9294, 2, 10);
		c.getPA().checkObjectSpawn(4641, 3233, 9289, 2, 10);
		c.getPA().checkObjectSpawn(4641, 3232, 9289, 2, 10);
		c.getPA().checkObjectSpawn(4641, 3218, 9302, 2, 10);
		c.getPA().checkObjectSpawn(4641, 3218, 9303, 2, 10);
        c.getPA().checkObjectSpawn(1277, 2049, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2050, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2051, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2052, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2053, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2054, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2055, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2056, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2057, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2058, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2059, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2060, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2061, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2062, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2063, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2064, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2065, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2066, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2067, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2068, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2069, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2070, 3217, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3217, 0, 10);
        c.getPA().checkObjectSpawn(2561, 2667, 3310, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2048, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2049, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2050, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2051, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2052, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2053, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2054, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2055, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2056, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2057, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2058, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2059, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2060, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2061, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2062, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2063, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2064, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2065, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2066, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2067, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2068, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2069, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2070, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3245, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3246, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3247, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3248, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3249, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3250, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3251, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3252, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3253, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3254, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3255, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3256, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3257, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3258, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3259, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3260, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3261, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3262, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3263, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3245, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3246, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3247, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3248, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3249, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3250, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3251, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3252, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3253, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3254, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3255, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3256, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3257, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3258, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3259, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3260, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3261, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3262, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3263, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2048, 3244, 0, 10);

		c.getPA().checkObjectSpawn(2996, 3180, 3436, 3, 10);//crystal chest varrock

		c.getPA().checkObjectSpawn(1277, 2049, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2050, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2051, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2052, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2053, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2054, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2055, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2056, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2057, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2058, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2059, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2060, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2061, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2062, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2063, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2064, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2065, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2066, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2067, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2068, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2069, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2070, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3244, 0, 10);
        c.getPA().checkObjectSpawn(2561, 2667, 3310, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2048, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2049, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2050, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2051, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2052, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2053, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2054, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2055, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2056, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2057, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2058, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2059, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2060, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2061, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2062, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2063, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2064, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2065, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2066, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2067, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2068, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2069, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2070, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3243, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3245, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3246, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3247, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3248, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3249, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3250, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3251, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3252, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3253, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3254, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3255, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3256, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3257, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3258, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3259, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3260, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3261, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3262, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2071, 3263, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3244, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3245, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3246, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3247, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3248, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3249, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3250, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3251, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3252, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3253, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3254, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3255, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3256, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3257, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3258, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3259, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3260, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3261, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3262, 0, 10);
		c.getPA().checkObjectSpawn(1277, 2072, 3263, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2069, 3247, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2065, 3247, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2061, 3247, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2057, 3247, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2053, 3247, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2049, 3247, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2067, 3248, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2063, 3248, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2059, 3248, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2055, 3248, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2051, 3248, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2069, 3249, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2065, 3249, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2061, 3249, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2057, 3249, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2053, 3249, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2049, 3249, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2067, 3250, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2063, 3250, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2059, 3250, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2055, 3250, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2051, 3250, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2069, 3251, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2065, 3251, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2061, 3251, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2057, 3251, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2053, 3251, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2049, 3251, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2067, 3252, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2063, 3252, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2059, 3252, 0, 10);		
		c.getPA().checkObjectSpawn(11214, 2055, 3252, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2051, 3252, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2069, 3253, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2065, 3253, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2061, 3253, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2057, 3253, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2053, 3253, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2049, 3253, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2067, 3254, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2063, 3254, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2059, 3254, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2055, 3254, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2051, 3254, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2069, 3255, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2065, 3255, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2061, 3255, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2057, 3255, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2053, 3255, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2049, 3255, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2067, 3256, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2063, 3256, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2059, 3256, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2055, 3256, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2051, 3256, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2069, 3257, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2065, 3257, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2061, 3257, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2057, 3257, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2053, 3257, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2049, 3257, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2067, 3258, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2063, 3258, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2059, 3258, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2055, 3258, 0, 10);
		c.getPA().checkObjectSpawn(11214, 2051, 3258, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3270, 3161, 0, 10); // Mining Gold ore
		c.getPA().checkObjectSpawn(-1, 3271, 3161, 0, 10); // Mining Gold ore
		c.getPA().checkObjectSpawn(-1, 3270, 3163, 0, 10); // Mining Gold ore
		c.getPA().checkObjectSpawn(-1, 3271, 3163, 0, 10); // Mining Gold ore
		c.getPA().checkObjectSpawn(3994, 3270, 3162, 0, 10); // Mining Gold ore
		c.getPA().checkObjectSpawn(2098, 3296, 3287, 0, 10); // Mining Gold ore
		c.getPA().checkObjectSpawn(2098, 3297, 3288, 0, 10); // Mining Gold ore
		c.getPA().checkObjectSpawn(2092, 3300, 3286, 0, 10); // Mining iron ore
		c.getPA().checkObjectSpawn(2092, 3300, 3287, 0, 10); // Mining iron ore
		c.getPA().checkObjectSpawn(2092, 3301, 3301, 0, 10); // Mining iron ore
		c.getPA().checkObjectSpawn(2092, 3302, 3302, 0, 10); // Mining iron ore
		c.getPA().checkObjectSpawn(2092, 3302, 3309, 0, 10); // Mining iron ore
		c.getPA().checkObjectSpawn(2092, 3303, 3310, 0, 10); // Mining iron ore
		c.getPA().checkObjectSpawn(2092, 3297, 3310, 0, 10); // Mining iron ore
		c.getPA().checkObjectSpawn(2092, 3296, 3312, 0, 10); // Mining iron ore
		c.getPA().checkObjectSpawn(2100, 3303, 3313, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(2100, 3293, 3300, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(2100, 3294, 3301, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(2100, 3295, 3303, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(2100, 3303, 3312, 0, 10); // Mining silver ore
		
		
		c.getPA().checkObjectSpawn(2100, 3303, 3312, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2404, 4685, 0, 10); // Mining silver ore
		
		
		c.getPA().checkObjectSpawn(11190, 2405, 4685, 0, 10); // Mining silver ore
		
		c.getPA().checkObjectSpawn(11190, 2404, 4682, 0, 10); // Mining silver ore
		
		c.getPA().checkObjectSpawn(11190, 2404, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2405, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2406, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2407, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2405, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2406, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2407, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2409, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2410, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2411, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2412, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2413, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2414, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2415, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2416, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2417, 4686, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2418, 4686, 0, 10); // Mining silver ore
		
		
		
		
		c.getPA().checkObjectSpawn(11190, 2408, 4674, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4676, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4677, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4678, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4679, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4680, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4681, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4682, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4683, 0, 10); // Mining silver ore
		
		
		
		c.getPA().checkObjectSpawn(11190, 2409, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2410, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2411, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2412, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2413, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2414, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2415, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2416, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2417, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2418, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2419, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2420, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2421, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2422, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2423, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2424, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2425, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2426, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2427, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2428, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2429, 4675, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2429, 4676, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2429, 4677, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2429, 4678, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2429, 4679, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2429, 4680, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2429, 4681, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2429, 4682, 0, 10); // Mining silver ore
		
		
		c.getPA().checkObjectSpawn(11190, 2405, 4682, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2406, 4682, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2407, 4682, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4682, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2409, 4682, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2404, 4683, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2405, 4683, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2406, 4683, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2407, 4683, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4683, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2409, 4683, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2404, 4684, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2405, 4684, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2406, 4684, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2407, 4684, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4684, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2409, 4684, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2406, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2407, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2408, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2409, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2412, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2410, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2411, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2413, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2414, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2415, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2416, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2417, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2418, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2419, 4685, 0, 10); // Mining silver ore
		c.getPA().checkObjectSpawn(11190, 2419, 4686, 0, 10); // Mining silver ore
		
		
		c.getPA().checkObjectSpawn(6551, 3297, 3311, 0, 10); // Mining teleport to eccense
		c.getPA().checkObjectSpawn(-1, 3090, 3496, 0, 10); // Edgeville bank removeing objects in bank Chair
		c.getPA().checkObjectSpawn(-1, 3092, 3496, 0, 10); // Edgeville bank removeing objects in bank Chair
		c.getPA().checkObjectSpawn(-1, 3091, 3495, 0, 10); // Edgeville bank removeing objects in bank Table
		c.getPA().checkObjectSpawn(-1, 2809, 3463, 0, 10); // Farming patch remove THE SINGLE ONE
		c.getPA().checkObjectSpawn(-1, 2806, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2806, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2805, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2819, 3463, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2805, 3466, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2806, 3466, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2805, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2804, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2804, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2803, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2803, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2809, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2809, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2808, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2808, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2807, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2807, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2814, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2814, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2813, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2813, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2812, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2812, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2810, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2810, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2811, 3467, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2811, 3468, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2808, 3460, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2808, 3459, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2807, 3459, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2807, 3460, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2806, 3460, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2806, 3461, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2806, 3459, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2805, 3459, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2805, 3461, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2805, 3460, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2804, 3459, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2804, 3460, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2812, 3459, 0, 10); // Farming patch remove
		c.getPA().checkObjectSpawn(-1, 2812, 3460, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2811, 3460, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2811, 3459, 0, 10);
		c.getPA().checkObjectSpawn(-1, 2809, 3460, 0, 10); // Farming patch remove
		c.getPA().checkObjectSpawn(-1, 2813, 3460, 0, 10); // Farming patch remove
		c.getPA().checkObjectSpawn(-1, 2813, 3459, 0, 10); // Farming patch remove
		c.getPA().checkObjectSpawn(-1, 2810, 3460, 0, 10); // Farming patch remove
		c.getPA().checkObjectSpawn(-1, 2810, 3459, 0, 10); // Farming patch remove
		c.getPA().checkObjectSpawn(-1, 2814, 3460, 0, 10); // Farming patch remove
		c.getPA().checkObjectSpawn(-1, 2814, 3459, 0, 10); // Farming patch remove
		c.getPA().checkObjectSpawn(-1, 2809, 3459, 0, 10); // Farming patch remove
		c.getPA().checkObjectSpawn(-1, 2819, 3462, 0, 10); // Farming table remove
		c.getPA().checkObjectSpawn(2152, 3451, 3514, 0, 10); //SUMMON OBELISK
		c.getPA().checkObjectSpawn(14367, 3333, 3333, 2, 10); // VARROCK HOMEBANK 1
		c.getPA().checkObjectSpawn(14367, 3333, 3333, 2, 10); // VARROCK HOMEBANK 2
		c.getPA().checkObjectSpawn(411, 2760, 3493, 2, 10); // Curse Prayers
		//c.getPA().checkObjectSpawn(1596, 2755, 3494, -1, 0);
		c.getPA().checkObjectSpawn(-1, 3217, 3436, -1, 10); //remvoe portal
		c.getPA().checkObjectSpawn(-1, 3220, 3437, -1, 10); //remvoe  stall varrock
		c.getPA().checkObjectSpawn(409, 2762, 3493, 0, 10); // Normal praying altar


		c.getPA().checkObjectSpawn(-1, 3508, 9494, -1, 10); //remvoe portal
		c.getPA().checkObjectSpawn(-1, 3508, 9494, -2, 10); //remvoe portal
		c.getPA().checkObjectSpawn(-1, 2841, 4828, -1, 10); //remvoe portal
		//c.getPA().checkObjectSpawn(1, 2842, 4829, 1, 10); //CRATES AIR ALTAR FIX
		//c.getPA().checkObjectSpawn(1, 2842, 4828, 1, 10); //CRATES AIR ALTAR FIX
		//c.getPA().checkObjectSpawn(1, 2842, 4827, 1, 10); //CRATES AIR ALTAR FIX
		//c.getPA().checkObjectSpawn(1, 2841, 4827, 1, 10); //CRATES AIR ALTAR FIX
		//c.getPA().checkObjectSpawn(1, 2840, 4827, 1, 10); //CRATES AIR ALTAR FIX
		//c.getPA().checkObjectSpawn(1, 2840, 4828, 1, 10); //CRATES AIR ALTAR FIX
		//c.getPA().checkObjectSpawn(1, 2840, 4829, 1, 10); //CRATES AIR ALTAR FIX
		//c.getPA().checkObjectSpawn(-1, 2841, 4828, -1, 10); //CRATES AIR ALTAR FIX
		c.getPA().checkObjectSpawn(-1, 2574, 4850, -1, 10); //REMOVE fire altar teleport rc
		c.getPA().checkObjectSpawn(-1, 3090, 3503, -1, 10); //REMOVE DEPOSIT BOX
		c.getPA().checkObjectSpawn(-1, 3095, 3499, -1, 10); //REMOVE CHAIR IN BANK
		c.getPA().checkObjectSpawn(-1, 3095, 3498, -1, 10); //REMOVE TABLE IN BANK
		c.getPA().checkObjectSpawn(-1, 3078, 3510, -1, 10); //REMOVE TABLE IN SHOP
		c.getPA().checkObjectSpawn(-1, 3080, 3510, -1, 10); //REMOVE BOXES in shop
		c.getPA().checkObjectSpawn(-1, 3077, 3512, -1, 10); //REMOVE STEPLADDERS
		c.getPA().checkObjectSpawn(-1, 3081, 3510, -1, 10); //REMOVE TABLE IN SHOP
		c.getPA().checkObjectSpawn(-1, 3098, 3496, -1, 10); //REMOVE TREE AT HOME
		c.getPA().checkObjectSpawn(-1, 3096, 3501, -1, 10); //REMOVE TREE AT HOME
		//START OF NOMAD MINIGAME
		c.getPA().checkObjectSpawn(400, 3487, 9948, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(400, 3488, 9948, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(400, 3493, 9938, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(400, 3493, 9937, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(400, 3482, 9937, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(400, 3481, 9937, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(400, 3480, 9937, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(400, 3477, 9945, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(400, 3477, 9944, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(400, 3477, 9943, 1, 10); //Nomad gravestones
		c.getPA().checkObjectSpawn(1, 2933, 9654, 1, 10); //Nomad CRATE
		c.getPA().checkObjectSpawn(1, 2933, 9655, 1, 10); //Nomad CRATE
		c.getPA().checkObjectSpawn(1, 2934, 9648, 1, 10); //Nomad CRATE
		c.getPA().checkObjectSpawn(1, 2500, 3018, 1, 10); //Nomad CRATE
		c.getPA().checkObjectSpawn(1, 2501, 3018, 1, 10); //Nomad CRATE
		c.getPA().checkObjectSpawn(1, 2505, 3012, 1, 10); //Nomad CRATE
		c.getPA().checkObjectSpawn(1, 2505, 3011, 1, 10); //Nomad CRATE
		//c.getPA().checkObjectSpawn(11, 2936, 9647, 1, 10); //Nomad LADDer
		//c.getPA().checkObjectSpawn(11, 2936, 9655, 1, 10); //Nomad LADDer
		//c.getPA().checkObjectSpawn(11, 2937, 9655, 1, 10); //Nomad LADDer
		//c.getPA().checkObjectSpawn(11, 2938, 9655, 1, 10); //Nomad LADDer
		//c.getPA().checkObjectSpawn(11, 2935, 9655, 1, 10); //Nomad LADDer
		//c.getPA().checkObjectSpawn(11, 2934, 9655, 1, 10); //Nomad LADDer*/
		c.getPA().checkObjectSpawn(11, 2498, 3014, 1, 10); //Nomad SPIRIT TREE
		//END OF NOMAD MINIGAME
		//remove edge well
		c.getPA().checkObjectSpawn(-1, 3084, 3502, 1, 10);
		c.getPA().checkObjectSpawn(-1, 3085, 3502, 1, 10);
		//removed
		
		//START OF PK MINIGAME BY Pandemic
		c.getPA().checkObjectSpawn(1, 3233, 3630, 0, 10); // Crate
		c.getPA().checkObjectSpawn(1, 3234, 3630, 0, 10); // Crate
		c.getPA().checkObjectSpawn(1, 3235, 3630, 0, 10); // Crate
		c.getPA().checkObjectSpawn(1, 3236, 3630, 0, 10); // Crate
		c.getPA().checkObjectSpawn(1, 3237, 3630, 0, 10); // Crate
		//END
		
		
		//START OF GOBLIN MINIGAME ANGRY
		c.getPA().checkObjectSpawn(672, 2539, 3032, 1, 10); //GOBLIN BARREL
		c.getPA().checkObjectSpawn(718, 2539, 3033, 1, 10); //GOBLIN BARREL
		c.getPA().checkObjectSpawn(2024, 2540, 3036, 1, 10); //GOBLIN ESCAPE
		c.getPA().checkObjectSpawn(2024, 2541, 3036, 1, 10); //GOBLIN ESCAPE
		c.getPA().checkObjectSpawn(2024, 2540, 3029, 1, 10); //GOBLIN ESCAPE
		c.getPA().checkObjectSpawn(2024, 2541, 3029, 1, 10); //GOBLIN ESCAPE
		c.getPA().checkObjectSpawn(2024, 2541, 3029, 1, 10); //GOBLIN ESCAPE
		c.getPA().checkObjectSpawn(2024, 2542, 3029, 1, 10); //GOBLIN ESCAPE
		c.getPA().checkObjectSpawn(2024, 2543, 3029, 1, 10); //GOBLIN ESCAPE
		c.getPA().checkObjectSpawn(2024, 2544, 3029, 1, 10); //GOBLIN ESCAPE
		c.getPA().checkObjectSpawn(2024, 2545, 3029, 1, 10); //GOBLIN ESCAPE
		//END OF GOBLIN MINIGAME
		c.getPA().checkObjectSpawn(4151, 2605, 3153, 1, 10); //portal home FunPk
		c.getPA().checkObjectSpawn(2619, 2602, 3156, 1, 10); //barrel FunPk
		c.getPA().checkObjectSpawn(1032, 2605, 3156, 2, 10); //warning sign FunPk
		c.getPA().checkObjectSpawn(1032, 2603, 3156, 2, 10); //warning sign FunPk
		c.getPA().checkObjectSpawn(1032, 2602, 3155, 1, 10); //warning sign FunPk
		c.getPA().checkObjectSpawn(1032, 2602, 3153, 1, 10); //warning sign FunPk
		c.getPA().checkObjectSpawn(1032, 2536, 4778, 0, 10); //warning sign donor
		c.getPA().checkObjectSpawn(1032, 2537, 4777, 1, 10); //warning sign donor
		c.getPA().checkObjectSpawn(1032, 2536, 4776, 2, 10); //warning sign donor
		c.getPA().checkObjectSpawn(7315, 2536, 4777, 0, 10); //funpk portals
		c.getPA().checkObjectSpawn(7316, 2605, 3153, 0, 10); //funpk portals
		c.getPA().checkObjectSpawn(4008, 2851, 2965, 1, 10); //spec alter
	/*	c.getPA().checkObjectSpawn(194, 2423, 3525, 0, 10); //Dungeoneering Rock
		c.getPA().checkObjectSpawn(23, 2385, 4719, 0, 10); //Dungeoneering sack
		c.getPA().checkObjectSpawn(23, 2384, 4719, 0, 10); //Dungeoneering sack
		c.getPA().checkObjectSpawn(16081, 1879, 4620, 0, 10); //Dungeoneering lvl 1 tele
		c.getPA().checkObjectSpawn(2014, 1921, 4640, 0, 10); //Dungeoneering Money
		c.getPA().checkObjectSpawn(16078, 1869, 4622, 0, 10); //Dungeoneering Rope
		c.getPA().checkObjectSpawn(2930, 2383, 4714, 3, 10); //Dungeoneering Boss 1 door
		c.getPA().checkObjectSpawn(1032, 2382, 4714, 1, 10); //warning sign FunPk
	//	c.getPA().checkObjectSpawn(11356, 3087, 3483, 0, 10); //Frost drags portal
		c.getPA().checkObjectSpawn(79, 3044, 5105, 1, 10); //dungie blocker
		c.getPA().checkObjectSpawn(9767, 2000, 4636, 0, 10); //slave blocker floor 1
		c.getPA().checkObjectSpawn(9767, 2001, 4636, 0, 10); //slave blocker floor 1
		c.getPA().checkObjectSpawn(9767, 1999, 4637, 0, 10); //slave blocker floor 1
		c.getPA().checkObjectSpawn(9767, 2002, 4636, 0, 10); //slave blocker floor 1
		c.getPA().checkObjectSpawn(10778, 2867, 9530, 1, 10); //dung floor 4 portal
		c.getPA().checkObjectSpawn(7272, 3233, 9316, 1, 10); //dung floor 5 portal
		c.getPA().checkObjectSpawn(4408, 2869, 9949, 1, 10); //dung floor 6 portalEND
		c.getPA().checkObjectSpawn(410, 1860, 4625, 1, 10); //dung floor 6 portalEND
		c.getPA().checkObjectSpawn(9947, 1914, 4639, 0, 10); // pillar dung floor 1
		c.getPA().checkObjectSpawn(6552, 1859, 4617, 1, 10); //dung floor 6 portalEND
		c.getPA().checkObjectSpawn(7318, 2772, 4454, 1, 10); //dung floor 7 portalEND
		c.getPA().checkObjectSpawn(4412, 1919, 4640, 0, 10); //escape ladder
		c.getPA().checkObjectSpawn(4412, 3048, 5233, 0, 10); //escape ladder*/
		c.getPA().checkObjectSpawn(195, 2980, 5111, 0, 10); //escape ladder
		c.getPA().checkObjectSpawn(4412, 2867, 9527, 0, 10); //escape ladder
		c.getPA().checkObjectSpawn(4412, 3234, 9327, 0, 10); //escape ladder
		c.getPA().checkObjectSpawn(4412, 2387, 4721, 0, 10); //escape ladder
		//start of dungkill
		c.getPA().checkObjectSpawn(3379, 2410, 3531, 0, 10); //dung kill cave
		//end of dung kill portal
		c.getPA().checkObjectSpawn(4412, 3229, 9312, 0, 10); //escape ladder
		c.getPA().checkObjectSpawn(4412, 2805, 4440, 0, 10); //escape ladder
		c.getPA().checkObjectSpawn(4412, 2744, 4453, 0, 10); //escape ladder
		c.getPA().checkObjectSpawn(4412, 2427, 9411, 0, 10); //escape ladder
		c.getPA().checkObjectSpawn(2465, 2426, 9415, 0, 10); //escape ladder
        c.getPA().checkObjectSpawn(2094, 3032, 9836, 0, 10);
        c.getPA().checkObjectSpawn(2094, 3033, 9836, 0, 10);
        c.getPA().checkObjectSpawn(2091, 3034, 9836, 0, 10);
        c.getPA().checkObjectSpawn(2091, 3035, 9836, 0, 10);
        c.getPA().checkObjectSpawn(2092, 3036, 9836, 0, 10);
        c.getPA().checkObjectSpawn(2092, 3037, 9836, 0, 10);
        c.getPA().checkObjectSpawn(2103, 3038, 9836, 0, 10);
        c.getPA().checkObjectSpawn(2103, 3039, 9836, 0, 10);
        c.getPA().checkObjectSpawn(2097, 3040, 9836, 0, 10);
        c.getPA().checkObjectSpawn(2097, 3041, 9836, 0, 10);
        c.getPA().checkObjectSpawn(14859, 3042, 9836, 0, 10);
		c.getPA().checkObjectSpawn(14859, 3043, 9836, 0, 10);
        c.getPA().checkObjectSpawn(3044, 3036, 9831, -1, 10);
		c.getPA().checkObjectSpawn(2213, 3037, 9835, -1, 10);
        c.getPA().checkObjectSpawn(2783, 3034, 9832, 0, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3495, 1, 10);
		c.getPA().checkObjectSpawn(-1, 3077, 3496, 1, 10);
		c.getPA().checkObjectSpawn(-1, 3079, 3501, 1, 10);
		c.getPA().checkObjectSpawn(-1, 3080, 3501, 1, 10);
		//c.getPA().checkObjectSpawn(2286, 2598, 4778, 1, 10);
		c.getPA().checkObjectSpawn(12356, 2845, 2957, 1, 10);
		c.getPA().checkObjectSpawn(2996, 2854, 2962, 1, 10);//al key chest
		c.getPA().checkObjectSpawn(14859, 2839, 3439, 0, 10);//runite ore skilling.
	    c.getPA().checkObjectSpawn(14859, 2520, 4773, 0, 10);//runite ore donor.
		c.getPA().checkObjectSpawn(14859, 2518, 4775, 0, 10);//runite ore donor.
		c.getPA().checkObjectSpawn(14859, 3298, 3308, 0, 10);//runite ore  mining spot
		c.getPA().checkObjectSpawn(14859, 2518, 4774, 0, 10);//runite ore donor.
		c.getPA().checkObjectSpawn(13617, 2384, 9899, 3, 10); //Barrelportal donor	
		//c.getPA().checkObjectSpawn(411, 3374, 9806, 2, 10); // Curse Prayers
		c.getPA().checkObjectSpawn(13615, 2525, 4770, 2, 10); // hill giants donor
		c.getPA().checkObjectSpawn(13620, 2384, 9895, 3, 10); // steel drags donor
		c.getPA().checkObjectSpawn(13619, 2384, 9891, 3, 10); // tormented demons donor
		c.getPA().checkObjectSpawn(410, 2864, 2955, 1, 10); 
		// CAMELOT WOODCUTTING
		c.getPA().checkObjectSpawn(1281, 2712, 3465, 0, 10);//OAK TREE CAMELOT WC
		c.getPA().checkObjectSpawn(1277, 2709, 3466, 0, 10);//NORMAL TREE CAMELOT WC
		c.getPA().checkObjectSpawn(1306, 2710, 3459, 0, 10);//MAGIC TREE CAMELOT WC
		//end of camelot woodcutting
		c.getPA().checkObjectSpawn(4874, 2849, 2995, 1, 10);
		c.getPA().checkObjectSpawn(4875, 2849, 2996, 1, 10);
		c.getPA().checkObjectSpawn(4876, 2849, 2997, 0, 10);
		c.getPA().checkObjectSpawn(4877, 2849, 2998, 0, 10);
		c.getPA().checkObjectSpawn(4878, 2849, 2999, 0, 10);	
		c.getPA().checkObjectSpawn(1596, 3008, 3850, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3008, 3849, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10307, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3040, 10308, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10311, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3022, 10312, 1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10341, -1, 0);
		c.getPA().checkObjectSpawn(1596, 3044, 10342, 1, 0);
		c.getPA().checkObjectSpawn(6552, 2842, 2954, 1, 10); //ancient prayers
		c.getPA().checkObjectSpawn(409, 2852, 2950, 2, 10);
		c.getPA().checkObjectSpawn(409, 2530, 4779, 3, 10);
		c.getPA().checkObjectSpawn(2213, 3047, 9779, 1, 10);
		c.getPA().checkObjectSpawn(2213, 3080, 9502, 1, 10);
		c.getPA().checkObjectSpawn(2213, 2516, 4780, 1, 10);
		c.getPA().checkObjectSpawn(2213, 2516, 4775, 1, 10);
		c.getPA().checkObjectSpawn(1530, 3093, 3487, 1, 10);
		c.getPA().checkObjectSpawn(2213, 2855, 3439, -1, 10);
		c.getPA().checkObjectSpawn(2090, 2839, 3440, -1, 10);
		c.getPA().checkObjectSpawn(2094, 2839, 3441, -1, 10);
		c.getPA().checkObjectSpawn(2092, 2839, 3442, -1, 10);
		c.getPA().checkObjectSpawn(2096, 2839, 3443, -1, 10);
		c.getPA().checkObjectSpawn(2102, 2839, 3444, -1, 10);
		c.getPA().checkObjectSpawn(2105, 2839, 3445, 0, 10);
		c.getPA().checkObjectSpawn(1278, 2843, 3442, 0, 10);
		c.getPA().checkObjectSpawn(1281, 2844, 3499, 0, 10);
		c.getPA().checkObjectSpawn(4156, 3083, 3440, 0, 10);
		c.getPA().checkObjectSpawn(1308, 2846, 3436, 0, 10);
		c.getPA().checkObjectSpawn(1309, 2846, 3439, -1, 10);
		c.getPA().checkObjectSpawn(1306, 2850, 3439, -1, 10);
		c.getPA().checkObjectSpawn(2783, 2841, 3436, 0, 10);
		c.getPA().checkObjectSpawn(2728, 2861, 3429, 0, 10);
		c.getPA().checkObjectSpawn(2728, 2429, 9416, 0, 10);//cooking range dung!
		c.getPA().checkObjectSpawn(3044, 2857, 3427, -1, 10);
		c.getPA().checkObjectSpawn(320, 3048, 10342, 0, 10);
		
		//NewDonorZone-MadeByBill
		
		//adds
		c.getPA().checkObjectSpawn(2996, 2396, 9899, 3, 10); //DonatorCChest
		c.getPA().checkObjectSpawn(409, 2393, 9909, 2, 10); //donatoraltar
		c.getPA().checkObjectSpawn(410, 2398, 9907, 2, 10); //Donatoraltar
		c.getPA().checkObjectSpawn(411, 2389, 9909, 2, 10); //Donatoraltar
		c.getPA().checkObjectSpawn(6552, 2387, 9907, 1, 10); //Donatoraltar
		c.getPA().checkObjectSpawn(2213, 2391, 9909, 2, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2392, 9909, 2, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3171, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3170, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3169, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3168, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3167, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3166, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3165, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3164, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3163, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3162, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3161, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3160, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3159, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(2213, 2097, 3158, 1, 10); //Donatorbank
		c.getPA().checkObjectSpawn(13660, 2036, 4528, 2, 10); //Pokeball
		c.getPA().checkObjectSpawn(10583, 3033, 9581, 1, 10); //Frost dragon rocks
		c.getPA().checkObjectSpawn(10583, 3033, 9582, 1, 10); //Frost dragon rocks
		c.getPA().checkObjectSpawn(10583, 3033, 9583, 1, 10); //Frost dragon rocks
		
		
		//removes
		c.getPA().checkObjectSpawn(-1, 2389, 9907, -1, 10);//woodenDefence
		c.getPA().checkObjectSpawn(-1, 2394, 9907, -1, 10);//woodenDefence
		c.getPA().checkObjectSpawn(-1, 2395, 9908, -1, 10);//woodenCrate
		c.getPA().checkObjectSpawn(-1, 2397, 9902, -1, 10);//woodenCrates
		c.getPA().checkObjectSpawn(-1, 2396, 9905, -1, 10);//glider
		c.getPA().checkObjectSpawn(-1, 2388, 9902, -1, 10);//gliderlever
		c.getPA().checkObjectSpawn(-1, 2384, 9902, -1, 10);//glider
		c.getPA().checkObjectSpawn(-1, 2383, 9904, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2397, 9891, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2400, 9890, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2383, 9905, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2388, 9898, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2384, 9894, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2388, 9894, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2388, 9890, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2384, 9890, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2388, 9886, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2384, 9886, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2387, 9881, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2394, 9883, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2396, 9884, -1, 10);//WoodenCrates
		c.getPA().checkObjectSpawn(-1, 2384, 9881, -1, 10);//WoodenCrates
		
		
		/*MegaZone*/

		
		
		c.getPA().checkObjectSpawn(6552, 3418, 2940, 2, 10);
		c.getPA().checkObjectSpawn(410, 3423, 2937, 2, 10); // big ass
		c.getPA().checkObjectSpawn(411, 3418, 2935, 2, 10);
		c.getPA().checkObjectSpawn(409, 3420, 2935, 3, 10);
		c.getPA().checkObjectSpawn(-1, 2128, 4915, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2128, 4922, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2129, 4921, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2129, 4920, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2129, 4925, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2129, 4926, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2129, 4927, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2130, 4920, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2130, 4925, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2130, 4927, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2131, 4925, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2131, 4926, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2131, 4927, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2132, 4919, -1, 0);
		c.getPA().checkObjectSpawn(-1, 2132, 4925, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2132, 4926, -1, 10);
		c.getPA().checkObjectSpawn(2996, 2135, 4907, 3, 10);
		c.getPA().checkObjectSpawn(-1, 2141, 4922, -1, 0);


		//::run lane 1
		c.getPA().checkObjectSpawn(3565, 3335, 2910, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2909, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2908, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2907, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2906, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2905, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2904, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2903, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2902, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2901, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2900, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2899, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2898, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2897, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2896, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2895, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2894, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2893, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2892, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2891, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2890, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2889, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2888, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2887, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2886, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2885, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2884, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2883, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2882, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2881, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2880, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2879, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2878, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2877, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2876, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2875, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2874, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2873, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2872, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2871, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2870, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2869, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2868, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2867, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2866, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2865, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3335, 2864, 1, 10); //Fence

		c.getPA().checkObjectSpawn(3565, 3338, 2910, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2909, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2908, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2907, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2906, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2905, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2904, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2903, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2902, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2901, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2900, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2899, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2898, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2897, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2896, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2895, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2894, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2893, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2892, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2891, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2890, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2889, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2888, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2887, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2886, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2885, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2884, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2883, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2882, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2881, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2880, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2879, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2878, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2877, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2876, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2875, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2874, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2873, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2872, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2871, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2870, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2869, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2868, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2867, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2866, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2865, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2864, 1, 10); //Fence

		//::run lane 2
		c.getPA().checkObjectSpawn(3565, 3343, 2910, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2909, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2908, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2907, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2906, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2905, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2904, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2903, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2902, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2901, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2900, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2899, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2898, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2897, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2896, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2895, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2894, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2893, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2892, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2891, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2890, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2889, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2888, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2887, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2886, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2885, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2884, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2883, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2882, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2881, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2880, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2879, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2878, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2877, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2876, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2875, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2874, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2873, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2872, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2871, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2870, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2869, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2868, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2867, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2866, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2865, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2864, 1, 10); //Fence

		c.getPA().checkObjectSpawn(3565, 3346, 2910, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2909, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2908, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2907, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2906, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2905, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2904, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2903, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2902, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2901, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2900, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2899, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2898, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2897, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2896, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2895, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2894, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2893, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2892, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2891, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2890, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2889, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2888, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2887, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2886, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2885, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2884, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2883, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2882, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2881, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2880, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2879, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2878, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2877, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2876, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2875, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2874, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2873, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2872, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2871, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2870, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2869, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2868, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2867, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2866, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2865, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3346, 2864, 1, 10); //Fence

		//::run lane 3
		c.getPA().checkObjectSpawn(3565, 3386, 2910, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2909, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2908, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2907, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2906, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2905, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2904, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2903, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2902, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2901, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2900, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2899, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2898, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2897, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2896, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2895, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2894, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2893, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2892, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2891, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2890, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2889, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2888, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2887, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2886, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2885, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2884, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2883, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2882, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2881, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2880, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2879, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2878, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2877, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2876, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2875, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2874, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2873, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2872, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2871, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2870, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2869, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2868, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2867, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2866, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2865, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2864, 1, 10); //Fence

		c.getPA().checkObjectSpawn(3565, 3389, 2910, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2909, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2908, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2907, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2906, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2905, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2904, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2903, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2902, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2901, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2900, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2899, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2898, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2897, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2896, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2895, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2894, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2893, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2892, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2891, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2890, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2889, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2888, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2887, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2886, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2885, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2884, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2883, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2882, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2881, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2880, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2879, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2878, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2877, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2876, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2875, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2874, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2873, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2872, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2871, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2870, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2869, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2868, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2867, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2866, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2865, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3389, 2864, 1, 10); //Fence

		c.getPA().checkObjectSpawn(3565, 3335, 2863, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3336, 2863, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3337, 2863, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3338, 2863, 1, 10); //Fence

		c.getPA().checkObjectSpawn(3565, 3346, 2863, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3345, 2863, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3344, 2863, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3343, 2863, 1, 10); //Fence

		c.getPA().checkObjectSpawn(3565, 3389, 2863, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3388, 2863, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3387, 2863, 1, 10); //Fence
		c.getPA().checkObjectSpawn(3565, 3386, 2863, 1, 10); //Fence

		//::run portals
		c.getPA().checkObjectSpawn(3565, 3344, 2911, 1, 10); //portal
		c.getPA().checkObjectSpawn(2473, 3345, 2911, 2, 10); //portal
		c.getPA().checkObjectSpawn(3565, 3346, 2911, 1, 10); //portal

		c.getPA().checkObjectSpawn(3565, 3338, 2911, 1, 10); //portal
		c.getPA().checkObjectSpawn(2475, 2393, 4382, 2, 10); //portal
		c.getPA().checkObjectSpawn(3565, 3336, 2911, 1, 10); //portal

		c.getPA().checkObjectSpawn(3565, 3387, 2911, 1, 10); //portal
		c.getPA().checkObjectSpawn(2476, 3388, 2911, 2, 10); //portal

		c.getPA().checkObjectSpawn(2470, 2418, 4444, 1, 10); //portal
		c.getPA().checkObjectSpawn(2470, 2418, 4445, 2, 10); //portal
		c.getPA().checkObjectSpawn(2470, 2435, 4444, 2, 10); //portal
		c.getPA().checkObjectSpawn(2470, 2435, 4445, 2, 10); //portal

		c.getPA().checkObjectSpawn(2468, 2425, 4446, 2, 10); //portal

		//Fire Torva
		c.getPA().checkObjectSpawn(4952, 2381, 4426, 2, 10); //Fire Torva Boss Teleport
		c.getPA().checkObjectSpawn(4953, 2603, 9636, 2, 10); //Fire Torva Boss Teleport if occupied


		c.getPA().checkObjectSpawn(4406, 2385, 4428, 1, 10); //PortalToMummyChampion

		c.getPA().checkObjectSpawn(4407, 2949, 2978, 2, 10); //PortalatMummy

		//Super portal at home
		c.getPA().checkObjectSpawn(2472, 2037, 4524, 1, 10); //Fence

		//undead fix
		c.getPA().checkObjectSpawn(1, 2983, 9629, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2982, 9629, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2981, 9629, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2980, 9629, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2980, 9630, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2980, 9631, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2980, 9632, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2980, 9633, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2981, 9633, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2982, 9634, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2982, 9635, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2981, 9636, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2980, 9637, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2980, 9638, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2980, 9639, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2980, 9640, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2981, 9641, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2982, 9642, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2983, 9643, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2984, 9643, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2985, 9643, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2986, 9644, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2987, 9645, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2990, 9645, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2991, 9644, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2992, 9643, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2993, 9643, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2994, 9642, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2994, 9641, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2994, 9640, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2994, 9639, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2993, 9638, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2992, 9637, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2992, 9636, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2992, 9635, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2993, 9634, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2994, 9633, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2994, 9632, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2994, 9631, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2994, 9630, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2994, 9629, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2993, 9628, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2992, 9628, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2990, 9629, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2991, 9628, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2989, 9630, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2988, 9630, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2987, 9629, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2986, 9629, 1, 10); //Crate
		c.getPA().checkObjectSpawn(1, 2985, 9629, 1, 10); //Crate

		//asgard
	        c.getPA().checkObjectSpawn(2996, 2385, 4460, 1, 10);//crystal chest
	        c.getPA().checkObjectSpawn(9951, 2399, 4446, 1, 10);//Portal to Trickster
			//altars
	c.getPA().checkObjectSpawn(410, 2402, 4474, 1, 10); //Guthix
	c.getPA().checkObjectSpawn(6552, 2404, 4470, 1, 10); //Ancient
	c.getPA().checkObjectSpawn(411, 2401, 4470, 1, 10); //Chaos
	c.getPA().checkObjectSpawn(409, 2387, 4461, 2, 10); //Altar
		
		
		//pengs
	        c.getPA().checkObjectSpawn(1, 2832, 2975, 1, 10);//crystal chest
	        c.getPA().checkObjectSpawn(1, 2831, 2975, 1, 10);//crystal chest
	        c.getPA().checkObjectSpawn(1, 2831, 2976, 1, 10);//crystal chest
	        c.getPA().checkObjectSpawn(1, 2832, 2976, 1, 10);//crystal chest
		
		
		//remove objects
		c.getPA().checkObjectSpawn(-1, 2844, 3440, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2846, 3437, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2840, 3439, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2841, 3443, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2851, 3438, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2235, 4450, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2878, 10199, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2880, 10199, -1, 10);

		c.getPA().checkObjectSpawn(-1, 2874, 10201, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2874, 10196, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2885, 10201, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2885, 10196, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2879, 10191, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2794, 9327, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2880, 10197, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2878, 10197, -1, 10);

		c.getPA().checkObjectSpawn(-1, 2443, 4426, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2444, 4425, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2446, 4424, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2447, 4425, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2445, 4426, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2410, 4442, -1, 10);
		c.getPA().checkObjectSpawn(-1, 2388, 4460, -1, 10);
		
	


	 if (c.heightLevel == 0) {
			//c.getPA().checkObjectSpawn(2492, 2911, 3614, 1, 10);
		 }else{
			c.getPA().checkObjectSpawn(-1, 2911, 3614, 1, 10);
	}
	}
	
	public final int IN_USE_ID = 14825;
	public boolean isObelisk(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return true;
		}
		return false;
	}
	public int[] obeliskIds = {14829,14830,14827,14828,14826,14831};
	public int[][] obeliskCoords = {{3154,3618},{3225,3665},{3033,3730},{3104,3792},{2978,3864},{3305,3914}};
	public boolean[] activated = {false,false,false,false,false,false};
	
	public void startObelisk(int obeliskId) {
		int index = getObeliskIndex(obeliskId);
		if (index >= 0) {
			if (!activated[index]) {
				activated[index] = true;
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1], 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0], obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
				addObject(new Object(14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1] + 4, 0, -1, 10, obeliskId,16));
			}
		}	
	}
	
	public int getObeliskIndex(int id) {
		for (int j = 0; j < obeliskIds.length; j++) {
			if (obeliskIds[j] == id)
				return j;
		}
		return -1;
	}
	
	public void teleportObelisk(int port) {
		int random = Misc.random(5);
		while (random == port) {
			random = Misc.random(5);
		}
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if (c.goodDistance(c.getX(), c.getY(), obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2, 1)) {
					c.getPA().startTeleport2(obeliskCoords[random][0] + xOffset, obeliskCoords[random][1] + yOffset, 0);
				}
			}		
		}
	}
	
	public boolean loadForPlayer(Object o, Client c) {
		if (o == null || c == null)
			return false;
		return c.distanceToPoint(o.objectX, o.objectY) <= 60 && c.heightLevel == o.height;
	}
	
	public void addObject(Object o) {
		if (getObject(o.objectX, o.objectY, o.height) == null) {
			objects.add(o);
			placeObject(o);
		}	
	}




}