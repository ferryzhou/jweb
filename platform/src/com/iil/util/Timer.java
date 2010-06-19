package com.iil.util;

import java.util.Date;
/**
 * ��ʱ����
 * @author Ferry Zhou
 * @version 1.0 
 */
public class Timer {
	
	/**
	 * ��ʼʱ��
	 */
	private long startTime;
	
	/**
	 * ��ֹʱ��
	 */
	private long endTime;
	
	/**
	 * ��ȡ��ʼʱ��
	 * @see Date
	 */
	public void start() {
		
		startTime = (new Date()).getTime();
	}
	
	/**
	 * ��ȡ��ֹʱ��
	 * @see Date
	 */
	public void end() {
		
		endTime = (new Date()).getTime();
	}
	
	/**
	 * ��ȡ��ʼʱ�����ֹʱ���ʱ����
	 * @return ���س�����ʱ����
	 */
	public long getTimeSpan() {
		
		return endTime - startTime;
	}
}
