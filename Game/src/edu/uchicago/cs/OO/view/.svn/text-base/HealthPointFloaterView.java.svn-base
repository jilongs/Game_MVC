package edu.uchicago.cs.OO.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

import edu.uchicago.cs.OO.model.HealthPointFloater;
import edu.uchicago.cs.OO.model.Model;

public class HealthPointFloaterView extends View {

	public HealthPointFloaterView() {
		// TODO Auto-generated constructor stub
		ArrayList<Point> pntCs = new ArrayList<Point>();
		// top of ship
		pntCs.add(new Point(1, 5));
		pntCs.add(new Point(1, 1));
		pntCs.add(new Point(5, 1));
		pntCs.add(new Point(5, -1));
		pntCs.add(new Point(1, -1));
		pntCs.add(new Point(1, -5));
		pntCs.add(new Point(-1, -5));
		pntCs.add(new Point(-1, -1));
		pntCs.add(new Point(-5, -1));
		pntCs.add(new Point(-5, 1));
		pntCs.add(new Point(-1, 1));
		pntCs.add(new Point(-1, 5));

		assignPolarPoints(pntCs);
		setColor(Color.BLUE);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		//fill this polygon (with whatever color it has)
		g.fillPolygon(getXcoords(), getYcoords(), dDegrees.length);
		//now draw a white border
		g.setColor(Color.RED);
		g.drawPolygon(getXcoords(), getYcoords(), dDegrees.length);
	}

}
