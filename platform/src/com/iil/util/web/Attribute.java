package com.iil.util.web;

/**
 * @author Ferry
 * @version 1.0
 */
public class Attribute {
	
	/**
	 * Attribute�Ĺ��캯��
	 * @param key ��Ҫʹ�õ���������
	 * @param value ��Ҫʹ�õ�����ֵ
	 */
	public Attribute(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * ��ȡ��������
	 * @return ������������
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * ��ȡ����ֵ
	 * @return ��������ֵ
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
	 * ��������
	 */
	private String key;
	
	/**
	 * ����ֵ
	 */
	private String value;
}