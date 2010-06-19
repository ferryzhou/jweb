package com.iil.util;

import java.util.Date;
/**
 * 计时器。
 * @author Ferry Zhou
 * @version 1.0 
 */
public class Timer {
	
	/**
	 * 起始时间
	 */
	private long startTime;
	
	/**
	 * 中止时间
	 */
	private long endTime;
	
	/**
	 * 获取起始时间
	 * @see Date
	 */
	public void start() {
		
		startTime = (new Date()).getTime();
	}
	
	/**
	 * 获取中止时间
	 * @see Date
	 */
	public void end() {
		
		endTime = (new Date()).getTime();
	}
	
	/**
	 * 获取起始时间和中止时间的时间间隔
	 * @return 返回长整型时间间隔
	 */
	public long getTimeSpan() {
		
		return endTime - startTime;
	}
}
