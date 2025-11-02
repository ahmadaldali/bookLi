package com.bookli.booking.dto.request;

import com.bookli.common.enums.UserRole;
import com.bookli.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@RequiredArgsConstructor
public class ExistingProviderIdValidator implements ConstraintValidator<ExistingProviderId, Long> {

  private final UserRepository userRepository;

  @Override
  public boolean isValid(Long providerId, ConstraintValidatorContext context) {
    if (providerId == null) return false;
    return userRepository.findById(providerId)
      .map(user -> user.getRole() == UserRole.PROVIDER)
      .orElse(false);
  }
}
