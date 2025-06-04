package me.kire.re.nourishment.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.in.CreateNourishmentPort;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentRepositoryPort;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateNourishmentUseCase implements CreateNourishmentPort {

    private final NourishmentRepositoryPort nourishmentRepositoryPort;

    @Override
    public Mono<Nourishment> createNourishment(Nourishment nourishment) {
        return this.nourishmentRepositoryPort.createNourishment(nourishment);
    }
}
