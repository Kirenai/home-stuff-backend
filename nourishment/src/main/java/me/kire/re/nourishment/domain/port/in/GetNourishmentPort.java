package me.kire.re.nourishment.domain.port.in;

import me.kire.re.nourishment.domain.model.Nourishment;
import reactor.core.publisher.Mono;

public interface GetNourishmentPort {

    Mono<Nourishment> findById(String nourishmentId);

}
