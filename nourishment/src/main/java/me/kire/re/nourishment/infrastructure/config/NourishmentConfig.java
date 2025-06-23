package me.kire.re.nourishment.infrastructure.config;

import me.kire.re.nourishment.application.factory.NourishmentUseCaseFactory;
import me.kire.re.nourishment.application.usecases.PageMapper;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentRepositoryPort;
import me.kire.re.nourishment.domain.port.out.repository.NourishmentSortingRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NourishmentConfig {
    @Bean
    public NourishmentUseCaseFactory nourishmentUseCaseFactory(NourishmentRepositoryPort nourishmentRepositoryPort,
                                                               NourishmentSortingRepositoryPort nourishmentSortingRepositoryPort,
                                                               PageMapper pageMapper) {
        return new NourishmentUseCaseFactory(nourishmentRepositoryPort, nourishmentSortingRepositoryPort, pageMapper);
    }
}
