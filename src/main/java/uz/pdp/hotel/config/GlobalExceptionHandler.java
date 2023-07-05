package uz.pdp.hotel.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.hotel.exceptions.DataNotFoundException;
import uz.pdp.hotel.exceptions.FailedAuthorizeException;
import uz.pdp.hotel.exceptions.NotAcceptable;
import uz.pdp.hotel.exceptions.RequestValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<String> dataNotFound(DataNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {RequestValidationException.class})
    public ResponseEntity<String> userCreateException(RequestValidationException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(value = {FailedAuthorizeException.class})
    public ResponseEntity<String> failToAuthorize(FailedAuthorizeException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {NotAcceptable.class})
    public ResponseEntity<String> justNotAcceptable(NotAcceptable e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }
}
