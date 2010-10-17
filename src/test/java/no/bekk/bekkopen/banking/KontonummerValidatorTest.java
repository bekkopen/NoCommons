package no.bekk.bekkopen.banking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import no.bekk.bekkopen.NoCommonsTestCase;

import org.junit.Test;

public class KontonummerValidatorTest extends NoCommonsTestCase {

	private static final String KONTONUMMER_VALID = "99990000006";
	private static final String KONTONUMMER_INVALID_CHECKSUM = "99990000005";

	@Test
	public void testInvalidKontonummerWrongLength() {
		try {
			KontonummerValidator.validateSyntax("123456789012");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, KontonummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidKontonummerNotDigits() {
		try {
			KontonummerValidator.validateSyntax("abcdefghijk");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, KontonummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidKontonummerWrongChecksum() {
		try {
			KontonummerValidator.validateChecksum(KONTONUMMER_INVALID_CHECKSUM);
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, KontonummerValidator.ERROR_INVALID_CHECKSUM);
		}
	}

	@Test
	public void testInvalidAccountTypeWrongLength() {
		StringBuffer b = new StringBuffer(KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS + 1);
		for (int i = 0; i < KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS + 1; i++) {
			b.append("0");
		}
		try {
			KontonummerValidator.validateAccountTypeSyntax(b.toString());
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, KontonummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidAccountTypeNotDigits() {
		StringBuffer b = new StringBuffer(KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS);
		for (int i = 0; i < KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS; i++) {
			b.append("A");
		}
		try {
			KontonummerValidator.validateAccountTypeSyntax(b.toString());
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, KontonummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidRegisternummerNotDigits() {
		StringBuffer b = new StringBuffer(KontonummerValidator.REGISTERNUMMER_NUM_DIGITS);
		for (int i = 0; i < KontonummerValidator.REGISTERNUMMER_NUM_DIGITS; i++) {
			b.append("A");
		}
		try {
			KontonummerValidator.validateRegisternummerSyntax(b.toString());
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, KontonummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidRegisternummerWrongLength() {
		StringBuffer b = new StringBuffer(KontonummerValidator.REGISTERNUMMER_NUM_DIGITS + 1);
		for (int i = 0; i < KontonummerValidator.REGISTERNUMMER_NUM_DIGITS + 1; i++) {
			b.append("0");
		}
		try {
			KontonummerValidator.validateRegisternummerSyntax(b.toString());
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, KontonummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testGetValidKontonummerFromInvalidKontonummerWrongChecksum() {
		Kontonummer knr = KontonummerValidator.getAndForceValidKontonummer(KONTONUMMER_INVALID_CHECKSUM);
		assertTrue(KontonummerValidator.isValid(knr.toString()));
	}

	@Test
	public void testIsValid() {
		assertTrue(KontonummerValidator.isValid(KONTONUMMER_VALID));
		assertFalse(KontonummerValidator.isValid(KONTONUMMER_INVALID_CHECKSUM));
	}

}
