/*
 * Created on 2006-4-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader;

import com.iil.word.*;
import java.util.*;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StringSimilarityCalculator {

	public boolean isSimilar(String s1, String s2) {
		double similarity = calcSimilarity(s1, s2);
		if (similarity > THRESHOLD) return true;
		else return false;
	}
	
	public double calcSimilarity(String s1, String s2) {
		Set<String> set1 = getSet(s1);
		Set<String> set2 = getSet(s2);
		Set<String> set3 = new HashSet<String>();
		set3.addAll(set1);
		set3.retainAll(set2);
		int size1 = set1.size();
		int size2 = set2.size();
		int size3 = set3.size();
		int size4 = size1 + size2 - size3;
		if (size4 != 0) return (double)size3 / size4;
		else return 0;
	}
	
	public Set<String> getSet(String s) {
		SimpleWordSplitter sws = new SimpleWordSplitter();
		Set<String> set = new HashSet<String>();
		sws.readString(s);
		while (sws.hasMoreWords()) {
			set.add(sws.nextWord());
		}
		return set;
	}
	
	private static double THRESHOLD = 0.3;
	public static void main(String[] args) {
		StringSimilarityCalculator ssc = new StringSimilarityCalculator();
		System.out.println(ssc.calcSimilarity("法国发起挑战Google的搜索引擎计划", "法国发起搜索引擎计划 对抗美国“Google杀手”-..."));
	}
}
