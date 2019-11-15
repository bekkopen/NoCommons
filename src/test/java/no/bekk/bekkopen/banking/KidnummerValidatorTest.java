package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.NoCommonsTestCase;
import no.bekk.bekkopen.common.StringNumberValidator;
import org.junit.Test;

import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static org.junit.Assert.*;

public class KidnummerValidatorTest extends NoCommonsTestCase {

    private static final String KIDNUMMER_VALID_MOD10 = "2345676";
	private static final String KIDNUMMER_VALID_MOD11 = "12345678903";
	private static final String KIDNUMMER_INVALID_CHECKSUM = "2345674";
	private static final String KIDNUMMER_INVALID_LENGTH_SHORT = "122";
	private static final String KIDNUMMER_INVALID_LENGTH_LONG = "12345678901234567890123456";
    private static final String KIDNUMMER_VALID_WITH_DASH = "1000005-";

    @Test
	public void testInvalidKidnummer() {
		try {
			KidnummerValidator.validateSyntax("");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, StringNumberValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidKidnummerNotDigits() {
		try {
			KidnummerValidator.validateSyntax("abcdefghijk");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, StringNumberValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidKidnummerTooShort() {
		try {
			KidnummerValidator.validateSyntax(KIDNUMMER_INVALID_LENGTH_SHORT);
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, KidnummerValidator.ERROR_LENGTH);
		}
	}

	@Test
	public void testInvalidKidnummerTooLong() {
		try {
			KidnummerValidator.validateSyntax(KIDNUMMER_INVALID_LENGTH_LONG);
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, KidnummerValidator.ERROR_LENGTH);
		}
	}

	@Test
	public void testInvalidKidnummerWrongChecksum() {
		try {
			KidnummerValidator.validateChecksum(KIDNUMMER_INVALID_CHECKSUM);
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, ERROR_INVALID_CHECKSUM);
		}
	}

    @Test
    public void testValidKidnummerMod10ButUnableToCalculateMod11() {
        boolean result = KidnummerValidator.isValid("01290865");

        assertEquals(true, result);
    }

	@Test
	public void testIsValidMod10() {
		assertTrue(KidnummerValidator.isValid(KIDNUMMER_VALID_MOD10));
	}

	@Test
	public void testIsValidMod11() {
		assertTrue(KidnummerValidator.isValid(KIDNUMMER_VALID_MOD11));
	}

	@Test
	public void testIsInvalid() {
		assertFalse(KidnummerValidator.isValid(KIDNUMMER_INVALID_CHECKSUM));
	}

    @Test
    public void testKidWithDash() {
        assertTrue(KidnummerValidator.isValid(KIDNUMMER_VALID_WITH_DASH));
    }

}
