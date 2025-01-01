package kr.rewordit.api.client.google;

import kr.rewordit.api.client.google.message.SocialAccountProfile;
import kr.rewordit.api.config.GoogleConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class GoogleLoginClient {

    private final GoogleConfig config;
    private final RestClient googleClient;

    public SocialAccountProfile getProfile(final String accessToken) {
        return googleClient.get()
            .uri(config.getUrl().userProfile())
            .header("Authorization", String.format("Bearer %s", accessToken))
            .retrieve()
            .body(SocialAccountProfile.class);
    }
}
