package server.model.minigames;
 
import server.model.players.Client;
import server.Server;
 
/**
 * @author Sanity
 */
 
public class TzhaarMinigame {
    private final int[][] WAVES =    {{2591,2598},{2604,2604,2598,2591},{2610},{7770,4020,4020,4020},{2741},{2745}};
    private int[][] coordinates = {{3161,9756},{3161,9760},{3163,9756},{3163,9760},{3161,9758}};
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
            c.dominionToKill = npcAmount;
            c.dominionKilled = 0;
        }
    }
     
    public int getHp(int npc) {
        switch (npc) {
        	case 2598:
        	return 10000;
            case 2591:
            return 50000;
            case 2604:
            return 75000;
            case 2610:
            return 12500;
            case 7770: 
            return 35000;
            case 2741: 
            return 45000;
            case 4020:
            return 25000;
            case 2745:
            return 85000; 
        }
        return 100;
    }
     
    public int getMax(int npc) {
        switch (npc) {      
        }
        return 5;
    }
     
    public int getAtk(int npc) {
        switch (npc) {  
        }
        return 100;
    }
     
    public int getDef(int npc) {
        switch (npc) {      
        }
        return 100;
    }
     
 
}