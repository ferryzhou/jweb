package com.iil.util.xml;

import java.util.*;

/**
 * XPath。包含一个XPathNode的列表。
 */
public class XPath {
	
	public XPath() {
	}
	
	public XPath(XPathNode node) {
		nodes.add(node);
	}
	
	public XPath(List nodes) {
		this.nodes.addAll(nodes);
	}
	
	public static XPath valueOf(String str) {
		List nodes = new ArrayList();
		StringTokenizer st = new StringTokenizer(str, "/");
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			nodes.add(XPathNode.valueOf(token));
		}
		XPath xpath = new XPath();
		xpath.addNodes(nodes);
		return xpath;
	}
	
	public XPathNode get(int i) {
		return (XPathNode)nodes.get(i);
	}
	
	public void addAtFront(XPath path) {
		nodes.addAll(0, path.nodes);
	}
	
	public void addAtFront(XPathNode node) {
		nodes.add(0, node);
	}

	public void append(XPathNode node) {
		nodes.add(node);
	}
	
	public void addNodes(List nodes) {
		this.nodes.addAll(nodes);
	}
	
	public void addAtEnd(XPath path) {
		nodes.addAll(path.nodes);
	}
	
	public XPathNode removeFront() {
		if (nodes.size() > 0) return (XPathNode)nodes.remove(0);
		else return null;
	}
	
	public void removeFront(int num) {
		for (int i = 0; i<num; i++) removeFront();
	}
	
	public int size() {
		return nodes.size();
	}
	
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof XPath) {
			XPath op = (XPath)o;
			if (this.nodes.size() != op.nodes.size()) return false;
			Iterator iter1 = this.nodes.iterator();
			Iterator iter2 = op.nodes.iterator();
			while (iter1.hasNext()) {
				if (!(iter1.next().equals(iter2.next()))) return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean contain(XPath xpath) {
		if (this.nodes.size() != xpath.nodes.size()) return false;
		Iterator iter1 = this.nodes.iterator();
		Iterator iter2 = xpath.nodes.iterator();
		while (iter1.hasNext()) {
			if (!(((XPathNode)iter1.next()).contain((XPathNode)iter2.next()))) return false;
		}
		return true;
	}
	
	public boolean isEmpty() {
		return nodes.isEmpty();
	}
	
	public Object clone() {
		return new XPath(this.nodes);
	}
	
	public XPathNode getEndNode() {
		if (nodes.size() > 0) {
			return (XPathNode)nodes.get(nodes.size() - 1);
		} else throw new IllegalArgumentException();
	}
	
	public String toString() {
		if (nodes.size() == 0) return ".";
		StringBuffer sb = new StringBuffer();
		Iterator iter = nodes.iterator();
		while (iter.hasNext()) {
			sb.append("/");
			sb.append(iter.next().toString());
		}
		if (!isEmpty()) sb.delete(0, 1);
		return sb.toString();
	}
	
	public List getNodes() {
		return Collections.unmodifiableList(nodes);
	}
	

	//List<XPathNode>	
	private List nodes = new ArrayList();
}