package me.kire.re.nourishment.domain.port.in;

import me.kire.re.nourishment.domain.model.Nourishment;
import reactor.core.publisher.Mono;

public interface UpdateNourishmentPort {

    Mono<Nourishment> updateNourishment(String nourishmentId, Nourishment nourishment);

}
