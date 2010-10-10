package no.bekk.bekkopen.org;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class OrganisasjonsnummerTest {

	private static final String ORGANISASJONSNUMMER = "123456789";
	private Organisasjonsnummer o = null;

	@Before
	public void setUpOrganisasjonsnummer() throws Exception {
		o = new Organisasjonsnummer(ORGANISASJONSNUMMER);
	}

	@Test
	public void testOrganisasjonsnummer() {
		assertNotNull(o);
		assertEquals(ORGANISASJONSNUMMER, o.getValue());
	}

	@Test
	public void testGetChecksumDigit() {
		assertEquals(9, o.getChecksumDigit());
	}

}
