package no.bekk.bekkopen.banking;

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
