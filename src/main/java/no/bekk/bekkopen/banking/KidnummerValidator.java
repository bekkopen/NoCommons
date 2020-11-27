package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.common.StringNumber;
import no.bekk.bekkopen.common.StringNumberValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static no.bekk.bekkopen.common.Checksums.calculateMod10CheckSum;
import static no.bekk.bekkopen.common.Checksums.calculateMod11CheckSumAllowDash;
import static no.bekk.bekkopen.common.Checksums.getMod10Weights;
import static no.bekk.bekkopen.common.Checksums.getMod11Weights;

public class KidnummerValidator extends StringNumberValidator implements ConstraintValidator<no.bekk.bekkopen.banking.annotation.Kidnummer, String> {

    public static final String ERROR_LENGTH = "A Kidnummer is between 3(+1) and 25 digits";

    private KidnummerValidator() {
        super();
    }

    /**
     * Return true if the provided String is a valid KID-nummmer.
     *
     * @param kidnummer A String containing a Kidnummer
     * @return true or false
     */
    public static boolean isValid(String kidnummer) {
        try {
            KidnummerValidator.getKidnummer(kidnummer);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns an object that represents a Kidnummer.
     *
     * @param kidnummer A String containing a Kidnummer
     * @return A Kidnummer instance
     * @throws IllegalArgumentException thrown if String contains an invalid Kidnummer
     */
    public static Kidnummer getKidnummer(String kidnummer) throws IllegalArgumentException {
        validateSyntax(kidnummer);
        validateChecksum(kidnummer);
        return new Kidnummer(kidnummer);
    }

    static void validateSyntax(String kidnummer) {
        validateAllDigits(kidnummer.replace("-", ""));
        validateLengthInRange(kidnummer, 4, 25);
    }

    private static void validateLengthInRange(String kidnummer, int i, int j) {
        if (kidnummer == null || kidnummer.length() < i || kidnummer.length() > j) {
            throw new IllegalArgumentException(ERROR_LENGTH);
        }
    }

    public static void validateChecksum(String kidnummer) {
        StringNumber k = new Kidnummer(kidnummer);
        int kMod10 = calculateMod10CheckSum(getMod10Weights(k), k);
        if (kMod10 == k.getChecksumDigit()) {
            return;
        }
        String kMod11 = calculateMod11CheckSumAllowDash(getMod11Weights(k), k);
        if ("-".equals(kMod11) || Integer.parseInt(kMod11) == k.getChecksumDigit()) {
            return;
        }
        throw new IllegalArgumentException(ERROR_INVALID_CHECKSUM + kidnummer);
    }

    public void initialize(no.bekk.bekkopen.banking.annotation.Kidnummer constraintAnnotation) {
    }

    public boolean isValid(String kidnummer, ConstraintValidatorContext context) {
        if (kidnummer == null) {
            return true;
        }

        return isValid(kidnummer);
    }
}
