package com.iil.util;

import java.util.*;

/**
 * 对列表对象进行一些特殊操作的类
 * @author Ferry
 * @version 1.0
 */
public class Lists {	

	/**
	 * 获取原列表前n的元素组成的列表。
	 * @param l 需要处理的列表对象
	 * @param n 要获取的元素数目
	 * @return 返回原列表前n的元素组成的列表对象
	 * @see List
	 */
	public static List getN(List l, int n) {
		int min = Math.min(l.size(), n);
		return l.subList(0, min);
	}

	/**
     * 获取排名前n的元素列表。
	 * @param l 需要获取子列表的原列表
	 * @param n 需要获取的元素数
	 * @return 返回排名前n的元素列表
	 */
	public static List getTopN(List l, int n) {
		return getTopN(l, n, null);
	}
	
	/**
     * 获取排名前n的元素列表。
	 * @param l 需要获取子列表的原列表
	 * @param n 需要获取的元素数
	 * @param c 需要使用的比较器
	 * @return 返回排名前n的元素列表
	 * @see Math
	 * @see Collections
	 */
	public static List getTopN(List l, int n, Comparator c) {
		if (c != null) Collections.sort(l, c);
		else Collections.sort(l);
		Collections.reverse(l);
		int min = Math.min(l.size(), n);
		return l.subList(0, min);
	}

	/**
     * 获取排名前rate(20%)的元素列表。
	 * @param l 需要过滤的列表对象
	 * @param rate 需要抽取得到百分比
	 * @return 返回排名前rate(20%)的元素列表
	 */
	public static List getTopNRate(List l, double rate) {
		int n = (int)(rate * l.size());
		return getTopN(l, n);
	}
	
	/**
	 * 对列表中的对象进行过滤，将过滤后的元素保存在新的列表对象中
	 * @param l 需要过滤的列表对象
	 * @param f 过滤使用的过滤器对象
	 * @return 返回滤除不合法的对象后的列表
	 * @see Lists
	 */
	public static List filter(List l, Filter f) {
		List n = new LinkedList();
		Iterator iter = l.iterator();
		while (iter.hasNext()) {
			Object o = iter.next();
			if (f.accept(o)) {
				n.add(o);
			}
		}
		return n;
	}
}