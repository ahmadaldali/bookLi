package com.bookli.booking.service.availability;

import com.bookli.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

  private final Map<String, AvailabilityCalculator> calculators;

  public Map<LocalDate, List<AvailabilityUtils.TimeSlot>> getAvailability(Long userId, String calendarType) {
    AvailabilityCalculator calculator = calculators.get(calendarType);
    if (calculator == null) {
      throw new ValidationException("error.user.availability.unknown");
    }

    return calculator.calculateAvailability(userId);
  }
}

