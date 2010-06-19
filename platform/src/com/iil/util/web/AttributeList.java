package com.iil.util.web;

import java.util.*;
import java.util.regex.*;

/**
 * ÊôĞÔÁĞ±í
 * @author Ferry
 * @version 1.0
 */
public class AttributeList {
	
	public static AttributeList getAttributeList(String s, String listdelimiter, String attrdelimiter) {
		return (new AttributeList()).unmarshal(s, listdelimiter, attrdelimiter);
	}
	
	public AttributeList unmarshal(String s, String listdelimiter, String attrdelimiter) {
		Pattern pl = Pattern.compile(listdelimiter);
		Pattern pa = Pattern.compile(attrdelimiter);
		String[] attrs = pl.split(s);
		for (int i = 0; i<attrs.length; i++) {
			String[] attr = pa.split(attrs[i]);
			if (attr.length == 1) {
				addAttribute(attr[0].trim(), "");
			} else {
				addAttribute(attr[0].trim(), attr[1].trim());
			}
		}
		return this;
	}
	
	public void addAttribute(String key, String value) {
		attrList.add(new Attribute(key, value));
	}
	
	public void addAttribute(Attribute attr) {
		attrList.add(attr);
	}

	public void addList(AttributeList list) {
		attrList.addAll(list.attrList);
	}
	
	public String get(String key) {
		Attribute attr = getAttribute(key);
		if (attr == null) return null;
		else return attr.getValue();
	}
	
	public Attribute getAttribute(String key) {
		Iterator iter = attrList.iterator();
		while (iter.hasNext()) {
			Attribute attr = (Attribute)iter.next();
			if (attr.getKey().equals(key)) return attr;
		}
		return null;		
	}
	
	public void replaceWithAttribute(Attribute rpattr) {
		Attribute attr = getAttribute(rpattr.getKey());
		if (attr != null) {
			attr.setValue(rpattr.getValue());
		}
	}
	
	public void replaceWithAttributeList(AttributeList rpattrs) {
		Iterator iter = rpattrs.getIterator();
		while (iter.hasNext()) {
			Attribute attr = (Attribute)iter.next();
			replaceWithAttribute(attr);
		}
	}

	public Iterator getIterator() {
		return attrList.iterator();
	}
	
	public int size() {
		return attrList.size();
	}
	
	public String marshal(String listdelimiter, String attrdelimiter) {
		StringBuffer sb = new StringBuffer();
		Iterator iter = attrList.iterator();
		while (iter.hasNext()) {
			Attribute attr = (Attribute)iter.next();
			if (sb.length() != 0) sb.append(listdelimiter);
			sb.append(attr.getKey());
			sb.append(attrdelimiter);
			sb.append(attr.getValue());
		}
		return sb.toString();
	}
	
	public String toString() {
		return marshal(", ", ": ");
	}
	
	public Object clone() {
		AttributeList al = new AttributeList();
		al.addList(this);
		return al;
	}
	
	private List attrList = new Vector();
}