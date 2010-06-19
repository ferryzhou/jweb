package com.iil.dm.core;

import java.util.*;
/**
 * 分类器。
 */
public interface Classifier {

	public void train(Collection labeledInstances);

	/**
	 * 对一个实例进行分类
	 * @param ins 待分类实例
	 * @return 分类结果
	 * List<LabeledInstance>
	 */
	public String classify(Instance ins);
}