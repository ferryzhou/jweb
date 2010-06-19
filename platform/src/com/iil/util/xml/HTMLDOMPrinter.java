package com.iil.util.xml;

import java.io.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import org.w3c.dom.traversal.*;

/**
 * 
 */
public class HTMLDOMPrinter implements DOMPrinter {
	
	public String print(Node node) {
		return compress(printHTMLNode(node).toString());
	}
	
	private String compress(String s) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new StringReader(s));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.length() != 0) {
					sb.append(DOMInfoExtractor.filter(line));
					sb.append(RETURN);
				}
			}
		}catch(IOException e) {};
		return sb.toString();
	}
	private StringBuffer printHTMLNode(Node node) {
		if (node.getNodeType() == Node.TEXT_NODE) {
			return new StringBuffer(node.getNodeValue());
		}
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			return printHTMLElement((Element)node);
		}
		if (node.getNodeType() == Node.DOCUMENT_NODE) {
			return printHTMLElement(((Document)node).getDocumentElement());
		}
		return new StringBuffer();
	}
	
	private StringBuffer printHTMLElement(Element e) {
		StringBuffer sb = new StringBuffer();
		String name = e.getNodeName();
		if (name.equals("br")) return new StringBuffer(RETURN);
		if (name.equals("p") || name.equals("tr") || name.equals("td") || name.equals("div")) {
			sb.append(RETURN);
			sb.append(printChildren(e));
			sb.append(RETURN);
			return sb;
		}
		if (name.equals("a")) {
			sb.append(BLANK);
			sb.append(printChildren(e));
			sb.append(BLANK);
			return sb;
		}
		if (name.equals("ol") || name.equals("ul") || name.equals("li")) {
			sb.append(printChildren(e));
			sb.append(RETURN);
			return sb;
		}
		if (name.equals("head") || name.equals("script")) return sb;
		return printChildren(e);
	}
	
	private StringBuffer printChildren(Node node) {
		StringBuffer sb = new StringBuffer();
		NodeList nl = node.getChildNodes();
		for (int i = 0; i<nl.getLength(); i++) {
			Node child = nl.item(i);
			sb.append(printHTMLNode(child));
		}
		return sb;
	}
	
	private static String RETURN = "\r\n";
	private static String BLANK = " ";
}