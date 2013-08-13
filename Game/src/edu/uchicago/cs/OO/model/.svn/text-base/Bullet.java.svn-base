package edu.uchicago.cs.OO.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Observer;

import edu.uchicago.cs.OO.controller.Game;
import edu.uchicago.cs.OO.view.View;


public class Bullet extends Attack {

	  private final double FIRE_POWER = 35.0;
	  private Fighter fighter;

	
	  public Bullet(Fighter f){
		
		super();
		setDamage(5);
		fighter = f;
		//a bullet expires after 20 frames
	    setExpire( 15 );
	    setRadius(6);
	    
	    //set the bullet orientation to the falcon (ship) orientation
	    if (f.isFacingRight()) {
			setOrientation(Directioin.RIGHT.getDegree());
		}
	    else{
	    	setOrientation(Directioin.LEFT.getDegree());
	    }
	    
	    //everything is relative to the falcon ship that fired the bullet
	    setDeltaX( f.getDeltaX() +
	               Math.cos( Math.toRadians( getOrientation() ) ) * FIRE_POWER );
	    setDeltaY( f.getDeltaY() +
	               Math.sin( Math.toRadians( getOrientation() ) ) * FIRE_POWER );
	    setCenter( f.getCenter() );

	   

		//notify the observers for change
	    setChanged();
  		notifyObservers();
	}

    //override the expire method - once an object expires, then remove it from the arrayList. 
	public void expire(){
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
