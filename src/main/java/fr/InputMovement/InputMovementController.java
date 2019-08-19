package fr.InputMovement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping(path = "/inputMovement")
public class InputMovementController {

    @Autowired
    InputMovementRepository inputMovementRepository;

    @CrossOrigin
    @GetMapping
    public Iterable<InputMovement> getAllInputMovements() {
        return inputMovementRepository.findAll();
    }

}
