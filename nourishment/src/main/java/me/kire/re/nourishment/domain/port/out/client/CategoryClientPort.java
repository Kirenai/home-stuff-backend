package me.kire.re.nourishment.domain.port.out.client;

import me.kire.re.nourishment.infrastructure.rest.dto.GetCategoryResponse;
import reactor.core.publisher.Mono;

public interface CategoryClientPort {

    Mono<GetCategoryResponse> getCategoryByCategoryId(Long categoryId);

}
