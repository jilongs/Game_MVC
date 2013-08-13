package edu.uchicago.cs.OO.model;

import java.awt.*;

public interface Movable {
	//for the game to draw
	public void move();
	
	public Point getCenter();
	public int getRadius();
	
	//for short-lasting objects only like powerUps, bullets, special weapons and UFOs
	 //controls nExpiry that clicks down to expire, then the object should remove itself
	//see Bullet class for how this works. 
	public void expire();
	//for fading objects
	public void fadeInOut();
} //end Movable
