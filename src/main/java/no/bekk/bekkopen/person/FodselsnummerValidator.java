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

import no.bekk.bekkopen.common.StringNumberValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static no.bekk.bekkopen.common.Checksums.calculateMod11CheckSum;

/**
 * Provides methods that validates if a Fodselsnummer is valid with respect to
 * syntax, Individnummer, Date and checksum digits.
 *
 * This validator also validates DNummer as valid.
 * Birth date is modified by adding 4 on the first digit
 *
 * The Norwegian registry Det Norske Folkeregister has decided
 * that it will create synthetic ssn for its test population.
 * This validator will can now validate true for Fodselsnummer that has month +80
 * for synthetic fnr/dnr
 */
public class FodselsnummerValidator extends StringNumberValidator implements ConstraintValidator<no.bekk.bekkopen.person.annotation.Fodselsnummer, String> {

	private static final int LENGTH = 11;

	private static final String DATE_FORMAT = "ddMMyyyy";

	private static final int[] K1_WEIGHTS = new int[] { 2, 5, 4, 9, 8, 1, 6, 7, 3 };
	private static final int[] K2_WEIGHTS = new int[] { 2, 3, 4, 5, 6, 7, 2, 3, 4, 5 };

	protected static final String ERROR_INVALID_DATE = "Invalid date in fødselsnummer : ";

	protected static final String ERROR_INVALID_INDIVIDNUMMER = "Invalid individnummer in fødselsnummer : ";

  /**
   * Truthy validation of synthetic fnr/dnr is true. You are adviced to
   * set til value to true in test environments where you expect
   * synthetic fnr/dnr to appear.
   */
	public static boolean ALLOW_SYNTHETIC_NUMBERS = false;
  static {
    final URL flag = FodselsnummerValidator.class.getResource("/no.bekk.bekkopen.person.allow_synthetic_numbers.flag");
    if (flag != null) {
      ALLOW_SYNTHETIC_NUMBERS = true;
    }
  }

	/**
	 * Returns an object that represents a Fodselsnummer.
	 *
	 * @param fodselsnummer
	 *            A String containing a Fodselsnummer
	 * @return A Fodselsnummer instance
	 * @throws IllegalArgumentException
	 *             thrown if String contains an invalid Fodselsnummer
	 */
	public static no.bekk.bekkopen.person.Fodselsnummer getFodselsnummer(String fodselsnummer) throws IllegalArgumentException {
		validateSyntax(fodselsnummer);
		validateIndividnummer(fodselsnummer);
		validateDate(fodselsnummer);
		validateChecksums(fodselsnummer);
		validateSynthetic(fodselsnummer);
		return new no.bekk.bekkopen.person.Fodselsnummer(fodselsnummer);
	}

  private static void validateSynthetic(String fodselsnummer) {
    if (Fodselsnummer.isSynthetic(fodselsnummer) && !ALLOW_SYNTHETIC_NUMBERS) {
      throw new IllegalArgumentException("Synthetic fødselsnummer is not allowd" + fodselsnummer);
    }
  }

  /**
	 * Return true if the provided String is a valid Fodselsnummer.
	 *
	 * @param fodselsnummer
	 *            A String containing a Fodselsnummer
	 * @return true or false
	 */
	public static boolean isValid(String fodselsnummer) {
		try {
			FodselsnummerValidator.getFodselsnummer(fodselsnummer);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	static void validateSyntax(String fodselsnummer) {
		validateLengthAndAllDigits(fodselsnummer, LENGTH);
	}

	static void validateIndividnummer(String fodselsnummer) {
		no.bekk.bekkopen.person.Fodselsnummer fnr = new no.bekk.bekkopen.person.Fodselsnummer(fodselsnummer);
		if (fnr.getCentury() == null) {
			throw new IllegalArgumentException(ERROR_INVALID_INDIVIDNUMMER + fodselsnummer);
		}
	}

	static void validateDate(String fodselsnummer) {
		no.bekk.bekkopen.person.Fodselsnummer fnr = new no.bekk.bekkopen.person.Fodselsnummer(fodselsnummer);
		try {
			String dateString = fnr.getDateAndMonth() + fnr.getCentury() + fnr.get2DigitBirthYear();
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(dateString);
		} catch (ParseException e) {
			throw new IllegalArgumentException(ERROR_INVALID_DATE + fodselsnummer);
		}
	}

	static void validateChecksums(String fodselsnummer) {
		no.bekk.bekkopen.person.Fodselsnummer fnr = new no.bekk.bekkopen.person.Fodselsnummer(fodselsnummer);
		int k1 = calculateFirstChecksumDigit(fnr);
		int k2 = calculateSecondChecksumDigit(fnr);
		if (k1 != fnr.getChecksumDigit1() || k2 != fnr.getChecksumDigit2()) {
			throw new IllegalArgumentException(ERROR_INVALID_CHECKSUM + fodselsnummer);
		}
	}

	static int calculateFirstChecksumDigit(no.bekk.bekkopen.person.Fodselsnummer fodselsnummer) {
		return calculateMod11CheckSum(K1_WEIGHTS, fodselsnummer);
	}

	static int calculateSecondChecksumDigit(no.bekk.bekkopen.person.Fodselsnummer fodselsnummer) {
		return calculateMod11CheckSum(K2_WEIGHTS, fodselsnummer);
	}

    /**
     * Initialize-method normally used only by a JSR303 validator. Does nothing.
     *
     * @param fodselsnummer
     *          Foddselsnummer-annotation which is validated
     */
    public void initialize(no.bekk.bekkopen.person.annotation.Fodselsnummer fodselsnummer) {}

    /**
     * Validation method used by a JSR303 validator. Normally it is better to call the static methods directly.
     *
     * @param fodselsnummer
     *          The fodselsnummer to be validated
     * @param constraintValidatorContext
     *         context sent in by a validator
     * @return boolean
     *         whether or not the given fodselsnummer is valid
     */
    public boolean isValid(String fodselsnummer, ConstraintValidatorContext constraintValidatorContext) {
        if(fodselsnummer == null){
            return true;
        }

        return isValid(fodselsnummer);
    }
}
