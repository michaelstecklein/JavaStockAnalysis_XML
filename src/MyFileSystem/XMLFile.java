package MyFileSystem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * The <code>XMLFile</code> class extends the CustomFile class to provided custom XML formats to XML files in a file
 * system. The class is abstract and requires only that the child classes setup the custom DOM object that outlines the XML
 * object the file will contain. 
 * @author michaelstecklein
 *
 */
public abstract class XMLFile extends CustomFile {
	
	/**
	 * Builds the parameter element into a custom XML DOM object for this XML file. The document object is provided to be used only
	 * as a factory to child elements of the root element. The root element parameter has already been initialized as a child of the
	 * document. 
	 * @param document
	 * @param rootElement
	 */
	abstract public void setupRootElement(Document document, Element rootElement);
	
	/**
	 * Returns the name of the root element for this XML file. The root element will encompass the entire XML structure of the file.
	 * @return the name of the root element
	 */
	abstract public String getRootElementName();
	

	private static final long serialVersionUID = 2597559504385952343L;
	
	public static String XMLFileExtension = "xml";

	public XMLFile(String fileName) {
		super(fileName);
	}
	
	@Override
	public String getContents() {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			Element root = doc.createElement(getRootElementName());
			setupRootAttributes(root);
			setupPreemptiveElements(doc, root);
			setupRootElement(doc, root);
			return convertXMLDocumentToString(doc);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * Documents the super class hierarchy of implementing subclasses up to but not including this superclass by writing them
	 * in as attributes of the root element.
	 * @param root
	 */
	private void setupRootAttributes(Element root) {
		// TODO add all super classes as attributes
	}
	
	/**
	 * Converts the parameter DOM document into a string of XML.
	 * @param doc
	 * @return the string of XML
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	public static String convertXMLDocumentToString(Document doc) throws TransformerFactoryConfigurationError, TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    StreamResult result = new StreamResult(new StringWriter());
	    DOMSource source = new DOMSource(doc);
	    transformer.transform(source, result);
	    return result.getWriter().toString();
	}
	
	/**
	 * This method should be overwritten in order to add elements to the root element before any subclasses touch it.
	 * @param document
	 * @param rootElement
	 */
	protected void setupPreemptiveElements(Document document, Element rootElement) {
	}

	@Override
	public String getExtension() {
		return XMLFileExtension;
	}
	
}
