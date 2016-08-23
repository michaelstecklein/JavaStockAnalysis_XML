package StockAnalysis;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import MyFileSystem.Directory;
import MyFileSystem.MyFileSystem;
import MyFileSystem.RootDirectory;
import StockAnalysis.SXMLFiles.TickerListXMLFile;

public class StockDataFileSystem implements MyFileSystem {
	
	/*
	 * stock data	/
	 * 				/	resources	/	date lookup tables	/ 2016.xml, 2015.xml, ...
	 * 				/ ticker lists	/	S&P500.xml, NASDAQ.xml, DJI.xml, *.xml...
	 * 				/	data		/	SCI					/	prices	/
	 * 														/	...(other pre-calculated indicators here)...
	 * 								/	USO					/	prices	/
	 * 														/	...(other pre-calculated indicators here)...
	 * 								/	AYI					/	prices	/
	 * 														/	...(other pre-calculated indicators here)...
	 * 								/... (everything on the lists) ...
	 */
	
	final private String RootPath = "/Users/michaelstecklein/Projects/Eclipse/PackageDevelopingProject/";
	final private String TickerListsDirectoryName = "ticker lists";
	final private String DataDirectoryName = "data";
	final private String ResourcesDirectoryName = "resources";
	final private String DataLookupTablesDirectoryName = "data lookup tables";
	
	private RootDirectory root = new RootDirectory(RootPath);
	private Directory tickerlists = new Directory(TickerListsDirectoryName);
	private Directory data = new Directory(DataDirectoryName);
	private Directory resources = new Directory(ResourcesDirectoryName);
	private Directory datelookuptables = new Directory(DataLookupTablesDirectoryName);
	
	public Directory getDateLookupTablesDirectory() {
		return datelookuptables;
	}

	@Override
	public RootDirectory getRootDirectory() {
		root.add(resources);
			resources.add(datelookuptables);
				initDateLookupTables(datelookuptables);
		root.add(tickerlists);
			initTickerLists(tickerlists);
		root.add(data);
			initData(data);
		
		return root;
	}
	
	private void initDateLookupTables(Directory dateLookupTablesDir) {
		for (int year = SUtil.FirstMarketDate.getYear(); year <= SDate.getTodaysDate().getYear(); year++) {
			List<SPrice> prices = SUtil.scrapePrices(SUtil.MarketDatesLookupTicker, year);
			dateLookupTablesDir.add(new DateLookupTableXMLFile(year, prices));
		}
	}
	
	private void initTickerLists(Directory tickerListsDir) {
		List<String> tickers;
		
		// S&P500
		tickers = SUtil.getSP500Tickers();
		tickerListsDir.add(new TickerListXMLFile("S&P500", tickers));
		
		// NASDAQ
		tickers = SUtil.getNASDAQTickers();
		tickerListsDir.add(new TickerListXMLFile("NASDAQ", tickers));
		
		// DJI
		tickers = SUtil.getDJITickers();
		tickerListsDir.add(new TickerListXMLFile("DJI", tickers));
		
		// Additional
		tickers = Arrays.asList(SUtil.AdditionalTickersList);
		tickerListsDir.add(new TickerListXMLFile("Additional", tickers));
		
	}
	
	private void initData(Directory dataDir) {
		// TODO
	}

}
