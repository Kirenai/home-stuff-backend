package me.kire.re.nourishment.infrastructure.rest.router;

import lombok.extern.slf4j.Slf4j;
import me.kire.re.nourishment.infrastructure.rest.handler.NourishmentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
public class NourishmentRouter {

    public final String URL = "/nourishments";

    @Bean
    public RouterFunction<ServerResponse> route(NourishmentHandler nourishmentHandler) {
        return RouterFunctions.route()
                .GET(URL, nourishmentHandler::findAll)
                .GET(URL + "/{nourishmentId}", nourishmentHandler::findById)
                .GET(URL + "/name/{name}", nourishmentHandler::findByName)
                .POST(URL + "/user/{userId}/category/{categoryId}", nourishmentHandler::create)
                .PUT(URL + "/{nourishmentId}", nourishmentHandler::update)
                .DELETE(URL + "/{nourishmentId}", nourishmentHandler::delete)
                .build();
    }

}