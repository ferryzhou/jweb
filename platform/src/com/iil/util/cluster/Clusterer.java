/*
 * Created on 2004-7-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.util.cluster;

import java.util.*;
/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Clusterer {
	/**
     * @return Collection<Collection<Object>>
     */
	public static Collection cluster(Collection objects, Similar similar) {
		return cluster(objects, similar, 2);
	}
	
	public static Collection cluster(Collection objects, Similar similar, int clusterminsize) {
		Clusterer clusterer = new Clusterer(objects, similar, clusterminsize);
		return clusterer.cluster();
	}

	public Clusterer(Collection objects, Similar similar) {
		this.objects = objects;
		this.similar = similar;
	}
	
	public Clusterer(Collection objects, Similar similar, int clusterminsize) {
		this.objects = objects;
		this.similar = similar;
		this.CLUSTER_MIN_SIZE = clusterminsize;
	}

	public Collection cluster() {
		clusters = new LinkedList();
		sparseObjects = new LinkedList();
		Iterator iter = objects.iterator();
		while (iter.hasNext()) {
			addObject(iter.next(), clusters, similar);
		}
		removeSparseClusters(clusters);
		return clusters;
	}
	
	public Collection getSparseObjects() {
		return Collections.unmodifiableCollection(sparseObjects);
	}
	
	public Collection getClusters() {
		return Collections.unmodifiableCollection(clusters);
	}
	
	public Collection getMaxCluster() {
		int max = -1;
		Collection maxCluster = null;
		Iterator iter = clusters.iterator();
		while (iter.hasNext()) {
			Collection cluster = (Collection)iter.next();
			if (cluster.size() > max) {
				maxCluster = cluster;
				max = cluster.size();
			}
		}
		return maxCluster;
	}
	
	private void addObject(Object o, Collection clusters, Similar similar) {
		Iterator iter = clusters.iterator();
		while (iter.hasNext()) {
			Collection cluster = (Collection)iter.next();
			Object first = cluster.iterator().next();
			if (similar.isSimilar(o, first)) {
				cluster.add(o);
				return;
			}
		}
		//没有找到一个属于的cluster
		//新建
		Collection newCluster = new LinkedList();
		newCluster.add(o);
		clusters.add(newCluster);
	}

	private void removeSparseClusters(Collection clusters) {
		Iterator iter = clusters.iterator();
		while (iter.hasNext()) {
			Collection cluster = (Collection)iter.next();
			if (cluster.size() < CLUSTER_MIN_SIZE) {
				sparseObjects.addAll(cluster);
				iter.remove();
			}
		}
	}
	
	private Collection clusters;
	private Collection sparseObjects;
	private Collection objects;
	private Similar similar;
	private int CLUSTER_MIN_SIZE = 3;

}
