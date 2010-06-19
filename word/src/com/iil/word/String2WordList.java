package com.iil.word;

import java.util.*;

class String2WordList {
	
	static public List<String> getWords(String s, WordSplitter ws) {
		
		List<String> wordLists = new ArrayList<String>();
		ws.readString(s);
		while (ws.hasMoreWords()) {
			wordLists.add(ws.nextWord());
		}
		return wordLists;
	}
}
