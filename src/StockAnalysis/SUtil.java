package StockAnalysis;

import java.util.List;

public class SUtil {
	
	final public static SDate FirstMarketDate = new SDate(0);
	
	final protected static String MarketDatesLookupTicker = "IBM";
	
	
	// Scraping
	
	public static List<SPrice> scrapePrices(String ticker, int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
		return null; // TODO
	}
	
	public static List<SPrice> scrapePrices(String ticker, int year) {
		return scrapePrices(ticker, 1, 1, year, 12, 31, year);
	}
	
	public static List<String> getSP500Tickers() {
		return null; // TODO
	}
	
	public static List<String> getNASDAQTickers() {
		return null; // TODO
	}
	
	public static List<String> getDJITickers() {
		return null; // TODO
	}
	
	final protected static String[] AdditionalTickersList = {  };

}
