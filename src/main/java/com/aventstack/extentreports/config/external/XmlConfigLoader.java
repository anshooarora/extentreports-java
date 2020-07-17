package com.aventstack.extentreports.config.external;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import com.aventstack.extentreports.config.ConfigStore;
import com.google.gson.Gson;

public class XmlConfigLoader<T> implements ConfigLoadable<T> {
    private static final Logger LOG = Logger.getLogger(XmlConfigLoader.class.getName());

    private ConfigStore store = new ConfigStore();
    private InputStream stream;
    private T instance;

    public XmlConfigLoader(T instance, File f) throws FileNotFoundException {
        createStream(f);
        this.instance = instance;
    }

    private void createStream(File file) {
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, file.getPath(), e);
        }
    }

    public void apply() {
        ConfigStore store = getConfigStore();
        Gson gson = new Gson();
        String json = gson.toJson(store.getStore());
        JsonConfigLoader<T> jsonLoader = new JsonConfigLoader<T>(instance, json);
        jsonLoader.apply();
    }

    public ConfigStore getConfigStore() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        String value;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("configuration").item(0).getChildNodes();

            for (int ix = 0; ix < nodeList.getLength(); ix++) {
                Node node = nodeList.item(ix);

                Element el = node.getNodeType() == Node.ELEMENT_NODE ? (Element) node : null;

                if (el != null) {
                    value = el.getTextContent();

                    value = el instanceof CharacterData ? ((CharacterData) el).getData() : value;
                    store.addConfig(el.getNodeName(), value);
                }
            }

            return store;
        } catch (IOException | SAXException | ParserConfigurationException e) {
            LOG.log(Level.SEVERE, "Failed to load external configuration", e);
        }

        return null;
    }
}
