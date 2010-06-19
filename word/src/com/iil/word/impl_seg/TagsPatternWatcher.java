package com.iil.word.impl_seg;

import java.util.*;
import java.io.*;

class TagsPatternWatcher {

	private Map<Set<String>, Int> patternFrequences = new HashMap<Set<String>, Int>();
	
	private static final int PATTERN_MAX_LEN = 10;
	private static final int PATTERN_MIN_LEN = 1;

	public void addTagList(Set<String> tags) {
		List<String> tl = new ArrayList<String>(tags);
		for (int len = PATTERN_MIN_LEN; len <= Math.min(PATTERN_MAX_LEN, tags.size()); len++) {
			for (int i = 0; i <= tags.size() - len; i++) {
				addPattern(new TreeSet(tl.subList(i, i + len)));
			}
		}
	}

	private void addPattern(Set<String> pattern) {
		//System.out.println ("add pattern: " + pattern);
		Int freq = patternFrequences.get(pattern);
		if (freq == null) {
			patternFrequences.put(pattern, new Int(1));
		} else freq.increase();
	}
	
	private List<Map.Entry> getSortedEntries() {
		List<Map.Entry> l = new LinkedList<Map.Entry>(patternFrequences.entrySet());
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
	
	private void patternLengthSortEntries(List<Map.Entry> l) {
		Collections.sort(l, (new Comparator() {
				public int compare(Object o1, Object o2) {
					int v1 = ((Set<String>)((Map.Entry)o1).getKey()).size();
					int v2 = ((Set<String>)((Map.Entry)o2).getKey()).size();
					return v1 - v2;
				}
			}));

		Collections.reverse(l);	
	}
	
	private void adjustFrequence(List<Map.Entry> entries) {
		for (Map.Entry entry : entries) {
			Set<String> s = (Set<String>)entry.getKey();
			List<Set<String>> ss = getSubPatterns(s);
			int v = ((Int)patternFrequences.get(s)).value;
			if (v <= 0) continue;
			for (Set<String> ts : ss) {
				Int tv = patternFrequences.get(ts);
				if (tv == null) {
					System.out.println (ts + " not found");
					continue;
				}
				tv.value -= v;
			}
		}
	}
	
	private List<Set<String>> getSubPatterns(Set<String> tags) {
		List<Set<String>> ss = new ArrayList<Set<String>>();
		List<String> tl = new ArrayList<String>(tags);
		for (int len = PATTERN_MIN_LEN; len <= Math.min(PATTERN_MAX_LEN, tags.size() - 1); len++) {
			for (int i = 0; i <= tags.size() - len; i++) {
				ss.add(new TreeSet(tl.subList(i, i + len)));
			}
		}
		return ss;	
	}
	
	public static TagsPatternWatcher getInstance() {
		if (instance == null) {
			instance = new TagsPatternWatcher();
		}
		return instance;
	}
	
	private static TagsPatternWatcher instance;
	
	private static class Int {
		
		Int(int v) {
			this.value = v;
		}
		
		public void increase() {
			value++;
		}
		
		public String toString() {
			return value + "";
		}
		int value = 0;
	}

	public List<Map.Entry> filterWords(int minInitial, int minFinal) {
    	List<Map.Entry> list = filter(getSortedEntries(), minInitial);
    	IOUtil.outEntries("../temp/after_filter_1.txt", list);
    	patternLengthSortEntries(list);
    	IOUtil.outEntries("../temp/patternLengthSortEntries.txt", list);
    	adjustFrequence(list);
    	IOUtil.outEntries("../temp/adjustFrequence.txt", list);
    	freqSortEntries(list);
    	IOUtil.outEntries("../temp/freqSortEntries.txt", list);
		return filter(list, minFinal);
	}
	
	public List<Map.Entry> filterWords() {
		return filterWords(MIN_INITIAL_FREQUENCE, MIN_FINAL_FREQUENCE);
	}
	
	private static final int MIN_INITIAL_FREQUENCE = 20;
	private static final int MIN_FINAL_FREQUENCE = 10;

	public static void main(String[] args) throws IOException{
		final TagsPatternWatcher tpw = new TagsPatternWatcher();
		final OneSegmenter os = new OneSegmenter("../dict/dict1.txt");

		//String input = "../data/internet_titles.txt";
		String input = "../data/temp1.txt";
    	Util.forEachLineInFile(input, new Util.LineProcessor() {
			public void process(String line) {
				tpw.addTagList(new TreeSet(os.getAllSubStrings(line.toLowerCase())));
			}
    	});

		//List<Map.Entry> entries = tpw.getSortedEntries();
		List<Map.Entry> entries = tpw.filterWords(2, 2);
    	String output = "../temp/it_tp2.txt";
    	BufferedWriter bw = new BufferedWriter (new FileWriter(output));
    	for (Map.Entry entry : entries) {
    		bw.write(entry.getKey() + " : " + entry.getValue());
    		bw.newLine();
    	}
    	bw.close();
    }
    
}
