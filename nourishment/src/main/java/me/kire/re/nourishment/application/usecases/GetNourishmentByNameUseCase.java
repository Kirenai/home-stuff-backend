package me.kire.re.nourishment.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.in.GetNourishmentByNamePort;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentRepositoryPort;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetNourishmentByNameUseCase implements GetNourishmentByNamePort {
    private final NourishmentRepositoryPort nourishmentRepositoryPort;

    @Override
    public Mono<Nourishment> execute(String name) {
        return this.nourishmentRepositoryPort.findByName(name);
    }
}
