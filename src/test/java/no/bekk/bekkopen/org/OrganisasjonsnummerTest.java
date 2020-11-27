package no.bekk.bekkopen.org;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class OrganisasjonsnummerTest {

	private static final String ORGANISASJONSNUMMER = "123456789";
	private Organisasjonsnummer o = null;

  @BeforeEach
	public void setUpOrganisasjonsnummer() {
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
