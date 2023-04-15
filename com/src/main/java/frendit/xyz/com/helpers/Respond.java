package frendit.xyz.com.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class Respond {
    public ResponseEntity<Map<String, String>> sendResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus).body(Collections.singletonMap("message", message));
    }
}
