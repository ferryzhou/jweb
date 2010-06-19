package com.iil.dm.textmining.preparation.test;

import java.io.*;
import java.util.*;

import com.iil.word.*;
import com.iil.dm.textmining.preparation.*;

public class Test_DocFeatureModelBuilder {
	
	public void test() throws Exception{
		List dtfs = creatDTFs("F:\\zj\\test\\java\\clustering\\sourceI\\");
		DocFeatureModelBuilder dfmb = new EntropyBasedDFMBuilder(dtfs);
		dfmb.build();
		//System.out.println (dfmb.getDocFeatureModels().get(0));
		//System.out.println (dfmb.getDocFeatureModels().get(10));
		System.out.println (dfmb.getDocFeatureModels().get(11));
		//System.out.println (dfmb.getDocFeatureModels().get(12));
	}
	
	public List creatDTFs(String dir) throws Exception{
		List dtfs = new LinkedList();
		WordSplitter ws = new EnglishWordSplitter(StopListReader.load("F:\\zj\\test\\java\\clustering\\stopList.txt"));
		File dirf = new File(dir);
		String[] files = dirf.list();
		for (int i = 0; i<files.length; i++) {
			String file = dir + files[i];
			Reader r = new InputStreamReader(new FileInputStream(file));
			DocTermFrequence dtf = DocTermFrequence.createFromReader(files[i], r, ws);
			r.close();
			dtfs.add(dtf);
		}
		return dtfs;
	}
	
	public static void main(String[] args) throws Exception{
		Test_DocFeatureModelBuilder t = new Test_DocFeatureModelBuilder();
		t.test();
	}
}