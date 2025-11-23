package com.bookli.booking.service;

import com.bookli.booking.dto.response.BookingResponse;
import com.bookli.booking.entity.Booking;

import com.bookli.booking.repository.BookingRepository;
import com.bookli.common.dto.SuccessResponse;
import com.bookli.common.enums.BookingStatus;
import com.bookli.common.exception.UnAuthorizedException;
import com.bookli.common.exception.ValidationException;
import com.bookli.user.entity.User;
import com.bookli.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
  @PersistenceContext
  private EntityManager entityManager;
  private final UserRepository userRepository;
  private final BookingRepository bookingRepository;

  private static final List<BookingStatus> ACTIVE_STATUSES = List.of(BookingStatus.PENDING, BookingStatus.BOOKED);

  public BookingResponse createBooking(Long providerId, LocalDateTime start, LocalDateTime end, Long userId) {

    validateProvider(userId, providerId);
    validateBookingTimes(start, end);

    boolean hasOverlap = bookingRepository.existsOverlappingBooking(providerId, start, end, ACTIVE_STATUSES);

    if (hasOverlap) {
      throw new ValidationException("error.booking.slot_overlapping");
    }

    Booking bookingBuilder = Booking.builder().provider(entityManager.getReference(User.class, providerId)).startTime(start).endTime(end).status(BookingStatus.BOOKED).build();

    Booking booking = bookingRepository.save(bookingBuilder);

    return BookingResponse.builder().id(booking.getId()).status(booking.getStatus()).startTime(booking.getStartTime()).endTime(booking.getEndTime()).build();
  }

  public BookingResponse getBooking(Long id, Long userId) {
    Booking booking = getUserBooking(id, userId);

    return BookingResponse.builder().id(booking.getId()).status(booking.getStatus()).startTime(booking.getStartTime()).endTime(booking.getEndTime()).build();
  }

  public SuccessResponse deleteBooking(Long id, Long userId) {
    Booking booking = getUserBooking(id, userId);
    bookingRepository.delete(booking);

    return new SuccessResponse("");
  }


  // ---------------- Helper Methods ----------------

  public List<Booking> fetchBookings(Long userId, LocalDate startDate, LocalDate endDate) {
    return bookingRepository.findByProviderIdAndStartTimeBetweenOrEndTimeBetween(userId, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX), startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
  }

  /**
   * Validates that you can't book for another user (for now only limited for yourself only)
   */
  private void validateProvider(Long userId, Long providerId) {
    if (!userId.equals(providerId)) {
      throw new UnAuthorizedException();
    }
  }

  /**
   * Validates that the booking start time is before end time
   */
  private void validateBookingTimes(LocalDateTime start, LocalDateTime end) {
    if (start == null || end == null) {
      throw new ValidationException("error.booking.times_required");
    }

    LocalDateTime now = LocalDateTime.now();
    if (!start.isAfter(now) || !end.isAfter(now)) {
      throw new ValidationException("error.booking.times_must_be_in_future");
    }

    if (!start.isBefore(end)) {
      throw new ValidationException("error.booking.times_overlapping");
    }
  }

  /**
   * Get a user booking
   */
  private Booking getUserBooking(Long bookingId, Long userId) {
    Booking booking = bookingRepository.findById(bookingId).orElseThrow(EntityNotFoundException::new);

    validateProvider(userId, booking.getProvider().getId());

    return booking;
  }
}
