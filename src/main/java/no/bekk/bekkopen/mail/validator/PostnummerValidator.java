package no.bekk.bekkopen.mail.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.bekk.bekkopen.mail.MailValidator;
import no.bekk.bekkopen.mail.annotation.Postnummer;

/**
 * Validator av postnummer
 */
public class PostnummerValidator implements ConstraintValidator<Postnummer, String> {
    public void initialize(no.bekk.bekkopen.mail.annotation.Postnummer constraintAnnotation) {}

    public boolean isValid(String postnummer, ConstraintValidatorContext context) {
        if (postnummer == null) {
            return true;
        }

        return MailValidator.isValidPostnummer(postnummer);
    }
}
