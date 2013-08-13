package edu.uchicago.cs.OO.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

import edu.uchicago.cs.OO.controller.Game;


public class Fighter extends MovingObject {

	// ==============================================================
	// FIELDS 
	// ==============================================================
	
	//from the beginning to the end of a jump would last 20 ticks
	private final int JUMPTIME = 10;

	final int DEGREE_STEP = 7;
	
	private boolean bShield = false;
	private boolean bProtected; //for fade in and out
	
	private boolean bJump = false;
	private boolean bMoving = false;
	private boolean bDefend = false;
	private boolean bSquat = false;
	private boolean bFacingRight = false;
	private boolean bAutoMove = false; 
	private int jumpUpCount = 0;
	private int jumpDownCount = 0;
	
	private int nShield;
	private double strength = 20;
	private double armour = 5;
	private double speed = 10;
	private int hp = 100;

	private int mp = 0;
	
			


	
	// ==============================================================
	// CONSTRUCTOR 
	// ==============================================================
	



	public Fighter() {
		super();
		
		
		//with random orientation
		setOrientation(0);
		
		//this is the size of the falcon
		setRadius(40);

		//these are falcon specific
		setProtected(true);
		setFadeValue(0);
		//notify the observers for change
		setChanged();
		notifyObservers();
	}
	public void init(){
		hp = 100;
		mp = 0;
		setChanged();
		notifyObservers();
	}
	
	// ==============================================================
	// METHODS 
	// ==============================================================

	public void move() {
		if (bMoving) {
			if (bJump) {
				if (jumpUpCount < JUMPTIME) {
					jumpUpCount ++;	
				}
				else {
					if (jumpDownCount == 0){
						jumpDownCount ++;
						setOrientation(Directioin.DOWN.getDegree());
					}
					else if (jumpDownCount == JUMPTIME) {
						bJump = false;
						System.out.println(bJump);
						stopMoving();
						setChanged();
						notifyObservers();
						return;
					}
					else{
						jumpDownCount ++;
					}
				}
			}
			
			double dAdjustX = Math.cos(Math.toRadians(getOrientation()))
					* speed;
			double dAdjustY = Math.sin(Math.toRadians(getOrientation()))
					* speed;
			setDeltaX(dAdjustX);
			setDeltaY(dAdjustY);
			
			Point pnt = getCenter();
			double dX = pnt.x + getDeltaX();
			double dY = pnt.y + getDeltaY();
			
			//this just keeps the object inside the bounds of the frame
			if (pnt.x > getDim().width) {
				setCenter(new Point(1, pnt.y));

			} else if (pnt.x < 0) {
				setCenter(new Point(getDim().width - 1, pnt.y));
			} else if (pnt.y > getDim().height) {
				setCenter(new Point(pnt.x, 1));

			} else if (pnt.y < 0) {
				setCenter(new Point(pnt.x, getDim().height - 1));
			} else {

				setCenter(new Point((int) dX, (int) dY));
			}
			
			setChanged();
			notifyObservers();
		}
	} //end move

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		if (hp > 100) {
			hp = 100;
		}
		if (hp < 0) {
			hp = 0;
		}
		this.hp = hp;
		setChanged();
		notifyObservers();
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		if (mp > 100) {
			mp = 100;
		}
		if(mp < 0){
			mp = 0;
		}
		this.mp = mp;
		setChanged();
		notifyObservers();
	}
	
	public void setDirection(Directioin directioin){
		if (directioin == Directioin.RIGHT) {
			bFacingRight = true;
		}
	}
	
	public double getStrength() {
		return strength;
	}


	public void setStrength(double strength) {
		this.strength = strength;
	}


	public double getArmour() {
		return armour;
	}


	public void setArmour(double armour) {
		this.armour = armour;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void moveLeft() {
		if (bJump) {
			return;
		}
		bFacingRight = false;
		bMoving = true;
		setOrientation(Directioin.LEFT.getDegree());
	}

	public void moveRight() {
		System.out.println(bJump);
		if (bJump) {
			return;
		}
		bFacingRight = true;
		bMoving = true;
		setOrientation(Directioin.RIGHT.getDegree());
	}

	public void stopMoving(){
		if (bJump) {
			return;
		}
		setDeltaX(0);
		setDeltaY(0);
		bMoving = false;
	}

	public void jump() {
		if (!bJump) {
			jumpUpCount = 0;
			jumpDownCount = 0;
			setOrientation(Directioin.UP.getDegree());
			bJump = true;
			bMoving = true;
		}
	}
	
	public boolean isMoving(){
		return bMoving;
	}
	
	public boolean isFacingRight(){
		return bFacingRight;
	}
	
	public void squatOn(){
		bSquat = true;
	}
	public void squatOff(){
		bSquat = false;
	}
	
	public boolean isSquat(){
		return bSquat;
	}

	public void defendOn() {
		bDefend = true;
	}
	
	public void defendOff(){
		bDefend = false;
	}
	
	public boolean isDefend(){
		return bDefend;
	}

	public void tick(){
		fadeInOut();
	}

	public void fadeInOut() {
		if (getProtected()) {
			setFadeValue(getFadeValue() + 3);
		}
		if (getFadeValue() == 255) {
			setProtected(false);
		}
	}
	
	public void setProtected(boolean bParam) {
		if (bParam) {
			setFadeValue(0);
		}
		bProtected = bParam;
	}

	public void setProtected(boolean bParam, int n) {
		if (bParam && n % 3 == 0) {
			setFadeValue(n);
		} else if (bParam) {
			setFadeValue(0);
		}
		bProtected = bParam;
	}	
	public void takeAttack(int attack){
		System.out.println("attack is " + attack);
		int realAttack = (int)(Math.pow(0.9, armour) * attack);
		System.out.println("after armour attack is " + realAttack);
		setHp(hp - realAttack);
	}

	public boolean getProtected() {return bProtected;}
	public void setShield(int n) {nShield = n;}
	public int getShield() {return nShield;}	
	
} //end class
