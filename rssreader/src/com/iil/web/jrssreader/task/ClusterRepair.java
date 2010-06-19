/*
 * Created on 2006-4-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader.task;

import java.util.*;

import com.iil.web.jrssreader.*;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ClusterRepair {

	public void run() throws Exception{
		ArticleStore store = ArticleStore.getInstance();
		store.connect();
		List<NewsItem> items = store.getAllItems();
		HashMap<Integer, NewsItem> hashItems = new HashMap<Integer, NewsItem>();
		for (NewsItem item : items) {
			hashItems.put(item.id, item);
		}
		for (NewsItem item : items) {
			if (item.pid != 0) {
				NewsItem parent = hashItems.get(item.pid);
				while (parent.pid != 0) parent = hashItems.get(parent.pid);
				item.cluster = parent.id;
			} else item.cluster = item.id;
			store.updateNewsItemCluster(item);
		}
		store.close();
	}
	
	public void run2() throws Exception{
		ArticleStore store = ArticleStore.getInstance();
		store.connect();
		List<NewsItem> items = store.getAllItems();
		List<NewsItem> prevItems = new ArrayList<NewsItem>();
		for (NewsItem item : items) {
			item.pid = 0;
			System.out.print(item.id + " ");
			boolean noSimilar = true;
			StringSimilarityCalculator ssc = new StringSimilarityCalculator();
			for (NewsItem prevItem : prevItems) {
				if (ssc.isSimilar(item.title, prevItem.title)) {
					item.pid = prevItem.id;
					item.cluster = prevItem.cluster;
					noSimilar = false;
					break;
				}				
			}
			if (noSimilar) {
				item.cluster = item.id;
			}
			item.copynum = 0;
			store.updateNewsItem(item);
			prevItems.add(item);
		}
		store.close();
	}

	public static void main(String[] args) throws Exception{
		ClusterRepair cr = new ClusterRepair();
		cr.run2();
	}
}
