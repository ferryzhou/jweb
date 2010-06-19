/*
 * Created on 2004-7-10
 *
 * TODO To change the template for this generated file go end
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.ie.freetext;

/**
 * <pre>
 * [epattern][^letter|\z]
 * text=[epattern]
 * </pre>
 * 
 * @author ferry zhou
 *
 */
public class EndWithEPattern extends EPattern {
	
	public EndWithEPattern(EPattern pattern) {
		this.ep=pattern;
	}
	
    public boolean doFind(int start) {
		boolean find = ep.find(start);
		//System.out.println(find + " " + ep.group());
		if (find && isEnd(ep.end())) {
			from = ep.start();
			to = ep.end();
			group = (String)ep.group();
			return true;
		}else {
			if (find  && ep.end() < super.getTextLength()) return find(ep.end());
			else return false;
		}
    }
    
    private boolean isEnd(int index) {
		if (index == super.getCharSequence().length()) return true; //end of string
    	//System.out.println(super.getCharSequence().charAt(index));
    	if (!Character.isLetter((int)super.getCharSequence().charAt(index))) return true;
    	return false;
    }
    
    private boolean isEndChar(char c) {
    	return 	!PatternFactory.CHINESECHAR_PATTERN.matcher(c + "").find();
    }

	public void resetInput(CharSequence cs) {
		super.resetInput(cs);
		ep.resetInput(cs);
	}
	

	private EPattern ep;
	public static void main(String[] args) {
		
	}
}
