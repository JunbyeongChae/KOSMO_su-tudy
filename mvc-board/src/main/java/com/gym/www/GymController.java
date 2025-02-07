package com.gym.www;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class GymController {
    @RequestMapping("/gym")
    public String gym(){
        log.info("gym호출 성공");
        return "gym";
    }
}
