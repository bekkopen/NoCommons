package no.bekk.bekkopen.person;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class FodselsnummerCalculatorTest {

	private DateFormat df = null;
	private Date date = null;

	@Before
	public void setUpDate() throws Exception {
		df = new SimpleDateFormat("ddMMyyyy");
		date = df.parse("09062006");
	}

	@Test
	public void testGetFodselsnummerForDateAndGender() throws ParseException {
		List<Fodselsnummer> options = FodselsnummerCalculator.getFodselsnummerForDateAndGender(date, KJONN.KVINNE);
		assertTrue("Forventet minst 10 fødselsnumre, men fikk " + options.size(), options.size() > 10);
	}

	@Test
	public void testGetFodselsnummerForDate() {
		List<Fodselsnummer> options = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertTrue("Forventet minst 20 fødselsnumre, men fikk " + options.size(), options.size() > 20);
	}

	@Test
	public void getValidFodselsnummerForDate() {
		List<Fodselsnummer> validOptions = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertTrue("Forventet 413 fødselsnumre, men fikk " + validOptions.size(), validOptions.size() == 413);
	}

	@Test
	public void testThatAllGeneratedNumbersAreValid() {
		for(Fodselsnummer fnr : FodselsnummerCalculator.getManyFodselsnummerForDate(date)) {
			assertTrue("Ugyldig fødselsnummer: " + fnr, FodselsnummerValidator.isValid(fnr.toString()));
		}
	}

	@Test
	public void testThatAllGeneratedNumbersAreNotDNumbers() {
		for(Fodselsnummer fnr : FodselsnummerCalculator.getManyFodselsnummerForDate(date)) {
			assertTrue("Ugyldig fødselsnummer: " + fnr, Fodselsnummer.isDNumber(fnr.toString()) != true);
		}
	}

	@Test
	public void testThatAtLeastOneFodselsnummerIsGenerated() {
		assertTrue(FodselsnummerCalculator.getManyFodselsnummerForDate(date).size() >= 1);
	}

	@Test
	public void testThatAtLeastOneDNumberIsGenerated() {
		assertTrue(FodselsnummerCalculator.getManyDNumberFodselsnummerForDate(date).size() >= 1);
	}

	@Test
	public void testThatAllGeneratedDNumbersAreValid() {
		for(Fodselsnummer dnr : FodselsnummerCalculator.getManyDNumberFodselsnummerForDate(date)) {
			assertTrue("Ugyldig D-nummer: " + dnr, FodselsnummerValidator.isValid(dnr.toString()));
		}
	}

	@Test
	public void testThatAllGeneratedDNumbersAreDNumbers() {
		for(Fodselsnummer dnr : FodselsnummerCalculator.getManyDNumberFodselsnummerForDate(date)) {
			assertTrue("Ugyldig D-nummer: " + dnr, Fodselsnummer.isDNumber(dnr.toString()) == true);
		}
	}

	@Test
	public void testInvalidDateTooEarly() throws ParseException {
		date = df.parse("09091853");
		List<Fodselsnummer> options = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertEquals(0, options.size());
	}

	@Test
	public void testInvalidDateTooLate() throws ParseException {
		date = df.parse("09092040");
		List<Fodselsnummer> options = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertEquals(0, options.size());
	}

	@Test
	public void testOneFodselsnummer() throws ParseException{
		date = df.parse("01121980");
		Fodselsnummer fodselsnummer = FodselsnummerCalculator.getFodselsnummerForDate(date);
		assertTrue(FodselsnummerValidator.isValid(fodselsnummer.toString()));
	}
}
