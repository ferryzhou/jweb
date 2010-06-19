/*
 * Created on 2004-7-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iil.util.xml;

import org.w3c.dom.*;

/**
 * @author ferry zhou
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SimpleDOMPrinter implements DOMPrinter{
	public String print(Node node) {
		StringBuffer sb = new StringBuffer();
		node2String(node, "", sb);
		return sb.toString();
	}
	
	public static void node2String(Node node, String indent, StringBuffer s) {
        switch (node.getNodeType()) {
            case Node.DOCUMENT_NODE:
                s.append("<xml version=\"1.0\">\n");
                s.append("\r\n");
                // recurse on each child
                NodeList nodes = node.getChildNodes();
                if (nodes != null) {
                    for (int i=0; i<nodes.getLength(); i++) {
                        node2String(nodes.item(i), "", s);
                    }
                }
                break;

            case Node.ELEMENT_NODE:
                String name = node.getNodeName();
                s.append(indent + "<" + name);
                NamedNodeMap attributes = node.getAttributes();
                for (int i=0; i<attributes.getLength(); i++) {
                    Node current = attributes.item(i);
                    s.append(" " + current.getNodeName() +
                                     "=\"" + current.getNodeValue() +
                                     "\"");
                }
                s.append(">");

                // recurse on each child
                NodeList children = node.getChildNodes();
                if (children != null) {
                    for (int i=0; i<children.getLength(); i++) {
                        node2String(children.item(i), indent + "  ", s);
                    }
                }

                s.append(indent + "</" + name + ">");
                s.append("\r\n");
                break;

            case Node.TEXT_NODE:
            case Node.CDATA_SECTION_NODE:
                s.append(node.getNodeValue());
                break;

            case Node.PROCESSING_INSTRUCTION_NODE:
                s.append("<?" + node.getNodeName() +
                                   " " + node.getNodeValue() +
                                   "?>");
                s.append("\r\n");
                break;

            case Node.ENTITY_REFERENCE_NODE:
                s.append("&" + node.getNodeName() + ";");
                break;

            case Node.DOCUMENT_TYPE_NODE:
                DocumentType docType = (DocumentType)node;
                s.append("<!DOCTYPE " + docType.getName());
                if (docType.getPublicId() != null)  {
                    s.append(" PUBLIC \"" + docType.getPublicId() + "\" ");
                } else {
                    s.append(" SYSTEM ");
                }
                s.append("\"" + docType.getSystemId() + "\">");
                s.append("\r\n");
                break;
        }

	}
	
}
