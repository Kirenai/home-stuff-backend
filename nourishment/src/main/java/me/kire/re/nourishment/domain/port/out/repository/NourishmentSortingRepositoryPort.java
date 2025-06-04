package me.kire.re.nourishment.domain.port.out.repository;

import me.kire.re.nourishment.domain.model.Nourishment;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NourishmentSortingRepositoryPort {

    Mono<Long> count();

    Flux<Nourishment> findAll(Pageable pageable, String userId, Boolean isAvailable);

}
