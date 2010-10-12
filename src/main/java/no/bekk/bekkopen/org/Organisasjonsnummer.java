package no.bekk.bekkopen.org;

import no.bekk.bekkopen.common.StringNumber;

/**
 * This class represent a Norwegian organization number - an
 * Organisasjonsnummer. An Organisasjonsnummer consists of 9 digits, and the
 * last digit is a checksum digit.
 */
public class Organisasjonsnummer extends StringNumber {

    Organisasjonsnummer(String organisasjonsnummer) {
        super(organisasjonsnummer);
    }

}
