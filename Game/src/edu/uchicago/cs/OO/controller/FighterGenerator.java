package edu.uchicago.cs.OO.controller;

import java.util.HashMap;
import java.util.Random;

import edu.uchicago.cs.OO.model.CommandCenter;
import edu.uchicago.cs.OO.model.Fighter;
import edu.uchicago.cs.OO.model.Floater;
import edu.uchicago.cs.OO.view.View;

public class FighterGenerator {
	private static HashMap<String, String> fighterAndViews = new HashMap<String, String>();
	static{
		fighterAndViews.put("FighterJack", "FighterJackView");
		fighterAndViews.put("FighterJohn", "FighterJohnView");
		fighterAndViews.put("FighterLucy", "FighterLucyView");
	}
	public static Fighter getFighter(){
		Random random = new Random();
		int i = random.nextInt(fighterAndViews.size());
		try {
			String keyString = (String)fighterAndViews.keySet().toArray()[i];
			String className = "edu.uchicago.cs.OO.model." + keyString;
			System.out.println(className);
			Fighter m = (Fighter) Class.forName(className).newInstance();
			String viewClassName = "edu.uchicago.cs.OO.view." + fighterAndViews.get(keyString);
			System.out.println(viewClassName);
			View view = (View) Class.forName(viewClassName).newInstance();
			view.setModel(m);
			CommandCenter.getInstance().dynamicViews.add(view);
			return m;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static Fighter getFighter(String name){

		try {
			String className = "edu.uchicago.cs.OO.model." + name;
			System.out.println(className);
			Fighter m = (Fighter) Class.forName(className).newInstance();
			String viewClassName = "edu.uchicago.cs.OO.view." + fighterAndViews.get(name);
			View view = (View) Class.forName(viewClassName).newInstance();
			System.out.println(viewClassName);
			view.setModel(m);
			CommandCenter.getInstance().dynamicViews.add(view);
			return m;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
