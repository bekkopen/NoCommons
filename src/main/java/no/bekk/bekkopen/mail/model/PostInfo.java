package no.bekk.bekkopen.mail.model;

/**
 * Bruker postnummer hentet fra: https://www.bring.no/tjenester/adressetjenester/postnummer
 *
 * Tilgjengelig i CSV-format her:   https://gist.github.com/storbukas/a3f47ff86e8fd9c0a9e8649ea3c670e1
 * Tilgjengelig i JSON-format her:  https://gist.github.com/storbukas/b5f86dca64b8bf2a46e5bb108c695ec1
 */
public class PostInfo {
    private Postnummer postnummer;
    private Poststed poststed;
    private PostnummerKategori postnummerKategori;
    private Kommunenummer kommunenummer;
    private Kommunenavn kommunenavn;

    public PostInfo(Postnummer postnummer, Poststed poststed,
        Kommunenummer kommunenummer, Kommunenavn kommunenavn, PostnummerKategori postnummerKategori) {
        this.postnummer = postnummer;
        this.poststed = poststed;
        this.kommunenummer = kommunenummer;
        this.kommunenavn = kommunenavn;
        this.postnummerKategori = postnummerKategori;
    }

    public Postnummer getPostnummer() {
        return postnummer;
    }

    public Poststed getPoststed() {
        return poststed;
    }

    public PostnummerKategori getPostnummerKategori() {
        return postnummerKategori;
    }

    public Kommunenummer getKommunenummer() {
        return kommunenummer;
    }

    public Kommunenavn getKommunenavn() {
        return kommunenavn;
    }
}
