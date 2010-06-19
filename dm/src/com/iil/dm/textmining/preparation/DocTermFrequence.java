package com.iil.dm.textmining.preparation;

import java.io.*;
import java.util.*;

import com.iil.word.*;
/**
 * 
 */
public class DocTermFrequence {
	
	public static DocTermFrequence createFromReader(String docid, Reader r) throws IOException{
		return createFromReader(docid, r, new SimpleWordSplitter());
	}
	
	public static DocTermFrequence createFromReader(String docid, Reader r, WordSplitter ws) throws IOException{
		BufferedReader br = new BufferedReader(r);
		DocTermFrequence ti = new DocTermFrequence(docid);
		String line;
		while ((line = br.readLine()) != null) {
			ws.readString(line);
			while (ws.hasMoreWords()) {
				String word = ws.nextWord();
				ti.addWord(word);
			}
		}
		return ti;
	}
	
	public DocTermFrequence(String docid) {
		this.docid = docid;
	}
	
	public String getDocId() {
		return this.docid;
	}
	
	public Set getOccuredWords() {
		return wordsFrequence.keySet();
	}
	
	public int getWordFrequence(String word) {
		Frequence freq = (Frequence)wordsFrequence.get(word);
		if (freq != null) return freq.getFrequence();
		else return 0;
	}
	
	public double getWordProb(String word) {
		return (double)getWordFrequence(word)/getTotalWordsNum();
	}
	
	public int getTotalWordsNum() {
		if (totalWordsNum == -1) totalWordsNum = calcTotalWordsNum();
		return totalWordsNum;
	}
	
	public List getSortedEntries() {
		List l = getAllEntries();
		Collections.sort(l, (new Comparator() {
				public int compare(Object o1, Object o2) {
					int i1 = ((Frequence)((Map.Entry)o1).getValue()).getFrequence();
					int i2 = ((Frequence)((Map.Entry)o2).getValue()).getFrequence();
					return i1 - i2;
				}
			}));
		Collections.reverse(l);
		return l;
	}

	public List getAllEntries() {
		return new LinkedList(wordsFrequence.entrySet());
	}
	
	private int calcTotalWordsNum() {
		int sum = 0;
		Collection freqs = wordsFrequence.values();
		Iterator iter = freqs.iterator();
		while (iter.hasNext()) {
			int num = ((Frequence)iter.next()).getFrequence();
			sum += num;
		}
		return sum;
	}
	
	/**
	 * changed! Now the result is every word's frequence is 1, not the real freq.
	 * just for changed bayesian text classification algorithm.
	 * The normal code should be "addWord(word, 1)";
	 * @param word
	 */
	private void addWord(String word) {
//		addWord(word, 1);
		Frequence freq = (Frequence)wordsFrequence.get(word);
		if (freq == null) wordsFrequence.put(word, new Frequence(1));
	}
	
	public void addWord(String word, int num) {
		Frequence freq = (Frequence)wordsFrequence.get(word);
		if (freq == null) wordsFrequence.put(word, new Frequence(num));
		else freq.addFrequence(num);
	}
	
	public void addDocTermFrequence(DocTermFrequence ti) {
		Set words = ti.getOccuredWords();
		Iterator wIter = words.iterator();
		while (wIter.hasNext()) {
			String word = (String)wIter.next();
			this.addWord(word, ti.getWordFrequence(word));
		}
	}
	
	public DocFeatureModel getDocFeatureModel() {
		return new DocFeatureModel(docid, Frequence.NAME, wordsFrequence); 
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Iterator iter = getSortedEntries().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			sb.append(entry.getKey() + " -- " + entry.getValue());
			sb.append("\r\n");
		}
		sb.append("words num: " + wordsFrequence.size() + "\r\n"); 
		sb.append("total words: " + this.getTotalWordsNum()); 
		return sb.toString();
	}

	private Map wordsFrequence = new HashMap();
	private int totalWordsNum = -1;
	private String docid;
}
