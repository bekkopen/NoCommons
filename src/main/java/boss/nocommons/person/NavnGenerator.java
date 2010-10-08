package boss.nocommons.person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NavnGenerator {

	private static final List<String> KVINNENAVN = csv2List(NavnGenerator.class
			.getResourceAsStream("/fornavn_kvinner.csv"));
	private static final List<String> MANNSNAVN = csv2List(NavnGenerator.class.getResourceAsStream("/fornavn_menn.csv"));
	private static final List<String> ETTERNAVN = csv2List(NavnGenerator.class.getResourceAsStream("/etternavn.csv"));

	public Navn genererMannsnavn() {
		return genererNavn(1, KJONN.MANN).get(0);
	}

	public Navn genererKvinnenavn() {
		return genererNavn(1, KJONN.KVINNE).get(0);
	}

	private List<Navn> genererNavn(final int antall, final KJONN kjonn) {
		List<Navn> mottakere = new ArrayList<Navn>(antall);
		KJONN kjonnSwitch = kjonn;
		while (mottakere.size() < antall) {
			if (KJONN.erBegge(kjonn)) {
				kjonnSwitch = KJONN.byttKjonn(kjonn);
			}
			Navn mottaker = genererNavn(kjonnSwitch);
			if (!mottakere.contains(mottaker)) {
				mottakere.add(mottaker);
			}
		}
		return mottakere;
	}

	private Navn genererNavn(final KJONN kjonn) {
		Random r1 = new Random();
		Random r2 = new Random();

		int indexF = 0;
		int indexL = r2.nextInt(ETTERNAVN.size() - 1);
		if (KJONN.erKvinne(kjonn)) {
			indexF = r1.nextInt(KVINNENAVN.size() - 1);
			return new Navn(KVINNENAVN.get(indexF), ETTERNAVN.get(indexL));
		} else {
			indexF = r1.nextInt(MANNSNAVN.size() - 1);
			return new Navn(MANNSNAVN.get(indexF), ETTERNAVN.get(indexL));
		}
	}

	private static List<String> csv2List(InputStream is) {
		InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);
		List<String> vList = new ArrayList<String>();
		String[] array;
		String line;
		try {
			while ((line = br.readLine()) != null) {
				array = line.split("[ ]*,[ ]*");
				for (int i = 0; i < array.length; i++) {
					vList.add(array[i]);
				}
			}
			br.close();
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return vList;
	}

	protected enum KJONN {
		MANN, KVINNE, BEGGE;

		static boolean erMann(final KJONN kjonn) {
			return kjonn.equals(MANN);
		}

		static boolean erKvinne(final KJONN kjonn) {
			return kjonn.equals(KVINNE);
		}

		static boolean erBegge(final KJONN kjonn) {
			return kjonn.equals(BEGGE);
		}

		static boolean erMannEllerKvinne(final KJONN kjonn) {
			return kjonn.equals(MANN) || kjonn.equals(KVINNE);
		}

		static KJONN byttKjonn(final KJONN kjonn) {
			if (erKvinne(kjonn)) {
				return MANN;
			}
			return KVINNE;
		}

	}

}
