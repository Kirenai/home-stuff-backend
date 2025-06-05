package me.kire.re.exceptions.function;

import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@FunctionalInterface
public interface ErrorHandler {
    Map<String, Object> handle(Throwable e, ServerRequest request);
}
