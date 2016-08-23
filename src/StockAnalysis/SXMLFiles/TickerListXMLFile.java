package StockAnalysis.SXMLFiles;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import MyFileSystem.Updatable;
import MyFileSystem.UpdatableXMLFile;
import MyFileSystem.XMLFile;

@SuppressWarnings("serial")
public class TickerListXMLFile extends UpdatableXMLFile {
	
	private List<String> tickers;

	public TickerListXMLFile(String fileName, List<String> tickers) {
		super(fileName);
		this.tickers = tickers;
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
		return "tickerlist";
	}

}
