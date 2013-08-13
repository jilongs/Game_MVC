package edu.uchicago.cs.OO.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observer;

import edu.uchicago.cs.OO.controller.Game;
import edu.uchicago.cs.OO.view.View;

public class Cruise extends Attack {

	private final double FIRE_POWER = 15.0;
	private final int MAX_EXPIRE = 25;
	private Fighter fighter;
	public Cruise(Fighter f) {

		super();
		fighter = f;

		//a cruis missile expires after 25 frames
		setExpire(MAX_EXPIRE);
		setRadius(20);
		setDamage(30);

		//everything is relative to the falcon ship that fired the bullet
		setDeltaX(f.getDeltaX()
				+ Math.cos(Math.toRadians(f.getOrientation())) * FIRE_POWER);
		setDeltaY(f.getDeltaY()
				+ Math.sin(Math.toRadians(f.getOrientation())) * FIRE_POWER);
		setCenter(f.getCenter());

		//set the bullet orientation to the falcon (ship) orientation
	    if (f.isFacingRight()) {
			setOrientation(Directioin.RIGHT.getDegree());
		}
	    else{
	    	setOrientation(Directioin.LEFT.getDegree());
	    }
		
		//notify the observers for change
		setChanged();
		notifyObservers();

	}
	
	@Override
	public void move() {

		super.move();
		
		if (getExpire() < MAX_EXPIRE -5){
			setDeltaX(getDeltaX() * 1.07);
			setDeltaY(getDeltaY() * 1.07);
		}
		


	}
	

	//override the expire method - once an object expires, then remove it from the arrayList.
	@Override
	public void expire() {
		if (getExpire() == 0){
 			if (fighter == CommandCenter.getInstance().getPlayer()) {
 				CommandCenter.getInstance().playerAttacks.remove(this);
			}
 			else{
 				CommandCenter.getInstance().opponentAttacks.remove(this);
 			}
 			View view = CommandCenter.getInstance().getViewForModel(this);
 			CommandCenter.getInstance().dynamicViews.remove(view);
 		}
		else
			setExpire(getExpire() - 1);
	}

}
