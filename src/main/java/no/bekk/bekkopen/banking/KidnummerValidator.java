package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.common.StringNumber;
import no.bekk.bekkopen.common.StringNumberValidator;

public class KidnummerValidator extends StringNumberValidator {

	public static final String ERROR_LENGTH = "A Kidnummer is between 2 and 25 digits";

	private KidnummerValidator() {
		super();
	}

	/**
	 * Return true if the provided String is a valid KID-nummmer.
	 * 
	 * @param kidnummer
	 *            A String containing a Kidnummer
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
	 * @param kidnummer
	 *            A String containing a Kidnummer
	 * @return A Kidnummer instance
	 * @throws IllegalArgumentException
	 *             thrown if String contains an invalid Kidnummer
	 */
	public static Kidnummer getKidnummer(String kidnummer) throws IllegalArgumentException {
		validateSyntax(kidnummer);
		validateChecksum(kidnummer);
		return new Kidnummer(kidnummer);
	}

	static void validateSyntax(String kidnummer) {
		validateAllDigits(kidnummer);
		validateLengthInRange(kidnummer, 2, 25);
	}

	private static void validateLengthInRange(String kidnummer, int i, int j) {
		if (kidnummer == null || kidnummer.length() < i || kidnummer.length() > j) {
			throw new IllegalArgumentException(ERROR_LENGTH);
		}
	}

	public static void validateChecksum(String kidnummer) {
		StringNumber k = new Kidnummer(kidnummer);
		int kMod10 = calculateMod10CheckSum(getMod10Weights(k), k);
		int kMod11 = calculateMod11CheckSum(getMod11Weights(k), k);
		if (kMod10 != k.getChecksumDigit() && kMod11 != k.getChecksumDigit()) {
			throw new IllegalArgumentException(ERROR_INVALID_CHECKSUM + kidnummer);
		}
	}

}
