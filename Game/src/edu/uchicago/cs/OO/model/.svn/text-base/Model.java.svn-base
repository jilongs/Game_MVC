package edu.uchicago.cs.OO.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import edu.uchicago.cs.OO.controller.Game;

public abstract class Model extends Observable{
	//the center-point of this sprite
	private Point pntCenter;
	//this causes movement; change in x and change in y
	private double dDeltaX, dDeltaY;
	//every sprite needs to know about the size of the gaming environ
	private Dimension dim; //dim of the gaming environment
	
	

	//the radius of circumscibing circle
	private int nRadius;
	
	private int nExpiry; //natural mortality (short-living objects)
	
	public Model(){
		setDim(Game.DIM);
		setCenter(new Point(Game.R.nextInt(Game.DIM.width),
				Game.R.nextInt(Game.DIM.height)));
	}
	public void addOvserver(Observer o){
		addObserver(o);
	}
	public void setDeltaX(double dSet) {
		dDeltaX = dSet;
	}

	public void setDeltaY(double dSet) {
		dDeltaY = dSet;
	}

	public double getDeltaY() {
		return dDeltaY;
	}

	public double getDeltaX() {
		return dDeltaX;
	}

	public int getRadius() {
		return nRadius;
	}

	public void setRadius(int n) {
		nRadius = n;

	}

	public Dimension getDim() {
		return dim;
	}

	public void setDim(Dimension dim) {
		this.dim = dim;
	}

	public Point getCenter() {
		return pntCenter;
	}

	public void setCenter(Point pntParam) {
		pntCenter = pntParam;
	}
	
	public void setExpire(int n) {
		nExpiry = n;
	}

	public int getExpire() {
		return nExpiry;
	}

	public void expire() {
	}
	
	public void tick(){
		
	}

	
}
