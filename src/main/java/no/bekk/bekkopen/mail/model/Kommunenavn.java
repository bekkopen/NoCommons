package no.bekk.bekkopen.mail.model;

/**
 * This class represent a Norwegian municipality name - a Kommunenavn.
 */
public class Kommunenavn {
    private String kommunenavn;

    public Kommunenavn(String kommunenavn) {
        if (kommunenavn == null) {
            throw new IllegalArgumentException();
        }
        this.kommunenavn = kommunenavn.toUpperCase().trim();
    }

    public String getValue() {
        return this.kommunenavn;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kommunenavn == null) ? 0 : kommunenavn.hashCode());
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
        final Kommunenavn other = (Kommunenavn) obj;
        if (kommunenavn == null) {
            if (other.kommunenavn != null) {
                return false;
            }
        } else if (!kommunenavn.equals(other.kommunenavn)) {
            return false;
        }
        return true;
    }
}
