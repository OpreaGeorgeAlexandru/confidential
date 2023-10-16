package ro.simavi.mf.avr.controllers;

import ro.simavi.mf.avr.model.ConfidentialData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @PostMapping("/test")
    public ConfidentialData test(@RequestBody ConfidentialData data){
        log.info("Received data: " + data);
        return data;
    }
}
