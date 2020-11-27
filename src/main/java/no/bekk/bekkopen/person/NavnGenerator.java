package no.bekk.bekkopen.person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Generator for Norwegian names - a {@link Navn}.
 *
 * Picks random names from the most common names in 2009.
 *
 * <h2>Sources:</h2>
 * <ul>
 * <li><a href="http://www.ssb.no/navn/fornavn-kvinner-200.html">Statistisk
 * Sentralbyrås liste over de 712 mest brukte kvinnenavn i 2009</a></li>
 * <li><a href="http://www.ssb.no/navn/fornavn-menn-200.html">Statistisk
 * Sentralbyrås liste over de 580 mest brukte mannsnavn i 2009</a></li>
 * <li><a href="http://www.ssb.no/navn/etternavn-200.html">Statistisk
 * Sentralbyrås liste over de 3229 mest brukte etternavn i 2009</a></li>
 * </ul>
 *
 */

public class NavnGenerator {

	private final static int caAntallKvinnerSomHarMellomnavnIProsent = 22;
	private final static int caAntallMennSomHarMellomnavnIProsent = 14;

	private final static List<String> kvinnenavn = csv2List(NavnGenerator.class
			.getResourceAsStream("/fornavn_kvinner.csv"));
	private final static List<String> mannsnavn = csv2List(NavnGenerator.class.getResourceAsStream("/fornavn_menn.csv"));
	private final static List<String> etternavn = csv2List(NavnGenerator.class.getResourceAsStream("/etternavn.csv"));

	private NavnGenerator() {
		super();
	}

	public static Navn genererMannsnavn() {
		return genererNavn(1, KJONN.MANN).get(0);
	}

	public static Navn genererKvinnenavn() {
		return genererNavn(1, KJONN.KVINNE).get(0);
	}

	public static List<Navn> genererMannsnavn(int antall) {
		return genererNavn(antall, KJONN.MANN);
	}

	public static List<Navn> genererKvinnenavn(int antall) {
		return genererNavn(antall, KJONN.KVINNE);
	}

	public static List<Navn> genererNavn(int antall) {
		return genererNavn(antall, KJONN.BEGGE);
	}

	private static List<Navn> genererNavn(final int antall, final KJONN kjonn) {
		List<Navn> navneliste = new ArrayList<>(antall);
		KJONN kjonnSwitch = kjonn;
		while (navneliste.size() < antall) {
			if (KJONN.erBegge(kjonn)) {
				kjonnSwitch = KJONN.byttKjonn(kjonn);
			}
			Navn navn = genererNavn(kjonnSwitch);
			if (!navneliste.contains(navn)) {
				navneliste.add(navn);
			}
		}
		return navneliste;
	}

	private static Navn genererNavn(final KJONN kjonn) {
		String fnavn, mnavn = null, enavn;
		int indexF;
		if (KJONN.erKvinne(kjonn)) {
			indexF = new Random().nextInt(kvinnenavn.size() - 1);
			fnavn = kvinnenavn.get(indexF);
		} else {
			indexF = new Random().nextInt(mannsnavn.size() - 1);
			fnavn = mannsnavn.get(indexF);
		}
		if (genererMellomnavn(kjonn)) {
			int indexM = new Random().nextInt(etternavn.size() - 1);
			mnavn = etternavn.get(indexM);
		}
		int indexE = new Random().nextInt(etternavn.size() - 1);
		enavn = etternavn.get(indexE);
		return new Navn(fnavn, mnavn, enavn);
	}

	private static boolean genererMellomnavn(KJONN kjonn) {
		if (KJONN.erKvinne(kjonn)) {
			if (new Random().nextInt(100) <= caAntallKvinnerSomHarMellomnavnIProsent) {
				return true;
			}
		} else {
			if (new Random().nextInt(100) <= caAntallMennSomHarMellomnavnIProsent) {
				return true;
			}
		}
		return false;
	}

	private static List<String> csv2List(InputStream is) {
		InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);
		List<String> vList = new ArrayList<>();
		String[] array;
		String line;
		try {
			while ((line = br.readLine()) != null) {
				array = line.split("[ ]*,[ ]*");
            Collections.addAll(vList, array);
			}
			br.close();
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vList;
	}
}
