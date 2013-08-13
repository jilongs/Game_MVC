package edu.uchicago.cs.OO.model;

import java.awt.Point;

import edu.uchicago.cs.OO.controller.Game;
import edu.uchicago.cs.OO.view.View;

public class Fist extends Attack {
	private Fighter f;


	private final double DAMAGE_PERCENTAGE = 0.8;
	public Fist(Fighter fighter){
		super();
		f = fighter;
		setDamage(f.getStrength() * DAMAGE_PERCENTAGE);
		//a fist expires after 5 frames
	    setExpire(3);
	    setRadius(f.getRadius() * 3 / 4);
	    

	    //everything is relative to the falcon ship that fired the bullet
	    setDeltaX( f.getDeltaX());
	    setDeltaY( f.getDeltaY());
	    if (f.isFacingRight()) {
	    	int x = f.getCenter().x + f.getRadius();
	    	System.out.println(f.getCenter() + " " + x);
	    	int y = f.getCenter().y - f.getRadius() / 2;
	    	setCenter(new Point(x, y));
			setOrientation(Directioin.RIGHT.getDegree());
		}
	    else{
	    	int x = f.getCenter().x - f.getRadius() + this.getRadius();
	    	System.out.println(f.getCenter() + " " + x);
	    	int y = f.getCenter().y - f.getRadius() / 2;
	    	setCenter(new Point(x, y));
	    	setOrientation(Directioin.LEFT.getDegree());
	    }

		//notify the observers for change
	    setChanged();
  		notifyObservers();
	}

	public void move() {
		if (f.isMoving()) {
			double dAdjustX = Math.cos(Math.toRadians(f.getOrientation()))
					* f.getSpeed();
			double dAdjustY = Math.sin(Math.toRadians(f.getOrientation()))
					* f.getSpeed();
			setDeltaX(dAdjustX);
			setDeltaY(dAdjustY);
			
			setChanged();
			notifyObservers();
		}
		else{
			setDeltaX(0);
			setDeltaY(0);
		}
		super.move();
	} //end move
	
	public void expire() {
		if (getExpire() == 0){
			if (f == CommandCenter.getInstance().getPlayer()) {
				CommandCenter.getInstance().playerAttacks.remove(this);
			}
 			else{
 				CommandCenter.getInstance().opponentAttacks.remove(this);
 			}
			View view = CommandCenter.getInstance().getViewForModel(this);
			while(view != null){
				CommandCenter.getInstance().dynamicViews.remove(view);
				view = CommandCenter.getInstance().getViewForModel(this);
			}
		}
			
		else
			setExpire(getExpire() - 1);
	}
	
}
