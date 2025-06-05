package me.kire.re.exceptions.core;

import lombok.extern.slf4j.Slf4j;
import me.kire.re.exceptions.dto.polymorphic.PolymorphicErrorMessageResponse;
import me.kire.re.exceptions.function.ErrorHandler;
import me.kire.re.exceptions.model.category.CategoryNotFoundException;
import me.kire.re.exceptions.model.consumption.ConsumptionNotFoundException;
import me.kire.re.exceptions.model.nourishment.NourishmentNotFoundException;
import me.kire.re.exceptions.model.nourishment.NourishmentTypeNotFoundException;
import me.kire.re.exceptions.model.user.KeycloakServiceUnavailableException;
import me.kire.re.exceptions.model.user.KeycloakUserNotFoundException;
import me.kire.re.exceptions.model.validator.ValidatorException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebInputException;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    private final Map<Class<? extends Throwable>, ErrorHandler> errorHandlers;

    public GlobalErrorAttributes() {
        this.errorHandlers = new HashMap<>();

        registerErrorHandler(ValidatorException.class,
                (exception, request) -> {
                    log.error("An error of type ValidatorException occurs");
                    var validatorException = (ValidatorException) exception;
                    return Map.of(
                            "error", validatorException.createError(),
                            "status", HttpStatus.BAD_REQUEST
                    );
                });

        registerErrorHandler(NourishmentNotFoundException.class,
                createNotFoundHandler("NourishmentNotFoundException"));

        registerErrorHandler(NourishmentTypeNotFoundException.class,
                createNotFoundHandler("NourishmentTypeNotFoundException"));

        registerErrorHandler(CategoryNotFoundException.class,
                createNotFoundHandler("CategoryNotFoundException"));

        registerErrorHandler(KeycloakUserNotFoundException.class,
                createNotFoundHandler("KeycloakUserNotFoundException"));

        registerErrorHandler(ConsumptionNotFoundException.class,
                createNotFoundHandler("ConsumptionNotFoundException"));

        registerErrorHandler(KeycloakServiceUnavailableException.class,
                (exception, request) -> {
                    log.error("An error of type KeycloakServiceUnavailableException occurs");
                    var typedException = (KeycloakServiceUnavailableException) exception;
                    return Map.of(
                            "error", typedException.createError(request),
                            "status", HttpStatus.SERVICE_UNAVAILABLE
                    );
                });

        registerErrorHandler(ServerWebInputException.class,
                (exception, request) -> {
                    log.error("An error of type ServerWebInputException occurs");
                    var typedException = (ServerWebInputException) exception;
                    var response = PolymorphicErrorMessageResponse.builder()
                            .message(typedException.getReason())
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .timestamp(LocalDateTime.now())
                            .path(request.path())
                            .build();
                    return Map.of(
                            "error", response,
                            "status", HttpStatus.BAD_REQUEST
                    );
                });
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest serverRequest, ErrorAttributeOptions options) {
        log.info("Invoking GlobalErrorAttributes#getErrorAttributes(..) method");
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        Throwable throwable = super.getError(serverRequest);

        ErrorHandler handler = this.findErrorHandler(throwable.getClass());
        if (handler != null) {
            errorAttributes.putAll(handler.handle(throwable, serverRequest));
        }

        return errorAttributes;
    }

    private <T extends Throwable> void registerErrorHandler(Class<T> exceptionClass, ErrorHandler handler) {
        this.errorHandlers.put(exceptionClass, handler);
    }

    private ErrorHandler findErrorHandler(Class<?> exceptionClass) {
        Class<?> currentClass = exceptionClass;
        while (currentClass != null) {
            ErrorHandler errorHandler = this.errorHandlers.get(currentClass);
            if (errorHandler != null) {
                return errorHandler;
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }

    private ErrorHandler createNotFoundHandler(String exceptionTypeName) {
        return (exception, request) -> {
            log.error("An error of type {} occurs", exceptionTypeName);
            try {
                Method createErrorMethod = exception.getClass().getMethod("createError", ServerRequest.class);
                Object error = createErrorMethod.invoke(exception, request);
                return Map.of(
                        "error", error,
                        "status", HttpStatus.NOT_FOUND
                );
            } catch (Exception e) {
                log.error("Error invoking createError method");
                return Map.of();
            }
        };
    }
}
