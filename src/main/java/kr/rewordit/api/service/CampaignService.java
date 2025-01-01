package kr.rewordit.api.service;

import kr.rewordit.api.client.greenFee.GreenFeeClient;
import kr.rewordit.api.client.greenFee.message.CampaignListRequest;
import kr.rewordit.api.client.greenFee.message.data.CampaignData;
import kr.rewordit.api.common.PaginateResponse;
import kr.rewordit.api.dto.GetCampaignListReq;
import kr.rewordit.api.dto.GetCampaignListRes;
import kr.rewordit.api.repository.RewardRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final GreenFeeClient greenFeeClient;

    private final RewardRateRepository rewardRateRepository;

    @Value("${greenfee.appcode}")
    private String appcode;


    public PaginateResponse<GetCampaignListRes> index(GetCampaignListReq request) {
        CampaignListRequest campaignListRequest = CampaignListRequest.builder()
            .appcode(appcode)
            .adsSubCate(request.getAdsSubCate())
            .page(request.getPage())
            .limit(request.getPerPage())
            .build();

        List<CampaignData> campaignList = greenFeeClient.getCampaignList(campaignListRequest);

        List<GetCampaignListRes> collected = campaignList.stream()
            .map(GetCampaignListRes::from)
            .toList();

        return new PaginateResponse<>(request, collected);
    }
}
