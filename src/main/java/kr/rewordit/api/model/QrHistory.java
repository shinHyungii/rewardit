package kr.rewordit.api.model;

import jakarta.persistence.*;
import kr.rewordit.api.dto.enumeration.QrHistoryStatus;
import kr.rewordit.api.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String qrId;

    @Column(columnDefinition = "TEXT")
    private String qrImage;

    private Integer requestPoint;

    private OffsetDateTime requestAt;

    private OffsetDateTime usedAt;

    @Column(nullable = false)
    private String status;

    @CreatedDate
    @Column(nullable = false)
    private OffsetDateTime createdAt;


    public void usedAt(Shop shop) {
        this.shop = shop;
        this.status = QrHistoryStatus.USED.name();
        this.usedAt = DateUtils.now();
    }
}
