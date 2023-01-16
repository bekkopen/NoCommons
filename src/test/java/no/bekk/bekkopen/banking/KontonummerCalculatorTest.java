package no.bekk.bekkopen.banking;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class KontonummerCalculatorTest {

	private static final int LIST_LENGTH = 100;
	private static final String TEST_ACCOUNT_TYPE = "45";
	private static final String TEST_REGISTERNUMMER = "9710";

	@Test
	public void testGetKontonummerList() {
		List<?> options = KontonummerCalculator.getKontonummerList(LIST_LENGTH);
		assertEquals(LIST_LENGTH, options.size());
      for (Object option : options) {
         Kontonummer kontoNr = (Kontonummer) option;
         assertTrue(KontonummerValidator.isValid(kontoNr.toString()));
      }
	}

	@Test
	public void testGetKontonummerListForAccountType() {
		List<?> options = KontonummerCalculator.getKontonummerListForAccountType(TEST_ACCOUNT_TYPE, LIST_LENGTH);
		assertEquals(LIST_LENGTH, options.size());
      for (Object option : options) {
         Kontonummer kontoNr = (Kontonummer) option;
         assertTrue(KontonummerValidator.isValid(kontoNr.toString()));
         assertTrue(kontoNr.getAccountType().equals(TEST_ACCOUNT_TYPE));
      }
	}

	@Test
	public void testGetKontonummerListForRegisternummer() {
		List<?> options = KontonummerCalculator.getKontonummerListForRegisternummer(TEST_REGISTERNUMMER, LIST_LENGTH);
		assertEquals(LIST_LENGTH, options.size());
      for (Object option : options) {
         Kontonummer kontoNr = (Kontonummer) option;
         assertTrue(KontonummerValidator.isValid(kontoNr.toString()));
         assertTrue(kontoNr.getRegisternummer().equals(TEST_REGISTERNUMMER));
      }
	}

}
