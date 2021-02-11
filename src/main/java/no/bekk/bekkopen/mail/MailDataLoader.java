package no.bekk.bekkopen.mail;

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

    public static Map<Postnummer, PostInfo> loadFromInputStream(InputStream is) {
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

        return postInfo;
    }

    /**
     * @deprecated 11.02.2021 The postnummer data is loaded automatically
     */
    @Deprecated
    public static boolean loadFromClassPath() {
        boolean success = false;
        Map<Postnummer, PostInfo> postInfo = new HashMap<>();

        try (InputStream is = MailDataLoader.class.getResourceAsStream("/postnummer.csv")) {
            postInfo = loadFromInputStream(is);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (success) {
            MailValidator.setPostInfo(postInfo);
        }

        return success;
    }
}
