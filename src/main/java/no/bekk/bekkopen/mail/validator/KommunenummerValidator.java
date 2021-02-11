package no.bekk.bekkopen.mail.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.bekk.bekkopen.mail.MailValidator;
import no.bekk.bekkopen.mail.annotation.Kommunenummer;

/**
 * Validering av kommunenummer
 */
public class KommunenummerValidator implements ConstraintValidator<Kommunenummer, String> {

    public void initialize(Kommunenummer constraintAnnotation) {}

    public boolean isValid(String kommunenummer, ConstraintValidatorContext context) {
        if (kommunenummer == null) {
            return true;
        }

        return MailValidator.isValidKommunenummer(kommunenummer);
    }

}
