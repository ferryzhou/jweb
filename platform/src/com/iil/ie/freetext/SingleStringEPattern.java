/*
 * Created on 2004-7-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.ie.freetext;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SingleStringEPattern extends EPattern{
	public SingleStringEPattern() {
	}
	
    public boolean doFind(int start) {
    	if (p.find(start)) {
	    	from = p.start();
	    	to = p.end() - 2;
	    	//if (to == super.getTextLength() + 1) to = super.getTextLength();
	    	group = p.group();
	    	return true;
    	}
    	return false;
    }

	public void resetInput(CharSequence cs) {
		//this.cs = cs;
		super.resetInput(cs);
		p.resetInput(" " + cs + " ");
	}
	
	
	private EPattern p = PatternFactory.OBSOLETECHINESESTRING_EPATTERN;
}
