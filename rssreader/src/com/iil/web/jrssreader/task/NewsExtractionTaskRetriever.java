/*
 * Created on 2006-4-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader.task;

import java.util.*;

import com.iil.util.thread.*;

import com.iil.web.jrssreader.*;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NewsExtractionTaskRetriever implements TaskRetriever {
	
	public NewsExtractionTaskRetriever() {
		try {
			List<NewsSource> sources = ArticleStore.getInstance().getAllNewsSources();
			for (NewsSource ns : sources) {
				System.out.println("read newssource: " + ns);
				robots.add(new JRSSRobot(ns));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasNext() {
		return robots.size() > 0;
	}
	
	public Runnable next() {
		return robots.remove(0);
	}
	
	private List<JRSSRobot> robots = new ArrayList<JRSSRobot>();

	public static void main(String[] args) throws Exception{
		TaskRunner tr = new TaskRunner();
		tr.run(new NewsExtractionTaskRetriever());
	}
}
