package com.iil.util;

import java.util.*;

/**
 * �����ӡList, Map, ����֮��Ķ���
 * @author Ferry
 * @version 1.0
 */
public class ObjectPrinter {
	
	/**
	 * ��ӡ�б����б��ÿһ��Ԫ�ض���\r\n�Ժ�
	 * Ȼ�����������ŵ�һ��string��������
	 * @param l ��Ҫ��ӡ���б�
	 * @return ��������ø�ʽ�������ַ���string����
	 */
	public static String print(List l) {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		Iterator iter = l.iterator();
		while (iter.hasNext()) {
			Object o = iter.next();
			sb.append(o + "\r\n");
		}
		return sb.toString();
	}
	
	/**
	 * ��ӡmap����map�е�ÿһ��Ԫ������������ӡ����
	 * �����ʽ��key->value\r\n
	 * @param map ��Ҫ��ӡ��map����
	 * @return ��������ø�ʽ�������ַ���
	 */
	public static String print(Map map) {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		Set entries = map.entrySet();
		Iterator iter = entries.iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			sb.append(key + " -> " + value + "\r\n");
		}
		return sb.toString();
	}
	
	/**
	 * ��ӡ�ַ������飬ÿһ���������ַ�������������\r\n��
	 * Ȼ��������һ��
	 * @param sarray ��Ҫ��ӡ���ַ�������
	 * @return ��������ø�ʽ�������ַ���
	 */
	public static String print(String[] sarray) {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		for (int i = 0; i<sarray.length; i++) {
			sb.append(sarray[i] + "\r\n");
		}
		return sb.toString();
	}

	/**
	 * ��ӡ�ַ������飬��ʽ���������ݷ���һ��[]�У�
	 * ÿһ���ַ������涼����","��������һ��
	 * @param sarray ��Ҫ��ӡ���ַ�������
	 * @return ��������ø�ʽ�������ַ���
	 */
	public static String print2(String[] sarray) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i<sarray.length; i++) {
			sb.append(sarray[i] + ",");
		}
		sb.append("]");
		return sb.toString();
	}
}