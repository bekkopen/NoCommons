package no.bekk.bekkopen.person;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class SyntheticFodselsnummerCalculatorTest {

  private Date date = null;

  @BeforeClass
  public static void setup() {
    FodselsnummerValidator.FAIL_ON_SYNTHETIC_NUMBER = false;
  }

  @AfterClass
  public static void taredown() {
    FodselsnummerValidator.FAIL_ON_SYNTHETIC_NUMBER = true;
  }

  @Before
  public void setUpDate() throws Exception {
    DateFormat df = new SimpleDateFormat("ddMMyyyy");
    date = df.parse("09062006");
  }

  @Test
  public void testThatAllGeneratedSyntheticDNumbersAreValid() {
    for (Fodselsnummer fnr : FodselsnummerCalculator.getManySynteticDNumberFodselsnummerForDate(date)) {
      assertTrue("Ugyldig fødselsnummer: " + fnr, FodselsnummerValidator.isValid(fnr.toString()));
    }
  }

  @Test
  public void testThatAtLeastOneSynteticNumberIsGenerated() {
    assertTrue(FodselsnummerCalculator.getManySynteticFodselsnummerForDate(date).size() >= 1);
  }

  @Test
  public void testThatAtLeastOneSynteticDNumberIsGenerated() {
    assertTrue(FodselsnummerCalculator.getManySynteticDNumberFodselsnummerForDate(date).size() >= 1);
  }
}
