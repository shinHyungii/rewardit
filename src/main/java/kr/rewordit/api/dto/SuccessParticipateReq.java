package kr.rewordit.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.rewordit.api.client.greenFee.message.enumeration.AdsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessParticipateReq {

    @JsonProperty("ads_idx")
    private Integer adsIdx;

    @JsonProperty("ads_idx")
    private AdsType adsType;

    @JsonProperty("ads_name")
    private String adsName;

    @JsonProperty("rwd_cost")
    private Integer rwsCost;

    @JsonProperty("ptn_cost")
    private Integer ptnCost;

    private String etc;

    @JsonProperty("app_uid")
    private String appUid;

    @JsonProperty("gp_key")
    private String gpKey;

    @JsonProperty("rwd_date")
    private LocalDateTime rwdDate;

    @JsonAlias({"adid", "idfa"})
    private String adid;

    @JsonProperty("ads_re_participate")
    private String adsReParticipate;
}
