package com.project.management.common.validation.validator;


import com.project.management.user.service.PersonService;
import com.project.management.common.validation.annotation.PersonEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPersonEmail implements ConstraintValidator<PersonEmail, String> {

    private final PersonService personService;

    public ValidPersonEmail(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void initialize(PersonEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String personEmail, ConstraintValidatorContext context) {
        return !personService.isEmailExist(personEmail);
    }
}
