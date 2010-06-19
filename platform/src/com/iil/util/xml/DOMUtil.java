package com.iil.util.xml;

import java.io.*;
import org.w3c.dom.*;
//import org.w3c.dom.ls.*;

//import javax.xml.parsers.*;

//import javax.xml.transform.*;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.*;

public class DOMUtil {
	
	public static void save(Document doc, String filename) throws IOException {
		xercesSave(doc, filename);
	}
	
	private static void xercesSave(Document doc, String filename) throws IOException {
		OutputFormat format = new OutputFormat(doc);
		format.setEncoding("gb2312");
		format.setIndenting(true);
		XMLSerializer output = new XMLSerializer(new FileOutputStream(filename), format);
		output.serialize(doc);		
	}
	
	public static Node importNode(Document target, Node source, boolean deep) throws DOMException{
        Node newnode=null;

        // Sigh. This doesn't work; too many nodes have private data that
        // would have to be manually tweaked. May be able to add local
        // shortcuts to each nodetype. Consider ?????
        // if(source instanceof NodeImpl &&
        //  !(source instanceof DocumentImpl))
        // {
        //  // Can't clone DocumentImpl since it invokes us...
        //  newnode=(NodeImpl)source.cloneNode(false);
        //  newnode.ownerDocument=this;
        // }
        // else
        
        int type = source.getNodeType();
        switch (type) {
            case Node.ELEMENT_NODE: {
                Element newElement;
                newElement = target.createElement(source.getNodeName());

                // Copy element's attributes, if any.
                NamedNodeMap sourceAttrs = source.getAttributes();
                if (sourceAttrs != null) {
                    int length = sourceAttrs.getLength();
                    for (int index = 0; index < length; index++) {
                        Attr attr = (Attr)sourceAttrs.item(index);

                        // NOTE: this methods is used for both importingNode
                        // and cloning the document node. In case of the 
                        // clonning default attributes should be copied.
                        // But for importNode defaults should be ignored.
                        Attr newAttr = (Attr)importNode(target, attr, true);
                        newElement.setAttributeNode(newAttr);
                    }
                }

                newnode = newElement;
                break;
            }

            case Node.ATTRIBUTE_NODE: {

                newnode = target.createAttribute(source.getNodeName());
                newnode.setNodeValue(source.getNodeValue());
                deep = false;
                break;
            }

            case Node.TEXT_NODE: {
                newnode = target.createTextNode(source.getNodeValue());
                break;
            }

            case Node.CDATA_SECTION_NODE: {
                newnode = target.createCDATASection(source.getNodeValue());
                break;
            }

            case Node.ENTITY_REFERENCE_NODE: {
                newnode = target.createEntityReference(source.getNodeName());
                // the subtree is created according to this doc by the method
                // above, so avoid carrying over original subtree
                deep = false;
                break;
            }

/*            case Node.ENTITY_NODE: {
                Entity srcentity = (Entity)source;
                EntityImpl newentity =
                    (EntityImpl)target.createEntity(source.getNodeName());
                newentity.setPublicId(srcentity.getPublicId());
                newentity.setSystemId(srcentity.getSystemId());
                newentity.setNotationName(srcentity.getNotationName());
                // Kids carry additional value,
                // allow deep import temporarily
                newentity.isReadOnly(false);
                newnode = newentity;
                break;
            }
*/
            case Node.PROCESSING_INSTRUCTION_NODE: {
                newnode = target.createProcessingInstruction(source.getNodeName(),
                                                      source.getNodeValue());
                break;
            }

            case Node.COMMENT_NODE: {
                newnode = target.createComment(source.getNodeValue());
                break;
            }

            case Node.DOCUMENT_FRAGMENT_NODE: {
                newnode = target.createDocumentFragment();
                // No name, kids carry value
                break;
            }

/*            case Node.NOTATION_NODE: {
                Notation srcnotation = (Notation)source;
                NotationImpl newnotation =
                    (NotationImpl)target.createNotation(source.getNodeName());
                newnotation.setPublicId(srcnotation.getPublicId());
                newnotation.setSystemId(srcnotation.getSystemId());
                // Kids carry additional value
                newnode = newnotation;
                // No name, no value
                break;
            }
*/            case Node.DOCUMENT_NODE : // Can't import document nodes
            default: {           // Unknown node type
                String msg = "import error";//DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null);
                //System.out.println("type: " + type);
                return target.createElement("unknown_type");
                //throw new DOMException(DOMException.NOT_SUPPORTED_ERR, msg);
            }
        }

        // If deep, replicate and attach the kids.
        if (deep) {
            NodeList nl = source.getChildNodes();
            for (int i = 0; i<nl.getLength(); i++) {
                newnode.appendChild(importNode(target, nl.item(i), true));
            }
        }
        return newnode;		
	}
}