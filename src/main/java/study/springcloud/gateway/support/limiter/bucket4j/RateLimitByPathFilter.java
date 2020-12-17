package study.springcloud.gateway.support.limiter.bucket4j;

import io.github.bucket4j.Bucket;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Bucket4j 是一个基于令牌桶算法实现的强大的限流库，
 * 它不仅支持单机限流，还支持通过诸如 Hazelcast、Ignite、Coherence、Infinispan 或其他兼容 JCache API (JSR 107) 规范的分布式缓存实现分布式限流
 */
@Component
public class RateLimitByPathFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        Bucket bucket = null;
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        } else {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return response.setComplete();
        }
    }
}
