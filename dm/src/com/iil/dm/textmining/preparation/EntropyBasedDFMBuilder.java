package com.iil.dm.textmining.preparation;

import java.util.*;

public class EntropyBasedDFMBuilder extends DocFeatureModelBuilder {
	
	public EntropyBasedDFMBuilder(List dtfs) {
		super(dtfs);
	}
	
	public void buildDFMs() {
		DocFeatureModel entropydfm = calcTotalEntropys();
		System.out.println (entropydfm);
		Iterator dociter = dtfs.iterator();
		while (dociter.hasNext()) {
			//build dfm for the kth doc
			DocTermFrequence dtfk = (DocTermFrequence)dociter.next();
			DocFeatureModel dfmk = new DocFeatureModel(dtfk.getDocId(), "Entropy");
			Iterator termiter = dtfk.getOccuredWords().iterator();
			while (termiter.hasNext()) {
				//calc weight for the ith term
				String ti = (String)termiter.next();
				int fik = dtfk.getWordFrequence(ti);
				double ei = ((Weight)entropydfm.getTermFeature(ti)).getWeight();
				double wik = Math.log(fik + 1.0) * (1 + ei);
				dfmk.addTermFeature(ti, new Weight(wik));
			}
			dfms.add(dfmk);
		}
	}
	
	public void featureReduction() {
	}
	
	/**
     * calculate entropy for every term in all documents;
     */
	private DocFeatureModel calcTotalEntropys() {
		DocFeatureModel dfm = new DocFeatureModel("total", "Entropy");
		int N = getDocNum();
		List entries = totaltf.getAllEntries();
		Iterator iter = entries.iterator();
		while (iter.hasNext()) {
			//calc entropy for the ith term
			Map.Entry entry = (Map.Entry)iter.next();
			String ti = (String)entry.getKey();
			int ni = ((Frequence)entry.getValue()).getFrequence();
			double totalentropy = 0;
			//calc total entropy
			Iterator dociter = dtfs.iterator();
			while (dociter.hasNext()) {
				//calc entropy in jth document
				DocTermFrequence dtfj = (DocTermFrequence)dociter.next();
				int fij = dtfj.getWordFrequence(ti);
				//System.out.println ("fij: " + fij);
				double eij;
				if (fij != 0) eij = ((double)fij/ni) * Math.log((double)fij/ni);
				else eij = 0;
				//System.out.println ("eij: " + eij);
				totalentropy += eij;
			}
			//System.out.println ("te: " + totalentropy);
			double ei;
			if (totalentropy != 0) ei = totalentropy / Math.log(N);
			else ei = 0.0;
			dfm.addTermFeature(ti, new Weight(ei));
		}
		return dfm;
	}
	
	private DocFeatureModel totalentropys;
}