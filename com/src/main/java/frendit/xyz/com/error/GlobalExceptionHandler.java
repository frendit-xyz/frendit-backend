package frendit.xyz.com.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import javax.security.auth.message.AuthException;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMultipartException(Exception ex) {
        return new ErrorResponse(ex, HttpStatus.BAD_REQUEST,  "Invalid Upload Request");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ErrorResponse handleMethodNotSupported(Exception ex) {
        return new ErrorResponse(ex, HttpStatus.METHOD_NOT_ALLOWED, "HTTP request method not supported for this operation.");
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleIOException(Exception ex) {
        return new ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "IO Error: " + ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNullpointerException(Exception ex) {
        return new ErrorResponse( ex, HttpStatus.NOT_FOUND, "Result not found from query.");
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleSqlException(Exception ex) {
        return new ErrorResponse( ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleGeneralException(Exception ex) {
        return new ErrorResponse( ex, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleRuntimeException(Exception ex) {
        return new ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Runtime Error: " + ex.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleAuthException(Exception ex) {
        return new ErrorResponse(ex, HttpStatus.UNAUTHORIZED, "Authorization Error: " + ex.getMessage());
    }
}
