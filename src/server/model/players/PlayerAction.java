package server.model.players;


public class PlayerAction {

	Client c;
	
	public PlayerAction(Client c) {
		this.c = c;
	}

	public boolean inAction = false;
	public boolean canWalk = true;
	public boolean canEat = true;
	
	public boolean setAction(boolean action)  {
		return inAction = action;
	}
	
	private boolean getAction() {
		return inAction;
	}
	
	public boolean checkAction() {
		return getAction();
	}
	
	public boolean canWalk(boolean walk)  {
		return canWalk = walk;
	}
	
	private boolean getcanWalk() {
		return canWalk;
	}
	
	public boolean checkWalking() {
		if (getcanWalk()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canEat(boolean eat)  {
		return canEat = eat;
	}
	
	private boolean getcanEat() {
		return canEat;
	}
	
	public boolean checkEating() {
		if (getcanEat()) {
			return true;
		} else {
			return false;
		}
	}
}