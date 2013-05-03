package no.bekk.bekkopen.person;

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
		return parseDNumber(getValue()).substring(0, 4);
	}

	/**
	 * Returns the first 2 digits of the Fodselsnummer that contains the date
	 * (01-31), stripped for eventual d-numbers.
	 *
	 * @return A String containing the date of birth
	 */
	public String getDayInMonth() {
		return parseDNumber(getValue()).substring(0, 2);
	}

	/**
	 * Returns the digits 3 and 4 of the Fodselsnummer that contains the month
	 * (01-12), stripped for eventual d-numbers.
	 *
	 * @return A String containing the date of birth
	 */
	public String getMonth() {
		return parseDNumber(getValue()).substring(2, 4);
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
		if (individnummerInt <= 499) {
			result = "19";
		} else if (individnummerInt >= 500 && birthYear < 40) {
			result = "20";
		} else if (individnummerInt >= 500 && individnummerInt <= 749 && birthYear >= 54) {
			result = "18";
		} else if (individnummerInt >= 900 && birthYear > 39) {
			result = "19";
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
		return parseDNumber(getValue()).substring(0, 6);
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

	public KJONN getKjonn() {
		if (isFemale()) {
			return KJONN.KVINNE;
		} else {
			return KJONN.MANN;
		}
	}

	@Override
	public String toString(){
		return super.getValue();
	}
}
