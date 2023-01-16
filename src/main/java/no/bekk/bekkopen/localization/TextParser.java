package no.bekk.bekkopen.localization;

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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides utilities useful when working with text written in
 * Norwegian - usually related to the characters 'æ', 'ø' and 'å'.
 */
public class TextParser {

	private final static Map<String, String> replacements;
	static {
		replacements = new HashMap<>();
		replacements.put("\u00F8", "oe");
		replacements.put("\u00D8", "Oe");
		replacements.put("\u00E6", "ae");
		replacements.put("\u00C6", "Ae");
		replacements.put("\u00E5", "aa");
		replacements.put("\u00C5", "Aa");
	}

	private TextParser() {
		super();
	}

	/**
	 * Replaces the norwegian letters 'æ/Æ', 'ø/Ø' and 'å/Å' with their English
	 * substitutes ae/Ae, oe/Oe and aa/Aa.
	 *
	 * @param text
	 *            The text containing the Norwegian letters to replace
	 * @return A text with all Norwegian letters replaced by their respective
	 *         substitutes.
	 */
	public static String replaceNorwegianLetters(CharSequence text) {
		Pattern norwegianLettersPattern = Pattern.compile("\u00F8|\u00D8|\u00E6|\u00C6|\u00E5|\u00C5");
		Matcher norwegianLetterMatcher = norwegianLettersPattern.matcher(text);
		StringBuffer replacedText = new StringBuffer();
		while (norwegianLetterMatcher.find()) {
			norwegianLetterMatcher.appendReplacement(replacedText, replacements.get(norwegianLetterMatcher.group()));
		}
		norwegianLetterMatcher.appendTail(replacedText);
		return replacedText.toString();
	}

}
