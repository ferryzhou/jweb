package com.iil.ie.freetext;

import org.w3c.dom.*;
import com.iil.util.xml.*;
/**
 * <pre>
 * used to extract information from free text by specified patterns.
 * we have several EPattern Construction forms :
 * RegexEPattern, PreEPattern, OREPattern, ContainEPattern, EndWithEPattern, StructEPattern
 * and EPatternConstructors:
 * DictEPC, LiteralStringEPC, SeperatedLiteralStringEC, LoadClassEPC
 * 
 * an typical example to use EPattern:
 *   EPattern ep = new PreEPattern(new RegexEPattern("地址"));
 *   String s = "地址：北京市 地址：上海市";
 *   ep.resetInput(s);
 *   while (ep.find()) {
 *     System.out.println(ep.group());  //print matched string
 *     System.out.println("start: " + ep.start());  //start index of the matched string
 *     System.out.println("end: " + ep.end());    //end index of the matched string
 *   } 
 * the result will be:
 * 北京市
 * start: 3
 * end: 6
 * 上海市
 * start: 10
 * end: 13
 * 
 * more examples can be found in com.iil.ie.freetext.test.Test_EPattern
 * </pre>
 * 
 * @author ferry zhou
 *
 */
public abstract class EPattern{

	public synchronized void resetInput(CharSequence cs) {
		this.cs = cs;
		from = 0;
		to = 0;
		group = null;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public synchronized boolean find() {
		return find(to);
	}
	
	/**
	 * Resets this EPattern and then attempts to find the next subsequence of the input sequence 
	 * that matches the Epattern, starting at the specified index.
	 * @param start start index to find
	 * @return true if find matched subsequence, else return false;
	 */
    public synchronized boolean find(int start) {
    	//System.out.println(cs);
        int limit = getTextLength();
        //System.out.println("start " + limit);
        //System.out.println("limit " + limit);
        if ((start < 0) || (start > limit))
            return false;//throw new IndexOutOfBoundsException("Illegal start index");
        return doFind(start);
    }
    
    protected abstract boolean doFind(int start);

	/**
	 * Returns the input subsequence matched by the previous match.
	 * @return
	 */
    public synchronized Object group() {
    	if ((group instanceof String) && this.name != null) {
    		Document doc = DocumentFactory.createDocument(this.name);
    		Element root = doc.getDocumentElement();
    		root.appendChild(doc.createTextNode((String)group));
    		return root;
    	}
		else return group;
	}
	
	public synchronized int start() {
		return from;
	}
	
	public synchronized int end() {
		return to;
	}
	
	public ExtractedObject getExtractedObject() {
		return new ExtractedObject(group(), start(), end());
	}
	
	public synchronized CharSequence getCharSequence() {
		return cs;
	}
	
	public synchronized int getTextLength() {
		return cs.length();
	}
	
	protected String name;
	
	protected int from;
	protected int to;
	protected Object group;
	protected CharSequence cs;
}
