package com.iil.util.test;

import java.io.*;

import com.iil.util.*;

public class Test_IO {
	
	public void test() {
		try {
			InputStream is = new FileInputStream("sina.in.htm");
			OutputStream os = new FileOutputStream("sina.out.htm");
			//InputStream uis = new FileInputStream("sina.unicode.in.htm");
			//gb2312->gb2312
			IO.readWriteAll(is, os);
			
			is.close();
			os.close();
		} catch (IOException e) {
			System.out.println (e);
		}
	}
	
	public void test2() {
		try {
			InputStream is = new FileInputStream("sina.in.htm");
			OutputStream uos = new FileOutputStream("sina.unicode.out.htm");
			//gb2312->unicode
			IO.readWriteAll(IO.inputStreamBridge(is, "gb2312", "utf-8"), uos);
			is.close();
			uos.close();
		} catch (IOException e) {
			System.out.println (e);
		}
	}
	
	public static void main(String[] args) throws Exception{
		Test_IO t = new Test_IO();
		//t.test();
		//t.test2();
		System.out.print(IO.getContent(new FileReader("F:\\zj\\eclipse\\workspace2\\lucene\\data\\simple\\1.txt")));
	}
}