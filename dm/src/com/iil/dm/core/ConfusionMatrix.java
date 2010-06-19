/*
 * Created on 2004-7-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.dm.core;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConfusionMatrix {
	
	public ConfusionMatrix(ClassTypeList ctl, int[][] conf) {
		this.ctl = ctl;
		this.confusion = conf;
		trueRates = getTrueRates(confusion);
		accuracy = getAccuracy(confusion);				
	}
	
	private double[] getTrueRates(int[][] confusion)
	{
		double[] r = new double[confusion.length];
		for(int i = 0; i < r.length; i++)
		{
			int total = calTotal(confusion[i]);
			if(total == 0) {r[i] = 0.0;}
			else {r[i] = (double) confusion[i][i] / total;}
		}
		return r;
	}

	private double getAccuracy(int[][] confusion)
	{
		int at = 0;
		int total = 0;
		double acc = 0;
		for(int i = 0; i < confusion.length; i++)
		{
			at += confusion[i][i];
			for(int j = 0; j < confusion[i].length; j++)
			{
				total += confusion[i][j];
			}
		}
		if(total == 0) {acc = 0.0;}
		else {acc = (double) at / total;}
		return acc;
	}
	
	private int calTotal(int[] s)
	{
		int total = 0;
		for(int i = 0; i < s.length; i++)
		{
			total += s[i];
		}
		return total;
	}

	public String toString()
	{
		String s = "";
		String ret = "\r\n";
		s += "actual/predict\t";
		for(int i = 0; i < ctl.size(); i++)
		{
			s += ctl.get(i) + "\t";
		}
		s += "true rate";
		s += ret;
		for(int i = 0; i < ctl.size(); i++)
		{
			s += ctl.get(i) + "\t\t";
			for(int j = 0; j < confusion[i].length; j++)
			{
				s += confusion[i][j] + "\t";
			}
			s += trueRates[i] + ret;
		}
		s += ret + "accuracy: " + accuracy + ret;
		return s;
	}
	
	private ClassTypeList ctl;
	private int[][] confusion;
	private double[] trueRates;
	private double accuracy = 0;

}
