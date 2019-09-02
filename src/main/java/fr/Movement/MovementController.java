package fr.Movement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import fr.Customs.Customs;
import fr.Customs.CustomsRepository;
import fr.MerchandiseInfo.MerchandiseInfo;
import fr.MerchandiseInfo.MerchandiseInfoRepository;
import fr.Message.Message;
import fr.Message.MessageRepository;
import fr.OutputInfo.OutputInfo;
import fr.OutputInfo.OutputInfoRepository;
import fr.RefrenceType.ReferenceType;
import fr.RefrenceType.ReferenceTypeRepository;
import fr.User.User;
import fr.User.UserRepository;
import fr.Utils.EmailService;
import fr.Utils.ResponseHandler;
import fr.Utils.XmlGenerator;
import fr.Warehouse.Warehouse;
import fr.Warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
@RestController
@RequestMapping(path = "/movement")
public class MovementController {

    @Autowired
    MovementRepository movementRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomsRepository customsRepository;
    @Autowired
    ReferenceTypeRepository referenceTypeRepository;
    @Autowired
    MerchandiseInfoRepository merchandiseInfoRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    OutputInfoRepository outputInfoRepository;

    @Autowired
    ResponseHandler responseHandler;
    @Autowired
    XmlGenerator xmlGenerator;
    @Autowired
    EmailService emailService;
    @Autowired
    MessageRepository messageRepository;

