package no.bekk.bekkopen.banking;

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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class KidnummerTest {
    @Test
    public void mod10Kid() {
        assertValidKid("0000001230", Kidnummer.mod10Kid("123", 10));
        assertValidKid("0000012344", Kidnummer.mod10Kid("1234", 10));
    }

    @Test
    public void mod11Kid() {
        assertValidKid("0000001236", Kidnummer.mod11Kid("123", 10));
        assertValidKid("0000012343", Kidnummer.mod11Kid("1234", 10));
    }

    @Test
    public void tooLongBaseMod10() {
      Assertions.assertThrows(IllegalArgumentException.class,() ->
        Kidnummer.mod10Kid("1234567890", 3)
      );
    }

    @Test
    public void tooLongBaseMod101() {
      Assertions.assertThrows(IllegalArgumentException.class,() ->
        Kidnummer.mod11Kid("1234567890", 3)
      );
    }

    @Test
    public void belowRangeMod10() {
      Assertions.assertThrows(IllegalArgumentException.class,() ->
        Kidnummer.mod10Kid("12", 1)
      );
    }

    @Test
    public void belowRangeMod11() {
      Assertions.assertThrows(IllegalArgumentException.class,() ->
        Kidnummer.mod11Kid("12", 1)
      );
    }

    @Test
    public void aboveRangeMod10() {
      Assertions.assertThrows(IllegalArgumentException.class,() ->
        Kidnummer.mod10Kid("12", 26)
      );
    }

    @Test
    public void aboveRangeMod11() {
      Assertions.assertThrows(IllegalArgumentException.class,() ->
        Kidnummer.mod11Kid("12", 26)
      );
    }

    private void assertValidKid(String expected, Kidnummer kidnummer) {
        KidnummerValidator.isValid(kidnummer.toString());
        assertEquals(expected, kidnummer.toString());
    }
}
