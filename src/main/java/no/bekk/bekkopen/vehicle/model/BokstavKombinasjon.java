package no.bekk.bekkopen.vehicle.model;

public class BokstavKombinasjon {
    private final String bokstavKombinasjon;

    public BokstavKombinasjon(String bokstavKombinasjon) {
        this.bokstavKombinasjon = bokstavKombinasjon;
    }

    public String getBokstavKombinasjon() {
        return bokstavKombinasjon;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bokstavKombinasjon == null) ? 0 : bokstavKombinasjon.hashCode());
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
        final BokstavKombinasjon other = (BokstavKombinasjon) obj;
        if (bokstavKombinasjon == null) {
            if (other.bokstavKombinasjon != null) {
                return false;
            }
        } else if (!bokstavKombinasjon.equals(other.bokstavKombinasjon)) {
            return false;
        }
        return true;
    }
}
