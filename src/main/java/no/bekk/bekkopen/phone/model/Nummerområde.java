package no.bekk.bekkopen.phone.model;

public class Nummerområde {
    private final String fra;
    private final String til;
    private final Long antall;

    public Nummerområde(String fra, String til, int antall) {
        this.fra = fra;
        this.til = til;
        this.antall = new Long(antall);
    }

    public Long getFra() {
        return Long.parseLong(fra);
    }

    public String getFraString() {
        return fra;
    }

    public Long getTil() {
        return Long.parseLong(til);
    }

    public String getTilString() {
        return til;
    }

    public Long getAntall() {
        return antall;
    }

    public boolean erInnenforNummerområde(String telefonummer) {
        return erInnenforNummerområde(Long.parseLong(telefonummer));
    }

    public boolean erInnenforNummerområde(Integer telefonummer) {
        return erInnenforNummerområde(new Long(telefonummer));
    }

    public boolean erInnenforNummerområde(Long telefonummer) {
        return telefonummer >= Long.parseLong(fra) && telefonummer <= Long.parseLong(til);
    }
}
