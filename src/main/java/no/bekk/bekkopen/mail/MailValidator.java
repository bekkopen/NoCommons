package no.bekk.bekkopen.mail;

import static no.bekk.bekkopen.common.HelperFunctions.distinctByKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import no.bekk.bekkopen.common.StringNumberValidator;
import no.bekk.bekkopen.mail.model.Kommunenavn;
import no.bekk.bekkopen.mail.model.Kommunenummer;
import no.bekk.bekkopen.mail.model.PostInfo;
import no.bekk.bekkopen.mail.model.Postnummer;
import no.bekk.bekkopen.mail.model.PostnummerKategori;
import no.bekk.bekkopen.mail.model.Poststed;

/**
 * Validates:
 * - Postnummer
 * - Poststed
 * - Kommunenummer
 * - Kommunenavn
 *
 * Lookup:
 * - Postnummer
 * - Poststed
 * - Kommunenummer
 * - Kommunenavn
 * - Postnummer kategori
 */
public class MailValidator extends StringNumberValidator {
    private static final int POSTNUMMER_LENGTH = 4;
    private static final int KOMMUNENUMMER_LENGTH = 4;

    private static Map<Postnummer, PostInfo> postInfo = new HashMap<>();

    public static Postnummer getPostnummer(String postnummer) {
        validatePostnummerSyntax(postnummer);
        return new Postnummer(postnummer);
    }

    public static Kommunenummer getKommunenummer(String kommunenummer) {
        validateKommunenummerSyntax(kommunenummer);
        return new Kommunenummer(kommunenummer);
    }

    public static PostnummerKategori getPostnummerKategori(String postnummerKategori) {
        return PostnummerKategori.fromString(postnummerKategori);
    }

    // Setup

    public static void setPostInfo(Map<Postnummer, PostInfo> postInfo) {
        MailValidator.postInfo = postInfo;
    }

    // Validation

    public static boolean isValidPostnummer(String postnummer) {
        try {
            MailValidator.getPostnummer(postnummer);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static void validatePostnummerSyntax(String postnummer) {
        validateLengthAndAllDigits(postnummer, POSTNUMMER_LENGTH);
    }

    public static boolean isValidKommunenummer(String kommunenummer) {
        try {
            MailValidator.getKommunenummer(kommunenummer);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static void validateKommunenummerSyntax(String kommunenummer) {
        validateLengthAndAllDigits(kommunenummer, KOMMUNENUMMER_LENGTH);
    }

    // Statistics

    public static int getAntallPoststed() {
        return (int) postInfo.entrySet().stream()
            .map(s -> s.getValue())
            .filter(distinctByKey(PostInfo::getPoststed))
            .count();
    }

    public static int getAntallPostnummer() {
        return postInfo.size();
    }

    public static int getAntallKommunenummer() {
        return (int) postInfo.entrySet().stream()
            .map(s -> s.getValue())
            .filter(distinctByKey(PostInfo::getKommunenummer))
            .count();
    }

    // Lookup - Postnummer

    public static PostInfo getPostInfoForPostnummer(String postnummer) {
        PostInfo result = null;
        Postnummer pn = getPostnummer(postnummer);

        if (postInfo.containsKey(pn)) {
            result = postInfo.get(pn);
        }

        return result;
    }

    public static Poststed getPoststedForPostnummer(String postnummer) {
        return getPostInfoForPostnummer(postnummer).getPoststed();
    }

    public static Kommunenavn getKommunenavnForPostnummer(String postnummer) {
        return getPostInfoForPostnummer(postnummer).getKommunenavn();
    }

    public static Kommunenummer getKommunenummerForPostnummer(String postnummer) {
        return getPostInfoForPostnummer(postnummer).getKommunenummer();
    }

    public static PostnummerKategori getPostnummerKategoriForPostnummer(String postnummer) {
        return getPostInfoForPostnummer(postnummer).getPostnummerKategori();
    }

    public static List<Postnummer> getPostnummerForPoststed(String poststed) {
        Poststed p = new Poststed(poststed);

        List<Postnummer> postnummerList =
            postInfo.entrySet().stream()
                .filter(a -> a.getValue().getPoststed().equals(p))
                .map(x -> x.getValue().getPostnummer())
                .collect(Collectors.toList());

        return (postnummerList == null ? new ArrayList<>() : postnummerList);
    }

    // Lookup - Kommunenummer / Kommunenavn

    public static Optional<PostInfo> getPostInfoForKommunenummer(String kommunenummer) {
        return postInfo.entrySet().stream()
            .map(s -> s.getValue())
            .filter(s -> s.getKommunenummer().toString().equals(kommunenummer))
            .findFirst();
    }

    public static Kommunenavn getKommunenavnForKommunenummer(String kommunenummer) {
        Optional<PostInfo> result = getPostInfoForKommunenummer(kommunenummer);

        return result.isPresent() ? result.get().getKommunenavn() : null;
    }

    public static Optional<PostInfo> getPostInfoForKommunenavn(String kommunenavn) {
        return postInfo.entrySet().stream()
            .map(s -> s.getValue())
            .filter(s -> s.getKommunenavn().toString().equalsIgnoreCase(kommunenavn))
            .findFirst();
    }

    public static Kommunenummer getKommunenummerForKommunenavn(String kommunenavn) {
        Optional<PostInfo> result = getPostInfoForKommunenavn(kommunenavn);

        return result.isPresent() ? result.get().getKommunenummer() : null;
    }
}
