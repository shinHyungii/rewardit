package kr.rewordit.api.repository;

import kr.rewordit.api.model.RewarditEvent;
import kr.rewordit.api.repository.custom.RewarditEventCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewarditEventRepository extends JpaRepository<RewarditEvent, Long>, RewarditEventCustomRepository {

}
