/*
 * Created on 2004-7-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.ie.freetext;

import org.w3c.dom.Element;
import org.w3c.dom.Attr;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NamedEPattern extends EPattern{
	public NamedEPattern(EPattern preep, EPattern contentep)
	{
		this.preep = preep;
		this.contentep = contentep;
	}
	
    public boolean doFind(int start) {
    	Part part = findCandidatePart(start, super.getCharSequence().toString());
    	System.out.println(part);
    	if (part == null) return false;
    	preep.resetInput(part.title);
    	if (!preep.find()) return doFind(part.colonIndex + 1);
    	contentep.resetInput(part.content);
    	if (!contentep.find()) return doFind(part.colonIndex + 1);
    	//System.out.println(contentep.start());
    	this.from = part.fromIndex;
    	this.to = contentep.end() + part.colonIndex;
    	this.group = contentep.group();
/*    	if (this.group instanceof Element) {
    		Element e = (Element)this.group;
			Attr attr = e.getOwnerDocument().createAttribute("description");
			attr.setNodeValue("true");
			e.appendChild(attr);    		
    	}
*/    	return true;
    }

	public void resetInput(CharSequence cs) {
		//this.cs = cs;
		super.resetInput(cs);
		preep.resetInput(cs);
		contentep.resetInput(cs);
	}
	
	//private Part findPart(int start, String s) {
	//}
	
	private Part findCandidatePart(int start, String s) {
    	int colon1 = nextColon(start, s);
    	if (colon1 == -1) return null;
    	int nameStart = start;//getNameStart(colon1, start, s);
    	if (nameStart == -1) return findCandidatePart(colon1 + 1, s);
    	else {
    		int colon2 = nextColon(colon1+1, s);
    		if (colon2 == -1) colon2 = s.length();
    		Part part = new Part(nameStart, colon1, s.substring(nameStart, colon1), s.substring(colon1+1, colon2));
    		return part;
    	}
	}
	
	private int nextColon(int index, String s) {
		int i = index;
		while (i < s.length() && !isColon(s.charAt(i))) {
			i++;
		}
		if (i == s.length()) return -1;
		else return i;
	}
	
	private int getNameStart(int colonIndex, int start, String s) {
    	for (int i = colonIndex - 1; i >= start; i--) {
    		if (!Character.isLetter((int)s.charAt(i))) {
    			if (!Character.isWhitespace(s.charAt(i))) return -1;
    			else return i;
    		}
    	}
    	return start;
	}
	
	

	private boolean isColon(char c) {
		for (int i = 0; i<colon.length(); i++) {
			if (colon.charAt(i) == c) return true;
		}
		return false;
	}

	private boolean isDelimiter(char c) {
		for (int i = 0; i<delimiters.length(); i++) {
			if (delimiters.charAt(i) == c) return true;
		}
		return false;
	}
	
	private boolean isLetter(int c) {
		return Character.isLetter(c);
	}
	
	private EPattern preep;
	private EPattern contentep;
	private static final String colon = ":£º";
	private static final String delimiters = ":£º\r\n";

}
class Part {
	
	public Part(int fromIndex, int colonIndex, String title, String content) {
		this.fromIndex = fromIndex;
		this.colonIndex = colonIndex;
		this.title = title;
		this.content = content;
	}

	public int fromIndex;
	public int colonIndex;
	public String title;
	public String content;
	
	public String toString() {
		return "title = " + title + " content = " + content;
	}
}