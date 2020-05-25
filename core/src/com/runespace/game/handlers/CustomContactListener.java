package com.runespace.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.runespace.game.item.Coin;
import com.runespace.game.utils.Constants;

public class CustomContactListener implements ContactListener {
	
	private int footContacts = 0;
	private int sensorGContacts = 0;
	private int headContacts = 0;
	private int coinContacts = 0;
	private int dead = 0;
	private int win = 0;
	//contact commence
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();

		if(fB.getUserData() != null && fB.getUserData().equals("win")) {
			win--;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("win")) {
			win--;
		}

		if(fA.getUserData() != null && fA.getUserData().equals("foot")) {
			 
			footContacts++;
		}
		
		if(fB.getUserData() != null && fB.getUserData().equals("foot")) {
			 
			footContacts++;
		}
		if(fA.getUserData()!= null && fA.getUserData().equals("sensorG")) {
			sensorGContacts++;
			
		}
		
		if(fB.getUserData()!= null && fB.getUserData().equals("sensorG")) {
			sensorGContacts++;
			
		}
		if(fA.getUserData() != null && fA.getFilterData().categoryBits == Constants.HEAD_BIT) {

			headContacts++;
		}
		
		if(fB.getUserData() != null && fB.getFilterData().categoryBits == Constants.HEAD_BIT) {
			 
			headContacts++;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("deadInversed")) {
			 
			dead++;
		}

		if(fB.getUserData() != null && fB.getUserData().equals("deadInversed")) {
			 
			dead++;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("dead")) {
			 
			dead++;
		}

		if(fB.getUserData() != null && fB.getUserData().equals("dead")) {
			 
			dead++;
		}

		if(fA.getUserData() != null && fA.getFilterData().categoryBits == Constants.COINT_BIT) {
		    coinContacts++;
			((Coin)fB.getUserData()).onHeadHit();
		}

		if(fB.getUserData() != null && fB.getFilterData().categoryBits == Constants.COINT_BIT) {
		    coinContacts++;
			((Coin)fB.getUserData()).onHeadHit();
		}
		// 
	}
	
	//contact termine
	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		Fixture fA = contact.getFixtureA();
		Fixture fB = contact.getFixtureB();
		
		if(fA.getUserData() != null && fA.getUserData().equals("foot")) {
			footContacts--;
		}
		
		if(fB.getUserData() != null && fB.getUserData().equals("foot")) {
			footContacts--;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("sensorG")) {
			sensorGContacts--;
		}
		
		if(fB.getUserData() != null && fB.getUserData().equals("sensorG")) {
			sensorGContacts--;
		}
		if(fA.getUserData() != null && fA.getFilterData().categoryBits == Constants.HEAD_BIT) {

			headContacts--;
		}

		if(fB.getUserData() != null && fB.getFilterData().categoryBits == Constants.HEAD_BIT) {

			headContacts--;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("deadInversed")) {
			System.out.println(fA.getUserData()+ " dis enrevoir a " +fB.getUserData());
			dead--;
		}

		if(fB.getUserData() != null && fB.getUserData().equals("deadInversed")) {
			dead--;
		}
		if(fA.getUserData() != null && fA.getUserData().equals("dead")) {
			dead--;
		}

		if(fB.getUserData() != null && fB.getUserData().equals("dead")) {
			dead--;
		}
		//System.out.println(fA.getUserData()+ " dis enrevoir a " +fB.getUserData());
        if(fA.getUserData() != null && fA.getFilterData().categoryBits == Constants.COIN_USE_BIT) {
            coinContacts--;
        }

        if(fB.getUserData() != null && fB.getFilterData().categoryBits == Constants.COIN_USE_BIT) {
            coinContacts--;
        }
	}

	//avant le dbut du contact
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	//avant la fin du contact
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isOnGround() {
		return footContacts > 0;
	}
	public boolean isDead() {
		return dead > 0;
	}
	public boolean isOnHead() { return headContacts > 0; }
	public boolean isSensorG() {
		return sensorGContacts > 0;
	}
    public boolean isCoinG(){
	    return coinContacts > 0;
	}
	public boolean isWin(){
		return win > 0;
	}
}
