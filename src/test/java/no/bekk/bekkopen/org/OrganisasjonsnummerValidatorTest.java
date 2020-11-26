package no.bekk.bekkopen.org;

import org.junit.jupiter.api.Test;

import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrganisasjonsnummerValidatorTest {

	private static final String ORGNUMMER_VALID = "981566378";
	private static final String ORGNUMMER_INVALID_CHECKSUM = "123456789";

	@Test
	public void testInvalidOrgnummerWrongLength() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> OrganisasjonsnummerValidator.validateSyntax("0123456789"));
        assertThat(thrown.getMessage(), containsString(OrganisasjonsnummerValidator.ERROR_SYNTAX));
	}

	@Test
	public void testInvalidOrgnummerNotDigits() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> OrganisasjonsnummerValidator.validateSyntax("abcdefghijk"));
	    assertThat(thrown.getMessage(), containsString(OrganisasjonsnummerValidator.ERROR_SYNTAX));
	}

	@Test
	public void testInvalidOrgnummerWrongChecksum() {
	    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
	            () -> OrganisasjonsnummerValidator.validateChecksum(ORGNUMMER_INVALID_CHECKSUM));
	    assertThat(thrown.getMessage(), containsString(ERROR_INVALID_CHECKSUM));
	}

	@Test
	public void testGetValidOrgnummerFromInvalidOrgnummerWrongChecksum() {
		Organisasjonsnummer orgNr = OrganisasjonsnummerValidator
				.getAndForceValidOrganisasjonsnummer(ORGNUMMER_INVALID_CHECKSUM);
		assertTrue(OrganisasjonsnummerValidator.isValid(orgNr.toString()));
	}

	@Test
	public void testIsValid() {
		assertTrue(OrganisasjonsnummerValidator.isValid(ORGNUMMER_VALID));
		assertFalse(OrganisasjonsnummerValidator.isValid(ORGNUMMER_INVALID_CHECKSUM));
	}

}
