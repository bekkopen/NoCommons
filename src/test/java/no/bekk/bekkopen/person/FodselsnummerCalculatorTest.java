package no.bekk.bekkopen.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		assertTrue("Forventet minst 200 fødselsnumre, men fikk " + options.size(), options.size() > 200);
	}

	@Test
	public void testGetFodselsnummerForDate() {
		List<Fodselsnummer> options = FodselsnummerCalculator.getFodselsnummerForDate(date);
		assertTrue("Forventet minst 400 fødselsnumre, men fikk " + options.size(), options.size() > 400);
	}

	@Test
	public void getValidFodselsnummerForDate() throws ParseException {
		List<Fodselsnummer> options = FodselsnummerCalculator.getFodselsnummerForDate(date);
		List<Fodselsnummer> validOptions = FodselsnummerCalculator.getValidFodselsnummere(options);
		assertTrue("Forventet 38 fødselsnumre, men fikk " + validOptions.size(), validOptions.size() == 38);
	}

	@Test
	public void testInvalidDateTooEarly() throws ParseException {
		date = df.parse("09091854");
		List<Fodselsnummer> options = FodselsnummerCalculator.getFodselsnummerForDate(date);
		assertEquals(0, options.size());
	}

	@Test
	public void testInvalidDateTooLate() throws ParseException {
		date = df.parse("09092040");
		List<Fodselsnummer> options = FodselsnummerCalculator.getFodselsnummerForDate(date);
		assertEquals(0, options.size());
	}
}
