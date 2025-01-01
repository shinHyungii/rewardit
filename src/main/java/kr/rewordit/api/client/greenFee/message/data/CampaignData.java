package kr.rewordit.api.client.greenFee.message.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.rewordit.api.client.greenFee.message.enumeration.AdsSubType;
import kr.rewordit.api.client.greenFee.message.enumeration.AdsType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CampaignData {

    @JsonProperty("ads_idx")
    private String adsIdx;

    @JsonProperty("ads_name")
    private String adsName;

    @JsonProperty("ads_package")
    private String adsPackage;

    @JsonProperty("ads_re_participate")
    private String adsReParticipate;

    @JsonProperty("ads_icon_img")
    private String adsIconImg;

    @JsonProperty("ads_feed_img")
    private String adsFeedImg;

    @JsonProperty("ads_condition")
    private String adsCondition;

    @JsonProperty("ads_summary")
    private String adsSummary;

    @JsonProperty("ads_reward_price")
    private Integer adsRewardPrice;

    @JsonProperty("ads_os_type")
    private String adsOsType;

    @JsonProperty("ads_type")
    private AdsType adsType;

    @JsonProperty("ads_sub_type")
    private AdsSubType adsSubType;

    @JsonProperty("click_url")
    private String clickUrl;
}
