package com.bookli.booking.service.availability;

import com.bookli.booking.entity.AvailabilityRule;
import com.bookli.booking.entity.Booking;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AvailabilityUtils {

  public static boolean intervalsOverlapDate(LocalDateTime start, LocalDateTime end, LocalDate date) {
    LocalDateTime dayStart = date.atStartOfDay();
    LocalDateTime dayEnd = date.atTime(LocalTime.MAX);

    return !(end.isBefore(dayStart) || start.isAfter(dayEnd));
  }

  public static List<TimeSlot> subtractBookingsFromSlots(List<TimeSlot> slots, List<Booking> bookings, LocalDate date) {
    List<TimeSlot> freeSlots = new ArrayList<>(slots);
    for (Booking booking : bookings) {
      LocalTime bookingStart = booking.getStartTime().toLocalDate().isBefore(date) ? LocalTime.MIN : booking.getStartTime().toLocalTime();
      LocalTime bookingEnd = booking.getEndTime().toLocalDate().isAfter(date) ? LocalTime.MAX : booking.getEndTime().toLocalTime();

      List<TimeSlot> updatedSlots = new ArrayList<>();
      for (TimeSlot slot : freeSlots) {
        LocalTime slotStart = slot.startTime();
        LocalTime slotEnd = slot.endTime();
        if (bookingEnd.isBefore(slotStart) || bookingStart.isAfter(slotEnd)) {
          updatedSlots.add(slot);
        } else {
          if (bookingStart.isAfter(slotStart)) {
            updatedSlots.add(new TimeSlot(slotStart, bookingStart));
          }
          if (bookingEnd.isBefore(slotEnd)) {
            updatedSlots.add(new TimeSlot(bookingEnd, slotEnd));
          }
        }
      }
      freeSlots = updatedSlots;
    }
    return freeSlots;
  }

  public static List<TimeSlot> splitIntoIntervals(List<TimeSlot> slots, Duration interval) {
    List<TimeSlot> result = new ArrayList<>();
    for (TimeSlot slot : slots) {
      LocalTime start = slot.startTime();
      LocalTime end = slot.endTime();
      while (!start.plus(interval).isAfter(end)) {
        result.add(new TimeSlot(start, start.plus(interval)));
        start = start.plus(interval);
      }
    }
    return result;
  }

  public static Map<Integer, List<AvailabilityRule>> groupRulesByDay(List<AvailabilityRule> rules) {
    return rules.stream().collect(Collectors.groupingBy(AvailabilityRule::getDayOfWeek));
  }

  public static List<AvailabilityUtils.TimeSlot> createInitialSlots(List<AvailabilityRule> dayRules) {
    List<AvailabilityUtils.TimeSlot> slots = new ArrayList<>();
    for (AvailabilityRule rule : dayRules) {
      slots.add(new AvailabilityUtils.TimeSlot(rule.getStartTime(), rule.getEndTime()));
    }

    return slots;
  }

  public static List<Booking> filterBookingsForDate(List<Booking> bookings, LocalDate date) {
    return bookings.stream().filter(b -> AvailabilityUtils.intervalsOverlapDate(b.getStartTime(), b.getEndTime(), date)).collect(Collectors.toList());
  }

  public record TimeSlot(LocalTime startTime, LocalTime endTime) {
  }
}
