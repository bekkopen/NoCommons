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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Utility class for Norwegian dates.
 */
public class NorwegianDateUtil {

	public static final String ZONEID_EUROPE_OSLO = "Europe/Oslo";
	public static final ZoneId ZONE_NORWAY = ZoneId.of(ZONEID_EUROPE_OSLO);
	
	private final static Map<Integer, NavigableSet<LocalDate>> holidays = new HashMap<>();

	/**
	 * Adds the given number of working days to the given date. A working day is
	 * specified as a regular Norwegian working day, excluding weekends and all
	 * national holidays.
	 *
	 * Example 1:
	 * - Add 5 working days to Wednesday 21.03.2007  Yields Wednesday
	 * 28.03.2007. (skipping saturday and sunday)
	 *
	 * Example 2:
	 * - Add 5 working days to Wednesday 04.04.2007 (day before
	 * easter-long-weekend)  yields Monday 16.04.2007 (skipping 2 weekends and
	 * 3 weekday holidays).
	 *
	 * @param date
	 *            The original date.
	 * @param days
	 *            The number of working days to add.
	 * @return The new date.
	 */
	public static ZonedDateTime addWorkingDaysToDate(ZonedDateTime date, int days) {
		return Stream.iterate(date, (d) -> d.plusDays(1))
			.filter(NorwegianDateUtil::isWorkingDay)
			.limit(days + 1)
			.max(Comparator.naturalOrder())
			.orElse(date);
	}
	
	/**
	 * Will check if the given date is a working day. That is check if the given
	 * date is a weekend day or a national holiday.
	 *
	 * @param date
	 *            The date to check.
	 * @return true if the given date is a working day, false otherwise.
	 */
	public static boolean isWorkingDay(ZonedDateTime date) {
		return date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY && !isHoliday(date);
	}

	/**
	 * Check if given Date object is a holiday.
	 *
	 * @param date
	 *            The Date to check.
	 * @return true if holiday, false otherwise.
	 */
	public static boolean isHoliday(ZonedDateTime date) {
        final Set<LocalDate> holidaySet = getHolidaySet(date.getYear());
		return holidaySet.contains(date.toLocalDate());
	}
	
	/**
	 * Return a sorted set of holidays for a given year.
	 *
	 * @param year
	 *            The year to get holidays for.
	 * @return The set of holidays, naturally sorted by date.
	 */
	public static NavigableSet<LocalDate> getHolidays(int year) {
		return getHolidaySet(year);
	}

	/**
	 * Calculates easter day (sunday) by using Spencer Jones formula found here:
	 * <a href="http://no.wikipedia.org/wiki/P%C3%A5skeformelen">Wikipedia -
	 * Påskeformelen</a>
	 *
	 * @param year
	 *            The year to calculate from.
	 * @return The LocalDate representing easter day for the given year.
	 */
	private static LocalDate getEasterDay(int year) {
		int a = year % 19;
		int b = year / 100;
		int c = year % 100;
		int d = b / 4;
		int e = b % 4;
		int f = (b + 8) / 25;
		int g = (b - f + 1) / 3;
		int h = ((19 * a) + b - d - g + 15) % 30;
		int i = c / 4;
		int k = c % 4;
		int l = (32 + (2 * e) + (2 * i) - h - k) % 7;
		int m = (a + (11 * h) + (22 * l)) / 451;
		int n = (h + l - (7 * m) + 114) / 31; // This is the month number.
		int p = (h + l - (7 * m) + 114) % 31; // This is the date minus one.

		return LocalDate.of(year, n, p + 1);
	}
	
	/**
	 * Get a set of holidays for a given year.
	 *
	 * @param year
	 *            The year to get holidays for.
	 * @return The set of dates.
	 */
	private static NavigableSet<LocalDate> getHolidaySet(int year) {
		if (!holidays.containsKey(year)) {
			NavigableSet<LocalDate> yearSet = new TreeSet<>();

			// Add set holidays.
			yearSet.add(LocalDate.of(year, Month.JANUARY, 1));
			yearSet.add(LocalDate.of(year, Month.MAY, 1));
			yearSet.add(LocalDate.of(year, Month.MAY, 17));
			yearSet.add(LocalDate.of(year, Month.DECEMBER, 25));
			yearSet.add(LocalDate.of(year, Month.DECEMBER, 26));

			// Add movable holidays - based on easter day.
			final LocalDate easterDay = getEasterDay(year);

			// Sunday before easter.
			yearSet.add(easterDay.minusDays(7));

			// Thursday before easter.
			yearSet.add(easterDay.minusDays(3));

			// Friday before easter.
			yearSet.add(easterDay.minusDays(2));

			// Easter day.
			yearSet.add(easterDay);

			// Second easter day.
			yearSet.add(easterDay.plusDays(1));

			// "Kristi himmelfart" day.
			yearSet.add(easterDay.plusDays(39));

			// "Pinse" day.
			yearSet.add(easterDay.plusDays(49));

			// Second "Pinse" day.
			yearSet.add(easterDay.plusDays(50));

			holidays.put(year, yearSet);
		}
		return holidays.get(year);
	}
}
