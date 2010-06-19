package com.iil.dm.core;

import java.util.*;

/**
 * 类型列表。比如"news", "job", "money"等等。
 * 每个分类问题都对应一个类型列表。
 * 对于两类问题，比如是否是招聘网页，类型列表为"job", "notjob"。
 */
public class ClassTypeList {
	
	public static ClassTypeList createList(List types) {
		return new ClassTypeList(types);
	}
	
	/**
     * types中不可有相等的元素。
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