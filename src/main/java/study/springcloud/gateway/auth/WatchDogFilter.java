package study.springcloud.gateway.auth;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import study.springcloud.gateway.support.utils.Exchanges;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Order(-Integer.MAX_VALUE)
public class WatchDogFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(">>>>>>");
        ServerHttpRequest request = exchange.getRequest();

        log.info("GatewayOriginalRouteUrl={}", Exchanges.getGatewayOriginalRouteUrl(exchange));
        log.info("{}", Exchanges.getGatewayRoute(exchange));

        String path = request.getURI().getPath();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Mono<Void> mono = chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    log.info(">>>>>> [{}] cost time [{}] ms", path, stopwatch.elapsed(TimeUnit.MILLISECONDS));
                }));
        return mono;
    }
}
