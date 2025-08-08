package com.project.management.common.validation.annotation;

import com.project.management.common.validation.validator.ValidPersonEmail;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidPersonEmail.class)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonEmail {

    String message() default "Ya existe una persona con el email ingresado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
