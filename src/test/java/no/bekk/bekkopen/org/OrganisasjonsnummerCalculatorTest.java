package no.bekk.bekkopen.org;

import java.util.Iterator;
import java.util.List;

import no.bekk.bekkopen.org.Organisasjonsnummer;
import no.bekk.bekkopen.org.OrganisasjonsnummerCalculator;
import no.bekk.bekkopen.org.OrganisasjonsnummerValidator;

import junit.framework.TestCase;

/**
 * Test for the OrganisasjonsnummerCalculator.
 * 
 * @author Håvard Nesvold
 */
public class OrganisasjonsnummerCalculatorTest extends TestCase {

	private static final int LIST_LENGTH = 100;

	public void testGetOrganisasjonsnummerList() {
		List<?> options = OrganisasjonsnummerCalculator.getOrganisasjonsnummerList(LIST_LENGTH);
		assertEquals(LIST_LENGTH, options.size());
		Iterator<?> i = options.iterator();
		while (i.hasNext()) {
			Organisasjonsnummer orgNr = (Organisasjonsnummer) i.next();
			assertTrue(OrganisasjonsnummerValidator.isValid(orgNr.toString()));
		}
	}

}
