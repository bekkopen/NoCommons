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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;


public class NavnTest {

	@Test
	public void skalKasteIllegalArgumentExceptionDersomFornavnEllerEtternavnErNull() {
		try {
			new Navn(null, null);
			fail("Skulle kastet IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("fornavn or etternavn can not be null: fornavn=null,etternavn=null", e.getMessage());
		}
		try {
			new Navn("Ola", null);
			fail("Skulle kastet IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("fornavn or etternavn can not be null: fornavn=Ola,etternavn=null", e.getMessage());
		}
		try {
			new Navn(null, "Nordmann");
			fail("Skulle kastet IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertEquals("fornavn or etternavn can not be null: fornavn=null,etternavn=Nordmann", e.getMessage());
		}
	}

	@Test
	public void skalSetteFornavnMellomnavnOgEtternavn() {
		Navn navn = new Navn("Ola", "Hvermannsen", "Nordmann");
		assertEquals("Ola", navn.getFornavn());
		assertEquals("Hvermannsen", navn.getMellomnavn());
		assertEquals("Nordmann", navn.getEtternavn());
	}

	@Test
	public void skalSetteFornavnOgEtternavn() {
		Navn navn = new Navn("Ola", "Nordmann");
		assertEquals("Ola", navn.getFornavn());
		assertNull(navn.getMellomnavn());
		assertEquals("Nordmann", navn.getEtternavn());

		navn = new Navn("Ola", null, "Nordmann");
		assertEquals("Ola", navn.getFornavn());
		assertNull(navn.getMellomnavn());
		assertEquals("Nordmann", navn.getEtternavn());
	}

	@Test
	public void skalKorrigereCasing() {
		Navn navn = new Navn("oLA", "HVERMANNSEN", "nordmann");
		assertEquals("Ola", navn.getFornavn());
		assertEquals("Hvermannsen", navn.getMellomnavn());
		assertEquals("Nordmann", navn.getEtternavn());
	}

	@Test
	public void skalSkriveFulltNavn() {
		Navn navn = new Navn("oLA", "HVERMANNSEN", "nordmann");
		assertEquals("Ola Hvermannsen Nordmann", navn.getNavn());
		assertEquals("Ola Hvermannsen Nordmann", navn.toString());

		navn = new Navn("oLA", "nordmann");
		assertEquals("Ola Nordmann", navn.getNavn());
		assertEquals("Ola Nordmann", navn.toString());
	}

}
