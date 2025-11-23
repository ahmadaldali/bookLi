package com.bookli.user.controller;

import com.bookli.booking.service.availability.AvailabilityUtils;
import com.bookli.user.dto.request.GetUserAvailabilityRequest;
import com.bookli.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/{id}/availability")
  public ResponseEntity<Map<LocalDate, List<AvailabilityUtils.TimeSlot>>> get(@PathVariable Long id, @Valid @RequestBody GetUserAvailabilityRequest request) {
    return ResponseEntity.ok(userService.getUserAvailability(id, request.getCalendarType()));
  }
}
