package no.bekk.bekkopen.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import no.bekk.bekkopen.vehicle.model.BokstavKombinasjon;
import no.bekk.bekkopen.vehicle.model.Trafikkstasjon;

public class VehicleUtilTest {

    @Test
    public void testAlleSkiltserierLastetInn() {
        assertEquals(413, VehicleUtil.getAntallSkiltserier());
    }

    @Test
    public void testSkiltserieForKristiansand() {
        assertEquals("KRISTIANSAND",
            VehicleUtil.hentSkiltserieFraBokstavkombinasjon("PR").getTrafikkstasjoner().get(0).getGeografiskOmråde());
        assertEquals("AGDER",
            VehicleUtil.hentSkiltserieFraBokstavkombinasjon("PR").getTrafikkstasjoner().get(0).getFylke());
        assertEquals("KRISTIANSAND",
            VehicleUtil.hentSkiltserieFraBokstavkombinasjon(new BokstavKombinasjon("PR")).getTrafikkstasjoner()
                .get(0).getGeografiskOmråde());
    }

    /**
     * Følgende bokstavkombinasjoner er tilknyttet flere trafikkstasjoner:
     *
     * CU: Jessheim, Lillestrøm
     * FZ: Elverum, Hamar
     * NA: Sandefjord, Larvik
     * LD: Tønsberg, Horten
     * VS: Trondheim, Støren
     * EX: Hammerfest, Finnsnes
     *
     * CU-, FZ- og EX-serien har de valgt å dele mellom de to
     * trafikkstasjonene som ligger i nærheten av hverandre.
     *
     * Når det gjelder Sandefjord, Horten og Støren er det
     * områder hvor Statens Vegvesen ikke har trafikkstasjoner,
     * så de er da knyttet til hhv. Larvik, Tønsberg og Trondheim
     * hvor det er trafikkstasjoner.
     */
    @Test
    public void testSkiltserieTilknyttetFlereTrafikkstasjoner() {
        List<Trafikkstasjon> traffikstasjoner =
            VehicleUtil.hentSkiltserieFraBokstavkombinasjon("CU").getTrafikkstasjoner();

        assertEquals(2, traffikstasjoner.size());
        assertEquals("JESSHEIM",
            traffikstasjoner.get(0).getGeografiskOmråde()
        );
        assertEquals("LILLESTRØM",
            traffikstasjoner.get(1).getGeografiskOmråde()
        );
    }

    @Test
    public void testHentSkiltserieFraKjennemerke() {
        List<Trafikkstasjon> traffikstasjoner =
            VehicleUtil.hentSkiltserieFraKjennemerke("PR12345").getTrafikkstasjoner();

        assertEquals(1, traffikstasjoner.size());
        assertEquals("KRISTIANSAND",
            traffikstasjoner.get(0).getGeografiskOmråde()
        );

        assertThrows(IllegalArgumentException.class, () -> {
            VehicleUtil.hentSkiltserieFraKjennemerke("PR01234");
        });
    }

    @Test
    public void testGyldigKjennemerke() {
        assertTrue(VehicleUtil.erGyldigKjennemerke("PR12345"));
        assertTrue(VehicleUtil.erGyldigKjennemerke("PR12345"));
        assertFalse(VehicleUtil.erGyldigKjennemerke("PR01234"));
        assertFalse(VehicleUtil.erGyldigKjennemerke("P12345"));
        assertFalse(VehicleUtil.erGyldigKjennemerke("DUMMY"));
    }
}
