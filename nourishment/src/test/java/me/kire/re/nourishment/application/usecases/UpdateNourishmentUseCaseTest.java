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
class UpdateNourishmentUseCaseTest {
    @InjectMocks
    private UpdateNourishmentUseCase useCase;
    @Mock
    private NourishmentRepositoryPort port;

    @Test
    void testUpdateNourishment() {
        Nourishment input = Nourishment.builder().name("Nourishment Name").build();
        Nourishment output = Nourishment.builder().name("Nourishment Name Updated").build();

        when(this.port.updateNourishment(anyString(), any()))
                .thenReturn(Mono.just(output));

        Mono<Nourishment> publisher = this.useCase.updateNourishment("test-id", input);

        StepVerifier.create(publisher)
                .expectNext(output)
                .verifyComplete();

        verify(this.port).updateNourishment(anyString(), any());
    }

    @Test
    void testUpdateNourishmentNotFoundException() {
        when(this.port.updateNourishment(anyString(), any()))
                .thenReturn(Mono.error(new NourishmentNotFoundException("message")));

        Mono<Nourishment> publisher = this.useCase.updateNourishment("test-id", Nourishment.builder().build());

        StepVerifier.create(publisher)
                .expectErrorMatches(throwable -> throwable instanceof NourishmentNotFoundException &&
                        throwable.getMessage().contains("message"))
                .verify();

        verify(this.port).updateNourishment(anyString(), any());
    }
}