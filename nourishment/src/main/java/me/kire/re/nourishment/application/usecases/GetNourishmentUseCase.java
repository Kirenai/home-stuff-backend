package me.kire.re.nourishment.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.in.GetNourishmentPort;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentRepositoryPort;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetNourishmentUseCase implements GetNourishmentPort {

    private final NourishmentRepositoryPort nourishmentRepositoryPort;

    @Override
    public Mono<Nourishment> findById(String nourishmentId) {
        return this.nourishmentRepositoryPort.findById(nourishmentId);
    }

}
