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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FodselsnummerCalculatorTest {

	private DateFormat df = null;
	private Date date = null;

	@BeforeEach
	public void setUpDate() throws Exception {
		df   = new SimpleDateFormat("ddMMyyyy");
		date = df.parse("09062006");
	}

	@Test
	public void testGetFodselsnummerForDate() {
		List<Fodselsnummer> options = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertTrue(options.size() > 20, "Forventet minst 20 fødselsnumre, men fikk " + options.size());
	}

	@Test
	public void getValidFodselsnummerForDate() {
		List<Fodselsnummer> validOptions = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertTrue(validOptions.size() == 413, "Forventet 413 fødselsnumre, men fikk " + validOptions.size());
	}

	@Test
	public void testThatAllGeneratedNumbersAreValid() {
		for (Fodselsnummer fnr : FodselsnummerCalculator.getManyFodselsnummerForDate(date)) {
			assertTrue(FodselsnummerValidator.isValid(fnr.toString()), "Ugyldig fødselsnummer: " + fnr);
		}
	}

	@Test
	public void testThatAllGeneratedNumbersAreNotDNumbers() {
		for (Fodselsnummer fnr : FodselsnummerCalculator.getManyFodselsnummerForDate(date)) {
			assertFalse(Fodselsnummer.isDNumber(fnr.toString()), "Ugyldig fødselsnummer: " + fnr);
		}
	}

	@Test
	public void testThatAtLeastOneFodselsnummerIsGenerated() {
		assertTrue(FodselsnummerCalculator.getManyFodselsnummerForDate(date).size() >= 1);
	}

	@Test
	public void testThatAtLeastOneDNumberIsGenerated() {
		assertTrue(FodselsnummerCalculator.getManyDNumberFodselsnummerForDate(date).size() >= 1);
	}

	@Test
	public void testThatAtLeastOneDNumberIsGeneratedsas() throws ParseException {
		assertEquals(82, FodselsnummerCalculator.getManyDNumberFodselsnummerForDate(df.parse("06061878")).size());
		assertEquals(164, FodselsnummerCalculator.getManyDNumberFodselsnummerForDate(df.parse("12101921")).size());
	}

	@Test
	public void testThatAllGeneratedDNumbersAreValid() {
		for (Fodselsnummer dnr : FodselsnummerCalculator.getManyDNumberFodselsnummerForDate(date)) {
			assertTrue(FodselsnummerValidator.isValid(dnr.toString()), "Ugyldig D-nummer: " + dnr);
		}
	}

	@Test
	public void testThatAllGeneratedDNumbersAreDNumbers() {
		for (Fodselsnummer dnr : FodselsnummerCalculator.getManyDNumberFodselsnummerForDate(date)) {
			assertTrue(Fodselsnummer.isDNumber(dnr.toString()), "Ugyldig D-nummer: " + dnr);
		}
	}

	@Test
	public void testInvalidDateTooEarly() throws ParseException {
		date = df.parse("09091853");
		List<Fodselsnummer> options = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertEquals(0, options.size());
	}

	@Test
	public void testInvalidDateTooLate() throws ParseException {
		date = df.parse("09092040");
		List<Fodselsnummer> options = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertEquals(0, options.size());
	}

	@Test
	public void testOneFodselsnummer() throws ParseException {
		date = df.parse("01121980");
		Fodselsnummer fodselsnummer = FodselsnummerCalculator.getFodselsnummerForDate(date);
		assertTrue(FodselsnummerValidator.isValid(fodselsnummer.toString()));
	}
}
