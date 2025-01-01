package kr.rewordit.api.repository;

import kr.rewordit.api.model.QrHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrHistoryRepository extends JpaRepository<QrHistory, Long> {

    Optional<QrHistory> findByQrId(String qrId);
}
