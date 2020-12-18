package study.springcloud.gateway.support.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;

public class WsSecurityGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            List<String> strings = exchange.getRequest().getHeaders().get(config.getName());
            if (strings!=null){
                ServerHttpRequest request = exchange.getRequest().mutate()
                        .header(config.getName(), strings.get(0))
                        .build();
//                System.out.println("-----------------------------------------");
//                System.out.println(strings.get(0));
                return chain.filter(exchange.mutate().request(request).build());
            }else{
                return chain.filter(exchange);
            }
        };
    }

}
