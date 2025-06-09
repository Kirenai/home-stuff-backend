package me.kire.re.apigateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GatewayConfig {
    private static final String SEGMENT_PATTERN = "?(?<segment>/?.*)";
    private static final String REWRITE_PATTERN = "${segment}";

    private static final List<RouteMetadata> ROUTES = List.of(
            new RouteMetadata("users", "/users", "/api/v0/users", "HS-USER"),
            new RouteMetadata("roles", "/roles", "/api/v0/roles", "HS-ROLE"),
            new RouteMetadata("nourishments", "/nourishments", "/products/v0/nourishments", "NOURISHMENT"),
            new RouteMetadata("consumptions", "/consumptions", "/api/v0/consumptions", "HS-CONSUMPTION"),
            new RouteMetadata("categories", "/categories", "/api/v0/categories", "HS-CATEGORY")
    );

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routesBuilder = builder.routes();
        ROUTES.forEach(route ->
                routesBuilder.route(route.id,
                        p -> routeWithRewrite(p, route.pathPrefix, route.rewritePrefix, route.serviceId)
                )
        );
        return routesBuilder.build();
    }


    private Buildable<Route> routeWithRewrite(PredicateSpec predicateSpec,
                                              String pathPrefix,
                                              String rewritePrefix,
                                              String serviceId) {
        return predicateSpec
                .path(pathPrefix + "/**")
                .filters(f -> f.rewritePath(pathPrefix + SEGMENT_PATTERN, rewritePrefix + REWRITE_PATTERN))
                .uri("lb://" + serviceId);
    }

    private record RouteMetadata(String id, String pathPrefix, String rewritePrefix, String serviceId) {
    }
}
