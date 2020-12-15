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
        Map<String, String> map = Maps.newHashMap();
        map.put("code", "fail");
        map.put("desc", "服务异常");
        map.put("data", "");
        return map;
    }
}
