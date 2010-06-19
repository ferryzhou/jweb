package com.iil.ie.freetext;

import java.util.*;

/**
 * <pre>
 * at best not use!
 * [ep1, ep2, ep3,...]
 * struct list
 * ep1 must exists and should be first occured pattern
 * </pre>
 */
public class StructEPattern extends EPattern{
	
	public StructEPattern(List eps) {
		if (eps.size() == 0) throw new IllegalArgumentException();
		this.eps = eps;
	}

	public void resetInput(CharSequence cs) {
		super.resetInput(cs);
		Iterator iter = eps.iterator();
		while (iter.hasNext()) {
			EPattern ep = (EPattern)iter.next();
			ep.resetInput(cs);
		}
	}

	/**
     * look for the fist two strings that match the first pattern, then look for
     * the rest patterns between the two strings.
     */
	public boolean doFind(int start) {
		EPattern firstep = getFirstEPattern();
		if (firstep.find(start) == false) return false;
		//find
		this.from = firstep.start();
		this.to = firstep.end();
		int end1 = firstep.end();
		Object[] struct = new Object[eps.size()];
		struct[0] = firstep.group();
		
		if (firstep.find(end1) == true) {
			findBetween(end1, firstep.start(), struct);
		} else {
			findBetween(end1, -1, struct);
		}
		
		this.group = new Struct(struct);
		
		return true;
	}
	
	private void findBetween(int start, int end, Object[] struct) {
		for (int i = 1; i<eps.size(); i++) {
			EPattern ep = (EPattern)eps.get(i);
			if (ep.find(start) && (end == -1 || ep.end() <= end)) {
				struct[i] = ep.group();
				this.to = ep.end();
			}
		}
	}
	
	public EPattern getFirstEPattern() {
		return (EPattern)eps.get(0);
	}
	
	
	/**
     * List<EPattern>
     */
	private List eps = new ArrayList();
	
}
