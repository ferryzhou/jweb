package com.iil.util.xml;


import java.util.*;
import org.w3c.dom.*;

/**
 * DOM树信息抽取
 * @author Ferry
 * @version 1.0
 */
public class DOMInfoExtractor {
	
	/**
	 * @param root 根结点
	 * @param elementName 需要抽取的元素名称
	 * @return 所有匹配元素名的元素列表。
	 */
	public static List extractNodesByName(Node root, String elementName) {
		return locateNodesToList(root, "//" + elementName);
	}
	
	/**
	 * @param root 根结点
	 * @param attributeName 需要抽取的元素所含的属性名
	 * @return 所有匹配的元素列表。
	 */
	public static List extractNodesByAttribute(Node root, String attributeName) {
		return locateNodesToList(root, "//*[@" + attributeName+"]");
	}
	
	/**
     * 获取一个结点在dom树中的xpath
	 * @param n 需要获取路径的节点
	 * @return 路径表达式
	 */
	public static String getXPath(Node n) {
		
		String xpath = "";
		Node parent;
		if (n.getNodeType() == Node.ATTRIBUTE_NODE) {
			xpath += "/@" + ((Attr)n).getName();
			//System.out.println (xpath);
			parent = ((Attr)n).getOwnerElement();
		} else {
			parent = n.getParentNode();
			if (n.getNodeName().equals("#document")) return xpath;
			int index = getNodeIndex(parent, n);
			String nodeName = n.getNodeName();
			if (nodeName.toLowerCase().equals("#text")) nodeName = "text()";
			if (nodeName.toLowerCase().equals("#comment")) nodeName = "comment()";
			xpath += "/" + nodeName + "[" + index + "]";
		}
		xpath = getXPath(parent) + xpath;
		return xpath;
	}
	
	public static XPath getXPath2(Node n) {
		XPath xpath = new XPath();
		Node parent;
		XPathNode xpathnode = null;
		if (n.getNodeType() == Node.ATTRIBUTE_NODE) {
			parent = ((Attr)n).getOwnerElement();
			xpathnode = new XPathNode("@" + ((Attr)n).getName());
		} else {
			parent = n.getParentNode();
			if (n.getNodeName().equals("#document")) return xpath;
			int index = getNodeIndex(parent, n);
			String nodeName = n.getNodeName();
			if (nodeName.toLowerCase().equals("#text")) nodeName = "text()";
			if (nodeName.toLowerCase().equals("#comment")) nodeName = "comment()";
			xpathnode = new XPathNode(nodeName, index);
		}
		xpath.append(xpathnode);
		xpath.addAtFront(getXPath2(parent));
		return xpath;
	}

	
	/**
	 * 寻找子节点在父节点下的index
	 * @param parent 提供的父节点
	 * @param child 提供的子节点
	 * @return 返回子节点的序号
	 * @see DOMInfoExtractor
	 */
	private static int getNodeIndex(Node parent, Node child) {
		NodeList nl = parent.getChildNodes();
		String name = child.getNodeName();
		//System.out.println ("name: " + name);
		//System.out.println ("parant: " + parent.getNodeName());
		int index = 1;
		for (int i = 0; i<nl.getLength(); i++) {
			Node n = nl.item(i);
			//System.out.println (n);
			String nodeName = n.getNodeName();
			if (nodeName != null && nodeName.equals(name)) {
				if (n == child) return index;
				else index++;
			}
		}
		return index;
	}

	/**
	 * 根据XPath寻找匹配节点列表函数
	 * @param node 需要定位的节点
	 * @param xpath 节点的路径（字符串对象）
	 * @param ts 需要使用的三段分割对象
	 * @return 返回节点的子节点的中间段节点列表
	 */
	public static Node locateNode(Node node, String xpath) {
		try {
			return org.apache.xpath.XPathAPI.selectSingleNode(node, xpath);
		} catch (javax.xml.transform.TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static NodeList locateNodes(Node node, String xpath) {
		try {
			return org.apache.xpath.XPathAPI.selectNodeList(node, xpath);
		} catch (javax.xml.transform.TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List locateNodesToList(Node node, String xpath) {
		List l = new LinkedList();
		NodeList nl = locateNodes(node, xpath);
		if(nl != null) {
			for (int i = 0; i<nl.getLength(); i++) {
				l.add(nl.item(i));
			}
		}
		return l;
	}
	
	/**
	 * 根据提供的信息寻找到相应的节点或者节点列表，并且将信息用字符串输出
	 * @param root 提供的根结点
	 * @param xpath 提供的路径
	 * @return 如果找不到就返回null，找到的话返回节点列表信息字符串
	 */
	public static String extractString(Node root, String xpath) {
		try {
			NodeList nl = org.apache.xpath.XPathAPI.selectNodeList(root, xpath);
			if (nl.getLength() == 0) return null;
			return filter(getNodeListString(nl));
		} catch (javax.xml.transform.TransformerException e) {
			System.out.println (e.getMessageAndLocation() + " -- " + xpath);
			return null;
		}
	}

	public static String filter(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<s.length(); i++) {
			char c = s.charAt(i);
			if (((int)c) != 160) sb.append(c);
		}
		//System.out.println (sb.toString());
		return sb.toString();
	}

	/**
	 * 获取节点列表的字符串输出，节点字符串中间没有任何其他字符
	 * @param l 需要输出的节点列表
	 * @return 返回节点列表信息字符串
	 */
	public static String getNodeListString(NodeList nl) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<nl.getLength(); i++) {
			sb.append(getNodeString(nl.item(i)));
		}
		return sb.toString();
	}
	
	/**
	 * 获取节点列表的字符串输出，节点字符串中间没有任何其他字符
	 * @param l 需要输出的节点列表
	 * @return 返回节点列表信息字符串
	 */
	public static String getNodeListString(List l) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<l.size(); i++) {
			sb.append(getNodeString((Node)l.get(i)));
		}
		return sb.toString();
	}
	
	/**
	 * 找到给定节点对应的值，用字符串表示
	 * @param n 需要表示的节点
	 * @return 如果是合法节点，就返回其value值，如果其是根节点
	 *         返回子节点列表，否则返回null
	 */
	public static String getNodeString(Node n) {
		if (n.getNodeValue() != null && n.getNodeValue().length() != 0 && n.getNodeType() != Node.COMMENT_NODE) return n.getNodeValue();
		if (n.getNodeType() == Node.ELEMENT_NODE) {
			NodeList nl = n.getChildNodes();
			return getNodeListString(nl);
		}
		return "";
	}
	
	/**
	 * 判断所给定的节点是否是结构术语
	 * @param n 需要判断的节点
	 * @return 如果节点名称={"br","tr","p","div"}返回"\r\n",否则返回null
	 */
	public static String getSpecNodeString(Node n) {
		if (n.getNodeName().toLowerCase().equals("br")) return "\r\n";
		if (n.getNodeName().toLowerCase().equals("tr")) return "\r\n";
		if (n.getNodeName().toLowerCase().equals("p")) return "\r\n";
		if (n.getNodeName().toLowerCase().equals("div")) return "\r\n";
		return "";
	}
	
}
