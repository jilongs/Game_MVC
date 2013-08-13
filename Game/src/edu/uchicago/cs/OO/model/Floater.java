package edu.uchicago.cs.OO.model;

import java.awt.Point;

import edu.uchicago.cs.OO.controller.Game;
import edu.uchicago.cs.OO.view.View;

public class Floater extends MovingObject {
	
	private int nSpin;
	public Floater(){
		super();
		setExpire(250);
		setRadius(50);

		int nX = Game.R.nextInt(10);
		int nY = Game.R.nextInt(10);
		int nS = Game.R.nextInt(5);
		
		
		//set random DeltaX
		if (nX % 2 == 0)
			setDeltaX(nX);
		else
			setDeltaX(-nX);

		//set rnadom DeltaY
		if (nY % 2 == 0)
			setDeltaX(nY);
		else
			setDeltaX(-nY);
		
		//set random spin
		if (nS % 2 == 0)
			setSpin(nS);
		else
			setSpin(-nS);

		//random point on the screen
		setCenter(new Point(Game.R.nextInt(Game.DIM.width/2),
				Game.R.nextInt(Game.DIM.height/2) + Game.DIM.height/4));

		//random orientation 
		 setOrientation(Game.R.nextInt(360));
		 
		//notify the observers for change
		setChanged();
		notifyObservers();
	}
	
	public void move() {
		super.move();

		setOrientation(getOrientation() + getSpin());

	}

	public int getSpin() {
		return this.nSpin;
	}

	public void setSpin(int nSpin) {
		this.nSpin = nSpin;
	}

	//override the expire method - once an object expires, then remove it from the arrayList.
	@Override
	public void expire() {
		if (getExpire() == 0){
			CommandCenter.getInstance().floaters.remove(this);
 			View view = CommandCenter.getInstance().getViewForModel(this);
 			CommandCenter.getInstance().dynamicViews.remove(view);
 		}
		else
			setExpire(getExpire() - 1);
	}

}
