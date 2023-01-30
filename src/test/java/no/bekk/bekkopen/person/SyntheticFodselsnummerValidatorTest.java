package no.bekk.bekkopen.person;

/*-
 * #%L
 * NoCommons
 * %%
 * Copyright (C) 2014 - 2023 BEKK open source
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
  public void testSyntheticDNumberIsValid() {
    assertTrue(FodselsnummerValidator.isValid("49860699787"));
    assertTrue(FodselsnummerValidator.isValid("49910690514"));
    assertTrue(FodselsnummerValidator.isValid("49710697182"));
    assertTrue(FodselsnummerValidator.isValid("49680699185"));
  }

  @Test
  void testSyntheticDNumberIsValidUtvidelse2021() {
		assertTrue(FodselsnummerValidator.isValid("41814061033"));
		assertTrue(FodselsnummerValidator.isValid("41814065640"));
		assertTrue(FodselsnummerValidator.isValid("41814075603"));

		assertTrue(FodselsnummerValidator.isValid("41810021827"));
		assertTrue(FodselsnummerValidator.isValid("41810025091"));
		assertTrue(FodselsnummerValidator.isValid("41810034422"));
  }

  @Test
  void testSyntheticDNumberIsWithinValidRange() {
	  //+80 (81-92)
	  assertTrue(FodselsnummerValidator.isValid("41814061033"));
	  assertFalse(FodselsnummerValidator.isValid("41804061033"));

	  assertTrue(FodselsnummerValidator.isValid("41924061586"));
	  assertFalse(FodselsnummerValidator.isValid("41934063587"));

	  //+65 (66-77)
	  assertTrue(FodselsnummerValidator.isValid("41664061188"));
	  assertFalse(FodselsnummerValidator.isValid("41654061783"));

	  assertTrue(FodselsnummerValidator.isValid("41774061388"));
	  assertFalse(FodselsnummerValidator.isValid("41784061882"));
  }
}
