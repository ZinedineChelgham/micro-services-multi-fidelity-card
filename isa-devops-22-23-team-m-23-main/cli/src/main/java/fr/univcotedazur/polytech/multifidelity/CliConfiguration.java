package fr.univcotedazur.polytech.multifidelity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class CliConfiguration {
    private static final Duration TIMEOUT_DURATION = Duration.ofMillis(3000);
    private static final Duration READ_TIMEOUT = Duration.ofMillis(3000);
    @Value("${mfc.host.baseurl}")
    private String hostBaseUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri(hostBaseUrl).setConnectTimeout(TIMEOUT_DURATION).setReadTimeout(READ_TIMEOUT).build();
    }
}
