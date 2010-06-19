package com.iil.util.thread.test;

public class RepeatSleep implements Runnable {
	
	public RepeatSleep(int threadIndex, int sleepTime) {
		this.threadIndex = threadIndex;
		this.sleepTime = sleepTime;
		System.out.println ("thread " + threadIndex + " sleep time: " + sleepTime);
	}
	
	public void run() {
		try {
			System.out.println ("thread " + threadIndex + " begin...");
			Thread.sleep(sleepTime * 1000);
			System.out.println ("thread " + threadIndex + " end...");
		} catch (InterruptedException ie) {
			System.out.println (ie);
		}
	}
	
	private int threadIndex;
	private int sleepTime;
}