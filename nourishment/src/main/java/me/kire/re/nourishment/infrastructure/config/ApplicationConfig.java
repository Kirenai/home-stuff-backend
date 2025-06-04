package me.kire.re.nourishment.infrastructure.config;

import me.kire.re.nourishment.application.factory.NourishmentPriceUseCaseFactory;
import me.kire.re.nourishment.application.factory.NourishmentUseCaseFactory;
import me.kire.re.nourishment.application.service.NourishmentPriceService;
import me.kire.re.nourishment.application.service.NourishmentService;
import me.kire.re.nourishment.domain.port.out.client.CategoryClientPort;
import me.kire.re.nourishment.domain.port.out.client.UserClientPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public NourishmentService nourishmentServiceMongo(NourishmentUseCaseFactory nourishmentUseCaseFactory,
                                                      UserClientPort userClientPort,
                                                      CategoryClientPort categoryClientPort) {
        return new NourishmentService(
                nourishmentUseCaseFactory.getNourishmentPort(),
                nourishmentUseCaseFactory.getNourishmentByNamePort(),
                nourishmentUseCaseFactory.listNourishmentsPort(),
                nourishmentUseCaseFactory.createNourishmentPort(),
                nourishmentUseCaseFactory.updateNourishmentPort(),
                nourishmentUseCaseFactory.deleteNourishmentPort(),
                userClientPort,
                categoryClientPort
        );
    }

    @Bean
    public NourishmentPriceService nourishmentPriceServiceMongo(NourishmentPriceUseCaseFactory nourishmentPriceUseCaseFactory) {
        return new NourishmentPriceService(
                nourishmentPriceUseCaseFactory.listNourishmentsPricePort(),
                nourishmentPriceUseCaseFactory.createNourishmentPricePort()
        );
    }
}
