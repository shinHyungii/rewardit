package kr.rewordit.api.repository;

import kr.rewordit.api.model.RewardHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {

    List<RewardHistory> findAllByMemberId(Pageable pageable, Long memberId);
}
