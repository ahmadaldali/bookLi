package com.bookli.booking.dto.request;


import com.bookli.common.validator.provider.ExistingProviderId;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookingRequest {

  @NotNull(message = "Start time is required")
  private LocalDateTime startTime;

  @NotNull(message = "End time is required")
  private LocalDateTime endTime;

  @ExistingProviderId(message = "{error.provider.notfound}")
  @NotNull(message = "{error.provider.required}")
  private Long providerId;
}
