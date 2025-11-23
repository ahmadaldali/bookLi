package com.bookli.booking.repository;

import com.bookli.booking.entity.AvailabilityRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailabilityRuleRepository extends JpaRepository<AvailabilityRule, Long> {
  List<AvailabilityRule> findByUserIdAndActiveTrueAndRecurringTrue(Long userId);
}

