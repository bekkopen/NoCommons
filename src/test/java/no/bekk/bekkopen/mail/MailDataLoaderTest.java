package no.bekk.bekkopen.mail;

import no.bekk.bekkopen.NoCommonsTestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MailDataLoaderTest extends NoCommonsTestCase {

   @Before
	public void setUpLocaleAndLoadData() throws Exception {
		Locale.setDefault(new Locale("no", "NO"));
		File f = new File("src/main/resources/tilbud5.txt");
		MailDataLoader.loadFromInputStream(new FileInputStream(f));
	}

	@Test
	public void testAntallPoststed() {
		assertEquals(1823, MailValidator.getAntallPoststed());
	}

	@Test
	public void testAntallPostnummer() {
		assertEquals(5056, MailValidator.getAntallPostnummer());
	}

	@Test
	public void testAntallPostnummerForHamar() {
		List<?> options = MailValidator.getPostnummerForPoststed("HAMAR");
		assertEquals(18, options.size());
	}

	@Test
	public void testPoststedForPostnummer2315() {
		assertEquals("HAMAR", MailValidator.getPoststedForPostnummer("2315").toString());
	}

	@Test
	public void testLoadDataFromClasspath() {
		boolean success = MailDataLoader.loadFromClassPath();
		assertTrue(success);
	}
}
