package me.kire.re.nourishment.application.usecases;

import me.kire.re.nourishment.domain.model.NourishmentPrice;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentPriceRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateNourishmentPriceUseCaseTest {
    @InjectMocks
    private CreateNourishmentPriceUseCase useCase;
    @Mock
    private NourishmentPriceRepositoryPort repository;

    @Test
    void testExecute() {
        NourishmentPrice nourishmentPrice = NourishmentPrice.builder().build();

        when(this.repository.create(any())).thenReturn(Mono.just(nourishmentPrice));

        Mono<NourishmentPrice> publisher = this.useCase.execute(nourishmentPrice);

        StepVerifier.create(publisher)
                .expectNext(nourishmentPrice)
                .verifyComplete();

        verify(this.repository).create(any());
    }
}