package study.springcloud.gateway.controller;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class FallbackController {

    @RequestMapping("/defaultFallback")
    public Map<String, String> defaultFallback() {
        log.info(">>>>>> 服务降级");
        Map<String, String> result = Maps.newHashMap();
        result.put("code", "9999");
        result.put("desc", "服务异常");
        return result;
    }
}
