package com.iil.word.impl_seg;

import java.util.*;
import java.io.*;

//import static com.iil.word.impl_seg.Util;
import static com.iil.word.impl_seg.EntityStringSegment.EntityType;

public class OneSegmenter {
	
	public OneSegmenter() {
		
	}
	
	public OneSegmenter(String filename) {
		loadDict(filename);
	}
	
	public OneSegmenter(WordDict dict) {
		this.dict = dict;
		css = new ChineseStringSegment(dict);
	}
	
	public void loadDict(String filename) {
		dict.load(filename);
		css = new ChineseStringSegment(dict);
	}
	
	public List<String> getAllSubStrings(String str) {
		List<StringSegment> sss = segment(str);
		List<String> strs = new ArrayList<String>();
		for (StringSegment ss : sss ){
			strs.add(ss.getString());
		}
		return strs;
	}
	
	public List<StringSegment> segment(String str) {
		List<StringSegment> words = new ArrayList<StringSegment>();
		ArticleStringSegment ass = new ArticleStringSegment(0, str);
    	ass.splitOnce();
    	//System.out.println ("ass size: " + ass.subSegments.size());
    	for (StringSegment ss : ass.subSegments) {
    		//System.out.println ("string segment : " + ss.getString());
    		if (ss instanceof SentenceStringSegment) {
		    	SentenceStringSegment sss = (SentenceStringSegment)ss;
		    	List<StringSegment> sslist = sss.aggregate();
		    	for (StringSegment tss : sslist) {
		    		//System.out.println ("tss: " + tss);
		    		EntityStringSegment ess = (EntityStringSegment)tss;
		    		if (ess.getEntityType() == EntityType.ENGLISH_WORD) {
		    			words.add(ess);
		    		} else if (ess.getEntityType() == EntityType.CHINESE_WORD) {
		    			words.addAll(css.split(ess.getString(), ess.getPosition()));
		    		}
		    	}
    		} else {
    			//System.out.println ("unknown characters: " + ss.getString());
    		}
    	}
    	return words;
	}
	
	public String print(List<StringSegment> sss) {
		StringBuffer sb = new StringBuffer();
		for (StringSegment ss : sss) {
			sb.append(ss.getString() + " ");
		}
		return sb.toString();	
	}
	
	private WordDict dict = WordDict.getInstance();
	private ChineseStringSegment css = null;
	
	public static void main(String[] args) throws IOException{
		OneSegmenter ww = new OneSegmenter();
		ww.loadDict("../dict/dict1.txt");
		
		String test = "百度CEO李彦宏:目前web2.0更应该称作web.1.1";
		System.out.println (ww.print(ww.segment(test)));
		
		WordWatcher watcher = new WordWatcher();
		WordWatcher watcher2 = new WordWatcher();
		
		String input = "../data/faxian_temp1.txt";
		String output = "../temp/faxian_temp1_segment.txt";
		//String input = "../data/jiyin.txt";
		//String output = "../temp/jiyin_segment.txt";
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(input));
    		BufferedWriter bw = new BufferedWriter(new FileWriter(output));
    		String line;
    		while ((line = br.readLine()) != null) {
    			System.out.println (line);
    			List<StringSegment> sss = ww.segment(line);
    			String seg = ww.print(sss);
    			bw.write(seg);
    			bw.newLine();
    			
    			for (StringSegment ss : sss) {
    				if (ss instanceof EntityStringSegment) {
    					watcher2.addString(ss.getString());
    					if (((EntityStringSegment)ss).getEntityType() == EntityStringSegment.EntityType.CHINESE_NON_WORD_STRING) {
    						watcher.addString(ss.getString());
    					} else {
    						//watcher2.addString(ss.getString());
    					}
    				}
    			}
    		}
    		br.close();
    		bw.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	outputWords("../temp/faxian_temp1_ww.txt", watcher.filterWords(2, 2));

    	outputWords("../temp/faxian_temp1_ww2.txt", watcher2.filterWords(2, 2));

    }
    
    public static void outputWords(String filename, List<Map.Entry> newWords) throws IOException{
    	//String outputww = "../temp/nba_ww.txt";
    	BufferedWriter bw = new BufferedWriter (new FileWriter(filename));
    	for (Map.Entry entry : newWords) {
    		bw.write(entry.getKey() + " : " + entry.getValue());
    		bw.newLine();
    	}
    	bw.close();    	
    }
  
}
