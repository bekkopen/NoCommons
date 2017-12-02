package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.common.StringNumber;
import org.apache.commons.lang3.StringUtils;

import static no.bekk.bekkopen.common.Checksums.*;

/**
 * This class represent a Norwegian KID-nummer - a number used to identify 
 * a customer on invoices. A Kidnummer consists of digits only, and the last
 * digit is a checksum digit (either mod10 or mod11).
 */
public class Kidnummer extends StringNumber {

    Kidnummer(String kidnummer) {
        super(kidnummer);
    }

    /**
     * Return a valid mod10 Kidnummer by adding checksum digit
     * @param baseNumber input number, digits only
     * @return Kidnummer
     */
    public static Kidnummer mod10Kid(String baseNumber) {
        return Kidnummer.mod10Kid(baseNumber, baseNumber.length()+1);
    }
    
    /**
     * Create a valid KID numer of the wanted length, using MOD10.
     * Input is padded with leading zeros to reach wanted target length
     * @param baseNumber base number to calculate checksum digit for
     * @param targetLength wanted length, 0-padded. Between 2-25
     * @return Kidnummer
     */
    public static Kidnummer mod10Kid(String baseNumber, int targetLength) {
        if (baseNumber.length() >= targetLength)
            throw new IllegalArgumentException("baseNumber too long");
        String padded = StringUtils.leftPad(baseNumber, targetLength-1, "0");
        Kidnummer k = new Kidnummer(padded + "0");
        return KidnummerValidator.getKidnummer(padded + calculateMod10CheckSum(getMod10Weights(k), k));
    }

    /**
     * Return a valid mod10 Kidnummer by adding checksum digit
     * @param baseNumber input number, digits only
     * @return Kidnummer
     */
    public static Kidnummer mod11Kid(String baseNumber) {
        return Kidnummer.mod11Kid(baseNumber, baseNumber.length()+1);
    }

    /**
     * Create a valid KID numer of the wanted length, using MOD11.
     * Input is padded with leading zeros to reach wanted target length
     * @param baseNumber base number to calculate checksum digit for
     * @param targetLength wanted length, 0-padded. Between 2-25
     * @return Kidnummer
     */
    public static Kidnummer mod11Kid(String baseNumber, int targetLength) {
        if (baseNumber.length() >= targetLength)
            throw new IllegalArgumentException("baseNumber too long");
        String padded = StringUtils.leftPad(baseNumber, targetLength-1, "0");
        Kidnummer k = new Kidnummer(padded + "0");
        return KidnummerValidator.getKidnummer(padded + calculateMod11CheckSum(getMod11Weights(k), k));
    }
}
