package me.kire.re.nourishment.application.usecases;

import me.kire.re.nourishment.domain.model.NourishmentPrice;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentPriceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListNourishmentsPriceUseCaseTest {
    @InjectMocks
    private ListNourishmentsPriceUseCase useCase;
    @Mock
    private NourishmentPriceRepositoryPort port;

    @Test
    void testExecute() {
        NourishmentPrice nourishmentPrice = NourishmentPrice.builder().build();

        when(this.port.findAll(anyString(), any())).thenReturn(Flux.just(nourishmentPrice));

        Flux<NourishmentPrice> publisher = this.useCase.execute("nourishmentId", Pageable.unpaged());

        StepVerifier.create(publisher)
                .expectNext(nourishmentPrice)
                .verifyComplete();

        verify(this.port).findAll(anyString(), any());
    }
}