package com.iil.word;

import java.util.*;

/**
 * use SimpleWordSplitter, filter out non-english words and stop words such as "of";
 *
 */
public class EnglishWordSplitter implements WordSplitter {
	
	public EnglishWordSplitter(List stoplist) {
		this.stoplist = stoplist;
	}
	
	public void readString(String s) {
		englishWords = new LinkedList();
		sws.readString(s);
		while (sws.hasMoreWords()) {
			String word = sws.nextWord();
			if (accept(word)) {
				englishWords.add(word);
			}
		}
		iter = englishWords.iterator();
	}
	
	public boolean hasMoreWords() {
		return iter.hasNext();
	}
	
	/**
     * 
     */
	public String nextWord() {
		return stem((String)iter.next());
	}
	
	private String stem(String word) {
		Stemmer stemmer = new Stemmer();
		String w = word.toLowerCase();
		for (int i = 0; i<word.length(); i++) {
			stemmer.add(w.charAt(i));
		}
		stemmer.stem();
		return stemmer.toString();
	}

	private boolean isEnglishWord(String word) {
		return sws.isLetter(word.charAt(0));
	}
	
	private boolean isStopWord(String word) {
		return stoplist.contains(word);
	}
	
	private boolean accept(String word) {
		return isEnglishWord(word) && !isStopWord(word);
	}
	
	private SimpleWordSplitter sws = new SimpleWordSplitter();
	private List englishWords;
	private Iterator iter;
	private List stoplist;
}
