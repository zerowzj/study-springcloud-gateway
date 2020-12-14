package study.springcloud.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping("/sayHi")
    public String sayHi() {
        return "hello";
    }
}
