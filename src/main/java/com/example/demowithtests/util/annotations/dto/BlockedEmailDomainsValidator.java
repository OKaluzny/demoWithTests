package com.example.demowithtests.util.annotations.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class BlockedEmailDomainsValidator implements ConstraintValidator<BlockedEmailDomains, String> {

    private String[] domains;
    private String[] endings;

    @Override
    public void initialize(BlockedEmailDomains constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        domains = constraintAnnotation.contains();
        endings = constraintAnnotation.endings();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email != null) {
            String emailDomain = email.substring(email.lastIndexOf('@') + 1);
            return Arrays.stream(endings).noneMatch(emailDomain::endsWith) &&
                    Arrays.stream(domains).noneMatch(emailDomain::contains);
        }
        return false;
    }

}
