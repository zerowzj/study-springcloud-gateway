package study.springcloud.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public String demo() {
        log.info("this is a demo");
        return "demo";
    }
}
