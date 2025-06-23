package me.kire.re.nourishment.application.usecases;

import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentSortingRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListNourishmentsUseCaseTest {
    @InjectMocks
    private ListNourishmentsUseCase useCase;
    @Mock
    private NourishmentSortingRepositoryPort port;
    @Mock
    private PageMapper pageMapper;

    @Test
    void testGetNourishments() {
        Nourishment nourishment = new Nourishment();
        PageImpl<Nourishment> page = new PageImpl<>(List.of(nourishment));

        when(this.port.findAll(any(), anyString(), anyBoolean())).thenReturn(Flux.just(nourishment));
        when(this.port.count()).thenReturn(Mono.just(1L));
        when(this.pageMapper.toPage(anyList(), any(), anyLong()))
                .thenReturn(page);

        Mono<Page<Nourishment>> result = useCase.getNourishments(Pageable.unpaged(), "userId", true);

        StepVerifier.create(result)
                .expectNext(page)
                .verifyComplete();

        verify(this.port).findAll(any(), anyString(), anyBoolean());
    }
}