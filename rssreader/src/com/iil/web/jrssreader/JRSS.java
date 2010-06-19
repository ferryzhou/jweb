/*
 * Created on 2006-4-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader;

import java.util.*;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JRSS {

	public String title;
	public String description;
	public String link;
	public String language;
	public String creator;
	public String tags;
	
	public List<NewsItem> items = new ArrayList<NewsItem>();

	public int id;
	public Date LatestNewsPubDate;
	
	public String toString() {
		String s = id + " " + title + " " + items.size();
		for (NewsItem item : items) {
			s += item;
		}
		s += "\r\n";
		return s;
	}
}
