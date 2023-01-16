package no.bekk.bekkopen.org;

/*-
 * #%L
 * NoCommons
 * %%
 * Copyright (C) 2014 - 2023 BEKK open source
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

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
