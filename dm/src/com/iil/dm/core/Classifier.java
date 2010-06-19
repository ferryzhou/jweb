package com.iil.dm.core;

import java.util.*;
/**
 * ��������
 */
public interface Classifier {

	public void train(Collection labeledInstances);

	/**
	 * ��һ��ʵ�����з���
	 * @param ins ������ʵ��
	 * @return ������
	 * List<LabeledInstance>
	 */
	public String classify(Instance ins);
}