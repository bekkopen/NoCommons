package no.bekk.bekkopen.person;

import no.bekk.bekkopen.NoCommonsTestCase;
import no.bekk.bekkopen.person.FodselsnummerValidator;

public class FodselsnummerValidatorTest extends NoCommonsTestCase {

    public void testInvalidFodselsnummerWrongLength() {
        try {
            FodselsnummerValidator.validateSyntax("0123456789");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, FodselsnummerValidator.ERROR_SYNTAX);
        }
    }

    public void testInvalidFodselsnummerNotDigits() {
        try {
            FodselsnummerValidator.validateSyntax("abcdefghijk");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, FodselsnummerValidator.ERROR_SYNTAX);
        }
    }

    public void testInvalidIndividnummer() {
        try {
            FodselsnummerValidator.validateIndividnummer("01015780000");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    FodselsnummerValidator.ERROR_INVALID_INDIVIDNUMMER);
        }
    }

    public void testInvalidDateMonthMax() {
        try {
            FodselsnummerValidator.validateDate("01130400000");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
        }
    }

    public void testInvalidDateMonthMin() {
        try {
            FodselsnummerValidator.validateDate("01000400000");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
        }
    }

    public void testInvalidDateDayMin() {
        try {
            FodselsnummerValidator.validateDate("00120467800");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
        }
    }

    public void testInvalidDateDayMax() {
        try {
            FodselsnummerValidator.validateDate("32120400000");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
        }
    }

    public void testInvalidDateLeapDay() {
        try {
            FodselsnummerValidator.validateDate("29020700000");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, FodselsnummerValidator.ERROR_INVALID_DATE);
        }
    }

    public void testInvalidFodselsnummerChecksum() {
        try {
            FodselsnummerValidator.validateChecksums("01010101010");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    FodselsnummerValidator.ERROR_INVALID_CHECKSUM);
        }
    }

    public void testValidLeapDay() {
        assertTrue(FodselsnummerValidator.isValid("29029633310"));
    }

    public void testIsValid() {
        assertTrue(FodselsnummerValidator.isValid("01010101006"));
    }
    
    public void testDNumberIsValid() {
    	assertTrue(FodselsnummerValidator.isValid("47086303651"));
    }
    
    public void testGetDNumber(){
    	FodselsnummerValidator.getFodselsnummer("47086303651");
    }
}
