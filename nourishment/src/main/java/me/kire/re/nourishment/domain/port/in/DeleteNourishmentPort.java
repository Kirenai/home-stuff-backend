package me.kire.re.nourishment.domain.port.in;

import reactor.core.publisher.Mono;

public interface DeleteNourishmentPort {

    Mono<Void> deleteNourishment(String nourishmentId);

}
