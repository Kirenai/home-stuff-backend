package me.kire.re.nourishment.infrastructure.repository;

import me.kire.re.nourishment.infrastructure.entity.NourishmentPriceDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MongoNourishmentPriceRepository extends ReactiveMongoRepository<NourishmentPriceDocument, String> {
    Flux<NourishmentPriceDocument> findAllByNourishmentId(String nourishmentId, Pageable pageable);
}
