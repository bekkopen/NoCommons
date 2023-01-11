package no.bekk.bekkopen.mail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import no.bekk.bekkopen.mail.model.PostnummerKategori;
import no.bekk.bekkopen.mail.model.Poststed;

public class MailValidatorTest {
    private static final Poststed HAMAR = new Poststed("Hamar");

    @Test
    public void testGetPostnummerForPoststed() {
        List<?> options = MailValidator.getPostnummerForPoststed("Hamar");
        assertEquals(18, options.size());
        options = MailValidator.getPostnummerForPoststed("Oslo");
        assertEquals(637, options.size());
    }

    @Test
    public void testGetPostnummerForPoststedWithDifferentCase() {
        List<?> options = MailValidator.getPostnummerForPoststed("HAMAR");
        assertEquals(18, options.size());
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
    public void testPoststedFor4633() {
        assertEquals("KRISTIANSAND S", MailValidator.getPoststedForPostnummer("4633").toString());
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

    @Test
    public void testFylkeForKristiansand() {
        assertEquals("Agder", MailValidator.getFylkeForPostnummer("4633").getFylkesNavn());
        assertEquals("Agder", MailValidator.getFylkeForKommunenummer("4204").getFylkesNavn());
        assertEquals("Agder", MailValidator.getFylkeForPoststed("Kristiansand S").getFylkesNavn());
    }

    @Test void testFylkeForFylkesnavnOgFylkesnummer() {
        assertEquals("Agder", MailValidator.getFylkeForFylkesnummer("42").getFylkesNavn());
        assertEquals("42", MailValidator.getFylkeForFylkesnavn("Agder").getFylkesNummer());
    }
}
