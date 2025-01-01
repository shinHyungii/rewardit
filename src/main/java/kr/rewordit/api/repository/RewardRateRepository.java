package kr.rewordit.api.repository;

import kr.rewordit.api.model.RewardRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRateRepository extends JpaRepository<RewardRate, Long> {

}
