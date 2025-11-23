package com.bookli.booking.service.availability;

import com.bookli.booking.repository.AvailabilityRuleRepository;
import com.bookli.booking.service.BookingService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

@Service("weekly")
public class WeeklyAvailabilityCalculator extends AvailabilityCalculator {
  protected WeeklyAvailabilityCalculator(AvailabilityRuleRepository availabilityRuleRepository, BookingService bookingService) {
    super(availabilityRuleRepository, bookingService);
  }

  @Override
  public void initDates() {
    LocalDate now = LocalDate.now();
    Locale locale = Locale.getDefault();
    DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
    this.startDate = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
    this.endDate = startDate.plusDays(6);
  }
}

