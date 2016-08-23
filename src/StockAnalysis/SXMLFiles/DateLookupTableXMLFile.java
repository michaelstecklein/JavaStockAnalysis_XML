package StockAnalysis.SXMLFiles;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import MyFileSystem.AnnualDataXMLFile;

@SuppressWarnings("serial")
public class DateLookupTableXMLFile extends AnnualDataXMLFile {

	public DateLookupTableXMLFile(String fileName) {
		super(fileName);
	}

	@Override
	public boolean updateXMLContent(Document document, Element rootElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setupRootElement(Document document, Element rootElement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRootElementName() {
		return "datelookuptable";
	}

}
