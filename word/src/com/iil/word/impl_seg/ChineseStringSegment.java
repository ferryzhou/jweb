package com.iil.word.impl_seg;

import java.util.*;

class ChineseStringSegment{

	public ChineseStringSegment(WordDict dict) {
		this.dict = dict;
	}
	
	private static class Range {
		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		public boolean include(Range r) {
			return start <= r.start && end >= r.end;
		}
		
		public boolean collide(Range r) {
			if (end < r.start || r.end < start) return false;
			else return true;
		}
		
		public String toString() {
			return "[" + start + " " + end + "]";
		}
		
		int start;
		int end;
	}
	
	public List<StringSegment> split(String s, int index) {
		//System.out.println ("split: " + s);
		List<Range> rs = new ArrayList<Range>();
    	for (int i = 0; i < s.length(); i++) {
    		int end = dict.scan(s, i);
    		if (end > i) {
    			//System.out.println ("detected: " + s.substring(i, end + 1));
    			rs.add(new Range(i, end));
    		}
    	}
    	List<Range> nrs = reduceRanges(rs);
    	
    	List<Range> brs = chooseNoConflictBestCombination(rs);
    	//System.out.println ("brs: " + printRanges(brs));
    	
    	return split(s, brs);
	}
	
	public String printRanges(List<Range> rs) {
		StringBuffer sb = new StringBuffer();
		for (Range r : rs) {
			sb.append(r + " ");
		}
		return sb.toString();
	}
	
	/**
     * rs: no conficts!
     */
	public List<StringSegment> split(String s, List<Range> rs) {
		List<StringSegment> sss = new ArrayList<StringSegment>();
		int lastBeginIndex = 0;
		for (Range r : rs) {
			if (lastBeginIndex < r.start) {
				sss.add(EntityStringSegment.ChineseNonWordStringEntity(lastBeginIndex, s.substring(lastBeginIndex, r.start)));
			}
			sss.add(EntityStringSegment.ChineseWordEntity(lastBeginIndex, s.substring(r.start, r.end + 1)));
			lastBeginIndex = r.end + 1;
		}
		if (lastBeginIndex < s.length()) {
			sss.add(EntityStringSegment.ChineseNonWordStringEntity(lastBeginIndex, s.substring(lastBeginIndex, s.length())));
		}
		return sss;
	}
	
	public List<Range> chooseNoConflictBestCombination(List<Range> rs) {
		List<List<Range>>cands = getRangeSequence(rs);
		for (List<Range> cand : cands) {
			//System.out.println ("cand ");
			//System.out.println (com.iil.util.ObjectPrinter.print(cand));			
		}
		return cands.get(0);
	}
	/**
     * generate all ambiguity combinations
     * first sort range list!
     */
	public List<List<Range>> getRangeSequence(List<Range> rs) {
		//System.out.println ("ranges: ");
		//System.out.println (com.iil.util.ObjectPrinter.print(rs));
		
		List<List<Range>> cands = new ArrayList<List<Range>>();
		if (rs.size() == 0) {
			cands.add(new ArrayList<Range>());
			return cands;
		}
		if (rs.size() == 1) {
			cands.add(rs);
			return cands;
		}
		//pick the first one
		Range first = rs.get(0);
		//pick all the conflict ones with first
		int conflictsN = 0;
		List<Range> conflicts = new ArrayList();
		for (int i = 1; i < rs.size(); i++) {
			if (first.collide(rs.get(i))) {
				conflicts.add(rs.get(i));
				conflictsN = i;
			} else break;
		}
		
		//System.out.println ("conflictN: " + conflictsN);
		
		List<List<Range>> firstSubs = getRangeSequence(rs.subList(conflictsN + 1, rs.size()));
		List<List<Range>> firstCands = new ArrayList<List<Range>>();
		for (List<Range> trs : firstSubs) {
			List<Range> ar = new ArrayList<Range>();
			ar.add(first);
			ar.addAll(trs);
			firstCands.add(ar);
			//trs.add(0, first);
		}
		//System.out.println ("first subs: ");
		//System.out.println (com.iil.util.ObjectPrinter.print(firstSubs));
		//cands.addAll(firstSubs);
		cands.addAll(firstCands);
		
		for (int i = 1; i <= conflictsN; i++) {
			List<List<Range>> conflictSubs = getRangeSequence(rs.subList(i, rs.size()));
			cands.addAll(conflictSubs);
			//System.out.println ("conflict subs: ");
			//System.out.println (com.iil.util.ObjectPrinter.print(conflictSubs));
		}
		
		return cands;
	}
	
	public List<Range> reduceRanges(List<Range> rs) {
		List<Range> nrs = new ArrayList<Range>();
		for (Range r : rs) {
			boolean included = false;
			for (Range j : rs) {
				if (j != r) {
					if (j.include(r)) {
						included = true;
						break;
					}
					if (r.collide(j)) {
						//System.out.println ("collide!!!!!!!!!!!!!!" + r + " x " + j);
					}
				}
			}
			if (!included) nrs.add(r);
		}
		return nrs;
	}

	private WordDict dict;
	
	private WordWatcher watcher;
	
	public static void main(String[] args) {
    	WordDict wd = WordDict.getInstance();
    	wd.load("../dict/dict1.txt");
    	ChineseStringSegment css = new ChineseStringSegment(wd);
    	//String test = "衣服和服装";
		String test = "新晋商代表百度CEO李彦宏信心带来盈利 ";
    	List<StringSegment> sss = css.split(test, 0);
    	for (StringSegment ss : sss) {
    		System.out.println (ss);
    	}
    }
}
