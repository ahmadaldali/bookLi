package com.bookli.booking.controller;

import com.bookli.booking.dto.request.CreateBookingRequest;
import com.bookli.booking.dto.response.BookingResponse;
import com.bookli.booking.service.BookingService;
import com.bookli.common.dto.SuccessResponse;
import com.bookli.user.service.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
  private final BookingService bookingService;

  @PostMapping("")
  public ResponseEntity<BookingResponse> create(@Valid @RequestBody CreateBookingRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(bookingService.createBooking(request.getProviderId(), request.getStartTime(), request.getEndTime(), userDetails.getUserId()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookingResponse> get(@PathVariable Long id) {
    return ResponseEntity.ok(bookingService.getBooking(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<SuccessResponse> delete(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
    return ResponseEntity.ok(bookingService.deleteBooking(id,userDetails.getUserId()));
  }
}
