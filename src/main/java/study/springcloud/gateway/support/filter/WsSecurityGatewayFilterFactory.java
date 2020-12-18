package study.springcloud.gateway.support.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Order(-200)
public class WsSecurityGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        log.info(">>> WsSecurityGatewayFilterFactory");
        return (exchange, chain) -> {
            List<String> strings = exchange.getRequest().getHeaders().get(config.getName());
            if (strings != null) {
//                ServerHttpRequest request = exchange.getRequest().mutate()
//                        .header(config.getName(), strings.get(0))
//                        .build();
////                System.out.println("-----------------------------------------");
//                System.out.println(strings.get(0));
                return chain.filter(exchange.mutate().request(exchange.getRequest()).build());
            } else {
                return chain.filter(exchange);
            }
        };
    }

}
