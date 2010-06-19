package com.iil.word.impl_seg;

import java.io.*;
import java.util.*;

public class ContentAnalysis {
	
	public ContentAnalysis(OneSegmenter seger, int minFreq) {
		this.ww = seger;
		this.minFreq = minFreq;
	}
	
	public List<Map.Entry> getWordCount(Reader r) throws IOException{
		WordWatcher watcher = new WordWatcher();
    	try {
    		BufferedReader br = new BufferedReader(r);
    		String line;
    		while ((line = br.readLine()) != null) {
    			//System.out.println (line);
    			List<StringSegment> sss = ww.segment(line);
    			
    			for (StringSegment ss : sss) {
    				if (ss instanceof EntityStringSegment) {
    					watcher.addString(ss.getString());
    				}
    			}
    		}
    		br.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	return watcher.filterWords(2, minFreq);
		
	}
	
	private OneSegmenter ww;
	private int minFreq;
	
	private List<String> newWords = new ArrayList<String>();
}
