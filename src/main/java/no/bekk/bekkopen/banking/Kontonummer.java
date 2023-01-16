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
        StringBuilder sb = new StringBuilder();
        sb.append(getRegisternummer()).append(Constants.DOT);
        sb.append(getAccountType()).append(Constants.DOT);
        sb.append(getPartAfterAccountType());
        return sb.toString();
    }

    private String getPartAfterAccountType() {
        return getValue().substring(6);
    }

}
