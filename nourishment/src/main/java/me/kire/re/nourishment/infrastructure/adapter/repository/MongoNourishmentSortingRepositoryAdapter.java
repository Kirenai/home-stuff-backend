package me.kire.re.nourishment.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentSortingRepositoryPort;
import me.kire.re.nourishment.infrastructure.entity.NourishmentDocument;
import me.kire.re.nourishment.infrastructure.mapper.out.MongoNourishmentMapper;
import me.kire.re.nourishment.infrastructure.repository.MongoNourishmentSortingRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongoNourishmentSortingRepositoryAdapter implements NourishmentSortingRepositoryPort {
    private final MongoNourishmentSortingRepository nourishmentSortingRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final MongoNourishmentMapper mapper;

    @Override
    public Mono<Long> count() {
        return this.nourishmentSortingRepository.count();
    }

    @Override
    public Flux<Nourishment> findAll(Pageable pageable, String userId, Boolean isAvailable) {
        log.info("invoking MongoNourishmentSortingRepositoryAdapter.findAll method");
        Query query = new Query();

        if (userId != null) {
            query.addCriteria(Criteria.where("userId").is(userId));
        }

        if (isAvailable != null) {
            query.addCriteria(Criteria.where("isAvailable").is(isAvailable));
        }

        query.with(pageable);

        return this.reactiveMongoTemplate.find(query, NourishmentDocument.class)
                .map(this.mapper::mapOutNourishmentEntityToNourishment);
    }

}
