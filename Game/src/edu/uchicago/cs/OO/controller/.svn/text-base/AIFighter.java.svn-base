package edu.uchicago.cs.OO.controller;

import edu.uchicago.cs.OO.model.CommandCenter;
import edu.uchicago.cs.OO.model.Fighter;

public class AIFighter implements Runnable{

	private Utility util;
	public AIFighter(Utility utility){
		util = utility;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

//		// and get the current time
//		long lStartTime = System.currentTimeMillis();
//
//		// this thread animates the scene
		
		while (true) {
			Fighter fighter = CommandCenter.getInstance().getOpponent();
			if(fighter == null){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			try {
				int move = Game.R.nextInt(9);
				switch (move) {
				case 0:
					fighter.jump();
					break;
				case 1:
					fighter.moveLeft();
					break;
				case 2:
					fighter.moveRight();
					break;
				case 3:
					fighter.squatOn();
					break;
				case 4:
					fighter.squatOff();
					break;
				case 5:
					util.bulletAttack(fighter);
					break;
				case 6:
					util.fistAttack(fighter);
					break;
				case 7:
					util.kickAttack(fighter);
					break;
				case 8:
					util.specialAttack(fighter);
					break;

				default:
					break;
				}
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// just skip this frame -- no big deal
				continue;
			}
		} // end while
	}

}
