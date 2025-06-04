package me.kire.re.nourishment.domain.port.out.repository;

import me.kire.re.nourishment.domain.model.NourishmentPrice;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NourishmentPriceRepositoryPort {
    Flux<NourishmentPrice> findAll(String nourishmentId, Pageable pageable);

    Mono<NourishmentPrice> create(NourishmentPrice nourishmentPrice);
}
