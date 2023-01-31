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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntheticFodselsnummerCalculatorTest {

	private Date date = null;

	@BeforeAll
	public static void setup() {
		FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = true;
	}

	@AfterAll
	public static void teardown() {
		FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = false;
	}

	@BeforeEach
	public void setUpDate() throws Exception {
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		date = df.parse("09062006");
	}

	@Test
	public void testThatAllGeneratedSyntheticDNumbersAreValid() {
		for (Fodselsnummer fnr : FodselsnummerCalculator.getManySyntheticDNumberFodselsnummerForDate(date)) {
			assertTrue(FodselsnummerValidator.isValid(fnr.toString()), "Ugyldig fødselsnummer: " + fnr);
		}
	}

	@Test
	public void testThatAtLeastOneSyntheticNumberIsGenerated() {
		assertTrue(FodselsnummerCalculator.getManySyntheticFodselsnummerForDate(date).size() >= 1);
	}

	@Test
	public void testThatAtLeastOneSyntheticDNumberIsGenerated() {
		assertTrue(FodselsnummerCalculator.getManySyntheticDNumberFodselsnummerForDate(date).size() >= 1);
	}
}
