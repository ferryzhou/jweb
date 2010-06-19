/*
 * Created on 2006-4-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.web.jrssreader.task;

import com.iil.util.thread.*;
import com.iil.web.jrssreader.ArticleStore;

/**
 * @author ferry zhou
 *
 * 
 */
public class TaskRunner {

	public TaskRunner(int maxTaskNum, int onceRetrieveNum, int threadsNum) {
		this.maxTaskNum = maxTaskNum;
		this.onceRetrieveNum = onceRetrieveNum;
		this.threadsNum = threadsNum;
	}
	
	public TaskRunner() throws Exception{		
		store = ArticleStore.getInstance();
		store.connect();
	}
	
	public void run(TaskRetriever itr) {
		
		try {	
			TaskPool taskPool = new TaskPool(itr, 100, 50);
			ThreadPool threadPool = new ThreadPool(20, taskPool);
			threadPool.run();
			threadPool.join();
			
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private int maxTaskNum = 100;
	private int onceRetrieveNum = 50;
	private int threadsNum = 20;
	
	private ArticleStore store;
}
