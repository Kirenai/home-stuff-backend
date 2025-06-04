package me.kire.re.nourishment.infrastructure.rest.router;

import me.kire.re.nourishment.infrastructure.rest.handler.NourishmentPriceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class NourishmentPriceRouter {
    public final String URL = "/nourishments/price";

    @Bean
    public RouterFunction<ServerResponse> routePrice(NourishmentPriceHandler nourishmentPriceHandler) {
        return route()
                .GET(URL + "/{nourishmentId}", nourishmentPriceHandler::findAll)
                .POST(URL + "/{nourishmentId}", nourishmentPriceHandler::save)
                .build();
    }
}
