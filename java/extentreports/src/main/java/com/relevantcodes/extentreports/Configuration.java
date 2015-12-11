package com.relevantcodes.extentreports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Configuration {
	private Map<String, String> configurationMap;
	private InputStream stream;
	private static final Logger logger = Logger.getLogger(ExtentReports.class.getName());
	
	public Map<String, String> getConfigurationMap() {	
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		String value = null;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			
			Document doc = dBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			
			NodeList nodeList = doc.getElementsByTagName("configuration").item(0).getChildNodes();
			
			for (int ix = 0; ix < nodeList.getLength(); ix++) {
				Node node = nodeList.item(ix);
				
				Element el = node.getNodeType() == Node.ELEMENT_NODE 
						? (Element) node 
						: null;
				
				if (el != null) {
					value = el.getTextContent();
					
					if (el instanceof CharacterData) {
						value = ((CharacterData) el).getData();
					}
					
					configurationMap.put(el.getNodeName(), value);
				}
			}
			
			return configurationMap;
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, "The configuration file or URL was not found", e);
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Configuration(URL url) {
		try {
			stream = url.openStream();

			configurationMap = new HashMap<String, String>();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Configuration(File file) {
		try {
			stream = new FileInputStream(file);
			
			configurationMap = new HashMap<String, String>();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
