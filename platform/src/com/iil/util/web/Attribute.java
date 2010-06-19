package com.iil.util.web;

/**
 * @author Ferry
 * @version 1.0
 */
public class Attribute {
	
	/**
	 * Attribute的构造函数
	 * @param key 需要使用的属性名称
	 * @param value 需要使用的属性值
	 */
	public Attribute(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * 获取属性名称
	 * @return 返回属性名称
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * 获取属性值
	 * @return 返回属性值
	 */
	public String getValue() {
		return value;
	}
	
	public void setValue(String v) {
		this.value = v;
	}

	public String toString() {
		return key + "=" + value;
	}
	
	/**
	 * 属性名称
	 */
	private String key;
	
	/**
	 * 属性值
	 */
	private String value;
}