package study.springcloud.gateway.support.predicate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class CustomRoutePredicateFactory extends AbstractRoutePredicateFactory<CustomRoutePredicateFactory.Config> {

    public CustomRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("name");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        log.info("");
        return exchange -> {
            //判断header里有放token
            HttpHeaders headers = exchange.getRequest().getHeaders();
            List<String> header = headers.get(config.getName());
            log.info("Token Predicate headers:{}", header);
            return header.size() > 0;
        };
    }

    @Setter
    @Getter
    public class Config {

        private String name;

    }
}
