package me.kire.re.validation.core;

import reactor.core.publisher.Mono;

public interface Validator {
    <T> Mono<T> validate(T object);
}
