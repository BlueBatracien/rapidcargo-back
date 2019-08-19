package fr.Movement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequestMapping(path = "/movement")
public class MovementController {

    @Autowired
    MovementRepository movementRepository;

    @CrossOrigin
    @GetMapping
    public ResponseEntity getMovements() {
        try {
            return new ResponseEntity<>(movementRepository.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("msg","An error occured.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }
}
