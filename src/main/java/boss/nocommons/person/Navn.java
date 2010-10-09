package boss.nocommons.person;

/**
 * This class represents a name according to Norwegian rules.
 */
public class Navn {

	private String fornavn;
	private String mellomnavn;
	private String etternavn;

	Navn(final String fornavn, final String mellomnavn, final String etternavn) {
		this(fornavn, etternavn);
		if (null != mellomnavn) {
			this.mellomnavn = korrigerCasing(mellomnavn);
		}

	}

	public Navn(final String fornavn, final String etternavn) {
		if (null != fornavn || null != etternavn) {
			this.fornavn = korrigerCasing(fornavn);
			this.etternavn = korrigerCasing(etternavn);
		} else {
			throw new IllegalArgumentException("Parameters can not be null");
		}
	}

	public String getFornavn() {
		return this.fornavn;
	}

	public String getMellomnavn() {
		return this.mellomnavn;
	}

	public String getEtternavn() {
		return this.etternavn;
	}

	public String getNavn() {
		StringBuilder navn = new StringBuilder(fornavn);
		if (null != mellomnavn) {
			navn.append(" ").append(mellomnavn);
		}
		navn.append(" ").append(etternavn);
		return navn.toString();
	}

	@Override
	public String toString() {
		return getNavn();
	}

	private String korrigerCasing(final String navn) {
		if (navn.length() == 0) {
			return "";
		}
		if (navn.length() == 1) {
			return navn.toUpperCase();
		}
		StringBuilder korrigetNavn = new StringBuilder(navn.substring(0, 1).toUpperCase());
		korrigetNavn.append(navn.substring(1, navn.length()).toLowerCase());
		return korrigetNavn.toString();
	}
}
