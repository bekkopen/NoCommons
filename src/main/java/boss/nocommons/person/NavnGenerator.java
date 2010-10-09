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

	private final int CA_ANTALL_KVINNER_SOM_HAR_MELLOMNAVN_I_PROSENT = 22;
	private final int CA_ANTALL_MENN_SOM_HAR_MELLOMNAVN_I_PROSENT = 14;

	private final List<String> KVINNENAVN;
	private final List<String> MANNSNAVN;
	private final List<String> ETTERNAVN;

	public NavnGenerator() {
		KVINNENAVN = csv2List(NavnGenerator.class.getResourceAsStream("/fornavn_kvinner.csv"));
		MANNSNAVN = csv2List(NavnGenerator.class.getResourceAsStream("/fornavn_menn.csv"));
		ETTERNAVN = csv2List(NavnGenerator.class.getResourceAsStream("/etternavn.csv"));

	}

	public Navn genererMannsnavn() {
		return genererNavn(1, KJONN.MANN).get(0);
	}

	public Navn genererKvinnenavn() {
		return genererNavn(1, KJONN.KVINNE).get(0);
	}

	public List<Navn> genererMannsnavn(int antall) {
		return genererNavn(antall, KJONN.MANN);
	}

	public List<Navn> genererKvinnenavn(int antall) {
		return genererNavn(antall, KJONN.KVINNE);
	}

	private List<Navn> genererNavn(final int antall, final KJONN kjonn) {
		List<Navn> navneliste = new ArrayList<Navn>(antall);
		KJONN kjonnSwitch = kjonn;
		while (navneliste.size() < antall) {
			if (KJONN.erBegge(kjonn)) {
				kjonnSwitch = KJONN.byttKjonn(kjonn);
			}
			Navn mottaker = genererNavn(kjonnSwitch);
			if (!navneliste.contains(mottaker)) {
				navneliste.add(mottaker);
			}
		}
		return navneliste;
	}

	private Navn genererNavn(final KJONN kjonn) {
		String fornavn, mellomnavn = null, etternavn;

		int indexF = 0;
		if (KJONN.erKvinne(kjonn)) {
			indexF = new Random().nextInt(KVINNENAVN.size() - 1);
			fornavn = KVINNENAVN.get(indexF);
		} else {
			indexF = new Random().nextInt(MANNSNAVN.size() - 1);
			fornavn = MANNSNAVN.get(indexF);
		}
		if (genererMellomnavn(kjonn)) {
			int indexM = new Random().nextInt(ETTERNAVN.size() - 1);
			mellomnavn = ETTERNAVN.get(indexM);
		}
		int indexE = new Random().nextInt(ETTERNAVN.size() - 1);
		etternavn = ETTERNAVN.get(indexE);
		return new Navn(fornavn, mellomnavn, etternavn);
	}

	private boolean genererMellomnavn(KJONN kjonn) {
		Random r = new Random();
		if (KJONN.KVINNE.equals(kjonn)) {
			if (r.nextInt(100) <= CA_ANTALL_KVINNER_SOM_HAR_MELLOMNAVN_I_PROSENT) {
				return true;
			}
		} else {
			if (r.nextInt(100) <= CA_ANTALL_MENN_SOM_HAR_MELLOMNAVN_I_PROSENT) {
				return true;
			}
		}
		return false;
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
