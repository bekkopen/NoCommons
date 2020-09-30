package no.bekk.bekkopen.person;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FodselsnummerTest {

    private static final String VALID_FODSELSNUMMER = "01010123476";

    private static final String D_FODSELSNUMMER = "41010123476";//Invalid but valid is tested in FodselsnummerValidatorTest

    private Fodselsnummer sut = null;

    @Before
    public void setUpValidFodselsnummer() {
        sut = new Fodselsnummer(VALID_FODSELSNUMMER);
    }

    @Test
    public void testGetDateAndMonth() {
        assertEquals("0101", sut.getDateAndMonth());
    }

    @Test
    public void testGetDayInMonth() {
        assertEquals("01", sut.getDayInMonth());
        sut = new Fodselsnummer(D_FODSELSNUMMER);
        assertEquals("01", sut.getDayInMonth());
    }

    @Test
    public void testGetMonth() {
        assertEquals("01", sut.getMonth());
    }

    @Test
    public void testGetDateAndMonthDNumber() {
        sut = new Fodselsnummer(D_FODSELSNUMMER);
        assertEquals("0101", sut.getDateAndMonth());
    }

    @Test
    public void testGetCentury() {
        sut = new Fodselsnummer("01016666609");
        assertEquals("18", sut.getCentury());

        sut = new Fodselsnummer("01015466609");
        assertEquals("18", sut.getCentury());

        sut = new Fodselsnummer("01016633301");
        assertEquals("19", sut.getCentury());

        sut = new Fodselsnummer("01019196697");
        assertEquals("19", sut.getCentury());

        sut = new Fodselsnummer("01013366671");
        assertEquals("20", sut.getCentury());

        // DNumber...
        sut = new Fodselsnummer("41016666609");
        assertEquals("18", sut.getCentury());

        sut = new Fodselsnummer("41016633301");
        assertEquals("19", sut.getCentury());

        sut = new Fodselsnummer("41019196697");
        assertEquals("19", sut.getCentury());

        sut = new Fodselsnummer("41013366671");
        assertEquals("20", sut.getCentury());
    }

    @Test
    public void testGet2DigitBirthYear() {
        assertEquals("01", sut.get2DigitBirthYear());
    }

    @Test
    public void testGetBirthYear() {
        assertEquals("1901", sut.getBirthYear());
        sut = new Fodselsnummer(D_FODSELSNUMMER);
        assertEquals("1901", sut.getBirthYear());
    }

    @Test
    public void testGetDateOfBirth() {
        assertEquals("010101", sut.getDateOfBirth());
    }

    @Test
    public void testGetDateOfBirthDNumber() {
        sut = new Fodselsnummer(D_FODSELSNUMMER);
        assertEquals("010101", sut.getDateOfBirth());
    }

    @Test
    public void testGetPersonnummer() {
        assertEquals("23476", sut.getPersonnummer());
    }

    @Test
    public void testGetIndividnummer() {
        assertEquals("234", sut.getIndividnummer());
    }

    @Test
    public void testGetGenderDigit() {
        assertEquals(4, sut.getGenderDigit());
    }

    @Test
    public void testGetChecksumDigits() {
        assertEquals(7, sut.getChecksumDigit1());
        assertEquals(6, sut.getChecksumDigit2());
    }

    @Test
    public void testIsDNumber() {
        assertFalse(Fodselsnummer.isDNumber("01010101006"));
        assertFalse(Fodselsnummer.isDNumber("80000000000"));
        assertTrue(Fodselsnummer.isDNumber("47086303651"));
    }

    @Test
    public void testParseDNumber() {
        assertEquals("07086303651", Fodselsnummer.parseDNumber("47086303651"));
    }

    @Test
    public void testParseSynteticNumber() {
        assertEquals("07086303651", Fodselsnummer.parseSynthenticNumber("07886303651"));
    }

    @Test
    public void testIsSynteticNumber() {
        assertFalse(Fodselsnummer.isSynthetic("01010101006"));
        assertFalse(Fodselsnummer.isSynthetic("80000000000"));
        assertTrue(Fodselsnummer.isSynthetic("07886303651"));
    }
}
