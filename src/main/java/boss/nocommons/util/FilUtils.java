package boss.nocommons.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;

public final class FilUtils extends FileUtils {

	private FilUtils() {
	}

	public static String[] csv2StringArray(final File csvfil) {
		try {
			return readFileToString(csvfil, "UTF-8").split("[ ]*,[ ]*");
		} catch (IOException e) {
			throw new RuntimeException("Klarte ikke lese fil: " + csvfil.getName(), e);
		}
	}

	public static File hentFilFraClasspath(final Class<?> clazz, final String filnavn) {
		URI uri;
		try {
			uri = new URI(clazz.getResource(filnavn).toString());
		} catch (URISyntaxException e) {
			throw new RuntimeException("Feil syntaks på URI: " + clazz.getResource(filnavn).toString(), e);
		}
		return new File(uri.getPath());
	}
}
