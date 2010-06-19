package com.iil.util.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 过滤器。过滤空白字符文本节点和注释节点。
 */
public class DOMFilter {
	
	public Document trimDocument(Document dom){
		
		trim(dom);
		return dom;
	}
	
	public void trim(Node node) {

		NodeList nl = node.getChildNodes();
		for (int i = 0; i<nl.getLength(); i++) {
			Node child = nl.item(i);
			trimNode(child);
			if (isWhiteSpaceNode(child)) deleteNode(child);
			else trim(child);
		}
	}
	
	public void trimNode(Node node) {
		if (node.getNodeType() == Node.TEXT_NODE) {
			node.setNodeValue(trimString(node.getNodeValue()));
		}
	}
	
	/**
     * 把两边的空白符和特殊字符滤掉。
     */
	private String trimString(String s) {
		StringBuffer sb = new StringBuffer();
		String st = s.trim();
		for (int i = 0; i<st.length(); i++) {
			int c = (int)st.charAt(i);
			if (isSpecialChar(c)) continue;
			sb.append((char)c);
		}
		return sb.toString();
	}
	
	private boolean isSpecialChar(int c) {
		if (c == 160) return true;
		if (c == 12539) return true;
		if (c == 12288) return true;
		return false;
	}
	
	private boolean isWhiteSpaceNode(Node node) {
		//if (node == null) return false;
		return ((node.getNodeType() == Node.TEXT_NODE) && 
		    (node.getNodeValue().trim().equals("")));
	}
	
	private void deleteNode(Node node) {
		Node parent = node.getParentNode();
		parent.removeChild(node);
	}
}

/**
 * faint. tidy的Document实现不支持DocumentTraversal
 *
 */