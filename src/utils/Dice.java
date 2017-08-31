package utils;

import java.util.Random;

public class Dice {
	private int mFacesNumber;
	private Random mRandom;
	
	public Dice(int facesNumber) {
		mFacesNumber = facesNumber;
		mRandom = new Random(); 
	}
	
	/**
	 * Retornar o valor de 1 até o numero de faces setado
	 * @return
	 */
	public int roll() {
		return mRandom.nextInt(mFacesNumber) + 1;
	}
}
