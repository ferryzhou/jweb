package com.iil.util.xml.test;

import org.w3c.dom.*;

import com.iil.util.web.*;
import com.iil.util.xml.*;

public class Test_DOMInfoExtractor {
	
	public void test() throws Exception{
   		Document dom = HTML2DOM.getDOM("http://dir.sina.com.cn/search_dir/syjj/com/IT/computers/");
   		Node n = DOMInfoExtractor.locateNode(dom, "/html/head");
   		System.out.println (n.getNodeName());
   		Node n1 = DOMInfoExtractor.locateNode(dom.getDocumentElement(), "/html/head");
   		System.out.println (n1.getNodeName());
   		System.out.println (DOMInfoExtractor.extractString((Element)n1, "meta/@content"));
   		//下标从1开始。
   		System.out.println (DOMInfoExtractor.extractString((Element)n1, "meta[2]/@charset"));
   		System.out.println (DOMInfoExtractor.extractString((Element)n1, "title/"));
	}
	
	public static void main(String[] args) throws Exception{
		Test_DOMInfoExtractor t = new Test_DOMInfoExtractor();
		t.test();
	}
}