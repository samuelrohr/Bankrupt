package utils;

import java.util.Random;

public class Dice {
	private int mFacesNumber;
	private Random mRandom;
	
	public Dice(int facesNumber) {
		mFacesNumber = facesNumber + 1;
		mRandom = new Random(); 
	}
	
	public int roll() {
		return mRandom.nextInt(mFacesNumber);
	}
}
