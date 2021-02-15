package no.bekk.bekkopen.vehicle.model;

public class Trafikkstasjon {
    private final String fylke;
    private final String geografiskOmråde;

    public Trafikkstasjon(String geografiskOmråde, String fylke) {
        this.geografiskOmråde = geografiskOmråde;
        this.fylke = fylke;
    }

    public String getFylke() {
        return fylke;
    }

    public String getGeografiskOmråde() {
        return geografiskOmråde;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fylke == null) ? 0 : fylke.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Trafikkstasjon other = (Trafikkstasjon) obj;
        boolean equals = true;
        
        if (fylke == null) {
            if (other.fylke != null) {
                equals = false;
            }
        } else if (!fylke.equals(other.fylke)) {
            equals = false;
        }

        if (geografiskOmråde == null) {
            if (other.geografiskOmråde != null) {
                equals = false;
            }
        } else if (!geografiskOmråde.equals(other.geografiskOmråde)) {
            equals = false;
        }

        return equals;
    }
}
