package no.bekk.bekkopen.banking;

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
 * Provides methods that validates if a Kontonummer is valid with respect to
 * syntax (length and digits only) and that the checksum digit is correct.
 */
public class KontonummerValidator extends StringNumberValidator implements ConstraintValidator<no.bekk.bekkopen.banking.annotation.Kontonummer, String> {

    private static final int LENGTH = 11;
    protected static final int ACCOUNTTYPE_NUM_DIGITS = 2;
    protected static final int REGISTERNUMMER_NUM_DIGITS = 4;
    private static final String NOT_ALLOWED_AS_LEADING = "0000";
    public static final String ERROR_LEADING_ZEROS = "Leading zeros too many : ";

    private KontonummerValidator() {
        super();
    }

    /**
     * Return true if the provided String is a valid Kontonummmer.
     *
     * @param kontonummer A String containing a Kontonummer
     * @return true or false
     */
    public static boolean isValid(String kontonummer) {
        try {
            KontonummerValidator.getKontonummer(kontonummer);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns an object that represents a Kontonummer.
     *
     * @param kontonummer A String containing a Kontonummer
     * @return A Kontonummer instance
     * @throws IllegalArgumentException thrown if String contains an invalid Kontonummer
     */
    public static Kontonummer getKontonummer(String kontonummer) throws IllegalArgumentException {
        validateSyntax(kontonummer);
        validateChecksum(kontonummer);
        return new Kontonummer(kontonummer);
    }

    /**
     * Returns an object that represents a Kontonummer. The checksum of the
     * supplied kontonummer is changed to a valid checksum if the original
     * kontonummer has an invalid checksum.
     *
     * @param kontonummer A String containing a Kontonummer
     * @return A Kontonummer instance
     * @throws IllegalArgumentException thrown if String contains an invalid Kontonummer, ie. a
     *                                  number which for one cannot calculate a valid checksum.
     */
    public static Kontonummer getAndForceValidKontonummer(String kontonummer) {
        validateSyntax(kontonummer);
        try {
            validateChecksum(kontonummer);
        } catch (IllegalArgumentException iae) {
            Kontonummer k = new Kontonummer(kontonummer);
            int checksum = calculateMod11CheckSum(getMod11Weights(k), k);
            kontonummer = kontonummer.substring(0, LENGTH - 1) + checksum;
        }
        return new Kontonummer(kontonummer);
    }

    static void validateSyntax(String kontonummer) {
        if (kontonummer.startsWith(NOT_ALLOWED_AS_LEADING)) {
            throw new IllegalArgumentException(ERROR_LEADING_ZEROS + kontonummer);
        }
        validateLengthAndAllDigits(kontonummer, LENGTH);
    }

    static void validateAccountTypeSyntax(String accountType) {
        validateLengthAndAllDigits(accountType, ACCOUNTTYPE_NUM_DIGITS);
    }

    static void validateRegisternummerSyntax(String registernummer) {
        validateLengthAndAllDigits(registernummer, REGISTERNUMMER_NUM_DIGITS);
    }

    static void validateChecksum(String kontonummer) {
        Kontonummer k = new Kontonummer(kontonummer);
        int k1 = calculateMod11CheckSum(getMod11Weights(k), k);
        if (k1 != k.getChecksumDigit()) {
            throw new IllegalArgumentException(ERROR_INVALID_CHECKSUM + kontonummer);
        }
    }

    public void initialize(no.bekk.bekkopen.banking.annotation.Kontonummer constraintAnnotation) {
    }

    public boolean isValid(String kontonummer, ConstraintValidatorContext context) {
        if(kontonummer == null){
            return true;
        }

        return isValid(kontonummer);
    }
}
