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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PersonTest {

	private Calendar kalender;

	@BeforeEach
	public void initialiserKalender() {
		kalender = Calendar.getInstance();
	}

	@Test
	public void lagMann() throws ParseException {
		kalender.set(1973, Calendar.DECEMBER, 10);
		Date fodselsdato = kalender.getTime();
		List<Fodselsnummer> fodselsnumre = FodselsnummerCalculator.getFodselsnummerForDateAndGender(fodselsdato,
				KJONN.MANN);
		Fodselsnummer fodselsnummer = fodselsnumre.get(0);
		Navn navn = NavnGenerator.genererMannsnavn();
		Person person = new Person(navn, fodselsnummer);

		assertTrue(person.erMann());
		assertFalse(person.erKvinne());

		String pFornavn = person.getFornavn();
		assertTrue(pFornavn.matches(NavnGeneratorTest.NAVN_PATTERN),
      pFornavn + " matcher ikke " + NavnGeneratorTest.NAVN_PATTERN);

		String pEtternavn = person.getEtternavn();
		assertTrue(pEtternavn.matches(NavnGeneratorTest.NAVN_PATTERN),
      pEtternavn + " matcher ikke " + NavnGeneratorTest.NAVN_PATTERN);

		String pMellomnavn = person.getMellomnavn();
		if (null != pMellomnavn) {
			assertTrue(pMellomnavn.matches(NavnGeneratorTest.NAVN_PATTERN),
        pMellomnavn + " matcher ikke " + NavnGeneratorTest.NAVN_PATTERN);
		}

		Navn pNavn = person.getNavn();
		NavnGeneratorTest.assertGyldigNavn(pNavn);

		Date pFodselsdato = person.getFodselsdato();
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("no", "NO"));
		assertEquals(df.format(fodselsdato), df.format(pFodselsdato));

		String pFodselsdatoString = person.getFodselsdatoAsString();
		assertEquals("101273", pFodselsdatoString);

		Fodselsnummer pFodselsnummer = person.getFodselsnummer();
		String pFodselsnummerString = pFodselsnummer.getValue();
		assertTrue(FodselsnummerValidator.isValid(pFodselsnummerString),
      pFodselsnummerString + " er ikke et gyldig fødselsnummer");

		String pPersonnummer = person.getPersonnummer();
		assertEquals(pFodselsnummer.getPersonnummer(), pPersonnummer);

	}
}
