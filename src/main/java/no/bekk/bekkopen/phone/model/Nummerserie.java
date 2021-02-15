package no.bekk.bekkopen.phone.model;

public class Nummerserie {
    private final Nummerområde nummerområde;
    private final Tilbyder tilbyder;
    private final Status status;
    private final Bruksområde bruksområde;

    public Nummerserie(Nummerområde nummerområde, Tilbyder tilbyder, Status status,
        Bruksområde bruksområde) {
        this.nummerområde = nummerområde;
        this.tilbyder = tilbyder;
        this.status = status;
        this.bruksområde = bruksområde;
    }

    public Nummerområde getNummerområde() {
        return nummerområde;
    }

    public Tilbyder getTilbyder() {
        return tilbyder;
    }

    public Status getStatus() {
        return status;
    }

    public Bruksområde getBruksområde() {
        return bruksområde;
    }
}
