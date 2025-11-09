package com.bookli.booking.service;

import com.bookli.booking.dto.response.BookingResponse;
import com.bookli.booking.entity.Booking;

import com.bookli.booking.repository.BookingRepository;
import com.bookli.common.enums.BookingStatus;
import com.bookli.common.exception.AppException;
import com.bookli.user.entity.User;
import com.bookli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

  private final UserRepository userRepository;
  private final BookingRepository bookingRepository;

  private static final List<BookingStatus> ACTIVE_STATUSES = List.of(BookingStatus.PENDING, BookingStatus.BOOKED);

  public BookingResponse createBooking(Long providerId,
                               LocalDateTime start, LocalDateTime end) {

    User provider = userRepository.findById(providerId)
      .orElseThrow(() -> new AppException("error.provider.notfound"));

    validateBookingTimes(start, end);

    boolean hasOverlap = bookingRepository.existsOverlappingBooking(
      provider.getId(),
      start,
      end,
      ACTIVE_STATUSES
    );

    if (hasOverlap) {
      throw new AppException("error.booking.slot_overlapping");
    }

    Booking booking = Booking.builder()
      .provider(provider)
      .startTime(start)
      .endTime(end)
      .status(BookingStatus.BOOKED)
      .build();

    Booking savedBooking = bookingRepository.save(booking);

    return new BookingResponse(savedBooking.getId(), booking.getStatus());
  }

  public BookingResponse getBooking(Long id) {
    Booking booking = bookingRepository.findById(id)
      .orElseThrow(() -> new AppException("error.booking.notfound"));

    return new BookingResponse(booking.getId(), booking.getStatus());
  }

  // ---------------- Helper Methods ----------------

  /**
   * Validates that the booking start time is before end time
   */
  private void validateBookingTimes(LocalDateTime start, LocalDateTime end) {
    if (start == null || end == null) {
      throw new IllegalArgumentException("Start time and end time must not be null.");
    }
    if (!start.isBefore(end)) {
      throw new AppException("error.booking.times_overlapping");
    }
  }

}
