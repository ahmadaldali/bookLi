package com.bookli.common.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
  private final String code;

  public ValidationException(String code) {
    super(code); // optional, you can keep the code as the message too
    this.code = code;
  }
}
