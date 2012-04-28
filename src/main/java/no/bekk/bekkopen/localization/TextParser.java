package no.bekk.bekkopen.localization;

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
		replacements = new HashMap<String, String>();
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
