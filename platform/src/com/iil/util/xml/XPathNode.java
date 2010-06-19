package com.iil.util.xml;

/**
 * XPath中的节点。包含节点名与index。
 * 当index = 0时，代表所有同名节点。即xx[*]
 */
public class XPathNode {
	
	public XPathNode(String name) {
		this.name = name;
	}
	
	public XPathNode(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	public static XPathNode valueOf(String str) {
		int left = str.indexOf("[");
		if (left == -1) return new XPathNode(str);
		int right = str.indexOf("]");
		String nodename = str.substring(0, left);
		int index = Integer.parseInt(str.substring(left + 1, right));
		return new XPathNode(nodename, index);
	}
	
	public String getName() {
		return name;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean equals(Object o) {
		//System.out.println (this);
		//System.out.println (o);
		//System.out.println ("equals...");
		if (o instanceof XPathNode) {
			XPathNode on = (XPathNode)o;
			if (this.name.equals(on.name) && this.index == on.index) return true;
		}
		//System.out.println ("not equal");
		return false;
	}
	
	public boolean contain(XPathNode n) {
		if (name.equals(n.name)) {
			if (index == n.index || index == 0) return true;
		}
		return false;
	}
	
	public String toString() {
		if (index == 0) return name;
		return name + "[" + index + "]";
	}
	
	private String name;
	private int index;
}