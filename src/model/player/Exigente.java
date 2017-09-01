package model.player;

import model.Propertie;
import utils.Dice;

public class Exigente extends Player {

	public static final String NAME = "Exigente";
	
	public Exigente(int coins, Dice d6) {
		super(coins, d6);
	}

	@Override
	public boolean wantToBuy(Propertie currPropertie) {
		if(currPropertie.getRentCost() > 50) {
			return true;
		}
		return false;
	}
	
	@Override
	public String getName() {
		return NAME;
	}
}
