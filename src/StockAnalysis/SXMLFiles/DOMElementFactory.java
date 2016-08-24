package StockAnalysis.SXMLFiles;

import StockAnalysis.SDate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DOMElementFactory {

	public static String getTag(Class<?> c) {
		if (c == SDate.class)
				return "sdate";
		return "";
	}
	
	public static Element create(SDate sdate) {
		return null; // TODO
	}
	
}
