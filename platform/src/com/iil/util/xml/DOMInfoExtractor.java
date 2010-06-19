package com.iil.util.xml;


import java.util.*;
import org.w3c.dom.*;

/**
 * DOM����Ϣ��ȡ
 * @author Ferry
 * @version 1.0
 */
public class DOMInfoExtractor {
	
	/**
	 * @param root �����
	 * @param elementName ��Ҫ��ȡ��Ԫ������
	 * @return ����ƥ��Ԫ������Ԫ���б�
	 */
	public static List extractNodesByName(Node root, String elementName) {
		return locateNodesToList(root, "//" + elementName);
	}
	
	/**
	 * @param root �����
	 * @param attributeName ��Ҫ��ȡ��Ԫ��������������
	 * @return ����ƥ���Ԫ���б�
	 */
	public static List extractNodesByAttribute(Node root, String attributeName) {
		return locateNodesToList(root, "//*[@" + attributeName+"]");
	}
	
	/**
     * ��ȡһ�������dom���е�xpath
	 * @param n ��Ҫ��ȡ·���Ľڵ�
	 * @return ·�����ʽ
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
	 * Ѱ���ӽڵ��ڸ��ڵ��µ�index
	 * @param parent �ṩ�ĸ��ڵ�
	 * @param child �ṩ���ӽڵ�
	 * @return �����ӽڵ�����
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
	 * ����XPathѰ��ƥ��ڵ��б���
	 * @param node ��Ҫ��λ�Ľڵ�
	 * @param xpath �ڵ��·�����ַ�������
	 * @param ts ��Ҫʹ�õ����ηָ����
	 * @return ���ؽڵ���ӽڵ���м�νڵ��б�
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
	 * �����ṩ����ϢѰ�ҵ���Ӧ�Ľڵ���߽ڵ��б����ҽ���Ϣ���ַ������
	 * @param root �ṩ�ĸ����
	 * @param xpath �ṩ��·��
	 * @return ����Ҳ����ͷ���null���ҵ��Ļ����ؽڵ��б���Ϣ�ַ���
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
	 * ��ȡ�ڵ��б���ַ���������ڵ��ַ����м�û���κ������ַ�
	 * @param l ��Ҫ����Ľڵ��б�
	 * @return ���ؽڵ��б���Ϣ�ַ���
	 */
	public static String getNodeListString(NodeList nl) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<nl.getLength(); i++) {
			sb.append(getNodeString(nl.item(i)));
		}
		return sb.toString();
	}
	
	/**
	 * ��ȡ�ڵ��б���ַ���������ڵ��ַ����м�û���κ������ַ�
	 * @param l ��Ҫ����Ľڵ��б�
	 * @return ���ؽڵ��б���Ϣ�ַ���
	 */
	public static String getNodeListString(List l) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<l.size(); i++) {
			sb.append(getNodeString((Node)l.get(i)));
		}
		return sb.toString();
	}
	
	/**
	 * �ҵ������ڵ��Ӧ��ֵ�����ַ�����ʾ
	 * @param n ��Ҫ��ʾ�Ľڵ�
	 * @return ����ǺϷ��ڵ㣬�ͷ�����valueֵ��������Ǹ��ڵ�
	 *         �����ӽڵ��б����򷵻�null
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
	 * �ж��������Ľڵ��Ƿ��ǽṹ����
	 * @param n ��Ҫ�жϵĽڵ�
	 * @return ����ڵ�����={"br","tr","p","div"}����"\r\n",���򷵻�null
	 */
	public static String getSpecNodeString(Node n) {
		if (n.getNodeName().toLowerCase().equals("br")) return "\r\n";
		if (n.getNodeName().toLowerCase().equals("tr")) return "\r\n";
		if (n.getNodeName().toLowerCase().equals("p")) return "\r\n";
		if (n.getNodeName().toLowerCase().equals("div")) return "\r\n";
		return "";
	}
	
}
