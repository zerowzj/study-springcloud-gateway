package study.springcloud.gateway.support;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = "study.springcloud.gateway")
public class SpringBootCfg {
}

