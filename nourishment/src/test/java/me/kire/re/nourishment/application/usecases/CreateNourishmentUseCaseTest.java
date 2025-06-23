package me.kire.re.nourishment.application.usecases;

import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentRepositoryPort;
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
class CreateNourishmentUseCaseTest {
    @InjectMocks
    private CreateNourishmentUseCase useCase;
    @Mock
    private NourishmentRepositoryPort port;

    @Test
    void testCreateNourishment() {
        Nourishment nourishment = Nourishment.builder().build();

        when(this.port.createNourishment(any())).thenReturn(Mono.just(nourishment));

        Mono<Nourishment> publisher = this.useCase.createNourishment(nourishment);

        StepVerifier.create(publisher)
                .expectNext(nourishment)
                .verifyComplete();

        verify(this.port).createNourishment(any());
    }
}