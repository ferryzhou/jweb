package com.iil.util.thread;

import java.util.*;

/**
 * ����ء�
 * ������̫�࣬������һ��ȫ������ʱ��ʹ������ء�
 * �������Ҫһ�������ȡ����������ȡ��һ������
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
     * ������п�λ����onceRetriveNum�����ȡ����ֱ��������
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