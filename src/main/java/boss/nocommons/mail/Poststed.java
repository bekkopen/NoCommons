package boss.nocommons.mail;

/**
 * This class represent a Norwegian postal area - a Poststed.
 * @author Per K. Mengshoel
 */
public class Poststed {

    private String poststedString = null;

    Poststed(String poststedString) {
        if (poststedString == null) {
            throw new IllegalArgumentException();
        }
        this.poststedString = poststedString.toUpperCase().trim();
    }

    public String getValue() {
        return this.poststedString;
    }

    public String toString() {
        return getValue();
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((poststedString == null) ? 0 : poststedString.hashCode());
        return result;
    }

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
        if (poststedString == null) {
            if (other.poststedString != null) {
                return false;
            }
        } else if (!poststedString.equals(other.poststedString)) {
            return false;
        }
        return true;
    }

}
