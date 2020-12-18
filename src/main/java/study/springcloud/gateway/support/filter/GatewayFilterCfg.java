package study.springcloud.gateway.support.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayFilterCfg {

    @Bean
    public CustomGatewayFilterFactory customGatewayFilterFactory(){
        return new CustomGatewayFilterFactory();
    }
}
