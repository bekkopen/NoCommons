package no.bekk.bekkopen.org;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrganisasjonsnummerCalculatorTest {

	private static final int LIST_LENGTH = 100;

	@Test
	public void testGetOrganisasjonsnummerList() {
		List<?> options = OrganisasjonsnummerCalculator.getOrganisasjonsnummerList(LIST_LENGTH);
		assertEquals(LIST_LENGTH, options.size());
      for (Object option : options) {
         Organisasjonsnummer orgNr = (Organisasjonsnummer) option;
         assertTrue(OrganisasjonsnummerValidator.isValid(orgNr.toString()));
      }
	}

}
