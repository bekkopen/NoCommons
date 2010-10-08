package boss.nocommons.person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

/**
 * Test for the FodselsnummerCalculator.
 * 
 * @author Per K. Mengshoel
 */
public class FodselsnummerCalculatorTest extends TestCase {

	private DateFormat df = null;
	private Date date = null;

	@Override
	protected void setUp() throws Exception {
		df = new SimpleDateFormat("ddMMyyyy");
		date = df.parse("09062006");
	}

	public void testGetFodselsnummerForDateAndGender() throws ParseException {
		List<?> options = FodselsnummerCalculator.getFodselsnummerForDateAndGender(date, true);
		assertTrue(options.size() > 200);
	}

	public void testGetFodselsnummerForDate() {
		List<?> options = FodselsnummerCalculator.getFodselsnummerForDate(date);
		assertTrue(options.size() > 400);
	}

	public void testInvalidDateTooEarly() throws ParseException {
		date = df.parse("09091854");
		List<?> options = FodselsnummerCalculator.getFodselsnummerForDate(date);
		assertEquals(0, options.size());
	}

	public void testInvalidDateTooLate() throws ParseException {
		date = df.parse("09092040");
		List<?> options = FodselsnummerCalculator.getFodselsnummerForDate(date);
		assertEquals(0, options.size());
	}
}
