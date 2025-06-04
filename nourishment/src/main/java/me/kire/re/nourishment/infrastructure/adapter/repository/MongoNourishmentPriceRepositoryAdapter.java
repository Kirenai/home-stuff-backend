package me.kire.re.nourishment.infrastructure.adapter.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.nourishment.domain.model.NourishmentPrice;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentPriceRepositoryPort;
import me.kire.re.nourishment.infrastructure.mapper.out.MongoNourishmentPriceMapper;
import me.kire.re.nourishment.infrastructure.repository.MongoNourishmentPriceRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MongoNourishmentPriceRepositoryAdapter implements NourishmentPriceRepositoryPort {
    private final MongoNourishmentPriceRepository mongoNourishmentPriceRepository;
    private final MongoNourishmentPriceMapper mapper;

    @Override
    public Flux<NourishmentPrice> findAll(String nourishmentId, Pageable pageable) {
        return this.mongoNourishmentPriceRepository.findAllByNourishmentId(nourishmentId, pageable)
                .map(this.mapper::mapOutNourishmentPrice);
    }

    @Override
    public Mono<NourishmentPrice> create(NourishmentPrice nourishmentPrice) {
        return Mono.just(this.mapper.mapInNourishmentPrice(nourishmentPrice))
                .flatMap(this.mongoNourishmentPriceRepository::save)
                .map(this.mapper::mapOutNourishmentPrice);
    }
}
