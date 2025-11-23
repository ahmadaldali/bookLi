package com.bookli.booking.entity;

import com.bookli.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;

@Entity
@Table(name = "availability_rules")
public class AvailabilityRule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 0=Sunday ... 6=Saturday
  @Getter
  @Column(name = "day_of_week", nullable = false)
  private Integer dayOfWeek;

  @Getter
  @Column(name = "start_time", nullable = false)
  private LocalTime startTime;

  @Getter
  @Column(name = "end_time", nullable = false)
  private LocalTime endTime;

  @Column(name = "is_recurring", nullable = false)
  private boolean recurring = true;

  @Column(name = "active", nullable = false)
  private boolean active = true;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_id")
  private Service service;
}

