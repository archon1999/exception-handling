package uz.xhamdam;

public enum ExampleErrorCodes implements ErrorCode {

  RESOURCE_NOT_FOUND(404, "Resource not found"),
  INVALID_REQUEST(400, "Invalid request"),
  INTERNAL_ERROR(500, "Internal server error");

  private final int status;
  private final String defaultMessage;

  ExampleErrorCodes(int status, String defaultMessage) {
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
