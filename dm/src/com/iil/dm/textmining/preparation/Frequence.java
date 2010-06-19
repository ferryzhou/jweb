package com.iil.dm.textmining.preparation;

public class Frequence implements Feature{

	public static final String NAME = "Frequence";
	
	public String getName() {
		return NAME;
	}
	
	public Frequence(int i) {
		this.freq = i;
	}

	public void addFrequence(int i) {
		freq += i;
	}

	public int getFrequence() {
		return freq;
	}
	
	public String toString() {
		return freq + "";
	}
	
	private int freq;
}
