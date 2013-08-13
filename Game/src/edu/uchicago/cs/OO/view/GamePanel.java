package edu.uchicago.cs.OO.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;


import edu.uchicago.cs.OO.controller.Game;
import edu.uchicago.cs.OO.controller.Utility;
import edu.uchicago.cs.OO.model.CommandCenter;
import edu.uchicago.cs.OO.model.Fighter;
import edu.uchicago.cs.OO.model.Movable;
import edu.uchicago.cs.OO.model.MovingObject;


 public class GamePanel extends Panel  implements Observer{
	
	// ==============================================================
	// FIELDS 
	// ============================================================== 
	 
	// The following "off" vars are used for the off-screen double-bufferred image. 
	private Dimension dimOff;
	private Image imgOff;
	private Graphics grpOff;
	
	private GameFrame gmf;
	private Font fnt = new Font("SansSerif", Font.BOLD, 12);
	private Font fntBig = new Font("SansSerif", Font.BOLD + Font.ITALIC, 36);
	private FontMetrics fmt; 
	private String message;
	private int nFontWidth;
	private int nFontHeight;
	private String strDisplay = "";
	private long timestamp = 0;
	private int delay = 0;
	

	// ==============================================================
	// CONSTRUCTOR 
	// ==============================================================
	
	public GamePanel(Dimension dim){
	    gmf = new GameFrame();
		gmf.getContentPane().add(this);
		gmf.pack();
		initView();
		
		gmf.setSize(dim);
		gmf.setTitle("Game Base");
		gmf.setResizable(false);
		gmf.setVisible(true);
		this.setFocusable(true);
		System.out.println("initialize game panel");
	}
	
	
	// ==============================================================
	// METHODS 
	// ==============================================================
	
	
	@SuppressWarnings("unchecked")
	public void update(Graphics g) {
		if (grpOff == null || Game.DIM.width != dimOff.width
				|| Game.DIM.height != dimOff.height) {
			dimOff = Game.DIM;
			imgOff = createImage(Game.DIM.width, Game.DIM.height);
			grpOff = imgOff.getGraphics();
		}
		// Fill in background with black.
		grpOff.setColor(Color.black);
		grpOff.fillRect(0, 0, Game.DIM.width, Game.DIM.height);
		
		if (!CommandCenter.getInstance().isPlaying()) {
			displayTextOnScreen();
		} else if (CommandCenter.getInstance().isPaused()) {
			strDisplay = "Game Paused";
			grpOff.drawString(strDisplay,
					(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);
		}
		
		//playing and not paused!
		else {
			//draw them in decreasing level of importance
			//friends will be on top layer and debris on the bottom
			iterateViews(grpOff, 
					CommandCenter.getInstance().staticViews,
					CommandCenter.getInstance().dynamicViews);
			
			if (CommandCenter.getInstance().isGameOver()) {
				Utility util = new Utility();
				util.setGmpPanel(this);
				Fighter winner = util.getWinner();
				if (winner == null) {
					message = "TIE";
				}
				else if (winner == CommandCenter.getInstance().getPlayer()) {
					message = "YOU WON";
				}
				else{
					message = "YOU LOSE";
				}
				CommandCenter.getInstance().setPlaying(false);
				//bPlaying = false;
			}
		}
		//draw the double-Buffered Image to the graphics context of the panel
		g.drawImage(imgOff, 0, 0, this);
	} 


	
	//for each movable array, process it.
	private void iterateViews(Graphics g,
			CopyOnWriteArrayList<View>... viewsList) {
		for (CopyOnWriteArrayList<View> views : viewsList) {
			for (View view : views) {
				view.draw(g);
			}
		}
	}
	


	
	private void initView() {
		Graphics g = getGraphics();			// get the graphics context for the panel
		g.setFont(fnt);						// take care of some simple font stuff
		fmt = g.getFontMetrics();
		nFontWidth = fmt.getMaxAdvance();
		nFontHeight = fmt.getHeight();
		g.setFont(fntBig);					// set font info
	}
	
	// This method draws some text to the middle of the screen before/after a game
	private void displayTextOnScreen() {
		if (message != null && !message.isEmpty()) {
			grpOff.setColor(Color.RED);
			grpOff.setFont(fnt);
			grpOff.drawString(message,
					(Game.DIM.width - fmt.stringWidth(message)) / 2, Game.DIM.height / 4 - 40);
		}
		grpOff.setColor(Color.white);
		grpOff.setFont(fnt);
		strDisplay = "GAME OVER";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4);

		strDisplay = "use the arrow keys to turn and thrust";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 40);

		strDisplay = "use the space bar to fire";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 80);

		strDisplay = "'S' to Start";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 120);

		strDisplay = "'P' to Pause";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 160);

		strDisplay = "'Q' to Quit";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 200);
		strDisplay = "left pinkie on 'A' for Shield";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 240);

		strDisplay = "left index finger on 'F' for Guided Missile";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 280);

		strDisplay = "'Numeric-Enter' for Hyperspace";
		grpOff.drawString(strDisplay,
				(Game.DIM.width - fmt.stringWidth(strDisplay)) / 2, Game.DIM.height / 4
						+ nFontHeight + 320);
	}
	
	public GameFrame getFrm() {return this.gmf;}
	public void setFrm(GameFrame frm) {this.gmf = frm;}


	//passing the panel refresh delay into this panel
	public void setDelay(int delay){
		this.delay = delay;
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		//doing this we don't have to refresh the panel after every single change
		if(System.currentTimeMillis() - timestamp >= delay){
			timestamp = System.currentTimeMillis();
			update(this.getGraphics());
		}
		
	}	
}