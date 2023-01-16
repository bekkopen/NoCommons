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

import no.bekk.bekkopen.common.StringNumber;

import java.math.BigInteger;

import static no.bekk.bekkopen.common.Checksums.calculateMod10CheckSum;
import static no.bekk.bekkopen.common.Checksums.calculateMod11CheckSumAllowDash;
import static no.bekk.bekkopen.common.Checksums.getMod10Weights;
import static no.bekk.bekkopen.common.Checksums.getMod11Weights;

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
        String padded = String.format("%0" + (targetLength-1) + "d", new BigInteger(baseNumber));
        Kidnummer k = new Kidnummer(padded + "0");
        return KidnummerValidator.getKidnummer(padded + calculateMod10CheckSum(getMod10Weights(k), k));
    }

    /**
     * Return a valid mod11 Kidnummer by adding checksum digit
     *
     * Se mod11Kid for more information about special checksum chars.
     *
     * @param baseNumber input number, digits only
     * @return Kidnummer
     */
    public static Kidnummer mod11Kid(String baseNumber) {
        return Kidnummer.mod11Kid(baseNumber, baseNumber.length()+1);
    }

    /**
     * Create a valid KID numer of the wanted length, using MOD11.
     * Input is padded with leading zeros to reach wanted target length
     *
     * Be aware that mod11 checksum generation for KID allows the remainder to be 1
     * and returns a checksum of `-` in stead of 10.
     *
     * However, many frontend validation in banks, for instance, can't cope with this case and the use
     * of KID numbers with a `-` is strongly discouraged. With this in mind we can argue
     * that you should eiter use mod10 as KID-checksum algorithm. Or if
     * NoCommons returns a KID with `-` as checksum, you should use som kind
     * of system for your self to use another baseNumber.
     *
     * In addition, many users don't understand that `-` is infact part of the KID.
     *
     * NoCommons will validate a KID with `-` as valid.
     *
     * @param baseNumber base number to calculate checksum digit for
     * @param targetLength wanted length, 0-padded. Between 2-25
     * @return Kidnummer
     */
    public static Kidnummer mod11Kid(String baseNumber, int targetLength) {
        if (baseNumber.length() >= targetLength)
            throw new IllegalArgumentException("baseNumber too long");
        String padded = String.format("%0" + (targetLength-1) + "d", new BigInteger(baseNumber));
        Kidnummer k = new Kidnummer(padded + "0");
        return KidnummerValidator.getKidnummer(padded + calculateMod11CheckSumAllowDash(getMod11Weights(k), k));
    }
}
