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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetNourishmentByNameUseCaseTest {
    @InjectMocks
    private GetNourishmentByNameUseCase useCase;
    @Mock
    private NourishmentRepositoryPort port;

    @Test
    void testExecute() {
        String name = "test-name";

        Nourishment nourishment = Nourishment.builder().build();

        when(this.port.findByName(anyString())).thenReturn(Mono.just(nourishment));

        Mono<Nourishment> publisher = this.useCase.execute(name);

        StepVerifier.create(publisher)
                .expectNext(nourishment)
                .verifyComplete();

        verify(this.port).findByName(anyString());
    }

    @Test
    void testExecuteNotFoundException() {
        String name = "non-existent-name";

        when(this.port.findByName(anyString())).thenReturn(Mono.error(new NourishmentNotFoundException("any")));

        Mono<Nourishment> publisher = this.useCase.execute(name);

        StepVerifier.create(publisher)
                .expectError(NourishmentNotFoundException.class)
                .verify();

        verify(this.port).findByName(anyString());
    }
}