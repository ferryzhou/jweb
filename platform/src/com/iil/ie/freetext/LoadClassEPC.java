/*
 * Created on 2004-7-18
 *
 */
package com.iil.ie.freetext;

/**
 * @author ferry zhou
 *
 */
public class LoadClassEPC implements EPatternConstructor{
	
	public LoadClassEPC(String classname) throws Exception{
		Class cls = Class.forName(classname);
		ep = (EPattern)cls.newInstance();
	}
	
	public EPattern getEPattern() {
		return ep;
	}
	
	private EPattern ep;
}
