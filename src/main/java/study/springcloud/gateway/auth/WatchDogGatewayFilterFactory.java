package study.springcloud.gateway.auth;

import com.google.common.base.Stopwatch;
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
import study.springcloud.gateway.support.utils.Exchanges;

import java.util.concurrent.TimeUnit;

/**
 * 1.全局过滤器与其他2类过滤器相比，永远是最后执行的；它的优先级只对其他全局过滤器起作用
 * 2.当默认过滤器与自定义过滤器的优先级一样时，优先出发默认过滤器，然后才是自定义过滤器；
 * 同类型的过滤器，出发顺序与他们在配置文件中声明的顺序一致
 * 3.默认过滤器与自定义过滤器使用同样的order顺序空间，即他们会按照各自的顺序来进行排序
 */
@Slf4j
@Component
@Order(1000)
public class WatchDogGatewayFilterFactory extends AbstractGatewayFilterFactory<WatchDogGatewayFilterFactory.Config> {

    private static final int ORDER = -1000;

    public WatchDogGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return new WatchDogGatewayFilter();
    }

    public class WatchDogGatewayFilter implements GatewayFilter, Ordered {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            //
            String path = Exchanges.getGatewayOriginalRouteUrl(exchange).getPath();
            log.info("pre >>>>>> [{}]", path);
            //
            Stopwatch stopwatch = Stopwatch.createStarted();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("post >>>>>> [{}] cost time [{}] ms", path, stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }));
        }

        @Override
        public int getOrder() {
            return ORDER;
        }
    }

    @Setter
    @Getter
    public static class Config {

    }
}
