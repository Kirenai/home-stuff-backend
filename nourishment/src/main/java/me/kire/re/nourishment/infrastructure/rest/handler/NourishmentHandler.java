package me.kire.re.nourishment.infrastructure.rest.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.nourishment.application.service.NourishmentService;
import me.kire.re.nourishment.infrastructure.mapper.in.CreateNourishmentMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.GetNourishmentMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.ListNourishmentMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.UpdateNourishmentMapper;
import me.kire.re.nourishment.infrastructure.rest.dto.CreateNourishmentRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.ListNourishmentsResponse;
import me.kire.re.nourishment.infrastructure.rest.dto.UpdateNourishmentRequest;
import me.kire.re.validation.core.Validator;
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
        log.info("Invoking NourishmentHandler.findAll with params: {}", request.queryParams());
        String userId = request.queryParam("user.id").orElse(null);

        Boolean isAvailable = null;
        if (request.queryParam("isAvailable").isPresent()) {
            try {
                isAvailable = Boolean.valueOf(request.queryParam("isAvailable").get());
            } catch (Exception e) {
                log.warn("Invalid isAvailable parameter: {}", request.queryParam("isAvailable").get());
                return ServerResponse.badRequest().bodyValue("{\"error\": \"Invalid isAvailable parameter\"}");
            }
        }

        int page;
        int size;
        String[] sort;
        try {
            page = Integer.parseInt(request.queryParam("page").orElse("0"));
            size = Integer.parseInt(request.queryParam("size").orElse("5"));
            sort = request.queryParam("sort").orElse("nourishmentId,ASC").split(",");
            if (sort.length != 2) throw new IllegalArgumentException();
        } catch (Exception e) {
            log.warn("Invalid pagination or sort parameters");
            return ServerResponse.badRequest().bodyValue("{\"error\": \"Invalid pagination or sort parameters\"}");
        }

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));
        Mono<Page<ListNourishmentsResponse>> response = this.nourishmentService.getNourishments(pageable, userId, isAvailable)
                .map(nourishments -> nourishments.map(this.listNourishmentMapper::mapOutNourishmentToListNourishmentsResponse));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(response, Page.class)
                .onErrorResume(e -> {
                    log.error("Error in {}.{}: ", this.getClass().getSimpleName(), "findAll", e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("{\"error\": \"Error retrieving nourishments.\"}");
                });
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        log.info("Invoking NourishmentHandler.findById with nourishmentId: {}", request.pathVariable("nourishmentId"));
        String nourishmentId = request.pathVariable("nourishmentId");
        return this.nourishmentService.getNourishmentById(nourishmentId)
                .map(this.getNourishmentMapper::mapOutNourishmentToGetNourishmentResponse)
                .flatMap(response -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> {
                    log.error("Error in findById: ", e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("{\"error\": \"Error retrieving nourishment by id.\"}");
                });
    }

    public Mono<ServerResponse> findByName(ServerRequest serverRequest) {
        log.info("Invoking NourishmentHandler.findByName with name: {}", serverRequest.pathVariable("name"));
        String name = serverRequest.pathVariable("name");
        return this.nourishmentService.getNourishmentByName(name)
                .map(this.getNourishmentMapper::mapOutNourishmentToGetNourishmentResponse)
                .flatMap(response -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> {
                    log.error("Error in findByName: ", e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("{\"error\": \"Error retrieving nourishment by name.\"}");
                });
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        log.info("Invoking NourishmentHandler.create with userId: {} and categoryId: {}", request.pathVariable("userId"), request.pathVariable("categoryId"));
        String userId = request.pathVariable("userId");
        String categoryId = request.pathVariable("categoryId");
        long userIdLong;
        long categoryIdLong;
        try {
            userIdLong = Long.parseLong(userId);
            categoryIdLong = Long.parseLong(categoryId);
        } catch (Exception e) {
            log.warn("Invalid userId or categoryId: userId={}, categoryId={}", userId, categoryId);
            return ServerResponse.badRequest().bodyValue("{\"error\": \"Invalid userId or categoryId\"}");
        }
        return request.bodyToMono(CreateNourishmentRequest.class)
                .flatMap(this.validator::validate)
                .map(this.createNourishmentMapper::mapInCreateNourishmentRequestToNourishment)
                .flatMap(nourishment -> this.nourishmentService.createNourishment(userIdLong, categoryIdLong, nourishment))
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .onErrorResume(e -> {
                    log.error("Error in create: ", e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("{\"error\": \"Error creating nourishment.\"}");
                });
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        log.info("Invoking NourishmentHandler.update with nourishmentId: {}", request.pathVariable("nourishmentId"));
        String nourishmentId = request.pathVariable("nourishmentId");
        return request.bodyToMono(UpdateNourishmentRequest.class)
                .flatMap(this.validator::validate)
                .map(this.updateNourishmentMapper::mapInUpdateNourishmentRequestToNourishment)
                .flatMap(nourishment -> this.nourishmentService.updateNourishment(nourishmentId, nourishment))
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response))
                .onErrorResume(e -> {
                    log.error("Error in update: ", e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("{\"error\": \"Error updating nourishment.\"}");
                });
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        log.info("Invoking NourishmentHandler.delete with nourishmentId: {}", request.pathVariable("nourishmentId"));
        String nourishmentId = request.pathVariable("nourishmentId");
        return this.nourishmentService.deleteNourishment(nourishmentId)
                .then(Mono.defer(() -> ServerResponse.noContent().build()))
                .onErrorResume(e -> {
                    log.error("Error in delete: ", e);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("{\"error\": \"Error deleting nourishment.\"}");
                });
    }

}