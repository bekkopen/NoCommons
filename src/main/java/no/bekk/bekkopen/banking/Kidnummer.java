package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.common.StringNumber;

/**
 * This class represent a Norwegian KID-nummer - a number used to identify 
 * a customer on invoices. A Kidnummer consists of digits only, and the last
 * digit is a checksum digit (either mod10 or mod11).
 */
public class Kidnummer extends StringNumber {
	
    Kidnummer(String kontonummer) {
        super(kontonummer);
    }

}
