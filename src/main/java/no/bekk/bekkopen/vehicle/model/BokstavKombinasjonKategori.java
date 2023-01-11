package no.bekk.bekkopen.vehicle.model;

public enum BokstavKombinasjonKategori {
    GEOGRAFISK("Bokstavkombinasjoner fra geografisk område"),
    ELEKTRISK("Elektriskdrevet kjøretøy"),
    GASS("Gassdrevet kjøretøy"),
    HYDROGEN("Hydrogendrevet kjøretøy"),
    AMBASSADE("Ambassadekjøretøy (Corps Diplomatique)");

    private final String beskrivelse;

    BokstavKombinasjonKategori(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public static BokstavKombinasjonKategori fromString(String bokstavKombinasjonKategori) {
        for (BokstavKombinasjonKategori bkk : BokstavKombinasjonKategori.values()) {
            if (bkk.name().equals(bokstavKombinasjonKategori)) {
                return bkk;
            }
        }
        return null;
    }
}
