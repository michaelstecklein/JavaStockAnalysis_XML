package StockAnalysis.SXMLFiles;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import MyFileSystem.AnnualDataXMLFile;
import StockAnalysis.SDate;

@SuppressWarnings("serial")
public class SDateLookupTableXMLFile extends AnnualDataXMLFile {
	
	private List<SDate> sdates;

	public SDateLookupTableXMLFile(int year, List<SDate> sdates) {
		super(year);
		this.sdates = sdates;
	}

	@Override
	public boolean updateXMLContent(Document document, Element rootElement) {
		if (document.getElementsByTagName(DOMElementFactory.getTag(SDate.class)).getLength() == 0) {
			for (int i = 0; i < sdates.size(); i++) {
				rootElement.appendChild(DOMElementFactory.create(sdates.get(i)));
			}
		}
		return false;
	}

	@Override
	public void setupRootElement(Document document, Element rootElement) {
		updateXMLContent(document, rootElement);
	}

	@Override
	public String getRootElementName() {
		return "sdatelookuptable";
	}
}
