package com.bookli.booking.service.availability;

import com.bookli.booking.entity.AvailabilityRule;
import com.bookli.booking.entity.Booking;
import com.bookli.booking.repository.AvailabilityRuleRepository;
import com.bookli.booking.service.BookingService;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

public abstract class AvailabilityCalculator {
  protected LocalDate startDate;
  protected LocalDate endDate;
  protected AvailabilityRuleRepository availabilityRuleRepository;
  protected BookingService bookingService;

  protected AvailabilityCalculator(AvailabilityRuleRepository availabilityRuleRepository, BookingService bookingService) {
    this.availabilityRuleRepository = availabilityRuleRepository;
    this.bookingService = bookingService;
  }

  protected abstract void initDates();

  protected Map<LocalDate, List<AvailabilityUtils.TimeSlot>> calculateAvailability(Long userId) {
    initDates();

    List<AvailabilityRule> rules = this.availabilityRuleRepository.findByUserIdAndActiveTrueAndRecurringTrue(userId);
    List<Booking> bookings = this.bookingService.fetchBookings(userId, this.startDate, this.endDate);

    Map<Integer, List<AvailabilityRule>> rulesByDay = AvailabilityUtils.groupRulesByDay(rules);
    Map<LocalDate, List<AvailabilityUtils.TimeSlot>> availabilityByDate = new HashMap<>();

    for (LocalDate date = this.startDate; !date.isAfter(this.endDate); date = date.plusDays(1)) {
      int dow = date.getDayOfWeek().getValue() % 7;
      List<AvailabilityUtils.TimeSlot> slots = AvailabilityUtils.createInitialSlots(rulesByDay.getOrDefault(dow, Collections.emptyList()));

      List<Booking> dayBookings = AvailabilityUtils.filterBookingsForDate(bookings, date);

      List<AvailabilityUtils.TimeSlot> freeSlots = AvailabilityUtils.subtractBookingsFromSlots(slots, dayBookings, date);
      List<AvailabilityUtils.TimeSlot> splitSlots = AvailabilityUtils.splitIntoIntervals(freeSlots, Duration.ofMinutes(30)); // TODO: read the duration from user/service

      availabilityByDate.put(date, splitSlots);
    }

    return availabilityByDate;
  }
}
