package kr.rewordit.api.client.greenFee;

import kr.rewordit.api.client.greenFee.message.CampaignListRequest;
import kr.rewordit.api.client.greenFee.message.data.CampaignData;

import java.util.List;

public interface GreenFeeClient {

    List<CampaignData> getCampaignList(CampaignListRequest request);


}
