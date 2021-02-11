package no.bekk.bekkopen.mail.model;

import no.bekk.bekkopen.common.StringNumber;

/**
 * This class represent a Norwegian Municipalioty code - a Kommunenummer.
 * A Kommunenummer consists of 4 digits.
 */
public class Kommunenummer extends StringNumber {
    public Kommunenummer(String kommunenummer) {
        super(kommunenummer);
    }
}
