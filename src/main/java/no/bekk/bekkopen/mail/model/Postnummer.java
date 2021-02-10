package no.bekk.bekkopen.mail.model;

import no.bekk.bekkopen.common.StringNumber;

/**
 * This class represent a Norwegian Zip code - a Postnummer. A Postnummer
 * consists of 4 digits.
 */
public class Postnummer extends StringNumber {
    public Postnummer(String postnummerString) {
        super(postnummerString);
    }
}
