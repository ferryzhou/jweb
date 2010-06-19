package com.iil.dm.textmining.preparation;

import java.util.*;

/**
 * doc -> Map<term, feature>
 * 
 * @author ferry zhou
 *
 */
public class DocFeatureModel {
	
	public DocFeatureModel(String docid, String featureName) {
		this.id = docid;
		this.featureName = featureName;
	}
	
	public DocFeatureModel(String docid, String featureName,Map termFeatureMap) {
		this.id = docid;
		this.featureName = featureName;
		this.termFeatureMap = termFeatureMap;
	}

	public void addTermFeature(String term, Feature feature) {
		this.termFeatureMap.put(term, feature);
	}
	
	public Set getOccuredWords() {
		return termFeatureMap.keySet();
	}
	
	public String getId() {
		return id;
	}
	
	public String getFeatureName() {
		return featureName;
	}
	
	public Feature getTermFeature(String term) {
		return (Feature)termFeatureMap.get(term);
	}

	public List getSortedEntries(final Comparator featureComparator) {
		List l = getAllEntries();
		if (featureComparator == null) return l;
		Collections.sort(l, (new Comparator() {
				public int compare(Object o1, Object o2) {
					Feature f1 = (Feature)((Map.Entry)o1).getValue();
					Feature f2 = (Feature)((Map.Entry)o2).getValue();
					return featureComparator.compare(f1, f2);
				}
			}));
		Collections.reverse(l);
		return l;
	}
	
	public DocFeatureModel reduct(Comparator featureComparator, Feature threshold) {
		Map m = new HashMap();
		Set entries = this.termFeatureMap.entrySet();
		Iterator iter = entries.iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			if (featureComparator.compare(entry.getValue(), threshold) > 0) {
				m.put(entry.getKey(), entry.getValue());
			}
		}
		return new DocFeatureModel(this.id, this.featureName, m);
	}
	
	public void setFermFeatureMap(Map m) {
		this.termFeatureMap = m;
	}

	public List getAllEntries() {
		return new LinkedList(termFeatureMap.entrySet());
	}
	
	public Collection getValues() {
		return this.termFeatureMap.values();
	}
	
	public String toString(final Comparator featureComparator) {
		StringBuffer sb = new StringBuffer();
		sb.append(id + " ===============================\r\n");
		Iterator iter = getSortedEntries(featureComparator).iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			sb.append(entry.getKey() + " -- " + entry.getValue());
			sb.append("\r\n");
		}
		sb.append("words num: " + termFeatureMap.size() + "\r\n"); 
		return sb.toString();
	}
	
	public String toString() {
		return toString(null);
	}
	
	private Map termFeatureMap = new HashMap();
	private String id;
	private String featureName;
}
