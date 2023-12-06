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

import no.bekk.bekkopen.common.StringNumber;

/**
 * This class represent a Norwegian social security number - a Fodselsnummer. A
 * Fodselsnummer consists of 11 digits, where the first 6 digits contains the
 * date of birth and the last 5 consists of an Individnummer (3 digits) and two
 * checksum digits.
 */
public class Fodselsnummer extends StringNumber {

	Fodselsnummer(String fodselsnummer) {
		super(fodselsnummer);
	}

	/**
	 * Returns the first 4 digits of the Fodselsnummer that contains the date
	 * (01-31) and month(01-12) of birth.
	 *
	 * @return A String containing the date and month of birth.
	 */
	public String getDateAndMonth() {
		return parseSyntheticNumber(parseDNumber(getValue())).substring(0, 4);
	}

	/**
	 * Returns the first 2 digits of the Fodselsnummer that contains the date
	 * (01-31), stripped for eventual d-numbers.
	 *
	 * @return A String containing the date of birth
	 */
	public String getDayInMonth() {
		return parseSyntheticNumber(parseDNumber(getValue())).substring(0, 2);
	}

	/**
	 * Returns the digits 3 and 4 of the Fodselsnummer that contains the month
	 * (01-12), stripped for eventual d-numbers.
	 *
	 * @return A String containing the date of birth
	 */
	public String getMonth() {
		return parseSyntheticNumber(parseDNumber(getValue())).substring(2, 4);
	}

	/**
	 * Returns the birthyear of the Fodselsnummer
	 *
	 * @return A String containing the year of birth.
	 */
	public String getBirthYear() {
		return getCentury() + get2DigitBirthYear();
	}

	String getCentury() {
		String result = null;
		int individnummerInt = Integer.parseInt(getIndividnummer());
		int birthYear = Integer.parseInt(get2DigitBirthYear());

		if (isDNumber(this.getValue())) {
			if (individnummerInt >= 500 && individnummerInt <= 599) {
				result = "18";
			} else if (individnummerInt <= 199 && birthYear < 40) {
				result = "19";
			} else if ((individnummerInt <= 499 || (individnummerInt >= 600 && individnummerInt <= 999)) && birthYear >= 40) {
				result = "19";
			} else if (individnummerInt >= 200 && individnummerInt <= 999 && birthYear < 40) {
				result = "20";
			}
		} else {
			if (individnummerInt <= 499) {
				result = "19";
			} else if (individnummerInt >= 500 && birthYear < 40) {
				result = "20";
			} else if (individnummerInt >= 500 && individnummerInt <= 749 && birthYear >= 54) {
				result = "18";
			} else if (individnummerInt >= 900 && birthYear > 39) {
				result = "19";
			}
		}

		return result;
	}

	/**
	 * Returns the two digits of the Fodselsnummer that contains the year birth
	 * (00-99).
	 *
	 * @return A String containing the year of birth.
	 */
	public String get2DigitBirthYear() {
		return getValue().substring(4, 6);
	}

	/**
	 * Returns the first 6 digits of the Fodselsnummer that contains the date
	 * (01-31), month(01-12) and year(00-99) of birth.
	 *
	 * @return A String containing the date and month of birth.
	 */
	public String getDateOfBirth() {
		return parseSyntheticNumber(parseDNumber(getValue())).substring(0, 6);
	}

	/**
	 * Returns the last 5 digits of the Fodselsnummer, also referred to as the
	 * Personnummer. The Personnummer consists of the Individnummer (3 digits)
	 * and two checksum digits.
	 *
	 * @return A String containing the Personnummer.
	 */
	public String getPersonnummer() {
		return getValue().substring(6);
	}

	/**
	 * Returns the first three digits of the Personnummer, also known as the
	 * Individnummer.
	 *
	 * @return A String containing the Individnummer.
	 */
	public String getIndividnummer() {
		return getValue().substring(6, 9);
	}

	/**
	 * Returns the digit that decides the gender - the 9th in the Fodselsnummer.
	 *
	 * @return The digit.
	 */
	public int getGenderDigit() {
		return getAt(8);
	}

	/**
	 * Returns the first checksum digit - the 10th in the Fodselsnummer.
	 *
	 * @return The digit.
	 */
	public int getChecksumDigit1() {
		return getAt(9);
	}

	/**
	 * Returns the second checksum digit - the 11th in the Fodselsnummer.
	 *
	 * @return The digit.
	 */
	public int getChecksumDigit2() {
		return getAt(10);
	}

	/**
	 * Returns true if the Fodselsnummer represents a man.
	 *
	 * @return true or false.
	 */
	public boolean isMale() {
		return getGenderDigit() % 2 != 0;
	}

	/**
	 * Returns true if the Fodselsnummer represents a woman.
	 *
	 * @return true or false.
	 */
	public boolean isFemale() {
		return !isMale();
	}

	static String parseSyntheticNumber(String fodselsnummer) {
		if (!isSynthetic(fodselsnummer)) {
			return fodselsnummer;
		} else {
			int monthNumber = Integer.parseInt(fodselsnummer.substring(2, 4));

			//Skatteetaten - synthetic numbers
			if (monthNumber >= 81 && monthNumber <= 92) {
				return fodselsnummer.substring(0, 2) + (getThirdDigit(fodselsnummer) - 8) + fodselsnummer.substring(3);
			}

			//Norsk helsenett - synthetic numbers
			if (monthNumber >= 66 && monthNumber <= 77) {
				String month = Integer.toString(monthNumber - 65);
				if (month.length() == 1) {
					month = "0" + month;
				}
				return fodselsnummer.substring(0, 2) + month + fodselsnummer.substring(4);
			}

			//Norwegian Labour and Welfare Administration (NAV) - synthetic numbers
			if (monthNumber >= 41 && monthNumber <= 52) {
				return fodselsnummer.substring(0, 2) + (getThirdDigit(fodselsnummer) - 4) + fodselsnummer.substring(3);
			}

			throw new IllegalArgumentException(fodselsnummer + " is not a valid synthethic number");
		}
	}

	static boolean isSynthetic(String fodselsnummer) {
		try {
			int monthNumber = Integer.parseInt(fodselsnummer.substring(2, 4));
			if ((monthNumber >= 81 && monthNumber <= 92) || (monthNumber >= 66 && monthNumber <= 77) || (monthNumber >= 41 && monthNumber <= 52)) {
				return true;
			}
		} catch (IllegalArgumentException e) {
			// ignore
		}
		return false;
	}

	static boolean isDNumber(String fodselsnummer) {
		try {
			int firstDigit = getFirstDigit(fodselsnummer);
			if (firstDigit > 3 && firstDigit < 8) {
				return true;
			}
		} catch (IllegalArgumentException e) {
			// ignore
		}
		return false;
	}

	static String parseDNumber(String fodselsnummer) {
		if (!isDNumber(fodselsnummer)) {
			return fodselsnummer;
		} else {
			return (getFirstDigit(fodselsnummer) - 4) + fodselsnummer.substring(1);
		}
	}

	private static int getFirstDigit(String fodselsnummer) {
		return Integer.parseInt(fodselsnummer.substring(0, 1));
	}

	private static int getThirdDigit(String fodselsnummer) {
		return Integer.parseInt(fodselsnummer.substring(2, 3));
	}

	public KJONN getKjonn() {
		if (isFemale()) {
			return KJONN.KVINNE;
		} else {
			return KJONN.MANN;
		}
	}

	@Override
	public String toString() {
		return super.getValue();
	}
}
