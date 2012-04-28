package no.bekk.bekkopen.person;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class calculates valid Fodselsnummer instances for a given date.
 */
public class FodselsnummerCalculator {

	private FodselsnummerCalculator() {
		super();
	}

	/**
	 * Returns a List with valid Fodselsnummer instances for a given Date and gender.
	 */
	public static List<Fodselsnummer> getFodselsnummerForDateAndGender(Date date, KJONN kjonn) {
		List<Fodselsnummer> result = getManyFodselsnummerForDate(date);
		splitByGender(kjonn, result);
		return result;
	}

	/**
	 * Return one random valid fodselsnummer on a given date
	 */
	public static Fodselsnummer getFodselsnummerForDate(Date date){
		List<Fodselsnummer> fodselsnummerList = getManyFodselsnummerForDate(date);
		Collections.shuffle(fodselsnummerList);
		return fodselsnummerList.get(0);
	}

	/**
	 * Returns a List with with VALID Fodselsnummer instances for a given Date.
	 *
	 * @param date The Date instance
	 * @return A List with Fodelsnummer instances
	 */
	public static List<Fodselsnummer> getManyFodselsnummerForDate(Date date) {
		if (date == null) {
			throw new IllegalArgumentException();
		}
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String century = getCentury(date);
		String dateString = df.format(date);
		List<Fodselsnummer> result = new ArrayList<Fodselsnummer>();
		for (int i = 999; i >= 0; i--) {
			StringBuilder sb = new StringBuilder(dateString);
			if (i < 10) {
				sb.append("00");
			} else if (i < 100) {
				sb.append("0");
			}
			sb.append(i);
			Fodselsnummer fodselsnummer = new Fodselsnummer(sb.toString());
			try {
				sb.append(FodselsnummerValidator.calculateFirstChecksumDigit(fodselsnummer));
				fodselsnummer = new Fodselsnummer(sb.toString());

				sb.append(FodselsnummerValidator.calculateSecondChecksumDigit(fodselsnummer));
				fodselsnummer = new Fodselsnummer(sb.toString());

				String centuryByIndividnummer = fodselsnummer.getCentury();
				if (centuryByIndividnummer != null && centuryByIndividnummer.equals(century) && FodselsnummerValidator.isValid(fodselsnummer.getValue())) {
					result.add(fodselsnummer);
				}
			} catch (IllegalArgumentException e) {
				continue;
			}
		}
		return result;
	}

	private static String getCentury(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		return Integer.toString(year).substring(0, 2);
	}

	private static void splitByGender(KJONN kjonn, List<Fodselsnummer> result) {
		Iterator<Fodselsnummer> iter = result.iterator();
		while (iter.hasNext()) {
			Fodselsnummer f = iter.next();
			if (f.getKjonn() != kjonn) {
				iter.remove();
			}
		}
	}

}
