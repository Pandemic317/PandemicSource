package server.util;
 
import server.Server;
import server.model.players.Client;
import server.model.players.Player;
import server.model.players.PlayerHandler;
 
import com.rspserver.motivote.MotivoteHandler;
import com.rspserver.motivote.Reward;
 
public class Voting extends MotivoteHandler<Reward>
 
{
    private static int VOTES;
 
        @Override
        public void onCompletion(Reward reward)
        {
                if (reward.rewardName().equalsIgnoreCase("1bticket"))
                {
                        if (PlayerHandler.isPlayerOn(reward.username()))
                        {
                                Player p = PlayerHandler.getPlayer(reward.username());
                               
                                if (p != null && p.isActive == true) // check isActive to make sure player is active. some servers, like project insanity, need extra checks.
                                {
                                        synchronized(p)
                                        {
                                                Client c = (Client)p;
                                               
                                                if (c.getItems().addItem(5021, reward.amount()))
                                                {
                                                    reward.complete();
						c.votingPoints ++;
                                                    c.sendMessage("<shad=519160><col=800000000>[Voting]</col></shad>"+" You've received your vote reward! Congratulations!");
                                                        for (int j = 0; j < Server.playerHandler.players.length; j++) {
                                                            if (Server.playerHandler.players[j] != null) {
                                                                Client c2 = (Client) Server.playerHandler.players[j];
                                                                 c2.sendMessage("<shad=519160><col=800000000>[Voting]</col> "+ Misc.optimizeText(c.playerName) +" has just voted for 250x 1b tickets ::vote to claim yours!");
                                                                if(VOTES >= 10) {
                                                         			c2.sendMessage("@cr7@ <col=008FB2>Another 10 votes have been claimed! Vote now using the ::vote command!");
                                                                    VOTES = 0;
                                                                }
                                                                VOTES++;
                                                }
                                                        }
                                                }
                                                else
                                                {
                                                        c.sendMessage("[Voting] Could not give you your reward item, try creating space.");
                                                }
                                        }
                                }
                        }
 
                       
                       
                       
                }
               
        }
       
}