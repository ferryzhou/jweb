package com.iil.util;

import java.util.*;

/**
 * 负责打印List, Map, 数组之类的对象。
 * @author Ferry
 * @version 1.0
 */
public class ObjectPrinter {
	
	/**
	 * 打印列表，将列表的每一个元素都加\r\n以后，
	 * 然后连接起来放到一个string对象里面
	 * @param l 需要打印的列表
	 * @return 返回整理好格式的数据字符串string对象
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
	 * 打印map，将map中的每一个元素连接起来打印出来
	 * 具体格式是key->value\r\n
	 * @param map 需要打印的map对象
	 * @return 返回整理好格式的数据字符串
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
	 * 打印字符串数组，每一个数组中字符串对象后面加上\r\n，
	 * 然后连接在一起
	 * @param sarray 需要打印的字符串数组
	 * @return 返回整理好格式的数据字符串
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
	 * 打印字符串数组，格式是整个数据放在一对[]中，
	 * 每一个字符串后面都加上","，连接在一起
	 * @param sarray 需要打印的字符串数组
	 * @return 返回整理好格式的数据字符串
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