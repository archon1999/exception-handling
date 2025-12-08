package uz.xhamdam;

import java.util.Arrays;
import java.util.Objects;

/**
 * Application runtime exception that carries an ErrorCode and optional message arguments.
 *
 * Usage:
 *   throw new AppException(UserErrors.USER_NOT_FOUND, userId);
 */
public class AppException extends RuntimeException {

  private final ErrorCode errorCode;
  private final Object[] args;

  /**
   * Create exception with error code and optional message args for i18n placeholders.
   */
  public AppException(ErrorCode errorCode, Object... args) {
    super(Objects.requireNonNull(errorCode, "errorCode").code());
    this.errorCode = errorCode;
    this.args = args == null ? new Object[0] : Arrays.copyOf(args, args.length);
  }

  /**
   * Returns the error code object (enum or implementation).
   */
  public ErrorCode getErrorCode() {
    return errorCode;
  }

  /**
   * Returns args intended for message placeholders (MessageFormat / MessageSource).
   */
  public Object[] getArgs() {
    return Arrays.copyOf(args, args.length);
  }

  @Override
  public String toString() {
    return "AppException{" +
        "errorCode=" + errorCode.code() +
        ", args=" + Arrays.toString(args) +
        ", message=" + getMessage() +
        '}';
  }
}
