import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ShopConfig {
    private boolean loadEnabled = false;
    private File loadFileName;
    private boolean loadFormat = false;
    private boolean saveEnabled = false;
    private File saveFileName;
    private boolean saveFormat = false;
    private boolean logEnabled = false;
    private File logFileName;

    public boolean isLoad() {
        return loadEnabled;
    }

    public boolean isFormat() {
        return loadFormat;
    }

    public File getBasketFile() {
        return loadFileName;
    }

    public void loadBasket() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        Element root = doc.getDocumentElement();
        Element loadTag = (Element) root.getElementsByTagName("load").item(0);
        NodeList childNode = loadTag.getChildNodes();
        for (int i = 0; i < childNode.getLength(); i++) {
            Node node = childNode.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getTagName().equals("fileName")) {
                    loadFileName = new File(element.getTextContent());
                }
                if (element.getTagName().equals("enabled")) {
                    if (element.getTextContent().equals("true")) {
                        loadEnabled = true;
                    }
                }
                if (element.getTagName().equals("format")) {
                    if (element.getTextContent().equals("json")) {
                        loadFormat = true;
                    }
                }
            }
        }
    }

    public void saveBasket() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        Element root = doc.getDocumentElement();
        Element saveTag = (Element) root.getElementsByTagName("save").item(0);
        NodeList childNode = saveTag.getChildNodes();
        for (int i = 0; i < childNode.getLength(); i++) {
            Node node = childNode.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getTagName().equals("fileName")) {
                    saveFileName = new File(element.getTextContent());
                }
                if (element.getTagName().equals("enabled")) {
                    if (element.getTextContent().equals("true")) {
                        saveEnabled = true;
                    }
                }
                if (element.getTagName().equals("format")) {
                    if (element.getTextContent().equals("json")) {
                        saveFormat = true;
                    }
                }
            }
        }
    }

    public boolean isSave() {
        return saveEnabled;
    }

    public File getSaveFile() {
        return saveFileName;
    }

    public boolean isSaveFormat() {
        return saveFormat;
    }

    public boolean isLog() {
        return logEnabled;
    }

    public File getLogFile() {
        return logFileName;
    }

    public void logCompletion() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("shop.xml"));

        Element root = doc.getDocumentElement();
        Element logTag = (Element) root.getElementsByTagName("log").item(0);
        NodeList childNode = logTag.getChildNodes();
        for (int i = 0; i < childNode.getLength(); i++) {
            Node node = childNode.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                if (element.getTagName().equals("fileName")) {
                    logFileName = new File(element.getTextContent());
                }
                if (element.getTagName().equals("enabled")) {
                    if (element.getTextContent().equals("true")) {
                        logEnabled = true;
                    }
                }
            }
        }
    }
}