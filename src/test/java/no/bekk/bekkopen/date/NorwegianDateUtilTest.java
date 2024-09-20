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

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.NavigableSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.co.probablyfine.matchers.Java8Matchers.where;

public class NorwegianDateUtilTest {
	
	public static ZoneId NORGESONE = ZoneId.of("Europe/Oslo");

	@Test
	public void testAdd2DaysWithinSameWeek() {
		ZonedDateTime zonedDateTime = LocalDate.of(2024, 9, 18).atStartOfDay(NORGESONE);

		ZonedDateTime ny = NorwegianDateUtil.addWorkingDaysToDate(zonedDateTime, 2);

		assertEquals(20, ny.getDayOfMonth());
	}


	@Test
	public void testAdd2DaysBeforeWeekend() {
		ZonedDateTime zonedDateTime = LocalDate.of(2024, 9, 20).atStartOfDay(NORGESONE);

		ZonedDateTime ny = NorwegianDateUtil.addWorkingDaysToDate(zonedDateTime, 2);

		assertEquals(24, ny.getDayOfMonth());
	}


	@Test
	void testAdd2DaysToLastDayOfMonth() {
		ZonedDateTime zonedDateTime = LocalDate.of(2024, 9, 30).atStartOfDay(NORGESONE);

		ZonedDateTime ny = NorwegianDateUtil.addWorkingDaysToDate(zonedDateTime, 2);

		assertEquals(2, ny.getDayOfMonth());
		assertEquals(Month.OCTOBER, ny.getMonth());
	}

	@Test
	void testAdd5DaysWithNoHolidays() {
		ZonedDateTime zonedDateTime = LocalDate.of(2024, 9, 30).atStartOfDay(NORGESONE);

		ZonedDateTime ny = NorwegianDateUtil.addWorkingDaysToDate(zonedDateTime, 5);

		assertEquals(7, ny.getDayOfMonth());
		assertEquals(Month.OCTOBER, ny.getMonth());
	}

	@Test
	void testAdd5DaysBeforeEasterHoliday() {
		ZonedDateTime zonedDateTime = LocalDate.of(2025, 4, 11).atStartOfDay(NORGESONE);

		ZonedDateTime ny = NorwegianDateUtil.addWorkingDaysToDate(zonedDateTime, 5);

		assertEquals(23, ny.getDayOfMonth());
		assertEquals(Month.APRIL, ny.getMonth());
	}

	@Test
	void testAdd5DaysBeforeNationalDay() {
		ZonedDateTime zonedDateTime = LocalDate.of(2007, 5, 16).atStartOfDay(NORGESONE);

		ZonedDateTime ny = NorwegianDateUtil.addWorkingDaysToDate(zonedDateTime, 5);

		assertEquals(24, ny.getDayOfMonth());
		assertEquals(Month.MAY, ny.getMonth());
	}

	@Test
	void testAdd5DaysBeforeChristmas() {
		ZonedDateTime zonedDateTime = LocalDate.of(2024, 12, 20).atStartOfDay(NORGESONE);

		ZonedDateTime ny = NorwegianDateUtil.addWorkingDaysToDate(zonedDateTime, 6);

		assertEquals(2, ny.getDayOfMonth());
		assertEquals(Month.JANUARY, ny.getMonth());
		assertEquals(2025, ny.getYear());
	}

	@Test
	public void testWorkingDays() {
		assertFalse(NorwegianDateUtil.isWorkingDay(LocalDate.of(2024, 9, 22).atStartOfDay(NORGESONE)), "Sunday not working day");
		assertTrue(NorwegianDateUtil.isWorkingDay(LocalDate.of(2024, 9, 16).atStartOfDay(NORGESONE)), "Monday is working day");
		assertFalse(NorwegianDateUtil.isWorkingDay(LocalDate.of(2025, 1, 1).atStartOfDay(NORGESONE)), "New years day not working day");
		assertFalse(NorwegianDateUtil.isWorkingDay(LocalDate.of(2007, 4, 8).atStartOfDay(NORGESONE)), "Easter day not working day");
	}


	@Test
	public void testVariousNorwegianHolidays() {
		// Set dates
		checkHoliday(LocalDate.of(2007, 1, 1));
		checkHoliday(LocalDate.of(2007, 5, 1));
		checkHoliday(LocalDate.of(2007, 5, 17));
		checkHoliday(LocalDate.of(2007, 12, 25));
		checkHoliday(LocalDate.of(2007, 12, 26));

		// Movable daLocalDate.of(2, 2, 2);tes 2007
		checkHoliday(LocalDate.of(2007, 4, 1));
		checkHoliday(LocalDate.of(2007, 4, 5));
		checkHoliday(LocalDate.of(2007, 4, 6));
		checkHoliday(LocalDate.of(2007, 4, 8));
		checkHoliday(LocalDate.of(2007, 4, 8));
		checkHoliday(LocalDate.of(2007, 5, 17));
		checkHoliday(LocalDate.of(2007, 5, 27));
		checkHoliday(LocalDate.of(2007, 5, 28));

		// Movable daLocalDate.of(2, 2, 2);tes 2008
		checkHoliday(LocalDate.of(2008, 3, 16));
		checkHoliday(LocalDate.of(2008, 3, 20));
		checkHoliday(LocalDate.of(2008, 3, 21));
		checkHoliday(LocalDate.of(2008, 3, 23));
		checkHoliday(LocalDate.of(2008, 3, 24));
		checkHoliday(LocalDate.of(2008, 5, 1));
		checkHoliday(LocalDate.of(2008, 5, 11));
		checkHoliday(LocalDate.of(2008, 5, 12));
	}


	@Test
	public void testGetAllNorwegianHolidaysForYear() {
		NavigableSet<LocalDate> holidays = NorwegianDateUtil.getHolidays(2009);

		assertEquals(13, holidays.size());

		LinkedHashSet<LocalDate> fasit = new LinkedHashSet<>(Arrays.asList(
			LocalDate.of(2009, 1, 1),
			LocalDate.of(2009, 4, 5),
			LocalDate.of(2009, 4, 9),
			LocalDate.of(2009, 4, 10),
			LocalDate.of(2009, 4, 12),
			LocalDate.of(2009, 4, 13),
			LocalDate.of(2009, 5, 1),
			LocalDate.of(2009, 5, 17),
			LocalDate.of(2009, 5, 21),
			LocalDate.of(2009, 5, 31),
			LocalDate.of(2009, 6, 1),
			LocalDate.of(2009, 12, 25),
			LocalDate.of(2009, 12, 26)
		));

		assertEquals(fasit, holidays);


	}

	private void checkHoliday(LocalDate date) {
		assertThat(date.atStartOfDay(NORGESONE), where(NorwegianDateUtil::isHoliday));
	}
}
