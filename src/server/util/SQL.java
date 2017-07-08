package server.util;

import server.model.players.Client;
import java.sql.*;
import java.security.MessageDigest;

@SuppressWarnings("all")
public class SQL {


	public static Connection con = null;
	public static Statement stmt;
	public static boolean connectionMade;
	public static void createConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost/highscores","root","removed");
			stmt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static ResultSet query(String s) throws SQLException {
		try {
			if (s.toLowerCase().startsWith("select")) {
				ResultSet rs = stmt.executeQuery(s);
				return rs;
			} else {
				stmt.executeUpdate(s);
			}
			return null;
		} catch (Exception e) {
			destroyConnection();
			createConnection();
			e.printStackTrace();
		}
		return null;
	}
	public static void destroyConnection() {
		try {
            if(stmt != null)
            stmt.close();
            if(con!= null)
            con.close();
			connectionMade = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public static boolean saveHighScore(Client c) {
		try {
	long totallvlexp = (long) (c.playerXP[0]) + (c.playerXP[1]) + (c.playerXP[2]) + (c.playerXP[3]) + (c.playerXP[4]) + (c.playerXP[5]) + (c.playerXP[6]) + (c.playerXP[7]) + (c.playerXP[8]) + (c.playerXP[9]) + (c.playerXP[10]) + (c.playerXP[11]) + (c.playerXP[12]) + (c.playerXP[13]) + (c.playerXP[14]) + (c.playerXP[15]) + (c.playerXP[16]) + (c.playerXP[17]) + (c.playerXP[18]) + (c.playerXP[19]) + (c.playerXP[20]); 
	long totallevell = (long) (c.getLevelForXP(c.playerXP[0]) + c.getLevelForXP(c.playerXP[1]) + c.getLevelForXP(c.playerXP[2]) + c.getLevelForXP(c.playerXP[3]) + c.getLevelForXP(c.playerXP[4]) + c.getLevelForXP(c.playerXP[5]) + c.getLevelForXP(c.playerXP[6]) + c.getLevelForXP(c.playerXP[7]) + c.getLevelForXP(c.playerXP[8]) + c.getLevelForXP(c.playerXP[9]) + c.getLevelForXP(c.playerXP[10]) + c.getLevelForXP(c.playerXP[11]) + c.getLevelForXP(c.playerXP[12]) + c.getLevelForXP(c.playerXP[13]) + c.getLevelForXP(c.playerXP[14]) + c.getLevelForXP(c.playerXP[15]) + c.getLevelForXP(c.playerXP[16]) + c.getLevelForXP(c.playerXP[17]) + c.getLevelForXP(c.playerXP[18]) + c.getLevelForXP(c.playerXP[19]) + c.getLevelForXP(c.playerXP[20]));

			query("DELETE FROM `skills` WHERE playerName = '"+c.playerName+"';");
			query("DELETE FROM `skillsoverall` WHERE playerName = '"+c.playerName+"';");
			query("INSERT INTO `skills` (`playerName`,`Attacklvl`,`Attackxp`,`Defencelvl`,`Defencexp`,`Strengthlvl`,`Strengthxp`,`Hitpointslvl`,`Hitpointsxp`,`Rangelvl`,`Rangexp`,`Prayerlvl`,`Prayerxp`,`Magiclvl`,`Magicxp`,`Cookinglvl`,`Cookingxp`,`Woodcuttinglvl`,`Woodcuttingxp`,`Fletchinglvl`,`Fletchingxp`,`Fishinglvl`,`Fishingxp`,`Firemakinglvl`,`Firemakingxp`,`Craftinglvl`,`Craftingxp`,`Smithinglvl`,`Smithingxp`,`Mininglvl`,`Miningxp`,`Herblorelvl`,`Herblorexp`,`Agilitylvl`,`Agilityxp`,`Thievinglvl`,`Thievingxp`,`Slayerlvl`,`Slayerxp`,`Farminglvl`,`Farmingxp`,`Runecraftlvl`,`Runecraftxp`) VALUES ('"+c.playerName+"',"+c.playerLevel[0]+","+c.playerXP[0]+","+c.playerLevel[1]+","+c.playerXP[1]+","+c.playerLevel[2]+","+c.playerXP[2]+","+c.playerLevel[3]+","+c.playerXP[3]+","+c.playerLevel[4]+","+c.playerXP[4]+","+c.playerLevel[5]+","+c.playerXP[5]+","+c.playerLevel[6]+","+c.playerXP[6]+","+c.playerLevel[7]+","+c.playerXP[7]+","+c.playerLevel[8]+","+c.playerXP[8]+","+c.playerLevel[9]+","+c.playerXP[9]+","+c.playerLevel[10]+","+c.playerXP[10]+","+c.playerLevel[11]+","+c.playerXP[11]+","+c.playerLevel[12]+","+c.playerXP[12]+","+c.playerLevel[13]+","+c.playerXP[13]+","+c.playerLevel[14]+","+c.playerXP[14]+","+c.playerLevel[15]+","+c.playerXP[15]+","+c.playerLevel[16]+","+c.playerXP[16]+","+c.playerLevel[17]+","+c.playerXP[17]+","+c.playerLevel[18]+","+c.playerXP[18]+","+c.playerLevel[19]+","+c.playerXP[19]+","+c.playerLevel[20]+","+c.playerXP[20]+");");
			query("INSERT INTO `skillsoverall` (`playerName`,`lvl`,`xp`) VALUES ('"+c.playerName+"',"+totallevell+","+totallvlexp+");");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}