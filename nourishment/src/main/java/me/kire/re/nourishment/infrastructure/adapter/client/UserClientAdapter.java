package me.kire.re.nourishment.infrastructure.adapter.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.nourishment.infrastructure.rest.dto.GetUserResponse;
import me.kire.re.nourishment.domain.port.out.client.UserClientPort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserClientAdapter implements UserClientPort {

    private final WebClient webClient;

    @Override
    public Mono<GetUserResponse> getUserByUserId(Long userId) {
        log.info("Invoking UserClientAdapter.getUserByUserId method");
        return this.webClient
                .get()
                .uri("http://USER/api/v0/users/{userId}", userId)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(GetUserResponse.class));
    }

}
