package com.iil.word.impl_seg;

import java.util.*;

/**
 * ARTICLE
 *   - SENTENCE //+ ENTITY unknown characters
 *     - ENTITY
 *       1. ENGLISH_WORD
 *       2. CALENDER
 *       3. NUMBER
 *       4. CHINESE_WORD
 */
public abstract class StringSegment {
	
	public StringSegment(int pos, String s) {
		this.position = pos;
		//this.type = type;
		this.str = s;
	}
	
	/**
     * str => subSegments
     */
	public abstract void splitOnce();
	
	public void split() {
		splitOnce();
		for (StringSegment ss : subSegments) {
			ss.split();
		}
	}
	
	public String getString() {
		return str;
	}
	
	public int getPosition() {
		return position;
	}
	
	public String toString() {
		return "[SEGMENT] " + position + " : " + str;
	}
	
	public List<StringSegment> getSubSegments() {
		return subSegments;
	}

	//public StringSegmentType getType() {
	//	return type;
	//}
	
	protected int position;
	//private StringSegmentType type;
	protected String str;
	
	protected List<StringSegment> subSegments = new ArrayList<StringSegment>();
	
	//may have ambiguity
	protected List<StringSegment> subSegments2 = null;
}


