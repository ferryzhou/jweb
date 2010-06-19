package com.iil.word.impl_seg;

import java.util.*;
import java.io.*;

public class WordWatcher {
	
	private static final int PATTERN_MAX_LEN = 10;
	private static final int PATTERN_MIN_LEN = 2;
	
	public void addString(String s) {
		for (int len = PATTERN_MIN_LEN; len <= PATTERN_MAX_LEN; len++) {
			for (int i = 0; i <= s.length() - len; i++) {
				addWord(s.substring(i, i + len).trim());
			}
		}
	}

	private void addWord(String s) {
		Int freq = stringFrequences.get(s);
		if (freq == null) {
			stringFrequences.put(s, new Int(1));
		} else freq.increase();
	}
	
	private List<Map.Entry> getSortedEntries() {
		List<Map.Entry> l = new LinkedList<Map.Entry>(stringFrequences.entrySet());
		return freqSortEntries(l);
	}
	
	private List<Map.Entry> freqSortEntries(List<Map.Entry> l) {
		Collections.sort(l, (new Comparator() {
				public int compare(Object o1, Object o2) {
					Int v1 = (Int)((Map.Entry)o1).getValue();
					Int v2 = (Int)((Map.Entry)o2).getValue();
					return v1.value - v2.value;
				}
			}));

		Collections.reverse(l);
		return l;
	}
	
	private List<Map.Entry> filter(List<Map.Entry> entries, int minFreq) {
		List<Map.Entry> newEntries = new ArrayList<Map.Entry>();
		for (Map.Entry entry : entries) {
			int v = ((Int)entry.getValue()).value;
			if (v < minFreq) return newEntries;
			else newEntries.add(entry);
		}
		return newEntries;
	}
	
	private void stringLengthSortEntries(List<Map.Entry> l) {
		Collections.sort(l, (new Comparator() {
				public int compare(Object o1, Object o2) {
					int v1 = ((String)((Map.Entry)o1).getKey()).length();
					int v2 = ((String)((Map.Entry)o2).getKey()).length();
					return v1 - v2;
				}
			}));

		Collections.reverse(l);	
	}
	
	private void adjustFrequence(List<Map.Entry> entries) {
		for (Map.Entry entry : entries) {
			String s = (String)entry.getKey();
			List<String> ss = getSubStrings(s);
			int v = ((Int)stringFrequences.get(s)).value;
			if (v <= 0) continue;
			for (String ts : ss) {
				Int tv = stringFrequences.get(ts);
				if (tv == null) {
					//System.out.println (ts + " not found");
					continue;
				}
				tv.value -= v;
			}
		}
	}
	
	private List<String> getSubStrings(String s) {
		List<String> ss = new ArrayList<String>();
		for (int len = PATTERN_MIN_LEN; len <= Math.min(PATTERN_MAX_LEN, s.length() - 1); len++) {
			for (int i = 0; i <= s.length() - len; i++) {
				ss.add(s.substring(i, i + len).trim());
			}
		}	
		return ss;	
	}
	
	public static WordWatcher getInstance() {
		if (instance == null) {
			instance = new WordWatcher();
		}
		return instance;
	}
	
	private static WordWatcher instance;
	
	public static class Int {
		
		Int(int v) {
			this.value = v;
		}
		
		public void increase() {
			value++;
		}
		
		public String toString() {
			return value + "";
		}
		
		public int getValue() {
			return value;
		}
		int value = 0;
	}

	private Map<String, Int> stringFrequences = new TreeMap<String, Int>();
	
	public List<Map.Entry> filterWords(int minInitial, int minFinal) {
    	List<Map.Entry> list = filter(getSortedEntries(), minInitial);
    	stringLengthSortEntries(list);
    	adjustFrequence(list);
    	freqSortEntries(list);
		return filter(list, minFinal);
	}
	
	public List<Map.Entry> filterWords() {
		return filterWords(MIN_INITIAL_FREQUENCE, MIN_FINAL_FREQUENCE);
	}
	
	private static final int MIN_INITIAL_FREQUENCE = 20;
	private static final int MIN_FINAL_FREQUENCE = 10;
	
	public static void main(String[] args) throws Exception{
		final WordWatcher ww = new WordWatcher();
		//String input = "../data/internet_titles.txt";
		String input = "../data/temp1.txt";
    	Util.forEachLineInFile(input, new Util.LineProcessor() {
			public void process(String line) {
				ww.addString(line);
			}
    	});
    	
    	List<Map.Entry> list = ww.filter(ww.getSortedEntries(), 5);
    	ww.stringLengthSortEntries(list);
    	ww.adjustFrequence(list);
    	ww.freqSortEntries(list);
    	String output = "../temp/temp1_ww_0.txt";
    	BufferedWriter bw = new BufferedWriter (new FileWriter(output));
    	for (Map.Entry entry : list) {
    		bw.write(entry.getKey() + " : " + entry.getValue());
    		bw.newLine();
    	}
    	bw.close();
    	
    	Integer t = 1;
    	t.valueOf(2);
    	System.out.println (t);
    }
}
