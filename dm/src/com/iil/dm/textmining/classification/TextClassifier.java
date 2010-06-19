package com.iil.dm.textmining.classification;

import java.util.*;

import com.iil.dm.textmining.preparation.*;
import com.iil.dm.core.*;

public class TextClassifier implements Classifier{
	
	public TextClassifier(ClassTypeList ctl) {
		this.ctl = ctl;
		ConditionalProbability.setClassTypeList(ctl);
	}
	
	/**
     * Collection<LabeledInstance>
     * TextInstance
     */
	public void train(Collection labeledInstances) {
		System.out.println ("train.....");
		clear();
		this.labeledInstances = labeledInstances;
		typesProb = calcTypeProb(labeledInstances);
		calcDTFforEachType(labeledInstances);
		calcGlobalDTF();
		calcWordsProb();
		featureReduction();
	}
	
	private void clear() {
		dtfMap.clear();
		docNum = 0;
		globalDTF = null;
		dfm = new DocFeatureModel("global", ConditionalProbability.NAME);
	}
	
	private void calcDTFforEachType(Collection labeledInstances) {
		for (int i = 0; i<ctl.size(); i++) {
			String type = ctl.get(i);
			DocTermFrequence dtf = getDocTermFrequenceUnderType(labeledInstances, type);
			dtfMap.put(type, dtf);
		}
	}
	
	private void calcGlobalDTF() {
		globalDTF = new DocTermFrequence("global");
		Iterator iter = dtfMap.values().iterator();
		while (iter.hasNext()) {
			DocTermFrequence temp = (DocTermFrequence)iter.next();
			globalDTF.addDocTermFrequence(temp);
		}
	}
	
	private void calcWordsProb() {
		Set words = globalDTF.getOccuredWords();
		int M = words.size();
		int TM = globalDTF.getTotalWordsNum();
		//double nonp = (double) 1 / TM;
		double nonp = (double) 1 / docNum;
		System.out.println ("nonp: " + nonp);
		for (int i = 0; i<ctl.size(); i++) {
			String type = ctl.get(i);
			DocTermFrequence dtf = (DocTermFrequence)dtfMap.get(type);
			int Nj = dtf.getTotalWordsNum();
			Iterator iter = words.iterator();
			while (iter.hasNext()) {
				String word = (String)iter.next();
				int Nij = dtf.getWordFrequence(word);
				//double prob = (double)(1 + Nij) / (M + Nj);
				/**
				 * calculate P(wj|ci). The jth word's probability under ith type.
				 */
				double prob = (double)(1 + Nij) / (1 + typesFreq[i]);
				/**
				 * remove the noise word.
				 */
				if (Nij <= 2 || prob <= (double)10 / docNum) prob = nonp;
				addWordProbUnderType(word, type, prob);
			}
		}
	}
	
	private Collection getInstancesUnderType(Collection labeledInstances, String type) {
		Collection l = new ArrayList();
		Iterator iter = labeledInstances.iterator();
		while (iter.hasNext()) {
			LabeledInstance li = (LabeledInstance)iter.next();
			if (li.getType().equals(type)) l.add(li.getInstance());
		}
		return l;
	}
	
	private DocTermFrequence getDocTermFrequenceUnderType(Collection labeledInstances, String type) {
		Collection textInstances = getInstancesUnderType(labeledInstances, type);
		//System.out.println ("type: " + type);
		DocTermFrequence dtf = new DocTermFrequence(type);
		Iterator iter = textInstances.iterator();
		while (iter.hasNext()) {
			DocTermFrequence temp = ((TextInstance)iter.next()).getDocTermFrequence();
			dtf.addDocTermFrequence(temp);
		}
		return dtf;
	}
	
	private void addWordProbUnderType(String word, String type, double prob) {
		ConditionalProbability cp = (ConditionalProbability)dfm.getTermFeature(word);
		if (cp == null) {
			cp = new ConditionalProbability();
			cp.setProbUnderType(type, prob);
			dfm.addTermFeature(word, cp);
		} else {
			cp.setProbUnderType(type, prob);
		}
	}
	
	private double[] calcTypeProb(Collection labeledInstances) {
		typesFreq = new int[ctl.size()];
		Iterator iter = labeledInstances.iterator();
		while (iter.hasNext()) {
			String type = ((LabeledInstance)iter.next()).getType();
			typesFreq[ctl.getIndex(type)]++;
		}
		//for (int i = 0; i<typesFreq.length; i++) System.out.println ("type freq " + typesFreq[i]);
		double[] typesProb = new double[ctl.size()];
		docNum = labeledInstances.size();
		for (int i = 0; i<typesFreq.length; i++) {
			typesProb[i] = (double)typesFreq[i]/labeledInstances.size();
			System.out.println ("type " + ctl.get(i) + " prob: " + typesProb[i]);
		}
		return typesProb;
	}
	
	/**
	 * reduce features
	 * remove words that are of low entropy
	 * 
	 */
	private void featureReduction() {
		Collection cps = dfm.getValues();
		Iterator iter = cps.iterator();
		while (iter.hasNext()) {
			ConditionalProbability cp = (ConditionalProbability)iter.next();
			cp.calcEntropy();
		}
		ConditionalProbability threshold = new ConditionalProbability();
		threshold.setEntropy(-0.2);
		dfm = dfm.reduct(ConditionalProbability.getEntropyComparator(), threshold);
	}
	
	public DocFeatureModel getDocFeatureModel() {
		return dfm;
	}
	
	/************************ Classify **********************************/
	
	public String classify(Instance ins) {
		return classifyText((TextInstance)ins);
	}
	
	public String classifyText(TextInstance text) {
		String type = null;
		double maxProb = 0;
		for (int i = 0; i<ctl.size(); i++) {
			if (i == 0) {
				type = ctl.get(i);
				maxProb = clacTypeProbUnderText(text, type);
				continue;
			}
			String tempType = ctl.get(i);
			double tempProb = clacTypeProbUnderText(text, tempType);
			if (maxProb < tempProb) {
				type = tempType;
				maxProb = tempProb;
			}
		}
		return type;
	}
	
	public double clacTypeProbUnderText(TextInstance text, String type) {
		return calcTypeProb(type) + clacTextProbUnderType(text, type);
	}
	
	public double calcTypeProb(String type) {
		return Math.log(typesProb[ctl.getIndex(type)]);
	}
	
	public double clacTextProbUnderType(TextInstance text, String type) {
		double prob = 0;
		DocTermFrequence dtf = text.getDocTermFrequence();
		Set words = dtf.getOccuredWords();
		Iterator iter = words.iterator();
		while (iter.hasNext()) {
			String word = (String)iter.next();
			double wt = getWordProbUnderType(word, type);
			if (Math.abs(wt - 0.0) < 1.0E-10) continue;
			//System.out.println (word + " " + type + " " + wt);
			prob += dtf.getWordFrequence(word) * Math.log(wt);
		}
		return prob;
	}
	
	private double getWordProbUnderType(String word, String type) {
		ConditionalProbability cp = (ConditionalProbability)dfm.getTermFeature(word);
		if (cp == null) return 0.0;
		else return cp.getProbUnderType(type);
	}

	/************************ Classify **********************************/

	private Collection labeledInstances;
	/**
	 * Map<type, DTF>
	 * see calcDTFforEachType
	 */
	private Map dtfMap = new HashMap();
	private int docNum;
	private int[] typesFreq;
	private DocTermFrequence globalDTF;

	/**
     * term - ConditionalProbability
     */
	private DocFeatureModel dfm;
	private double[] typesProb;
	private ClassTypeList ctl;
}

