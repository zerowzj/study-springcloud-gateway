package study.springcloud.gateway.support.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.LinkedHashSet;

@Slf4j
public class Exchanges {

    /**
     * 获取原始路由url
     */
    public static URI getUrl(ServerWebExchange exchange) {
        return exchange.getRequest().getURI();
    }

    /**
     * 获取原始路由url
     */
    public static URI getGatewayRequestUrl(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
    }

    /**
     * 获取原始路由url
     */
    public static URI getGatewayOriginalRouteUrl(ServerWebExchange exchange) {
        LinkedHashSet set = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        log.info("{}", set);
        return null;
    }

    /**
     * 获取网关路由
     */
    public static Route getGatewayRoute(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    }
}
