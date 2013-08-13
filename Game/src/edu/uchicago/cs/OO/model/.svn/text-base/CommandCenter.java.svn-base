package edu.uchicago.cs.OO.model;

import java.awt.Color;
import java.awt.Point;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.uchicago.cs.OO.controller.FighterGenerator;
import edu.uchicago.cs.OO.controller.Game;
import edu.uchicago.cs.OO.sounds.Sound;
import edu.uchicago.cs.OO.view.BarView;
import edu.uchicago.cs.OO.view.FighterView;
import edu.uchicago.cs.OO.view.GameStatusCountView;
import edu.uchicago.cs.OO.view.GroundLineView;
import edu.uchicago.cs.OO.view.View;

// I only want one Command Center and therefore this is a perfect candidate for static
// Able to get access to methods and my movMovables ArrayList from the static context.
public class CommandCenter {

	private long lScore;
	private boolean bPlaying;
	private boolean bPaused;
	
	// These ArrayLists are thread-safe
	public CopyOnWriteArrayList<Movable> playerAttacks = new CopyOnWriteArrayList<Movable>();
	public CopyOnWriteArrayList<Movable> players = new CopyOnWriteArrayList<Movable>();
	public CopyOnWriteArrayList<Movable> opponents = new CopyOnWriteArrayList<Movable>();
	public CopyOnWriteArrayList<Movable> opponentAttacks = new CopyOnWriteArrayList<Movable>();
	public CopyOnWriteArrayList<Movable> floaters = new CopyOnWriteArrayList<Movable>();
	public CopyOnWriteArrayList<StaticObject> staticObjects = new CopyOnWriteArrayList<StaticObject>();
	public CopyOnWriteArrayList<View> dynamicViews = new CopyOnWriteArrayList<View>();
	public CopyOnWriteArrayList<View> staticViews = new CopyOnWriteArrayList<View>();
	
	private static CommandCenter instance = null;

	// Constructor made private - static Utility class only
	private CommandCenter() {}
	
	public static synchronized CommandCenter getInstance(){
		if (instance == null) {
			instance = new CommandCenter();
			}
		return instance;
	}
	
	public synchronized void initGame(Observer observer){
		spawnBackground(observer);
		spawnPlayers(true, observer);
	}
	
	private synchronized void spawnBackground(Observer observer){
		GroundLineView groundLineView = new GroundLineView();
		groundLineView.setPosition(Game.DIM.height * 2 / 3);
		staticViews.add(groundLineView);
	}
	// The parameter is true if this is for the beginning of the game, otherwise false
	// When you spawn a new falcon, you need to decrement its number
	/**
	 * @param bFirst
	 * @param observer
	 */
	public synchronized void spawnPlayers(boolean bFirst, Observer observer) {
		GroundLineView gView = null;
		for(View v: staticViews){
			if (v instanceof GroundLineView) {
				gView = (GroundLineView)v;
			}
		}

		
		if (gView == null) {
			return;
		}
		if (bFirst) {
			//we should bind the view to the model here
			Fighter player = FighterGenerator.getFighter();
			int player_x = Game.DIM.width/3;
			int player_y = gView.getPosition() - player.getRadius();
			player.setCenter(new Point(player_x, player_y));
			player.addObserver(observer);
			player.setDirection(Directioin.RIGHT);
			players.add(player);
			//generate the HP bar and MP bar
			Bar playerHPBar = new Bar("HP", player);
			BarView playerHPBarView = new BarView();
			playerHPBarView.setModel(playerHPBar);
			staticObjects.add(playerHPBar);
			staticViews.add(playerHPBarView);
			Bar playerMPBar = new Bar("MP", player);
			BarView playerMPBarView = new BarView();
			playerMPBarView.setModel(playerMPBar);
			staticObjects.add(playerMPBar);
			staticViews.add(playerMPBarView);
			//generate total game status
			GameStatusCount playerStatus = new GameStatusCount(player) ;
			GameStatusCountView playerCountView = new GameStatusCountView();
			playerCountView.setModel(playerStatus);
			staticObjects.add(playerStatus);
			staticViews.add(playerCountView);
			
			//generate opponent
			
			Fighter opponent = FighterGenerator.getFighter();
			int opponent_x = Game.DIM.width * 2 / 3;
			int opponent_y = gView.getPosition() - opponent.getRadius();
			opponent.setCenter(new Point(opponent_x, opponent_y));
			opponent.addObserver(observer);
			opponents.add(opponent);
			//generate the HP bar and MP bar
			Bar opponentHPBar = new Bar("HP", opponent);
			BarView opponentHPBarView = new BarView();
			opponentHPBarView.setModel(opponentHPBar);
			staticObjects.add(opponentHPBar);
			staticViews.add(opponentHPBarView);
			Bar opponentMPBar = new Bar("MP", opponent);
			BarView opponentMPBarView = new BarView();
			opponentMPBarView.setModel(opponentMPBar);
			staticObjects.add(opponentMPBar);
			staticViews.add(opponentMPBarView);
			//generate total game status
			GameStatusCount opponentStatus = new GameStatusCount(opponent) ;
			GameStatusCountView opponentCountView = new GameStatusCountView();
			opponentCountView.setModel(opponentStatus);
			staticObjects.add(opponentStatus);
			staticViews.add(opponentCountView);
		}
		else{
			Fighter player = getPlayer();
			Fighter opponent = getOpponent();
			player.init();
			opponent.init();
			int player_x = Game.DIM.width/3;
			int player_y = gView.getPosition() - player.getRadius();
			player.setCenter(new Point(player_x, player_y));
			int opponent_x = Game.DIM.width * 2 / 3;
			int opponent_y = gView.getPosition() - opponent.getRadius();
			opponent.setCenter(new Point(opponent_x, opponent_y));
		}
		
		Sound.playSound("shipspawn.wav");

	}
	
	public synchronized void clearAll(){
		playerAttacks.clear();
		opponentAttacks.clear();
		floaters.clear();
		dynamicViews.clear();
		staticViews.clear();
		players.clear();
		opponents.clear();
		staticObjects.clear();
	}

	public synchronized boolean isPlaying() {
		return bPlaying;
	}

	public synchronized void setPlaying(boolean bPlaying) {
		this.bPlaying = bPlaying;
	}

	public synchronized boolean isPaused() {
		return bPaused;
	}

	public synchronized void setPaused(boolean bPaused) {
		this.bPaused = bPaused;
	}
	
	public synchronized boolean isGameOver() {		//if the number of falcons is zero, then game over
		for (StaticObject staticObject : staticObjects) {
			if (staticObject instanceof GameStatusCount) {
				GameStatusCount status = (GameStatusCount)staticObject;
				return status.isFinished();
			}
		}
		return false;
	}
	
	public synchronized Fighter getPlayer(){
		if (players.isEmpty()) {
			return null;
		}
		return (Fighter)players.get(0);
	}
	
	public synchronized Fighter getOpponent(){
		if (opponents.isEmpty()) {
			return null;
		}
		return (Fighter)opponents.get(0);
	}
	
	
	public synchronized View getViewForModel(Model model){
		for (View view: dynamicViews) {
			if (view.getModel() == model) {
				return view;
			}
		}
		for (View  view : staticViews) {
			if (view.getModel() == model) {
				return view;
			}
		}
		return null;
	}

	
	
}
