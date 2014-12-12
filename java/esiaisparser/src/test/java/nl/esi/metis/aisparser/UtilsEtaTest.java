package nl.esi.metis.aisparser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.Assert;

import org.junit.Test;

import cern.colt.bitvector.BitVector;

public class UtilsEtaTest {
	/** Time zone object for UTC */
	private static final TimeZone utcTZ = TimeZone.getTimeZone("UTC");
	
	/** Date format using UTC */
	private static final DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	static {
		utcFormat.setTimeZone(utcTZ);
	}

	/** Tests the convertToTime method for an ETA set to an unavailable month and an earlier day than the reference date. 
	 * Here the important part is that the month must be incremented. 
	 * @throws ParseException */
	@Test
	public void overflowMonth() throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo( 0, 16, 19); // month = unavailable
		eta.putLongFromTo(15, 11, 15); // day = 15 (earlier than reference date)
		eta.putLongFromTo(23, 6, 10);  // hour = 23
		eta.putLongFromTo(55, 0, 5);   // minute = 55
		
		Date refDate = utcFormat.parse("2011-09-26T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2011-10-15T23:55:00.000Z", etaString);
	}
	
	/** Tests the convertToTime method for an ETA set to the same date and time as the reference date. 
	 * Here the important part is that the year must be incremented to be in the future.
	 * @throws ParseException */
	@Test
	public void extremeOverflow() throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo( 9, 16, 19); // month = September
		eta.putLongFromTo(26, 11, 15); // day = 26
		eta.putLongFromTo(15, 6, 10);  // hour = 15
		eta.putLongFromTo( 6, 0, 5);   // minute = 06
		
		Date refDate = utcFormat.parse("2011-09-26T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2012-09-26T15:06:00.000Z", etaString);
	}
	
	/** Tests the convertToTime method for an ETA of December 31 and a reference date in September. 
	 * Here the important part is that the month must be set before the date, since September does not have 31 days. 
	 * @throws ParseException */
	@Test
	public void septemberDecember31() throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo(12, 16, 19); // month = December
		eta.putLongFromTo(31, 11, 15); // day = 31
		eta.putLongFromTo(23, 6, 10);  // hour = 23
		eta.putLongFromTo(55, 0, 5);   // minute = 55
		
		Date refDate = utcFormat.parse("2011-09-26T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2011-12-31T23:55:00.000Z", etaString);
	}
	
	/** Tests the convertToTime method for an ETA in March and a reference date of December 31. 
	 * Here the important part is that the next year should be chosen. 
	 * @throws ParseException */
	@Test
	public void December31March () throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo(03, 16, 19); // month = March
		eta.putLongFromTo(15, 11, 15); // day = 15 (not important)
		eta.putLongFromTo(24, 6, 10);  // hour = unavailable
		eta.putLongFromTo(60, 0, 5);   // minute = unavailable
		
		Date refDate = utcFormat.parse("2011-12-31T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2012-03-15T00:00:00.000Z", etaString);
	}

	/** Tests the convertToTime method for an ETA with an unavailable month and a later date than the reference date. 
	 * Here the important part is that the same month should be chosen. 
	 * @throws ParseException */
	@Test
	public void unavailableThisMonth () throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo( 0, 16, 19); // month = unavailable
		eta.putLongFromTo(15, 11, 15); // day = 15 (later than reference)
		eta.putLongFromTo(24, 6, 10);  // hour = unavailable
		eta.putLongFromTo(60, 0, 5);   // minute = unavailable
		
		Date refDate = utcFormat.parse("2011-12-03T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2011-12-15T00:00:00.000Z", etaString);
	}

	/** Tests the convertToTime method for an ETA with an unavailable month and an earlier date than the reference date. 
	 * Here the important part is that the next month should be chosen. 
	 * @throws ParseException */
	@Test
	public void unavailableNextMonth () throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo( 0, 16, 19); // month = unavailable
		eta.putLongFromTo( 3, 11, 15); // day = 3 (earlier than reference)
		eta.putLongFromTo(24, 6, 10);  // hour = unavailable
		eta.putLongFromTo(43, 0, 5);   // minute = 43
		
		Date refDate = utcFormat.parse("2011-09-15T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2011-10-03T00:43:00.000Z", etaString);
	}

	/** Tests the convertToTime method for an ETA with an unavailable month and date, and a later hour than the reference date. 
	 * Here the important part is that the same date should be chosen. 
	 * @throws ParseException */
	@Test
	public void unavailableThisDay () throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo( 0, 16, 19); // month = unavailable
		eta.putLongFromTo( 0, 11, 15); // day = unavailable
		eta.putLongFromTo(20,  6, 10); // hour = 20 (later than reference)
		eta.putLongFromTo(60,  0,  5); // minute = unavailable
		
		Date refDate = utcFormat.parse("2011-12-03T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2011-12-03T20:00:00.000Z", etaString);
	}

	/** Tests the convertToTime method for an ETA with an unavailable month and date, and an earlier hour than the reference date.
	 * Here the important part is that the next day should be chosen. 
	 * @throws ParseException */
	@Test
	public void unavailableNextDay () throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo( 0, 16, 19); // month = unavailable
		eta.putLongFromTo( 0, 11, 15); // day = unavailable
		eta.putLongFromTo( 3,  6, 10); // hour = 3 (earlier than reference)
		eta.putLongFromTo(43,  0,  5); // minute = 43
		
		Date refDate = utcFormat.parse("2011-09-15T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2011-09-16T03:43:00.000Z", etaString);
	}

	/** Tests the convertToTime method for an ETA with an unavailable month, date, and hour, and a later minute than the reference date. 
	 * Here the important part is that the same hour should be chosen. 
	 * @throws ParseException */
	@Test
	public void unavailableThisHour () throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo( 0, 16, 19); // month = unavailable
		eta.putLongFromTo( 0, 11, 15); // day = unavailable
		eta.putLongFromTo(24,  6, 10); // hour = unavailable 
		eta.putLongFromTo(43,  0,  5); // minute = 43 (later than reference)
		
		Date refDate = utcFormat.parse("2011-12-03T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2011-12-03T15:43:00.000Z", etaString);
	}

	/** Tests the convertToTime method for an ETA with an unavailable month, date, and hour, and an earlier minute than the reference date. 
	 * Here the important part is that the next hour should be chosen. 
	 * @throws ParseException */
	@Test
	public void unavailableNextHour () throws ParseException {
		BitVector eta = new BitVector(20);
		eta.putLongFromTo( 0, 16, 19); // month = unavailable
		eta.putLongFromTo( 0, 11, 15); // day = unavailable
		eta.putLongFromTo(24,  6, 10); // hour = unavailable 
		eta.putLongFromTo( 3,  0,  5); // minute = 3 (earlier than reference)
		
		Date refDate = utcFormat.parse("2011-09-15T15:06:17.000Z");
		double refDouble = refDate.getTime() / 1000;
				
		long etaLong = UtilsEta.convertToTime(eta, refDouble);
		Date etaDate = new Date(etaLong * 1000);
		String etaString = utcFormat.format(etaDate);
		Assert.assertEquals("Incorrect converted date" , "2011-09-15T16:03:00.000Z", etaString);
	}

}
