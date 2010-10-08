package boss.nocommons.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import boss.nocommons.NoCommonsTestCase;

public class MailDataLoaderTest extends NoCommonsTestCase {

    private InputStream is = null;

    protected void setUp() throws Exception {
        Locale.setDefault(new Locale("no", "NO"));
        File f = new File("src/main/resources/tilbud5.txt");
        is = new FileInputStream(f);
        MailDataLoader.loadFromInputStream(is);
    }

    public void testAntallPoststed() throws IOException {
        assertEquals(1852, MailValidator.getAntallPoststed());
    }

    public void testAntallPostnummer() throws IOException {
        assertEquals(4586, MailValidator.getAntallPostnummer());
    }

    public void testAntallPostnummerForHamar() throws IOException {
        List options = MailValidator.getPostnummerForPoststed("HAMAR");
        assertEquals(17, options.size());
    }

    public void testPoststedForPostnummer2315() throws IOException {
        assertEquals("HAMAR", MailValidator.getPoststedForPostnummer("2315")
                .toString());
    }

    public void testLoadDataFromClasspath() throws IOException {
        boolean success = MailDataLoader.loadFromClassPath();
        assertTrue(success);
    }
}
