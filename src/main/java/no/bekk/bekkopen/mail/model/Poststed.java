package no.bekk.bekkopen.mail.model;

/**
 * This class represent a Norwegian postal area - a Poststed.
 */
public class Poststed {
    private String poststed;

    public Poststed(String poststed) {
        if (poststed == null) {
            throw new IllegalArgumentException();
        }
        this.poststed = poststed.toUpperCase().trim();
    }

    public String getValue() {
        return this.poststed;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((poststed == null) ? 0 : poststed.hashCode());
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
        final Poststed other = (Poststed) obj;
        if (poststed == null) {
            if (other.poststed != null) {
                return false;
            }
        } else if (!poststed.equals(other.poststed)) {
            return false;
        }
        return true;
    }
}
