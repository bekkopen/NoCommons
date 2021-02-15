package no.bekk.bekkopen.phone.model;

public enum Bruksområde {
    FASTNETTNUMMER("Fastnettnummer"),
    LANDMOBILE_TJENESTER("Landmobile tjenester"),
    MASKIN("Maskin-til-maskin kommunikasjon"),
    DELT_TAKSERING("Delt taksering"),
    REVERSERT_TAKSERING("Reversert taksering"),
    PERSONSVAR("Personsvar (Voice mail)"),
    FJERNTAKSTNUMMER("Fjerntakstnummer"),
    TELETORG_1("Teletorg kategori 1"),
    TELETORG_2("Teletorg kategori 2"),
    INNSAMLING_1("Innsamling kategori 1"),
    INNSAMLING_2("Innsamling kategori 2"),
    INNSAMLING_3("Innsamling kategori 3"),
    INNSAMLING_4("Innsamling kategori 4"),
    INNSAMLING_5("Innsamling kategori 5"),
    IP("IP-baserte telefon- og fakstjenester"),
    TETRA("TETRA"),
    GSM_R("GSM-R"),
    UPT("UPT / Personlig nummer"),
    LOKAL("Lokaltakst-nummer"),
    GLOBAL("Global title adresser");

    private final String beskrivelse;

    Bruksområde(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public static Bruksområde fromString(String bruksområde) {
        for (Bruksområde b : Bruksområde.values()) {
            if (b.name().equals(bruksområde)) {
                return b;
            }
        }
        return null;
    }

    public static Bruksområde fromBeskrivelse(String beskrivelse) {
        for (Bruksområde b : Bruksområde.values()) {
            if (b.beskrivelse.equals(beskrivelse)) {
                return b;
            }
        }
        return null;
    }
}
