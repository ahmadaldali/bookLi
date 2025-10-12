package com.bookli.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  private static final String SECRET_KEY = "5b9b16c5e6340204852dcfedf1cc6e5e";

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public String generateToken(String email) {
    return Jwts.builder()
      .subject(email)
      .issuedAt(new Date())
      .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
      .signWith(getSigningKey())
      .compact();
  }

  public String extractEmail(String token) {
    return Jwts.parser()
      .build()
      .parseSignedClaims(token)
      .getPayload().getSubject();
  }

  public boolean isTokenValid(String token, String email) {
    return email.equals(extractEmail(token)) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return Jwts.parser()
      .build()
      .parseSignedClaims(token)
      .getPayload().getExpiration();
  }
}
