package boss.nocommons.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class NavnTest {

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
	}

	@Test
	public void skalKorrigereCasing() {
		Navn navn = new Navn("oLA", "HVERMANNSEN", "nordmann");
		assertEquals("Ola", navn.getFornavn());
		assertEquals("Hvermannsen", navn.getMellomnavn());
		assertEquals("Nordmann", navn.getEtternavn());

	}

}
