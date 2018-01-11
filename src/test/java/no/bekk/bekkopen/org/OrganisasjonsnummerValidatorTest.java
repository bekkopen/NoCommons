package no.bekk.bekkopen.org;

import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import no.bekk.bekkopen.NoCommonsTestCase;

import org.junit.Test;

public class OrganisasjonsnummerValidatorTest extends NoCommonsTestCase {

	private static final String ORGNUMMER_VALID = "981566378";
	private static final String ORGNUMMER_INVALID_CHECKSUM = "123456789";

	@Test
	public void testInvalidOrgnummerWrongLength() {
		try {
			OrganisasjonsnummerValidator.validateSyntax("0123456789");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, OrganisasjonsnummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidOrgnummerNotDigits() {
		try {
			OrganisasjonsnummerValidator.validateSyntax("abcdefghijk");
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, OrganisasjonsnummerValidator.ERROR_SYNTAX);
		}
	}

	@Test
	public void testInvalidOrgnummerWrongChecksum() {
		try {
			OrganisasjonsnummerValidator.validateChecksum(ORGNUMMER_INVALID_CHECKSUM);
			fail();
		} catch (IllegalArgumentException e) {
			assertMessageContains(e, ERROR_INVALID_CHECKSUM);
		}
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
