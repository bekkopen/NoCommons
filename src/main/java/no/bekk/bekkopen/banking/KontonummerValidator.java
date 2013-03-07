package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.common.StringNumberValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
        return isValid(kontonummer);
    }
}
