package com.iil.util;

/**
 * �������ӿڡ�
 * @author Ferry
 * @version 1.0
 */
public interface Filter {
	
	/**
	 * �ж�һ�������Ƿ񱻽��ܡ�
	 * @param o Ҫ�жϵĶ���
	 * @return ������Խ��ܷ���true�����򷵻�false
	 */
	public boolean accept(Object o);
}
