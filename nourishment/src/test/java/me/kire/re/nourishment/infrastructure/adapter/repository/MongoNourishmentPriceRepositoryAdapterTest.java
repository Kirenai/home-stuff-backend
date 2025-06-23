package me.kire.re.nourishment.infrastructure.adapter.repository;

import me.kire.re.nourishment.domain.model.NourishmentPrice;
import me.kire.re.nourishment.infrastructure.entity.NourishmentPriceDocument;
import me.kire.re.nourishment.infrastructure.mapper.out.MongoNourishmentPriceMapper;
import me.kire.re.nourishment.infrastructure.repository.MongoNourishmentPriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MongoNourishmentPriceRepositoryAdapterTest {
    @InjectMocks
    private MongoNourishmentPriceRepositoryAdapter adapter;
    @Mock
    private MongoNourishmentPriceRepository mongoNourishmentPriceRepository;
    @Mock
    private MongoNourishmentPriceMapper mapper;

    @Test
    void testFindAll() {
        NourishmentPriceDocument nourishmentPriceDocument = NourishmentPriceDocument.builder()
                .nourishmentId("1")
                .nourishmentPriceId("123")
                .price(new BigDecimal("100.00"))
                .build();

        NourishmentPrice nourishmentPrice = NourishmentPrice.builder()
                .nourishmentId("1")
                .nourishmentPriceId("123")
                .price(new BigDecimal("100.00"))
                .build();

        when(this.mongoNourishmentPriceRepository.findAllByNourishmentId(anyString(), any()))
                .thenReturn(Flux.just(nourishmentPriceDocument));
        when(this.mapper.mapOutNourishmentPrice(any()))
                .thenReturn(nourishmentPrice);

        Flux<NourishmentPrice> publisher = this.adapter.findAll("1", PageRequest.ofSize(1));

        StepVerifier
                .create(publisher)
                .assertNext(value -> {
                    assertEquals(nourishmentPrice.nourishmentId(), value.nourishmentId());
                    assertEquals(nourishmentPrice.nourishmentPriceId(), value.nourishmentPriceId());
                    assertEquals(nourishmentPrice.price(), value.price());
                })
                .verifyComplete();

        verify(this.mapper).mapOutNourishmentPrice(any());
    }

    @Test
    void testCreate() {
        NourishmentPrice nourishmentPrice = NourishmentPrice.builder()
                .nourishmentId("1")
                .nourishmentPriceId("123")
                .price(new BigDecimal("100.00"))
                .build();

        NourishmentPriceDocument nourishmentPriceDocument = NourishmentPriceDocument.builder()
                .nourishmentId("1")
                .nourishmentPriceId("123")
                .price(new BigDecimal("100.00"))
                .build();

        when(this.mapper.mapInNourishmentPrice(nourishmentPrice))
                .thenReturn(nourishmentPriceDocument);
        when(this.mongoNourishmentPriceRepository.save(any()))
                .thenReturn(Mono.just(nourishmentPriceDocument));
        when(this.mapper.mapOutNourishmentPrice(any()))
                .thenReturn(nourishmentPrice);

        Mono<NourishmentPrice> publisher = this.adapter.create(nourishmentPrice);

        StepVerifier
                .create(publisher)
                .assertNext(value -> {
                    assertEquals(nourishmentPrice.nourishmentId(), value.nourishmentId());
                    assertEquals(nourishmentPrice.nourishmentPriceId(), value.nourishmentPriceId());
                    assertEquals(nourishmentPrice.price(), value.price());
                })
                .verifyComplete();

        verify(this.mapper).mapInNourishmentPrice(any());
        verify(this.mongoNourishmentPriceRepository).save(any());
    }
}