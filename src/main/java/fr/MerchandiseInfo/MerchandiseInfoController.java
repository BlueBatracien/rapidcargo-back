package fr.MerchandiseInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping(path = "/merchandiseInfo")
public class MerchandiseInfoController {

    @Autowired
    MerchandiseInfoRepository merchandiseInfoRepository;

    @CrossOrigin
    @GetMapping
    public Iterable<MerchandiseInfo> getAllMerchandiseInfos() {
        return merchandiseInfoRepository.findAll();
    }
}
