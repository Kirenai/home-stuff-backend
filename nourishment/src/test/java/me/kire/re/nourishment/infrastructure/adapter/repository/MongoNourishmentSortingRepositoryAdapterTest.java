package me.kire.re.nourishment.infrastructure.adapter.repository;

import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.infrastructure.entity.NourishmentDocument;
import me.kire.re.nourishment.infrastructure.mapper.out.MongoNourishmentMapper;
import me.kire.re.nourishment.infrastructure.repository.MongoNourishmentSortingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MongoNourishmentSortingRepositoryAdapterTest {
    @InjectMocks
    private MongoNourishmentSortingRepositoryAdapter adapter;
    @Mock
    private MongoNourishmentSortingRepository nourishmentSortingRepository;
    @Mock
    private MongoNourishmentMapper mapper;
    @Mock
    private ReactiveMongoTemplate template;

    @Test
    void testCount() {
        when(this.nourishmentSortingRepository.count()).thenReturn(Mono.just(1L));

        Mono<Long> publisher = this.adapter.count();

        StepVerifier.create(publisher)
                .expectNext(1L)
                .verifyComplete();

        verify(this.nourishmentSortingRepository).count();
    }

    @Test
    void testFindAll() {
        NourishmentDocument nourishmentDocument = NourishmentDocument.builder()
                .nourishmentId("1")
                .name("Nourishment 1")
                .description("Description 1")
                .isAvailable(true)
                .build();

        Nourishment nourishment = Nourishment.builder()
                .nourishmentId("1")
                .name("Nourishment 1")
                .description("Description 1")
                .isAvailable(true)
                .build();

        when(this.template.find(any(Query.class), eq(NourishmentDocument.class))).thenReturn(Flux.just(nourishmentDocument));
        when(this.mapper.mapOutNourishmentEntityToNourishment(any()))
                .thenReturn(nourishment);

        Flux<Nourishment> publisher = this.adapter.findAll(Pageable.ofSize(1), "1", true);

        StepVerifier.create(publisher)
                .expectNext(nourishment)
                .verifyComplete();

        verify(template).find(any(Query.class), eq(NourishmentDocument.class));
        verify(this.mapper).mapOutNourishmentEntityToNourishment(any());
    }

    @Test
    void testFindAllUserIdAndIsAvailableNull() {
        NourishmentDocument nourishmentDocument = NourishmentDocument.builder()
                .nourishmentId("1")
                .name("Nourishment 1")
                .description("Description 1")
                .isAvailable(true)
                .build();

        Nourishment nourishment = Nourishment.builder()
                .nourishmentId("1")
                .name("Nourishment 1")
                .description("Description 1")
                .isAvailable(true)
                .build();

        when(this.template.find(any(Query.class), eq(NourishmentDocument.class))).thenReturn(Flux.just(nourishmentDocument));
        when(this.mapper.mapOutNourishmentEntityToNourishment(any()))
                .thenReturn(nourishment);

        Flux<Nourishment> publisher = this.adapter.findAll(Pageable.ofSize(1), null, null);

        StepVerifier.create(publisher)
                .expectNext(nourishment)
                .verifyComplete();

        verify(template).find(any(Query.class), eq(NourishmentDocument.class));
        verify(this.mapper).mapOutNourishmentEntityToNourishment(any());
    }
}