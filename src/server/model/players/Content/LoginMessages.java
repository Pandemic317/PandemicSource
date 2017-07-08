package server.model.players.Content;

import server.Server;
import server.model.players.Client;

/**
 * Handles all Login Messages
 * 
 * @author Gabbe
 */
@SuppressWarnings("all")
public class LoginMessages {

	public static void handleAllLoginMessages(Client c) {
		handleOwner(c);
		handlePlayer(c);
		handleDonator(c);
		handleMod(c);
		handleAdmin(c);
		handleHelper(c);
		TrustedDicer(c);
		handleYoutuber(c);
		handleMediaManager(c);
		TrustedDicer(c);
		SponsorRank(c);
	}

	public static void handleOwner(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerName.equalsIgnoreCase("haxman")
						&& c.playerRights == 3) {
				// c2.sendMessage("<shad=800000000>[Owner] Haxman has just logged in.");
				} 
			}
		}
	}

	public static void handleHelper(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerRights == 7) {
					c2.sendMessage("<shad=2964>[Helper] "
							+ c.playerName.toLowerCase()
							+ " Has Just Logged in!");
					c.staffLogin1 = System.currentTimeMillis();
				}
			}
		}
	}

	public static void handleYoutuber(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerRights == 8) {
					//c2.sendMessage("<shad=16014601>[Youtuber] "
					//		+ c.playerName.toLowerCase()
					//		+ " Has Just Logged in!");
				}
			}
		}
	}

	public static void handleMediaManager(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerRights == 9) {
					//c2.sendMessage("<col=8000000><shad=20451204>[Forum Manager] "
					//		+ c.playerName.toLowerCase()
					//		+ " Has Just Logged in!");
				}
			}
		}
	}

	public static void TrustedDicer(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerRights == 10) {
				//	c2.sendMessage("<shad=15460061>[Trusted Dicer] "
				//			+ c.playerName.toLowerCase()
				//			+ " Has Just Logged in!");
				}
			}
		}
	}

	public static void SponsorRank(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerRights == 6) {
					c2.sendMessage("<shad=800000000>[Sponsor] "
							+ c.playerName.toLowerCase()
							+ " Has Just Logged in!");
				}
			}
		}
	}

	public static void handlePlayer(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerRights == 0) {
					//c2.sendMessage("<shad=29184>[Player] "
					//		+ c.playerName.toLowerCase()
					//		+ " Has Just Logged in!");
				}
			}
		}
	}

	public static void handleDonator(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerRights == 4 && c.issDonator == 0) {
					//c2.sendMessage("<col=800000000><shad=255>[Donator] "
					//		+ c.playerName.toLowerCase()
					//		+ " Has Just Logged in!");
				} else if (c.playerRights == 5 && c.issDonator == 1) {
					//c2.sendMessage("<shad=15692059>[Super Donator] "
					//		+ c.playerName.toLowerCase()
					//		+ " Has Just Logged in!");
				} else if (c.playerRights == 5 && c.issDonator == 2) {
					//c2.sendMessage("<shad=15692059>[Extreme Donator] "
					//	+ c.playerName.toLowerCase()
					//	+ " Has Just Logged in!");

				} else if (c.playerName.equalsIgnoreCase("doubler")) {
					c2.sendMessage("<shad=15692059>[Donation Doubler] "
							+ c.playerName.toLowerCase()
							+ " Has Just Logged in!");
				}

			}
		}
	}

	/*
	 * public static void handlesDonator(Client c) { for (int j = 0; j <
	 * Server.playerHandler.players.length; j++) { if
	 * (Server.playerHandler.players[j] != null) { Client c2 =
	 * (Client)Server.playerHandler.players[j]; if (c.playerRights == 5 &&
	 * c.issDonator == 1) {
	 * c2.sendMessage("<shad=15692059>[Super Donator] "+c.playerName
	 * .toLowerCase()+" Has Just Logged in!"); } } } } public static void
	 * handlesDonator(Client c) { for (int j = 0; j <
	 * Server.playerHandler.players.length; j++) { if
	 * (Server.playerHandler.players[j] != null) { Client c2 =
	 * (Client)Server.playerHandler.players[j]; if (c.playerRights == 5 &&
	 * c.issDonator == 2) {
	 * c2.sendMessage("<shad=15692059>[Extreme Donator] "+c.
	 * playerName.toLowerCase()+" Has Just Logged in!"); } } } }
	 */
	public static void handleMod(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerRights == 1) {
					c2.sendMessage("<shad=10712099>[Moderator] "
							+ c.playerName.toLowerCase()
							+ " Has just Logged in!"); // 4348433
					c.staffLogin = System.currentTimeMillis();
				}
			}
		}
	}

	public static void handleAdmin(Client c) {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				if (c.playerRights == 2) {
					c2.sendMessage("<shad=200000000>[Administrator] "
							+ c.playerName.toLowerCase()
							+ " Has just Logged in!");
					c.staffLogin = System.currentTimeMillis();
				}
			}
		}
	}
}