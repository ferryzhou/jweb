package com.iil.ie.xmlbased;

import java.io.*;

import org.w3c.dom.*;
//import javax.xml.transform.*;
//import javax.xml.transform.stream.*;
//import javax.xml.transform.dom.*;

import com.iil.util.web.*;
import com.iil.ie.InformationExtractionException;

/**
 * not complete
 */
public class ContinualXInformationExtractor {
	
	public ContinualXInformationExtractor(String xslfile) throws InformationExtractionException{
		xie = new XInformationExtractor(xslfile);
	}
	
	public void setBiginURL(String url) {
		curURL = url;
	}

	public boolean goNextURL() {
		//下一页的词可能会有多种，当前的做法以后要改。
		try {
			String nextURL = WebRouter.hop(curURL, "下一页");
			//if (nextURL == null) {
			//	nextURL = WebRouter.hop(curURL, "Next");
			//}
			if (nextURL == null) return false;
			curURL = nextURL;
			return true;
		}catch(IOException e) {
			return false;
		}
	}

	public Node extract() throws InformationExtractionException{
   		return xie.extract(curURL);
	}

	private String curURL;
	private XInformationExtractor xie;

}

