package StockAnalysis.SXMLFiles;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import MyFileSystem.DOMElementBuilder;
import MyFileSystem.UpdatableXMLFile;
import StockAnalysis.Ticker;

@SuppressWarnings("serial")
public class TickerListXMLFile extends UpdatableXMLFile {
	
	private List<String> tickers;

	public TickerListXMLFile(String fileName, List<String> tickers) {
		super(fileName);
		this.tickers = tickers;
	}

	@Override
	public boolean updateXMLContent(Document document, Element rootElement) {
		if (document.getElementsByTagName(DOMElementBuilder.getTag(Ticker.class)).getLength() == 0) {
			for (int i = 0; i < tickers.size(); i++) {
				rootElement.appendChild(DOMElementBuilder.build(tickers.get(i), document));
			}
			return true;
		}
		return false;
	}

	@Override
	public void setupRootElement(Document document, Element rootElement) {
		updateXMLContent(document, rootElement);
	}

	@Override
	public String getRootElementName() {
		return "tickerlist";
	}

}
