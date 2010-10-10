package no.bekk.bekkopen.person;


public enum KJONN {

	MANN, KVINNE, BEGGE;

	static boolean erMann(final KJONN kjonn) {
		return kjonn.equals(MANN);
	}

	static boolean erKvinne(final KJONN kjonn) {
		return kjonn.equals(KVINNE);
	}

	static boolean erBegge(final KJONN kjonn) {
		return kjonn.equals(BEGGE);
	}

	static KJONN byttKjonn(final KJONN kjonn) {
		if (erKvinne(kjonn)) {
			return MANN;
		}
		return KVINNE;
	}

}
