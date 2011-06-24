package no.bekk.bekkopen.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {

	private Calendar kalender;

	@Before
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
		assertTrue(pFornavn + " matcher ikke " + NavnGeneratorTest.NAVN_PATTERN,
				pFornavn.matches(NavnGeneratorTest.NAVN_PATTERN));

		String pEtternavn = person.getEtternavn();
		assertTrue(pEtternavn + " matcher ikke " + NavnGeneratorTest.NAVN_PATTERN,
				pEtternavn.matches(NavnGeneratorTest.NAVN_PATTERN));

		String pMellomnavn = person.getMellomnavn();
		if (null != pMellomnavn) {
			assertTrue(pMellomnavn + " matcher ikke " + NavnGeneratorTest.NAVN_PATTERN,
					pMellomnavn.matches(NavnGeneratorTest.NAVN_PATTERN));
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
		assertTrue(pFodselsnummerString + " er ikke et gyldig fødselsnummer",
				FodselsnummerValidator.isValid(pFodselsnummerString));

		String pPersonnummer = person.getPersonnummer();
		assertEquals(pFodselsnummer.getPersonnummer(), pPersonnummer);

	}
}
