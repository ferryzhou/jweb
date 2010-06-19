/*
 * Created on 2004-12-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import java.io.*;
import java.util.*;
import java.text.*;

import org.w3c.dom.*;

import com.iil.util.web.*;
import com.iil.ie.xmlbased.*;
import com.iil.ie.*;
import com.iil.util.*;
import com.iil.util.xml.*;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ListExtractor {
	
	/**
	 * 
	 * @param c
	 */
	public ListExtractor(String name, String startURL, LEConfig lec) {
		this.name = name;
		this.startURL = startURL;
		this.lec = lec;
	}
	
	public ListExtractor(String name, String startURL, LEConfig lec, Filter filter) {
		this.name = name;
		this.startURL = startURL;
		this.lec = lec;
		this.exfilter = filter;
	}
	
	//List<Element>   Element -> Item<title, url, date, author>
	public List extractList() throws IOException, InformationExtractionException{
		
		List es = new LinkedList();
		String url = startURL;
		while (url != null) {
			System.out.println("process page " + url);
			HTMLWrapper hw = new HTMLWrapper(url); 
			Document doc = hw.getDOM();
			DataCleaner.toAbsoluteURL(doc, hw.getURL());
			Document results = lec.getXie().extract(doc);
			//lec.getXie().transform(doc, new FileWriter("out.xml"));break;
			List items = getList(results);
			System.out.println("size: " + items.size());
			ObjectPrinter.print(items);
			
			if (lec.reverse()) Collections.reverse(es);
			
			Filter filter = lec.getItemFilter();
			if (filter != null) items = Lists.filter(items, filter);
			System.out.println("after filtered, size: " + items.size());
			if (exfilter != null) items = Lists.filter(items, exfilter);
			System.out.println("after 2 filtered, size: " + items.size());
			es.addAll(items);
			
			Stoper stoper = lec.getStoper();
			if (stoper != null && stoper.check(es)) break;
			
			NextPageURLExtractor npue = lec.getNpue();
			if (npue != null) url = npue.nextPageURL(doc);
			else break;
		}
		if (lec.getDateConverter() != null) {
			for (Object o : es) {
				Element e = (Element)o;
				Node n = DOMInfoExtractor.locateNode(e, "date/text()");
				changeItemDateFormat(n);
			}			
		}
		//printList(es);
		return es;
	}

	public Document toRSS(Document doc) throws InformationExtractionException{
		Document outdoc = lec.getRssXie().extract(doc);
		changeTitle(outdoc, name);
		//Format outFormat = getOutFormat(outdoc);
		
		//if (lec.getDateConverter() != null) changeDateFormat(outdoc);
		return outdoc;
	}

	public void toRSS(Document doc, String filename) throws IOException, InformationExtractionException{
		Document odoc = toRSS(doc);
		DOMUtil.save(doc, filename);
	}
	
/*	
	// Not Complete!!!!!!!!!!!!!!!!!!
	private Format getOutFormat(Document doc) {
		String version = DOMInfoExtractor.extractString(doc, "//rss/@version");
		Locale locale = Locale.ENGLISH;
		String fs;
		if (version.trim().equals("2.0")) fs = "E, dd MMM yyyy HH:mm:ss Z";
		else fs = "E, dd MMM yyyy HH:mm:ss Z";
    	SimpleDateFormat fmt = new SimpleDateFormat(fs, locale);
    	fmt.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    	return fmt;
	}
*/	
	public void changeTitle(Document doc, String name) {
		Node title = DOMInfoExtractor.locateNode(doc, "//channel/title/text()");
		title.setNodeValue(name);
	}
	
	public void changeDateFormat(Document doc) {
		System.out.println("change date format...");
		NodeList nl = DOMInfoExtractor.locateNodes(doc, "//pubDate/text()");
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			changeItemDateFormat(n);
		}
	}
	
	public void changeItemDateFormat(Node node) {
		//System.out.println(node.getNodeValue());
		//System.out.println(lec.getDateConverter().convert(node.getNodeValue()));
		node.setNodeValue(lec.getDateConverter().convert(node.getNodeValue()));
	}
/*	
	public String changeDateFormat(String date, Format srcFormat, Format outFormat) {
		try {
			if (date == null || date.trim().length() == 0) return "";
			//System.out.println("date: " + date);
			Date newdate = (Date)srcFormat.parseObject(date);
			//System.out.println(newdate.getYear());
			if (newdate.getYear() == 70) {
				Date d = new Date();
				if (newdate.getMonth() <= d.getMonth())	newdate.setYear(d.getYear());
				else newdate.setYear(d.getYear()-1);
			}
        	String s = outFormat.format(newdate);
        	//s += " GMT";
        	return s;
		} catch (Exception e) {
			e.printStackTrace();
			return date;
		}
	}
*/	
	public static Document toDocument(List es) {
		Document doc = DocumentFactory.createDocument("items");
		Element root = doc.getDocumentElement();
		Iterator iter = es.iterator();
		while (iter.hasNext()) {
			Node n = (Node)iter.next();
			root.appendChild(DOMUtil.importNode(doc, n, true));
		}		
		return doc;
	}
	
	public static List getList(Document dom) {
		NodeList children = dom.getDocumentElement().getChildNodes();
		List items = new LinkedList();
		for (int i = 0; i < children.getLength(); i++) {
			items.add(children.item(i));
		}
		return items;
	}
	private String startURL;
	private LEConfig lec;
	private String name;
	private Filter exfilter;
	
	public static void printList(List es) {
		DOMPrinter dp = new HTMLDOMPrinter();
		Iterator iter = es.iterator();
		while (iter.hasNext()) {
			Node n = (Node)iter.next();
			System.out.println(dp.print(n));
		}
	}
	
	public static void main(String[] args) throws Exception{
		extractiask();
	}
	
	public static void extractbbs() throws Exception{
		LEConfig lec = new LEConfig();
		lec.load("bbs.lec");
		ListExtractor le = new ListExtractor("9710bbs", "http://bbs.ustc.edu.cn/cgi/bbsdoc?board=AUTO", lec);
		List l = le.extractList();
		System.out.println("final size: " + l.size());
		printList(l);
		Document doc = le.toDocument(l);
		DOMUtil.save(doc, "items.xml");
		le.toRSS(doc, "rss9710.xml");
	}
	
	public static void extractiask() throws Exception{
		LEConfig lec = new LEConfig();
		lec.loadFromDir("lec_lib/iask");
		String keyword = "Google";
		ListExtractor le = new ListExtractor("Google", "http://iask.com/n?k=" + keyword + "&t=title", lec);
		List l = le.extractList();
		System.out.println("final size: " + l.size());
		printList(l);
		Document doc = le.toDocument(l);
		DOMUtil.save(doc, "items_" + keyword + ".xml");
		le.toRSS(doc, "iask_" + keyword + ".xml");
	}
	
	public static void extractsina() throws Exception{
		LEConfig lec = new LEConfig();
		lec.load("sinasearch.lec");
		String keyword = "google";
		ListExtractor le = new ListExtractor("Google", "http://chanews.sina.com.cn/s.cgi?k=" + keyword + "&t=", lec);
		List l = le.extractList();
		System.out.println("final size: " + l.size());
		printList(l);
		Document doc = le.toDocument(l);
		DOMUtil.save(doc, "items_" + keyword + ".xml");
		le.toRSS(doc, "sina_" + keyword + ".xml");
	}
}
