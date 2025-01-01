package kr.rewordit.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GreenFeeClientConfig {

    @Value("${greenfee.url}")
    private String greenFeeUrl;


    @Bean(name = "greenFeeTemplate")
    public RestTemplate greenFeeTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder
            .rootUri(greenFeeUrl)
            .build();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        return restTemplate;
    }

}
