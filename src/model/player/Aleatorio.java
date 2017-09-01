package model.player;

import model.Propertie;
import utils.Dice;

public class Aleatorio extends Player {

	public static final String NAME = "Aleatório";
	private Dice d2;
	
	public Aleatorio(int coins, Dice d6) {
		super(coins, d6);
		
		d2 = new Dice(2);
	}

	@Override
	public boolean wantToBuy(Propertie currPropertie) {
		if(d2.roll() == 1) {
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		return NAME;
	}
}
