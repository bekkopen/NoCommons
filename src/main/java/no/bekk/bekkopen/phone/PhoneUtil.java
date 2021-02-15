package no.bekk.bekkopen.phone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import no.bekk.bekkopen.phone.model.Bruksområde;
import no.bekk.bekkopen.phone.model.Nummerområde;
import no.bekk.bekkopen.phone.model.Nummerserie;
import no.bekk.bekkopen.phone.model.Status;
import no.bekk.bekkopen.phone.model.Tilbyder;

/**
 * Data hentet fra: https://www.nkom.no/telefoni-og-telefonnummer/telefonnummer-og-den-norske-nummerplan/alle
 * -nummerserier-for-norske-telefonnumre
 *
 * Nummerserie        Nummertype
 * 00  : Internasjonalt prefiks
 * 01  : Reservert for fremtidige endringer i nummerplanen
 * 02000 – 09999  : 5-sifrede spesialnummer
 * 100 – 115  : Standardiserte spesialnummer
 * 116 000 – 116 999  : 6-sifrede EØS-harmoniserte nummer
 * 117 – 179  : Standardiserte spesialnummer
 * 1800 – 1899  :  4-sifrede nummer for nummeropplysningstjenester
 * 190 – 199  : Tilbyderspesifikke spesialnummer
 * 20 00 00 00 – 39 99 99 99  : 8-sifrede fastnettnummer
 * 40 00 00 00 – 49 99 99 99  : 8-sifrede mobilnummer
 * 50 00 00 00 – 57 99 99 99  : 8-sifrede fastnettnummer
 * 58 00 00 00 00 00 – 58 99 99 99 99 99  : 12-sifrede nummer
 * for maskin-til-maskin og tingenes internett kommunikasjon
 * 59 00 00 00 – 59 99 99 99  : 8-sifrede nummer for
 * maskin-til-maskin og tingenes internett kommunikasjon
 * 60 00 00 00 – 79 99 99 99  : 8-sifrede fastnettnummer
 * 800 00 000 – 899 99 999  : 8-sifrede spesialnummer
 * 90 00 00 00 – 99 99 99 99  : 8-sifrede mobilnummer
 *
 * I de fleste tilfeller tildeler Nkom nummerserier til tilbydere,
 * som igjen tildeler enkeltnummer til sine kunder.
 * Enkelte typer nummer tildeler Nkom direkte til sluttbrukere.
 */
public class PhoneUtil {
    // Telefonnummer skal ha mellom 3 og 12 siffer, og starte med 1-9
    // med mindre det er 5 sifret nummer (som kan starte med 0)
    private static final Pattern telefonnummerPattern = Pattern.compile("(^[1-9]{1}[0-9]{2,11}$|^[0-9]{5}$)");

    // Setup

    private final static List<Nummerserie> nummerserier = lesNummerserierFraCsvFil(
        PhoneUtil.class.getResourceAsStream("/telefon_nummerserier.csv")
    );

    // Lookup

    public static List<Nummerserie> hentAlleNummerserier() {
        return nummerserier;
    }

    public static List<Nummerserie> hentNummerserieFraTilbyder(String tilbyder) {
        return hentNummerserieFraTilbyder(
            new Tilbyder(tilbyder)
        );
    }

    public static List<Nummerserie> hentNummerserieFraTilbyder(Tilbyder tilbyder) {
        try {
            return nummerserier.stream().filter(
                ns -> ns.getTilbyder().equals(tilbyder)
            ).collect(Collectors.toList());
        } catch (NullPointerException e) { // TODO
            return Collections.emptyList();
        }
    }

    public static Nummerserie hentNummerserieFraTelefonnummer(String telefonnummer) {
        return nummerserier.stream()
            .filter(nummerserie -> nummerserie.getNummerområde().erInnenforNummerområde(telefonnummer))
            .findAny()
            .orElse(null);
    }

    // Statistics

    public static int getAntallNummerserier() {
        return nummerserier.size();
    }

    // Validate

    public static boolean erGyldigNummer(String telefonnummer) {
        return telefonnummerPattern.matcher(telefonnummer).matches()
            && nummerEksisterer(telefonnummer);
    }

    public static boolean nummerEksisterer(String telefonnummer) {
        return nummerserier.stream()
            .filter(nummerserie -> nummerserie.getNummerområde().erInnenforNummerområde(telefonnummer))
            .findAny().orElse(null) != null;
    }

    public static boolean nummerErTildelt(String telefonnummer) {
        return nummerserier.stream()
            .filter(nummerserie -> nummerserie.getNummerområde().erInnenforNummerområde(telefonnummer)
                && nummerserie.getStatus() == Status.TILDELT)
            .findAny().orElse(null) != null;
    }

    // Helper functions

    private static List<Nummerserie> lesNummerserierFraCsvFil(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException();
        }

        List<Nummerserie> nummerserie = new ArrayList<>();

        try (
            InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",", false);

                String fra = st.nextToken();
                String til = st.nextToken();
                int antall = Integer.parseInt(st.nextToken());
                Tilbyder tilbyder = new Tilbyder(lesCsvFelt(st.nextToken()));
                Status status = Status.fromString(lesCsvFelt(st.nextToken()));
                Bruksområde bruksområde = Bruksområde.fromBeskrivelse(lesCsvFelt(st.nextToken()));

                // legg til nummerserie
                nummerserie.add(
                    new Nummerserie(
                        new Nummerområde(
                            fra, til, antall
                        ),
                        tilbyder,
                        status,
                        bruksområde
                    )
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nummerserie;
    }

    private static String lesCsvFelt(String nextToken) {
        if (nextToken.equals(" ")) {
            return null;
        } else {
            return nextToken;
        }
    }
}
