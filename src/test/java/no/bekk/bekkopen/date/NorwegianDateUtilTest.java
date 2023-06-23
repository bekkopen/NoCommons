package no.bekk.bekkopen.date;

/*-
 * #%L
 * NoCommons
 * %%
 * Copyright (C) 2014 - 2023 BEKK open source
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class NorwegianDateUtilTest {
	private static DateTimeFormatter FORMAT(){
		return DateTimeFormatter.ofPattern("dd.MM.yyyy");
	}

	@BeforeEach
	public void setLocale() {
		Locale.setDefault(new Locale("no", "NO"));
	}

	@Test
	public void testAdd2DaysWithinSameWeek() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("21.03.2007", LocalDate::from), 2);

		assertEquals(23, localDate.getDayOfMonth());
	}

	@Test
	public void testAdd2DaysWithinSameWeekBackInTime() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("23.03.2007", LocalDate::from), -2);

		assertEquals(21, localDate.getDayOfMonth());
	}

	@Test
	public void testAdd2DaysToLastDayOfMonth() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("28.02.2007", LocalDate::from), 2);

		assertEquals(2, localDate.getDayOfMonth());
		assertEquals(Month.MARCH, localDate.getMonth());
	}

	@Test
	public void testAdd2DaysToLastDayOfMonthBackInTime() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("02.03.2007", LocalDate::from), -2);

		assertEquals(28, localDate.getDayOfMonth());
		assertEquals(Month.FEBRUARY, localDate.getMonth());
	}

	@Test
	public void testAdd5DaysWithNoHolidays() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("21.03.2007", LocalDate::from), 5);

		assertEquals(28, localDate.getDayOfMonth());
	}

	@Test
	public void testAdd5DaysWithNoHolidaysBackInTime() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("28.03.2007", LocalDate::from), -5);

		assertEquals(21, localDate.getDayOfMonth());
	}

	@Test
	public void testAdd5DaysBeforeEasterHoliday() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("04.04.2007", LocalDate::from), 5);

		assertEquals(16, localDate.getDayOfMonth());
	}

	@Test
	public void testAdd5DaysBeforeEasterHolidayBackInTime() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("16.04.2007", LocalDate::from), -5);

		assertEquals(4, localDate.getDayOfMonth());
	}

	@Test
	public void testAdd5DaysBeforeNationalDay() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("16.05.2007", LocalDate::from), 5);

		assertEquals(24, localDate.getDayOfMonth());
	}

	@Test
	public void testAdd5DaysBeforeNationalDayBackInTime() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("24.05.2007", LocalDate::from), -5);

		assertEquals(16, localDate.getDayOfMonth());
	}

	@Test
	public void testAdd5DaysBeforeChristmas() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("21.12.2007", LocalDate::from), 5);

		assertEquals(2, localDate.getDayOfMonth());
		assertEquals(Month.JANUARY, localDate.getMonth());
		assertEquals(2008, localDate.getYear());
	}

	@Test
	public void testAdd5DaysBeforeChristmasBackInTime() {
		LocalDate localDate = NorwegianDateUtil.addWorkingDaysToDate(FORMAT().parse("02.01.2008", LocalDate::from), -5);

		assertEquals(21, localDate.getDayOfMonth());
		assertEquals(Month.DECEMBER, localDate.getMonth());
		assertEquals(2007, localDate.getYear());
	}

	@Test
	public void testWorkingDays() {
		assertFalse(NorwegianDateUtil.isWorkingDay(FORMAT().parse("25.03.2007", LocalDate::from)), "Sunday not working day");
		assertTrue(NorwegianDateUtil.isWorkingDay(FORMAT().parse("26.03.2007", LocalDate::from)), "Monday is working day");
		assertFalse(NorwegianDateUtil.isWorkingDay(FORMAT().parse("01.01.2007", LocalDate::from)), "New years day not working day");
		assertFalse(NorwegianDateUtil.isWorkingDay(FORMAT().parse("08.04.2007", LocalDate::from)), "Easter day not working day");
	}

	@Test
	public void testVariousNorwegianHolidays() {
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
		LocalDate[] holidays = NorwegianDateUtil.getHolidays(2009);
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

	private void checkHoliday(String date) {
		assertTrue(NorwegianDateUtil.isHoliday(FORMAT().parse(date, LocalDate::from)), date);
	}
}
