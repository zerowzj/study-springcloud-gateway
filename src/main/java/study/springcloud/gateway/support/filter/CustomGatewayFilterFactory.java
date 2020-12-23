package study.springcloud.gateway.support.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Order(-1)
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {

    public CustomGatewayFilterFactory(){
        //这里需要将自定义的 Config 传过去，否则会报告ClassCastException
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name", "age");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new CustomGatewayFilter(config);
    }

    public class CustomGatewayFilter implements GatewayFilter, Ordered {

        private Config config;

        public CustomGatewayFilter(Config config){
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            String name = config.getName();
            int age = config.getAge();
            log.info("name={}, age={}", name, age);
            //在then方法里的，相当于aop中的后置通知
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //do something
            }));
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }

    @Setter
    @Getter
    public static class Config {

        private String name;

        private int age;

    }
}
