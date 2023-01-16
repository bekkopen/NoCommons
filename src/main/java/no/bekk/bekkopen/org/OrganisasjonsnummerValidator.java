package no.bekk.bekkopen.org;

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

import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static no.bekk.bekkopen.common.Checksums.calculateMod11CheckSum;
import static no.bekk.bekkopen.common.Checksums.getMod11Weights;

/**
 * Provides methods that validates if an Organisasjonsnummer is valid with
 * respect to syntax (length and digits only) and that the checksum digit is
 * correct.
 */
public class OrganisasjonsnummerValidator extends StringNumberValidator implements ConstraintValidator<no.bekk.bekkopen.org.annotation.Organisasjonsnummer, String> {

	private static final int LENGTH = 9;

	/**
	 * Return true if the provided String is a valid Organisasjonsnummer.
	 * 
	 * @param organisasjonsnummer
	 *            A String containing a Organisasjonsnummer
	 * @return true or false
	 */
	public static boolean isValid(String organisasjonsnummer) {
		try {
			OrganisasjonsnummerValidator.getOrganisasjonsnummer(organisasjonsnummer);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Returns an object that represents an Organisasjonsnummer.
	 * 
	 * @param organisasjonsnummer
	 *            A String containing an Organisasjonsnummer
	 * @return An Organisasjonsnummer instance
	 * @throws IllegalArgumentException
	 *             thrown if String contains an invalid Organisasjonsnummer
	 */
	public static Organisasjonsnummer getOrganisasjonsnummer(String organisasjonsnummer)
			throws IllegalArgumentException {
		validateSyntax(organisasjonsnummer);
		validateChecksum(organisasjonsnummer);
		return new Organisasjonsnummer(organisasjonsnummer);
	}

	/**
	 * Returns an object that represents a Organisasjonsnummer. The checksum of
	 * the supplied organisasjonsnummer is changed to a valid checksum if the
	 * original organisasjonsnummer has an invalid checksum.
	 * 
	 * @param organisasjonsnummer
	 *            A String containing a Organisasjonsnummer
	 * @return A Organisasjonsnummer instance
	 * @throws IllegalArgumentException
	 *             thrown if String contains an invalid Organisasjonsnummer, ie.
	 *             a number which for one cannot calculate a valid checksum.
	 */
	public static Organisasjonsnummer getAndForceValidOrganisasjonsnummer(String organisasjonsnummer) {
		validateSyntax(organisasjonsnummer);
		try {
			validateChecksum(organisasjonsnummer);
		} catch (IllegalArgumentException iae) {
			Organisasjonsnummer onr = new Organisasjonsnummer(organisasjonsnummer);
			int checksum = calculateMod11CheckSum(getMod11Weights(onr), onr);
			organisasjonsnummer = organisasjonsnummer.substring(0, LENGTH - 1) + checksum;
		}
		return new Organisasjonsnummer(organisasjonsnummer);
	}

	static void validateSyntax(String organisasjonsnummer) {
		validateLengthAndAllDigits(organisasjonsnummer, LENGTH);
	}

	static void validateChecksum(String organisasjonsnummer) {
		Organisasjonsnummer onr = new Organisasjonsnummer(organisasjonsnummer);
		int k1 = calculateMod11CheckSum(getMod11Weights(onr), onr);
		if (k1 != onr.getChecksumDigit()) {
			throw new IllegalArgumentException(ERROR_INVALID_CHECKSUM + organisasjonsnummer);
		}
	}

    /**
     * Initialize-method normally used only by a JSR303 validator. Does nothing.
     *
     * @param constraintAnnotation
     *          Foddselsnummer-annotation which is validated
     */
    public void initialize(no.bekk.bekkopen.org.annotation.Organisasjonsnummer constraintAnnotation) {}

    /**
     * Validation method used by a JSR303 validator. Normally it is better to call the static methods directly.
     *
     * @param organisasjonsnummer
     *          The organisasjonsnummer to be validated
     * @param context
     *         context sent in by a validator
     * @return boolean
     *         whether or not the given organisasjonsnummer is valid
     */
    public boolean isValid(String organisasjonsnummer, ConstraintValidatorContext context) {
        if(organisasjonsnummer == null){
            return true;
        }

        return isValid(organisasjonsnummer);
    }
}
