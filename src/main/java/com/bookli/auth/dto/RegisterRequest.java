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
  @Email(message = "{error.email.invalid}")
  private String email;

  @NotBlank(message = "{error.password.required}")
  @Size(min = 6, max = 50, message = "{error.password.length_6_50}")
  private String password;
}
