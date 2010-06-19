/*
 * Created on 2005-2-8
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.rssgen;

import java.io.*;
import java.util.List;

import org.w3c.dom.Document;

import com.iil.util.Filter;
import com.iil.util.xml.DOMUtil;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RSSGenerator {

	public RSSGenerator(String name, String lecfile, String url, String outfile) {
		this.name = name;
		this.lecfile = lecfile;
		this.url = url;
		this.outfile = outfile;
	}
	
	public RSSGenerator(String name, String lecfile, String url, String outfile, Filter filter) {
		this.name = name;
		this.lecfile = lecfile;
		this.url = url;
		this.outfile = outfile;
		this.filter = filter;
	}
	
	public RSSGenerator(RSSGenConfig config) {
		this.name = config.getTitle();
		this.lecfile = config.getLecDir();
		this.url = config.getURL();
		this.outfile = config.getOutFile();
		this.filter = config.getFilter();
	}
	
	public void run() throws Exception{
		LEConfig lec = new LEConfig();
		lec.loadFromDir(lecfile);
		ListExtractor le = new ListExtractor(name, url, lec, filter);
		list = le.extractList();
		System.out.println("final size: " + list.size());
		//printList(l);
		Document idoc = ListExtractor.toDocument(list);
		//DOMUtil.save(doc, "items_" + name + ".xml");
		doc = le.toRSS(idoc);
		if (outfile != null) DOMUtil.save(doc, outfile);
	}
	
	public Document getRSSDocument() {
		return doc;
	}
	
	public List getList() {
		return list;
	}

	private Document doc;
	private List list;
	
	private String name;
	private String lecfile;
	private String url;
	private String outfile;
	
	private Filter filter;
}
