package com.iil.dm.textmining.classification;

import java.util.*;

import com.iil.dm.textmining.preparation.*;
import com.iil.dm.core.*;

public class ConditionalProbability implements Feature{

	public static final String NAME = "ConditionalProbability";
	
	public String getName() {
		return NAME;
	}
	
	public ConditionalProbability() {
		if (ctl != null) cps = new double[ctl.size()];
	}
	
	public static void setClassTypeList(ClassTypeList c) {
		ctl = c;
	}

	public void setProbUnderType(String type, double prob) {
		cps[ctl.getIndex(type)] = prob;
	}

	public double getProbUnderType(String type) {
		return cps[ctl.getIndex(type)];
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i<cps.length; i++) {
			sb.append(ctl.get(i) + ": " + cps[i] + "\r\n");
		}
		sb.append("entropy: " + entropy + "\r\n");
		return sb.toString();
	}
	
	public void setEntropy(double d) {
		this.entropy = d;
	}
	
	public void calcEntropy() {
		double t = 0.0;
		for (int i = 0; i<cps.length; i++) {
			t += cps[i];
		}
		double[] ps = new double[cps.length];
		for (int i = 0; i<ps.length; i++) {
			ps[i] = cps[i] / t;
			entropy += ps[i] * Math.log(ps[i]) / Math.log(2.0);
		}
	}
	
	public static Comparator getEntropyComparator() {
		return new Comparator() {
			public int compare(Object o1, Object o2) {
				ConditionalProbability f1 = (ConditionalProbability)o1;
				ConditionalProbability f2 = (ConditionalProbability)o2;
				if (f1.entropy - f2.entropy < 1.0E-10) return -1;
				if (f1.entropy - f2.entropy > 1.0E-10) return 1;
				return 0;
			}
		};	
	}
	
	private double[] cps;
	private double entropy;
	private static ClassTypeList ctl = null;
}
