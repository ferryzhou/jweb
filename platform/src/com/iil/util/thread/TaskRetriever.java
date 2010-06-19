package com.iil.util.thread;

/**
 * 任务获取器。
 */
public interface TaskRetriever {
	
	public boolean hasNext();
	
	public Runnable next();
}