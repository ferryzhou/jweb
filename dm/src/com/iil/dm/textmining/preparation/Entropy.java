package com.iil.dm.textmining.preparation;

public class Entropy extends Weight implements Feature{

	private static final String name = "Entropy";

	public Entropy(double w) {
		super(w);
	}
	
	public String getName() {
		return name;
	}
}