package StockAnalysis;

public class SPrice {

	public SDate date;
	public double open, high, low, close, adjClose;
	public int volume;
	
	public SPrice(SDate date, double open, double high, double low, double close, int volume, double adjClose) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.adjClose = adjClose;
		this.volume = volume;
	}
	
}
