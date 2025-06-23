package me.kire.re.nourishment.application.usecases;

import me.kire.re.exceptions.model.nourishment.NourishmentNotFoundException;
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
class DeleteNourishmentUseCaseTest {
    @InjectMocks
    private DeleteNourishmentUseCase useCase;
    @Mock
    private NourishmentRepositoryPort port;

    @Test
    void testExecute() {
        String nourishmentId = "test-id";

        when(this.port.deleteNourishment(any())).thenReturn(Mono.empty());

        Mono<Void> publisher = this.useCase.execute(nourishmentId);

        StepVerifier.create(publisher)
                .verifyComplete();

        verify(this.port).deleteNourishment(any());
    }

    @Test
    void testExecuteThrowException() {
        String nourishmentId = "test-id";

        when(this.port.deleteNourishment(any())).thenReturn(Mono.error(new NourishmentNotFoundException("any")));

        Mono<Void> publisher = this.useCase.execute(nourishmentId);

        StepVerifier.create(publisher)
                .expectError(NourishmentNotFoundException.class)
                .verify();

        verify(this.port).deleteNourishment(any());
    }
}