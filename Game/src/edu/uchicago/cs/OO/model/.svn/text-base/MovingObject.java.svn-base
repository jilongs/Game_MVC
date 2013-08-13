package edu.uchicago.cs.OO.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import edu.uchicago.cs.OO.controller.Game;

public abstract class MovingObject extends Model implements Movable {

	//is this DEBRIS, FRIEND, FOE, OR FLOATER 
	//private byte yFriend;
	//degrees (where the sprite is pointing out of 360)
	private int nOrientation;


	
	//for drawing alternative 
	//public double[] dLengthAlts;
	//public double[] dDegreeAlts;
	

	//fade value for fading in and out
	private int nFade;



	public void move() {

		Point pnt = getCenter();
		double dX = pnt.x + getDeltaX();
		double dY = pnt.y + getDeltaY();
		
		//this just keeps the object inside the bounds of the frame
		if (pnt.x > getDim().width) {
			setCenter(new Point(1, pnt.y));

		} else if (pnt.x < 0) {
			setCenter(new Point(getDim().width - 1, pnt.y));
		} else if (pnt.y > getDim().height) {
			setCenter(new Point(pnt.x, 1));

		} else if (pnt.y < 0) {
			setCenter(new Point(pnt.x, getDim().height - 1));
		} else {

			setCenter(new Point((int) dX, (int) dY));
		}
		setChanged();
		notifyObservers();
	}

	public boolean isExploding() {
		return false;
	}

	public void fadeInOut() {
	};

	public int getOrientation() {
		return nOrientation;
	}

	public void setOrientation(int n) {
		nOrientation = n;
	}


	public int getFadeValue() {
		return nFade;
	}

	public void setFadeValue(int n) {
		nFade = n;
	}

}
