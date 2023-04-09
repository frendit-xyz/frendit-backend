package frendit.xyz.com.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

@Data
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private int status;
    private Map<String, String> errors;
    private String message;

    public ErrorResponse(Exception ex, HttpStatus httpStatus, Map<String, String> errors) {
        this.timestamp = new Date();
        this.status = httpStatus.value();
        this.errors = errors;
        this.message = errors.getOrDefault(errors.keySet().iterator().next(), "");
    }

}
