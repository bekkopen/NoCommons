package no.bekk.bekkopen.person;

import org.junit.jupiter.api.Test;

import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FodselsnummerValidatorTest {

	@Test
	public void testInvalidFodselsnummerWrongLength() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> FodselsnummerValidator.validateSyntax("0123456789"));
	    assertThat(thrown.getMessage(), containsString(FodselsnummerValidator.ERROR_SYNTAX));
	}

	@Test
	public void testInvalidFodselsnummerNotDigits() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> FodselsnummerValidator.validateSyntax("abcdefghijk"));
        assertThat(thrown.getMessage(), containsString(FodselsnummerValidator.ERROR_SYNTAX));
	}

	@Test
	public void testInvalidIndividnummer() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> FodselsnummerValidator.validateIndividnummer("01015780000"));
        assertThat(thrown.getMessage(), containsString(FodselsnummerValidator.ERROR_INVALID_INDIVIDNUMMER));
	}

	@Test
	public void testInvalidDateMonthMax() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> FodselsnummerValidator.validateDate("01130400000"));
	    assertThat(thrown.getMessage(), containsString(FodselsnummerValidator.ERROR_INVALID_DATE));
	}

	@Test
	public void testInvalidDateMonthMin() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> FodselsnummerValidator.validateDate("01000400000"));
	    assertThat(thrown.getMessage(), containsString(FodselsnummerValidator.ERROR_INVALID_DATE));
	}

	@Test
	public void testInvalidDateDayMin() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> FodselsnummerValidator.validateDate("00120467800"));
	    assertThat(thrown.getMessage(), containsString(FodselsnummerValidator.ERROR_INVALID_DATE));
	}

	@Test
	public void testInvalidDateDayMax() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> FodselsnummerValidator.validateDate("32120400000"));
	    assertThat(thrown.getMessage(), containsString(FodselsnummerValidator.ERROR_INVALID_DATE));
	}

	@Test
	public void testInvalidDateLeapDay() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> FodselsnummerValidator.validateDate("29020700000"));
        assertThat(thrown.getMessage(), containsString(FodselsnummerValidator.ERROR_INVALID_DATE));
	}

	@Test
	public void testInvalidFodselsnummerChecksum() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> FodselsnummerValidator.validateChecksums("01010101010"));
	    assertThat(thrown.getMessage(), containsString(ERROR_INVALID_CHECKSUM));
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
