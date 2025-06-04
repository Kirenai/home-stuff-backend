package me.kire.re.nourishment.infrastructure.config;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder,
                               ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        return builder.filter(loadBalancerExchangeFilterFunction).build();
    }

}
