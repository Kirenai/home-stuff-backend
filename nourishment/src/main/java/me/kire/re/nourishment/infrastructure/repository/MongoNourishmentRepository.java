package me.kire.re.nourishment.infrastructure.repository;

import me.kire.re.nourishment.infrastructure.entity.NourishmentDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MongoNourishmentRepository extends ReactiveMongoRepository<NourishmentDocument, String> {
    Mono<NourishmentDocument> findByName(String name);
}