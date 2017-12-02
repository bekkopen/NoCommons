package no.bekk.bekkopen.banking;

import org.junit.Test;

import static org.junit.Assert.*;

public class KidnummerTest {
    @Test
    public void mod10Kid() throws Exception {
        assertValidKid("0000001230", Kidnummer.mod10Kid("123", 10));
        assertValidKid("0000012344", Kidnummer.mod10Kid("1234", 10));
    }
    
    @Test
    public void mod11Kid() throws Exception {
        assertValidKid("0000001236", Kidnummer.mod11Kid("123", 10));
        assertValidKid("0000012343", Kidnummer.mod11Kid("1234", 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooLongBaseMod10() {
        Kidnummer.mod10Kid("1234567890", 3);
    } 

    @Test(expected = IllegalArgumentException.class)
    public void tooLongBaseMod101() {
        Kidnummer.mod11Kid("1234567890", 3);
    } 

    @Test(expected = IllegalArgumentException.class)
    public void belowRangeMod10() {
        Kidnummer.mod10Kid("12", 1);
    } 

    @Test(expected = IllegalArgumentException.class)
    public void belowRangeMod11() {
        Kidnummer.mod11Kid("12", 1);
    } 

    @Test(expected = IllegalArgumentException.class)
    public void aboveRangeMod10() {
        Kidnummer.mod10Kid("12", 26);
    } 

    @Test(expected = IllegalArgumentException.class)
    public void aboveRangeMod11() {
        Kidnummer.mod11Kid("12", 26);
    } 
    
    private void assertValidKid(String expected, Kidnummer kidnummer) {
        KidnummerValidator.isValid(kidnummer.toString());
        assertEquals(expected, kidnummer.toString());
    }
}