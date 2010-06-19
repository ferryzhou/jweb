/*
 * Created on 2006-4-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader;

import java.util.*;

import com.iil.web.rssgen.LEConfig;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NewsItem implements Comparable{

	public int id;
	public int pid;
	public String title;
	public Date pubDate;
	public String content;
	public String author;
	public String url;
	public String imgSource;
	public String subject;
	public String description;
	public String tags;
	public int newsSourceId;
	public int copynum;
	public String quality;
	public int cluster;
	
	public List<NewsItem> childrens = new ArrayList<NewsItem>();
	
	public int compareTo(Object o) {
		if (pubDate.before(((NewsItem)o).pubDate)) return 1;
		else return -1;
	}
	
	public String toString() {
		return id + " " + pid + " " + title + " " + pubDate + "\r\n";
	}
}
