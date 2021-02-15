package no.bekk.bekkopen.mail;

import static no.bekk.bekkopen.common.HelperFunctions.distinctByKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import no.bekk.bekkopen.common.StringNumberValidator;
import no.bekk.bekkopen.mail.model.Fylke;
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

    // Setup

    private static Map<Postnummer, PostInfo> postInfo = MailDataLoader.lesPostnummerFraCsvFil(
        MailValidator.class.getResourceAsStream("/postnummer.csv")
    );

    private static List<Fylke> fylker = MailDataLoader.lesFylkerFraCsvFil(
        MailValidator.class.getResourceAsStream("/fylker.csv")
    );

    /**
     * @deprecated 11.02.2021 The postnummer data is loaded automatically
     */
    @Deprecated
    public static void setPostInfo(Map<Postnummer, PostInfo> postInfo) {
        MailValidator.postInfo = postInfo;
    }

    // Model

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
            .map(pI -> pI.getValue())
            .filter(distinctByKey(PostInfo::getPoststed))
            .count();
    }

    public static int getAntallPostnummer() {
        return postInfo.size();
    }

    public static int getAntallKommunenummer() {
        return (int) postInfo.entrySet().stream()
            .map(pI -> pI.getValue())
            .filter(distinctByKey(PostInfo::getKommunenummer))
            .count();
    }

    public static int getAntallFylker() {
        return fylker.size();
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
        PostInfo postInfo = getPostInfoForPostnummer(postnummer);
        return postInfo != null ? postInfo.getKommunenummer() : null;
    }

    public static PostnummerKategori getPostnummerKategoriForPostnummer(String postnummer) {
        return getPostInfoForPostnummer(postnummer).getPostnummerKategori();
    }

    public static List<Postnummer> getPostnummerForPoststed(String poststed) {
        return getPostnummerForPoststed(new Poststed(poststed));
    }

    public static List<Postnummer> getPostnummerForPoststed(Poststed poststed) {
        List<Postnummer> postnummerList =
            postInfo.entrySet().stream()
                .filter(pI -> pI.getValue().getPoststed().equals(poststed))
                .map(pI -> pI.getValue().getPostnummer())
                .collect(Collectors.toList());

        return (postnummerList == null ? new ArrayList<>() : postnummerList);
    }

    // Lookup - Kommunenummer / Kommunenavn

    public static Optional<PostInfo> getPostInfoForKommunenummer(String kommunenummer) {
        return postInfo.entrySet().stream()
            .map(pI -> pI.getValue())
            .filter(pI -> pI.getKommunenummer().toString().equals(kommunenummer))
            .findFirst();
    }

    public static Kommunenavn getKommunenavnForKommunenummer(String kommunenummer) {
        Optional<PostInfo> result = getPostInfoForKommunenummer(kommunenummer);

        return result.isPresent() ? result.get().getKommunenavn() : null;
    }

    public static Optional<PostInfo> getPostInfoForKommunenavn(String kommunenavn) {
        return postInfo.entrySet().stream()
            .map(pI -> pI.getValue())
            .filter(pI -> pI.getKommunenavn().toString().equalsIgnoreCase(kommunenavn))
            .findFirst();
    }

    public static Kommunenummer getKommunenummerForKommunenavn(String kommunenavn) {
        Optional<PostInfo> result = getPostInfoForKommunenavn(kommunenavn);

        return result.isPresent() ? result.get().getKommunenummer() : null;
    }

    // Lookup - Fylke

    public static Fylke getFylkeForFylkesnummer(String fylkesnummer) {
        return fylker.stream()
            .filter(fylke -> fylke.getFylkesNummer().equals(fylkesnummer))
            .findAny().orElse(null);
    }

    public static Fylke getFylkeForFylkesnavn(String fylkesnavn) {
        return fylker.stream()
            .filter(fylke -> fylke.getFylkesNavn().equalsIgnoreCase(fylkesnavn))
            .findAny().orElse(null);
    }

    public static Fylke getFylkeForPostnummer(String postnummer) {
        return getFylkeForPostnummer(getPostnummer(postnummer));
    }

    public static Fylke getFylkeForPostnummer(Postnummer postnummer) {
        return getFylkeForKommunenummer(
            getKommunenummerForPostnummer(postnummer.getValue())
        );
    }

    public static Fylke getFylkeForPoststed(String poststed) {
        return getFylkeForPoststed(new Poststed(poststed));
    }

    public static Fylke getFylkeForPoststed(Poststed poststed) {
        Optional<Postnummer> postnummer = getPostnummerForPoststed(poststed).stream().findFirst();

        return postnummer.isPresent() ? getFylkeForPostnummer(postnummer.get()) : null;
    }

    public static Fylke getFylkeForKommunenummer(String kommunenummer) {
        return getFylkeForKommunenummer(getKommunenummer(kommunenummer));
    }

    public static Fylke getFylkeForKommunenummer(Kommunenummer kommunenummer) {
        if (kommunenummer != null && kommunenummer.getValue() != null) {
            String fylkesId = kommunenummer.getValue().substring(0, 2);

            return fylker.stream().filter(
                fylke -> fylke.getFylkesNummer().equals(fylkesId)
            ).findFirst().orElse(null);
        }

        return null;
    }
}
