package edu.uchicago.cs.OO.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Observable;

import edu.uchicago.cs.OO.controller.Game;
import edu.uchicago.cs.OO.model.*;

public class BarView extends View {
	
	private String title;
	private Fighter owner;
	private Bar bar;
	private int width;
	private int height;
	
	private Directioin directioin;
	public BarView(){
		super();
		// TODO Auto-generated constructor stub
		//defined the points on a cartesean grid
		height = 30;
		width = Game.DIM.width / 2 - 60;
	}
	public void setModel(Model model) {
		this.model = model;
		this.model.addObserver(this);
		bar = (Bar) model;
		title = bar.getTitle();
		owner = bar.getOwner();
		
		if (title.equals("HP")) {
			setColor(Color.GREEN);
			if (owner == CommandCenter.getInstance().getPlayer()) {
				directioin = Directioin.RIGHT;
			}
			else{
				directioin = Directioin.LEFT;
			}
		}
		else if(title.equals("MP")){
			setColor(Color.BLUE);
			if (owner == CommandCenter.getInstance().getPlayer()) {
				directioin = Directioin.RIGHT;
			}
			else{
				directioin = Directioin.LEFT;
			}
		}
		else{
			//nothing
		}
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
        g.drawRect(model.getCenter().x, model.getCenter().y, width, height);
        double lifeLeft = width * bar.getPointsLeft() / 100;
        
        int fontSize = 20;

        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
         
        g.setColor(Color.WHITE);
        
        
        if (directioin == Directioin.RIGHT) {
        	g.drawString(title, model.getCenter().x - 30, model.getCenter().y);
        	g.setColor(getColor());
        	g.fillRect(model.getCenter().x, model.getCenter().y, (int)lifeLeft, height);
		}
        else{
        	g.drawString(title, model.getCenter().x + width, model.getCenter().y);
        	g.setColor(getColor());
        	g.fillRect(model.getCenter().x + width - (int)lifeLeft, model.getCenter().y, (int)lifeLeft, height);
        }
	}

}
