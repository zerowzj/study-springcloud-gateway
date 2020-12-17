package study.springcloud.gateway.support.limiter.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component("rateLimitByBurstyFilter")
public class RateLimitByBurstyFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        RateLimiter limiter = RateLimiter.create(5);
        if (limiter.acquire() != 0) {
            return chain.filter(exchange);
        } else {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return response.setComplete();
        }
    }
}
