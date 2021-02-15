package no.bekk.bekkopen.mail.model;

/**
 * De to første sifrene i kommunenummeret er fylkesnummeret.
 *
 * Svalbard og Jan Mayen er ikke ordinære fylker, men for å
 * kunne skille ut postnummer som tilhører disse områdene
 * har vi valgt å gi dem egne koder som om de var fylker.
 * Kodene er innhentet fra Statistisk Sentralbyrå og er
 * forøvrig de samme som benyttes ellers i forvaltningen.
 *
 * Data hentet fra: https://www.bring.no/tjenester/adressetjenester/postnummer/postnummertabeller-veiledning
 */
public class Fylke {
    private final String fylkesNummer;
    private final String fylkesNavn;

    public Fylke(String fylkesNummer, String fylkesNavn) {
        this.fylkesNummer = fylkesNummer;
        this.fylkesNavn = fylkesNavn;
    }

    public String getFylkesNummer() {
        return fylkesNummer;
    }

    public String getFylkesNavn() {
        return fylkesNavn;
    }
}
