package com.iil.word;

import java.util.*;

import com.iil.word.impl.*;

/**
 *
 */
public class ChineseWordSplitter implements WordSplitter {
	
	public ChineseWordSplitter(WordSegmenterImpl wsi) {
		this.wsi = wsi;
	}
	
	public void readString(String s) {
		words = wsi.segmentSentence(s.trim());
		fromIndex = 0;
	}
	
	public boolean hasMoreWords() {
		return fromIndex < words.size();
	}
	
	public String nextWord() {
		String s = (String)words.get(fromIndex);
		fromIndex++;
		return s;
	}

	private WordSegmenterImpl wsi;
	private List words;
	private int fromIndex = 0;
}
