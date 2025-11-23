package com.bookli.user.service;

import com.bookli.booking.service.availability.AvailabilityService;
import com.bookli.booking.service.availability.AvailabilityUtils;
import com.bookli.user.entity.User;
import com.bookli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final AvailabilityService availabilityService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

    return new CustomUserDetails(user);
  }

  public Map<LocalDate, List<AvailabilityUtils.TimeSlot>> getUserAvailability(Long userId, String calendarType) {
    return availabilityService.getAvailability(userId, calendarType);
  }
}
