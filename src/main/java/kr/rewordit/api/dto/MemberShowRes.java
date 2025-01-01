package kr.rewordit.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberShowRes {

    private Long id;

    private String email;

    private String name;

    private Integer rewardPoint;

    private Integer usedPoint;
}
