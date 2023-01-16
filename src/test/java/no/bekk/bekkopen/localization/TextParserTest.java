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
