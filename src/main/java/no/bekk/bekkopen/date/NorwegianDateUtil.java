package no.bekk.bekkopen.date;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * Utility class for Norwegian dates.
 */
public class NorwegianDateUtil {
	private static Map<Integer, Set<Date>> holidays;

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
	public static Date addWorkingDaysToDate(Date date, int days) {
		Calendar cal = dateToCalendar(date);

		for (int i = 0; i < days; i++) {
			cal.add(Calendar.DATE, 1);
			while (!isWorkingDay(cal)) {
				cal.add(Calendar.DATE, 1);
			}
		}

		return cal.getTime();
	}

	/**
	 * Will check if the given date is a working day. That is check if the given
	 * date is a weekend day or a national holiday.
	 *
	 * @param date
	 *            The date to check.
	 * @return true if the given date is a working day, false otherwise.
	 */
	public static boolean isWorkingDay(Date date) {
		return isWorkingDay(dateToCalendar(date));
	}

	/**
	 * Check if given Date object is a holiday.
	 *
	 * @param date
	 *            The Date to check.
	 * @return true if holiday, false otherwise.
	 */
	public static boolean isHoliday(Date date) {
		return isHoliday(dateToCalendar(date));
	}

	/**
	 * Return a sorted array of holidays for a given year.
	 *
	 * @param year
	 *            The year to get holidays for.
	 * @return The array of holidays, sorted by date.
	 */
	public static Date[] getHolidays(int year) {
		Set<Date> days = getHolidaySet(year);
		Date[] dates = days.toArray(new Date[days.size()]);
		Arrays.sort(dates);
		return dates;
	}

	/**
	 * Get a set of holidays for a given year.
	 *
	 * @param year
	 *            The year to get holidays for.
	 * @return The set of dates.
	 */
	private static Set<Date> getHolidaySet(int year) {
		if (holidays == null) {
			holidays = new HashMap<Integer, Set<Date>>();
		}
		if (!holidays.containsKey(year)) {
			Set<Date> yearSet = new HashSet<Date>();

			// Add set holidays.
			yearSet.add(getDate(1, Calendar.JANUARY, year));
			yearSet.add(getDate(1, Calendar.MAY, year));
			yearSet.add(getDate(17, Calendar.MAY, year));
			yearSet.add(getDate(25, Calendar.DECEMBER, year));
			yearSet.add(getDate(26, Calendar.DECEMBER, year));

			// Add movable holidays - based on easter day.
			Calendar easterDay = dateToCalendar(getEasterDay(year));

			// Sunday before easter.
			yearSet.add(rollGetDate(easterDay, -7));

			// Thurday before easter.
			yearSet.add(rollGetDate(easterDay, -3));

			// Friday before easter.
			yearSet.add(rollGetDate(easterDay, -2));

			// Easter day.
			yearSet.add(easterDay.getTime());

			// Second easter day.
			yearSet.add(rollGetDate(easterDay, 1));

			// "Kristi himmelfart" day.
			yearSet.add(rollGetDate(easterDay, 39));

			// "Pinse" day.
			yearSet.add(rollGetDate(easterDay, 49));

			// Second "Pinse" day.
			yearSet.add(rollGetDate(easterDay, 50));

			holidays.put(year, yearSet);
		}
		return holidays.get(year);
	}

	/**
	 * Will check if the given date is a working day. That is check if the given
	 * date is a weekend day or a national holiday.
	 *
	 * @param cal
	 *            The Calendar object representing the date.
	 * @return true if the given date is a working day, false otherwise.
	 */
	private static boolean isWorkingDay(Calendar cal) {
		return cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
				&& !isHoliday(cal);
	}

	/**
	 * Check if given Calendar object represents a holiday.
	 *
	 * @param cal
	 *            The Calendar to check.
	 * @return true if holiday, false otherwise.
	 */
	private static boolean isHoliday(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		Set<?> yearSet = getHolidaySet(year);
		for (Object aYearSet : yearSet) {
			Date date = (Date) aYearSet;
			if (checkDate(cal, dateToCalendar(date))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calculates easter day (sunday) by using Spencer Jones formula found here:
	 * <a href="http://no.wikipedia.org/wiki/P%C3%A5skeformelen">Wikipedia -
	 * Påskeformelen</a>
	 *
	 * @param year
	 *            The year to calculate from.
	 * @return The Calendar object representing easter day for the given year.
	 */
	private static Date getEasterDay(int year) {
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

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, n - 1);
		cal.set(Calendar.DATE, p + 1);

		return cal.getTime();
	}

	/**
	 * Check if the given dates match on day and month.
	 *
	 * @param cal
	 *            The Calendar representing the first date.
	 * @param other
	 *            The Calendar representing the second date.
	 * @return true if they match, false otherwise.
	 */
	private static boolean checkDate(Calendar cal, Calendar other) {
		return checkDate(cal, other.get(Calendar.DATE), other.get(Calendar.MONTH));
	}

	/**
	 * Check if the given date represents the given date and month.
	 *
	 * @param cal
	 *            The Calendar object representing date to check.
	 * @param date
	 *            The date.
	 * @param month
	 *            The month.
	 * @return true if they match, false otherwise.
	 */
	private static boolean checkDate(Calendar cal, int date, int month) {
		return cal.get(Calendar.DATE) == date && cal.get(Calendar.MONTH) == month;
	}

	/**
	 * Convert the given Date object to a Calendar instance.
	 *
	 * @param date
	 *            The Date object.
	 * @return The Calendar instance.
	 */
	private static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * Add the given number of days to the calendar and convert to Date.
	 *
	 * @param calendar
	 *            The calendar to add to.
	 * @param days
	 *            The number of days to add.
	 * @return The date object given by the modified calendar.
	 */
	private static Date rollGetDate(Calendar calendar, int days) {
		Calendar easterSunday = (Calendar) calendar.clone();
		easterSunday.add(Calendar.DATE, days);
		return easterSunday.getTime();
	}

	/**
	 * Get the date for the given values.
	 *
	 * @param day
	 *            The day.
	 * @param month
	 *            The month.
	 * @param year
	 *            The year.
	 * @return The date represented by the given values.
	 */
	private static Date getDate(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		return cal.getTime();
	}
}
