package fr.RefrenceType;

import fr.Utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping(path = "/referencetype")
public class ReferenceTypeController {

    @Autowired
    ReferenceTypeRepository referenceTypeRepository;

    @Autowired
    ResponseHandler responseHandler;

    @CrossOrigin
    @GetMapping
    public @ResponseBody ResponseEntity getReferenceTypes() {
        try {
            return new ResponseEntity<>(referenceTypeRepository.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }

    @CrossOrigin
    @PostMapping("/create")
    public @ResponseBody ResponseEntity createReferenceType(@RequestBody String name) {
        try {
            if (referenceTypeRepository.existsByName(name)) {
                return responseHandler.responseError("This reference type name already exists.");
            }
            ReferenceType newRef = new ReferenceType();
            newRef.setName(name);
            referenceTypeRepository.save(newRef);
            return responseHandler.responseSuccess("New reference type successfully added.");
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }
}
