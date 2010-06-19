package com.iil.dm.core;

import java.util.*;

/**
 * �����б�����"news", "job", "money"�ȵȡ�
 * ÿ���������ⶼ��Ӧһ�������б�
 * �����������⣬�����Ƿ�����Ƹ��ҳ�������б�Ϊ"job", "notjob"��
 */
public class ClassTypeList {
	
	public static ClassTypeList createList(List types) {
		return new ClassTypeList(types);
	}
	
	/**
     * types�в�������ȵ�Ԫ�ء�
     */
	private ClassTypeList(List types) {
		this.types = types;
	}
	
	public String getClassType(String type) {
		return (String)types.get(types.indexOf(type));
	}
	
	public int size() {
		return types.size();
	}
	
	public String get(int i) {
		return (String)types.get(i);
	}
	
	public int getIndex(String type) {
		int index = types.indexOf(type);
		if (index == -1) throw new IllegalArgumentException();
		return index;
	}
	
	public String toString() {
		return types.toString();
	}
	
	private List types;
}