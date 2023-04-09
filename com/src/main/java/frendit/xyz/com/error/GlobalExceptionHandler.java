package frendit.xyz.com.error;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import javax.security.auth.message.AuthException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorResponse(ex, HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMultipartException(Exception ex) {
        Map<String, String> err = new HashMap<>();
        err.put("exception", "Invalid Upload Request");
        return new ErrorResponse(ex, HttpStatus.BAD_REQUEST, err);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorResponse handleMethodNotSupported(Exception ex) {
        Map<String, String> err = new HashMap<>();
        err.put("exception", "HTTP request method not supported for this operation.");
        return new ErrorResponse(ex, HttpStatus.METHOD_NOT_ALLOWED, err);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleIOException(Exception ex) {
        Map<String, String> err = new HashMap<>();
        err.put("io", ex.getMessage());
        return new ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, err);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNullpointerException(Exception ex) {
        Map<String, String> err = new HashMap<>();
        err.put("exception", "Result not found from query.");
        return new ErrorResponse( ex, HttpStatus.NOT_FOUND, err);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleSqlException(Exception ex) {
        Map<String, String> err = new HashMap<>();
        err.put("exception", ex.getMessage());
        return new ErrorResponse( ex, HttpStatus.BAD_REQUEST, err);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleGeneralException(Exception ex) {
        Map<String, String> err = new HashMap<>();
        err.put("exception", ex.getMessage());
        return new ErrorResponse( ex, HttpStatus.BAD_REQUEST, err);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleRuntimeException(Exception ex) {
        Map<String, String> err = new HashMap<>();
        err.put("runtime", ex.getMessage());
        return new ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, err);
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleAuthException(Exception ex) {
        Map<String, String> err = new HashMap<>();
        err.put("authorization", ex.getMessage());
        return new ErrorResponse(ex, HttpStatus.UNAUTHORIZED, err);
    }
}
