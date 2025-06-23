package me.kire.re.nourishment.infrastructure.rest.router;

import me.kire.re.nourishment.application.service.NourishmentService;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.infrastructure.mapper.in.CreateNourishmentMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.GetNourishmentMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.ListNourishmentMapper;
import me.kire.re.nourishment.infrastructure.mapper.in.UpdateNourishmentMapper;
import me.kire.re.nourishment.infrastructure.rest.dto.CreateNourishmentRequest;
import me.kire.re.nourishment.infrastructure.rest.dto.GetNourishmentResponse;
import me.kire.re.nourishment.infrastructure.rest.dto.ListNourishmentsResponse;
import me.kire.re.nourishment.infrastructure.rest.dto.UpdateNourishmentRequest;
import me.kire.re.nourishment.infrastructure.rest.handler.NourishmentHandler;
import me.kire.re.validation.core.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {NourishmentRouter.class, NourishmentHandler.class})
class NourishmentRouterTest {
    private WebTestClient client;

    @MockitoBean
    private NourishmentService nourishmentService;
    @MockitoBean
    private ListNourishmentMapper listNourishmentMapper;
    @MockitoBean
    private GetNourishmentMapper getNourishmentMapper;
    @MockitoBean
    private CreateNourishmentMapper createNourishmentMapper;
    @MockitoBean
    private UpdateNourishmentMapper updateNourishmentMapper;
    @MockitoBean
    private Validator validator;

    private final StringBuilder URL = new StringBuilder("/nourishments");

    @BeforeEach
    void setUp() {
        NourishmentHandler handler = new NourishmentHandler(
                nourishmentService,
                listNourishmentMapper,
                getNourishmentMapper,
                createNourishmentMapper,
                updateNourishmentMapper,
                validator
        );
        RouterFunction<ServerResponse> route = RouterFunctions.route()
                .GET("/nourishments", handler::findAll)
                .GET("/nourishments/{nourishmentId}", handler::findById)
                .GET("/nourishments/name/{name}", handler::findByName)
                .POST("/nourishments/user/{userId}/category/{categoryId}", handler::create)
                .PUT("/nourishments/{nourishmentId}", handler::update)
                .DELETE("/nourishments/{nourishmentId}", handler::delete)
                .build();

        this.client = WebTestClient.bindToRouterFunction(route).build();
    }

    @Test
    void findAllTest() {
        Nourishment nourishment = Nourishment.builder().build();

        List<Nourishment> nourishments = List.of(nourishment);

        when(this.nourishmentService.getNourishments(any(), any(), any()))
                .thenReturn(Mono.just(new PageImpl<>(nourishments, Pageable.ofSize(10), nourishments.size())));
        when(this.listNourishmentMapper.mapOutNourishmentToListNourishmentsResponse(any()))
                .thenReturn(ListNourishmentsResponse.builder().build());

        this.client.get()
                .uri(URL.toString())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void findByIdTest() {
        String nourishmentId = "test-id";
        Nourishment nourishment = Nourishment.builder().nourishmentId(nourishmentId).build();

        when(this.nourishmentService.getNourishmentById(nourishmentId))
                .thenReturn(Mono.just(nourishment));
        when(this.getNourishmentMapper.mapOutNourishmentToGetNourishmentResponse(nourishment))
                .thenReturn(GetNourishmentResponse.builder().build());

        this.client.get()
                .uri(URL + "/" + nourishmentId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void findByNameTest() {
        String name = "test-name";
        Nourishment nourishment = Nourishment.builder().name(name).build();

        when(this.nourishmentService.getNourishmentByName(name))
                .thenReturn(Mono.just(nourishment));
        when(this.getNourishmentMapper.mapOutNourishmentToGetNourishmentResponse(nourishment))
                .thenReturn(GetNourishmentResponse.builder().build());

        this.client.get()
                .uri(URL + "/name/" + name)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void createTest() {
        String userId = "1";
        String categoryId = "2";
        Nourishment nourishment = Nourishment.builder().build();

        CreateNourishmentRequest request = CreateNourishmentRequest.builder().name("name").build();

        when(this.validator.validate(any()))
                .thenReturn(Mono.just(request));
        when(this.createNourishmentMapper.mapInCreateNourishmentRequestToNourishment(any()))
                .thenReturn(nourishment);
        when(this.nourishmentService.createNourishment(any(), any(), any()))
                .thenReturn(Mono.just(nourishment));

        this.client.post()
                .uri(URL + "/user/" + userId + "/category/" + categoryId)
                .body(Mono.just(request), CreateNourishmentRequest.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void updateTest() {
        String nourishmentId = "test-id";
        Nourishment nourishment = Nourishment.builder().nourishmentId(nourishmentId).build();

        UpdateNourishmentRequest request = UpdateNourishmentRequest.builder().build();

        when(this.validator.validate(any()))
                .thenReturn(Mono.just(request));
        when(this.updateNourishmentMapper.mapInUpdateNourishmentRequestToNourishment(any()))
                .thenReturn(nourishment);
        when(this.nourishmentService.updateNourishment(anyString(), any()))
                .thenReturn(Mono.just(nourishment));

        this.client.put()
                .uri(URL + "/" + nourishmentId)
                .body(Mono.just(request), UpdateNourishmentRequest.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteTest() {
        String nourishmentId = "test-id";

        when(this.nourishmentService.deleteNourishment(nourishmentId))
                .thenReturn(Mono.empty());

        this.client.delete()
                .uri(URL + "/" + nourishmentId)
                .exchange()
                .expectStatus().isNoContent();
    }
}