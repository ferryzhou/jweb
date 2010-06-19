package com.iil.util;

/**
 * 过滤器接口。
 * @author Ferry
 * @version 1.0
 */
public interface Filter {
	
	/**
	 * 判断一个对象是否被接受。
	 * @param o 要判断的对象
	 * @return 如果可以接受返回true，否则返回false
	 */
	public boolean accept(Object o);
}
