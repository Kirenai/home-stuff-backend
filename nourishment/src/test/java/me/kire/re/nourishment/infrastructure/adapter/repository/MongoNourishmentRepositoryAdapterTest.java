package me.kire.re.nourishment.infrastructure.adapter.repository;

import me.kire.re.exceptions.model.nourishment.NourishmentNotFoundException;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.infrastructure.entity.NourishmentDocument;
import me.kire.re.nourishment.infrastructure.mapper.out.MongoNourishmentMapper;
import me.kire.re.nourishment.infrastructure.repository.MongoNourishmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MongoNourishmentRepositoryAdapterTest {
    @InjectMocks
    private MongoNourishmentRepositoryAdapter adapter;
    @Mock
    private MongoNourishmentRepository mongoNourishmentRepository;
    @Mock
    private MongoNourishmentMapper mapper;

    @Test
    void testFindById() {
        NourishmentDocument nourishmentPriceDocument = NourishmentDocument.builder()
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

        when(this.mongoNourishmentRepository.findById(anyString()))
                .thenReturn(Mono.just(nourishmentPriceDocument));
        when(this.mapper.mapOutNourishmentEntityToNourishment(any()))
                .thenReturn(nourishment);

        Mono<Nourishment> publisher = this.adapter.findById("123");

        StepVerifier.create(publisher)
                .assertNext(value -> {
                    assertEquals(nourishment.getNourishmentId(), value.getNourishmentId());
                    assertEquals(nourishment.getName(), value.getName());
                    assertEquals(nourishment.getDescription(), value.getDescription());
                    assertEquals(nourishment.getIsAvailable(), value.getIsAvailable());
                })
                .verifyComplete();

        verify(this.mongoNourishmentRepository).findById(anyString());
        verify(this.mapper).mapOutNourishmentEntityToNourishment(any());
    }

    @Test
    void testFindByIdThrowExceptionNourishmentNotFoundException() {
        when(this.mongoNourishmentRepository.findById(anyString()))
                .thenReturn(Mono.empty());

        Mono<Nourishment> publisher = this.adapter.findById("123");

        StepVerifier.create(publisher)
                .expectError(NourishmentNotFoundException.class)
                .verify();

        verify(this.mongoNourishmentRepository).findById(anyString());
        verify(this.mapper, times(0)).mapOutNourishmentEntityToNourishment(any());
    }

    @Test
    void testFindByName() {
        NourishmentDocument nourishmentPriceDocument = NourishmentDocument.builder()
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

        when(this.mongoNourishmentRepository.findByName(anyString()))
                .thenReturn(Mono.just(nourishmentPriceDocument));
        when(this.mapper.mapOutNourishmentEntityToNourishment(any()))
                .thenReturn(nourishment);

        Mono<Nourishment> publisher = this.adapter.findByName("Nourishment 1");

        StepVerifier.create(publisher)
                .assertNext(value -> {
                    assertEquals(nourishment.getNourishmentId(), value.getNourishmentId());
                    assertEquals(nourishment.getName(), value.getName());
                    assertEquals(nourishment.getDescription(), value.getDescription());
                    assertEquals(nourishment.getIsAvailable(), value.getIsAvailable());
                })
                .verifyComplete();

        verify(this.mongoNourishmentRepository).findByName(anyString());
        verify(this.mapper).mapOutNourishmentEntityToNourishment(any());
    }

    @Test
    void testFindByNameThrowExceptionNourishmentNotFoundException() {
        when(this.mongoNourishmentRepository.findByName(anyString()))
                .thenReturn(Mono.empty());

        Mono<Nourishment> publisher = this.adapter.findByName("Nourishment 1");

        StepVerifier.create(publisher)
                .expectError(NourishmentNotFoundException.class)
                .verify();

        verify(this.mongoNourishmentRepository).findByName(anyString());
        verify(this.mapper, times(0)).mapOutNourishmentEntityToNourishment(any());
    }

    @Test
    void testCreateNourishment() {
        Nourishment nourishment = Nourishment.builder()
                .nourishmentId("1")
                .name("Nourishment 1")
                .description("Description 1")
                .isAvailable(true)
                .build();

        NourishmentDocument nourishmentDocument = NourishmentDocument.builder()
                .nourishmentId("1")
                .name("Nourishment 1")
                .description("Description 1")
                .isAvailable(true)
                .build();

        when(this.mapper.mapInNourishmentToNourishmentEntity(any()))
                .thenReturn(nourishmentDocument);
        when(this.mongoNourishmentRepository.save(any()))
                .thenReturn(Mono.just(nourishmentDocument));
        when(this.mapper.mapOutNourishmentEntityToNourishment(any()))
                .thenReturn(nourishment);

        Mono<Nourishment> publisher = this.adapter.createNourishment(nourishment);

        StepVerifier.create(publisher)
                .assertNext(value -> {
                    assertEquals(nourishment.getNourishmentId(), value.getNourishmentId());
                    assertEquals(nourishment.getName(), value.getName());
                    assertEquals(nourishment.getDescription(), value.getDescription());
                    assertEquals(nourishment.getIsAvailable(), value.getIsAvailable());
                })
                .verifyComplete();

        verify(this.mapper).mapInNourishmentToNourishmentEntity(any());
        verify(this.mongoNourishmentRepository).save(any());
        verify(this.mapper).mapOutNourishmentEntityToNourishment(any());
    }

    @Test
    void testUpdateNourishment() {
        Nourishment nourishment = Nourishment.builder()
                .nourishmentId("12")
                .name("Nourishment Updated")
                .description("Description Updated")
                .isAvailable(false)
                .build();

        NourishmentDocument nourishmentDocument = NourishmentDocument.builder()
                .nourishmentId("1")
                .name("Nourishment 1")
                .description("Description 1")
                .isAvailable(true)
                .build();

        when(this.mongoNourishmentRepository.findById(anyString()))
                .thenReturn(Mono.just(nourishmentDocument));
        when(this.mongoNourishmentRepository.save(any()))
                .thenReturn(Mono.just(nourishmentDocument));
        when(this.mapper.mapOutNourishmentEntityToNourishment(any()))
                .thenReturn(nourishment);

        Mono<Nourishment> publisher = this.adapter.updateNourishment("1", nourishment);

        StepVerifier.create(publisher)
                .assertNext(value -> {
                    assertEquals(nourishment.getNourishmentId(), value.getNourishmentId());
                    assertEquals(nourishment.getName(), value.getName());
                    assertEquals(nourishment.getDescription(), value.getDescription());
                    assertEquals(nourishment.getIsAvailable(), value.getIsAvailable());
                })
                .verifyComplete();

        verify(this.mongoNourishmentRepository).findById(anyString());
        verify(this.mapper).mapOutNourishmentEntityToNourishment(any());
        verify(this.mongoNourishmentRepository).save(any());
    }

    @Test
    void testUpdateNourishmentThrowExceptionNourishmentNotFoundException() {
        Nourishment nourishment = Nourishment.builder()
                .nourishmentId("12")
                .name("Nourishment Updated")
                .description("Description Updated")
                .isAvailable(false)
                .build();

        when(this.mongoNourishmentRepository.findById(anyString()))
                .thenReturn(Mono.empty());

        Mono<Nourishment> publisher = this.adapter.updateNourishment("1", nourishment);

        StepVerifier.create(publisher)
                .expectError(NourishmentNotFoundException.class)
                .verify();

        verify(this.mongoNourishmentRepository).findById(anyString());
        verify(this.mapper, times(0)).mapOutNourishmentEntityToNourishment(any());
        verify(this.mongoNourishmentRepository, times(0)).save(any());
    }

    @Test
    void testDeleteNourishment() {
        NourishmentDocument nourishmentDocument = NourishmentDocument.builder()
                .nourishmentId("1")
                .name("Nourishment to Delete")
                .description("Description 1")
                .isAvailable(true)
                .build();

        when(this.mongoNourishmentRepository.findById(anyString()))
                .thenReturn(Mono.just(nourishmentDocument));
        when(this.mongoNourishmentRepository.delete(any()))
                .thenReturn(Mono.empty());

        Mono<Void> publisher = this.adapter.deleteNourishment("1");

        StepVerifier.create(publisher)
                .verifyComplete();

        verify(this.mongoNourishmentRepository).findById(anyString());
        verify(this.mongoNourishmentRepository).delete(any());
    }

    @Test
    void testDeleteNourishmentThrowExceptionNourishmentNotFoundException() {
        when(this.mongoNourishmentRepository.findById(anyString()))
                .thenReturn(Mono.empty());

        Mono<Void> publisher = this.adapter.deleteNourishment("1");

        StepVerifier.create(publisher)
                .expectError(NourishmentNotFoundException.class)
                .verify();

        verify(this.mongoNourishmentRepository).findById(anyString());
        verify(this.mongoNourishmentRepository, times(0)).delete(any());
    }
}