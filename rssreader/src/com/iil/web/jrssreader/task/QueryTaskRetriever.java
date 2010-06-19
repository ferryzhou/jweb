/*
 * Created on 2006-4-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader.task;

import java.util.ArrayList;
import java.util.List;

import com.iil.util.thread.*;

import com.iil.web.jrssreader.*;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryTaskRetriever implements TaskRetriever{
	
	public QueryTaskRetriever() {
		try {
			List<QueryTask> sources = ArticleStore.getInstance().getAllRSSQueryTask();
			for (QueryTask ns : sources) {
				System.out.println("read rss query task: " + ns);
				robots.add(new QueryRobot(ns));
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
	
	private List<QueryRobot> robots = new ArrayList<QueryRobot>();

	public static void main(String[] args) throws Exception{
		TaskRunner tr = new TaskRunner();
		tr.run(new QueryTaskRetriever());
	}

}
