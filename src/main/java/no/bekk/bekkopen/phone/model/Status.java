package no.bekk.bekkopen.phone.model;

public enum Status {
    LEDIG,
    TILDELT,
    BLOKKERT;

    public static Status fromString(String status) {
        for (Status s : Status.values()) {
            if (s.name().equals(status)) {
                return s;
            }
        }
        return null;
    }
}
