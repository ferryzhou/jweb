package com.iil.word.impl_seg;

import java.util.*;

import com.iil.word.CharacterType;
import static com.iil.word.impl_seg.EntityStringSegment.EntityType;

class SentenceStringSegment extends StringSegment{

	public SentenceStringSegment(int pos, String s) {
		super(pos, s);
	}

	/**
     * => ENGLISH_WORD, NUMBER. 
     * ENGLISH_WORD [a-z|A-Z|a'-z'|A'-Z']+
     * NUMBER [ENGLISH_DIGIT | CHINESE_DIGIT | CHINESE_NUMBER_METRIC]+
     * 
     */
	public void splitOnce() {
		
	}
	
	public List<StringSegment> aggregate() {
		List<StringSegment> ss = new ArrayList<StringSegment>();
		if (str.length() == 0) return ss;
		StringBuffer sb = new StringBuffer();
		EntityType bufferType = getType(str.charAt(0)); 
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			EntityType type = getType(c);
			if (type == bufferType) {
				sb.append(c);
				continue;
			} else {
				ss.add(new EntityStringSegment(i - sb.length(), sb.toString(), bufferType));
				sb = new StringBuffer();
				sb.append(c);
				bufferType = type;
			}
		}
		if (sb.length() != 0) {
			ss.add(new EntityStringSegment(str.length() - sb.length(), sb.toString(), bufferType));
		}
		return ss;
	}
	
	/**
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * Web2.0
     * 百度
     */
	public EntityType getType(char c) {
		CharacterType type = CharacterType.getType(c);
		if (type == CharacterType.ENGLISH_LETTER || type == CharacterType.CHINESE_LETTER ) {
			return EntityType.ENGLISH_WORD;
		}
		if (type == CharacterType.ENGLISH_DIGIT 
			|| type == CharacterType.CHINESE_DIGIT || c == '.'
			/*|| type == CharacterType.CHINESE_NUMBER_METRIC*/) {
			return EntityType.ENGLISH_WORD;//EntityType.NUMBER;
		}
		return EntityType.CHINESE_WORD;
	}
	
	public String toString() {
		return "[SENTENCE] " + position + " : " + str;
	}
	
	public static void main(String[] args) {
		String sentence = "李开复被传离职 Google中国进退两难(上)-Google 浙江在线 09:06 06/15";
		ArticleStringSegment ass = new ArticleStringSegment(0, sentence);
    	ass.splitOnce();
    	for (StringSegment ss : ass.subSegments) {
    		if (ss instanceof SentenceStringSegment) {
    			System.out.println ("string segment : " + ss.getString());
		    	SentenceStringSegment sss = (SentenceStringSegment)ss;
		    	List<StringSegment> sslist = sss.aggregate();
		    	for (StringSegment tss : sslist) {
		    		System.out.println (tss);
		    	}
    		} else {
    			System.out.println ("unknown characters: " + ss.getString());
    		}
    	}		
    }
}
