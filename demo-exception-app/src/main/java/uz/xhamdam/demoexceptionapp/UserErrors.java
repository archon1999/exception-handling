package uz.xhamdam.demoexceptionapp;

import uz.xhamdam.ErrorCode;

public enum UserErrors implements ErrorCode {

  USER_NOT_FOUND(404,"User not found"),

  USER_ALREADY_EXISTS(400,"User already exists"),

  INVALID_EMAIL(400,"Invalid email address");

  private final int status;
  private final String defaultMessage;

  UserErrors(int status, String defaultMessage) {
    this.status = status;
    this.defaultMessage = defaultMessage;
  }

  @Override
  public String code() {
    return name();
  }

  @Override
  public int httpStatus() {
    return status;
  }

  @Override
  public String defaultMessage() {
    return defaultMessage;
  }
}