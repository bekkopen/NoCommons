package no.bekk.bekkopen.phone.model;

public class Tilbyder {
    private final String tilbyderNavn;

    public Tilbyder(String tilbyderNavn) {
        this.tilbyderNavn = tilbyderNavn;
    }

    public String getTilbyderNavn() {
        return tilbyderNavn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tilbyderNavn == null) ? 0 : tilbyderNavn.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tilbyder other = (Tilbyder) obj;
        if (tilbyderNavn == null) {
            if (other.tilbyderNavn != null) {
                return false;
            }
        } else if (!tilbyderNavn.equals(other.tilbyderNavn)) {
            return false;
        }
        return true;
    }
}
