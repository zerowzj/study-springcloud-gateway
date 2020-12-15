package study.springcloud.gateway.filter;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
//@Order(0)
public class WatchDogFilter implements GlobalFilter, Ordered {

    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(">>>>>> WatchDogFilter");
        ServerHttpRequest request = exchange.getRequest();

        Stopwatch stopwatch = Stopwatch.createStarted();
        Mono<Void> mono;
        try {
            mono = chain.filter(exchange).then();
        } finally {
            log.info("cost time [{}] ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
//        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
//        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//            Long startTime = exchange.getAttribute(START_TIME);
//            if (startTime != null) {
//                Long executeTime = (System.currentTimeMillis() - startTime);
//                log.info(">>>>>>>>>>>>"+exchange.getRequest().getURI().getRawPath() + " : " + executeTime + "ms");
//            }
//        }));

        return mono;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
