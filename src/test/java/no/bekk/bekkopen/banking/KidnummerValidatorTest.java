package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.common.StringNumberValidator;
import org.junit.jupiter.api.Test;

import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KidnummerValidatorTest {

  private static final String KIDNUMMER_VALID_MOD10 = "2345676";
	private static final String KIDNUMMER_VALID_MOD11 = "12345678903";
	private static final String KIDNUMMER_INVALID_CHECKSUM = "2345674";
	private static final String KIDNUMMER_INVALID_LENGTH_SHORT = "122";
	private static final String KIDNUMMER_INVALID_LENGTH_LONG = "12345678901234567890123456";
  private static final String KIDNUMMER_VALID_WITH_DASH = "1000005-";

    @Test
	public void testInvalidKidnummer() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> KidnummerValidator.validateSyntax(""));
        assertThat(thrown.getMessage(), containsString(StringNumberValidator.ERROR_SYNTAX));
	}

	@Test
	public void testInvalidKidnummerNotDigits() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> KidnummerValidator.validateSyntax("abcdefghijk"));
	    assertThat(thrown.getMessage(), containsString(StringNumberValidator.ERROR_SYNTAX));
	}

	@Test
	public void testInvalidKidnummerTooShort() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> KidnummerValidator.validateSyntax(KIDNUMMER_INVALID_LENGTH_SHORT));
	    assertThat(thrown.getMessage(), containsString(KidnummerValidator.ERROR_LENGTH));
	}

	@Test
	public void testInvalidKidnummerTooLong() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> KidnummerValidator.validateSyntax(KIDNUMMER_INVALID_LENGTH_LONG));
	    assertThat(thrown.getMessage(), containsString(KidnummerValidator.ERROR_LENGTH));
	}

	@Test
	public void testInvalidKidnummerWrongChecksum() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> KidnummerValidator.validateChecksum(KIDNUMMER_INVALID_CHECKSUM));
	    assertThat(thrown.getMessage(), containsString(ERROR_INVALID_CHECKSUM));
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
