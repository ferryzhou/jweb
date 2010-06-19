package com.iil.util.thread;

import java.util.Iterator;

/**
 * ����Iterator�������ȡ����
 */
public class IteratorTaskRetriever implements TaskRetriever {
	
	public IteratorTaskRetriever(Iterator taskIter) {
		this.taskIter = taskIter;
	}
	
	public boolean hasNext() {
		return taskIter.hasNext();
	}
	
	public Runnable next() {
		return (Runnable)taskIter.next();
	}
	
	private Iterator taskIter;
}