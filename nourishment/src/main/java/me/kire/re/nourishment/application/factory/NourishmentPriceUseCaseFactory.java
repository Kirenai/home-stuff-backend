package me.kire.re.nourishment.application.factory;

import lombok.RequiredArgsConstructor;
import me.kire.re.nourishment.application.usecases.CreateNourishmentPriceUseCase;
import me.kire.re.nourishment.application.usecases.ListNourishmentsPriceUseCase;
import me.kire.re.nourishment.domain.port.in.CreateNourishmentPricePort;
import me.kire.re.nourishment.domain.port.in.ListNourishmentsPricePort;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentPriceRepositoryPort;

@RequiredArgsConstructor
public class NourishmentPriceUseCaseFactory {
    private final NourishmentPriceRepositoryPort nourishmentPriceRepositoryPort;

    public ListNourishmentsPricePort listNourishmentsPricePort() {
        return new ListNourishmentsPriceUseCase(this.nourishmentPriceRepositoryPort);
    }

    public CreateNourishmentPricePort createNourishmentPricePort() {
        return new CreateNourishmentPriceUseCase(this.nourishmentPriceRepositoryPort);
    }
}
