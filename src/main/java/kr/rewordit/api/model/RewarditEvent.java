package kr.rewordit.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RewarditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OffsetDateTime eventStart;

    private OffsetDateTime eventEnd;

    @Column(nullable = false)
    private String eventName;

    @Column(nullable = false)
    private Integer eventPoint;

    private Integer memberLimit;

    @CreatedDate
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime deletedAt;


    public void decMemberLimit() {
        if (this.memberLimit != null) {
            this.memberLimit--;
        }
    }
}
