package com.bookli.booking.dto.response;

import com.bookli.common.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingResponse {
  private Long id;
  private BookingStatus status;
}
