package no.bekk.bekkopen.org;

import no.bekk.bekkopen.common.StringNumberValidator;

/**
 * Provides methods that validates if an Organisasjonsnummer is valid with
 * respect to syntax (length and digits only) and that the checksum digit is
 * correct.
 */
public class OrganisasjonsnummerValidator extends StringNumberValidator {

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

	static void validateSyntax(String fodselsnummer) {
		validateLengthAndAllDigits(fodselsnummer, LENGTH);
	}

	static void validateChecksum(String organisasjonsnummer) {
		Organisasjonsnummer onr = new Organisasjonsnummer(organisasjonsnummer);
		int k1 = calculateMod11CheckSum(getMod11Weights(onr), onr);
		if (k1 != onr.getChecksumDigit()) {
			throw new IllegalArgumentException(ERROR_INVALID_CHECKSUM + organisasjonsnummer);
		}
	}

}
