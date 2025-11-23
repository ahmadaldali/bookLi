package com.bookli.booking.dto.response;

import com.bookli.common.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
  private Long id;
  private BookingStatus status;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
}

