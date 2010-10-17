package no.bekk.bekkopen.banking;

import no.bekk.bekkopen.common.Constants;
import no.bekk.bekkopen.common.StringNumber;

/**
 * This class represent a Norwegian bank account number - a Kontonummer. A
 * Kontonummer consists of 11 digits, often grouped in 4, 2 and 5 digits from
 * left to right. The first 4 digits are known as the Registernummer. The last
 * digit in the final group of 5 digits is a checksum digit.
 */
public class Kontonummer extends StringNumber {
	
    Kontonummer(String kontonummer) {
        super(kontonummer);
    }

    /**
     * The four first digit of the Kontonummer is known as the Registernummer.
     * 
     * @return The Registernummer
     */
    public String getRegisternummer() {
        return getValue().substring(0, 4);
    }

    /**
     * The two digits after the Registernummer is often used to identify an
     * account type.
     * 
     * @return The Account Type
     */
    public String getAccountType() {
        return getValue().substring(4, 6);
    }

    /**
     * The 6 digits after the Registernummer are known as the Account - Konto.
     * 
     * @return The Konto
     */
    public String getKonto() {
        return getValue().substring(4, 10);
    }

    /**
     * Returns the Kontonummer as a String, formatted with '.''s separating the
     * Registernummer, AccountType and end part.
     * 
     * @return The formatted Kontonummer
     */
    public String getGroupedValue() {
        StringBuffer sb = new StringBuffer();
        sb.append(getRegisternummer()).append(Constants.DOT);
        sb.append(getAccountType()).append(Constants.DOT);
        sb.append(getPartAfterAccountType());
        return sb.toString();
    }

    private String getPartAfterAccountType() {
        return getValue().substring(6);
    }

}
