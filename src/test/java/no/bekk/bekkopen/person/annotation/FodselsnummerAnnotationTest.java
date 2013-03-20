package no.bekk.bekkopen.person.annotation;

import no.bekk.bekkopen.person.FodselsnummerCalculator;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class FodselsnummerAnnotationTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void should_not_validate_invalid_string(){
        Person k = new Person("1234");

        Set<ConstraintViolation<Person>> violations = validator.validate(k);

        assertEquals(1, violations.size());
    }

    @Test
    public void should_accept_null_values(){
        Person k = new Person(null);

        Set<ConstraintViolation<Person>> violations = validator.validate(k);

        assertEquals(0, violations.size());
    }

    @Test
    public void should_validate_valid_fodselsnummer(){
        Person k = new Person(FodselsnummerCalculator.getFodselsnummerForDate(new Date()).toString());

        Set<ConstraintViolation<Person>> violations = validator.validate(k);

        assertEquals(0, violations.size());
    }
}

 class Person {

    @Fodselsnummer
    public String fnr;

    public Person(String s) {
        fnr = s;
    }
}
