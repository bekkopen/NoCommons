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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import no.bekk.bekkopen.mail.model.Kommunenavn;
import no.bekk.bekkopen.mail.model.Kommunenummer;
import no.bekk.bekkopen.mail.model.PostInfo;
import no.bekk.bekkopen.mail.model.Postnummer;
import no.bekk.bekkopen.mail.model.PostnummerKategori;
import no.bekk.bekkopen.mail.model.Poststed;

/**
 * This class loads data about Postnummer and Poststed into memory. The class
 * provides a method that loads data from a file included in the jar, and
 * another method that loads data from a given InputStream.
 */
public class MailDataLoader {

    private MailDataLoader() {
        super();
    }

    public static void loadFromInputStream(InputStream is) {
        if (is == null) {
            throw new IllegalArgumentException();
        }

        Map<Postnummer, PostInfo> postInfo = new HashMap<>();

        try (
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",", false);

                Postnummer postnummer = MailValidator.getPostnummer(st.nextToken());
                Poststed poststed = new Poststed(st.nextToken());
                Kommunenummer kommunenummer = MailValidator.getKommunenummer(st.nextToken());
                Kommunenavn kommunenavn = new Kommunenavn(st.nextToken());
                PostnummerKategori postnummerKategori = MailValidator.getPostnummerKategori(st.nextToken());

                // add to postInfo
                postInfo.put(
                    postnummer,
                    new PostInfo(
                        postnummer,
                        poststed,
                        kommunenummer,
                        kommunenavn,
                        postnummerKategori
                    )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        MailValidator.setPostInfo(postInfo);
    }

    public static boolean loadFromClassPath() {
        boolean success = false;

        try (InputStream is = MailDataLoader.class.getResourceAsStream("/postnummer.csv")) {
            loadFromInputStream(is);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return success;
    }
}
