package me.kire.re.nourishment.infrastructure.config;

import me.kire.re.nourishment.application.factory.NourishmentPriceUseCaseFactory;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentPriceRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NourishmentPriceConfig {
    @Bean
    public NourishmentPriceUseCaseFactory nourishmentPriceUseCaseFactory(NourishmentPriceRepositoryPort nourishmentPriceRepositoryPort) {
        return new NourishmentPriceUseCaseFactory(nourishmentPriceRepositoryPort);
    }
}
