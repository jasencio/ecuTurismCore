package ec.tourismvisitplanner.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static ResponseEntity<Map<String, Object>> success(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<Map<String, Object>> success(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<Map<String, Object>> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<Map<String, Object>> error(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        return new ResponseEntity<>(response, status);
    }
}
