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
		super(getFileNameStub().concat(String.valueOf(year)));
		this.year = year;
	}
	
	/**
	 * Returns the beginning of the file name for annual XML files. They will follow the format '<fileNameStub><year>.xml'.
	 * This method should be overwritten to add a prefixed file name pattern to the annual file name.
	 * @return
	 */
	public static String getFileNameStub() {
		return "";
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
