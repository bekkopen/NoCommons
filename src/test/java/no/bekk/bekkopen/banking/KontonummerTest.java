package no.bekk.bekkopen.banking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KontonummerTest {

	private static final String KONTONUMMER = "99990000001";
	private static final String KONTONUMMER_WITH_DOTS = "9999.00.00001";
	private Kontonummer k = null;

	@BeforeEach
	public void setUpKontonummer() {
		k = new Kontonummer(KONTONUMMER);
	}

	@Test
	public void testKontonummer() {
		Assertions.assertNotNull(k);
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
		Assertions.assertNotNull(k);
		assertEquals(KONTONUMMER_WITH_DOTS, k.getGroupedValue());
	}

}
