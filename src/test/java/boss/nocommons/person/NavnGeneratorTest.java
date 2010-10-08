package boss.nocommons.person;

import static org.junit.Assert.assertTrue;

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
}
