package no.bekk.bekkopen.person;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavnGeneratorTest {

	protected final static String NAVN_PATTERN = "[A-ZÆØÅ][a-zæøå]+[\\-]*[[A-ZÆØÅ][a-zæøå]+]*";

	@Test
	public void skalGenerereEtMannsnavn() {
		Navn mann = NavnGenerator.genererMannsnavn();

		assertGyldigNavn(mann);
	}

	@Test
	public void skalGenerereEtKvinnenavn() {
		Navn kvinne = NavnGenerator.genererKvinnenavn();

		assertGyldigNavn(kvinne);
	}

	@Test
	public void skalGenerereTiMannsnavn() {
		List<Navn> menn = NavnGenerator.genererMannsnavn(10);
		assertGyldigeNavn(menn);
	}

	@Test
	public void skalGenerere10Kvinnenavn() {
		List<Navn> kvinner = NavnGenerator.genererKvinnenavn(10);
		assertGyldigeNavn(kvinner);
	}

	@Test
	public void skalGenerere100NavnMedBlandingAvKjoenn() {
		List<Navn> navneliste = NavnGenerator.genererNavn(100);
		assertGyldigeNavn(navneliste);
	}

	@Test
	public void skalGenerereEnAndelKvinnerMedMellomnavn() {
		List<Navn> kvinner = NavnGenerator.genererKvinnenavn(100);
		int antallMedMellomnavn = 0;
		for (Navn kvinne : kvinner) {
			if (null != kvinne.getMellomnavn()) {
				antallMedMellomnavn++;
			}
		}
		assertTrue(5 < antallMedMellomnavn && 50 > antallMedMellomnavn,
      "Forventet mindre enn 50 og fler en 5. Antall med mellomnavn: " + antallMedMellomnavn);
	}

	@Test
	public void skalGenerereEnAndelMennMedMellomnavn() {
		List<Navn> menn = NavnGenerator.genererMannsnavn(100);
		int antallMedMellomnavn = 0;
		for (Navn mann : menn) {
			if (null != mann.getMellomnavn()) {
				antallMedMellomnavn++;
			}
		}
		assertTrue(5 < antallMedMellomnavn && 50 > antallMedMellomnavn,
      "Forventet mindre enn 50 og fler en 5. Antall med mellomnavn: " + antallMedMellomnavn);
	}

	private void assertGyldigeNavn(List<Navn> navneliste) {
		for (Navn navn : navneliste) {
			assertGyldigNavn(navn);
		}
	}

	protected static void assertGyldigNavn(Navn navn) {
		assertTrue(navn.getFornavn().matches(NAVN_PATTERN), navn.getFornavn() + " matcher ikke " + NAVN_PATTERN);
		assertTrue(navn.getEtternavn().matches(NAVN_PATTERN), navn.getEtternavn() + " matcher ikke " + NAVN_PATTERN);
		if (null != navn.getMellomnavn()) {
			assertTrue(navn.getMellomnavn().matches(NAVN_PATTERN),
        navn.getMellomnavn() + " matcher ikke " + NAVN_PATTERN);
		}
	}
}
