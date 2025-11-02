package com.bookli.common.exception;

public class AppException extends RuntimeException {
  private final String code;

  public AppException(String code) {
    super(code); // optional, you can keep the code as the message too
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
