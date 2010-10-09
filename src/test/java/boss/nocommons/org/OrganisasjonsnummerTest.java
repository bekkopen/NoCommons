package boss.nocommons.org;

import junit.framework.TestCase;

public class OrganisasjonsnummerTest extends TestCase {

    private static final String ORGANISASJONSNUMMER = "123456789";
    private Organisasjonsnummer o = null;

    protected void setUp() throws Exception {
        o = new Organisasjonsnummer(ORGANISASJONSNUMMER);
    }

    public void testOrganisasjonsnummer() {
        assertNotNull(o);
        assertEquals(ORGANISASJONSNUMMER, o.getValue());
    }

    public void testGetChecksumDigit() {
        assertEquals(9, o.getChecksumDigit());
    }

}
