package no.bekk.bekkopen.person;

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
