package boss.nocommons.person;

import static org.junit.Assert.assertTrue;

import java.util.List;

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

		assertTrue(mann.getFornavn() + " matcher ikke " + NAVN_PATTERN, mann.getFornavn().matches(NAVN_PATTERN));
		assertTrue(mann.getEtternavn() + " matcher ikke " + NAVN_PATTERN, mann.getEtternavn().matches(NAVN_PATTERN));
		if (null != mann.getMellomnavn()) {
			assertTrue(mann.getMellomnavn() + " matcher ikke " + NAVN_PATTERN, mann.getEtternavn()
					.matches(NAVN_PATTERN));
		}
	}

	@Test
	public void skalGenerereEtKvinnenavn() {
		Navn kvinne = navnGenerator.genererKvinnenavn();

		assertTrue(kvinne.getFornavn() + " matcher ikke " + NAVN_PATTERN, kvinne.getFornavn().matches(NAVN_PATTERN));
		assertTrue(kvinne.getEtternavn() + " matcher ikke " + NAVN_PATTERN, kvinne.getEtternavn().matches(NAVN_PATTERN));
		if (null != kvinne.getMellomnavn()) {
			assertTrue(kvinne.getMellomnavn() + " matcher ikke " + NAVN_PATTERN,
					kvinne.getEtternavn().matches(NAVN_PATTERN));
		}
	}

	@Test
	public void skalGenerereTiMannsnavn() {
		List<Navn> menn = navnGenerator.genererMannsnavn(10);
		for (Navn mann : menn) {
			assertTrue(mann.getFornavn() + " matcher ikke " + NAVN_PATTERN, mann.getFornavn().matches(NAVN_PATTERN));
			assertTrue(mann.getEtternavn() + " matcher ikke " + NAVN_PATTERN, mann.getEtternavn().matches(NAVN_PATTERN));
			if (null != mann.getMellomnavn()) {
				assertTrue(mann.getMellomnavn() + " matcher ikke " + NAVN_PATTERN,
						mann.getEtternavn().matches(NAVN_PATTERN));
			}
		}
	}

	@Test
	public void skalGenerere10Kvinnenavn() {
		List<Navn> kvinner = navnGenerator.genererKvinnenavn(10);
		for (Navn kvinne : kvinner) {
			assertTrue(kvinne.getFornavn() + " matcher ikke " + NAVN_PATTERN, kvinne.getFornavn().matches(NAVN_PATTERN));
			assertTrue(kvinne.getEtternavn() + " matcher ikke " + NAVN_PATTERN,
					kvinne.getEtternavn().matches(NAVN_PATTERN));
			if (null != kvinne.getMellomnavn()) {
				assertTrue(kvinne.getMellomnavn() + " matcher ikke " + NAVN_PATTERN,
						kvinne.getEtternavn().matches(NAVN_PATTERN));
			}
		}
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
}
