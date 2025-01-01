package kr.rewordit.api.model;

import jakarta.persistence.*;
import kr.rewordit.api.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    private String password;

    @Column(nullable = false)
    private String email;

    private String name;

    private OffsetDateTime lastLogin;

    @Column(nullable = false)
    private Integer rewardPoint;

    private Integer usedPoint;

    @CreatedDate
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "member")
    private List<RewardHistory> rewardHistoryList;


    public void givePoint(int point) {
        this.rewardPoint += point;
    }


    public void usePoint(int point) {
        this.rewardPoint -= point;
    }


    public void loggedIn() {
        this.lastLogin = DateUtils.now();
    }
}
