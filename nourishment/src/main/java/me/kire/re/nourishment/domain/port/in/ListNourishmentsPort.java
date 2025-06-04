package me.kire.re.nourishment.domain.port.in;

import me.kire.re.nourishment.domain.model.Nourishment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface ListNourishmentsPort {

    Mono<Page<Nourishment>> getNourishments(Pageable pageable, String userId, Boolean isAvailable);

}
