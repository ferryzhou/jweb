package com.iil.util.xml;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;

import org.w3c.dom.*;

import ui.DOMTreeFull;

import com.iil.util.web.*;

public class DOMTreeView extends JFrame{
	
    Document document;
    DOMTreeFull jtree;
    JScrollPane treeScroll;
    TreeNode lastSelected;
    JTextField urlTextField;
    JTextField selectedXPathTextField;

    JTextField lookupTextField;
    JList foundList;
    
    /** main */
    public static void view (Document dom) {

                DOMTreeView frame = new DOMTreeView(dom) ;
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                 public void windowClosing(java.awt.event.WindowEvent e) {
                     System.exit(0);
                 }
                });
                frame.setSize(700, 700);
                frame.setVisible(true);
    }
    
    public static void main(String[] args) throws IOException{
    	String url = "http://www.ustc.edu.cn/";
    	if (args.length != 0 && args[0].length() != 0) {
    		url = args[0];
    	}
    	Document dom;
    	try {
    		URL uurl = new URL(url);
    		HTMLWrapper hw = new HTMLWrapper(url);
   			dom = hw.getDOM();
   		}catch (IOException e) {
   			dom = HTML2DOM.getDOM(new FileInputStream(url));
   		}
   		view(dom);
    }
    
    
    public DOMTreeView(Document dom) {
        super("TreeWalkerView ");
		
		document = dom;
            // jtree  UI setup
        jtree = new DOMTreeFull((Node)document);
        jtree.getSelectionModel().setSelectionMode
            (TreeSelectionModel.SINGLE_TREE_SELECTION);

            // Listen for when the selection changes, call nodeSelected(node)
            jtree.addTreeSelectionListener(
                new TreeSelectionListener() {
                    public void valueChanged(TreeSelectionEvent e) {
                        TreePath path = (TreePath)e.getPath();
                        TreeNode treeNode = (TreeNode)path.getLastPathComponent();
                        if(jtree.getSelectionModel().isPathSelected(path))
                                nodeSelected(treeNode);
                    }
                }
            );
            
            treeScroll = new JScrollPane(jtree) ;
            treeScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("DOM Tree View"),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
                ));

		JPanel urlPanel = new JPanel();
		JLabel urlLabel = new JLabel("URL:");
		urlTextField = new JTextField(50);
		urlTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Object source = evt.getSource();
				if(source == urlTextField) {
					reloadJTree(urlTextField.getText());
				}
			}
		});
		urlPanel.add(urlLabel);
		urlPanel.add(urlTextField);
		
		JPanel selectedXPathPanel = new JPanel();
		JLabel xpathLabel = new JLabel("XPath: ");
		selectedXPathTextField = new JTextField(50);
		selectedXPathTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Object source = evt.getSource();
				if(source == selectedXPathTextField) {
					lookupByXPath(selectedXPathTextField.getText());
				}
			}
		});
		selectedXPathPanel.add(xpathLabel);
		selectedXPathPanel.add(selectedXPathTextField);
		
		JPanel lookupPanel = new JPanel();
		JLabel lookupLabel = new JLabel("look up:");
		lookupTextField = new JTextField(20);
		lookupTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Object source = evt.getSource();
				if(source == lookupTextField) {
					lookup(lookupTextField.getText());
				}
			}
		});
		foundList = new JList();
		foundList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		foundList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				Object source = evt.getSource();
				if(source == foundList) {
					FoundItem fi = (FoundItem)foundList.getSelectedValue();
					if (fi == null) return;
					jtree.setSelectionPath(fi.treePath);
					jtree.scrollPathToVisible(fi.treePath);
				}
			}			
		});
		
        JScrollPane foundScroll = new JScrollPane(foundList);
        foundScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Nodes found"),
                BorderFactory.createEmptyBorder(4, 4, 4, 4)
                ));
        //foundScroll.set
        JPanel queryPanel = new JPanel();
        queryPanel.add(lookupLabel);
        queryPanel.add(lookupTextField);
        lookupPanel.setLayout(new BorderLayout());
		lookupPanel.add(queryPanel, BorderLayout.NORTH);
		lookupPanel.add(foundScroll, BorderLayout.CENTER);
		
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeScroll, lookupPanel);
        splitPane.setContinuousLayout(true);
		splitPane.setOneTouchExpandable(true);

        splitPane.setDividerLocation(400);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
           
        mainPanel.add(urlPanel, BorderLayout.NORTH);    
        mainPanel.add(selectedXPathPanel, BorderLayout.SOUTH);   
        mainPanel.add(splitPane, BorderLayout.CENTER); 
        //mainPanel.add(treeScroll, BorderLayout.CENTER);
        //mainPanel.add(lookupPanel, BorderLayout.EAST);    
            
        getContentPane().add(mainPanel);
    }
    
    void lookup(String text) {
    	NodeList nl = DOMInfoExtractor.locateNodes(document, ".//text()[contains(.,'" + text + "')]");
    	System.out.println ("find " + nl.getLength() + " items");
    	FoundItem[] fis = new FoundItem[nl.getLength()];
    	for (int i = 0; i<nl.getLength(); i++) {
    		fis[i] = getFoundItem(nl.item(i), text);
    	}
    	foundList.setListData(fis);
    }
    
    void lookupByXPath(String xpath) {
    	NodeList nl = DOMInfoExtractor.locateNodes(document, xpath);
    	System.out.println ("lookupByXPath: " + xpath);
    	if (nl == null) {
    		JOptionPane.showMessageDialog(this, "error xpath: " + xpath);
    	}
    	System.out.println ("find " + nl.getLength() + " items");
    	FoundItem[] fis = new FoundItem[nl.getLength()];
    	for (int i = 0; i<nl.getLength(); i++) {
    		fis[i] = getFoundItem(nl.item(i), null);
    	}
    	foundList.setListData(fis);
    }
    
    FoundItem getFoundItem(Node n, String text) {
    	String s = n.getNodeValue();
    	if (s == null) s = n.getNodeName();
    	String string;
    	if (text != null) {
    		int index = s.indexOf(text);
    		int left = Math.max(0, index - 5);
    		int right = Math.min(s.length(), index + text.length() + 20);
    		string = s.substring(left, right);
    	} else string = s;
        TreeNode tn = jtree.getTreeNode(n);
        TreePath tp = getTreePath(tn);
        return new FoundItem(string, tp);
    }
    
    class FoundItem {
    	public FoundItem(String string, TreePath treePath) {
    		this.string = string;
    		this.treePath = treePath;
    	}
    	public String string;
    	public TreePath treePath;
    	public String toString() {
    		return string;
    	}
    }
    void reloadJTree(String url) {
    	System.out.println ("reload " + url);
	   	Document dom;
    	try {
    		URL uurl = new URL(url);
    		HTMLWrapper hw = new HTMLWrapper(url);
   			dom = hw.getDOM();
   		}catch (IOException e) {
			e.printStackTrace();
   			try {
   				dom = HTML2DOM.getDOM(new FileInputStream(url));
   			}catch (IOException ie) {
   				JOptionPane.showMessageDialog(this, "can't get document of the url: " + url);
   				ie.printStackTrace();
   				return;
   			}
   		}
   		document = dom;
        jtree.setRootNode(dom);
        
    }

    /** called when our JTree's nodes are selected.
     */
    void nodeSelected(TreeNode treeNode) {

        lastSelected = treeNode;
        Node node = jtree.getNode(treeNode);
        if (node == null) return;
        
        String xpath = DOMInfoExtractor.getXPath(node);
        selectedXPathTextField.setText(xpath);
        
        String message = DOMTreeFull.toString(node);
        message += "\r\n";
        message += xpath;
        setMessage(message);
    }
    
    void setMessage(String message) {
    	
    	System.out.println (message);
    }
    
    TreePath getTreePath(TreeNode treeNode) {
    	
    	TreeNode parent = treeNode.getParent();
    	if (parent == null) {
    		return new TreePath(treeNode);
    	}
    	else {
    		TreePath ptp = getTreePath(parent);
    		ptp = ptp.pathByAddingChild(treeNode);
    		return ptp;
    	}
    }
}
