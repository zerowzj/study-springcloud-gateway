package study.springcloud.gateway.auth;

import com.google.common.base.Stopwatch;
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
import study.springcloud.gateway.support.utils.Exchanges;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WatchDogGatewayFilterFactory extends AbstractGatewayFilterFactory<WatchDogGatewayFilterFactory.Config> {

    public WatchDogGatewayFilterFactory() {
        //这里需要将自定义的 Config 传过去，否则会报告ClassCastException
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new WatchDogGatewayFilter();
    }

    public class WatchDogGatewayFilter implements GatewayFilter, Ordered {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            String path = Exchanges.getGatewayOriginalRouteUrl(exchange).getPath();
            log.info(">>>>>> [{}]", path);
            Stopwatch stopwatch = Stopwatch.createStarted();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info(">>>>>> [{}] cost time [{}] ms", path, stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }));
        }

        @Override
        public int getOrder() {
            return -100;
        }
    }

    @Setter
    @Getter
    public static class Config {

    }
}
