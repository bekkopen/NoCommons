package no.bekk.bekkopen.mail.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import no.bekk.bekkopen.mail.validator.PostnummerValidator;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, TYPE_USE, TYPE_PARAMETER, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = PostnummerValidator.class)
public @interface Postnummer {

    String message() default "Invalid postnummer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

