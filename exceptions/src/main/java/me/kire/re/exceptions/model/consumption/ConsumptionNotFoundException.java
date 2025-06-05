package me.kire.re.exceptions.model.consumption;

import me.kire.re.exceptions.dto.consumption.ConsumptionErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

public class ConsumptionNotFoundException extends RuntimeException {

    public ConsumptionNotFoundException(String message) {
        super(message);
    }

    public ConsumptionErrorMessageResponse createError(ServerRequest serverRequest) {
        return ConsumptionErrorMessageResponse
                .builder()
                .message(super.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(serverRequest.path())
                .build();
    }

}
