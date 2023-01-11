package no.bekk.bekkopen.person.annotation;

/*-
 * #%L
 * NoCommons
 * %%
 * Copyright (C) 2014 - 2023 BEKK open source
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import no.bekk.bekkopen.person.FodselsnummerCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FodselsnummerAnnotationTest {

    private static Validator validator;

    @BeforeAll
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

    @Test
    public void should_accept_message_value(){
        Person k = new Person("1234");

        Set<ConstraintViolation<Person>> violations = validator.validate(k);

        assertEquals(Person.CUSTOM_MESSAGE, violations.iterator().next().getMessage());
    }
}

class Person {

    static final String CUSTOM_MESSAGE = "IKKE GYLDIG FØDSELSNUMMER";

    @Fodselsnummer(message = CUSTOM_MESSAGE)
    public String fnr;

    public Person(String s) {
        fnr = s;
    }
}
