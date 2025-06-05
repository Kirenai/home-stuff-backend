package me.kire.re.exceptions.model.category;

import me.kire.re.exceptions.dto.category.CategoryErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryErrorMessageResponse createError(ServerRequest serverRequest) {
        return CategoryErrorMessageResponse
                .builder()
                .message(super.getMessage())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .path(serverRequest.path())
                .build();
    }

}
