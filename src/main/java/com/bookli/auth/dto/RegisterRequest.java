package com.bookli.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

  @NotBlank(message = "{error.name.required}")
  private String name;

  @NotBlank(message = "{error.email.required}")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "{error.password.required}")
  @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
  private String password;
}
