package no.bekk.bekkopen.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class MailDataLoaderTest {

    @Test
    public void testExceptionVedFeilInputVedLastingAvFil() {
        assertThrows(IllegalArgumentException.class, () -> {
            MailDataLoader.lesPostnummerFraCsvFil(null);
        });
    }

    @Test
    public void testAntallPoststed() {
        assertEquals(1828, MailValidator.getAntallPoststed());
    }

    @Test
    public void testAntallPostnummer() {
        assertEquals(5133, MailValidator.getAntallPostnummer());
    }

    @Test
    public void testAntallKommunenummer() {
        assertEquals(358, MailValidator.getAntallKommunenummer());
    }

    @Test
    public void testAntallFylker() {
        assertEquals(13, MailValidator.getAntallFylker());
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
}
