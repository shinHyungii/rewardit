package kr.rewordit.api.client.greenFee.impl;

import kr.rewordit.api.client.greenFee.GreenFeeClient;
import kr.rewordit.api.client.greenFee.message.CampaignListRequest;
import kr.rewordit.api.client.greenFee.message.data.CampaignData;
import kr.rewordit.api.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GreenFeeClientImpl implements GreenFeeClient {

    private final RestTemplate greenPTemplate;

    private final ParameterizedTypeReference<List<CampaignData>> GET_CAMPAIGN_LIST_TR = new ParameterizedTypeReference<>() {
    };


    @Override
    public List<CampaignData> getCampaignList(CampaignListRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            ResponseEntity<List<CampaignData>> exchange = greenPTemplate.exchange(
                "/ads_json.html",
                HttpMethod.POST,
                new HttpEntity<>(request.toMap(), headers),
                GET_CAMPAIGN_LIST_TR
            );

            return exchange.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error : {}", e.getMessage());

            throw new CustomException("캠페인 목록 조회 실패");
        }
    }
}
