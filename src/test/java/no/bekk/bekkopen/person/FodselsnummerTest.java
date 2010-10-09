package no.bekk.bekkopen.person;

import no.bekk.bekkopen.person.Fodselsnummer;
import junit.framework.TestCase;

/**
 * Test class for Fodselsnummer.
 * 
 * @author per k. mengshoel
 */
public class FodselsnummerTest extends TestCase
{

	private static final String VALID_FODSELSNUMMER = "01010123476";

	private static final String VALID_D_FODSELSNUMMER = "41010123476";

	private Fodselsnummer sut = null;

	protected void setUp() throws Exception
	{
		sut = new Fodselsnummer(VALID_FODSELSNUMMER);
	}

	public void testGetDateAndMonth()
	{
		assertEquals("0101", sut.getDateAndMonth());
	}
	
	public void testGetDayInMonth()
	{
		assertEquals("01", sut.getDayInMonth());
		sut = new Fodselsnummer(VALID_D_FODSELSNUMMER);
		assertEquals("01", sut.getDayInMonth());
	}
	
	public void testGetMonth()
	{
		assertEquals("01", sut.getMonth());
	}
	
	public void testGetDateAndMonthDNumber()
	{
		sut = new Fodselsnummer(VALID_D_FODSELSNUMMER);
		assertEquals("0101", sut.getDateAndMonth());
	}

	public void testGetCentury()
	{
		sut = new Fodselsnummer("01016666609");
		assertEquals("18", sut.getCentury());

		sut = new Fodselsnummer("01016633301");
		assertEquals("19", sut.getCentury());

		sut = new Fodselsnummer("01019196697");
		assertEquals("19", sut.getCentury());

		sut = new Fodselsnummer("01013366671");
		assertEquals("20", sut.getCentury());
		
		//DNumber...
		sut = new Fodselsnummer("41016666609");
		assertEquals("18", sut.getCentury());
		
		sut = new Fodselsnummer("41016633301");
		assertEquals("19", sut.getCentury());
		
		sut = new Fodselsnummer("41019196697");
		assertEquals("19", sut.getCentury());
		
		sut = new Fodselsnummer("41013366671");
		assertEquals("20", sut.getCentury());
	}

	public void testGet2DigitBirthYear()
	{
		assertEquals("01", sut.get2DigitBirthYear());
	}
	
	public void testGetBirthYear()
	{
		assertEquals("1901", sut.getBirthYear());
		sut = new Fodselsnummer(VALID_D_FODSELSNUMMER);
		assertEquals("1901", sut.getBirthYear());
	}

	public void testGetDateOfBirth()
	{
		assertEquals("010101", sut.getDateOfBirth());
	}
	
	public void testGetDateOfBirthDNumber()
	{
		sut = new Fodselsnummer(VALID_D_FODSELSNUMMER);
		assertEquals("010101", sut.getDateOfBirth());
	}

	public void testGetPersonnummer()
	{
		assertEquals("23476", sut.getPersonnummer());
	}

	public void testGetIndividnummer()
	{
		assertEquals("234", sut.getIndividnummer());
	}

	public void testGetGenderDigit()
	{
		assertEquals(4, sut.getGenderDigit());
	}

	public void testGetChecksumDigits()
	{
		assertEquals(7, sut.getChecksumDigit1());
		assertEquals(6, sut.getChecksumDigit2());
	}

	public void testIsMale()
	{
		assertEquals(false, sut.isMale());
	}

	public void testIsMaleDNumber()
	{
		sut = new Fodselsnummer(VALID_D_FODSELSNUMMER);
		assertEquals(false, sut.isMale());
	}

	public void testIsFemale()
	{
		assertEquals(true, sut.isFemale());
	}
	
	public void testIsFemaleDNumber()
	{
		sut = new Fodselsnummer(VALID_D_FODSELSNUMMER);
		assertEquals(true, sut.isFemale());
	}
	
	public void testIsDNumber()
    {
    	assertFalse(Fodselsnummer.isDNumber("01010101006"));
    	assertFalse(Fodselsnummer.isDNumber("80000000000"));
    	assertTrue(Fodselsnummer.isDNumber("47086303651"));
    }
    
    public void testParseDNumber()
    {
    	assertEquals("07086303651", Fodselsnummer.parseDNumber("47086303651"));
    }
}
