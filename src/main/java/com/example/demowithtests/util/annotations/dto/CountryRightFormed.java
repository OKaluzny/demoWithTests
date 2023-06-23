package com.example.demowithtests.util.annotations.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation verifies that the country is spelled correctly.
 * <p>
 * Condition: The string must be two characters long and be an uppercase.
 * <p>
 * Throws: MethodArgumentNotValidException if the string does not meet the conditions.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryRightFormedValidator.class)
public @interface CountryRightFormed {
    String message() default "Country must be a 2 characters length and uppercase. E.g. UK, CZ, UA";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
