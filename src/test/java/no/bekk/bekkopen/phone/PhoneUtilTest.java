package no.bekk.bekkopen.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import no.bekk.bekkopen.phone.model.Bruksområde;
import no.bekk.bekkopen.phone.model.Nummerserie;
import no.bekk.bekkopen.phone.model.Status;

public class PhoneUtilTest {

    @Test
    public void testAlleNummerserierLastetInn() {
        assertEquals(2072, PhoneUtil.getAntallNummerserier());
    }

    @Test
    public void testAntallNummerserierMedManglendeTilbyderSkalVæreLikStatusLedig() {
        assertEquals(702, PhoneUtil.hentNummerserieFraTilbyder((String) null).size());
    }

    @Test
    public void testAntallNummerserierTelia() {
        assertEquals(360, PhoneUtil.hentNummerserieFraTilbyder("Telia Norge AS").size());
    }

    @Test
    public void testNummerområde() {
        assertEquals(21000000L,
            PhoneUtil.hentNummerserieFraTilbyder("Telia Norge AS").get(0).getNummerområde().getFra());
        assertEquals(21099999L,
            PhoneUtil.hentNummerserieFraTilbyder("Telia Norge AS").get(0).getNummerområde().getTil());
        assertEquals(false, PhoneUtil.hentNummerserieFraTilbyder("Telia Norge AS").get(0).getNummerområde()
            .erInnenforNummerområde(12345678));
        assertEquals(true, PhoneUtil.hentNummerserieFraTilbyder("Telia Norge AS").get(0).getNummerområde()
            .erInnenforNummerområde(21000000));
        assertEquals(true, PhoneUtil.hentNummerserieFraTilbyder("Telia Norge AS").get(0).getNummerområde()
            .erInnenforNummerområde(21000001));
        assertEquals(true, PhoneUtil.hentNummerserieFraTilbyder("Telia Norge AS").get(0).getNummerområde()
            .erInnenforNummerområde(21099999));
    }

    private static List<Nummerserie> provideAlleNummerserier() {
        return PhoneUtil.hentAlleNummerserier();
    }

    @DisplayName("Telefonnummer skal være gyldig")
    @ParameterizedTest
    @ValueSource(strings = { "99999999", "95087795", "40095096", "41414141" })
    void testAtGyldigeTelefonnummerValiderer(String telefonnummer) {
        assertTrue(PhoneUtil.erGyldigNummer(telefonnummer));
    }

    @DisplayName("Telefonnummer skal være ugyldig")
    @ParameterizedTest
    @ValueSource(strings = { "Hello", "0123", "0123456789", "123456789", "1111111111111111" })
    void testAtUgyldigeTelefonnummerFeilerValidering(String telefonnummer) {
        assertFalse(PhoneUtil.erGyldigNummer(telefonnummer));
    }

    // Telefonnummer skal ha mellom 3 og 12 siffer, og starte med 1-9
    // med mindre det er 5 sifret nummer (som kan starte med 0)
    @DisplayName("Alle nummerserier skal inneholde gyldige nummer i nummerområde")
    @ParameterizedTest
    @MethodSource("provideAlleNummerserier")
    void testAlleNummerserierHarGyldigeNummer(Nummerserie nummerserie) {
        assertTrue(PhoneUtil.erGyldigNummer(nummerserie.getNummerområde().getFraString()));
        assertTrue(PhoneUtil.erGyldigNummer(nummerserie.getNummerområde().getTilString()));
    }

    @Test
    void testNummerErTildelt() {
        assertTrue(PhoneUtil.nummerErTildelt("40095096"));
        assertFalse(PhoneUtil.nummerErTildelt("21100000"));
    }

    @Test
    void testHentNummerserieFraTelefonnummer() {
        Nummerserie nummerserie = PhoneUtil.hentNummerserieFraTelefonnummer("40095096");
        assertEquals(Status.TILDELT, nummerserie.getStatus());
        assertEquals(Bruksområde.LANDMOBILE_TJENESTER, nummerserie.getBruksområde());
        assertEquals("40040000", nummerserie.getNummerområde().getFraString());
        assertEquals("40099999", nummerserie.getNummerområde().getTilString());
    }
}
