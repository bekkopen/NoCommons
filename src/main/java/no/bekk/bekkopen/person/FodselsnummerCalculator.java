package no.bekk.bekkopen.person;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
   * Returns a List with valid Fodselsnummer instances for a given Date and gender.
   *
   * @param date en dato
   * @param kjonn kjønn
   * @return liste med fødselsnummer
   */

	public static List<Fodselsnummer> getFodselsnummerForDateAndGender(Date date, KJONN kjonn) {
		List<Fodselsnummer> result = getManyFodselsnummerForDate(date);
		splitByGender(kjonn, result);
		return result;
	}

  /**
   * Return one random valid fodselsnummer on a given date
   * @param date en dato
   * @return et fødselsnummer
   */
	public static Fodselsnummer getFodselsnummerForDate(Date date){
		List<Fodselsnummer> fodselsnummerList = getManyFodselsnummerForDate(date);
		Collections.shuffle(fodselsnummerList);
		return fodselsnummerList.get(0);
	}

	/**
	 * Returns a List with with VALID DNumber Fodselsnummer instances for a given Date.
	 *
	 * @param date The Date instance
	 * @return A List with Fodelsnummer instances
	 */

	public static List<Fodselsnummer> getManyDNumberFodselsnummerForDate(Date date) {
		if (date == null) {
			throw new IllegalArgumentException();
		}
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String centuryString = getCentury(date);
		String dateString = df.format(date);
		dateString = Character.toChars(dateString.charAt(0) + 4)[0] +
      dateString.substring(1);
		return generateFodselsnummerForDate(dateString, centuryString);
	}

	/**
	 * Returns a List with with VALID DNumber Fodselsnummer instances for a given Date.
	 *
	 * @param date The Date instance
	 * @return A List with Fodelsnummer instances
	 */

	public static List<Fodselsnummer> getManySyntheticFodselsnummerForDate(Date date) {
		if (date == null) {
			throw new IllegalArgumentException();
		}
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String centuryString = getCentury(date);
		String dateString = df.format(date);
		dateString = dateString.substring(0, 2) +
      Character.toChars(dateString.charAt(2) + 8)[0] +
      dateString.substring(3);
		return generateFodselsnummerForDate(dateString, centuryString);
	}

	/**
	 * Returns a List with with VALID DNumber Fodselsnummer instances for a given Date.
	 *
	 * @param date The Date instance
	 * @return A List with Fodelsnummer instances
	 */

	public static List<Fodselsnummer> getManySyntheticDNumberFodselsnummerForDate(Date date) {
		if (date == null) {
			throw new IllegalArgumentException();
		}
		DateFormat df = new SimpleDateFormat("ddMMyy");
		String centuryString = getCentury(date);
		String dateString = df.format(date);
		dateString = Character.toChars(dateString.charAt(0) + 4)[0] +
      dateString.substring(1, 2) +
      Character.toChars(dateString.charAt(2) + 8)[0] +
      dateString.substring(3);
		return generateFodselsnummerForDate(dateString, centuryString);
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
		String centuryString = getCentury(date);
		String dateString = df.format(date);
		return generateFodselsnummerForDate(dateString, centuryString);
	}

	private static List<Fodselsnummer> generateFodselsnummerForDate(String dateString, String centuryString) {
		List<Fodselsnummer> result = new ArrayList<>();
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
				if (centuryByIndividnummer != null && centuryByIndividnummer.equals(centuryString) && FodselsnummerValidator.isValid(fodselsnummer.getValue())) {
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
