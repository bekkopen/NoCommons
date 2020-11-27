package no.bekk.bekkopen.person;

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
