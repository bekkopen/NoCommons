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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KontonummerTest {

	private static final String KONTONUMMER = "99990000001";
	private static final String KONTONUMMER_WITH_DOTS = "9999.00.00001";
	private Kontonummer k = null;

	@BeforeEach
	public void setUpKontonummer() {
		k = new Kontonummer(KONTONUMMER);
	}

	@Test
	public void testKontonummer() {
		Assertions.assertNotNull(k);
		assertEquals(KONTONUMMER, k.getValue());
	}

	@Test
	public void testGetRegisternummer() {
		assertEquals("9999", k.getRegisternummer());
	}

	@Test
	public void testGetChecksumDigit() {
		assertEquals(1, k.getChecksumDigit());
	}

	@Test
	public void testGetSecondGroupOfDigits() {
		assertEquals("00", k.getAccountType());
	}

	@Test
	public void testGetKonto() {
		assertEquals("000000", k.getKonto());
	}

	@Test
	public void testGetGroupedValue() {
		Assertions.assertNotNull(k);
		assertEquals(KONTONUMMER_WITH_DOTS, k.getGroupedValue());
	}

}
