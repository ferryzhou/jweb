package com.iil.ie.freetext;

import java.util.regex.*;

/**
 * <pre>
 * The pattern is specified by regular expression.
 * RegexEPatttern just wrapped Pattern and Matcher class in java.util package.
 * [regular expression]
 * 
 * </pre>
 * 
 * @see Pattern
 * @author ferry zhou
 *
 */
public class RegexEPattern extends EPattern
{
	public RegexEPattern(Pattern p) {
		this.p=p;
	}
	
	public RegexEPattern(String regex) {
		p = Pattern.compile(regex);
	}
	
	public RegexEPattern(String regex, int groupIndex) {
		p = Pattern.compile(regex);
		this.groupIndex = groupIndex;
	}

    public boolean doFind(int start) {
    	if (m.find(start)) {
    		from = m.start();
    		to = m.end();
    		group = m.group(groupIndex);
    		return true;
    	}else return false;
    }
    
	public void resetInput(CharSequence cs) {
		//this.cs = cs;
		super.resetInput(cs);
		m = p.matcher(cs);
	}
	
	private Pattern p;
	private Matcher m;
	private int groupIndex;
}