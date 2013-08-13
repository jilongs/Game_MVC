package edu.uchicago.cs.OO.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;


public class FistView extends View {
	public FistView(FighterView fv){
		super();
		setColor(fv.getColor());
		ArrayList<Point> pntCs = new ArrayList<Point>();
		
		pntCs.add(new Point(0, 0)); 
		pntCs.add(new Point(2, 0)); 
		pntCs.add(new Point(2, 1)); 
		pntCs.add(new Point(0, 1)); 
		assignPolarPoints(pntCs);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
	

}
