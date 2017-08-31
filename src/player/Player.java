package player;

import java.util.ArrayList;
import java.util.List;

import model.Propertie;
import utils.Dice;

public abstract class Player {
	protected int mCoins;
	private List<Propertie> mProperties;
	private int mBoardPos;
	private Dice mD6;
	private int mInitiative;
	
	/**
	 * Valor de coins iniciais do player e o dado que foi instanciado pelo Game
	 * @param coins
	 * @param d6
	 */
	public Player(int coins, Dice d6) {
		mBoardPos = 0;
		mCoins = coins;
		mProperties = new ArrayList<Propertie>();
		mD6 = d6;
		mInitiative = d6.roll();
	}
	
	public abstract boolean wantToBuy(Propertie currPropertie);
	
	public int getCoins() {
		return mCoins;
	}
	
	public List<Propertie> getProperties() {
		return mProperties;
	}
	
	public int getPosition() {
		return mBoardPos;
	}
	
	public int getInitiative() {
		return mInitiative;
	}
	
	public void receivePayment(int payment) {
		mCoins += payment;
	}
	
	public void deductValue(int value) {
		mCoins -= value;
	}
	
	public int move() {
		mBoardPos += mD6.roll();
		return mBoardPos;
	}
}
