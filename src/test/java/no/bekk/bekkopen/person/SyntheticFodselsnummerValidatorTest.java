package no.bekk.bekkopen.person;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyntheticFodselsnummerValidatorTest {

  @BeforeAll
  public static void setup() {
    FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = true;
  }

  @AfterAll
  public static void teardown() {
    FodselsnummerValidator.ALLOW_SYNTHETIC_NUMBERS = false;
  }

  @Test
  public void testSynteticDNumberIsValid() {
    assertTrue(FodselsnummerValidator.isValid("49860699787"));
  }

  @Test
  void testSynteticDNumberIsValidUtvidelse2021() {
		assertTrue(FodselsnummerValidator.isValid("41814061033"));
		assertTrue(FodselsnummerValidator.isValid("41814065640"));
		assertTrue(FodselsnummerValidator.isValid("41814075603"));

		assertTrue(FodselsnummerValidator.isValid("41810021827"));
		assertTrue(FodselsnummerValidator.isValid("41810025091"));
		assertTrue(FodselsnummerValidator.isValid("41810034422"));
  }
}
