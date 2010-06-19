package com.iil.dm.textmining.classification.test;

import java.util.*;
import java.io.*;


import com.iil.dm.core.*;

import com.iil.word.*;
import com.iil.word.impl.*;

import com.iil.dm.textmining.classification.*;
import com.iil.dm.textmining.preparation.*;

public class Test_TextClassifier {
	
	public void init() throws IOException{
		System.out.println ("load dict.........");
		dict = DictLoader.load("dict.xml");
		System.out.println ("load end.........");
    	wsi = new WordSegmenterImpl(dict);
    	ws = new ChineseWordSplitter(wsi);
    	//ws = new SimpleWordSplitter();
		
		List typelist = new LinkedList();
		samples = new LinkedList();
		String datadirname = "F:\\zj\\eclipse\\dm\\dm\\data\\samples";//"F:\\zj\\work\\Project\\platform\\src\\com\\iil\\classifier\\test\\samples";
		File datadir = new File(datadirname);
		String[] types = datadir.list();
		//System.out.println (types.length);
		for (int i = 0; i<types.length; i++) {
			File type = new File(datadirname + "\\" + types[i]);
			if (!type.isDirectory()) continue;
			String typename = types[i];
			typelist.add(typename);
			String[] files = type.list();
			for (int j = 0; j<files.length; j++) {
				String file = datadirname + "\\" + typename + "\\" + files[j];
				LabeledInstance li = getLabeledInstance(file, typename);
				int random = (int)(Math.random() * samples.size());
				samples.add(random, li);
			}
		}
		ctl = ClassTypeList.createList(typelist);
		
		testdata = getTestData();
	}
	
	public List getTestData() throws IOException{
		List data = new LinkedList();
		String datadirname = "F:\\zj\\work\\Project\\platform\\src\\com\\iil\\classifier\\test\\test\\samples5";
		File datadir = new File(datadirname);
		String[] types = datadir.list();
		System.out.println (types.length);
		for (int i = 0; i<types.length; i++) {
			File type = new File(datadirname + "\\" + types[i]);
			if (!type.isDirectory()) continue;
			String typename = types[i];
			String[] files = type.list();
			for (int j = 0; j<files.length; j++) {
				String file = datadirname + "\\" + typename + "\\" + files[j];
				LabeledInstance li = getLabeledInstance(file, typename);
				data.add(li);
			}
		}
		return data;
	}
	
	public void test() throws IOException{
		//com.iil.util.IO.readWriteAll(new StringReader("adfdfa"), 
		//		new FileWriter("dfm.txt"));
		init();
		System.out.println (ctl);
		TextClassifier tc = new TextClassifier(ctl);
		//tc.train(samples);
		ClassifierEvaluator ce = new ClassifierEvaluator(ctl, tc);
		ConfusionMatrix cm = ce.ncrossTest(5, samples);
		com.iil.util.IO.readWriteAll(new StringReader(tc.getDocFeatureModel().toString()), 
				new FileWriter("dfm.txt"));
		System.out.println (cm);
	}
	
	public LabeledInstance getLabeledInstance(String file, String type) throws IOException{
		Reader r = new FileReader(file);
		String docid = file;
		TextInstance ti = new TextInstance(DocTermFrequence.createFromReader(docid, r, ws));
		//TextInstance ti = TextInstance.createFromInputStream(is);
		LabeledInstance li = new LabeledInstance(ti, type);
		return li;
	}
	
	private static Dict dict;
    private static WordSegmenterImpl wsi;
    private static WordSplitter ws;

	private ClassTypeList ctl;
	private List samples;
	private List testdata;
	
	public static void main(String[] args) throws IOException{
		Test_TextClassifier t = new Test_TextClassifier();
		t.test();
	}
}