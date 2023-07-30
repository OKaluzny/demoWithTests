package com.example.demowithtests.util.annotations.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryRightFormedValidator implements ConstraintValidator<CountryRightFormed, String> {

    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        if (country == null)
            return true;
        return country.length() == 2 && country.equals(country.toUpperCase());
    }
}
