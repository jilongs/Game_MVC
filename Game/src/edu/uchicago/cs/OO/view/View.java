package edu.uchicago.cs.OO.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observer;

import com.sun.tools.javac.code.Attribute.Array;

import edu.uchicago.cs.OO.model.*;


public abstract class View  implements Observer{
	
	private Dimension dim; //dim of the gaming environment
	//the color of this sprite
	private Color col;

	//radial coordinates
	//this game uses radial coordinates to render sprites
	public double[] dLengths;
	public double[] dDegrees;
	
	//these are used to draw the polygon. You don't usually need to interface with these
	private Point[] pntCoords; //an array of points used to draw polygon
	private int[] nXCoords;
	private int[] nYCoords;
	
	//the model of this view
	
	protected Model model;
	
	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
		this.model.addObserver(this);
	}

	public View(){
		setColor(Color.white);
	}
	
	public Dimension getDim() {
		return dim;
	}

	public void setDim(Dimension dim) {
		this.dim = dim;
	}
	
	public double[] getLengths() {
		return this.dLengths;
	}

	public void setLengths(double[] dLengths) {
		this.dLengths = dLengths;
	}

	public double[] getDegrees() {
		return this.dDegrees;
	}

	public void setDegrees(double[] dDegrees) {
		this.dDegrees = dDegrees;
	}

	public Color getColor() {
		return col;
	}

	public void setColor(Color col) {
		this.col = col;

	}

	public void setYcoord(int nValue, int nIndex) {
		nYCoords[nIndex] = nValue;
	}

	public void setXcoord(int nValue, int nIndex) {
		nXCoords[nIndex] = nValue;
	}
	
	
	public int getYcoord( int nIndex) {
		return nYCoords[nIndex];// = nValue;
	}

	public int getXcoord( int nIndex) {
		return nXCoords[nIndex];// = nValue;
	}

	public int[] getXcoords() {
		return nXCoords;
	}

	public int[] getYcoords() {
		return nYCoords;
	}
	
	
	public void setXcoords( int[] nCoords) {
		 nXCoords = nCoords;
	}

	public void setYcoords(int[] nCoords) {
		 nYCoords =nCoords;
	}

	//utility function to convert from cartesian to polar
	//since it's much easier to describe a sprite as a list of cartesean points
	//sprites (except Asteroid) should use the cartesean technique to describe the coordinates
	//see Falcon or Bullet constructor for examples
	protected double[] convertToPolarDegs(ArrayList<Point> pntPoints) {

		//ArrayList<Tuple<Double,Double>> dblCoords = new ArrayList<Tuple<Double,Double>>();
		double[] dDegs = new double[pntPoints.size()];

		int nC = 0;
		for (Point pnt : pntPoints) {
			dDegs[nC++]=(Math.toDegrees(Math.atan2(pnt.y, pnt.x))) * Math.PI / 180 ;
		}
		return dDegs;
	}
	//utility function to convert to polar
	protected double[] convertToPolarLens(ArrayList<Point> pntPoints) {

		double[] dLens = new double[pntPoints.size()];

		//determine the largest hypotenuse
		double dL = 0;
		for (Point pnt : pntPoints)
			if (hypot(pnt.x, pnt.y) > dL)
				dL = hypot(pnt.x, pnt.y);

		int nC = 0;
		for (Point pnt : pntPoints) {
			if (pnt.x == 0 && pnt.y > 0) {
				dLens[nC] = hypot(pnt.x, pnt.y) / dL;
			} else if (pnt.x < 0 && pnt.y > 0) {
				dLens[nC] = hypot(pnt.x, pnt.y) / dL;
			} else {
				dLens[nC] = hypot(pnt.x, pnt.y) / dL;
			}
			nC++;
		}

		// holds <thetas, lens>
		return dLens;

	}
	
	protected double hypot(double dX, double dY) {
		return Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
	}

	protected void assignPolarPoints(ArrayList<Point> pntCs) {
		setDegrees(convertToPolarDegs(pntCs));
		setLengths(convertToPolarLens(pntCs));

	}
	
    public void draw(Graphics g) {
        nXCoords = new int[dDegrees.length];
        nYCoords = new int[dDegrees.length];
        
        //need this as well
        pntCoords = new Point[dDegrees.length];
        
		for (int nC = 0; nC < dDegrees.length; nC++) {

			nXCoords[nC] = (int) (model.getCenter().x + model.getRadius()
					* dLengths[nC] * Math.sin(Math.toRadians(Directioin.UP.getDegree()) + dDegrees[nC]));
			nYCoords[nC] = (int) (model.getCenter().y - model.getRadius()
					* dLengths[nC] * Math.cos(Math.toRadians(Directioin.UP.getDegree()) + dDegrees[nC]));

			// need this line of code to create the points which we will need
			// for debris
			pntCoords[nC] = new Point(nXCoords[nC], nYCoords[nC]);
		}
        g.setColor(getColor());

        g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
    }
    
	
	public void setObjectPoint(Point pnt, int nIndex) {
		 pntCoords[nIndex] = pnt;
	}
	
	public Point[] getObjectPoints() {
		return pntCoords;
	}
	
	public void setObjectPoints(Point[] pntPs) {
		 pntCoords = pntPs;
	}
	

}
