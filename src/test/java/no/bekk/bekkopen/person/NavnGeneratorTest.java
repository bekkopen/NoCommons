package no.bekk.bekkopen.person;

import static org.junit.Assert.assertTrue;

import java.util.List;

import no.bekk.bekkopen.person.Navn;
import no.bekk.bekkopen.person.NavnGenerator;

import org.junit.Before;
import org.junit.Test;

public class NavnGeneratorTest {

	private NavnGenerator navnGenerator;
	private final static String NAVN_PATTERN = "[A-ZÆØÅ][a-zæøå]+[\\-]*[[A-ZÆØÅ][a-zæøå]+]*";

	@Before
	public void initialiserGenerator() {
		navnGenerator = new NavnGenerator();
	}

	@Test
	public void skalGenerereEtMannsnavn() {
		Navn mann = navnGenerator.genererMannsnavn();

		assertGyldigNavn(mann);
	}

	@Test
	public void skalGenerereEtKvinnenavn() {
		Navn kvinne = navnGenerator.genererKvinnenavn();

		assertGyldigNavn(kvinne);
	}

	@Test
	public void skalGenerereTiMannsnavn() {
		List<Navn> menn = navnGenerator.genererMannsnavn(10);
		assertGyldigeNavn(menn);
	}

	@Test
	public void skalGenerere10Kvinnenavn() {
		List<Navn> kvinner = navnGenerator.genererKvinnenavn(10);
		assertGyldigeNavn(kvinner);
	}

	@Test
	public void skalGenerere100NavnMedBlandingAvKjoenn() {
		List<Navn> navneliste = navnGenerator.genererNavn(100);
		assertGyldigeNavn(navneliste);
	}

	@Test
	public void skalGenerereEnAndelKvinnerMedMellomnavn() {
		List<Navn> kvinner = navnGenerator.genererKvinnenavn(100);
		int antallMedMellomnavn = 0;
		for (Navn kvinne : kvinner) {
			if (null != kvinne.getMellomnavn()) {
				antallMedMellomnavn++;
			}
		}
		assertTrue("Forventet mindre enn 50 og fler en 5. Antall med mellomnavn: " + antallMedMellomnavn,
				5 < antallMedMellomnavn && 50 > antallMedMellomnavn);
	}

	@Test
	public void skalGenerereEnAndelMennMedMellomnavn() {
		List<Navn> menn = navnGenerator.genererMannsnavn(100);
		int antallMedMellomnavn = 0;
		for (Navn mann : menn) {
			if (null != mann.getMellomnavn()) {
				antallMedMellomnavn++;
			}
		}
		assertTrue("Forventet mindre enn 50 og fler en 5. Antall med mellomnavn: " + antallMedMellomnavn,
				5 < antallMedMellomnavn && 50 > antallMedMellomnavn);
	}

	private void assertGyldigeNavn(List<Navn> navneliste) {
		for (Navn navn : navneliste) {
			assertGyldigNavn(navn);
		}
	}

	private void assertGyldigNavn(Navn navn) {
		assertTrue(navn.getFornavn() + " matcher ikke " + NAVN_PATTERN, navn.getFornavn().matches(NAVN_PATTERN));
		assertTrue(navn.getEtternavn() + " matcher ikke " + NAVN_PATTERN, navn.getEtternavn().matches(NAVN_PATTERN));
		if (null != navn.getMellomnavn()) {
			assertTrue(navn.getMellomnavn() + " matcher ikke " + NAVN_PATTERN, navn.getEtternavn()
					.matches(NAVN_PATTERN));
		}
	}
}
