package com.bookli.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserAvailabilityRequest {

  @NotNull(message = "{error.calendar_type.required}")
  private String calendarType;
}
