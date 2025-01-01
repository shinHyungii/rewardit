package kr.rewordit.api.dto;

import kr.rewordit.api.model.RewardHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardHistoryIndexRes {

    private Long id;

    private String adsType;

    private String eventName;

    private Integer reward;

    private OffsetDateTime acceptedAt;


    public static RewardHistoryIndexRes from(RewardHistory o) {
        RewardHistoryIndexRes t = new RewardHistoryIndexRes();
        t.setId(o.getId());
        t.setAdsType(o.getAdsSubType());
        t.setEventName(o.getEventName());
        t.setReward(o.getUsersReward());
        t.setAcceptedAt(o.getAcceptedAt());
        
        return t;
    }
}
