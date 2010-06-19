/*
 * Created on 2006-4-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader;

import java.util.List;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryRobot implements Runnable{

	public QueryRobot(QueryTask task) {
		this.task = task;
	}
	
	public void run() {
		NewsQuery nq = new NewsQuery();
		List<NewsCluster> clusters = nq.query(task.condition);
		String outfile = "out/news/" + task.outfile + ".htm";
		if ("rss".equalsIgnoreCase(task.type)) {
			nq.toRSS(clusters, task.title, "out/rss/" + task.outfile + ".xml");
		} else if ("rss".equalsIgnoreCase(task.type)) {
			nq.toHTML(clusters, task.title, "out/news/" + task.outfile + ".htm");
		} else { //default both
			nq.toRSS(clusters, task.title, "out/rss/" + task.outfile + ".xml");
			nq.toHTML(clusters, task.title, "out/news/" + task.outfile + ".htm");			
		}
		
	}
	
	private QueryTask task;
}
