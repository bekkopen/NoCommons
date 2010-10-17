package no.bekk.bekkopen.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.bekk.bekkopen.NoCommonsTestCase;

import org.junit.Before;
import org.junit.Test;

public class MailValidatorTest extends NoCommonsTestCase {

	private static final Poststed OSLO = new Poststed("Oslo");
	private static final Poststed HAMAR = new Poststed("Hamar");
	private static final Postnummer PN0102 = MailValidator.getPostnummer("0102");
	private static final Postnummer PN0357 = MailValidator.getPostnummer("0357");
	private static final Postnummer PN0457 = MailValidator.getPostnummer("0457");
	private static final Postnummer PN2316 = MailValidator.getPostnummer("2316");
	private static final Postnummer PN2315 = MailValidator.getPostnummer("2315");

	@Before
	public void setUpMailValidator() throws Exception {
		Map<Poststed, List<Postnummer>> poststedMap = new HashMap<Poststed, List<Postnummer>>();
		List<Postnummer> hamarList = new ArrayList<Postnummer>();
		hamarList.add(PN2315);
		hamarList.add(PN2316);
		poststedMap.put(HAMAR, hamarList);
		List<Postnummer> osloList = new ArrayList<Postnummer>();
		osloList.add(PN0457);
		osloList.add(PN0357);
		osloList.add(PN0102);
		poststedMap.put(OSLO, osloList);
		MailValidator.setPoststedMap(poststedMap);

		Map<Postnummer, Poststed> postnummerMap = new HashMap<Postnummer, Poststed>();
		postnummerMap.put(PN2315, HAMAR);
		postnummerMap.put(PN2316, HAMAR);
		postnummerMap.put(PN0357, OSLO);
		postnummerMap.put(PN0457, OSLO);
		postnummerMap.put(PN0102, OSLO);
		MailValidator.setPostnummerMap(postnummerMap);
	}

	@Test
	public void testGetPostnummerForPoststed() {
		List<?> options = MailValidator.getPostnummerForPoststed("Hamar");
		assertEquals(2, options.size());
		options = MailValidator.getPostnummerForPoststed("Oslo");
		assertEquals(3, options.size());
	}

	@Test
	public void testGetPostnummerForPoststedWithDifferentCase() {
		List<?> options = MailValidator.getPostnummerForPoststed("HAMAR");
		assertEquals(2, options.size());
	}

	@Test
	public void testGetPostnummerForPoststedThatDoesNotExist() {
		List<?> options = MailValidator.getPostnummerForPoststed("StedSomIkkeFinnes");
		assertEquals(0, options.size());
	}

	@Test
	public void testGetPoststedForPostnummer() {
		assertEquals(HAMAR, MailValidator.getPoststedForPostnummer("2315"));
	}

	@Test
	public void testValidPostnummer() {
		assertTrue(MailValidator.isValidPostnummer("0102"));
		assertTrue(MailValidator.isValidPostnummer("2315"));
	}

	@Test
	public void testInvalidPostnummerNotDigits() {
		assertFalse(MailValidator.isValidPostnummer("ABCD"));
	}

	@Test
	public void testInvalidPostnummerLength() {
		assertFalse(MailValidator.isValidPostnummer("012"));
	}

}
