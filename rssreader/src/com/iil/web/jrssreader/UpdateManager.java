/*
 * Created on 2005-2-17
 *
 */
package com.iil.web.jrssreader;

import java.util.*;
import java.sql.SQLException;

/**
 * @author ferry zhou
 *
 * insert new data.
 * cache data.
 * 
 */
public class UpdateManager {

	public static UpdateManager getInstance() {
		if (instance == null) instance = new UpdateManager(ArticleStore.getInstance());
		return instance;
	}

	private UpdateManager(ArticleStore store) {
		this.store = store;
		try {
			nextId = store.getMaxItemId();
			nextId ++;
			cachedItems.addAll(store.getAllItems());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void insertJRSS(JRSS jrss) throws SQLException {
		JRSS oldjrss = store.getJRSS(jrss.id);
		
		//System.out.println("old jrss: " + oldjrss);
		System.out.println("new jrss: " + jrss);
		
		//cachedItems.addAll(oldjrss.items);
		for (NewsItem item : jrss.items) {
			//if (item.pubDate.before(oldjrss.LatestNewsPubDate)) continue;
			if (isLinkExisted(oldjrss.items, item)) continue;
			boolean noSimilar = true;
			item.id = nextId++;
			for (NewsItem olditem : cachedItems) {
				if (isTitleSimilar(olditem.title, item.title)) {
					noSimilar = false;
					if (item.url.equals(olditem.url)) break;
					//olditem.copynum ++;
					item.pid = olditem.id;
					item.cluster = olditem.cluster;
					//store.updateNewsItemCopyNum(olditem);
					store.insertNewsItem(item);
					break;
				}
			}
			if (noSimilar) {
				item.cluster = item.id;
				store.insertNewsItem(item);
			}
			cachedItems.add(item);
		}
	}
	
	private boolean isLinkExisted(List<NewsItem> items, NewsItem newitem) {
		for (NewsItem item : items) {
			if (item.url.equals(newitem.url)) return true;
		}
		return false;
	}
	
	public boolean isTitleSimilar(String s1, String s2) {
		return (new StringSimilarityCalculator()).isSimilar(s1, s2);
	}
	
	public void close() throws SQLException{
		store.close();
	}
	
	private ArticleStore store;
	private List<NewsItem> cachedItems = new ArrayList<NewsItem>();
	
	public static int nextId = 0;
	
	private static UpdateManager instance = null;
}
