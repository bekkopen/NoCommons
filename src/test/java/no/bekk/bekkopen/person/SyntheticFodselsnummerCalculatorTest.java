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
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntheticFodselsnummerCalculatorTest {

	private LocalDate date = LocalDate.of(2006, Month.JUNE, 9);

	@BeforeAll
	public static void setup() {
		FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = true;
	}

	@AfterAll
	public static void teardown() {
		FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = false;
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
