package com.iil.ie.freetext;

public class Struct {
	
	public Struct(Object[] struct) {
		this.struct = struct;
	}
	
	public Object[] getStruct() {
		return this.struct;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<struct.length; i++) {
			sb.append(struct[i] + "\r\n");
		}
		return sb.toString();
	}
	
	private Object[] struct;
}
