package no.bekk.bekkopen.mail;

import no.bekk.bekkopen.mail.annotation.Postnummer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Bruker postnummer hentet fra: https://data.norge.no/data/posten-norge/postnummer-i-norge 
 */
public class PostnummerValidator implements ConstraintValidator<Postnummer, String> {

    public void initialize(no.bekk.bekkopen.mail.annotation.Postnummer constraintAnnotation) {}

    public boolean isValid(String postnummer, ConstraintValidatorContext context) {
        if(postnummer == null){
            return true;
        }

        return MailValidator.isValidPostnummer(postnummer);
    }

}
