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
 * This class represent a Norwegian postal area category.
 *
 * Postnummerkategoriene forteller hva postnummeret blir benyttet til
 * (f.eks. gateadresser og/eller postboksadresser).
 *
 * B = Både gateadresser og postbokser
 * F = Flere bruksområder (felles)
 * G = Gateadresser (og stedsadresser), dvs. “grønne postkasser”
 * P = Postbokser
 * S = Servicepostnummer (disse postnumrene er ikke i bruk til postadresser)
 */
public enum PostnummerKategori {
    B("Både gateadresser og postbokser"),
    F("Flere bruksområder (felles)"),
    G("Gateadresser (og stedsadresser)"),
    P("Postbokser"),
    S("Servicepostnummer");

    private String beskrivelse;

    private PostnummerKategori(String beskrivelse) {
        if (beskrivelse == null) {
            throw new IllegalArgumentException();
        }
        this.beskrivelse = beskrivelse;
    }

    public static PostnummerKategori fromString(String postnummerKategori) {
        for (PostnummerKategori p : PostnummerKategori.values()) {
            if (p.name().equals(postnummerKategori)) {
                return p;
            }
        }
        return null;
    }
}
