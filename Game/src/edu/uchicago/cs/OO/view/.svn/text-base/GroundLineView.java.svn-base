package edu.uchicago.cs.OO.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;

import edu.uchicago.cs.OO.controller.Game;

public class GroundLineView extends View {
	private int position = 0;
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public GroundLineView(){
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
    public void draw(Graphics g) {
    	g.setColor(Color.GREEN);
        g.drawLine(0, position, Game.DIM.width, position);
    }

}
