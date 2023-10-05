package com.clearsolutions.oleksiytest.annotation;

import com.clearsolutions.oleksiytest.exception.ValidAgeException;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {
    String message() default "Invalid age";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long minimumAge();
}

class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

    private long minimumAge;

    @Override
    public void initialize(ValidAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.minimumAge = constraintAnnotation.minimumAge();
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return false;
        }

        long age = ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
        if (age >= minimumAge) {
            return true;
        }

        throw new ValidAgeException("Age must be at least 18 years old");
    }
}