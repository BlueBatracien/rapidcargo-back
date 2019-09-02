package fr.Warehouse;

import fr.Utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping(path = "/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ResponseHandler responseHandler;

    @CrossOrigin
    @GetMapping
    public @ResponseBody ResponseEntity getWarehouses() {
        try {
            return new ResponseEntity<>(warehouseRepository.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }

    @CrossOrigin
    @PostMapping("/create")
    public @ResponseBody ResponseEntity createWarehouse(@RequestBody Warehouse warehouse) {
        System.out.println(warehouse);
        try {
            if (warehouseRepository.existsByCode(warehouse.getCode().trim())) {
                return responseHandler.responseError("This warehouse code already exists.");
            } else {
                if (warehouseRepository.existsByName(warehouse.getName().trim())) {
                    return responseHandler.responseError("This warehouse name already exists.");
                } else {
                    Warehouse newWarehouse = new Warehouse();
                    newWarehouse.setCode(warehouse.getCode());
                    newWarehouse.setName(warehouse.getName());
                    warehouseRepository.save(newWarehouse);
                    return responseHandler.responseSuccess("New warehouse successfully added.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }

}
