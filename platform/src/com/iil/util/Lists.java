package com.iil.util;

import java.util.*;

/**
 * ���б�������һЩ�����������
 * @author Ferry
 * @version 1.0
 */
public class Lists {	

	/**
	 * ��ȡԭ�б�ǰn��Ԫ����ɵ��б�
	 * @param l ��Ҫ������б����
	 * @param n Ҫ��ȡ��Ԫ����Ŀ
	 * @return ����ԭ�б�ǰn��Ԫ����ɵ��б����
	 * @see List
	 */
	public static List getN(List l, int n) {
		int min = Math.min(l.size(), n);
		return l.subList(0, min);
	}

	/**
     * ��ȡ����ǰn��Ԫ���б�
	 * @param l ��Ҫ��ȡ���б��ԭ�б�
	 * @param n ��Ҫ��ȡ��Ԫ����
	 * @return ��������ǰn��Ԫ���б�
	 */
	public static List getTopN(List l, int n) {
		return getTopN(l, n, null);
	}
	
	/**
     * ��ȡ����ǰn��Ԫ���б�
	 * @param l ��Ҫ��ȡ���б��ԭ�б�
	 * @param n ��Ҫ��ȡ��Ԫ����
	 * @param c ��Ҫʹ�õıȽ���
	 * @return ��������ǰn��Ԫ���б�
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
     * ��ȡ����ǰrate(20%)��Ԫ���б�
	 * @param l ��Ҫ���˵��б����
	 * @param rate ��Ҫ��ȡ�õ��ٷֱ�
	 * @return ��������ǰrate(20%)��Ԫ���б�
	 */
	public static List getTopNRate(List l, double rate) {
		int n = (int)(rate * l.size());
		return getTopN(l, n);
	}
	
	/**
	 * ���б��еĶ�����й��ˣ������˺��Ԫ�ر������µ��б������
	 * @param l ��Ҫ���˵��б����
	 * @param f ����ʹ�õĹ���������
	 * @return �����˳����Ϸ��Ķ������б�
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