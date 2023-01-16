package no.bekk.bekkopen.org;

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

import java.util.ArrayList;
import java.util.List;

/**
 * This class calculates valid Organisasjonsnummer instances.
 */
public class OrganisasjonsnummerCalculator {

	private static final int LENGTH = 9;

	private OrganisasjonsnummerCalculator() {
		super();
	}

	/**
	 * Returns a List with completely random but syntactically valid
	 * Organisasjonsnummer instances.
	 *
	 * @param length
	 *            Specifies the number of Organisasjonsnummer instances to
	 *            create in the returned List
	 *
	 * @return A List with random valid Organisasjonsnummer instances
	 */
	public static List<Organisasjonsnummer> getOrganisasjonsnummerList(int length) {
		List<Organisasjonsnummer> result = new ArrayList<>();
		int numAddedToList = 0;
		while (numAddedToList < length) {
			StringBuilder orgnrBuffer = new StringBuilder(LENGTH);
			for (int i = 0; i < LENGTH; i++) {
				orgnrBuffer.append((int) (Math.random() * 10));
			}
			Organisasjonsnummer orgNr;
			try {
				orgNr = OrganisasjonsnummerValidator.getAndForceValidOrganisasjonsnummer(orgnrBuffer.toString());
			} catch (IllegalArgumentException iae) {
				// this number has no valid checksum
				continue;
			}
			result.add(orgNr);
			numAddedToList++;
		}
		return result;
	}

}
