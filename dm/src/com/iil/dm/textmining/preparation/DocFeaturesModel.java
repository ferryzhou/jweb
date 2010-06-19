package com.iil.dm.textmining.preparation;

import java.util.*;

/**
 * doc -> Map<term - Map <featurename, featureobject>>
 */
public class DocFeaturesModel {
	
	public DocFeaturesModel(String docid) {
		this.id = docid;
	}
	
	public void addTermFeature(String term, Feature feature) {
		Map m = (Map)termFeaturesMap.get(term);
		if (m == null) {
			m = new HashMap();
			m.put(feature.getName(), feature);
			this.termFeaturesMap.put(term, m);
		} else {
			m.put(feature.getName(), feature);
		}
	}
	
	public Set getOccuredWords() {
		return termFeaturesMap.keySet();
	}
	
	public Feature getTermFeature(String term, String featureName) {
		Map featureMap = (Map)termFeaturesMap.get(term);
		if (featureMap != null) {
			return (Feature)featureMap.get(featureName);
		} else return null;
	}

	public List getSortedEntries(final String featureName, final Comparator featureComparator) {
		List l = getAllEntries();
		Collections.sort(l, (new Comparator() {
				public int compare(Object o1, Object o2) {
					Feature w1 = (Feature)((Map)((Map.Entry)o1).getValue()).get(featureName);
					Feature w2 = (Feature)((Map)((Map.Entry)o2).getValue()).get(featureName);
					return featureComparator.compare(w1, w2);
				}
			}));
		Collections.reverse(l);
		return l;
	}

	public List getAllEntries() {
		return new LinkedList(termFeaturesMap.entrySet());
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(id + " ===============================\r\n");
/*		Iterator iter = getSortedEntries().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			sb.append(entry.getKey() + " -- " + entry.getValue());
			sb.append("\r\n");
		}
*/		sb.append("words num: " + termFeaturesMap.size() + "\r\n"); 
		return sb.toString();
	}
	
	/**
     * Map<term - Map <featurename, featureobject>>
     */
	private Map termFeaturesMap = new HashMap();
	private String id;
}
