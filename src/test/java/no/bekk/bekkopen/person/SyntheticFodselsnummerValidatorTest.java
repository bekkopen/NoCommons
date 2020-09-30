package no.bekk.bekkopen.person;

import no.bekk.bekkopen.NoCommonsTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SyntheticFodselsnummerValidatorTest extends NoCommonsTestCase {

  @BeforeClass
  public static void setup() {
    FodselsnummerValidator.FAIL_ON_SYNTHETIC_NUMBER = false;
  }

  @AfterClass
  public static void taredown() {
    FodselsnummerValidator.FAIL_ON_SYNTHETIC_NUMBER = true;
  }

  @Test
  public void testSynteticDNumberIsValid() {
    assertTrue(FodselsnummerValidator.isValid("49860699787"));
  }

}
