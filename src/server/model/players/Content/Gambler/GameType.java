package server.model.players.Content.Gambler;

public enum GameType {
	DICE_DUEL("Dice Duel", "1.Highest roll wins", "2.If there's a draw", "bets are returned.");

	private String gameName;
	private String[] rules;

	GameType(String gameName, String... rules) {
		this.setGameName(gameName);
		this.setRules(rules);
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String[] getRules() {
		return rules;
	}

	public void setRules(String[] rules) {
		this.rules = rules;
	}
}
