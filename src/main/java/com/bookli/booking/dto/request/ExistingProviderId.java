package com.bookli.booking.dto.request;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingProviderIdValidator.class)
@Documented
public @interface ExistingProviderId {
  String message() default "Provider ID does not exist or is not a provider";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
