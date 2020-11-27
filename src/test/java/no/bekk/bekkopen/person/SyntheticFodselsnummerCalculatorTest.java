package no.bekk.bekkopen.person;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntheticFodselsnummerCalculatorTest {

  private Date date = null;

  @BeforeAll
  public static void setup() {
    FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = true;
  }

  @AfterAll
  public static void taredown() {
    FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = false;
  }

  @BeforeEach
  public void setUpDate() throws Exception {
    DateFormat df = new SimpleDateFormat("ddMMyyyy");
    date = df.parse("09062006");
  }

  @Test
  public void testThatAllGeneratedSyntheticDNumbersAreValid() {
    for (Fodselsnummer fnr : FodselsnummerCalculator.getManySynteticDNumberFodselsnummerForDate(date)) {
      assertTrue(FodselsnummerValidator.isValid(fnr.toString()), "Ugyldig fødselsnummer: " + fnr);
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
