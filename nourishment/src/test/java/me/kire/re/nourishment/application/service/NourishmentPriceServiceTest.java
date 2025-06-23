package me.kire.re.nourishment.application.service;

import me.kire.re.nourishment.domain.model.NourishmentPrice;
import me.kire.re.nourishment.domain.port.in.CreateNourishmentPricePort;
import me.kire.re.nourishment.domain.port.in.ListNourishmentsPricePort;
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
class NourishmentPriceServiceTest {
    @InjectMocks
    private NourishmentPriceService nourishmentPriceService;
    @Mock
    private ListNourishmentsPricePort listNourishmentsPricePort;
    @Mock
    private CreateNourishmentPricePort nourishmentPriceRepository;

    @Test
    void testFindAll() {
        NourishmentPrice nourishmentPrice = NourishmentPrice.builder()
                .nourishmentId("1")
                .nourishmentPriceId("123")
                .price(new BigDecimal("100.00"))
                .build();
        when(this.listNourishmentsPricePort.execute(anyString(), any()))
                .thenReturn(Flux.just(nourishmentPrice));

        Flux<NourishmentPrice> publisher = this.nourishmentPriceService.findAll("1", PageRequest.of(0, 10));

        StepVerifier
                .create(publisher)
                .assertNext(value -> {
                    assertEquals(nourishmentPrice.nourishmentId(), value.nourishmentId());
                    assertEquals(nourishmentPrice.nourishmentPriceId(), value.nourishmentPriceId());
                    assertEquals(nourishmentPrice.price(), value.price());
                })
                .verifyComplete();

        verify(this.listNourishmentsPricePort).execute(anyString(), any());
    }

    @Test
    void testFindAllEmpty() {
        when(this.listNourishmentsPricePort.execute(anyString(), any()))
                .thenReturn(Flux.empty());

        Flux<NourishmentPrice> publisher = this.nourishmentPriceService.findAll("1", PageRequest.of(0, 10));

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.listNourishmentsPricePort).execute(anyString(), any());
    }

    @Test
    void testCreate() {
        NourishmentPrice nourishmentPrice = NourishmentPrice.builder()
                .nourishmentId("1")
                .nourishmentPriceId("123")
                .price(new BigDecimal("100.00"))
                .build();
        when(this.nourishmentPriceRepository.execute(any()))
                .thenReturn(Mono.just(nourishmentPrice));

        Mono<NourishmentPrice> publisher = this.nourishmentPriceService
                .create(nourishmentPrice);

        StepVerifier
                .create(publisher)
                .assertNext(value -> {
                    assertEquals(nourishmentPrice.nourishmentId(), value.nourishmentId());
                    assertEquals(nourishmentPrice.nourishmentPriceId(), value.nourishmentPriceId());
                    assertEquals(nourishmentPrice.price(), value.price());
                })
                .verifyComplete();

        verify(this.nourishmentPriceRepository).execute(any());
    }
}