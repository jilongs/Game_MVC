package edu.uchicago.cs.OO.controller;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sound.sampled.Clip;

import sun.misc.Perf.GetPerfAction;

import edu.uchicago.cs.OO.model.*;
import edu.uchicago.cs.OO.sounds.Sound;
import edu.uchicago.cs.OO.view.*;

public class Utility {
	private GamePanel gmpPanel;

	public void iterateMovables(CopyOnWriteArrayList<Movable>...movMovz){
		for (CopyOnWriteArrayList<Movable> movMovs : movMovz) {
			for (Movable mov : movMovs) {
				mov.move();
				mov.expire();
			}
		}
		
	}
	

	// Varargs for stopping looping-music-clips
	public static void stopLoopingSounds(Clip... clpClips) {
		for (Clip clp : clpClips) {
			clp.stop();
		}
	}
	public void fistAttack(Fighter f){
		f.setMp(f.getMp() + 5);
		Fist fist = new Fist(f);
		FighterView fighterView = (FighterView)CommandCenter.getInstance().getViewForModel(f);
		FistView fistView = new FistView(fighterView);
		fistView.setModel(fist);
		fist.addObserver(gmpPanel);
		CommandCenter.getInstance().dynamicViews.add(fistView);
		if (f== CommandCenter.getInstance().getPlayer()) {
			CommandCenter.getInstance().playerAttacks.add(fist);
		}
		else{
			CommandCenter.getInstance().opponentAttacks.add(fist);
		}
	}
	
	public void kickAttack(Fighter f){
		f.setMp(f.getMp() + 10);
		Kick kick = new Kick(f);
		FighterView fighterView = (FighterView)CommandCenter.getInstance().getViewForModel(f);
		KickView kickView = new KickView(fighterView);
		kickView.setModel(kick);
		kick.addObserver(gmpPanel);
		CommandCenter.getInstance().dynamicViews.add(kickView);
		if (f== CommandCenter.getInstance().getPlayer()) {
			CommandCenter.getInstance().playerAttacks.add(kick);
		}
		else{
			CommandCenter.getInstance().opponentAttacks.add(kick);
		}
	}
	public void bulletAttack(Fighter f){
		Bullet bullet = new Bullet(f);
		BulletView bulletView = new BulletView();
		bullet.addObserver(gmpPanel);
		bulletView.setModel(bullet);
		CommandCenter.getInstance().dynamicViews.add(bulletView);
		if (f== CommandCenter.getInstance().getPlayer()) {
			CommandCenter.getInstance().playerAttacks.add(bullet);
		}
		else{
			CommandCenter.getInstance().opponentAttacks.add(bullet);
		}
	}
	
