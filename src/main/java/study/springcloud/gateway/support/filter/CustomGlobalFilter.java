package study.springcloud.gateway.support.filter;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import study.springcloud.gateway.support.utils.Exchanges;

@Slf4j
@Component
//@Order(-1)
public class CustomGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(">>>>>> CustomGlobalFilter");

        String path = Exchanges.getGatewayOriginalRouteUrl(exchange).getPath();
        Stopwatch stopwatch = Stopwatch.createStarted();
        Mono<Void> mono = chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                }));
        return mono;
    }
}
