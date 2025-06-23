package me.kire.re.nourishment.application.usecases;

import lombok.RequiredArgsConstructor;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.in.ListNourishmentsPort;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentSortingRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ListNourishmentsUseCase implements ListNourishmentsPort {

    private final NourishmentSortingRepositoryPort nourishmentSortingRepositoryPort;
    private final PageMapper pageMapper;

    @Override
    public Mono<Page<Nourishment>> getNourishments(Pageable pageable, String userId, Boolean isAvailable) {
        return this.nourishmentSortingRepositoryPort.findAll(pageable, userId, isAvailable)
                .collectList()
                .zipWith(this.nourishmentSortingRepositoryPort.count())
                .map(tuple -> this.pageMapper.toPage(tuple.getT1(), pageable, tuple.getT2()));
    }

}
