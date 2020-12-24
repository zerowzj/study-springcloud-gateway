package study.springcloud.gateway.auth;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import study.springcloud.gateway.support.utils.Exchanges;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@Order(-100000)
public class WatchDogGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = Exchanges.getGatewayOriginalRouteUrl(exchange).getPath();
        log.info("sssssssssssssssss");
        Stopwatch stopwatch = Stopwatch.createStarted();
        Mono<Void> mono = chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    log.info("sssssssssssssssssss", path, stopwatch.elapsed(TimeUnit.MILLISECONDS));
                }));
        return mono;
    }
}
