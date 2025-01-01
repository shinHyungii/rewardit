package kr.rewordit.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "google")
public class GoogleConfig {

    private String clientId;

    private String clientSecret;

    private UrlProperties url;

    @Setter
    public static class UrlProperties {

        private String loginPage;
        private String token;
        private String userProfile;
        private String redirection;

        public String loginPage() {
            return loginPage;
        }

        public String token() {
            return token;
        }

        public String userProfile() {
            return userProfile;
        }

        public String redirection() {
            return redirection;
        }
    }


    @Bean(name = "googleClient")
    public RestClient googleClient(RestTemplateBuilder restTemplateBuilder) {
        return RestClient.builder()
            .requestFactory(new HttpComponentsClientHttpRequestFactory())
            .build();

    }
}
