package no.bekk.bekkopen.org.annotation;

import no.bekk.bekkopen.org.OrganisasjonsnummerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = OrganisasjonsnummerValidator.class)
public @interface Organisasjonsnummer {

    String message() default "Invalid organisasjonsnummer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
