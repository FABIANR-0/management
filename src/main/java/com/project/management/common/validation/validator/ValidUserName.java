package com.project.management.common.validation.validator;


import com.project.management.user.service.UserService;
import com.project.management.common.validation.annotation.UserName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidUserName implements ConstraintValidator<UserName, String> {

    private final UserService userService;

    public ValidUserName(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UserName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
        return !userService.existsByName(userName);
    }
}
