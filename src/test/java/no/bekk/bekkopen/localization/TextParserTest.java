package no.bekk.bekkopen.localization;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextParserTest {
	private final String NORWEGIAN_TEXT = "Der bode en underlig gr\u00E5spr\u00E6ngt en\tp\u00E5 den yderste n\u00F8gne \u00F8\n. \u00C5\u00E5\u00C6\u00E6\u00D8\u00F8";
	private final String NORWEGIAN_TEXT_WITH_REPLACEMENTS = "Der bode en underlig graaspraengt en\tpaa den yderste noegne oe\n. AaaaAeaeOeoe";

	@Test
	public void testReplaceNorwegianLetters() {
		Locale.setDefault(new Locale("no", "NO"));
		assertEquals(NORWEGIAN_TEXT_WITH_REPLACEMENTS, TextParser.replaceNorwegianLetters(NORWEGIAN_TEXT));
	}

}
