package com.yychainsaw.anno;

import com.yychainsaw.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(
        validatedBy = {StateValidation.class}
)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface State {

    String message() default "State must be either '已发布' or '草稿'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
