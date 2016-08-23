package StockAnalysis;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The <code>StockDate</code> class
 * @author michaelstecklein
 *
 */
//		USE 'IBM' TO COLLECT STOCK DATE DATA FROM
/*
 * Since the stock market is only open certain days out of the year, in order to provide linearity, dates are shifted
 * in reference to an original start date so that each day the market is open is numbered sequentially via its "dayNumber".
 * The first day of the market is denoted by a '0' for the day number.
 */
@SuppressWarnings("serial")
public class SDate extends GregorianCalendar {
	
	final public static int InvalidStockDate = -1; 
	
	public SDate() {
		this(InvalidStockDate);
	}
	
	public SDate(int dayNumber) {
		SDate temp = convertDayNumberToDate(dayNumber);
		setDate(temp.getDay(), temp.getMonth(), temp.getYear());
	}
	
	public SDate(Calendar calendar) {
		this(convertDateToDayNumber(calendar.get(DAY_OF_MONTH), calendar.get(MONTH), calendar.get(YEAR)));
	}
	
	public static int convertDateToDayNumber(int day, int month, int year) {
		return 0; // TODO
	}
	
	public static SDate convertDayNumberToDate(int dayNumber) {
		return new SDate(0); // TODO
	}
	
	public int getDayNumber() {
		return convertDateToDayNumber(this.getDay(), this.getMonth(), this.getYear());
	}
	
	public int getDay() {
		return this.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getMonth() {
		return this.get(Calendar.MONTH);
	}
	
	public int getYear() {
		return this.get(Calendar.YEAR);
	}
	
	public void setDate(int day, int month, int year) {
		this.set(Calendar.DAY_OF_MONTH, day);
		this.set(Calendar.MONTH, month);
		this.set(Calendar.YEAR, year);
	}
	
	public static SDate getTodaysDate() {
		return new SDate(Calendar.getInstance());
	}
	
}
