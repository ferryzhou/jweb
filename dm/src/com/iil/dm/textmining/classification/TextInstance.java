package com.iil.dm.textmining.classification;

import com.iil.dm.core.*;

import com.iil.dm.textmining.preparation.*;

public class TextInstance implements Instance{
	
	public TextInstance(DocTermFrequence dtf) {
		this.dtf = dtf;
	}
	
	public DocTermFrequence getDocTermFrequence() {
		return dtf;
	}
	
	/**
     * Term - Frequence
     */
	private DocTermFrequence dtf;
}

