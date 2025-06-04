package me.kire.re.nourishment.domain.port.out.repository;

import me.kire.re.nourishment.domain.model.Nourishment;
import reactor.core.publisher.Mono;

public interface NourishmentRepositoryPort {

    Mono<Nourishment> findById(String nourishmentId);

    Mono<Nourishment> findByName(String name);

    Mono<Nourishment> createNourishment(Nourishment nourishment);

    Mono<Nourishment> updateNourishment(String nourishmentId, Nourishment nourishment);

    Mono<Void> deleteNourishment(String nourishmentId);

}
