package edu.uchicago.cs.OO.model;

public enum Directioin {
	UP("up", 270),DOWN("down", 90),LEFT("left", 180),RIGHT("right", 0);
	private int degree;
	private String desc;
	private Directioin(String desc, int degree){
		this.degree = degree;
		this.desc = desc;
	}
	public int getDegree(){
		return degree;
	}
	public String getDesc(){
		return desc;
	}
}
