package no.bekk.bekkopen.person;

import no.bekk.bekkopen.NoCommonsTestCase;
import org.junit.Test;

import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FodselsnummerValidatorTest extends NoCommonsTestCase {

	@Test
	public void testInvalidFodselsnummerWrongLength() {
		try {
			FodselsnummerValidator.validateSyntax("0123456789");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, FodselsnummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidFodselsnummerNotDigits() {
		try {
			FodselsnummerValidator.validateSyntax("abcdefghijk");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, FodselsnummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidIndividnummer() {
		try {
			FodselsnummerValidator.validateIndividnummer("01015780000");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_INDIVIDNUMMER);
		}
	}

	@Test
	public void testInvalidDateMonthMax() {
		try {
			FodselsnummerValidator.validateDate("01130400000");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
		}
	}

	@Test
	public void testInvalidDateMonthMin() {
		try {
			FodselsnummerValidator.validateDate("01000400000");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
		}
	}

	@Test
	public void testInvalidDateDayMin() {
		try {
			FodselsnummerValidator.validateDate("00120467800");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
		}
	}

	@Test
	public void testInvalidDateDayMax() {
		try {
			FodselsnummerValidator.validateDate("32120400000");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
		}
	}

	@Test
	public void testInvalidDateLeapDay() {
		try {
			FodselsnummerValidator.validateDate("29020700000");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
		}
	}

	@Test
	public void testInvalidFodselsnummerChecksum() {
		try {
			FodselsnummerValidator.validateChecksums("01010101010");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, ERROR_INVALID_CHECKSUM);
		}
	}

	@Test
	public void testValidLeapDay() {
		assertTrue(FodselsnummerValidator.isValid("29029633310"));
	}

	@Test
	public void testIsValid() {
		assertTrue(FodselsnummerValidator.isValid("01010101006"));
	}

	@Test
	public void testDNumberIsValid() {
		assertTrue(FodselsnummerValidator.isValid("47086303651"));
	}

	@Test
	public void testGetDNumber() {
		FodselsnummerValidator.getFodselsnummer("47086303651");
	}

	@Test
	public void testCalculateSecondChecksumDigit() {
		Fodselsnummer testcase = new Fodselsnummer("1234567891");
		int correctChecksum = 1;
		int calculatedChecksum = FodselsnummerValidator.calculateSecondChecksumDigit(testcase);
		assertEquals(calculatedChecksum, correctChecksum);
	}
}
