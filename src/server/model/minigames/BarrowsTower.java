package server.model.minigames;
 
import server.model.players.Client;
import server.Server;
 
/**
 * @author Sanity
 */
 
public class BarrowsTower {
    private final int[][] WAVES =    {{4022},{4022,4023},{4022,4023,4024},{4022,4023,4024,4025},{4022,4023,4024,4025,4026},{2025},{2025,2027},{2025,2027,2028},{2025,2027,2028,2029},{2025,2027,2028,2029},{2025,2027,2028,2029,4022,4023,4024,4025,4026},{2030}};
    private int[][] coordinates = {{3555,9694},{3551,9690},{3547,9694},{3549,9691},{3554,9691},{3555,9694},{3551,9698},{3547,9694},{3549,9691},{3554,9691}};
    //2743 = 360, 2627 = 22, 2630 = 45, 2631 = 90, 2741 = 180
    public void spawnNextWave(Client c) {
        if (c != null) {
            if (c.waveId >= WAVES.length) {
                c.waveId = 0;
                return;             
            }
            if (c.waveId < 0){
            return;
            }
            int npcAmount = WAVES[c.waveId].length;
            for (int j = 0; j < npcAmount; j++) {
                int npc = WAVES[c.waveId][j];
                int X = coordinates[j][0];
                int Y = coordinates[j][1];
                int H = c.heightLevel;
                int hp = getHp(npc);
                int max = getMax(npc);
                int atk = getAtk(npc);
                int def = getDef(npc);
                Server.npcHandler.spawnNpc(c, npc, X, Y, H, 0, hp, max, atk, def, true, false);             
            }
            c.BarrowsToKill = npcAmount;
            c.BarrowsKilled = 0;
        }
    }
     
    public int getHp(int npc) {
        switch (npc) {
        case 4022:
        case 4023:
        case 4024:
        case 4025:
        case 4026:
        	return 7500;
        case 2025:
        case 2027:
        case 2028:
        case 2029:
        	return 15000;
        case 2030:
        	return 145000;
        }
        return 15000;
    }
     
    public int getMax(int npc) {
        switch (npc) {          
        case 4022:
        case 4023:
        case 4024:
        case 4025:
        case 4026:
        	return 10;
        case 2025:
        case 2027:
        case 2028:
        case 2029:
        	return 25;
        case 2030:
        	return 100;
        }
        return 150;
    }
     
    public int getAtk(int npc) {
        switch (npc) {  
        case 2025:
        case 2027:
        case 2028:
        case 2029:
        	return 150;
        case 2030:
        	return 300;
        }
        return 1250;
    }
     
    public int getDef(int npc) {
        switch (npc) {      
        }
        return 550;
    }
     
 
}