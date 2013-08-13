package edu.uchicago.cs.OO.model;

import java.awt.*;

public interface Visable {
	public void draw(Graphics g);

	//for collisions
	//if two objects are both friends or both foes, then nothing happens, otherwise exlode both
	//the friend type may be DEBRIS, in which case it is inert
	//public byte getFriend();
	//when a foe explodes, your points increase
	public int points();
	

}
