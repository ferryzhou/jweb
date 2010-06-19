package com.iil.dm.textmining.preparation;

public class Weight implements Feature{

	public static final String NAME = "Frequence";
	
	public String getName() {
		return NAME;
	}

	public Weight(double w) {
		this.weight = w;
	}
	
	public void setWeight(double w) {
		this.weight = w;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public String toString() {
		return weight + "";
	}
	
	private double weight;
}
