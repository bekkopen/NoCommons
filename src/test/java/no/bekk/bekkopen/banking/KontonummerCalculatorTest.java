package no.bekk.bekkopen.banking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class KontonummerCalculatorTest {

	private static final int LIST_LENGTH = 100;
	private static final String TEST_ACCOUNT_TYPE = "45";
	private static final String TEST_REGISTERNUMMER = "9710";

	@Test
	public void testGetKontonummerList() {
		List<?> options = KontonummerCalculator.getKontonummerList(LIST_LENGTH);
		assertEquals(LIST_LENGTH, options.size());
		Iterator<?> i = options.iterator();
		while (i.hasNext()) {
			Kontonummer kontoNr = (Kontonummer) i.next();
			assertTrue(KontonummerValidator.isValid(kontoNr.toString()));
		}
	}

	@Test
	public void testGetKontonummerListForAccountType() {
		List<?> options = KontonummerCalculator.getKontonummerListForAccountType(TEST_ACCOUNT_TYPE, LIST_LENGTH);
		assertEquals(LIST_LENGTH, options.size());
		Iterator<?> i = options.iterator();
		while (i.hasNext()) {
			Kontonummer kontoNr = (Kontonummer) i.next();
			assertTrue(KontonummerValidator.isValid(kontoNr.toString()));
			assertTrue(kontoNr.getAccountType().equals(TEST_ACCOUNT_TYPE));
		}
	}

	@Test
	public void testGetKontonummerListForRegisternummer() {
		List<?> options = KontonummerCalculator.getKontonummerListForRegisternummer(TEST_REGISTERNUMMER, LIST_LENGTH);
		assertEquals(LIST_LENGTH, options.size());
		Iterator<?> i = options.iterator();
		while (i.hasNext()) {
			Kontonummer kontoNr = (Kontonummer) i.next();
			assertTrue(KontonummerValidator.isValid(kontoNr.toString()));
			assertTrue(kontoNr.getRegisternummer().equals(TEST_REGISTERNUMMER));
		}
	}

}
