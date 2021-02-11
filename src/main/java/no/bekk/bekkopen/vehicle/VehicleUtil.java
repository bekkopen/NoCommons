package no.bekk.bekkopen.vehicle;

import static no.bekk.bekkopen.common.HelperFunctions.distinctByKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import no.bekk.bekkopen.person.NavnGenerator;
import no.bekk.bekkopen.vehicle.model.BokstavKombinasjon;
import no.bekk.bekkopen.vehicle.model.BokstavKombinasjonKategori;
import no.bekk.bekkopen.vehicle.model.Skiltserie;
import no.bekk.bekkopen.vehicle.model.Trafikkstasjon;

/**
 * Data hentet fra: https://www.vegvesen.no/kjoretoy/Eie+og+vedlikeholde/skilt/skiltserier
 *
 * Et registreringsnummer består av en bokstav- og tallkombinasjon.
 *
 * Bokstavkombinasjonen baserer seg ofte på hvilken trafikkstasjon som har utstedt registreringsnummeret.
 * Tallseriene for biler og lastebiler er fra 10000 til 99999, mens for andre kjøretøy er det fra 1000 til 9999.
 */
public class VehicleUtil {
    // Setup

    private final static Map<BokstavKombinasjon, Skiltserie> skiltserier = lesSkiltserierFraCsvFil(
        NavnGenerator.class.getResourceAsStream("/kjennemerke_skiltserier.csv")
    );

    // Lookup

    public static Skiltserie hentSkiltserieFraBokstavkombinasjon(String bokstavKombinasjon) {
        return hentSkiltserieFraBokstavkombinasjon(
            new BokstavKombinasjon(
                bokstavKombinasjon
            )
        );
    }

    public static Skiltserie hentSkiltserieFraBokstavkombinasjon(BokstavKombinasjon bokstavKombinasjon) {
        return skiltserier.get(bokstavKombinasjon);
    }

    // Statistics

    public static int getAntallSkiltserier() {
        return (int) skiltserier.entrySet().stream()
            .map(s -> s.getValue())
            .filter(distinctByKey(Skiltserie::getBokstavKombinasjon))
            .count();
    }

    // Helper functions

    private static Map<BokstavKombinasjon, Skiltserie> lesSkiltserierFraCsvFil(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException();
        }

        Map<BokstavKombinasjon, Skiltserie> skiltserier = new HashMap<>();

        try (
            InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",", false);

                BokstavKombinasjon bokstavKombinasjon = new BokstavKombinasjon(st.nextToken());
                String geografiskOmråde = st.nextToken();
                String fylke = st.nextToken();
                BokstavKombinasjonKategori bokstavKombinasjonKategori = BokstavKombinasjonKategori.fromString(
                    st.nextToken());

                // legg til eller oppdater skiltserier
                Skiltserie skiltserie = skiltserier.get(bokstavKombinasjon);
                if (skiltserie != null) {
                    skiltserie.getTrafikkstasjoner().add(
                        new Trafikkstasjon(
                            geografiskOmråde,
                            fylke
                        )
                    );
                } else {
                    skiltserier.put(
                        bokstavKombinasjon,
                        new Skiltserie(
                            bokstavKombinasjon,
                            new Trafikkstasjon(
                                geografiskOmråde,
                                fylke
                            ),
                            bokstavKombinasjonKategori
                        )
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return skiltserier;
    }
}
