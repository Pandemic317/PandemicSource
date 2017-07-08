package server.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.model.players.Client;
import server.model.players.PlayerHandler;

/*
 * Using this class:
 * To call this class, it's best to make a new thread. You can do it below like so:
 * new Thread(new Donation(player)).start();*/
 
public class Donation implements Runnable {

	public static final String HOST = "162.212.253.153"; // website ip address
	public static final String USER = "LfPTJR62Hu30";
	public static final String PASS = "sl1d3r";
	public static final String DATABASE = "beastps1_Donate";

	private static int minimum = 1; // must match what u have set on the website!
	private static int maximum = 1000;
	private static double pricePerToken = 1.00; // must match what u have set on the website!

	private Client player;
	private Connection conn;
	private Statement stmt;

	public Donation(Client player) {
		this.player = player;
	}

	@Override
	public void run() {
		try {
			if (!connect(HOST, DATABASE, USER, PASS)) {
				return;
			}

			String name = Misc.formatPlayerName(player.playerName);
			ResultSet rs = executeQuery("SELECT * FROM donations WHERE user='"+name+"' AND claimed=0");

			while (rs.next()) {
				int quantity = rs.getInt("quantity");
				double total = rs.getDouble("amount");
				double ratio = total / quantity;

				if (quantity < minimum || quantity > maximum || ratio != pricePerToken) {
					System.out.println("Invalid purchase detected for "+name+"!");
					continue;
				}
				if (player.playerRights == 3 || player.playerRights == 2 || player.playerRights == 1 || player.playerRights == 7 || 
						player.playerRights == 8 || player.playerRights == 9 || player.playerRights == 10 || player.playerRights == 11) {
					if (player.issDonator < 2) {
						player.issDonator = 2;
						player.isDonator = 1;
						player.sendMessage("<col=800000000><shad>You have been given free Extreme Donator for being part of the staff/media management.");
					}
					player.donatorChest += quantity;
					player.totalDonatorPoints += quantity;
					PlayerHandler.sendGlobalMessage("[DONATION]", "" + player.playerName
					+ "  has just donated for "  + quantity + "  Donator Points! Type ::donate to donate!");
					player.sendMessage("<col=255>Thank you for your donation! You have received "+ Misc.format(quantity)+" points!");
					player.sendMessage("<col=255>Please relog if you have not received your donator rank!");
					player.sendMessage("<col=255>You have received "+ quantity +"x Crystal Keys in your bank for donating $"+ quantity +"!");
					player.getItems().addItemToBank(989, quantity);
					rs.updateInt("claimed", 1);
					rs.updateRow();
				} else {//
				player.donatorChest += quantity;{
				player.totalDonatorPoints += quantity;
				PlayerHandler.sendGlobalMessage("[DONATION]", "" + player.playerName
				+ "  has just donated for "  + quantity + "  Donator Points! Type ::donate to donate!");
				if(player.playerRights >= 1 && player.playerRights <= 3 && player.playerRights == 7){
				} else {
					if(player.isDonator == 0 && player.totalDonatorPoints < 15){
						player.isDonator = 1;
						player.playerRights = 4;
						player.isDonator = 1; //that was auto too, 1
					} else if(player.issDonator < 2 && player.totalDonatorPoints >= 40 && player.totalDonatorPoints < 100){
						player.isDonator = 1;
						player.issDonator = 2;
						player.playerRights = 5;
						player.isDonator = 1; //that was auto too, 1
					} else if(player.issDonator < 1 && player.totalDonatorPoints > 14 && player.totalDonatorPoints < 40){
						player.isDonator = 1;
						player.issDonator = 1;
						player.playerRights = 5;
						player.isDonator = 1; //that was auto too, 1
					} else if (player.playerRights == 6) {
						player.playerRights = 6;
					}
					}

				}
				if(player.totalDonatorPoints >= 100){
					player.playerRights = 6;
					player.isDonator = 1;
					player.issDonator = 2;
				}
				player.sendMessage("<col=255>Thank you for your donation! You have received "+ Misc.format(quantity)+" points!");
				player.sendMessage("<col=255>Please relog if you have not received your donator rank!");
				player.sendMessage("<col=255>You have received "+ quantity +"x Crystal Keys in your bank for donating $"+ quantity +"!");
				player.getItems().addItemToBank(989, quantity);
				rs.updateInt("claimed", 1);
				rs.updateRow();

			}
			}
			// normal: rights = 4, isDonator = 1;,   super: rights = 5, isDonator = 1 & issDonator = 1;, extreme same as super but issDonator = 2
			//We also have a sponsor rank, which is 100$+ donated in total, right is err 6 I believe yh 6, where is total stored
			destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean connect(String host, String database, String user, String pass) {
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, pass);
			return true;
		} catch (SQLException e) {
			System.out.println("Failing connecting to database!");
			return false;
		}
	}

	public void destroy() {
		try {
			conn.close();
			conn = null;
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public int executeUpdate(String query) {
		try {
			this.stmt = this.conn.createStatement(1005, 1008);
			int results = stmt.executeUpdate(query);
			return results;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public ResultSet executeQuery(String query) {
		try {
			this.stmt = this.conn.createStatement(1005, 1008);
			ResultSet results = stmt.executeQuery(query);
			return results;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}


}


