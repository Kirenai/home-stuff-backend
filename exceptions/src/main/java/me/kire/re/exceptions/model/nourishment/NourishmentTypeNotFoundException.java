package me.kire.re.exceptions.model.nourishment;

import me.kire.re.exceptions.dto.nourishment.NourishmentTypeErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

public class NourishmentTypeNotFoundException extends RuntimeException {

    public NourishmentTypeNotFoundException(String message) {
        super(message);
    }

    public NourishmentTypeErrorMessageResponse createError(ServerRequest serverRequest) {
        return NourishmentTypeErrorMessageResponse
                .builder()
                .message(super.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(serverRequest.path())
                .build();
    }

}
