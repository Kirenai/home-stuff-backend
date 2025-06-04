package me.kire.re.nourishment.infrastructure.rest.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.nourishment.application.service.NourishmentPriceService;
import me.kire.re.nourishment.infrastructure.rest.dto.price.CreateNourishmentPriceRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.price.ListNourishmentsPriceResponse;
import me.kire.re.nourishment.infrastructure.mapper.in.CreateNourishmentPriceMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.ListNourishmentsPriceMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class NourishmentPriceHandler {
    private final NourishmentPriceService nourishmentPriceService;
    private final ListNourishmentsPriceMapper listNourishmentsPriceMapper;
    private final CreateNourishmentPriceMapper createNourishmentPriceMapper;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        log.info("Invoking NourishmentPriceHandler.getAll method");
        String nourishmentId = serverRequest.pathVariable("nourishmentId");
        int page = Integer.parseInt(serverRequest.queryParam("page").orElse("0"));
        int size = Integer.parseInt(serverRequest.queryParam("size").orElse("4"));
        String[] sort = serverRequest.queryParam("sort").orElse("nourishmentPriceId,ASC").split(",");
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(sort[1]), sort[0]));
        Flux<ListNourishmentsPriceResponse> response = this.nourishmentPriceService.findAll(nourishmentId, pageable)
                .map(this.listNourishmentsPriceMapper::mapOutListNourishmentsPriceResponse);
        return ServerResponse.ok().body(response, ListNourishmentsPriceResponse.class);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        log.info("Invoking NourishmentPriceService.save method");
        String nourishmentId = serverRequest.pathVariable("nourishmentId");
        return serverRequest.bodyToMono(CreateNourishmentPriceRequest.class)
                .map(request -> this.createNourishmentPriceMapper.mapInNourishmentPrice(nourishmentId, request))
                .flatMap(this.nourishmentPriceService::create)
                .map(this.createNourishmentPriceMapper::mapOutCreateNourishmentPriceResponse)
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(response));
    }
}
