/*
 * Created on 2006-4-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader;

import java.util.*;

import org.w3c.dom.*;
import com.iil.web.rssgen.*;

import com.iil.util.xml.*;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JRSSRobot implements Runnable{

	public JRSSRobot(NewsSource ns) {
		this.newsSource = ns;
	}
	
	public void run() {
		if (newsSource.type.equals("wie")) {
			try {
				RSSGenConfig rgc = new RSSGenConfig();
				rgc.load("task_lib/" + newsSource.uri);
				RSSGenerator rssg = new RSSGenerator(rgc.getTitle(), rgc.getLecDir(), rgc.getURL(), null, rgc.getFilter());
				rssg.run();
				List list = rssg.getList();
				JRSS jrss = listToJRSS(list);
				updateManager.insertJRSS(jrss);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public JRSS listToJRSS(List list) {
		JRSS jrss = new JRSS();
		jrss.id = newsSource.id;
		jrss.title = newsSource.title;
		jrss.tags = newsSource.tags;
		
		for (Object o : list) {
			NewsItem item = nodeToNewsItem((Node)o);
			if (item != null) jrss.items.add(item);
		}
		return jrss;
	}
	
	//Item<title, url, date, author>
	private NewsItem nodeToNewsItem(Node n) {
		NewsItem item = new NewsItem();
		item.newsSourceId = newsSource.id;
		item.title = DOMInfoExtractor.extractString(n, "title");
		item.url = DOMInfoExtractor.extractString(n, "url");
		//error entry
		if (item.url == null || item.url.trim().length() == 0) return null;
		item.author = DOMInfoExtractor.extractString(n, "author");
		try {
			item.pubDate = LEConfig.getOutDateFormat().parse(DOMInfoExtractor.extractString(n, "date"));
		} catch (Exception e) {
			e.printStackTrace();
			item.pubDate = new Date();
		}
		return item;
	}
	
	private NewsSource newsSource;
	private UpdateManager updateManager = UpdateManager.getInstance();
	
	public static void main(String[] args) throws Exception{
		ArticleStore store = ArticleStore.getInstance();
		store.connect();
		
		List<NewsSource> sources = store.getAllNewsSources();
		for (NewsSource ns : sources) {
			System.out.println(ns);
			JRSSRobot robot = new JRSSRobot(ns);
			robot.run();
		}
/*		NewsSource ns = new NewsSource();
		ns.id = 0;
		ns.type = "wie";
		ns.title = "test";
		ns.tags = "test";
		ns.uri = "iask_title/Google.tsk";
		//ns.uri = "sina/famous_media/ccw.tsk";
*/		
		
		store.close();
	}
}
