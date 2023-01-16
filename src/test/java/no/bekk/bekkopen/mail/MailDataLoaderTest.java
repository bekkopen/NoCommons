package no.bekk.bekkopen.mail;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import no.bekk.bekkopen.mail.model.PostnummerKategori;

public class MailDataLoaderTest {

    @BeforeEach
    public void setUpLocaleAndLoadData() throws Exception {
        Locale.setDefault(new Locale("no", "NO"));
        File f = new File("src/main/resources/postnummer.csv");
        MailDataLoader.loadFromInputStream(new FileInputStream(f));
    }

    @Test
    public void testExceptionVedFeilInputVedLastingAvFil() {
        assertThrows(IllegalArgumentException.class, () -> {
            MailDataLoader.loadFromInputStream(null);
        });
    }

    @Test
    public void testAntallPoststed() {
        assertEquals(1828, MailValidator.getAntallPoststed());
    }

    @Test
    public void testAntallPostnummer() {
        assertEquals(5133, MailValidator.getAntallPostnummer());
    }

    @Test
    public void testAntallKommunenummer() {
        assertEquals(358, MailValidator.getAntallKommunenummer());
    }

    @Test
    public void testAntallPostnummerForHamar() {
        List<?> options = MailValidator.getPostnummerForPoststed("HAMAR");
        assertEquals(18, options.size());
    }

    @Test
    public void testPoststedForPostnummer2315() {
        assertEquals("HAMAR", MailValidator.getPoststedForPostnummer("2315").toString());
    }

    @Test
    public void testLoadDataFromClasspath() {
        boolean success = MailDataLoader.loadFromClassPath();
        assertTrue(success);
    }
}
