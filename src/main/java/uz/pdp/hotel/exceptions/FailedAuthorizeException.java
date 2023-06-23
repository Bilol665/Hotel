package uz.pdp.hotel.exceptions;

public class FailedAuthorizeException extends RuntimeException {
    public FailedAuthorizeException(String message) {
        super(message);
    }
}
