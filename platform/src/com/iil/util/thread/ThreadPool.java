package com.iil.util.thread;

import java.util.*;

public class ThreadPool {
	
	public ThreadPool(int threadsNum, List runs) {
		this(threadsNum, new TaskPool(runs));
	}
	
	public ThreadPool(int threadsNum, TaskPool taskPool) {
		this.threadsNum = threadsNum;
		this.taskPool = taskPool;
		threads = new PoolWorker[threadsNum];
	}
	
	public void run() {
		for (int i = 0; i<threadsNum; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}
	
	public void join() {
		for (int i = 0; i<threadsNum; i++) {
			try {
				threads[i].join();
			}catch(InterruptedException ie) {
				System.out.println (ie);
			}
		}
	}

	public class PoolWorker extends Thread {
		
		public void run() {
			Runnable r;
			
			while (true) {
				synchronized (taskPool) {
					if (taskPool.isEnd() || taskPool.isEmpty()) {
						//System.out.println ("thread " + this.getName() + " end..");
						return;				
					}
/*					if (taskPool.isEmpty()) {
						System.out.println ("thread " + this.getName() + " end..");
						try {
							taskPool.wait();
						}catch(InterruptedException ie) {
							break;
						}
					}
*/					r = taskPool.getNextTask();
					//System.out.println ("thread " + this.getName() + " get begin new task");
				}
				try {
					r.run();
				} catch (Exception e) {
					System.out.println (e);
					e.printStackTrace();
				}
			}
		}
	}
	
	private final int threadsNum;
	private final TaskPool taskPool;
	private final PoolWorker[] threads;
}