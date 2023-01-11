package no.bekk.bekkopen.banking;

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
 * This class calculates valid Kontonummer instances.
 */
public class KontonummerCalculator {

	private static final int LENGTH = 11;
	private static final int REGISTERNUMMER_START_DIGIT = 0;
	private static final int ACCOUNTTYPE_START_DIGIT = 4;

	private KontonummerCalculator() {
		super();
	}

	private abstract static class KontonummerDigitGenerator {
		abstract String generateKontonummer();
	}

	private static List<Kontonummer> getKontonummerListUsingGenerator(KontonummerDigitGenerator generator, int length) {
		List<Kontonummer> result = new ArrayList<>();
		int numAddedToList = 0;
		while (numAddedToList < length) {
			Kontonummer kontoNr;
			try {
				kontoNr = KontonummerValidator.getAndForceValidKontonummer(generator.generateKontonummer());
			} catch (IllegalArgumentException iae) {
				// this number has no valid checksum
				continue;
			}
			result.add(kontoNr);
			numAddedToList++;
		}
		return result;
	}

	/**
	 * Returns a List with random but syntactically valid Kontonummer instances
	 * for a given AccountType.
	 *
	 * @param accountType
	 *            A String representing the AccountType to use for all
	 *            Kontonummer instances
	 * @param length
	 *            Specifies the number of Kontonummer instances to create in the
	 *            returned List
	 * @return A List with Kontonummer instances
	 */
	public static List<Kontonummer> getKontonummerListForAccountType(String accountType, int length) {
		KontonummerValidator.validateAccountTypeSyntax(accountType);
		final class AccountTypeKontonrDigitGenerator extends KontonummerDigitGenerator {
			private final String accountType;

			AccountTypeKontonrDigitGenerator(String accountType) {
				this.accountType = accountType;
			}

			@Override
			String generateKontonummer() {
				StringBuilder kontonrBuffer = new StringBuilder(LENGTH);
				for (int i = 0; i < LENGTH;) {
					if (i == ACCOUNTTYPE_START_DIGIT) {
						kontonrBuffer.append(accountType);
						i += accountType.length();
					} else {
						kontonrBuffer.append((int) (Math.random() * 10));
						i++;
					}
				}
				return kontonrBuffer.toString();
			}
		}
		return getKontonummerListUsingGenerator(new AccountTypeKontonrDigitGenerator(accountType), length);
	}

	/**
	 * Returns a List with random but syntactically valid Kontonummer instances
	 * for a given Registernummer.
	 *
	 * @param registernummer
	 *            A String representing the Registernummer to use for all
	 *            Kontonummer instances
	 * @param length
	 *            Specifies the number of Kontonummer instances to create in the
	 *            returned List
	 * @return A List with Kontonummer instances
	 */
	public static List<Kontonummer> getKontonummerListForRegisternummer(String registernummer, int length) {
		KontonummerValidator.validateRegisternummerSyntax(registernummer);
		final class RegisternummerKontonrDigitGenerator extends KontonummerDigitGenerator {
			private final String registerNr;

			RegisternummerKontonrDigitGenerator(String registerNr) {
				this.registerNr = registerNr;
			}

			@Override
			String generateKontonummer() {
				StringBuilder kontonrBuffer = new StringBuilder(LENGTH);
				for (int i = 0; i < LENGTH;) {
					if (i == REGISTERNUMMER_START_DIGIT) {
						kontonrBuffer.append(registerNr);
						i += registerNr.length();
					} else {
						kontonrBuffer.append((int) (Math.random() * 10));
						i++;
					}
				}
				return kontonrBuffer.toString();
			}
		}
		return getKontonummerListUsingGenerator(new RegisternummerKontonrDigitGenerator(registernummer), length);
	}

	/**
	 * Returns a List with completely random but syntactically valid Kontonummer
	 * instances.
	 *
	 * @param length
	 *            Specifies the number of Kontonummer instances to create in the
	 *            returned List
	 *
	 * @return A List with random valid Kontonummer instances
	 */
	public static List<Kontonummer> getKontonummerList(int length) {
		final class NormalKontonrDigitGenerator extends KontonummerDigitGenerator {
			@Override
			String generateKontonummer() {
				StringBuilder kontonrBuffer = new StringBuilder(LENGTH);
				for (int i = 0; i < LENGTH; i++) {
					kontonrBuffer.append((int) (Math.random() * 10));
				}
				return kontonrBuffer.toString();
			}
		}
		return getKontonummerListUsingGenerator(new NormalKontonrDigitGenerator(), length);
	}

}
