package player;

import model.Propertie;
import utils.Dice;

public class Impulsivo extends Player {
	
	public Impulsivo(int coins, Dice d6) {
		super(coins, d6);
	}
	
	@Override
	public boolean wantToBuy(Propertie currPropertie) {
		return true;
	}

}
