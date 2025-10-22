package no.bekk.bekkopen.person;

/*-
 * #%L
 * NoCommons
 * %%
 * Copyright (C) 2014 - 2023 BEKK open source
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person {

	private static final DateTimeFormatter fDatoFormat = DateTimeFormatter.ofPattern("ddMMyyyy");
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

	public LocalDate getFodselsdato() {
        return LocalDate.parse(this.fodselsnummer.getDateAndMonth() + this.fodselsnummer.getBirthYear(), fDatoFormat);
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
