package me.kire.re.nourishment.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
//import me.kire.re.validation.exception.nourishment.NourishmentNotFoundException;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentRepositoryPort;
import me.kire.re.nourishment.infrastructure.mapper.out.MongoNourishmentMapper;
import me.kire.re.nourishment.infrastructure.repository.MongoNourishmentRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MongoNourishmentRepositoryAdapter implements NourishmentRepositoryPort {

    private final MongoNourishmentRepository mongoNourishmentRepository;
    private final MongoNourishmentMapper mapper;

    @Override
    public Mono<Nourishment> findById(String nourishmentId) {
        return this.mongoNourishmentRepository.findById(nourishmentId)
//                .switchIfEmpty(Mono.error(new NourishmentNotFoundException(String.format("Nourishment not found by id: %s", nourishmentId))))
                .map(this.mapper::mapOutNourishmentEntityToNourishment);
    }

    @Override
    public Mono<Nourishment> findByName(String name) {
        return this.mongoNourishmentRepository.findByName(name)
//                .switchIfEmpty(Mono.error(new NourishmentNotFoundException(String.format("Nourishment not found by name: %s", name))))
                .map(this.mapper::mapOutNourishmentEntityToNourishment);
    }

    @Override
    public Mono<Nourishment> createNourishment(Nourishment nourishment) {
        return Mono.just(this.mapper.mapInNourishmentToNourishmentEntity(nourishment))
                .flatMap(this.mongoNourishmentRepository::save)
                .map(this.mapper::mapOutNourishmentEntityToNourishment);
    }

    @Override
    public Mono<Nourishment> updateNourishment(String nourishmentId, Nourishment nourishment) {
        return this.mongoNourishmentRepository.findById(nourishmentId)
//                .switchIfEmpty(Mono.error(new NourishmentNotFoundException(String.format("Nourishment not found by id: %s", nourishmentId))))
                .map(nourishmentEntity -> {
                    nourishmentEntity.setName(nourishment.getName());
                    nourishmentEntity.setDescription(nourishment.getDescription());
                    nourishmentEntity.setImageUrl(nourishment.getImageUrl());
//                    nourishmentEntity.setNourishmentType(nourishment.getNourishmentType());
                    return nourishmentEntity;
                })
                .flatMap(this.mongoNourishmentRepository::save)
                .map(this.mapper::mapOutNourishmentEntityToNourishment);
    }

    @Override
    public Mono<Void> deleteNourishment(String nourishmentId) {
        return this.mongoNourishmentRepository.findById(nourishmentId)
//                .switchIfEmpty(Mono.error(new NourishmentNotFoundException(String.format("Nourishment not found by id: %s", nourishmentId))))
                .flatMap(this.mongoNourishmentRepository::delete);
    }

}
