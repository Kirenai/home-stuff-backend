package me.kire.re.nourishment.application.factory;

import lombok.RequiredArgsConstructor;
import me.kire.re.nourishment.application.usecases.CreateNourishmentUseCase;
import me.kire.re.nourishment.application.usecases.DeleteNourishmentUseCase;
import me.kire.re.nourishment.application.usecases.GetNourishmentByNameUseCase;
import me.kire.re.nourishment.application.usecases.GetNourishmentUseCase;
import me.kire.re.nourishment.application.usecases.ListNourishmentsUseCase;
import me.kire.re.nourishment.application.usecases.UpdateNourishmentUseCase;
import me.kire.re.nourishment.domain.port.in.CreateNourishmentPort;
import me.kire.re.nourishment.domain.port.in.DeleteNourishmentPort;
import me.kire.re.nourishment.domain.port.in.GetNourishmentByNamePort;
import me.kire.re.nourishment.domain.port.in.GetNourishmentPort;
import me.kire.re.nourishment.domain.port.in.ListNourishmentsPort;
import me.kire.re.nourishment.domain.port.in.UpdateNourishmentPort;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentRepositoryPort;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentSortingRepositoryPort;

@RequiredArgsConstructor
public class NourishmentUseCaseFactory {

    private final NourishmentRepositoryPort nourishmentRepositoryPort;
    private final NourishmentSortingRepositoryPort nourishmentSortingRepositoryPort;


    public GetNourishmentPort getNourishmentPort() {
        return new GetNourishmentUseCase(this.nourishmentRepositoryPort);
    }

    public GetNourishmentByNamePort getNourishmentByNamePort() {
        return new GetNourishmentByNameUseCase(this.nourishmentRepositoryPort);
    }

    public ListNourishmentsPort listNourishmentsPort() {
        return new ListNourishmentsUseCase(this.nourishmentSortingRepositoryPort);
    }

    public CreateNourishmentPort createNourishmentPort() {
        return new CreateNourishmentUseCase(this.nourishmentRepositoryPort);
    }

    public UpdateNourishmentPort updateNourishmentPort() {
        return new UpdateNourishmentUseCase(this.nourishmentRepositoryPort);
    }

    public DeleteNourishmentPort deleteNourishmentPort() {
        return new DeleteNourishmentUseCase(this.nourishmentRepositoryPort);
    }
}
