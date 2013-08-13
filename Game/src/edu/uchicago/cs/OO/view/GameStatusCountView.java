package edu.uchicago.cs.OO.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Observable;

import edu.uchicago.cs.OO.controller.Game;
import edu.uchicago.cs.OO.model.Bar;
import edu.uchicago.cs.OO.model.CommandCenter;
import edu.uchicago.cs.OO.model.Directioin;
import edu.uchicago.cs.OO.model.Fighter;
import edu.uchicago.cs.OO.model.GameStatusCount;
import edu.uchicago.cs.OO.model.Model;

public class GameStatusCountView extends View {

	private GameStatusCount status;
	public GameStatusCountView() {
		super();
		// TODO Auto-generated constructor stub
		
	}
	public void setModel(Model model) {
		this.model = model;
		this.model.addObserver(this);
		status = (GameStatusCount) model;
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	// Draw the number of games this player has win next to the health point bar. 
	public void draw(Graphics g) {
		int cur = model.getCenter().x;
		int fontSize = 20;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(Color.YELLOW);
		for (int i = 0; i < status.getWinCount(); i++) {
			g.drawString("V", cur, model.getCenter().y);
			cur += 20;
		}
		g.setColor(Color.PINK);
		for (int i = 0; i < status.getTieCount(); i++) {
			g.drawString("T", cur, model.getCenter().y);
			cur += 20;
		}
	}
}
