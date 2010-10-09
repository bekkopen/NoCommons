package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.NoCommonsTestCase;
import no.bekk.bekkopen.banking.Kontonummer;
import no.bekk.bekkopen.banking.KontonummerValidator;

public class KontonummerValidatorTest extends NoCommonsTestCase {

    private static final String KONTONUMMER_VALID = "99990000006";
    private static final String KONTONUMMER_INVALID_CHECKSUM = "99990000005";

    public void testInvalidKontonummerWrongLength() {
        try {
            KontonummerValidator.validateSyntax("123456789012");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, KontonummerValidator.ERROR_SYNTAX);
        }
    }

    public void testInvalidKontonummerNotDigits() {
        try {
            KontonummerValidator.validateSyntax("abcdefghijk");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, KontonummerValidator.ERROR_SYNTAX);
        }
    }

    public void testInvalidKontonummerWrongChecksum() {
        try {
            KontonummerValidator.validateChecksum(KONTONUMMER_INVALID_CHECKSUM);
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    KontonummerValidator.ERROR_INVALID_CHECKSUM);
        }
    }
    
    public void testInvalidAccountTypeWrongLength() {
        StringBuffer b = new StringBuffer( KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS + 1 );
    	for (int i = 0; i < KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS + 1; i++) {
        	b.append( "0" );
        }
    	try {
            KontonummerValidator.validateAccountTypeSyntax(b.toString());
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    KontonummerValidator.ERROR_SYNTAX);
        }
    }
    
    public void testInvalidAccountTypeNotDigits() {
        StringBuffer b = new StringBuffer( KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS );
    	for (int i = 0; i < KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS; i++) {
        	b.append( "A" );
        }
    	try {
            KontonummerValidator.validateAccountTypeSyntax(b.toString());
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    KontonummerValidator.ERROR_SYNTAX);
        }
    }
    
    public void testInvalidRegisternummerNotDigits() {
        StringBuffer b = new StringBuffer( KontonummerValidator.REGISTERNUMMER_NUM_DIGITS );
    	for (int i = 0; i < KontonummerValidator.REGISTERNUMMER_NUM_DIGITS; i++) {
        	b.append( "A" );
        }
    	try {
            KontonummerValidator.validateRegisternummerSyntax(b.toString());
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    KontonummerValidator.ERROR_SYNTAX);
        }
    }
    
    
    public void testInvalidRegisternummerWrongLength() {
        StringBuffer b = new StringBuffer( KontonummerValidator.REGISTERNUMMER_NUM_DIGITS + 1 );
    	for (int i = 0; i < KontonummerValidator.REGISTERNUMMER_NUM_DIGITS + 1; i++) {
        	b.append( "0" );
        }
    	try {
            KontonummerValidator.validateRegisternummerSyntax(b.toString());
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    KontonummerValidator.ERROR_SYNTAX);
        }
    }
    
    public void testGetValidKontonummerFromInvalidKontonummerWrongChecksum() {
    	Kontonummer knr = KontonummerValidator.getAndForceValidKontonummer( KONTONUMMER_INVALID_CHECKSUM );
    	assertTrue(KontonummerValidator.isValid( knr.toString() ));
    }

    public void testIsValid() {
        assertTrue(KontonummerValidator.isValid(KONTONUMMER_VALID));
        assertFalse(KontonummerValidator.isValid(KONTONUMMER_INVALID_CHECKSUM));
    }

}
