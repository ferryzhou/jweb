package com.iil.dm.textmining.preparation;

public class TermFrequence {
	
	public TermFrequence(String term, int freq) {
		this.term = term;
		this.freq = freq;
	}
	
	public String getTerm() {
		return this.term;
	}
	
	public int getFreq() {
		return this.freq;
	}
	
	private String term;
	private int freq;
}