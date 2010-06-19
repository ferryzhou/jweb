package com.iil.word.impl;

import java.io.*;

import org.w3c.dom.*;

import com.iil.util.xml.DOMBuilder;

/**
 * xml文件格式参考dict.xml
 */
public class DictLoader {
	
	public static Dict load(String filename) throws IOException {
		Dict dict = new Dict();
		Document dom = DOMBuilder.build(filename);
		Element root = dom.getDocumentElement();
		NodeList nl = root.getElementsByTagName("*");
		for (int i = 0; i<nl.getLength(); i++) {
			Element e = (Element)nl.item(i);
			addToDict(e, dict);
		}
		return dict;
	}
	
	private static void addToDict(Element e, Dict dict) {
		String word = e.getNodeName();
		String freqv = e.getAttribute(WordPropertyKey.Frequnece.toString());
		dict.put(word, new WordProperties(WordPropertyKey.Frequnece, freqv));
	}
	
	public static void main(String[] args) throws IOException{
    	System.out.println (DictLoader.load("dict.xml"));
    	BufferedReader br = new BufferedReader(new FileReader());
    	BufferedWriter bw = new BufferedWriter(new FileWriter());
    	String line = null;
    	while ((line = br.readLine()) != null) {
    		StringBuffer sb = new StringBuffer();
    		for (int i = 0; i < line.length(); i++) {
    			//if (line.charAt(i))
    		}
    	}	
    }
}