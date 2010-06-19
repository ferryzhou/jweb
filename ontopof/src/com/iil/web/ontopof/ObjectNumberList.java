package com.iil.web.ontopof;

import java.util.*;

class ObjectNumberList {
	
	public List<ObjectNumberPair> getSortPairs() {
		Collections.sort(pairs, (new Comparator() {
				public int compare(Object o1, Object o2) {
					double v1 = ((ObjectNumberPair)o1).number;
					double v2 = ((ObjectNumberPair)o2).number;
					if (v2 > v1) return 1;
					else if (v2 < v1) return -1;
					else return 0;
				}
			}));

		//Collections.reverse(pair);
		return pairs;		
	}
	
	public List<Object> getSortedKeywords() {
		List<Object> l = new ArrayList();
		List<ObjectNumberPair> sortPairs = getSortPairs();
		for (ObjectNumberPair pair : sortPairs) {
			l.add(pair.key);
		}
		return l;
	}
	
	public void addPair(Object key, double number) {
		pairs.add(new ObjectNumberPair(key, number));
	}
	
	public static class ObjectNumberPair {
		
		public ObjectNumberPair(Object key, double number) {
			this.key = key;
			this.number = number;
		}
		
		public String toString() {
			return key + " - " + number;
		}
		public Object key;
		public double number;
	}
	
	private List<ObjectNumberPair> pairs = new ArrayList<ObjectNumberPair>();
	
	public static void main(String[] args) {
    	ObjectNumberList l = new ObjectNumberList();
    	l.addPair("a", 1.01);
    	l.addPair("b", 3.0);
    	l.addPair("c", 2.0);
    	l.addPair("d", 1.02);
    	List sl = l.getSortedKeywords();
    	System.out.println (sl);
    }
}
