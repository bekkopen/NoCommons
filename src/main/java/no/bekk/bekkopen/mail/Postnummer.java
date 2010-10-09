package no.bekk.bekkopen.mail;

import no.bekk.bekkopen.common.StringNumber;

/**
 * This class represent a Norwegian Zip code - a Postnummer. A Postnummer
 * consists of 4 digits.
 * @author Per K. Mengshoel
 */
public class Postnummer extends StringNumber {

    Postnummer(String postnummerString) {
        super(postnummerString);
    }

}
