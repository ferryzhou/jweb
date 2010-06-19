package com.iil.word.impl_seg;

import java.util.*;
import java.io.*;

import com.iil.word.CharacterType;

public class WordDict {
	
	private WordDict() {
	}
	
	public static WordDict getInstance() {
		if (instance == null) {
			instance = new WordDict();
		}
		return instance;
	}
	
	public boolean contain(String s) {
		return dict.contains(s);
	}
	
	int scan (String blockString, int index) {
		if (index >= blockString.length()) return index;
		for (int i = Math.min(index + 7, blockString.length() - 1); i > index; i--) {
			String can = blockString.substring(index, i + 1);
			if (contain(can)) return i;
		}
		return index;
	}
	
	public void addWord(String word) {
		dict.add(word);
	}
	
	public void load(String filename) {
		try {
    		BufferedReader br = new BufferedReader(new FileReader(filename));
    		String line;
    		while ((line = br.readLine()) != null) {
    			addWord(line.trim());
    		}
    		br.close();
    		System.out.println ("load finished .....");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	private TreeSet<String> dict = new TreeSet<String>();
	
	private static WordDict instance = null;
	
	public static void main(String[] args) {
    	//processFile("../dict/拼音加加词库2005.txt", "../dict/dict1.txt");
    	WordDict dict = WordDict.getInstance();
    	dict.load("../dict/dict1.txt");
    	String test = "李开复被传离职 Google中国进退两难";
    	for (int i = 0; i < test.length(); i++) {
    		//System.out.println ("i: " + i);
    		int end = dict.scan(test, i);
    		if (end > i) {
    			System.out.println ("detected: " + test.substring(i, end + 1));
    		}
    	}
    }	
    
    public static void processFile(String filename, String outfile) {
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(filename));
    		BufferedWriter bw = new BufferedWriter(new FileWriter(outfile));
    		String line;
    		while ((line = br.readLine()) != null) {
    			StringBuffer sb = new StringBuffer();
    			for (int i = 0; i < line.length(); i++) {
    				char c = line.charAt(i);
    				if (CharacterType.CHINESE_CHARACTER.eval(c)) {
    					sb.append(c);
    				}
    			}
    			//System.out.println (line + " => " + sb.toString());
    			bw.write(sb.toString());
    			bw.newLine();
    		}
    		br.close();
    		bw.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}

/*
	int scan (String blockString, int index) {
		char c = blockString.charAt(index);
		List<String> matches = map.get(c);
		System.out.println (c + " matches: ");
		if (matches == null || matches.size() == 0) return index;
		System.out.println ("matches size: " + matches.size());
		if ()
		String target = new String(c + "");
		for (int i = 0; i < Math.min(matches.size(), 5); i++) {
			System.out.println (matches.get(i));
		}

		for (int i = 2; i < blockString.length() - index; i++) {
			String newTarget = blockString.substring(index, index + i);
			matches = searchMatches(target, matches);
			if (matches.size() == 0) {
				return target.length() + index - 1;
			} else {
				target = newTarget;
			}
		}
		return target.length() + index - 1;
	}
	
	List<String> searchMatches(String target, List<String> patterns) {
		List<String> matches = new ArrayList<String>();
		for (String pattern : patterns) {
			if (contains(target, pattern)) {
				matches.add(pattern);
			}
		}
		return matches;
	}
	
	//pattern contains target?
	boolean contains(String target, String pattern) {
		if (target.length() > pattern.length()) return false;
		for (int i = 0; i < target.length(); i++) {
			if (target.charAt(i) != pattern.charAt(i)) return false;
		}
		return true;
	}
	
	Map<Character, List<String>> map = new HashMap<Character, List<String>>();
	
	public void readWordList(List<String> words) {
		for (String word : words) {
			addWord(word);
		}		
	}
	
	public void addWord(String word) {
		if (word.length() == 0) return;
		if (word.length() == 1) {
			System.out.println ("length 1: " + word);
			return;
		}
		char c = word.charAt(0);
		List<String> sl = map.get(c);
		if (sl == null) {
			sl = new ArrayList<String>();
			map.put(c, sl);
		}
		sl.add(word);		
	}

 */