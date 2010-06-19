/*
 * Created on 2004-7-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.ie.freetext;

import org.w3c.dom.Element;

import com.iil.util.xml.SimpleDOMPrinter;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExtractedObject {
	
	
	/**
	 * @param o
	 * @param from
	 * @param to
	 */
	public ExtractedObject(Object group, int from, int to) {
		this.group = group;
		this.from = from;
		this.to = to;
	}
	
	/**
	 * @return Returns the from.
	 */
	public int getFrom() {
		return from;
	}
	/**
	 * @param from The from to set.
	 */
	public void setFrom(int from) {
		this.from = from;
	}
	/**
	 * @return Returns the to.
	 */
	public int getTo() {
		return to;
	}
	/**
	 * @param to The to to set.
	 */
	public void setTo(int to) {
		this.to = to;
	}
	
	public String toString() {
		String s;
		if (group instanceof Element) {
			s = (new SimpleDOMPrinter()).print((Element)group);
		}
		else s = group + "";
		return from + " - " + to + " " + s;
	}
	
	private Object group;
	private int from;
	private int to;
	/**
	 * @return Returns the group.
	 */
	public Object getGroup() {
		return group;
	}
	/**
	 * @param group The group to set.
	 */
	public void setGroup(Object group) {
		this.group = group;
	}
}
