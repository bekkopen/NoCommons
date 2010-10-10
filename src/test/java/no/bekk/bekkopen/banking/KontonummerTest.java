package no.bekk.bekkopen.banking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class KontonummerTest {

	private static final String KONTONUMMER = "99990000001";
	private static final String KONTONUMMER_WITH_DOTS = "9999.00.00001";
	private Kontonummer k = null;

	@Before
	public void setUpKontonummer() throws Exception {
		k = new Kontonummer(KONTONUMMER);
	}

	@Test
	public void testKontonummer() {
		assertNotNull(k);
		assertEquals(KONTONUMMER, k.getValue());
	}

	@Test
	public void testGetRegisternummer() {
		assertEquals("9999", k.getRegisternummer());
	}

	@Test
	public void testGetChecksumDigit() {
		assertEquals(1, k.getChecksumDigit());
	}

	@Test
	public void testGetSecondGroupOfDigits() {
		assertEquals("00", k.getAccountType());
	}

	@Test
	public void testGetKonto() {
		assertEquals("000000", k.getKonto());
	}

	@Test
	public void testGetGroupedValue() {
		assertNotNull(k);
		assertEquals(KONTONUMMER_WITH_DOTS, k.getGroupedValue());
	}

}
