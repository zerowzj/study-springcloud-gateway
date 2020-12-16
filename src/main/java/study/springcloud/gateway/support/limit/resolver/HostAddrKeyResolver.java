package study.springcloud.gateway.support.limit.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component("hostAddrKeyResolver")
public class HostAddrKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String hostName =  request.getRemoteAddress().getHostName();
        log.info(">>>>>> rate_limit host_name: {}", hostName);


        return Mono.just(hostName);
    }
}
