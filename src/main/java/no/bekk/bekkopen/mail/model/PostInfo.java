package no.bekk.bekkopen.mail.model;

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

/**
 * Bruker postnummer hentet fra: https://www.bring.no/tjenester/adressetjenester/postnummer
 *
 * Tilgjengelig i CSV-format her:   https://gist.github.com/storbukas/a3f47ff86e8fd9c0a9e8649ea3c670e1
 * Tilgjengelig i JSON-format her:  https://gist.github.com/storbukas/b5f86dca64b8bf2a46e5bb108c695ec1
 */
public class PostInfo {
    private Postnummer postnummer;
    private Poststed poststed;
    private PostnummerKategori postnummerKategori;
    private Kommunenummer kommunenummer;
    private Kommunenavn kommunenavn;

    public PostInfo(Postnummer postnummer, Poststed poststed,
        Kommunenummer kommunenummer, Kommunenavn kommunenavn, PostnummerKategori postnummerKategori) {
        this.postnummer = postnummer;
        this.poststed = poststed;
        this.kommunenummer = kommunenummer;
        this.kommunenavn = kommunenavn;
        this.postnummerKategori = postnummerKategori;
    }

    public Postnummer getPostnummer() {
        return postnummer;
    }

    public Poststed getPoststed() {
        return poststed;
    }

    public PostnummerKategori getPostnummerKategori() {
        return postnummerKategori;
    }

    public Kommunenummer getKommunenummer() {
        return kommunenummer;
    }

    public Kommunenavn getKommunenavn() {
        return kommunenavn;
    }
}
