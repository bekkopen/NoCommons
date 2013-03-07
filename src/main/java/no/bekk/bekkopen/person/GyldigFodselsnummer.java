package no.bekk.bekkopen.person;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = FodselsnummerValidator.class)
public @interface GyldigFodselsnummer {

    String message() default "Invalid fødselsnummer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
