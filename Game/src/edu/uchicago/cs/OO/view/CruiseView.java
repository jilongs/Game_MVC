package edu.uchicago.cs.OO.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

import edu.uchicago.cs.OO.model.Cruise;
import edu.uchicago.cs.OO.model.Model;

public class CruiseView extends View {
	

	public CruiseView() {
		// TODO Auto-generated constructor stub
		
		//defined the points on a cartesean grid
		ArrayList<Point> pntCs = new ArrayList<Point>();


		pntCs.add(new Point(0, 5));
		pntCs.add(new Point(1, 3));
		pntCs.add(new Point(1, 0));
		pntCs.add(new Point(6, 0));
		pntCs.add(new Point(6, -1));
		pntCs.add(new Point(1, -1));
		pntCs.add(new Point(1, -2));

		pntCs.add(new Point(-1, -2));
		pntCs.add(new Point(-1, -1));
		pntCs.add(new Point(-6, -1));
		pntCs.add(new Point(-6, 0));
		pntCs.add(new Point(-1, 0));
		pntCs.add(new Point(-1, 3));
		assignPolarPoints(pntCs);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
}
