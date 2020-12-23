package study.springcloud.gateway.support.utils;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.LinkedHashSet;

public class Exchanges {

    public static URI getUrl(ServerWebExchange exchange) {
        return exchange.getRequest().getURI();
    }

    public static URI getGatewayRequestUrl(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
    }

    public static URI getGatewayOriginalRouteUrl(ServerWebExchange exchange) {
        LinkedHashSet STE =  exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        return null;
    }

    public static Route getGatewayRoute(ServerWebExchange exchange) {
        return exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
    }
}
