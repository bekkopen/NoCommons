package boss.nocommons.org;

import boss.nocommons.NoCommonsTestCase;

public class OrganisasjonsnummerValidatorTest extends NoCommonsTestCase {

    private static final String ORGNUMMER_VALID = "981566378";
    private static final String ORGNUMMER_INVALID_CHECKSUM = "123456789";

    public void testInvalidOrgnummerWrongLength() {
        try {
            OrganisasjonsnummerValidator.validateSyntax("0123456789");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, OrganisasjonsnummerValidator.ERROR_SYNTAX);
        }
    }

    public void testInvalidOrgnummerNotDigits() {
        try {
            OrganisasjonsnummerValidator.validateSyntax("abcdefghijk");
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e, OrganisasjonsnummerValidator.ERROR_SYNTAX);
        }
    }

    public void testInvalidOrgnummerWrongChecksum() {
        try {
            OrganisasjonsnummerValidator
                    .validateChecksum(ORGNUMMER_INVALID_CHECKSUM);
            fail();
        } catch (IllegalArgumentException e) {
            assertMessageContains(e,
                    OrganisasjonsnummerValidator.ERROR_INVALID_CHECKSUM);
        }
    }

    public void testGetValidOrgnummerFromInvalidOrgnummerWrongChecksum() {
    	Organisasjonsnummer orgNr = OrganisasjonsnummerValidator.getAndForceValidOrganisasjonsnummer( ORGNUMMER_INVALID_CHECKSUM );
    	assertTrue(OrganisasjonsnummerValidator.isValid( orgNr.toString() ));
    }
    
    public void testIsValid() {
        assertTrue(OrganisasjonsnummerValidator.isValid(ORGNUMMER_VALID));
        assertFalse(OrganisasjonsnummerValidator
                .isValid(ORGNUMMER_INVALID_CHECKSUM));
    }

}
