package model.player;

import model.Propertie;
import utils.Dice;

public class Impulsivo extends Player {
	
	public static final String NAME = "Impulsivo";
	
	public Impulsivo(int coins, Dice d6) {
		super(coins, d6);
	}
	
	@Override
	public boolean wantToBuy(Propertie currPropertie) {
		return true;
	}
	
	@Override
	public String getName() {
		return NAME;
	}
}
