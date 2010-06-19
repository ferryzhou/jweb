package com.iil.ie.xmlbased;

import java.io.*;

import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

import com.iil.util.web.*;
import com.iil.util.xml.*;
import com.iil.ie.InformationExtractionException;

public class XInformationExtractor {
	
	public XInformationExtractor(String xslfile) throws InformationExtractionException{
		this(xslfile, null);
	}
	
	public XInformationExtractor(String xslfile, HttpClient hc) throws InformationExtractionException{
		try {
			TransformerFactory xformFactory = TransformerFactory.newInstance();
			Source xsl = new StreamSource(xslfile);
			stylesheet = xformFactory.newTransformer(xsl);			
			this.hc = hc;
		} catch(TransformerException e) {
			throw new InformationExtractionException(e.getMessage());
		}
	}

	public Document extract(String url) throws InformationExtractionException {
		try {
			HTMLWrapper hw = new HTMLWrapper(hc, url);
			Document dom = hw.getDOM();
			if (toAbsoluteURL) {
				DataCleaner.toAbsoluteURL(dom, hw.getURL());
			}
			return extract(dom);
		} catch(IOException e) {
			throw new InformationExtractionException(e.getMessage());
		}
	}
	
	public Document extract(InputStream in) throws InformationExtractionException {
		try {
			Document dom = HTML2DOM.getDOM(in);
			return extract(dom);
		} catch(IOException e) {
			throw new InformationExtractionException(e.getMessage());
		}
	}
	 
	public Document extract(Document dom) throws InformationExtractionException {
		try {
			Source request = new DOMSource(dom);
			DOMResult response = new DOMResult();
			stylesheet.transform(request, response);
			doc = (Document)response.getNode();
			return doc;
		} catch(TransformerException e) {
			throw new InformationExtractionException(e.getMessage());
		}
	}
	
	/**
     * get a child of root element
     */
	public Node getObject(String oid) {
		NodeList nl = doc.getDocumentElement().getElementsByTagName(oid);
		if (nl.getLength() == 0) return null;
		else return nl.item(0);
	}
	
	public void transformFromURL(String url, Writer out) throws InformationExtractionException {
		try {
			HTMLWrapper hw = new HTMLWrapper(url);
			Document dom = hw.getDOM();
			if (toAbsoluteURL) {
				DataCleaner.toAbsoluteURL(dom, hw.getURL());
			}			
			transform(dom, out);
		}catch(IOException e) {
			throw new InformationExtractionException(e.getMessage());
		}
	}
	
	
	public void transformHTMLFile(String filename, Writer out) throws InformationExtractionException {
		try {
			Document dom = HTML2DOM.getDOM(new FileInputStream(filename));
			transform(dom, out);
		}catch(IOException e) {
			throw new InformationExtractionException(e.getMessage());
		}
	}
	
	public void transformXML(String filename, Writer out) throws InformationExtractionException {
		try {
			Document dom = DOMBuilder.build(filename);
			transform(dom, out);
		}catch(IOException e) {
			throw new InformationExtractionException(e.getMessage());
		}
	}
	
	public void transform(Document dom, Writer out) throws InformationExtractionException {
		try {
			Source request = new DOMSource(dom);
			
			StreamResult response = new StreamResult(out);
			stylesheet.transform(request, response);
		} catch(TransformerException e) {
			e.printStackTrace();
			throw new InformationExtractionException(e.getMessage());
		}
	}

	private Transformer stylesheet;
	private Document doc;
	private HttpClient hc;
	
	private boolean toAbsoluteURL = true;
}