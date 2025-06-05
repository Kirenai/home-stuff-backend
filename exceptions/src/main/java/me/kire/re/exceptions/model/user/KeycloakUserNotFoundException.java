package me.kire.re.exceptions.model.user;

import me.kire.re.exceptions.dto.user.KeycloakUserErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

public class KeycloakUserNotFoundException extends RuntimeException {
    public KeycloakUserNotFoundException(String message) {
        super(message);
    }

    public KeycloakUserErrorMessageResponse createError(ServerRequest serverRequest) {
        return KeycloakUserErrorMessageResponse
                .builder()
                .message(super.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(serverRequest.path())
                .build();
    }
}
