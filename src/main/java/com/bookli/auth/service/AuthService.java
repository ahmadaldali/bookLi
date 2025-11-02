package com.bookli.auth.service;

import com.bookli.auth.dto.*;
import com.bookli.auth.jwt.JwtService;
import com.bookli.common.enums.UserRole;
import com.bookli.common.exception.AppException;
import com.bookli.user.entity.User;
import com.bookli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResponse register(RegisterRequest request) {
    User user = User.builder()
      .name(request.getName())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .role(UserRole.USER)
      .build();
    userRepository.save(user);

    String token = jwtService.generateToken(user.getEmail());
    return new AuthResponse(token);
  }

  public AuthResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
      .orElseThrow(() -> new AppException("error.login.user_notfound"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new AppException("error.login.invalid_credentials");
    }

    String token = jwtService.generateToken(user.getEmail());
    return new AuthResponse(token);
  }
}
