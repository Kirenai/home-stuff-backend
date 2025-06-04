package me.kire.re.nourishment.domain.port.in;

import me.kire.re.nourishment.domain.model.Nourishment;
import reactor.core.publisher.Mono;

public interface GetNourishmentByNamePort {
    Mono<Nourishment> execute(String name);
}
