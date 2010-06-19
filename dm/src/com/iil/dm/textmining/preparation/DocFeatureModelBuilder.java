package com.iil.dm.textmining.preparation;

import java.util.*;

/**
 * input: a group of doctermfrequence objects
 * output: a group of docfeaturemoel objects
 */
public abstract class DocFeatureModelBuilder {
	
	public DocFeatureModelBuilder(List dtfs) {
		this.dtfs = dtfs;
	}
	
	public void build() {
		totaltf = calcTotalTF();
		System.out.println (totaltf);
		buildDFMs();
		featureReduction();
	}
	
	public List getDocFeatureModels() {
		return this.dfms;
	}
	
	public int getDocNum() {
		return dtfs.size();
	}
	
	private DocTermFrequence calcTotalTF() {
		DocTermFrequence tf = new DocTermFrequence("total");
		Iterator iter = dtfs.iterator();
		while (iter.hasNext()) {
			DocTermFrequence dtf = (DocTermFrequence)iter.next();
			tf.addDocTermFrequence(dtf);
		}
		return tf;
	}
	
	protected abstract void buildDFMs(); 
	protected abstract void featureReduction();
	
	//List<DocTermFrequence>
	protected List dtfs;
	protected DocTermFrequence totaltf;
	
	//List<DocFeatureModel>
	protected List docsFeaturesModel = new LinkedList();
	protected DocFeaturesModel globalFeaturesModel;
	//List<DocFeatureModel>
	protected List dfms = new LinkedList();
}
