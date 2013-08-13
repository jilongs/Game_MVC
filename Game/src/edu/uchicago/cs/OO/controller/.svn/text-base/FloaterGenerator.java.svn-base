package edu.uchicago.cs.OO.controller;

import java.util.*;

import edu.uchicago.cs.OO.model.*;
import edu.uchicago.cs.OO.view.View;

public class FloaterGenerator {
	private static HashMap<String, String> floaterAndViews = new HashMap<String, String>();
	static{
		floaterAndViews.put("HealthPointFloater", "HealthPointFloaterView");
		floaterAndViews.put("ManaPointFloater", "ManaPointFloaterView");
	}
	public static Floater getFloater(){
		Random random = new Random();
		int i = random.nextInt(floaterAndViews.size());
		try {
			String keyString = (String)floaterAndViews.keySet().toArray()[i];
			String className = "edu.uchicago.cs.OO.model." + keyString;
			System.out.println(className);
			Floater m = (Floater) Class.forName(className).newInstance();
			String viewClassName = "edu.uchicago.cs.OO.view." + floaterAndViews.get(keyString);
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
	public static Floater getFloater(String name){

		try {
			String className = "edu.uchicago.cs.OO.model." + name;
			System.out.println(className);
			Floater m = (Floater) Class.forName(className).newInstance();
			String viewClassName = "edu.uchicago.cs.OO.view." + floaterAndViews.get(name);
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
