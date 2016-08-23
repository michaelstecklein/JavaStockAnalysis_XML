package MyFileSystem;

import java.io.IOException;
import java.util.Calendar;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import MyUtils.FileUtil;

/**
 * The <code>UpdatableXMLFile</code> extends XMLFile and implements Updatable to abstract the updatable fuctionality from
 * implementing XML files which require updating.
 * @author michaelstecklein
 *
 */
@SuppressWarnings("serial")
public abstract class UpdatableXMLFile extends XMLFile implements Updatable {
	
	final public static String LastUpdateTagname = "lastupdate";
	final public static String LastUpdateYearTagName = "year";
	final public static String LastUpdateMonthTagName = "month";
	final public static String LastUpdateDateTagName = "date";

	public UpdatableXMLFile(String fileName) {
		super(fileName);
	}
	
	/**
	 * Updates the XML content of the XML file. The root element of the file is provided for updating and
	 * a document is provided only as a factory for elements.
	 * @param document
	 * @param rootElement
	 * @return whether the update was successful
	 */
	abstract public boolean updateXMLContent(Document document, Element rootElement);

	/**
	 * Abstracts the loading and saving on the XML file from the {@link #updateXMLContent(Document, Element)}
	 * method as well as the saving of the last-update information.
	 */
	/*
	 * <lastupdate>
	 * 		<date>1</date>
	 * 		<month>1</month>
	 * 		<year>1990</year>
	 * </lastupdate>
	 */
	@Override
	public boolean update() {
		try {
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this);
			document.getDocumentElement().normalize();
			boolean success = updateXMLContent(document, document.getDocumentElement());
			success &= updateLastUpdateElement(document);
			success &= FileUtil.write(this, convertXMLDocumentToString(document).getBytes());
			return success;
		} catch (TransformerFactoryConfigurationError | TransformerException | SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean updateLastUpdateElement(Document doc) {
		Node n = doc.getElementsByTagName(LastUpdateDateTagName).item(0);
		NodeList nList = n.getChildNodes();
		Calendar calendar = Calendar.getInstance();
		String date = String.valueOf(calendar.get(Calendar.DATE));
		String month = String.valueOf(calendar.get(Calendar.MONTH));
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		
		int index;
		index = getNodeListIndex(nList, LastUpdateDateTagName);
		if (index < 0) { return false; }
		nList.item(index).setNodeValue(date);

		index = getNodeListIndex(nList, LastUpdateMonthTagName);
		if (index < 0) { return false; }
		nList.item(index).setNodeValue(month);

		index = getNodeListIndex(nList, LastUpdateYearTagName);
		if (index < 0) { return false; }
		nList.item(index).setNodeValue(year);
		
		return true;
	}
	
	/**
	 * Abstracts the element which holds the information regarding the last update from subclasses.
	 */
	@Override
	public boolean requiresUpdate() {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this);
			doc.getDocumentElement().normalize();
			Node n = doc.getElementsByTagName(LastUpdateTagname).item(0);
			NodeList nList = n.getChildNodes();
			Calendar calendar = Calendar.getInstance();
			
			// compare years
			int updateYear = Integer.parseInt(nList.item(getNodeListIndex(nList, LastUpdateYearTagName)).getTextContent());
			int thisYear = calendar.get(Calendar.YEAR);
			if (updateYear < thisYear) {
				return true;
			}

			// compare months
			int updateMonth = Integer.parseInt(nList.item(getNodeListIndex(nList, LastUpdateMonthTagName)).getTextContent());
			int thisMonth = calendar.get(Calendar.MONTH);
			if (updateMonth < thisMonth) {
				return true;
			}
			
			// compare dates
			int updateDate = Integer.parseInt(nList.item(getNodeListIndex(nList, LastUpdateDateTagName)).getTextContent());
			int thisDate = calendar.get(Calendar.DATE);
			if (updateDate < thisDate) {
				return true;
			}
			
			return false;
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	protected int getNodeListIndex(NodeList nList, String nodeName) {
		for (int i = 0; i < nList.getLength(); i++) {
			if (nList.item(i).equals(nodeName)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Sets up the element which holds the information regarding the last update.
	 */
	@Override
	protected void setupPreemptiveElements(Document document, Element rootElement) {
		Calendar calendar = Calendar.getInstance();
		String todaysDate = String.valueOf(calendar.get(Calendar.DATE));
		String todaysMonth = String.valueOf(calendar.get(Calendar.MONTH));
		String todaysYear = String.valueOf(calendar.get(Calendar.YEAR));
		
		Element lastupdate = document.createElement(LastUpdateTagname);
		rootElement.appendChild(lastupdate);
		
		Element date = document.createElement(LastUpdateDateTagName);
		date.setTextContent(todaysDate);
		lastupdate.appendChild(date);
		
		Element month = document.createElement(LastUpdateMonthTagName);
		month.setTextContent(todaysMonth);
		lastupdate.appendChild(date);
		
		Element year = document.createElement(LastUpdateYearTagName);
		year.setTextContent(todaysYear);
		lastupdate.appendChild(year);
	}

}
