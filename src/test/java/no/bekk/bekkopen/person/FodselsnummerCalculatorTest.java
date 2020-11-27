package no.bekk.bekkopen.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FodselsnummerCalculatorTest {

	private DateFormat df = null;
	private Date date = null;

	@BeforeEach
	public void setUpDate() throws Exception {
		df = new SimpleDateFormat("ddMMyyyy");
		date = df.parse("09062006");
	}

	@Test
	public void testGetFodselsnummerForDate() {
		List<Fodselsnummer> options = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertTrue(options.size() > 20, "Forventet minst 20 fødselsnumre, men fikk " + options.size());
	}

	@Test
	public void getValidFodselsnummerForDate() {
		List<Fodselsnummer> validOptions = FodselsnummerCalculator.getManyFodselsnummerForDate(date);
		assertTrue(validOptions.size() == 413, "Forventet 413 fødselsnumre, men fikk " + validOptions.size());
	}

	@Test
	public void testThatAllGeneratedNumbersAreValid() {
		for(Fodselsnummer fnr : FodselsnummerCalculator.getManyFodselsnummerForDate(date)) {
			assertTrue(FodselsnummerValidator.isValid(fnr.toString()), "Ugyldig fødselsnummer: " + fnr);
		}
	}

	@Test
	public void testThatAllGeneratedNumbersAreNotDNumbers() {
		for(Fodselsnummer fnr : FodselsnummerCalculator.getManyFodselsnummerForDate(date)) {
      assertFalse(Fodselsnummer.isDNumber(fnr.toString()), "Ugyldig fødselsnummer: " + fnr);
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
			assertTrue(FodselsnummerValidator.isValid(dnr.toString()), "Ugyldig D-nummer: " + dnr);
		}
	}

	@Test
	public void testThatAllGeneratedDNumbersAreDNumbers() {
		for(Fodselsnummer dnr : FodselsnummerCalculator.getManyDNumberFodselsnummerForDate(date)) {
      assertTrue(Fodselsnummer.isDNumber(dnr.toString()), "Ugyldig D-nummer: " + dnr);
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
