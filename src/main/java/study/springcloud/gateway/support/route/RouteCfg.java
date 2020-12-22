package study.springcloud.gateway.support.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RouteCfg {

    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.host("**.yuqiyu.com")
                        .uri("http://blog.yuqiyu.com")
                        .order(1)
                        .id("blog"))
                .build();
    }
}
