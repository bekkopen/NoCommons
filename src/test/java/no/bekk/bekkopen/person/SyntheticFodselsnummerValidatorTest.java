package no.bekk.bekkopen.person;

import no.bekk.bekkopen.NoCommonsBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntheticFodselsnummerValidatorTest extends NoCommonsBase {

  @BeforeAll
  public static void setup() {
    FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = true;
  }

  @AfterAll
  public static void taredown() {
    FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = false;
  }

  @Test
  public void testSynteticDNumberIsValid() {
    assertTrue(FodselsnummerValidator.isValid("49860699787"));
  }

}
