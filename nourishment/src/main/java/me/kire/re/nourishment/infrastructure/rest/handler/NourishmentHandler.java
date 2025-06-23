package me.kire.re.nourishment.infrastructure.rest.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.validation.core.Validator;
import me.kire.re.nourishment.application.service.NourishmentService;
import me.kire.re.nourishment.infrastructure.rest.dto.CreateNourishmentRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.ListNourishmentsResponse;
import me.kire.re.nourishment.infrastructure.rest.dto.UpdateNourishmentRequest;
import me.kire.re.nourishment.infrastructure.mapper.in.CreateNourishmentMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.GetNourishmentMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.ListNourishmentMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.UpdateNourishmentMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class NourishmentHandler {

    private final NourishmentService nourishmentService;
    private final ListNourishmentMapper listNourishmentMapper;
    private final GetNourishmentMapper getNourishmentMapper;
    private final CreateNourishmentMapper createNourishmentMapper;
    private final UpdateNourishmentMapper updateNourishmentMapper;
    private final Validator validator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        log.info("Invoking NourishmentHandler.findAll method");
        String userId = request.queryParam("user.id").orElse(null);

        Boolean isAvailable = null;
        if (request.queryParam("isAvailable").isPresent()) {
            isAvailable = Boolean.valueOf(request.queryParam("isAvailable").get());
        }

        int page = Integer.parseInt(request.queryParam("page").orElse("0"));
        int size = Integer.parseInt(request.queryParam("size").orElse("5"));
        String[] sort = request.queryParam("sort").orElse("nourishmentId,ASC").split(",");

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));
        Mono<Page<ListNourishmentsResponse>> response = this.nourishmentService.getNourishments(pageable, userId, isAvailable)
                .map(nourishments -> nourishments.map(this.listNourishmentMapper::mapOutNourishmentToListNourishmentsResponse));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(response, Page.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        log.info("Invoking NourishmentHandler.findById method");
        String nourishmentId = request.pathVariable("nourishmentId");
        return this.nourishmentService.getNourishmentById(nourishmentId)
                .map(this.getNourishmentMapper::mapOutNourishmentToGetNourishmentResponse)
                .flatMap(response -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response));
    }

    public Mono<ServerResponse> findByName(ServerRequest serverRequest) {
        log.info("Invoking NourishmentHandler.findByName method");
        String name = serverRequest.pathVariable("name");
        return this.nourishmentService.getNourishmentByName(name)
                .map(this.getNourishmentMapper::mapOutNourishmentToGetNourishmentResponse)
                .flatMap(response -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        log.info("Invoking NourishmentHandler.create method");
        String userId = request.pathVariable("userId");
        String categoryId = request.pathVariable("categoryId");
        return request.bodyToMono(CreateNourishmentRequest.class)
                .flatMap(this.validator::validate)
                .map(this.createNourishmentMapper::mapInCreateNourishmentRequestToNourishment)
                .flatMap(nourishment -> this.nourishmentService
                        .createNourishment(Long.valueOf(userId), Long.valueOf(categoryId), nourishment))
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        log.info("Invoking NourishmentHandler.update method");
        String nourishmentId = request.pathVariable("nourishmentId");
        return request.bodyToMono(UpdateNourishmentRequest.class)
                .flatMap(this.validator::validate)
                .map(this.updateNourishmentMapper::mapInUpdateNourishmentRequestToNourishment)
                .flatMap(nourishment -> this.nourishmentService
                        .updateNourishment(nourishmentId, nourishment))
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        log.info("Invoking NourishmentHandler.delete method");
        String nourishmentId = request.pathVariable("nourishmentId");
        return this.nourishmentService.deleteNourishment(nourishmentId)
                .then(Mono.defer(() -> ServerResponse.noContent().build()));
    }

}