package kr.rewordit.api.repository.custom;

import kr.rewordit.api.model.RewarditEvent;

import java.time.OffsetDateTime;
import java.util.List;

public interface RewarditEventCustomRepository {

    List<RewarditEvent> findAllEvent(OffsetDateTime now);
}
