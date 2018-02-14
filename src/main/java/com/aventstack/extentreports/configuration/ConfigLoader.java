package com.aventstack.extentreports.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

public class ConfigLoader {
    
    private static final Logger logger = Logger.getLogger(ConfigLoader.class.getName());
    
	private ConfigMap configContext;
	private InputStream stream;

	ConfigLoader() {
        configContext = new ConfigMap();
    }
    
    public ConfigLoader(URL url) {
        this();
        
        try {
            stream = url.openStream();
        } 
        catch (IOException e) {
            logger.log(Level.SEVERE, url.toString(), e);
        }
    }
    
    public ConfigLoader(File file) {
        this();
        
        try {
            stream = new FileInputStream(file);
        } 
        catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, file.getPath(), e);
        }
    }
    
	public ConfigMap getConfigurationHash() {	
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		String value;
		
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
					
					value = el instanceof CharacterData ? ((CharacterData) el).getData() : value;
    				
    				Config c = new Config();
    				c.setKey(el.getNodeName());
    				c.setValue(value);
    				
    				configContext.setConfig(c);
				}
			}
			
			return configContext;
		} 
		catch (IOException|SAXException|ParserConfigurationException e) {
		    logger.log(Level.SEVERE, "Config", e);
		}
		
		return null;
	}
}
