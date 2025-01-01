package kr.rewordit.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.rewordit.api.client.greenFee.message.enumeration.SubCategory;
import kr.rewordit.api.common.PagingRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCampaignListReq extends PagingRequest {

    private SubCategory adsSubCate;

}
