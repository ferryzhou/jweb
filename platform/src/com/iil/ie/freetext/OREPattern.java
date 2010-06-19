package com.iil.ie.freetext;

import java.util.*;

/**
 * [ep1|ep2|ep3|...]
 * 
 * <p>
 * choose the first match pattern, if the first index is same, then choose the 
 * largest match pattern.
 * </p>
 * <p>in java regex package, or pattern can be expressed by X|Y. for example "a.|bc"</p>
 * 
 */
public class OREPattern extends EPattern{
	
	public OREPattern(List eps) {
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

	public boolean doFind(int start) {
		EPattern ep = selectApropriateOne(getMatchedEPatterns(start));
		if (ep == null) return false;
		else return true;
	}
	
	private List getMatchedEPatterns(int start) {
		List l = new ArrayList();
		Iterator iter = eps.iterator();
		while (iter.hasNext()) {
			EPattern ep = (EPattern)iter.next();
			if (ep.find(start)) l.add(ep);
		}
		return l;
	}
	
	private EPattern selectApropriateOne(List eps) {
		if (eps.size() == 0) return null;
		EPattern ep = (EPattern)eps.get(0);
		from = ep.start();
		to = ep.end();
		Iterator iter = eps.iterator();
		iter.next();
		while (iter.hasNext()) {
			EPattern nextep = (EPattern)iter.next();
			if (nextep.start() < from || (nextep.start() == from && nextep.end() > to)) {
				from = nextep.start();
				ep = nextep;
				to = nextep.end();
			}
		}
		group = ep.group();
		return ep;
	}
	
	/**
     * List<EPattern>
     */
	private List eps = new ArrayList();
	
}
