package com.iil.util.xml;

import javax.xml.parsers.*;
import org.w3c.dom.*;

public class DocumentFactory {
	
	public static Document createDocument(String rootName) {
		try {
			//System.out.println (System.getProperty("javax.xml.parsers.DocumentBuilderFactory"));
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();
	  
			Document doc = impl.createDocument(null, rootName, null);
			return doc;
		}
		catch (FactoryConfigurationError e) { 
			System.out.println("Could not locate a JAXP DocumentBuilderFactory class"); 
			throw new IllegalStateException(e.getMessage());
		}
		catch (ParserConfigurationException e) { 
			System.out.println("Could not locate a JAXP DocumentBuilder class");
			throw new IllegalStateException(e.getMessage()); 
		}
	}
	
	public static Element createTextElement(Document doc, String elementname, String text) {
		Element e = doc.createElement(elementname);
		e.appendChild(doc.createTextNode(text));
		return e;
	}
}