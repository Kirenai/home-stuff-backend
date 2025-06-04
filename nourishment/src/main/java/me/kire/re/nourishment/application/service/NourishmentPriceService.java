package me.kire.re.nourishment.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.nourishment.domain.model.NourishmentPrice;
import me.kire.re.nourishment.domain.port.in.CreateNourishmentPricePort;
import me.kire.re.nourishment.domain.port.in.ListNourishmentsPricePort;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class NourishmentPriceService {
    private final ListNourishmentsPricePort listNourishmentsPricePort;
    private final CreateNourishmentPricePort nourishmentPriceRepository;

    public Flux<NourishmentPrice> findAll(String nourishmentId, PageRequest pageable) {
        log.info("Invoking NourishmentPriceService.findAll method");
        return this.listNourishmentsPricePort.execute(nourishmentId, pageable);
    }

    @Transactional
    public Mono<NourishmentPrice> create(NourishmentPrice nourishmentPrice) {
        log.info("Invoking NourishmentPriceService.create method");
        return this.nourishmentPriceRepository.execute(nourishmentPrice);
    }
}