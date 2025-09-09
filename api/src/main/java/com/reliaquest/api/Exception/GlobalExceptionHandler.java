package com.reliaquest.api.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getStatusCode().getClass());
        body.put("message", ex.getReason()); // <-- include your custom message
        body.put("timestamp", new Date());
        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}
