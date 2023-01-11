package no.bekk.bekkopen.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.bekk.bekkopen.mail.model.Kommunenavn;
import no.bekk.bekkopen.mail.model.Kommunenummer;
import no.bekk.bekkopen.mail.model.PostInfo;
import no.bekk.bekkopen.mail.model.Postnummer;
import no.bekk.bekkopen.mail.model.PostnummerKategori;
import no.bekk.bekkopen.mail.model.Poststed;

public class MailValidatorTest {
    private static final Poststed HAMAR = new Poststed("Hamar");

    private static final List<PostInfo> postInfo = List.of(
        new PostInfo(
            new Postnummer("4633"),
            new Poststed("KRISTIANSAND S"),
            new Kommunenummer("4204"),
            new Kommunenavn("KRISTIANSAND"),
            PostnummerKategori.G
        ),
        new PostInfo(
            new Postnummer("2315"),
            new Poststed("HAMAR"),
            new Kommunenummer("3403"),
            new Kommunenavn("HAMAR"),
            PostnummerKategori.G
        ),
        new PostInfo(
            new Postnummer("2316"),
            new Poststed("HAMAR"),
            new Kommunenummer("3403"),
            new Kommunenavn("HAMAR"),
            PostnummerKategori.G
        ),
        new PostInfo(
            new Postnummer("0457"),
            new Poststed("OSLO"),
            new Kommunenummer("0301"),
            new Kommunenavn("OSLO"),
            PostnummerKategori.G
        ),
        new PostInfo(
            new Postnummer("0357"),
            new Poststed("OSLO"),
            new Kommunenummer("0301"),
            new Kommunenavn("OSLO"),
            PostnummerKategori.G
        ),
        new PostInfo(
            new Postnummer("0102"),
            new Poststed("OSLO"),
            new Kommunenummer("0301"),
            new Kommunenavn("OSLO"),
            PostnummerKategori.P
        )
    );

    @BeforeEach
    public void setUpMailValidator() {
        Map<Postnummer, PostInfo> postInfo =
            MailValidatorTest.postInfo.stream().collect(Collectors.toMap(e -> e.getPostnummer(), e -> e));

        MailValidator.setPostInfo(postInfo);
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

    @Test
    public void testValidKommunenummer() {
        assertTrue(MailValidator.isValidKommunenummer("4204"));
        assertTrue(MailValidator.isValidKommunenummer("2315"));
    }

    @Test
    public void testInvalidKommunenummerNotDigits() {
        assertFalse(MailValidator.isValidPostnummer("ABCD"));
    }

    @Test
    public void testInvalidKommunenummerLength() {
        assertFalse(MailValidator.isValidPostnummer("012"));
    }

    @Test
    public void testKategoriFor4633() {
        assertEquals(PostnummerKategori.G, MailValidator.getPostnummerKategoriForPostnummer("4633"));
        assertEquals("G", MailValidator.getPostnummerKategoriForPostnummer("4633").toString());
    }

    @Test
    public void testKommuneFor4633() {
        assertEquals("4204", MailValidator.getKommunenummerForPostnummer("4633").toString());
        assertEquals("KRISTIANSAND", MailValidator.getKommunenavnForPostnummer("4633").toString());
    }

    @Test
    public void testKommunenavnFor4204() {
        assertEquals("KRISTIANSAND", MailValidator.getKommunenavnForKommunenummer("4204").toString());
    }

    @Test
    public void testKommunenummerForKristiansand() {
        assertEquals("4204", MailValidator.getKommunenummerForKommunenavn("Kristiansand").toString());
        assertEquals("4204", MailValidator.getKommunenummerForKommunenavn("KRISTIANSAND").toString());
    }
}
