package me.kire.re.apigateway.config;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Configuration
public class GatewayConfig {
    private final List<RouteDefinition> routes = new ArrayList<>();

    public GatewayConfig() {
        this.routes.add(new RouteDefinition("users", r -> this.routeWithRewrite(r, "/users", "/api/v0/users", "HS-USER")));
        this.routes.add(new RouteDefinition("roles", r -> this.routeWithRewrite(r, "/roles", "/api/v0/roles", "HS-ROLE")));
        this.routes.add(new RouteDefinition("nourishments", r -> this.routeWithRewrite(r, "/nourishments", "/products/v0/nourishments", "NOURISHMENT")));
        this.routes.add(new RouteDefinition("consumptions", r -> this.routeWithRewrite(r, "/consumptions", "/api/v0/consumptions", "HS-CONSUMPTION")));
        this.routes.add(new RouteDefinition("categories", r -> this.routeWithRewrite(r, "/categories", "/api/v0/categories", "HS-CATEGORY")));
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder builderRoutes = builder.routes();
        this.routes.forEach(route -> builderRoutes.route(route.id, route.fn));
        return builderRoutes.build();
    }


    private Buildable<Route> routeWithRewrite(PredicateSpec predicateSpec,
                                              String pathPrefix,
                                              String rewritePrefix,
                                              String serviceId) {
        return predicateSpec
                .path(pathPrefix + "/**")
                .filters(f -> f.rewritePath(pathPrefix + "?(?<segment>/?.*)", rewritePrefix + "${segment}"))
                .uri("lb://" + serviceId);
    }

    @AllArgsConstructor
    private static class RouteDefinition {
        private String id;
        private Function<PredicateSpec, Buildable<Route>> fn;
    }
}
