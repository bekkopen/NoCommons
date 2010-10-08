package boss.nocommons.person;

/**
 * This class represents a name according to Norwegian rules.
 */
public class Navn {

	private final String fornavn;
	private String mellomnavn;
	private final String etternavn;

	Navn(final String fornavn, final String mellomnavn, final String etternavn) {
		this.fornavn = korrigerCasing(fornavn);
		this.mellomnavn = korrigerCasing(mellomnavn);
		this.etternavn = korrigerCasing(etternavn);

	}

	public Navn(final String fornavn, final String etternavn) {
		this.fornavn = fornavn;
		this.etternavn = etternavn;
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
