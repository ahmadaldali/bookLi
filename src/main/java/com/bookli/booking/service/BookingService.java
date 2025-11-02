package com.bookli.booking.service;

import com.bookli.booking.dto.request.CreateBookingRequest;
import com.bookli.booking.dto.response.BookingResponse;
import com.bookli.booking.entity.Booking;

import com.bookli.booking.repository.BookingRepository;
import com.bookli.common.enums.BookingStatus;
import com.bookli.common.exception.AppException;
import com.bookli.user.entity.User;
import com.bookli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {
  private final UserRepository userRepository;
  private final BookingRepository bookingRepository;

  public BookingResponse CreateBooking(CreateBookingRequest request) {
    User provider = userRepository.findById(request.getProviderId())
      .orElseThrow(() -> new AppException("Provider not found"));

    Booking booking = Booking.builder()
      .provider(provider)
      .startTime(request.getStartTime())
      .endTime(request.getEndTime())
      .status(BookingStatus.BOOKED)
      .build();

    Booking savedBooking = bookingRepository.save(booking);

    return new BookingResponse( savedBooking.getId() );
  }
}
