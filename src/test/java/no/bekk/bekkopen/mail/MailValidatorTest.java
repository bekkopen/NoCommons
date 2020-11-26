package no.bekk.bekkopen.mail;

import no.bekk.bekkopen.NoCommonsBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MailValidatorTest extends NoCommonsBase {

	private static final Poststed OSLO = new Poststed("Oslo");
	private static final Poststed HAMAR = new Poststed("Hamar");
	private static final Postnummer PN0102 = MailValidator.getPostnummer("0102");
	private static final Postnummer PN0357 = MailValidator.getPostnummer("0357");
	private static final Postnummer PN0457 = MailValidator.getPostnummer("0457");
	private static final Postnummer PN2316 = MailValidator.getPostnummer("2316");
	private static final Postnummer PN2315 = MailValidator.getPostnummer("2315");

	@BeforeEach
	public void setUpMailValidator() {
		Map<Poststed, List<Postnummer>> poststedMap = new HashMap<>();
		List<Postnummer> hamarList = new ArrayList<>();
		hamarList.add(PN2315);
		hamarList.add(PN2316);
		poststedMap.put(HAMAR, hamarList);
		List<Postnummer> osloList = new ArrayList<>();
		osloList.add(PN0457);
		osloList.add(PN0357);
		osloList.add(PN0102);
		poststedMap.put(OSLO, osloList);
		MailValidator.setPoststedMap(poststedMap);

		Map<Postnummer, Poststed> postnummerMap = new HashMap<>();
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
