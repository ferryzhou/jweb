package com.iil.util.xml;

/**
 * 
 */
public class ListXPathNode extends XPathNode {
	
	public ListXPathNode(String name) {
		super(name);
	}
	
	public ListXPathNode(String name, int beginIndex, int endIndex, int span) {
		super(name);
		if (endIndex < beginIndex) throw new IllegalArgumentException();
		if ((endIndex - beginIndex) % span != 0) throw new IllegalArgumentException();
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
		this.span = span;
	}
	
	public boolean equals(Object o) {
		if (!(super.equals(o))) return false;
		if (! (o instanceof ListXPathNode)) {
			return beginIndex == 0 && endIndex == 0 && span == 0;
		} else {
			ListXPathNode lxpn = (ListXPathNode)o;
			return beginIndex == lxpn.beginIndex && endIndex == lxpn.endIndex
			    && span == lxpn.span;
		}
	}
	
	public boolean contain(XPathNode n) {
		if (getName().equals(n.getName())) {
			if (getIndex() == 0 && n.getIndex() == 0) return true;
			if (getIndex() != 0) return getIndex() == n.getIndex();
			if (n.getIndex() < beginIndex) return false;
			if ((n.getIndex() - beginIndex) % span != 0) return false;
			return true;
		}
		return false;
	}
	
	public int getBeginIndex() {
		return beginIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}
	
	public void setBeginIndex(int i) {
		this.beginIndex = i;
	}

	public void setEndIndex(int i) {
		this.endIndex = i;
	}
	
	public int getSpan() {
		return span;
	}
	
	public String toString() {
		return getName() + "[" + beginIndex + " .. " + endIndex + ", " + span + "]";
	}
		
	private int beginIndex;
	private int endIndex;
	private int span;
}
