package com.bookli.booking.repository;

import com.bookli.booking.entity.Booking;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
  @Query("""
    SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END
    FROM Booking b
    WHERE b.provider.id = :providerId
    AND b.status IN :statuses
    AND b.startTime < :end
    AND b.endTime > :start
""")
  boolean existsOverlappingBooking(
    @Param("providerId") Long providerId,
    @Param("start") LocalDateTime start,
    @Param("end") LocalDateTime end,
    @Param("statuses") java.util.List<com.bookli.common.enums.BookingStatus> statuses
  );

  Optional<Booking> findByIdAndProviderId(Long id, Long providerId);

  List<Booking> findByProviderIdAndStartTimeBetweenOrEndTimeBetween(Long providerId, LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2);
}
