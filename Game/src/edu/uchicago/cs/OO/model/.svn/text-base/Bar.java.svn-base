package edu.uchicago.cs.OO.model;

import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import edu.uchicago.cs.OO.controller.Game;

public class Bar extends StaticObject implements Observer {
	private String title;
	private Fighter owner;
	private int pointsLeft;
	
	public Bar(String titleString, Fighter fighter){
		super();
		title = titleString;
		owner = fighter;
		fighter.addObserver(this);
		
		if (title.equals("HP")) {
			pointsLeft = 100;
			if (owner == CommandCenter.getInstance().getPlayer()) {
				setCenter(new Point(30, 20));
			}
			else{
				setCenter(new Point(Game.DIM.width / 2 + 30, 20));
			}
		}
		else if(title.equals("MP")){
			pointsLeft = 0;
			if (owner == CommandCenter.getInstance().getPlayer()) {
				setCenter(new Point(30, 60));
			}
			else{
				setCenter(new Point(Game.DIM.width / 2 + 30, 60));
			}
		}
		else{
			//nothing
			;
		}
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Fighter getOwner() {
		return owner;
	}
	public void setOwner(Fighter owner) {
		this.owner = owner;
	}
	public int getPointsLeft() {
		return pointsLeft;
	}
	public void setPointsLeft(int pointsLeft) {
		this.pointsLeft = pointsLeft;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (title.equals("HP")) {
			setPointsLeft(owner.getHp());
		}
		else if(title.equals("MP")){
			setPointsLeft(owner.getMp());
		}
		
	}
	
}
