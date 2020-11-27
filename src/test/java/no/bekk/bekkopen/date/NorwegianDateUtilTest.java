package no.bekk.bekkopen.date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NorwegianDateUtilTest {
	private static DateFormat FORMAT(){
	    return new SimpleDateFormat("dd.MM.yyyy");
    }

	@BeforeEach
	public void setLocale() {
		Locale.setDefault(new Locale("no", "NO"));
	}

	@Test
	public void testAdd2DaysWithinSameWeek() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("21.03.2007"), 2));

		assertEquals(23, cal.get(Calendar.DATE));
	}

	@Test
	public void testAdd2DaysToLastDayOfMonth() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("28.02.2007"), 2));

		assertEquals(2, cal.get(Calendar.DATE));
		assertEquals(Calendar.MARCH, cal.get(Calendar.MONTH));
	}

	@Test
	public void testAdd5DaysWithNoHolidays() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("21.03.2007"), 5));

		assertEquals(28, cal.get(Calendar.DATE));
	}

	@Test
	public void testAdd5DaysBeforeEasterHoliday() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("04.04.2007"), 5));

		assertEquals(16, cal.get(Calendar.DATE));
	}

	@Test
	public void testAdd5DaysBeforeNationalDay() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("16.05.2007"), 5));

		assertEquals(24, cal.get(Calendar.DATE));
	}

	@Test
	public void testAdd5DaysBeforeChristmas() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("21.12.2007"), 5));

		assertEquals(2, cal.get(Calendar.DATE));
		assertEquals(Calendar.JANUARY, cal.get(Calendar.MONTH));
		assertEquals(2008, cal.get(Calendar.YEAR));
	}

	@Test
	public void testWorkingDays() throws Exception {
		assertFalse(NorwegianDateUtil.isWorkingDay(FORMAT().parse("25.03.2007")), "Sunday not working day");
		assertTrue(NorwegianDateUtil.isWorkingDay(FORMAT().parse("26.03.2007")), "Monday is working day");
		assertFalse(NorwegianDateUtil.isWorkingDay(FORMAT().parse("01.01.2007")), "New years day not working day");
		assertFalse(NorwegianDateUtil.isWorkingDay(FORMAT().parse("08.04.2007")), "Easter day not working day");
	}

	@Test
	public void testVariousNorwegianHolidays() throws Exception {
		// Set dates
		checkHoliday("01.01.2007");
		checkHoliday("01.05.2007");
		checkHoliday("17.05.2007");
		checkHoliday("25.12.2007");
		checkHoliday("26.12.2007");

		// Movable dates 2007
		checkHoliday("01.04.2007");
		checkHoliday("05.04.2007");
		checkHoliday("06.04.2007");
		checkHoliday("08.04.2007");
		checkHoliday("09.04.2007");
		checkHoliday("17.05.2007");
		checkHoliday("27.05.2007");
		checkHoliday("28.05.2007");

		// Movable dates 2008
		checkHoliday("16.03.2008");
		checkHoliday("20.03.2008");
		checkHoliday("21.03.2008");
		checkHoliday("23.03.2008");
		checkHoliday("24.03.2008");
		checkHoliday("01.05.2008");
		checkHoliday("11.05.2008");
		checkHoliday("12.05.2008");
	}

	@Test
	public void testGetAllNorwegianHolidaysForYear() {
		Date[] holidays = NorwegianDateUtil.getHolidays(2009);
		assertEquals(13, holidays.length);
		assertEquals("01.01.2009", FORMAT().format(holidays[0]));
		assertEquals("05.04.2009", FORMAT().format(holidays[1]));
		assertEquals("09.04.2009", FORMAT().format(holidays[2]));
		assertEquals("10.04.2009", FORMAT().format(holidays[3]));
		assertEquals("12.04.2009", FORMAT().format(holidays[4]));
		assertEquals("13.04.2009", FORMAT().format(holidays[5]));
		assertEquals("01.05.2009", FORMAT().format(holidays[6]));
		assertEquals("17.05.2009", FORMAT().format(holidays[7]));
		assertEquals("21.05.2009", FORMAT().format(holidays[8]));
		assertEquals("31.05.2009", FORMAT().format(holidays[9]));
		assertEquals("01.06.2009", FORMAT().format(holidays[10]));
		assertEquals("25.12.2009", FORMAT().format(holidays[11]));
		assertEquals("26.12.2009", FORMAT().format(holidays[12]));
	}

	private void checkHoliday(String date) throws ParseException {
		assertTrue(NorwegianDateUtil.isHoliday(FORMAT().parse(date)), date);
	}
}
