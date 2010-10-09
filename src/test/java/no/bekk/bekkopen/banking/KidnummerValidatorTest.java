package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.NoCommonsTestCase;
import no.bekk.bekkopen.banking.KidnummerValidator;
import no.bekk.bekkopen.common.StringNumberValidator;

public class KidnummerValidatorTest extends NoCommonsTestCase {

    private static final String KIDNUMMER_VALID_MOD10 = "2345676";
    private static final String KIDNUMMER_VALID_MOD11 = "12345678903";
    private static final String KIDNUMMER_INVALID_CHECKSUM = "2345674";
    private static final String KIDNUMMER_INVALID_LENGTH_SHORT = "1";
    private static final String KIDNUMMER_INVALID_LENGTH_LONG = "12345678901234567890123456";

    public void testInvalidKidnummer() {
        try {
            KidnummerValidator.validateSyntax("");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, StringNumberValidator.ERROR_SYNTAX);
        }
    }

    public void testInvalidKidnummerNotDigits() {
        try {
            KidnummerValidator.validateSyntax("abcdefghijk");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, StringNumberValidator.ERROR_SYNTAX);
        }
    }

    public void testInvalidKidnummerTooShort() {
        try {
            KidnummerValidator.validateSyntax(KIDNUMMER_INVALID_LENGTH_SHORT);
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    KidnummerValidator.ERROR_LENGTH);
        }
    }
    
    public void testInvalidKidnummerTooLong() {
        try {
            KidnummerValidator.validateSyntax(KIDNUMMER_INVALID_LENGTH_LONG);
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    KidnummerValidator.ERROR_LENGTH);
        }
    }
    
    public void testInvalidKidnummerWrongChecksum() {
        try {
            KidnummerValidator.validateChecksum(KIDNUMMER_INVALID_CHECKSUM);
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    StringNumberValidator.ERROR_INVALID_CHECKSUM);
        }
    }
    
    public void testIsValidMod10() {
        assertTrue(KidnummerValidator.isValid(KIDNUMMER_VALID_MOD10));
    }

    public void testIsValidMod11() {
        assertTrue(KidnummerValidator.isValid(KIDNUMMER_VALID_MOD11));
    }
    
    public void testIsInvalid() {
        assertFalse(KidnummerValidator.isValid(KIDNUMMER_INVALID_CHECKSUM));
    }

}
