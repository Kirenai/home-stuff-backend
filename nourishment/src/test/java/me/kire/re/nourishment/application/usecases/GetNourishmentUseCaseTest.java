package me.kire.re.nourishment.application.usecases;

import me.kire.re.exceptions.model.nourishment.NourishmentNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetNourishmentUseCaseTest {
    @InjectMocks
    private GetNourishmentUseCase useCase;
    @Mock
    private NourishmentRepositoryPort port;

    @Test
    void testFindById() {
        String nourishmentId = "test-id";

        Nourishment output = new Nourishment();

        when(this.port.findById(anyString())).thenReturn(Mono.just(output));

        Mono<Nourishment> publisher = this.useCase.findById(nourishmentId);

        StepVerifier.create(publisher)
                .expectNext(output)
                .verifyComplete();

        verify(this.port).findById(any());
    }

    @Test
    void testFindByIdNotFoundException() {
        String nourishmentId = "test-id";

        when(this.port.findById(anyString())).thenReturn(Mono.error(new NourishmentNotFoundException("any")));

        Mono<Nourishment> publisher = this.useCase.findById(nourishmentId);

        StepVerifier.create(publisher)
                .expectError(NourishmentNotFoundException.class)
                .verify();

        verify(this.port).findById(any());
    }
}