package kr.rewordit.api.client.greenFee.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.rewordit.api.client.greenFee.message.enumeration.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignListRequest {

    private String appcode;

    private SubCategory adsSubCate;

    private Integer ssmIdx;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer limit = 20;


    public LinkedMultiValueMap<String, String> toMap() {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        if (this.appcode != null) {
            map.add("appcode", this.appcode);
        }

        if (this.adsSubCate != null) {
            map.add("ads_sub_cate", String.valueOf(this.adsSubCate));
        }

        if (this.ssmIdx != null) {
            map.add("ssm_idx", String.valueOf(this.ssmIdx));
        }

        if (this.page != null) {
            map.add("page", String.valueOf(this.page));
        }

        if (this.limit != null) {
            map.add("limit", String.valueOf(this.limit));
        }

        return map;
    }
}
