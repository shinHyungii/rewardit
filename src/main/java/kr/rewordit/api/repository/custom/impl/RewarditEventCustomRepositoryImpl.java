package kr.rewordit.api.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.rewordit.api.model.RewarditEvent;
import kr.rewordit.api.repository.custom.RewarditEventCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

import static kr.rewordit.api.model.QRewarditEvent.rewarditEvent;

@Repository
@RequiredArgsConstructor
public class RewarditEventCustomRepositoryImpl implements RewarditEventCustomRepository {


    private final JPAQueryFactory query;


    @Override
    public List<RewarditEvent> findAllEvent(OffsetDateTime now) {
        return query.selectFrom(rewarditEvent)
            .where(
                rewarditEvent.eventEnd.isNull()
                    .or(
                        rewarditEvent.eventEnd.after(now)
                            .and(rewarditEvent.eventStart.before(now))
                    )
                    .and(rewarditEvent.deletedAt.isNull())
            )
            .fetch();
    }
}
