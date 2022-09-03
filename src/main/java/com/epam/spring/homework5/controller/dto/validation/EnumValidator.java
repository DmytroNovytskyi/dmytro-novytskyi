package com.epam.spring.homework5.controller.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorConstraint.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnumValidator {

    String name();

    Class<? extends Enum<?>> enumClass();

    String message() default "{name} {common.enumValidator} {enumClass}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
