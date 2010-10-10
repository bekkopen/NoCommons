package no.bekk.bekkopen.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import no.bekk.bekkopen.NoCommonsTestCase;

import org.junit.Before;
import org.junit.Test;

public class MailDataLoaderTest extends NoCommonsTestCase {

	private InputStream is = null;

	@Before
	public void setUpLocaleAndLoadData() throws Exception {
		Locale.setDefault(new Locale("no", "NO"));
		File f = new File("src/main/resources/tilbud5.txt");
		is = new FileInputStream(f);
		MailDataLoader.loadFromInputStream(is);
	}

	@Test
	public void testAntallPoststed() throws IOException {
		assertEquals(1852, MailValidator.getAntallPoststed());
	}

	@Test
	public void testAntallPostnummer() throws IOException {
		assertEquals(4586, MailValidator.getAntallPostnummer());
	}

	@Test
	public void testAntallPostnummerForHamar() throws IOException {
		List<?> options = MailValidator.getPostnummerForPoststed("HAMAR");
		assertEquals(17, options.size());
	}

	@Test
	public void testPoststedForPostnummer2315() throws IOException {
		assertEquals("HAMAR", MailValidator.getPoststedForPostnummer("2315").toString());
	}

	@Test
	public void testLoadDataFromClasspath() throws IOException {
		boolean success = MailDataLoader.loadFromClassPath();
		assertTrue(success);
	}
}
