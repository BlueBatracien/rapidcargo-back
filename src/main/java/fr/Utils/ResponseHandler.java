package fr.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ResponseHandler {

    public ResponseEntity responseError() {
        Map<String, String> response = new HashMap<>();
        response.put("msg"," Something went wrong.");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    public ResponseEntity responseError(String msg) {
        Map<String, String> response = new HashMap<>();
        response.put("msg", " "+msg);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    public ResponseEntity responseSuccess(String msg) {
        Map<String, String> response = new HashMap<>();
        response.put("msg", " "+msg);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
