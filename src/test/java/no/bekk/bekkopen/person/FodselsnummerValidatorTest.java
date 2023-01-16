package no.bekk.bekkopen.person;

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
	void testDNumberIsValidUtvidelse2021() {
		assertTrue(FodselsnummerValidator.isValid("41014061078"));
		assertTrue(FodselsnummerValidator.isValid("41010021861"));
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
