package com.bookli.common.validator.provider;

import com.bookli.common.enums.UserRole;
import com.bookli.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistingProviderIdValidator implements ConstraintValidator<ExistingProviderId, Long> {

  private final UserRepository userRepository;

  @Override
  public boolean isValid(Long providerId, ConstraintValidatorContext context) {
    if (providerId == null) return false;

    return userRepository.findById(providerId).isPresent();
  }
}
