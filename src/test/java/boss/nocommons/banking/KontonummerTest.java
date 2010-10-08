package boss.nocommons.banking;

import junit.framework.TestCase;

public class KontonummerTest extends TestCase {

    private static final String KONTONUMMER = "99990000001";
    private static final String KONTONUMMER_WITH_DOTS = "9999.00.00001";
    private Kontonummer k = null;

    protected void setUp() throws Exception {
        k = new Kontonummer(KONTONUMMER);
    }

    public void testKontonummer() {
        assertNotNull(k);
        assertEquals(KONTONUMMER, k.getValue());
    }

    public void testGetRegisternummer() {
        assertEquals("9999", k.getRegisternummer());
    }

    public void testGetChecksumDigit() {
        assertEquals(1, k.getChecksumDigit());
    }

    public void testGetSecondGroupOfDigits() {
        assertEquals("00", k.getAccountType());
    }

    public void testGetKonto() {
        assertEquals("000000", k.getKonto());
    }

    public void testGetGroupedValue() {
        assertNotNull(k);
        assertEquals(KONTONUMMER_WITH_DOTS, k.getGroupedValue());
    }

}
