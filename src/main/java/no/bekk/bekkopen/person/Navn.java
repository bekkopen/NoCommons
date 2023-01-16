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

/**
 * This class represents a name according to Norwegian rules. A legal Norwegian
 * name consist of at least a Fornavn and an Etternavn. Etternavn can also
 * optionally be used as a Mellomnavn.
 */
public class Navn {

	private final String fornavn;
	private String mellomnavn;
	private final String etternavn;

	Navn(final String fornavn, final String mellomnavn, final String etternavn) {
		this(fornavn, etternavn);
		if (null != mellomnavn) {
			this.mellomnavn = korrigerCasing(mellomnavn);
		}

	}

	public Navn(final String fornavn, final String etternavn) {
		if (null == fornavn || null == etternavn) {
			throw new IllegalArgumentException("fornavn or etternavn can not be null: " + "fornavn=" + fornavn
					+ ",etternavn=" + etternavn);
		}
		this.fornavn = korrigerCasing(fornavn);
		this.etternavn = korrigerCasing(etternavn);
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
