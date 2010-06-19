package com.iil.dm.core;

/**
 * ���б�ǵ�ʵ����
 */
public class LabeledInstance {
	
	public LabeledInstance(Instance instance, String type) {
		this.instance = instance;
		this.type = type;
	}
	
	public Instance getInstance() {
		return this.instance;
	}
	
	public String getType() {
		return this.type;
	}
	
	private Instance instance;
	private String type;
}
