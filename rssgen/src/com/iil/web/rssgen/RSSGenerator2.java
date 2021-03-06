/*
 * Created on 2004-12-18
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.util.*;
import java.io.*;

import org.w3c.dom.*;

import com.iil.ie.*;
import com.iil.ie.xmlbased.*;
import com.iil.util.web.*;
import com.iil.util.xml.*;

public class RSSGenerator2 {
	
	public RSSGenerator2(String bbsurl) throws IOException{
		this.bbsurl = bbsurl;
		boards = getBoards();
		System.out.println (com.iil.util.ObjectPrinter.print(boards));
		boardIter = boards.iterator();
	}
	/**
     * if none, return empty list
     */
	public List nextPageURLList() throws IOException{
		if (prevPageURL == null) {
			if (boardIter.hasNext()) {
				String board = (String)boardIter.next();
				prevPageURL = getFirstPageURL(board);
				System.out.println ("next board prevPageURL: " + prevPageURL);
			} else return new ArrayList();
		}
		curPageDoc = HTMLWrapper.getDOM(prevPageURL);
		prevPageURL = getPrevPageURL(curPageDoc);
		System.out.println ("prevPageURL: " + prevPageURL);
		List list = getPageURLs(curPageDoc);
		if (list.size() == 0) return nextPageURLList();
		else return list;
	}
	
	private List getBoards() throws IOException{
		try {
    		String boardurl = URLUtil.toAbsoluteURL(bbsurl, "cgi-bin/bbstopb10");
    		XInformationExtractor xie = new XInformationExtractor("boards.xsl");
    		Document doc = xie.extract(boardurl);
    		//List urls = filterReplicated(getURLs(doc));

			List urls = new ArrayList();
			urls.add("http://fbbs.ustc.edu.cn/cgi-bin/bbsdoc?board=AUTO");
    		return urls;
    	}catch(InformationExtractionException e) {
    		throw new IOException(e.getMessage());
    	}
	}
	
	private List filterReplicated(List l) {
		List list = new ArrayList();
		Iterator iter = l.iterator();
		while (iter.hasNext()) {
			Object o = iter.next();
			if (!list.contains(o)) list.add(o);
		}
		return list;
	}
	
	private List getPageURLs(Document pageDoc) throws IOException{
		try {
	    	XInformationExtractor xie = new XInformationExtractor("pages.xsl");
	    	Document doc = xie.extract(pageDoc);
	    	List urls = getURLs(doc);
	    	return urls;		
    	}catch(InformationExtractionException e) {
    		throw new IOException(e.getMessage());
    	}
	}
	
	/**
     * if not exists! return null;
     */
	private String getPrevPageURL(Document pageDoc) {
		return DOMInfoExtractor.extractString(pageDoc.getDocumentElement(), "//a[.='上一页'][1]/@href");
	}
	
	private String getFirstPageURL(String board) throws IOException{
		Document doc = HTMLWrapper.getDOM(board);
		return DOMInfoExtractor.extractString(doc.getDocumentElement(), "//a[.='主题模式'][1]/@href");		
	}
	
	//http://fbbs.ustc.edu.cn/
	private String bbsurl;
	private List boards;
	private Iterator boardIter;
	private Document curPageDoc;
	private String prevPageURL;
	
    
    public static List getURLs(Document doc) {
    	NodeList nl = DOMInfoExtractor.locateNodes(doc.getDocumentElement(), "//link");
    	List urls = new ArrayList();
    	for (int i = 0; i<nl.getLength(); i++) {
    		urls.add(getURL((Element)nl.item(i)));
    	}
    	return urls;
    }
    
    public static String getURL(Element e) {
    	String href = DOMInfoExtractor.extractString(e, "href");
    	return href;
    }
}
