package se.nt1dc.authservice.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {
    @Bean
    @LoadBalanced
    fun restTemplateBean(): RestTemplate {
        return RestTemplate()
    }
}