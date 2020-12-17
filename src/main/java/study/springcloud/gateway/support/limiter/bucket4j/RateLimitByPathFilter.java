package study.springcloud.gateway.support.limiter.bucket4j;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import study.springcloud.gateway.support.utils.Exchanges;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bucket4j 是一个基于令牌桶算法实现的强大的限流库，
 * 它不仅支持单机限流，还支持通过诸如 Hazelcast、Ignite、Coherence、Infinispan
 * 或其他兼容 JCache API (JSR 107) 规范的分布式缓存实现分布式限流
 */
@Slf4j
@Component
public class RateLimitByPathFilter implements GatewayFilter {

    private static final Map<String, Bucket> LOCAL_CACHE = new ConcurrentHashMap<>();

    //桶的最大容量，即能装载 Token 的最大数量
    private int capacity;
    //每次 Token 补充量
    private int refillTokens;
    //补充 Token 的时间间隔
    private Duration refillDuration;

    public RateLimitByPathFilter() {
    }

    public RateLimitByPathFilter(int capacity, int refillTokens, Duration refillDuration) {
        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillDuration = refillDuration;
    }

    private Bucket createBucket() {
        Refill refill = Refill.greedy(refillTokens, refillDuration);
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = Exchanges.getUrl(exchange).getPath();

        Bucket bucket = LOCAL_CACHE.computeIfAbsent(path, k -> createBucket());
        log.info("path:{} ,令牌通可用的Token数量:{} ", path, bucket.getAvailableTokens());
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        } else {
            log.error("path:{} ,限制访问:{} ", path, bucket.getAvailableTokens());
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return response.setComplete();
        }
    }
}