    @CrossOrigin
    @GetMapping
    public @ResponseBody  ResponseEntity getMovements() {
        try {
            return new ResponseEntity<>(movementRepository.findAll(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }

    @CrossOrigin
    @GetMapping("/last50")
    public @ResponseBody ResponseEntity getLast50Movements() {
        try {
            return new ResponseEntity<>(movementRepository.findLast50ByOrderByIdDesc(), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }

    @CrossOrigin
    @GetMapping("/empty")
    public @ResponseBody ResponseEntity getEmptyMovement(@RequestParam("type") String type) {
        try {
            Movement mov = new Movement();
            mov.setCustoms(new Customs());
            mov.setMerchandiseInfo(new MerchandiseInfo());
            mov.setUser(new User());
            if (type.equals("input")) {
                mov.setOriginalWarehouse(new Warehouse());
                mov.setType("input");
            } else if (type.equals("output")) {
                mov.setType("output");
                mov.setDestinationWarehouse(new Warehouse());
                mov.setOutputInfo(new OutputInfo());
            }
            return new ResponseEntity<>(mov, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }


    @CrossOrigin
    @PostMapping("/createInput")
    public @ResponseBody ResponseEntity createMovement(@RequestBody Movement movement,
                                                       @RequestParam("user") String username,
                                                       @RequestParam("date") String date,
                                                       @RequestParam("email") String email) {
        try {
            Movement newMovement = new Movement();
            newMovement.setCreationDate(LocalDateTime.now());
            newMovement.setRealizedDate(LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(date)));
            newMovement.setDeclarationPlace(warehouseRepository.findByName("RapidCargo CDG"));
            newMovement.setUser(userRepository.findByFirstNameAndLastName(username.split(" ", 2)[0], username.split(" ",2)[1]));
            newMovement.setCustoms(customsRepository.findOne(movement.getCustoms().getId()));
            MerchandiseInfo newMerchandiseInfo = new MerchandiseInfo();
            if (movement.getMerchandiseInfo().getTotalQuantity()<movement.getMerchandiseInfo().getQuantity()
                || movement.getMerchandiseInfo().getTotalWeight()<movement.getMerchandiseInfo().getWeight()) {
                return responseHandler.responseError("The total quantity and weight of the reference must each be greater than or equal to the quantity and weight of the goods in the movement.");
            }
            newMerchandiseInfo.setQuantity(movement.getMerchandiseInfo().getQuantity());
            newMerchandiseInfo.setWeight(movement.getMerchandiseInfo().getWeight());
            newMerchandiseInfo.setTotalQuantity(movement.getMerchandiseInfo().getTotalQuantity());
            newMerchandiseInfo.setTotalWeight(movement.getMerchandiseInfo().getTotalWeight());
            newMerchandiseInfo.setDescription(movement.getMerchandiseInfo().getDescription());
            newMerchandiseInfo.setReference(movement.getMerchandiseInfo().getReference());
            newMerchandiseInfo.setReferenceType(referenceTypeRepository.findOne(movement.getMerchandiseInfo().getReferenceType().getId()));
            newMovement.setMerchandiseInfo(newMerchandiseInfo);
            newMovement.setOriginalWarehouse(warehouseRepository.findOne(movement.getOriginalWarehouse().getId()));
            newMovement.setDestinationWarehouse(warehouseRepository.findByCode("RCW"));
            if (movement.getOriginalWarehouse() == warehouseRepository.findByCode("RCW")) {
                newMovement.setType("cons");
            } else {
                newMovement.setType("input");
            }

            try {
                xmlGenerator.createInputXML(newMovement);
                emailService.sendMail(email, "input");
                merchandiseInfoRepository.save(newMerchandiseInfo);
                movementRepository.save(newMovement);
                Message message = new Message();
                message.setTime(LocalDateTime.now());
                message.setMovement(newMovement);
                messageRepository.save(message);
                return responseHandler.responseSuccess("New input movement successfully registered.");
            } catch (Exception e) {
                e.printStackTrace();
                return responseHandler.responseError("Error while sending the email.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseHandler.responseError();
        }
    }

    @CrossOrigin
    @PostMapping("/createOutput")
    public @ResponseBody ResponseEntity createOutputMovement(@RequestBody Movement movement,
                                                             @RequestParam("user") String username,
                                                             @RequestParam("date") String date,
                                                             @RequestParam("email") String email) {
        try {
            if (movementRepository.existsAllByMerchandiseInfo_Reference(movement.getOutputInfo().getCustomsDocRef())
                && movementRepository.findByMerchandiseInfo_Reference(movement.getOutputInfo().getCustomsDocRef()).getType().equals("input")) {
                Movement input = movementRepository.findByMerchandiseInfo_Reference(movement.getOutputInfo().getCustomsDocRef());
                Movement newMovement = new Movement();
                newMovement.setCreationDate(LocalDateTime.now());
                newMovement.setRealizedDate(LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(date)));
                newMovement.setDeclarationPlace(warehouseRepository.findByName("RapidCargo CDG"));
                newMovement.setUser(userRepository.findByFirstNameAndLastName(username.split(" ", 2)[0], username.split(" ",2)[1]));
                newMovement.setCustoms(customsRepository.findOne(movement.getCustoms().getId()));
                MerchandiseInfo newMerchandiseInfo = new MerchandiseInfo();
                if (movement.getMerchandiseInfo().getTotalQuantity()<movement.getMerchandiseInfo().getQuantity()
                        || movement.getMerchandiseInfo().getTotalWeight()<movement.getMerchandiseInfo().getWeight()) {
                    return responseHandler.responseError("The total quantity and weight of the reference must each be greater than or equal to the quantity and weight of the goods in the movement.");
                }
                newMerchandiseInfo.setQuantity(movement.getMerchandiseInfo().getQuantity());
                newMerchandiseInfo.setWeight(movement.getMerchandiseInfo().getWeight());
                newMerchandiseInfo.setTotalQuantity(movement.getMerchandiseInfo().getTotalQuantity());
                newMerchandiseInfo.setTotalWeight(movement.getMerchandiseInfo().getTotalWeight());
                newMerchandiseInfo.setDescription(movement.getMerchandiseInfo().getDescription());
                newMerchandiseInfo.setReference(movement.getMerchandiseInfo().getReference());
                newMerchandiseInfo.setReferenceType(referenceTypeRepository.findOne(movement.getMerchandiseInfo().getReferenceType().getId()));
                newMovement.setMerchandiseInfo(newMerchandiseInfo);
                OutputInfo newOutputinfo = new OutputInfo();
                newOutputinfo.setInputMovement(input);
                newOutputinfo.setCustomsDocRef(movement.getOutputInfo().getCustomsDocRef());
                newMovement.setOutputInfo(newOutputinfo);
                newOutputinfo.setCustomsDoc(customsRepository.findOne(movement.getOutputInfo().getCustomsDoc().getId()));
                newMovement.setDestinationWarehouse(warehouseRepository.findOne(movement.getDestinationWarehouse().getId()));
                newMovement.setOriginalWarehouse(warehouseRepository.findByCode("RCW"));
                if (movement.getDestinationWarehouse() == warehouseRepository.findByCode("RCW")) {
                    newMovement.setType("cons");
                } else {
                    newMovement.setType("output");
                }

                try {
                    xmlGenerator.createOutputXML(newMovement);
                    emailService.sendMail(email, "output");
                    merchandiseInfoRepository.save(newMerchandiseInfo);
                    outputInfoRepository.save(newOutputinfo);
                    movementRepository.save(newMovement);
                    Message message = new Message();
                    message.setTime(LocalDateTime.now());
                    message.setMovement(newMovement);
                    messageRepository.save(message);
                    return responseHandler.responseSuccess("New output movement successfully registered.");
                } catch (Exception e) {
                    e.printStackTrace();
                    return responseHandler.responseError("Error while sending the email.");
                }
            } else {
                return responseHandler.responseError("The customs document reference you entered does not exist or does not match an input movement");
            }
        } catch (Exception e) {
            return responseHandler.responseError();
        }
    }
}
