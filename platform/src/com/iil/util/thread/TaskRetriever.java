package com.iil.util.thread;

/**
 * �����ȡ����
 */
public interface TaskRetriever {
	
	public boolean hasNext();
	
	public Runnable next();
}