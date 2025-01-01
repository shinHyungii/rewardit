package kr.rewordit.api.repository;

import kr.rewordit.api.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginIdOrEmail(String loginId, String email);

    Optional<Member> findByEmail(String email);
}
