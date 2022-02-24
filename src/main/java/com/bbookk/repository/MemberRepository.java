package com.bbookk.repository;

import com.bbookk.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> ,MemberRepositoryCustom{
    Member findByLoginId(String loginId);
    Boolean existsByLoginIdAndPassword(String loginId, String password);
}
