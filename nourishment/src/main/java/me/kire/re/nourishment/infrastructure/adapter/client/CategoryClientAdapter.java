package me.kire.re.nourishment.infrastructure.adapter.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.nourishment.infrastructure.rest.dto.GetCategoryResponse;
import me.kire.re.nourishment.domain.port.out.client.CategoryClientPort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryClientAdapter implements CategoryClientPort {

    private final WebClient webClient;

    @Override
    public Mono<GetCategoryResponse> getCategoryByCategoryId(Long categoryId) {
        log.info("Invoking CategoryClientAdapter.getCategoryByCategoryId method");
        return this.webClient
                .get()
                .uri("http://CATEGORY/api/v0/categories/{categoryId}", categoryId)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(GetCategoryResponse.class));
    }

}
