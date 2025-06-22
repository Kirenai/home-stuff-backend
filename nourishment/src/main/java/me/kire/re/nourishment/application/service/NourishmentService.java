package me.kire.re.nourishment.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kire.re.nourishment.domain.model.Nourishment;
import me.kire.re.nourishment.domain.port.in.CreateNourishmentPort;
import me.kire.re.nourishment.domain.port.in.DeleteNourishmentPort;
import me.kire.re.nourishment.domain.port.in.GetNourishmentByNamePort;
import me.kire.re.nourishment.domain.port.in.GetNourishmentPort;
import me.kire.re.nourishment.domain.port.in.ListNourishmentsPort;
import me.kire.re.nourishment.domain.port.in.UpdateNourishmentPort;
import me.kire.re.nourishment.domain.port.out.client.CategoryClientPort;
import me.kire.re.nourishment.domain.port.out.client.UserClientPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class NourishmentService {

    private final GetNourishmentPort getNourishmentPort;
    private final GetNourishmentByNamePort getNourishmentByNamePort;
    private final ListNourishmentsPort listNourishmentsPort;
    private final CreateNourishmentPort createNourishmentPort;
    private final UpdateNourishmentPort updateNourishmentPort;
    private final DeleteNourishmentPort deleteNourishmentPort;
    private final UserClientPort userClientPort;
    private final CategoryClientPort categoryClientPort;

    public Mono<Page<Nourishment>> getNourishments(PageRequest pageable, String userId, Boolean isAvailable) {
        log.info("Invoking NourishmentService.getNourishments method");
        return this.listNourishmentsPort.getNourishments(pageable, userId, isAvailable);
    }

    public Mono<Nourishment> getNourishmentById(String nourishmentId) {
        log.info("Invoking NourishmentService.getNourishmentById method");
        return this.getNourishmentPort.findById(nourishmentId);
    }

    public Mono<Nourishment> getNourishmentByName(String name) {
        log.info("Invoking NourishmentService.getNourishmentBy method");
        return this.getNourishmentByNamePort.execute(name);
    }

    public Mono<Nourishment> createNourishment(Long userId, Long categoryId, Nourishment nourishment) {
        log.info("Invoking NourishmentService.createNourishment method");
        nourishment.setIsAvailable(Boolean.TRUE);
//        return this.userClientPort.getUserByUserId(userId)
//                .flatMap(userResponse -> {
//                    nourishment.setUserId(userResponse.userId());
//                    return this.categoryClientPort.getCategoryByCategoryId(categoryId);
//                })
//                .flatMap(categoryResponse -> {
//                    nourishment.setCategoryId(categoryResponse.categoryId());
//                    return this.createNourishmentPort.createNourishment(nourishment);
//                });
        nourishment.setUserId(userId);
        nourishment.setCategoryId(categoryId);
        return this.createNourishmentPort.createNourishment(nourishment);
    }

    public Mono<Nourishment> updateNourishment(String nourishmentId, Nourishment nourishment) {
        log.info("Invoking NourishmentService.updateNourishment method");
        return this.updateNourishmentPort.updateNourishment(nourishmentId, nourishment);
    }

    public Mono<Void> deleteNourishment(String nourishmentId) {
        log.info("Invoking NourishmentService.deleteNourishment method");
        return this.deleteNourishmentPort.execute(nourishmentId);
    }

}