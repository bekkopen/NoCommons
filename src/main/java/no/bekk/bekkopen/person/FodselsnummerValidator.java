package no.bekk.bekkopen.person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.bekk.bekkopen.common.StringNumberValidator;

/**
 * Provides methods that validates if a Fodselsnummer is valid with respect to
 * syntax, Individnummer, Date and checksum digits.
 */
public class FodselsnummerValidator extends StringNumberValidator implements ConstraintValidator<GyldigFodselsnummer, String> {

	private static final int LENGTH = 11;

	private static final String DATE_FORMAT = "ddMMyyyy";

	private static final int[] K1_WEIGHTS = new int[] { 2, 5, 4, 9, 8, 1, 6, 7, 3 };

	protected static final String ERROR_INVALID_DATE = "Invalid date in fødselsnummer : ";

	protected static final String ERROR_INVALID_INDIVIDNUMMER = "Invalid individnummer in fødselsnummer : ";

	/**
	 * Returns an object that represents a Fodselsnummer.
	 * 
	 * @param fodselsnummer
	 *            A String containing a Fodselsnummer
	 * @return A Fodselsnummer instance
	 * @throws IllegalArgumentException
	 *             thrown if String contains an invalid Fodselsnummer
	 */
	public static Fodselsnummer getFodselsnummer(String fodselsnummer) throws IllegalArgumentException {
		validateSyntax(fodselsnummer);
		validateIndividnummer(fodselsnummer);
		validateDate(fodselsnummer);
		validateChecksums(fodselsnummer);
		return new Fodselsnummer(fodselsnummer);
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
	
        /**
         *
         * Initialize-method normally used only by a JSR303 validator.
         *
         * @Param gyldigFodselsnummer
         *          Instance of GyldigFodselsnummer. Called
         */	
        @Override
        public void initialize(GyldigFodselsnummer gyldigFodselsnummer) {
           //noop
        }
        
	 /**
	  * Validation method used by a JSR303 validator. Normally it is better to call the static methods directly.  
	  *  
	  * @param fodselsnummer
	  *          The fodselsnummer to be validated
	  * @param constraintValidatorContext
	  * @return
	  */
        @Override
        public boolean isValid(String fodselsnummer, ConstraintValidatorContext constraintValidatorContext) {
            return isValid(fodselsnummer);
        }	

	static void validateSyntax(String fodselsnummer) {
		validateLengthAndAllDigits(fodselsnummer, LENGTH);
	}

	static void validateIndividnummer(String fodselsnummer) {
		Fodselsnummer fnr = new Fodselsnummer(fodselsnummer);
		if (fnr.getCentury() == null) {
			throw new IllegalArgumentException(ERROR_INVALID_INDIVIDNUMMER + fodselsnummer);
		}
	}

	static void validateDate(String fodselsnummer) {
		Fodselsnummer fnr = new Fodselsnummer(fodselsnummer);
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
		Fodselsnummer fnr = new Fodselsnummer(fodselsnummer);
		int k1 = calculateFirstChecksumDigit(fnr);
		int k2 = calculateSecondChecksumDigit(fnr);
		if (k1 != fnr.getChecksumDigit1() || k2 != fnr.getChecksumDigit2()) {
			throw new IllegalArgumentException(ERROR_INVALID_CHECKSUM + fodselsnummer);
		}
	}

	static int calculateFirstChecksumDigit(Fodselsnummer fodselsnummer) {
		return calculateMod11CheckSum(K1_WEIGHTS, fodselsnummer);
	}

	static int calculateSecondChecksumDigit(Fodselsnummer fodselsnummer) {
		return calculateMod11CheckSum(getMod11Weights(fodselsnummer), fodselsnummer);
	}
}
