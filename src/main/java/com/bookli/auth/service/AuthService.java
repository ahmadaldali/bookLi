package com.bookli.auth.service;

import com.bookli.auth.dto.*;
import com.bookli.auth.jwt.JwtService;
import com.bookli.user.entity.User;
import com.bookli.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
      .role(User.Role.USER)
      .build();
    userRepository.save(user);

    String token = jwtService.generateToken(user.getEmail());
    return new AuthResponse(token);
  }

  public AuthResponse login(LoginRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    User user = userRepository.findByEmail(request.getEmail())
      .orElseThrow(() -> new RuntimeException("User not found"));

    String token = jwtService.generateToken(user.getEmail());
    return new AuthResponse(token);
  }
}
