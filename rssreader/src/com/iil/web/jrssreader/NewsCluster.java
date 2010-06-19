/*
 * Created on 2006-5-4
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NewsCluster implements Comparable{
	
	public NewsCluster(NewsItem item) {
		items.add(item);
		this.id = item.id;
		this.earliestItem = item;
	}
	
	public void addItem(NewsItem item) {
		items.add(item);
	}
	
	public List<NewsItem> getItems() {
		return items;
	}
	
	public void sort() {
		Collections.sort(items);
		Collections.reverse(items);
		earliestItem = items.remove(0);
	}
	
	public int compareTo(Object o) {
		return (earliestItem.compareTo(((NewsCluster)o).earliestItem));
	}
	
	public int size() {
		return items.size();
	}
	
	public NewsItem getEarliestItem() {
		return earliestItem;
	}
	private List<NewsItem> items = new LinkedList<NewsItem>();
	private int id;
	private NewsItem earliestItem;
}
