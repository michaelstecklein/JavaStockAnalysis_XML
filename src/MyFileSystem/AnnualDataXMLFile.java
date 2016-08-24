package MyFileSystem;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@SuppressWarnings("serial")
public abstract class AnnualDataXMLFile extends UpdatableXMLFile {
	
	private int year;

	/**
	 * Constructs and AnnualDataXMLFile for the given year.
	 * @param year
	 */
	public AnnualDataXMLFile(int year) {
		super(String.valueOf(year));
		this.year = year;
	}
	
	@Override
	public boolean requiresUpdate() {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(LastUpdateTagname).item(0).getChildNodes();
			int index = getNodeListIndex(nList, LastUpdateYearTagName);
			int lastUpdateYear = Integer.parseInt(nList.item(index).getTextContent());
			
			return lastUpdateYear <= this.year;
			
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return true;
		}
	}

}
