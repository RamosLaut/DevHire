package TP_Final.devhire.Exceptions;

public class ExistingEmailException extends RuntimeException {
  public ExistingEmailException(String message) {
    super(message);
  }
}
