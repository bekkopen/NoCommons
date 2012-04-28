package no.bekk.bekkopen.person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Person {

	private final static DateFormat fDatoFormat = new SimpleDateFormat("ddMMyy");
	private final Navn navn;
	private final Fodselsnummer fodselsnummer;

	public Person(Navn navn, Fodselsnummer fodselsnummer) {
		super();
		this.navn = navn;
		this.fodselsnummer = fodselsnummer;
	}

	public Navn getNavn() {
		return this.navn;
	}

	public String getFornavn() {
		return this.navn.getFornavn();
	}

	public String getMellomnavn() {
		return this.navn.getMellomnavn();
	}

	public String getEtternavn() {
		return this.navn.getEtternavn();
	}

	public Fodselsnummer getFodselsnummer() {
		return this.fodselsnummer;
	}

	public String getFodselsdatoAsString() {
		return this.fodselsnummer.getDateOfBirth();
	}

	public Date getFodselsdato() throws ParseException {
      return fDatoFormat.parse(this.fodselsnummer.getDateOfBirth());
	}

	public String getPersonnummer() {
		return this.fodselsnummer.getPersonnummer();
	}

	public boolean erKvinne() {
		return fodselsnummer.isFemale();
	}

	public boolean erMann() {
		return fodselsnummer.isMale();
	}

}
