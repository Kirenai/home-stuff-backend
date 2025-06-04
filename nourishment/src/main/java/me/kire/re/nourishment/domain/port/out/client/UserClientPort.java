package me.kire.re.nourishment.domain.port.out.client;

import me.kire.re.nourishment.infrastructure.rest.dto.GetUserResponse;
import reactor.core.publisher.Mono;

public interface UserClientPort {

    Mono<GetUserResponse> getUserByUserId(Long userId);

}
