package no.bekk.bekkopen.common;

/**
 * Base class for all classes that represents a String with digits.
 */
public abstract class StringNumber {

    private final String value;

    protected StringNumber(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getAt(int i) {
        return getValue().charAt(i) - '0';
    }

    public String toString() {
        return getValue();
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        final StringNumber other = (StringNumber) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

	public int getLength() {
		return getValue().length();
	}

	/**
	 * The last digit of a StringNumber is the checksum digit.
	 * 
	 * @return The checksum digit.
	 */
	public int getChecksumDigit() {
	    return getAt(getLength()-1);
	}

}
