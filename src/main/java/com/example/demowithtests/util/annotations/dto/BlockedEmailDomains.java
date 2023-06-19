package com.example.demowithtests.util.annotations.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * In the annotation field {@code contains}, you can pass
 * an array of strings with the values of forbidden domains.
 * <p>
 * Condition: The email string should not contain the domains
 * listed in {@code contains}.
 * <p>
 * Throws: MethodArgumentNotValidException if the string does
 * not meet the conditions.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BlockedEmailDomainsValidator.class)
public @interface BlockedEmailDomains {
    String message() default "This email domain is banned";

    String[] contains() default {".ru", ".su"};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
