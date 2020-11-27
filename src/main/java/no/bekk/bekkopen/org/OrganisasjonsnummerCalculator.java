package no.bekk.bekkopen.org;

import java.util.ArrayList;
import java.util.List;

/**
 * This class calculates valid Organisasjonsnummer instances.
 */
public class OrganisasjonsnummerCalculator {

	private static final int LENGTH = 9;

	private OrganisasjonsnummerCalculator() {
		super();
	}

	/**
	 * Returns a List with completely random but syntactically valid
	 * Organisasjonsnummer instances.
	 *
	 * @param length
	 *            Specifies the number of Organisasjonsnummer instances to
	 *            create in the returned List
	 *
	 * @return A List with random valid Organisasjonsnummer instances
	 */
	public static List<Organisasjonsnummer> getOrganisasjonsnummerList(int length) {
		List<Organisasjonsnummer> result = new ArrayList<>();
		int numAddedToList = 0;
		while (numAddedToList < length) {
			StringBuilder orgnrBuffer = new StringBuilder(LENGTH);
			for (int i = 0; i < LENGTH; i++) {
				orgnrBuffer.append((int) (Math.random() * 10));
			}
			Organisasjonsnummer orgNr;
			try {
				orgNr = OrganisasjonsnummerValidator.getAndForceValidOrganisasjonsnummer(orgnrBuffer.toString());
			} catch (IllegalArgumentException iae) {
				// this number has no valid checksum
				continue;
			}
			result.add(orgNr);
			numAddedToList++;
		}
		return result;
	}

}
