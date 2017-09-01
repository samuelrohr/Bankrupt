package model;

import model.player.Player;

public class Propertie {
	
	private int mBuyCost;
	private int mRentCost;
	private Player mOwner;
	
	public Propertie(int buyCost, int rentCost) {
		mBuyCost = buyCost;
		mRentCost = rentCost;
		mOwner = null;
	}
	
	public Propertie(String buyCost, String rentCost) {
		mBuyCost = Integer.parseInt(buyCost);
		mRentCost = Integer.parseInt(rentCost);
		mOwner = null;
	}
	
	public Player getOwner() {
		return mOwner;
	}
	
	public void removeOwner() {
		mOwner = null;
	}
	
	public int getBuyCost() {
		return mBuyCost;
	}
	
	public int getRentCost() {
		return mRentCost;
	}
	
	public boolean buy(Player owner) {
		if(mOwner == null) {
			mOwner = owner;
			return true;
		}
		return false;
	}
}
