package com.iil.word.eval;

import java.util.*;

import com.iil.util.IO;

class WordSplitterEvaluator {
	
	public void eval(String ref, String response) {
		Set refIndexes = getSpaces(ref);
		Set resIndexes = getSpaces(response);
		prEval(refIndexes, resIndexes);
	}
	
    void prEval(Set refSet, Set responseSet) {
		
		int refSize = refSet.size();
		int resSize = responseSet.size();
		
		int p = 0;
		Iterator refIt = refSet.iterator();
		while (refIt.hasNext()) {
			if (responseSet.contains(refIt.next())) p++;
		}
		
		System.out.println ("p: " + p);
		precision = (double) p / resSize;
	
		int r = 0;
		Iterator respIt = responseSet.iterator();
		while (respIt.hasNext()) {
			if (refSet.contains(respIt.next())) r++;
		}
		recall = (double) r / refSize;
		System.out.println ("r: " + r);
		
		System.out.println ("f: " + 2 * precision * recall / (precision + recall));

    }
    
    static Set getSpaces(String xs) {
		HashSet breakSet = new HashSet();
		int index = 0;
		for (int i = 0; i < xs.length(); ++i)
		    if (xs.charAt(i) == ' ')
			breakSet.add(new Integer(index));
		    else
			++index;
		return breakSet;
    }
    
	private double precision;
	private double recall;
	private double fMeasure;
	
	public static void main(String[] args) throws Exception{
    	WordSplitterEvaluator wse = new WordSplitterEvaluator();
    	String ref = IO.getContent("C:/zj/research/nlp/word segmentation/resources/data/back/pk-testref.txt");
    	String rs = IO.getContent("C:/zj/research/nlp/word segmentation/resources/data/back/pk.out.segments");
    	wse.eval(ref, rs);
    	System.out.println ("precision: " + wse.precision);
    	System.out.println ("recall: " + wse.recall);
    }
}
