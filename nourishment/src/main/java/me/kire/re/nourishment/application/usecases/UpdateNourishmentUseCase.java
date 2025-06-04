package me.kire.re.nourishment.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.in.UpdateNourishmentPort;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentRepositoryPort;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateNourishmentUseCase implements UpdateNourishmentPort {

    private final NourishmentRepositoryPort nourishmentRepositoryPort;

    @Override
    public Mono<Nourishment> updateNourishment(String nourishmentId, Nourishment nourishment) {
        return this.nourishmentRepositoryPort.updateNourishment(nourishmentId, nourishment);
    }

}
