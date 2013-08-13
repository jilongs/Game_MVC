package edu.uchicago.cs.OO.model;

import java.awt.Point;

import edu.uchicago.cs.OO.controller.Game;

public class GameStatusCount extends StaticObject {
	private Fighter owner;
	private int winCount;
	private int loseCount;
	private int tieCount;
	private final int FINISH_COUNT = 3;
	private String status;


	public GameStatusCount(Fighter fighter) {
		super();
		winCount = 0;
		loseCount = 0;
		tieCount = 0;
		status = "playing";
		owner = fighter;

		if (owner == CommandCenter.getInstance().getPlayer()) {
			setCenter(new Point(20, 120));
		} else {
			setCenter(new Point(Game.DIM.width / 2 + 20, 120));
		}
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public int getLoseCount() {
		return loseCount;
	}

	public void setLoseCount(int loseCount) {
		this.loseCount = loseCount;
	}

	public int getTieCount() {
		return tieCount;
	}

	public void setTieCount(int tieCount) {
		this.tieCount = tieCount;
	}

	public void addWin(){
		winCount += 1;
	}
	public void addLose(){
		loseCount +=1;
	}
	public void addTie(){
		tieCount += 1;
	}
	
	public boolean isFinished(){
		if (winCount > FINISH_COUNT / 2) {
			return true;
		}
		else if (loseCount > FINISH_COUNT / 2) {
			return true;
		}
		return winCount + tieCount + loseCount == FINISH_COUNT;
	}
	public Fighter getOwner() {
		return owner;
	}
	public void setOwner(Fighter owner) {
		this.owner = owner;
	}
}
