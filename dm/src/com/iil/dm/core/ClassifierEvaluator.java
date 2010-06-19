package com.iil.dm.core;

import java.util.*;

/**
* 评估一个分类器的性能。
* 也就是拿一组测试测试来，拿分类器进行分类，计算各种错误率。
*
* @author ferry zhou
* @version 1.0, 2003-9-28
*/

public class ClassifierEvaluator
{
	private ClassTypeList ctl;
	private Classifier classifier;
	/**
	*confusion matrix:
	*	actual \ predicted	att1	att2	...
 	*	     att1         	 a  	 b  	...
	*	     att2         	 c  	 d  	...
	*	      :           	 :  	 :  	 :
	*
	*trueRates att1 = a/(a+b+);
	*accuracy = (a+d) / (a+b+c+d);
	*/
	private int[][] confusion;
	private double[] trueRates;
	private double accuracy = 0;
	
	public ClassifierEvaluator(ClassTypeList ctl, Classifier classifier)
	{
		this.ctl = ctl;
		this.classifier = classifier;
		confusion = new int[ctl.size()][ctl.size()];
		trueRates = new double[ctl.size()];
	}
	
	public void clear() {
		for (int i = 0; i < confusion.length; i++) {
			for (int j = 0; j < confusion[i].length; j++) {
				confusion[i][j] = 0;
			}
		}
		for (int i = 0; i < trueRates.length; i++) {
			trueRates[i] = 0.0;
		}
		accuracy = 0.0;
	}
	
	public ConfusionMatrix ncrossTest(int n, List labeledInstances) {
		//clear();
		int[][] totalconf = new int[ctl.size()][ctl.size()];
		TrainandTestInstances[] ttis = getNCrossTestInstances(n, labeledInstances);
		for (int i = 0; i < n; i++) {
			classifier.train(ttis[i].trainInstances);
			int[][] conf = test(classifier, ttis[i].testInstances);
			addConfusion(totalconf, conf);
			System.out.println(new ConfusionMatrix(ctl, conf));
		}
		return new ConfusionMatrix(ctl, totalconf);
	}
	
	private void addConfusion(int[][] total, int[][] confusion) {
		for (int i = 0; i < confusion.length; i++) {
			for (int j = 0; j < confusion[i].length; j++) {
				total[i][j] += confusion[i][j];
			}
		}
	}
	/**
	 * split train instances in n ways. 
	 * @param n n-cross.
	 * @param labeledInstances
	 * @return
	 */
	private TrainandTestInstances[] getNCrossTestInstances(int n, List labeledInstances) {
		TrainandTestInstances[] ncls = new TrainandTestInstances[n];
		List[] ls = splitInstances(n, labeledInstances);
		for (int i = 0; i < n; i++) {
			ncls[i] = new TrainandTestInstances();
			ncls[i].testInstances.addAll(ls[i]);
			for (int j = 0; j < n; j ++) {
				if (j != i) ncls[i].trainInstances.addAll(ls[j]);
			}
		}
		return ncls;
	}
	
	private List[] splitInstances(int n, List labeledInstances) {
		List[] ls = new LinkedList[n];
		int span = labeledInstances.size() / n;
		//[0, span-1], [span, 2span-1]....[(n-1)span, size()]
		for (int i = 0; i < n; i++) {
			ls[i] = new LinkedList();
			if (i == n-1) ls[i].addAll(labeledInstances.subList(i * span, labeledInstances.size()));
			else ls[i].addAll(labeledInstances.subList(i * span, (i + 1) * span - 1));
		}
		return ls;
	}
	
	/**
     * List<LabledInstances>
     */
	public int[][] test(Classifier classifier, List labeledInstances) {
		int[][] conf = new int[ctl.size()][ctl.size()];
		Iterator iter = labeledInstances.iterator();
		while (iter.hasNext()) {
			LabeledInstance li = (LabeledInstance)iter.next();
			Instance ins = li.getInstance();
			String type = li.getType();
			//System.out.println ("instance type: " + type);
			int actual = ctl.getIndex(type);
			int predict = ctl.getIndex(classifier.classify(ins));
			conf[actual][predict] ++;
		}
		//trueRates = getTrueRates(conf);
		//accuracy = getAccuracy(conf);
		return conf;
	}
	
	public static void main(String[] args)
	{
		System.out.println("hello TestClassifier!");
	}
}

class TrainandTestInstances {
	public List trainInstances = new LinkedList();
	public List testInstances = new LinkedList();
}