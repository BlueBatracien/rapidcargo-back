package fr.Customs;

import fr.Utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping(path = "/customs")
public class CustomsController {

    @Autowired
    CustomsRepository customsRepository;

    @Autowired
    ResponseHandler responseHandler;

    @CrossOrigin
    @GetMapping
    public @ResponseBody ResponseEntity getCustoms(){
        try {
            return new ResponseEntity<>(customsRepository.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }

    @CrossOrigin
    @PostMapping("/create")
    public @ResponseBody ResponseEntity createCustoms(@RequestBody String status) {
        try {
            Customs customs = new Customs();
            customs.setStatus(status.toUpperCase());
            customsRepository.save(customs);
            return responseHandler.responseSuccess("New customs status succcessfully added.");
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }
}
