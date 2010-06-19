package com.iil.util.thread.test;

import java.util.*;

import com.iil.util.thread.*;

public class Test_ThreadPool {
	
	public void test() {
		Iterator taskIter = getTasks().iterator();
		TaskPool tp = new TaskPool(new IteratorTaskRetriever(taskIter), 5, 2);
		ThreadPool atp = new ThreadPool(5, tp);
	}

	private List getTasks() {
		return getRepeadSleepTasks();
	}

	private List getRepeadSleepTasks() {
		List tasks = new LinkedList();
		for (int i = 0; i<10; i++) {
			Runnable r = new RepeatSleep(i, getRandomLen());
			tasks.add(r);
		}
		return tasks;
	}
	
	private int getRandomLen() {
		return (int)(Math.random() * 10);
	}
	
	public static void main(String[] args) {
    	Test_ThreadPool t = new Test_ThreadPool();
    	t.test();
    }
}