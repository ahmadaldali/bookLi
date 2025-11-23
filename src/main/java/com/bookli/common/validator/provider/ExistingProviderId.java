package com.bookli.common.validator.provider;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingProviderIdValidator.class)
@Documented
public @interface ExistingProviderId {
  String message() default "{error.provider.notfound}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
