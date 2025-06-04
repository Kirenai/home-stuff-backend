package me.kire.re.nourishment.domain.port.in;

import me.kire.re.nourishment.domain.model.Nourishment;
import reactor.core.publisher.Mono;

public interface CreateNourishmentPort {

    Mono<Nourishment> createNourishment(Nourishment nourishment);

}
