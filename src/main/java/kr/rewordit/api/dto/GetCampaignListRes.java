package kr.rewordit.api.dto;

import kr.rewordit.api.client.greenFee.message.data.CampaignData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCampaignListRes {

    private String adsIdx;

    private String adsFeedImg;

    private String adsSummary;

    private String adsCondition;

    private String adsReParticipate;

    private String adsName;

    private Integer adsRewardPrice;

    private String clickUrl;


    public static GetCampaignListRes from(CampaignData o) {
        return GetCampaignListRes.builder()
            .adsIdx(o.getAdsIdx())
            .adsFeedImg(o.getAdsFeedImg())
            .adsSummary(o.getAdsSummary())
            .adsCondition(o.getAdsCondition())
            .adsReParticipate(o.getAdsReParticipate())
            .adsName(o.getAdsName())
            .adsRewardPrice(o.getAdsRewardPrice())
            .clickUrl(o.getClickUrl())
            .build();
    }
}
