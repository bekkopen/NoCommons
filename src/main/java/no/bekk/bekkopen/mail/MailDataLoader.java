package no.bekk.bekkopen.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * This class loads data about Postnummer and Poststed into memory. The class
 * provides a method that loads data from a file included in the jar, and
 * another method that loads data from a given InputStream.
 */
public class MailDataLoader {

	private MailDataLoader() {
		super();
	}

	public static void loadFromInputStream(InputStream is) throws IOException {
		if (is == null) {
			throw new IllegalArgumentException();
		}
		Map<Poststed, List<Postnummer>> poststedMap = new HashMap<Poststed, List<Postnummer>>();
		Map<Postnummer, Poststed> postnummerMap = new HashMap<Postnummer, Poststed>();
		InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "\t", false);
			Postnummer pn = MailValidator.getPostnummer(st.nextToken());
			Poststed ps = new Poststed(st.nextToken());

			// add to poststedMap
			List<Postnummer> postnummerList = new ArrayList<Postnummer>();
			if (poststedMap.containsKey(ps)) {
				postnummerList = poststedMap.get(ps);
			}
			if (!postnummerList.contains(pn)) {
				postnummerList.add(pn);
			}
			poststedMap.put(ps, postnummerList);

			// add to postnummerMap
			if (!postnummerMap.containsKey(pn)) {
				postnummerMap.put(pn, ps);
			}
		}
		br.close();
		isr.close();
		MailValidator.setPoststedMap(poststedMap);
		MailValidator.setPostnummerMap(postnummerMap);
	}

	public static boolean loadFromClassPath() {
		boolean success = false;
		InputStream is = null;
		try {
			is = MailDataLoader.class.getResourceAsStream("/tilbud5.txt");
			loadFromInputStream(is);
			success = true;
		} catch (IOException e) {
			// ignore
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// ignore
			}
		}
		return success;
	}

}
