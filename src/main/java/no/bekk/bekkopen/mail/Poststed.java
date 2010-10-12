package no.bekk.bekkopen.mail;

/**
 * This class represent a Norwegian postal area - a Poststed.
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

	@Override
	public String toString() {
		return getValue();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((poststedString == null) ? 0 : poststedString.hashCode());
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
