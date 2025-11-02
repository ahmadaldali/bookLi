package com.bookli.booking.controller;

import com.bookli.booking.dto.request.CreateBookingRequest;
import com.bookli.booking.dto.response.BookingResponse;
import com.bookli.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  @PostMapping("")
  public ResponseEntity<BookingResponse> create(@Valid @RequestBody CreateBookingRequest request) {
    return ResponseEntity.ok(bookingService.CreateBooking(request));
  }
}
