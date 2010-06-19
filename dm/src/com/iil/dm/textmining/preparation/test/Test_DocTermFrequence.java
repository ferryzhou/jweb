package com.iil.dm.textmining.preparation.test;

import java.io.*;
import com.iil.word.*;
import com.iil.dm.textmining.preparation.*;

public class Test_DocTermFrequence {
	
	public void test() throws Exception{
		String file = "F:\\zj\\test\\java\\clustering\\sourceI\\2.TXT";
		WordSplitter ws = new EnglishWordSplitter(StopListReader.load("F:\\zj\\test\\java\\clustering\\stopList.txt"));
		Reader r = new InputStreamReader(new FileInputStream(file));
		DocTermFrequence dtf = DocTermFrequence.createFromReader("2", r, ws);
		System.out.println (dtf);
		r.close();
	}
	
	
	public static void main(String[] args) throws Exception{
		Test_DocTermFrequence t = new Test_DocTermFrequence();
		t.test();
	}
}