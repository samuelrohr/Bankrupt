package player;

import model.Propertie;
import utils.Dice;

public class Cauteloso extends Player {

	public Cauteloso(int coins, Dice d6) {
		super(coins, d6);
	}

	@Override
	public boolean wantToBuy(Propertie currPropertie) {
		if(mCoins - currPropertie.getBuyCost() >= 80) {
			return true;
		}
		return false;
	}

}
