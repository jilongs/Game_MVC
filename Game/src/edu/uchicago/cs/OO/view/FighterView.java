package edu.uchicago.cs.OO.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import com.sun.tools.javac.code.Attribute.Array;

import edu.uchicago.cs.OO.model.Directioin;
import edu.uchicago.cs.OO.model.Fighter;
import edu.uchicago.cs.OO.model.Model;

public class FighterView extends View {
	
	//for drawing alternative shapes

	public double[] dLengthsAlts;
	public double[] dDegreesAlts;
	public FighterView() {
		// TODO Auto-generated constructor stub
		ArrayList<Point> pntCs = new ArrayList<Point>();
		
		pntCs.add(new Point(-1, 2)); 
		pntCs.add(new Point(1, 2)); 
		pntCs.add(new Point(1, -2)); 
		pntCs.add(new Point(-1, -2)); 
		assignPolarPoints(pntCs);
		
		ArrayList<Point> SquatPntCs = new ArrayList<Point>();
		SquatPntCs.add(new Point(-1, 1)); 
		SquatPntCs.add(new Point(1, 1)); 
		SquatPntCs.add(new Point(1, -1)); 
		SquatPntCs.add(new Point(-1, -1)); 
		
		assignPolorPointsAlts(SquatPntCs);

		setColor(Color.RED);
	}
	//assign for alt imag
	protected void assignPolorPointsAlts(ArrayList<Point> pntCs) {
		 dDegreesAlts = convertToPolarDegs(pntCs);
		 dLengthsAlts = convertToPolarLens(pntCs);

	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	public void draw(Graphics g) {
		Fighter f = (Fighter)model;
		if (f.isSquat()) {
			drawSquat(g);
			return;
		}
		//does the fading at the beginning or after hyperspace
		Color colFighter;
		if (f.getFadeValue() == 255) {
			colFighter = Color.white;
		} else {
			colFighter = new Color(adjustColor(f.getFadeValue(), 200), adjustColor(
					f.getFadeValue(), 175), f.getFadeValue());
		}

//		//shield on
//		if (bShield && nShield > 0) {
//
//			setShield(getShield() - 1);
//
//			g.setColor(Color.cyan);
//			g.drawOval(getCenter().x - getRadius(),
//					getCenter().y - getRadius(), getRadius() * 2,
//					getRadius() * 2);
//
//		} //end if shield

		drawFighterWithColor(g, getColor());
	} //end draw()
	
	private void drawSquat(Graphics g){
		Fighter f = (Fighter)model;
    	setXcoords( new int[dDegreesAlts.length]);
    	setYcoords( new int[dDegreesAlts.length]);
        setObjectPoints( new Point[dDegreesAlts.length]);
        for (int nC = 0; nC < dDegreesAlts.length; nC++) {
        	setXcoord((int) (f.getCenter().x + f.getRadius() *3 / 4
                    * dLengthsAlts[nC] 
                    * Math.sin(Math.toRadians(Directioin.DOWN.getDegree()) + dDegreesAlts[nC])), nC);
        	

        	setYcoord((int) (f.getCenter().y + f.getRadius() / 2 - f.getRadius() *3 / 4
                            * dLengthsAlts[nC]
                            * Math.cos(Math.toRadians(Directioin.DOWN.getDegree()) + dDegreesAlts[nC])), nC);
            //need this line of code to create the points which we will need for debris
        	setObjectPoint( new Point(getXcoord(nC), getYcoord(nC)), nC);
        }
        g.setColor(getColor());
        g.drawPolygon(getXcoords(), getYcoords(), dDegreesAlts.length);
	}
	private int adjustColor(int nCol, int nAdj) {
		if (nCol - nAdj <= 0) {
			return 0;
		} else {
			return nCol - nAdj;
		}
	}
	public void drawFighterWithColor(Graphics g, Color col) {
		super.draw(g);
		g.setColor(col);
		g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
	}
}
