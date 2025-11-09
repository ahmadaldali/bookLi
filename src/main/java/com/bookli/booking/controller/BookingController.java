package com.bookli.booking.controller;

import com.bookli.booking.dto.request.CreateBookingRequest;
import com.bookli.booking.dto.response.BookingResponse;
import com.bookli.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  @PostMapping("")
  public ResponseEntity<BookingResponse> create(@Valid @RequestBody CreateBookingRequest request) {
    return ResponseEntity.ok(bookingService.createBooking(request.getProviderId(), request.getStartTime(), request.getEndTime()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookingResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(bookingService.getBooking(id));
  }
}
