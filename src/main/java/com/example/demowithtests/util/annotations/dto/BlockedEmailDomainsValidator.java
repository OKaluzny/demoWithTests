package com.example.demowithtests.util.annotations.dto;

import lombok.NonNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
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
            String emailDomain = email.substring(email.lastIndexOf('@') +1);
            return Arrays.stream(endings).noneMatch(emailDomain::endsWith) &&
                    Arrays.stream(domains).noneMatch(emailDomain::contains);
        }
        return false;
    }

    public static void main(String[] args) {
        String[] emails = {null, "yandru@yandex.ru", "yandua@yandex.ua", "mailru@mail.ru", "ukr@ukr.com", "vkua@vk.com", "ukr@ukr.рф"};
        String[] domains = {"yandex", "vk"};
        String[] endings = {".com1", ".ru", ".su", ".ру", ".рф"};
        for (String email : emails) {
            String emailDomain = null;
            boolean b = false;
            if (email != null) {
                emailDomain = email.substring(email.lastIndexOf('@') +1);
                b = Arrays.stream(endings).noneMatch(emailDomain::endsWith) &&
                        Arrays.stream(domains).noneMatch(emailDomain::contains);
            }
            System.out.println("Email: " + email + " | " + "Domain: " + emailDomain + " | " + "Boolean 1: " + b);// + " | " + "Boolean 2: " + b1);
//            boolean b1 = Arrays.stream(endings).noneMatch(emailDomain::endsWith);
        }



    }
}
