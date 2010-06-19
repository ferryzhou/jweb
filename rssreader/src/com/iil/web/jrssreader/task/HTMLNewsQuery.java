/*
 * Created on 2006-5-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader.task;

import java.util.List;

import com.iil.web.jrssreader.ArticleStore;
import com.iil.web.jrssreader.NewsCluster;
import com.iil.web.jrssreader.NewsQuery;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HTMLNewsQuery {

	public static void main(String[] args) throws Exception{
		ArticleStore store = ArticleStore.getInstance();
		store.connect();

		NewsQuery nq = new NewsQuery();
		List<NewsCluster> clusters = nq.query("1");

		nq.toHTML(clusters, "»¥ÁªÍøÏûÏ¢", "test_out/Internet.htm");	
		store.close();
		
	}
}
