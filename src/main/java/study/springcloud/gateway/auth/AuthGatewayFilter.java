package study.springcloud.gateway.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class AuthGatewayFilter extends AbstractGatewayFilterFactory<AuthGatewayFilter.Config> {

    private List<String> ignoreLt;

    public AuthGatewayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new MyGatewayFilter(config);
    }

    public class MyGatewayFilter implements GatewayFilter, Ordered {

        private Config config;

        public MyGatewayFilter(Config config) {
            this.config = config;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            log.info(">>>>>> ignore_list={}", ignoreLt);
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info(">>>>>> ignore_list={}", ignoreLt);
             }));
        }

        @Override
        public int getOrder() {
            return 100;
        }
    }

    @Setter
    @Getter
    public static class Config {

    }
}
