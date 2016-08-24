package StockAnalysis;

import java.util.Calendar;
import java.util.GregorianCalendar;

import MyFileSystem.DOMObject;

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
@DOMObject(
	elements = { "dayNumber", "day", "month", "year" }
)
public class SDate extends GregorianCalendar {
	
	final public static int InvalidStockDate = -1;
	
	/* DOM Elements */
	private int dayNumber;
	private int day;
	private int month;
	private int year;
	
	public SDate() {
		this(InvalidStockDate);
	}
	
	public SDate(int dayNumber) {
		this.dayNumber = dayNumber;
		SDate s = convertDayNumberToDate(dayNumber);
		setDate(s.getDay(), s.getMonth(), s.getYear());
	}
	
	public SDate(int day, int month, int year) {
		this(convertDateToDayNumber(day, month, year));
	}
	
	public SDate(Calendar calendar) {
		this(calendar.get(DAY_OF_MONTH), calendar.get(MONTH), calendar.get(YEAR));
	}
	
	public int getDayNumber() {
		return dayNumber;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	private void setDate(int day, int month, int year) {
		this.set(Calendar.DAY_OF_MONTH, day);
		this.day = day;
		this.set(Calendar.MONTH, month);
		this.month = month;
		this.set(Calendar.YEAR, year);
		this.year = year;
	}
	
	public static SDate getTodaysDate() {
		return new SDate(Calendar.getInstance());
	}
	
	public static int convertDateToDayNumber(int day, int month, int year) {
		return 0; // TODO
	}
	
	public static SDate convertDayNumberToDate(int dayNumber) {
		return new SDate(0); // TODO
	}
	
}
