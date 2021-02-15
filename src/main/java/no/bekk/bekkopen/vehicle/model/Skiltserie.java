package no.bekk.bekkopen.vehicle.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Skiltserie {
    private static final String regexp = "^[A-Z]{1,3}-[A-Z]{1,2}-[0-9]{1,4}$";

    private final BokstavKombinasjon bokstavKombinasjon;
    private final List<Trafikkstasjon> trafikkstasjoner = new ArrayList<>();
    private final BokstavKombinasjonKategori bokstavKombinasjonKategori;

    public Skiltserie(BokstavKombinasjon bokstavKombinasjon, List<Trafikkstasjon> trafikkstasjoner,
        BokstavKombinasjonKategori bokstavKombinasjonKategori) {
        this.bokstavKombinasjon = bokstavKombinasjon;
        this.trafikkstasjoner.addAll(trafikkstasjoner);
        this.bokstavKombinasjonKategori = bokstavKombinasjonKategori;
    }

    public Skiltserie(BokstavKombinasjon bokstavKombinasjon, Trafikkstasjon trafikkstasjoner,
        BokstavKombinasjonKategori bokstavKombinasjonKategori) {
        this(bokstavKombinasjon, Arrays.asList(trafikkstasjoner), bokstavKombinasjonKategori);
    }

    public BokstavKombinasjon getBokstavKombinasjon() {
        return bokstavKombinasjon;
    }

    public List<Trafikkstasjon> getTrafikkstasjoner() {
        return trafikkstasjoner;
    }

    public BokstavKombinasjonKategori getBokstavKombinasjonKategori() {
        return bokstavKombinasjonKategori;
    }
}
