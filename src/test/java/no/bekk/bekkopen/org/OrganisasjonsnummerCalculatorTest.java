package no.bekk.bekkopen.org;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class OrganisasjonsnummerCalculatorTest {

	private static final int LIST_LENGTH = 100;

	@Test
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
