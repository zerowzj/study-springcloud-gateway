package study.springcloud.gateway.support.filter;

import com.google.common.base.Stopwatch;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import study.springcloud.gateway.support.utils.Exchanges;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {

    public CustomGatewayFilterFactory(){
        //这里需要将自定义的 Config 传过去，否则会报告ClassCastException
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            boolean root = "root".equals(config.getType());
            if (root) {
                log.info("GatewayFilter root");
            } else {
                log.info("GatewayFilter customer");
            }
            //在then方法里的，相当于aop中的后置通知
            Stopwatch stopwatch = Stopwatch.createStarted();
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                //do something
                log.info("[{}] cost time [{}] ms", Exchanges.getGatewayRequestUrl(exchange), stopwatch.elapsed(TimeUnit.MILLISECONDS));
            }));
        });
    }

    @Setter
    @Getter
    public static class Config {

        private String type;

    }
}