	public void specialAttack(Fighter f){
		if (f.getMp() < 100) {
			return;
		}
		Cruise cruise = new Cruise(f);
		CruiseView cruiseView = new CruiseView();
		cruise.addObserver(gmpPanel);
		cruiseView.setModel(cruise);
		CommandCenter.getInstance().dynamicViews.add(cruiseView);
		if (f== CommandCenter.getInstance().getPlayer()) {
			CommandCenter.getInstance().playerAttacks.add(cruise);
		}
		else{
			CommandCenter.getInstance().opponentAttacks.add(cruise);
		}
		f.setMp(0);
		Sound.playSound("laser.wav");
	}
	public void spawnNewFloater(GamePanel gmpPanel) {
		//make the appearance of power-up dependent upon ticks and levels
		//the higher the level the more frequent the appearance
//		if (nTick % (SPAWN_NEW_SHIP_FLOATER - nLevel * 7) == 0) {
		if(Game.getTick() % 100 == 0){
			Floater floater = FloaterGenerator.getFloater();
			floater.addObserver(gmpPanel);
			CommandCenter.getInstance().floaters.add(floater);
		}
	}
	public Fighter getWinner(){
		Fighter player = CommandCenter.getInstance().getPlayer();
		Fighter opponent = CommandCenter.getInstance().getOpponent();
		GameStatusCount playerStatus = getStatusFromFighter(player);
		GameStatusCount opponentStatus = getStatusFromFighter(opponent);
		if (playerStatus.getWinCount() > opponentStatus.getWinCount()) {
			return player;
		}
		else if (playerStatus.getWinCount() < opponentStatus.getWinCount()) {
			return opponent;
		}
		else{
			return null;
		}
	}
	private GameStatusCount getStatusFromFighter(Fighter fighter){
		for (StaticObject staticObject : CommandCenter.getInstance().staticObjects) {
			if (staticObject instanceof GameStatusCount) {
				GameStatusCount status = (GameStatusCount)staticObject;
				if (status.getOwner() == fighter) {
					return status;
				}
			}
		}
		return null;
	}
	private Fighter getOpFighter(Fighter fighter){
		if (fighter == CommandCenter.getInstance().getPlayer()) {
			return CommandCenter.getInstance().getOpponent();
		}
		else{
			return CommandCenter.getInstance().getPlayer();
		}
	}
	private void checkFloatersCollisions(Fighter fighter, CopyOnWriteArrayList<Movable> floaters){
		ArrayList<Tuple> tupMarkForRemovals = new ArrayList<Tuple>();
		ArrayList<Tuple> tupMarkForAdds = new ArrayList<Tuple>();
		Point fighterCenter = fighter.getCenter();
		int nRadiux = fighter.getRadius();
		Point attackCenter;
		int nAttackRadiux;
		for (Movable floater : floaters) {
			attackCenter = floater.getCenter();
			nAttackRadiux = floater.getRadius();

			//detect collision
			if (fighterCenter.distance(attackCenter) >= (nRadiux + nAttackRadiux)) {
				continue;
			}//end if 
			tupMarkForRemovals.add(new Tuple(floaters, floater));
			View view = CommandCenter.getInstance().getViewForModel((Model) floater);
			tupMarkForRemovals.add(new Tuple(CommandCenter.getInstance().dynamicViews, view));
			Sound.playSound("pacman_eatghost.wav");
			if (floater instanceof HealthPointFloater) {
				fighter.setHp(fighter.getHp() + ((HealthPointFloater)floater).getPoint());
				System.out.println("HP = " + fighter.getHp());
			}
			else if (floater instanceof ManaPointFloater) {
				fighter.setMp(fighter.getMp() + ((ManaPointFloater)floater).getPoint());
				System.out.println("MP = " + fighter.getMp());
			}
			else {
				
			}
			
		}//end inner for
		//remove these objects from their appropriate ArrayLists
		//this happens after the above iterations are done
		for (Tuple tup : tupMarkForRemovals) 
			tup.removeMovable();
		
		//add these objects to their appropriate ArrayLists
		//this happens after the above iterations are done
		for (Tuple tup : tupMarkForAdds) 
			tup.addMovable();

		//call garbage collection
		System.gc();
	}
	private void checkAttackCollisions(Fighter fighter, CopyOnWriteArrayList<Movable> attacks){
		ArrayList<Tuple> tupMarkForRemovals = new ArrayList<Tuple>();
		ArrayList<Tuple> tupMarkForAdds = new ArrayList<Tuple>();
		Point fighterCenter = fighter.getCenter();
		int nRadiux = fighter.getRadius();
		Point attackCenter;
		int nAttackRadiux;
		for (Movable attack : attacks) {
			attackCenter = attack.getCenter();
			nAttackRadiux = attack.getRadius();

			//detect collision
			if (fighterCenter.distance(attackCenter) < (nRadiux + nAttackRadiux)) {
				tupMarkForRemovals.add(new Tuple(attacks, attack));
				View view = CommandCenter.getInstance().getViewForModel((Model) attack);
				tupMarkForRemovals.add(new Tuple(CommandCenter.getInstance().dynamicViews, view));
				Sound.playSound("whitenoise.wav");
				fighter.takeAttack((int)((Attack)attack).getDamage());
				System.out.println("HP = " + fighter.getHp());

			}//end if 
		}//end inner for
		//remove these objects from their appropriate ArrayLists
		//this happens after the above iterations are done
		for (Tuple tup : tupMarkForRemovals) 
			tup.removeMovable();
		
		//add these objects to their appropriate ArrayLists
		//this happens after the above iterations are done
		for (Tuple tup : tupMarkForAdds) 
			tup.addMovable();

		//call garbage collection
		System.gc();
	}
	public void checkCollisions() {

		Fighter player = CommandCenter.getInstance().getPlayer();
		Fighter opponent = CommandCenter.getInstance().getOpponent();
		if (player == null || opponent == null) {
			return;
		}
		//check collisions between player and opponent's attacks
		checkAttackCollisions(player, CommandCenter.getInstance().opponentAttacks);
		//check collisions between opponent and player's attacks
		checkAttackCollisions(opponent, CommandCenter.getInstance().playerAttacks);

		//check for collisions between fighter and floaters
		checkFloatersCollisions(player, CommandCenter.getInstance().floaters);
		checkFloatersCollisions(opponent, CommandCenter.getInstance().floaters);
		
		if (player.getHp() == 0 && opponent.getHp() == 0) {
			GameStatusCount status = getStatusFromFighter(player);
			GameStatusCount opStatus = getStatusFromFighter(opponent);
			status.addTie();
			opStatus.addTie();
			CommandCenter.getInstance().spawnPlayers(false, gmpPanel);
		}
		else if (player.getHp() == 0) {
			GameStatusCount status = getStatusFromFighter(player);
			GameStatusCount opStatus = getStatusFromFighter(opponent);
			status.addLose();
			opStatus.addWin();
			CommandCenter.getInstance().spawnPlayers(false, gmpPanel);
		}
		else if (opponent.getHp() == 0) {
			GameStatusCount status = getStatusFromFighter(player);
			GameStatusCount opStatus = getStatusFromFighter(opponent);
			status.addWin();
			opStatus.addLose();
			CommandCenter.getInstance().spawnPlayers(false, gmpPanel);
		}

		
	}//end meth
	
	public GamePanel getGmpPanel() {
		return gmpPanel;
	}


	public void setGmpPanel(GamePanel gmpPanel) {
		this.gmpPanel = gmpPanel;
	}
}

//===============================================
//==A tuple takes a reference to an ArrayList and a reference to a Movable
//This class is used in the collision detection method, to avoid mutating the array list while we are iterating
//it has two public methods that either remove or add the movable from the appropriate ArrayList 
//===============================================

class Tuple{
	//this can be any one of several CopyOnWriteArrayList<Movable>
	private CopyOnWriteArrayList<Movable> movMovs;
	//this is the target movable object to remove
	private Movable movTarget;
	//this can be any one of several CopyOnWriteArrayList<Movable>
	private CopyOnWriteArrayList<View> views;
	//this is the target movable object to remove
	private View viewTarget;
	
	public Tuple(CopyOnWriteArrayList<Movable> movMovs, Movable movTarget) {
		this.movMovs = movMovs;
		this.movTarget = movTarget;
	}
	
	public Tuple(CopyOnWriteArrayList<View> views, View viewTarget) {
		this.views = views;
		this.viewTarget = viewTarget;
	}
	
	public void removeMovable(){
		if (movMovs != null) {
			movMovs.remove(movTarget);
		}
		else{
			views.remove(viewTarget);
		}
	}
	
	public void addMovable(){
		if (movMovs != null) {
			movMovs.add(movTarget);
		}
		else{
			views.add(viewTarget);
		}
		
	}


}
