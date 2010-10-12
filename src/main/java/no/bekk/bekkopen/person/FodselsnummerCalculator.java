package no.bekk.bekkopen.person;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * This class calculates valid Fodselsnummer instances for a given date.
 */
public class FodselsnummerCalculator {

	private FodselsnummerCalculator() {
		super();
	}

	/**
	 * Returns a List with valid Fodselsnummer instances for a given Date and
	 * gender.
	 */
	public static List<Fodselsnummer> getFodselsnummerForDateAndGender(Date date, KJONN kjonn) {
		List<Fodselsnummer> result = getFodselsnummerForDate(date);
		splitByGender(kjonn, result);
		return result;
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

	/**
	 * Returns a List with valid Fodselsnummer instances for a given Date.
	 * 
	 * @param date
	 *            The Date instance
	 * @return A List with Fodelsnummer instances
	 */
	public static List<Fodselsnummer> getFodselsnummerForDate(Date date) {
		if (date == null) {
			throw new IllegalArgumentException();
		}
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String century = getCentury(date);
		String dateString = df.format(date);
		List<Fodselsnummer> result = new ArrayList<Fodselsnummer>();
		for (int i = 999; i >= 0; i--) {
			StringBuffer sb = new StringBuffer(dateString);
			if (i < 10) {
				sb.append("00");
			} else if (i < 100) {
				sb.append("0");
			}
			sb.append(i);
			Fodselsnummer f = new Fodselsnummer(sb.toString());
			try {
				sb.append(FodselsnummerValidator.calculateFirstChecksumDigit(f));
				f = new Fodselsnummer(sb.toString());
				sb.append(FodselsnummerValidator.calculateSecondChecksumDigit(f));
				f = new Fodselsnummer(sb.toString());
				String centuryByIndividnummer = f.getCentury();
				if (centuryByIndividnummer != null && centuryByIndividnummer.equals(century)) {
					result.add(f);
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

	public static List<Fodselsnummer> getValidFodselsnummere(List<Fodselsnummer> fodselsnumre) {
		List<Fodselsnummer> validFodselsnumre = new ArrayList<Fodselsnummer>();
		for (Fodselsnummer fodselsnummer : fodselsnumre) {
			if (FodselsnummerValidator.isValid(fodselsnummer.getValue())) {
				validFodselsnumre.add(fodselsnummer);
			}
		}
		return validFodselsnumre;
	}

}
