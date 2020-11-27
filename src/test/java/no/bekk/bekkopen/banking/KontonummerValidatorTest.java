package no.bekk.bekkopen.banking;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static no.bekk.bekkopen.common.Checksums.ERROR_INVALID_CHECKSUM;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class KontonummerValidatorTest {

    private static final String KONTONUMMER_VALID = "99990000006";
    private static final String KONTONUMMER_INVALID_CHECKSUM = "99990000005";

    @Test
    public void testInvalidKontonummerWrongLength() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> KontonummerValidator.validateSyntax("123456789012"));
        assertThat(thrown.getMessage(), containsString(KontonummerValidator.ERROR_SYNTAX));
    }

    @Test
    public void testInvalidKontonummerNotDigits() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> KontonummerValidator.validateSyntax("abcdefghijk"));
        assertThat(thrown.getMessage(), containsString(KontonummerValidator.ERROR_SYNTAX));
    }

    @Test
    public void testInvalidKontonummerWrongChecksum() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> KontonummerValidator.validateChecksum(KONTONUMMER_INVALID_CHECKSUM));
        assertThat(thrown.getMessage(), containsString(ERROR_INVALID_CHECKSUM));
    }

    @Test
    public void testInvalidAccountTypeWrongLength() {
        String wrongLength = Stream.generate(() -> "0").limit(KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS + 1).collect(joining());
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> KontonummerValidator.validateAccountTypeSyntax(wrongLength));
        assertThat(thrown.getMessage(), containsString(KontonummerValidator.ERROR_SYNTAX));
    }

    @Test
    public void testInvalidAccountTypeNotDigits() {
        String correctLengthButNotDigits = Stream.generate(() -> "A").limit(KontonummerValidator.ACCOUNTTYPE_NUM_DIGITS).collect(joining());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> KontonummerValidator.validateAccountTypeSyntax(correctLengthButNotDigits));
        assertThat(thrown.getMessage(), containsString(KontonummerValidator.ERROR_SYNTAX));
    }

    @Test
    public void testInvalidRegisternummerNotDigits() {
        String correctLengthButNotDigits = Stream.generate(() -> "A").limit(KontonummerValidator.REGISTERNUMMER_NUM_DIGITS).collect(joining());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> KontonummerValidator.validateRegisternummerSyntax(correctLengthButNotDigits));
        assertThat(thrown.getMessage(), containsString(KontonummerValidator.ERROR_SYNTAX));
    }

    @Test
    public void testInvalidRegisternummerWrongLength() {
        String wrongLength = Stream.generate(() -> "0").limit(KontonummerValidator.REGISTERNUMMER_NUM_DIGITS + 1).collect(joining());
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> KontonummerValidator.validateRegisternummerSyntax(wrongLength));
        assertThat(thrown.getMessage(), containsString(KontonummerValidator.ERROR_SYNTAX));
    }

    @Test
    public void testGetValidKontonummerFromInvalidKontonummerWrongChecksum() {
        Kontonummer knr = KontonummerValidator.getAndForceValidKontonummer(KONTONUMMER_INVALID_CHECKSUM);
        assertTrue(KontonummerValidator.isValid(knr.toString()));
    }

    @Test
    public void testIsValid() {
        assertTrue(KontonummerValidator.isValid(KONTONUMMER_VALID));
        assertFalse(KontonummerValidator.isValid(KONTONUMMER_INVALID_CHECKSUM));
    }

    @Test
    public void testIf00000000000IsValid(){
        assertFalse(KontonummerValidator.isValid("00000000000"));
    }

   @Test
   public void testValidNumberEndingOn9() {
      assertTrue(KontonummerValidator.isValid("97104133219"));
      assertTrue(KontonummerValidator.isValid("97105302049"));
      assertTrue(KontonummerValidator.isValid("97104008309"));
      assertTrue(KontonummerValidator.isValid("97102749069"));
   }
}
