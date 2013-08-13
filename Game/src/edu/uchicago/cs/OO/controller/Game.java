package edu.uchicago.cs.OO.controller;

import sun.audio.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sound.sampled.Clip;

import edu.uchicago.cs.OO.model.*;
import edu.uchicago.cs.OO.sounds.Sound;
import edu.uchicago.cs.OO.view.*;

// ===============================================
// == This Game class is the CONTROLLER
// ===============================================

public class Game implements Runnable, KeyListener {

	// ===============================================
	// FIELDS
	// ===============================================

	public static final Dimension DIM = new Dimension(1200, 700); //the dimension of the game.
	private GamePanel gmpPanel;
	private Utility util;
	public static Random R = new Random();
	public final static int ANI_DELAY = 45; // milliseconds between screen
											// updates (animation)
	private Thread thrAnim;
	private Thread aIThread;
	private int nLevel = 1;
	private static int nTick = 0;
	private ArrayList<Tuple> tupMarkForRemovals;
	private ArrayList<Tuple> tupMarkForAdds;
	private boolean bMuted = true;
	

	private final int PAUSE = 80, // p key
			QUIT = 81, // q key
			LEFT = 37, // rotate left; left arrow
			RIGHT = 39, // rotate right; right arrow
			UP = 38, // thrust; up arrow
			START = 83, // s key
			FIRE = 32, // space key
			MUTE = 77, // m-key mute
			DOWN = 40, // back; down arrow
			SHIELD = 65, // a key arrow
			FIST = 87,// W key
			KICK = 69,//E key

			// for possible future use
			 HYPER = 68, // d key
			// NUM_ENTER = 10, // hyp
			SPECIAL = 70; // fire special weapon; F key

	private Clip clpThrust;
	private Clip clpMusicBackground;

	private static final int SPAWN_NEW_SHIP_FLOATER = 1200;



	// ===============================================
	// ==CONSTRUCTOR
	// ===============================================

	public Game() {

		gmpPanel = new GamePanel(DIM);
		gmpPanel.addKeyListener(this);
		gmpPanel.setDelay(ANI_DELAY);
		util = new Utility();
		util.setGmpPanel(gmpPanel);

		clpThrust = Sound.clipForLoopFactory("whitenoise.wav");
		clpMusicBackground = Sound.clipForLoopFactory("music-background.wav");
	

	}

	// ===============================================
	// ==METHODS
	// ===============================================

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() { // uses the Event dispatch thread from Java 5 (refactored)
					public void run() {
						try {
							Game game = new Game(); // construct itself
							game.fireUpAnimThread();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

	private void fireUpAnimThread() { // called initially
		if (thrAnim == null) {
			thrAnim = new Thread(this); // pass the thread a runnable object (this)
			thrAnim.start();
		}
		if (aIThread == null) {
			aIThread = new Thread(new AIFighter(util));
			aIThread.start();
		}
	}

	// implements runnable - must have run method
	public void run() {

		// lower this thread's priority; let the "main" aka 'Event Dispatch'
		// thread do what it needs to do first
		thrAnim.setPriority(Thread.MIN_PRIORITY);

		// and get the current time
		long lStartTime = System.currentTimeMillis();

		// this thread animates the scene
		while (Thread.currentThread() == thrAnim) {
			tick();
			util.spawnNewFloater(gmpPanel);
			gmpPanel.update(gmpPanel.getGraphics()); // update takes the graphics context we must 
														// surround the sleep() in a try/catch block
														// this simply controls delay time between 
														// the frames of the animation
			//we update the models here
			util.iterateMovables(CommandCenter.getInstance().playerAttacks,
					CommandCenter.getInstance().opponentAttacks, 
					CommandCenter.getInstance().players,
					CommandCenter.getInstance().opponents,
					CommandCenter.getInstance().floaters);

			//this might be a good place to check for collisions
			util.checkCollisions();

			try {
				// The total amount of time is guaranteed to be at least ANI_DELAY long.  If processing (update) 
				// between frames takes longer than ANI_DELAY, then the difference between lStartTime - 
				// System.currentTimeMillis() will be negative, then zero will be the sleep time
				lStartTime += ANI_DELAY;
				Thread.sleep(Math.max(0,
						lStartTime - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				// just skip this frame -- no big deal
				continue;
			}
		} // end while
	} // end run

	//some methods for timing events in the game,
	//such as the appearance of UFOs, floaters (power-ups), etc. 
	public void tick() {
		if (nTick == Integer.MAX_VALUE)
			nTick = 0;
		else
			nTick++;
	}

	public static int getTick() {
		return nTick;
	}



	// Called when user presses 's'
	private void startGame() {
		CommandCenter.getInstance().clearAll();
		CommandCenter.getInstance().initGame(gmpPanel);
		CommandCenter.getInstance().setPlaying(true);
		CommandCenter.getInstance().setPaused(false);
		//if (!bMuted)
		   // clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
	}

	
	
	//we do the move the asteroid and others here so the viewer do not change the state of model
	//for each movable array, process it.


	// ===============================================
	// KEYLISTENER METHODS
	// ===============================================

	@Override
	public void keyPressed(KeyEvent e) {
		Fighter fighter = CommandCenter.getInstance().getPlayer();
		int nKey = e.getKeyCode();
		// System.out.println(nKey);

		if (nKey == START && !CommandCenter.getInstance().isPlaying())
			startGame();

		if (fighter != null) {

			switch (nKey) {
			case PAUSE:
				CommandCenter.getInstance().setPaused(!CommandCenter.getInstance().isPaused());
				if (CommandCenter.getInstance().isPaused())
					util.stopLoopingSounds(clpMusicBackground, clpThrust);
				else
					clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
				break;
			case QUIT:
				System.exit(0);
				break;
			case UP:
				fighter.jump();
				if (!CommandCenter.getInstance().isPaused())
					clpThrust.loop(Clip.LOOP_CONTINUOUSLY);
				break;
			case LEFT:
				fighter.moveLeft();
				break;
			case RIGHT:
				fighter.moveRight();
				break;
			case DOWN:
				fighter.squatOn();
				break;

			// possible future use
			// case KILL:
			// case SHIELD:
			// case NUM_ENTER:

			default:
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Fighter fighter = CommandCenter.getInstance().getPlayer();
		int nKey = e.getKeyCode();
		 System.out.println(nKey);

		if (fighter != null) {
			switch (nKey) {
			case FIRE:
				util.bulletAttack(fighter);
				Sound.playSound("laser.wav");
				break;
				
			//special is a special weapon, current it just fires the cruise missile. 
			case SPECIAL:
				util.specialAttack(fighter);
				break;
				
			case LEFT:
				fighter.stopMoving();
				break;
			case RIGHT:
				fighter.stopMoving();
				break;
			case UP:
				clpThrust.stop();
				break;
			case DOWN:
				fighter.squatOff();
				break;
			case FIST:
				util.fistAttack(fighter);
				break;
			case KICK:
				util.kickAttack(fighter);
				break;
				
			case MUTE:
				if (!bMuted){
					util.stopLoopingSounds(clpMusicBackground);
					bMuted = !bMuted;
				} 
				else {
					clpMusicBackground.loop(Clip.LOOP_CONTINUOUSLY);
					bMuted = !bMuted;
				}
				break;
				
				
			default:
				break;
			}
		}
	}

	@Override
	// Just need it b/c of KeyListener implementation
	public void keyTyped(KeyEvent e) {
	}
	

	
}


