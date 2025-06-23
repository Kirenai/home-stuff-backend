package me.kire.re.nourishment.application.service;

import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.in.CreateNourishmentPort;
import me.kire.re.nourishment.domain.port.in.DeleteNourishmentPort;
import me.kire.re.nourishment.domain.port.in.GetNourishmentByNamePort;
import me.kire.re.nourishment.domain.port.in.GetNourishmentPort;
import me.kire.re.nourishment.domain.port.in.ListNourishmentsPort;
import me.kire.re.nourishment.domain.port.in.UpdateNourishmentPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NourishmentServiceTest {
    @InjectMocks
    private NourishmentService nourishmentService;
    @Mock
    private GetNourishmentPort getNourishmentPort;
    @Mock
    private GetNourishmentByNamePort getNourishmentByNamePort;
    @Mock
    private ListNourishmentsPort listNourishmentsPort;
    @Mock
    private CreateNourishmentPort createNourishmentPort;
    @Mock
    private UpdateNourishmentPort updateNourishmentPort;
    @Mock
    private DeleteNourishmentPort deleteNourishmentPort;

    @Test
    void testGetNourishments() {
        Page<Nourishment> pageEmpty = Page.empty();
        when(this.listNourishmentsPort.getNourishments(any(), anyString(), anyBoolean()))
                .thenReturn(Mono.just(pageEmpty));

        Mono<Page<Nourishment>> publisher = this.nourishmentService
                .getNourishments(PageRequest.ofSize(10), "userId", true);

        StepVerifier
                .create(publisher)
                .expectNext(pageEmpty)
                .verifyComplete();

        verify(this.listNourishmentsPort)
                .getNourishments(any(), anyString(), anyBoolean());
    }

    @Test
    void testGetNourishmentById() {
        Nourishment nourishment = new Nourishment();
        nourishment.setNourishmentId("123");
        when(this.getNourishmentPort.findById(anyString()))
                .thenReturn(Mono.just(nourishment));

        Mono<Nourishment> publisher = this.nourishmentService
                .getNourishmentById("nourishmentId");

        StepVerifier
                .create(publisher)
                .assertNext(value -> assertEquals(nourishment.getNourishmentId(), value.getNourishmentId()))
                .verifyComplete();

        verify(this.getNourishmentPort).findById(anyString());
    }

    @Test
    void testGetNourishmentByName() {
        Nourishment nourishment = new Nourishment();
        nourishment.setName("Test Nourishment");
        when(this.getNourishmentByNamePort.execute(anyString()))
                .thenReturn(Mono.just(nourishment));

        Mono<Nourishment> publisher = this.nourishmentService
                .getNourishmentByName("Test Nourishment");

        StepVerifier
                .create(publisher)
                .assertNext(value -> assertEquals(nourishment.getName(), value.getName()))
                .verifyComplete();

        verify(this.getNourishmentByNamePort).execute(anyString());
    }

    @Test
    void testCreateNourishment() {
        Nourishment nourishment = new Nourishment();
        nourishment.setName("New Nourishment");
        nourishment.setDescription("New Description");
        nourishment.setIsAvailable(true);
        nourishment.setUserId(1L);
        nourishment.setCategoryId(1L);

        when(this.createNourishmentPort.createNourishment(any()))
                .thenReturn(Mono.just(nourishment));

        Mono<Nourishment> publisher = this.nourishmentService
                .createNourishment(1L, 1L, nourishment);

        StepVerifier
                .create(publisher)
                .assertNext(value -> {
                    assertEquals(nourishment.getName(), value.getName());
                    assertEquals(nourishment.getDescription(), value.getDescription());
                    assertEquals(nourishment.getIsAvailable(), value.getIsAvailable());
                    assertEquals(nourishment.getUserId(), value.getUserId());
                    assertEquals(nourishment.getCategoryId(), value.getCategoryId());
                })
                .verifyComplete();

        verify(this.createNourishmentPort).createNourishment(any());
    }

    @Test
    void testUpdateNourishment() {
        when(this.updateNourishmentPort.updateNourishment(anyString(), any()))
                .thenReturn(Mono.just(Nourishment.builder().name("Updated Nourishment").build()));

        Mono<Nourishment> publisher = this.nourishmentService
                .updateNourishment("1", Nourishment.builder().name("Nourishment init").build());

        StepVerifier
                .create(publisher)
                .assertNext(value -> {
                    assertEquals("Updated Nourishment", value.getName());
                })
                .verifyComplete();

        verify(this.updateNourishmentPort).updateNourishment(anyString(), any());
    }

    @Test
    void testDeleteNourishment() {
        when(this.deleteNourishmentPort.execute(anyString()))
                .thenReturn(Mono.empty());

        Mono<Void> publisher = this.nourishmentService
                .deleteNourishment("nourishmentId");

        StepVerifier
                .create(publisher)
                .verifyComplete();

        verify(this.deleteNourishmentPort).execute(anyString());
    }
}