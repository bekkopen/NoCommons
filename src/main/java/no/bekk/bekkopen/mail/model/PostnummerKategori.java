package no.bekk.bekkopen.mail.model;

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

    PostnummerKategori(String beskrivelse) {
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
