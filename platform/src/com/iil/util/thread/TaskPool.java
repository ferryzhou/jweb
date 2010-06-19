package com.iil.util.thread;

import java.util.*;

/**
 * 任务池。
 * 当任务太多，不便于一次全部生成时，使用任务池。
 * 任务池需要一个任务获取器，用来读取下一个任务。
 */
public class TaskPool {
	
	public TaskPool(TaskRetriever tr, int maxTaskNum, int onceRetriveNum) {
		//assert maxTaskNum >= onceRetriveNum && tr != null;
		
		this.tr = tr;
		this.maxTasksNum = maxTaskNum;
		this.onceRetriveNum = onceRetriveNum;
		readTasks();
	}
	
	public TaskPool(TaskRetriever tr) {
		this(tr, DEFAULT_MAX_TASKS_NUM, DEFAULT_ONCE_RETRIEVE_NUM);
	}
	
	/**
     * List<Runnable>
     */
	public TaskPool(List runs) {
		this(new IteratorTaskRetriever(runs.iterator()));
	}
	
	public synchronized Runnable getNextTask() {
		Runnable r = (Runnable)tasks.remove(0);
		readTasks();
		return r;
	}
	
	public synchronized boolean isEmpty() {
		return tasks.size() == 0;
	}
	
	public synchronized boolean isEnd() {
		return end;
	}
	
	public synchronized void setEnd() {
		end = true;
		this.notify();
	}
	
	/**
     * 如果池中空位大于onceRetriveNum，则读取任务，直至池满。
     */
	private void readTasks() {
		if (!tr.hasNext()) return;
		int empty = maxTasksNum - tasks.size();
		if (empty >= onceRetriveNum) {
			for (int i = 0; i<empty; i++) {
				if (tr.hasNext()) tasks.add(tr.next());
			}
		}
	}
	
	private int maxTasksNum;
	private int onceRetriveNum;
	private List tasks = new LinkedList();
	private TaskRetriever tr;
	private boolean end = false;
	
	private static int DEFAULT_MAX_TASKS_NUM = 10;
	private static int DEFAULT_ONCE_RETRIEVE_NUM = 5;
}